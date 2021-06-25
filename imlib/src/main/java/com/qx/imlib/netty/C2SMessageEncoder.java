package com.qx.imlib.netty;


import com.qx.imlib.SystemCmd;
import com.qx.imlib.qlog.QLog;
import com.qx.imlib.utils.encry.CryptUtil;
import com.qx.imlib.utils.encry.Key;
import com.qx.imlib.utils.event.SysConstants;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息编码器（S2C）
 * 协议规则（cmd后面可能没有数据，解码时需根据contentLen-5是否大于0判断解析）：
 * contentLen（不包含自身4byte）  |  $TQ  |  cmd  |  aesKeyLen  |  aesKey(RSA加密)  |  securityBody[contentLen - 5 - 2 -
 * aesKeyLen](AES加密)
 * 4byte                           3byte   2byte   2byte         [aesKeyLen]byte     [contentLen - 5 - 2 -
 * aesKeyLen]byte
 * body部分(ProtoBuf对象)：
 * |  {ByteBuf}  |
 * Nbyte
 * 说明：
 * 1. AES加密key每次通信随机生成，长度固定6位（一次性使用）
 * 2. AES加密内容只包含AES的加密key（控制性能：RSA加密长度越长，性能越差）
 * 3. aesKeyLen标记RSA加密AES-key后的值
 * 4. AES加密body部分(ProtoBuf对象)
 *
 * @author hechuan
 * @since JDK 1.8
 */
public class C2SMessageEncoder extends MessageToByteEncoder<S2CSndMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, S2CSndMessage msg, ByteBuf out) throws Exception {
        // contentLen(4) + $(1) + T(1) + Q(1) + cmd(2) + sequence(8) + aesKeyLen(2) + aesKey(aesKeyLen) + body(N)
        try {
            short version = C2SRouter.VERSION;
            //报文长度
            int contentLen = 7;// V(2) + $(1) + T(1) + Q(1) + cmd(2) = 7
            short cmd = msg.getCmd();
            long sequence = -1;
            if (cmd!= SystemCmd.S2C_HEARTBEAT){
                sequence = msg.getSequence();
                contentLen += 8;
            }
            //ack body 部分不加密（性能考虑）
            byte[] bodyBytes = null;
            if (null != msg.getBody()) {
                // AES加密body
                byte[] realBytes = msg.getBody().toByteArray(); // Nbyte
                if (cmd == SystemCmd.C2S_AES_KEY) {
                    bodyBytes = CryptUtil.RSAEncrypt(realBytes, Key.TCP_SERVER_PUB_KEY); // 服务端公钥加密
                } else if (cmd == SystemCmd.C2S_RSA_KEY) {
                    bodyBytes = realBytes;
                } else {
                    String aesKey = C2SRouter.aesKey;
                    bodyBytes = CryptUtil.AESEncrypt(realBytes, aesKey);
                }
                contentLen += bodyBytes.length; // body(N)
            }

            // 写入报文长度
            out.writeInt(contentLen); // 4byte
            // 写入version
            out.writeShort(version); // 2byte
            // 写入$TQ
            out.writeByte(SysConstants._$); // 1byte
            out.writeByte(SysConstants._T); // 1byte
            out.writeByte(SysConstants._Q); // 1byte
            // 写入cmd
            out.writeShort(cmd); // 2byte
            if (sequence != -1) {
                out.writeLong(sequence); // 8byte
            }

            // 写入body
            if (null != bodyBytes) {
                out.writeBytes(bodyBytes);
            }
            // body为空，则无需写入
        }catch (Exception e){
            QLog.e("C2SMessageEncoder", "编码异常：" + e.getMessage());
        }
    }
}

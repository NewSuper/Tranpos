package com.qx.imlib.netty;

import com.qx.imlib.SystemCmd;
import com.qx.imlib.qlog.QLog;
import com.qx.imlib.utils.encry.CryptUtil;
import com.qx.imlib.utils.encry.Key;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 消息解码器（C2S）
 * 协议规则（cmd后面可能没有数据，解码时需根据contentLen-5是否大于0判断解析）：
 * contentLen（不包含自身4byte）  |  $TQ  |  cmd  |  sequence  |  aesKeyLen  |  aesKey(RSA加密)  |  securityBody[contentLen -
 * 13 - 2 - aesKeyLen](AES加密)
 * 4byte                           3byte   2byte   8byte        2byte         [aesKeyLen]byte     [contentLen - 13 -
 * 2 - aesKeyLen]byte
 * body部分(ProtoBuf对象)：
 * |  {ByteBuf}  |
 * Nbyte
 * 说明：
 * 1. AES加密key每次通信随机生成，长度固定16位（一次性使用）
 * 2. AES加密内容只包含AES的加密key（控制性能：RSA加密长度越长，性能越差）
 * 3. aesKeyLen标记RSA加密AES-key后的值
 * 4. AES加密body部分(ProtoBuf对象)
 * 5. sequence为每次通信的通信序号
 *
 * @author hechuan
 * @since JDK 1.8
 */
public class C2SMessageDecoder extends ByteToMessageDecoder {

    //static volatile ByteBuf in;
    ByteBuf temp;
    static volatile List<Object> mOut;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // contentLen(4) + $(1) + T(1) + Q(1) + cmd(2) + sequence(8) + aesKeyLen(2) + aesKey(aesKeyLen) + body(N)


        try {

            if (in.readableBytes() < 9) { // contentLen(4) + $(1) + T(1) + Q(1) + cmd(2) + sequence(8) = 9
                QLog.e("C2SMessageDecoder", "非法协议：长度（小于9）：" + in.readableBytes());
                return;
            }

            // 1.读取报文长度
            int contentLen = in.readInt();
            if (in.readableBytes() < contentLen) {
                in.readerIndex(in.readerIndex() - 4); // contentLen(4)
                return;
            }
            //2.读取$TQ（3个字节）
            in.readByte(); // $ = 36
            in.readByte(); // T = 84
            in.readByte(); // Q = 81

            // 3.读取协议号（2个字节）
            short cmd = in.readShort();
            S2CRecMessage recMessage = new S2CRecMessage();
            recMessage.setCmd(cmd);

            if (contentLen > 5) {
                //4.读sequence（8个字节）
                long sequence = in.readLong();
                recMessage.setSequence(sequence);
            }

            // contentLen > 13 表示有body，则继续解析报文，$(1) + T(1) + Q(1) + cmd(2) + sequence(8) = 13
            if (contentLen > 13) {
                byte[] bodyBytes = new byte[contentLen - 13];
                in.readBytes(bodyBytes);
                byte[] realBytes = null;
                if (cmd == SystemCmd.S2C_ACK) { // ack body 不加密
                    //处理ack命令，ack不加密，不需要做解密处理
                    realBytes = bodyBytes;
                } else {
                    // 非ACK
                    if (cmd == SystemCmd.S2C_AES_KEY || cmd == SystemCmd.S2C_RSA_KEY) {
                        realBytes = CryptUtil.RSADecrypt(bodyBytes, Key.TCP_CLIENT_PRI_KEY); // 客户端私钥解密
                        // 后续对应协议处理方法会将服务端生成并返回的aesKey设置到C2SRouter.aesKey，else部分即可使用其值
                    } else {
                        realBytes = CryptUtil.AESDecrypt(bodyBytes, C2SRouter.aesKey);
                    }
                }
                // realBytes本身即为proto流
                recMessage.setContents(realBytes); // Nbyte
            }

            out.add(recMessage);
        } catch (Exception e) {
            e.printStackTrace();
            QLog.e("C2SMessageDecoder", "解码异常：" + e.getMessage());
        }
    }

}
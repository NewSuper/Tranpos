package com.qx.imlib.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qx.im.model.UserInfoCache;
import com.qx.imlib.db.IMDatabaseRepository;
import com.qx.imlib.db.entity.ConversationEntity;
import com.qx.imlib.db.entity.MessageEntity;
import com.qx.imlib.db.entity.TBUnTrustTime;
import com.qx.imlib.netty.ConnectionStatusListener;
import com.qx.imlib.netty.HeartBeatHolder;
import com.qx.imlib.netty.NettyConnectionState;
import com.qx.imlib.netty.NettyConnectionStateManager;
import com.qx.imlib.netty.S2CRecMessage;
import com.qx.imlib.qlog.QLog;
import com.qx.imlib.utils.event.EventBusUtil;
import com.qx.it.protos.S2CAuth;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;

public class AuthHandler extends BaseCmdHandler{
    @Override
    public void handle(ChannelHandlerContext ctx, S2CRecMessage recMessage) throws InvalidProtocolBufferException {
        S2CAuth.AuthSuccess authSuccess = S2CAuth.AuthSuccess.parseFrom(recMessage.getContents());
        QLog.i("AuthHandler", "auth成功，auth：" + authSuccess.toString());
        //userid 缓存在本地
        UserInfoCache.INSTANCE.setUserId(authSuccess.getUserId());
        //连接成功，设置状态为NettyConnectionState.STATE_CONNECTED
        NettyConnectionStateManager.getInstance().setState(NettyConnectionState.STATE_CONNECTED);
        //启动心跳保活
        HeartBeatHolder.getInstance().startHeartBeatService();
        //回调给model层通知sdk init success
        EventBusUtil.post(ConnectionStatusListener.Status.CONNECTED);
        //处理不信任时间区域
        handleUnTrustTime(authSuccess.getTimestamp());
    }
    private void handleUnTrustTime(long endTime){
        try {
            List<ConversationEntity> conversations = IMDatabaseRepository.Companion.getInstance().getAllConversation();
            if (conversations!=null && conversations.size()>0){
                for (ConversationEntity conversationEntity:conversations){
                    MessageEntity messageEntity = IMDatabaseRepository.Companion.getInstance().getLatestMessageByConversationId(conversationEntity.getConversationId(), conversationEntity.getOwnerId());
                    if (messageEntity !=null){
                        TBUnTrustTime tbUnTrustTime = TBUnTrustTime.obtain(conversationEntity.getOwnerId(), conversationEntity.getConversationType(),
                                conversationEntity.getTargetId(), messageEntity.getTimestamp(), endTime);
                        IMDatabaseRepository.Companion.getInstance().insertUnTrustTime(tbUnTrustTime);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

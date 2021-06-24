package com.qx.imlib;


public class SystemCmd {

    public static final short S2C_AES_KEY = 3; // 返回aesKey
    public static final short C2S_AES_KEY = 3; // 请求aesKey
    public static final short S2C_RSA_KEY = 4; // 返回RsaKey
    public static final short C2S_RSA_KEY = 4; // 请求RsaKey

    /** 服务端->客户端协议，前缀S2C_ **/
    public static final short S2C_ACK = 1; // ACK确认协议，协议文档：无
    public static final short S2C_BUSY = 2; // 系统繁忙，协议文档：无
    public static final short S2C_MAINTENANCE = 10; // 服务端维护，协议文档：无
    public static final short S2C_HEARTBEAT = 11; // 服务端-客户端心跳响应，协议文档：无
    public static final short S2C_ERROR = 12; // 发生错误，协议文档：S2C_ErrorResponse.proto
    public static final short S2C_KICK = 15; // 节点被踢出，服务端会断开连接，协议文档：无
    public static final short S2C_AUTH_SUCCESS = 16; // 登录成功，协议文档：S2C_AuthSuccess.proto
    public static final short S2C_DESTORY_SUCCESS = 17; // 销毁SDK（登出）成功，协议文档：无
    public static final short S2C_PROPERTY = 18; // 一些敏感词，RTC 相关配置 S2C_Property.proto

    public static final short S2C_SEND_MESSAGE_STATE = 501; // 消息状态确认，协议文档：S2C_MessageStatus.proto
    public static final short S2C_SEND_READ_COUNT = 502; // 消息阅读次数，协议文档：S2C_MessageRead.proto
    public static final short S2C_MESSAGE_MUTED = 503; // 会话免打扰，协议文档：S2C_SpecialOperation.proto
    public static final short S2C_USER_CLOSURE = 504; // 用户封禁，协议文档：无
    public static final short S2C_GLOBAL_GROUP_FORBIDDEN = 505; // 单个成员所在的所有群禁言，协议文档：S2C_GlobalOperation.proto
    public static final short S2C_GLOBAL_CHATROOM_FORBIDDEN = 506; // 全局聊天室禁言，协议文档：S2C_GlobalOperation.proto
    public static final short S2C_CHATROOM_DESTORY = 507; // 聊天室销毁，协议文档：无
    public static final short S2C_CHATROOM_MEMBER_CLOSURE = 508; // 聊天室封禁成员，协议文档：S2C_SpecialOperation.proto
    public static final short S2C_CHATROOM_MEMBER_FORBIDDEN = 509; // 聊天室成员禁言，协议文档：S2C_SpecialOperation.proto
    public static final short S2C_SESSION_TOP = 510; // 会话置顶，协议文档：S2C_SpecialOperation.proto
    public static final short S2C_MESSAGE_LIST_PAGED = 511; // 分页聊天记录，协议文档：S2C_MessageRecords.proto
    public static final short S2C_CHATROOM_GET_PROP = 512; // 获取聊天室属性，协议文档：C2S_ChatroomProperty.proto
    public static final short S2C_MESSAGE_STATUS_LIST = 513; //消息状态列表，协议文档：C2S_GetMessageStatus.proto -> GetMessageStatusResult
    public static final short S2C_SESSION_UNREAD_COUNT = 514; // 会话未读消息数，协议文档：S2C_SessionUnReadCount.proto
    public static final short S2C_GROUP_FORBIDDEN = 515; // 群聊个体成员禁言，协议文档：S2C_SpecialOperation.proto
    public static final short S2C_MESSAGE_LIST_OFFLINE = 516;//(离线消息）未确认未阅读消息分页查询,协议文档：S2C_MessageRecords.proto
    public static final short S2C_GROUP_ALL_FORBIDDEN = 517;//群组全部成员禁言，该群下所有人都不能发消息，协议文档：S2C_SpecialOperation.proto
    public static final short S2C_GROUP_DESTROY = 518;//群组解散，协议文档：无
    public static final short S2C_GROUP_WHITELIST = 519;//群组白名单，协议文档：使用群组禁言协议
    public static final short S2C_AT_MESSAGES = 520;//群组at消息列表，协议文档：S2C_AtMessages.proto
    //


    public static final short S2C_VIDEO_CALL = 540;//音视频呼叫，协议文档：S2C_VideoCall.proto
    public static final short S2C_VIDEO_ANSWER = 541;//对方接听，协议文档：S2C_VideoAnswered.proto
    public static final short S2C_VIDEO_OTHER_ANSWER = 542;//其他设备接听，协议文档：S2C_VideoAnswered.proto
    public static final short S2C_VIDEO_REFUSE = 543;//对方拒接，协议文档：S2C_VideoAnswered.proto
    public static final short S2C_VIDEO_CANCEL = 544;//对方已取消，协议文档：S2C_VideoAnswered.proto
    public static final short S2C_VIDEO_OUT_TIME = 545;//呼叫超时，协议文档：S2C_VideoAnswered.proto
    public static final short S2C_VIDEO_RING_OFF = 546;//挂断，协议文档：S2C_VideoAnswered.proto
    public static final short S2C_VIDEO_SWITCH = 547;//音视频切换，协议文档：S2C_VideoAnswered.proto
    public static final short S2C_RTC_SIGNAL_JOINED = 560;//rtc加入，协议文档:S2C_VideoRtcJoined.proto
    public static final short S2C_RTC_SIGNAL_OFFER = 561;//rtc answer,协议文档：C2S_VideoRtcOffer.proto
    public static final short S2C_RTC_SIGNAL_ANSWER = 562;//rtc answer,协议文档：C2S_VideoRtcOffer.proto
    public static final short S2C_RTC_SIGNAL_CANDIDATE = 563;//rtc Candidate，协议文档：C2S_VideoRtcCandidate.proto
    public static final short S2C_RTC_SIGNAL_JOIN = 564;//rtc 加入，协议文档：:S2C_VideoRtcJoined.proto
    public static final short S2C_VIDEO_CALL_RESULT = 548; //音视频邀请结果，协议文档：S2C_VideoCallResult.proto
    public static final short S2C_VIDEO_GROUP = 549;//查询群组是否存在音视频通话结果，协议文档：S2C_VideoGroup.proto
    //--------------------------------------------------------即时视频协议end-------------------------------

    /** 客户端->服务端协议，前缀C2S_ **/
    public static final short C2S_HEARTBEAT = 11; // 客户端-服务端心跳请求，协议文档：无
    public static final short C2S_AUTH = 12; // 登录认证请求，协议文档：C2S_Auth.proto
    public static final short C2S_LOGOUT = 13; // 请求销毁SDK（登出），协议文档：C2S_LogOut
    public static final short C2S_USERPROPERTY = 14; // 用户信息配置，协议文档：C2S_C2SUserProperty

    public static final short C2S_SEND_P2P_MESSAGE = 101; // 发送点对点在线消息，协议文档：C2S_CustomMessage.proto
    public static final short C2S_SEND_GROUP_MESSAGE = 102; // 发送群聊消息，协议文档：C2S_CustomMessage.proto
    public static final short C2S_SEND_CHATROOM_MESSAGE = 103; // 发送聊天室消息，协议文档：C2S_CustomMessage.proto
    public static final short C2S_SEND_SYSTEM_MESSAGE = 104; // 发送系统消息，协议文档：C2S_CustomMessage.proto
    public static final short C2S_RECV_CONFIRM = 105; // 消息送达确认，协议文档：C2S_MessageRecvConfirm.proto
    public static final short C2S_MESSAGE_READ = 106; // 消息阅读确认，协议文档：C2S_MessageRead.proto
    public static final short C2S_MESSAGE_MUTED = 107; // 会话免打扰，协议文档：S2C_SpecialOperation.proto
    public static final short C2S_SESSION_TOP = 108; // 会话置顶，协议文档：S2C_SpecialOperation.proto
    public static final short C2S_MESSAGE_LOAD_PAGED = 109; // 获取消息记录列表（历史消息），协议文档：C2S_MessageLoad.proto
    public static final short C2S_MESSAGE_RECALL = 110; // 撤回消息，协议文档：C2S_MessageRecall.proto
    public static final short C2S_MESSAGE_DEL = 111; // 删除消息，协议文档：C2S_MessageDelete.proto
    public static final short C2S_JOIN_CHATROOM = 112; // 加入聊天室，协议文档：C2S_ChatroomJoin.proto
    public static final short C2S_EXIT_CHATROOM = 113; // 退出聊天室，协议文档：C2S_ChatroomJoin.proto
    public static final short C2S_CHATROOM_SET_PROP = 114; // 设置聊天室属性，协议文档：C2S_ChatroomProperty.proto
    public static final short C2S_CHATROOM_DEL_PROP = 115; // 删除聊天室属性，协议文档：C2S_ChatroomProperty.proto
    public static final short C2S_CHATROOM_GET_PROP = 116; // 获取聊天室属性，协议文档：C2S_ChatroomProperty.proto
    public static final short C2S_GET_INIT_DATA = 117; // 获取消息状态，协议文档：C2S_GetMessageStatus.proto -> GetMessageStatus
    public static final short C2S_GET_TOP_PROPERTY = 118; // 获取会话属性设置：置顶，协议文档：S2C_SpecialOperation.proto
    public static final short C2S_GET_NO_DISTURB_PROPERTY = 119; // 获取会话属性设置：免打扰，协议文档：S2C_SpecialOperation.proto

    //--------------------------------------------------------即时视频协议-------------------------------
    public static final short C2S_VIDEO_LAUNCH = 140; //发起音视频,C2S_VideoLaunch.proto
    public static final short C2S_VIDEO_ANSWER = 141; //音视频接听,C2S_VideoAnswer.proto
    public static final short C2S_VIDEO_REFUSE = 142; //音视频拒接,C2S_VideoAnswer.proto
    public static final short C2S_VIDEO_CANCEL = 143; //取消呼叫,C2S_VideoAnswer.proto
    public static final short C2S_VIDEO_RING_OFF = 144; //通话挂断,C2S_VideoAnswer.proto
    public static final short C2S_VIDEO_SWITCH = 145; //通话类型被切换，协议文档：C2S_VideoAnswer.proto
    public static final short C2S_VIDEO_ERROR = 146; //通话异常，协议文档：C2S_VideoAnswer.proto
    public static final short C2S_VIDEO_MEMBER_MODIFY = 147;//修改群音视频通话人员，协议文档：C2S_VideoMemberModify.proto
    public static final short C2S_VIDEO_PARAM = 149;//通话信息，协议文档：C2S_VideoParam.proto

    public static final short C2S_RTC_SIGNAL_JOIN = 160;//rtc加入，协议文档:C2S_VideoRtcJoin.proto
    public static final short C2S_RTC_SIGNAL_OFFER = 161;//rtc offer，协议文档：C2S_VideoRtcOffer.proto
    public static final short C2S_RTC_SIGNAL_ANSWER = 162;//rtc answer,协议文档：C2S_VideoRtcOffer.proto
    public static final short C2S_RTC_SIGNAL_CANDIDATE = 163;//rtc Candidate，协议文档：C2S_VideoRtcCandidate.proto
    //--------------------------------------------------------即时视频协议end-------------------------------

    /** 服务端(客户) -> 服务端(服务) 协议，前缀SC2SS_**/
    public static final short SC2SS_HANDSHAKE = 51; // 服务端-服务端握手信息，请求：SC2SS_Handshake.proto

}

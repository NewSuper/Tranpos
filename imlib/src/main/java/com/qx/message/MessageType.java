package com.qx.message;

import java.util.ArrayList;
import java.util.List;

public class MessageType {
    /**
     * 通知消息
     */
    public static final String TYPE_NOTICE = "QX:NoticeMsg";
    /**
     * 文本消息
     */
    public static final String TYPE_TEXT = "QX:TxtMsg";
    /**
     * 图片消息
     */
    public static final String TYPE_IMAGE = "QX:ImgMsg";
    /**
     * 图文消息
     */
    public static final String TYPE_IMAGE_AND_TEXT = "QX:ImgTextMsg";
    /**
     * 语音消息
     */
    public static final String TYPE_AUDIO = "QX:AcMsg";
    /**
     * 视频消息
     */
    public static final String TYPE_VIDEO = "QX:VcMsg";
    /**
     * 文件消息
     */
    public static final String TYPE_FILE = "QX:FileMsg";
    /**
     * 地址位置消息
     */
    public static final String TYPE_GEO = "QX:GeoMsg";
    /**
     * 语音通话消息
     */
    public static final String TYPE_AUDIO_CALL = "QX:ActMsg";
    /**
     * 视频通话消息
     */
    public static final String TYPE_VIDEO_CALL = "QX:VctMsg";
    /**
     * 名片消息
     */
    public static final String TYPE_CARD = "QX:CardMsg";
    /**
     * 红包消息
     */
    public static final String TYPE_RPP = "QX:RppMsg";
    /**
     * 输入状态消息
     */
    public static final String TYPE_STATUS = "QX:StusMsg";
    /**
     * 撤回消息
     */
    public static final String TYPE_RECALL = "QX:RecallMsg";

    /**
     * 回复消息
     */
    public static final String TYPE_REPLY = "QX:Reply";
    /**
     * 转发消息
     */
    public static final String TYPE_RECORD = "QX:Record";

    /**
     * 自定义事件
     */
    public static final String TYPE_EVENT = "QX:Event";


    /**
     * 自定义消息
     */
    public interface CustomMsge {
        /**
         * 自定义：通过好友验证问候语消息
         */
        String THROUGH_VERIFY_MSG_TYPE = "SC:ThroughVerify";
        /**
         * 自定义：添加好友问候语消息
         */
        String SAY_HELLO_MSG_TYPE = "SC:SayHello";
    }


    private static List<String> mPreSetMessageTypeList = new ArrayList<>();
    static {
        mPreSetMessageTypeList.add(TYPE_NOTICE);
        mPreSetMessageTypeList.add(TYPE_TEXT);
        mPreSetMessageTypeList.add(TYPE_IMAGE);
        mPreSetMessageTypeList.add(TYPE_IMAGE_AND_TEXT);
        mPreSetMessageTypeList.add(TYPE_AUDIO);
        mPreSetMessageTypeList.add(TYPE_VIDEO);
        mPreSetMessageTypeList.add(TYPE_FILE);
        mPreSetMessageTypeList.add(TYPE_GEO);
        mPreSetMessageTypeList.add(TYPE_AUDIO_CALL);
        mPreSetMessageTypeList.add(TYPE_VIDEO_CALL);
        mPreSetMessageTypeList.add(TYPE_STATUS);
        mPreSetMessageTypeList.add(TYPE_RECALL);
        mPreSetMessageTypeList.add(TYPE_REPLY);
        mPreSetMessageTypeList.add(TYPE_RECORD);
    }
    public static boolean isPreSetMessage(String type) {
        return mPreSetMessageTypeList.contains(type);
    }
}

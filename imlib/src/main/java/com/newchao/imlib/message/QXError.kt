package com.newchao.imlib.message


/**
 *    author : zhangjingming
 *    e-mail : jimstin@126.com
 *    date   : 2020/12/23 10:08
 *    desc   :错误类型分为：连接错误、网络错误、服务器错误、消息错误、参数错误、操作错误、数据库错误、本地错误、其它错误
 *    version: 1.0
 */
enum class QXError(var code: Int, var msg: String?, var extra: String = "") {

    //数据库错误
    /**
     * 查不到数据库记录
     */
    DB_NO_ROW_FOUND(-1001, "no row found"),

    /**
     * 连接错误
     */
    CONNECTION_NOT_READY(-2001, "im connection not ready"),

    /**
     * 发送超时
     */
    MESSAGE_SEND_TIME_OUT(-3001, "send time out"),

    /**
     * 重试超时
     */
    MESSAGE_RETRY_TIME_OUT(-3002, "retry time out"),

    /**
     * 文件路径错误
     */
    LOCAL_FILE_URI_ERROR(-4001, "uri error:file not found"),

    /**
     * 撤回错误
     */
    OPERATE_RECALL_FAILED(-5001, "recall failed"),

    /**
     * 未知错误
     */
    UNKNOWN(-100, "unknown error"),

    /**
     * 不可重复认证
     */
    CONNECTION_DUPLICATE_AUTH(10001, "duplicate auth"),

    /**
     * 参数错误
     */
    PARAMS_INCORRECT(10002, "params incorrect"),


    /**
     * 黑名单异常
     */
    MESSAGE_BLACK_LIST(10003, "black list error"),

    /**
     * 发送对象不存在
     */
    MESSAGE_TARGET_NON_EXIST(10004, "target non-exist"),

    /**
     * 消息类型不支持
     */
    MESSAGE_UNSUPPORT_TYPE(10005, "un support type"),

    /**
     * 发送方被禁言
     */
    MESSAGE_USER_MUTE(10006, "sender mute"),

    /**
     * 发送方被聊天室封禁
     */
    MESSAGE_CHAT_ROOM_BAN(10007, "chat room ban"),

    /**
     * 无权查看
     */
    MESSAGE_NO_ACCESS(10008, "no acess"),

    /**
     * 消息参数过长
     */
    MESSAGE_PARAM_TOO_LONG(10009, "param too long"),

    /**
     * 请求频率过快
     */
    OPERATE_REQUEST_FREQUENCY_TOO_FAST(10010, "request frequency too fast"),

    /**
     * 对象忙
     */
    MESSAGE_OBJECT_BUSING(10011, "object busing"),

    /**
     * 重复操作
     */
    MESSAGE_DUPLICATE_OPERATE(10012, "duplicate operate"),

    /**
     * 不能撤回别人的消息
     */
    MESSAGE_NOT_ALLOED_RECALL_OTHER_MESSAGE(10013, "不能撤回别人的消息")
}
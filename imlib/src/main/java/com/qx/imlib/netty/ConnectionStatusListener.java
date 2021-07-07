package com.qx.imlib.netty;


public interface ConnectionStatusListener {

    void onChanged(ConnectionStatusListener.Status status);

    int STATUS_UNKNOWN = 0;
    int STATUS_CONN_USER_BLOCKED = 1;
    int STATUS_CONNECTED = 2;
    int STATUS_CONNECTING = 3;
    int STATUS_DISCONNECTED = 4;
    int STATUS_KICKED = 5;
    int STATUS_NETWORK_UNAVAILABLE = 6;
    int STATUS_SERVER_INVALID = 7;
    int STATUS_TOKEN_INCORRECT = 8;
    int STATUS_REFUSE = 9;
    int STATUS_LOGOUT = 10;
    int STATUS_NETWORK_ERROR = 11;
    int STATUS_INIT_ERROR = 12;
    int STATUS_TIME_OUT = 13;

    enum Status {
        UNKNOWN(STATUS_UNKNOWN, "未知状态"),
        CONN_USER_BLOCKED(STATUS_CONN_USER_BLOCKED, "用户被禁用"),
        CONNECTED(STATUS_CONNECTED, "已连接"),
        CONNECTING(STATUS_CONNECTING, "连接中"),
        DISCONNECTED(STATUS_DISCONNECTED, "连接已断开"),
        KICKED(STATUS_KICKED, "被踢下线"),
        NETWORK_UNAVAILABLE(STATUS_NETWORK_UNAVAILABLE, "网络不可用"),
        NETWORK_ERROR(STATUS_NETWORK_ERROR, "网络错误"),
        INIT_ERROR(STATUS_INIT_ERROR, "初始化错误，请先调用init方法"),
        SERVER_INVALID(STATUS_SERVER_INVALID, "服务器异常"),
        TOKEN_INCORRECT(STATUS_TOKEN_INCORRECT, "token错误"),
        REFUSE(STATUS_REFUSE, "非法访问"),
        LOGOUT(STATUS_LOGOUT, "注销成功"),
        TIMEOUT(STATUS_TIME_OUT, "连接超时");

        private int value;
        private String message;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        Status(int value, String message) {
            this.value = value;
            this.message = message;
        }
    }
}

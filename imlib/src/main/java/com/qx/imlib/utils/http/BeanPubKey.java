package com.qx.imlib.utils.http;

public class BeanPubKey {
    private int code;
    private Key data;
    private String message;
    private long timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Key getData() {
        return data;
    }

    public void setData(Key data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public class Key {
        private String p;

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }
}

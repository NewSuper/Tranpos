package com.qx.imlib.utils.http;

import com.qx.im.model.BeanSensitiveWord;

import java.util.List;

public class BeanGetSensitiveWord {
    private int code;
    private List<BeanSensitiveWord> data;
    private String message;
    private long timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BeanSensitiveWord> getData() {
        return data;
    }

    public void setData(List<BeanSensitiveWord> data) {
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
}

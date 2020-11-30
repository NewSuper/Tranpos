package com.newsuper.t.consumer.utils.UpyunUtils.exception;

public class RespException extends UpYunException {
    private int code;

    public int code() {
        return code;
    }

    public RespException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}

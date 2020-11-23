package com.newsuper.t.juejinbao.ui.song.entity;

/**
 * 歌曲是否可以听
 */
public class MusicCanPlayBean {

    /**
     * success : true
     * message : ok
     */

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MusicCanPlayBean{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}

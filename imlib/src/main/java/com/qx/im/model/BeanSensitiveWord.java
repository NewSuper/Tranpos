package com.qx.im.model;

public class BeanSensitiveWord {
    private String id;
    private String appId;
    /**
     * 敏感词
     */
    private String sensitiveWord;
    /**
     * 替换词
     */
    private String replaceWord;
    /**
     * 1过滤：用替换词替换关键字，2屏蔽：不能发送
     */
    private String type;
    private long createTime;
    private long updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSensitiveWord() {
        return sensitiveWord;
    }

    public void setSensitiveWord(String sensitiveWord) {
        this.sensitiveWord = sensitiveWord;
    }

    public String getReplaceWord() {
        return replaceWord;
    }

    public void setReplaceWord(String replaceWord) {
        this.replaceWord = replaceWord;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public class Type {
        /**
         * 替换
         */
        public static final String TYPE_REPLACE = "1";
        /**
         * 屏蔽
         */
        public static final String TYPE_BAN = "2";
    }
}


package com.newsuper.t.sale.entity;

public class WXFaceInfo {
    private String appName;
    private String appid;
    private long createDate;
    private String createUser;
    private String id;
    private String mchId;
    private String  modifyUser;
    private String  tenantId;

    public String getAppName() {
        return appName;
    }

    public WXFaceInfo setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getAppid() {
        return appid;
    }

    public WXFaceInfo setAppid(String appid) {
        this.appid = appid;
        return this;
    }

    public long getCreateDate() {
        return createDate;
    }

    public WXFaceInfo setCreateDate(long createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getCreateUser() {
        return createUser;
    }

    public WXFaceInfo setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public String getId() {
        return id;
    }

    public WXFaceInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getMchId() {
        return mchId;
    }

    public WXFaceInfo setMchId(String mchId) {
        this.mchId = mchId;
        return this;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public WXFaceInfo setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
        return this;
    }

    public String getTenantId() {
        return tenantId;
    }

    public WXFaceInfo setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }
}

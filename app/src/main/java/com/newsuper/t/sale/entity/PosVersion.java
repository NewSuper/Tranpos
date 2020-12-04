package com.newsuper.t.sale.entity;

public class PosVersion {

    private int hasNew;// 是否需要更新(false时不用解析下面字段)
    private String appName;// 应用名称
    private String appSign;// 门店名称
    private String terminalType;// 终端类型
    private Integer versionType;// 版本类型(1稳定版2内测版)
    private String newVersionNum;// 新版本号
    private String minVersionNum; // 升级最低版本
    private String checkNum; //
    private String fileName; // 文件名称
    private String uploadFile; // 文件路径
    private String length; // 文件大小
    private String uploadLog; // 升级日志
    private Integer forceUpload; // 强制升级(0否1是)
    private String description; // 备注说明

    public int getHasNew() {
        return hasNew;
    }

    public void setHasNew(int hasNew) {
        this.hasNew = hasNew;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppSign() {
        return appSign;
    }

    public void setAppSign(String appSign) {
        this.appSign = appSign;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public Integer getVersionType() {
        return versionType;
    }

    public void setVersionType(Integer versionType) {
        this.versionType = versionType;
    }

    public String getNewVersionNum() {
        return newVersionNum;
    }

    public void setNewVersionNum(String newVersionNum) {
        this.newVersionNum = newVersionNum;
    }

    public String getMinVersionNum() {
        return minVersionNum;
    }

    public void setMinVersionNum(String minVersionNum) {
        this.minVersionNum = minVersionNum;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getUploadLog() {
        return uploadLog;
    }

    public void setUploadLog(String uploadLog) {
        this.uploadLog = uploadLog;
    }

    public Integer getForceUpload() {
        return forceUpload;
    }

    public void setForceUpload(Integer forceUpload) {
        this.forceUpload = forceUpload;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.transpos.market.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.List;

@Entity(tableName = "pos_worker")
public class Worker extends BaseEntity {

    /// <summary>
    /// 门店ID
    /// </summary>
    @ColumnInfo
    private String storeId;

    /// <summary>
    /// 人员工号
    /// </summary>
    @ColumnInfo
    private String no;

    /// <summary>
    /// 人员姓名
    /// </summary>
    @ColumnInfo
    private String name;

    /// <summary>
    /// 性别(1男0女)
    /// </summary>
    @ColumnInfo
    private int sex;

    /// <summary>
    /// 出生日期
    /// </summary>
    @ColumnInfo
    private String birthday;

    /// <summary>
    /// 手机
    /// </summary>
    @ColumnInfo
    private String mobile;

    /// <summary>
    /// 密码类型
    /// </summary>
    @ColumnInfo
    private int pwdType;

    /// <summary>
    /// 登录密码
    /// </summary>
    @ColumnInfo
    private String passwd;

    /// <summary>
    /// 备注
    /// </summary>
    @ColumnInfo
    private String memo;

    /// <summary>
    /// openId
    /// </summary>
    @ColumnInfo
    private String openId;

    /// <summary>
    /// 绑定状态
    /// </summary>
    @ColumnInfo
    private int bindingStatus;

    /// <summary>
    /// 类型  1 营业员  2 收银员 3 后台管理员
    /// </summary>
    @ColumnInfo
    private String type;

    /// <summary>
    /// 销售提成比率
    /// </summary>
    @ColumnInfo
    private double salesRate;

    /// <summary>
    /// 职务
    /// </summary>
    @ColumnInfo
    private String job;

    /// <summary>
    /// 充值优惠率上限
    /// </summary>
    @ColumnInfo
    private double giftRate;

    /// <summary>
    /// 职务提成比率
    /// </summary>
    @ColumnInfo
    private double jobRate;

    /// <summary>
    /// 上级ID
    /// </summary>
    @ColumnInfo
    private String superId;

    /// <summary>
    /// 最近登录时间
    /// </summary>
    @ColumnInfo
    private String lastTime;

    /// <summary>
    /// 最高折扣
    /// </summary>
    @ColumnInfo
    private double discount;

    /// <summary>
    /// 最高免单金额
    /// </summary>
    @ColumnInfo
    private double freeAmount;

    /// <summary>
    /// 状态 1-正常；2-离职
    /// </summary>
    @ColumnInfo
    private int status;

    /// <summary>
    /// 删除标识
    /// </summary>
    @ColumnInfo
    private int deleteFlag;

    /// <summary>
    /// 云端登陆标识
    /// </summary>
    @ColumnInfo
    private int cloudLoginFlag;

    /// <summary>
    /// pos登陆标识
    /// </summary>
    @ColumnInfo
    private int posLoginFlag;

    /// <summary>
    /// 管控数据权限
    /// </summary>
    @ColumnInfo
    private int dataAuthFlag;

    /// <summary>
    /// 当前操作员的模块权限
    /// </summary>
    @Ignore
    private List<String> permission;

    /// <summary>
    /// 当前操作员拥有的最大折扣额度，88代表8.8折
    /// </summary>
    @ColumnInfo
    private double maxDiscountRate;

    /// <summary>
    /// 最高免单金额   没有用到
    /// </summary>
    @ColumnInfo
    private double maxFreeAmount;

    /// <summary>
    /// 登录日志Id
    /// </summary>
    @ColumnInfo
    private String loginLogId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPwdType() {
        return pwdType;
    }

    public void setPwdType(int pwdType) {
        this.pwdType = pwdType;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getBindingStatus() {
        return bindingStatus;
    }

    public void setBindingStatus(int bindingStatus) {
        this.bindingStatus = bindingStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(double salesRate) {
        this.salesRate = salesRate;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public double getGiftRate() {
        return giftRate;
    }

    public void setGiftRate(double giftRate) {
        this.giftRate = giftRate;
    }

    public double getJobRate() {
        return jobRate;
    }

    public void setJobRate(double jobRate) {
        this.jobRate = jobRate;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFreeAmount() {
        return freeAmount;
    }

    public void setFreeAmount(double freeAmount) {
        this.freeAmount = freeAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getCloudLoginFlag() {
        return cloudLoginFlag;
    }

    public void setCloudLoginFlag(int cloudLoginFlag) {
        this.cloudLoginFlag = cloudLoginFlag;
    }

    public int getPosLoginFlag() {
        return posLoginFlag;
    }

    public void setPosLoginFlag(int posLoginFlag) {
        this.posLoginFlag = posLoginFlag;
    }

    public int getDataAuthFlag() {
        return dataAuthFlag;
    }

    public void setDataAuthFlag(int dataAuthFlag) {
        this.dataAuthFlag = dataAuthFlag;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }

    public double getMaxDiscountRate() {
        return maxDiscountRate;
    }

    public void setMaxDiscountRate(double maxDiscountRate) {
        this.maxDiscountRate = maxDiscountRate;
    }

    public double getMaxFreeAmount() {
        return maxFreeAmount;
    }

    public void setMaxFreeAmount(double maxFreeAmount) {
        this.maxFreeAmount = maxFreeAmount;
    }

    public String getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(String loginLogId) {
        this.loginLogId = loginLogId;
    }
}

package com.newsuper.t.consumer.bean;


import java.io.Serializable;

public class UserEntity implements Serializable {
    //头像
    private String cardImage;
    private String logo;

    //名字
    private String name;
    //性别
    private int sex;
    //公司
    private String company;
    //职位
    private String position;
    //信息完成度
    private int infoPercentage;

    private String address;
    //地区
    private String region;
    //是否有密码
    private boolean isPassWord;
    //微脉号
    private String weiMaiCard;
    private String defaultCompany;

    public String getDefaultCompany() {
        return defaultCompany;
    }

    public void setDefaultCompany(String defaultCompany) {
        this.defaultCompany = defaultCompany;
    }

    public String getDefaultPosition() {
        return defaultPosition;
    }

    public void setDefaultPosition(String defaultPosition) {
        this.defaultPosition = defaultPosition;
    }

    private String defaultPosition;


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWeiMaiCard() {
        return weiMaiCard;
    }

    public void setWeiMaiCard(String weiMaiCard) {
        this.weiMaiCard = weiMaiCard;
    }

    public boolean isPassWord() {
        return isPassWord;
    }

    public void setPassWord(boolean passWord) {
        isPassWord = passWord;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getInfoPercentage() {
        return infoPercentage;
    }

    public void setInfoPercentage(int infoPercentage) {
        this.infoPercentage = infoPercentage;
    }
}

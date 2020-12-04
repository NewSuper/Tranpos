package com.newsuper.t.markert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.List;

@Entity(tableName = "pos_member_point_rule")
public class MemberPointRule extends BaseEntity {

    /// <summary>
    /// 会员等级
    /// </summary>
    @ColumnInfo
    private String levelId;

    /// <summary>
    /// 每单按积分整数值累加积分标识
    /// </summary>
    @ColumnInfo
    private int integralFlag;

    /// <summary>
    /// 促销商品参与积分标识
    /// </summary>
    @ColumnInfo
    private int proGoodsFlag;

    /// <summary>
    /// 手工折扣议价商品参与积分标识
    /// </summary>
    @ColumnInfo
    private int handFlag;

    /// <summary>
    /// 促销买满送的赠品不计积分标识
    /// </summary>
    @ColumnInfo
    private int proFullFlag;

    /// <summary>
    /// 商品积分设置标识
    /// </summary>
    @ColumnInfo
    private int goodsSetFlag;

    /// <summary>
    /// 商品利润类型
    /// </summary>
    @ColumnInfo
    private int goodsProfitType;

    /// <summary>
    /// 商品利润阈值
    /// </summary>
    @ColumnInfo
    private double goodsProfitValue;

    /// <summary>
    /// 消费阈值
    /// </summary>
    @ColumnInfo
    private double consumeMinMoney;

    /// <summary>
    /// 积分限制次数
    /// </summary>
    @ColumnInfo
    private int limitCount;

    /// <summary>
    /// 会员生日积分标识
    /// </summary>
    @ColumnInfo
    private int birthdayFlag;

    /// <summary>
    /// 会员生日积分值
    /// </summary>
    @ColumnInfo
    private double birthdayValue;

    /// <summary>
    /// 会员生日当月倍数积分标识
    /// </summary>
    @ColumnInfo
    private int birthdayMonthFlag;

    /// <summary>
    /// 会员生日当月积分倍数
    /// </summary>
    @ColumnInfo
    private double birthdayMonthValue;

    /// <summary>
    /// 每月第几天倍数积分标识
    /// </summary>
    @ColumnInfo
    private int monthPointFlag;

    /// <summary>
    /// 日积分倍数
    /// </summary>
    @ColumnInfo
    private double dayRate;

    /// <summary>
    /// 月日
    /// </summary>
    @ColumnInfo
    private String monthDays;

    /// <summary>
    /// 每星期几倍数积分
    /// </summary>
    @ColumnInfo
    private int weekPointFlag;

    /// <summary>
    /// 星期倍数
    /// </summary>
    @ColumnInfo
    private double weekRate;

    /// <summary>
    /// 星期
    /// </summary>
    @ColumnInfo
    private String weekDays;

    /// <summary>
    /// 次消费满元
    /// </summary>
    @ColumnInfo
    private double countFullValue;

    /// <summary>
    /// 次消费积分
    /// </summary>
    @ColumnInfo
    private double countPointValue;

    /// <summary>
    /// 积分类型
    /// </summary>
    @ColumnInfo
    private int pointType;

    /// <summary>
    /// 消费积分比例
    /// </summary>
    @ColumnInfo
    private double rate;

    /// <summary>
    /// 类别比例
    /// </summary>
    @ColumnInfo
    private double categoryValue;

    /// <summary>
    /// 品牌比例
    /// </summary>
    @ColumnInfo
    private double brandValue;

    /// <summary>
    /// 节日积分内容
    /// </summary>
    @ColumnInfo
    private String festivalContent;

    /// <summary>
    /// 注册等积分选项
    /// </summary>
    @ColumnInfo
    private String registerContent;

    /// <summary>
    /// 备注信息
    /// </summary>
    @ColumnInfo
    private String description;

    /// <summary>
    /// 品牌信息
    /// </summary>
    @Ignore
    private List<MemberPointRuleBrand> brandList;

    /// <summary>
    /// 品类信息
    /// </summary>
    @Ignore
    private List<MemberPointRuleCategory> categoryList;

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public int getIntegralFlag() {
        return integralFlag;
    }

    public void setIntegralFlag(int integralFlag) {
        this.integralFlag = integralFlag;
    }

    public int getProGoodsFlag() {
        return proGoodsFlag;
    }

    public void setProGoodsFlag(int proGoodsFlag) {
        this.proGoodsFlag = proGoodsFlag;
    }

    public int getHandFlag() {
        return handFlag;
    }

    public void setHandFlag(int handFlag) {
        this.handFlag = handFlag;
    }

    public int getProFullFlag() {
        return proFullFlag;
    }

    public void setProFullFlag(int proFullFlag) {
        this.proFullFlag = proFullFlag;
    }

    public int getGoodsSetFlag() {
        return goodsSetFlag;
    }

    public void setGoodsSetFlag(int goodsSetFlag) {
        this.goodsSetFlag = goodsSetFlag;
    }

    public int getGoodsProfitType() {
        return goodsProfitType;
    }

    public void setGoodsProfitType(int goodsProfitType) {
        this.goodsProfitType = goodsProfitType;
    }

    public double getGoodsProfitValue() {
        return goodsProfitValue;
    }

    public void setGoodsProfitValue(double goodsProfitValue) {
        this.goodsProfitValue = goodsProfitValue;
    }

    public double getConsumeMinMoney() {
        return consumeMinMoney;
    }

    public void setConsumeMinMoney(double consumeMinMoney) {
        this.consumeMinMoney = consumeMinMoney;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public int getBirthdayFlag() {
        return birthdayFlag;
    }

    public void setBirthdayFlag(int birthdayFlag) {
        this.birthdayFlag = birthdayFlag;
    }

    public double getBirthdayValue() {
        return birthdayValue;
    }

    public void setBirthdayValue(double birthdayValue) {
        this.birthdayValue = birthdayValue;
    }

    public int getBirthdayMonthFlag() {
        return birthdayMonthFlag;
    }

    public void setBirthdayMonthFlag(int birthdayMonthFlag) {
        this.birthdayMonthFlag = birthdayMonthFlag;
    }

    public double getBirthdayMonthValue() {
        return birthdayMonthValue;
    }

    public void setBirthdayMonthValue(double birthdayMonthValue) {
        this.birthdayMonthValue = birthdayMonthValue;
    }

    public int getMonthPointFlag() {
        return monthPointFlag;
    }

    public void setMonthPointFlag(int monthPointFlag) {
        this.monthPointFlag = monthPointFlag;
    }

    public double getDayRate() {
        return dayRate;
    }

    public void setDayRate(double dayRate) {
        this.dayRate = dayRate;
    }

    public String getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(String monthDays) {
        this.monthDays = monthDays;
    }

    public int getWeekPointFlag() {
        return weekPointFlag;
    }

    public void setWeekPointFlag(int weekPointFlag) {
        this.weekPointFlag = weekPointFlag;
    }

    public double getWeekRate() {
        return weekRate;
    }

    public void setWeekRate(double weekRate) {
        this.weekRate = weekRate;
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public double getCountFullValue() {
        return countFullValue;
    }

    public void setCountFullValue(double countFullValue) {
        this.countFullValue = countFullValue;
    }

    public double getCountPointValue() {
        return countPointValue;
    }

    public void setCountPointValue(double countPointValue) {
        this.countPointValue = countPointValue;
    }

    public int getPointType() {
        return pointType;
    }

    public void setPointType(int pointType) {
        this.pointType = pointType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(double categoryValue) {
        this.categoryValue = categoryValue;
    }

    public double getBrandValue() {
        return brandValue;
    }

    public void setBrandValue(double brandValue) {
        this.brandValue = brandValue;
    }

    public String getFestivalContent() {
        return festivalContent;
    }

    public void setFestivalContent(String festivalContent) {
        this.festivalContent = festivalContent;
    }

    public String getRegisterContent() {
        return registerContent;
    }

    public void setRegisterContent(String registerContent) {
        this.registerContent = registerContent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MemberPointRuleBrand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<MemberPointRuleBrand> brandList) {
        this.brandList = brandList;
    }

    public List<MemberPointRuleCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<MemberPointRuleCategory> categoryList) {
        this.categoryList = categoryList;
    }
}

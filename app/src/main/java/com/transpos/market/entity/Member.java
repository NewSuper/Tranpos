package com.transpos.market.entity;

import java.io.Serializable;
import java.util.List;

public class Member implements Serializable {

    /// <summary>
    /// 会员ID
    /// </summary>
    private String id;

    /// <summary>
    /// 会员编号
    /// </summary>
    private String no;

    /// <summary>
    /// 姓名
    /// </summary>
    private String name;

    /// <summary>
    /// 性别
    /// </summary>
    private int sex;

    /// <summary>
    /// 手机号
    /// </summary>
    private String mobile;

    /// <summary>
    /// 联系电话
    /// </summary>
    private String linkphone;

    /// <summary>
    /// 生日
    /// </summary>
    private String birthday;

    /// <summary>
    /// 是否公历
    /// </summary>
    private int isSolar;

    /// <summary>
    /// email
    /// </summary>
    private String email;

    /// <summary>
    /// QQ
    /// </summary>
    private String qq;

    /// <summary>
    /// 民族
    /// </summary>
    private String nation;

    /// <summary>
    /// 家庭住址
    /// </summary>
    private String address;

    /// <summary>
    /// 推荐人
    /// </summary>
    private String referMemberId;

    /// <summary>
    /// 卡号
    /// </summary>
    private String cardNo;

    /// <summary>
    /// 卡类型编号
    /// </summary>
    private String cardTypeId;

    /// <summary>
    /// 会员头像
    /// </summary>
    private String headimgurl;

    /// <summary>
    /// 会员类型
    /// </summary>
    private String memberTypeNo;

    /// <summary>
    /// 会员等级Id
    /// </summary>
    private String memberLevelId;

    /// <summary>
    /// 会员等级编号
    /// </summary>
    private String memberLevelNo;

    /// <summary>
    /// 会员等级名称
    /// </summary>
    private String memberLevelName;

    /// <summary>
    /// 会员等级
    /// </summary>
    private MemberLevel memberLevel;

    /// <summary>
    /// 来源标识  pos weixin
    /// </summary>
    private String sourceNo;

    /// <summary>
    /// 证件类型编号
    /// </summary>
    private String cryTypeNo;

    /// <summary>
    /// 证件类型名称 01身份证  02驾照  03军官证  04护照   05学生证  06其他
    /// </summary>
    private String cryTypeName;


    /// <summary>
    /// 证件号码
    /// </summary>
    private String cryNo;

    /// <summary>
    /// 登陆密码
    /// </summary>
    private String password;

    /// <summary>
    /// 微信会员标识
    /// </summary>
    private int wechatFlag;

    /// <summary>
    /// 默认微信公众号标识
    /// </summary>
    private String wid;

    /// <summary>
    /// 默认openId
    /// </summary>
    private String openId;

    /// <summary>
    /// 门店ID
    /// </summary>
    private String shopId;

    /// <summary>
    /// 门店编号
    /// </summary>
    private String shopNo;

    /// <summary>
    /// pos
    /// </summary>
    private String posNo;

    /// <summary>
    /// 员工工号
    /// </summary>
    private String workerNo;

    /// <summary>
    /// 营业员
    /// </summary>
    private String salesClerk;

    /// <summary>
    /// 挂账信誉额度
    /// </summary>
    private double creditAmount;

    /// <summary>
    /// 备注说明
    /// </summary>
    private String description;

    /// <summary>
    /// 总余额
    /// </summary>
    private double totalAmount;

    /// <summary>
    /// 实收剩余金额
    /// </summary>
    private double globalAmount;

    /// <summary>
    /// 赠送剩余金额
    /// </summary>
    private double giftAmount;

    /// <summary>
    /// 冻结余额
    /// </summary>
    private double stageAmount;

    /// <summary>
    /// 总积分
    /// </summary>
    private double totalPoint;

    /// <summary>
    /// 累计充值总金额
    /// </summary>
    private double rechargeAmount;

    /// <summary>
    /// 累计赠送金额
    /// </summary>
    private double rechargeGiftAmount;

    /// <summary>
    /// 累计积分
    /// </summary>
    private double grandTotalPoint;

    /// <summary>
    /// 累计充值积分
    /// </summary>
    private double rechargePoint;

    /// <summary>
    /// 累计消费总金额
    /// </summary>
    private double consumeAmount;

    /// <summary>
    /// 累计消费积分
    /// </summary>
    private double consumePoint;

    /// <summary>
    /// 累计已用积分
    /// </summary>
    private double usedPoint;

    /// <summary>
    /// 累计充值次数
    /// </summary>
    private int rechargeCount;

    /// <summary>
    /// 累计消费次数
    /// </summary>
    private int consumeCount;

    /// <summary>
    /// 累计计次项目消费金额
    /// </summary>
    private double jcConsumeAmount;

    /// <summary>
    /// 累计计次项目次数
    /// </summary>
    private int jcConsumeCount;

    /// <summary>
    /// 积分有效期
    /// </summary>
    private String PointValiditDate;

    /// <summary>
    /// 建档时间
    /// </summary>
    private String jointime;

    /// <summary>
    /// 最新消费时间
    /// </summary>
    private String lastConsumeTime;

    /// <summary>
    /// 会员标签
    /// </summary>
    //private List<MemberTag> memberTagList;

    /// <summary>
    /// 会员卡列表
    /// </summary>
    private List<MemberCard> cardList;

    /// <summary>
    /// 有效计次项目列表
    /// </summary>
    //private List<MemberTimesCountProject> timesCountProjectList;

    /// <summary>
    /// 默认、选中的卡
    /// </summary>
    private MemberCard defaultCard;

    /// <summary>
    /// 识别方式
    /// </summary>
    private int judgeCardType;

    /// <summary>
    /// 是否免密  1：是；0：否
    /// </summary>
    private int isNoPwd;

    /// <summary>
    /// 免密金额
    /// </summary>
    private double npAmount;

    /// <summary>
    /// 是否plus会员
    /// </summary>
    private int isPlus;

    /// <summary>
    /// 是否购买过plus会员
    /// </summary>
    private int isPlusRecord;

    /// <summary>
    /// plus会员过期时间
    /// </summary>
    private String plusEndDateStr;

    /// <summary>
    /// plus会员券累计优惠
    /// </summary>
    private double plusCouponAmount;

    /// <summary>
    /// plus会员价累计优惠
    /// </summary>
    private double plusPriceDiscountAmount;

    /// <summary>
    /// plus价商品累计购买金额
    /// </summary>
    private double plusProductAmount;

    /// <summary>
    /// 当月plus价格商品已购买金额
    /// </summary>
    private double monthPlusAmount;

    /// <summary>
    /// plus价格商品限购额度 0为不限额
    /// </summary>
    private double consumptionQuota;
    /**
     * 额外字段
     * 是否弹出输入密码框
     */
    public boolean needShowPwdDialog;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLinkphone() {
        return linkphone;
    }

    public void setLinkphone(String linkphone) {
        this.linkphone = linkphone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getIsSolar() {
        return isSolar;
    }

    public void setIsSolar(int isSolar) {
        this.isSolar = isSolar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReferMemberId() {
        return referMemberId;
    }

    public void setReferMemberId(String referMemberId) {
        this.referMemberId = referMemberId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(String cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getMemberTypeNo() {
        return memberTypeNo;
    }

    public void setMemberTypeNo(String memberTypeNo) {
        this.memberTypeNo = memberTypeNo;
    }

    public String getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(String memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public String getMemberLevelNo() {
        return memberLevelNo;
    }

    public void setMemberLevelNo(String memberLevelNo) {
        this.memberLevelNo = memberLevelNo;
    }

    public String getMemberLevelName() {
        return memberLevelName;
    }

    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }

    public MemberLevel getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(MemberLevel memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getSourceNo() {
        return sourceNo;
    }

    public void setSourceNo(String sourceNo) {
        this.sourceNo = sourceNo;
    }

    public String getCryTypeNo() {
        return cryTypeNo;
    }

    public void setCryTypeNo(String cryTypeNo) {
        this.cryTypeNo = cryTypeNo;
    }

    public String getCryTypeName() {
        return cryTypeName;
    }

    public void setCryTypeName(String cryTypeName) {
        this.cryTypeName = cryTypeName;
    }

    public String getCryNo() {
        return cryNo;
    }

    public void setCryNo(String cryNo) {
        this.cryNo = cryNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWechatFlag() {
        return wechatFlag;
    }

    public void setWechatFlag(int wechatFlag) {
        this.wechatFlag = wechatFlag;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getWorkerNo() {
        return workerNo;
    }

    public void setWorkerNo(String workerNo) {
        this.workerNo = workerNo;
    }

    public String getSalesClerk() {
        return salesClerk;
    }

    public void setSalesClerk(String salesClerk) {
        this.salesClerk = salesClerk;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getGlobalAmount() {
        return globalAmount;
    }

    public void setGlobalAmount(double globalAmount) {
        this.globalAmount = globalAmount;
    }

    public double getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(double giftAmount) {
        this.giftAmount = giftAmount;
    }

    public double getStageAmount() {
        return stageAmount;
    }

    public void setStageAmount(double stageAmount) {
        this.stageAmount = stageAmount;
    }

    public double getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(double totalPoint) {
        this.totalPoint = totalPoint;
    }

    public double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public double getRechargeGiftAmount() {
        return rechargeGiftAmount;
    }

    public void setRechargeGiftAmount(double rechargeGiftAmount) {
        this.rechargeGiftAmount = rechargeGiftAmount;
    }

    public double getGrandTotalPoint() {
        return grandTotalPoint;
    }

    public void setGrandTotalPoint(double grandTotalPoint) {
        this.grandTotalPoint = grandTotalPoint;
    }

    public double getRechargePoint() {
        return rechargePoint;
    }

    public void setRechargePoint(double rechargePoint) {
        this.rechargePoint = rechargePoint;
    }

    public double getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(double consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public double getConsumePoint() {
        return consumePoint;
    }

    public void setConsumePoint(double consumePoint) {
        this.consumePoint = consumePoint;
    }

    public double getUsedPoint() {
        return usedPoint;
    }

    public void setUsedPoint(double usedPoint) {
        this.usedPoint = usedPoint;
    }

    public int getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(int rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public int getConsumeCount() {
        return consumeCount;
    }

    public void setConsumeCount(int consumeCount) {
        this.consumeCount = consumeCount;
    }

    public double getJcConsumeAmount() {
        return jcConsumeAmount;
    }

    public void setJcConsumeAmount(double jcConsumeAmount) {
        this.jcConsumeAmount = jcConsumeAmount;
    }

    public int getJcConsumeCount() {
        return jcConsumeCount;
    }

    public void setJcConsumeCount(int jcConsumeCount) {
        this.jcConsumeCount = jcConsumeCount;
    }

    public String getPointValiditDate() {
        return PointValiditDate;
    }

    public void setPointValiditDate(String PointValiditDate) {
        this.PointValiditDate = PointValiditDate;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
    }

    public String getLastConsumeTime() {
        return lastConsumeTime;
    }

    public void setLastConsumeTime(String lastConsumeTime) {
        this.lastConsumeTime = lastConsumeTime;
    }

    public List<MemberCard> getCardList() {
        return cardList;
    }

    public void setCardList(List<MemberCard> cardList) {
        this.cardList = cardList;
    }

    public MemberCard getDefaultCard() {
        return defaultCard;
    }

    public void setDefaultCard(MemberCard defaultCard) {
        this.defaultCard = defaultCard;
    }

    public int getJudgeCardType() {
        return judgeCardType;
    }

    public void setJudgeCardType(int judgeCardType) {
        this.judgeCardType = judgeCardType;
    }

    public int getIsNoPwd() {
        return isNoPwd;
    }

    public void setIsNoPwd(int isNoPwd) {
        this.isNoPwd = isNoPwd;
    }

    public double getNpAmount() {
        return npAmount;
    }

    public void setNpAmount(double npAmount) {
        this.npAmount = npAmount;
    }

    public int getIsPlus() {
        return isPlus;
    }

    public void setIsPlus(int isPlus) {
        this.isPlus = isPlus;
    }

    public int getIsPlusRecord() {
        return isPlusRecord;
    }

    public void setIsPlusRecord(int isPlusRecord) {
        this.isPlusRecord = isPlusRecord;
    }

    public String getPlusEndDateStr() {
        return plusEndDateStr;
    }

    public void setPlusEndDateStr(String plusEndDateStr) {
        this.plusEndDateStr = plusEndDateStr;
    }

    public double getPlusCouponAmount() {
        return plusCouponAmount;
    }

    public void setPlusCouponAmount(double plusCouponAmount) {
        this.plusCouponAmount = plusCouponAmount;
    }

    public double getPlusPriceDiscountAmount() {
        return plusPriceDiscountAmount;
    }

    public void setPlusPriceDiscountAmount(double plusPriceDiscountAmount) {
        this.plusPriceDiscountAmount = plusPriceDiscountAmount;
    }

    public double getPlusProductAmount() {
        return plusProductAmount;
    }

    public void setPlusProductAmount(double plusProductAmount) {
        this.plusProductAmount = plusProductAmount;
    }

    public double getMonthPlusAmount() {
        return monthPlusAmount;
    }

    public void setMonthPlusAmount(double monthPlusAmount) {
        this.monthPlusAmount = monthPlusAmount;
    }

    public double getConsumptionQuota() {
        return consumptionQuota;
    }

    public void setConsumptionQuota(double consumptionQuota) {
        this.consumptionQuota = consumptionQuota;
    }
}

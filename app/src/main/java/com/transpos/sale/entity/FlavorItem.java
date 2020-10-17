package com.transpos.sale.entity;

public class FlavorItem extends BaseEntity {

    /// <summary>
    /// 单据编号
    /// </summary>
    private String tradeNo;

    /// <summary>
    /// 订单ID
    /// </summary>
    private String orderId;

    /// <summary>
    /// 行ID
    /// </summary>
    private String itemId;

    /// <summary>
    /// 做法ID
    /// </summary>
    private String makeId;

    /// <summary>
    /// 做法编号
    /// </summary>
    private String code;

    /// <summary>
    /// 做法名称
    /// </summary>
    private String name;

    /// <summary>
    /// 做法加价规则1 不加价  2固定加价  3 按数量加价
    /// </summary>
    private int qtyFlag;

    /// <summary>
    /// 数量
    /// </summary>
    private double quantity;

    /// <summary>
    /// 退数量
    /// </summary>
    private double refund;

    /// <summary>
    /// 售价
    /// </summary>
    private double salePrice;

    /// <summary>
    /// 折后售价
    /// </summary>
    private double price;


    /// <summary>
    /// 类型(0口味1加料)
    /// </summary>
    private int type;

    /// <summary>
    /// 是否单选(0否1是)
    /// </summary>
    private int isRadio;

    /// <summary>
    /// 做法单选或者多选情况下的标识，单选采用TypeId，多选采用detailId
    /// </summary>
    private String group;

    /// <summary>
    /// 备注
    /// </summary>
    private String description;

    /// <summary>
    /// 金额小计
    /// </summary>
    private double amount;

    /// <summary>
    /// 折扣金额
    /// </summary>
    private double discountAmount;

    /// <summary>
    /// 折扣率
    /// </summary>
    private double discountRate;

    /// <summary>
    /// 应收金额
    /// </summary>
    private double receivableAmount;

    /// <summary>
    /// 手写做法标识(0否1是)
    /// </summary>
    private int hand;

    /// <summary>
    /// 订单中的商品数量
    /// </summary>
    private double itemQuantity;

    /// <summary>
    /// 做法计价的基准数量 1单位数量对应的做法数量
    /// </summary>
    private double baseQuantity;

    /// <summary>
    /// 订单完成时间(格式:yyyy-MM-dd HH:mm:ss)
    /// </summary>
    private String finishDate;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQtyFlag() {
        return qtyFlag;
    }

    public void setQtyFlag(int qtyFlag) {
        this.qtyFlag = qtyFlag;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRefund() {
        return refund;
    }

    public void setRefund(double refund) {
        this.refund = refund;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsRadio() {
        return isRadio;
    }

    public void setIsRadio(int isRadio) {
        this.isRadio = isRadio;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getBaseQuantity() {
        return baseQuantity;
    }

    public void setBaseQuantity(double baseQuantity) {
        this.baseQuantity = baseQuantity;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}

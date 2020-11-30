package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

public class OrderBean extends BaseBean{
    public OrderData data;
    public class OrderData{
      public String page;
      public ArrayList<OrderInfo> rows;
    }
    public class OrderInfo{
        public String id;//订单id
        public String init_date;//订单创建时间
        public String order_no;//全局唯一的订单号，应该不允许重复，这个是内部查询用的
        public String shopname;//店铺名称
        public ArrayList<Goods> fooddetail;//商品详情
        public String foodcount;//商品总数
        public String total_price;//订单总金额
        public String order_status;//订单状态，
        public String is_comment;//是否评论店铺，1：是，0：否
        public String is_opencomment;//是否开启店铺评论，1：是，0：否
        public String shop_id;//店铺ID
        public String statusname;//订单状态， NOTPAID:未支付 OPEN:未处理CONFIRMED: 已确认 SUCCEEDED: 交易成功 FAILED: 交易失败" CANCELLED: 已取消 PAYCANCEL：支付已取消 REFUNDING：退款中
        public String pay_type;//支付方式，-1：货到付款，0：余额支付，1：乐外卖自己通道的支付宝 2：乐外卖自己通道的微信支付 3：乐外卖自己通道的财付通 4：钱方通道的微信支付（需要加余额）5：乐刷通道微信支付（需要加余额） 6：乐刷通道支付宝（需要加余额） 7：商户自己的微信支付（非特约商户）8：商户自己的微信支付（特约商户） 9：乐刷T1结算微信支付 10：乐刷T1结算支付宝支付
        public String memo;//顾客备注
    }

    public class Goods{
      public String foodname;
      public String quantity;
    }
}

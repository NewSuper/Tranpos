package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class GetuiBean {
    public String type;
    public String order_id ;
    public String title ;
    public String content;
    public String order_no;
    public GettuiData data;

    public static class GettuiData{
        public CouponData couponData;
        public OrderData orderData;
    }
    public static class CouponData{
        public String title ;
        public String content;
    }
    public static class OrderData{
        public String order_id ;
        public String title ;
        public String content;
        public String order_no;
    }
}

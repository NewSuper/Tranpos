package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public class PaotuiOrderBean extends BaseBean {
    public OrderData data;
    public static class OrderData{
        public String  order_id;
        public String title;
        public String init_time;

    }
}

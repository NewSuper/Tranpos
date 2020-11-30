package com.newsuper.t.consumer.bean;

public class PaotuiPayOrderInfo extends BaseBean{
    public PayOrderInfo data;
    public static class PayOrderInfo{
        public RouletteData roulette;
    }
    public static class RouletteData{
        public String is_roulette;//	是否可抽奖 0否 1是
        public String back_url;
    }
}

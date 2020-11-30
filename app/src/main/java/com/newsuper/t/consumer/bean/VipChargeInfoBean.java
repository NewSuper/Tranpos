package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class VipChargeInfoBean extends BaseBean{
    public VipChargeData data;
    public class VipChargeData{
        public VipChargeInfo info;

    }
    public class VipChargeInfo{
        public String open_upsend;//	是否开通充值送0关闭1开启
        public ArrayList<ChargeSend> upsend;// 	充值送金额
        public String is_balance ;//	是否可以通过在线充值余额自动开通会员0否1是
        public String balance_value ;//	一次性充值多少元可以开通会员
        public String open_condition;// 	充值开通会员条件
        public String weixinzhifu;// 	微信支付方式，若为空，表示没有开通该种支付方式
        public String zhifubao;// 	支付宝支付方式，若为空，表示没有开通该种支付方式
    }
    public class ChargeSend{
        public String  money;
        public String  upsend;
        public String status;//0 表示不能点击 1 表示选中 2表示未选中
    }

}

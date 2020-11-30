package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class VipChargeBean extends BaseBean{
    public VipInfoData data;
    public class VipInfoData{
        public String paytype;//支付类型
        public String zhifu_type ;//支付方式，一般和支付类型相同，但是商户微信支付方式会区分为商家自己的微信支付和服务商微信支付
        public String order_no;//	订单号
        public String zhifubaoParameters;//	支付参数
        public WeiXinParams zhifuParameters;//	微信参数
    }

    public class WeiXinParams{
          public String appid;
          public String timestamp;
          public String noncestr;
          public String sign;
          public String partnerid;
          public String prepayid;
    }

}

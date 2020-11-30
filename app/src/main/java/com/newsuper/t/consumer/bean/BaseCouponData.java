package com.newsuper.t.consumer.bean;

import java.io.Serializable;

public class BaseCouponData  implements Serializable {
    public String id;//	int	优惠券ID
    public String coupon_name;//	string	优惠券名称
    public String coupon_des;//string	优惠券描述
    public String coupon_value;//float	优惠券面值
    public String coupon_basic_price;//	float	最低消费限制
    public String coupon_deadline;//	string	截止日期
    public String coupon_status;//	string	状态 OPEN 代表有效， USED 代表已经使用
    public String rand_number;//生成了唯一编号，用于展示优惠券二维码
    //safsaf
}

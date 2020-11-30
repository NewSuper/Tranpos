package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/12 0012.
 */

public class PayBean  {
    public ArrayList<String> paytype; //支付类型["1","2","3","4"]
    public String title;//标题
    public String order_id;//订单
    public String total_price;//支付金额
    public String init_time;//时间
    public String limit_money;//限制金额
    public String member_notice;
    public String tip_price;
}


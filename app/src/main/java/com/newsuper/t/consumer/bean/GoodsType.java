package com.newsuper.t.consumer.bean;

import java.util.ArrayList;


public class GoodsType {
    public String type_id;
    public String name;
    public int count=0;//每个类别选中的数量(默认为0)
//    public String is_need;//自己加的必选字段，根据后台修改
    public ArrayList<GoodsListBean.GoodsInfo> goodsList;
//    public String switch_advertising;//是否开启广告图片
//    public String image_advertising;//广告图片
//    public ArrayList<GoodsListBean.Coupon> exclusivecoupon;
}

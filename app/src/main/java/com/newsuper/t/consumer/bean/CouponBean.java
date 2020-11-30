package com.newsuper.t.consumer.bean;


import java.io.Serializable;
import java.util.ArrayList;

public class CouponBean extends BaseBean implements Serializable{
    public CouponData data;

    public class CouponData implements Serializable{
        public ArrayList<CouponList> list;
        public int num;
    }

    public class CouponList extends BaseCouponData implements Serializable{

        public ArrayList<String> shop_ids;//json_array_string	优惠券适用店铺，为空则为全部店铺
        public ArrayList<String> shop_titles;//json_array_string	优惠券适用店铺名列表
        public ArrayList<String> foodids;//json_array_string	关联的商品列表， 为空则没有关联商品
        public ArrayList<String> food_titles;//	json_array_string	关联的商品标题列表

    }
}
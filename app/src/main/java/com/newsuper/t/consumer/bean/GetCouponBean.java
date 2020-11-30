package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class GetCouponBean extends BaseBean{
    public CouponInfo data;

    public static class CouponInfo{
        public String show_type;
        public String logo;
        public String bg_color;
        public String status;
        public ArrayList<CouponInfoItem> coupons;
        public ArrayList<CouponInfoItem> freshman_coupons;
        public String show_freshman_coupon;
        public String show_coupon;
        public AdvertisementData advertisement;
    }

    public static class CouponInfoItem{
        public String id;
        public String name;
        public String basic_price;
        public String value;
        public String valid_date;
    }
    public static class AdvertisementData{
        public String title;
        public String img_url;
        public String location_url;

    }
}

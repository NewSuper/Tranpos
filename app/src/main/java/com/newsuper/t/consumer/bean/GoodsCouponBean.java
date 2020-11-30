package com.newsuper.t.consumer.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class GoodsCouponBean extends BaseBean{
    public GoodsCouponData data;
    public class  GoodsCouponData {
        public String image_advertising;
        public ArrayList<GoodsListBean.GoodsInfo> list;
        public String is_only_promotion;
        public String open_promotion;
        public List<ManJian> promotion_rule;
        public String IsShopMember;
        public String memberFreeze;
    }
}

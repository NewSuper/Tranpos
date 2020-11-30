package com.newsuper.t.consumer.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class GoodsSearchBean extends BaseBean{
    public   GoodsSearchData data;
    public class  GoodsSearchData {
        public String is_total;
        public String pageTotal;
        public String basicprice;//起送价
        public String IsShopMember;//是否会员
        public String memberFreeze;//是否冻结
        public String is_only_promotion;
        public String open_promotion;
        public List<ManJian> promotion_rule;
        public ArrayList<GoodsListBean.GoodsInfo> foodlist;
    }
}

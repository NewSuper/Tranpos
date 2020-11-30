package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/14 0014.
 */

public class PaotuiCouponBean implements Serializable{


    public PaotuiCouponData data;

    public class PaotuiCouponData implements Serializable{
        public ArrayList<CouponList> list;
        public int num;
    }

    public class CouponList extends BaseCouponData implements Serializable{
        public String errand_type;//跑腿适用类型 0全部 1帮买 2帮送 3帮取 4帮排队 5自定义
        public String qrcodeurl;
    }
}

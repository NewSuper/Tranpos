package com.newsuper.t.consumer.utils;


import com.newsuper.t.consumer.bean.GoodsListBean;

import java.util.ArrayList;

public class MemberUtil {
   /* 1.根据当前会员等级显示对应的商品会员价，比如一级会员就显示一级的会员价，二级的就显示二级的会员价格；

      2.如果当前用户的等级较高，但是商品会员价只有一级，则该用户只享受一级会员商品会员价格，

      3.如果是未登录的时候，则普通用户看到商品会员价应是一级商品会员价

      4.如平台禁用掉了某个会员等级，被禁用的当前会员用户只可享受一级会员商品会员价格

      */

   //商品显示会员价
    public static String getMemberPriceString(ArrayList<GoodsListBean.MemberGradePrice> gradePrices){
        String price = "";
        String grade_id = SharedPreferencesUtil.getMemberGradeId();
        LogUtil.log("MemberUtil","getMemberPriceString grade_id == "+ grade_id );
        if (gradePrices != null && gradePrices.size() > 0 ){
            price = gradePrices.get(0).price;
            // 只有一级  未登录 返回一级
            if (StringUtils.isEmpty(SharedPreferencesUtil.getToken()) || gradePrices.size() == 1){
                return price;
            }
            //-1 非会员或者冻结  0 会员等级禁用
            if ("-1".equals(grade_id)|| "0".equals(grade_id)){
                return price;
            }
            //返回等级价格
            for (GoodsListBean.MemberGradePrice gradePrice : gradePrices){
                if (grade_id.equals(gradePrice.grade_id)){
                    price = gradePrice.price;
                    LogUtil.log("MemberUtil","getMemberPriceString =111= "+ price);
                    return price;
                }
            }
            // 找不到相应等级价格返回一级
        }
        LogUtil.log("MemberUtil","getMemberPriceString =111= "+ price);
        return price;
    }

    // 微页面商品显示会员价
    public static String getMemberPriceStringWPage(GoodsListBean.GoodsInfo goods){
        String price = goods.price;
        String grade_id = SharedPreferencesUtil.getMemberGradeId();
        LogUtil.log("MemberUtil","getMemberPriceStringWPage grade_id == "+ grade_id);

        //  未登录 返回原价
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            return price;
        }
        //-1 非会员  返回原价
        if ("-1".equals(grade_id)){
            return price;
        }
        if (goods.member_grade_price != null && goods.member_grade_price.size() > 0 ){
            price = goods.member_grade_price.get(0).price;
            //0 会员等级禁用  返回一级
            if ("0".equals(grade_id)){
                return price;
            }
            //返回等级价格
            for (GoodsListBean.MemberGradePrice gradePrice : goods.member_grade_price){
                if (SharedPreferencesUtil.getMemberGradeId().equals(gradePrice.grade_id)){
                    price = gradePrice.price;
                    LogUtil.log("MemberUtil","getMemberPriceStringWPage =111= "+ price);
                    return price;
                }
            }
            // 找不到相应等级价格返回一级
        }
        LogUtil.log("MemberUtil","getMemberPriceStringWPage =111= "+ price);
        return price;
    }
    //购物车商品会员等级价格
    public static Float getMemberPriceFloat(ArrayList<GoodsListBean.MemberGradePrice> gradePrices){
        float price = 0;
        String grade_id = SharedPreferencesUtil.getMemberGradeId();
        //-1 非会员或者冻结
        if ("-1".equals(grade_id)){
            return price;
        }
        if (gradePrices != null && gradePrices.size() > 0 ){
            String p = gradePrices.get(0).price;
            price = FormatUtil.numFloat(p);
            //只有一级   0 会员等级禁用  直接返回
            if (gradePrices.size() == 1 || "0".equals(grade_id)){
                return price;
            }
            for (GoodsListBean.MemberGradePrice gradePrice : gradePrices){
                if (grade_id.equals(gradePrice.grade_id)){
                    price = FormatUtil.numFloat(gradePrice.price);
                    return  price;
                }
            }
            // 找不到相应等级价格返回一级
        }
        LogUtil.log("MemberUtil","getMemberPriceFloat =000= "+ price);
        return price;
    }
}

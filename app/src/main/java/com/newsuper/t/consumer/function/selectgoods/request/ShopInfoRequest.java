package com.newsuper.t.consumer.function.selectgoods.request;

import android.net.Uri;

import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class ShopInfoRequest {
    //店铺信息请求
    public static HashMap<String, String> shopInfoRequest(String token,String admin_id, String shop_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("lwm_sess_token="+Uri.encode(token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("admin_id", admin_id);
        params.put("shop_id", shop_id);
        params.put("lwm_sess_token",token);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }

    //购物车请求
    public static HashMap<String, String> shoppingCartRequest(String token,String admin_id, String shop_id,String version,String customerapp_id,String isNewApp,String food_ids) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+token);
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("version="+Uri.encode(version,"UTF-8"));
            param.add("customerapp_id="+Uri.encode(customerapp_id,"UTF-8"));
            param.add("isNewApp="+Uri.encode(isNewApp,"UTF-8"));
            param.add("food_ids="+Uri.encode(food_ids,"UTF-8"));
          /*  param.add("user_lat="+user_lat);
            param.add("user_lnt="+user_lnt);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("admin_id", admin_id);
        params.put("shop_id", shop_id);
        params.put("version",version);
       /* params.put("user_lat",user_lat);
        params.put("user_lnt",user_lnt);*/
        params.put("lwm_sess_token",token);
        params.put("customerapp_id",customerapp_id);
        params.put("isNewApp",isNewApp);
        params.put("food_ids",food_ids);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }

    //商品详情
    public static HashMap<String, String> goodsDetailRequest(String token,String admin_id, String food_id,String food_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+token);
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("food_id="+Uri.encode(food_id,"UTF-8"));
            param.add("food_type=" + Uri.encode(food_type,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("admin_id", admin_id);
        params.put("food_id",food_id);
        params.put("food_type",food_type);
        params.put("lwm_sess_token",token);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }
    //商品搜索
    public static HashMap<String, String> goodsSearchRequest(String token,String admin_id, String shop_id,String search_name,String page,String from_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+token);
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("search_name="+ Uri.encode(search_name,"UTF-8"));
            param.add("page="+page);
            param.add("from_type="+from_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("admin_id", admin_id);
        params.put("shop_id", shop_id);
        params.put("search_name",search_name);
        params.put("lwm_sess_token",token);
        params.put("page",page);
        params.put("from_type",from_type);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }
    //商品搜索虚拟分类
    public static HashMap<String, String> goodsSearchRequest(String token,String admin_id, String shop_id,String search_name,String page,String from_type,String is_all) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+token);
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("search_name="+ Uri.encode(search_name,"UTF-8"));
            param.add("page="+page);
            param.add("from_type="+from_type);
            param.add("is_all="+is_all);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("admin_id", admin_id);
        params.put("shop_id", shop_id);
        params.put("search_name",search_name);
        params.put("lwm_sess_token",token);
        params.put("page",page);
        params.put("from_type",from_type);
        params.put("is_all",is_all);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }
    //购物车列表请求
    public static HashMap<String, String> shoppingCartListRequest(String token,String admin_id, String shopArr,String foodArr,String foodPackageArr,String from_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+token);
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("shopArr=" + Uri.encode(shopArr,"UTF-8"));
            param.add("foodArr=" + Uri.encode(foodArr,"UTF-8"));
            param.add("foodPackageArr=" + Uri.encode(foodPackageArr,"UTF-8"));
            param.add("from_type=" + Uri.encode(from_type,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("admin_id", admin_id);
        params.put("shopArr", shopArr);
        params.put("foodArr",foodArr);
        params.put("foodPackageArr",foodPackageArr);
        params.put("from_type",from_type);
        params.put("lwm_sess_token",token);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }

    //下单
    /**
     *
     * @param token
     * @param admin_id 商家id
     * @param shop_id 店铺id
     * @param pay_type 支付方式online:在线支付，offline:货到付款，balance:余额支付
     * @param online_pay_type 顾客在线支付类型，weixinzhifu：商家自己的微信支付，qianfangweixinzhifu：钱方微信支付，leshuaweixinzhifu：乐刷微信支付，leshuazhifubao：乐刷支付宝支付
     * @param coupon 优惠券id，如果没有传-1
     * @param deliverydaynum 顾客所选配送日期1:今天,2:明天,3:后天，一次类推，最大值为7
     * @param delivertime 顾客所选配送时间
     * @param selftake 是商家配送还是自提，0表示商家配送1表示自提
     * @param name 顾客姓名
     * @param phone 顾客电话
     * @param address 顾客地址
     * @param lat 纬度
     * @param lng 经度
     * @param note 备注
     * @param field 预设
     * @param cart 购物车数据
     * @param captcha 首次下单验证码，默认为空
     * @param from_type 订单来源0:外卖,1:后台新建,2:智能机,3:饿了么,4:美团外卖,5:百度外卖,6:小程序,7:消费者APP
     * @param customerapp_id 消费者APP对应乐外卖的APPID，消费者APP为必传，H5和小程序可以不传
     * @param customerapp_type 安卓传 1
     */
    public static HashMap<String, String> cartOrderRequest(String token,String admin_id, String shop_id,String pay_type,String online_pay_type,String coupon,
                                                           String deliverydaynum,String delivertime,String selftake,String name,String phone,String address,String lat,String lng,
                                                           String note,String field,String cart,String captcha,String from_type,String total_price,String customerapp_id,String customerapp_type,String isNewApp,String manzeng_name) {
        ArrayList<String> param = new ArrayList<>();

        LogUtil.log("cartOrderRequest","cart == "+cart);
        try {
            param.add("lwm_sess_token="+token);
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("pay_type=" + Uri.encode(pay_type,"UTF-8"));
            param.add("online_pay_type=" + Uri.encode(online_pay_type,"UTF-8"));
            param.add("coupon=" + Uri.encode(coupon,"UTF-8"));
            param.add("deliverydaynum=" + Uri.encode(deliverydaynum,"UTF-8"));
            param.add("delivertime=" + Uri.encode(delivertime,"UTF-8"));
            param.add("selftake=" + Uri.encode(selftake,"UTF-8"));
            param.add("name=" + Uri.encode(name,"UTF-8"));
            param.add("phone=" + Uri.encode(phone,"UTF-8"));
            param.add("address=" + URLEncoder.encode(address,"UTF-8"));
            param.add("lat=" + Uri.encode(lat,"UTF-8"));
            param.add("lng=" + Uri.encode(lng,"UTF-8"));
            param.add("note=" + Uri.encode(note,"UTF-8"));
            param.add("field=" + Uri.encode(field,"UTF-8"));
            param.add("cart=" + URLEncoder.encode(cart,"UTF-8"));
            param.add("captcha=" + Uri.encode(captcha,"UTF-8"));
            param.add("from_type=" + Uri.encode(from_type,"UTF-8"));
            param.add("total_price=" + Uri.encode(total_price,"UTF-8"));
            param.add("customerapp_id=" + Uri.encode(customerapp_id,"UTF-8"));
            param.add("customerapp_type=" + Uri.encode(customerapp_type,"UTF-8"));
            param.add("isNewApp=" + Uri.encode(isNewApp,"UTF-8"));
            param.add("manzeng_name=" + Uri.encode(manzeng_name,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        LogUtil.log("cartOrderRequest","sign == "+sign);
        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("admin_id", admin_id);
        params.put("shop_id", shop_id);
        params.put("pay_type",pay_type);
        params.put("online_pay_type",online_pay_type);
        params.put("coupon",coupon);
        params.put("lwm_sess_token",token);

        params.put("deliverydaynum",deliverydaynum);
        params.put("delivertime",delivertime);
        params.put("selftake",selftake);
        params.put("name",name);
        params.put("phone",phone);
        params.put("address",address);
        params.put("lat",lat);
        params.put("lng",lng);
        params.put("note",note);
        params.put("field",field);
        params.put("cart",cart);
        params.put("captcha",captcha);
        params.put("from_type",from_type);
        params.put("total_price",total_price);
        params.put("customerapp_id",customerapp_id);
        params.put("customerapp_type",customerapp_type);
        params.put("isNewApp",isNewApp);
        params.put("manzeng_name",manzeng_name);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }

    //购物车列表请求
    public static HashMap<String, String> shoppingCartSendCodeRequest(String token,String admin_id, String shop_id,String phone) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+token);
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("phone=" + Uri.encode(phone,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("admin_id", admin_id);
        params.put("shop_id", shop_id);
        params.put("phone",phone);
        params.put("lwm_sess_token",token);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }
}

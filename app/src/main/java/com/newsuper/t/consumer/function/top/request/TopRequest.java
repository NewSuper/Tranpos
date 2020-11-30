package com.newsuper.t.consumer.function.top.request;

import android.util.Log;

import com.xunjoy.lewaimai.consumer.utils.MyDateUtils;
import com.xunjoy.lewaimai.consumer.utils.MyLogUtils;
import com.xunjoy.lewaimai.consumer.utils.NewSign;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class TopRequest {
    //无登录首页请求
    public static HashMap<String, String> topRequestNoLogin(String admin_id, String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("from_type="+from_type);
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("from_type",from_type);
        params.put("services",services);
        return params;
    }
    //登录首页请求
    public static HashMap<String, String> topRequestWithLogin(String admin_id,String token ,String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("lwm_sess_token="+token);
            param.add("from_type="+from_type);
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("lwm_sess_token",token);
        params.put("from_type",from_type);
        params.put("services",services);
        return params;
    }


    //无登录首页请求
    public static HashMap<String, String> shopListRequestNoLogin(String admin_id, String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type,String area_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("from_type="+from_type);
            param.add("area_id="+area_id);
            param.add("combine_id=0");
            param.add("divpage_id="+ SharedPreferencesUtil.getWPageid());
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("from_type",from_type);
        params.put("services",services);
        params.put("area_id",area_id);
        params.put("combine_id","0");
        params.put("divpage_id", SharedPreferencesUtil.getWPageid());
        return params;
    }
    //登录首页请求
    public static HashMap<String, String> shopListRequestWithLogin(String admin_id,String token ,String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type,String area_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("lwm_sess_token="+token);
            param.add("from_type="+from_type);
            param.add("area_id="+area_id);
            param.add("combine_id=0");
            param.add("divpage_id="+ SharedPreferencesUtil.getWPageid());
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("lwm_sess_token",token);
        params.put("from_type",from_type);
        params.put("services",services);
        params.put("area_id",area_id);
        params.put("combine_id","0");
        params.put("divpage_id", SharedPreferencesUtil.getWPageid());
        return params;
    }


    //无登录微页面页请求
    public static HashMap<String, String> weiShopRequestNoLogin(String admin_id, String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type,String divpage_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("from_type="+from_type);
            param.add("divpage_id="+divpage_id);
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("from_type",from_type);
        params.put("divpage_id",divpage_id);
        params.put("services",services);
        return params;
    }
    //登录登录微页面页请求
    public static HashMap<String, String> weiShopRequestLogin(String admin_id,String token ,String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type,String divpage_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("lwm_sess_token="+token);
            param.add("from_type="+from_type);
            param.add("divpage_id="+divpage_id);
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("lwm_sess_token",token);
        params.put("from_type",from_type);
        params.put("divpage_id",divpage_id);
        params.put("services",services);
        return params;
    }

    //无登录分区微页面页请求
    public static HashMap<String, String> weiAreaShopRequestNoLogin(String admin_id, String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type,String divpage_id,String area_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("from_type="+from_type);
            param.add("divpage_id="+divpage_id);
            param.add("area_id="+area_id);
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("from_type",from_type);
        params.put("divpage_id",divpage_id);
        params.put("services",services);
        params.put("area_id",area_id);
        return params;
    }
    //登录分区微页面页请求
    public static HashMap<String, String> weiAreaShopRequestLogin(String admin_id,String token ,String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type,String divpage_id,String area_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("lwm_sess_token="+token);
            param.add("from_type="+from_type);
            param.add("divpage_id="+divpage_id);
            param.add("area_id="+area_id);
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("lwm_sess_token",token);
        params.put("from_type",from_type);
        params.put("divpage_id",divpage_id);
        params.put("services",services);
        params.put("area_id",area_id);
        return params;
    }





    //无登录店铺列表请求
    public static HashMap<String, String> getWShopNoLogin(String admin_id, String type_id, String order_type,
                                                          String condition,String services,String lat,String lng,String page,String from_type,String combine_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("from_type="+from_type);
            param.add("combine_id="+combine_id);
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("from_type",from_type);
        params.put("combine_id",combine_id);
        params.put("services",services);
        return params;
    }
    //有登录店铺列表请求
    public static HashMap<String, String> getWShopLogin(String admin_id,String token ,String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type,String combine_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("lwm_sess_token="+token);
            param.add("from_type="+from_type);
            param.add("combine_id="+combine_id);
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("lwm_sess_token",token);
        params.put("from_type",from_type);
        params.put("combine_id",combine_id);
        params.put("services",services);
        return params;
    }


    //无登录店铺列表请求
    public static HashMap<String, String> getShopListNoLogin(String admin_id, String type_id, String order_type,
                                                          String condition,String services,String lat,String lng,String page,String from_type,String combine_id,String area_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("from_type="+from_type);
            param.add("combine_id="+combine_id);
            param.add("area_id="+area_id);
            param.add("divpage_id="+ SharedPreferencesUtil.getWPageid());
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("from_type",from_type);
        params.put("combine_id",combine_id);
        params.put("services",services);
        params.put("area_id",area_id);
        params.put("divpage_id", SharedPreferencesUtil.getWPageid());
        return params;
    }
    //有登录店铺列表请求
    public static HashMap<String, String> getShopListLogin(String admin_id,String token ,String type_id, String order_type, String condition,String services,String lat,String lng,String page,String from_type,String combine_id,String area_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(condition,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("lwm_sess_token="+token);
            param.add("from_type="+from_type);
            param.add("combine_id="+combine_id);
            param.add("area_id="+area_id);
            param.add("divpage_id="+ SharedPreferencesUtil.getWPageid());
        } catch (UnsupportedEncodingException e) {
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
        params.put("type_id", type_id);
        params.put("order_type", order_type);
        params.put("filter", condition);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("lwm_sess_token",token);
        params.put("from_type",from_type);
        params.put("combine_id",combine_id);
        params.put("services",services);
        params.put("area_id",area_id);
        params.put("divpage_id", SharedPreferencesUtil.getWPageid());
        return params;
    }



    //获取地址to432j3r1d5cf8s02dl9q27jh3
    public static HashMap<String,String> getAddressRequest(String token,String admin_id){
        ArrayList<String>param = new ArrayList<>();
        param.add("admin_id="+admin_id);
        param.add("lwm_sess_token="+token);
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("admin_id",admin_id);
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token",token);
        return params;
    }

    //获取店铺收货地址
    public static HashMap<String,String> getShopAddressRequest(String token,String admin_id,String shop_id){
        ArrayList<String>param = new ArrayList<>();
        param.add("shop_id="+shop_id);
        param.add("admin_id="+admin_id);
        param.add("lwm_sess_token="+token);
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("shop_id",shop_id);
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token",token);
        params.put("admin_id",admin_id);
        return params;
    }

    //获取店铺收货地址
    public static HashMap<String,String> weiRequest(String admin_id,String id,String user_lat,String user_lnt,String version,String from_type,String customerapp_id){
        ArrayList<String>param = new ArrayList<>();
        param.add("admin_id="+admin_id);
        param.add("id="+id);
        param.add("user_lat="+user_lat);
        param.add("user_lnt="+user_lnt);
        param.add("version="+version);
        param.add("from_type="+from_type);
        param.add("customerapp_id="+customerapp_id);
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("admin_id",admin_id);
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);

        params.put("id",id);
        params.put("user_lat",user_lat);
        params.put("user_lnt",user_lnt);
        params.put("version",version);
        params.put("from_type",from_type);
        params.put("customerapp_id",customerapp_id);
        return params;
    }
    //获取分区微页面
    public static HashMap<String,String> weiAreaRequest(String token,String admin_id,String user_lat,String user_lnt,String version,String customerapp_id,String return_type){
        ArrayList<String>param = new ArrayList<>();
        param.add("admin_id="+admin_id);
        param.add("lwm_sess_token="+token);
        param.add("user_lat="+user_lat);
        param.add("user_lnt="+user_lnt);
        param.add("version="+version);
        param.add("return_type="+return_type);
        param.add("customerapp_id="+customerapp_id);
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("admin_id",admin_id);
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token",token);
        params.put("user_lat",user_lat);
        params.put("user_lnt",user_lnt);
        params.put("version",version);
        params.put("return_type",return_type);
        params.put("customerapp_id",customerapp_id);
        return params;
    }

    //获取优惠券
    public static HashMap<String,String> couponRequest(String admin_id,String token,String from_type,String id){
        ArrayList<String>param = new ArrayList<>();
        param.add("admin_id="+admin_id);
        param.add("from_type="+from_type);
        param.add("lwm_sess_token="+token);
        param.add("divpage_id="+id);
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("admin_id",admin_id);
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("from_type",from_type);
        params.put("lwm_sess_token",token);
        params.put("divpage_id",id);
        return params;
    }
    //获取优惠券
    public static HashMap<String,String> couponRequestLatLng(String admin_id,String token,String from_type,String lat,String lnt){
        ArrayList<String>param = new ArrayList<>();
        param.add("admin_id="+admin_id);
        param.add("from_type="+from_type);
        param.add("lwm_sess_token="+token);
        param.add("lat="+lat);
        param.add("lnt="+lnt);
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("admin_id",admin_id);
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("from_type",from_type);
        params.put("lwm_sess_token",token);
        params.put("lat",lat);
        params.put("lnt",lnt);
        return params;
    }
    //获取优惠券
    public static HashMap<String,String> tabRequest(String admin_id,String id){
        ArrayList<String>param = new ArrayList<>();
        param.add("admin_id="+admin_id);
        param.add("id="+id);
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("admin_id",admin_id);
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("id",id);
        return params;
    }
}

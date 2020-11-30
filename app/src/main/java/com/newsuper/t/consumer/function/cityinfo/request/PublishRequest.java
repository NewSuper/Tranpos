package com.newsuper.t.consumer.function.cityinfo.request;

import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/19 0019.
 */

public class PublishRequest {

    public static HashMap<String,String> getCategoryRequest(String admin_id,String lwm_sess_token,String type_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("type_id",type_id);
        params.put("lwm_sess_token",lwm_sess_token);
        return params;
    }
    public static HashMap<String,String> getPublishListRequest(String admin_id,String lwm_sess_token,String area_id,String first_category,String second_category,String sort,String page){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("first_category=" + URLEncoder.encode(first_category,"UTF-8"));
            param.add("second_category=" + URLEncoder.encode(second_category,"UTF-8"));
            param.add("sort=" + URLEncoder.encode(sort,"UTF-8"));
            param.add("page=" + URLEncoder.encode(page,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
            param.add("area_id=" + URLEncoder.encode(area_id,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("first_category",first_category);
        params.put("second_category",second_category);
        params.put("sort",sort);
        params.put("page",page);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("area_id",area_id);
        return params;
    }
    public static HashMap<String,String> getPublishSearchRequest(String admin_id,String lwm_sess_token,String search,String page){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("search=" + URLEncoder.encode(search,"UTF-8"));
            param.add("page=" + URLEncoder.encode(page,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("search",search);
        params.put("page",page);
        params.put("lwm_sess_token",lwm_sess_token);
        return params;
    }
    public static HashMap<String,String> getMyPublishRequest(String admin_id,String lwm_sess_token,String page){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("page=" + URLEncoder.encode(page,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("page",page);
        params.put("lwm_sess_token",lwm_sess_token);
        return params;
    }
    public static HashMap<String,String> getMyCollectRequest(String admin_id,String lwm_sess_token,String page){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("page=" + URLEncoder.encode(page,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("page",page);
        params.put("lwm_sess_token",lwm_sess_token);
        return params;
    }
    public static HashMap<String,String> getMyPublishDelRequest(String admin_id,String lwm_sess_token,String id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("id=" + URLEncoder.encode(id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("lwm_sess_token",lwm_sess_token);
        return params;
    }
    //追加置顶所需信息
    public static HashMap<String,String> getSetTopInfoRequest(String admin_id,String lwm_sess_token,String id,String version,String app_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("id=" + URLEncoder.encode(id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
            param.add("version=" + URLEncoder.encode(version,"UTF-8"));
            param.add("app_id=" + URLEncoder.encode(app_id,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("version",version);
        params.put("app_id",app_id);
        return params;
    }
    public static HashMap<String,String> setToTopRequest(String admin_id,String lwm_sess_token,String info_id,String top_num,String create_fee,
                                                         String from_type,String pay_type,String online_pay_type,String customerapp_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("info_id=" + URLEncoder.encode(info_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));

            param.add("top_num=" + URLEncoder.encode(top_num,"UTF-8"));
            param.add("create_fee=" + URLEncoder.encode(create_fee,"UTF-8"));
            param.add("from_type=" + URLEncoder.encode(from_type,"UTF-8"));
            param.add("pay_type=" + URLEncoder.encode(pay_type,"UTF-8"));
            param.add("online_pay_type=" + URLEncoder.encode(online_pay_type,"UTF-8"));
            param.add("customerapp_id=" + URLEncoder.encode(customerapp_id,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("info_id",info_id);
        params.put("lwm_sess_token",lwm_sess_token);

        params.put("top_num",top_num);
        params.put("create_fee",create_fee);
        params.put("from_type",from_type);
        params.put("pay_type",pay_type);
        params.put("online_pay_type",online_pay_type);
        params.put("customerapp_id",customerapp_id);
        return params;
    }
    public static HashMap<String,String> getPublishDetailRequest(String admin_id,String lwm_sess_token,String info_id,
                                                                 String sign_type,String lat,String lnt,String version,String customerapp_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("info_id=" + URLEncoder.encode(info_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
            param.add("sign_type=" + URLEncoder.encode(sign_type,"UTF-8"));
            param.add("lat=" + URLEncoder.encode(lat,"UTF-8"));
            param.add("lnt=" + URLEncoder.encode(lnt,"UTF-8"));
            param.add("version=" + URLEncoder.encode(version,"UTF-8"));
            param.add("customerapp_id=" + URLEncoder.encode(customerapp_id,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("info_id",info_id);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("sign_type",sign_type);
        params.put("lat",lat);
        params.put("lnt",lnt);
        params.put("version",version);
        params.put("customerapp_id",customerapp_id);
        return params;
    }

    public static HashMap<String,String> getCategoryImgRequest(String admin_id,String lwm_sess_token ,String type_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);
        HashMap<String, String> params = new HashMap<>();
        params.put("admin_id",admin_id);
        params.put("lwm_appid",lwm_appid);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("type_id",type_id);
        return params;
    }
    public static HashMap<String,String> getReportRequest(String admin_id,String lwm_sess_token,String info_id,String report_type,String report_reason,String report_tel){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
            param.add("info_id=" + URLEncoder.encode(info_id,"UTF-8"));
            param.add("report_type=" + URLEncoder.encode(report_type,"UTF-8"));
            param.add("report_reason=" + URLEncoder.encode(report_reason,"UTF-8"));
            param.add("report_tel=" + URLEncoder.encode(report_tel,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("info_id",info_id);
        params.put("report_reason",report_reason);
        params.put("report_tel",report_tel);
        params.put("report_type",report_type);
        return params;
    }
    public static HashMap<String,String> getCollectRequest(String admin_id,String lwm_sess_token,String info_id,String is_collect,String collect_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
            param.add("info_id=" + URLEncoder.encode(info_id,"UTF-8"));
            param.add("is_collect=" + URLEncoder.encode(is_collect,"UTF-8"));
            param.add("collect_id=" + URLEncoder.encode(collect_id,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("info_id",info_id);
        params.put("is_collect",is_collect);
        params.put("collect_id",collect_id);
        return params;
    }

    //获取发布前的信息
    public static HashMap<String,String> getAheadPublishRequest(String lwm_sess_token,String admin_id,String category_id,String lat,String lnt){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("category_id=" + URLEncoder.encode(category_id,"UTF-8"));
            param.add("lat=" + URLEncoder.encode(lat,"UTF-8"));
            param.add("lnt=" + URLEncoder.encode(lnt,"UTF-8"));
            param.add("version=" + URLEncoder.encode("app","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid ,timestamp ,nonce , lwm_app_secret, param);
        HashMap<String, String> params = new HashMap<>();
        params.put("admin_id",admin_id);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("category_id",category_id);
        params.put("lat",lat);
        params.put("lnt",lnt);
        params.put("version","app");
        return params;
    }
    //获取发布前的信息
    public static HashMap<String,String> getPublishAreaRequest(String admin_id,String lwm_sess_token,String lat,String lnt){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
            param.add("lat=" + URLEncoder.encode(lat,"UTF-8"));
            param.add("lnt=" + URLEncoder.encode(lnt,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("lat",lat);
        params.put("lnt",lnt);
        return params;
    }

    public static HashMap<String,String> editPublishRequest(String admin_id,String lwm_sess_token,String info_id,
                                                            String first_category,String second_category,
                                                         String contact_tel,String contact_name,String images,
                                                            String area_id,String business_id,String labs,String content){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("info_id=" + URLEncoder.encode(info_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));

            param.add("first_category=" + URLEncoder.encode(first_category,"UTF-8"));
            param.add("second_category=" + URLEncoder.encode(second_category,"UTF-8"));
            param.add("contact_tel=" + URLEncoder.encode(contact_tel,"UTF-8"));
            param.add("contact_name=" + URLEncoder.encode(contact_name,"UTF-8"));
            param.add("images=" + URLEncoder.encode(images,"UTF-8"));

            param.add("area_id=" + URLEncoder.encode(area_id,"UTF-8"));
            param.add("business_id=" + URLEncoder.encode(business_id,"UTF-8"));
            param.add("labs=" + URLEncoder.encode(labs,"UTF-8"));
            param.add("content=" + URLEncoder.encode(content,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("info_id",info_id);
        params.put("lwm_sess_token",lwm_sess_token);

        params.put("first_category",first_category);
        params.put("second_category",second_category);
        params.put("contact_tel",contact_tel);
        params.put("contact_name",contact_name);
        params.put("images",images);

        params.put("area_id",area_id);
        params.put("business_id",business_id);
        params.put("labs",labs);
        params.put("content",content);
        return params;
    }

    public static HashMap<String,String> commitPublishRequest(String admin_id,String lwm_sess_token,String content,
                                                            String labs,String images,String business_id,String area_id,
                                                            String contact_name,String contact_tel,String top_num,String create_fee,
                                                            String second_category,String first_category,String from_type,String customerapp_id,
                                                            String api_type,String info_id,String pay_type,String online_pay_type){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token,"UTF-8"));
            param.add("content=" + URLEncoder.encode(content,"UTF-8"));
            param.add("labs=" + URLEncoder.encode(labs,"UTF-8"));
            param.add("images=" + URLEncoder.encode(images,"UTF-8"));
            param.add("business_id=" + URLEncoder.encode(business_id,"UTF-8"));
            param.add("area_id=" + URLEncoder.encode(area_id,"UTF-8"));
            param.add("first_category=" + URLEncoder.encode(first_category,"UTF-8"));
            param.add("second_category=" + URLEncoder.encode(second_category,"UTF-8"));
            param.add("contact_tel=" + URLEncoder.encode(contact_tel,"UTF-8"));
            param.add("contact_name=" + URLEncoder.encode(contact_name,"UTF-8"));
            param.add("top_num=" + URLEncoder.encode(top_num,"UTF-8"));
            param.add("create_fee=" + URLEncoder.encode(create_fee,"UTF-8"));
            param.add("from_type=" + URLEncoder.encode(from_type,"UTF-8"));
            param.add("customerapp_id=" + URLEncoder.encode(customerapp_id,"UTF-8"));
            param.add("api_type=" + URLEncoder.encode(api_type,"UTF-8"));
            param.add("info_id=" + URLEncoder.encode(info_id,"UTF-8"));
            param.add("pay_type=" + URLEncoder.encode(pay_type,"UTF-8"));
            param.add("online_pay_type=" + URLEncoder.encode(online_pay_type,"UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        params.put("info_id",info_id);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("first_category",first_category);
        params.put("second_category",second_category);
        params.put("contact_tel",contact_tel);
        params.put("contact_name",contact_name);
        params.put("images",images);
        params.put("area_id",area_id);
        params.put("business_id",business_id);
        params.put("labs",labs);
        params.put("content",content);
        params.put("top_num",top_num);
        params.put("create_fee",create_fee);
        params.put("from_type",from_type);
        params.put("customerapp_id",customerapp_id);
        params.put("api_type",api_type);
        params.put("pay_type",pay_type);
        params.put("online_pay_type",online_pay_type);
        return params;
    }

}

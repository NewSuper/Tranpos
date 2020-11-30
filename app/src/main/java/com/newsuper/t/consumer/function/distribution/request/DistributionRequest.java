package com.newsuper.t.consumer.function.distribution.request;

import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class DistributionRequest {
    public static HashMap<String, String> paotuiTopRequest(String lwm_sess_token, String admin_id, String latitude, String longitude) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("latitude=" + URLEncoder.encode(latitude, "UTF-8"));
            param.add("longitude=" + URLEncoder.encode(longitude, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }

    public static HashMap<String, String> categoryRequest(String lwm_sess_token, String admin_id,String phone, String type_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id, "UTF-8"));
            param.add("phone=" + URLEncoder.encode(phone, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("type_id", type_id);
        params.put("phone", phone);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }
    public static HashMap<String, String> categoryCustomRequest(String lwm_sess_token, String admin_id,String phone, String type_id,String lat,String lng) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id, "UTF-8"));
            param.add("phone=" + URLEncoder.encode(phone, "UTF-8"));
            param.add("lat=" + URLEncoder.encode(lat, "UTF-8"));
            param.add("lng=" + URLEncoder.encode(lng, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("type_id", type_id);
        params.put("phone", phone);
        params.put("lat", lat);
        params.put("lng", lng);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }
    public static HashMap<String, String> addressRequest(String lwm_sess_token, String admin_id, String lat,String lng) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("lat=" + URLEncoder.encode(lat, "UTF-8"));
            param.add("lng=" + URLEncoder.encode(lng, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("lat", lat);
        params.put("lng", lng);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }
    public static HashMap<String, String> couponRequest(String lwm_sess_token, String admin_id,String status) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("status=" + URLEncoder.encode(status, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("status", status);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }
    public static HashMap<String, String> couponRequest(String lwm_sess_token, String admin_id,String status,String errand_category_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("status=" + URLEncoder.encode(status, "UTF-8"));
            param.add("errand_category_id=" + URLEncoder.encode(errand_category_id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("status", status);
        params.put("errand_category_id", errand_category_id);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }
    public static HashMap<String, String> buyRequest(String lwm_sess_token, String admin_id, String customerapp_id, String type_id, String from_type,
                                                     String customerapp_type, String total_price, String is_nearby_buy, String phone, String address,
                                                     String customer_lat, String customer_lng, String customer_memo, String from_address, String from_lng, String from_lat,
                                                     String delivery_now, String delivery_request, String tip, String commodity_prices, String special_list,String buy_type,String is_coupon,String coupon_id,String name) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("customerapp_id=" + URLEncoder.encode(customerapp_id, "UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id, "UTF-8"));

            param.add("from_type=" + URLEncoder.encode(from_type, "UTF-8"));
            param.add("customerapp_type=" + URLEncoder.encode(customerapp_type, "UTF-8"));
            param.add("total_price=" + URLEncoder.encode(total_price, "UTF-8"));
            param.add("is_nearby_buy=" + URLEncoder.encode(is_nearby_buy, "UTF-8"));
            param.add("phone=" + URLEncoder.encode(phone, "UTF-8"));
            param.add("address=" + URLEncoder.encode(address, "UTF-8"));
            param.add("customer_lat=" + URLEncoder.encode(customer_lat, "UTF-8"));
            param.add("customer_lng=" + URLEncoder.encode(customer_lng, "UTF-8"));
            param.add("customer_memo=" + URLEncoder.encode(customer_memo, "UTF-8"));
            param.add("from_address=" + URLEncoder.encode(from_address, "UTF-8"));
            param.add("from_lng=" + URLEncoder.encode(from_lng, "UTF-8"));

            param.add("from_lat=" + URLEncoder.encode(from_lat, "UTF-8"));
            param.add("delivery_request=" + URLEncoder.encode(delivery_request, "UTF-8"));
            param.add("tip=" + URLEncoder.encode(tip, "UTF-8"));
            param.add("commodity_prices=" + URLEncoder.encode(commodity_prices, "UTF-8"));
            param.add("special_list=" + URLEncoder.encode(special_list, "UTF-8"));
            param.add("delivery_now=" + URLEncoder.encode(delivery_now, "UTF-8"));
            param.add("buy_type=" + URLEncoder.encode(buy_type, "UTF-8"));
            param.add("is_coupon=" + URLEncoder.encode(is_coupon, "UTF-8"));
            param.add("coupon_id=" + URLEncoder.encode(coupon_id, "UTF-8"));
            param.add("name=" + URLEncoder.encode(name, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("type_id", type_id);

        params.put("from_type", from_type);
        params.put("customerapp_type", customerapp_type);
        params.put("customerapp_id", customerapp_id);
        params.put("total_price", total_price);
        params.put("is_nearby_buy", is_nearby_buy);
        params.put("phone", phone);
        params.put("address", address);
        params.put("customer_lat", customer_lat);
        params.put("customer_lng", customer_lng);
        params.put("customer_memo", customer_memo);
        params.put("from_address", from_address);
        params.put("from_lng", from_lng);

        params.put("from_lat", from_lat);
        params.put("delivery_request", delivery_request);
        params.put("tip", tip);
        params.put("commodity_prices", commodity_prices);
        params.put("special_list", special_list);
        params.put("delivery_now", delivery_now);
        params.put("buy_type", buy_type);
        params.put("coupon_id", coupon_id);
        params.put("is_coupon", is_coupon);
        params.put("name", name);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }

    public static HashMap<String, String> sendRequest(String lwm_sess_token, String admin_id, String customerapp_id, String type_id, String from_type,
                                                      String customerapp_type, String total_price,  String phone, String address,
                                                      String customer_lat, String customer_lng, String customer_memo, String from_address, String from_phone, String from_lng, String from_lat,
                                                      String delivery_request,String delivery_now, String tip, String special_list,String buy_type,String is_coupon,String coupon_id,String name,String from_name) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("customerapp_id=" + URLEncoder.encode(customerapp_id, "UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id, "UTF-8"));

            param.add("from_type=" + URLEncoder.encode(from_type, "UTF-8"));
            param.add("customerapp_type=" + URLEncoder.encode(customerapp_type, "UTF-8"));
            param.add("total_price=" + URLEncoder.encode(total_price, "UTF-8"));
            param.add("phone=" + URLEncoder.encode(phone, "UTF-8"));
            param.add("address=" + URLEncoder.encode(address, "UTF-8"));
            param.add("customer_lat=" + URLEncoder.encode(customer_lat, "UTF-8"));
            param.add("customer_lng=" + URLEncoder.encode(customer_lng, "UTF-8"));
            param.add("customer_memo=" + URLEncoder.encode(customer_memo, "UTF-8"));
            param.add("from_address=" + URLEncoder.encode(from_address, "UTF-8"));
            param.add("from_lng=" + URLEncoder.encode(from_lng, "UTF-8"));

            param.add("from_lat=" + URLEncoder.encode(from_lat, "UTF-8"));
            param.add("delivery_request=" + URLEncoder.encode(delivery_request, "UTF-8"));
            param.add("tip=" + URLEncoder.encode(tip, "UTF-8"));
            param.add("from_phone=" + URLEncoder.encode(from_phone, "UTF-8"));
            param.add("special_list=" + URLEncoder.encode(special_list, "UTF-8"));
            param.add("delivery_now=" + URLEncoder.encode(delivery_now, "UTF-8"));
            param.add("buy_type=" + URLEncoder.encode(buy_type, "UTF-8"));
            param.add("is_coupon=" + URLEncoder.encode(is_coupon, "UTF-8"));
            param.add("coupon_id=" + URLEncoder.encode(coupon_id, "UTF-8"));
            param.add("name=" + URLEncoder.encode(name, "UTF-8"));
            param.add("from_name=" + URLEncoder.encode(from_name, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("type_id", type_id);

        params.put("from_type", from_type);
        params.put("customerapp_type", customerapp_type);
        params.put("customerapp_id", customerapp_id);
        params.put("total_price", total_price);
        params.put("phone", phone);
        params.put("address", address);
        params.put("customer_lat", customer_lat);
        params.put("customer_lng", customer_lng);
        params.put("customer_memo", customer_memo);
        params.put("from_address", from_address);
        params.put("from_lng", from_lng);

        params.put("from_lat", from_lat);
        params.put("delivery_request", delivery_request);
        params.put("tip", tip);
        params.put("from_phone", from_phone);
        params.put("special_list", special_list);
        params.put("delivery_now", delivery_now);
        params.put("buy_type", buy_type);
        params.put("coupon_id", coupon_id);
        params.put("is_coupon", is_coupon);
        params.put("name", name);
        params.put("from_name", from_name);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }

    public static HashMap<String, String> lineRequest(String lwm_sess_token, String admin_id, String customerapp_id, String type_id, String from_type,
                                                      String customerapp_type, String total_price,String phone, String address,
                                                      String customer_lat, String customer_lng, String customer_memo, String service_duration,
                                                      String delivery_request, String delivery_now,String tip,String buy_type,String is_coupon,String coupon_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("customerapp_id=" + URLEncoder.encode(customerapp_id, "UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id, "UTF-8"));

            param.add("from_type=" + URLEncoder.encode(from_type, "UTF-8"));
            param.add("customerapp_type=" + URLEncoder.encode(customerapp_type, "UTF-8"));
            param.add("total_price=" + URLEncoder.encode(total_price, "UTF-8"));
            param.add("phone=" + URLEncoder.encode(phone, "UTF-8"));
            param.add("address=" + URLEncoder.encode(address, "UTF-8"));
            param.add("customer_lat=" + URLEncoder.encode(customer_lat, "UTF-8"));
            param.add("customer_lng=" + URLEncoder.encode(customer_lng, "UTF-8"));
            param.add("customer_memo=" + URLEncoder.encode(customer_memo, "UTF-8"));
            param.add("service_duration=" + URLEncoder.encode(service_duration, "UTF-8"));
            param.add("delivery_request=" + URLEncoder.encode(delivery_request, "UTF-8"));
            param.add("tip=" + URLEncoder.encode(tip, "UTF-8"));
            param.add("delivery_now=" + URLEncoder.encode(delivery_now, "UTF-8"));
            param.add("buy_type=" + URLEncoder.encode(buy_type, "UTF-8"));
            param.add("is_coupon=" + URLEncoder.encode(is_coupon, "UTF-8"));
            param.add("coupon_id=" + URLEncoder.encode(coupon_id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("type_id", type_id);
        params.put("customerapp_id", customerapp_id);

        params.put("from_type", from_type);
        params.put("customerapp_type", customerapp_type);
        params.put("total_price", total_price);
        params.put("phone", phone);
        params.put("address", address);
        params.put("customer_lat", customer_lat);
        params.put("customer_lng", customer_lng);
        params.put("customer_memo", customer_memo);
        params.put("service_duration", service_duration);
        params.put("delivery_request", delivery_request);
        params.put("tip", tip);
        params.put("delivery_now", delivery_now);
        params.put("buy_type", buy_type);
        params.put("coupon_id", coupon_id);
        params.put("is_coupon", is_coupon);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }

    public static HashMap<String, String> customRequest(String lwm_sess_token, String admin_id, String customerapp_id, String type_id, String from_type,
                                                        String customerapp_type, String total_price, String name, String phone, String address,
                                                        String customer_lat, String customer_lng, String individuation,
                                                        String service_content, String tip,String is_coupon,String coupon_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id, "UTF-8"));

            param.add("from_type=" + URLEncoder.encode(from_type, "UTF-8"));
            param.add("customerapp_type=" + URLEncoder.encode(customerapp_type, "UTF-8"));
            param.add("customerapp_id=" + URLEncoder.encode(customerapp_id, "UTF-8"));
            param.add("total_price=" + URLEncoder.encode(total_price, "UTF-8"));
            param.add("name=" + URLEncoder.encode(name, "UTF-8"));
            param.add("phone=" + URLEncoder.encode(phone, "UTF-8"));
            param.add("address=" + URLEncoder.encode(address, "UTF-8"));
            param.add("customer_lat=" + URLEncoder.encode(customer_lat, "UTF-8"));
            param.add("customer_lng=" + URLEncoder.encode(customer_lng, "UTF-8"));
            param.add("individuation=" + URLEncoder.encode(individuation, "UTF-8"));
            param.add("service_content=" + URLEncoder.encode(service_content, "UTF-8"));
            param.add("tip=" + URLEncoder.encode(tip, "UTF-8"));
            param.add("is_coupon=" + URLEncoder.encode(is_coupon, "UTF-8"));
            param.add("coupon_id=" + URLEncoder.encode(coupon_id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("type_id", type_id);
        params.put("customerapp_id", customerapp_id);

        params.put("from_type", from_type);
        params.put("customerapp_type", customerapp_type);
        params.put("total_price", total_price);
        params.put("name", name);
        params.put("phone", phone);
        params.put("address", address);
        params.put("customer_lat", customer_lat);
        params.put("customer_lng", customer_lng);
        params.put("individuation", individuation);
        params.put("service_content", service_content);
        params.put("tip", tip);
        params.put("coupon_id", coupon_id);
        params.put("is_coupon", is_coupon);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }

    public static HashMap<String, String> payOrderRequest(String lwm_sess_token, String admin_id, String from_type, String paytype, String order_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("from_type=" + URLEncoder.encode(from_type, "UTF-8"));
            param.add("paytype=" + URLEncoder.encode(paytype, "UTF-8"));
            param.add("order_id=" + URLEncoder.encode(order_id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("from_type", from_type);
        params.put("paytype", paytype);
        params.put("order_id", order_id);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }
    public static HashMap<String, String> payOrderInfoRequest(String lwm_sess_token, String admin_id,String order_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("order_id=" + URLEncoder.encode(order_id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("order_id", order_id);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }
    public static HashMap<String, String> smallFeeRequest(String lwm_sess_token, String admin_id, String from_type, String order_id,String tip_price) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("from_type=" + URLEncoder.encode(from_type, "UTF-8"));
            param.add("tip_price=" + URLEncoder.encode(tip_price, "UTF-8"));
            param.add("order_id=" + URLEncoder.encode(order_id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("admin_id", admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("from_type", from_type);
        params.put("tip_price", tip_price);
        params.put("order_id", order_id);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }
}

package com.newsuper.t.consumer.function.top.request;

import com.xunjoy.lewaimai.consumer.utils.MyDateUtils;
import com.xunjoy.lewaimai.consumer.utils.MyLogUtils;
import com.xunjoy.lewaimai.consumer.utils.NewSign;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class ShopSearchRequest {
    public static HashMap<String, String> shopSearchRequest(String admin_id,String search,String latitude,String longitude,String area_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("latitude=" + URLEncoder.encode(latitude,"UTF-8"));
            param.add("longitude=" + URLEncoder.encode(longitude,"UTF-8"));
            param.add("search_val=" + URLEncoder.encode(search,"UTF-8"));
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
        params.put("lwm_appid",lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("admin_id", admin_id);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("search_val", search);
        params.put("area_id", area_id);
        return params;
    }

    public static HashMap<String, String> searchRequest(String admin_id,String key_word ,String type_id, String order_type, String filter,String services,String lat,String lng,String page,String from_type,String area_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("type_id=" + URLEncoder.encode(type_id,"UTF-8"));
            param.add("order_type=" + URLEncoder.encode(order_type,"UTF-8"));
            param.add("filter=" + URLEncoder.encode(filter,"UTF-8"));
            param.add("services=" + URLEncoder.encode(services,"UTF-8"));
            param.add("key_word=" + URLEncoder.encode(key_word,"UTF-8"));
            param.add("latitude="+lat);
            param.add("longitude="+lng);
            param.add("page="+page);
            param.add("from_type="+from_type);
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
        params.put("filter", filter);
        params.put("latitude",lat);
        params.put("longitude",lng);
        params.put("page",page);
        params.put("from_type",from_type);
        params.put("services",services);
        params.put("area_id",area_id);
        params.put("key_word",key_word);
        return params;
    }

}

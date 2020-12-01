package com.newsuper.t.consumer.function.order.request;

import android.net.Uri;

import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class OrderInfoRequest {
    public static HashMap<String, String> orderInfoRequest(String token,String admin_id, String order_id,String lat,String lnt) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("order_no=" + Uri.encode(order_id,"UTF-8"));
            param.add("lat=" + Uri.encode(lat,"UTF-8"));
            param.add("lnt=" + Uri.encode(lnt,"UTF-8"));
            param.add("lwm_sess_token="+Uri.encode(token,"UTF-8"));
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
        params.put("order_no", order_id);
        params.put("lat", lat);
        params.put("lnt", lnt);
        params.put("lwm_sess_token",token);
        return params;
    }
    public static HashMap<String, String> orderAgainRequest(String token,String admin_id, String order_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("order_id=" + Uri.encode(order_id,"UTF-8"));
            param.add("lwm_sess_token="+Uri.encode(token,"UTF-8"));
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
        params.put("order_id", order_id);
        params.put("lwm_sess_token",token);
        return params;
    }
}

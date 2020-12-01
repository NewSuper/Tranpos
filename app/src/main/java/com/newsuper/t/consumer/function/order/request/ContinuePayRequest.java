package com.newsuper.t.consumer.function.order.request;

import android.net.Uri;

import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;

import java.util.ArrayList;
import java.util.HashMap;


public class ContinuePayRequest {
    public static HashMap<String, String> continuePayRequest(String token,String admin_id, String order_id,String online_pay_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("order_id=" + Uri.encode(order_id,"UTF-8"));
            param.add("h5_url=" + Uri.encode("","UTF-8"));
            param.add("h5_title=" + Uri.encode("","UTF-8"));
            param.add("online_pay_type=" + Uri.encode(online_pay_type,"UTF-8"));
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
        params.put("h5_url", "");
        params.put("h5_title", "");
        params.put("online_pay_type", online_pay_type);
        params.put("lwm_sess_token",token);
        MyLogUtils.printf(MyLogUtils.DEBUG, "OrderInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "OrderInfoRequest", "params:" + params.toString());
        return params;
    }
    public static HashMap<String, String> getPayRequest(String token,String admin_id,String shop_id,String customerapp_id,String version) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("customerapp_id=" + Uri.encode(customerapp_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("version=" + Uri.encode(version,"UTF-8"));
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
        params.put("customerapp_id", customerapp_id);
        params.put("shop_id",shop_id);
        params.put("version", version);
        params.put("lwm_sess_token",token);
        MyLogUtils.printf(MyLogUtils.DEBUG, "OrderInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "OrderInfoRequest", "params:" + params.toString());
        return params;
    }
}

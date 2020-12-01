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


public class EditOrderRequest {

    //删除订单
    public static HashMap<String, String> editOrderRequest(String token,String admin_id,String order_no) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+Uri.encode(token,"UTF-8"));
            param.add("admin_id=" + Uri.encode(admin_id,"UTF-8"));
            param.add("order_no=" + Uri.encode(order_no,"UTF-8"));
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
        params.put("lwm_sess_token",token);
        params.put("admin_id", admin_id);
        params.put("order_no", order_no);
        MyLogUtils.printf(MyLogUtils.DEBUG, "EditOrderRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "EditOrderRequest", "params:" + params.toString());
        return params;
    }
    //取消订单
    public static HashMap<String, String> quitOrderRequest(String token,String admin_id,String order_no,String cancel_reason,String cancel_detail) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+Uri.encode(token,"UTF-8"));
            param.add("admin_id=" + Uri.encode(admin_id,"UTF-8"));
            param.add("order_no=" + Uri.encode(order_no,"UTF-8"));
            param.add("cancel_reason=" + Uri.encode(cancel_reason,"UTF-8"));
            param.add("cancel_detail=" + Uri.encode(cancel_detail,"UTF-8"));
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
        params.put("lwm_sess_token",token);
        params.put("admin_id", admin_id);
        params.put("order_no", order_no);
        params.put("cancel_reason", cancel_reason);
        params.put("cancel_detail", cancel_detail);
        return params;
    }
}

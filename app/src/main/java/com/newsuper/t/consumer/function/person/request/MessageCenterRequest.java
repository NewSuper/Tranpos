package com.newsuper.t.consumer.function.person.request;

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
 * Created by Administrator on 2018/7/23 0023.
 */

public class MessageCenterRequest  {
    public static HashMap<String,String> msgListRequest(String lwm_sess_token, String admin_id,String channel,String customer_app_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("channel=" + URLEncoder.encode( channel,"UTF-8"));
            param.add("customer_app_id=" + URLEncoder.encode( customer_app_id,"UTF-8"));
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
        params.put("admin_id",admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("channel",channel);
        params.put("customer_app_id",customer_app_id);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:"+param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:"+params.toString());

        return params;
    }
    public static HashMap<String,String> msgReadRequest(String lwm_sess_token, String admin_id,String channel,String customer_app_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("channel=" + URLEncoder.encode( channel,"UTF-8"));
            param.add("customer_app_id=" + URLEncoder.encode( customer_app_id,"UTF-8"));
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
        params.put("admin_id",admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("channel",channel);
        params.put("customer_app_id",customer_app_id);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:"+param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:"+params.toString());

        return params;
    }
    public static HashMap<String,String> msgDetailRequest(String lwm_sess_token, String admin_id,String info_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("info_id=" + URLEncoder.encode( info_id,"UTF-8"));
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
        params.put("admin_id",admin_id);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("info_id",info_id);


        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:"+param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:"+params.toString());

        return params;
    }

}

package com.newsuper.t.consumer.function.login.request;

import com.xunjoy.lewaimai.consumer.utils.MyDateUtils;
import com.xunjoy.lewaimai.consumer.utils.NewSign;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class LoginRequest {
    //发送登录验证码 获取验证码类型 1：语音 2：短信
    //短信语音作用类型 0修改绑定手机，1找回密码，2注册验证码，3语音验证码,4语音订单通知
    public static HashMap<String, String> getCodeRequest(String phone,String type,String admin_id,String action_type) {
        ArrayList<String> param = new ArrayList<>();
        String lwm_sess_token = "";
        if (!StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            lwm_sess_token = SharedPreferencesUtil.getToken();
        }
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("phone=" + URLEncoder.encode( phone,"UTF-8"));
            param.add("type=" + URLEncoder.encode( type,"UTF-8"));
            param.add("action_type=" + URLEncoder.encode( action_type,"UTF-8"));
            if (!StringUtils.isEmpty(lwm_sess_token)){
                param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
            }
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
        params.put("phone",phone);
        params.put("type",type);
        params.put("action_type",action_type);
        if (!StringUtils.isEmpty(lwm_sess_token)){
            params.put("lwm_sess_token",lwm_sess_token);
        }

        return params;
    }
    //发送登录验证码 获取验证码类型 1：语音 2：短信
    //短信语音作用类型 0修改绑定手机，1找回密码，2注册验证码，3语音验证码,4语音订单通知
    public static HashMap<String, String> getCodeRequestToken(String phone,String type,String admin_id,String action_type,String lwm_sess_token) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("phone=" + URLEncoder.encode( phone,"UTF-8"));
            param.add("type=" + URLEncoder.encode( type,"UTF-8"));
            param.add("action_type=" + URLEncoder.encode( action_type,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
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
        params.put("phone",phone);
        params.put("type",type);
        params.put("action_type",action_type);
        params.put("lwm_sess_token",lwm_sess_token);
        return params;
    }
    //验证码登录
    public static HashMap<String, String> loginCodeRequest(String phone,String code,String lwm_sess_token,String admin_id,String from_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("phone=" + URLEncoder.encode( phone,"UTF-8"));
            param.add("code=" + URLEncoder.encode( code,"UTF-8"));
            param.add("from_type=" + URLEncoder.encode( from_type,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
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
        params.put("phone",phone);
        params.put("code",code);
        params.put("from_type",from_type);
        params.put("lwm_sess_token",lwm_sess_token);
        return params;
    }
    //密码登录
    public static HashMap<String, String> loginPasswordRequest(String phone,String password,String admin_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id ,"UTF-8"));
            param.add("phone=" + URLEncoder.encode( phone,"UTF-8"));
            param.add("password=" + URLEncoder.encode( password ,"UTF-8"));
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
        params.put("admin_id",admin_id);
        params.put("phone",phone);
        params.put("password",password );
        return params;
    }
    //找回密码
    public static HashMap<String, String> getBackPasswordRequest(String phone,String password,String code,String lwm_sess_token,String admin_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id ,"UTF-8"));
            param.add("phone=" + URLEncoder.encode( phone,"UTF-8"));
            param.add("code=" + URLEncoder.encode( code ,"UTF-8"));
            param.add("password=" + URLEncoder.encode( password ,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
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
        params.put("phone",phone);
        params.put("password",password );
        params.put("code",code );
        params.put("lwm_sess_token",lwm_sess_token);
        return params;
    }

    //注册
    public static HashMap<String, String> registerRequest(String admin_id,String phone,String password,String code,String lwm_sess_token) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("phone=" + URLEncoder.encode( phone,"UTF-8"));
            param.add("code=" + URLEncoder.encode( code ,"UTF-8"));
            param.add("password=" + URLEncoder.encode( password ,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
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
        params.put("phone",phone);
        params.put("password",password );
        params.put("code",code );
        params.put("lwm_sess_token",lwm_sess_token);
        return params;
    }
    //绑定手机
    public static HashMap<String, String> bindPhoneRequest(String admin_id,String phone,String code,String from_type,String app_id,String lwm_sess_token,String isNewApp) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("phone=" + URLEncoder.encode( phone,"UTF-8"));
            param.add("code=" + URLEncoder.encode( code ,"UTF-8"));
            param.add("from_type=" + URLEncoder.encode( from_type ,"UTF-8"));
            param.add("app_id=" + URLEncoder.encode( app_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
            param.add("isNewApp=" + URLEncoder.encode( isNewApp,"UTF-8"));
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
        params.put("phone",phone);
        params.put("from_type",from_type );
        params.put("code",code );
        params.put("app_id",app_id);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("isNewApp",isNewApp);
        return params;
    }
    //qq登录
    public static HashMap<String, String> qqLogin(String admin_id,String appid,String openid,String access_token) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("openid=" + URLEncoder.encode( openid ,"UTF-8"));
            param.add("appid=" + URLEncoder.encode( appid ,"UTF-8"));
            param.add("access_token=" + URLEncoder.encode( access_token,"UTF-8"));
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
        params.put("admin_id",admin_id);
        params.put("appid",appid );
        params.put("openid",openid );
        params.put("access_token",access_token);
        return params;
    }
    //微信登录
    public static HashMap<String, String> weixinLogin(String admin_id,String appid,String code) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("appid=" + URLEncoder.encode( appid ,"UTF-8"));
            param.add("code=" + URLEncoder.encode( code ,"UTF-8"));
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
        params.put("admin_id",admin_id);
        params.put("appid",appid );
        params.put("code",code );
        return params;
    }


    //用户协议
    public static HashMap<String, String> getUserXieYiRequest(String admin_id,String customerapp_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("customerapp_id=" + URLEncoder.encode( customerapp_id,"UTF-8"));
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
        params.put("customerapp_id",customerapp_id);
        return params;
    }
}

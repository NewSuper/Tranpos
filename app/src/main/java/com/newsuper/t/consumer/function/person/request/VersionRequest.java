package com.newsuper.t.consumer.function.person.request;

import android.net.Uri;
import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;
import com.newsuper.t.consumer.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class VersionRequest {
    public static HashMap<String,String>versionRequest(String app_id){
        ArrayList<String>param = new ArrayList<>();
        try {
            param.add("app_id=" + URLEncoder.encode(app_id, "UTF-8"));
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
        params.put("app_id",app_id);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:"+param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:"+params.toString());

        return params;
    }
    public static HashMap<String, String> grayRequest(String account,String type,String ver_type,String ver_num) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("account=" + Uri.encode( account,"UTF-8"));
            param.add("type=" + Uri.encode( type,"UTF-8"));
            param.add("ver_type=" + Uri.encode( ver_type,"UTF-8"));
            param.add("ver_num=" + Uri.encode( ver_num,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid,timestamp,nonce,lwm_app_secret,param);

        HashMap<String, String> params = new HashMap<>();
        params.put("nonce", nonce);
        params.put("lwm_appid",lwm_appid);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("account", account);
        params.put("type", type);
        params.put("ver_type", ver_type);
        params.put("ver_num", ver_num);
        return params;
    }

}

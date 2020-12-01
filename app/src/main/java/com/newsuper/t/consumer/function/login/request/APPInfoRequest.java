package com.newsuper.t.consumer.function.login.request;

import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/30 0030.
 */

public class APPInfoRequest {

    public static HashMap<String,String> getAPPInfoRequest(String admin_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("customerapp_id=" + URLEncoder.encode(RetrofitUtil.ADMIN_APP_ID,"UTF-8"));
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
        params.put("customerapp_id",RetrofitUtil.ADMIN_APP_ID);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        return params;
    }
}

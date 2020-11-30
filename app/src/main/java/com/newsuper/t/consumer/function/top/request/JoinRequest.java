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
 * Create by Administrator on 2019/6/24 0024
 */
public class JoinRequest {
    public static HashMap<String,String> sendJoin(String admin_id, String name, String phone, String address, String dec, String city) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("contact_name=" + URLEncoder.encode(name, "UTF-8"));
            param.add("contact_phone=" + URLEncoder.encode(phone, "UTF-8"));
            param.add("contact_address=" + URLEncoder.encode(address, "UTF-8"));
            param.add("apply_remark=" + URLEncoder.encode(dec, "UTF-8"));
            param.add("join_city=" + URLEncoder.encode(city, "UTF-8"));
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
        params.put("contact_name", name);
        params.put("contact_phone", phone);
        params.put("contact_address", address);
        params.put("apply_remark", dec);
        params.put("join_city", city);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());

        return params;
    }
}

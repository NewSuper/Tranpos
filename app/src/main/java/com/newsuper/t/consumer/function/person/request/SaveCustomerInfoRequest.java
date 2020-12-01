package com.newsuper.t.consumer.function.person.request;

import android.net.Uri;

import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;
import com.newsuper.t.consumer.utils.StringUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 获取顾客资料
 */

public class SaveCustomerInfoRequest {
    public static HashMap<String, String> savecustomerRequest(String admin_id,String lwm_sess_token, String headimgurl, String nickname, String sex, String address) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode(admin_id, "UTF-8"));
            param.add("lwm_sess_token=" + Uri.encode(lwm_sess_token, "UTF-8"));
            param.add("headimgurl=" + Uri.encode(headimgurl, "UTF-8"));
            param.add("nickname=" + Uri.encode(nickname, "UTF-8"));
            param.add("sex=" + Uri.encode(sex, "UTF-8"));
            param.add("address=" + Uri.encode(address, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);

        HashMap<String, String> params = new HashMap<>();
        params.put("lwm_appid", lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("headimgurl", headimgurl);
        params.put("nickname", nickname);
        params.put("sex", sex);
        params.put("address", address);
        params.put("admin_id", admin_id);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());

        return params;
    }
}

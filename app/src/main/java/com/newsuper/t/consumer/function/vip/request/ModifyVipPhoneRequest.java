package com.newsuper.t.consumer.function.vip.request;

import com.xunjoy.lewaimai.consumer.utils.MyDateUtils;
import com.xunjoy.lewaimai.consumer.utils.NewSign;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class ModifyVipPhoneRequest {
    //更改绑定手机
    public static HashMap<String, String> modifyPhoneRequest(String admin_id,String lwm_sess_token, String phone, String code) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
            param.add("phone=" + URLEncoder.encode( phone,"UTF-8"));
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
        params.put("admin_id",admin_id);
        params.put("lwm_appid",lwm_appid);
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("phone",phone);
        params.put("code",code );
        return params;
    }
}

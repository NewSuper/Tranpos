package com.newsuper.t.consumer.function.top.request;



import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class DredgeAreaRequest {
    public static HashMap<String,String> loadData(String lwm_sess_token, String admin_id) {
        String version = "app";
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("version=" + URLEncoder.encode(version, "UTF-8"));
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
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("version", version);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());

        return params;
    }
}

package com.newsuper.t.consumer.function.person.request;

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

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class EvaluateRequest {

    public static HashMap<String, String> loadEvaluateData(String lwm_sess_token, String admin_id, String page, String pagesize) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            if (!StringUtils.isEmpty(page))
                param.add("page=" + URLEncoder.encode(page, "UTF-8"));
            if (!StringUtils.isEmpty(pagesize))
                param.add("pagesize=" + URLEncoder.encode(pagesize, "UTF-8"));
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
        if (!StringUtils.isEmpty(page))
            params.put("page", page);
        if (!StringUtils.isEmpty(pagesize))
            params.put("pagesize", pagesize);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());

        return params;
    }

    public static HashMap<String, String> deleteEvaluate(String lwm_sess_token, String admin_id, String comment_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode(lwm_sess_token, "UTF-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "UTF-8"));
            param.add("comment_id=" + URLEncoder.encode(comment_id, "UTF-8"));
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
        params.put("comment_id", comment_id);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());

        return params;
    }
}

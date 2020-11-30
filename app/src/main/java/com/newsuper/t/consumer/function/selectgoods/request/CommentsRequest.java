package com.newsuper.t.consumer.function.selectgoods.request;

import android.net.Uri;

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
 * Created by Administrator on 2017/5/3 0003.
 */

public class CommentsRequest {
    //无登录首页请求
    public static HashMap<String, String> commentsRequest(String admin_id, String shop_id, String page, String num,String tag) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("page=" + Uri.encode(page,"UTF-8"));
            param.add("num=" + Uri.encode(num,"UTF-8"));
            param.add("tag=" + Uri.encode(tag,"UTF-8"));
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
        params.put("admin_id", admin_id);
        params.put("shop_id", shop_id);
        params.put("page", page);
        params.put("num", num);
        params.put("tag", tag);
        MyLogUtils.printf(MyLogUtils.DEBUG, "CommentsRequst", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "CommentsRequst", "params:" + params.toString());
        return params;
    }
}

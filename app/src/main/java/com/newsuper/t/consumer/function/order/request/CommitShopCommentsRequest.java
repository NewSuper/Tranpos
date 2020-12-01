package com.newsuper.t.consumer.function.order.request;

import android.net.Uri;

import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class CommitShopCommentsRequest {
    public static HashMap<String, String> commitCommentsRequest(String token,String admin_id, String shop_id,String order_id
    ,String grade,String content,String courier_grade,String courier_content,String is_showname,String tag,String imgurl,String from_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+Uri.encode(token,"UTF-8"));
            param.add("admin_id=" + Uri.encode(admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("order_id=" + Uri.encode(order_id,"UTF-8"));
            param.add("grade=" + Uri.encode(grade,"UTF-8"));
            param.add("content=" + Uri.encode(content,"UTF-8"));
            param.add("courier_grade=" + Uri.encode(courier_grade,"UTF-8"));
            param.add("courier_content=" + Uri.encode(courier_content,"UTF-8"));
            param.add("is_showname=" + Uri.encode(is_showname,"UTF-8"));
            param.add("tag=" + Uri.encode(tag,"UTF-8"));
            param.add("imgurl=" + Uri.encode(imgurl,"UTF-8"));
            param.add("from_type=" + Uri.encode(from_type,"UTF-8"));
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
        params.put("order_id", order_id);
        params.put("lwm_sess_token",token);
        params.put("grade",grade);
        params.put("content",content);
        params.put("courier_grade",courier_grade);
        params.put("courier_content",courier_content);
        params.put("is_showname",is_showname);
        params.put("tag",tag);
        params.put("imgurl",imgurl);
        params.put("from_type",from_type);
        MyLogUtils.printf(MyLogUtils.DEBUG, "OrderInfoRequest", "param:" + param.toString());
        return params;
    }

    public static HashMap<String, String> commentsRequest(String token,String admin_id,String order_no) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+Uri.encode(token,"UTF-8"));
            param.add("admin_id=" + Uri.encode(admin_id,"UTF-8"));
            param.add("order_no=" + Uri.encode(order_no,"UTF-8"));
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
        params.put("order_no", order_no);
        params.put("lwm_sess_token",token);
        MyLogUtils.printf(MyLogUtils.DEBUG, "OrderInfoRequest", "param:" + param.toString());
        return params;
    }

    //修改评论
    public static HashMap<String, String> editCommentsRequest(String token,String admin_id, String comment_id,String grade,String content, String courier_grade,String courier_content,
                                                              String is_showname,String tag,String imgurl,String from_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token="+Uri.encode(token,"UTF-8"));
            param.add("admin_id=" + Uri.encode(admin_id,"UTF-8"));
            param.add("comment_id=" + Uri.encode(comment_id,"UTF-8"));
            param.add("grade=" + Uri.encode(grade,"UTF-8"));
            param.add("content=" + Uri.encode(content,"UTF-8"));
            param.add("courier_grade=" + Uri.encode(courier_grade,"UTF-8"));
            param.add("courier_content=" + Uri.encode(courier_content,"UTF-8"));
            param.add("is_showname=" + Uri.encode(is_showname,"UTF-8"));
            param.add("tag=" + Uri.encode(tag,"UTF-8"));
            param.add("imgurl=" + Uri.encode(imgurl,"UTF-8"));
            param.add("from_type=" + Uri.encode(from_type,"UTF-8"));
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
        params.put("comment_id", comment_id);
        params.put("lwm_sess_token",token);
        params.put("grade",grade);
        params.put("content",content);
        params.put("courier_grade",courier_grade);
        params.put("courier_content",courier_content);
        params.put("is_showname",is_showname);
        params.put("tag",tag);
        params.put("imgurl",imgurl);
        params.put("from_type",from_type);
        MyLogUtils.printf(MyLogUtils.DEBUG, "OrderInfoRequest", "param:" + param.toString());
        return params;
    }
}

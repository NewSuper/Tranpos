package com.newsuper.t.consumer.function.order.request;

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


public class GetOrderListRequest {
    //请求订单列表
    public static HashMap<String, String> getOrderListRequest(String token,String admin_id,String type, String page,String isFetchHistory,int pageSize) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("type=" + Uri.encode( type,"UTF-8"));
            param.add("page=" + Uri.encode(page,"UTF-8"));
            param.add("isFetchHistory=" + Uri.encode(isFetchHistory,"UTF-8"));
            param.add("pageSize=" + pageSize);
            param.add("admin_id=" + Uri.encode(admin_id,"UTF-8"));
            param.add("lwm_sess_token="+Uri.encode(token,"UTF-8"));
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
        params.put("type", type);
        params.put("isFetchHistory", isFetchHistory);
        params.put("pageSize", pageSize+"");
        params.put("type", type);
        params.put("admin_id", admin_id);
        params.put("page", page);
        params.put("lwm_sess_token",token);
        MyLogUtils.printf(MyLogUtils.DEBUG, "GetOrderListRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "GetOrderListRequest", "params:" + params.toString());
        return params;
    }
}

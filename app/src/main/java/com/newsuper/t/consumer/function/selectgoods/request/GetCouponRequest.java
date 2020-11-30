package com.newsuper.t.consumer.function.selectgoods.request;

import android.net.Uri;

import com.xunjoy.lewaimai.consumer.utils.MyDateUtils;
import com.xunjoy.lewaimai.consumer.utils.MyLogUtils;
import com.xunjoy.lewaimai.consumer.utils.NewSign;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;

import java.util.ArrayList;
import java.util.HashMap;


public class GetCouponRequest {
    //无登录首页请求
    public static HashMap<String, String> getCoupon(String admin_id,String token,String shop_id,String coupon_template_id) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode(admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("lwm_sess_token="+Uri.encode(token,"UTF-8"));
            param.add("coupon_template_id="+Uri.encode(coupon_template_id,"UTF-8"));
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
        params.put("lwm_sess_token",token);
        params.put("coupon_template_id",coupon_template_id);
        MyLogUtils.printf(MyLogUtils.DEBUG, "QuitCollectRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "QuitCollectRequest", "params:" + params.toString());
        return params;
    }
}

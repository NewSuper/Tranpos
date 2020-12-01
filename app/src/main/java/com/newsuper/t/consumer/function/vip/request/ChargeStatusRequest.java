package com.newsuper.t.consumer.function.vip.request;

import android.net.Uri;

import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;

import java.util.ArrayList;
import java.util.HashMap;


public class ChargeStatusRequest {
    //会员卡信息请求
    public static HashMap<String, String> chargeStatusRequest(String token, String admin_id, String order_no, String from_type){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("order_no=" + Uri.encode( order_no,"UTF-8"));
            param.add("from_type=" + Uri.encode( from_type,"UTF-8"));
            param.add("lwm_sess_token="+Uri.encode(token));
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
        params.put("lwm_sess_token",token);
        params.put("order_no",order_no);
        params.put("from_type",from_type);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }


}

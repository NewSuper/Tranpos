package com.newsuper.t.consumer.function.vip.request;

import android.net.Uri;

import com.xunjoy.lewaimai.consumer.utils.MyDateUtils;
import com.xunjoy.lewaimai.consumer.utils.MyLogUtils;
import com.xunjoy.lewaimai.consumer.utils.NewSign;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class VipChargeRequest {
    //会员卡信息请求
    public static HashMap<String, String> vipChargeRequest(String token, String admin_id, String pay_money, String online_pay_type, String url,String grade_id){
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("pay_money=" + Uri.encode( pay_money,"UTF-8"));
            param.add("online_pay_type=" + Uri.encode( online_pay_type,"UTF-8"));
            param.add("url=" + Uri.encode( url,"UTF-8"));
            param.add("from_type=" + Uri.encode( "app","UTF-8"));
            param.add("customerapp_id=" + Uri.encode( RetrofitUtil.ADMIN_APP_ID,"UTF-8"));
            param.add("customerapp_type=" + Uri.encode( "1","UTF-8"));
            param.add("lwm_sess_token="+Uri.encode(token));
            if (!StringUtils.isEmpty(grade_id))
                param.add("grade_id="+Uri.encode(grade_id));
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
        params.put("pay_money",pay_money);
        params.put("online_pay_type",online_pay_type);
        params.put("url",url);
        params.put("from_type","app");
        params.put("customerapp_id",RetrofitUtil.ADMIN_APP_ID);
        params.put("customerapp_type","1");
        if (!StringUtils.isEmpty(grade_id))
            params.put("grade_id",grade_id);

        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }


}

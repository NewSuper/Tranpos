package com.newsuper.t.consumer.function.vip.request;

import android.net.Uri;

import com.xunjoy.lewaimai.consumer.utils.MyDateUtils;
import com.xunjoy.lewaimai.consumer.utils.MyLogUtils;
import com.xunjoy.lewaimai.consumer.utils.NewSign;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;

import java.util.ArrayList;
import java.util.HashMap;


public class VipInfoCommitRequest {
    //会员添加信息
    public static HashMap<String, String> vipInfoCommitRequest(String token,String admin_id,String name,String sex,String birthday,String address) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("name=" + Uri.encode( name,"UTF-8"));
            param.add("sex=" + Uri.encode( sex,"UTF-8"));
            param.add("birthday=" + Uri.encode( birthday,"UTF-8"));
            param.add("address=" + Uri.encode( address,"UTF-8"));
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
        params.put("name", name);
        params.put("sex", sex);
        params.put("birthday", birthday);
        params.put("address", address);
        params.put("lwm_sess_token",token);
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "ShopInfoRequest", "params:" + params.toString());
        return params;
    }


}

package com.newsuper.t.consumer.function.person.request;

import com.xunjoy.lewaimai.consumer.utils.MyDateUtils;
import com.xunjoy.lewaimai.consumer.utils.MyLogUtils;
import com.xunjoy.lewaimai.consumer.utils.NewSign;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
//上传图片
public class UpdateImgRequest {
    public static HashMap<String,String>updateImgRequest(String lwm_sess_token,String headimg,String admin_id){
        ArrayList<String>param = new ArrayList<>();

        try {
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
            param.add("headimg=" + URLEncoder.encode( headimg,"UTF-8"));
            param.add("admin_id=" + URLEncoder.encode( admin_id,"UTF-8"));
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
        params.put("lwm_sess_token",lwm_sess_token);
        params.put("headimg",headimg);
        params.put("admin_id",admin_id);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "param:"+param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "Uuuuuuuuuuuu", "params:"+params.toString());

        return params;
    }
}

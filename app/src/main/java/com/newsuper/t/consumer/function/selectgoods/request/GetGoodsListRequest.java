package com.newsuper.t.consumer.function.selectgoods.request;

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


public class GetGoodsListRequest {
    //无登录首页请求
    public static HashMap<String, String> getGoodsListRequest(String token,String admin_id, String shop_id,String foodSelectedArr,String foodPackageSelectedArr,String from_type) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("admin_id=" + Uri.encode( admin_id,"UTF-8"));
            param.add("shop_id=" + Uri.encode(shop_id,"UTF-8"));
            param.add("lwm_sess_token="+ Uri.encode(token,"UTF-8"));
            param.add("foodSelectedArr="+Uri.encode(foodSelectedArr,"UTF-8"));
            param.add("foodPackageSelectedArr="+Uri.encode(foodPackageSelectedArr,"UTF-8"));
            param.add("from_type="+Uri.encode(from_type,"UTF-8"));
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
        params.put("foodSelectedArr",foodSelectedArr);
        params.put("foodPackageSelectedArr",foodPackageSelectedArr);
        params.put("from_type",from_type);
        MyLogUtils.printf(MyLogUtils.DEBUG, "GetGoodsListRequest", "param:" + param.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "GetGoodsListRequest", "params:" + params.toString());
        return params;
    }
}

package com.newsuper.t.consumer.function.person.request;


import com.newsuper.t.consumer.utils.MyDateUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.NewSign;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.StringRandom;
import com.newsuper.t.consumer.utils.StringUtils;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 新建顾客地址
 */

public class NewddressRequest {
    public static HashMap<String, String> newaddreessRequest(String lwm_sess_token,String admin_id,String addaddress_id, String addaddress_name, String addaddress_phone,
                                                             String addaddress_address, String mapname,String lat,String lng) {
        ArrayList<String> param = new ArrayList<>();
        try {
            param.add("lwm_sess_token=" + URLEncoder.encode( lwm_sess_token,"UTF-8"));
            param.add("addaddress_id=" + URLEncoder.encode(addaddress_id, "UTF-8"));
            param.add("addaddress_name=" + URLEncoder.encode(addaddress_name, "utf-8"));
            param.add("addaddress_phone=" + URLEncoder.encode(addaddress_phone, "UTF-8"));
            param.add("addaddress_address=" + URLEncoder.encode(addaddress_address, "utf-8"));
            param.add("maplng=" + lng);
            param.add("maplat=" + lat);
            param.add("mapname=" + URLEncoder.encode(mapname, "utf-8"));
            param.add("admin_id=" + URLEncoder.encode(admin_id, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lwm_appid = RetrofitUtil.APP_ID;
        String lwm_app_secret = RetrofitUtil.APP_SECRET;
        String nonce = StringRandom.getRandomString(5);
        String timestamp = MyDateUtils.getTimestamp();
        String sign = NewSign.getNewSignNoLogin(lwm_appid, timestamp, nonce, lwm_app_secret, param);
        HashMap<String, String> params = new HashMap<>();

        params.put("lwm_appid", lwm_appid);
        params.put("nonce", nonce);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        params.put("lwm_sess_token", lwm_sess_token);
        params.put("admin_id", admin_id);
        params.put("addaddress_id", addaddress_id);
        params.put("addaddress_name", addaddress_name);
        params.put("addaddress_phone", addaddress_phone);
        params.put("addaddress_address", addaddress_address);
        params.put("maplng", lng);
        params.put("maplat", lat);
        params.put("mapname", mapname);

        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "params:" + params.toString());
        return params;
    }
}

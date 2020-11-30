package com.newsuper.t.consumer.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;


public class NewSign {

    public static  String getNewSignNoLogin(String appId,String timestamp, String nonce,String lwm_app_secret , ArrayList<String> params){

        params.add("lwm_appid="+appId);
        params.add("nonce="+nonce);
        params.add("timestamp="+timestamp);
        String[] signString = new String[params.size()];
        for(int i=0;i<params.size();i++){
            signString[i] = params.get(i);
        }
        //将参数排序
        Arrays.sort(signString);

        //强参数拼接
        String preSign = "";
        for(int i=0;i<signString.length;i++){
            preSign +=signString[i]+"&";
        }
        if(!TextUtils.isEmpty(preSign)){
            preSign  = preSign.substring(0,preSign.length() -1);
        }
        preSign = MD5.encodeMD5(preSign);
        preSign += lwm_app_secret ;

        //得到参数MD5加密
        return MD5.BIGMD5(preSign);
    }
}

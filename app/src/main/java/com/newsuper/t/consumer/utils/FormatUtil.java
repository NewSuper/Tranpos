package com.newsuper.t.consumer.utils;

import android.text.TextUtils;

public class FormatUtil {

    public static String numFormat(String num){
        if(!TextUtils.isEmpty(num)){
            if(num.contains(".")){
                if(num.endsWith(".0")){
                    return num.substring(0,num.indexOf(".0"));
                }else if(num.endsWith(".00")){
                    return num.substring(0,num.indexOf(".00"));
                }else if(num.endsWith("0")){
                    return num.substring(0,num.lastIndexOf("0"));
                }else{
                    return num;
                }
            }else{
                return num;
            }
        }else{
            return "0";
        }
    }
    public static double numDouble(String num){
        return (StringUtils.isEmpty(num) ? 0 : Double.parseDouble(num));
    }
    public static Float numFloat(String num){
        return (StringUtils.isEmpty(num) ? 0 : Float.parseFloat(num));
    }
    public static int numInteger(String num){
        return (StringUtils.isEmpty(num) ? 0 : Integer.parseInt(num));
    }

}

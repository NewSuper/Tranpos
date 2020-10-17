package com.transpos.market.utils.base64;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLEncodedUtil {

    private static String defaultCharset = "UTF-8";

    public static String encode(String value) {
        return encode(value, defaultCharset);
    }

    public static String encode(String value, String charset) {
        String result = value;
        try {
            result = URLEncoder.encode(value, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}

package com.newsuper.t.juejinbao.ui.movie.craw;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

public class CrawUtils {

    public static synchronized String getClickUrl(String head , String tail){
        if(tail == null){
            return null;
        }

        if(head == null){
            return null;
        }

        if(tail.startsWith("http")){
            return tail;
        }else{
            try {
                URL url = new URI(head).toURL();
                if(head.startsWith("http")) {
                    return "http://" + url.getHost() + tail;
                }else{
                    return "https://" + url.getHost() + tail;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }


//    public static String createNoChineseUrl(String url) throws Exception  {
//        String resultURL = "";
//        //遍历字符串
//        for (int i = 0; i < url.length(); i++) {
//            char charAt = url.charAt(i);
//            //只对汉字处理
//            if (isChineseChar(charAt)) {
//                String encode = URLEncoder.encode(charAt+"","UTF-8");
//                resultURL+=encode;
//            }else {
//                resultURL+=charAt;
//            }
//        }
//        return resultURL;
//
//    }
//
//    //判断汉字的方法,只要编码在\u4e00到\u9fa5之间的都是汉字
//    public static boolean isChineseChar(char c) {
//        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
//    }
}

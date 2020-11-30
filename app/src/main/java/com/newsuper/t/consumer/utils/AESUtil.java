package com.newsuper.t.consumer.utils;



import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class AESUtil {
    private static final String AESTYPE ="AES/ECB/PKCS5Padding";

    public static String AES_Encrypt(String keyStr, String plainText) {
        byte[] encrypt = null;
        try{
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
        return new String(Base64.encode(encrypt, Base64.NO_WRAP));
    }

    public static String AES_Decrypt(String keyStr, String encryptData) {
        byte[] decrypt = null;
        try{
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decode(encryptData,Base64.NO_WRAP));
        }catch(Exception e){
            e.printStackTrace();
        }
        return new String(decrypt).trim();
    }

    private static Key generateKey(String key)throws Exception{
        try{
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    public static void doAES(){
        String data = "{\"request\":{\"partnerBiz\":\"109001\",\"notifyUrl\":\"http://www.baidu.com\",\"returnUrl\":\"http://www.baidu.com\",\"userIp\":\"192.168.1.1\",\"payMoney\":\"100\",\"source\":\"h5Open\",\"payChannelList\":\"{\\\"payChannel\\\":\\\"weixin\\\"}\",\"orderList\":[{\"orderId\":\"5555555\",\"productName\":\"又一个商品测试\",\"orderMoney\":\"100\",\"orderTime\":\"2017-11-2015:00:11\"}]},\"header\":{\"timestamp\":\"1511167751182\",\"accessToken\":\"e3bb0678f39139f3ae2dc8dbb9745fcd\",\"partnerId\":\"123123\",\"signType\":\"MD5\",\"sign\":\"2554bd1638c5269979ce4aff4fea43ac\"}}";
        String key = "1111111111111111";
        String en = AES_Encrypt(key,data);
        en = "391n0janqG3fu0frNEmnw7tGeVMVBGKggeRROpYW7JOKz4eRblWj0MMoKxF9rnEh+7fqp0VljS+VtSrUqCiLoG7HngeE7G9rCbznR0GAmaZ2Epgs523/9rrg/enI74U1CyYLpXrQzvnL4/w0ZlMJ1I5f8XK2mBsKg0PW4QSNCqYLCwu9G2OUfZbaHRdbNVFuNQi4mPRGIM1TbnNkqG2n/d665QAmMTNVFenJVKivc6oGC/UG4001IX+nhkrIOJijyq2UCESG2tsXgxYGwYM1ZxNqgvOyTbFM/xj0bCYKhN6PHEIt8bxeM2FJQFff+WU8B1fgLeNqaPw49ohN5JQ2PE6TMaLVHZVRN45NUI8L/gd+Ah7E9d7OxbnWKL0XZ73E6QFyv9NozdT7NlVgjhmdzJPUaQcRQf2JWlSS6toJRoUnLVyga+hrufUZrV/N4xah9vgTgMbC8dJAt5KAIzmF1L/+ojC/e+1ur3X0TxNgzaTTBewZQiLxr4SvzHW+JhHbRJT/R5XaD6MTh4DDTq7EU7IuUgLGt518Y6qmzn8C7g1G214w5Xfn8/sIz/vR90mY7F7o0DnR/5g93l9oM7Ksp+W+alv7lT5uaulJ7UeXC3kqojWl7GJwvtMentG9NYv1cK1T4teIlwav0vog7mZ8n2FAE/9UabURFx+jQgy9BRM=";
        LogUtil.log("doAES",en);
        LogUtil.log("doAES",AES_Decrypt(key,en));
    }


}

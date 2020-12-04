package com.newsuper.t.markert.utils.base64;


/**
 * DES加密解密工具类,兼容JavaScript
 * @author 张莹
 *
 */
public class DesUtils {

    public static final String DEFAULT_DES_KEY = "$FFFFFF$";

    private static DesUtils instance = null;

    private static DesX desObj = null;

    public static DesUtils getInstance() {
        if (instance == null) { // 延迟加载
            synchronized (DesUtils.class) { // 同步锁
                if (instance == null) {
                    instance = new DesUtils();
                    desObj = new DesX();
                }
            }
        }
        return instance;
    }

    /**
     * DES加密
     *
     * @param data
     * @param key1
     * @param key2
     * @param key3
     * @return
     */
    public String EncryptString(String data, String key1, String key2, String key3) {
        return desObj.strEnc(data, key1, key2, key3);
    }

    public String EncryptString(String data, String key1, String key2) {
        return desObj.strEnc(data, key1, key2, DEFAULT_DES_KEY);
    }

    public String EncryptString(String data, String key1) {
        return desObj.strEnc(data, key1, DEFAULT_DES_KEY, DEFAULT_DES_KEY);
    }

    public String EncryptString(String data) {
        return desObj.strEnc(data, DEFAULT_DES_KEY, DEFAULT_DES_KEY, DEFAULT_DES_KEY);
    }

    /**
     * DES解密
     *
     * @param str
     * @param key1
     * @param key2
     * @param key3
     * @return
     */
    public String DecryptString(String str, String key1, String key2, String key3) {
        return desObj.strDec(str, key1, key2, key3);
    }

    public String DecryptString(String str, String key1, String key2) {
        return desObj.strDec(str, key1, key2, DEFAULT_DES_KEY);
    }

    public String DecryptString(String str, String key1) {
        return desObj.strDec(str, key1, DEFAULT_DES_KEY, DEFAULT_DES_KEY);
    }

    public String DecryptString(String str) {
        return desObj.strDec(str, DEFAULT_DES_KEY, DEFAULT_DES_KEY, DEFAULT_DES_KEY);
    }
}

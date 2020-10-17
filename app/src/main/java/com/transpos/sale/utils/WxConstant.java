package com.transpos.sale.utils;

/**
 * 类 名: WxConstant
 * 作 者: yzhg
 * 创 建: 2019/5/23 0023
 * 版 本: 2.0
 * 历 史: (版本) 作者 时间 注释
 * 描 述:  微信人脸支付常用静态常量
 */
public class WxConstant {

    /*人脸支付  返回码*/
    public static final String RETURN_CODE = "return_code";
    /*人脸支付 错误信息*/
    public static final String RETURN_MSG = "return_msg";

    /*人脸支付第二步获取rawdata*/
    public static final String RAW_DATA = "rawdata";

    /*人脸识别 获取到face Code*/
    public static final String FACE_CODE = "face_code";
    public static final String CODE_MSG = "code_msg";

    public static final String OPEN_ID = "openid";
    public static final String SUB_OPEN_ID = "sub_openid";


    /*用户取消支付*/
    public static final int USER_CANCEL_FACE_PAY = 0;
    //用户使用扫码支付
    public static final int USER_BAR_FACE_PAY = 1;
    //没有权限
    public static final int USER_NO_PERMISSION = 2;
    //用户支付失败
    public static final int USER_PAY_FAILED = 3;



    /*****************************以下配置均在微信开放平台获取************************************/
    /*配置APPID*/
    public static final String APP_ID = "wx2b029c08a6232582";
    /*商户号*/
    public static final String MCH_ID = "1900007081";
    //子商户ID
    public static final String SUB_APP_ID = "wxdace645e0bc2c424";
    //子商户MCH_ID
    public static final String SUB_MCH_ID = "349180682";
    /*商户支付秘钥*/
    public static final String MCH_KEY_ID = "MCH_KEY_ID";
    /*商户公众账号 ID*/
    public static final String LE_APP_ID = "wx60d9f2e19f7fcba1";
    /*乐刷商户号*/
    public static final String LE_MERCHANT_ID = "2211212854";

    public static String LE_KEY = "278A4BF18E15FB0F3C986C244DBEBBED";

}

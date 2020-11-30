package com.newsuper.t.consumer.utils;
/**
 * Created by Administrator on 2017/6/3 0003.
 */
public class Const  {
  /*  public static final String ADMIN_ID = "1";//开发
//    public static final String ADMIN_ID = "81562";//正式 上线
    public static final String QQ_APP_ID = "1106200468";
    public static final String WEIXIN_APP_ID = "wx096761a5c9a3c379";

//    public static final String ADMIN_APP_ID = "9805OXKSLF1504861195";//dev  阿翔测试
//        public static final String ADMIN_APP_ID = "4506CUXAWN1527932706";//dev测试angw
    public static final String ADMIN_APP_ID = "8908MGBGDR1503565580";//test app   测试
//    public static final String ADMIN_APP_ID = "5656TNYNKT1510144419";//测试 - 分区
//        public static final String ADMIN_APP_ID = "8554KIJIPU1511189988";//正式 - 市场 admin = 1
//        public static final String ADMIN_APP_ID = "1524VXWFBE1512010473";//正式 上线 admin = 81562*/


    public static final String ADMIN_ID = BuildConfig.ADMIN_ID;//开发
    public static final String QQ_APP_ID = BuildConfig.QQ_APP_ID;
    public static final String WEIXIN_APP_ID = BuildConfig.WEIXIN_APP_ID;
    public static final String ADMIN_APP_ID = BuildConfig.ADMIN_APP_ID;//test app   测试


    //登录返回code  不需要修改
    public static final String STRING_DIVER = "｜";
    public static final String VERSION_NUM = "20802";
    public static final int GO_TO_LOGIN = 1101;
    public static final String BROADCAST_ACTION_ORDER  = "action_order_dialog_show";
    public static final String BROADCAST_ACTION_COUPON  = "action_coupon_dialog_show";

}
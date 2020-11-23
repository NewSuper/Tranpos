package com.newsuper.t.juejinbao.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.JJBApplication;


public class WXLaunchMiniUtil {

    private static final String MINI_PROGRAM_TYPE_RELEASE = "0";//0正式版 1开发版 2体验版
    public String appId = Constant.WX_APP_ID;//微信开发者平台APP的id
    public String userName = Constant.WX_MINI_OLD_ID;// 小程序原始id
    public String path;//拉起小程序页面的可带参路径，不填默认拉起小程序首页
    public String miniProgramType = MINI_PROGRAM_TYPE_RELEASE;

    public WXLaunchMiniUtil(String path, String miniProgramType, String appId, String smallId) {
        this.path = path;
        this.miniProgramType = miniProgramType;
        this.appId = appId;
        this.userName = smallId;
    }

    //跳转
    public void sendReq() {
        ContentResolver resolver = JJBApplication.getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.tencent.mm.sdk.comm.provider/launchWXMiniprogram");
        String[] path = new String[]{this.appId, this.userName, this.path, this.miniProgramType, ""};
        Cursor cursor;
        if ((cursor = resolver.query(uri, null, null, path, null)) != null) {
            cursor.close();
        }
    }
}

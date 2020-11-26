package com.newsuper.t.juejinbao.ui.movie.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.newsuper.t.juejinbao.ui.movie.activity.ScreenTeachActivity;

import java.util.List;

import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

public class JzvdStdEx extends JzvdStd {
    private Context context;

    //视频下载地址
//    private String title = null;
//    private String videoDownloadUrl = null;
//    //app下载地址
//    private String appDownloadUrl = null;

    protected RelativeLayout ll_upnp;
    protected LinearLayout rl_operate;
    protected RelativeLayout rl_operate2;
    protected ImageView iv_set;
    protected TextView tv_upnp_close;
    protected RelativeLayout ll_download;
    protected TextView tv_net;

    protected ImageView iv_tpjc;

    public RelativeLayout rl_next;
    protected RelativeLayout rl_xuanji;
    protected RelativeLayout rl_beisu;
    protected RelativeLayout b1;
    protected RelativeLayout b2;

    protected RelativeLayout ll_back2;


    protected RelativeLayout blackboard;

//    protected TextView tv_netkb;

    public JzvdStdEx(Context context) {
        super(context);
    }

    public JzvdStdEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        this.context = context;

        loadingProgressBar.setVisibility(View.GONE);
        ll_upnp = findViewById(cn.jzvd.R.id.ll_upnp);
        rl_operate = findViewById(cn.jzvd.R.id.rl_operate);
        rl_operate2 = findViewById(cn.jzvd.R.id.rl_operate2);
        iv_set = findViewById(cn.jzvd.R.id.iv_set);
        tv_upnp_close = findViewById(cn.jzvd.R.id.tv_upnp_close);

        ll_download = findViewById(cn.jzvd.R.id.ll_download);

        tv_net = findViewById(cn.jzvd.R.id.tv_net);
        iv_tpjc = findViewById(cn.jzvd.R.id.iv_tpjc);

        rl_next = findViewById(cn.jzvd.R.id.rl_next);
        rl_xuanji = findViewById(cn.jzvd.R.id.rl_xuanji);
        rl_beisu = findViewById(cn.jzvd.R.id.rl_beisu);
        b1 = findViewById(cn.jzvd.R.id.b1);
        b2 = findViewById(cn.jzvd.R.id.b2);

        ll_back2 = findViewById(cn.jzvd.R.id.ll_back2);

        blackboard = findViewById(cn.jzvd.R.id.blackboard);

        ll_upnp.setOnClickListener(this);
        iv_set.setOnClickListener(this);
        tv_upnp_close.setOnClickListener(this);
        blackboard.setOnClickListener(this);

        ll_download.setOnClickListener(this);
        rl_lock.setOnClickListener(this);
        iv_tpjc.setOnClickListener(this);
        rl_xuanji.setOnClickListener(this);

        ll_back2.setOnClickListener(this);
        rl_next.setOnClickListener(this);
        rl_beisu.setOnClickListener(this);

        rl_operate.setVisibility(View.VISIBLE);
        rl_operate2.setVisibility(View.VISIBLE);
        tv_net.setVisibility(View.VISIBLE);

        rl_bofang.setVisibility(View.VISIBLE);
        rl_next.setVisibility(View.VISIBLE);
        rl_xuanji.setVisibility(View.VISIBLE);
        rl_beisu.setVisibility(View.VISIBLE);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);



    }

    @Override
    public void setScreenNormal() {
        super.setScreenNormal();

        rl_operate.setVisibility(View.GONE);
        rl_operate2.setVisibility(View.GONE);
        rl_beisu.setVisibility(View.GONE);
        if(stateListener != null) {
            stateListener.screennormal();
        }
    }

    @Override
    public void setScreenFullscreen() {
        super.setScreenFullscreen();

        if(netUrl != null && screen == SCREEN_FULLSCREEN) {
            this.rl_operate.setVisibility(bottomContainer.getVisibility());
            this.rl_operate2.setVisibility(bottomContainer.getVisibility());
            this.rl_beisu.setVisibility(bottomContainer.getVisibility());
        }else{
            this.rl_operate.setVisibility(View.GONE);
            this.rl_operate2.setVisibility(View.GONE);
        }


    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro,
                                        int thumbImg, int bottomPro, int retryLayout) {
        super.setAllControlsVisiblity(topCon , bottomCon , startBtn , loadingPro , thumbImg , bottomPro , retryLayout);

        if(isLock){

        }else {
            if (netUrl != null && screen == SCREEN_FULLSCREEN) {
                this.rl_operate.setVisibility(bottomCon);
                this.rl_operate2.setVisibility(bottomCon);
                this.rl_beisu.setVisibility(bottomCon);
            } else {
                this.rl_operate.setVisibility(View.GONE);
                this.rl_operate2.setVisibility(View.GONE);
                this.rl_beisu.setVisibility(View.GONE);
            }
        }
    }



    public int brightnessPercent = 100;
    @Override
    public void showBrightnessDialog(int brightnessPercent) {
        super.showBrightnessDialog(brightnessPercent);
        this.brightnessPercent = brightnessPercent;
    }

    @Override
    public void dissmissControlView() {
        super.dissmissControlView();
        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> {
                if(netUrl != null && screen == SCREEN_FULLSCREEN) {
                    this.rl_operate.setVisibility(View.INVISIBLE);
                    this.rl_operate2.setVisibility(View.INVISIBLE);
                }else{
                    this.rl_operate.setVisibility(View.GONE);
                    this.rl_operate2.setVisibility(View.GONE);
                }

            });
        }
    }

    //隐藏右侧多功能按钮
    public void hideFuncButton(){
        this.rl_operate.setVisibility(View.GONE);
        this.rl_operate2.setVisibility(View.GONE);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        //点击投屏
        if(i == cn.jzvd.R.id.ll_upnp){
            if(upnpListener != null) {
                upnpListener.upnp(netUrl);
            }
        }
        //取消投屏
        else if(i == cn.jzvd.R.id.tv_upnp_close){
            if(upnpListener != null) {
                upnpListener.cancelupnp();
            }
        }
        //点击更多
        else if(i == cn.jzvd.R.id.iv_set){
            if(upnpListener != null) {
                upnpListener.more();
            }
        }
        //点击下载
        else if(i == cn.jzvd.R.id.ll_download){
//            clickDownload();
            if(upnpListener != null) {
                upnpListener.download();
            }
        }
        //点击投屏教程
        else if(i == cn.jzvd.R.id.iv_tpjc){
            ScreenTeachActivity.intentMe(context);
        }
        //点击锁屏
        else if(i == cn.jzvd.R.id.rl_lock){
            if(changeLock()){
                JZUtils.setRequestedOrientation(getContext(), ActivityInfo.SCREEN_ORIENTATION_LOCKED );
                iv_lock.setBackgroundResource(cn.jzvd.R.drawable.player_lock);
                dissmissControlView();
            }else{
                JZUtils.setRequestedOrientation(getContext(), FULLSCREEN_ORIENTATION);
                iv_lock.setBackgroundResource(cn.jzvd.R.drawable.player_unlock);
                showUI();
            }
        }
        //选集
        else if(i == cn.jzvd.R.id.rl_xuanji){
            if(upnpListener != null) {
                upnpListener.selectTV();
            }
        }

        //黑板退出activity
        else if(i == cn.jzvd.R.id.ll_back2){
            if(finishListener != null){
                finishListener.finish();
            }
        }
        //点击下一集
        else if(i == cn.jzvd.R.id.rl_next){
            if(upnpListener != null) {
                upnpListener.nextTV();
            }
        }
        //点击倍速
        else if(i == cn.jzvd.R.id.rl_beisu){
            if(upnpListener != null) {
                upnpListener.beisu();
            }
        }

    }

//    //点击下载
//    @SuppressLint("WrongConstant")
//    public void clickDownload(){
//        if(!TextUtils.isEmpty(appDownloadUrl) && !TextUtils.isEmpty(title)) {
//            //已安装，直接打开
//            if(isAPKInstalled(context , "com.shandianspeed.android")){
//                new android.support.v7.app.AlertDialog.Builder(context)
//                        .setCancelable(true)
//                        .setMessage("您将跳转至第三方应用下载，掘金宝对您下载的内容不承担任何责任")
//                        .setPositiveButton("同意", (dialog, which) -> {
//                            dialog.dismiss();
//                            Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.shandianspeed.android");
//                            if (intent != null) {
//                                intent.putExtra("title", title);
//                                intent.putExtra("videoDownloadUrl", videoDownloadUrl);
//                                intent.setFlags(101);
//                                context.startActivity(intent);
//                            }
//
//
//                        })
//                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
//                        .create()
//                        .show();
//
//
//
//            }
//            //未安装 去下载
//            else{
//                if(!TextUtils.isEmpty(appDownloadUrl)) {
//
//
//                    new android.support.v7.app.AlertDialog.Builder(context)
//                            .setCancelable(true)
//                            .setMessage("下载该视频需下载闪电视频加速软件")
//                            .setPositiveButton("同意", (dialog, which) -> {
//                                dialog.dismiss();
//
//
//                                //视频地址复制到剪贴板
//                                ClipboardManager myClipboard = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE);
//                                ClipData myClip = ClipData.newPlainText("text", "jjb_" + title + "_" + videoDownloadUrl);
//                                myClipboard.setPrimaryClip(myClip);
//
//                                lightDownloadListener.download(appDownloadUrl);
//
//
//
//
//                            })
//                            .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
//                            .create()
//                            .show();
//
//                }
//            }
//
//
//        }
//    }

    private String netUrl = null;
    private UpnpListener upnpListener;
    public void setNetUrl(String netUrl, UpnpListener upnpListener){
        this.netUrl = netUrl;
        this.upnpListener = upnpListener;
    }

    public static interface UpnpListener{
        public void more();
        public void upnp(String netUrl);
        public void download();
        public void cancelupnp();
        public void selectTV();
        public void nextTV();
        public void beisu();
    }

    //是否显示关闭投屏按钮
    public void upnpCloseShow(boolean show){
        if(show) {
            tv_upnp_close.setVisibility(View.VISIBLE);
        }else{
            tv_upnp_close.setVisibility(View.GONE);
        }
    }

//    //判断是否安装APK
//    public static boolean isAPKInstalled(Context context , String pkgName) {
//        final PackageManager packageManager = context.getPackageManager();
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
//        if (pinfo != null) {
//            for (int i = 0; i < pinfo.size(); i++) {
//                String pn = pinfo.get(i).packageName;
//                if (pn.equals(pkgName)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


    //设置视频下载地址
//    public void setVideoDownloadUrl(String title ,String videoDownloadUrl){
//        this.title = title;
//        this.videoDownloadUrl = videoDownloadUrl;
//        //显示下载按钮
//        ll_download.setVisibility(View.VISIBLE);
//    }

//    //下载闪电app
//    private LightDownloadListener lightDownloadListener;
//    public void setAppDownloadUrl(String appDownloadUrl , LightDownloadListener lightDownloadListener){
//        this.appDownloadUrl = appDownloadUrl;
//        this.lightDownloadListener = lightDownloadListener;
//        //显示下载按钮
//        ll_download.setVisibility(View.VISIBLE);
//    }
//    //启动下载
//    public static interface LightDownloadListener{
//        public void download(String url);
//    }

//    public void showNetkb(){
//        ll_loading.setVisibility(View.VISIBLE);
//        tv_netkb.setVisibility(View.VISIBLE);
//        loadingProgressBar.setVisibility(View.VISIBLE);
//    }
//    public void setNetkb(int kbs){
//        if(kbs < 1000) {
//            tv_netkb.setText(kbs + " kb/s");
//        }else{
//            tv_netkb.setText(Utils.float1(kbs / 1000f)  + " mb/s");
//        }
//    }
//
//    public void hideNetkb(){
//        ll_loading.setVisibility(View.INVISIBLE);
//        tv_netkb.setVisibility(View.INVISIBLE);
//        loadingProgressBar.setVisibility(View.INVISIBLE);
//    }

    //更改网络状态
    public void changeNetState(String stateStr){
        tv_net.setText(stateStr);
    }

    //选集黑板显示
    public void showBlackboard(boolean show){
        if(show){
            blackboard.setVisibility(View.VISIBLE);
        }else{
            blackboard.setVisibility(View.GONE);
        }
    }

    //是否能显示选集
    public void showSelectTV(boolean show){
        if(show){
            rl_xuanji.setVisibility(View.VISIBLE);
            rl_bofang.setVisibility(View.VISIBLE);
        }else{
            rl_xuanji.setVisibility(View.GONE);
            rl_next.setVisibility(View.GONE);
        }
    }

}

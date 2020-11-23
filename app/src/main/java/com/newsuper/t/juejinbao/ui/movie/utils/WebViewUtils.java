package com.newsuper.t.juejinbao.ui.movie.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import razerdp.blur.thread.ThreadPoolManager;
import rx.Subscriber;
import rx.Subscription;

import static android.app.Activity.RESULT_OK;
import static io.paperdb.Paper.book;

public class WebViewUtils {
    //登录界面跳转回来
    public static final int RESQUESTCODE_LOGIN_JS_BACK = 100;

    public static final int UPLOAD_IMAGE = 2001;
    public static final int UPLOAD_VIDEO = 2002;
    public static final int UPLOAD_AUDIO = 2003;
    public static final int REQUEST_CODE_WATCH_AD_IN_TASK = 2003;

    private Activity activity;
    private WebFragment fragment;


    private BridgeWebView webView;
    private RelativeLayout rl_loading;
    public boolean loading = false;

    WebViewUtilsListener webViewUtilsListener;

    //登录回调JS
    private CallBackFunction logonBackFunction;

    //授权回调JS
    private CallBackFunction authorizeFunction;

    //下载回调JS
    private CallBackFunction downloadFunction;

    //播放激励广告视频回调JS
    private CallBackFunction playTimeFunction;

    //大转盘回调
//    private CallBackFunction bigTurnFunction;

    //Activity分享回调JS
    private CallBackFunction shareFunction;

    //影视搜索网页爬取回调
    private CallBackFunction crawlFunction;


    //奖励弹框
    GoldDialog goldDialog;
    private int activityId;
    private CallBackFunction reloadFunction;

    public WebViewUtils(Activity activity, WebFragment fragment, BridgeWebView webView, RelativeLayout rl_loading, WebViewUtilsListener webViewUtilsListener) {

        this.activity = activity;
        if (fragment != null) {
            this.activity = fragment.getActivity();
        }
        this.fragment = fragment;
        this.webView = webView;
        this.rl_loading = rl_loading;
        this.webViewUtilsListener = webViewUtilsListener;
        init1();
        init4();
        init3();
    }


    private void init1() {
        WebSettings websettings = webView.getSettings();
//        webView.clearM\atches();
//        webView.clearCache(true);
        webView.resumeTimers();
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速

        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        websettings.setLoadsImagesAutomatically(true);
        websettings.setJavaScriptEnabled(true);
//        websettings.setUseWideViewPort(true);
        websettings.setSupportMultipleWindows(true);
        websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        websettings.setBuiltInZoomControls(false);
        websettings.setSupportZoom(false);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
        websettings.setDomStorageEnabled(true); // 开启 DOM storage 功能
        String appCachePath = activity.getApplicationContext().getCacheDir().getAbsolutePath();
        websettings.setAppCachePath(appCachePath);
        websettings.setAllowFileAccess(true);    // 可以读取文件缓存
        websettings.setAppCacheEnabled(true);    //开启H5(APPCache)缓存功能

        websettings.setTextZoom(100);//防止系统字体大小影响布局


        //某些魅族手机白屏问题
//        websettings.setJavaScriptEnabled(true);
//        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        websettings.setDomStorageEnabled(true);
//        websettings.setDatabaseEnabled(true);
//        websettings.setAppCacheEnabled(true);
//        websettings.setAllowFileAccess(true);
//        websettings.setSavePassword(true);
//        websettings.setSupportZoom(true);
//        websettings.setBuiltInZoomControls(true);
//        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        websettings.setUseWideViewPort(true);

        //与js弹框交互
        websettings.setJavaScriptEnabled(true);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

    }


    private void init3() {
        webView.setWebViewClient(new MyWebViewClient(webView));
    }

    private CallBackFunction uploadFunction;

    private void init4() {

        //1.1分享
        webView.registerHandler("share", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                shareFunction = function;
                try {


                    JSONObject jsonObject = new JSONObject(data);

                    Log.i("zzz", "handler: " + data.toString());
                    String url = jsonObject.optString("url");
                    String title = jsonObject.optString("title");
                    String description = jsonObject.optString("description");
                    String thumb = jsonObject.optString("thumb");

                    //platform_type:wx,qq,wb,pyq,sfsq
                    String platform_type = jsonObject.optString("platform_type");
                    String type = jsonObject.optString("type");
                    String pictures = jsonObject.optString("pictures");
                    int isPictrueBybase64 = jsonObject.optInt("isPictrueBybase64");


                    String path = jsonObject.optString("path");

                    String id = jsonObject.optString("id");
                    //分享文案  title + url + describe
                    String shareContent = jsonObject.optString("shareContent");

                    //1为走路赚钱的分享
                    int isWalk = jsonObject.optInt("isWalk", 0);

                    ShareInfo shareInfo = new ShareInfo();

                    shareInfo.setUrl(url);
                    shareInfo.setTitle(title);
                    shareInfo.setDescription(description);
                    shareInfo.setThumb(thumb);
                    shareInfo.setBigPic(pictures);
                    shareInfo.setShareContent(shareContent);
                    shareInfo.setIsPictrueBybase64(isPictrueBybase64);

                    shareInfo.setId(id);
                    shareInfo.setUrl_type(type);
                    shareInfo.setUrl_path(path);
                    shareInfo.setPlatform_type(platform_type);

                    shareInfo.setIsWalk(isWalk);


                    if (fragment != null) {
                        fragment.showShareDialog(shareInfo, false);
                    } else {
                        ((BridgeWebViewActivity) activity).showShareDialog(shareInfo);
                    }
//                    Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        //1.2点击关闭方法
        webView.registerHandler("finish", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (fragment == null) {
                    activity.finish();
                }
            }
        });

        //1.3弹出提示语
        webView.registerHandler("showToast", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                ToastUtils.getInstance().show(activity, data);
            }
        });

        //1.4登录方法
        webView.registerHandler("login", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (!ClickUtil.isNotFastClick()) {
                    return;
                }

                logonBackFunction = function;
                Intent intent = new Intent(activity, GuideLoginActivity.class);
                if (fragment != null) {
                    fragment.startActivityForResult(intent, RESQUESTCODE_LOGIN_JS_BACK);
                } else {
                    activity.startActivityForResult(intent, RESQUESTCODE_LOGIN_JS_BACK);
                }
            }
        });

        //1.5获取登录信息
        webView.registerHandler("logininfo", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity != null) {
                    String loginStr = JSON.toJSONString(loginEntity);
                    function.onCallBack(loginStr);

                } else {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("code", 1);
                        json.put("channel", BaseApplication.getChannel());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    function.onCallBack(json.toString());
                }
            }
        });
        //1.6退出登录
        webView.registerHandler("logout", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                webViewUtilsListener.logout();
            }
        });

        //1.8打开广告
        webView.registerHandler("openAd", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    ADDataEntity adDataEntity = JSON.parseObject(data, ADDataEntity.class);
                    if (adDataEntity != null) {
                        WebActivity.intentMe(activity, adDataEntity.getTitle(), adDataEntity.getLink());
//                        BridgeWebViewActivity.intentMe(activity, adDataEntity.getLink());
                    } else {
                        Toast.makeText(activity, "解析广告失败", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(activity, "解析广告失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        //1.9下载文件
        webView.registerHandler("downloadFile", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    ADDataEntity adDataEntity = JSON.parseObject(data, ADDataEntity.class);
                    if (adDataEntity != null) {
                        //浏览器下载
                        if (adDataEntity.getDownload_type() == 1) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(adDataEntity.getDownload_android());
                            intent.setData(content_url);
                            activity.startActivity(intent);
                        }
                        //自己下载
                        else {
                            downloadFunction = function;
                            download(adDataEntity.getDownload_android());

                        }
                    } else {
                        Toast.makeText(activity, "解析广告失败", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(activity, "解析广告失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        //1.10跳转
        webView.registerHandler("jumpTab", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                reloadFunction = function;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int index = jsonObject.optInt("index", 2);
                    String path = jsonObject.optString("path", "");
                    int self = jsonObject.optInt("self", 0);
                    String query = jsonObject.optString("query", "");
                    //跳转tab
                    if (TextUtils.isEmpty(path)) {

                        if (fragment == null) {
                            //通知跳转
                            Message msg = new Message();
                            msg.what = BusConstant.MAIN_SWITCH;
                            msg.arg1 = index;
                            msg.obj = query;

                            BusProvider.getInstance().post(msg);
                            activity.startActivity(new Intent(activity, MainActivity.class));
//                            activity.finish();
                        } else {
                            //强制跳转
                            Message msg = new Message();
                            msg.what = BusConstant.MAIN_SWITCH2;
                            msg.arg1 = index;
                            msg.obj = query;

                            BusProvider.getInstance().post(msg);
                        }
                    } else {
                        //刷新页面
                        if (self == 1) {
                            webView.loadUrl(RetrofitManager.WEB_URL_ONLINE + path);
//                            webView.loadUrl("https://www.hao123.com");
                        }
                        //跳转新页面
                        else {
                            if (path.contains("http")) {
                                if (!ClickUtil.isNotFastClick()) {
                                    return;
                                }
                                BridgeWebViewActivity.intentMe(activity, path);
                            } else {
                                if (!ClickUtil.isNotFastClick()) {
                                    return;
                                }
                                BridgeWebViewActivity.intentMe(activity, RetrofitManager.WEB_URL_ONLINE + path);
                            }
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //1.11关闭loading
        webView.registerHandler("closeLoading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (rl_loading != null) {
                    rl_loading.setVisibility(View.GONE);
                }
                loading = false;
            }
        });

        //1.12授权
        webView.registerHandler("authorize", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                authorizeFunction = function;
                if (data.equals("wx")) {
                    if (!Utils.isWXClientAvailable(activity)) {
                        Toast.makeText(activity, "未安装微信", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    authorization(SHARE_MEDIA.WEIXIN);
                } else if (data.equals("qq")) {
                    if (!Utils.isQQClientAvailable(activity)) {
                        Toast.makeText(activity, "未安装QQ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    authorization(SHARE_MEDIA.QQ);
                }
            }
        });

        //1.13保存图片到相册
        webView.registerHandler("savePictrue", new BridgeHandler() {
            @Override
            public void handler(String datas, final CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String type = jsonObject.optString("type");
                    final String data = jsonObject.optString("data");
                    ThreadPoolManager.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (type.equals("link")) {
                                FutureTarget<Bitmap> bitmap = Glide.with(activity)
                                        .asBitmap()
                                        .load(data)
                                        .submit();
                                try {
                                    Bitmap bmp = bitmap.get();
                                    Utils.saveBmp2Gallery(activity, bmp, System.currentTimeMillis() + ".jpg");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (type.equals("base64")) {
                                Bitmap bmp = Utils.base64ToBitmap(data);
                                Utils.saveBmp2Gallery(activity, bmp, System.currentTimeMillis() + ".jpg");
                            }


                            if (activity != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //返回成功
                                        try {
                                            JSONObject jsonObject1 = new JSONObject();
                                            jsonObject1.put("code", 0);

                                            function.onCallBack(jsonObject1.toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                        }
                    });

                } catch (Exception e) {
                }
            }
        });

        //1.14打开浏览器
        webView.registerHandler("openBrowser", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    Log.i("zzz", "handler: " + jsonObject.toString());
                    String url = jsonObject.optString("url");
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    activity.startActivity(intent);

                } catch (Exception e) {
                }
            }
        });

        //1.15打开宝箱
        webView.registerHandler("openBox", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {

                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String url = jsonObject.optString("url");
                    String coin = jsonObject.optString("coin");
                    String share_friend = jsonObject.optString("share_friend");
                    String inviting_friend = jsonObject.optString("inviting_friend");

                    openBox(url, coin, share_friend, inviting_friend);

                    //埋点（点击打开宝箱按钮分享按钮）
                    MobclickAgent.onEvent(MyApplication.getContext(), EventID.MISSION_EVERYDAYMISSION_OPENBOX_SHARE);

                } catch (JSONException e) {
                }


            }
        });

        //1.16打开奖励弹框
        webView.registerHandler("openReward", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {

                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String title = jsonObject.optString("title");
                    double gold = jsonObject.optDouble("gold", 0);
                    String detail = jsonObject.optString("detail");

//                    if(goldDialog == null) {
//                        goldDialog = new GoldDialog(activity);
//                    }
//                    goldDialog.show( gold, title, detail);
                    showReward(Utils.FormatGold(gold), title, detail);

                } catch (JSONException e) {
                }

            }
        });

        //1.17签到弹框
        webView.registerHandler("openCheckIn", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                int id = 0;
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    id = jsonObject.optInt("id", 0);
                } catch (JSONException e) {
                }


                CheckInEntity checkInEntity = JSON.parseObject(datas, CheckInEntity.class);

                if (checkInEntity != null) {
                    if (checkInEntity.getData().getPrompt_reward() == 1) {
//                        if (checkInEntity.getData().getAd_info().size() > 0) {
//                            CheckInDialog checkInDialog = new CheckInDialog(activity, checkInEntity.getData().getCoin(), checkInEntity.getData().getAd_info().get(0));
//                            checkInDialog.show();
//                        } else {
//                            showReward(Utils.FormatGold(checkInEntity.getData().getCoin()), "奖励到账", "签到成功");
//
//                        }
                        showRewardPop(checkInEntity.getData().getCoin());
                    }

                    //关闭任务小红点
                    Message msg = new Message();
                    msg.what = BusConstant.TASK_CLOSE_ALERT;

                    BusProvider.getInstance().post(msg);
                }

            }
        });

        //1.19提供文章阅读历史
        webView.registerHandler("provideArticleHistory", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                ArrayList<HomeListEntity.DataBean> dataBeanArrayList = new ArrayList<>();
                try {
                    dataBeanArrayList = book().read(PagerCons.KEY_READOBJECT);
                    if (dataBeanArrayList == null) {
                        dataBeanArrayList = new ArrayList<>();
                    }
                } catch (Exception e) {

                }

                function.onCallBack(JSON.toJSONString(dataBeanArrayList));
            }
        });

        //1.19删除文章阅读历史
        webView.registerHandler("deleteArticleHistory", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                ArrayList<HomeListEntity.DataBean> dataBeanArrayList = new ArrayList<>();
                try {
                    dataBeanArrayList = book().read(PagerCons.KEY_READOBJECT);
                    if (dataBeanArrayList == null) {
                        dataBeanArrayList = new ArrayList<>();
                    }

                    JSONArray jsonArray = new JSONArray(datas);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        int deleteId = jsonArray.getInt(i);

                        Iterator<HomeListEntity.DataBean> it = dataBeanArrayList.iterator();
                        while (it.hasNext()) {
                            int id = it.next().getId();
                            if (id == deleteId) {
                                it.remove();
                            }
                        }


                    }


                    book().write(PagerCons.KEY_READOBJECT, dataBeanArrayList);

                } catch (Exception e) {

                }


//                function.onCallBack(JSON.toJSONString(dataBeanArrayList));
            }
        });

        //1.20点击VIP视频平台
        webView.registerHandler("openVIPPlatform", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                VIPPlatformEntity vipPlatformEntity = JSON.parseObject(datas, VIPPlatformEntity.class);
                if (vipPlatformEntity.getType().equals("parse")) {
                    VipWebActivity.intentMe(activity, vipPlatformEntity.getTitle(), vipPlatformEntity.getIndex_url(), vipPlatformEntity.getTitle(), vipPlatformEntity.getIs_resolving());
                } else if (vipPlatformEntity.getType().equals("live")) {
                    AlertWebActivity.intentMe(activity, vipPlatformEntity.getTitle(), RetrofitManager.VIP_JS_URL + "/live" + vipPlatformEntity.getRemoteUrl());
                }
            }
        });

        //1.21离开web请求接口
        webView.registerHandler("leaveRequest", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                String aid = "";
                String starttime = "";
                String type = "";

                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    aid = jsonObject.optString("aid");
                    starttime = jsonObject.optString("starttime");
                    type = jsonObject.optString("type");
                } catch (JSONException e) {
                }

                Map<String, String> map = new HashMap<>();
                map.put("aid", aid);
                map.put("starttime", starttime);
                map.put("type", type);

                Utils.leaveWeb(activity, map);


            }
        });

        //1.22修改密码后更新登录信息
        webView.registerHandler("updateToken", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                String token = "";
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    token = jsonObject.optString("token");
                } catch (JSONException e) {
                }

                if (!TextUtils.isEmpty(token)) {
                    LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);

                    if (loginEntity != null && loginEntity.isLogin()) {
                        loginEntity.getData().setUser_token(token);
                        Paper.book().write(PagerCons.USER_DATA, loginEntity);
                    }
                }
            }
        });

        //1.23拉起小游戏
        webView.registerHandler("loadGame", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                if (LoginEntity.getIsLogin()) {
                    activity.startActivity(new Intent(activity, BaoquGameActivity.class));
                } else {
                    activity.startActivity(new Intent(activity, GuideLoginActivity.class));
                }

            }
        });

        //1.24打开图集
        webView.registerHandler("openPictureViewPager", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                int id = 0;
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    id = jsonObject.optInt("id", 0);

//                    Intent intent = new Intent(activity, PictureViewPagerActivity.class);
//                    intent.putExtra("id", id);
//                    activity.startActivity(intent);
                    PictureViewPagerActivity.intentMe(activity, id, 0);

                } catch (JSONException e) {
                }

            }
        });

        //1.25开启右下广告
        webView.registerHandler("isShowConvertPage", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String imgUrl = jsonObject.optString("imgUrl");
                    String link = jsonObject.optString("link");
                    webViewUtilsListener.showRightBottomAD(imgUrl, link);

                } catch (JSONException e) {
                }

            }
        });

        //1.26复制到剪贴板
        webView.registerHandler("copytext", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String text = jsonObject.optString("text");
                    ClipboardUtil.Clip(activity, text);
                    function.onCallBack("123");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


//        //1.31播放视频
//        webView.registerHandler("playMovie", new BridgeHandler() {
//            @Override
//            public void handler(String datas, CallBackFunction function) {
//                try {
//                    JSONObject jsonObject = new JSONObject(datas);
//                    String title = jsonObject.optString("title");
//                    String image = jsonObject.optString("image");
//                    String url = jsonObject.optString("url");
//
//                    PlayMovieActivity.intentMe(activity, title, image, url);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        //1.27跳转解析播放
        webView.registerHandler("toVipPlay", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String title = jsonObject.optString("title");
                    String url = jsonObject.optString("url");
//
//                    VipPlayActivity.intentMe(activity, title, url);


                    MovieThirdIframeEntity movieThirdIframeEntity = new MovieThirdIframeEntity();
                    movieThirdIframeEntity.setTitle(title);
                    movieThirdIframeEntity.setCurrentIndex(0);
                    movieThirdIframeEntity.setType("thirdIframe");

                    MovieThirdIframeEntity.SourceListBean sourceListBean = new MovieThirdIframeEntity.SourceListBean();
                    sourceListBean.setTitle("");
                    sourceListBean.setSource("");
                    sourceListBean.setUrl(url);

                    List<MovieThirdIframeEntity.SourceListBean> listBeans = new ArrayList<>();
                    listBeans.add(sourceListBean);

                    movieThirdIframeEntity.setSourceList(listBeans);
                    book().write(PagerCons.KEY_PLAYMOVIE_DATA, JSON.toJSONString(movieThirdIframeEntity));
                    PlayMovieActivity.intentMe(activity, false);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        //1.28提供今日步数
        webView.registerHandler("todaySteps", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
//                try {
//                    if (MyApplication.getInstance().iSportStepInterface != null) {
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("step", MyApplication.getInstance().iSportStepInterface.getCurrentTimeSportStep());
//                        jsonObject.put("date", DateUtils.showYearAndMonAndDay(System.currentTimeMillis() / 1000L));
//                        function.onCallBack(jsonObject.toString());
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("step", StepUtils.getInstance(activity).getCurrentStep());
                    jsonObject.put("date", DateUtils.showYearAndMonAndDay(System.currentTimeMillis() / 1000L));
                    function.onCallBack(jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        //1.29清空今日步数
        webView.registerHandler("cleanSteps", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
//                try {
//                    MyApplication.getInstance().iSportStepInterface.clean();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
                StepUtils.getInstance(activity).cleanStep();
            }
        });

        //1.30播放广告视频
        webView.registerHandler("playVideo", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                playTimeFunction = function;
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String entryType = jsonObject.optString("entry_type");
//                    String coin = jsonObject.optString("coin");
//                    String image = jsonObject.optString("image");
//                    String url = jsonObject.optString("url");


                    if (!ClickUtil.isNotFastClick()) {
                        return;
                    }
                    if ("view_ad".equals(entryType)) {  //任务看广告赚金币
                        PlayRewardVideoAdActicity.intentMe(activity, PlayRewardVideoAdActicity.WATCHAD);
                    } else if ("bigTurnTable".equals(entryType)) {
                        PlayRewardVideoAdActicity.intentMe(activity, PlayRewardVideoAdActicity.BIGTURNTAB);
//                        GDTRewardVideoActivityActivity.intentMe(activity, PlayRewardVideoAdActicity.BIGTURNTAB);
                    }
                    //走路赚钱
                    else {
                        PlayRewardVideoAdActicity.intentMe(activity, PlayRewardVideoAdActicity.TOTHIS);
                    }

//                    activity.startActivity(new Intent(activity,PlayRewardVideoAdActicity.class));


//                    if(fragment != null) {
//
//                        PlayVideoActivity.intentMe(fragment, title, image , coin, url, PlayVideoActivity.TOTHIS);
//                    }else{
//                        PlayVideoActivity.intentMe(activity, title, image , coin, url, PlayVideoActivity.TOTHIS);
//                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //1.32测试
        webView.registerHandler("injectJS", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                playTimeFunction = function;
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String url = jsonObject.optString("url");

                    JSWebActivity.intentMe(activity, "", url);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //1.33是否存在计步器
        webView.registerHandler("haveWalk", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {


//                try {
//                    if (MyApplication.getInstance().iSportStepInterface != null) {
//                        if (MyApplication.getInstance().iSportStepInterface.canWalk()) {
//                            JSONObject jsonObject = new JSONObject();
//                            jsonObject.put("canWalk", 1);
//                            function.onCallBack(jsonObject.toString());
//                        } else {
//                            JSONObject jsonObject = new JSONObject();
//                            jsonObject.put("canWalk", 0);
//                            function.onCallBack(jsonObject.toString());
//                        }
//                    } else {
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("canWalk", 0);
//                        function.onCallBack(jsonObject.toString());
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }

                try {
                    if (StepUtils.getInstance(activity).getStepCounter()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("canWalk", 1);
                        function.onCallBack(jsonObject.toString());
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("canWalk", 0);
                        function.onCallBack(jsonObject.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        //1.34获取图片url后下载并转成base64返回 urlToBase64
        webView.registerHandler("urlToBase64", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String url = jsonObject.optString("url");

                    Glide.with(activity).load(url).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                            try {
                                Bitmap bmp = Utils.drawableToBitmap(resource);

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("base64", Utils.bitmapToBase64(bmp));
                                function.onCallBack(jsonObject.toString());

                            } catch (Exception e) {
                            }

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("code", 1);
                                if (function != null) {
                                    function.onCallBack(jsonObject.toString());
                                }
                            } catch (Exception e) {
                            }
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        //1.35暗加载网页注入js并拿取数据
        webView.registerHandler("getWebMovieInfo", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                crawlFunction = function;


//                function.onCallBack("{}");
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String url = jsonObject.optString("url");

                    webViewUtilsListener.getWebMovieInfo(url);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //1.36影视页跳转原生播放器 movieWebToPlay
        webView.registerHandler("movieWebToPlay", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                book().write(PagerCons.KEY_PLAYMOVIE_DATA, datas);
                PlayMovieActivity.intentMe(activity, false);
            }
        });


        //1.37打开一个第三方网页 openThirdWeb
        webView.registerHandler("openThirdWeb", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String title = jsonObject.optString("title");
                    final String url = jsonObject.optString("url");
                    WebActivity.intentMe(activity, title, url);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        //1.38跳转小说 toRead
        webView.registerHandler("toRead", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {

                if (!NetworkUtils.isConnected(activity)) {
                    Toast.makeText(activity, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String url = jsonObject.optString("url");

                    if (!LoginEntity.getIsLogin()) {
                        Intent intent = new Intent(activity, GuideLoginActivity.class);
                        activity.startActivity(intent);
                        return;
                    }

                    url = url + "?user_token=" + LoginEntity.getUserToken() + "&source_style=7&is_first=1";

//                    WebActivity.intentMe(activity , "");
//                    WebBookActivity.intentMe(activity, url);

                    BridgeWebViewActivity.intentMe(activity, url, true, true);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        //1.39下载app downloadApp
        webView.registerHandler("downloadApp", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String pkgName = jsonObject.optString("pkgName");
                    final String url = jsonObject.optString("url");
                    final int downloadType = jsonObject.optInt("downloadType", 0);

                    if (!ClickUtil.isNot10Click()) {
                        return;
                    }

                    if (Utils.isAPKInstalled(activity, pkgName)) {
                        Toast.makeText(activity, "已安装，无需重复下载", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!NetworkUtils.isConnected(activity)) {
                        Toast.makeText(activity, "无网络连接", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //浏览器下载
                    if (downloadType == 1) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        activity.startActivity(intent);
                    }
                    //自己下载
                    else {
                        Toast.makeText(activity, "正在下载", Toast.LENGTH_SHORT).show();
                        downloadFunction = function;
                        download(url);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        //1.40关闭页面
        webView.registerHandler("closePage", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                activity.finish();
            }

        });

        //1.41存一个字符串 saveDataByWeb
        webView.registerHandler("saveDataByWeb", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                book().write(PagerCons.KEY_SAVEDATABYWEB, datas);
            }

        });

        //1.42取一个字符串 loadDataByWeb
        webView.registerHandler("loadDataByWeb", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                String data = book().read(PagerCons.KEY_SAVEDATABYWEB);
                if (!TextUtils.isEmpty(data)) {
                    function.onCallBack(data);
                } else {
                    function.onCallBack("[]");
                }

            }

        });


        //1.43文章id跳转至文章详情articleToDetail
        webView.registerHandler("articleToDetail", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String id = jsonObject.optString("id");

                    Intent intent = new Intent(activity, HomeDetailActivity.class);
                    intent.putExtra("id", id);
                    activity.startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

        //1.44存一个字符串 saveDataByRead
        webView.registerHandler("saveDataByRead", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String key = jsonObject.optString("key");
                    String value = jsonObject.optString("value");

                    book().write("webread_" + key, value);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }

            }

        });

        //1.45取一个字符串 loadDataByRead
        webView.registerHandler("loadDataByRead", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String key = jsonObject.optString("key");

                    String value = book().read("webread_" + key);
                    if (value == null) {
                        function.onCallBack("0");
                    } else {
                        function.onCallBack(book().read("webread_" + key));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }

            }

        });

        //1.46更新invite_uid数据 updateInviteUid
        webView.registerHandler("updateInviteUid", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String invite_uid = jsonObject.optString("inviter_uid");


                    LoginEntity loginEntity = book().read(PagerCons.USER_DATA);

                    if (loginEntity != null) {

                        LoginEntity.DataBean dataBean = loginEntity.getData();
                        dataBean.setInviter_uid(invite_uid);

                        loginEntity.setData(dataBean);

                        book().write(PagerCons.USER_DATA, loginEntity);
                    } else {
                        activity.startActivity(new Intent(activity, GuideLoginActivity.class));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }

            }

        });


        //1.47检测是否安装apk isInstallAPK
        webView.registerHandler("isInstallAPK", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String pkgName = jsonObject.optString("pkgName");

                    if (Utils.isAPKInstalled(activity, pkgName)) {
                        jsonObject.put("install", 1);
                    } else {
                        jsonObject.put("install", 0);
                    }

                    function.onCallBack(jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }

            }

        });

        //1.48点击打开apk openAPK
        webView.registerHandler("openAPK", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String pkgName = jsonObject.optString("pkgName");

                    PackageManager packageManager = activity.getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage(pkgName);
                    activity.startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }

            }

        });

        //1.49上传文件方法uploadFile
        webView.registerHandler("uploadFile", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    uploadFunction = function;
                    Log.v("uploadFunction", "start:" + uploadFunction);
                    JSONObject jsonObject = new JSONObject(datas);
                    String type = jsonObject.optString("type");
                    int maxlength = jsonObject.optInt("maxlength");
                    activityId = jsonObject.optInt("activityId");
                    if ("video".equals(type)) {
                        Intent intent = new Intent();
                        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        activity.startActivityForResult(intent, UPLOAD_VIDEO);
                    } else if ("image".equals(type)) {
                        Intent intent = new Intent();
                        intent.setType("image/*"); //选择图片
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        activity.startActivityForResult(intent, UPLOAD_IMAGE);
                    } else {
                        Intent intent = new Intent();
                        intent.setType("audio/*"); //选择音频
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        activity.startActivityForResult(intent, UPLOAD_AUDIO);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        //1.50播放选中视频
        webView.registerHandler("playSelectVideo", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String url = jsonObject.optString("url");
                    String id = jsonObject.optString("id");
                    Log.v("compressTactics", "playurl:" + url);
                    PlayLocalVideoActivity.start(activity, url);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }

            }

        });

        //1.60 存一个数据，退出app重新进入就会清除
        webView.registerHandler("setTag", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String webViewTag = jsonObject.optString("tag");
                    MyApplication.WebViewTag = webViewTag;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //1.61 取一个数据，退出app重新进入就会清除
        webView.registerHandler("getTag", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    jsonObject.put("tag", MyApplication.WebViewTag);
                    function.onCallBack(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //1.70 升级接口
        webView.registerHandler("update", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    Map<String, String> param = new HashMap<>();
                    param.put("type", "temp");
                    rx.Observable<ShareDomainEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                            getDomainApi(HttpRequestBody.generateRequestQuery(param)).map((new HttpResultFunc<ShareDomainEntity>()));
                    RetrofitManager.getInstance(activity).toSubscribe(observable, new ProgressSubscriber<>(new SubscriberOnResponseListenter<ShareDomainEntity>() {
                        @Override
                        public void next(ShareDomainEntity entity) {
                            UpAppUtil.checkVersion(activity, entity.getData().getDomain());
                        }

                        @Override
                        public void error(String target, Throwable e, String errResponse) {

                        }
                    }, activity, false));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // 1.71 看视频广告赚金币
        webView.registerHandler("playAdVideoInTask", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {

                    PlayRewardVideoAdActicity.intentMe(activity, PlayRewardVideoAdActicity.WATCHAD);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //1.80 获取UUID
        webView.registerHandler("getUUID", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("uuid", TextUtils.isEmpty(DeviceUtil.getUUID(activity)) ? "" : DeviceUtil.getUUID(activity));
                    function.onCallBack(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //1.90 返回版本号 versionInfo
        webView.registerHandler("versionInfo", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("versionName", Utils.getAppVersionName(activity));
                    jsonObject.put("uuid", TextUtils.isEmpty(DeviceUtil.getUUID(activity)) ? "" : DeviceUtil.getUUID(activity));

                    function.onCallBack(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //1.100 影视详情提醒返回下拉刷新alertForBackPullRefresh
        webView.registerHandler("alertForBackPullRefresh", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_ALERTPULLRERESH));
            }
        });

        //2.00 保存用户交易申请状态
        webView.registerHandler("uploadTradeStatus", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    int tradeStatus = jsonObject.optInt("tradeStatus");
                    LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                    loginEntity.getData().setTrade_status(tradeStatus);
                    Paper.book().write(PagerCons.USER_DATA, loginEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //2.1 检测是否当天第二次播放
        webView.registerHandler("isTwicePlayMovieOneDay", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {

                    JSONObject jsonObject = new JSONObject();

                    //每天第二次观看需要分享才可以正常观看
                    int times = book().read(PagerCons.PLAY_MOVIE_TIMES, 0);
                    int needShareCount = book().read(PagerCons.KEY_MOVIE_PLAY_COUNR_NEED_SHARE, 0);
                    if (needShareCount != 0 && times == needShareCount) {
                        jsonObject.put("isTwice", 1);
                    } else {
                        jsonObject.put("isTwice", 0);
                    }
                    function.onCallBack(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //2.2 保存base64图片至本地，用户在播放器页面进行分享
        webView.registerHandler("saveMovieSharePic", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {

                    JSONObject jsonObject = new JSONObject(datas);

                    String url = jsonObject.optString("url");
                    String title = jsonObject.optString("title");
                    String description = jsonObject.optString("description");
                    String thumb = jsonObject.optString("thumb");

                    //platform_type:wx,qq,wb,pyq,sfsq
                    String platform_type = jsonObject.optString("platform_type");
                    String type = jsonObject.optString("type");
                    String pictures = jsonObject.optString("pictures");
                    int isPictrueBybase64 = jsonObject.optInt("isPictrueBybase64");


                    String path = jsonObject.optString("path");

                    String id = jsonObject.optString("id");
                    //分享文案  title + url + describe
                    String shareContent = jsonObject.optString("shareContent");

                    //1为走路赚钱的分享
                    int isWalk = jsonObject.optInt("isWalk", 0);

                    ShareInfo shareInfo = new ShareInfo();

                    shareInfo.setUrl(url);
                    shareInfo.setTitle(title);
                    shareInfo.setDescription(description);
                    shareInfo.setThumb(thumb);
                    shareInfo.setBigPic(pictures);
                    shareInfo.setShareContent(shareContent);
                    shareInfo.setIsPictrueBybase64(isPictrueBybase64);

                    shareInfo.setId(id);
                    shareInfo.setUrl_type(type);
                    shareInfo.setUrl_path(path);
                    shareInfo.setPlatform_type(platform_type);

                    shareInfo.setIsWalk(isWalk);

                    Paper.book().write(PagerCons.PLAY_MOVIE_SHARE_INFO, shareInfo);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //2.3 h5友盟事件埋点
        webView.registerHandler("UMengEvent", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String event = jsonObject.optString("event");
                    int value = jsonObject.optInt("value");
                    if (value == 0) {
                        MobclickAgent.onEvent(MyApplication.getContext(), event);
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("onLine", value);
                        MobclickAgent.onEventObject(MyApplication.getContext(), event, map);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        //2.4 跳转微信小程序
        webView.registerHandler("ToWXMiniProgram", new BridgeHandler() {

            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    if (!StringUtils.isWxInstall(activity)) {
                        ToastUtils.getInstance().show(activity, "您未安装微信");
                        return;
                    }

                    JSONObject jsonObject = new JSONObject(datas);
                    String path = jsonObject.optString("path");
                    String smallId = jsonObject.optString("small_id");
                    String appId = jsonObject.optString("appid");
                    String miniProgramType = jsonObject.optString("miniProgramType");
                    new WXLaunchMiniUtil(path, miniProgramType, appId, smallId).sendReq();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //2.5直播暗加载网页并拿取数据
        webView.registerHandler("getWebLiveVideoInfo", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                crawlFunction = function;


//                function.onCallBack("{}");
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String url = jsonObject.optString("url");
                    final String js = jsonObject.optString("js");

                    webViewUtilsListener.getWebLiveInfo(url, js);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //2.6直播拉起顶部原生直播播放
        webView.registerHandler("playTopLiveVideo", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                crawlFunction = function;


//                function.onCallBack("{}");
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String url = jsonObject.optString("url");
                    final String title = jsonObject.optString("title");
                    final int live = jsonObject.optInt("live", 1);

                    if (liveListener != null) {
                        liveListener.play(title, url, live);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //2.7跳转个人主页
        webView.registerHandler("goToUserInfo", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String invitation = jsonObject.optString("invitation");
                    UserInfoActivity.intentMe(invitation, 1, activity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //2.8跳转隐私设置
        webView.registerHandler("goToPrivacySetting", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    PrivacyActivity.intentMe(activity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // 2.7 展示分享分享教程（）
        webView.registerHandler("showCourse", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {

                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String url = jsonObject.optString("url");
                    BridgeWebViewActivity.intentMe(activity, url);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // 2.10 被挤下线后登录
        webView.registerHandler("login705", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                EventBus.getDefault().postSticky(new EventBusOffLineEntity(705, "您的账号在其他设备登录，如果这不是您的操作，请及时修改您的登录密码。"));
            }
        });

        // 2.11 提供观影历史
        webView.registerHandler("viewingHistory", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    ArrayList<BeanMovieDetail> saves = book().read(PagerCons.KEY_MOVIEHISTORY_SAVE2);
                    if (saves == null) {
                        saves = new ArrayList<>();
                    }

                    function.onCallBack(JSON.toJSONString(saves));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 2.11 删除观影历史条目
        webView.registerHandler("deleteViewingHistory", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {

                    ArrayList<BeanMovieDetail> saves = book().read(PagerCons.KEY_MOVIEHISTORY_SAVE2);
                    if (saves == null) {
                        saves = new ArrayList<>();
                    }

                    JSONArray jsonArray = new JSONArray(datas);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String deleteOrigin = jsonArray.getString(i);

                        Iterator<BeanMovieDetail> it = saves.iterator();
                        while (it.hasNext()) {
                            String x = it.next().getOrigin();
                            if (x.equals(deleteOrigin)) {
                                it.remove();
                            }
                        }


                    }

//                    for(BeanMovieDetail beanMovieDetail : saves){
//                        if( beanMovieDetail.getOrigin().equals(datas)){
//                            saves.remove(beanMovieDetail);
//                            break;
//                        }
//                    }
                    book().write(PagerCons.KEY_MOVIEHISTORY_SAVE2, saves);
//                    function.onCallBack(JSON.toJSONString(saves));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        // 2.12 跳转原生界面
        webView.registerHandler("toNativePage", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    final String type = jsonObject.optString("type");
                    //邀请好友
                    if (type.equals("inviteFriend")) {
                        InviteFriendActivity.intentMe(activity);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        // 2.13 更新用户信息
        webView.registerHandler("updateUserInfo", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {

                    LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                    if (loginEntity == null) {
                        return;
                    }

                    JSONObject jsonObject = new JSONObject(datas);
                    String avatar = jsonObject.optString("avatar");

                    if (TextUtils.isEmpty(avatar)) {
                        loginEntity.getData().setAvatar(avatar);
                    }

                    Paper.book().write(PagerCons.USER_DATA, loginEntity);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        // 2.14 观影历史跳转播放详情
        webView.registerHandler("toMovieDetail", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(datas);

                    BeanMovieDetail beanMovieDetail = JSON.parseObject(jsonObject.toString(), BeanMovieDetail.class);

                    MovieDetailActivity.intentMe(activity, beanMovieDetail.getOrigin(), beanMovieDetail);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 2.15 大转盘奖励弹框
        webView.registerHandler("showTurnRewardDialog", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                playTimeFunction = function;
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    int type = jsonObject.optInt("type");
                    int coin = jsonObject.optInt("coin");
                    int vcoin = jsonObject.optInt("vcoin");
                    int is_open_ad = jsonObject.optInt("is_open_ad");
                    int alertType = jsonObject.optInt("alertType");
                    RaffleEntity rewardEntity = new RaffleEntity();
                    rewardEntity.setCoin(coin);
                    rewardEntity.setType(type);
                    rewardEntity.setIs_open_ad(is_open_ad);
                    rewardEntity.setVcoin(vcoin);
                    rewardEntity.setAlertType(alertType);
                    showTurnRewardDialog(rewardEntity);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        // 分享小程序
        webView.registerHandler("shareByMiniProgram", new BridgeHandler() {
            @Override
            public void handler(String datas, CallBackFunction function) {
                playTimeFunction = function;
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    String thumb = jsonObject.optString("thumb");
                    String title = jsonObject.optString("title");
                    String description = jsonObject.optString("description");
                    String mini_program_path = jsonObject.optString("mini_program_path");
                    String mini_program_id = jsonObject.optString("mini_program_id");
                    int mode = jsonObject.optInt("mode");  // 0线上  1测试


                    UMImage umImage = new UMImage(activity, thumb);

                    ShareUtils.shareToWXMiniProgram(
                            activity,
                            ShareConfigEntity.getDownLoadUrl(),
                            umImage,
                            title,
                            description,
                            mini_program_path + "&uid=" + LoginEntity.getUid()
                            ,
                            mini_program_id,
                            SHARE_MEDIA.WEIXIN,
                            mode
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void showTurnRewardDialog(RaffleEntity entity) {

        RafflePop acticleRewardPop = new RafflePop(activity);
        acticleRewardPop.setView(entity);
        acticleRewardPop.setOnClickListener(new RafflePop.OnClickListener() {
            @Override
            public void onclick(View view, int type) {
                if (type == 0) {
                    //看广告
                    PlayRewardVideoAdActicity.intentMe(activity, PlayRewardVideoAdActicity.BIGTURNTAB);

                } else if (type == 1) {
                    //不看广告直接领取
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("coin", 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (playTimeFunction != null) {
                        playTimeFunction.onCallBack(jsonObject.toString());
                    }
                } else {
                    //领取成功
                    BridgeWebViewActivity.intentMe(activity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TURN_RECORD);
                }

                acticleRewardPop.dismiss();
            }
        });
        acticleRewardPop.showPopupWindow();
    }

    //分享回调
    public void shareCallBack() {
        if (shareFunction != null) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 0);
                shareFunction.onCallBack(jsonObject.toString());
                shareFunction = null;
            } catch (Exception e) {
            }


        }
    }

    //爬取回调
    public synchronized void crawlCallback(String json) {
        if (crawlFunction != null) {
            crawlFunction.onCallBack(json);
//            crawlFunction = null;
        }
    }


    //显示奖励框
    public void showReward(String gold, String title, String detail) {
        if (goldDialog == null) {
            goldDialog = new GoldDialog(activity);
        }
        goldDialog.show(gold, title, detail);
    }

    public void onResume() {
        try {
            if (reloadFunction != null) {
                //给 h5 一个刷新的动作
                reloadFunction.onCallBack("回调");
            }

            if (webView != null) {
                webView.loadUrl("javascript:window.showAppWebPage()");
            }
        } catch (Exception e) {

        }

    }

    public void onPause() {
        if (webView != null) {
            webView.loadUrl("javascript:window.hideAppWebPage()");
        }
    }


    private class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loading = true;
            if (rl_loading != null) {
                rl_loading.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
            // super.onReceivedSslError(view, handler, error);
            // 接受所有网站的证书，忽略SSL错误，执行访问网页
            handler.proceed();
        }
    }

    public void onDestory() {

        try {
            logonBackFunction = null;
            downloadFunction = null;
            authorizeFunction = null;
            shareFunction = null;
            playTimeFunction = null;
            crawlFunction = null;
            reloadFunction = null;
            playTimeFunction = null;

            if (goldDialog != null) {
                goldDialog.destory();
                goldDialog = null;
            }

            if (mDownloadBroadcast != null) {
                activity.unregisterReceiver(mDownloadBroadcast);
            }
            uploadFunction = null;
        } catch (Exception e) {

        }

    }

    //开宝箱
    public void openBox(String url, String coin, String share_friend, String inviting_friend) {
        if (fragment != null) {
            fragment.showInvitationBox(url, coin, share_friend, inviting_friend);
        }
    }


    //启动下载
    public void download(String url) {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(activity, "SD卡不存在", Toast.LENGTH_SHORT).show();
            return;
        }
//
//        mFile = new File(Environment.getExternalStorageDirectory() + "/jjb/download/" + System.currentTimeMillis() + ".apk");
//        if (!mFile.exists()) {
//            mFile.mkdirs();
//        }
//        String fileName = mFile.getAbsolutePath();

        String apkName = System.currentTimeMillis() + ".apk";
        String path = "/jjb/download/";
        fileName = Environment.getExternalStorageDirectory() + path + apkName;

        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 通知栏的下载通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.setMimeType("application/vnd.android.package-archive");

        File file = new File(fileName);
//        if (file.exists()) {
//            file.delete();
//        }
//        request.setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setDestinationInExternalPublicDir(path, apkName);
        long downloadId = downloadManager.enqueue(request);
        //文件下载完成会发送完成广播，可注册广播进行监听
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        intentFilter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        mDownloadBroadcast = new DownloadBroadcast(activity, file);
        activity.registerReceiver(mDownloadBroadcast, intentFilter);

    }

    //下载监听
    BroadcastReceiver mDownloadBroadcast;
    private File mFile;
    private String fileName;

    private static class DownloadBroadcast extends BroadcastReceiver {
        private Activity activity;
        private final File mFile;

        public DownloadBroadcast(Activity activity, File file) {
            this.activity = activity;
            mFile = file;

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
//                String apkPath = mFile.getAbsolutePath();
                Utils.installAPK(activity, mFile.getAbsolutePath());
//                Intent intent1 = new Intent(Intent.ACTION_VIEW);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                    intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    Uri uri1 = FileProvider.getUriForFile(context, "com.juejinchain.android.fileProvider", mFile);
//                    intent1.setDataAndType(uri1, "application/vnd.android.package-archive");
//                } else {
//                    intent1.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
//                }
//
//                if(downloadFunction != null){
//                    downloadFunction.onCallBack("下载成功");
//                }
//                try {
//                    activity.startActivity(intent1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //友盟授权
//        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //登录回调
            case WebViewUtils.RESQUESTCODE_LOGIN_JS_BACK:
                if (resultCode == RESULT_OK) {
                    LoginEntity loginEntity = book().read(PagerCons.USER_DATA);

                    if (loginEntity != null && loginEntity.isLogin()) {

                        if (logonBackFunction != null) {
                            String loginStr = JSON.toJSONString(loginEntity);
                            if (logonBackFunction != null) {
                                logonBackFunction.onCallBack(loginStr);
                            }
                            logonBackFunction = null;
                        }
                    } else {
                        if (logonBackFunction != null) {
                            JSONObject json = new JSONObject();
                            try {
                                json.put("code", 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (logonBackFunction != null) {
                                logonBackFunction.onCallBack(json.toString());
                            }
                            logonBackFunction = null;
                        }
                    }
                }
                break;
            //激励视频回调（大转盘）
            case PlayRewardVideoAdActicity.BIGTURNTAB:
                if (resultCode == RESULT_OK) {
                    //请求大转盘奖励
                    rx.Observable<BigTurnTabRewardEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).bigTurnTab(new HashMap<>()).map((new HttpResultFunc<BigTurnTabRewardEntity>()));
                    Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BigTurnTabRewardEntity>() {
                        @Override
                        public void next(BigTurnTabRewardEntity bigTurnTabRewardEntity) {
                            if (playTimeFunction != null) {

                                if (bigTurnTabRewardEntity.getCode() == 0) {
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("coin", bigTurnTabRewardEntity.getData().getCoin());
                                        playTimeFunction.onCallBack(jsonObject.toString());


                                        showTurnRewardDialog(new RaffleEntity(1, 0, 0, 0, 2));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("coin", 0);
                                        playTimeFunction.onCallBack(jsonObject.toString());

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    MyToast.show(activity, bigTurnTabRewardEntity.getMsg());
                                }
                            }
                            playTimeFunction = null;
                        }

                        @Override
                        public void error(String target, Throwable e, String errResponse) {
                            playTimeFunction = null;
                            Log.e("zy", "bindThird error=====++++e=" + e.toString());
                        }
                    }, activity, false);
                    RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);

                }
                break;
            //激励视频回调（走路赚钱）
            case PlayRewardVideoAdActicity.TOTHIS:

//                try {
//                    long startTime = book().read(PagerCons.PLAYSTART, System.currentTimeMillis());
//                    long seconds = (System.currentTimeMillis() - startTime) / 1000L;
//
//                    long videoTime = book().read(PagerCons.PLAYCURRENT, System.currentTimeMillis());
//                    long videoSeconds = videoTime / 1000L;
//                    Log.e("zy" , "播放时长 = " + seconds + "");
////                    Toast.makeText(activity, "播放时长 = " + seconds + "", Toast.LENGTH_SHORT).show();
//                    Log.e("zy" , "视频时长 = " + videoSeconds + "");
////                    if(videoSeconds)
//
//                    //符合观看时长
//
//                    if (playTimeFunction != null) {
//                        JSONObject json = new JSONObject();
//                        try {
//                            if( (seconds + 1) > videoSeconds) {
//                                json.put("state", 1);
//                            }else {
//                                json.put("state", 0);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        playTimeFunction.onCallBack(json.toString());
//                        playTimeFunction = null;
//                    }
//                }catch (Exception e){}


                JSONObject json = new JSONObject();
                try {
                    if (resultCode == RESULT_OK) {
                        json.put("state", 1);
                    } else {
                        json.put("state", 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (playTimeFunction != null) {
                    playTimeFunction.onCallBack(json.toString());
                }
                playTimeFunction = null;


                break;
            case UPLOAD_VIDEO:
                if (data != null && data.getData() != null) {
                    String inputPath = FileUtil.getRealPathFromUri(activity, data.getData());
                    VideoCompressHelperNew.getInstance(activity).compress(activityId + "", inputPath, new VideoCompressHelperNew.VideoCompressUploadCallback() {
                        @Override
                        public void success(String uploadUrl, String localUrl) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.getInstance().show(activity, "上传成功");
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("code", 0);
                                        jsonObject.put("url", uploadUrl);
                                        jsonObject.put("localUrl", localUrl);
                                        if (uploadFunction != null) {
                                            uploadFunction.onCallBack(jsonObject.toString());
                                        }

                                        uploadFunction = null;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        @Override
                        public void fail(String msg) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("code", 1);
                                        jsonObject.put("msg", msg);
                                        if (uploadFunction != null) {
                                            WebViewUtils.this.uploadFunction.onCallBack(jsonObject.toString());
                                        }
                                        WebViewUtils.this.uploadFunction = null;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
                break;
            case UPLOAD_IMAGE:
                if (data != null && data.getData() != null) {
                    String inputPath = FileUtil.getRealPathFromUri(activity, data.getData());
                    if (TextUtils.isEmpty(inputPath)) {
                        ToastUtils.getInstance().show(activity, "获取文件路径失败");
                        return;
                    }
                    BitmapCompressHelper.getInstance(activity).compress(activityId + "", inputPath, new BitmapCompressHelper.VideoCompressUploadCallback() {
                        @Override
                        public void success(String uploadUrl, String localUrl) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.getInstance().show(activity, "上传成功");
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("code", 0);
                                        jsonObject.put("url", uploadUrl);
                                        jsonObject.put("localUrl", localUrl);
                                        if (uploadFunction != null) {
                                            uploadFunction.onCallBack(jsonObject.toString());
                                        }
                                        uploadFunction = null;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        @Override
                        public void fail(String msg) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("code", 1);
                                        jsonObject.put("msg", msg);
                                        if (uploadFunction != null) {
                                            WebViewUtils.this.uploadFunction.onCallBack(jsonObject.toString());
                                        }
                                        WebViewUtils.this.uploadFunction = null;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
                break;
            case UPLOAD_AUDIO:
                if (data != null && data.getData() != null) {
                    String inputPath = FileUtil.getRealPathFromUri(activity, data.getData());
                    AudioCompressHelper.getInstance(activity).compress(activityId, inputPath, new AudioCompressHelper.AudioCompressUploadCallback() {
                        @Override
                        public void success(String uploadUrl, String localUrl) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.getInstance().show(activity, "上传成功");
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("code", 0);
                                        jsonObject.put("url", uploadUrl);
                                        jsonObject.put("localUrl", localUrl);
                                        Log.v("audioUrl", "url：" + uploadUrl + "， localUrl：" + localUrl);
                                        if (uploadFunction != null) {
                                            uploadFunction.onCallBack(jsonObject.toString());
                                        }

                                        uploadFunction = null;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        @Override
                        public void fail(String msg) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("code", 1);
                                        jsonObject.put("msg", msg);
                                        if (uploadFunction != null) {
                                            WebViewUtils.this.uploadFunction.onCallBack(jsonObject.toString());
                                        }
                                        WebViewUtils.this.uploadFunction = null;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
                break;
        }
    }


    public interface WebViewUtilsListener {
        void logout();

        void showRightBottomAD(String imgUrl, String link);

        //影视搜索业务，加载url，注入并获取alert数据
        void getWebMovieInfo(String url);

        //直播业务业务，加载url，注入js
        void getWebLiveInfo(String url, String js);
    }

    //    //第三方授权
//    private void authorization(SHARE_MEDIA share_media) {
//        UMShareAPI.get(activity).getPlatformInfo(activity, share_media, new UMAuthListener() {
//            @Override
//            public void onStart(SHARE_MEDIA share_media) {
//
//                Log.e("TAG", "onStart " + "授权开始");
//            }
//
//            @Override
//            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                Log.e("TAG", "onStart =============》》》》》》》" + "授权完成");
//                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
//                String uid = map.get("uid");
//                String openid = map.get("openid");//微博没有
//                String unionid = map.get("unionid");//微博没有
//                String access_token = map.get("access_token");
//                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
//                String expires_in = map.get("expires_in");
//                String name = map.get("name");
//                String gender = map.get("gender");
//                String iconurl = map.get("iconurl");
//                Log.e("TAG", "onComplete: 打印第三方获取的参数=======>>>>>" + "name=" + name
//                        + "uid=" + uid
//                        + "openid=" + openid
//                        + "unionid =" + unionid
//                        + "access_token =" + access_token
//                        + "refresh_token=" + refresh_token
//                        + "expires_in=" + expires_in
//                        + "gender=" + gender
//                        + "iconurl=" + iconurl);
//
//                //授权成功
//                if (authorizeFunction != null) {
//                    authorizeFunction.onCallBack(openid);
//                }
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//                if (share_media.equals(SHARE_MEDIA.WEIXIN)) {
//
//                    ToastUtils.instance.show(activity.getApplicationContext(), "未安装微信客户端，请选择其他登录方式！");
//                } else if (share_media.equals(SHARE_MEDIA.QQ)) {
//                    ToastUtils.instance.show(activity.getApplicationContext(), "未安装QQ客户端，请选择其他登录方式！");
//                }
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA share_media, int i) {
//                Log.d("TAG", "onCancel " + "授权取消");
//            }
//        });
//    }
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(activity).getPlatformInfo(activity, share_media, umAuthListener);
    }

    /**
     * 授权监听
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            Log.e("TAG", "onStart =============》》》》》》》" + "授权开始的回调");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int action, Map<String, String> map) {

            Log.e("TAG", "onComplete =============》》》》》》》" + "授权完成");
            //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
            String uid = map.get("uid");
            String openid = map.get("openid");//微博没有
            String unionid = map.get("unionid");//微博没有
            String access_token = map.get("access_token");
            String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
            String expires_in = map.get("expires_in");
            String name = map.get("name");
            String gender = map.get("gender");
            String iconurl = map.get("iconurl");
            Log.e("TAG", "onComplete: 打印第三方获取的参数=======>>>>>" + "name=" + name
                    + "uid=" + uid
                    + "openid=" + openid
                    + "unionid =" + unionid
                    + "access_token =" + access_token
                    + "refresh_token=" + refresh_token
                    + "expires_in=" + expires_in
                    + "gender=" + gender
                    + "iconurl=" + iconurl);


            //绑定第三方接口
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("access_token", access_token);
            paramMap.put("openid", openid);
            if (share_media.equals(SHARE_MEDIA.WEIXIN)) {
                paramMap.put("platform", "wechat");

                rx.Observable<BindThirdEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).bindWX(paramMap).map((new HttpResultFunc<BindThirdEntity>()));
                Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BindThirdEntity>() {
                    @Override
                    public void next(BindThirdEntity testBean) {
                        //关联成功
                        if (testBean.getCode() == 0) {
                            if (authorizeFunction != null) {
//                                authorizeFunction.onCallBack("");
                                //显示奖励框
//                            if(goldDialog == null) {
//                                goldDialog = new GoldDialog(activity);
//                            }
//                            goldDialog.show(testBean.getData().getCoin(), "奖励到账", "阅读赚车赚房");
                                showReward(Utils.FormatGold(testBean.getData().getCoin()), "奖励到账", "认证微信成功");
                                if (authorizeFunction != null) {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            authorizeFunction.onCallBack("{}");

                                        }
                                    });

                                }
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle(testBean.getMsg());
                            //点击对话框以外的区域是否让对话框消失
                            builder.setCancelable(true);
                            //设置正面按钮
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();


//                            Toast.makeText(activity, testBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void error(String target, Throwable e, String errResponse) {
                        Log.e("zy", "bindThird error=====++++e=" + e.toString());
//                baseView.error(errResponse);
                    }
                }, activity, false);
                RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);


            } else if (share_media.equals(SHARE_MEDIA.QQ)) {
                paramMap.put("platform", "qq");

                rx.Observable<BindThirdEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).bindThird(paramMap).map((new HttpResultFunc<BindThirdEntity>()));
                Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BindThirdEntity>() {
                    @Override
                    public void next(BindThirdEntity testBean) {
                        //关联成功
                        if (testBean.getCode() == 0) {

                            if (!TextUtils.isEmpty(testBean.getData().getUser_token())) {
                                LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                                loginEntity.getData().setUser_token(testBean.getData().getUser_token());
                                Paper.book().write(PagerCons.USER_DATA, loginEntity);
                            }
                            if (authorizeFunction != null) {
//                                authorizeFunction.onCallBack("");
                                //显示奖励框
//                            if(goldDialog == null) {
//                                goldDialog = new GoldDialog(activity);
//                            }
//                            goldDialog.show(testBean.getData().getCoin(), "奖励到账", "阅读赚车赚房");
                                showReward(Utils.FormatGold(testBean.getData().getCoin()), "奖励到账", "关联QQ成功");
                                if (authorizeFunction != null) {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            authorizeFunction.onCallBack("{}");

                                        }
                                    });

                                }

                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle(testBean.getMsg());
                            //点击对话框以外的区域是否让对话框消失
                            builder.setCancelable(true);
                            //设置正面按钮
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();


//                            Toast.makeText(activity, testBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void error(String target, Throwable e, String errResponse) {
                        Log.e("zy", "bindThird error=====++++e=" + e.toString());
//                baseView.error(errResponse);
                    }
                }, activity, false);
                RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);

            } else {
                return;
            }


            //直接取消授权
            UMShareAPI.get(activity).deleteOauth(activity, share_media, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {

                }
            });

//            if(authorizeFunction != null){
//                authorizeFunction.onCallBack("");
//                //显示奖励框
//                if (goldDialog == null) {
//                    goldDialog = new GoldDialog(activity);
//                }
//                goldDialog.show();
//            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Log.e("TAG", "onError =============》》》》》》》" + "onError");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Log.e("TAG", "onCancel =============》》》》》》》" + "onCancel");
//            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    public void showRewardPop(double coin) {
        RewardEntity rewardEntity = new RewardEntity();
        rewardEntity.setCoin(coin);
        rewardEntity.setRewardType("签到");
        rewardEntity.setTitle("签到成功");

        RewardMoreCoinPop acticleRewardPop = new RewardMoreCoinPop(activity);
        acticleRewardPop.setView(rewardEntity);
        acticleRewardPop.setOnClickListener(new RewardMoreCoinPop.OnClickListener() {
            @Override
            public void onclick(View view) {
                activity.startActivity(new Intent(activity, PlayRewardVideoAdActicity.class));
            }
        });
        acticleRewardPop.showPopupWindow();
    }


    public void showForceShareDialog() {
        ForceShareByPlayMovieDialog dialog = new ForceShareByPlayMovieDialog(activity);
        dialog.setClickListener(new CustomItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

            }
        });
        dialog.show();
    }

    //直播监听
    public static interface LiveListener {
        public void play(String title, String url, int live);
    }

    private LiveListener liveListener;

    public void setLiveListener(LiveListener liveListener) {
        this.liveListener = liveListener;
    }

}

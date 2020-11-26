package com.newsuper.t.juejinbao.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;


import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityShenmasearchBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.ShenmaSearchImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.NetUtils;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;


/**
 * 神马搜索结果页
 */
public class ShenmaSearchActivity extends BaseActivity<ShenmaSearchImpl, ActivityShenmasearchBinding> implements ShenmaSearchImpl.MvpView {

    private WebView X5webView;


    private String title;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shenmasearch;
    }

    @Override
    public void initView() {

//        mViewBinding.loading.showLoading();
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        Log.i("zzz", "initView: " + url);


        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        X5webView = new WebView(mActivity.getApplicationContext());
        X5webView.setLayoutParams(params);
        mViewBinding.llContain.addView(X5webView);

        WebSettings websettings = X5webView.getSettings();


        websettings.setDomStorageEnabled(true);

        //与js弹框交互
        websettings.setJavaScriptEnabled(true);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
        websettings.setMediaPlaybackRequiresUserGesture(false);
        websettings.setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            X5webView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
        }

        X5webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mViewBinding.progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    //加载完毕隐藏进度条
                    mViewBinding.progressBar.setVisibility(View.GONE);
                }else{
                    mViewBinding.progressBar.setVisibility(View.VISIBLE);
                }

            }
        });
        X5webView.setWebViewClient(new WebViewClient() {

            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接
                if(url.contains("http://")||url.contains("https://")) {
                    return false;
                }else{
                    return true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                hideSearch();
//                mViewBinding.loading.showContent();
            }
        });



        X5webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //按返回键操作并且能回退网页
                    if (keyCode == KeyEvent.KEYCODE_BACK && X5webView.canGoBack()) {
                        //后退
                        X5webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        //分享
        mViewBinding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginEntity.getIsLogin()) {
                    Intent intent = new Intent(mActivity, GuideLoginActivity.class);
                    mActivity.startActivity(intent);
                    return;
                }

                if(X5webView==null && TextUtils.isEmpty(title)){
                    return;
                }

                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);

                shareInfo.setId(LoginEntity.getUserToken());
                shareInfo.setType("index");
                shareInfo.setTitle(title);

                ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
                mShareDialog.show();
//                HomeSearchShareAndLinkDialog homeSearchShareAndLinkDialog = new HomeSearchShareAndLinkDialog(mActivity, new HomeSearchShareAndLinkDialog.ItemClickListener() {
//                    @Override
//                    public void share() {
//
//
//                    }
//
//                    @Override
//                    public void link() {
//                        ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//                        ClipData myClip = ClipData.newPlainText("text", X5webView.getUrl());
//                        myClipboard.setPrimaryClip(myClip);
//
//                        MyToast.show(mActivity , "复制成功，可以将链接发给好友啦~");
//                    }
//                });
//
//                homeSearchShareAndLinkDialog.show();


            }
        });

        if(NetUtils.isConnected(mActivity)){
            X5webView.loadUrl(url);
        }else {
            mViewBinding.loading.showError("网络出错");
            mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(NetUtils.isConnected(mActivity)){
                        mViewBinding.loading.showContent();
                        X5webView.loadUrl(url);
                    }
                }
            });
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//
//        MobclickAgent.onEvent(mActivity, EventID.SHENMA_CLICK_SEARCH);   //免费专区-分享-埋点
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }

    @Override
    public void initData() {

    }


    public static void intentMe(Context context, String title, String url) {
        if(!ClickUtil.isNotFastClick()){
            return;
        }


//        if (!LoginEntity.getIsLogin()) {
//            Intent intent = new Intent(context, GuideLoginActivity.class);
//            context.startActivity(intent);
//            return;
//        }


        Intent intent = new Intent(context, ShenmaSearchActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }


    @Override
    protected void onDestroy() {

        if (X5webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = X5webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(X5webView);
            }

            X5webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            X5webView.getSettings().setJavaScriptEnabled(false);
            X5webView.clearHistory();
            X5webView.clearView();
            X5webView.removeAllViews();

            try {
                X5webView.destroy();
            } catch (Throwable ex) {

            }

            X5webView = null;

            //清空View资源
            ViewGroup view = (ViewGroup) mActivity.getWindow().getDecorView();
            view.removeAllViews();
        }


        super.onDestroy();
    }



    // 隐藏网页顶部搜索框方法(若提bug可以调用)
    private void hideSearch() {
        try {
            //定义javaScript方法
            String javascript = "javascript:function hideSearch() { "
                    + "document.getElementsByClassName('searchbox-wrap')[0].style.display='none'"
                    + "}";

            //加载方法
            X5webView.loadUrl(javascript);
            //执行方法
            X5webView.loadUrl("javascript:hideSearch();");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;


import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentHomesearchdetailwebBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchDetailEvent;
import com.newsuper.t.juejinbao.ui.home.presenter.HomeSearchDetailWebImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayInputStream;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 全网搜索-搜索结果详情-第三方网页
 */
public class HomeSearchDetailWebFragment extends BaseFragment<HomeSearchDetailWebImpl, FragmentHomesearchdetailwebBinding> implements HomeSearchDetailWebImpl.MvpView {

    //360新闻
    public static final String XW360 = "360";
    //搜狗新闻
    public static final String SGXW = "sgxw";
    //新浪新闻
    public static final String XLXW = "xlxw";

    private WebView X5webView;

    private String type;
    private String kw;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = getArguments().getString("type");
        kw = getArguments().getString("kw");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homesearchdetailweb, container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void initView() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        X5webView = new WebView(mActivity.getApplicationContext());
        X5webView.setLayoutParams(params);
        mViewBinding.llContain.addView(X5webView , 0);

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

        X5webView.setWebChromeClient(new WebChromeClient(){
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
        X5webView.setWebViewClient(new WebViewClient(){
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
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(type.equals(XW360)){

                    if(url.startsWith("https://m.news.so.com/ns?q=")){
                        mViewBinding.ll.setVisibility(View.GONE);
                    }else{
                        mViewBinding.ll.setVisibility(View.VISIBLE);
                    }

                }
                else if(type.equals(SGXW)){
                    if(url.startsWith("https://news.sogou.com/news?query=")){
                        mViewBinding.ll.setVisibility(View.GONE);
                    }else{
                        mViewBinding.ll.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        X5webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    //按返回键操作并且能回退网页
                    if (keyCode == KeyEvent.KEYCODE_BACK && X5webView.canGoBack()) {
                        //后退
                        X5webView.goBack();
                        return true;
                    }
                }
//                else if(event.getAction() == KeyEvent.ACTION_UP){
//                    return true;
//                }
                return false;
            }
        });


        mViewBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!LoginEntity.getIsLogin()) {
                    Intent intent = new Intent(mActivity, GuideLoginActivity.class);
                    startActivity(intent);
                    return;
                }


                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setTitle(X5webView.getTitle().replace("搜狗新闻搜索 - " , ""));

                ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
                mShareDialog.show();
            }
        });


        loadUrl(kw);


    }

    @Override
    public void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void search(HomeSearchDetailEvent homeSearchDetailEvent){
        loadUrl(homeSearchDetailEvent.getKw());
    }

    private void loadUrl(String kw){
        String url = "https://www.baidu.com";
        if(type.equals(XW360)){
//            url = "https://weixin.sogou.com/weixin?query=" + kw + "&_sug_type_=&s_from=input&_sug_=y&type=2&page=1&ie=utf8";
            url = "https://m.news.so.com/ns?q=" + kw + "&src=newhome";

        }
        else if(type.equals(SGXW)){
//            url = "http://news.baidu.com/ns?word=" + kw;
            url = "https://news.sogou.com/news?query=" + kw + "&p=42040301&s_t=1582773181897&ie=utf8";
        }
        else if(type.equals(XLXW)){
            url = "http://search.sina.com.cn/?c=news&from=channel&ie=utf-8&q=" + kw + "&col=&range=&source=&country=&size=&time=&a=&page=1&pf=0&ps=0&dpc=1";
        }
        X5webView.loadUrl(url);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
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


        super.onDestroyView();
    }

}

package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.basepop.blur.thread.ThreadPoolManager;
import com.newsuper.t.juejinbao.ui.WebFragment;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.movie.utils.WebViewUtils;
import com.newsuper.t.juejinbao.utils.NetworkUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebView;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebViewClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class RefreshWebFragment extends WebFragment {
   View view;
//
//
//    protected BridgeWebView webView;
//
//    //加载地址
//    protected String url;
//
//    protected WebViewUtils webViewUtils;
//
//    //分享弹框
//    protected ShareDialog mShareDialog;
//
//    //宝箱弹框
//    protected TreasureBoxDialog treasureBoxDialog;
//
//    protected boolean isLogin = false;
//
    private LinearLayout llError;
    private TextView tv_error_msg;
    private SmartRefreshLayout srl;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(url == null) {
            url = getArguments().getString("url");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_refreshweb, container, false);
        llError = view.findViewById(R.id.ll_error);
        tv_error_msg = view.findViewById(R.id.tv_error_msg);
        srl = view.findViewById(R.id.srl);

        webView = view.findViewById(R.id.webView);
        webViewUtils = new WebViewUtils(null, this, webView, view.findViewById(R.id.rl_loading), new WebViewUtils.WebViewUtilsListener() {
            @Override
            public void logout() {
                Utils.logout(getActivity());
            }

            @Override
            public void showRightBottomAD(String imgUrl, String link) {

            }

            @Override
            public void getWebMovieInfo(String url) {

            }

            @Override
            public void getWebLiveInfo(String url, String js) {

            }
        });
        webView.setWebChromeClient(new MyWebChromeClient());


        Log.e("TAG", "initView:注解状态2=======>>>>>>> " + url);


        if(!NetworkUtils.isConnected(getActivity())){
            llError.setVisibility(View.VISIBLE);
            tv_error_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!NetworkUtils.isConnected(getActivity())){
                        ToastUtils.getInstance().show(getActivity() , "网络未链接");
                    }else{
                        srl.setEnableRefresh(true);
                        llError.setVisibility(View.GONE);
                        webView.loadUrl(url);
                    }
                }
            });
        }else{
            webView.loadUrl(url);
        }


        srl.setEnableLoadMore(false);
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                webView.loadUrl(url);
                srl.finishRefresh(1000);
            }
        });
        webView.setWebViewClient(new MyWebViewClient(webView));
        clearCache();




        return view;
    }


    private class MyWebChromeClient extends WebChromeClient {




        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(getActivity() , message , Toast.LENGTH_SHORT).show();
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onHideCustomView() {



        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

        }

    }

    private class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            srl.setEnableRefresh(false);

            ThreadPoolManager.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);

                        if(getActivity() != null){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(webView != null){
                                        webView.scrollTo(0 , 1);
                                    }
                                }
                            });
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });


        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
            // super.onReceivedSslError(view, handler, error);
            // 接受所有网站的证书，忽略SSL错误，执行访问网页
            handler.proceed();
        }
    }



}

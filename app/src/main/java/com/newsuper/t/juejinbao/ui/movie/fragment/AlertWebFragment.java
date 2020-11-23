package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentAlertwebBinding;
import com.juejinchain.android.jsbridge.BridgeWebView;
import com.juejinchain.android.jsbridge.BridgeWebViewClient;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.movie.activity.BridgeWebViewActivity;
import com.juejinchain.android.module.movie.activity.BridgeWebViewFullActivity;
import com.juejinchain.android.module.movie.vip.AlertWebActivity;
import com.juejinchain.android.module.movie.vip.VipLiveActivity;
import com.ys.network.base.BaseFragment;
import com.ys.network.base.LoginEntity;
import com.ys.network.utils.NetworkUtils;
import com.ys.network.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jzvd.Jzvd;

/**
 * vip - 全网VIP
 */
public class AlertWebFragment extends BaseFragment<PublicPresenterImpl , FragmentAlertwebBinding>  {
    View view;

    private BridgeWebView webView;

    //加载地址
    protected String url;

    private LinearLayout llError;
    private TextView tv_error_msg;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(url == null) {
            url = getArguments().getString("url");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alertweb, container, false);

        llError = view.findViewById(R.id.ll_error);
        tv_error_msg = view.findViewById(R.id.tv_error_msg);

        return view;
    }

    @Override
    public void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new BridgeWebView(getActivity().getApplicationContext());
        webView.setLayoutParams(params);
        mViewBinding.llWebcontain.addView(webView);

        WebSettings websettings = webView.getSettings();
        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        websettings.setLoadsImagesAutomatically(true);
        websettings.setJavaScriptEnabled(true);
        websettings.setUseWideViewPort(true);
        websettings.setSupportMultipleWindows(true);
        websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
        websettings.setAppCacheEnabled(true);
        websettings.setDomStorageEnabled(true);
        websettings.setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
        }

        //与js弹框交互
        websettings.setJavaScriptEnabled(true);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new BridgeWebViewClient(webView));
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                Toast.makeText(AlertWebActivity.this, message, Toast.LENGTH_SHORT).show();
                result.cancel();
                operate(message);
                return super.onJsAlert(view, url, message, result);
            }
        });



    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        if(!NetworkUtils.isConnected(getActivity())){
            llError.setVisibility(View.VISIBLE);
            tv_error_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!NetworkUtils.isConnected(getActivity())){
                        ToastUtils.getInstance().show(getActivity() , "网络未链接");
                    }else{
                        llError.setVisibility(View.GONE);
                        webView.loadUrl(url);
                    }
                }
            });
        }else{
            webView.loadUrl(url);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //释放内存
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }


        super.onDestroy();
    }

    private void operate(String json) {
        try {
            String type = "";
            JSONObject data = null;

            JSONObject jsonObject = new JSONObject(json);
            type = jsonObject.optString("type");
            data = jsonObject.optJSONObject("data");


            //跳转直播详情
            if (type.equals("toLiveDetail")) {

                if (LoginEntity.getIsLogin()) {
                } else {
                    mActivity.startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }

                String src = data.optString("src");
                String picture = data.optString("picture");
                String url = data.optString("url");
                String location = data.optString("type");
//                    startPlay("", src, picture);

                VipLiveActivity.intentMe(mActivity , src , url , picture , location);
            }
            //跳转带标题的bridgewebview
            else if(type.equals("toTitleBridgewebview")){
                if (LoginEntity.getIsLogin()) {;
                } else {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                String title = data.optString("title");
                String url = data.optString("url");

                BridgeWebViewActivity.intentMe(mActivity ,title , url , true);

            }
            else if(type.equals("ToAlertWeb")){
                if (LoginEntity.getIsLogin()) {;
                } else {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                String title = data.optString("title");
                String url = data.optString("url");

                AlertWebActivity.intentMe(mActivity , title , url);

            }
            //跳转到带播放器头的bridgeview
            else if(type.equals("ToVideoTitleBridgewebview")){
                if (LoginEntity.getIsLogin()) {;
                } else {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                String url = data.optString("url");

                BridgeWebViewFullActivity.intentMeForLive(context , url);

            }
            //带标题去loading的bridgeview
            else if(type.equals("toTitleNoLoadingBridgewebview")){
                if (LoginEntity.getIsLogin()) {;
                } else {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                String title = data.optString("title");
                String url = data.optString("url");

                BridgeWebViewActivity.intentMeForHaveTitleAndNoLoading(mActivity , title ,url);

            }


        } catch (JSONException e) {
        }
    }


}

package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
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

import com.alibaba.fastjson.JSON;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentSearchdetailhhmwebBinding;
import com.juejinchain.android.module.movie.activity.MovieSearchMZSMActivity;
import com.juejinchain.android.module.movie.activity.PlayMovieActivity;
import com.juejinchain.android.module.movie.entity.MovieThirdIframeEntity;
import com.juejinchain.android.module.movie.presenter.impl.SearchDetailHHMWebImpl;
import com.juejinchain.android.module.movie.view.MovieWebLoadingDialog;

import com.squareup.otto.Subscribe;
import com.ys.network.base.BaseFragment;
import com.ys.network.base.PagerCons;
import com.ys.network.bus.BusConstant;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import razerdp.blur.thread.ThreadPoolManager;

import static io.paperdb.Paper.book;

//坏坏猫网页tab 废弃
public class SearchDetailHHMWebFragment extends BaseFragment<SearchDetailHHMWebImpl, FragmentSearchdetailhhmwebBinding> implements SearchDetailHHMWebImpl.MvpView {
    private String showKw = "";
    private String kw;
    private String title;
    private String key;
    private String url;

    private WebView X5webView;
    private String loadUrl = "";

    private MovieWebLoadingDialog movieWebLoadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        kw = getArguments().getString("kw");

        kw = getKw(kw);

        key = getArguments().getString("key");
        title = getArguments().getString("title");
        url = getArguments().getString("url");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchdetailhhmweb, container, false);
        return view;
    }

    @Override
    public void initView() {

//        mViewBinding.tvPmd.setSelected(true);
        mViewBinding.jgContent.setSelected(true);

        mViewBinding.rlMzsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieSearchMZSMActivity.intentMe(mActivity);
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        X5webView = new WebView(mActivity.getApplicationContext());
        X5webView.setLayoutParams(params);
        mViewBinding.llWebcontain.addView(X5webView);

        WebSettings websettings = X5webView.getSettings();


        websettings.setDomStorageEnabled(true);

        //与js弹框交互
        websettings.setJavaScriptEnabled(true);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
        websettings.setMediaPlaybackRequiresUserGesture(false);
//        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        websettings.setLoadsImagesAutomatically(true);
//        websettings.setUseWideViewPort(true);
//        websettings.setSupportMultipleWindows(true);
//        websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        websettings.setAppCacheEnabled(true);
//        websettings.setDomStorageEnabled(true);
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
                }

                //加载到一定程度就可以关闭了
                if(newProgress > 30){
                    if (movieWebLoadingDialog != null) {
                        movieWebLoadingDialog.hide();
                    }
                }
            }
        });
        X5webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);

//                Log.e("zy", "onPageStarted=" + s);
//                if (!s.contains(key)) {


                    if (movieWebLoadingDialog == null) {
                        movieWebLoadingDialog = new MovieWebLoadingDialog(context);
                    }
                    if (getUserVisibleHint()) {

                        if(loadUrl.contains(key)) {
                            try {
                                movieWebLoadingDialog.show();
                                movieWebLoadingDialog.settitle("正在离开掘金宝前往第三方网");
                            }catch (Exception e){}
                        }
//                        else{
//                            movieWebLoadingDialog.settitle("正在加载");
//                        }
                        movieWebLoadingDialog.setText(s);
                    }
                    mViewBinding.progressBar.setVisibility(View.VISIBLE);
//                    mViewBinding.rlJh.setVisibility(View.VISIBLE);
//                } else {
//                    mViewBinding.rlJh.setVisibility(View.GONE);
//
//                    mViewBinding.progressBar.setVisibility(View.VISIBLE);
//                }
                loadUrl = s;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (movieWebLoadingDialog != null) {
                    movieWebLoadingDialog.hide();
                }
            }


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {

                if(request.getUrl().toString().contains(".gif")){
                    return new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream("".getBytes()));
                }

                findVideo(request.getUrl().toString());
                return super.shouldInterceptRequest(webView, request);
            }


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().contains(".apk")) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });




        //下拉刷新
//        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                if(X5webView != null){
//                    X5webView.reload();
//                }
//                mViewBinding.srl.finishRefresh();
//
//            }
//        });
//        mViewBinding.srl.setEnableLoadMore(false);

    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        X5webView.loadUrl(url + kw + " 播放");
    }

    public boolean onKeyBackPressed() {
        if (getUserVisibleHint()) {
            if(X5webView != null) {
                if (X5webView.canGoBack()) {
                    X5webView.goBack();
                    return true;
                }
            }
//            if (!loadUrl.equals(url + kw + " 播放")) {
//                mViewBinding.rlJh.setVisibility(View.GONE);
//                X5webView.loadUrl(url + kw + " 播放");
//                return true;
//            }
        }
        return false;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
////                    if(X5webView.canGoBack()) {
////                        X5webView.goBack();
////                        return true;
////                    }else{
////                    String X5webViewUrl = loadUrl;
////                    Log.e("zy", "X5webViewUrl=" + X5webViewUrl);
//                    if (!loadUrl.contains(key)) {
//                        mViewBinding.rlJh.setVisibility(View.GONE);
//                        X5webView.loadUrl(url + kw + " 播放");
//                        return true;
//                    }
////                    }
//                }
//                return false;
//            }
//        });
//
//    }

    @Override
    public void initData() {

    }

    //通知kw变更
    @Subscribe
    public void showKW(Message message){
        if(message.what == BusConstant.MOVIESEARHC_REFRESH_KW){
            if (mPresenter == null) {
                return;
            }

            String newKw = (String) message.obj;

            if (!TextUtils.isEmpty(newKw)) {
                this.showKw = getKw(newKw);
            }else{
                return;
            }

            if (getUserVisibleHint()) {
                kw = showKw;
//                mViewBinding.rlJh.setVisibility(View.GONE);
                X5webView.loadUrl(url + kw + " 播放");

//                if (!showKw.equals(kw)) {
//                    kw = showKw;
//                    mViewBinding.rlJh.setVisibility(View.GONE);
//                    X5webView.loadUrl(url + kw + " 播放");
//                }else{
//                    //不是官方搜索页，页要重新加载
//                    if(X5webView != null && X5webView.canGoBack()){
//                        kw = showKw;
//                        mViewBinding.rlJh.setVisibility(View.GONE);
//                        X5webView.loadUrl(url + kw + " 播放");
//                    }
//                }
            }


        }
    }

//    public void show(String showKw) {
//
//        showKw = getKw(showKw);
//
//        if (mPresenter == null) {
//            return;
//        }
//        if (!TextUtils.isEmpty(showKw)) {
//            this.showKw = showKw;
//        }
//
//        if (getUserVisibleHint()) {
//            if (!showKw.equals(kw)) {
//                kw = showKw;
//                mViewBinding.rlJh.setVisibility(View.GONE);
//                X5webView.loadUrl(url + kw + " 播放");
//            }
//        }
//
//    }

//    //强制加载
//    public void showForce(String showKw) {
//
//        showKw = getKw(showKw);
//
//        if (mPresenter == null) {
//            return;
//        }
//        if (!TextUtils.isEmpty(showKw)) {
//            this.showKw = showKw;
//        }
//
//        if (getUserVisibleHint()) {
//            kw = showKw;
//            mViewBinding.rlJh.setVisibility(View.GONE);
//            X5webView.loadUrl(url + kw + " 播放");
//
//        }
//
//    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);

        if (isVisible) {
            if(X5webView != null){
                X5webView.resumeTimers();
                X5webView.onResume();
            }
            if (!TextUtils.isEmpty(showKw)) {
                if (!showKw.equals(kw)) {
                    kw = showKw;
//                    mViewBinding.rlJh.setVisibility(View.GONE);
                    X5webView.loadUrl(url + kw + " 播放");
                }
            }
        }else{
            if(X5webView != null){
                X5webView.onPause();
                X5webView.pauseTimers();
            }
        }

    }

    @Override
    public void onDestroyView() {

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


    private String getKw(String kw) {
        kw = kw.replace("(", "")
                .replace("~", "")
                .replace("`", "")
                .replace("!", "")
                .replace(")", "")
                .replace("@", "")
                .replace("#", "")
                .replace("$", "")
                .replace("%", "")
                .replace("^", "")
                .replace("&", "")
                .replace("*", "")
                .replace("[", "")
                .replace("]", "")
                .replace("{", "")
                .replace("}", "")
                .replace(",", "")
                .replace(".", "")
                .replace("/", "")
                .replace(";", "")
                .replace(":", "")
                .replace("+", "")
                .replace("-", "")
                .replace("_", "")
                .replace("=", "")
                .replace("|", "")
                .replace("*", "")
                .replace("?", "");

        return kw;
    }

    //嗅探链接里的视频
    private void findVideo(String url) {
        ThreadPoolManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL realUrl = new URL(url);


                    URLConnection connection = realUrl.openConnection();
                    Map<String, List<String>> map = connection.getHeaderFields();


//                    Log.e("zy", String.valueOf(map.get("Content-Type")));
//                    Log.e("zy", url);
                    if (map.get("Content-Type") != null) {
                        if (String.valueOf(map.get("Content-Type")).contains("mpeg")
                                || String.valueOf(map.get("Content-Type")).contains("flv")
                                || String.valueOf(map.get("Content-Type")).contains("3gpp")
                                || String.valueOf(map.get("Content-Type")).contains("wmv")
                                || String.valueOf(map.get("Content-Type")).contains("asf")
                                || String.valueOf(map.get("Content-Type")).contains("rm")
                                || String.valueOf(map.get("Content-Type")).contains("rmvb")
                                || String.valueOf(map.get("Content-Type")).contains("avi")
                                || String.valueOf(map.get("Content-Type")).contains("mov")
                                || String.valueOf(map.get("Content-Type")).contains("dat")
                                || String.valueOf(map.get("Content-Type")).contains("mpg")
                                || String.valueOf(map.get("Content-Type")).contains("mp4")) {
//                            Log.e("zy", "播放：" + String.valueOf(map.get("Content-Type")));
//                            Log.e("zy", url);

                            //构造播放种子
                            MovieThirdIframeEntity movieThirdIframeEntity = new MovieThirdIframeEntity();
                            movieThirdIframeEntity.setTitle(kw);
                            movieThirdIframeEntity.setCurrentIndex(0);
                            movieThirdIframeEntity.setType("normalVideo");

                            MovieThirdIframeEntity.SourceListBean sourceListBean = new MovieThirdIframeEntity.SourceListBean();
                            sourceListBean.setTitle("");
                            sourceListBean.setSource("");
                            sourceListBean.setUrl(url);

                            List<MovieThirdIframeEntity.SourceListBean> listBeans = new ArrayList<>();
                            listBeans.add(sourceListBean);

                            movieThirdIframeEntity.setSourceList(listBeans);


                            if (mActivity != null && !mActivity.isDestroyed()) {
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(getUserVisibleHint()) {
                                            if (movieWebLoadingDialog != null) {
                                                movieWebLoadingDialog.hide();
                                            }

                                            book().write(PagerCons.KEY_PLAYMOVIE_DATA , JSON.toJSONString(movieThirdIframeEntity));
                                            PlayMovieActivity.intentMe(mActivity, false);
                                        }
                                    }
                                });
                            }

                        } else {
//                            Log.e("zy", "Content-Type = null , url=" + url);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){}
            }
        });
    }

}
package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentHhmthridBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.base.BusProvider;
import com.newsuper.t.juejinbao.basepop.blur.thread.ThreadPoolManager;
import com.newsuper.t.juejinbao.ui.movie.activity.MovieSearchActivity;
import com.newsuper.t.juejinbao.ui.movie.adapter.SearchMovieThirdAdapter;
import com.newsuper.t.juejinbao.ui.movie.bean.LoadState;
import com.newsuper.t.juejinbao.ui.movie.bean.SearchMovieCache;
import com.newsuper.t.juejinbao.ui.movie.entity.JSMovieSearchWebBean;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieCinamesEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.HHMthirdFragmentImpl;
import com.newsuper.t.juejinbao.ui.movie.view.MovieLoadingDialog;
import com.newsuper.t.juejinbao.ui.movie.view.WrapContentGridViewManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.otto.Subscribe;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 坏坏猫 废弃
 */
public class HHMthirdFragment extends BaseFragment<HHMthirdFragmentImpl, FragmentHhmthridBinding> {
    private static final long OVERDUE_TIME = 1000 * 60 * 60 * 24;

    //JS爬取电影数据
    List<JSMovieSearchWebBean.DataBean> webCinemaItems = new ArrayList<>();
    //爬取电影适配
    SearchMovieThirdAdapter searchMovieThirdAdapter;

    //暗中webview
    private WebView webView2;
    MovieLoadingDialog movieLoadingDialog;
//    private String kw = "";
    //注入的JS
    private String getVideoJs = null;
    //当前加载的nowUrl
    private String nowUrl;
    //当前影院
    private MovieCinamesEntity.DataBean cinema;
    //当前嗅探的地址
//    private String nowDomain = "";

    //即将显示的kw
    private String showKw = "";

    //状态标识
    private LoadState loadState = new LoadState("" , false);

    //vp滑动的位置
    private int curPosition = 0;
    //处于viewpager中的位置
    private int position = 0;


    private Handler mHandler = new Handler();

    //左右预加载
    private int preLoad = 0;


    //计时器
    private Subscription subscription;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        loadState.setKw(getArguments().getString("kw"));
//        showKw = getArguments().getString("kw");
        loadState.setKw(MovieSearchActivity.kw);
        showKw = MovieSearchActivity.kw;
        position = getArguments().getInt("position");
        getVideoJs = getArguments().getString("js");
        try{
            String cinemaData = getArguments().getString("cinemaData");
            cinema = JSON.parseObject(cinemaData, MovieCinamesEntity.DataBean.class);
        }catch (Exception e){}
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hhmthrid, container, false);
        return view;
    }

    @Override
    public void initView() {
        //超时提醒
        movieLoadingDialog = new MovieLoadingDialog(getActivity(), new MovieLoadingDialog.ChaoshiListener() {
            @Override
            public void chaoshi() {
                mViewBinding.rlBlank.setVisibility(View.VISIBLE);
                mViewBinding.tvLoadresult.setText("连接超时，请下拉刷新");
                mViewBinding.btNext.setVisibility(View.GONE);
                mViewBinding.srl3.finishRefresh();
            }
        });
        createWebView();

        //爬取影院适配
        WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(context, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        mViewBinding.rvThirdmovie.setLayoutManager(gridLayoutManager);
        searchMovieThirdAdapter = new SearchMovieThirdAdapter(context);
        mViewBinding.rvThirdmovie.setAdapter(searchMovieThirdAdapter);

        //下拉刷新
        mViewBinding.srl3.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestData(cinema , loadState.getKw());
            }
        });
        mViewBinding.srl3.setEnableLoadMore(false);


        //点击切换影院
        mViewBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewBinding.btNext.getText().toString().equals("点击刷新")) {
                    requestData(cinema , loadState.getKw());
                } else {
                    BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_MOVIENEXT));
                }
            }
        });
    }

    @Override
    public void initData() {
//        if(curPosition == position || curPosition == position - preLoad || curPosition == position + preLoad) {
            if (!loadCache(loadState.getKw())) {
                requestData(cinema, loadState.getKw());
            }
//        }

    }


    //通知kw变更
    @Subscribe
    public void showKW(Message message){
        if(message.what == BusConstant.MOVIESEARHC_REFRESH_KW){
            if (mPresenter == null) {
                return;
            }
            //kw变更，关闭所有loading
            mViewBinding.srl3.finishRefresh();

            String newKW = (String) message.obj;
            //得到通知，需要更新数据
            if (!TextUtils.isEmpty(newKW)) {
//                Log.e("zy" , position + cinema.getTitle() + " showKw变更 " + newKW);
                showKw = newKW;

                //清除webview，中断嗅探，防止kw保存冲突
                destoryWebView();

                updatelogic();
                //不同的kw，则开始请求
//                if(!loadState.getKw().equals(newKW)) {
//                    requestData(cinema , newKW);
//                }
            }

//            if (!TextUtils.isEmpty((String) message.obj)) {
//                this.showKw = (String) message.obj;
//            }
//            if (getUserVisibleHint()) {
//                kw = showKw;
//                requestData(cinema);
//            }
        }
    }

    //滑动位置变更
    @Subscribe
    public void positionChange(Message message){
        if(message.what == BusConstant.MOVIESEARHC_POSITION){
            if (mPresenter == null) {
                return;
            }

            curPosition = message.arg1;
            updatelogic();


        }
    }

    //更新逻辑
    private void updatelogic(){
        //处于可更新范围
        if(curPosition == position || curPosition == position - preLoad || curPosition == position + preLoad){
            //非加载状态
            if(!loadState.isState()) {
                //相同的kw，无需加载
                if(showKw.equals(loadState.getKw())){
                }
                else{
                    requestData(cinema , showKw);
                }
            }
            //加载状态
            else{
                startCountDown();


                //相同的kw，无需加载
                if(showKw.equals(loadState.getKw())){
//                    Log.e("zy" , position + " " + cinema.getTitle() + " 处于可更新范围 加载状态 相同的kw，无需加载");
                }
                //不同的kw，需要加载
                else{
//                    Log.e("zy" , position + " " + cinema.getTitle() + "处于可更新范围 加载状态 不同的kw，需要加载");
                    requestData(cinema , showKw);
                }
            }
        }
        //不可更新范围
        else{
            destoryWebView();

            //处于加载状态，停止加载
            if(loadState.isState()){
//                Log.e("zy" , position + " " + cinema.getTitle() + " 不可更新范围 处于加载状态，停止加载");
                //先取消加载
//                webView2.stopLoading();

                //重置以便下次申请请求
                loadState.setKw("");
                loadState.setState(false);
            }
            //不处于加载状态
            else{
//                Log.e("zy" , position + " 不处于加载状态");
            }

        }
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);

        if (isVisible) {
            loadCache(loadState.getKw());
        }
    }

    //请求加载数据
    private void requestData(MovieCinamesEntity.DataBean dataBean , String kw) {
        if (dataBean == null) {
            return;
        }

        loadState.setKw(kw);
        loadState.setState(true);


        movieLoadingDialog.setNum(0);
        mViewBinding.rlBlank.setVisibility(View.GONE);
        mViewBinding.llThird.setVisibility(View.VISIBLE);

//        searchMovieThirdAdapter.update(new ArrayList());



        //有缓存则加载
        if(loadCache(kw)){
//            Log.e("zy" , position + cinema.getTitle() + " requestData 加载缓存");
            return;
        }


        if (!TextUtils.isEmpty(getVideoJs)) {

//            //先取消加载
//            webView2.stopLoading();

            String url;

            if(TextUtils.isEmpty(dataBean.getSearch_url())){
                url = dataBean.getUrl() + "?path=index&search=" + kw.trim();
            }else {
                url = dataBean.getSearch_url().replace("{key}", kw.trim());
            }

//            Log.e("zy" , position + " " + cinema.getTitle() + " requestData 加载 " + url);
            createWebView();
            webView2.loadUrl(url);
            //开始加载了就去掉下拉刷新，用本地的刷新
            mViewBinding.srl3.finishRefresh();



            startCountDown();
        }
    }


    //加载缓存数据
    private boolean loadCache(String kw){
        //先填充一个空白数据
//        List<JSMovieSearchWebBean.DataBean> blankList = new ArrayList<>();
//        JSMovieSearchWebBean.DataBean footerDataBean = new JSMovieSearchWebBean.DataBean();
//        footerDataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//        blankList.add(footerDataBean);
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                searchMovieThirdAdapter.update(blankList);
//            }
//        });
//
        //先填充一个空白数据
        webCinemaItems.clear();
        searchMovieThirdAdapter.update(webCinemaItems);


        //先加载缓存
        SearchMovieCache.SearchMovieKwData.Cinema saveData = mPresenter.readCache(kw.trim(), cinema.getUrl());
        if (saveData != null) {
            operate(saveData.getData() , true);

        }
        movieLoadingDialog.addNum(10);
        //未超过过期时间
        if (saveData != null && System.currentTimeMillis() - saveData.getTimastamp() < OVERDUE_TIME) {
            movieLoadingDialog.setNum(100);
            movieLoadingDialog.hide();
            return true;
        }
        return false;
    }

    //得到数据,方法解析
    private void operate(String json , boolean isCache) {

        mViewBinding.tvPath.setText("搜索来自网站:" + cinema.getUrl());
//        Log.e("zy", position + " 解析 " + cinema.getTitle());

        movieLoadingDialog.addNum(50);
        //解析完成，关闭log界面
//
//        mViewBinding.llLog.setVisibility(View.GONE);
//        saveCache(kw , cinema.getTitle() , json);
        if(isCache) {
//            Log.e("zy", position + " " + cinema.getTitle() + " 缓存 " + json);
        }else{
//            Log.e("zy", position + " " + cinema.getTitle() + " 响应数据 " + json);
        }
        try {
            JSMovieSearchWebBean jsMovieSearchWebBean = JSON.parseObject(json, JSMovieSearchWebBean.class);

            //是对应的返回
//            if (nowDomain.contains(jsMovieSearchWebBean.getDomain())) {

                searchMovieThirdAdapter.setKey(loadState.getKw());
//                mViewBinding.imgLoadingBg.setVisibility(View.GONE);
                movieLoadingDialog.hide();
            webCinemaItems.clear();
            webCinemaItems.addAll(jsMovieSearchWebBean.getData());
//                webCinemaItems = jsMovieSearchWebBean.getData();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        searchMovieThirdAdapter.update(webCinemaItems);
                    }
                });

//                Log.e("zy" ,  position + " " + cinema.getTitle() + "填充数据成功");
                if (webCinemaItems.size() == 0) {
                    //增加一个footer item
//                    JSMovieSearchWebBean.DataBean footerDataBean = new JSMovieSearchWebBean.DataBean();
//                    footerDataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//                    webCinemaItems.add(footerDataBean);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            searchMovieThirdAdapter.update(webCinemaItems);
                        }
                    });

                    mViewBinding.rlBlank.setVisibility(View.VISIBLE);
                    mViewBinding.tvLoadresult.setText("未找到相关影视");
                    mViewBinding.btNext.setText("点击切换影院");
                    mViewBinding.btNext.setVisibility(View.VISIBLE);
                } else {
                    mViewBinding.rlBlank.setVisibility(View.GONE);
                }
                mViewBinding.srl3.finishRefresh();

            //加载完成，更新状态
            loadState.setState(false);

            closeCountDown();
//            }

        } catch (Exception e) {
        }

    }

    //销毁界面同时销毁webview
    @Override
    public void onDestroyView() {
//        Log.e("zy" , position + " " + cinema.getTitle() + " " + "onDestroyView");
        destoryWebView();


        if (movieLoadingDialog != null) {
            movieLoadingDialog.destory();
        }
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    private void createWebView(){

        if(webView2 == null) {
//            Log.e("zy" , position + " " + cinema.getTitle() + " " + "创建webview");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            webView2 = new WebView(mActivity.getApplicationContext());
            webView2.setLayoutParams(params);
            mViewBinding.llWebcontain.addView(webView2);

            com.tencent.smtt.sdk.WebSettings websettings = webView2.getSettings();
//        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            websettings.setLoadsImagesAutomatically(true);
            websettings.setJavaScriptEnabled(true);
            websettings.setUseWideViewPort(true);
            websettings.setSupportMultipleWindows(true);
            websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            websettings.setJavaScriptCanOpenWindowsAutomatically(true);
            websettings.setAppCacheEnabled(true);
            websettings.setDomStorageEnabled(true);
//        websettings.setBlockNetworkImage(false); // 解决图片不显示
            websettings.setBlockNetworkImage(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                websettings.setMixedContentMode(WebSettings.LOAD_NORMAL);
            }

            //与js弹框交互
            websettings.setJavaScriptEnabled(true);
            websettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webView2.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {

                @Override
                public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
                    super.onPageStarted(webView, url, bitmap);
                    nowUrl = url;
                }

                @Override
                public void onPageFinished(WebView webView, String url) {
                    super.onPageFinished(webView, url);
                }

                @Override
                public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {

                    if (String.valueOf(request.getUrl()).contains(".png")
                            || String.valueOf(request.getUrl()).contains(".jpg")
                            || String.valueOf(request.getUrl()).contains(".gif")
                            || String.valueOf(request.getUrl()).contains(".jpeg")
                            || String.valueOf(request.getUrl()).contains(".webp")
                            || String.valueOf(request.getUrl()).contains(".css")
                            || String.valueOf(request.getUrl()).contains(".bmp")
                    ) {
//                    return super.shouldInterceptRequest(webView, request);
                        return new WebResourceResponse(null, null, null);
                    }


                    return super.shouldInterceptRequest(webView, request);
                }
            });

            webView2.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onJsAlert(WebView webView, String s, String s1, com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    try {
                        JSMovieSearchWebBean jsMovieSearchWebBean = JSON.parseObject(s1, JSMovieSearchWebBean.class);

                        if (loadState.getKw() != null) {
                            mPresenter.saveCache(loadState.getKw().trim(), jsMovieSearchWebBean.getDomain(), s1);
                        }


                        operate(s1 , false);
                    } catch (Exception e) {
//                        Log.e("zy", "js返回无法解析:" + s1);
                        e.printStackTrace();
                    }
                    jsResult.cancel();
                    return super.onJsAlert(webView, s, s1, jsResult);
                }

                @Override
                public void onProgressChanged(WebView webView, int i) {
                    super.onProgressChanged(webView, i);
//                Log.e("zy", "onProgressChanged = " + i);
                    if (i >= cinema.getProgress()) {
                        if (!TextUtils.isEmpty(nowUrl)) {
//                        Log.e("zy", "value=开始");


//                            if (!nowUrl.contains("?path=index&search=")) {

                                ThreadPoolManager.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(cinema.getInjection_time() * 1L);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        if (webView != null) {
                                            mActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    webView.evaluateJavascript(getVideoJs, new ValueCallback<String>() {
                                                        @Override
                                                        public void onReceiveValue(String value) {//js与native交互的回调函数
//                                                        Log.e("zy", "value=完成" + value);


                                                        }
                                                    });
                                                }
                                            });

                                        }
                                    }});

//                            } else {
//                                webView.evaluateJavascript(getVideoJs, new ValueCallback<String>() {
//                                    @Override
//                                    public void onReceiveValue(String value) {//js与native交互的回调函数
////                                    Log.e("zy", "value=完成" + value);
//
//
//                                    }
//                                });
//                            }
                            nowUrl = null;

                        }
                    }

                }


            });
        }
    }

    private void destoryWebView(){

        if (webView2 != null) {
//            Log.e("zy" , position + " " + cinema.getTitle() + " " + "销毁webview");
            webView2.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView2.clearHistory();

            ((ViewGroup) webView2.getParent()).removeView(webView2);
            webView2.destroy();
            webView2 = null;
        }

        closeCountDown();
    }


    //倒计时长
    private int loadcd = 5;
    //启动倒计时
    private void startCountDown(){
        closeCountDown();

        mViewBinding.rlLoading.setVisibility(View.VISIBLE);
        mViewBinding.tvLoading.setText("正在搜索此影院...(" + loadcd + "s)");

        subscription = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(loadcd)
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onCompleted() {
//                        Log.e("zy", "Subscriber onCompleted");

                        mViewBinding.rlLoading.setVisibility(View.GONE);
                        mViewBinding.rlBlank.setVisibility(View.VISIBLE);
                        mViewBinding.tvLoadresult.setText("读取影视超时");
                        mViewBinding.btNext.setVisibility(View.GONE);

                        mViewBinding.btNext.setText("点击刷新");
                        mViewBinding.btNext.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
//                        Log.e("zy" , "Subscriber " + aLong);
                        mViewBinding.tvLoading.setText("正在搜索此影院...(" + (loadcd - aLong - 1) + "s)");
                    }
                });
    }

    //关闭倒计时
    private void closeCountDown(){
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }

        if(mViewBinding != null) {
            mViewBinding.rlLoading.setVisibility(View.GONE);
        }
    }

}

package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentSearchdetailhhmmvieBinding;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.SearchMovieCinemasConditionAdapter;
import com.juejinchain.android.module.movie.adapter.SearchMovieThirdAdapter;
import com.juejinchain.android.module.movie.bean.SearchMovieCache;
import com.juejinchain.android.module.movie.entity.DependentResourcesDataEntity;
import com.juejinchain.android.module.movie.entity.JSMovieSearchWebBean;
import com.juejinchain.android.module.movie.entity.MovieCinamesEntity;
import com.juejinchain.android.module.movie.presenter.impl.SearchDetailHHMMovieImpl;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.movie.view.MovieLoadingDialog;
import com.juejinchain.android.module.movie.view.WrapContentGridViewManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.ys.network.base.BaseFragment;
import com.ys.network.base.PagerCons;
import com.ys.network.bus.BusConstant;
import com.ys.network.bus.BusProvider;
import com.ys.network.network.RetrofitManager;
import com.ys.network.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static io.paperdb.Paper.book;

//坏坏猫影视库 废弃
public class SearchDetailHHMMovieFragment extends BaseFragment<SearchDetailHHMMovieImpl, FragmentSearchdetailhhmmvieBinding> implements SearchDetailHHMMovieImpl.MvpView {
    private static final long OVERDUE_TIME = 1000 * 60 * 60 * 24;


    public RecyclerView.RecycledViewPool recyclerViewPool = new RecyclerView.RecycledViewPool();
    List<MovieCinamesEntity.DataBean> labels = new ArrayList<>();
    //标签适配器
    SearchMovieCinemasConditionAdapter searchMovieCinemasConditionAdapter;

    //接口电影数据
    List<DependentResourcesDataEntity.DataBeanX.DataBean> myCinemaItems = new ArrayList<>();
    //我的影院适配
    SearchMovieThirdAdapter searchMovieJJBAdapter;

    //JS爬取电影数据
    List<JSMovieSearchWebBean.DataBean> webCinemaItems = new ArrayList<>();
    //爬取电影适配
    SearchMovieThirdAdapter searchMovieThirdAdapter;

    MovieLoadingDialog movieLoadingDialog;

    //暗中webview
    private WebView webView2;

    private String type = "全部";
    private int page = 1;
    private String kw = "";

    //注入的JS
    private String getVideoJs = null;
    private String nowUrl;

    //当前影院
    private MovieCinamesEntity.DataBean cinema;

    //当前嗅探的地址
    private String nowDomain = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        kw = getArguments().getString("kw");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchdetailhhmmvie, container, false);
        movieLoadingDialog = new MovieLoadingDialog(getActivity(), new MovieLoadingDialog.ChaoshiListener() {
            @Override
            public void chaoshi() {
                mViewBinding.rlBlank.setVisibility(View.VISIBLE);
                mViewBinding.tvLoadresult.setText("连接超时，请下拉刷新");
                mViewBinding.srl2.finishRefresh();
                mViewBinding.srl3.finishRefresh();
            }
        });
        return view;
    }

    @Override
    public void initView() {
//        Glide.with(getContext()).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoading);


        //点击去全网搜
        mViewBinding.ivQqws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.TOALLMOVIESEARCH, ""));

            }
        });

        boolean pmdInto = book().read(PagerCons.KEY_MOVIESEARCH_PMDIN, false);


        //跑马灯显示规则
        if (pmdInto) {
            book().write(PagerCons.KEY_MOVIESEARCH_PMDIN, false);

            int totalHour = 0;
            try {
                totalHour = Integer.parseInt(StringUtils.getDateToHour(System.currentTimeMillis()));
            } catch (Exception e) {
            }

            int nowHour = totalHour % 24;

            if (nowHour >= 19 && nowHour < 23) {

                mViewBinding.rlPmd.setVisibility(View.VISIBLE);
                mViewBinding.tvPmd.setSelected(true);
                mViewBinding.rlPmd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewBinding.rlPmd.setVisibility(View.GONE);
                    }
                });

            }

        }


//        long recordTime = book().read(PagerCons.KEY_MOVIESEARCH_PMD ,0L);
//
//        long nOWtiem = System.currentTimeMillis();
//        if(nOWtiem - recordTime > 1000 * 60 * 60 * 24){
//
//            mViewBinding.rlPmd.setVisibility(View.VISIBLE);
//            mViewBinding.tvPmd.setSelected(true);
//            mViewBinding.rlPmd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mViewBinding.rlPmd.setVisibility(View.GONE);
//                }
//            });
//
//            book().write(PagerCons.KEY_MOVIESEARCH_PMD , nOWtiem);
//        }


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
                Log.e("zy", "nowUrl=" + nowUrl);

                StringBuilder stringBuilder = new StringBuilder(mViewBinding.tvLog.getText().toString());
                stringBuilder.append("\n正在加载网址");
                movieLoadingDialog.addNum(20);
                mViewBinding.sv.fullScroll(ScrollView.FOCUS_DOWN);
                mViewBinding.tvLog.setText(stringBuilder.toString());
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                Log.e("zy", "onPageFinished");
//                    StringBuilder stringBuilder = new StringBuilder(mViewBinding.tvLog.getText().toString());
//                    mViewBinding.sv.fullScroll(ScrollView.FOCUS_DOWN);
//                    mViewBinding.tvLog.setText(stringBuilder.toString());
//                    if (!TextUtils.isEmpty(nowUrl)) {
//                        Log.e("zy", "value=开始");
//                        nowUrl = null;
//                        webView.evaluateJavascript(getVideoJs, new ValueCallback<String>() {
//                            @Override
//                            public void onReceiveValue(String value) {//js与native交互的回调函数
//                                Log.e("zy", "value=完成" + value);
////                                Toast.makeText(mActivity , "js注入完成--->" + StringUtils.getDateFromTest( System.currentTimeMillis() / 1000L) , Toast.LENGTH_SHORT ).show();
//
//                                if (mActivity != null) {
//                                    mActivity.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//
//                                            StringBuilder stringBuilder = new StringBuilder(mViewBinding.tvLog.getText().toString());
//                                            stringBuilder.append("\n等待解析完成...");
//                                            movieLoadingDialog.addNum(10);
//                                            mViewBinding.sv.fullScroll(ScrollView.FOCUS_DOWN);
//                                            mViewBinding.tvLog.setText(stringBuilder.toString());
//                                        }
//                                    });
//                                }
//
//                            }
//                        });
//                    }


            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {

                if( String.valueOf(request.getUrl()).contains(".png")
                        ||  String.valueOf(request.getUrl()).contains(".jpg")
                        ||  String.valueOf(request.getUrl()).contains(".gif")
                        ||  String.valueOf(request.getUrl()).contains(".jpeg")
                        ||  String.valueOf(request.getUrl()).contains(".webp")
                        ||  String.valueOf(request.getUrl()).contains(".css")
                        ||  String.valueOf(request.getUrl()).contains(".bmp")
                ){
//                    return super.shouldInterceptRequest(webView, request);
                    return new WebResourceResponse(null,null,null);
                }


                return super.shouldInterceptRequest(webView, request);
            }
        });

        webView2.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                JSMovieSearchWebBean jsMovieSearchWebBean = JSON.parseObject(s1, JSMovieSearchWebBean.class);
                Log.e("zy", "解析");
                mPresenter.saveCache(kw, jsMovieSearchWebBean.getDomain(), s1);


                operate(s1);
                jsResult.cancel();
                return super.onJsAlert(webView, s, s1, jsResult);
            }

            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                Log.e("zy", "onProgressChanged = " + i);
                if (i >= 100) {
                    StringBuilder stringBuilder = new StringBuilder(mViewBinding.tvLog.getText().toString());
                    stringBuilder.append("\n加载" + i + "%");
                    mViewBinding.sv.fullScroll(ScrollView.FOCUS_DOWN);
                    mViewBinding.tvLog.setText(stringBuilder.toString());
                    if (!TextUtils.isEmpty(nowUrl)) {
                        Log.e("zy", "value=开始");


                        if (!nowUrl.contains("?path=index&search=")) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        nowUrl = null;
                        if(webView != null) {
                            webView.evaluateJavascript(getVideoJs, new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {//js与native交互的回调函数
                                    Log.e("zy", "value=完成" + value);
//                                Toast.makeText(mActivity , "js注入完成--->" + StringUtils.getDateFromTest( System.currentTimeMillis() / 1000L) , Toast.LENGTH_SHORT ).show();

                                    if (mActivity != null) {
                                        mActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                StringBuilder stringBuilder = new StringBuilder(mViewBinding.tvLog.getText().toString());
                                                stringBuilder.append("\n等待解析完成...");
                                                movieLoadingDialog.addNum(10);
                                                mViewBinding.sv.fullScroll(ScrollView.FOCUS_DOWN);
                                                mViewBinding.tvLog.setText(stringBuilder.toString());
                                            }
                                        });
                                    }

                                }
                            });
                        }
                    }
                }

            }


        });

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvCondition.setLayoutManager(linearLayoutManager1);

        //标签适配
        searchMovieCinemasConditionAdapter = new SearchMovieCinemasConditionAdapter(context, new SearchMovieCinemasConditionAdapter.OnItemClickListener() {
            @Override
            public void click(MovieCinamesEntity.DataBean dataBean) {
                if (dataBean.getUiType() == EasyAdapter.TypeBean.FOOTER) {
//                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + "/movie/jkk_addweb");

                } else {
                    cinema = dataBean;
                    nowDomain = cinema.getUrl();
                    mViewBinding.srl3.finishRefresh();
                    selectLabel(cinema);
                }

            }
        });
        mViewBinding.rvCondition.setAdapter(searchMovieCinemasConditionAdapter);


//        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
//        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        WrapContentGridViewManager gridLayoutManager2 = new WrapContentGridViewManager(context, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager2.setOrientation(GridLayout.VERTICAL);
        mViewBinding.rvJjbmovie.setLayoutManager(gridLayoutManager2);
        //我的影院适配
        searchMovieJJBAdapter = new SearchMovieThirdAdapter(context);
        mViewBinding.rvJjbmovie.setAdapter(searchMovieJJBAdapter);

        //爬取影院适配
        WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(context, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        //设置布局管理器， 参数gridLayoutManager对象
        gridLayoutManager.setRecycleChildrenOnDetach(true);
        mViewBinding.rvThirdmovie.setLayoutManager(gridLayoutManager);
        mViewBinding.rvThirdmovie.setNestedScrollingEnabled(false);
        searchMovieThirdAdapter = new SearchMovieThirdAdapter(context);
        mViewBinding.rvThirdmovie.setRecycledViewPool(recyclerViewPool);
        mViewBinding.rvThirdmovie.setAdapter(searchMovieThirdAdapter);


        mViewBinding.srl2.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mViewBinding.rlBlank.setVisibility(View.GONE);
                mPresenter.requestDependentResource(context, page, kw, type);
            }
        });

        mViewBinding.srl2.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.requestDependentResource(context, page, kw, type);
            }
        });

        mViewBinding.srl3.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                selectLabel(cinema);
            }
        });
        mViewBinding.srl3.setEnableLoadMore(false);


    }

    @Override
    public void initData() {
        mPresenter.requestCinemas(mActivity);
        mPresenter.requestDependentResource(context, page, kw, type);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getVideoJs = Utils.downloadStr(RetrofitManager.VIP_JS_URL + "/live/js/getJkkVideo.js");
                if (!TextUtils.isEmpty(getVideoJs)) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });

                }

            }
        }).start();
    }


    public void show(String kw) {
        if (mPresenter == null) {
            return;
        }
        this.kw = kw;
        if (cinema == null) {
            return;
        }

//        mViewBinding.llLog.setVisibility(View.GONE);

        mViewBinding.srl3.finishRefresh();
        selectLabel(cinema);

    }

    //请求影院列表返回
    @Override
    public void requestCinemas(MovieCinamesEntity movieCinamesEntity) {

        labels = movieCinamesEntity.getData();
//        MovieCinamesEntity.DataBean label = new MovieCinamesEntity.DataBean();
//        label.setTitle("好看影院");
//        label.isCheck = true;
//        labels.add(0 , label);

        if (labels.size() == 0) {
            return;
        }


        MovieCinamesEntity.DataBean labelAdd = new MovieCinamesEntity.DataBean();
        labelAdd.setTitle("");
        labelAdd.setUiType(EasyAdapter.TypeBean.FOOTER);
        labels.add(labelAdd);

        searchMovieCinemasConditionAdapter.update(labels);

        cinema = labels.get(0);
        cinema.isCheck = true;
        mViewBinding.srl3.finishRefresh();
        selectLabel(cinema);
    }

    //搜索kw相关资源返回
    @Override
    public void requestDependentResource(DependentResourcesDataEntity dependentResourcesDataEntity, int page) {
        movieLoadingDialog.hide();

        if (page == 1) {
            myCinemaItems = dependentResourcesDataEntity.getData().getData();

        } else {
            myCinemaItems.addAll(dependentResourcesDataEntity.getData().getData());

        }

        for (DependentResourcesDataEntity.DataBeanX.DataBean dataBean : myCinemaItems) {
            if (dataBean.getUiType() == EasyAdapter.TypeBean.BLANK) {
                myCinemaItems.remove(dataBean);
                break;
            }
        }

        if (myCinemaItems.size() == 0) {
            mViewBinding.rlBlank.setVisibility(View.VISIBLE);
            mViewBinding.tvLoadresult.setText("未找到相关影视");

        } else {
//            if(dependentResourcesDataEntity.getData().getLast_page() <= page){
//                DependentResourcesDataEntity.DataBeanX.DataBean dataBean = new DependentResourcesDataEntity.DataBeanX.DataBean();
//                dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//                myCinemaItems.add(dataBean);
//                mViewBinding.srl2.setEnableLoadMore(false);
//            }
        }


        mViewBinding.srl2.finishRefresh();
        mViewBinding.srl2.finishLoadMore();

        searchMovieJJBAdapter.update(myCinemaItems);

    }

    @Override
    public void error() {

    }

    //标签选择
    private void selectLabel(MovieCinamesEntity.DataBean dataBean) {

        if (dataBean == null) {
            return;
        }

        movieLoadingDialog.show();
        movieLoadingDialog.setNum(0);
//        mViewBinding.llLog.setVisibility(View.GONE);
//        mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);

        if (searchMovieJJBAdapter == null) {
            return;
        }
        searchMovieJJBAdapter.setKey(kw);

        if (dataBean.getTitle().equals("好看影院")) {
            nowDomain = "";
            movieLoadingDialog.setNum(100);
            //先清空数据
            searchMovieJJBAdapter.update(new ArrayList());

            mViewBinding.rvJjbmovie.setVisibility(View.VISIBLE);
            mViewBinding.llThird.setVisibility(View.GONE);

            mViewBinding.rlBlank.setVisibility(View.GONE);
            mPresenter.requestDependentResource(context, page, kw, type);
            webView2.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);

        } else {
            mViewBinding.rlBlank.setVisibility(View.GONE);
            mViewBinding.rvJjbmovie.setVisibility(View.GONE);
            mViewBinding.llThird.setVisibility(View.VISIBLE);

            searchMovieThirdAdapter.update(new ArrayList());


            mViewBinding.tvPath.setText("搜索来自网站:" + dataBean.getUrl());

            //先加载缓存
            SearchMovieCache.SearchMovieKwData.Cinema saveData = mPresenter.readCache(kw, cinema.getUrl());
            if (saveData != null) {
                operate(saveData.getData());
            }
            movieLoadingDialog.addNum(10);
            //未超过过期时间
            if (saveData != null && System.currentTimeMillis() - saveData.getTimastamp() < OVERDUE_TIME) {
                movieLoadingDialog.setNum(100);
                movieLoadingDialog.hide();
                return;
            }

            if (!TextUtils.isEmpty(getVideoJs)) {
                //先取消加载
                webView2.stopLoading();

                String url = dataBean.getUrl() + "?path=index&search=" + kw;

                if (!url.equals("about:blank")) {
//                    mViewBinding.llLog.setVisibility(View.VISIBLE);
                    mViewBinding.sv.fullScroll(ScrollView.FOCUS_DOWN);
                    mViewBinding.tvLog.setText("开始加载");
                }

                webView2.loadUrl(url);
            }


        }

    }


    //方法解析
    private void operate(String json) {

        movieLoadingDialog.addNum(50);
        //解析完成，关闭log界面
//
//        mViewBinding.llLog.setVisibility(View.GONE);
//        saveCache(kw , cinema.getTitle() , json);
        Log.e("zy", json);
        try {
            JSMovieSearchWebBean jsMovieSearchWebBean = JSON.parseObject(json, JSMovieSearchWebBean.class);

            //是对应的返回
            if (nowDomain.contains(jsMovieSearchWebBean.getDomain())) {

                searchMovieThirdAdapter.setKey(kw);
//                mViewBinding.imgLoadingBg.setVisibility(View.GONE);
                movieLoadingDialog.hide();
                webCinemaItems = jsMovieSearchWebBean.getData();
                searchMovieThirdAdapter.update(webCinemaItems);
                if (webCinemaItems.size() == 0) {
                    mViewBinding.rlBlank.setVisibility(View.VISIBLE);
                    mViewBinding.tvLoadresult.setText("未找到相关影视");
                } else {
                    mViewBinding.rlBlank.setVisibility(View.GONE);
                }
                mViewBinding.srl3.finishRefresh();
            }

        } catch (Exception e) {
        }

    }

    @Override
    public void onDestroyView() {

        if (webView2 != null) {
            webView2.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView2.clearHistory();

            ((ViewGroup) webView2.getParent()).removeView(webView2);
            webView2.destroy();
            webView2 = null;
        }


        if (movieLoadingDialog != null) {
            movieLoadingDialog.destory();
        }

        super.onDestroyView();
    }


}

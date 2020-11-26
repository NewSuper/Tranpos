package com.newsuper.t.juejinbao.ui.song.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lzx.starrysky.provider.SongInfo;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityMusicSearchBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.basepop.blur.thread.ThreadPoolManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.share.util.LogUtil;
import com.newsuper.t.juejinbao.ui.song.adapter.MusicHotAdapter;
import com.newsuper.t.juejinbao.ui.song.adapter.MusicSearchAdapter;
import com.newsuper.t.juejinbao.ui.song.adapter.SearchHistoryAdapter;
import com.newsuper.t.juejinbao.ui.song.entity.AddSongEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicHotListEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicSearchEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicSearchFromEntity;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.ui.song.presenter.impl.MusicSearchImpl;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeHandler;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebView;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebViewClient;
import com.newsuper.t.juejinbao.utils.jsbridge.CallBackFunction;
import com.qq.e.comm.util.StringUtil;


import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;


public class MusicSearchActivity extends BaseActivity<MusicSearchImpl, ActivityMusicSearchBinding> implements MusicSearchImpl.MvpView, MusicSearchAdapter.OnItemClickListener, MusicHotAdapter.OnItemClickListener, SearchHistoryAdapter.OnItemClickListener {

    private BridgeWebView webView;
    private String keyword;
    private int page;
    private List<String> historyList = new ArrayList<>();
    private List<MusicHotListEntity.DataBean> searchHotList = new ArrayList<>();
    private List<MusicSearchEntity.DataBean> searchResultList = new ArrayList<>();
    private SearchHistoryAdapter historyAdapter;
    private MusicHotAdapter hotAdapter;
    private MusicSearchAdapter resultAdapter;
    private MusicSearchEntity.DataBean song;

    private MusicSearchFromEntity.DataBean entity;
    //注入的JS
    private String getVideoJs = null;
    private String nowUrl = null;

    public static void intentMe(Context context , String keyword) {
        if (!ClickUtil.isNotFastClick()) {
            return;
        }
        //必须登录
        if (!LoginEntity.getIsLogin()) {
            context.startActivity(new Intent(context, GuideLoginActivity.class));
            return;
        }

        Intent intent = new Intent(context, MusicSearchActivity.class);
        intent.putExtra("keyword" , keyword);
        context.startActivity(intent);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_music_search;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        if(NetUtil.isNetworkAvailable(mActivity)){
            mViewBinding.loading.showContent();
        }else{
            mViewBinding.loading.showError();
        }
        createWebView();

        //搜索历史
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvHistory.setLayoutManager(linearLayoutManager);
        historyAdapter = new SearchHistoryAdapter(this,this);
        mViewBinding.rvHistory.setAdapter(historyAdapter);
        //热搜榜
        mViewBinding.rvSearch.setLayoutManager(new LinearLayoutManager(this));
        hotAdapter = new MusicHotAdapter(this,this);
        mViewBinding.rvSearch.setNestedScrollingEnabled(false);
        mViewBinding.rvSearch.setAdapter(hotAdapter);
        //搜索结果
        resultAdapter = new MusicSearchAdapter(this,this);

        mViewBinding.rlBack.setOnClickListener(view -> finish());
        //清空输入框
        mViewBinding.btnClearKw.setOnClickListener(view -> {
            mViewBinding.edtSearch.setText("");
            showHotSearch();
        });
        //点击搜索
        mViewBinding.btnSearch.setOnClickListener(view -> {
            if(!TextUtils.isEmpty(keyword)){
                saveSearchHistory();
                search();
            }else {
                ToastUtils.getInstance().show(mActivity,"");
            }

        });
        //点击键盘搜索
        mViewBinding.edtSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE) {
                saveSearchHistory();
                search();
            }
            return handled;
        });
        //清除搜索历史记录
        mViewBinding.ivDelete.setOnClickListener(view -> {

            new android.support.v7.app.AlertDialog.Builder(mActivity)
                    .setCancelable(true)
                    .setMessage("确定要清空历史记录吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();

                        historyList.clear();
                        historyAdapter.update(historyList);
                        mViewBinding.rlHistory.setVisibility(View.GONE);
                        mViewBinding.rvHistory.setVisibility(View.GONE);
                        Paper.book().delete(PagerCons.KEY_MUSIC_SEARCH_LIST);

                    })
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();

//            historyList.clear();
//            historyAdapter.update(historyList);
//            mViewBinding.rlHistory.setVisibility(View.GONE);
//            mViewBinding.rvHistory.setVisibility(View.GONE);
//            Paper.book().delete(PagerCons.KEY_MUSIC_SEARCH_LIST);



        });
        //免责声明
        mViewBinding.tvTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicMZSMActivity.intentMe(mActivity);
            }
        });
        //重连网络
        mViewBinding.loading.setOnErrorClickListener(view -> {
            if(NetUtil.isNetworkAvailable(mActivity)){
                initData();
                mViewBinding.loading.showContent();
            }else{
                ToastUtils.getInstance().show(mActivity,"网络未连接");
            }
        });

        mViewBinding.edtSearch.addTextChangedListener(textWatcher);

        mViewBinding.refresh.setEnableRefresh(false);
        mViewBinding.refresh.setEnableLoadMore(false);
        initRefresh();
    }

    private void saveSearchHistory() {
        boolean hasStr = false;
        for (String str:historyList) {
            if(str.equals(keyword)){
                hasStr = true;
                break;
            }
        }
        if(!hasStr){
            historyList.add(keyword);
            Paper.book().write(PagerCons.KEY_MUSIC_SEARCH_LIST, historyList);
        }
    }

    @Override
    public void initData() {
        if(Paper.book().read(PagerCons.KEY_MUSIC_SEARCH_LIST)!=null){
            historyList = Paper.book().read(PagerCons.KEY_MUSIC_SEARCH_LIST);
            Collections.reverse(historyList);
            historyAdapter.update(historyList);
        }else{
            mViewBinding.rlHistory.setVisibility(View.GONE);
            mViewBinding.rvHistory.setVisibility(View.GONE);
        }
        mPresenter.getSearchHotList(mActivity);
        mPresenter.getSearchFrom(mActivity);
    }

    /**
     * 输入框监听
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mViewBinding.btnClearKw.setVisibility(View.GONE);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mViewBinding.btnClearKw.setVisibility(StringUtil.isEmpty(s.toString()) ? View.GONE : View.VISIBLE);
            keyword = s.toString();
        }
    };

    private void initRefresh() {
        mViewBinding.refresh.setOnRefreshListener(refreshLayout -> {
            page = 1;
            if(!TextUtils.isEmpty(keyword))
                loadJs(keyword, page);
        });
        mViewBinding.refresh.setOnLoadMoreListener(refreshLayout -> {
            if(keyword!=null){
                page++;
                loadJs(keyword, page);
            }
        });
    }

    private void search() {
        if (TextUtils.isEmpty(mViewBinding.edtSearch.getText().toString())) {
            ToastUtils.getInstance().show(mActivity, "您还没有输入内容哦");
        } else {
            keyword = mViewBinding.edtSearch.getText().toString().trim();
            mViewBinding.rlHistory.setVisibility(View.GONE);
            mViewBinding.rvHistory.setVisibility(View.GONE);
            mViewBinding.tvHot.setText("搜索结果");
            mViewBinding.tvTip.setVisibility(View.VISIBLE);
            mViewBinding.rvSearch.setAdapter(resultAdapter);
            page = 1;
            loadJs(keyword, page);
            mViewBinding.loading.showLoading();
        }
        hideSoftInput();
    }

    private void showHotSearch() {
        mViewBinding.tvEmpty.setVisibility(View.GONE);
        mViewBinding.refresh.setEnableRefresh(false);
        mViewBinding.refresh.setEnableLoadMore(false);
        mViewBinding.rlHistory.setVisibility(View.VISIBLE);
        mViewBinding.rvHistory.setVisibility(View.VISIBLE);
        Collections.reverse(historyList);
        historyAdapter.update(historyList);
        mViewBinding.tvHot.setText("热搜榜");
        mViewBinding.tvTip.setVisibility(View.GONE);
        keyword = null;
        page = 1;
        searchResultList.clear();
        mViewBinding.rvSearch.setAdapter(hotAdapter);
        mViewBinding.loading.showContent();
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getSearchFrom(MusicSearchFromEntity data) {
        if(data.getCode()==0){
            if (data.getData() == null)
                return;
            entity = data.getData();
            //进来直接搜索关键字
            try {
                keyword = getIntent().getStringExtra("keyword");
            }catch (Exception e){
                e.printStackTrace();
            }
            if(!TextUtils.isEmpty(keyword)){
                mViewBinding.edtSearch.setText(keyword);
                saveSearchHistory();
                search();
            }
        }else{
            ToastUtils.getInstance().show(mActivity,data.getMsg());
        }
    }

    @Override
    public void getSearchHotList(MusicHotListEntity data) {
        if(data.getCode() == 0) {
            if (data.getData() == null)
                return;
            searchHotList.clear();
            searchHotList.addAll(data.getData());
            hotAdapter.update(searchHotList);
        }else{
            ToastUtils.getInstance().show(mActivity,data.getMsg());
        }
    }

    @Override
    public void addSongs(AddSongEntity bean) {
        if(bean.getCode() == 0){
            SongInfo songInfo = new SongInfo();
            songInfo.setSongId(bean.getData().getId());
            songInfo.setSongName(song.getTitle());
            songInfo.setSongUrl(song.getUrl());
            songInfo.setArtist(song.getAuthor());
            songInfo.setSongCover(song.getPic());
            songInfo.setDescription("");
            songInfo.setLanguage(song.getLrc());

            SongPlayManager.getInstance().addSongAndPlay(songInfo);
            Intent intent = new Intent(mActivity, SongActivity.class);
            intent.putExtra(SongActivity.SONG_INFO, songInfo);
            startActivity(intent);
        }else{
            ToastUtils.getInstance().show(mActivity,bean.getMsg());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewBinding.loadingProgressbar.setVisibility(View.GONE);
            }
        },500);
    }

    @Override
    public void onSearchHotItemClick(MusicHotListEntity.DataBean dataBean) {
        mViewBinding.edtSearch.setText(dataBean.getTitle());
        saveSearchHistory();
        search();
    }

    @Override
    public void click(MusicSearchEntity.DataBean dataBean) {
        mViewBinding.loadingProgressbar.setVisibility(View.VISIBLE);
        song = dataBean;
        Map<String, Object> map = new HashMap<>();
        map.put("id",dataBean.getSongid());
        //歌曲名
        map.put("song_name",dataBean.getTitle());
        //歌手名字
        map.put("singer",dataBean.getAuthor());
        //播放地址
        map.put("song_url",dataBean.getUrl());
        //专辑名 可选
        map.put("special_name","");
        //歌曲封面 可选
        map.put("image",dataBean.getPic());
        //歌词链接 可选
        map.put("lyric",dataBean.getLrc());
        //歌单名字 可选
        map.put("playlist_name","");

        mPresenter.addSongs(mActivity,map);
    }

    @Override
    public void onSearchHistoryClick(String string) {
        mViewBinding.edtSearch.setText(string);
        search();
    }

    /**
     * 隐藏键盘
     */
    protected void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    private void loadJs(String input,int page) {
        if(entity==null)
            return;
        Map<String, Object> map = new HashMap<>();
        map.put("input",input);
        map.put("page",page);
        map.put("type",entity.getPlatform_type());
        map.put("filter","name");
        map.put("url",entity.getSource_url());
        String json = encodeURI(JSON.toJSON(map).toString());

        new Thread(() -> {
            if(getVideoJs==null)
                getVideoJs = download(entity.getJs_url());
            if (!TextUtils.isEmpty(getVideoJs)) {
                runOnUiThread(() -> {
                    if (getVideoJs != null) {
                        if(webView==null)
                            createWebView();
                        webView.loadUrl(entity.getSource_url()+"?json="+json);
                    }
                });
            }
        }).start();
    }

    private String encodeURI(String str) {
        return Uri.encode(str, ":/-![].,%?&=");
    }

    private void createWebView(){
        if(webView == null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            webView = new BridgeWebView(getApplicationContext());
            webView.setLayoutParams(params);
            mViewBinding.llWeb.addView(webView);
            webView.resumeTimers();
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速

            WebSettings websettings = webView.getSettings();
            websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            websettings.setLoadsImagesAutomatically(true);
            websettings.setUseWideViewPort(true);
            websettings.setSupportMultipleWindows(true);
            websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            websettings.setAppCacheEnabled(true);
            websettings.setDomStorageEnabled(true);
            websettings.setBlockNetworkImage(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                websettings.setMixedContentMode(WebSettings.LOAD_NORMAL);
            }
            websettings.setBuiltInZoomControls(false);
            websettings.setSupportZoom(false);
            String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
            websettings.setAppCachePath(appCachePath);
            websettings.setAllowFileAccess(true);    // 可以读取文件缓存
            websettings.setTextZoom(100);//防止系统字体大小影响布局
            websettings.setJavaScriptEnabled(true);
            websettings.setJavaScriptCanOpenWindowsAutomatically(true);

            webView.registerHandler("getMusicList", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
                   setData(data);
                }
            });
            webView.setWebViewClient(new MyWebViewClient(webView));
        }
    }

    private class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            nowUrl = url;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!TextUtils.isEmpty(nowUrl)) {
                ThreadPoolManager.execute(() -> {
                    if (webView != null) {
                        mActivity.runOnUiThread(() -> webView.evaluateJavascript(getVideoJs, value -> {//js与native交互的回调函数
                            LogUtil.e("value=完成" + value);
                        }));
                    }
                });
                nowUrl = null;
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }


    private void setData(String s1) {
        mViewBinding.loading.showContent();
        mViewBinding.refresh.setEnableRefresh(true);
        mViewBinding.refresh.setEnableLoadMore(true);
        mViewBinding.tvEmpty.setVisibility(View.GONE);
        mViewBinding.refresh.finishRefresh();
        mViewBinding.refresh.finishLoadMore();
        try {
            LogUtil.e("onJsAlert==>" + s1);
            //  MusicSearchEntity entity = Gson.fromJson(s1,MusicSearchEntity.class);
            MusicSearchEntity entity = new Gson().fromJson(s1, MusicSearchEntity.class);
            if (entity.getData() != null && entity.getData().size() != 0) {
                if (page == 1)
                    searchResultList.clear();
                searchResultList.addAll(entity.getData());
                resultAdapter.update(searchResultList, keyword);
            } else {
                ToastUtils.getInstance().show(mActivity, entity.getError());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mViewBinding.tvEmpty.setVisibility(View.VISIBLE);
            mViewBinding.refresh.setEnableLoadMore(false);
        }
    }

    private void destroyWebView(){
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    protected void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    public String download(String urlStr) {
        URL url;
        try {
            // 创建一个URL对象
            url = new URL(urlStr);
            // 创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();
            byte buff[] = new byte[2048];
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            do {
                int numRead = urlConn.getInputStream().read(buff);
                if (numRead <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numRead);
            } while (true);
            getVideoJs = fromFile.toString();
            urlConn.getInputStream().close();
            fromFile.close();
        } catch (Exception e) {
            System.out.println("download:" + e.getMessage());
            e.printStackTrace();
        } finally {

        }
        return getVideoJs;
    }
}

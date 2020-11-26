package com.newsuper.t.juejinbao.ui.task.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.WebFragment;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.movie.utils.WebViewUtils;

public class TaskWebFragment extends WebFragment {

    //加载地址
    private String url;
    protected WebViewUtils webViewUtils;

    public static TaskWebFragment newInstance(Bundle data) {
        TaskWebFragment fragment = new TaskWebFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getArguments()!=null) {
            url = getArguments().getString("url");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_ad, container, false);
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
        webView.setWebChromeClient(new WebChromeClient());
        if(!TextUtils.isEmpty(url)){
            webView.loadUrl(url);
        }
        clearCache();
        return view;
    }

    public void showAppWebPage() {
        if(webView != null){
            webView.loadUrl("javascript:window.showAppTaskPage()");
        }
    }
}

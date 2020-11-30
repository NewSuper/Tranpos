package com.newsuper.t.consumer.function.person.activity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.MsgDetailBean;
import com.xunjoy.lewaimai.consumer.function.person.internal.IMessageDetailView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.MessageDetailPresenter;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDetailActivity extends BaseActivity implements IMessageDetailView {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.webview)
    WebView webView;
    MessageDetailPresenter presenter;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        toolbar.setTitleText("消息详情");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(false);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 设置缓存
        webView.getSettings().setDomStorageEnabled(true);

        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);//禁止水平滚动
        webView.setVerticalScrollBarEnabled(true);//允许垂直滚动
        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return false;
            }
        });
        String id = getIntent().getStringExtra("id");
        presenter = new MessageDetailPresenter(this);
        presenter.getMsgDetail(id);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void getMsgDetailSuccess(MsgDetailBean bean) {
        if (bean.data != null ){
            tvTime.setText(bean.data.date);
            tvTitle.setText(bean.data.push_title);
            showWebViewData(bean.data.push_content);
        }
    }

    @Override
    public void getMsgDetailFail() {

    }
    private void showWebViewData(String data) {
        String html = "";
        String css = "<style type=\"text/css\"> img {" +
                "width:100%;" +//限定图片宽度填充屏幕
                "height:auto;" +//限定图片高度自动
                "}" +
                "body {" +
               /* "margin-right:10px;" +//限定网页中的文字右边距为15px(可根据实际需要进行行管屏幕适配操作)
                "margin-left:10px;" +//限定网页中的文字左边距为15px(可根据实际需要进行行管屏幕适配操作)
                "margin-top:10px;" +//限定网页中的文字上边距为15px(可根据实际需要进行行管屏幕适配操作)
                "font-size:40px;" +//限定网页中文字的大小为40px,请务必根据各种屏幕分辨率进行适配更改*/
                "word-wrap:break-word;" +//允许自动换行(汉字网页应该不需要这一属性,这个用来强制英文单词换行,类似于word/wps中的西文换行)
                "}" +
                "</style>";
        html = "<html><header>" + css + "</header>" + data + "</html>";
        webView.loadData(getWebContent(html), "text/html; charset=UTF-8", null);
    }

    private String getWebContent(String content) {
        String css = "<style>.box{ font-size:40px ;color:#676767 } </style>";
        css = css + "<body><div class = 'box'>" + content + "</div></body";
        return css;
    }
}

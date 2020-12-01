package com.newsuper.t.consumer.function.vip.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.VipCardInfoBean;
import com.newsuper.t.consumer.function.vip.inter.IVipCardView;
import com.newsuper.t.consumer.function.vip.presenter.VipCardPresenter;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class VipCardDetailActivity extends BaseActivity implements IVipCardView {


    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.iv_card)
    ImageView ivCard;
    @BindView(R.id.loading_view)
    RefreshThirdStepView loadingView;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.webview_info)
    WebView webviewInfo;
    private VipCardPresenter presenter;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    closeLoadingView();
                    break;
            }
        }
    };

    //加载动画
    private AnimationDrawable animationDrawable;
    Runnable anim = new Runnable() {
        @Override
        public void run() {
            animationDrawable.start();
        }
    };

    @Override
    public void initData() {
        presenter = new VipCardPresenter(this);
    }

    //正在加载
    private void showLoadingView() {
        if (null != llFail) {
            llFail.setVisibility(View.GONE);
        }
        llLoading.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        handler.post(anim);
    }

    //关闭加载动画
    private void closeLoadingView() {
        if (null != animationDrawable) {
            animationDrawable.stop();
        }
        if (null != llLoading) {
            llLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_card_detail);
        ButterKnife.bind(this);

        animationDrawable = (AnimationDrawable) loadingView.getBackground();
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("会员卡详情");
        mToolbar.setMenuText("");
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });

        //加载会员卡详情
        showLoadingView();
        presenter.loadDetail(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void loadCardInfo(VipCardInfoBean cardInfo) {

        if (null != cardInfo) {
            //加载网络图片
            String picUrl = cardInfo.data.pic;
            if (!picUrl.startsWith("http")) {
                picUrl = "https://img.lewaimai.com" + picUrl;
            }
            UIUtils.glideAppLoad(this,picUrl,R.mipmap.vipcard,ivCard);
        }
        String html="";
        String css = "<style type=\"text/css\"> img {" +
                "width:100%;" +//限定图片宽度填充屏幕
                "height:auto;" +//限定图片高度自动
                "}" +
                "body {" +
                "margin-right:10px;" +//限定网页中的文字右边距为15px(可根据实际需要进行行管屏幕适配操作)
                "margin-left:10px;" +//限定网页中的文字左边距为15px(可根据实际需要进行行管屏幕适配操作)
                "margin-top:10px;" +//限定网页中的文字上边距为15px(可根据实际需要进行行管屏幕适配操作)
                "font-size:40px;" +//限定网页中文字的大小为40px,请务必根据各种屏幕分辨率进行适配更改
                "word-wrap:break-word;"+//允许自动换行(汉字网页应该不需要这一属性,这个用来强制英文单词换行,类似于word/wps中的西文换行)
                "}" +
                "</style>";
        html = "<html><header>" + css + "</header>" + cardInfo.data.member_privilege + "</html>";
        WebSettings webSettings = webviewInfo.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webviewInfo.loadData(html, "text/html; charset=UTF-8", null);

        //关闭动画
        closeLoadingView();
    }

    @Override
    public void loadFail() {
        if (null != animationDrawable) {
            animationDrawable.stop();
        }
        llFail.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);

    }


    @OnClick({R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                llFail.setVisibility(View.GONE);
                //加载会员卡详情
                showLoadingView();
                presenter.loadDetail(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

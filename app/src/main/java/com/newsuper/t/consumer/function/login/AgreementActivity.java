package com.newsuper.t.consumer.function.login;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.AgreementBean;
import com.xunjoy.lewaimai.consumer.function.login.internal.IAgreementView;
import com.xunjoy.lewaimai.consumer.function.login.presenter.AgreementPresenter;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgreementActivity extends BaseActivity implements IAgreementView{
    @BindView(R.id.web_agreement)
    WebView webAgreement;
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    AgreementPresenter presenter;
    String from = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);
        from = getIntent().getStringExtra("type");
        toolbar.setMenuText("");
        if ("0".equals(from)){
            toolbar.setTitleText("用户协议");
        }else {
            toolbar.setTitleText("隐私政策");
        }
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }
            @Override
            public void onMenuClick() {

            }
        });

        webAgreement.getSettings().setUseWideViewPort(true);
        webAgreement.getSettings().setLoadWithOverviewMode(true);
        webAgreement.getSettings().setDefaultTextEncodingName("UTF-8");
        webAgreement.getSettings().setJavaScriptEnabled(true);
        webAgreement.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setProgress(newProgress);

            }
        });
        presenter.getData(Const.ADMIN_ID,RetrofitUtil.ADMIN_APP_ID);
    }
    private String getWebContent(String content){
        String css = "<style>.box{ font-size:40px ;color:#333333 ;margin:30px} </style>";
        css = css +"<body><div class = 'box'>" +content+"</div></body";

        return css;
    }
    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        presenter = new AgreementPresenter(this);
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }

    @Override
    public void loadData(AgreementBean bean) {
        if (bean.data != null){
            String s = "";
            if ("0".equals(from)){
                s = bean.data.user_protocol;
            }else {
                s = bean.data.user_privacy_policy;
            }
            if (!StringUtils.isEmpty(s)){
                webAgreement.loadData(getWebContent(s),"text/html; charset=UTF-8", null);
            }

        }
    }

    @Override
    public void loadFail() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webAgreement.clearCache(true);
        webAgreement = null;
    }
}

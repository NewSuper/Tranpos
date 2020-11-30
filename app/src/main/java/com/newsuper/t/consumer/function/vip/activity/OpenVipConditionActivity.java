package com.newsuper.t.consumer.function.vip.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.VipTopInfoBean;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class OpenVipConditionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.webview_condition)
    WebView webviewCondition;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.ll_charge)
    LinearLayout llCharge;
    public static OpenVipConditionActivity instance;

    private VipTopInfoBean.VipInfoData vipInfoData;
    @Override
    public void initData() {
        instance=this;
        vipInfoData=(VipTopInfoBean.VipInfoData)getIntent().getSerializableExtra("condition");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_open_vip_condition);
        ButterKnife.bind(this);

        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("开通会员");
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

        //开通会员条件
        webviewCondition.loadData(vipInfoData.open_condition, "text/html; charset=UTF-8", null);
       if("1".equals(vipInfoData.is_userinfo_automember)){
           llInfo.setVisibility(View.VISIBLE);
       }
        if("1".equals(vipInfoData.is_balance)){
            llCharge.setVisibility(View.VISIBLE);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @OnClick({R.id.ll_info, R.id.ll_charge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_info:
                startActivity(new Intent(this,CommitVipInfoActivity.class));
                break;
            case R.id.ll_charge:
                startActivity(new Intent(this,ChargeOpenVipActivity.class));
                break;
        }
    }
}

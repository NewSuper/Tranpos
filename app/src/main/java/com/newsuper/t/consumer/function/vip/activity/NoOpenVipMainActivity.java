package com.newsuper.t.consumer.function.vip.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.VipInfoBean;
import com.newsuper.t.consumer.bean.VipTopInfoBean;
import com.newsuper.t.consumer.function.vip.inter.IVipInfoView;
import com.newsuper.t.consumer.function.vip.presenter.VipTopInfoPresenter;
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

public class NoOpenVipMainActivity extends BaseActivity implements IVipInfoView {


    @BindView(R.id.ll_vip)
    LinearLayout llVip;
    @BindView(R.id.ll_card_detail)
    LinearLayout llCardDetail;
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.loading_view)
    RefreshThirdStepView loadingView;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.iv_card)
    ImageView ivCard;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.tv_card_name)
    TextView tvCardName;
    @BindView(R.id.tv_card_num)
    TextView tvCardNum;
    private VipTopInfoPresenter presenter;
    private VipTopInfoBean.VipInfoData vipInfoData;
    public static NoOpenVipMainActivity instance;


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
        instance=this;
        presenter = new VipTopInfoPresenter(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_no_vip_main);
        ButterKnife.bind(this);

        animationDrawable = (AnimationDrawable) loadingView.getBackground();
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("会员中心");
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

        //加载会员卡信息
        loadCardInfo();
    }

    private void loadCardInfo() {
        showLoadingView();
        presenter.showVipInfo(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.ll_vip, R.id.ll_card_detail, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_vip:
                Intent intent = new Intent(this, OpenVipConditionActivity.class);
                intent.putExtra("condition", vipInfoData);
                startActivity(intent);
                break;
            case R.id.ll_card_detail:
                Intent detailIntent = new Intent(this, VipCardDetailActivity.class);
                startActivity(detailIntent);
                break;
            case R.id.btn_ok:
                llFail.setVisibility(View.GONE);
                loadCardInfo();
                break;
        }
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void loadVipTopInfo(VipTopInfoBean vipInfo) {
        //加载网络图片
        String picUrl = vipInfo.data.pic;
        if (!picUrl.startsWith("http")) {
            picUrl = "https://img.lewaimai.com" + picUrl;
        }
        if ("0".equals(vipInfo.data.is_userinfo_automember) && "0".equals(vipInfo.data.is_balance)) {
            llVip.setVisibility(View.GONE);
        }
        tvCardName.setText(vipInfo.data.card_name);
        if(!TextUtils.isEmpty(vipInfo.data.num)){
            tvCardNum.setText("NO."+vipInfo.data.num);
        }
        UIUtils.glideAppLoad(this,picUrl,R.mipmap.vipcard,ivCard);
        vipInfoData = vipInfo.data;
        //关闭动画
        closeLoadingView();
    }

    @Override
    public void loadVipInfo(VipInfoBean vipInfo) {

    }

    @Override
    public void loadFail() {
        if (null != animationDrawable) {
            animationDrawable.stop();
        }
        llFail.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

}

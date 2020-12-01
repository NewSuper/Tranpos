package com.newsuper.t.consumer.function.vip.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class VipMainActivity extends BaseActivity implements IVipInfoView {
    public static final int VIP_FREEZE_CODE = 1233;
    public static final int QRCODE_PAY_CODE = 1222;
    public static final int VIPCHARGE = 100;
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
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.ll_balance)
    LinearLayout llBalance;
    @BindView(R.id.ll_look_info)
    LinearLayout llLookInfo;
    @BindView(R.id.ll_charge)
    LinearLayout llCharge;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.ll_freeze_vip)
    LinearLayout llFreezeVip;
    @BindView(R.id.ll_card_detail)
    LinearLayout llCardDetail;
    @BindView(R.id.tv_card_name)
    TextView tvCardName;
    @BindView(R.id.tv_card_num)
    TextView tvCardNum;
    @BindView(R.id.tv_vip_level)
    TextView tvVipLevel;
    @BindView(R.id.ll_vip_level)
    LinearLayout llVipLevel;
    private VipTopInfoPresenter presenter;
    private String freeze;
    private String member_url, phone;
    private boolean isShowCharge = false;

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
        presenter = new VipTopInfoPresenter(this);

    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_vip_main);
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
        loadVipInfo();
    }

    private void loadVipInfo() {
        showLoadingView();
        presenter.showVipInfo(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
    }

    @Override
    public void loadVipTopInfo(VipTopInfoBean vipInfo) {
        //加载网络图片
        String picUrl = vipInfo.data.pic;
        if (!picUrl.startsWith("http")) {
            picUrl = "https://img.lewaimai.com" + picUrl;
        }
        UIUtils.glideAppLoad(this, picUrl, R.mipmap.vipcard, ivCard);
        if (!TextUtils.isEmpty(vipInfo.data.balance)) {
            tvBalance.setText(FormatUtil.numFormat(vipInfo.data.balance));
        }
        tvCardName.setText(vipInfo.data.card_name);
        if (!TextUtils.isEmpty(vipInfo.data.num)) {
            tvCardNum.setText("NO." + vipInfo.data.num);
        }
        if (!TextUtils.isEmpty(vipInfo.data.grade_nickname)) {
            tvVipLevel.setText(vipInfo.data.grade_nickname);
        }
        freeze = vipInfo.data.freeze;
        member_url = vipInfo.data.pic;
        phone = vipInfo.data.hotline;
        //关闭动画
        closeLoadingView();
        if ("1".equals(vipInfo.data.freeze)) {
            Intent fIntent = new Intent(this, FreezeVipActivity.class);
            fIntent.putExtra("freeze", freeze);
            fIntent.putExtra("member_url", member_url);
            fIntent.putExtra("phone", phone);
            startActivityForResult(fIntent, VIP_FREEZE_CODE);
            finish();
        }
        if ("1".equals(vipInfo.data.is_onlinepay)) {
            llCharge.setVisibility(View.VISIBLE);
            isShowCharge = true;
        } else {
            llCharge.setVisibility(View.GONE);
            isShowCharge = false;
        }
    }

    @Override
    public void loadVipInfo(VipInfoBean vipInfo) {

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


    @OnClick({R.id.ll_card_detail, R.id.btn_ok, R.id.ll_balance, R.id.ll_charge, R.id.ll_info, R.id.ll_pay, R.id.ll_freeze_vip, R.id.ll_look_info, R.id.ll_vip_level})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_card_detail:
                Intent intent = new Intent(this, VipCardDetailActivity.class);
                intent.putExtra("from_type", "vip");
                startActivity(intent);
                break;
            case R.id.btn_ok:
                llFail.setVisibility(View.GONE);
                loadVipInfo();
                break;
            case R.id.ll_balance:
                //会员余额
                break;
            case R.id.ll_look_info:
                //查看会员明细
                startActivity(new Intent(this, BalanceDetailActivity.class));
                break;
            case R.id.ll_charge:
                //会员充值
                startActivityForResult(new Intent(this, VipChargeActivity.class), VIPCHARGE);
                break;
            case R.id.ll_info:
                //会员信息
                Intent editIntent = new Intent(this, EditVipInfoActivity.class);
                editIntent.putExtra("flag_from", "vip");
                startActivity(editIntent);
                break;
            //付款二维码
            case R.id.ll_pay:
                Intent qIntent = new Intent(this, QRCodePayActivity.class);
                startActivityForResult(qIntent, QRCODE_PAY_CODE);
                break;
            case R.id.ll_freeze_vip:
                Intent fIntent = new Intent(this, FreezeVipActivity.class);
                fIntent.putExtra("freeze", freeze);
                fIntent.putExtra("member_url", member_url);
                fIntent.putExtra("phone", phone);
                startActivityForResult(fIntent, VIP_FREEZE_CODE);
                //冻结会员
                break;
            case R.id.ll_vip_level:
                Intent vipLevelIntent = new Intent(this,VipLevelActivity.class);
                vipLevelIntent.putExtra("isShowCharge",isShowCharge);
                startActivityForResult(vipLevelIntent, VIPCHARGE);
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
    public void loadFail() {
        if (null != animationDrawable) {
            animationDrawable.stop();
        }
        llFail.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == VIP_FREEZE_CODE) {
                setResult(RESULT_OK);
                finish();
            }
            if (requestCode == QRCODE_PAY_CODE) {
                presenter.showVipInfo(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
            }
            if (requestCode == VIPCHARGE) {
                presenter.showVipInfo(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
            }
        }
    }

}

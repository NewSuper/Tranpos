package com.newsuper.t.consumer.function.vip.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.VipChargeBean;
import com.newsuper.t.consumer.bean.VipChargeInfoBean;
import com.newsuper.t.consumer.bean.VipPayStatusBean;
import com.newsuper.t.consumer.function.pay.alipay.Alipay;
import com.newsuper.t.consumer.function.vip.adapter.ChargeStandardAdapter;
import com.newsuper.t.consumer.function.vip.inter.ISelectChargeMoney;
import com.newsuper.t.consumer.function.vip.inter.IVipChargeOpenView;
import com.newsuper.t.consumer.function.vip.inter.IVipChargeView;
import com.newsuper.t.consumer.function.vip.inter.IVipPayStatusView;
import com.newsuper.t.consumer.function.vip.presenter.ChargeStatusPresenter;
import com.newsuper.t.consumer.function.vip.presenter.VipChargeInfoPresenter;
import com.newsuper.t.consumer.function.vip.presenter.VipChargePresenter;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.WXResult;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.GridDivider;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;
import com.newsuper.t.consumer.wxapi.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChargeOpenVipActivity extends BaseActivity implements ISelectChargeMoney, IVipChargeOpenView, IVipChargeView,IVipPayStatusView {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
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
    @BindView(R.id.rv_charge)
    RecyclerView rvCharge;
    @BindView(R.id.tv_charge_standard)
    TextView tvChargeStandard;
    @BindView(R.id.ll_weixin)
    LinearLayout llWeixin;
    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;
    @BindView(R.id.iv_zhifubao)
    ImageView ivZhifubao;
    @BindView(R.id.ll_zhifubao)
    LinearLayout llZhifubao;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private VipChargeInfoPresenter openPresenter;
    private VipChargePresenter presenter;
    private ArrayList<VipChargeInfoBean.ChargeSend> chargeList;
    private String payType;
    private String pay_money;
    private VipChargeBean.WeiXinParams parameters;
    private IWXAPI msgApi;//微信支付
    private VipChargeInfoBean chargeInfo;
    private LoadingDialog2 loadingDialog;
    private boolean isCheckStatus;
    private String order_no;
    private ChargeStatusPresenter statusPresenter;

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
    protected void onResume() {
        super.onResume();
        //微信支付成功
        if (WXResult.errCode == 0){
            //做订单状态轮询
            checkOrderStatus();
            WXResult.errCode = -60;

        }
    }

    private void checkOrderStatus(){
        isCheckStatus=true;
        showLoadingDialog();
        statusPresenter.checkChargeStatus(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,order_no,"6");
    }

    @Override
    public void checkStatus(VipPayStatusBean status) {
        if(status.data.status==0){
            isCheckStatus=false;
            //付款成功
            startActivity(new Intent(this,ChargeOpenSuccessActivity.class));

            sendBroadcast(new Intent("update_custome_info"));
            NoOpenVipMainActivity.instance.finish();
            OpenVipConditionActivity.instance.finish();
            this.finish();
        }

    }

    @Override
    public void initData() {
        msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        msgApi.registerApp(Constants.APP_ID);
        chargeList=new ArrayList<>();
        openPresenter = new VipChargeInfoPresenter(this);
        presenter = new VipChargePresenter(this);
        statusPresenter = new ChargeStatusPresenter(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_charge_open_vip);
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


        animationDrawable = (AnimationDrawable) loadingView.getBackground();
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        GridDivider gridDivider=new GridDivider(24,20, Color.WHITE,false);
        rvCharge.addItemDecoration(gridDivider);
        rvCharge.setLayoutManager(manager);
        //请求开通充值页面的数据
        showLoadingView();
        openPresenter.openCharge(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
    }

    @Override
    public void chargeOpenInfo(VipChargeInfoBean chargeInfo) {
        //关闭动画
        closeLoadingView();
        chargeList.clear();
        this.chargeInfo=chargeInfo;
        if(null!=chargeInfo.data.info.upsend&&chargeInfo.data.info.upsend.size()>0){
            for (VipChargeInfoBean.ChargeSend chargeSend : chargeInfo.data.info.upsend) {
                if (Float.parseFloat(chargeSend.money) < Float.parseFloat(chargeInfo.data.info.balance_value)) {
                    chargeSend.status = "0";
                }else{
                    chargeSend.status="2";
                }
                chargeList.add(chargeSend);
            }
        }
        ChargeStandardAdapter adapter = new ChargeStandardAdapter(this, chargeList, chargeInfo);
        adapter.setISelectChargeMoney(this);
        rvCharge.setAdapter(adapter);

        tvChargeStandard.setText(chargeInfo.data.info.balance_value+"元");

        if (StringUtils.isEmpty(chargeInfo.data.info.weixinzhifu)){
            llWeixin.setVisibility(View.GONE);
        }
        if (StringUtils.isEmpty(chargeInfo.data.info.zhifubao)){
            llZhifubao.setVisibility(View.GONE);
        }

    }


    @Override
    public void selectChargeMoney(VipChargeInfoBean.ChargeSend money) {
        pay_money=money.money;
    }


    @Override
    public void dialogDismiss() {
        if (loadingDialog !=null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String s) {
        UIUtils.showToast(s);
    }

    @Override
    public void chargeVip(VipChargeBean vipInfo) {
        //充值提交成功回调相关支付接口
        if( null!= vipInfo ){
            order_no = vipInfo.data.order_no;
            if("1".equals(payType)){
                //微信支付
                if (vipInfo.data.zhifuParameters != null) {
                    parameters = vipInfo.data.zhifuParameters;
                    weixinPay(parameters);
                }
            }else if("2".equals(payType)){
                //支付宝支付
                alipay(vipInfo.data.zhifubaoParameters);
            }
        }
    }

    //微信支付
    private void weixinPay(VipChargeBean.WeiXinParams parameters) {
        msgApi.registerApp(Constants.APP_ID);
        if (msgApi.isWXAppInstalled()) {
            PayReq req = new PayReq();
            req.appId = parameters.appid;
            req.partnerId = parameters.partnerid;//微信支付分配的商户号
            req.prepayId = parameters.prepayid;//微信返回的支付交易会话ID
            req.nonceStr = parameters.noncestr;//32位随机字符
            req.timeStamp = parameters.timestamp;// 10位时间戳
            req.packageValue = "Sign=WXPay";//暂填写固定值Sign=WXPay
            req.sign = parameters.sign; //签名
            msgApi.registerApp(Constants.APP_ID);
            msgApi.sendReq(req);
        } else {
            UIUtils.showToast("未安装微信！");
        }
    }

    //支付宝支付
    private void alipay(final String payInfo) {
        Alipay alipay = Alipay.getInstance(this);
        alipay.pay(payInfo);
        alipay.setAlipayPayListener(new Alipay.AlipayPayListener() {
            @Override
            public void onPaySuccess(String statusCode, String msg) {
                switch (statusCode) {
                    //支付成功
                    case "9000":
                        //正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "8000":
                        //支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "6004":
                        checkOrderStatus();
                        break;
                    //订单支付失败
                    case "4000":
                        ToastUtil.showTosat(ChargeOpenVipActivity.this,"支付失败");
                        break;
                    //重复请求
                    case "5000":
                        break;
                    //用户中途取消
                    case "6001":
                        ToastUtil.showTosat(ChargeOpenVipActivity.this,"支付取消");
                        break;
                    //网络连接出错
                    case "6002":
                        ToastUtil.showTosat(ChargeOpenVipActivity.this,"网络错误，支付失败");
                        break;

                }
            }

            @Override
            public void onPayFail(String msg) {
                ToastUtil.showTosat(ChargeOpenVipActivity.this,"网络错误，支付失败");
            }
        });
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ok, R.id.ll_weixin, R.id.ll_zhifubao,R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                llFail.setVisibility(View.GONE);
                //请求开通充值页面的数据
                showLoadingView();
                openPresenter.openCharge(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
                break;
            case R.id.ll_weixin:
                payType="1";
                changeSelectView();
                break;
            case R.id.ll_zhifubao:
                payType="2";
                changeSelectView();
                break;
            case R.id.tv_commit:
                if(TextUtils.isEmpty(pay_money)){
                    UIUtils.showToast("请选择充值金额");
                    return;
                }
                String type="";
                if("1".equals(payType)){
                    //微信支付
                    type=chargeInfo.data.info.weixinzhifu;
                }else if("2".equals(payType)){
                    //支付宝支付
                    type=chargeInfo.data.info.zhifubao;
                }
                showLoadingDialog();
                presenter.vipCharge(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,pay_money,type,null);
                break;

        }
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(this, R.style.transparentDialog);
        }
        loadingDialog.show();
    }

    private void changeSelectView(){
        switch (payType){
            case "1":
                ivWeixin.setImageResource(R.mipmap.vip_icon_select);
                ivZhifubao.setImageResource(R.mipmap.vip_icon_no_select);
                break;
            case "2":
                ivWeixin.setImageResource(R.mipmap.vip_icon_no_select);
                ivZhifubao.setImageResource(R.mipmap.vip_icon_select);
                break;
        }
    }
}
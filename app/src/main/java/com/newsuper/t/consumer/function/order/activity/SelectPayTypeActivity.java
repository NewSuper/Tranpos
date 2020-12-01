package com.newsuper.t.consumer.function.order.activity;

import android.os.Bundle;
import android.util.Log;
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
import com.newsuper.t.consumer.bean.ContinuePayResultBean;
import com.newsuper.t.consumer.bean.ContinuePayTypeBean;
import com.newsuper.t.consumer.function.inter.IContinuePay;
import com.newsuper.t.consumer.function.order.presenter.ContinuePayPresenter;
import com.newsuper.t.consumer.function.order.request.ContinuePayRequest;
import com.newsuper.t.consumer.function.pay.alipay.Alipay;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.utils.WXResult;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.wxapi.Constants;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectPayTypeActivity extends BaseActivity implements View.OnClickListener,IContinuePay {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_select_wei)
    ImageView ivSelectWei;
    @BindView(R.id.iv_select_alipay)
    ImageView ivSelectAlipay;
    @BindView(R.id.ll_alipay)
    LinearLayout llAlipay;
    @BindView(R.id.ll_weixin)
    LinearLayout llWeixin;
    @BindView(R.id.btn_pay)
    Button btnPay;
    private int payType = 0;// 0 微信 1支付宝
    private String pay_weixin="";
    private String pay_zhifubao="";
    private String order_id;
    private ContinuePayPresenter continuePayPresenter;
    private String token;
    private LoadingDialog2 loadingDialog;
    private ContinuePayResultBean.ZhiFuParameters parameters;
    private IWXAPI msgApi;//微信支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay_type);
        ButterKnife.bind(this);
        toolbar.setTitleText("选择支付方式");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        llAlipay.setOnClickListener(this);
        llWeixin.setOnClickListener(this);
        btnPay.setOnClickListener(this);

        continuePayPresenter = new ContinuePayPresenter(this);
        String name = getIntent().getStringExtra("shop_name");
        String money  = getIntent().getStringExtra("money");
        order_id  = getIntent().getStringExtra("order_id");
        String shop_id  = getIntent().getStringExtra("shop_id");
        token = SharedPreferencesUtil.getToken();
        tvShopName.setText(name);
        tvMoney.setText(money);
        msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        HashMap<String, String> payParams = ContinuePayRequest.getPayRequest(token, Const.ADMIN_ID, shop_id,Const.ADMIN_APP_ID, "app");
        continuePayPresenter.getPayType(UrlConst.CONTINUE_PAY_TYPE, payParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (WXResult.errCode == 0){
            this.setResult(RESULT_OK);
            this.finish();
        }
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(this, R.style.transparentDialog);
        }
        loadingDialog.show();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_alipay:
                if (payType == 0){
                    payType = 1;
                    ivSelectAlipay.setVisibility(View.VISIBLE);
                    ivSelectWei.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.ll_weixin:
                if (payType == 1){
                    payType = 0;
                    ivSelectAlipay.setVisibility(View.INVISIBLE);
                    ivSelectWei.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.btn_pay:
                String type = "";
                if(payType == 0){
                    type = pay_weixin;
                }else{
                    type = pay_zhifubao;
                }
                showLoadingDialog();
                HashMap<String, String> payParams = ContinuePayRequest.continuePayRequest(token, Const.ADMIN_ID, order_id, type);
                continuePayPresenter.loadData(UrlConst.CONTINUE_PAY_ORDER, payParams);
                break;
        }
    }

    @Override
    public void dialogDismiss() {
        if (null != loadingDialog) {
            loadingDialog.cancel();
        }
    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void getPayParams(ContinuePayResultBean bean) {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
        if (0 == payType) {
            if (bean.data.zhifuParameters != null) {
                parameters = bean.data.zhifuParameters;
                weixinPay(parameters);
            }
        } else if (1 == payType) {
            alipay(bean.data.zhifubaoParameters);
        }
    }

    @Override
    public void getPayType(ContinuePayTypeBean bean) {
        if (bean.data != null){
            pay_weixin = bean.data.weixinzhifuType;
            pay_zhifubao = bean.data.zhifubaozhifuType;
        }
    }

    //微信支付
    private void weixinPay(ContinuePayResultBean.ZhiFuParameters parameters) {
        msgApi.registerApp(Constants.APP_ID);
        if (msgApi.isWXAppInstalled()) {
            Log.i("weixinPay", Constants.APP_ID + " === " + parameters.appid);
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
                    //订单支付成功
                    case "9000":
                        //正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "8000":
                        //支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "6004":
//                        if (!StringUtils.isEmpty(order_id)) {
//                            Intent intent = new Intent(SelectPayTypeActivity.this, MyOrderActivity.class);
//                            intent.putExtra("order_id", order_id);
//                            intent.putExtra("pay_status", "success");
//                            startActivity(intent);
//                            order_id = "";
//                            return;
//                        }
                        SelectPayTypeActivity.this.setResult(RESULT_OK);
                        SelectPayTypeActivity.this.finish();
                        break;
                    //订单支付失败
                    case "4000":
                        ToastUtil.showTosat(SelectPayTypeActivity.this, "支付失败");
                        break;
                    //重复请求
                    case "5000":
                        break;
                    //用户中途取消
                    case "6001":
//                        ToastUtil.showTosat(SelectPayTypeActivity.this, "支付取消");
//                        if (!StringUtils.isEmpty(order_id)) {
//                            Intent intent = new Intent(SelectPayTypeActivity.this, SelectPayTypeActivity.class);
//                            intent.putExtra("order_id", order_id);
//                            intent.putExtra("pay_status", "fail");
//                            intent.putExtra("pay_param", payInfo);
//                            startActivity(intent);
//                            order_id = "";
//                            return;
//                        }
                        break;
                    //网络连接出错0
                    case "6002":
                        ToastUtil.showTosat(SelectPayTypeActivity.this, "网络错误，支付失败");
                        break;
                }
            }

            @Override
            public void onPayFail(String msg) {

            }
        });
    }
}

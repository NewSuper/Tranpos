package com.newsuper.t.consumer.function.distribution;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.OrderPayResultBean;
import com.newsuper.t.consumer.bean.PayOrderBean;
import com.newsuper.t.consumer.function.distribution.adapter.SelectPayTypeAdapter;
import com.newsuper.t.consumer.function.distribution.internal.IPayOrderView;
import com.newsuper.t.consumer.function.distribution.presenter.PayOrderPresenter;
import com.newsuper.t.consumer.function.pay.alipay.Alipay;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.WXResult;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.ListViewForScrollView;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.LoadingDialog3;
import com.newsuper.t.consumer.wxapi.Constants;
import com.newsuper.t.consumer.wxapi.WXPayEntryActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 支付跑腿订单
 */

public class PayOrderActivity extends BaseActivity implements IPayOrderView{
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.pay_listview)
    ListViewForScrollView payListview;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    public static final String WHERE_FROM = "where_from";
    public static final String WHERE_FROM_ORDER_PAY = "where_from_order_pay";
    public static final String WHERE_FROM_ORDER_SMALL_FEE = "where_from_order_small_fee";
    private static final int SUCCESS_CODE = 4555;
    private SelectPayTypeAdapter typeAdapter;
    private ArrayList<String> types;
    private String allPrice,order_id;
    private PayOrderPresenter payOrderPresenter;
    private String paytype;
    private LoadingDialog3 loadingDialog;
    private String where_from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.bind(this);

        if (loadingDialog == null){
            loadingDialog = new LoadingDialog3(PayOrderActivity.this);
        }
        toolbar.setTitleText("支付订单");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                showDialog();
            }

            @Override
            public void onMenuClick() {

            }
        });
        where_from = getIntent().getStringExtra(WHERE_FROM);
        String title = getIntent().getStringExtra("title");
        String time = getIntent().getStringExtra("time");
        order_id = getIntent().getStringExtra("order_id");
        allPrice = getIntent().getStringExtra("pay_money");
        tvType.setText(title);
        tvPay.setText("确认支付￥" + allPrice + "元");
        tvMoney.setText("￥" + allPrice + "元");
        if (StringUtils.isEmpty(time)){
            rlTime.setVisibility(View.GONE);
        }
        tvTime.setText(time);
        if (types == null) {
            types = new ArrayList<>();
        }
        ArrayList<String> list = getIntent().getStringArrayListExtra("pay_type");

        if (WHERE_FROM_ORDER_PAY.equals(where_from)){
            for(String s : list){
                if (s.equals("3") || s.equals("4")){
                    types.add(s);
                }
            }
        }else {
            types.addAll(list);
        }
        typeAdapter = new SelectPayTypeAdapter(this, types);
        payListview.setAdapter(typeAdapter);
        payListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = types.get(position);
                typeAdapter.setSelectType(type);
                paytype = types.get(position);
            }
        });
        llPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (typeAdapter.getSelectType().equals("1")){
                    double limit  = FormatUtil.numDouble(limit_money);
                    double all = FormatUtil.numDouble(allPrice);
                    if (limit <= all){
                        ToastUtil.showTosat(PayOrderActivity.this,"支付金额已超过货到付款限制额度（"+limit+"元），请选择其他支付方式！");
                        return;
                    }
                }*/
                loadingDialog.show();
                if (WHERE_FROM_ORDER_PAY.equals(where_from)){
                    payOrderPresenter.payOrder(order_id,typeAdapter.getSelectType());
                }else if (WHERE_FROM_ORDER_SMALL_FEE.equals(where_from)){
                    payOrderPresenter.paySmallFee(order_id,allPrice);
                }

            }
        });
        payOrderPresenter = new PayOrderPresenter(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
    private AlertDialog dialog;

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_tip, null);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                payOrderPresenter.payOrder(paytype,order_id);
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        showDialog();
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }

    @Override
    public void paySuccess(PayOrderBean bean) {
        loadingDialog.dismiss();
        if (bean.data == null){
            return;
        }
        if ("success".equals(bean.data.status)){
            paySuccess();
        }else if ("paying".equals(bean.data.status)){
            if (bean.data.zhifuParameters != null && !StringUtils.isEmpty(bean.data.zhifuParameters.partnerid)){
                weixinPay(bean.data.zhifuParameters);
            }else if (!StringUtils.isEmpty(bean.data.zhifubaoParameters)){
                alipay(bean.data.zhifubaoParameters);
            }
        }
    }
    private void paySuccess(){
        Intent intent = new Intent(PayOrderActivity.this,PaySuccessActivity.class);
        if (WHERE_FROM_ORDER_PAY.equals(where_from)){
            intent.putExtra("where_from","0");
        }else if (WHERE_FROM_ORDER_SMALL_FEE.equals(where_from)){
            intent.putExtra("where_from","1");
        }
        intent.putExtra("pay_type",typeAdapter.getSelectType());
        intent.putExtra("pay_money",allPrice+"");
        intent.putExtra("order_id",order_id);
        startActivityForResult(intent,SUCCESS_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (typeAdapter.getSelectType().equals("3")){
            //微信支付成功（0） 或取消（-2）
            if (WXResult.errCode == 0){
                WXResult.errCode = -2222;
                paySuccess();
            }else if (WXResult.errCode == -2){
                WXResult.errCode = -2222;
//            ToastUtil.showTosat(this,"支付取消");
            }
        }

    }
    private IWXAPI msgApi;//微信支付
    //微信支付
    private void weixinPay(OrderPayResultBean.ZhiFuParameters parameters) {
        msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
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
                    //订单支付成功
                    case "9000":
                        //正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "8000":
                        //支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "6004":
                        paySuccess();
                        break;
                    //订单支付失败
                    case "4000":
                        ToastUtil.showTosat(PayOrderActivity.this,"支付失败");
                        break;
                    //重复请求
                    case "5000":
                        break;
                    //用户中途取消
                    case "6001":
                        ToastUtil.showTosat(PayOrderActivity.this,"支付取消");
                        break;
                    //网络连接出错
                    case "6002":
                        ToastUtil.showTosat(PayOrderActivity.this,"网络错误，支付失败");
                        break;
                }
            }

            @Override
            public void onPayFail(String msg) {
                ToastUtil.showTosat(PayOrderActivity.this,"网络错误，支付失败");
            }
        });
    }
    @Override
    public void payFail() {
        loadingDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode &&requestCode == SUCCESS_CODE){
            setResult(RESULT_OK);
            finish();
        }
    }
}

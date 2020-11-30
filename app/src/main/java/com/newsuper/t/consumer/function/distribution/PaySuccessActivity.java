package com.newsuper.t.consumer.function.distribution;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.OrderInfoBean;
import com.newsuper.t.consumer.bean.PaotuiPayOrderInfo;
import com.newsuper.t.consumer.function.distribution.internal.IPayOrderSuccessView;
import com.newsuper.t.consumer.function.distribution.presenter.PayOrderSuccessPresenter;
import com.newsuper.t.consumer.function.person.activity.SignActivity;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaySuccessActivity extends BaseActivity implements IPayOrderSuccessView,View.OnClickListener{

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.tv_pay_tip)
    TextView tvPayTip;
    @BindView(R.id.ll_pay_tip)
    LinearLayout llPayTip;
    @BindView(R.id.ll_logo)
    LinearLayout ll_logo;
    private PayOrderSuccessPresenter payOrderSuccessPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        ButterKnife.bind(this);
        toolbar.setMenuText("");
        toolbar.setTitleText("支付成功");
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
        String where_from = getIntent().getStringExtra("where_from");
        String pay_type = getIntent().getStringExtra("pay_type");
        String pay_money = getIntent().getStringExtra("pay_money");
        tvPayMoney.setText("￥"+pay_money);
        switch (pay_type){
            case "1":
                if (where_from.equals("0")){
                    toolbar.setTitleText("下单成功");
                    tvPayTip.setText("下单成功");
                    llPayTip.setVisibility(View.VISIBLE);
                }else {
                    llPayTip.setVisibility(View.GONE);
                }
                tvPayType.setText("货到付款");
                break;
            case "2":
                tvPayType.setText("会员支付");
                break;
            case "3":
                tvPayType.setText("微信支付");
                break;
            case "4":
                tvPayType.setText("支付宝支付");
                break;
        }
        tvOk.setOnClickListener(this);
        ll_logo.setOnClickListener(this);
     payOrderSuccessPresenter = new PayOrderSuccessPresenter(this);
     String order_id = getIntent().getStringExtra("order_id");
     payOrderSuccessPresenter.getOrderInfo(order_id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void getPayOrderInfo(PaotuiPayOrderInfo info) {
        if (info.data != null && info.data.roulette != null){
            if ("1".equals(info.data.roulette.is_roulette)){
                showRouleteeDialog(info.data.roulette.back_url);
            }
        }
    }

    @Override
    public void getFaile() {

    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {

    }
    Dialog rouleteeDialog;
    public void showRouleteeDialog(final String url ) {
        LogUtil.log("showRouleteeDialog","000000000");
        View deleteView = View.inflate(this, R.layout.dialog_choujiang, null);
        rouleteeDialog = new Dialog(this, R.style.CenterDialogTheme2);
        rouleteeDialog.setContentView(deleteView);
        rouleteeDialog.setCanceledOnTouchOutside(false);
        deleteView.findViewById(R.id.iv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = new Intent(PaySuccessActivity.this, SignActivity.class);
                sign.putExtra("type", 3);
                sign.putExtra("url", url);
                startActivity(sign);
            }
        });
        deleteView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rouleteeDialog.dismiss();
            }
        });
        rouleteeDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                setResult(RESULT_OK);
                finish();
            break;

        }
    }
}

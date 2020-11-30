package com.newsuper.t.consumer.function.distribution.fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.OrderPayResultBean;
import com.newsuper.t.consumer.bean.PaotuiCouponBean;
import com.newsuper.t.consumer.function.distribution.adapter.SelectPayTypeAdapter;
import com.newsuper.t.consumer.function.distribution.presenter.PayOrderPresenter;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.ListViewForScrollView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public abstract class HelpBaseFragment extends Fragment {
    public static final int COUNPON_CODE = 117;
    public static final int PAY_SUCCESS_CODE = 4000;
    public String is_coupon = "0";
    public String coupon_id = "";
    public String coupon_info;
    public PaotuiCouponBean couponBean;
    public PaotuiCouponBean.CouponList coupon;
    public double couponFee = 0;
    public boolean isCanback;
    public ArrayList<PaotuiCouponBean.CouponList> nCouponLists = new ArrayList<>();
    public ArrayList<PaotuiCouponBean.CouponList> uCouponLists = new ArrayList<>();
    Dialog payOrderDialog;
    SelectPayTypeAdapter payTypeAdapter;
    IWXAPI msgApi;//微信支付
    PayOrderPresenter payOrderPresenter;
    String order_id,member_notice;
    double member_balance;
    String payPrice;
    TextView tvAllPay;

    public void showPayDialog(final ArrayList<String> list, final String totalPrice, final View.OnClickListener listener){
        if (FormatUtil.numDouble(totalPrice) == 0){
            ToastUtil.showTosat(getContext(),"金额为0，无法支付！");
            return;
        }
        payPrice = totalPrice;
        LogUtil.log("HelpBaseActivity","showPayDialog ");
        if (list == null && list.size() == 0){
            return;
        }
        if (payOrderDialog == null){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_help_pay, null);
            tvAllPay = ((TextView)view.findViewById(R.id.tv_price));
            view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payOrderDialog.dismiss();
                }
            });
            final TextView tvPay = (TextView)view.findViewById(R.id.tv_pay);
            final LinearLayout llPay = (LinearLayout) view.findViewById(R.id.ll_pay);
            llPay.setOnClickListener(listener);
            ListViewForScrollView payListView = (ListViewForScrollView)view.findViewById(R.id.pay_listview);
            payTypeAdapter = new SelectPayTypeAdapter(getContext(), list);
            payTypeAdapter.setSelectType(list.get(0));
            if ("2".equals(list.get(0))){
                if (!StringUtils.isEmpty(member_notice)){
                    tvPay.setText(member_notice);
                    llPay.setOnClickListener(null);
                    llPay.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.text_color_c5));
                }else if (FormatUtil.numDouble(payPrice) > member_balance){
                    tvPay.setText("余额不足");
                    llPay.setOnClickListener(null);
                    llPay.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.text_color_c5));
                }
            }
            payListView.setAdapter(payTypeAdapter);
            payListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String type = list.get(position);
                    if (type.equals("2")){
                        if (!StringUtils.isEmpty(member_notice)){
                            tvPay.setText(member_notice);
                            llPay.setOnClickListener(null);
                            llPay.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.text_color_c5));
                        }else if (FormatUtil.numDouble(payPrice) > member_balance){
                            tvPay.setText("余额不足");
                            llPay.setOnClickListener(null);
                            llPay.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.text_color_c5));
                        }
                    }else {
                        if (!tvPay.getText().toString().equals("确认支付")){
                            tvPay.setText("确认支付");
                            llPay.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.text_red_f8));
                            llPay.setOnClickListener(listener);
                        }

                    }
                    payTypeAdapter.setSelectType(type);
                }
            });
            payOrderDialog = DialogUtils.BottonDialog(getContext(), view);
        }
        tvAllPay.setText("￥"+payPrice);
        payOrderDialog.show();
    }
    public boolean isOverTime(String day,String time,String second){
        String f = day + " "+ time +":"+ second + ":00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(f);
            if (date.getTime() < (new Date().getTime() -  60 * 1000));{
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    abstract void weixinPay(OrderPayResultBean.ZhiFuParameters parameters);
    abstract void alipay( String payInfo);
    abstract void toOrderDetailActivity(String order_id);
    abstract void toPaySuccessActivity();
    public abstract void onBackPressed();
}

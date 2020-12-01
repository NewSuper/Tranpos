package com.newsuper.t.consumer.function.distribution.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.HelpBean;
import com.newsuper.t.consumer.bean.OrderPayResultBean;
import com.newsuper.t.consumer.bean.PaotuiCouponBean;
import com.newsuper.t.consumer.bean.PaotuiOrderBean;
import com.newsuper.t.consumer.bean.PayOrderBean;
import com.newsuper.t.consumer.bean.PriceDetailBean;
import com.newsuper.t.consumer.function.distribution.AddressBookActivity;
import com.newsuper.t.consumer.function.distribution.NearAddressListActivity;
import com.newsuper.t.consumer.function.distribution.PaotuiCouponActivity;
import com.newsuper.t.consumer.function.distribution.PaotuiOrderActivity;
import com.newsuper.t.consumer.function.distribution.PaySuccessActivity;
import com.newsuper.t.consumer.function.distribution.SaveAddressActivity;
import com.newsuper.t.consumer.function.distribution.adapter.HelpBuyTypeAdapter;
import com.newsuper.t.consumer.function.distribution.adapter.RequirementAdapter;
import com.newsuper.t.consumer.function.distribution.internal.IHelpView;
import com.newsuper.t.consumer.function.distribution.internal.IPayOrderView;
import com.newsuper.t.consumer.function.distribution.presenter.HelpPresenter;
import com.newsuper.t.consumer.function.distribution.presenter.PayOrderPresenter;
import com.newsuper.t.consumer.function.distribution.request.DistributionRequest;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.pay.alipay.Alipay;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.WXResult;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog3;
import com.newsuper.t.consumer.widget.popupwindow.AllPricePopupWindow;
import com.newsuper.t.consumer.wxapi.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class HelpSendFragment extends HelpBaseFragment implements View.OnClickListener, IHelpView, IPayOrderView {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tab_recyclerview)
    RecyclerView tabRecyclerview;

    @BindView(R.id.edt_remarks)
    EditText edtRemarks;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.edt_small_fee)
    EditText edtSmallFee;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_over_dis)
    TextView tvOverDis;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_tip)
    ImageView ivTip;
    @BindView(R.id.tv_commit_order)
    TextView tvCommitOrder;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.btn_load_again)
    Button btnLoadAgain;
    @BindView(R.id.ll_fail)
    FrameLayout llFail;
    @BindView(R.id.ll_special)
    LinearLayout llSpecial;
    private static final int SEARCH_ADDRESS_CODE_TAKE = 114;
    private static final int SEARCH_ADDRESS_CODE_TAKE_BOOK = 113;
    private static final int SEARCH_ADDRESS_CODE_SEND = 112;
    private static final int SEARCH_ADDRESS_CODE_SEND_BOOK = 111;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.tv_send_address)
    TextView tvSendAddress;
    @BindView(R.id.tv_send_name)
    TextView tvSendName;
    @BindView(R.id.tv_send_phone)
    TextView tvSendPhone;
    @BindView(R.id.ll_send_person)
    LinearLayout llSendPerson;
    @BindView(R.id.tv_address_list)
    TextView tvAddressList;
    @BindView(R.id.tv_take_address)
    TextView tvTakeAddress;
    @BindView(R.id.tv_take_name)
    TextView tvTakeName;
    @BindView(R.id.tv_take_phone)
    TextView tvTakePhone;
    @BindView(R.id.ll_take_person)
    LinearLayout llTakePerson;
    @BindView(R.id.tv_take_address_list)
    TextView tvTakeAddressList;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    private double rlat, rlng, slat, slng;
    private HelpBuyTypeAdapter helpBuyTypeAdapter;
    private RequirementAdapter requirementAdapter;
    private AllPricePopupWindow pricePopupWindow;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private HelpPresenter helpPresenter;
    private String type_id;
    private ArrayList<HelpBean.SpecialFeeBean> specialFeeBeen;
    private HelpBean.HelpData helpData;
    private LoadingDialog3 loadingDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 111:
                    loadingDialog.dismiss();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_help_send, null);
        ButterKnife.bind(this, view);
        String title = getArguments().getString("title");
        type_id = getArguments().getString("type_id");
        String la = getArguments().getString("lat");
        String ln = getArguments().getString("lng");
        String ad = getArguments().getString("address");
        rlat = StringUtils.isEmpty(la) ? 0 : Double.parseDouble(la);
        rlng = StringUtils.isEmpty(ln) ? 0 : Double.parseDouble(ln);
        tvAddressList.setOnClickListener(this);
        tvSendAddress.setOnClickListener(this);
        tvTakeAddress.setOnClickListener(this);
        tvTakeAddressList.setOnClickListener(this);
        llSendPerson.setOnClickListener(this);
        llTakePerson.setOnClickListener(this);
        llCoupon.setOnClickListener(this);
        llSpecial.setOnClickListener(this);
        llTime.setOnClickListener(this);
        ivTip.setOnClickListener(this);
        tvCommitOrder.setOnClickListener(this);
        btnLoadAgain.setOnClickListener(this);
        llFail.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        toolbar.setTitleText(title);
        toolbar.setMenuText("");
        if (isCanback) {
            toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
                @Override
                public void onBackClick() {
                    onBackPressed();
                }

                @Override
                public void onMenuClick() {

                }
            });
        } else {
            toolbar.setBackImageViewVisibility(View.INVISIBLE);
        }
        tvSendAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(sequence.toString()) || StringUtils.isEmpty(tvTakeAddress.getText().toString())) {
                    tvCommitOrder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tab_normal));
                    tvCommitOrder.setOnClickListener(null);
                } else {
                    tvCommitOrder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.text_red_f8));
                    tvCommitOrder.setOnClickListener(HelpSendFragment.this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        tvTakeAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(sequence.toString()) || StringUtils.isEmpty(tvSendAddress.getText().toString())) {
                    tvCommitOrder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tab_normal));
                    tvCommitOrder.setOnClickListener(null);
                } else {
                    tvCommitOrder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.text_red_f8));
                    tvCommitOrder.setOnClickListener(HelpSendFragment.this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtSmallFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                countAllPrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
//        final String s[] = getResources().getStringArray(R.array.send_type);
        final String s[] = new String[]{"其他","美食","文件","蛋糕","鲜花","钥匙","手机"};
        helpBuyTypeAdapter = new HelpBuyTypeAdapter(getContext(), s);
        tabRecyclerview.setAdapter(helpBuyTypeAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tabRecyclerview.setLayoutManager(manager);
        specialFeeBeen = new ArrayList<>();
        if (helpPresenter == null) {
            helpPresenter = new HelpPresenter(this);
        }
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
            llLogin.setVisibility(View.VISIBLE);
        } else {
            helpPresenter.loadData(type_id);
        }

        return view;
    }

    private Map<String, String> orderMap;

    private void commitOrder() {
        if (helpData == null) {
            return;
        }
        String address = tvSendAddress.getText().toString();
        if (StringUtils.isEmpty(address)) {
            ToastUtil.showTosat(getContext(), "请选择发货地址");
            return;
        }
        String phone = tvSendPhone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showTosat(getContext(), "请输入发货电话");
            return;
        }


        String from_name = StringUtils.isEmpty(tvSendName.getText().toString()) ? "":tvSendName.getText().toString();



        String tAddress = tvTakeAddress.getText().toString();
        if (StringUtils.isEmpty(tAddress)) {
            ToastUtil.showTosat(getContext(), "请选择收货地址");
            return;
        }
        String tPhone = tvTakePhone.getText().toString();
        if (StringUtils.isEmpty(tPhone)) {
            ToastUtil.showTosat(getContext(), "请输入收货电话");
            return;
        }
        String name = StringUtils.isEmpty(tvTakeName.getText().toString()) ? "":tvTakeName.getText().toString();
        String time = tvTime.getText().toString();
        if (StringUtils.isEmpty(time)) {
            ToastUtil.showTosat(getContext(), "请选择发货时间");
            return;
        } else {
            if (time.contains("今天")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String s = format.format(new Date());
                String arr1[] = s.split(" ");
                time = time.replace("今天", arr1[0]);
            }
            LogUtil.log("commitOrder", "time == " + time);
        }
        if ("1".equals(helpData.delivery_fee_type)) {
            double dis = rideRouteDis;
            double ra = FormatUtil.numDouble(helpData.delivery_radius) * 1000;
            LogUtil.log("commitOrder", "dsi == " + dis + " ra == " + ra);
            if (dis > ra) {
                ToastUtil.showTosat(getContext(), "已超过配送范围，请重新选择地址");
                return;
            }
        }
        String customer_memo = StringUtils.isEmpty(edtRemarks.getText().toString()) ? "" : edtRemarks.getText().toString();
        String tip = StringUtils.isEmpty(edtSmallFee.getText().toString()) ? "0" : edtSmallFee.getText().toString();
        String special = "";
        if (requirementAdapter != null ){
            special = requirementAdapter.getSpecialValue().toString();
        }
        if (special.equals("[]")) {
            special = "";
        }
        String buy_type = helpBuyTypeAdapter.getSelectType();
        String delivery_now = "立即发货".equals(time) ? "1" : "0";
        orderMap = DistributionRequest.sendRequest(SharedPreferencesUtil.getToken(),
                Const.ADMIN_ID, RetrofitUtil.ADMIN_APP_ID, type_id, "2", "1", allPrice + "",
                phone, address, slat + "", slng + "", customer_memo, tAddress, tPhone, rlng + "", rlat + "", time, delivery_now, tip, special, buy_type,is_coupon,coupon_id,name,from_name);
        showPayDialog(helpData.paytype, allPrice + "", this);


    }

    private void isBeyongRange() {
        if (helpData == null) {
            return;
        }
        if ("2".equals(helpData.delivery_fee_type) && !StringUtils.isEmpty(tvSendAddress.getText().toString())
                && !StringUtils.isEmpty(tvTakeAddress.getText().toString())) {
            double dis = rideRouteDis;
            double ra = FormatUtil.numDouble(helpData.delivery_radius) * 1000;
            if (dis > ra) {
                tvOverDis.setVisibility(View.VISIBLE);
                tvCommitOrder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tab_normal));
                tvCommitOrder.setOnClickListener(null);
            } else {
                tvOverDis.setVisibility(View.GONE);
                tvCommitOrder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.text_red_f8));
                tvCommitOrder.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_send_person:
            case R.id.tv_send_address:
                if (StringUtils.isEmpty(tvSendAddress.getText().toString())){
                    Intent in2 = new Intent(getActivity(), NearAddressListActivity.class);
                    in2.putExtra("where_from", "0");
                    startActivityForResult(in2,SEARCH_ADDRESS_CODE_SEND);
                }else {
                    /*if ("0".equals((String)tvSendAddress.getTag(R.id.tag_from))){

                    }else {
                        startActivityForResult(new Intent(getActivity(), AddressBookActivity.class), SEARCH_ADDRESS_CODE_SEND_BOOK);
                    }*/
                    Intent intent = new Intent(getContext(),SaveAddressActivity.class);
                    intent.putExtra("address",(String) tvSendAddress.getTag(R.id.tag_address));
                    intent.putExtra("lat",rlat+"");
                    intent.putExtra("lng",rlng+"");
                    intent.putExtra("address_detail",(String) tvSendAddress.getTag(R.id.tag_address_detail));
                    intent.putExtra("name",tvSendName.getText().toString());
                    intent.putExtra("phone",tvSendPhone.getText().toString());
                    startActivityForResult(intent, SEARCH_ADDRESS_CODE_SEND);
                }

                break;

            case R.id.tv_address_list:
                startActivityForResult(new Intent(getActivity(), AddressBookActivity.class), SEARCH_ADDRESS_CODE_SEND_BOOK);
                break;
            case R.id.ll_take_person:
            case R.id.tv_take_address:
                if (StringUtils.isEmpty(tvTakeAddress.getText().toString())){
                    Intent in3 = new Intent(getActivity(), NearAddressListActivity.class);
                    in3.putExtra("where_from", "0");
                    startActivityForResult(in3,SEARCH_ADDRESS_CODE_TAKE);
                }else {
                    /*if ("0".equals((String)tvTakeAddress.getTag(R.id.tag_from))){

                    }else {
                        startActivityForResult(new Intent(getActivity(), AddressBookActivity.class), SEARCH_ADDRESS_CODE_TAKE_BOOK);
                    }*/
                    Intent intent = new Intent(getContext(),SaveAddressActivity.class);
                    intent.putExtra("address",(String) tvTakeAddress.getTag(R.id.tag_address));
                    intent.putExtra("lat",slat+"");
                    intent.putExtra("lng",slat+"");
                    intent.putExtra("address_detail",(String) tvTakeAddress.getTag(R.id.tag_address_detail));
                    intent.putExtra("name",tvTakeName.getText().toString());
                    intent.putExtra("phone",tvTakePhone.getText().toString());
                    startActivityForResult(intent, SEARCH_ADDRESS_CODE_TAKE);
                }

                break;
            case R.id.tv_take_address_list:
                startActivityForResult(new Intent(getActivity(), AddressBookActivity.class), SEARCH_ADDRESS_CODE_TAKE_BOOK);
                break;
            case R.id.ll_time:
                if (timeDialog != null) {
                    timeDialog.show();
                }
                break;
            case R.id.iv_tip:
                if (pricePopupWindow == null) {
                    pricePopupWindow = new AllPricePopupWindow(getContext());
                }
                countAllPrice();
                pricePopupWindow.showWithData(allPrice + "", priceList);
                break;
            case R.id.btn_load_again:
                llFail.setVisibility(View.GONE);
                helpPresenter.loadData(type_id);
                break;
            case R.id.tv_commit_order:
                commitOrder();
                break;
            case R.id.ll_pay:
                payOrderDialog.dismiss();
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog3(getContext());
                }
                loadingDialog.show();
                helpPresenter.commitOrder(orderMap);
                break;
            case R.id.btn_login:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent1, Const.GO_TO_LOGIN);
                break;
            case R.id.ll_coupon:
               /* if (StringUtils.isEmpty(tvCoupon.getText().toString())) {
                    return;
                }*/
                Intent p = new Intent(getActivity(), PaotuiCouponActivity.class);
                p.putExtra("coupon_id",coupon_id);
                p.putExtra("coupon_no",nCouponLists);
                p.putExtra("coupon_use",uCouponLists);
                startActivityForResult(p, COUNPON_CODE);
                break;
            case R.id.ll_special:
                initServiceDialog();
                break;
        }
    }

    double smallFee = 0;
    double disFee = 0;
    double serviceFee = 0;
    double conditionPrice = 0;
    double allPrice = 0;
    double rideRouteDis = 0;
    ArrayList<PriceDetailBean> priceList;

    /**
     * 计算价格
     */
    private void countAllPrice() {
        if (priceList == null) {
            priceList = new ArrayList<>();
        }
        priceList.clear();
        smallFee = getFormatData(StringUtils.isEmpty(edtSmallFee.getText().toString().trim()) ? 0 : Double.parseDouble(edtSmallFee.getText().toString().trim()));
        PriceDetailBean small = new PriceDetailBean();
        small.fee = df.format(smallFee);
        small.title = "小费";

        disFee = 0;
        serviceFee = 0;
        PriceDetailBean base = new PriceDetailBean();
        base.title = "基础费用";

        if ("1".equals(helpData.delivery_fee_type)) {
            disFee = FormatUtil.numDouble(helpData.delivery_fee);
            base.tip = "";
        } else {
            if (slat > 0 && rlat > 0) {
                double dis = rideRouteDis;
                LogUtil.log("countAllPrice", "dis == " + dis);
                for (int i = 0; i < helpData.delivery_fee_by_distance.size(); i++) {
                    HelpBean.DeliveryFeeByDistanceBean bean = helpData.delivery_fee_by_distance.get(i);
                    double s = FormatUtil.numDouble(bean.start) * 1000;
                    double e = FormatUtil.numDouble(bean.stop) * 1000;
                    LogUtil.log("countAllPrice", "s == " + s + "    e == " + e);
                    if (dis > s && dis <= e) {
                        if (dis > 1000){
                            base.tip = "预计" + FormatUtil.numDouble(df.format(dis / 1000) + "") + "公里";
                        }else {
                            base.tip = "预计" + FormatUtil.numDouble(df.format(dis) + "") + "m";
                        }
                        disFee = FormatUtil.numDouble(bean.fee);
                        break;
                    }

                }
            } else {
                if (helpData.delivery_fee_by_distance.size() > 0) {
                    disFee = FormatUtil.numDouble(helpData.delivery_fee_by_distance.get(0).fee);
                    for (int i = 1; i < helpData.delivery_fee_by_distance.size(); i++) {
                        HelpBean.DeliveryFeeByDistanceBean bean = helpData.delivery_fee_by_distance.get(i);
                        double d = FormatUtil.numDouble(bean.fee);
                        if (disFee > d) {
                            disFee = d;
                        }
                    }
                    base.tip = "";
                }
            }
        }
        base.fee = df.format(disFee);
        priceList.add(base);
        priceList.add(small);

        if (helpData.addservice.size() > 0) {
            for (int i = 0; i < helpData.addservice.size(); i++) {
                HelpBean.AddserviceBean bean = helpData.addservice.get(i);
                serviceFee += FormatUtil.numDouble(bean.fee);
                PriceDetailBean p = new PriceDetailBean();
                p.title = bean.title;
                p.fee = df.format(FormatUtil.numDouble(bean.fee));
                priceList.add(p);
            }
        }
        if (requirementAdapter != null){
            conditionPrice = requirementAdapter.getConditionPrice();
            priceList.addAll(requirementAdapter.getPriceList());
        }
        getCouponSum();
        if (couponFee > 0){
            PriceDetailBean co = new PriceDetailBean();
            co.title = "优惠券";
            co.fee = couponFee + "";
            priceList.add(co);
        }
        allPrice = getFormatData(smallFee + disFee + conditionPrice + serviceFee - couponFee);
        if (allPrice <= 0){
            allPrice = 0.01;
        }
        tvMoney.setText(FormatUtil.numFormat(allPrice + ""));
    }
    private void getCouponSum(){
        if (couponBean == null){
            coupon_id = "";
            is_coupon = "0";
            couponFee = 0;
            tvCoupon.setText("");
            tvCoupon.setGravity(Gravity.CENTER|Gravity.LEFT);
            helpPresenter.loadCounpon(type_id);
        }else {
            //如果已选，重现处理
            if (coupon != null && disFee >= FormatUtil.numFloat(coupon.coupon_basic_price)){
                nCouponLists.clear();
                uCouponLists.clear();
                if (couponBean.data.list != null && couponBean.data.list.size() > 0){
                    int sum = 0;
                    for (PaotuiCouponBean.CouponList couponList : couponBean.data.list){
                        if (disFee >= FormatUtil.numFloat(couponList.coupon_basic_price) && "OPEN".equals(couponList.coupon_status)
                                && ("0".equals(couponList.errand_type) || "2".equals(couponList.errand_type))){
                            sum++;
                            uCouponLists.add(couponList);
                        }else {
                            nCouponLists.add(couponList);
                        }
                    }
                }
            }else {
                coupon = null;
                coupon_id = "";
                is_coupon = "0";
                couponFee = 0;
                coupon_info = "";
                tvCoupon.setText("");
                tvCoupon.setGravity(Gravity.CENTER|Gravity.LEFT);
                nCouponLists.clear();
                uCouponLists.clear();
                if (couponBean.data.list != null && couponBean.data.list.size() > 0){
                    int sum = 0;
                    for (PaotuiCouponBean.CouponList couponList : couponBean.data.list){
                        if (disFee >= FormatUtil.numFloat(couponList.coupon_basic_price) && "OPEN".equals(couponList.coupon_status)
                                && ("0".equals(couponList.errand_type) || "2".equals(couponList.errand_type))){
                            sum++;
                            uCouponLists.add(couponList);
                        }else {
                            nCouponLists.add(couponList);
                        }
                    }
                    if (sum > 0){
                        coupon_info = sum + "张可用";
                        tvCoupon.setText(coupon_info);
                        tvCoupon.setGravity(Gravity.CENTER|Gravity.RIGHT);
                    }
                }
            }
        }

    }
    private void getRideDistance() {
        if (helpData == null) {
            return;
        }
        if ("2".equals(helpData.delivery_fee_type) && (slat > 0 && rlat > 0)) {
            RouteSearch routeSearch = new RouteSearch(getContext());
            //模拟起始点与目的经纬度（如：深圳市）
            LatLonPoint sl = new LatLonPoint(slat, slng);//起点：发货地址
            LatLonPoint el = new LatLonPoint(rlat, rlng);//终点：收货地址
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(sl,
                    el);
            //骑行：（默认推荐路线及最快路线综合模式，可以接二参同上）
            RouteSearch.RideRouteQuery query3 = new RouteSearch.RideRouteQuery(fromAndTo);
            routeSearch.calculateRideRouteAsyn(query3);
            routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
                @Override
                public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

                }

                @Override
                public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

                }

                @Override
                public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

                }

                @Override
                public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
                    if (rideRouteResult.getPaths().size() > 0) {
                        RidePath ridePath = rideRouteResult.getPaths().get(0);
                        rideRouteDis = ridePath.getDistance();
                    }
                    countAllPrice();
                    isBeyongRange();
                }
            });
        } else {
            countAllPrice();
        }
    }

    public double getFormatData(double d) {
        return Double.parseDouble(df.format(d));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SEARCH_ADDRESS_CODE_TAKE  && data != null) {
                AddressBean.AddressList addressList = (AddressBean.AddressList)data.getSerializableExtra("addressData");

                tvTakeAddress.setTag(R.id.tag_from,"0");
                tvTakeAddress.setTag(R.id.tag_address,addressList.address_name);
                tvTakeAddress.setTag(R.id.tag_address_detail,addressList.address);
                tvTakeAddress.setText(addressList.address_name + addressList.address);
                tvTakePhone.setText(addressList.phone);
                if (StringUtils.isEmpty(addressList.name)){
                    tvTakeName.setVisibility(View.GONE);
                }else {
                    tvTakeName.setVisibility(View.VISIBLE);
                    tvTakeName.setText(addressList.name);
                }
                slat = FormatUtil.numDouble(addressList.lat);
                slng = FormatUtil.numDouble(addressList.lng);
                llTakePerson.setVisibility(View.VISIBLE);
                getRideDistance();
            }
            if (requestCode == SEARCH_ADDRESS_CODE_TAKE_BOOK  && data != null) {
                AddressBean.AddressList addressList = (AddressBean.AddressList)data.getSerializableExtra("addressData");

                tvTakeAddress.setTag(R.id.tag_from,"1");
                tvTakeAddress.setTag(R.id.tag_address,addressList.address_name);
                tvTakeAddress.setTag(R.id.tag_address_detail,addressList.address);
                tvTakeAddress.setText(addressList.address_name + addressList.address);
                tvTakePhone.setText(addressList.phone);
                if (StringUtils.isEmpty(addressList.name)){
                    tvTakeName.setVisibility(View.GONE);
                }else {
                    tvTakeName.setVisibility(View.VISIBLE);
                    tvTakeName.setText(addressList.name);
                }
                slat = FormatUtil.numDouble(addressList.lat);
                slng = FormatUtil.numDouble(addressList.lng);
                llTakePerson.setVisibility(View.VISIBLE);
                getRideDistance();
            }
            if (requestCode == SEARCH_ADDRESS_CODE_SEND && data != null) {
                AddressBean.AddressList addressList = (AddressBean.AddressList)data.getSerializableExtra("addressData");
                tvSendAddress.setTag(R.id.tag_from,"0");
                tvSendAddress.setTag(R.id.tag_address,addressList.address_name);
                tvSendAddress.setTag(R.id.tag_address_detail,addressList.address);

                tvSendAddress.setText(addressList.address_name + addressList.address);
                tvSendPhone.setText(addressList.phone);
                if (StringUtils.isEmpty(addressList.name)){
                    tvSendName.setVisibility(View.GONE);
                }else {
                    tvSendName.setVisibility(View.VISIBLE);
                    tvSendName.setText(addressList.name);
                }
                rlat = FormatUtil.numDouble(addressList.lat);
                rlng = FormatUtil.numDouble(addressList.lng);
                llSendPerson.setVisibility(View.VISIBLE);
                getRideDistance();

            }
            if (requestCode == SEARCH_ADDRESS_CODE_SEND_BOOK && data != null) {
                AddressBean.AddressList addressList = (AddressBean.AddressList)data.getSerializableExtra("addressData");
                tvSendAddress.setTag(R.id.tag_from,"1");
                tvSendAddress.setTag(R.id.tag_address,addressList.address_name);
                tvSendAddress.setTag(R.id.tag_address_detail,addressList.address);

                tvSendAddress.setText(addressList.address_name + addressList.address);
                tvSendPhone.setText(addressList.phone);
                if (StringUtils.isEmpty(addressList.name)){
                    tvSendName.setVisibility(View.GONE);
                }else {
                    tvSendName.setVisibility(View.VISIBLE);
                    tvSendName.setText(addressList.name);
                }
                rlat = FormatUtil.numDouble(addressList.lat);
                rlng = FormatUtil.numDouble(addressList.lng);
                llSendPerson.setVisibility(View.VISIBLE);
                getRideDistance();

            }
            if (requestCode == COUNPON_CODE && data != null){
                boolean isSelect = data.getBooleanExtra("is_select",false) ;
                if (isSelect){
                    coupon = (PaotuiCouponBean.CouponList) data.getSerializableExtra("coupon");
                    couponFee = getFormatData(FormatUtil.numDouble(coupon.coupon_value));
                    coupon_id = coupon.id;
                    is_coupon = "1";
                    coupon_info = "-￥"+couponFee;
                    tvCoupon.setText(coupon_info);
                    tvCoupon.setGravity(Gravity.CENTER|Gravity.RIGHT);
                }else {
                    coupon = null;
                }
                countAllPrice();
            }
            if (requestCode == PAY_SUCCESS_CODE) {
                toOrderDetailActivity(order_id);
            }
        }
        if (requestCode == Const.GO_TO_LOGIN && resultCode == getActivity().RESULT_OK) {
            llLogin.setVisibility(View.GONE);
            helpPresenter.loadData(type_id);
        }
    }

    private Dialog timeDialog,serviceDialog;

    //选择时间
    public void initTimeDialog() {
        if (helpData == null) {
            return;
        }
        if (helpData.time_today.size() > 0) {
            if (helpData.time_today.get(0).equals("立即发货")) {
                tvTime.setText("立即发货");
            } else {
                String s = "";
                String m = "";
                if (helpData.delivery_day.size() > 0) {
                    s = helpData.delivery_day.get(0);
                }
                if (helpData.minute_start_today.size() > 0) {
                    m = helpData.minute_start_today.get(0);
                }
                s = s + " " + helpData.time_today.get(0) + ":" + m;
                tvTime.setText(s);
            }
        }
        ArrayList<String> days = new ArrayList<>();
        ArrayList<String> times = new ArrayList<>();
        ArrayList<String> minute = new ArrayList<>();
        for (int j = 0; j < 60; j++) {
            if (j < 10) {
                minute.add("0" + j);
            } else {
                minute.add("" + j);
            }
        }
        final ArrayList<String> seconds = new ArrayList<>();
        seconds.add("");
        days.addAll(helpData.delivery_day);
        times.addAll(helpData.time_today);
        if (timeDialog == null) {
            HashMap<String, ArrayList<String>> tMap = new HashMap<>();
            final HashMap<String, ArrayList<String>> sMap = new HashMap<>();
            for (String day : days) {
                ArrayList<String> tList = new ArrayList<>();
                if ("今天".equals(day)) {
                    for (int i = 0; i < helpData.time_today.size(); i++) {
                        String s = helpData.time_today.get(i);
                        ArrayList<String> sList = new ArrayList<>();
                        if (helpData.time_today.contains("立即发货")) {
                            if (i == 0) {
                                sList.add("");
                                seconds.add("");
                            } else if (i == (helpData.time_today.size() - 1)) {
                                sList.addAll(helpData.minute_end);
                            } else {
                                if (i == 1) {
                                    sList.addAll(helpData.minute_start_today);
                                } else {
                                    sList.addAll(helpData.minute_start_otherday);
                                }
                            }

                        } else {
                            if (i == 0) {
                                sList.addAll(helpData.minute_start_today);
                            } else if (i == (helpData.time_today.size() - 1)) {
                                sList.addAll(helpData.minute_end);
                            } else {
                                sList.addAll(minute);
                            }
                        }
                        sMap.put(day + s, sList);
                    }
                    tList.addAll(helpData.time_today);
                } else {
                    for (int i = 0; i < helpData.time_otherday.size(); i++) {
                        String s = helpData.time_otherday.get(i);
                        ArrayList<String> sList = new ArrayList<>();
                        if (i == 0) {
                            sList.addAll(helpData.minute_start_otherday);
                        } else if (i == helpData.time_otherday.size() - 1) {
                            sList.addAll(helpData.minute_end);
                        } else {
                            sList.addAll(minute);
                        }
                        sMap.put(day + s, sList);
                    }
                    tList.addAll(helpData.time_otherday);
                }
                tMap.put(day, tList);
            }

            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_buy_time, null);
            final WheelView wheelDay = (WheelView) view.findViewById(R.id.wheel_day);
            wheelDay.setSelection(0);
            wheelDay.setWheelAdapter(new ArrayWheelAdapter(getContext())); // 文本数据源
            wheelDay.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelDay.setWheelData(days);  // 数据集合

            final WheelView wheelTime = (WheelView) view.findViewById(R.id.wheel_time);
            wheelTime.setSelection(0);
            wheelTime.setWheelAdapter(new ArrayWheelAdapter(getContext())); // 文本数据源
            wheelTime.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelTime.setWheelData(times);  // 数据集合

            wheelDay.join(wheelTime);
            wheelDay.joinDatas(tMap);

            final WheelView wheelSecond = (WheelView) view.findViewById(R.id.wheel_second);
            wheelSecond.setSelection(0);
            wheelSecond.setWheelAdapter(new ArrayWheelAdapter(getContext())); // 文本数据源
            wheelSecond.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelSecond.setWheelData(seconds);  // 数据集合
           /* wheelTime.join(wheelSecond);
            wheelTime.joinDatas(sMap);*/
            wheelTime.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
                    String key = (String) wheelDay.getSelectionItem() + (String) o;
                    LogUtil.log("onItemSelected", "key == " + key);
                    if (sMap.get(key) != null && sMap.get(key).size() > 0) {
                        wheelSecond.resetDataFromTop(sMap.get(key));
                    }
                }
            });
            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeDialog.dismiss();

                }
            });
            view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String day = (String) wheelDay.getSelectionItem();
                    String time = (String) wheelTime.getSelectionItem();
                    String second = (String) wheelSecond.getSelectionItem();
                    String select = "";
                    if ("今天".equals(day)) {
                        if ("立即发货".equals(time)) {
                            select = time;
                        } else {
                            select = day + " " + time + ":" + second;
                        }
                    } else {
                       /* if (isOverTime(day,time,second)){
                            ToastUtil.showTosat(HelpSendActivity.this,"选择的时间必须超过当前时间");
                            return;
                        }*/
                        select = day + " " + time + ":" + second;
                    }
                    tvTime.setText(select);
                    timeDialog.dismiss();
                }
            });
            timeDialog = DialogUtils.BottonDialog(getContext(), view);
        }

    }
    Set<String> finalSet ;
    public void initServiceDialog() {
        if (helpData == null || helpData.special_fee.size() == 0) {
            return;
        }
        if (serviceDialog == null){
            finalSet = new HashSet<>();
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_service, null);
            RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
            requirementAdapter = new RequirementAdapter(getContext(),helpData.special_fee);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(requirementAdapter);
            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceDialog.dismiss();
                    requirementAdapter.setCurrentSet(finalSet);
                    countAllPrice();
                }
            });
            view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceDialog.dismiss();
                    tvService.setText(requirementAdapter.getServiceValue());
                    finalSet.clear();
                    finalSet.addAll(requirementAdapter.getSelectSet());
                    countAllPrice();
                }
            });
            serviceDialog = DialogUtils.BottonDialog(getContext(), view);
        }
        requirementAdapter.setCurrentSet(finalSet);
        serviceDialog.setCancelable(false);
        serviceDialog.show();

    }


    public void onBackPressed() {
        if (pricePopupWindow != null && pricePopupWindow.isShowing()) {
            pricePopupWindow.dismiss();
            return;
        }
        getActivity().finish();
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(getContext(), s);
    }

    @Override
    public void loadData(HelpBean bean) {
        llFail.setVisibility(View.GONE);
        if (bean.data != null) {
            helpData = bean.data;
            toolbar.setTitleText(helpData.title);
            member_notice = helpData.member_notice;
            member_balance = FormatUtil.numDouble(helpData.member_balance);
            if (helpData.special_fee != null && helpData.special_fee.size() > 0) {
                specialFeeBeen.addAll(helpData.special_fee);
                llSpecial.setVisibility(View.VISIBLE);
            } else {
                llSpecial.setVisibility(View.GONE);
            }
            initTimeDialog();
            countAllPrice();
            if (bean.data != null && bean.data.nearest_info != null) {
                if(StringUtils.isEmpty(bean.data.nearest_info.address_name)){
                    return;
                }
                llSendPerson.setVisibility(View.VISIBLE);
                tvSendAddress.setText(bean.data.nearest_info.address_name + bean.data.nearest_info.address);
                tvSendName.setText(bean.data.nearest_info.name);
                tvSendPhone.setText(bean.data.nearest_info.phone);
                rlat = StringUtils.isEmpty(bean.data.nearest_info.lat) ? 0 : Double.parseDouble(bean.data.nearest_info.lat);
                rlng = StringUtils.isEmpty(bean.data.nearest_info.lng) ? 0 : Double.parseDouble(bean.data.nearest_info.lng);
            }
        }
    }

    @Override
    public void loadFail() {
        llFail.setVisibility(View.VISIBLE);
    }

    @Override
    public void commitOrderSuccess(final PaotuiOrderBean bean) {
        if (bean.data == null) {
            loadingDialog.dismiss();
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (payOrderPresenter == null) {
                    payOrderPresenter = new PayOrderPresenter(HelpSendFragment.this);
                }
                order_id = bean.data.order_id;
                if (!StringUtils.isEmpty(order_id) && payTypeAdapter != null) {
                    payOrderPresenter.payOrder(order_id, payTypeAdapter.getSelectType());
                }
            }
        }, 1000);

    }

    @Override
    public void commitOrderFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void loadCoupon(PaotuiCouponBean bean) {
        if (bean != null ){
            couponBean = bean;
            getCouponSum();
        }
    }

    @Override
    public void loadCouponFail() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (payTypeAdapter != null && payTypeAdapter.getSelectType().equals("3")) {
            //微信支付成功（0） 或取消（-2）
            if (WXResult.errCode == 0) {
                WXResult.errCode = -2222;
                toPaySuccessActivity();
            } else if (WXResult.errCode == -2) {
                WXResult.errCode = -2222;
//            ToastUtil.showTosat(this,"支付取消");
            }
        }
    }

    @Override
    void weixinPay(OrderPayResultBean.ZhiFuParameters parameters) {
        msgApi = WXAPIFactory.createWXAPI(getContext(), Constants.APP_ID);
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

    @Override
    void alipay(String payInfo) {
        Alipay alipay = Alipay.getInstance(getActivity());
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
                        toPaySuccessActivity();
                        break;
                    //订单支付失败
                    case "4000":
                        ToastUtil.showTosat(getContext(), "支付失败");
                        break;
                    //重复请求
                    case "5000":
                        break;
                    //用户中途取消
                    case "6001":
                        ToastUtil.showTosat(getContext(), "支付取消");
                        break;
                    //网络连接出错
                    case "6002":
                        ToastUtil.showTosat(getContext(), "网络错误，支付失败");
                        break;
                }
            }

            @Override
            public void onPayFail(String msg) {
                ToastUtil.showTosat(getContext(), "网络错误，支付失败");
            }
        });
    }

    @Override
    void toOrderDetailActivity(String order_id) {
        Intent intent = new Intent(getContext(), PaotuiOrderActivity.class);
        intent.putExtra("where_from", "order_detail");
        intent.putExtra("order_id", order_id);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    void toPaySuccessActivity() {
        Intent intent = new Intent(getContext(), PaySuccessActivity.class);
        intent.putExtra("where_from", "0");
        intent.putExtra("pay_type", payTypeAdapter.getSelectType());
        intent.putExtra("pay_money", allPrice + "");
        intent.putExtra("order_id",order_id);
        startActivityForResult(intent, PAY_SUCCESS_CODE);
    }

    @Override
    public void paySuccess(PayOrderBean bean) {
        loadingDialog.dismiss();
        if (bean.data == null) {
            return;
        }
        if ("success".equals(bean.data.status)) {
            toPaySuccessActivity();
        } else if ("paying".equals(bean.data.status)) {
            if (bean.data.zhifuParameters != null && !StringUtils.isEmpty(bean.data.zhifuParameters.partnerid)){
                weixinPay(bean.data.zhifuParameters);
            } else if (!StringUtils.isEmpty(bean.data.zhifubaoParameters)) {
                alipay(bean.data.zhifubaoParameters);
            }
        }
    }

    @Override
    public void payFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

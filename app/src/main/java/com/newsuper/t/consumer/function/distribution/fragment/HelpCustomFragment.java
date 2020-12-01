package com.newsuper.t.consumer.function.distribution.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.CustomAddressBean;
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
import com.newsuper.t.consumer.function.distribution.adapter.CustomHelpTypeAdapter;
import com.newsuper.t.consumer.function.distribution.adapter.CustomServiceAdapter;
import com.newsuper.t.consumer.function.distribution.internal.ICustomeHelpView;
import com.newsuper.t.consumer.function.distribution.internal.IPayOrderView;
import com.newsuper.t.consumer.function.distribution.presenter.CustomHelpPresenter;
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
import com.newsuper.t.consumer.widget.ListViewForScrollView;
import com.newsuper.t.consumer.widget.LoadingDialog3;
import com.newsuper.t.consumer.widget.defineTopView.WGridView;
import com.newsuper.t.consumer.widget.popupwindow.AllPricePopupWindow;
import com.newsuper.t.consumer.wxapi.Constants;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class HelpCustomFragment extends HelpBaseFragment implements View.OnClickListener, ICustomeHelpView, IPayOrderView {
    private static final int SEARCH_ADDRESS_CODE = 114;
    private static final int SEARCH_ADDRESS_CODE_BOOK = 115;
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_service_title)
    TextView tvServiceTitle;
    @BindView(R.id.tv_service_select)
    TextView tvServiceSelect;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.grideview)
    WGridView grideview;
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
    @BindView(R.id.custom_listview)
    ListViewForScrollView customListview;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_person)
    LinearLayout llPerson;
    @BindView(R.id.tv_address_list)
    TextView tvAddressList;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    private double lat, lng;
    private AllPricePopupWindow pricePopupWindow;
    private CustomHelpTypeAdapter typeAdapter;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private CustomHelpPresenter helpPresenter;
    private String type_id, la, ln;
    private HelpBean.HelpData helpData;
    private ArrayList<HelpBean.IndividuationBean> list;
    private ArrayList<HelpBean.ServiceContentBean> service_content;
    private CustomServiceAdapter customServiceAdapter;
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
        View view = inflater.inflate(R.layout.activity_help_costom, null);
        ButterKnife.bind(this, view);
        la = getArguments().getString("lat");
        ln = getArguments().getString("lng");
        String title = getArguments().getString("title");
        type_id = getArguments().getString("type_id");
        lat = StringUtils.isEmpty(la) ? 0 : Double.parseDouble(la);
        lng = StringUtils.isEmpty(ln) ? 0 : Double.parseDouble(ln);
        tvAddress.setOnClickListener(this);
        tvAddressList.setOnClickListener(this);
        llCoupon.setOnClickListener(this);
        llPerson.setOnClickListener(this);
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
        edtSmallFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countAllPrice();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString()) || StringUtils.isEmpty(tvAddress.getText().toString())) {
                    tvCommitOrder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tab_normal));
                    tvCommitOrder.setOnClickListener(null);
                } else {
                    tvCommitOrder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.text_red_f8));
                    tvCommitOrder.setOnClickListener(HelpCustomFragment.this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        list = new ArrayList<>();
        typeAdapter = new CustomHelpTypeAdapter(getContext(), list);
        grideview.setAdapter(typeAdapter);
        grideview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeAdapter.setSelectType(position);
                countAllPrice();
            }
        });
        service_content = new ArrayList<>();
        customServiceAdapter = new CustomServiceAdapter(getContext(), service_content);
        customServiceAdapter.setSelectItemCallBack(new CustomServiceAdapter.SelectItemCallBack() {
            @Override
            public void onSelect(int key) {
                showSelectDialog(key);
            }
        });
        customListview.setAdapter(customServiceAdapter);
        helpPresenter = new CustomHelpPresenter(this);
        payOrderPresenter = new PayOrderPresenter(this);
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
            llLogin.setVisibility(View.VISIBLE);
        } else {
            helpPresenter.loadCustomData(type_id, la, ln);
        }

        return view;
    }

    private Map<String, String> orderMap;

    private void commitOrder() {
        if (helpData == null) {
            return;
        }

        String name = tvName.getText().toString();
        if (StringUtils.isEmpty(name)) {
            ToastUtil.showTosat(getContext(), "请输入联系人姓名");
            return;
        }
        String address = tvAddress.getText().toString();
        if (StringUtils.isEmpty(address)) {
            ToastUtil.showTosat(getContext(), "请输入地址");
            return;
        }
        String phone = tvPhone.getText().toString();
        if (StringUtils.isMobile(address)) {
            ToastUtil.showTosat(getContext(), "请输入正确的手机号");
            return;
        }
        String tip = StringUtils.isEmpty(edtSmallFee.getText().toString()) ? "0" : edtSmallFee.getText().toString();
        String individuation = typeAdapter.getSelectType();
        String service_content = "";
        try {
            service_content = customServiceAdapter.getValueArray();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        orderMap = DistributionRequest.customRequest(SharedPreferencesUtil.getToken(),
                Const.ADMIN_ID, RetrofitUtil.ADMIN_APP_ID, type_id, "2", "1", allPrice + "",
                name, phone, address, lat + "", lng + "", individuation, service_content, tip,is_coupon,coupon_id);
        showPayDialog(helpData.paytype, allPrice + "", this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_person:
            case R.id.tv_address:
                if (StringUtils.isEmpty(tvAddress.getText().toString())){
                    Intent in2 = new Intent(getActivity(), NearAddressListActivity.class);
                    in2.putExtra("where_from", "0");
                    startActivityForResult(in2, SEARCH_ADDRESS_CODE);
                }else {
                    /*if ("0".equals((String)tvAddress.getTag(R.id.tag_from))){

                    }else {
                        startActivityForResult(new Intent(getActivity(), AddressBookActivity.class), SEARCH_ADDRESS_CODE_BOOK);
                    }*/
                    Intent intent = new Intent(getContext(),SaveAddressActivity.class);
                    intent.putExtra("address",(String) tvAddress.getTag(R.id.tag_address));
                    intent.putExtra("lat",lat+"");
                    intent.putExtra("lng",lng+"");
                    intent.putExtra("address_detail",(String) tvAddress.getTag(R.id.tag_address_detail));
                    intent.putExtra("name",tvName.getText().toString());
                    intent.putExtra("phone",tvPhone.getText().toString());
                    startActivityForResult(intent, SEARCH_ADDRESS_CODE);
                }
                break;
            case R.id.tv_address_list:
                startActivityForResult(new Intent(getActivity(), AddressBookActivity.class), SEARCH_ADDRESS_CODE_BOOK);
                break;
            case R.id.ll_time:
                break;
            case R.id.tv_commit_order:
                commitOrder();
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
        }
    }

    double smallFee = 0;
    double disFee = 0;
    double serviceFee = 0;
    double conditionPrice = 0;
    double baseFee = 0;
    double allPrice = 0;
    ArrayList<PriceDetailBean> priceList;

    /**
     * 计算价格
     */
    private void countAllPrice() {
        if (helpData == null) {
            return;
        }
        if (priceList == null) {
            priceList = new ArrayList<>();
        }
        serviceFee = 0;
        smallFee = 0;
        baseFee = 0;
        allPrice = 0;
        priceList.clear();
        if (list.size() > 0) {
            disFee = typeAdapter.getServiceFee();
            PriceDetailBean base = new PriceDetailBean();
            base.title = "个性服务费用";
            base.fee = df.format(disFee);
            priceList.add(base);
        } else {
            if ("1".equals(helpData.delivery_fee_type)) {
                baseFee = FormatUtil.numDouble(helpData.delivery_fee);
                PriceDetailBean base = new PriceDetailBean();
                base.title = "基础费用";
                base.fee = df.format(baseFee);
                base.tip = "";
                priceList.add(base);
            }
        }
        smallFee = getFormatData(StringUtils.isEmpty(edtSmallFee.getText().toString().trim()) ? 0 : Double.parseDouble(edtSmallFee.getText().toString().trim()));
        PriceDetailBean small = new PriceDetailBean();
        small.fee = df.format(smallFee);
        small.title = "小费";
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
        LogUtil.log("countAllPrice","baseFee == " + baseFee);
        LogUtil.log("countAllPrice","disFee == " + disFee);
        getCouponSum();
        if (couponFee > 0){
            PriceDetailBean co = new PriceDetailBean();
            co.title = "优惠券";
            co.fee = couponFee + "";
            priceList.add(co);
        }
        allPrice = getFormatData(baseFee + smallFee + disFee + conditionPrice + serviceFee- couponFee);
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
            LogUtil.log("countAllPrice","disFee == " + disFee);

            //金额改变，重新计算优惠券
            if (coupon != null && (baseFee + disFee) >= FormatUtil.numFloat(coupon.coupon_basic_price) ){
                nCouponLists.clear();
                uCouponLists.clear();
                if (couponBean.data.list != null && couponBean.data.list.size() > 0) {
                    for (PaotuiCouponBean.CouponList couponList : couponBean.data.list) {
                        if ((baseFee + disFee) >= FormatUtil.numFloat(couponList.coupon_basic_price)
                                && "OPEN".equals(couponList.coupon_status) && ("0".equals(couponList.errand_type) || "5".equals(couponList.errand_type))) {
                            uCouponLists.add(couponList);
                        } else {
                            nCouponLists.add(couponList);
                        }
                    }
                }
            }else {
                coupon = null;
                coupon_id = "";
                is_coupon = "0";
                couponFee = 0;
                tvCoupon.setText("");
                tvCoupon.setGravity(Gravity.CENTER | Gravity.LEFT);
                nCouponLists.clear();
                uCouponLists.clear();
                if (couponBean.data.list != null && couponBean.data.list.size() > 0) {
                    int sum = 0;
                    for (PaotuiCouponBean.CouponList couponList : couponBean.data.list) {
                        if ((baseFee + disFee) >= FormatUtil.numFloat(couponList.coupon_basic_price)
                                && "OPEN".equals(couponList.coupon_status) && ("0".equals(couponList.errand_type) || "5".equals(couponList.errand_type))) {
                            sum++;
                            uCouponLists.add(couponList);
                        } else {
                            nCouponLists.add(couponList);
                        }
                    }
                    if (sum > 0) {
                        coupon_info = sum + "张可用";
                        tvCoupon.setText(coupon_info);
                        tvCoupon.setGravity(Gravity.CENTER | Gravity.RIGHT);
                    }
                }
            }

        }

    }

    public double getFormatData(double d) {
        return Double.parseDouble(df.format(d));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SEARCH_ADDRESS_CODE && data != null) {
                AddressBean.AddressList addressList = (AddressBean.AddressList) data.getSerializableExtra("addressData");
                if (addressList != null) {
                    tvAddress.setText(addressList.address_name + addressList.address);
                    tvAddress.setTag(R.id.tag_from,"0");
                    tvAddress.setTag(R.id.tag_address,addressList.address_name);
                    tvAddress.setTag(R.id.tag_address_detail,addressList.address);
                    tvAddress.setText(addressList.address_name + addressList.address);
                    tvPhone.setText(addressList.phone);
                    if (StringUtils.isEmpty(addressList.name)){
                        tvName.setVisibility(View.GONE);
                    }else {
                        tvName.setVisibility(View.VISIBLE);
                        tvName.setText(addressList.name);
                    }
                    lat = StringUtils.isEmpty(addressList.lat) ? 0 : Double.parseDouble(addressList.lat);
                    lng = StringUtils.isEmpty(addressList.lng) ? 0 : Double.parseDouble(addressList.lng);
                    llPerson.setVisibility(View.VISIBLE);
                }
            }
            if (requestCode == SEARCH_ADDRESS_CODE_BOOK && data != null) {
                AddressBean.AddressList addressList = (AddressBean.AddressList) data.getSerializableExtra("addressData");
                if (addressList != null) {
                    tvAddress.setText(addressList.address_name + addressList.address);
                    tvAddress.setTag(R.id.tag_from,"1");
                    tvAddress.setTag(R.id.tag_address,addressList.address_name);
                    tvAddress.setTag(R.id.tag_address_detail,addressList.address);

                    tvAddress.setText(addressList.address_name + addressList.address);
                    if (StringUtils.isEmpty(addressList.name)){
                        tvName.setVisibility(View.GONE);
                    }else {
                        tvName.setVisibility(View.VISIBLE);
                        tvName.setText(addressList.name);
                    }
                    tvPhone.setText(addressList.phone);
                    lat = StringUtils.isEmpty(addressList.lat) ? 0 : Double.parseDouble(addressList.lat);
                    lng = StringUtils.isEmpty(addressList.lng) ? 0 : Double.parseDouble(addressList.lng);
                    llPerson.setVisibility(View.VISIBLE);
                }
            }
            if (requestCode == PAY_SUCCESS_CODE) {
                toOrderDetailActivity(order_id);
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
        }
        if (requestCode == Const.GO_TO_LOGIN && resultCode == getActivity().RESULT_OK) {
            llLogin.setVisibility(View.GONE);
            helpPresenter.loadData(type_id);
        }
    }

    private Dialog selectDialog;
    private Map<Integer, ArrayList<String>> selectMap = new HashMap<>();
    private ArrayList<Integer> keys = new ArrayList<>();
    private ImageView ivNext;
    private ImageView ivLast;
    private WheelView wheelDay;
    private int currentSelect = 0;
    final View.OnClickListener onLast = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSelectDialog(currentSelect - 1);
        }
    };
    final View.OnClickListener onNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSelectDialog(currentSelect + 1);
        }
    };

    //选择时间
    public void showSelectDialog(final int i) {
        if (selectMap.containsKey(i) && selectMap.get(i) == null && selectMap.get(i).size() == 0) {
            return;
        }
        currentSelect = i;
        if (selectDialog == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_help_custom_time, null);
            wheelDay = (WheelView) view.findViewById(R.id.wheel_day);
            wheelDay.setSelection(0);
            wheelDay.setWheelAdapter(new ArrayWheelAdapter(getContext())); // 文本数据源
            wheelDay.setSkin(WheelView.Skin.Holo); // common皮肤
            ivNext = (ImageView) view.findViewById(R.id.iv_next);
            ivLast = (ImageView) view.findViewById(R.id.iv_last);
            view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = (String) wheelDay.getSelectionItem();
                    customServiceAdapter.setValueMap(currentSelect, s);
                    customServiceAdapter.notifyDataSetChanged();
                    selectDialog.dismiss();
                }
            });
            selectDialog = DialogUtils.BottonDialog(getContext(), view);
        }
        changeButton(i, ivLast, ivNext, onLast, onNext);
        wheelDay.setWheelData(selectMap.get(i));  // 数据集合
        selectDialog.show();

    }

    private void changeButton(int index, ImageView v1, ImageView v2, View.OnClickListener listener1, View.OnClickListener listener2) {
        if (selectMap.containsKey((index - 1))) {
            v1.setImageResource(R.mipmap.pull_red);
            v1.setOnClickListener(listener1);
        } else {
            v1.setImageResource(R.mipmap.pull_gray);
            v1.setOnClickListener(null);
        }
        if (selectMap.containsKey((index + 1))) {
            v2.setImageResource(R.mipmap.push_red);
            v2.setOnClickListener(listener2);
        } else {
            v2.setImageResource(R.mipmap.push_gray);
            v2.setOnClickListener(null);
        }
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
            if (helpData.individuation_fee.size() > 0) {
                list.addAll(helpData.individuation_fee);
                typeAdapter.notifyDataSetChanged();
                llService.setVisibility(View.VISIBLE);
            }
            if (helpData.service_content.size() > 0) {
                int key = 0;
                for (HelpBean.ServiceContentBean b : helpData.service_content) {
                    if ("1".equals(b.type)) {
                        key++;
                        String arr[] = b.content.split(",");
                        ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < arr.length; i++) {
                            if (i == 0) {
                                customServiceAdapter.setValueMap(key, arr[i]);
                            }
                            list.add(arr[i]);
                        }
                        keys.add(key);
                        selectMap.put(key, list);
                        b.key = key;
                        b.value = list;
                    }
                }
                service_content.addAll(helpData.service_content);
                customServiceAdapter.notifyDataSetChanged();
                if (bean.data != null && bean.data.nearest_info != null) {
                    if(StringUtils.isEmpty(bean.data.nearest_info.address_name)){
                        return;
                    }
                    tvAddress.setText(bean.data.nearest_info.address_name + bean.data.nearest_info.address);
                    tvName.setText(bean.data.nearest_info.name);
                    tvPhone.setText(bean.data.nearest_info.phone);
                    lat = StringUtils.isEmpty(bean.data.nearest_info.lat) ? 0 : Double.parseDouble(bean.data.nearest_info.lat);
                    lng = StringUtils.isEmpty(bean.data.nearest_info.lng) ? 0 : Double.parseDouble(bean.data.nearest_info.lng);
                    llPerson.setVisibility(View.VISIBLE);
                }
            }
            countAllPrice();
//            helpPresenter.loadAddress(lat+"",lng+"");
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
                    payOrderPresenter = new PayOrderPresenter(HelpCustomFragment.this);
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
    public void getAddress(CustomAddressBean bean) {

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

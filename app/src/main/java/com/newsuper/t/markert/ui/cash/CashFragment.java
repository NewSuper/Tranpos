package com.newsuper.t.markert.ui.cash;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import com.newsuper.t.markert.entity.PayMode;
import com.newsuper.t.markert.view.MultipleDisplay;
import com.trans.network.utils.GsonHelper;
import com.newsuper.t.R;
import com.newsuper.t.markert.adapter.ScanCodeQueryAdapter;
import com.newsuper.t.markert.base.BaseApp;
import com.newsuper.t.markert.base.mvp.BaseMvpFragment;
import com.newsuper.t.markert.db.manger.OrderItemDbManger;
import com.newsuper.t.markert.db.manger.OrderPayDbManger;
import com.newsuper.t.markert.db.manger.WorkerDbManger;
import com.newsuper.t.markert.dialog.EditNumDialog;
import com.newsuper.t.markert.dialog.MemberPsdDialog;
import com.newsuper.t.markert.dialog.PayMoneyDialog;
import com.newsuper.t.markert.dialog.VipCancelDialog;
import com.newsuper.t.markert.dialog.VipVerifyDialog;
import com.newsuper.t.markert.dialog.SpecsSelectDialog;
import com.newsuper.t.markert.dialog.WorkerSelectDialog;
import com.newsuper.t.markert.dialog.vm.InputDialogVM;
import com.newsuper.t.markert.entity.AccountsBean;
import com.newsuper.t.markert.entity.Member;
import com.newsuper.t.markert.entity.MultipleQueryProduct;
import com.newsuper.t.markert.entity.OrderItem;
import com.newsuper.t.markert.entity.OrderObject;

import com.newsuper.t.markert.entity.PromotionOrder;
import com.newsuper.t.markert.entity.Worker;
import com.newsuper.t.markert.entity.state.OrderPaymentStatusFlag;
import com.newsuper.t.markert.entity.state.OrderStatusFlag;

import com.newsuper.t.markert.service.PrintService;
import com.newsuper.t.markert.service.SummiPrintService;
import com.newsuper.t.markert.ui.cash.manger.OrderItemManger;
import com.newsuper.t.markert.ui.cash.manger.OrderManger;
import com.newsuper.t.markert.ui.cash.manger.OrderPayManger;
import com.newsuper.t.markert.ui.cash.mvp.CashContract;
import com.newsuper.t.markert.ui.cash.mvp.CashPresenter;

import com.newsuper.t.markert.utils.DateUtil;
import com.newsuper.t.markert.utils.Global;
import com.newsuper.t.markert.utils.KeyConstrant;
import com.newsuper.t.markert.utils.MathUtil;
import com.newsuper.t.markert.utils.NoDoubleListener;
import com.newsuper.t.markert.utils.StringTextUtil;
import com.newsuper.t.markert.utils.StringKotlin;
import com.newsuper.t.markert.utils.UiUtils;

import com.newsuper.t.markert.view.PayStatuDialog;
import com.newsuper.t.markert.view.PaySuccessDialog;
import com.transpos.sale.thread.ThreadDispatcher;
import com.transpos.tools.tputils.TPUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.os.Build.MANUFACTURER;


public class CashFragment extends BaseMvpFragment<CashPresenter> implements CashContract.View {
    @BindView(R.id.tv_lastOrderNo)
    TextView tvLastOrderNo;
    @BindView(R.id.tv_lastOrderNum)
    TextView tvLastOrderNum;
    @BindView(R.id.tv_lastTotalOrder)
    TextView tvLastTotalOrder;
    @BindView(R.id.tv_lastOrderTime)
    TextView tvLastOrderTime;
    @BindView(R.id.tv_lastOrderCoupon)
    TextView tvLastOrderCoupon;
    @BindView(R.id.tv_lastOrderZero)
    TextView tvLastOrderZero;
    @BindView(R.id.tv_viPhone)
    TextView tvViPhone;
    @BindView(R.id.tv_vipName)
    TextView tvVipName;
    @BindView(R.id.tv_ViPoint)
    TextView tvViPoint;
    @BindView(R.id.tv_vipLevel)
    TextView tvVipLevel;
    @BindView(R.id.tv_VipYue)
    TextView tvVipYue;
    @BindView(R.id.tv_worker)
    TextView tvWorker;
    @BindView(R.id.tv_currenTime)
    TextView tvCurrenTime;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.tv_totalNum)
    TextView tv_totalNum;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.tv_zero)
    TextView tvZero;
    @BindView(R.id.tv_totalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tv_pay)
    TextView tvPay;

    @BindView(R.id.ll_vip)
    LinearLayout llVip;
    @BindView(R.id.ll_worker)
    LinearLayout llWorker;
    @BindView(R.id.ll_shift_key)
    LinearLayout llShiftKey;
    @BindView(R.id.tv_test)
    TextView tvTest;
    Unbinder unbinder;
    private ScanCodeQueryAdapter scanCodeAdapter;
    private float[] vipPriceArr;
    private MultipleDisplay multipleDisplay;
    private Timer mTimer = new Timer();
    private DecimalFormat df;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean isSelectBusinesser;//是否选择营业员
//   private @PayMode int mPayMode = PayMode.PAY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {
        df = new DecimalFormat("#0.00");
        TPUtils.remove(getContext(),KeyConstrant.KEY_MEMBER);
        multipleDisplay = new MultipleDisplay();
        multipleDisplay.ShowMoneyDisplay(getActivity(), new ArrayList<>(), "0", "0", "0", "", "", "");
        Worker worker = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_WORKER, Worker.class);
        tvWorker.setText("收银员：" + worker.getName());
        etCode.setText("");
        etCode.setFocusable(false);
        etCode.setFocusableInTouchMode(false);
        etCode.setOnClickListener(v -> {
            etCode.setFocusable(true);
            etCode.setFocusableInTouchMode(true);
            etCode.requestFocus();
        });
        etCode.clearFocus();
        scanCodeAdapter = new ScanCodeQueryAdapter(getContext());
        scanCodeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            EditNumDialog dialog = new EditNumDialog.Builder().build();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
        ///    bundle.putBoolean(KeyConstrant.KEY_WEIGHT_FLAG,scanCodeAdapter.getData().get(position).getWeightFlag() == 1);
            bundle.putFloat("amount", (float) scanCodeAdapter.getData().get(position).getmAmount());
            bundle.putSerializable("scanList", (Serializable) scanCodeAdapter.getData());
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "goodsnum");
        });
        rvGoods.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGoods.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvGoods.setAdapter(scanCodeAdapter);
        //选择优惠券后，只能单选
      //  MutableLiveData<String[]> couponData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getCouponData();
       // couponData.observe(this, strings -> {
         //   payMoneyDialog.setCouponValue(Double.parseDouble(strings[0]));
      //  });
        //支付方式选择
       // MutableLiveData<String[]> payTypeData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getPayTypeData();
//        payTypeData.observe(this, strings -> {
//
//        });
        //多规格商品回调
        MutableLiveData<String> mutableLiveData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getSpecsData();
        mutableLiveData.observe(this, json -> {
            MultipleQueryProduct product = GsonHelper.fromJson(json, MultipleQueryProduct.class);
            List<MultipleQueryProduct> list = scanCodeAdapter.getData();
            if (list.contains(product)) {
                int i = 0;
                for (MultipleQueryProduct bean : list) {
                    if (bean.equals(product)) {
                        bean.setmAmount(bean.getmAmount() + 1);
                        updateTotal(product, OrderItemManger.AddItemState.MODIFY, i);
                        break;
                    }
                    i++;
                }
            } else {
                list.add(product);
                updateTotal(product, OrderItemManger.AddItemState.ADD, 0);
            }
            scanCodeAdapter.notifyDataSetChanged();
        });
        //首页选择会员
        MutableLiveData<String> verifyVip = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getVipVerifyData();
        verifyVip.observe(this, phone -> {
            if (phone.length() == 11) {
                presenter.queryMemberByPhone(phone);
            } else {
                presenter.queryMemberByBarcode(phone);
            }
        });
        //取消会员
        InputDialogVM cancelVip = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);
        cancelVip.getVipCancelData().observe(this, phone -> {
            scanCodeAdapter.setMMember(null);
            scanCodeAdapter.notifyDataSetChanged();
            tvViPhone.setText("会员：");
            tvVipName.setText("姓名：");
            tvViPoint.setText("积分：");
            tvVipLevel.setText("等级：");
            tvVipYue.setText("余额：");
            updatePrice();
            showDisplay();

            OrderManger.INSTANCE.getOrderBean().setIsMember(0);
            OrderManger.INSTANCE.getOrderBean().setReceivableAmount(Double.parseDouble(tvTotalPrice.getText().toString().trim()));
            OrderManger.INSTANCE.getOrderBean().setDiscountAmount(0);
        });
        //商品数量加减
        MutableLiveData<String[]> getEditNumData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getEditNumData();
        getEditNumData.observe(this, strings -> {
            List<MultipleQueryProduct> list = scanCodeAdapter.getData();
            MultipleQueryProduct item = list.get(Integer.parseInt(strings[1]));
            int count = 0;
            float weigt = 0f;
            try {
                count = Integer.parseInt(strings[0]);
                if (count == 0) {
                    scanCodeAdapter.getData().remove(Integer.parseInt(strings[1]));
                    updateTotal(item, OrderItemManger.AddItemState.REMOVE, Integer.parseInt(strings[1]));
                } else {
                    item.setmAmount(count);
                    updateTotal(item, OrderItemManger.AddItemState.MODIFY, Integer.parseInt(strings[1]));
                }
            } catch (Exception e) {
                weigt = Float.parseFloat(strings[0]);
                if (weigt == 0) {
                    scanCodeAdapter.getData().remove(Integer.parseInt(strings[1]));
                    updateTotal(item, OrderItemManger.AddItemState.REMOVE, Integer.parseInt(strings[1]));
                } else {
                   // item.setmAmount(weigt);
                    updateTotal(item, OrderItemManger.AddItemState.MODIFY, Integer.parseInt(strings[1]));
                }
            }
            scanCodeAdapter.notifyDataSetChanged();
        });
        //整单议价
      //  MutableLiveData<String[]> bargaiVm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getBargaiData();
//        bargaiVm.observe(this, strings -> {
//            if (payMoneyDialog.bargaiDialog.isVisible()) {
//                payMoneyDialog.bargaiDialog.dismiss();
//            }
//            if (!TextUtils.isEmpty(strings[0])) {
//                PromotionOrder promotionOrder = new PromotionOrder();
//                promotionOrder.setId(IdWorkerUtils.Companion.nextId());
//                promotionOrder.setTenantId(OrderManger.INSTANCE.getOrderBean().getTenantId());
//                promotionOrder.setOrderId(OrderManger.INSTANCE.getOrderBean().getId());
//                promotionOrder.setTradeNo(OrderManger.INSTANCE.getOrderBean().getTradeNo());
//                promotionOrder.setItemId(OrderManger.INSTANCE.getOrderBean().getId());
//                promotionOrder.setPromotionType(PromotionTypeFlag.整单议价);
//                promotionOrder.setReason("整单议价");
//                promotionOrder.setBargainPrice(Double.parseDouble(strings[0]));
//                if (OrderManger.INSTANCE.getOrderBean().getAmount() == 0) {
//                    promotionOrder.setDiscountRate(100);
//                } else {
//                    promotionOrder.setDiscountRate(MathUtil.divide(Double.parseDouble(strings[0]), OrderManger.INSTANCE.getOrderBean().getAmount(), 2));
//                }
//                promotionOrder.setDiscountAmount(MathUtil.subtract(OrderManger.INSTANCE.getOrderBean().getAmount(), Double.parseDouble(strings[0]), 2));
//                promotionOrder.setCreateDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
//                promotionOrder.setModifyDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
//                PromotionOrderDbManger.INSTANCE.insert(promotionOrder);
//                payMoneyDialog.setBargai(promotionOrder.getBargainPrice(), promotionOrder.getDiscountAmount());
//            }
//        });

        //整单折扣
//        MutableLiveData<String[]> discountVm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getDiscountData();
//        discountVm.observe(this, strings -> {
//            if (payMoneyDialog.discountDialog.isVisible()) {
//                payMoneyDialog.discountDialog.dismiss();
//            }
//            if (!TextUtils.isEmpty(strings[0])) {
//                PromotionOrder promotionOrder = new PromotionOrder();
//                promotionOrder.setId(IdWorkerUtils.Companion.nextId());
//                promotionOrder.setTenantId(OrderManger.INSTANCE.getOrderBean().getTenantId());
//                promotionOrder.setOrderId(OrderManger.INSTANCE.getOrderBean().getId());
//                promotionOrder.setTradeNo(OrderManger.INSTANCE.getOrderBean().getTradeNo());
//                promotionOrder.setItemId(OrderManger.INSTANCE.getOrderBean().getId());
//                promotionOrder.setPromotionType(PromotionTypeFlag.整单折扣);
//                if (promotionOrder.getPromotionType() == PromotionTypeFlag.线上促销) {
//                    promotionOrder.setOnlineFlag(1);
//                } else {
//                    promotionOrder.setOnlineFlag(0);
//                }
//                promotionOrder.setReason("整单折扣");
//                promotionOrder.setDiscountRate(Double.parseDouble(strings[0]) / 100);
//                BigDecimal a = new BigDecimal(strings[1]);
//                BigDecimal b = new BigDecimal(promotionOrder.getDiscountRate());
//                promotionOrder.setBargainPrice(Double.parseDouble(a.multiply(b) + ""));
//                promotionOrder.setAmount(Double.parseDouble(strings[1]));
//                promotionOrder.setDiscountAmount(MathUtil.multiply(OrderManger.INSTANCE.getOrderBean().getAmount(), promotionOrder.getDiscountRate(), 2));
//                promotionOrder.setCreateDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
//                promotionOrder.setModifyDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
//                PromotionOrderDbManger.INSTANCE.insert(promotionOrder);
//                payMoneyDialog.setDiscount(promotionOrder.getBargainPrice(), promotionOrder.getDiscountAmount());
//            }
//        });

        //整单取消
        InputDialogVM orderCancelVm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);
        orderCancelVm.getOrderCancelData().observe(this, phone -> {
            clearView();
        });
        //现金打印
        MutableLiveData<String[]> canPayVm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getCashPayData();
        canPayVm.observe(this, strings -> {
            if (paySuccessDialog == null) {
                paySuccessDialog = new PaySuccessDialog(getActivity(), "支付成功");
            }
            paySuccessDialog.show();

            //插入现金支付的数据-- 防止数据量过大 在线程中操作数据库
            ThreadDispatcher.getDispatcher().post(new Runnable() {
                @Override
                public void run() {
                    if (null != OrderManger.INSTANCE.getOrderBean()) {
                        OrderPayManger.INSTANCE.createPayInfo();
                        OrderPayManger.INSTANCE.getPayInfo().setPayNo(OrderPayManger.PayModeEnum.PAYCASH.getPayNo());
                        OrderPayManger.INSTANCE.getPayInfo().setName(OrderPayManger.PayModeEnum.PAYCASH.getPayName());
                        OrderPayManger.INSTANCE.getPayInfo().setFinishDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
                        OrderPayManger.INSTANCE.getPayInfo().setPayTime(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
                        OrderPayManger.INSTANCE.getPayInfo().setStatus(OrderPaymentStatusFlag.PAY_COMPLETE_STATE);
                        OrderPayManger.INSTANCE.getPayInfo().setPaidAmount(Double.parseDouble(strings[0]));//实收金额
                        OrderPayDbManger.INSTANCE.insert(OrderPayManger.payInfo);

                    //    OrderPointManger.Companion.setOrderPoint();
                     //   presenter.memberPointUpdate();

                        OrderManger.INSTANCE.getOrderBean().setChangeAmount(Double.parseDouble(strings[1]));
                        OrderManger.INSTANCE.getOrderBean().setFinishDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
                        OrderManger.INSTANCE.getOrderBean().setOrderStatus(OrderStatusFlag.COMPLETE_STATE);
                        OrderManger.INSTANCE.getOrderBean().setPaymentStatus(OrderPaymentStatusFlag.PAY_COMPLETE_STATE);
                     //   OrderObjectDbManger.INSTANCE.update(OrderManger.INSTANCE.getOrderBean());
                        if (OrderItemManger.INSTANCE.getList() != null) {
                            for (OrderItem orderItem : OrderItemManger.INSTANCE.getList()) {
                                orderItem.setFinishDate(DateUtil.getNowDateStr());
                            }
                            OrderItemDbManger.INSTANCE.update(OrderItemManger.INSTANCE.getList());
                        }

                    }
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (paySuccessDialog != null) {
                                if (paySuccessDialog.isShowing()) {
                                    paySuccessDialog.dismiss();
                                }
                            }
                            finishOrderToPrint();
                            clearView();
                        }
                    }, 1000);
                }
            });
            //支付成功调一次初始化副屏
          //  multipleDisplay.ShowMoneyDisplay(getActivity(), new ArrayList<>(), "0", "0", "0", "", "", "");
        });
        //结账页会员卡手机号+条码
        MutableLiveData<String[]> phoneOrCodeVm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getPhoneOrBarcodeData();
        phoneOrCodeVm.observe(this, strings -> {
            if (strings[0].length() == 11) {
                presenter.queryMemberByPhoneByPay(strings[0]);
            } else {
                presenter.queryMemberByBarcodeByPay(strings[0]);
            }
            memberPayMoney = strings[2];//会员支付的金额，区别于是否全额
            cashPayMoney = strings[3];//会员支付的金额，区别于是否全额
            isFullMoney = strings[1];//是否全额
        });
        //继续支付
        MutableLiveData<String[]> continueMemberPayVm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getContinueMemberPay();
        continueMemberPayVm.observe(this, strings -> {
            if (payLoadingDialog == null) {
                payLoadingDialog = new PayStatuDialog(getActivity(), "支付中");
         //       payLoadingDialog.show(PayingFlag.VIP_PAY);
            }

            isFullMoney = strings[3];
            memberPayMoney = strings[2];
            presenter.continueMemberPay2(new Gson().fromJson(strings[1], Member.class), strings[0], Float.parseFloat(strings[2]));
        });
        //调起会员+微信+支付宝支付
        MutableLiveData<String[]> getThirdCodeData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getThirdCodeData();
        getThirdCodeData.observe(this, strings -> {
            if (StringUtils.isNotEmpty(strings[1])) {
                presenter.scanCode(Float.parseFloat(strings[0]), strings[1]);
                cashPayMoney = strings[2];
                isFullMoney = strings[3];
            }
        });
        //单纯微信，会员，支付宝支付回调密码进行支付
        MutableLiveData<String> getPasswordData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getPasswordData();
        getPasswordData.observe(this, s -> presenter.continueMemberPay(s));

        //微信+支付宝回调显示loading
        MutableLiveData<String[]> getPayLoadingData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getPayLoadingData();
        getPayLoadingData.observe(this, strings -> {
            if (payLoadingDialog == null) {
                payLoadingDialog = new PayStatuDialog(getActivity(), "支付中");
                if ("WXZF".equals(strings[0])) {
               //     payLoadingDialog.show(PayingFlag.WECHAT_PAY);
                } else if ("ZFBZF".equals(strings[0])) {
                //    payLoadingDialog.show(PayingFlag.ALIPAY);
                } else if ("UPSMZF".equals(strings[0])) {
                //    payLoadingDialog.show(PayingFlag.UNION_PAY);
                } else {
                //    payLoadingDialog.show(PayingFlag.VIP_PAY);
                }
            }
        });
        //选择营业员
//        MutableLiveData<String> businesserLiveData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getBusinesserLiveData();
//        businesserLiveData.observe(this,json -> {
//            Worker w = GsonHelper.fromJson(json,Worker.class);
//            if(OrderManger.INSTANCE.getOrderBean() != null){
//                OrderManger.INSTANCE.getOrderBean().setSalesName(w.getName());
//                OrderManger.INSTANCE.getOrderBean().setSalesCode(w.getNo());
//            }
//        });
        //退货成功
//        ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getRejectedLiveData().observe(this,s -> {
////            presenter.decPoint();
//            clearView();
//            showPreviousOrder();
//        });
        //进入退款模式
//        ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getRejectedShortcut().observe(this,s -> {
//            mPayMode = PayMode.REJECTED_PRODUCT;
//            scanCodeAdapter.notifyDataSetChanged();
//            String totalPrice = StringKotlin.Companion.formatPrice(vipPriceArr[0]);
//            if(mPayMode == PayMode.REJECTED_PRODUCT){
//                tvYouhui.setText((StringTextUtil.formatTextNumString2(vipPriceArr == null ? "0.00" : StringKotlin.Companion.formatPrice(-vipPriceArr[1]))));
//                tvTotalPrice.setText(StringTextUtil.formatTextNumString2("-"+totalPrice));
//            }
//            presenter.setOrderValue(scanCodeAdapter.getData(),0,vipPriceArr[0],scanCodeAdapter.getMMember());
//        });
        //挂单
//        ViewModelProviders.of(getActivity()).get(HangVm.class).getHangOption().observe(this,s -> {
//            if(s.equals(OrderHangState.HANG)){
//                List<OrderItem> orderItemList = OrderItemManger.INSTANCE.getList();
//                for (int i = 0; i < scanCodeAdapter.getData().size(); i++) {
//                    orderItemList.get(i).setItemData(GsonHelper.toJson(scanCodeAdapter.getData().get(i)));
//                }
//                OrderItemDbManger.INSTANCE.update(orderItemList);
//                clearView();
//            } else if(s.equals(OrderHangState.TAKE)){
//                List<MultipleQueryProduct> products = new ArrayList<>();
//                for (OrderItem orderItem : OrderItemManger.INSTANCE.getList()) {
//                    if(!TextUtils.isEmpty(orderItem.getItemData())){
//                        products.add(GsonHelper.fromJson(orderItem.getItemData(),MultipleQueryProduct.class));
//                    }
//                }
//                scanCodeAdapter.setNewData(products);
//                if(OrderManger.INSTANCE.getOrderBean().getIsMember() == 1){
//                    presenter.queryMemberByPhone(OrderManger.INSTANCE.getOrderBean().getMemberMobileNo());
//                } else {
//                    updatePrice();
//                }
//            }
//        });

        llVip.setOnClickListener(new NoDoubleListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (null == scanCodeAdapter.getMMember()) {
                    new VipVerifyDialog.Builder().build().show(getFragmentManager(), "vipVerify");
                } else {
                    new VipCancelDialog.Builder().build().show(getFragmentManager(), "vipCancel");
                }
            }
        });
    }

    private void finishOrderToPrint() {
        Intent intent = new Intent();
        intent.putExtra("tradeNo", OrderManger.INSTANCE.getOrderBean().getTradeNo());
      //  intent.putExtra(KeyConstrant.KEY_PRINT_ACTION, Global.IPrint.ACTION_PAY);
        PrintService.Companion.enqueueWork(getActivity(), intent);
     //   presenter.checkCoupon();
        if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase())) {
            Intent intent2 = new Intent();
            intent2.putExtra("tradeNo", OrderManger.INSTANCE.getOrderBean().getTradeNo());
         //   intent2.putExtra(KeyConstrant.KEY_PRINT_ACTION, Global.IPrint.ACTION_PAY);
            SummiPrintService.Companion.enqueueWork(getActivity(), intent2);
        }
        showPreviousOrder();

    }

    private void showPreviousOrder(){
    //    OrderObject lastOrder = OrderObjectDbManger.INSTANCE.checkPreviousOrder();
//        if(lastOrder != null){
//            tvLastOrderNo.setText("上单：" + lastOrder.getTradeNo());
//            tvLastOrderNum.setText("数量：" + df.format(lastOrder.getTotalQuantity()));
//            tvLastOrderTime.setText("时间：" + lastOrder.getSaleDate().substring(11));
//            tvLastOrderCoupon.setText("优惠：" + df.format(lastOrder.getDiscountAmount()));
//            tvLastOrderZero.setText("找零：" + df.format(lastOrder.getChangeAmount()));
//            tvLastTotalOrder.setText("合计：" + df.format(lastOrder.getReceivableAmount()));
//        }
    }

    String memberPayMoney;//会员将付金额
    String cashPayMoney;//现金已收金额
    String isFullMoney;

    private void updatePrice() {
        List<MultipleQueryProduct> list = scanCodeAdapter.getData();
        vipPriceArr = presenter.getVipPrice(scanCodeAdapter.getMMember(), list);
        String totalPrice = StringKotlin.Companion.formatPrice(vipPriceArr[0]);
        tvTotalPrice.setText(StringTextUtil.formatTextNumString2(totalPrice));
        tv_totalNum.setText(presenter.getTotalAmount(list) + "");
        tv_totalNum.setTag(presenter.getTotalAmount(list));
        tvTotalPrice.setTag(totalPrice);
        tvYouhui.setText(StringTextUtil.formatTextNumString2(vipPriceArr == null ? "0" : StringKotlin.Companion.formatPrice(vipPriceArr[1])));
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (payLoadingDialog != null) {
            if (payLoadingDialog.isShowing()) {
                payLoadingDialog.dismiss();
                payLoadingDialog = null;
            }
        }
    }

    @Override
    public void onPaySuccess() {
        //OrderPointManger.Companion.setOrderPoint();
        if (payLoadingDialog != null) {
            if (payLoadingDialog.isShowing()) {
                payLoadingDialog.dismiss();
            }
        }
        if (paySuccessDialog == null) {
            paySuccessDialog = new PaySuccessDialog(getActivity(), "支付成功");
        }
        paySuccessDialog.show();
        if (payMoneyDialog != null) {
            if (payMoneyDialog.getDialog().isShowing()) {
                payMoneyDialog.dismiss();
            }
        }
        if ("yes".equals(isFullMoney)) {//插入混合支付的数据即可
            OrderPayManger.INSTANCE.createPayInfo();
            OrderPayManger.INSTANCE.getPayInfo().setPayNo(OrderPayManger.PayModeEnum.PAYCASH.getPayNo());
            OrderPayManger.INSTANCE.getPayInfo().setName(OrderPayManger.PayModeEnum.PAYCASH.getPayName());
            OrderPayManger.INSTANCE.getPayInfo().setPayTime(DateUtil.getNowDateStr());
            OrderPayManger.INSTANCE.getPayInfo().setFinishDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
            OrderPayManger.INSTANCE.getPayInfo().setStatus(OrderPaymentStatusFlag.PAY_COMPLETE_STATE);
            OrderPayManger.INSTANCE.getPayInfo().setPaidAmount(Double.parseDouble(cashPayMoney));//实收金额
            OrderPayDbManger.INSTANCE.insert(OrderPayManger.payInfo);
        }

        // 防止数据量过大 在线程中操作数据库
        ThreadDispatcher.getDispatcher().post(new Runnable() {
            @Override
            public void run() {
                if (null != OrderManger.INSTANCE.getOrderBean()) {
                    //在这里判断是否是全额支付，插会员支付+现金混合支付的表
                    OrderManger.INSTANCE.getOrderBean().setOrderStatus(OrderStatusFlag.COMPLETE_STATE);
                    OrderManger.INSTANCE.getOrderBean().setFinishDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
                    OrderManger.INSTANCE.getOrderBean().setPaymentStatus(OrderPaymentStatusFlag.PAY_COMPLETE_STATE);
              //      OrderObjectDbManger.INSTANCE.update(OrderManger.INSTANCE.getOrderBean());
                    if (OrderItemManger.INSTANCE.getList() != null) {
                        OrderItemDbManger.INSTANCE.update(OrderItemManger.INSTANCE.getList());
                    }
                }
            }
        });
        finishOrderToPrint();
        //支付成功调一次初始化副屏，因为暂时只有这一个界面有使用
      //  multipleDisplay.ShowMoneyDisplay(getActivity(), new ArrayList<>(), "0", "0", "0", "", "", "");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (paySuccessDialog != null) {
                    if (paySuccessDialog.isShowing()) {
                        paySuccessDialog.dismiss();
                    }
                }
                clearView();
            }
        }, 1000);
    }

    private void updateTotal(MultipleQueryProduct product, OrderItemManger.AddItemState state, int position) {
        List<MultipleQueryProduct> list = scanCodeAdapter.getData();
        vipPriceArr = presenter.getVipPrice(scanCodeAdapter.getMMember(), list);
        String totalPrice = StringKotlin.Companion.formatPrice(vipPriceArr[0]);
        tvTotalPrice.setText(StringTextUtil.formatTextNumString2(totalPrice));
        tv_totalNum.setText(presenter.getTotalAmount(list) + "");
        tv_totalNum.setTag(presenter.getTotalAmount(list));
        tvYouhui.setText((StringTextUtil.formatTextNumString2(vipPriceArr == null ? "0" : StringKotlin.Companion.formatPrice(vipPriceArr[1]))));
        tvTotalPrice.setTag(totalPrice);
        presenter.setOrderValue(list, vipPriceArr[1], Float.parseFloat(totalPrice), scanCodeAdapter.getMMember());
        presenter.addOrderItem(product, state, position, scanCodeAdapter.getMMember(), OrderItemManger.JoinTypeEnmu.SCANCODE);
//        if(mPayMode == PayMode.REJECTED_PRODUCT){
//            tvYouhui.setText((StringTextUtil.formatTextNumString2(vipPriceArr == null ? "0" : StringKotlin.Companion.formatPrice(-vipPriceArr[1]))));
//            tvTotalPrice.setText(StringTextUtil.formatTextNumString2("-"+totalPrice));
//        }
        showDisplay();
        if(isSelectBusinesser){
            if(OrderManger.INSTANCE.getOrderBean() != null && TextUtils.isEmpty(OrderManger.INSTANCE.getOrderBean().getSalesCode())){
                WorkerSelectDialog wDialog = new WorkerSelectDialog.Builder().build();
                Bundle bundle = new Bundle();
                bundle.putString(KeyConstrant.KEY_WORKER_BEAN,GsonHelper.toJson(WorkerDbManger.getInstance().loadAll()));
                wDialog.setArguments(bundle);
                wDialog.show(getChildFragmentManager(),"w");
            }
        }
    }

    @Override
    public void initData() {

    }

    private PaySuccessDialog paySuccessDialog;
    private PayStatuDialog payLoadingDialog;
    private PayMoneyDialog payMoneyDialog;

//    @Override
//    public void onScan(String barcode) {
//        if (StringUtils.isNoneBlank(barcode)) {
//            presenter.queryProductByBarcode(barcode);
//            etCode.setText(null);//显示出扫描到的条码
//            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(etCode.getWindowToken(), 0);
//            etCode.setFocusable(false);
//            etCode.setFocusableInTouchMode(false);
//        }
//    }

    @Override
    protected CashPresenter createPresenter() {
        CashPresenter p = new CashPresenter();
        p.createOrder();
        return p;
    }

//    @Override
//    public void onDownloadFinish() {
//        presenter.initOnlineParamas();
//    }

    @OnClick({R.id.tv_test, R.id.tv_pay, R.id.ll_shift_key, R.id.ll_worker})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shift_key:
                // new ShiftKeyDialog.Builder().build().show(getFragmentManager(), "shiftlog");
                break;
            case R.id.ll_worker:
//                   WorkerSelectDialog build = new WorkerSelectDialog.Builder().build();
//                   Bundle bundle2 = new Bundle();
//                   bundle2.putString(KeyConstrant.KEY_WORKER_BEAN,GsonHelper.toJson(WorkerDbManger.getInstance().loadAll()));
//                   build.setArguments(bundle2);
//                   build.show(getFragmentManager(), "worker");
                break;
            case R.id.tv_test:
                if (StringUtils.isNotEmpty(etCode.getText().toString().trim())) {
                    presenter.queryProductByBarcode(etCode.getText().toString().trim());
                    etCode.setText(null);
                }
//                presenter.queryProductByBarcode("02000002");// test  coding
                presenter.queryProductByBarcode("4009932009132");// test  coding
                break;
            case R.id.tv_pay:
//                if(mPayMode == PayMode.PAY){
//                    if (scanCodeAdapter.getData().size() > 0) {
//                        payMoneyDialog = new PayMoneyDialog.Builder().build();
//                        Bundle bundle = new Bundle();
//                        AccountsBean accountsBean = new AccountsBean()
//                                .setTotalMoney((String) tvTotalPrice.getTag())
//                                .setReduction(vipPriceArr == null ? "0" : StringKotlin.Companion.formatPrice(vipPriceArr[1]));
//                        bundle.putSerializable(KeyConstrant.KEY_ACCOUNT_BEAN, accountsBean);
//                        bundle.putSerializable("scanList", (Serializable) scanCodeAdapter.getData());
//                        bundle.putSerializable("member", (Serializable) scanCodeAdapter.getMMember());
//                        bundle.putString("tradeNo", OrderManger.INSTANCE.getOrderBean().getTradeNo());
//                        bundle.putSerializable("couponList", (Serializable) couponList);
//                        payMoneyDialog.setArguments(bundle);
//                        payMoneyDialog.show(getFragmentManager(), "payMoney");
//                    } else {
//                        UiUtils.showToastShort("主人，购物车为空，快去选购吧");
//                    }
//                } else {
//                    if (scanCodeAdapter.getData().size() > 0){
//                        RejectedMoneyDialog rejectedMoneyDialog = new RejectedMoneyDialog.Builder().build();
//                        AccountsBean accountsBean = new AccountsBean()
//                                .setTotalMoney((String) tvTotalPrice.getTag())
//                                .setReduction(vipPriceArr == null ? "0" : StringKotlin.Companion.formatPrice(vipPriceArr[1]));
//                        String json = GsonHelper.toJson(OrderItemManger.INSTANCE.getList());
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable(KeyConstrant.KEY_ACCOUNT_BEAN,accountsBean);
//                        bundle.putString("data", json);
//                        rejectedMoneyDialog.setArguments(bundle);
//                        rejectedMoneyDialog.show(getChildFragmentManager(),"r");
//                    } else {
//                        UiUtils.showToastShort("主人，购物车为空，快去选购吧");
//                    }
//                }

                break;
        }
    }

    @Override
    public void queryMemberSuccess(Member member) {
        scanCodeAdapter.setMMember(member);
        scanCodeAdapter.notifyDataSetChanged();
        tvViPhone.setText("会员：" + UiUtils.hidePhoneNum(member.getMobile()));
        tvVipName.setText("姓名：" + member.getName());
        tvViPoint.setText("积分：" + member.getTotalPoint());
        tvVipLevel.setText("等级：" + member.getMemberLevelName());
        tvVipYue.setText("余额：" + member.getTotalAmount());

//        for (int i = 0; i < member.getCardList().size(); i++) {
//            if (member.getCardList().get(i).getStatus() == MemberCardState.LOSS) {
//                UiUtils.showToastShort("会员卡状态：挂失，请确认");
//            } else if (member.getCardList().get(i).getStatus() == MemberCardState.FREEZE) {
//                UiUtils.showToastShort("会员卡状态：冻结，请确认");
//            } else if (member.getCardList().get(i).getStatus() == MemberCardState.UNREGISTER) {
//                UiUtils.showToastShort("会员卡状态：销户，请确认");
//            } else if (member.getCardList().get(i).getStatus() == MemberCardState.GIFT) {
//                UiUtils.showToastShort("会员卡状态：转赠中，请确认");
//            }
//        }

        List<MultipleQueryProduct> list = scanCodeAdapter.getData();
        vipPriceArr = presenter.getVipPrice(scanCodeAdapter.getMMember(), list);
        String totalPrice = StringKotlin.Companion.formatPrice(vipPriceArr[0]);
//        if(mPayMode == PayMode.REJECTED_PRODUCT){
//            tvTotalPrice.setText(StringTextUtil.formatTextNumString2("-"+totalPrice));
//            tv_totalNum.setText("-"+presenter.getTotalAmount(list));
//            tvYouhui.setText(StringTextUtil.formatTextNumString2(
//                    vipPriceArr == null ? "0" : StringKotlin.Companion.formatPrice(-vipPriceArr[1])));
//        } else {
//            tvTotalPrice.setText(StringTextUtil.formatTextNumString2(totalPrice));
//            tv_totalNum.setText(presenter.getTotalAmount(list) + "");
//            tvYouhui.setText(StringTextUtil.formatTextNumString2(
//                    vipPriceArr == null ? "0" : StringKotlin.Companion.formatPrice(vipPriceArr[1])));
//        }

        tvTotalPrice.setTag(totalPrice);
        //以下是重复选择会员，再选择会员，重新赋值，不然小票上实付金额不对
        if (OrderManger.INSTANCE.getOrderBean() == null) {
            presenter.createOrder();
        }
        OrderManger.INSTANCE.getOrderBean().setIsMember(1);
        OrderManger.INSTANCE.getOrderBean().setMemberMobileNo(member.getMobile());
        OrderManger.INSTANCE.getOrderBean().setReceivableAmount(Double.parseDouble(tvTotalPrice.getText().toString().trim()));
        OrderManger.INSTANCE.getOrderBean().setDiscountAmount(Double.parseDouble(tvYouhui.getText().toString().trim()));
//        OrderManger.INSTANCE.getOrderBean().setMemberLevelNo(member.getMemberLevelNo());
//        OrderManger.INSTANCE.getOrderBean().birthday = member.getBirthday();
//        showDisplay();
//        presenter.memberCouponQuery(1, 2000, CouponConstrant.CouponUseStatus.GAIN, member.getId());
    }

   // private List<ElecCoupon> couponList;
//
//    @Override
//    public void memberCouponQuerySuccess(@NotNull List<ElecCoupon> eleCoupon) {
//        couponList = eleCoupon;
//    }

    //副屏显示
    private void showDisplay() {
//        if (multipleDisplay.getPayMoneyPresentation() != null &&
//                multipleDisplay.getPayMoneyPresentation().getPresentationAdapter() != null) {
//            multipleDisplay.getPayMoneyPresentation().getPresentationAdapter().setMMember(scanCodeAdapter.getMMember());
//        }
//
//        multipleDisplay.ShowMoneyDisplay(getActivity(), scanCodeAdapter.getData(), tv_totalNum.getText().toString().trim(),
//                tvTotalPrice.getText().toString().trim(), tvYouhui.getText().toString().trim(),
//                (scanCodeAdapter.getMMember() == null) ? "" : scanCodeAdapter.getMMember().getCardList().get(0).getCardNo(),
//                (scanCodeAdapter.getMMember() == null) ? "" : scanCodeAdapter.getMMember().getName(),
//                (scanCodeAdapter.getMMember() == null) ? "" : scanCodeAdapter.getMMember().getTotalPoint() + "");
    }

    @Override
    public void queryProductSuccess(@org.jetbrains.annotations.Nullable List<? extends MultipleQueryProduct> products) {
        if (OrderManger.INSTANCE.getOrderBean() == null) {
            presenter.createOrder();
        }
        if (products != null) {
            if (products.size() == 1) {
                MultipleQueryProduct product = products.get(0);
                List<MultipleQueryProduct> list = scanCodeAdapter.getData();
                if (list.contains(product)) {
                    int i = 0;
                    for (MultipleQueryProduct bean : list) {
                        if (bean.equals(product)) {
                            bean.setmAmount(bean.getmAmount() + 1);
                            updateTotal(product, OrderItemManger.AddItemState.MODIFY, i);
                            break;
                        }
                        i++;
                    }
                } else {
                    list.add(product);
                    updateTotal(product, OrderItemManger.AddItemState.ADD, 0);
                }
                scanCodeAdapter.notifyDataSetChanged();
            } else {
                //多规格
                SpecsSelectDialog selectDialog = new SpecsSelectDialog.Builder().build();
                Bundle bundle = new Bundle();
                bundle.putString(KeyConstrant.KEY_MULTIPLE_SPECS_PRODUCT, GsonHelper.toJson(products));
                selectDialog.setArguments(bundle);
                selectDialog.show(getFragmentManager(), "specs");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mHandler.removeCallbacksAndMessages(null);
        mTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer.cancel();
        mTimer = new Timer();
        //mTimer.schedule(new DateTimerTask(tvCurrenTime),0,1000);
    }

    @Override
    public void onStop() {
        super.onStop();
        mTimer.cancel();
    }

    private void clearView(){
        scanCodeAdapter.getData().clear();
        scanCodeAdapter.setMMember(null);
        TPUtils.remove(getContext(),KeyConstrant.KEY_MEMBER);
        scanCodeAdapter.notifyDataSetChanged();
        OrderManger.INSTANCE.clear();
        presenter.createOrder();
        tvViPhone.setText("会员：");
        tvVipName.setText("姓名：");
        tvViPoint.setText("积分：");
        tvVipLevel.setText("等级：");
        tvVipYue.setText("余额：");
        tvTotalPrice.setText("0");
        tvYouhui.setText("0");
        tvZero.setText("0");
        tv_totalNum.setText("0");
//        mPayMode = PayMode.PAY;
//        if(couponList != null)
//            couponList.clear();
    }

    private void insertMemberInfo(Member member) {
        TPUtils.putObject(getContext(),KeyConstrant.KEY_MEMBER,member);
        OrderManger.INSTANCE.getOrderBean().setDiscountRate(OrderManger.INSTANCE.getOrderBean().getAmount() == 0
                ? 0 : vipPriceArr[1] / OrderManger.INSTANCE.getOrderBean().getAmount());
        OrderManger.INSTANCE.getOrderBean().setIsMember(member != null ? 1 : 0);
        OrderManger.INSTANCE.getOrderBean().setMemberNo(member != null ? member.getNo() : "");
        OrderManger.INSTANCE.getOrderBean().setMemberName(member != null ? member.getName() : "");
        OrderManger.INSTANCE.getOrderBean().setMemberMobileNo(member != null ? member.getMobile() : "");
        OrderManger.INSTANCE.getOrderBean().setMemberId(member != null ? member.getId() : "");
        OrderManger.INSTANCE.getOrderBean().setReceivableRemoveCouponAmount(OrderManger.INSTANCE.getOrderBean().getReceivableAmount());
        OrderManger.INSTANCE.getOrderBean().setModifyDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
        OrderManger.INSTANCE.getOrderBean().setExt2(member != null ? StringKotlin.Companion.formatPrice((float) member.getTotalAmount()) : "");//设置会员余额
     //   OrderObjectDbManger.INSTANCE.update(OrderManger.INSTANCE.getOrderBean());
    }

    @Override
    public void queryMemberSuccessByPay(@NotNull Member member) {
        insertMemberInfo(member);
        if (member.getIsNoPwd() == 1) {  // 是否免密  1：是；0：否
            if (member.getNpAmount() >= Double.parseDouble(memberPayMoney)) {
                presenter.continueMemberPay2(member, member.getPassword(), Float.parseFloat(memberPayMoney));
            } else {
                MemberPsdDialog build = new MemberPsdDialog.Builder().build();
                Bundle bundle = new Bundle();
                bundle.putSerializable("member", member);
                bundle.putString("memberPayMoney", memberPayMoney);
                bundle.putString("isFullMoney", isFullMoney);
                build.setArguments(bundle);
                build.show(getFragmentManager(), "p");
            }
        } else {
            MemberPsdDialog build = new MemberPsdDialog.Builder().build();
            Bundle bundle = new Bundle();
            bundle.putSerializable("member", member);
            bundle.putString("memberPayMoney", memberPayMoney);
            bundle.putString("isFullMoney", isFullMoney);
            build.setArguments(bundle);
            build.show(getFragmentManager(), "p");
        }
    }

//    @Override
//    public void onSetWorkerShow(boolean isShow) {
//        isSelectBusinesser = isShow;
//    }
}

package com.newsuper.t.markert.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FixDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.trans.network.utils.GsonHelper;
import com.newsuper.t.R;
import com.newsuper.t.markert.db.manger.PayModeDbManger;
import com.newsuper.t.markert.dialog.vm.InputDialogVM;
import com.newsuper.t.markert.entity.AccountsBean;
import com.newsuper.t.markert.entity.Member;
import com.newsuper.t.markert.entity.MultipleQueryProduct;
import com.newsuper.t.markert.entity.PayMode;
import com.newsuper.t.markert.entity.RegistrationCode;
import com.newsuper.t.markert.utils.KeyConstrant;
import com.newsuper.t.markert.utils.KeyboardUtils;
import com.newsuper.t.markert.utils.NoDoubleListener;
import com.newsuper.t.markert.utils.StringTextUtil;
import com.newsuper.t.markert.utils.ToolScanner;
import com.newsuper.t.markert.utils.UiUtils;
import com.newsuper.t.markert.view.SixteenKeyboardView;
import com.transpos.tools.tputils.TPUtils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayMoneyDialog extends FixDialogFragment implements SixteenKeyboardView.OnKeyboardInputListener, ToolScanner.OnScanSuccessListener {
    @BindView(R.id.tv_payStore)
    TextView tvPayStore;
    @BindView(R.id.tv_yingshou)
    TextView tvYingshou;
    @BindView(R.id.tv_yishou)
    TextView tvYishou;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.tv_weishou)
    TextView tvWeishou;
    @BindView(R.id.tv_zhaoling)
    TextView tvZhaoling;
    @BindView(R.id.et_pay)
    EditText etPay;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.ll_vip)
    LinearLayout llVip;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.ll_other)
    LinearLayout llOther;
    @BindView(R.id.btn_cancelOrder)
    Button btnCancelOrder;
    @BindView(R.id.btn_discount)
    Button btnDiscount;
    @BindView(R.id.btn_bargaining)
    Button btnBargaining;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.payMoney)
    SixteenKeyboardView payMoney;

    private Builder mBuilder;
    private AccountsBean accountsBean;
    private Member member;
    List<MultipleQueryProduct> scanList;
    ToolScanner toolScanner = new ToolScanner(PayMoneyDialog.this);

    private PayMoneyDialog setmBuilder(Builder mBuilder) {
        this.mBuilder = mBuilder;
        return this;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBuilder == null) {
            mBuilder = new Builder();
        }
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = mBuilder.gravity;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable());
        window.setWindowAnimations(mBuilder.animations);
        DisplayMetrics dm = new DisplayMetrics(); //设置边距
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(mBuilder.withParams, mBuilder.heightParams);
        setCancelable(false);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_paymoney, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    DecimalFormat df ;
    private void initView() {
        df = new DecimalFormat("#0.00");
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                toolScanner.analysisKeyEvent(event);
                return true;
            }
        });
        KeyboardUtils.disableShowInput(etPay);
        payMoney.setmKeyboardInputListener(this);
        RegistrationCode registrationCode = TPUtils.getObject(getActivity(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);
        tvPayStore.setText(String.format("%s-%s-%s", registrationCode.getTenantId(), registrationCode.getStoreNo(), registrationCode.getStoreName()));
        accountsBean = (AccountsBean) getArguments().getSerializable(KeyConstrant.KEY_ACCOUNT_BEAN);
        tvYingshou.setText(StringTextUtil.formatTextNumString(accountsBean.getTotalMoney()));
        tvWeishou.setText(df.format(Double.parseDouble(accountsBean.getTotalMoney())));
        tvYouhui.setText(df.format(Double.parseDouble(accountsBean.getReduction())));
        scanList = (List<MultipleQueryProduct>) getArguments().getSerializable("scanList");
        member = (Member) getArguments().getSerializable("member");
        etPay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //改变之前会执行的方法
            }

            @Override //实时监听
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                etPay.removeTextChangedListener(this); // 移除监听
//                // etPay.setText(/*对字符串s进行相关处理*/);
//                String input = s.toString();
//                if (input.length() != 0) {
//                    if (input.length() < 6) {
//                        tvZhaoling.setText(sub(input, tvYingshou.getText().toString().trim()) + "");
//                    }
//                }
//                etPay.addTextChangedListener(this);
            }

            //改变之后会执行的方法、注意,在此写验证方法将导致死循环,请不要在这里进行验证操作
            @Override
            public void afterTextChanged(Editable s) {
                etPay.setSelection(etPay.length());
            }
        });
    }

    @Override
    public void onChanged(String content) {
        etPay.setText(content);
    }

    @Override
    public void onConfirm(String content) {
        String input = etPay.getText().toString().trim();
        if (StringUtils.isEmpty(input)) {
            UiUtils.showToastShort("请正确输入");
        } else {
            try {
                if (Float.parseFloat(input) == 0) {
                    UiUtils.showToastShort("输入不能为0");
                }
            } catch (Exception e) {
                UiUtils.showToastShort("请正确输入");
                return;
            }

            if (sub(input, tvYingshou.getText().toString().trim()).doubleValue() > 100) {
                UiUtils.showToastShort("找零金额不能超过100");
                return;
            }
            if (input.endsWith(".")) {
                tvYishou.setText(input + "00");
            } else if (StringTextUtil.isNumeric(input)) {
                tvYishou.setText(input + ".00");
            } else {
                tvYishou.setText(input);
            }

            String yingshou = tvYingshou.getText().toString().trim();
            String shishou = tvYishou.getText().toString().trim();
            tvWeishou.setText(sub(yingshou, shishou) + "");
            // Log.i("NEWSUPER", "------tvZhaoling-------" + sub(shishou, yingshou) + "");
            BigDecimal zongjia = new BigDecimal(yingshou);
            BigDecimal yishou = new BigDecimal(shishou);
            if (yishou.compareTo(zongjia) == 0 || (yishou.compareTo(zongjia) == 1)) {
                InputDialogVM vm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);
                vm.getCashPayData().postValue(new String[]{input, sub(shishou, yingshou) + ""});
                dismiss(); //   a<b, 返回-1    a=b，返回0    a>b, 返回1
            } else if (yishou.compareTo(zongjia) == -1) {
                tvZhaoling.setText("0");

                llVip.setOnClickListener(new NoDoubleListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {

                        BigDecimal a = new BigDecimal(tvZhaoling.getText().toString());
                        BigDecimal b = new BigDecimal("0");
                        if (a.compareTo(b) == 0 || (a.compareTo(b) == 1)) {
                            if (null != member) {
                                if (StringUtils.isEmpty(member.getCardList().get(0).getCardNo())) {
                                    UiUtils.showToastShort("无会员卡不能进行会员卡支付");
                                    return;
                                }
                            }
                            MemberPhoneDialog dialog = new MemberPhoneDialog.Builder().build();
                            Bundle bundle = new Bundle();
                            bundle.putDouble("nowPay", sub(yingshou, shishou).doubleValue());//会员将支付的金额
                            bundle.putDouble("cashPay", Double.parseDouble(shishou));//现金已付的钱
                            bundle.putString("isFullMoney", "yes");
                            dialog.setArguments(bundle);
                            dialog.show(getFragmentManager(), "memberPhone");
                        }
                    }
                });
                llOther.setOnClickListener(new NoDoubleListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {

                    }
                });
                llCoupon.setOnClickListener(new NoDoubleListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        llCoupon.setBackgroundResource(R.drawable.shape_gray_3dp);
                    }
                });
                btnBargaining.setOnClickListener(new NoDoubleListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        UiUtils.showToastShort("已存在支付信息，禁止整单议价，如需继续，请点击返回收银");
                    }
                });
                btnDiscount.setOnClickListener(new NoDoubleListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        UiUtils.showToastShort("已存在支付信息，禁止整单折扣，如需继续，请点击返回收银");
                    }
                });
            }
        }

    }


    public BigDecimal sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2);   // 提供精确的减法运算。
    }

    @OnClick({R.id.iv_clear, R.id.ll_vip, R.id.ll_coupon, R.id.ll_other,
            R.id.btn_cancelOrder, R.id.btn_discount, R.id.btn_bargaining, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                // etPay.setText("");
                break;
            case R.id.ll_vip:
                BigDecimal a = new BigDecimal(tvZhaoling.getText().toString());
                BigDecimal b = new BigDecimal("0");
                if (a.compareTo(b) == 0 || (a.compareTo(b) == 1)) {
                    if (null != member) {
                        if (StringUtils.isEmpty(member.getCardList().get(0).getCardNo())) {
                            UiUtils.showToastShort("无会员卡不能进行会员卡支付");
                            break;
                        }
                    }
                    MemberPhoneDialog dialog = new MemberPhoneDialog.Builder().build();
                    Bundle bundle = new Bundle();
                    bundle.putDouble("nowPay", Double.parseDouble(tvYingshou.getText().toString().trim()));//会员将支付的金额
                    bundle.putDouble("cashPay", Double.parseDouble(tvYishou.getText().toString().trim()));//现金已支付的金额,方便插数据使用
                    bundle.putString("isFullMoney", "not");
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "memberPhone");
                }
                break;
            case R.id.ll_coupon:
//                CouponSelectDialog couponSelectDialog = new CouponSelectDialog.Builder().build();
//                Bundle bundle = new Bundle();
//                bundle.putString(KeyConstrant.KEY_MULTIPLE_SPECS_PRODUCT, GsonHelper.toJson(scanList));
//                couponSelectDialog.setArguments(bundle);
//                couponSelectDialog.show(getFragmentManager(), "coupon");

                break;
            case R.id.ll_other:   //test coding
                PayTypeSelectDialog payTypeSelectDialog = new PayTypeSelectDialog.Builder().build();
                Bundle bundle1 = new Bundle();
                bundle1.putString(KeyConstrant.KEY_PAYTYPE_BEAN, GsonHelper.toJson(PayModeDbManger.getInstance().loadAll()));
                payTypeSelectDialog.setArguments(bundle1);
                payTypeSelectDialog.show(getFragmentManager(), "paytype");

//                String yingshou = tvYingshou.getText().toString().trim();
//                String shishou = tvYishou.getText().toString().trim();
//                tvWeishou.setText(sub(yingshou, shishou) + "");
//                BigDecimal zongjia = new BigDecimal(yingshou);
//                BigDecimal yishou = new BigDecimal(shishou);
//                if (yishou.compareTo(zongjia) == 0 || (yishou.compareTo(zongjia) == 1)) {
//                    //代表单纯微信+支付宝+会员支付,等于全额调3方支付
//                    if (!StringUtils.isEmpty(etPay.getText().toString().trim())) {
//                        InputDialogVM vm2 = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);
//                        //   vm2.getThirdCodeData().postValue(new String[]{tvYingshou.getText().toString().trim(), etPay.getText().toString().trim()}); //value1为将收金额,value2为barcode
//                        vm2.getThirdCodeData().postValue(new String[]{"0.01", etPay.getText().toString().trim()}); //value1为将收金额,value2为barcode
//                    }
//                } else if (yishou.compareTo(zongjia) == -1) {
//                    if (!StringUtils.isEmpty(etPay.getText().toString().trim())) {
//                        InputDialogVM vm2 = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);
//                        //   vm2.getThirdCodeData().postValue(new String[]{String.valueOf(sub(yingshou,shishou).doubleValue()), etPay.getText().toString().trim()});
//                        vm2.getThirdCodeData().postValue(new String[]{"0.01", etPay.getText().toString().trim()});//test coding
//                    }
//                }
                break;
            case R.id.btn_cancelOrder:
                InputDialogVM vm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);
                vm.getOrderCancelData().postValue("");
                dismiss();
                break;
            case R.id.btn_discount:
                for (int i = 0; i < scanList.size(); i++) {
                    if (scanList.get(i).getForeDiscount() == 0) {
                        UiUtils.showToastShort("未开启前台议价");
                    } else {
                        new DiscountDialog.Builder().build().show(getFragmentManager(), "discount");
                    }
                }
                break;
            case R.id.btn_bargaining:
                for (int i = 0; i < scanList.size(); i++) {
                    if (scanList.get(i).getForeBargain() == 0) {
                        UiUtils.showToastShort("未开启前台议价");
                    } else {
                        new BargaiDialog.Builder().build().show(getFragmentManager(), "bargai");
                    }
                }
                break;
            case R.id.btn_back:
                dismiss();
                break;
        }
    }

    @Override
    public void onScanSuccess(String barcode) {
        String yingshou = tvYingshou.getText().toString().trim();
        String yishou = tvYishou.getText().toString().trim();
        if (Double.parseDouble(yishou) > 0) {
            InputDialogVM vm2 = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);        //混合支付
            vm2.getThirdCodeData().postValue(new String[]{String.valueOf(sub(yingshou, yishou).doubleValue()), barcode,yishou,"yes"});  //发版需使用这句，下面只是测试
            //vm2.getThirdCodeData().postValue(new String[]{"0.01", barcode,yishou,"yes"}); //test coding
        } else {
            InputDialogVM vm2 = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);    //全额扫码支付
            vm2.getThirdCodeData().postValue(new String[]{tvYingshou.getText().toString().trim(), barcode,yishou,"not"});//发版需使用这句，下面只是测试
         //   vm2.getThirdCodeData().postValue(new String[]{"0.01", barcode,yishou,"not"}); //test coding
        }
    }

    public static class Builder {
        private int withParams = ViewGroup.LayoutParams.MATCH_PARENT;
        private int heightParams = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int gravity = Gravity.CENTER;
        private int animations = R.style.dialog_animation_fade;
        private int leftMargin = 0;
        private int rightMargin = 0;

        public Builder setLeftMargin(int leftMargin) {
            this.leftMargin = leftMargin;
            return this;
        }

        public Builder setRightMargin(int rightMargin) {
            this.rightMargin = rightMargin;
            return this;
        }

        public Builder setWithParams(int withParams) {
            this.withParams = withParams;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setAnimations(int animations) {
            this.animations = animations;
            return this;
        }

        public Builder setHeightParams(int heightParams) {
            this.heightParams = heightParams;
            return this;
        }

        public PayMoneyDialog build() {
            PayMoneyDialog dialog = new PayMoneyDialog();
            dialog.setmBuilder(this);
            return dialog;
        }
    }
}

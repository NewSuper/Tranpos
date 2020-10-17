package com.transpos.market.ui.food;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.transpos.market.R;
import com.transpos.market.base.BaseFragment;
import com.transpos.market.base.mvp.BaseMvpActivity;
import com.transpos.market.entity.RegistrationCode;
import com.transpos.market.service.UploadService;
import com.transpos.market.ui.cash.CashFragment;
//import com.transpos.market.ui.data.DataFragment;
import com.transpos.market.ui.food.mvp.FoodContract;
import com.transpos.market.ui.food.mvp.FoodPrestener;
//import com.transpos.market.ui.merber.MerberFragment;
//import com.transpos.market.ui.order.OrderFragment;
//import com.transpos.market.ui.setting.SettingFragment;
//import com.transpos.market.ui.succession.SuccessionFragment;
//import com.transpos.market.ui.web.WebFragment;
import com.transpos.market.utils.KeyConstrant;
import com.transpos.market.utils.OSUtils;
import com.transpos.market.utils.ToolScanner;
import com.transpos.market.utils.summiPrint.SummiPrinterQueneService;
import com.transpos.market.utils.usbPrint.UsbPhoneService;
import com.transpos.market.view.CenterRadioButton;
import com.transpos.market.view.LoadingDialog;
import com.transpos.tools.tputils.TPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FoodActivity extends BaseMvpActivity<FoodPrestener> implements FoodContract.View,
        ToolScanner.OnScanSuccessListener {
    @BindView(R.id.tv_storeName)
    TextView tvStoreName;
    @BindView(R.id.ll_weiOrder)
    LinearLayout llWeiOrder;
    @BindView(R.id.ll_guaDan)
    LinearLayout llGuaDan;
    @BindView(R.id.iv_wifi)
    ImageView ivWifi;
    @BindView(R.id.iv_switch)
    ImageView ivSwitch;
    @BindView(R.id.iv_jian)
    ImageView ivJian;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.fragment_layout)
    FrameLayout fragmentLayout;
    @BindView(R.id.rb_cash)
    CenterRadioButton rbCash;
    @BindView(R.id.rb_merber)
    CenterRadioButton rbMerber;
    @BindView(R.id.rb_small_order)
    CenterRadioButton rbSmallOrder;
    @BindView(R.id.rb_succession)
    CenterRadioButton rbSuccession;
    @BindView(R.id.rb_data)
    CenterRadioButton rbData;
    @BindView(R.id.rb_set)
    CenterRadioButton rbSet;
    @BindView(R.id.rb_goweb)
    CenterRadioButton rbGoweb;
    @BindView(R.id.rg_button)
    RadioGroup rgButton;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private LoadingDialog mLoadingDialog;
    private boolean isInit;

    private static final int PAGE_CASH = R.id.rb_cash;
    private static final int PAGE_MERBER = R.id.rb_merber;
    private static final int PAGE_SMALL_ORDER = R.id.rb_small_order;
    private static final int PAGE_SUCCESSION = R.id.rb_succession;
    private static final int PAGE_DATA = R.id.rb_data;
    private static final int PAGE_SET = R.id.rb_set;
    private static final int PAGE_GOWEB = R.id.rb_goweb;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private BaseFragment mContent;

    @Override
    public void onScanSuccess(String barcode) {
        CashFragment fragment = (CashFragment) fragmentList.get(0);
      //  fragment.onScan(barcode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_food;
    }

    @Override
    protected ToolScanner getScanner() {
        return new ToolScanner(this);
    }

    @Override
    protected void initData() {
        drawerLayout.setScrimColor(Color.TRANSPARENT);//去除阴影
        fragmentList.add(new CashFragment());
//        fragmentList.add(new MerberFragment());
//        fragmentList.add(new OrderFragment());
//        fragmentList.add(new SuccessionFragment());
//        fragmentList.add(new DataFragment());
//        fragmentList.add(new SettingFragment());
//        fragmentList.add(new WebFragment());

        rgButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectPage(checkedId);
            }
        });

      //  initContent();
        rbCash.setChecked(true);

        Intent work = new Intent();
        work.putExtra(KeyConstrant.KEY_START_LOOP,true);
        UploadService.Companion.enqueueWork(this,work);

        RegistrationCode registrationCode = TPUtils.getObject(this, KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);
        tvStoreName.setText(String.format("%s-%s-%s", registrationCode.getTenantId(), registrationCode.getStoreNo(), registrationCode.getStoreName()));
        startService(new Intent(this, UsbPhoneService.class));
    }

    private void selectPage(int checkedId) {
        BaseFragment fragment = null;
        switch (checkedId) {
            case PAGE_CASH:
                fragment = fragmentList.get(0);
                break;
            case PAGE_MERBER:
                fragment = fragmentList.get(1);
                break;
            case PAGE_SMALL_ORDER:
                fragment = fragmentList.get(2);
                break;
            case PAGE_SUCCESSION:
                fragment = fragmentList.get(3);
                break;
            case PAGE_DATA:
                fragment = fragmentList.get(4);
                break;
            case PAGE_SET:
                fragment = fragmentList.get(5);
                break;
            case PAGE_GOWEB:
                fragment = fragmentList.get(6);
                break;
            default:
                fragment = fragmentList.get(0);
                break;
        }
        switchContent(fragment);
    }

    public void switchContent(BaseFragment to) {
        if (mContent != to) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (mContent == null){
                transaction.add(R.id.fragment_layout,to).commitAllowingStateLoss();
            }else {
                if (!to.isAdded()) { // 先判断是否被add过
                    transaction.hide(mContent).add(R.id.fragment_layout, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mContent).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }
            }
            mContent = to;
        }
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isInit) {
            isInit = true;
            presenter.startDownload();
        }
    }

    @Override
    protected boolean isSetSystemTransparent() {
        return false;
    }

    @Override
    protected FoodPrestener createPresenter() {
        return new FoodPrestener();
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, "下载中...");
            mLoadingDialog.setCancelable(false);
        }
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError(Object tag, String errorMsg) {

    }
    boolean is_open;
    @OnClick({R.id.ll_weiOrder, R.id.ll_guaDan, R.id.iv_switch, R.id.iv_jian, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_weiOrder:
                break;
            case R.id.ll_guaDan:
                break;
            case R.id.iv_switch:
                is_open = !is_open;
                if (is_open) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                break;
            case R.id.iv_jian:
                break;
            case R.id.iv_close:
                OSUtils.exitApp();
                break;
        }
    }

}

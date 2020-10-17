package com.transpos.sale.ui.navi;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transpos.market.R;
import com.transpos.sale.base.BaseActivity;
import com.transpos.sale.base.mvp.BaseMvpActivity;
import com.transpos.sale.ui.food.FoodActivity;
import com.transpos.sale.ui.navi.mvp.ScanCodeContract;
import com.transpos.sale.ui.navi.mvp.ScanCodePresenter;
import com.transpos.sale.utils.CountTimer;
import com.transpos.sale.utils.ToolScanner;
import com.transpos.sale.utils.UiUtils;
import com.transpos.sale.view.CustomGradient;

import butterknife.BindView;
import butterknife.OnClick;

public class VipScanCodeLoginActivity extends BaseMvpActivity<ScanCodePresenter> implements ToolScanner.OnScanSuccessListener, ScanCodeContract.View {

    private CountTimer mTimeCount;
    @BindView(R.id.tv_deal)
    TextView tv_deal;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navi_vip_scan_login;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_deal.setVisibility(View.VISIBLE);
        ll_back.setBackground(new CustomGradient(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Color.parseColor("#FFB200"),Color.parseColor("#FF7B00")},
                UiUtils.dp2px(4,this)));
    }

    @Override
    protected void initData() {
        super.initData();
        String reslut = getString(R.string.txt_actionbar_content);
        tv_deal.setText(String.format(reslut,"300"));
        mTimeCount = new CountTimer(300*1000L,1000L,this,tv_deal,false);
        mTimeCount.start();
    }

    @Override
    protected ToolScanner getScanner() {
        return new ToolScanner(this);
    }

    @OnClick({R.id.ll_back,R.id.tv_deal})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.ll_back:
            case R.id.tv_deal:
                if(mTimeCount != null){
                    mTimeCount.cancel();
                    mTimeCount = null;
                }
                startActivity(FoodActivity.class);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mTimeCount != null){
            mTimeCount.cancel();
            mTimeCount = null;
        }
    }

    @Override
    public void onScanSuccess(String barcode) {
        presenter.queryMemberByCode(barcode);
    }

    @Override
    protected ScanCodePresenter createPresenter() {
        return new ScanCodePresenter();
    }

    @Override
    public void queryMemberSuccess() {
        finish();
    }
}

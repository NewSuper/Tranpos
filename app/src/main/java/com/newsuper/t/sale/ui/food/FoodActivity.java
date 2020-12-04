package com.newsuper.t.sale.ui.food;

import android.content.Intent;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.sale.base.mvp.BaseMvpActivity;
import com.newsuper.t.sale.service.UploadService;
import com.newsuper.t.sale.service.UsbPhoneService;
import com.newsuper.t.sale.ui.food.mvp.FoodContract;
import com.newsuper.t.sale.ui.food.mvp.FoodPrestener;
import com.newsuper.t.sale.ui.navi.VipSelectActivity;
import com.newsuper.t.sale.ui.set.SettingActivity;
import com.newsuper.t.sale.utils.KeyConstrant;
import com.newsuper.t.sale.view.dialog.LoadingDialog;

import butterknife.OnClick;

public class FoodActivity extends BaseMvpActivity<FoodPrestener> implements FoodContract.View {

    private LoadingDialog mLoadingDialog;
    private boolean isInit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_food_home;
    }


    @Override
    protected void initData() {
        Intent work = new Intent();
        work.putExtra(KeyConstrant.KEY_START_LOOP,true);
        UploadService.Companion.enqueueWork(this,work);

        startService(new Intent(this, UsbPhoneService.class));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!isInit){
            isInit = true;
            presenter.startDownload();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshDb();
    }

    @Override
    protected FoodPrestener createPresenter() {
        return new FoodPrestener();
    }

    @Override
    public void showLoading() {
        if(mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(this,"下载中...");
            mLoadingDialog.setCancelable(false);
        }
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        if(mLoadingDialog != null){
            mLoadingDialog.dismiss();
        }
    }

    @OnClick({R.id.btn_start,R.id.btn_tips})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btn_start:
                startActivity(new Intent(this, VipSelectActivity.class));
                break;
            case R.id.btn_tips:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }

    @Override
    public void onError(Object tag, String errorMsg) {

    }
}

package com.newsuper.t.consumer.function.vip.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class ChargeOpenSuccessActivity extends BaseActivity {
    @BindView(R.id.tv_return_vip_center)
    TextView tvReturnVipCenter;
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.tv_commit_info)
    TextView tvCommitInfo;
    public static ChargeOpenSuccessActivity instance;

    @Override
    public void initData() {
        instance=this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_open_success_charge);
        ButterKnife.bind(this);

        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("开通成功");
        mToolbar.setMenuText("");
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });

    }

    @OnClick({R.id.tv_return_vip_center,R.id.tv_commit_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_return_vip_center:
                //回到会员中心
                startActivity(new Intent(this, VipMainActivity.class));
                this.finish();
                break;
            case R.id.tv_commit_info:
                //完善资料页面
                Intent intent=new Intent(this, EditVipInfoActivity.class);
                intent.putExtra("flag_from","no_vip");
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            this.finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

}

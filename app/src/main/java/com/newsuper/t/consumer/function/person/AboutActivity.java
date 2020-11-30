package com.newsuper.t.consumer.function.person;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.ib_logo)
    ImageView mIbLogo;
    @BindView(R.id.tv_version_info)
    TextView mTvVersionInfo;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_pnone_number)
    TextView mTvPnoneNumber;
    @BindView(R.id.rl_call_phone)
    RelativeLayout mRlCallPhone;
    private static final int CALL_PHONE_REQUEST_CODE = 9;

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("关于我们");
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
        mTvVersionInfo.setText(getHandSetInfo());
        if (!StringUtils.isEmpty(SharedPreferencesUtil.getHotOnline())){
            mTvPnoneNumber.setText(SharedPreferencesUtil.getHotOnline());
        }
    }

    private String getHandSetInfo() {
        return "当前版本V" + getAppVersionName();
    }

    //获得当前版本号
    private String getAppVersionName() {
        String versionName = "";
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }
    @OnClick(R.id.rl_call_phone)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_call_phone:
                String phoneNumber = mTvPnoneNumber.getText().toString().trim();
                phoneNumber = phoneNumber.replace("-", "");
                callPhone(phoneNumber);
                break;
        }
    }

    private void callPhone(String number) {
        if (TextUtils.isEmpty(number)) {
            UIUtils.showToast("当前号码为空");
            return;
        }
        Uri uri = Uri.parse("tel:" + number);
        Intent callPnoenIntent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(callPnoenIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (paramArrayOfInt[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                UIUtils.showToast("请允许使用电话权限！");
            }
        }
    }
}

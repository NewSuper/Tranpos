package com.newsuper.t.consumer.function.distribution;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.distribution.fragment.HelpBaseFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpBuyFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpCustomFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpLineUpFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpSendFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpTakeFragment;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpClassifyActivity extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    HelpBaseFragment baseFragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_classify);
        ButterKnife.bind(this);
        switch (getIntent().getStringExtra("type")){
            //帮买
            case "1":
                baseFragment = new HelpBuyFragment();
                break;
            //帮送
            case "2":
                baseFragment = new HelpSendFragment();
                break;
            //帮取
            case "3":
                baseFragment = new HelpTakeFragment();
                break;
            //帮排队
            case "4":
                baseFragment = new HelpLineUpFragment();
                break;
            //自定义
            case "5":
                baseFragment = new HelpCustomFragment();
                break;
        }
        String la = getIntent().getStringExtra("lat");
        la = StringUtils.isEmpty(la)? SharedPreferencesUtil.getLatitude():la;
        String ln = getIntent().getStringExtra("lng");
        ln = StringUtils.isEmpty(ln)? SharedPreferencesUtil.getLongitude():ln;
        String ad = getIntent().getStringExtra("address");
        String title = getIntent().getStringExtra("title");
        String type_id = getIntent().getStringExtra("type_id");
        Bundle bundle = new Bundle();
        bundle.putString("address",ad );
        bundle.putString("lat", la);
        bundle.putString("lng", ln);
        bundle.putString("type_id", type_id);
        bundle.putString("title",title);
        baseFragment.isCanback = true;
        baseFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,baseFragment).commitAllowingStateLoss();
       
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (baseFragment != null){
            baseFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        baseFragment.onBackPressed();
    }
}

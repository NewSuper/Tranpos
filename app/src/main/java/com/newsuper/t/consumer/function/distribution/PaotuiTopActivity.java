package com.newsuper.t.consumer.function.distribution;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.distribution.fragment.DistributionFragment;


public class PaotuiTopActivity extends BaseActivity {
    private DistributionFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paotui_top);
        //必需继承FragmentActivity,嵌套fragment只需要这行代码
        fragment = new DistributionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment).commitAllowingStateLoss();
    }
    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (fragment != null){
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

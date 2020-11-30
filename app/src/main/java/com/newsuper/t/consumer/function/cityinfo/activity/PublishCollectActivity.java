package com.newsuper.t.consumer.function.cityinfo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.function.cityinfo.fragment.PublishCollectFragment;

public class PublishCollectActivity extends BaseActivity {
    private PublishCollectFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_collect);
        fragment = new PublishCollectFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment).commitAllowingStateLoss();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}

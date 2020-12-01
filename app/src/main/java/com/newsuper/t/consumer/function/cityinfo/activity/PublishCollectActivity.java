package com.newsuper.t.consumer.function.cityinfo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.cityinfo.fragment.PublishCollectFragment;

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

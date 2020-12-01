package com.newsuper.t.consumer.function.top.avtivity;

import android.os.Bundle;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.top.fragment.FormFragment;

public class FormActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_web_view);
        FormFragment weiWebFragment = new FormFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",getIntent().getStringExtra("url"));
        bundle.putString("title",getIntent().getStringExtra("title"));
        weiWebFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,weiWebFragment).commitAllowingStateLoss();
    }
    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}

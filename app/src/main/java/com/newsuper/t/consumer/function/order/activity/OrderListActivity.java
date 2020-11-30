package com.newsuper.t.consumer.function.order.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.function.distribution.fragment.DistributionFragment;
import com.xunjoy.lewaimai.consumer.function.order.fragment.OrderFragment;

public class OrderListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        OrderFragment fragment = new OrderFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment).commitAllowingStateLoss();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}

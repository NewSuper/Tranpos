package com.newsuper.t.consumer.function.distribution;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.distribution.fragment.PaotuiOrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 跑腿订单
 */
public class PaotuiOrderActivity extends BaseActivity {
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    PaotuiOrderFragment baseFragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_classify);
        ButterKnife.bind(this);
        baseFragment = new PaotuiOrderFragment();
        String where_from = getIntent().getStringExtra("where_from");
        String order_id = getIntent().getStringExtra("order_id");
        Bundle bundle = new Bundle();
        bundle.putString("where_from",where_from);
        bundle.putString("order_id",order_id);
        baseFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,baseFragment).commitAllowingStateLoss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        baseFragment.onRequestPermissionsResult(requestCode,permissions,grantResults);
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

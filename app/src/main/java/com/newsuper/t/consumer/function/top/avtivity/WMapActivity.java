package com.newsuper.t.consumer.function.top.avtivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.function.top.fragment.MapFragment;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WMapActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private MapFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmap);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //必需继承FragmentActivity,嵌套fragment只需要这行代码
        fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lat",getIntent().getStringExtra("lat"));
        bundle.putString("lng",getIntent().getStringExtra("lng"));
        bundle.putString("des", getIntent().getStringExtra("dec"));

        fragment.setArguments(bundle);
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

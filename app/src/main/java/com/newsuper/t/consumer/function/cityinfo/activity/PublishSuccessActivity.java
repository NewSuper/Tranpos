package com.newsuper.t.consumer.function.cityinfo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.widget.CustomToolbar;
import butterknife.BindView;
import butterknife.ButterKnife;


public class PublishSuccessActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.tv_cat)
    TextView tvCat;

    private String title;
    private String info_id;


    @Override
    public void initData() {
          title=getIntent().getStringExtra("title");
          info_id=getIntent().getStringExtra("info_id");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_publish_success);
        ButterKnife.bind(this);
        toolbar.setMenuText("");
        toolbar.setTitleText(title);
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        tvReturn.setOnClickListener(this);
        tvCat.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return:
                this.finish();
                break;
            case R.id.tv_cat:
                //跳转到详情页面
                Intent intent=new Intent(this,PublishDetailActivity.class);
                intent.putExtra("info_id",info_id);
                startActivity(intent);
                break;
        }
    }



}

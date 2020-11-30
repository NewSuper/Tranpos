package com.newsuper.t.consumer.function.cityinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.function.cityinfo.adapter.PictureSelectAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureSelectActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.pic_gridview)
    GridView picGridview;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    private PictureSelectAdapter selectAdapter;
    private ArrayList<String> paths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_select);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        paths = new ArrayList<>();
        selectAdapter = new PictureSelectAdapter(this,paths);
        picGridview.setAdapter(selectAdapter);
        picGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    return;
                }
                String s = paths.get(position - 1);
                if (selectAdapter.getSelectCount() == 10){
                    if (selectAdapter.isSelected(s)){
                        selectAdapter.removeSelectPath(s);
                    }
                }else {
                    if (selectAdapter.isSelected(s)){
                        selectAdapter.removeSelectPath(s);
                    }else {
                        selectAdapter.saveSelectPath(s);
                    }
                }
                tvNum.setText(selectAdapter.getSelectCount()+"/10");
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_ok:
                break;
        }
    }
    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }


}

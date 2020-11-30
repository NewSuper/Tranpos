package com.newsuper.t.consumer.function.selectgoods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.CustomTabLayout;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeizhuActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_word)
    EditText edtWord;
    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.btn_write)
    Button btnWrite;
    @BindView(R.id.tab_layout)
    CustomTabLayout tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_beizhu);
        ButterKnife.bind(this);
        toolbar.setMenuText("");
        toolbar.setTitleText("备注");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {
            }
        });
        btnWrite.setOnClickListener(BeizhuActivity.this);
       /* edtWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               *//* if (StringUtils.isEmpty(s.toString())) {
                    btnWrite.setBackgroundResource(R.drawable.shape_btn_login);
                    btnWrite.setOnClickListener(null);
                } else {
                    btnWrite.setOnClickListener(BeizhuActivity.this);
                    btnWrite.setBackgroundResource(R.drawable.selector_btn_login);
                    tvWord.setText(s.length()+"/200");
                }*//*
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        ArrayList<String> beizhu = getIntent().getStringArrayListExtra("beizhu");
        tab_layout.setTabView(beizhu, new CustomTabLayout.TabClickListener() {
            @Override
            public void onClick(String s) {
                String value = edtWord.getText().toString();
                if (StringUtils.isEmpty(value)){
                    edtWord.setText(s);
                }else {
                    edtWord.setText(value+","+s);
                }
            }
        });

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_write:
                String s = edtWord.getText().toString();
                if (StringUtils.isEmpty(s)){
                    UIUtils.showToast("请输入备注");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("beizhu",s);
                setResult(RESULT_OK,intent);
                finish();
                break;

        }
    }
}

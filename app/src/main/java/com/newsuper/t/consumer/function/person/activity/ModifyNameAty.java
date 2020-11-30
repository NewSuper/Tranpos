package com.newsuper.t.consumer.function.person.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ModifyNameAty extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.edit_content)
    EditText mEditContent;
    @BindView(R.id.btn_clear)
    ImageView mBtnClear;
    @BindView(R.id.btn_save)
    Button mBtnSave;


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_modifyname);
        ButterKnife.bind(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("修改呢称");
        mToolbar.setMenuText("");
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        mEditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                int visible = TextUtils.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
                mBtnClear.setVisibility(visible);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick({R.id.btn_save, R.id.btn_clear})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                Intent i = new Intent();
                i.putExtra("modify_name", mEditContent.getText().toString().trim());
                setResult(RESULT_OK, i);
                finish();
                break;
            case R.id.btn_clear:
                mEditContent.setText("");
                break;
        }
    }
}

package com.newsuper.t.consumer.function.vip.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.login.internal.IBindPhoneView;
import com.newsuper.t.consumer.function.login.presenter.BindPhonePresenter;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class ChangePhoneActivity extends BaseActivity implements IBindPhoneView,View.OnClickListener{

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.ll_voice_code)
    LinearLayout llVoiceCode;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.iv_pass)
    ImageView ivPass;
    private boolean isTimeOut = true;
    private BindPhonePresenter bindPhonePresenter;
    private int second = 60;
    private boolean isPhoneNull = true;
    private boolean isPassNull = true;
    private LoadingDialog2 loadingDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (second >= 0) {
                        if (second == 60) {
                            tvGetCode.setTextColor(ContextCompat.getColor(ChangePhoneActivity.this, R.color.text_color_99));
                            tvGetCode.setBackgroundResource(R.drawable.shape_vip_gray);
                        }
                        tvGetCode.setText(second + "s");
                        second--;
                        handler.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        isTimeOut = true;
                        second = 60;
                        tvGetCode.setText("重新获取");
                        tvGetCode.setBackgroundResource(R.drawable.shape_vip_theme_red);
                        tvGetCode.setTextColor(ContextCompat.getColor(ChangePhoneActivity.this, R.color.text_color_67));
                    }
                    break;
            }
        }
    };

    @Override
    public void initData() {
        bindPhonePresenter = new BindPhonePresenter(this);
        loadingDialog = new LoadingDialog2(this);

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);

        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("修改手机号码");
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

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    ivPhone.setVisibility(View.INVISIBLE);
                    isPhoneNull = true;
                } else {
                    isPhoneNull = false;
                    ivPhone.setVisibility(View.VISIBLE);
                }
                if (isPhoneNull || isPassNull) {
                    tvCommit.setBackgroundResource(R.drawable.shape_vip_gray);
                    tvCommit.setOnClickListener(null);
                } else {
                    tvCommit.setOnClickListener(ChangePhoneActivity.this);
                    tvCommit.setBackgroundResource(R.drawable.selector_btn_change_phone);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    ivPass.setVisibility(View.INVISIBLE);
                    isPassNull = true;
                } else {
                    isPassNull = false;
                    ivPass.setVisibility(View.VISIBLE);
                }
                if (isPhoneNull || isPassNull) {
                    tvCommit.setBackgroundResource(R.drawable.shape_vip_gray);
                    tvCommit.setOnClickListener(null);
                } else {
                    tvCommit.setOnClickListener(ChangePhoneActivity.this);
                    tvCommit.setBackgroundResource(R.drawable.selector_btn_change_phone);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        String loginPhone=SharedPreferencesUtil.getLoginPhone();
        if(!TextUtils.isEmpty(loginPhone)){
            etPhone.setText(loginPhone);
        }
        tvGetCode.setOnClickListener(this);
        llVoiceCode.setOnClickListener(this);
    }

    //获取验证码类型 1：语音 2：短信
    private void sendCode(String type) {
        String phone = etPhone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            UIUtils.showToast("请输入手机号");
            return;
        }
        if (StringUtils.isMobile(phone) && phone.length() == 11) {
            if (isTimeOut) {
                isTimeOut = false;
                handler.sendEmptyMessage(1);
                bindPhonePresenter.getCode(phone, Const.ADMIN_ID, SharedPreferencesUtil.getToken(), type);
            }
        } else {
            UIUtils.showToast("请输入正确的手机号");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_get_code:
                sendCode("2");
                break;
            case R.id.tv_commit:
                //确定更改手机号
                String phone = etPhone.getText().toString().trim();
                if (StringUtils.isEmpty(phone)) {
                    ToastUtil.showTosat(ChangePhoneActivity.this, "请输入手机号");
                    return;
                }
                String code = etCode.getText().toString();
                if (StringUtils.isEmpty(code)) {
                    ToastUtil.showTosat(ChangePhoneActivity.this, "请输入验证码");
                    return;
                }
                bindPhonePresenter.modifyVipPhone(Const.ADMIN_ID, SharedPreferencesUtil.getToken(), phone, code);
                loadingDialog.show();
                break;
            case R.id.ll_voice_code:
                sendCode("1");
                break;
        }
    }

    @Override
    public void dialogDismiss() {
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void sendVerificationCode(String token) {
        ToastUtil.showTosat(this, "验证码已发送");
    }

    @Override
    public void sendFail() {
        ToastUtil.showTosat(this, "发送失败");
    }

    @Override
    public void bindSuccess() {
        Intent phoneIntent=new Intent();
        phoneIntent.putExtra("phone",etPhone.getText().toString().trim());
        setResult(RESULT_OK, phoneIntent);
        finish();
    }

    @Override
    public void bindFail(String s) {

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        if (loadingDialog != null){
            loadingDialog.dismiss();
        }
        super.onDestroy();
    }
}

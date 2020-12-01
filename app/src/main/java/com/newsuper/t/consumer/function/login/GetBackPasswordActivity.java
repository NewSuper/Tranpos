package com.newsuper.t.consumer.function.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.login.internal.IGetBackPassView;
import com.newsuper.t.consumer.function.login.presenter.GetBackPassPresenter;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetBackPasswordActivity extends BaseActivity implements View.OnClickListener,IGetBackPassView{

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.iv_pass)
    ImageView ivPass;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_yuyin)
    TextView tvYuyin;
    GetBackPassPresenter passPresenter;
    boolean isPhoneNull = true;
    boolean isPassNull = true;
    boolean isCodeNull = true;
    int second = 60;
    boolean isTimeOut = true;
    long timeLimit;
    String token;
    private LoadingDialog2 loadingDialog;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (second >= 0){
                        if (second == 60){
                            tvCode.setTextColor(ContextCompat.getColor(GetBackPasswordActivity.this, R.color.text_color_99));
                            tvCode.setBackgroundResource(R.drawable.shape_get_code_second);
                            tvCode.setOnClickListener(null);
                            tvYuyin.setTextColor(ContextCompat.getColor(GetBackPasswordActivity.this, R.color.text_color_99));
                            tvYuyin.setOnClickListener(null);
                        }
                        tvCode.setText(second+"s");
                        second -- ;
                        handler.sendEmptyMessageDelayed(1,1000);
                    }else {
                        isTimeOut = true;
                        second = 60;
                        tvCode.setText("重新获取");
                        tvCode.setBackgroundResource(R.drawable.shape_get_code);
                        tvCode.setTextColor(ContextCompat.getColor(GetBackPasswordActivity.this, R.color.text_color_67));
                        tvCode.setOnClickListener(GetBackPasswordActivity.this);
                        tvYuyin.setTextColor(ContextCompat.getColor(GetBackPasswordActivity.this, R.color.theme_red));
                        tvYuyin.setOnClickListener(GetBackPasswordActivity.this);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passPresenter = new GetBackPassPresenter(this);
        loadingDialog = new LoadingDialog2(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_get_back_password);
        ButterKnife.bind(this);
        toolbar.setMenuText("");
        toolbar.setTitleText("找回密码");
        toolbar.setMenuTextSize(14);
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {
                startActivity(new Intent(GetBackPasswordActivity.this,LoginByPasswordActivity.class));

            }
        });
        edtPhone.addTextChangedListener(new TextWatcher() {
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
                if (isPhoneNull  || isPassNull || isCodeNull){
                    btnCommit.setBackgroundResource(R.drawable.shape_btn_login);
                    btnCommit.setOnClickListener(null);
                }else {
                    btnCommit.setOnClickListener(GetBackPasswordActivity.this);
                    btnCommit.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPass.addTextChangedListener(new TextWatcher() {
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
                if (isPhoneNull  || isPassNull || isCodeNull){
                    btnCommit.setBackgroundResource(R.drawable.shape_btn_login);
                    btnCommit.setOnClickListener(null);
                }else {
                    btnCommit.setOnClickListener(GetBackPasswordActivity.this);
                    btnCommit.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    ivCode.setVisibility(View.INVISIBLE);
                    isCodeNull = true;
                } else {
                    isCodeNull = false;
                    ivCode.setVisibility(View.VISIBLE);
                }
                if (isPhoneNull  || isPassNull || isCodeNull){
                    btnCommit.setBackgroundResource(R.drawable.shape_btn_login);
                    btnCommit.setOnClickListener(null);
                }else {
                    btnCommit.setOnClickListener(GetBackPasswordActivity.this);
                    btnCommit.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvCode.setOnClickListener(this);
        tvYuyin.setOnClickListener(this);
        ivPass.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
        ivCode.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }
    @Override
    public void onClick(View v) {
        hideKeyBord();
        switch (v.getId()) {
            case R.id.iv_pass:
                edtPass.setText("");
                break;
            case R.id.iv_phone:
                edtPhone.setText("");
                break;
            case R.id.iv_code:
                edtCode.setText("");
                break;
            case R.id.tv_code:
                sendCode("2");
                break;
            case R.id.tv_yuyin:
                sendCode("1");
                break;
            case R.id.btn_commit:
                String p = edtPhone.getText().toString();
                String pass = edtPass.getText().toString();
                String code = edtCode.getText().toString();
                if (StringUtils.isEmpty(p)) {
                    ToastUtil.showTosat(GetBackPasswordActivity.this, "请输入手机号");
                    return;
                }
                if ( p.length() != 11){
                    ToastUtil.showTosat(GetBackPasswordActivity.this,"请输入正确的手机号");
                    return;
                }
                if (StringUtils.isEmpty(code)){
                    ToastUtil.showTosat(GetBackPasswordActivity.this,"请输入验证码");
                    return;
                }
                if (System.currentTimeMillis() - timeLimit > (5 * 60 * 1000)){
                    ToastUtil.showTosat(GetBackPasswordActivity.this,"验证码无效，请重新获取");
                    return;
                }
                if (StringUtils.isEmpty(pass)){
                    ToastUtil.showTosat(GetBackPasswordActivity.this,"请输入密码");
                    return;
                }
                loadingDialog.show();
                passPresenter.getBackPassword(p,pass,code,token, SharedPreferencesUtil.getAdminId());
                break;
        }
    }
    private void sendCode(String type){
        String phone = edtPhone.getText().toString();
        if (StringUtils.isEmpty(phone)){
            ToastUtil.showTosat(GetBackPasswordActivity.this,"请输入手机号");
            return;
        }
        if (StringUtils.isMobile(phone) && phone.length() == 11 ){
            passPresenter.getLoginCode(phone,type,SharedPreferencesUtil.getAdminId());
        }else {
            ToastUtil.showTosat(GetBackPasswordActivity.this,"请输入正确的手机号");
        }
    }

    @Override
    public void sendCodeFail() {
//        ToastUtil.showTosat(this,"发送失败");
    }

    @Override
    public void sendCodeSuccess(String token) {
        this.token = token;
        ToastUtil.showTosat(this,"验证码已发送");
        timeLimit = System.currentTimeMillis();
        handler.sendEmptyMessage(1);
    }

    @Override
    public void getBackSuccess() {
        Intent intent = new Intent();
        intent.putExtra("phone",edtPhone.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void getBackFail() {

    }

    @Override
    public void dialogDismiss() {
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }

    }

    @Override
    public void showToast(String s) {
        if (StringUtils.isEmpty(s)){
            ToastUtil.showTosat(this,"发送失败");
        }else {
            ToastUtil.showTosat(this,s);
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler.removeMessages(1);
        super.onDestroy();
    }
}

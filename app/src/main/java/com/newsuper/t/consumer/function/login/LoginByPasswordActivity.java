package com.newsuper.t.consumer.function.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.igexin.sdk.PushManager;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.CustomerInfoBean;
import com.xunjoy.lewaimai.consumer.function.login.internal.ILoginView;
import com.xunjoy.lewaimai.consumer.function.login.presenter.LoginPresenter;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginByPasswordActivity extends BaseActivity implements View.OnClickListener,ILoginView{
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.iv_pass)
    ImageView ivPass;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    private static final int BACK_REGISTER = 121;
    boolean isPhoneNull = true;
    boolean isPassNull = true;
    private LoginPresenter loginPresenter;
    private boolean isOpenEyes;
    private LoadingDialog2 loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter(this);
        loadingDialog = new LoadingDialog2(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_login_by_password);
        ButterKnife.bind(this);
        toolbar.setMenuText("注册");
        toolbar.setTitleText("密码登录");
        toolbar.setMenuTextSize(14);
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {
//                Intent intent = new Intent(LoginByPasswordActivity.this,RegisterActivity.class);
                Intent intent = new Intent(LoginByPasswordActivity.this,RegisterActivity2.class);
                startActivityForResult(intent,BACK_REGISTER);
            }
        });
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    isPhoneNull = true;
                } else {
                    isPhoneNull = false;
                }
                if (isPhoneNull  || isPassNull){
                    btnLogin.setBackgroundResource(R.drawable.shape_btn_login);
                    btnLogin.setOnClickListener(null);
                }else {
                    btnLogin.setOnClickListener(LoginByPasswordActivity.this);
                    btnLogin.setBackgroundResource(R.drawable.selector_btn_login);
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
                    isPassNull = true;
                } else {
                    isPassNull = false;
                }
                if (isPhoneNull  || isPassNull){
                    btnLogin.setBackgroundResource(R.drawable.shape_btn_login);
                    btnLogin.setOnClickListener(null);
                }else {
                    btnLogin.setOnClickListener(LoginByPasswordActivity.this);
                    btnLogin.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivPass.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
        tvForget.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }
    @Override
    public void onClick(View v) {
        hideKeyBord();
        switch (v.getId()) {
            case R.id.btn_login:
                String phone = edtPhone.getText().toString();
                String pass = edtPass.getText().toString();
                if (StringUtils.isEmpty(phone)){
                    ToastUtil.showTosat(LoginByPasswordActivity.this,"请输入手机号码");
                    return;
                }
                if ( phone.length() != 11 ){
                    ToastUtil.showTosat(LoginByPasswordActivity.this,"请输入正确的手机号码");
                    return;
                }
                if (StringUtils.isEmpty(pass)){
                    ToastUtil.showTosat(LoginByPasswordActivity.this,"请输入密码");
                    return;
                }
                loadingDialog.show();
                loginPresenter.loginByPassword(phone,pass,SharedPreferencesUtil.getAdminId());
                break;
            case R.id.iv_pass:
                if (isOpenEyes){
                    isOpenEyes = false;
                    ivPass.setImageResource(R.mipmap.login_icon_eyes_close2x);
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else {
                    isOpenEyes = true;
                    ivPass.setImageResource(R.mipmap.login_icon_eyes_open2x);
                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.iv_phone:
                edtPhone.setText("");
                break;
            case R.id.tv_forget:
                Intent intent = new Intent(LoginByPasswordActivity.this,GetBackPasswordActivity.class);
                startActivityForResult(intent,BACK_REGISTER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == BACK_REGISTER){
                String type = data.getStringExtra("select");
                if ("login".equals(type)){
                    String phone = data.getStringExtra("phone");
                    edtPhone.setText(phone);
                    edtPass.setText("");
                }else  if ("finish".equals(type)){
                    finish();
                }

            }
        }
    }

    @Override
    public void loginSuccess(String token,String bind) {
        if (!StringUtils.isEmpty(token)){
            PushManager.getInstance().bindAlias(this, SharedPreferencesUtil.getUserId());
            SharedPreferencesUtil.saveToken(token);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setResult(RESULT_OK);
                    finish();
                }
            },1000);
        }else {
            ToastUtil.showTosat(this,"登录失败");
        }

    }

    @Override
    public void sendVerificationCode(String token) {
        ToastUtil.showTosat(this,"验证码已发送");
    }

    @Override
    public void sendFail() {
        ToastUtil.showTosat(this,"发送失败");
    }

    @Override
    public void loginFail() {
        ToastUtil.showTosat(this,"登录失败");
    }

    @Override
    public void getUserInfo(CustomerInfoBean bean) {

    }

    @Override
    public void getUserFail() {

    }

    @Override
    public void dialogDismiss() {
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }
}

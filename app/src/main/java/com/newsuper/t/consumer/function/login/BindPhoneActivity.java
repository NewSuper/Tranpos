package com.newsuper.t.consumer.function.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.function.login.internal.IBindPhoneView;
import com.xunjoy.lewaimai.consumer.function.login.presenter.BindPhonePresenter;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog2;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* 绑定手机号
* */
public class BindPhoneActivity extends BaseActivity implements View.OnClickListener,IBindPhoneView{

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.iv_pass)
    ImageView ivPass;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_yuyin)
    TextView tvYuyin;
    private boolean isPhoneNull = true;
    private boolean isPassNull = true;
    private int second = 60;
    private boolean isTimeOut = true;
    private long timeLimit;
    private String TAG = "BindPhoneActivity";
    private String token;
    private BindPhonePresenter bindPhonePresenter;
    private LoadingDialog2 loadingDialog;
    private String from_type;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (second >= 0){
                        if (second == 60){
                            tvCode.setTextColor(ContextCompat.getColor(BindPhoneActivity.this, R.color.text_color_99));
                            tvCode.setBackgroundResource(R.drawable.shape_get_code_second);
                            tvCode.setOnClickListener(null);
                            tvYuyin.setTextColor(ContextCompat.getColor(BindPhoneActivity.this, R.color.text_color_99));
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
                        tvCode.setTextColor(ContextCompat.getColor(BindPhoneActivity.this, R.color.text_color_67));
                        tvCode.setOnClickListener(BindPhoneActivity.this);
                        tvYuyin.setTextColor(ContextCompat.getColor(BindPhoneActivity.this, R.color.theme_red));
                        tvYuyin.setOnClickListener(BindPhoneActivity.this);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void onResume() {
        super.onResume();
       
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void initData() {
        bindPhonePresenter = new BindPhonePresenter(this);
        loadingDialog = new LoadingDialog2(this);
        from_type = getIntent().getStringExtra("from_type");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        toolbar.setMenuText("");
        toolbar.setTitleText("绑定手机");
        toolbar.setMenuTextSize(14);
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {
               

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
                if (isPhoneNull  || isPassNull){
                    btnLogin.setBackgroundResource(R.drawable.shape_btn_login);
                    btnLogin.setOnClickListener(null);
                }else {
                    btnLogin.setOnClickListener(BindPhoneActivity.this);
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
                    ivPass.setVisibility(View.INVISIBLE);
                    isPassNull = true;
                } else {
                    isPassNull = false;
                    ivPass.setVisibility(View.VISIBLE);
                }
                if (isPhoneNull  || isPassNull){
                    btnLogin.setBackgroundResource(R.drawable.shape_btn_login);
                    btnLogin.setOnClickListener(null);
                }else {
                    btnLogin.setOnClickListener(BindPhoneActivity.this);
                    btnLogin.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvCode.setOnClickListener(this);
        ivPass.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
        tvYuyin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyBord();
        switch (v.getId()) {
            case R.id.btn_login:
                String phone1 = edtPhone.getText().toString();
                if (StringUtils.isEmpty(phone1)){
                    ToastUtil.showTosat(BindPhoneActivity.this,"请输入手机号");
                    return;
                }
                if (phone1.length() != 11){
                    ToastUtil.showTosat(BindPhoneActivity.this,"请输入正确的手机号");
                    return;
                }
                String code = edtPass.getText().toString();
                if (StringUtils.isEmpty(code)){
                    ToastUtil.showTosat(BindPhoneActivity.this,"请输入验证码");
                    return;
                }
                String app_id = "";
                if (from_type.equals("4")){
                    app_id = Const.WEIXIN_APP_ID;
                }else if (from_type.equals("5")){
                    app_id = Const.QQ_APP_ID;
                }
                bindPhonePresenter.bindPhone(SharedPreferencesUtil.getAdminId(),phone1,code,from_type,app_id,SharedPreferencesUtil.getToken());
                showLoadingDialog();
                break;
            case R.id.tv_code:
                sendCode("2");
                break;
            case R.id.tv_yuyin:
                sendCode("1");
                break;
            case R.id.iv_pass:
                edtPass.setText("");
                break;
            case R.id.iv_phone:
                edtPhone.setText("");
                break;
        }
    }
    //获取验证码类型 1：语音 2：短信
    private void sendCode(String type){
        String phone = edtPhone.getText().toString();
        if (StringUtils.isEmpty(phone)){
            ToastUtil.showTosat(BindPhoneActivity.this,"请输入手机号");
            return;
        }
        if (StringUtils.isMobile(phone) && phone.length() == 11 ){
            if (isTimeOut){
                bindPhonePresenter.getCode(phone,SharedPreferencesUtil.getAdminId(),SharedPreferencesUtil.getToken(),type);
            }
        }else {
            ToastUtil.showTosat(BindPhoneActivity.this,"请输入正确的手机号");
        }
    }

    private void showLoadingDialog(){
        loadingDialog.show();
    }



    @Override
    public void sendVerificationCode(String token) {
        this.token = token;
        isTimeOut = false;
        handler.sendEmptyMessage(1);
        ToastUtil.showTosat(this,"验证码已发送");
        timeLimit = System.currentTimeMillis();
    }

    @Override
    public void sendFail() {
//        ToastUtil.showTosat(this,"发送失败");
    }

    @Override
    public void bindSuccess() {
        setResult(RESULT_OK,new Intent());
        finish();
    }

    @Override
    public void bindFail(String s) {

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
        if (loadingDialog != null){
            loadingDialog.dismiss();
        }
        super.onDestroy();
    }
}

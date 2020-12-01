package com.newsuper.t.consumer.function.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.login.internal.IRegisterView;
import com.newsuper.t.consumer.function.login.presenter.RegisterPresenter;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, IRegisterView {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_input)
    EditText edtInput;
    @BindView(R.id.iv_pass)
    ImageView ivPass;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_yuyin)
    TextView tvYuyin;
    @BindView(R.id.tv_line1)
    View tvLine1;
    @BindView(R.id.tv_line2)
    View tvLine2;
    @BindView(R.id.tv_step1)
    ImageView tvStep1;
    @BindView(R.id.tv_step2)
    ImageView tvStep2;
    @BindView(R.id.tv_step3)
    ImageView tvStep3;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    private static final int MODE_PHONE = 12;
    private static final int MODE_CODE = 13;
    private static final int MODE_PASS = 14;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.iv_register)
    ImageView ivRegister;
    private int mode = MODE_PHONE;
    private String phone, code, pass;
    private RegisterPresenter presenter;
    private String token;
    private int second = 60;
    private boolean isTimeOut = true;
    private long timeLimit;
    private LoadingDialog2 loadingDialog;
    private String admin_id = SharedPreferencesUtil.getAdminId();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mode == MODE_CODE) {
                        if (second >= 0) {
                            if (second == 60) {
                                tvGetCode.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_color_99));
                                tvGetCode.setBackgroundResource(R.drawable.shape_get_code_second);
                            }
                            tvGetCode.setText(second + "s");
                            second--;
                            handler.sendEmptyMessageDelayed(1, 1000);
                        } else {
                            isTimeOut = true;
                            second = 60;
                            tvGetCode.setText("重新获取");
                            tvGetCode.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_color_67));
                            tvGetCode.setBackgroundResource(R.drawable.shape_get_code);

                        }
                        break;
                    }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterPresenter(this);
        loadingDialog = new LoadingDialog2(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        toolbar.setMenuText("");
        toolbar.setTitleText("注册");
        toolbar.setMenuTextSize(14);
        toolbar.setImageViewMenuIcon(R.mipmap.login_icon_close2x);
        toolbar.setImageViewMenuSize(30, 30);
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }

            @Override
            public void onMenuClick() {
                Intent intent = new Intent();
                intent.putExtra("select", "finish");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    ivPass.setVisibility(View.INVISIBLE);
                    btnNext.setBackgroundResource(R.drawable.shape_btn_login);
                    btnNext.setOnClickListener(null);
                } else {
                    ivPass.setVisibility(View.VISIBLE);
                    btnNext.setOnClickListener(RegisterActivity.this);
                    btnNext.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvAgreement.setOnClickListener(this);
        tvYuyin.setOnClickListener(this);
        ivPass.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        hideKeyBord();
        switch (v.getId()) {
            case R.id.btn_next:
                switch (mode) {
                    case MODE_PHONE:
                        phone = edtInput.getText().toString();
                        if (StringUtils.isEmpty(phone)) {
                            ToastUtil.showTosat(RegisterActivity.this, "请输入手机号");
                            return;
                        }
                        if (!StringUtils.isMobile(phone) || phone.length() != 11) {
                            ToastUtil.showTosat(RegisterActivity.this, "请输入正确的手机号");
                            return;
                        }
                        presenter.getCode(phone, "2", admin_id);
                        break;
                    case MODE_CODE:
                        code = edtInput.getText().toString();
                        if (StringUtils.isEmpty(code)) {
                            ToastUtil.showTosat(RegisterActivity.this, "请输入验证码");
                            return;
                        }
                        if (System.currentTimeMillis() - timeLimit > (5 * 60 * 1000)) {
                            ToastUtil.showTosat(RegisterActivity.this, "验证码失效，重新获取");
                            return;
                        }
                        mode = MODE_PASS;
                        ivRegister.setImageResource(R.mipmap.login_icon_password2x);
                        edtInput.setHint("请输入6~20个字符");
                        edtInput.setText("");
                        edtInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        btnNext.setText("注 册");
                        tvYuyin.setVisibility(View.INVISIBLE);
                        tvGetCode.setVisibility(View.GONE);
                        tvStep3.setImageResource(R.mipmap.login_icon_circle_s2x);
                        tvPass.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.theme_red));
                        tvLine2.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.theme_red));
                        break;
                    case MODE_PASS:
                        pass = edtInput.getText().toString();
                        if (StringUtils.isEmpty(pass)) {
                            ToastUtil.showTosat(RegisterActivity.this, "请输入密码");
                            return;
                        }
                        if (pass.length() < 6 || pass.length() > 20) {
                            ToastUtil.showTosat(RegisterActivity.this, "请输入6~20位密码");
                            return;
                        }
                        if (System.currentTimeMillis() - timeLimit > (5 * 60 * 1000)) {
                            ToastUtil.showTosat(RegisterActivity.this, "验证码失效，重新获取");
                            return;
                        }
                        loadingDialog.show();
                        presenter.register(phone, pass, code, token, admin_id);
                        break;
                }
                break;
            case R.id.tv_yuyin:
                if (mode == MODE_PHONE){
                    phone = edtInput.getText().toString();
                }
                if (second == 60) {
                    if (StringUtils.isEmpty(phone)) {
                        ToastUtil.showTosat(RegisterActivity.this, "请输入手机号");
                        return;
                    }
                    if (!StringUtils.isMobile(phone) || phone.length() != 11) {
                        ToastUtil.showTosat(RegisterActivity.this, "请输入正确的手机号");
                        return;
                    }
                    presenter.getCode(phone, "1", admin_id);
                }
                break;
            case R.id.tv_get_code:
                if (mode == MODE_PHONE){
                    phone = edtInput.getText().toString();
                }
                if (second == 60) {
                    if (StringUtils.isEmpty(phone)) {
                        ToastUtil.showTosat(RegisterActivity.this, "请输入手机号");
                        return;
                    }
                    if (!StringUtils.isMobile(phone)) {
                        ToastUtil.showTosat(RegisterActivity.this, "请输入正确的手机号");
                        return;
                    }
                    presenter.getCode(phone, "2", admin_id);
                    handler.sendEmptyMessage(1);
                }
                break;
            case R.id.iv_pass:
                edtInput.setText("");
                break;
            case R.id.tv_agreement:
                startActivity(new Intent(RegisterActivity.this, AgreementActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        switch (mode) {
            case MODE_PHONE:
                super.onBackPressed();
                break;
            case MODE_CODE:
                code = "";
                mode = MODE_PHONE;
                ivRegister.setImageResource(R.mipmap.login_icon_username2x);
                edtInput.setHint("请输入手机号");
                edtInput.setText(phone);
                tvYuyin.setVisibility(View.INVISIBLE);
                btnNext.setText("获取验证码");
                tvGetCode.setText("获取验证码");
                tvGetCode.setVisibility(View.VISIBLE);
                tvGetCode.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_color_67));
                tvGetCode.setBackgroundResource(R.drawable.shape_get_code);

                tvCode.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_color_82));
                tvStep2.setImageResource(R.mipmap.login_icon_circle_n2x);
                tvLine1.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_color_82));
                break;
            case MODE_PASS:
                pass = "";
                mode = MODE_CODE;
                ivRegister.setImageResource(R.mipmap.login_icon_verification2x);
                edtInput.setHint("请输入验证码");
                edtInput.setText("");
                edtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                btnNext.setText("提交验证码");
                second = 60;
                isTimeOut = true;
                tvGetCode.setText("获取验证码");
                tvGetCode.setVisibility(View.VISIBLE);
                tvGetCode.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_color_67));
                tvGetCode.setBackgroundResource(R.drawable.shape_get_code);

                tvYuyin.setVisibility(View.VISIBLE);
                tvStep3.setImageResource(R.mipmap.login_icon_circle_n2x);
                tvPass.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_color_82));
                tvLine2.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_color_82));
                break;
        }
    }



    @Override
    public void sendCodeFail() {
        ToastUtil.showTosat(this, "发送失败");
    }

    @Override
    public void sendCodeSuccess(String token) {
        this.token = token;
        ToastUtil.showTosat(this, "验证码已发送");
        timeLimit = System.currentTimeMillis();
        handler.sendEmptyMessage(1);
        if (mode == MODE_PHONE) {
            mode = MODE_CODE;
            ivRegister.setImageResource(R.mipmap.login_icon_verification2x);
            edtInput.setHint("请输入验证码");
            edtInput.setText("");
            tvYuyin.setVisibility(View.VISIBLE);
            btnNext.setText("提交验证码");
            second = 60;
            tvGetCode.setVisibility(View.VISIBLE);
            tvCode.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.theme_red));
            tvStep2.setImageResource(R.mipmap.login_icon_circle_s2x);
            tvLine1.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.theme_red));
            handler.sendEmptyMessageDelayed(1, 500);
        }

    }

    @Override
    public void registerSuccess() {
        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        intent.putExtra("select", "login");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void registerFail() {
//        showRegisterFailDialog();
    }

    @Override
    public void dialogDismiss() {
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this, s);
    }
    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}

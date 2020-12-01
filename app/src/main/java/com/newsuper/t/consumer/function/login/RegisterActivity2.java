package com.newsuper.t.consumer.function.login;

import android.content.Intent;
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
import android.widget.LinearLayout;
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

public class RegisterActivity2 extends BaseActivity implements View.OnClickListener,IRegisterView {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_input1)
    EditText edtInput1;
    @BindView(R.id.iv_pass1)
    ImageView ivPass1;
    @BindView(R.id.tv_agreement1)
    TextView tvAgreement1;
    @BindView(R.id.btn_next1)
    Button btnNext1;
    @BindView(R.id.tv_yuyin1)
    TextView tvYuyin1;
    @BindView(R.id.ll_step_1)
    LinearLayout llStep1;
    @BindView(R.id.edt_input2)
    EditText edtInput2;
    @BindView(R.id.iv_pass2)
    ImageView ivPass2;
    @BindView(R.id.tv_get_code2)
    TextView tvGetCode2;
    @BindView(R.id.tv_agreement2)
    TextView tvAgreement2;
    @BindView(R.id.btn_next2)
    Button btnNext2;
    @BindView(R.id.tv_yuyin2)
    TextView tvYuyin2;
    @BindView(R.id.ll_step_2)
    LinearLayout llStep2;
    @BindView(R.id.edt_input3)
    EditText edtInput3;
    @BindView(R.id.iv_pass3)
    ImageView ivPass3;
    @BindView(R.id.tv_agreement3)
    TextView tvAgreement3;
    @BindView(R.id.btn_next3)
    Button btnNext3;
    @BindView(R.id.ll_step_3)
    LinearLayout llStep3;
    @BindView(R.id.tv_step1)
    ImageView tvStep1;
    @BindView(R.id.tv_line1)
    View tvLine1;
    @BindView(R.id.tv_step2)
    ImageView tvStep2;
    @BindView(R.id.tv_line2)
    View tvLine2;
    @BindView(R.id.tv_step3)
    ImageView tvStep3;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_pass)
    TextView tvPass;
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
                    if (second >= 0) {
                        if (second == 60) {
                            tvGetCode2.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.text_color_99));
                            tvGetCode2.setBackgroundResource(R.drawable.shape_get_code_second);
                            tvGetCode2.setOnClickListener(null);
                            tvYuyin1.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.text_color_99));
                            tvYuyin1.setOnClickListener(null);
                            tvYuyin2.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.text_color_99));
                            tvYuyin2.setOnClickListener(null);
                        }
                        tvGetCode2.setText(second + "s");
                        second--;
                        handler.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        isTimeOut = true;
                        second = 60;
                        tvGetCode2.setText("重新获取");
                        tvGetCode2.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.text_color_67));
                        tvGetCode2.setBackgroundResource(R.drawable.shape_get_code);
                        tvYuyin2.setOnClickListener(RegisterActivity2.this);
                        tvYuyin1.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                        tvYuyin1.setOnClickListener(RegisterActivity2.this);
                        tvYuyin2.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                        tvYuyin2.setOnClickListener(RegisterActivity2.this);
                    }
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        stepChange(1);
        presenter = new RegisterPresenter(this);
        loadingDialog = new LoadingDialog2(this);
        toolbar.setMenuText("");
        toolbar.setTitleText("注册");
        toolbar.setMenuTextSize(14);
        toolbar.setImageViewMenuIcon(R.mipmap.login_icon_close2x);
        toolbar.setImageViewMenuSize(15, 15);
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }

            @Override
            public void onMenuClick() {
                second = 60;
                Intent intent = new Intent();
                intent.putExtra("select", "finish");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        edtInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    btnNext1.setBackgroundResource(R.drawable.shape_btn_login);
                    btnNext1.setOnClickListener(null);
                } else {
                    btnNext1.setOnClickListener(RegisterActivity2.this);
                    btnNext1.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtInput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    btnNext2.setBackgroundResource(R.drawable.shape_btn_login);
                    btnNext2.setOnClickListener(null);
                } else {
                    btnNext2.setOnClickListener(RegisterActivity2.this);
                    btnNext2.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtInput3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    btnNext3.setBackgroundResource(R.drawable.shape_btn_login);
                    btnNext3.setOnClickListener(null);
                } else {
                    btnNext3.setOnClickListener(RegisterActivity2.this);
                    btnNext3.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnNext1.setOnClickListener(this);
        btnNext2.setOnClickListener(this);
        btnNext3.setOnClickListener(this);
        tvYuyin1.setOnClickListener(this);
        tvYuyin2.setOnClickListener(this);
        tvAgreement1.setOnClickListener(this);
        tvAgreement2.setOnClickListener(this);
        tvAgreement3.setOnClickListener(this);
        tvGetCode2.setOnClickListener(this);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {
        hideKeyBord();
        switch (v.getId()) {
            case R.id.btn_next1:
            case R.id.tv_get_code2:
              String  phone = edtInput1.getText().toString();
                if (StringUtils.isEmpty(phone)) {
                    ToastUtil.showTosat(RegisterActivity2.this, "请输入手机号");
                    return;
                }
                if ( phone.length() != 11) {
                    ToastUtil.showTosat(RegisterActivity2.this, "请输入正确的手机号");
                    return;
                }
                presenter.getCode(phone, "2", admin_id);
                break;

            case R.id.btn_next2:
                String code = edtInput2.getText().toString();
                if (!StringUtils.isEmpty(code)){
                    llStep1.setVisibility(View.GONE);
                    llStep2.setVisibility(View.GONE);
                    llStep3.setVisibility(View.VISIBLE);
                    stepChange(3);
                }else {
                    if (StringUtils.isEmpty(code)) {
                        ToastUtil.showTosat(RegisterActivity2.this, "请输入验证码");
                        return;
                    }
                    if (System.currentTimeMillis() - timeLimit > (5 * 60 * 1000)) {
                        ToastUtil.showTosat(RegisterActivity2.this, "验证码失效，重新获取");
                        return;
                    }
                }
                break;
            case R.id.btn_next3:
               String pass = edtInput3.getText().toString();
                if (StringUtils.isEmpty(pass)) {
                    ToastUtil.showTosat(RegisterActivity2.this, "请输入密码");
                    return;
                }
                if (pass.length() < 6 || pass.length() > 20) {
                    ToastUtil.showTosat(RegisterActivity2.this, "请输入6~20位密码");
                    return;
                }
                if (System.currentTimeMillis() - timeLimit > (5 * 60 * 1000)) {
                    ToastUtil.showTosat(RegisterActivity2.this, "验证码失效，重新获取");
                    return;
                }
                loadingDialog.show();
                String code1 = edtInput2.getText().toString();
                String phone1 = edtInput1.getText().toString();
                presenter.register(phone1, pass, code1, token, admin_id);
                break;

            case R.id.tv_yuyin1:
                second = 60;
            case R.id.tv_yuyin2:
                String phone2 = edtInput1.getText().toString();
                if (second == 60) {
                    if (StringUtils.isEmpty(phone2)) {
                        ToastUtil.showTosat(RegisterActivity2.this, "请输入手机号");
                        return;
                    }
                    if (!StringUtils.isMobile(phone2) || phone2.length() != 11) {
                        ToastUtil.showTosat(RegisterActivity2.this, "请输入正确的手机号");
                        return;
                    }
                    presenter.getCode(phone2, "1", admin_id);
                }
                break;
            case R.id.iv_pass1:
                edtInput1.setText("");
                break;
            case R.id.iv_pass2:
                edtInput2.setText("");
                break;
            case R.id.iv_pass3:
                edtInput3.setText("");
                break;

            case R.id.tv_agreement1:
            case R.id.tv_agreement2:
            case R.id.tv_agreement3:
                startActivity(new Intent(RegisterActivity2.this, AgreementActivity.class));
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
        if (StringUtils.isEmpty(s)){
            ToastUtil.showTosat(this,"发送失败");
        }else {
            ToastUtil.showTosat(this,s);
        }
    }

    @Override
    public void sendCodeFail() {

    }

    @Override
    public void sendCodeSuccess(String token) {
        this.token = token;
        ToastUtil.showTosat(this, "验证码已发送");
        timeLimit = System.currentTimeMillis();
        stepChange(2);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void registerSuccess() {
        Intent intent = new Intent();
        intent.putExtra("phone", edtInput1.getText().toString());
        intent.putExtra("select", "login");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void registerFail() {

    } protected void onDestroy() {
        handler.removeMessages(1);
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
    private void stepChange(int step){
        tvStep1.setImageResource(R.mipmap.login_icon_circle_n2x);
        tvStep2.setImageResource(R.mipmap.login_icon_circle_n2x);
        tvStep3.setImageResource(R.mipmap.login_icon_circle_n2x);
        tvPhone.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.text_color_82));
        tvCode.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.text_color_82));
        tvPass.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.text_color_82));
        tvLine1.setBackgroundColor(ContextCompat.getColor(RegisterActivity2.this, R.color.text_color_82));
        tvLine2.setBackgroundColor(ContextCompat.getColor(RegisterActivity2.this, R.color.text_color_82));
        switch (step){
            case 1:
                second = -1;
                llStep1.setVisibility(View.VISIBLE);
                llStep2.setVisibility(View.GONE);
                llStep3.setVisibility(View.GONE);
                tvPhone.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                tvStep1.setImageResource(R.mipmap.login_icon_circle_s2x);
                break;
            case 2:
                second = 60;
                llStep1.setVisibility(View.GONE);
                llStep2.setVisibility(View.VISIBLE);
                llStep3.setVisibility(View.GONE);
                tvPhone.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                tvStep1.setImageResource(R.mipmap.login_icon_circle_s2x);
                tvCode.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                tvStep2.setImageResource(R.mipmap.login_icon_circle_s2x);
                tvLine1.setBackgroundColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                break;
            case 3:
                second = -1;
                llStep1.setVisibility(View.GONE);
                llStep2.setVisibility(View.GONE);
                llStep3.setVisibility(View.VISIBLE);
                tvPhone.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                tvStep1.setImageResource(R.mipmap.login_icon_circle_s2x);
                tvCode.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                tvStep2.setImageResource(R.mipmap.login_icon_circle_s2x);
                tvLine1.setBackgroundColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                tvPass.setTextColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                tvStep3.setImageResource(R.mipmap.login_icon_circle_s2x);
                tvLine2.setBackgroundColor(ContextCompat.getColor(RegisterActivity2.this, R.color.theme_red));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (llStep1.getVisibility() == View.VISIBLE){
            super.onBackPressed();
        }else  if (llStep2.getVisibility() == View.VISIBLE){
            stepChange(1);
        }else  if (llStep3.getVisibility() == View.VISIBLE){
            stepChange(2);
        }

    }
}

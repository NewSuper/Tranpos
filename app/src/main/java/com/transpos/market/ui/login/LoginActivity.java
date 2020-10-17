package com.transpos.market.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.transpos.market.ui.food.FoodActivity;
import com.transpos.market.R;
import com.transpos.market.base.mvp.BaseMvpActivity;
import com.transpos.market.entity.Worker;
import com.transpos.market.ui.login.mvp.LoginContract;
import com.transpos.market.ui.login.mvp.LoginPresenter;
import com.transpos.market.utils.KeyConstrant;
import com.transpos.market.utils.KeyboardUtils;
import com.transpos.market.utils.UiUtils;
import com.transpos.market.view.LoadingDialog;
import com.transpos.market.view.RegisterKeyboardView;
import com.transpos.tools.tputils.TPUtils;

import butterknife.BindView;


public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements RegisterKeyboardView.OnInputContentListener, LoginContract.View {

    @BindView(R.id.et_account)
    EditText et_userName;
    @BindView(R.id.et_password)
    EditText et_pwd;
    @BindView(R.id.input_keyboard)
    RegisterKeyboardView mKeyboardView;
    private LoadingDialog mLoadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_market;
    }

    @Override
    protected void initView() {
        KeyboardUtils.disableShowInput(et_userName);
        KeyboardUtils.disableShowInput(et_pwd);
        et_userName.setInputType(InputType.TYPE_NULL);
       // et_pwd.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        mKeyboardView.setmOnInputContentListener(this);
        et_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mKeyboardView.setContent(et_userName.getText().toString());
                }
            }
        });
        et_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mKeyboardView.setContent(et_pwd.getText().toString());
                }
            }
        });
        et_userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et_userName.setSelection(et_userName.length());
            }
        });
        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et_pwd.setSelection(et_pwd.length());
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onChanged(String content) {
        if (et_pwd.hasFocus()) {
            et_pwd.setText(content);
            et_pwd.setSelection(content.length());
        } else {
            et_userName.setText(content);
            et_userName.setSelection(content.length());
        }
    }

    @Override
    public void onConfirm(String content) {
        String user_name = et_userName.getText().toString();
        String pwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(user_name)) {
            UiUtils.showToastShort("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            UiUtils.showToastShort("请输入密码");
            return;
        }
        presenter.startLogin(user_name, pwd);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this).setTitleText("正在登录...");
        }
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        UiUtils.showToastShort("登录失败");
    }

    @Override
    public void loginSuccess(Worker worker) {
        TPUtils.putObject(this, KeyConstrant.KEY_WORKER, worker);
       // UiUtils.showToastShort("登录成功");
        Intent intent = new Intent(this, FoodActivity.class);
        startActivity(intent);
    }

    @Override
    public void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}


package com.transpos.sale.ui.login;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.transpos.market.R;
import com.transpos.sale.base.BaseActivity;
import com.transpos.sale.base.BaseApp;
import com.transpos.sale.base.mvp.BaseMvpActivity;
import com.transpos.sale.entity.EntityResponse;
import com.transpos.sale.entity.RegistrationCode;
import com.transpos.sale.entity.Worker;
import com.transpos.sale.ui.food.FoodActivity;
import com.transpos.sale.ui.login.mvp.LoginContract;
import com.transpos.sale.ui.login.mvp.LoginPresenter;
import com.transpos.sale.utils.KeyConstrant;
import com.transpos.sale.utils.KeyboardUtils;
import com.transpos.sale.utils.UiUtils;
import com.transpos.sale.view.RegisterKeyboardView;
import com.transpos.sale.view.dialog.LoadingDialog;
import com.transpos.tools.tputils.TPUtils;

import java.io.File;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements RegisterKeyboardView.OnInputContentListener, LoginContract.View {

    @BindView(R.id.tv_storeName)
    TextView tv_storeName;
    @BindView(R.id.et_userName)
    EditText et_userName;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.input_keyboard)
    RegisterKeyboardView mKeyboardView;
    private LoadingDialog mLoadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        KeyboardUtils.disableShowInput(et_userName);
        KeyboardUtils.disableShowInput(et_pwd);
        mKeyboardView.setmOnInputContentListener(this);
        et_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mKeyboardView.setContent(et_userName.getText().toString());
                }
            }
        });
        et_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mKeyboardView.setContent(et_pwd.getText().toString());
                }
            }
        });
    }

    @Override
    protected void initData() {
        RegistrationCode registrationCode = TPUtils.getObject(this, KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);
        tv_storeName.setText(String.format("%s-%s-%s",registrationCode.getTenantId(), registrationCode.getStoreNo(), registrationCode.getStoreName()));
    }

    @Override
    public void onChanged(String content) {
        if(et_pwd.hasFocus()){
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
        if(TextUtils.isEmpty(user_name)){
            UiUtils.showToastShort("请输入用户名");
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            UiUtils.showToastShort("请输入密码");
            return;
        }
        presenter.startLogin(user_name,pwd);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showLoading() {
        if(mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(this).setTitleText("正在登录...");
        }
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        UiUtils.showToastShort(errorMsg);
    }

    @Override
    public void loginSuccess(Worker worker) {
        TPUtils.putObject(this,KeyConstrant.KEY_WORKER,worker);
        UiUtils.showToastShort("登录成功");
        Intent intent = new Intent(this, FoodActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void dismissLoading() {
        if(mLoadingDialog != null){
            mLoadingDialog.dismiss();
        }
    }
}

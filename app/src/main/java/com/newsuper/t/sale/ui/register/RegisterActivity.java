package com.newsuper.t.sale.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.sale.base.mvp.BaseMvpActivity;
import com.newsuper.t.sale.entity.OpenResponse;
import com.newsuper.t.sale.entity.RegistrationCode;
import com.newsuper.t.sale.ui.login.LoginActivity;
import com.newsuper.t.sale.ui.register.mvp.ReginsterPresenter;
import com.newsuper.t.sale.ui.register.mvp.RegisterContract;
import com.newsuper.t.sale.utils.KeyConstrant;
import com.newsuper.t.sale.utils.UiUtils;
import com.newsuper.t.sale.view.RegisterKeyboardView;
import com.transpos.tools.tputils.TPUtils;

import butterknife.BindView;

public class RegisterActivity extends BaseMvpActivity<ReginsterPresenter> implements RegisterKeyboardView.OnInputContentListener, RegisterContract.View {

    @BindView(R.id.input_keyboard)
    RegisterKeyboardView mRegisterKeyboardView;
    @BindView(R.id.et_input)
    TextView mInput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterKeyboardView.setmOnInputContentListener(this);
    }

    @Override
    protected ReginsterPresenter createPresenter() {
        return new ReginsterPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_sale;
    }

    @Override
    public void onChanged(String content) {
        mInput.setText(content.trim());
    }

    @Override
    public void onConfirm(String authCode) {
        if(TextUtils.isEmpty(mInput.getText().toString())){
            UiUtils.showToastLong("请输入注册码");
            return;
        }
        doRegister(authCode);
    }

    private void doRegister(String authCode) {
        presenter.register(authCode);
    }

    @Override
    public void registerSuccess(OpenResponse<RegistrationCode> result) {
        TPUtils.putObject(this, KeyConstrant.KEY_AUTH_REGISTER,result.getData());
        UiUtils.showToastShort("恭喜，注册成功，可以开始正常使用了");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

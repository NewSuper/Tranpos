package com.newsuper.t.consumer.function.top.avtivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.top.internal.IJoinView;
import com.newsuper.t.consumer.function.top.presenter.JoinPresenter;
import com.newsuper.t.consumer.function.top.request.JoinRequest;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by Administrator on 2019/6/24 0024
 */
public class JoinActivity extends BaseActivity implements IJoinView {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.et_join_name)
    EditText etJoinName;
    @BindView(R.id.et_join_phone)
    EditText etJoinPhone;
    @BindView(R.id.et_join_address)
    EditText etJoinAddress;
    @BindView(R.id.et_join_dec)
    EditText etJoinDec;
    @BindView(R.id.et_join_city)
    EditText etJoinCity;
    @BindView(R.id.tv_join)
    TextView tvJoin;

    String token;
    String adminId;
    JoinPresenter mPresenter;
    @Override
    public void initData() {
        adminId = SharedPreferencesUtil.getAdminId();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
        mPresenter = new JoinPresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("加盟合作");
        mToolbar.setMenuText("");
        mToolbar.setMenuTextColor(R.color.text_color_66);
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {
            }
        });
    }

    @Override
    public void requestSuccess() {
        UIUtils.showToast("加盟申请已提交");
        finish();
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_join)
    public void onViewClicked() {
        String name = etJoinName.getText().toString().trim();
        String phone = etJoinPhone.getText().toString().trim();
        String address = etJoinAddress.getText().toString().trim();
        String dec = etJoinDec.getText().toString().trim();
        String city = etJoinCity.getText().toString().trim();
        if (StringUtils.isEmpty(name)) {
            UIUtils.showToast("请输入联系人");
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            UIUtils.showToast("请输入手机号");
            return;
        }
        if (!phone.startsWith("1")) {
            UIUtils.showToast("手机号格式错误");
            return;
        }
        if (StringUtils.isEmpty(address)) {
            UIUtils.showToast("请输入联系地址");
            return;
        }
//        if (StringUtils.isEmpty(dec)) {
//            UIUtils.showToast("请输入备注");
//            return;
//        }
        if (StringUtils.isEmpty(city)) {
            UIUtils.showToast("请输入加盟城市");
            return;
        }
        HashMap<String,String> map = JoinRequest.sendJoin(adminId,name,phone,address,dec,city);
        mPresenter.sendJoinApplication(UrlConst.JOIN_APPLY,map);
    }
}

package com.newsuper.t.consumer.function.person.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.DelAddressBean;
import com.newsuper.t.consumer.function.login.LoginByPasswordActivity;
import com.newsuper.t.consumer.function.person.internal.IAddressView;
import com.newsuper.t.consumer.function.person.presenter.NewAddressPresenter;
import com.newsuper.t.consumer.function.person.request.DelAddressRequest;
import com.newsuper.t.consumer.function.person.request.NewddressRequest;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAddressActivity extends BaseActivity implements View.OnClickListener, IAddressView {
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.tv_customer_name)
    EditText mTvCustomerName;
    @BindView(R.id.tv_customer_name_tel)
    EditText mTvCustomerNameTel;
    @BindView(R.id.tv_goods_address)
    TextView mTvGoodsAddress;
    @BindView(R.id.tv_detail_address)
    EditText mTvDetailAddress;
    @BindView(R.id.btn_sure)
    Button mBtnSure;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.ll_choose)
    LinearLayout mLlChoose;

    private NewAddressPresenter mPresenter;
    private boolean edit;
    private AddressBean.AddressList list;
    public final static int PUT_ADDRESS = 1;
    private Dialog delDialog;
    private String token,admin_id;
    private String lat = "";
    private String lng = "";

    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
        admin_id = SharedPreferencesUtil.getAdminId();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_new_address);
        ButterKnife.bind(this);
        mPresenter = new NewAddressPresenter(this);
        edit = getIntent().getBooleanExtra("edit", false);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        if (edit) {
            mToolbar.setTitleText("编辑地址");
            mBtnCancel.setVisibility(View.VISIBLE);
            list = (AddressBean.AddressList) getIntent().getSerializableExtra("addresslist");
            if (list != null){
                mTvCustomerName.setText(list.name);
                mTvCustomerNameTel.setText(list.phone);
                mTvDetailAddress.setText(list.address);
                mTvGoodsAddress.setText(list.address_name);
                lat = list.lat;
                lng = list.lng;
            }
        } else {
            mToolbar.setTitleText("新建地址");
            mBtnCancel.setVisibility(View.GONE);
        }
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
    }

    //添加顾客地址
    private void add() {
        String address = mTvDetailAddress.getText().toString().trim();
        String address_name = mTvGoodsAddress.getText().toString().trim();
        String name = mTvCustomerName.getText().toString().trim();
        String phone = mTvCustomerNameTel.getText().toString().trim();
        if (StringUtils.isEmpty(phone)){
            ToastUtil.showTosat(NewAddressActivity.this,"请输入手机号码");
            return;
        }
        if (!StringUtils.isMobile(phone) || phone.length() != 11 ){
            ToastUtil.showTosat(NewAddressActivity.this,"请输入正确的手机号码");
            return;
        }
        if (StringUtils.isEmpty(address_name)){
            ToastUtil.showTosat(NewAddressActivity.this,"选择地址");
            return;
        }
        if (StringUtils.isEmpty(address) ){
            ToastUtil.showTosat(NewAddressActivity.this,"请输入详细地址");
            return;
        }
        HashMap<String, String> map = NewddressRequest.newaddreessRequest(token,admin_id,"", name,phone, address,address_name,lat,lng);
        mPresenter.addAddress(UrlConst.ADD_CUSTOMER_ADDRESS, map);
    }

    //修改顾客地址
    private void edit() {
        if (list != null){
            String id = list.id;
            String address = mTvDetailAddress.getText().toString().trim();
            String address_name = mTvGoodsAddress.getText().toString().trim();
            String name = mTvCustomerName.getText().toString().trim();
            String phone = mTvCustomerNameTel.getText().toString().trim();
            if (StringUtils.isEmpty(phone)){
                ToastUtil.showTosat(NewAddressActivity.this,"请输入手机号码");
                return;
            }
            if (!StringUtils.isMobile(phone) || phone.length() != 11 ){
                ToastUtil.showTosat(NewAddressActivity.this,"请输入正确的手机号码");
                return;
            }
            if (StringUtils.isEmpty(address_name)){
                ToastUtil.showTosat(NewAddressActivity.this,"选择地址");
                return;
            }
            if (StringUtils.isEmpty(address) ){
                ToastUtil.showTosat(NewAddressActivity.this,"请输入详细地址");
                return;
            }
            HashMap<String, String> map = NewddressRequest.newaddreessRequest(token,admin_id,id,name,phone, address,address_name,lat,lng);
            mPresenter.addAddress(UrlConst.ADD_CUSTOMER_ADDRESS, map);
        }

    }

    //删除顾客地址
    private void delAddress() {
        if (list != null){
            HashMap<String, String> map = DelAddressRequest.delAddressRequest(token,admin_id,list.id);
            mPresenter.delAddress(UrlConst.DEL_CUSTOMER_ADDRESS, map);
        }
    }

    @OnClick({R.id.btn_sure, R.id.btn_cancel, R.id.ll_choose})
    @Override
    public void onClick(View view) {
        hideKeyBord();
        switch (view.getId()) {
            case R.id.btn_sure:
                if (edit) {
                    edit();
                }else {
                    add();
                }
                break;
            case R.id.btn_cancel:
                showDelAdr();
                break;
            case R.id.ll_choose:
                Intent i = new Intent(this, ChooseAddressActivity.class);
                if (list != null && edit) {
                    i.putExtra("maplng", list.lng);
                    i.putExtra("maplat", list.lat);
                    i.putExtra("addressname", list.address_name);
                } else {
                    i.putExtra("maplng", SharedPreferencesUtil.getLongitude());
                    i.putExtra("maplat", SharedPreferencesUtil.getLatitude());
                    i.putExtra("addressname", "");
                }
                startActivityForResult(i, PUT_ADDRESS);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PUT_ADDRESS) {
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");
                String address = data.getStringExtra("address");
                if (!StringUtils.isEmpty(address)) {
                    mTvGoodsAddress.setText(address);
                }
            }
        }
    }

    //删除地址弹窗
    private void showDelAdr() {
        View dialog = UIUtils.inflate(R.layout.dialog_del_address);
        delDialog = DialogUtils.centerDialog(NewAddressActivity.this, dialog);
        Button btn_sure = (Button) dialog.findViewById(R.id.bt_confirm);
        Button btn_cancel2 = (Button) dialog.findViewById(R.id.bt_cancel2);
        btn_cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delDialog.cancel();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delAddress();
            }
        });
        delDialog.show();
    }

    @Override
    public void refresh(AddressBean bean) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void loadFail() {

    }

    @Override
    public void refresh2(DelAddressBean bean) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showDelDataView(DelAddressBean bean) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void dialogDismiss() {
        if (delDialog != null){
            delDialog.dismiss();
        }
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDataToVIew(AddressBean bean) {

    }

}

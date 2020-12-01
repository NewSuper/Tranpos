package com.newsuper.t.consumer.function.distribution;

import android.content.Intent;
import android.os.Bundle;
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
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.DelAddressBean;
import com.newsuper.t.consumer.function.person.internal.IAddressView;
import com.newsuper.t.consumer.function.person.presenter.NewAddressPresenter;
import com.newsuper.t.consumer.function.person.request.NewddressRequest;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaveAddressActivity extends BaseActivity implements View.OnClickListener,IAddressView {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.ll_save)
    LinearLayout llSave;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.iv_del)
    ImageView ivDel;
    @BindView(R.id.iv_contact)
    ImageView ivContact;
    static final int ADD_ADDRESS = 4343;
    String address,lat,lng;
    NewAddressPresenter mPresenter;
    boolean isSave = false;
    LoadingDialog2 loadingDialog;
    AddressBean.AddressList addressList;
    static final int SEARCH_PHOEN_CODE = 5455;
    static final int PERMISSION_CODE = 855;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_address);
        ButterKnife.bind(this);
        llSave.setOnClickListener(this);
        ivContact.setOnClickListener(this);
        ivDel.setOnClickListener(this);
        llAddress.setOnClickListener(this);

        toolbar.setMenuText("");
        toolbar.setTitleText("添加地址");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        tvAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(sequence.toString()) || StringUtils.isEmpty(edtPhone.getText().toString())) {
                    btnLogin.setBackgroundResource(R.drawable.shape_btn_login);
                    btnLogin.setOnClickListener(null);
                } else {
                    btnLogin.setOnClickListener(SaveAddressActivity.this);
                    btnLogin.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(sequence.toString())){
                    ivDel.setVisibility(View.INVISIBLE);
                }else {
                    ivDel.setVisibility(View.VISIBLE);
                }
                if (StringUtils.isEmpty(sequence.toString()) || StringUtils.isEmpty(tvAddress.getText().toString())) {
                    btnLogin.setBackgroundResource(R.drawable.shape_btn_login);
                    btnLogin.setOnClickListener(null);
                } else {
                    btnLogin.setOnClickListener(SaveAddressActivity.this);
                    btnLogin.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        address = getIntent().getStringExtra("address");
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        String address_detail = getIntent().getStringExtra("address_detail");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        edtPhone.setText(phone);
        edtAddress.setText(address_detail);
        edtName.setText(name);
        tvAddress.setText(address);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    //添加顾客地址
    private void add() {
        addressList = new AddressBean.AddressList();
        String address = edtAddress.getText().toString().trim();
        String address_name = tvAddress.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        if (StringUtils.isEmpty(address_name)){
            ToastUtil.showTosat(SaveAddressActivity.this,"选择地址");
            return;
        }
        if (StringUtils.isEmpty(address) ){
//            ToastUtil.showTosat(SaveAddressActivity.this,"请输入详细地址");
            address = "";
        }
        if (StringUtils.isEmpty(name) ){
//            ToastUtil.showTosat(SaveAddressActivity.this,"请输入详细地址");
            name = "";
        }

        if (StringUtils.isEmpty(phone)){
            ToastUtil.showTosat(SaveAddressActivity.this,"请输入手机号码");
            return;
        }
        phone = phone.replace(" ","");
        phone = phone.replace("-","");
        if (!StringUtils.isMobile(phone.trim()) ){
            ToastUtil.showTosat(SaveAddressActivity.this,"请输入正确的手机号码");
            return;
        }
        addressList.address = address;
        addressList.address_name = address_name;
        addressList.name = name;
        addressList.phone = phone;

        if (isSave){
            loadingDialog = new LoadingDialog2(this);
            loadingDialog.show();
            HashMap<String, String> map = NewddressRequest.newaddreessRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,"", name,phone, address,address_name,lat,lng);
            if (mPresenter == null){
                mPresenter = new NewAddressPresenter(this);
            }
            mPresenter.addAddress(UrlConst.ADD_CUSTOMER_ADDRESS, map);
        }else {
            Intent intent = new Intent();
            intent.putExtra("addressData",addressList);
            setResult(RESULT_OK,intent);
            finish();
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                add();
                break;
            case R.id.ll_address:
                Intent i = new Intent(SaveAddressActivity.this, NearAddressListActivity.class);
                i.putExtra("where_from", "1");
                startActivityForResult(i,ADD_ADDRESS);
                break;
            case R.id.ll_save:
                isSave = !isSave;
                if (isSave){
                    ivSave.setImageResource(R.mipmap.zhifu_xuanzhong_2x);
                }else {
                    ivSave.setImageResource(R.mipmap.icon_weixuanze_2x);
                }
                break;
            case R.id.iv_contact:
               /* if (Build.VERSION.SDK_INT >= 23) {
                    requirePermission();
                } else {
                    getContactsFormSystem();
                }*/
                break;
            case R.id.iv_del:
                edtPhone.setText("");
                ivDel.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void dialogDismiss() {
        loadingDialog.dismiss();
    }

    @Override
    public void showToast(String s) {
        loadingDialog.dismiss();
    }

    @Override
    public void showDataToVIew(AddressBean bean) {
        loadingDialog.dismiss();
        Intent intent = new Intent();
        intent.putExtra("addressData",addressList);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void refresh(AddressBean bean) {

    }

    @Override
    public void loadFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void showDelDataView(DelAddressBean bean) {

    }

    @Override
    public void refresh2(DelAddressBean bean) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ADDRESS && resultCode == RESULT_OK){
            tvAddress.setText(data.getStringExtra("address"));
            lat = data.getStringExtra("lat")+"";
            lng = data.getStringExtra("lng")+"";
        }

      /*  if (requestCode == SEARCH_PHOEN_CODE && resultCode == RESULT_OK && data != null) {
            //处理返回的data,获取选择的联系人信息
            Uri uri = data.getData();
            edtPhone.setText(getPhoneContacts(uri).trim());
        }*/
    }

   /* //调系统联系人
    private void getContactsFormSystem() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        startActivityForResult(intent, SEARCH_PHOEN_CODE);
    }

    //获取联系人号码
    private String getPhoneContacts(Uri uri) {
        String phoneNumber = "";
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            //取得联系人姓名
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
        }
        return phoneNumber.trim();
    }

    private void requirePermission() {
        PackageManager pkgManager = getPackageManager();
        boolean readContacts =
                pkgManager.checkPermission(Manifest.permission.READ_CONTACTS, this.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        boolean phoneStatus =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, this.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (readContacts && phoneStatus) {
            getContactsFormSystem();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_CODE);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getContactsFormSystem();
            }
        }
    }*/
}

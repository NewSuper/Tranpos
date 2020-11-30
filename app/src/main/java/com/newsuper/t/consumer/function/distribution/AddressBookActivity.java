package com.newsuper.t.consumer.function.distribution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.DelAddressBean;
import com.newsuper.t.consumer.function.distribution.adapter.AddressBookAdapter;
import com.newsuper.t.consumer.function.person.activity.MyAddressActivity;
import com.newsuper.t.consumer.function.person.activity.NewAddressActivity;
import com.newsuper.t.consumer.function.person.internal.IAddressView;
import com.newsuper.t.consumer.function.person.presenter.AddressPresenter;
import com.newsuper.t.consumer.function.person.request.AddressRequest;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressBookActivity extends BaseActivity implements IAddressView, View.OnClickListener{

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.ll_none)
    LinearLayout llNone;
    @BindView(R.id.ll_add_address)
    LinearLayout llAddAddress;
    AddressBookAdapter addressBookAdapter;
    ArrayList<AddressBean.AddressList> mAddressLists = new ArrayList<>();
    AddressPresenter mPresenter;
    static final int ADD_ADDRESS = 2552;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        ButterKnife.bind(this);
        toolbar.setBackImageViewVisibility(View.VISIBLE);
        toolbar.setTitleText("选择地址");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        llAddAddress.setOnClickListener(this);
        addressBookAdapter = new AddressBookAdapter(this,mAddressLists);
        listview.setAdapter(addressBookAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressBean.AddressList addressList = mAddressLists.get(position);
                Intent intent = new Intent();
                intent.putExtra("addressData",addressList);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        mPresenter = new AddressPresenter(this);
        load();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    private void load() {
        HashMap<String, String> map = AddressRequest.addreessRequest(SharedPreferencesUtil.getToken(),SharedPreferencesUtil.getAdminId());
        mPresenter.loadData(UrlConst.GET_ADDRESS_LIST, map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_address:
                Intent i = new Intent(AddressBookActivity.this, NearAddressListActivity.class);
                i.putExtra("where_from", "0");
                startActivityForResult(i,ADD_ADDRESS);
                break;
        }
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void showDataToVIew(AddressBean bean) {
        if (bean != null) {
            mAddressLists.clear();
            if (bean.data.addresslist != null && bean.data.addresslist.size() > 0) {
                mAddressLists.addAll(bean.data.addresslist);
                llNone.setVisibility(View.GONE);
            }else {
                loadFail();
            }
            addressBookAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refresh(AddressBean bean) {

    }

    @Override
    public void loadFail() {

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
            load();
        }
    }
}

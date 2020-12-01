package com.newsuper.t.consumer.function.selectgoods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.function.person.activity.NewAddressActivity;
import com.newsuper.t.consumer.function.selectgoods.adapter.AddressInRangeAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter.AddressOutRangeAdapter;
import com.newsuper.t.consumer.function.selectgoods.inter.ISelectAddressView;
import com.newsuper.t.consumer.function.selectgoods.presenter.SelectAddressPresenter;
import com.newsuper.t.consumer.manager.AddressManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.ListViewForScrollView;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//选收货地址
public class SelectAddressActivity extends BaseActivity implements View.OnClickListener, ISelectAddressView {
    public static final int BACK_CODE_UPDATE_ADDRESS = 12;
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.listview_in_range)
    ListViewForScrollView listviewInRange;
    @BindView(R.id.listview_out_range)
    ListViewForScrollView listviewOutRange;
    @BindView(R.id.ll_add_new_address)
    LinearLayout llAddNewAddress;
    @BindView(R.id.tv_out_range)
    TextView tvOutRange;
    @BindView(R.id.btn_load_again)
    Button btnLoadAgain;
    @BindView(R.id.ll_load_fail)
    LinearLayout llLoadFail;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.ll_no)
    LinearLayout llNo;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.activity_select_address)
    LinearLayout activitySelectAddress;
    private String token, shop_id, admin_id;
    private SelectAddressPresenter presenter;
    private AddressInRangeAdapter inRangeAdapter;
    private AddressOutRangeAdapter outRangeAdapter;
    private ArrayList<AddressBean.AddressList> iAddresslist;
    private ArrayList<AddressBean.AddressList> oAddresslist;
    public static String address_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        presenter = new SelectAddressPresenter(this);
        token = SharedPreferencesUtil.getToken();
        admin_id = SharedPreferencesUtil.getAdminId();
        shop_id = getIntent().getStringExtra("shop_id");
        address_id = getIntent().getStringExtra("address_id");
        LogUtil.log("SelectAddressCart","id == "+address_id);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);

        toolbar.setTitleText("选择地址");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }

            @Override
            public void onMenuClick() {

            }
        });
        llAddNewAddress.setOnClickListener(this);
        btnLoadAgain.setOnClickListener(this);
        iAddresslist = new ArrayList<>();
        oAddresslist = new ArrayList<>();
        outRangeAdapter = new AddressOutRangeAdapter(this, oAddresslist);
        inRangeAdapter = new AddressInRangeAdapter(this, iAddresslist);
        listviewInRange.setAdapter(inRangeAdapter);
        listviewOutRange.setAdapter(outRangeAdapter);
        listviewInRange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressBean.AddressList list = iAddresslist.get(position);
                address_id = list.id;
                inRangeAdapter.notifyDataSetChanged();
                LogUtil.log("SelectAddressCart", "position == " + position+"    id == " +address_id);
                Intent intent = new Intent();
                intent.putExtra("address", list);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        if (!StringUtils.isEmpty(token)) {
            loadView.showView();
            presenter.loadAddress(token, admin_id, shop_id);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_new_address:
                Intent intent = new Intent(SelectAddressActivity.this, NewAddressActivity.class);
                intent.putExtra("edit", false);
                startActivityForResult(intent, BACK_CODE_UPDATE_ADDRESS);
                break;
            case R.id.btn_load_again:
                llLoadFail.setVisibility(View.GONE);
                loadView.showView();
                presenter.loadAddress(token, admin_id, shop_id);
                break;
        }
    }

    @Override
    public void loadAddress(AddressBean bean) {
        String lat = StringUtils.isEmpty(SharedPreferencesUtil.getLatitude()) ? "0" : SharedPreferencesUtil.getLatitude();
        String lng = StringUtils.isEmpty(SharedPreferencesUtil.getLongitude()) ? "0" : SharedPreferencesUtil.getLongitude();
        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        iAddresslist.clear();
        oAddresslist.clear();
        if (bean.data.addresslist != null && bean.data.addresslist.size() > 0) {
            presenter.getInRangeAddress(latLng, bean.data.addresslist, iAddresslist, oAddresslist);
            AddressManager.getInstance().saveAddressList(bean.data.addresslist);
        }
        if (oAddresslist.size() == 0) {
            tvOutRange.setVisibility(View.GONE);
        } else {
            tvOutRange.setVisibility(View.VISIBLE);
        }
        if (oAddresslist.size() == 0 && iAddresslist.size() == 0){
            scrollView.setVisibility(View.GONE);
            llNo.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            llNo.setVisibility(View.GONE);
        }
        inRangeAdapter.notifyDataSetChanged();
        outRangeAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail() {
        llLoadFail.setVisibility(View.VISIBLE);
        loadView.dismissView();
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this, s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == BACK_CODE_UPDATE_ADDRESS) {
                presenter.loadAddress(token, admin_id, shop_id);
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        if (iAddresslist.size() == 0) {
            address_id = "";
        }else {
            if (!StringUtils.isEmpty(address_id)){
                for (AddressBean.AddressList list  : iAddresslist){
                    if (list.id.equals(address_id)){
                        inRangeAdapter.notifyDataSetChanged();
                        intent.putExtra("address", list);
                        setResult(RESULT_OK, intent);
                        finish();
                        return;
                    }
                }
            }
            address_id = "";
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}

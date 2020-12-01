package com.newsuper.t.consumer.function.person.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.DelAddressBean;
import com.newsuper.t.consumer.function.person.adapter.AddressAdapter;
import com.newsuper.t.consumer.function.person.internal.IAddressView;
import com.newsuper.t.consumer.function.person.presenter.AddressPresenter;
import com.newsuper.t.consumer.function.person.request.AddressRequest;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddressActivity extends BaseActivity implements IAddressView, View.OnClickListener {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_add_address)
    LinearLayout mLlAddAddress;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.ll_none)
    LinearLayout mLlNone;

    public static final int ADDRESS_EDIT_CODE = 122;
    private AddressPresenter mPresenter;
    private AddressAdapter mAddressAdapter;
    ArrayList<AddressBean.AddressList> mAddressLists = new ArrayList<>();
    private String token;

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        mPresenter = new AddressPresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("我的地址");
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

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        mAddressAdapter = new AddressAdapter(this,mAddressLists);
        mRecyclerView.setAdapter(mAddressAdapter);
        mAddressAdapter.notifyDataSetChanged();
        mAddressAdapter.setItemClickListener(new AddressAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent();
                i.putExtra("addressData", mAddressLists.get(position));
                setResult(RESULT_OK,i);
                finish();
            }
        });
        loadView.showView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        token = SharedPreferencesUtil.getToken();
        HashMap<String, String> map = AddressRequest.addreessRequest(token,SharedPreferencesUtil.getAdminId());
        mPresenter.loadData(UrlConst.GET_ADDRESS_LIST, map);
    }

    //加载失败
    @Override
    public void loadFail() {
        mLlNone.setVisibility(View.VISIBLE);
        loadView.dismissView();
    }




    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ll_add_address)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_address:
                Intent i = new Intent(MyAddressActivity.this, NewAddressActivity.class);
                i.putExtra("edit", false);
                startActivity(i);
                break;
        }
    }

    @Override
    public void showDataToVIew(AddressBean bean) {
        loadView.dismissView();
        if (bean != null) {
            mAddressLists.clear();
            if (bean.data.addresslist != null && bean.data.addresslist.size() > 0) {
                mAddressLists.addAll(bean.data.addresslist);
                mLlNone.setVisibility(View.GONE);
            }else {
                loadFail();
            }
            mAddressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showDelDataView(DelAddressBean bean) {

    }


    @Override
    public void refresh2(DelAddressBean bean) {

    }

    @Override
    public void refresh(AddressBean bean) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == ADDRESS_EDIT_CODE){
                load();
            }
        }
    }
}

package com.newsuper.t.consumer.function.person.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.DepositBean;
import com.xunjoy.lewaimai.consumer.function.person.adapter.DepositAdapter;
import com.xunjoy.lewaimai.consumer.function.person.internal.IDepositView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.DepositPresenter;
import com.xunjoy.lewaimai.consumer.function.person.request.DepositRequest;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingAnimatorView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class MyDepositActivity extends BaseActivity implements IDepositView {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.ll_none)
    LinearLayout llNone;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_none_deposit_history_btn)
    TextView tvHistoryBtn;
    @BindView(R.id.ll_deposit_history_btn)
    LinearLayout llHistoryBtn;
    private DepositPresenter mPresenter;
    private DepositAdapter adapter;
    private ArrayList<DepositBean.DataBean> mList = new ArrayList<>();
    private String token;
    private String adminId;

    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
        adminId = SharedPreferencesUtil.getAdminId();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);
        mPresenter = new DepositPresenter(this);
        toolbar.setBackImageViewVisibility(View.VISIBLE);
        toolbar.setTitleText("我的押金");
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
        loadView.showView();
        adapter = new DepositAdapter(this, mList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        HashMap<String, String> map = DepositRequest.depositRequest(token, adminId, null, null);
        mPresenter.loadData(UrlConst.GET_MY_DEPOSIT_LIST, map);
    }

    @Override
    public void showDataToView(DepositBean bean) {
        if (null != bean && null != bean.data) {
            if (bean.data.size() > 0) {
                mList.clear();
                adapter.setIsLoadAll(true);
                mList.addAll(bean.data);
                adapter.notifyDataSetChanged();
            }
        }
        if (mList.size() == 0)
            llNone.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadFail() {
        llNone.setVisibility(View.VISIBLE);
    }

    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.ll_none, R.id.tv_none_deposit_history_btn, R.id.ll_deposit_history_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_none:
                load();
                break;
            case R.id.ll_deposit_history_btn:
            case R.id.tv_none_deposit_history_btn:
                startActivity(new Intent(this, DepositHistoryActivity.class));
                break;
        }
    }

}

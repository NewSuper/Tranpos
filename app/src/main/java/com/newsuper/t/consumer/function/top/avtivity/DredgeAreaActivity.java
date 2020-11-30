package com.newsuper.t.consumer.function.top.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.DredgeAreaBean;
import com.xunjoy.lewaimai.consumer.function.top.adapter.DredgeAreaAdapter;
import com.xunjoy.lewaimai.consumer.function.top.internal.IDredgeAreaView;
import com.xunjoy.lewaimai.consumer.function.top.presenter.DredgeAreaPresenter;
import com.xunjoy.lewaimai.consumer.function.top.request.DredgeAreaRequest;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingAnimatorView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class DredgeAreaActivity extends BaseActivity implements IDredgeAreaView{

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String token;
    private String adminId;
    private DredgeAreaPresenter mPresenter;
    private DredgeAreaAdapter adapter;
    private ArrayList<DredgeAreaBean.DataBean> list = new ArrayList<>();

    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
        adminId = SharedPreferencesUtil.getAdminId();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_dredge_area);
        ButterKnife.bind(this);
        mPresenter = new DredgeAreaPresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("已开通区域");
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

        loadView.showView();

        adapter = new DredgeAreaAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        HashMap<String,String> map = DredgeAreaRequest.loadData(token,adminId);
        mPresenter.loadData(UrlConst.GET_DREDGE_AREA_LIST,map);
    }

    @Override
    public void showLoadData(DredgeAreaBean bean) {
        if (null != bean.data) {
            list.clear();
            list.addAll(bean.data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadFail() {

    }

    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            System.out.println("自定义地址页面地址选择成功："+data.getStringExtra("address")
                    +"("+data.getStringExtra("lat")+","+data.getStringExtra("lng")+")");
            setResult(RESULT_OK, data);
            finish();
        }
    }
}

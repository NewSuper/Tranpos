package com.newsuper.t.consumer.function.person.activity;

import android.content.Intent;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.TraceBean;
import com.xunjoy.lewaimai.consumer.bean.TraceChildBean;
import com.xunjoy.lewaimai.consumer.bean.TraceGroupBean;
import com.xunjoy.lewaimai.consumer.function.person.adapter.TraceAdapter;
import com.xunjoy.lewaimai.consumer.function.person.internal.ITraceView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.TracePresenter;
import com.xunjoy.lewaimai.consumer.function.person.request.TraceRequest;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingAnimatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by Administrator on 2019/4/25 0025
 */
public class MyTraceActivity extends BaseActivity implements ITraceView {
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.elv_activity_foot)
    ExpandableListView elv;
    @BindView(R.id.cb_activity_foot_all)
    CheckBox cbAll;
    @BindView(R.id.tv_activity_foot_delete)
    TextView tvDelete;
    @BindView(R.id.ll_activity_foot_edit)
    LinearLayout llEdit;
    @BindView(R.id.ll_none)
    LinearLayout llNone;

    private boolean isEdit = true;
    private TracePresenter mPresenter;
    private String token;
    private String adminId;
    private TraceAdapter adapter;
    private List<TraceGroupBean> groupBeans = new ArrayList<>();
    private List<List<TraceChildBean>> childBeans = new ArrayList<>();

    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
        adminId = SharedPreferencesUtil.getAdminId();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_foot);
        ButterKnife.bind(this);
        mPresenter = new TracePresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("我的足迹");
        mToolbar.setMenuText("");
        mToolbar.setMenuTextColor(R.color.text_color_66);
        tvDelete.getText();
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {
                if (isEdit) {
                    mToolbar.setMenuText("完成");
                    isEdit = false;
                    llEdit.setVisibility(View.VISIBLE);
                } else {
                    mToolbar.setMenuText("编辑");
                    isEdit = true;
                    llEdit.setVisibility(View.GONE);
                }
                adapter.setShowCb(!isEdit);
            }
        });
        loadView.showView();
        llEdit.setVisibility(View.GONE);
        adapter = new TraceAdapter(this,groupBeans,childBeans,cbAll);
        elv.setGroupIndicator(null);
        elv.setAdapter(adapter);
        View footView = LayoutInflater.from(this).inflate(R.layout.adapter_shop_list_foot,null);
        elv.addFooterView(footView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        HashMap<String, String> map = TraceRequest.loadTraceData(token,adminId,null,"1000");
        mPresenter.loadData(UrlConst.GET_TRACE_LIST,map);
    }

    private void deleteTrace(String ids,String is_all) {
        System.out.println("删除ids== "+ids);
        HashMap<String,String> map = TraceRequest.deleteTrace(token,adminId,ids,is_all);
        mPresenter.deleteTrace(UrlConst.DELETE_TRACE,map);
    }

    @Override
    public void showDataToView(TraceBean bean) {
        if (null != bean.data.footprintlist && bean.data.footprintlist.size() > 0) {
            llNone.setVisibility(View.GONE);
            groupBeans.clear();
            childBeans.clear();
            mToolbar.setMenuText("编辑");
            isEdit = true;
            llEdit.setVisibility(View.GONE);
            List<TraceBean.DataBean.FootprintlistBean> fBeans = bean.data.footprintlist;
            for (int i = 0;i<fBeans.size();i++) {
                TraceGroupBean traceGroupBean = new TraceGroupBean();
                traceGroupBean.checked = false;
                traceGroupBean.groupName = fBeans.get(i).date;
                groupBeans.add(traceGroupBean);
                List<TraceBean.DataBean.FootprintlistBean.ItemsBean> itemsBeans = fBeans.get(i).items;
                List<TraceChildBean> childBeanList = new ArrayList<>();
                for (int j = 0; j<itemsBeans.size();j++) {
                    TraceChildBean traceChildBean = new TraceChildBean();
                    traceChildBean.checked = false;
                    traceChildBean.id = itemsBeans.get(j).id;
                    traceChildBean.shopInfo = itemsBeans.get(j).shopinfo;
                    childBeanList.add(traceChildBean);
                }
                childBeans.add(childBeanList);
            }
            for (int i = 0;i<groupBeans.size();i++) {
                elv.expandGroup(i);
            }
            adapter.setShowCb(!isEdit);
        } else
            llNone.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadFail() {
        llNone.setVisibility(View.VISIBLE);
    }

    @Override
    public void deleteTraceSuss() {
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        HashMap<String, String> map = TraceRequest.loadTraceData(token,adminId,null,null);
        mPresenter.loadData(UrlConst.GET_TRACE_LIST,map);
    }

    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.ll_none, R.id.tv_activity_foot_delete, R.id.cb_activity_foot_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_none:
                load();
                break;
            case R.id.tv_activity_foot_delete:
                //获取选中的足迹id 用逗号隔开 ids
                String deleteIds = adapter.getSelectIds();
                if (!StringUtils.isEmpty(deleteIds)) {
                    if (cbAll.isChecked())
                        deleteTrace(deleteIds, "1");
                    else
                        deleteTrace(deleteIds, "0");
                } else {
                    mToolbar.setMenuText("编辑");
                    isEdit = true;
                    llEdit.setVisibility(View.GONE);
                    adapter.setShowCb(!isEdit);
                }
                break;
            case R.id.cb_activity_foot_all:
                adapter.allChecked(cbAll.isChecked());
                break;
        }
    }
}

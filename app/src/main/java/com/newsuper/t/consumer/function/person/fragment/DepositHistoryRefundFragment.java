package com.newsuper.t.consumer.function.person.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.DepositHistoryBean;
import com.xunjoy.lewaimai.consumer.function.person.adapter.DepositHistoryAdapter;
import com.xunjoy.lewaimai.consumer.function.person.internal.IDepositHistoryView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.DepositHistoryPresenter;
import com.xunjoy.lewaimai.consumer.function.person.request.DepositHistoryRequest;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.LoadingAnimatorView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by Administrator on 2019/6/21 0021
 */
public class DepositHistoryRefundFragment extends Fragment implements IDepositHistoryView {

    @BindView(R.id.rv_fragment_deposit_history)
    RecyclerView rv;
    @BindView(R.id.srl_fragment_deposit_history)
    SmartRefreshLayout srl;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.tv_fragment_deposit_history_none)
    TextView tvNone;
    Unbinder unbinder;
    String token;
    String adminId;
    int page = 1;
    DepositHistoryPresenter mPresenter;
    DepositHistoryAdapter adapter;
    ArrayList<DepositHistoryBean.DataBean.DatasBean> mList = new ArrayList<>();
    boolean isLoading = false;
    boolean isLoadMore = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposit_history, null);
        token = SharedPreferencesUtil.getToken();
        adminId = SharedPreferencesUtil.getAdminId();
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new DepositHistoryPresenter(this);
        initPtrFrame();
        loadView.showView();
        adapter = new DepositHistoryAdapter(getContext(), mList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && (totalItemCount - visibleItemCount <= firstVisibleItem && dy > 0)) {
                    loadMore();
                }
            }
        });
        rv.setAdapter(adapter);
        loadData();
        return view;
    }

    //下拉刷新
    private void initPtrFrame() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setDisableContentWhenRefresh(true);
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setTextSizeTitle(14);
                layout.setPrimaryColorsId(R.color.bg_eb, R.color.text_color_66);//全局设置主题颜色
                classicsHeader.setDrawableArrowSize(15);
                return classicsHeader;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isLoadMore = false;
                page = 1;
                loadData();
            }
        });
        srl.setDisableContentWhenRefresh(true);
    }

    private void loadMore() {
        isLoadMore = true;
        loadData();
    }

    private void loadData() {
        isLoading = true;
        HashMap<String, String> map = DepositHistoryRequest.depositHistoryRequest(token, adminId, page + "", null, "2");
        mPresenter.loadData(UrlConst.GET_DEPOSIT_HISTORY_LIST, map);
    }

    @Override
    public void showDataToView(DepositHistoryBean bean) {
        isLoading = false;
        if (null != bean && null != bean.data && null != bean.data.datas) {
            if (bean.data.datas.size() > 0 && page <= bean.data.page) {
                page++;
                if (!isLoadMore)
                    mList.clear();
                mList.addAll(bean.data.datas);
                adapter.setIsLoadAll(false);
            }
            if ((bean.data.datas.size() == 0 && mList.size() > 0)|| page > bean.data.page)
                adapter.setIsLoadAll(true);
            adapter.notifyDataSetChanged();
        }
        if (mList.size() == 0)
            tvNone.setVisibility(View.VISIBLE);
        if (srl.getState() == RefreshState.Refreshing)
            srl.finishRefresh();
    }

    @Override
    public void loadFail() {
        tvNone.setVisibility(View.VISIBLE);
    }

    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this.getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

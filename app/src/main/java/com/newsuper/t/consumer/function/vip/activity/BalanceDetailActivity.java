package com.newsuper.t.consumer.function.vip.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.BalanceDetailBean;
import com.newsuper.t.consumer.function.vip.adapter.BalanceAdapter;
import com.newsuper.t.consumer.function.vip.inter.IBalanceDetail;
import com.newsuper.t.consumer.function.vip.presenter.BalanceDetailPresenter;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 余额明细
 */
public class BalanceDetailActivity extends BaseActivity implements IBalanceDetail{

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.balance_listview)
    ListView balanceListView;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.ll_none)
    LinearLayout llNone;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    private View footerView;
    private TextView tvMore;
    private BalanceDetailPresenter presenter;
    private BalanceAdapter balanceAdapter;
    private ArrayList<BalanceDetailBean.HistoryListBean> historyList;
    private boolean isBottom,isLoadingMore;
    private int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        presenter = new BalanceDetailPresenter(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_balance_detail);
        ButterKnife.bind(this);
        toolbar.setBackImageViewVisibility(View.VISIBLE);
        toolbar.setTitleText("余额明细");
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
        historyList = new ArrayList<>();
        balanceAdapter = new BalanceAdapter(this,historyList);
        balanceListView.setAdapter(balanceAdapter);
        balanceListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case SCROLL_STATE_IDLE:
                        if (isBottom && !isLoadingMore) {
                            if (tvMore != null){
                                isLoadingMore = true;
                                tvMore.setText("加载更多...");
                                presenter.getDataMore(SharedPreferencesUtil.getToken(),SharedPreferencesUtil.getAdminId(),page);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
        loadView.showView("");
        presenter.getData(SharedPreferencesUtil.getToken(),SharedPreferencesUtil.getAdminId(),"0");
    }

    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }

    @Override
    public void loadData(BalanceDetailBean bean) {
        if (bean.data != null && bean.data.historylist != null && bean.data.historylist.size() > 0){
            historyList.addAll(bean.data.historylist);
            balanceAdapter.notifyDataSetChanged();
            if (historyList.size() >= 30){
                if (footerView == null){
                    footerView = LayoutInflater.from(this).inflate(R.layout.layout_footer_balance, null);
                    tvMore = (TextView)footerView.findViewById(R.id.tv_load_more);
                    balanceListView.addFooterView(footerView);
                }
            }
        }else {
            llNone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadFail() {
        llNone.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadMoreData(BalanceDetailBean bean) {
        isLoadingMore = false;
        if (bean.data != null && bean.data.historylist != null && bean.data.historylist.size() > 0){
            page ++;
            historyList.addAll(bean.data.historylist);
            balanceAdapter.notifyDataSetChanged();
        }else {
            if (tvMore != null){
                tvMore.setText("已全部显示");
            }
        }
    }

    @Override
    public void loadMoreFail() {
        isLoadingMore = false;
    }
}

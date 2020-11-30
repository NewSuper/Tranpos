package com.newsuper.t.consumer.function.cityinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.PublishSearchBean;
import com.xunjoy.lewaimai.consumer.bean.ShopHistoryBean;
import com.xunjoy.lewaimai.consumer.function.cityinfo.adapter.PublishDetailPicAdapter;
import com.xunjoy.lewaimai.consumer.function.cityinfo.adapter.PublishSearchAdapter;
import com.xunjoy.lewaimai.consumer.function.cityinfo.internal.IPublishSearchView;
import com.xunjoy.lewaimai.consumer.function.cityinfo.presenter.PublishSearchPresenter;
import com.xunjoy.lewaimai.consumer.function.top.adapter.ShopHistoryAdapter;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.ListViewForScrollView;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog2;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublishSearchActivity extends BaseActivity implements View.OnClickListener, IPublishSearchView {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.lv_search)
    ListView lvSearch;
    @BindView(R.id.lv_history)
    ListViewForScrollView lvHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.ll_result)
    LinearLayout llResult;
    private ArrayList<ShopHistoryBean.HistoryBean> hs_list;
    //历史搜索记录
    private ShopHistoryAdapter historyAdapter;
    private LoadingDialog2 loadingDialog;
    private PublishSearchPresenter searchPresenter;
    private int page = 1;
    private ArrayList<PublishSearchBean.PublishSearchData> searchDatas;
    private PublishSearchAdapter searchAdapter;
    View footerView;
    TextView tvFooter;
    boolean isBottom,isLoadingMore = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_search);
        ButterKnife.bind(this);
        toolbar.setTitleText("搜索");
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
        tvClear.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        searchPresenter = new PublishSearchPresenter(this);
        //监听键盘搜索按键
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!StringUtils.isEmpty(edtSearch.getText().toString())) {
                        hideKeyBord();
                        String search = edtSearch.getText().toString().trim();

                        getSearchData(search);
                    } else {
                        ToastUtil.showTosat(PublishSearchActivity.this, "请输入关键字搜索");
                    }
                }
                return false;
            }
        });
        hs_list = SharedPreferencesUtil.getPublishSearchInfoList();
        if (hs_list == null || hs_list.size() == 0) {
            llHistory.setVisibility(View.GONE);
        } else {
            llHistory.setVisibility(View.VISIBLE);
            historyAdapter = new ShopHistoryAdapter(this, hs_list);
            lvHistory.setAdapter(historyAdapter);
            lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getSearchData(hs_list.get(position).name);
                }
            });
        }
        searchDatas = new ArrayList<>();
        searchAdapter = new PublishSearchAdapter(this, searchDatas);
        lvSearch.setAdapter(searchAdapter);
        footerView = LayoutInflater.from(this).inflate(R.layout.listview_footer_load_more, null);
        tvFooter = (TextView) footerView.findViewById(R.id.tv_load_more);
        lvSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        if (isBottom && !isLoadingMore) {
                            getMoreSearch();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = (firstVisibleItem + visibleItemCount == totalItemCount);
            }

        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PublishSearchActivity.this, PublishDetailActivity.class);
                intent.putExtra("info_id",searchDatas.get(position).id);
                startActivity(intent);
            }
        });
        loadingDialog = new LoadingDialog2(this);
        loadingDialog.setCancelable(true);

    }

    String currentSearch;
    private void getSearchData(String search) {
        if (!search.equals(currentSearch)){
            ShopHistoryBean.HistoryBean bean = new ShopHistoryBean.HistoryBean();
            bean.name = edtSearch.getText().toString();
            SharedPreferencesUtil.savePublishSearchInfo(bean);
        }
        loadingDialog.show();
        page = 1;
        currentSearch = search;
        searchPresenter.getPublishSearch(search, page);
    }

    private void getMoreSearch() {
        searchPresenter.getPublishSearch(currentSearch, page + 1);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                if (!StringUtils.isEmpty(edtSearch.getText().toString())) {
                    hideKeyBord();
                    String search = edtSearch.getText().toString().trim();
                    getSearchData(search);
                } else {
                    ToastUtil.showTosat(PublishSearchActivity.this, "请输入关键字搜索");
                }
                break;
            case R.id.tv_clear:
                SharedPreferencesUtil.clearPublishSearchInfo();
                hs_list = SharedPreferencesUtil.getPublishSearchInfoList();
                historyAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void dialogDismiss() {
        loadingDialog.dismiss();
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this, s);
    }

    @Override
    public void getSearchData(PublishSearchBean bean) {
        if (bean.data != null && bean.data.size() > 0) {
            searchDatas.clear();
            searchDatas.addAll(bean.data);
            searchAdapter.notifyDataSetChanged();
            llHistory.setVisibility(View.GONE);
            llResult.setVisibility(View.GONE);
            if (bean.data.size() > 5){
                lvSearch.addFooterView(footerView);
                isLoadingMore = false;
            }
        } else {
            llHistory.setVisibility(View.GONE);
            llResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getMoreSearchData(PublishSearchBean bean) {
        if (bean.data != null && bean.data.size() > 0) {
            page++;
            searchDatas.addAll(bean.data);
            searchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void searchFail() {
        llHistory.setVisibility(View.GONE);
        llResult.setVisibility(View.VISIBLE);
    }
}

package com.newsuper.t.consumer.function.top.avtivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.ShopHistoryBean;
import com.newsuper.t.consumer.bean.ShopSearchBean;
import com.newsuper.t.consumer.function.top.adapter.ShopHistoryAdapter;
import com.newsuper.t.consumer.function.top.adapter.ShopSearchAdapter;
import com.newsuper.t.consumer.function.top.internal.IShopSearchView;
import com.newsuper.t.consumer.function.top.presenter.ShopSearchPresenter;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.ShopScreenView;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 店铺搜索
 */
public class ShopSearchActivity extends BaseActivity implements View.OnClickListener, IShopSearchView {
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.history_listView)
    ListView historyListView;
    @BindView(R.id.iv_del)
    ImageView iv_del;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.search_recyclerView)
    ListView searchRecyclerView;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.sl_shop)
    RelativeLayout sl_shop;
    @BindView(R.id.shop_screen)
    ShopScreenView shopScreen;
    @BindView(R.id.ll_filter_empty)
    LinearLayout ll_filter_empty;
    @BindView(R.id.tv_reset)
    TextView tv_reset;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    @BindView(R.id.tv_empty_tip)
    TextView tv_empty_tip;

    private ShopSearchPresenter presenter;
    //搜索店铺
    private ShopSearchAdapter storeSearchAdapter;
    //历史搜索记录
    private ShopHistoryAdapter historyAdapter;
    private ArrayList<ShopSearchBean.ShopList> shopLists;
    private ArrayList<ShopHistoryBean.HistoryBean> historyList;
    private LoadingDialog2 loadingDialog;
    private String search_area_id;
    private boolean isBottom;
    private int searchPage = 1;
    private int filterPage = 1;
    private boolean isLoadingMore;
    private String keyWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        if (shopLists == null) {
            shopLists = new ArrayList<>();
        }
        if (historyList == null) {
            historyList = new ArrayList<>();
        }
        loadingDialog = new LoadingDialog2(this);
        presenter = new ShopSearchPresenter(this);
        search_area_id  = getIntent().getStringExtra("search_area_id");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_shop_search);
        ButterKnife.bind(this);
        ivClear.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        iv_del.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        storeSearchAdapter = new ShopSearchAdapter(this, shopLists);
        searchRecyclerView.setAdapter(storeSearchAdapter);
        historyList = SharedPreferencesUtil.getShopSearchInfoList();
        llHistory.setVisibility(View.VISIBLE);
        historyAdapter = new ShopHistoryAdapter(this, historyList);
        historyListView.setAdapter(historyAdapter);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = historyAdapter.getCount() - 1 - position;
                searchShop(historyList.get(i).name);
                edtSearch.setText(historyList.get(i).name);
            }
        });
        searchRecyclerView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = (firstVisibleItem + visibleItemCount == totalItemCount);
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        if (isBottom && !isLoadingMore) {
                            searchShopMore();
                        }
                        break;
                }
            }
        });
        //监听键盘搜索按键
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!StringUtils.isEmpty(edtSearch.getText().toString().trim())) {
                        hideKeyBord();
                        loadingDialog.show();
                        searchShop(edtSearch.getText().toString().trim());
                        if (checkHistoryList(edtSearch.getText().toString().trim())) {
                            ShopHistoryBean.HistoryBean bean = new ShopHistoryBean.HistoryBean();
                            bean.name = edtSearch.getText().toString().trim();
                            presenter.saveHistory(bean);
                            historyList.add(bean);
                            historyAdapter.notifyDataSetChanged();
                        }
                    }else {
                        ToastUtil.showTosat(ShopSearchActivity.this,"请输入关键字搜索");
                    }
                }
                return false;
            }
        });
        shopScreen.setShowSingle(false);
        shopScreen.setShopScreenListener(new ShopScreenView.ShopScreenListener() {
            @Override
            public void onSort(String value,String valueName) {
                filterShop();
            }

            @Override
            public void onSale(String value) {
                filterShop();
            }

            @Override
            public void onDistance(String value) {
                filterShop();
            }

            @Override
            public void onScreen(String value1,String value2,String value3) {
                filterShop();
            }

            @Override
            public void onSelect(int i) {
                filterShop();
            }

            @Override
            public void onSingleSelect(int i,  Set<String> selectValue) {

            }

            @Override
            public void onClear() {
                filterShop();

            }
        });

    }

    private boolean checkHistoryList(String name) {
        for (ShopHistoryBean.HistoryBean bean : historyList) {
            if (name.equals(bean.name))
                return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_clear:
                edtSearch.setText("");
                sl_shop.setVisibility(View.GONE);
                llHistory.setVisibility(View.VISIBLE);
                ll_empty.setVisibility(View.GONE);
                shopScreen.setDefaultValue2();
                break;
            case R.id.tv_search:
                if (!StringUtils.isEmpty(edtSearch.getText().toString().trim())) {
                    hideKeyBord();
                    loadingDialog.show();
                    searchShop(edtSearch.getText().toString().trim());
                    if (checkHistoryList(edtSearch.getText().toString().trim())) {
                        ShopHistoryBean.HistoryBean bean = new ShopHistoryBean.HistoryBean();
                        bean.name = edtSearch.getText().toString().trim();
                        presenter.saveHistory(bean);
                        historyList.add(bean);
                        historyAdapter.notifyDataSetChanged();
                    }
                }else {
                    ToastUtil.showTosat(ShopSearchActivity.this,"请输入关键字搜索");
                }
                break;
            case R.id.iv_del:
                SharedPreferencesUtil.clearShopSearchInfo();
                historyList.clear();
                if (historyAdapter != null){
                    historyAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_reset:
                shopScreen.setDefaultValue2();
                searchShop(edtSearch.getText().toString().trim());
                break;
        }
    }

    @Override
    public void showDataToVIew(ShopSearchBean bean) {
        ll_filter_empty.setVisibility(View.GONE);
        ll_empty.setVisibility(View.GONE);
        if (bean.data != null) {
            shopLists.clear();
            if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
                llHistory.setVisibility(View.GONE);
                storeSearchAdapter.setSearchWord(edtSearch.getText().toString().trim());
                shopScreen.setDefaultValue2();
                shopLists.addAll(bean.data.shoplist);
                storeSearchAdapter.setIs_show_expected_delivery(bean.data.is_show_expected_delivery);
                storeSearchAdapter.setIs_show_sales_volume(bean.data.is_show_sales_volume);
                shopScreen.setShopClassifyData(bean.data.shoptype);
                storeSearchAdapter.notifyDataSetChanged();
                sl_shop.setVisibility(View.VISIBLE);
            }else {
//                showTipsDialog("您搜索的店铺或商品不存在");
                ll_empty.setVisibility(View.VISIBLE);
                llHistory.setVisibility(View.GONE);
                sl_shop.setVisibility(View.GONE);
            }
            if (storeSearchAdapter.getCount() > 1){
                searchRecyclerView.setSelection(0);
            }
        }

    }

    @Override
    public void showDataToVIewMore(ShopSearchBean bean) {
        isLoadingMore = false;
        if (bean.data != null) {
            if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
                searchPage++;
                shopLists.addAll(bean.data.shoplist);
                storeSearchAdapter.setSearchWord(edtSearch.getText().toString().trim());
                storeSearchAdapter.notifyDataSetChanged();
            }else {

            }
        }
    }

    @Override
    public void showFilterData(ShopSearchBean bean) {
        ll_filter_empty.setVisibility(View.GONE);
        ll_empty.setVisibility(View.GONE);
        shopLists.clear();
        if (bean.data != null) {
            if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
                llHistory.setVisibility(View.GONE);
                storeSearchAdapter.setSearchWord(edtSearch.getText().toString().trim());
                shopLists.addAll(bean.data.shoplist);
                shopScreen.setShopClassifyData(bean.data.shoptype);
                sl_shop.setVisibility(View.VISIBLE);
            }else {
                ll_filter_empty.setVisibility(View.VISIBLE);
                ll_empty.setVisibility(View.GONE);
                llHistory.setVisibility(View.GONE);
            }
        }
        storeSearchAdapter.notifyDataSetChanged();
        if (storeSearchAdapter.getCount() > 1){
            searchRecyclerView.setSelection(0);
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }

    @Override
    public void dialogDismiss() {
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    private void searchShop(String search){
        keyWord = search;
        isLoadingMore = false;
        searchPage = 1;
        if (StringUtils.isEmpty(search)){
            UIUtils.showToast("请输入关键字搜索");
            return;
        }
        shopScreen.setDefaultValue2();
        String type_id = shopScreen.getTypeValue();
        String order_type = shopScreen.getSortValue();
        String filter = shopScreen.getFilterValue();
        String services = shopScreen.getServiceValue();
        presenter.searchShop(search,search_area_id,type_id,order_type,filter,services,searchPage);
    }
    private void searchShopMore(){
        keyWord = edtSearch.getText().toString().trim();
        isLoadingMore = true;
//        shopScreen.setDefaultValue2();
        String type_id = shopScreen.getTypeValue();
        String order_type = shopScreen.getSortValue();
        String filter = shopScreen.getFilterValue();
        String services = shopScreen.getServiceValue();
        presenter.searchShop(keyWord,search_area_id,type_id,order_type,filter,services,searchPage + 1);
    }

    private void filterShop(){
        keyWord = edtSearch.getText().toString().trim();
        if (StringUtils.isEmpty(keyWord)){
            UIUtils.showToast("请输入关键字搜索");
            return;
        }
        searchPage = 1;
        String type_id = shopScreen.getTypeValue();
        String order_type = shopScreen.getSortValue();
        String filter = shopScreen.getFilterValue();
        String services = shopScreen.getServiceValue();
        presenter.filterShop(keyWord,search_area_id,type_id,order_type,filter,services,"1");
    }

}

package com.newsuper.t.consumer.function.top.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.function.person.activity.SignActivity;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.selectgoods.adapter.MyItemAnimator;
import com.newsuper.t.consumer.function.top.adapter.ShopAdapter;
import com.newsuper.t.consumer.function.top.adapter.ShopTypeAdapter;
import com.newsuper.t.consumer.function.top.internal.IWShopListView;
import com.newsuper.t.consumer.function.top.presenter.WShopListPresenter;
import com.newsuper.t.consumer.function.top.request.TopRequest;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.ShopRadioView;
import com.newsuper.t.consumer.widget.ShopScreenView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 店铺列表
 */
public class WShopListActivity extends BaseActivity implements IWShopListView {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.btn_load_again)
    Button btnLoadAgain;
    @BindView(R.id.ll_load_fail)
    LinearLayout llLoadFail;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.shop_screen)
    ShopScreenView shopScreen;
    @BindView(R.id.ll_filter_empty)
    LinearLayout ll_filter_empty;
    @BindView(R.id.tv_reset)
    TextView tv_reset;

    private WShopListPresenter presenter;
    private String token, admin_id;
    private int page = 1;
    private ShopAdapter shopAdapter;
    private ArrayList<TopBean.ShopList> shopLists = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private boolean isLoading = false;
    private String from_id = "";
    private String combine_id = "0";
    private String area_id;
    private LoadingDialog2 loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        presenter = new WShopListPresenter(this);
        loadingDialog = new LoadingDialog2(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_wshop_list);
        ButterKnife.bind(this);
        area_id = getIntent().getStringExtra("area_id");
        toolbar.setTitleText("店铺列表");
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
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopScreen.setDefaultValue2();
                loadShop();
            }
        });
        btnLoadAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadShop();
            }
        });
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new MyItemAnimator());
        shopAdapter = new ShopAdapter(this, shopLists);
        shopAdapter.setShopOnClickListener(new ShopAdapter.ShopOnClickListener() {
            @Override
            public void onShopOnClick(TopBean.ShopList shopList) {
                Intent intent = new Intent(WShopListActivity.this, SelectGoodsActivity3.class);
                intent.putExtra("from_page", "topPage");
                intent.putExtra("shop_info", shopList);
                startActivity(intent);
            }

            @Override
            public void onLogoClick() {
                Intent intent = new Intent(WShopListActivity.this, SignActivity.class);
                intent.putExtra("type", 5);
                startActivity(intent);
            }
        });
        shopScreen.setShowSingle(true);
        shopScreen.setShopScreenListener(new ShopScreenView.ShopScreenListener() {
            @Override
            public void onSort(String value,String valueName) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(WShopListActivity.this);
                }
                loadingDialog.show();
                filterShop();
            }

            @Override
            public void onSale(String value) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(WShopListActivity.this);
                }
                loadingDialog.show();
                filterShop();
            }

            @Override
            public void onDistance(String value) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(WShopListActivity.this);
                }
                loadingDialog.show();
                filterShop();
            }

            @Override
            public void onScreen(String value1,String value2,String value3) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(WShopListActivity.this);
                }
                loadingDialog.show();
                filterShop();
            }

            @Override
            public void onSelect(int i) {
//                shopAdapter.setSelect(i);
            }
            @Override
            public void onSingleSelect(int i,  Set<String> selectValue) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(WShopListActivity.this);
                }
                loadingDialog.show();
//                loadShop();
                filterShop();
            }

            @Override
            public void onClear() {
                from_id  = "";
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(WShopListActivity.this);
                }
                loadingDialog.show();
                loadShop();
            }
        });
        recyclerView.setAdapter(shopAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (totalItemCount < page * 20) {
                    return;
                }
                //这里需要好好理解
                if (!isLoading && (totalItemCount - visibleItemCount <= firstVisibleItem)) {
                    loadMoreShop();
                }

            }
        });
        from_id = getIntent().getStringExtra("from_id");
        if (!StringUtils.isEmpty(from_id)){
            shopScreen.setTypeSelectValue(from_id);
        }
        String c = getIntent().getStringExtra("combine_id");
        combine_id = StringUtils.isEmpty(c) ? "0" : c;
        String lat = SharedPreferencesUtil.getLatitude();
        if (!StringUtils.isEmpty(lat) && !"0".equals(lat)){

        }
        loadView.showView();
        loadShop();
    }

    @Override
    public void dialogDismiss() {
        isLoading = false;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this, s);
    }

    @Override
    public void loadFail() {
        loadView.dismissView();
        isLoading = false;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


    @Override
    public void loadShop() {
        page = 1;
        token = SharedPreferencesUtil.getToken();
        admin_id = SharedPreferencesUtil.getAdminId();
        String lat = SharedPreferencesUtil.getLatitude();
        String lng = SharedPreferencesUtil.getLongitude();
        String type_id = shopScreen.getTypeValue();
        String order_type = shopScreen.getSortValue();
        String condition = shopScreen.getFilterValue();
        String services = shopScreen.getServiceValue();
        //是否为分类组合
        if (StringUtils.isEmpty(from_id)) {
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.getShopListNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + "", "2", combine_id,area_id);
            } else {
                map = TopRequest.getShopListLogin(admin_id, token, type_id, order_type, condition, services,lat, lng, page + "", "2", combine_id,area_id);
            }
            presenter.loadShopData(map);
        } else {
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.shopListRequestNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + "", "2",area_id);
            } else {
                map = TopRequest.shopListRequestWithLogin(admin_id, token, type_id, order_type, condition,services, lat, lng, page + "", "2",area_id);
            }
            presenter.loadShopData(map);
        }
    }
    private void filterShop(){
        page = 1;
        token = SharedPreferencesUtil.getToken();
        admin_id = SharedPreferencesUtil.getAdminId();
        String lat = SharedPreferencesUtil.getLatitude();
        String lng = SharedPreferencesUtil.getLongitude();
        String type_id = shopScreen.getTypeValue();
        String order_type = shopScreen.getSortValue();
        String condition = shopScreen.getFilterValue();
        String services = shopScreen.getServiceValue();
        combine_id = "0";
        //是否为分类组合
        if (StringUtils.isEmpty(from_id)) {
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.getShopListNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + "", "2", combine_id,area_id);
            } else {
                map = TopRequest.getShopListLogin(admin_id, token, type_id, order_type, condition, services,lat, lng, page + "", "2", combine_id,area_id);
            }
            presenter.loadFilterShopData(map);
        } else {
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.shopListRequestNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + "", "2",area_id);
            } else {
                map = TopRequest.shopListRequestWithLogin(admin_id, token, type_id, order_type, condition,services, lat, lng, page + "", "2",area_id);
            }
            presenter.loadFilterShopData(map);
        }
    }

    @Override
    public void loadMoreShop() {
        isLoading = true;
        token = SharedPreferencesUtil.getToken();
        admin_id = SharedPreferencesUtil.getAdminId();
        String lat = SharedPreferencesUtil.getLatitude();
        String lng = SharedPreferencesUtil.getLongitude();
        String type_id = shopScreen.getTypeValue();
        String order_type = shopScreen.getSortValue();
        String condition = shopScreen.getFilterValue();
        String services = shopScreen.getServiceValue();
        if (StringUtils.isEmpty(from_id)) {
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.getShopListNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, (page + 1)+ "", "2", combine_id,area_id);
            } else {
                map = TopRequest.getShopListLogin(admin_id, token, type_id, order_type, condition, services,lat, lng, (page + 1) + "", "2", combine_id,area_id);
            }
            presenter.loadMoreShopData(map);
        } else {
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.shopListRequestNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, (page + 1) + "", "2",area_id);
            } else {
                map = TopRequest.shopListRequestWithLogin(admin_id, token, type_id, order_type, condition, services,lat, lng, (page + 1) + "", "2",area_id);
            }
            presenter.loadMoreShopData(map);
        }
    }

    @Override
    public void showDataToView(TopBean bean) {
        loadView.dismissView();
        llLoadFail.setVisibility(View.GONE);
        ll_filter_empty.setVisibility(View.GONE);
        if (bean.data != null) {
            if (bean.data.shoptype != null) {
                shopScreen.setShopClassifyData(bean.data.shoptype);
            }
            shopLists.clear();
            if (bean.data.shoplist != null){
                if (bean.data.shoplist.size() > 0){
                    for (TopBean.ShopList shop : bean.data.shoplist){
                        shop.is_show_expected_delivery = bean.data.is_show_expected_delivery;
                        shopLists.add(shop);
                    }
                    shopAdapter.setIsLoadAll(false);
                    shopAdapter.notifyDataSetChanged();
                }else {
                    llLoadFail.setVisibility(View.VISIBLE);
                    tvFail.setText("暂无店铺");
                    btnLoadAgain.setVisibility(View.GONE);
                }
            }else {
                llLoadFail.setVisibility(View.VISIBLE);
                tvFail.setText("暂无店铺");
                btnLoadAgain.setVisibility(View.GONE);
            }

        }
        if (shopAdapter.getItemCount() > 1){
            recyclerView.scrollToPosition(0);
        }

    }

    @Override
    public void showMoreShop(TopBean bean) {
        isLoading = false;
        if (bean.data != null && bean.data != null && bean.data.shoplist != null) {
            if (bean.data.shoplist.size() > 0) {
                page++;
                for (TopBean.ShopList shop : bean.data.shoplist){
                    shop.is_show_expected_delivery = bean.data.is_show_expected_delivery;
                    shopLists.add(shop);
                }
            } else {
                shopAdapter.setIsLoadAll(true);
                ToastUtil.showTosat(this,"所有店铺都加载完啦");
            }
        } else {
            shopAdapter.setIsLoadAll(true);
            ToastUtil.showTosat(this,"所有店铺都加载完啦");
        }
        shopAdapter.notifyDataSetChanged();

    }

    @Override
    public void showFilterDataToView(TopBean bean) {
        loadView.dismissView();
        llLoadFail.setVisibility(View.GONE);
        ll_filter_empty.setVisibility(View.GONE);
        if (bean.data != null) {
            shopLists.clear();
            if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
                for (TopBean.ShopList shop : bean.data.shoplist){
                    shop.is_show_expected_delivery = bean.data.is_show_expected_delivery;
                    shopLists.add(shop);
                }
            }else {
                LogUtil.log("showFilterDataToView"," 000 empty");
//                shopScreen.showEmptyView(0);
                ll_filter_empty.setVisibility(View.VISIBLE);
            }
            shopAdapter.notifyDataSetChanged();
        }else {
            LogUtil.log("showFilterDataToView","1111 empty");
//            shopScreen.showEmptyView(0);
            ll_filter_empty.setVisibility(View.VISIBLE);
        }
        if (shopAdapter.getItemCount() > 1){
            recyclerView.scrollToPosition(0);
        }
    }
}

package com.newsuper.t.consumer.function.top.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseFragment;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.selectgoods.adapter.MyItemAnimator;
import com.newsuper.t.consumer.function.top.adapter.ShopAdapter;
import com.newsuper.t.consumer.function.top.internal.IWShopListView;
import com.newsuper.t.consumer.function.top.presenter.WShopListPresenter;
import com.newsuper.t.consumer.function.top.request.TopRequest;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.ShopScreenView;
import com.newsuper.t.consumer.widget.popupwindow.QRCodePopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class ShopClassifyFragment extends BaseFragment implements IWShopListView, View.OnClickListener {
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.rl_code)
    RelativeLayout rlCode;
    @BindView(R.id.shop_screen)
    ShopScreenView shopScreen;
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
    Unbinder unbinder;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_call)
    TextView tvCall;
    @BindView(R.id.rl_phone)
    LinearLayout rlPhone;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
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
    private LoadingDialog2 loadingDialog;
    private String from_id = "";
    private String combine_id = "0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_classify, null);
        unbinder = ButterKnife.bind(this, view);
        mToolbar.setBackImageViewVisibility(View.GONE);
        mToolbar.setTitleText("店铺分类");
        mToolbar.setMenuText("");
        btnLoadAgain.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        tvCall.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        llLogin.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new MyItemAnimator());
        shopAdapter = new ShopAdapter(getContext(), shopLists);
        shopAdapter.setShopOnClickListener(new ShopAdapter.ShopOnClickListener() {
            @Override
            public void onShopOnClick(TopBean.ShopList shopList) {
                Intent intent = new Intent(getContext(), SelectGoodsActivity3.class);
                intent.putExtra("from_page", "topPage");
                intent.putExtra("shop_info", shopList);
                startActivity(intent);
            }

            @Override
            public void onLogoClick() {
               /* Intent intent = new Intent(getContext(), SignActivity.class);
                intent.putExtra("type", 5);
                startActivity(intent);*/
            }
        });
        shopScreen.setShowSingle(true);
        shopScreen.setShopScreenListener(new ShopScreenView.ShopScreenListener() {
            @Override
            public void onSort(String value,String valueName) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                filterShop();
            }

            @Override
            public void onSale(String value) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                filterShop();
            }

            @Override
            public void onDistance(String value) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                filterShop();
            }

            @Override
            public void onScreen(String value1,String value2,String value3) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                filterShop();
            }

            @Override
            public void onSelect(int i) {
            }

            @Override
            public void onSingleSelect(int i, Set<String> selectValue) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                filterShop();
            }

            @Override
            public void onClear() {
                from_id = "";
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
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
                if (shopLists.size() < 10) {
                    return;
                }
                //这里需要好好理解
                if (!isLoading && (totalItemCount - visibleItemCount <= firstVisibleItem)) {
                    loadMoreShop();
                }

            }
        });

        loadingDialog = new LoadingDialog2(getContext());
        presenter = new WShopListPresenter(this);
        from_id = getArguments().getString("from_id");
        if (!StringUtils.isEmpty(from_id)){
            shopScreen.setTypeSelectValue(from_id);
        }
        String c = getArguments().getString("combine_id");
        combine_id = StringUtils.isEmpty(c) ? "0" : c;
        String lat = SharedPreferencesUtil.getLatitude();
        if (!StringUtils.isEmpty(lat) && !"0".equals(lat)){
//            radioViewMain.setSortDisValue();
        }
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            llLogin.setVisibility(View.VISIBLE);
        }else {
            loadView.showView();
            loadShop();
        }

        return view;
    }

    public void refresh() {
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            llLogin.setVisibility(View.VISIBLE);
        }else {
            llLogin.setVisibility(View.GONE);
            loadView.showView();
            loadShop();
        }
    }

    @Override
    public void load() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        ToastUtil.showTosat(getContext(), s);
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
                map = TopRequest.getWShopNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + "", "2", combine_id);
            } else {
                map = TopRequest.getWShopLogin(admin_id, token, type_id, order_type, condition, services,lat, lng, page + "", "2", combine_id);
            }
            presenter.loadShopData(map);
        } else {
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.topRequestNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + "", "2");
            } else {
                map = TopRequest.topRequestWithLogin(admin_id, token, type_id, order_type, condition,services, lat, lng, page + "", "2");
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
                map = TopRequest.getWShopNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + "", "2", combine_id);
            } else {
                map = TopRequest.getWShopLogin(admin_id, token, type_id, order_type, condition, services,lat, lng, page + "", "2", combine_id);
            }
            presenter.loadFilterShopData(map);
        } else {
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.topRequestNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + "", "2");
            } else {
                map = TopRequest.topRequestWithLogin(admin_id, token, type_id, order_type, condition,services, lat, lng, page + "", "2");
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
                map = TopRequest.getWShopNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + 1 + "", "2", combine_id);
            } else {
                map = TopRequest.getWShopLogin(admin_id, token, type_id, order_type, condition, services,lat, lng, page + 1 + "", "2", combine_id);
            }
            presenter.loadMoreShopData(map);
        } else {
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.topRequestNoLogin(admin_id, type_id, order_type, condition,services, lat, lng, page + "", "2");
            } else {
                map = TopRequest.topRequestWithLogin(admin_id, token, type_id, order_type, condition, services,lat, lng, page + "", "2");
            }
            presenter.loadMoreShopData(map);
        }
    }

    @Override
    public void showDataToView(TopBean bean) {
        loadView.dismissView();
        llLoadFail.setVisibility(View.GONE);
        ll_filter_empty.setVisibility(View.GONE);
        if (bean != null) {
            if (bean.data != null) {
                if (bean.data.shoptype != null) {
                    shopScreen.setShopClassifyData(bean.data.shoptype);
                }
                shopLists.clear();
                if (bean.data.shoplist != null) {
                    if (bean.data.shoplist.size() > 0) {
                        if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
                            for (TopBean.ShopList shop : bean.data.shoplist){
                                shop.is_show_expected_delivery = bean.data.is_show_expected_delivery;
                                shopLists.add(shop);
                            }
                        }
                        shopAdapter.notifyDataSetChanged();
                    } else {
//                        shopScreen.showEmptyView(0);
                        llLoadFail.setVisibility(View.VISIBLE);
                        tvFail.setText("暂无店铺");
                        btnLoadAgain.setVisibility(View.GONE);
                    }
                } else {
//                    shopScreen.showEmptyView(0);
                    llLoadFail.setVisibility(View.VISIBLE);
                    tvFail.setText("暂无店铺");
                    btnLoadAgain.setVisibility(View.GONE);
                }
                if (shopAdapter.getItemCount() > 1){
                    recyclerView.scrollToPosition(0);
                }
            }

        } else {
//            shopScreen.showEmptyView(0);
            llLoadFail.setVisibility(View.VISIBLE);
            tvFail.setText("暂无店铺");
            btnLoadAgain.setVisibility(View.GONE);
        }

    }

    @Override
    public void showMoreShop(TopBean bean) {
        isLoading = false;
        if (bean.data != null && bean.data != null && bean.data.shoplist != null) {
            if (bean.data.shoplist.size() > 0) {
                page++;
                if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
                    for (TopBean.ShopList shop : bean.data.shoplist){
                        shop.is_show_expected_delivery = bean.data.is_show_expected_delivery;
                        shopLists.add(shop);
                    }
                }
                shopAdapter.notifyDataSetChanged();
            } else {
                shopAdapter.setIsLoadAll(true);
                ToastUtil.showTosat(getContext(), "所有店铺都加载完啦");
            }
            if (shopAdapter.getItemCount() > 1){
                recyclerView.scrollToPosition(0);
            }
        } else {
            shopAdapter.setIsLoadAll(true);
            ToastUtil.showTosat(getContext(), "所有店铺都加载完啦");
        }

    }

    @Override
    public void showFilterDataToView(TopBean bean) {
        loadView.dismissView();
        llLoadFail.setVisibility(View.GONE);
        ll_filter_empty.setVisibility(View.GONE);
        if (bean.data != null) {
            shopLists.clear();
            if (bean.data.shoplist != null && bean.data.shoplist.size() > 0) {
                if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
                    for (TopBean.ShopList shop : bean.data.shoplist){
                        shop.is_show_expected_delivery = bean.data.is_show_expected_delivery;
                        shopLists.add(shop);
                    }
                }
                shopAdapter.notifyDataSetChanged();
            } else {
//                shopScreen.showEmptyView(0);
                ll_filter_empty.setVisibility(View.VISIBLE);
            }
        }else {
            ll_filter_empty.setVisibility(View.VISIBLE);
//            shopScreen.showEmptyView(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_again:
                loadShop();
                break;
            case R.id.tv_ok:
                QRCodePopupWindow qrCodePopupWindow = new QRCodePopupWindow(getContext());
                qrCodePopupWindow.show();
                break;
            case R.id.tv_call:
                break;
            case R.id.btn_login:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent1, Const.GO_TO_LOGIN);
                break;
            case R.id.tv_reset:
                shopScreen.setDefaultValue2();
                loadShop();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.GO_TO_LOGIN && resultCode == getActivity().RESULT_OK){
            llLogin.setVisibility(View.GONE);
            loadView.showView();
            loadShop();
        }
    }
}

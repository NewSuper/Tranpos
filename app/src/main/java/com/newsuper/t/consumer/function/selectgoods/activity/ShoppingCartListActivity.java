package com.newsuper.t.consumer.function.selectgoods.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.application.BaseApplication;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.CartGoodsInfoBean;
import com.xunjoy.lewaimai.consumer.bean.CartGoodsModel;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.ShopCartListBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.adapter.CartGoodsLvAdapter;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IShoppingCartListView;
import com.xunjoy.lewaimai.consumer.function.selectgoods.presenter.ShoppingCartListPresenter;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.ListViewForScrollView;
import com.xunjoy.lewaimai.consumer.widget.LoadingAnimatorView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车列表
 */
public class ShoppingCartListActivity extends BaseActivity implements IShoppingCartListView, View.OnClickListener {
    public static final int ACTIVITY_RESULT = 11;
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.btn_load_again)
    Button btnLoadAgain;
    @BindView(R.id.ll_load_fail)
    LinearLayout llLoadFail;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.lv_goods)
    ListView lvGoods;
    private ShoppingCartListPresenter listPresenter;
    private List<GoodsListBean.GoodsInfo> goodsInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        listPresenter = new ShoppingCartListPresenter(this);
    }

    private void load() {
        JSONArray array = new JSONArray();
        ArrayList<String> shops = BaseApplication.greenDaoManager.getSelectShop();
        if (shops == null || shops.size() == 0) {
            llLoadFail.setVisibility(View.VISIBLE);
            tvFail.setText("您的购物车空空如也，快去逛逛吧！");
            btnLoadAgain.setText("去逛逛");
            return;
        }
        for (String s : shops) {
            if (!StringUtils.isEmpty(s)) {
                array.put(s);
            }
        }
        goodsInfos = BaseApplication.greenDaoManager.getAllGoods();
        JSONArray array1 = new JSONArray();//商品
        JSONArray array2 = new JSONArray();//套餐
        for (GoodsListBean.GoodsInfo s : goodsInfos) {
            if (s.packageNature != null && s.packageNature.size() > 0) {
                array2.put(s.id);
            } else {
                array1.put(s.id);
            }
        }
        loadView.showView();
        listPresenter.loadData(SharedPreferencesUtil.getToken(), SharedPreferencesUtil.getAdminId(), array, array1, array2);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_shopping_cart_list);
        ButterKnife.bind(this);
        toolbar.setTitleText("购物车");
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
        btnLoadAgain.setOnClickListener(this);
        load();
    }

    @Override
    public void loadData(ShopCartListBean bean) {
        if (bean.data.shoplist != null) {
            if (bean.data.shoplist.size() > 0) {
                if (bean.data.shoplist.size() == 0) {
                    llLoadFail.setVisibility(View.VISIBLE);
                    tvFail.setText("您的购物车空空如也，快去逛逛吧！");
                    btnLoadAgain.setText("去逛逛");
                    btnLoadAgain.setTextColor(Color.parseColor("#FFFFFF"));
                    return;
                }
                ArrayList<CartGoodsInfoBean> list = CartGoodsInfoBean.getCartGoodsInfo(bean.data);
                CartGoodsLvAdapter cartGoodsLvAdapter = new CartGoodsLvAdapter(this,list);
                lvGoods.setAdapter(cartGoodsLvAdapter);
                cartGoodsLvAdapter.setDeleteGoodsListener(new CartGoodsLvAdapter.DeleteGoodsListener() {
                    @Override
                    public void delete() {
                        llLoadFail.setVisibility(View.VISIBLE);
                        tvFail.setText("您的购物车空空如也，快去逛逛吧！");
                        btnLoadAgain.setText("去逛逛");
                        btnLoadAgain.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                });
            } else {
                llLoadFail.setVisibility(View.VISIBLE);
                tvFail.setText("您的购物车空空如也，快去逛逛吧！");
                btnLoadAgain.setText("去逛逛");
                btnLoadAgain.setTextColor(Color.parseColor("#FFFFFF"));
            }


        } else {
            llLoadFail.setVisibility(View.VISIBLE);
            tvFail.setText("您的购物车空空如也，快去逛逛吧！");
            btnLoadAgain.setText("去逛逛");
            btnLoadAgain.setTextColor(Color.parseColor("#FFFFFF"));
        }

    }

    @Override
    public void loadFail() {
        llLoadFail.setVisibility(View.VISIBLE);
        if (loadView != null) {
            loadView.dismissView();
        }
    }

    @Override
    public void dialogDismiss() {
        if (loadView != null) {
            loadView.dismissView();
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this, s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_again:
                if (btnLoadAgain.getText().toString().equals("去逛逛")) {
                    finish();
                    return;
                }
                load();
                llLoadFail.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_RESULT) {
            load();
        }
    }
}

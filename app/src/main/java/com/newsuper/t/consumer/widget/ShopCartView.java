package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.function.selectgoods.adapter.DividerDecoration;
import com.newsuper.t.consumer.function.selectgoods.adapter.MyItemAnimator;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopCartDetailAdapter;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCart;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCartDialog;


public class ShopCartView extends LinearLayout implements View.OnClickListener, IShopCart {
    private View rootView;
    private Context context;
    private ShopCart2 shopCart;
    private IShopCartDialog iShopCartDialog;
    private String shop_id;
    private ShopCartDetailAdapter cartAdapter;
    private MaxHeightRecycleView rcl_cart;
    private boolean isBindView;


    public ShopCartView(Context context) {
        super(context);
        initView(context);
    }

    public ShopCartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ShopCartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public boolean isBindView() {
        return isBindView;
    }

    private void initView(Context context){
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_shop_cart,null);
        rcl_cart = (MaxHeightRecycleView)rootView.findViewById(R.id.rcl_cart);
        this.addView(rootView);
    }
    public void bindView(ShopCart2 shopCart, String shop_id,boolean isEffectiveVip) {
        isBindView = true;
        this.shopCart = shopCart;
        this.shop_id = shop_id;
        rootView.findViewById(R.id.ll_clear).setOnClickListener(this);
        rcl_cart.setItemAnimator(new MyItemAnimator());
        rcl_cart.setLayoutManager(new LinearLayoutManager(context));
        DividerDecoration cartDivider = new DividerDecoration();
        cartDivider.setDividerColor(Color.parseColor("#EBEBEB"));
        rcl_cart.addItemDecoration(cartDivider);
        rcl_cart.setMaxHeight(360);
        cartAdapter = new ShopCartDetailAdapter(context, shopCart, isEffectiveVip, shop_id);
        cartAdapter.setShopCartListener(this);
        rcl_cart.setAdapter(cartAdapter);
    }
    public void showCartView(){
        cartAdapter.refreshData();
        setVisibility(VISIBLE);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_clear:
                clearCart();
                break;
        }
    }
    public void setIShopCartDialog(IShopCartDialog iShopCartDialog) {
        this.iShopCartDialog = iShopCartDialog;
    }
    @Override
    public void add(View view, int postiion, GoodsListBean.GoodsInfo goods) {
        if(null!=iShopCartDialog){
            iShopCartDialog.updateCartData();
        }
    }

    @Override
    public void remove(int position, GoodsListBean.GoodsInfo goods) {
        if(null!=iShopCartDialog){
            iShopCartDialog.updateCartData();
        }
        if(shopCart.getShoppingAccount()==0){
            setVisibility(GONE);
        }
    }

    @Override
    public void showNatureDialog(GoodsListBean.GoodsInfo goods, int position) {

    }

    @Override
    public void showTipDialog() {

    }

    @Override
    public void showVipDialog() {

    }

    public void  clearCart(){
        shopCart.clear();
        if(null!=iShopCartDialog){
            iShopCartDialog.updateCartData();
        }
        //从本地清空数据
        BaseApplication.greenDaoManager.deleteGoodsByShopId(shop_id);
        setVisibility(GONE);
    }

}

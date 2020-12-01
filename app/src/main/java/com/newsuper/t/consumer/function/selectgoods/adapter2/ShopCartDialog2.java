package com.newsuper.t.consumer.function.selectgoods.adapter2;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.function.selectgoods.adapter.DividerDecoration;
import com.newsuper.t.consumer.function.selectgoods.adapter.MyItemAnimator;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCart;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCartDialog;
import com.newsuper.t.consumer.widget.MaxHeightRecycleView;

public class ShopCartDialog2 extends Dialog implements View.OnClickListener,IShopCart {

    private ShopCart2 shopCart;
    private IShopCartDialog iShopCartDialog;
    private LinearLayout ll_root;
    private String shop_id;
    private ShopCartDetailAdapter cartAdapter;
    private boolean isEffectiveVip;//是否有效会员
    public ShopCartDialog2(Context context, ShopCart2 shopCart, String shop_id, int themeResId, boolean isEffectiveVip) {
        super(context,themeResId);
        this.shopCart = shopCart;
        this.shop_id=shop_id;
        this.isEffectiveVip=isEffectiveVip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart_detail);
        LinearLayout ll_clear=(LinearLayout)findViewById(R.id.ll_clear);
        MaxHeightRecycleView rcl_cart=(MaxHeightRecycleView)findViewById(R.id.rcl_cart);
        rcl_cart.setMaxHeight(360);
        ll_root=(LinearLayout)findViewById(R.id.ll_root);
        rcl_cart.setItemAnimator(new MyItemAnimator());
        rcl_cart.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration cartDivider=new DividerDecoration();
        cartDivider.setDividerColor(Color.parseColor("#EBEBEB"));
        rcl_cart.addItemDecoration(cartDivider);
        cartAdapter = new ShopCartDetailAdapter(getContext(),shopCart,isEffectiveVip,shop_id);
        cartAdapter.setShopCartListener(this);
        rcl_cart.setAdapter(cartAdapter);
        ll_clear.setOnClickListener(this);
    }


    @Override
    public void show() {
        super.show();
        animationShow(300);
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        animationHide(300);
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(ll_root, "translationY",1000, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(ll_root, "translationY",0,1000).setDuration(mDuration)
        );
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ShopCartDialog2.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
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
        if(null != iShopCartDialog){
            iShopCartDialog.updateCartData();
        }
        if(shopCart.getShoppingAccount()==0){
            this.dismiss();
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
        if(null != iShopCartDialog){
            iShopCartDialog.updateCartData();
        }
        //从本地清空数据
        BaseApplication.greenDaoManager.deleteGoodsByShopId(shop_id);
        this.dismiss();
    }

}

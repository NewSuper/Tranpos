package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.function.selectgoods.adapter.DividerDecoration;
import com.newsuper.t.consumer.function.selectgoods.adapter.MyItemAnimator;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopCartDetailAdapter;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCart;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCartDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowShoppingCartView extends LinearLayout implements View.OnClickListener, IShopCart {
    private Context context;
    private ShopCartViewHolder viewHolder;
    private ShopCart2 shopCart;
    private IShopCartDialog iShopCartDialog;
    private String shop_id;
    private ShopCartDetailAdapter cartAdapter;
    private boolean isBindView;
    public ShowShoppingCartView(Context context) {
        super(context);
        initView(context);
    }

    public ShowShoppingCartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ShowShoppingCartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shopping_cart, null);
        viewHolder = new ShopCartViewHolder(view);
        viewHolder.llClear.setOnClickListener(this);
        viewHolder.showCart.setOnClickListener(this);
        viewHolder.vvBlackArea.setOnClickListener(this);
        addView(view);
    }
    public void bindView(ShopCart2 shopCart, String shop_id,boolean isEffectiveVip,IShopCartDialog iShopCartDialog) {
        isBindView = true;
        this.shopCart = shopCart;
        this.shop_id = shop_id;
        this.iShopCartDialog = iShopCartDialog;
        viewHolder.rclCart.setItemAnimator(new MyItemAnimator());
        viewHolder.rclCart.setLayoutManager(new LinearLayoutManager(context));
        DividerDecoration cartDivider = new DividerDecoration();
        cartDivider.setDividerColor(Color.parseColor("#EBEBEB"));
        viewHolder.rclCart.addItemDecoration(cartDivider);
        viewHolder.rclCart.setMaxHeight(360);
        cartAdapter = new ShopCartDetailAdapter(context, shopCart, isEffectiveVip, shop_id);
        cartAdapter.setShopCartListener(this);
        viewHolder.rclCart.setAdapter(cartAdapter);
    }

    public boolean isBindView() {
        return isBindView;
    }

    public void showCartView(){
        cartAdapter.refreshData();
        viewHolder.llCartDetail.setVisibility(VISIBLE);
        viewHolder.vvBlackArea.setVisibility(VISIBLE);
        viewHolder.vvBlackArea.setIntercept(true);
    }
    public void closeCartView(){
        viewHolder.llCartDetail.setVisibility(GONE);
        viewHolder.vvBlackArea.setVisibility(INVISIBLE);
        viewHolder.vvBlackArea.setIntercept(false);
    }
    public void setOnClickListener(OnClickListener listener){
        viewHolder.tvSubmit.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_clear:
                clearCart();
                break;
            case R.id.show_cart:
                if (viewHolder.llCartDetail.getVisibility() == VISIBLE){
                    closeCartView();
                }else {
                    showCartView();
                }
                break;
        }
    }
    @Override
    public void add(View view, int postiion, GoodsListBean.GoodsInfo goods) {
        if(null != iShopCartDialog){
            iShopCartDialog.updateCartData();
        }
    }

    @Override
    public void remove(int position, GoodsListBean.GoodsInfo goods) {
        if(null != iShopCartDialog){
            iShopCartDialog.updateCartData();
        }
        if(shopCart.getShoppingAccount() == 0){
            closeCartView();
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
        closeCartView();
        //从本地清空数据
        BaseApplication.greenDaoManager.deleteGoodsByShopId(shop_id);

    }
    public void setWorkTime(boolean b){
        if (b){
            viewHolder.bottom.setVisibility(VISIBLE);
            viewHolder.tvShopTip.setVisibility(GONE);
        }else {
            viewHolder.bottom.setVisibility(GONE);
            viewHolder.tvShopTip.setVisibility(VISIBLE);
        }
    }
    public ShopCartViewHolder getViewHolder() {
        return viewHolder;
    }

    public ImageView getImgCart() {
        return viewHolder.imgCart;
    }

    public LinearLayout getBottomView() {
        return viewHolder.bottom;
    }
    public LinearLayout getCartView() {
        return viewHolder.llCart;
    }
    public TextView getTvCost() {
        return viewHolder.tvCost;
    }

    public TextView getTvCount() {
        return viewHolder.tvCount;
    }

    public TextView getTvNum() {
        return viewHolder.tvNum;
    }

    public TextView getTvTips() {
        return viewHolder.tvTips;
    }

    public ClickableFrameLayout getFlBottom() {
        return viewHolder.flBottom;
    }

    public TextView getTvShopTip() {
        return viewHolder.tvShopTip;
    }

    public TextView getTvSubmit() {
        return viewHolder.tvSubmit;
    }

    public TextView getTvManjian() {
        return viewHolder.tvManjian;
    }


    static class ShopCartViewHolder {
        @BindView(R.id.vv_black_area)
        InterceptTouchEventView vvBlackArea;
        @BindView(R.id.tv_manjian)
        TextView tvManjian;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.ll_clear)
        LinearLayout llClear;
        @BindView(R.id.rcl_cart)
        MaxHeightRecycleView rclCart;
        @BindView(R.id.ll_cart_detail)
        LinearLayout llCartDetail;
        @BindView(R.id.imgCart)
        ImageView imgCart;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.show_cart)
        RelativeLayout showCart;
        @BindView(R.id.tvCost)
        TextView tvCost;
        @BindView(R.id.tvTips)
        TextView tvTips;
        @BindView(R.id.tvSubmit)
        TextView tvSubmit;
        @BindView(R.id.ll_cart)
        LinearLayout llCart;
        @BindView(R.id.bottom)
        LinearLayout bottom;
        @BindView(R.id.tv_shop_tip)
        TextView tvShopTip;
        @BindView(R.id.fl_bottom)
        ClickableFrameLayout flBottom;
        ShopCartViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}

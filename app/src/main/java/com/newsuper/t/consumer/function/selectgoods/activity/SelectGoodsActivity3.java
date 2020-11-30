package com.newsuper.t.consumer.function.selectgoods.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.application.BaseApplication;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.CollectionBean;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.GoodsSearchBean;
import com.xunjoy.lewaimai.consumer.bean.ShopInfoBean;
import com.xunjoy.lewaimai.consumer.bean.ShopSearchBean;
import com.xunjoy.lewaimai.consumer.bean.TopBean;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.function.TopActivity3;
import com.xunjoy.lewaimai.consumer.function.login.LoginActivity;
import com.xunjoy.lewaimai.consumer.function.person.presenter.CouponPresenter;
import com.xunjoy.lewaimai.consumer.function.selectgoods.adapter.FragmentAdapter;
import com.xunjoy.lewaimai.consumer.function.selectgoods.adapter.ShopCouponAdapter;
import com.xunjoy.lewaimai.consumer.function.selectgoods.fragment.SelectBigGoodsFragment2;
import com.xunjoy.lewaimai.consumer.function.selectgoods.fragment.SelectGoodsFragment2;
import com.xunjoy.lewaimai.consumer.function.selectgoods.fragment.SelectMiddleGoodsFragment2;
import com.xunjoy.lewaimai.consumer.function.selectgoods.fragment.ShopCommentsFragment;
import com.xunjoy.lewaimai.consumer.function.selectgoods.fragment.ShopInfoFragment;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IGetCouponResponse;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IShopInfoFragmentView;
import com.xunjoy.lewaimai.consumer.function.selectgoods.presenter.GetCouponPresenter;
import com.xunjoy.lewaimai.consumer.function.selectgoods.presenter.ShopInfoFragmentPresenter;
import com.xunjoy.lewaimai.consumer.function.selectgoods.request.GetCouponRequest;
import com.xunjoy.lewaimai.consumer.function.selectgoods.request.ShopInfoRequest;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StatusBarUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.BlurBitmap;
import com.xunjoy.lewaimai.consumer.widget.ClickableFrameLayout;
import com.xunjoy.lewaimai.consumer.widget.ShopCartView;
import com.xunjoy.lewaimai.consumer.widget.ShowShoppingCartView;
import com.xunjoy.lewaimai.consumer.widget.UpMarqueeView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WGridView;
import com.xunjoy.lewaimai.consumer.widget.popupwindow.ShopCartPopupWindow;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectGoodsActivity3 extends BaseActivity implements View.OnClickListener, IShopInfoFragmentView ,IGetCouponResponse {
    @BindView(R.id.iv_bg)
    RelativeLayout ivBg;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_header_name)
    TextView tvHeaderName;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_gonggao)
    TextView tv_gonggao;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.tv_act_count)
    TextView tvActCount;
    @BindView(R.id.ll_act)
    LinearLayout llAct;
    @BindView(R.id.ll_act_tip)
    LinearLayout llActTip;
    @BindView(R.id.ll_act_info)
    LinearLayout llActInfo;
    @BindView(R.id.sl_act_info)
    NestedScrollView sl_act_info;
    @BindView(R.id.ll_act_detail)
    LinearLayout llActDetail;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_search_goods)
    LinearLayout llSearchGoods;
    @BindView(R.id.tb_shop)
    Toolbar tbShop;
    @BindView(R.id.abl)
    AppBarLayout abl;
    @BindView(R.id.tab_select_goods)
    TabLayout tabSelectGoods;
    @BindView(R.id.vp_select_goods)
    ViewPager vpSelectGoods;
    @BindView(R.id.fl_bottom)
    ClickableFrameLayout flBottom;
    @BindView(R.id.tv_order_tip)
    TextView tvOrderTip;
    @BindView(R.id.ll_order_tip)
    LinearLayout llOrderTip;
    @BindView(R.id.gv_coupon_small)
    WGridView gvCouponSmall;
    @BindView(R.id.gv_coupon_big)
    WGridView gvCouponBig;
    @BindView(R.id.tv_basePrice)
    TextView tvBasePrice;
    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.iv_item)
    ImageView ivItem;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_item)
    TextView tvItem;
    @BindView(R.id.iv_icon_collect)
    ImageView ivIconCollect;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.tv_gao)
    TextView tvGao;
    private FragmentAdapter adapter;
    private String shop_id;
    private String token;
    private String admin_id = Const.ADMIN_ID;
    private ShopInfoFragmentPresenter shopPresenter;
    private ShopInfoBean bean;
    private ShopInfoBean.ShopInfo shopInfo;//包含店铺的所有信息
    private ArrayList<GoodsListBean.GoodsInfo> searchGoodsItem;//存放搜索商品
    private SelectGoodsFragment2 selectGoodsFragment;
    private SelectMiddleGoodsFragment2 selectMiddleGoodsFragment;
    private SelectBigGoodsFragment2 selectBigGoodsFragment;
    public String from_page;
    private Intent dataIntent;
    private Uri uri;
    private String picUrl = "";
    private ShopCouponAdapter sSouponAdapter;
    private ShopCouponAdapter bCouponAdapter;
    private GetCouponPresenter couponPresenter;
    public String advertising_url;
    public String goods_id ,second_type_id,type_id,keyWord;
    private int top_status = 0;//头部状态 0：打开 1：打开一点 2：关闭
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 11:
                    openView(llActInfo);
                    break;
            }
        }
    };
    public String goods_tip;
//    public ShopCartPopupWindow shopCartPopupWindow;
    @Override
    public void initStatusBar() {
        StatusBarUtil.setStatusBarFullScreen(this);
    }

    @Override
    public void initData() {
        //获取店铺列表传递的数据
        dataIntent = getIntent();
        //接收分享的数据
        uri = dataIntent.getData();
        if (null != uri) {
            //从分享进来
            from_page = "share";
            SharedPreferencesUtil.saveAdminId(Const.ADMIN_ID);
        } else {
            from_page = dataIntent.getStringExtra("from_page");
        }

        token = SharedPreferencesUtil.getToken();
        adapter = new FragmentAdapter(getSupportFragmentManager());
        shopPresenter = new ShopInfoFragmentPresenter(this);
    }

    public String getShopId() {
        return shop_id;
    }

    @Override
    public void showSearchDataToView(GoodsSearchBean bean) {
        if (null!=bean) {
            searchGoodsItem.addAll(bean.data.foodlist);
        }
        LogUtil.log("searchGoodsType","3、获取搜索商品数据searchGoodsItem== "+new Gson().toJson(searchGoodsItem));
        HashMap<String, String> request = ShopInfoRequest.shopInfoRequest(token, admin_id, shop_id);
        shopPresenter.loadData(UrlConst.SHOP_INFO, request);
    }

    @Override
    public void searchDataError() {
        HashMap<String, String> request = ShopInfoRequest.shopInfoRequest(token, admin_id, shop_id);
        shopPresenter.loadData(UrlConst.SHOP_INFO, request);
    }

    @Override
    public void showDataToVIew(ShopInfoBean bean) {
        if (null != bean) {
            this.bean = bean;
            shopInfo = bean.data.shop;
            //店铺信息加载之后才能点击搜索店铺
            llSearchGoods.setOnClickListener(this);
            if (TextUtils.isEmpty(picUrl)) {
                if (shopInfo.shopimage.size() > 0) {
                    picUrl = shopInfo.shopimage.get(0);
                    if (picUrl != null && !picUrl.startsWith("http")) {
                        picUrl = RetrofitManager.BASE_IMG_URL + picUrl;
                    }
                }
            }
            //处理店铺收藏
            if ("1".equals(bean.data.is_collect)) {
                ivIconCollect.setImageResource(R.mipmap.collect_s_2x);
            } else {
                ivIconCollect.setImageResource(R.mipmap.collect_n_2x);
            }
            switch (from_page) {
                case "topPage":
                    initShopInfo();
                    break;
                case "searchShop":
                    /*goods_id = getIntent().getStringExtra("goods_id");
                    type_id = getIntent().getStringExtra("type_id");
                    second_type_id = getIntent().getStringExtra("second_type_id");*/
                    initShopInfo();
                    break;
                case "collect_shop":
                    initShopInfo();
                    break;
                case "WShopList":
                    initShopInfo();
                    break;
                case "WShopSelect":
                    initShopInfo();
                    break;
                case "WGoShop":
                    loadShopImage(picUrl, ivHeader);
                    initShopInfo();
                    break;
                case "cartList":
                    tvHeaderName.setText(getIntent().getStringExtra("shop_name"));
                  /*  picUrl = getIntent().getStringExtra("picUrl");
                    if (picUrl != null && !picUrl.startsWith("http")){
                        picUrl = RetrofitManager.BASE_IMG_URL_MEDIUM + picUrl;
                    }*/
                    loadShopImage(picUrl, ivHeader);
                    initShopInfo();
                    break;
                case "Wlink":
                    tvHeaderName.setText(shopInfo.shopname);
                    loadShopImage(picUrl, ivHeader);
                    initShopInfo();
                    break;
                case "WlinkComment":
                    tvHeaderName.setText(shopInfo.shopname);
                    loadShopImage(picUrl, ivHeader);
                    initShopInfo();
                    selectFragment(1);
                    break;
                case "goodsDetail":
                    tvHeaderName.setText(shopInfo.shopname);
                    loadShopImage(picUrl, ivHeader);
                    initShopInfo();
                    break;
                case "order_list":
                    tvHeaderName.setText(shopInfo.shopname);
                    loadShopImage(picUrl, ivHeader);
                    initShopInfo();
                    break;
                case "share":
                    tvHeaderName.setText(shopInfo.shopname);
                    loadShopImage(picUrl, ivHeader);
                    initShopInfo();
                    break;
                case "order_again":
                    tvHeaderName.setText(shopInfo.shopname);
                    loadShopImage(picUrl, ivHeader);
                    goods_tip = getIntent().getStringExtra("goods_tip");
                    initShopInfo();
                    break;

            }
        }else {

        }

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_select_goods3);
        ButterKnife.bind(this);

        tbShop.setBackgroundColor(Color.argb(0, 255, 255, 255));

        abl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    top_status = 0;
                    ivBack.setImageResource(R.mipmap.icon_back);
                    ivShare.setImageResource(R.mipmap.icon_shop_share);
                    llSearchGoods.setBackgroundResource(R.drawable.shape_search_store);
                    tbShop.setBackgroundColor(Color.argb(0, 255, 255, 255));
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    top_status = 2;
                    tbShop.setBackgroundColor(Color.argb(255, 255, 255, 255));
                    ivBack.setImageResource(R.mipmap.icon_back_gray);
                    ivShare.setImageResource(R.mipmap.icon_shop_share_gray);
                    llSearchGoods.setBackgroundResource(R.drawable.shape_search_store_gray);
                } else {
                    top_status = 1;
                    tbShop.setBackgroundColor(Color.argb((int) (255 * (Math.abs(verticalOffset) * 1.0 / (appBarLayout.getTotalScrollRange()))), 255, 255, 255));
                }
            }
        });
        setSupportActionBar(tbShop);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
        }
        tbShop.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
//        shopCartPopupWindow = new ShopCartPopupWindow(this,flBottom);
        if (null != from_page) {
            switch (from_page) {
                case "topPage":
                    TopBean.ShopList shopList = (TopBean.ShopList) dataIntent.getSerializableExtra("shop_info");
                    shop_id = shopList.id;
                    tvHeaderName.setText(shopList.shopname);
                    if (!TextUtils.isEmpty(shopList.shopimage)) {
                        if (shopList.shopimage.startsWith("http")) {
                            picUrl = shopList.shopimage + "!max200";
                        } else {
                            picUrl = RetrofitManager.BASE_IMG_URL + shopList.shopimage + "!max200";
                        }
                    }
                    break;
                case "searchShop":
                    goods_id = getIntent().getStringExtra("goods_id");
                    ShopSearchBean.ShopList searchShop = (ShopSearchBean.ShopList) dataIntent.getSerializableExtra("shop_info");
                    shop_id = searchShop.id;
                    tvHeaderName.setText(searchShop.shopname);
                    if (!TextUtils.isEmpty(searchShop.shopimage)) {
                        if (searchShop.shopimage.startsWith("http")) {
                            picUrl = searchShop.shopimage + "!max200";
                        } else {
                            picUrl = RetrofitManager.BASE_IMG_URL + searchShop.shopimage + "!max200";
                        }
                    }
                    if (!StringUtils.isEmpty(shop_id))
                        keyWord = getIntent().getStringExtra("keyWord");
                    if (!StringUtils.isEmpty(keyWord)) {
                        LogUtil.log("searchGoodsType","2、接收keyword== "+keyWord+"; goods_id== "+goods_id);
                        searchGoodsItem = new ArrayList<>();
                        HashMap<String, String> request = ShopInfoRequest.goodsSearchRequest(token, admin_id, shop_id, keyWord,"1","3","1");
                        shopPresenter.searchGoodsData(UrlConst.GOODS_SEARCH,request);
                    }
                    break;
                case "collect_shop":
                    CollectionBean.ShopList collectShop = (CollectionBean.ShopList) dataIntent.getSerializableExtra("shop_info");
                    shop_id = collectShop.id;
                    tvHeaderName.setText(collectShop.shopname);
                    if (!TextUtils.isEmpty(collectShop.shopimage)) {
                        if (collectShop.shopimage.startsWith("http")) {
                            picUrl = collectShop.shopimage + "!max200";
                        } else {
                            picUrl = RetrofitManager.BASE_IMG_URL + collectShop.shopimage + "!max200";
                        }
                    }
                    break;
                case "WShopList":
                    TopBean.ShopList wshopList = (TopBean.ShopList) dataIntent.getSerializableExtra("shop_info");
                    shop_id = wshopList.id;
                    tvHeaderName.setText(wshopList.shopname);
                    if (!TextUtils.isEmpty(wshopList.shopimage)) {
                        if (wshopList.shopimage.startsWith("http")) {
                            picUrl = wshopList.shopimage + "!max200";
                        } else {
                            picUrl = RetrofitManager.BASE_IMG_URL + wshopList.shopimage + "!max200";
                        }
                    }

                    break;
                case "WShopSelect":
                    WTopBean.ShopSelect wshopSelect = (WTopBean.ShopSelect) dataIntent.getSerializableExtra("shop_info");
                    shop_id = wshopSelect.shop_id;
                    tvHeaderName.setText(wshopSelect.shop_name);
                    if (!TextUtils.isEmpty(wshopSelect.image)) {
                        if (wshopSelect.image.startsWith("http")) {
                            picUrl = wshopSelect.image + "!max200";
                        } else {
                            picUrl = RetrofitManager.BASE_IMG_URL + wshopSelect.image + "!max200";
                        }
                    }
                    break;
                case "WGoShop":
                    WTopBean.ShopComeIn wGoShop = (WTopBean.ShopComeIn) dataIntent.getSerializableExtra("shop_info");
                    shop_id = wGoShop.shop_id;
                    tvHeaderName.setText(wGoShop.shopname);
                    break;
                case "cartList":
                    shop_id = dataIntent.getStringExtra("shop_id");
                    tvHeaderName.setText(dataIntent.getStringExtra("shop_name"));
                    break;
                case "Wlink":
                    shop_id = dataIntent.getStringExtra("shop_id");
                    break;
                case "WlinkComment":
                    shop_id = dataIntent.getStringExtra("shop_id");
                    break;
                case "goodsDetail":
                    shop_id = dataIntent.getStringExtra("shop_id");
                    break;
                case "order_list":
                    //从订单列表跳转
                    shop_id = dataIntent.getStringExtra("shop_id");
                    break;
                case "share":
                    if (null != uri) {
                        shop_id = uri.getQueryParameter("shop_id");
                    } else {
                        shop_id = dataIntent.getStringExtra("shop_id");
                    }
                    break;
                case "order_again":
                    shop_id = dataIntent.getStringExtra("shop_id");
                    break;
            }
        }
        loadShopImg();
        ivBack.setOnClickListener(this);
        if (StringUtils.isEmpty(keyWord)) {
            //请求商家信息
            if (!TextUtils.isEmpty(shop_id)) {
                HashMap<String, String> request = ShopInfoRequest.shopInfoRequest(token, admin_id, shop_id);
                shopPresenter.loadData(UrlConst.SHOP_INFO, request);
            } else {
                UIUtils.showToast("该店铺Id为空!");
            }
        }
    }

    //加载店铺图片
    private void loadShopImg(){
        if (StringUtils.isEmpty(picUrl)){
            ivHeader.setImageResource(R.mipmap.store_logo_default);
        }else {
            UIUtils.glideRequestBuilder(this, picUrl, R.mipmap.store_logo_default).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    Bitmap blurBg = BlurBitmap.blur(SelectGoodsActivity3.this, resource);
                    ivBg.setBackground((Drawable) (new BitmapDrawable(blurBg)));
                    ivBg.getBackground().setAlpha(180);
                    return false;
                }
            }).into(ivHeader);
        }

    }
    private ArrayList<GoodsListBean.Coupon> coupons;
    private String getCoupon_id;
    private void initShopInfo() {
        if ("1".equals(shopInfo.switch_advertising) && !StringUtils.isEmpty(shopInfo.image_advertising)){
            advertising_url = shopInfo.image_advertising;
        }
        if (shopInfo.exclusivecoupon.list != null && shopInfo.exclusivecoupon.list.size() > 0) {
            gvCouponSmall.setVisibility(View.VISIBLE);
            gvCouponBig.setVisibility(View.VISIBLE);
            couponPresenter = new GetCouponPresenter(this);
            coupons = shopInfo.exclusivecoupon.list;
            sSouponAdapter = new ShopCouponAdapter(this, 0,coupons);
            int viewHeight = llActTip.getMeasuredWidth();
            if (coupons.size() == 1){
                gvCouponSmall.getLayoutParams().width = viewHeight / 3;
            }else if (coupons.size() == 2){
                gvCouponSmall.getLayoutParams().width = viewHeight / 2;
            }
            gvCouponSmall.setNumColumns(coupons.size());
            gvCouponSmall.setAdapter(sSouponAdapter);
            bCouponAdapter = new ShopCouponAdapter(this, 1,coupons);
            gvCouponBig.setAdapter(bCouponAdapter);
            sSouponAdapter.setItemClickListener(new ShopCouponAdapter.CustomItemClickListener() {
                @Override
                public void onItemClick(String id) {
                    LogUtil.log("ShopCouponAdapter","small   id = "+id);
                    getCoupon_id = id;
                    getCoupon(id);
                }
            });
            bCouponAdapter.setItemClickListener(new ShopCouponAdapter.CustomItemClickListener() {
                @Override
                public void onItemClick(String id) {
                    LogUtil.log("ShopCouponAdapter","big   id = "+id);
                    getCoupon_id = id;
                    getCoupon(id);
                }
            });
        }else {
            gvCouponSmall.setVisibility(View.GONE);
            gvCouponBig.setVisibility(View.GONE);

        }
        String order_num = bean.data.shop.order_num;
        String expected_delivery_times = bean.data.shop.expected_delivery_times;

        if ("1".equals(bean.data.is_show_sales_volume) && !StringUtils.isEmpty(order_num)){
            tv_sale.setText("已售" + order_num+"单 ");
            if ("1".equals(bean.data.is_show_expected_delivery) && !StringUtils.isEmpty(expected_delivery_times)){
                tv_sale.setText("已售" + order_num+"单  配送约" + bean.data.shop.expected_delivery_times+"分钟");
            }
        }else {
            if ("1".equals(bean.data.is_show_expected_delivery) && !StringUtils.isEmpty(expected_delivery_times)){
                tv_sale.setText("配送约" + bean.data.shop.expected_delivery_times+"分钟");
            }else {
                tv_sale.setVisibility(View.INVISIBLE);
            }
        }
        tvBasePrice.setText("起送￥" + FormatUtil.numFormat(bean.data.basicprice));
        tvDeliveryFee.setText(Const.STRING_DIVER +"配送 ￥" + FormatUtil.numFormat(bean.data.delivery_fee));
        boolean isFirst = true;
        if (null != shopInfo.activity_info && !TextUtils.isEmpty(shopInfo.activity_info.first_order)) {
            View view = View.inflate(this, R.layout.item_upmarqueeview2, null);
            view.findViewById(R.id.iv_item).setBackgroundResource(R.mipmap.store_first_order);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_item.setText(shopInfo.activity_info.first_order);
            llActDetail.addView(view);
            isFirst = false;
            ivItem.setBackgroundResource(R.mipmap.store_first_order);
            tvItem.setText(shopInfo.activity_info.first_order);

        }
        if (null != shopInfo.activity_info && !TextUtils.isEmpty(shopInfo.activity_info.consume)) {
            View view = View.inflate(this, R.layout.item_upmarqueeview2, null);
            view.findViewById(R.id.iv_item).setBackgroundResource(R.mipmap.store_jian);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_item.setText(shopInfo.activity_info.consume);
            llActDetail.addView(view);

            if (isFirst){
                ivItem.setBackgroundResource(R.mipmap.store_jian);
                tvItem.setText(shopInfo.activity_info.consume);
            }
            isFirst = false;

        }
        //3.1.1需求新客立减
        if (null != shopInfo.activity_info && !TextUtils.isEmpty(shopInfo.activity_info.shop_first_discount)) {
            View view = View.inflate(this, R.layout.item_upmarqueeview2, null);
            view.findViewById(R.id.iv_item).setBackgroundResource(R.mipmap.shop_first_order);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_item.setText(shopInfo.activity_info.shop_first_discount);
            llActDetail.addView(view);

            if (isFirst){
                ivItem.setBackgroundResource(R.mipmap.shop_first_order);
                tvItem.setText(shopInfo.activity_info.shop_first_discount);
            }
            isFirst = false;
        }
        if (null != shopInfo.activity_info && !TextUtils.isEmpty(shopInfo.activity_info.coupon)) {
            View view = View.inflate(this, R.layout.item_upmarqueeview2, null);
            view.findViewById(R.id.iv_item).setBackgroundResource(R.mipmap.store_coupon);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_item.setText(shopInfo.activity_info.coupon);
            llActDetail.addView(view);

            if (isFirst){
                ivItem.setBackgroundResource(R.mipmap.store_coupon);
                tvItem.setText(shopInfo.activity_info.coupon);
            }
            isFirst = false;
        }
        //2.8需求
        if (null != shopInfo.activity_info && !TextUtils.isEmpty(shopInfo.activity_info.shop_discount)) {
            View view = View.inflate(this, R.layout.item_upmarqueeview2, null);
            view.findViewById(R.id.iv_item).setBackgroundResource(R.mipmap.store_discount);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_item.setText(shopInfo.activity_info.shop_discount);
            llActDetail.addView(view);

            if (isFirst){
                ivItem.setBackgroundResource(R.mipmap.store_discount);
                tvItem.setText(shopInfo.activity_info.shop_discount);
            }
            isFirst = false;
        }

        if (null != shopInfo.activity_info && !TextUtils.isEmpty(shopInfo.activity_info.delivery_fee)) {
            View view = View.inflate(this, R.layout.item_upmarqueeview2, null);
            view.findViewById(R.id.iv_item).setBackgroundResource(R.mipmap.mian);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_item.setText(shopInfo.activity_info.delivery_fee);
            llActDetail.addView(view);

            if (isFirst){
                ivItem.setBackgroundResource(R.mipmap.mian);
                tvItem.setText(shopInfo.activity_info.delivery_fee);
            }
            isFirst = false;

        }
        if (null != shopInfo.activity_info && !TextUtils.isEmpty(shopInfo.activity_info.member)) {
            View view = View.inflate(this, R.layout.item_upmarqueeview2, null);
            view.findViewById(R.id.iv_item).setBackgroundResource(R.mipmap.mian);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_item.setText(shopInfo.activity_info.member);
            llActDetail.addView(view);

            if (isFirst){
                ivItem.setBackgroundResource(R.mipmap.mian);
                tvItem.setText(shopInfo.activity_info.member);
            }
            isFirst = false;
        }

        if ("1".equals(shopInfo.is_manzeng) && null != shopInfo.activity_info && !TextUtils.isEmpty(shopInfo.activity_info.manzeng)) {
            View view = View.inflate(this, R.layout.item_upmarqueeview2, null);
            view.findViewById(R.id.iv_item).setBackgroundResource(R.mipmap.icon_zeng_2x);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_item.setText(shopInfo.activity_info.manzeng);
            llActDetail.addView(view);
            if (isFirst){
                ivItem.setBackgroundResource(R.mipmap.icon_zeng_2x);
                tvItem.setText(shopInfo.activity_info.manzeng);
            }
            isFirst = false;
        }

        if (!TextUtils.isEmpty(shopInfo.shop_notice)) {
            tvNotice.setText("公告：" + shopInfo.shop_notice);
            tv_gonggao.setText("公告：" + shopInfo.shop_notice);
            tvGao.setText("公告：" + shopInfo.shop_notice);
        } else {
            tvNotice.setText("公告：欢迎光临，用餐高峰期请提前下单，谢谢。");
            tv_gonggao.setText("公告：欢迎光临，用餐高峰期请提前下单，谢谢。");
            tvGao.setText("公告：欢迎光临，用餐高峰期请提前下单，谢谢。");
        }
        if (!isFirst) {
            llAct.setVisibility(View.VISIBLE);
            llActDetail.setVisibility(View.VISIBLE);
            tvActCount.setText((llActDetail.getChildCount() - 1) + "个活动");
        } else {
            llAct.setVisibility(View.GONE);
            llActDetail.setVisibility(View.GONE);
        }
//        upMarqueeView.setViews(views);

        adapter = new FragmentAdapter(getSupportFragmentManager());
        switch (shopInfo.showtype) {
            case "2":
                //小图
                LogUtil.log("initShopInfo", "小图");
                selectGoodsFragment = new SelectGoodsFragment2();
                adapter.addFragment(selectGoodsFragment);
                break;
            case "3":
                //中图
                LogUtil.log("initShopInfo", "中图");
                selectMiddleGoodsFragment = new SelectMiddleGoodsFragment2();
                adapter.addFragment(selectMiddleGoodsFragment);
                break;
            case "4":
                //大图
                LogUtil.log("initShopInfo", "大图");
                selectBigGoodsFragment = new SelectBigGoodsFragment2();
                adapter.addFragment(selectBigGoodsFragment);
                break;
            default:
                adapter.addFragment(new Fragment());
                break;
        }
        adapter.addFragment(new ShopCommentsFragment());
        adapter.addFragment(new ShopInfoFragment());
        vpSelectGoods.setOffscreenPageLimit(2);
        vpSelectGoods.setAdapter(adapter);
        vpSelectGoods.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    flBottom.setVisibility(View.VISIBLE);
                } else {
                    flBottom.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //必须在setAdapter()之后再绑定TabLayout;
        tabSelectGoods.setupWithViewPager(vpSelectGoods);
        tabSelectGoods.setTabMode(TabLayout.MODE_FIXED);
//        setIndicator(this, tabSelectGoods, 20, 20);
        tabSelectGoods.setTabIndicatorFullWidth(false);
    }


    public void selectFragment(int position) {
        vpSelectGoods.setCurrentItem(position);
    }


    public ShopInfoBean.ShopInfo getShopInfo() {
        return shopInfo;
    }

    public ArrayList<GoodsListBean.GoodsInfo> getSearchGoodsItem(){
        return searchGoodsItem;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getGoods_id(){
        return goods_id;
    }

    public String getShopLogo() {
        return picUrl;
    }

    public ShopInfoBean getShopInfoBean() {
        return bean;
    }

    //获取起送价
    public String getBasicPrice() {
        return bean.data.shop.basicprice;
    }

    @OnClick({R.id.ll_close, R.id.ll_act_tip, R.id.iv_back, R.id.ll_search_goods})
    @Override
    public void onClick(View v) {
        LogUtil.log("DropDownAnim", " onClick ==  ");
        switch (v.getId()) {
            case R.id.ll_act_tip:
                LogUtil.log("DropDownAnim", " ll_act_tip ==  ");
                if (top_status != 0){
                    abl.setExpanded(true);
                    mHandle.sendEmptyMessageDelayed(11,500);
                    return;
                }else {
                    openView(llActInfo);
                }
                break;
            case R.id.ll_close:
                LogUtil.log("DropDownAnim", " ll_close ==  ");
                closeView(llActInfo);
                break;
            case R.id.iv_back:
                LogUtil.log("DropDownAnim", " iv_back ==  ");
                if (from_page.equals("cartList")) {
                    setResult(RESULT_OK);
                }
                if (from_page.equals("share")) {
                    startActivity(new Intent(this, TopActivity3.class));
                }
                this.finish();
                break;
            case R.id.ll_search_goods:
                Intent intent = new Intent(this, GoodsSearchActivity2.class);
                intent.putExtra("shop_info", shopInfo);
                intent.putExtra("shop_status", shopInfo.worktime);
                intent.putExtra("shopname", shopInfo.shopname);
                intent.putExtra("basicPrice", bean.data.basicprice);
                intent.putExtra("open_promotion", bean.data.shop.open_promotion);
                intent.putExtra("promotion", bean.data.shop.promotion_rule);
                intent.putExtra("is_only_promotion", bean.data.shop.is_only_promotion);
                switch (shopInfo.showtype) {
                    case "2":
                        //小图
                        LogUtil.log("initShopInfo", "小图");
                        intent.putExtra("shop_cart", selectGoodsFragment.getShopCart());
                        intent.putExtra("packageList", selectGoodsFragment.getPackageItems());
                        break;
                    case "3":
                        //中图
                        LogUtil.log("initShopInfo", "中图");
                        intent.putExtra("shop_cart", selectMiddleGoodsFragment.getShopCart());
                        intent.putExtra("packageList", selectMiddleGoodsFragment.getPackageItems());
                        break;
                    case "4":
                        //大图
                        LogUtil.log("initShopInfo", "大图");
                        intent.putExtra("shop_cart", selectBigGoodsFragment.getShopCart());
                        intent.putExtra("packageList", selectBigGoodsFragment.getPackageItems());
                        break;
                }
                startActivity(intent);
                break;
        }
    }


    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        UIUtils.showToast(s);
        if (!StringUtils.isEmpty(s) && s.contains("店铺") && s.contains("删除")){
            showGoodsTipDialog(s);
            BaseApplication.greenDaoManager.deleteGoodsByShopId(shop_id);
        }
    }
    private void showGoodsTipDialog(String title){
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_shop_goods_tip, null);
        final Dialog shopTipDialog = new Dialog(this, R.style.CenterDialogTheme2);
        TextView tv_tip_title = (TextView) dialogView.findViewById(R.id.tv_tip_title);
        tv_tip_title.setText(title);
        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != shopTipDialog) {
                    shopTipDialog.cancel();
                }
                finish();
            }
        });
        shopTipDialog.setContentView(dialogView);
        shopTipDialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* if (shopCartPopupWindow != null){
            shopCartPopupWindow.dismiss();
            shopCartPopupWindow = null;
        }*/
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (from_page.equals("share")) {
                startActivity(new Intent(this, TopActivity3.class));
            }
            if ("cartList".equals(from_page)) {
                setResult(RESULT_OK);
            }
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
       /* if (shopCartPopupWindow.isShowing()){
            shopCartPopupWindow.dismiss();
            return;
        }*/
        if ("cartList".equals(from_page)) {
            setResult(RESULT_OK);
        }
        if ("share".equals(from_page)) {
            startActivity(new Intent(this, TopActivity3.class));
        }
        this.finish();
    }

    public void animationTip(String tip) {
        llOrderTip.setVisibility(View.VISIBLE);
        tvOrderTip.setText(tip);
        moveAnimator();

    }

    private void moveAnimator() {
        float y = UIUtils.dip2px(50);
        ObjectAnimator animator = ObjectAnimator.ofFloat(llOrderTip, "translationY", y);
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                llOrderTip.clearAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveBackAnimator();
                    }
                }, 5000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void moveBackAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(llOrderTip, "translationY", 0);
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                llOrderTip.clearAnimation();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void loadShopImage(String url, ImageView imageView) {
        UIUtils.glideRequestBuilder(getApplicationContext(), url, R.mipmap.store_logo_default).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                Bitmap blurBg = BlurBitmap.blur(SelectGoodsActivity3.this, resource);
                ivBg.setBackground((Drawable) (new BitmapDrawable(blurBg)));
                ivBg.getBackground().setAlpha(180);
                return false;
            }
        }).into(imageView);

    }

    //设置tablayout下面指示条的宽度
    public void setIndicator(Context context, TabLayout tab, int leftDip, int rightDip) {
        Class<?> tabLayout = tab.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (null != tabStrip) {
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = null;
            try {
                ll_tab = (LinearLayout) tabStrip.get(tab);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        }

    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    int mContentHeight ;
    private void openView(View mContentView) {
        //topview 完全打开才能展开
        if (top_status != 0 ){
            return;
        }
        if (mContentHeight ==0){
            LogUtil.log("DropDownAnim", " llshop  ==  " + llShop.getMeasuredHeight());
            LogUtil.log("DropDownAnim", " llActTip  ==  " + llActTip.getTop());
//            mContentHeight = UIUtils.getWindowHeight() - llShop.getMeasuredHeight() + llActTip.getMeasuredHeight() - UIUtils.dip2px(50);
            mContentHeight = (int)(UIUtils.getWindowHeight() * 0.6);

        }
        mContentView.clearAnimation();
        LogUtil.log("DropDownAnim", " openView ==  " + mContentHeight);
        DropDownAnim animationDown = null;
        if (animationDown == null) {
            animationDown = new DropDownAnim(mContentView,
                    mContentHeight, true);
            animationDown.setDuration(500); // SUPPRESS CHECKSTYLE
        }
        llActTip.startAnimation(animationDown);
        LogUtil.log("DropDownAnim", " openView == animationDown start");
    }

    private void closeView(View mContentView) {

        LogUtil.log("DropDownAnim", " closeView ==  ");
        mContentView.clearAnimation();
        DropDownAnim animationDown = null;
        if (animationDown == null) {
            animationDown = new DropDownAnim(mContentView,
                    mContentHeight, false);
            animationDown.setDuration(500); // SUPPRESS CHECKSTYLE
        }
        llActTip.startAnimation(animationDown);
    }


    /**
     * 领取优惠券接口
     */
    public void getCoupon(String id) {
        token = SharedPreferencesUtil.getToken();
        if (TextUtils.isEmpty(token)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        HashMap<String, String> params = GetCouponRequest.getCoupon(admin_id, token, shopInfo.id, id);
        couponPresenter.loadData(UrlConst.GET_COUPON, params);
    }
    @Override
    public void getCouponSuccess() {
        for (GoodsListBean.Coupon coupon : coupons){
            if (coupon.id.equals(getCoupon_id)){
                coupon.got = "1";
                break;
            }
        }
        sSouponAdapter.notifyDataSetChanged();
        bCouponAdapter.notifyDataSetChanged();
    }

    @Override
    public void getFail() {
    }


    class DropDownAnim extends Animation {
        /**
         * 目标的高度
         */
        private int targetHeight;
        /**
         * 目标view
         */
        private View view;
        /**
         * 是否向下展开
         */
        private boolean down;

        /**
         * 构造方法
         *
         * @param targetview 需要被展现的view
         * @param viewHeight 目的高
         * @param isDown     true:向下展开，false:收起
         */
        public DropDownAnim(View targetview, int viewHeight, boolean isDown) {
            this.view = targetview;
            this.targetHeight = viewHeight;
            this.down = isDown;
        }

        //down的时候，interpolatedTime从0增长到1，这样newHeight也从0增长到targetHeight
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight;
            if (down) {
                newHeight = (int) (targetHeight * interpolatedTime);
                llActTip.setAlpha(1 - interpolatedTime);
                if (vpSelectGoods.getCurrentItem() == 0){
                    flBottom.setAlpha(1 - interpolatedTime);
                }
                llActInfo.setAlpha(interpolatedTime);
                if (interpolatedTime > 0.8) {
                    if (vpSelectGoods.getCurrentItem() == 0){
                        flBottom.setVisibility(View.GONE);
                    }
                    llShop.setBackgroundColor(ContextCompat.getColor(SelectGoodsActivity3.this,R.color.text_color_66));
                }
                if (interpolatedTime > 0.2) {
                    llActTip.setVisibility(View.GONE);
                }
                if (interpolatedTime > 0.99){
                    llShop.getLayoutParams().height = UIUtils.getWindowHeight();
                    llShop.requestLayout();
                }

            } else {
                newHeight = (int) (targetHeight * (1 - interpolatedTime));
                llActTip.setAlpha(interpolatedTime);
                if (vpSelectGoods.getCurrentItem() == 0){
                    flBottom.setAlpha(interpolatedTime);
                }
                llActInfo.setAlpha(1 - interpolatedTime);
                if (interpolatedTime > 0.8) {
                    if (vpSelectGoods.getCurrentItem() == 0){
                        flBottom.setVisibility(View.VISIBLE);
                    }
                    llShop.setBackgroundColor(ContextCompat.getColor(SelectGoodsActivity3.this,R.color.white));
                }
                if (interpolatedTime > 0.8) {
                    llActTip.setVisibility(View.VISIBLE);
                }
                if (interpolatedTime < 0.1){
                    llShop.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    llShop.requestLayout();
                }
            }
            LogUtil.log("DropDownAnim", " openView == applyTransformation newHeight == " + newHeight + "  interpolatedTime = " + interpolatedTime);
            view.getLayoutParams().height = newHeight;
            view.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth,
                               int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
    float mY;
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                mY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (llActInfo.getMeasuredHeight() > 20) {
                    int distance = (int)((mY - ev.getY()) * 0.5);
                    sl_act_info.fling(distance);//添加上这句滑动才有效
                    sl_act_info.smoothScrollBy(0,distance);
                    return false;
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}

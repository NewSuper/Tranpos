package com.newsuper.t.consumer.function.selectgoods.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.GoodsSearchBean;
import com.newsuper.t.consumer.bean.ManJian;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.selectgoods.adapter.DividerDecoration;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopGoodsSearchAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopNatureGoodsAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopCartDialog2;
import com.newsuper.t.consumer.function.selectgoods.inter.AnimationListener;
import com.newsuper.t.consumer.function.selectgoods.inter.ICheckGoodsMustView;
import com.newsuper.t.consumer.function.selectgoods.inter.IDeleteNatureGoods;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsSearchView;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCart;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCartDialog;
import com.newsuper.t.consumer.function.selectgoods.inter.IShowLimitTime;
import com.newsuper.t.consumer.function.selectgoods.presenter.CheckGoodsMustPresenter;
import com.newsuper.t.consumer.function.selectgoods.presenter.GoodsSearchPresenter;
import com.newsuper.t.consumer.function.selectgoods.request.IsCollectRequest;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.AnimatorView;
import com.newsuper.t.consumer.widget.CenterPopView;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.TimePicker.CenterDialogView;
import com.newsuper.t.consumer.widget.spinerwidget.LimitCountDownTimer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsSearchActivity2 extends BaseActivity implements IGoodsSearchView, View.OnClickListener, ICheckGoodsMustView
        , IShopCart, IGoodsToDetailPage, AnimationListener, IShopCartDialog, IShowLimitTime, IDeleteNatureGoods {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.recyclerview_goods)
    RecyclerView recyclerviewGoods;
    @BindView(R.id.imgCart)
    ImageView imgCart;
    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.tvCost)
    TextView tvCost;
    @BindView(R.id.tvTips)
    TextView tvTips;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.btn_load_again)
    Button btnLoadAgain;
    @BindView(R.id.ll_load_fail)
    LinearLayout llLoadFail;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.show_cart)
    RelativeLayout showCart;
    @BindView(R.id.tv_manjian)
    TextView tvManjian;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    private GoodsSearchPresenter presenter;
    private CheckGoodsMustPresenter checkPresenter;
    private String shop_id = "";
    private ShopGoodsSearchAdapter goodsAdapter;
    private ShopCart2 shopCart;
    //    private SVProgressHUD svProgressHUD;
    private ArrayList<GoodsListBean.GoodsInfo> goodsInfos;
    private DecimalFormat df;
    private String basicPrice;
    private CenterPopView centerPopView;
    private String shopname;
    private String token;
    private String shop_status;
    private boolean isFirstInit;//表示第一次进入该页面
    private boolean isFreshSearchData = true;//是否刷新搜索商品数据
    private int page;
    private boolean isLoadMore;
    private boolean isLoading;
    private LoadingDialog2 loadingDialog;
    ArrayList<GoodsListBean.GoodsInfo> packageList;
    private ShopInfoBean.ShopInfo shopInfo;//包含店铺的所有信息
    private boolean isEffectiveVip;
    private boolean isShopMember;//是否有效会员
    private boolean isFreezenMember;//是否冻结会员
    private final int LIMIT_LOGIN = 11;//点击限购跳转登陆的
    private final int VIP_LOGIN = 12;//点击会员跳转登陆的
    private String is_only_promotion;
    private String open_promotion;
    private ArrayList<ManJian> promotion_rule;
    private GoodsListBean.GoodsInfo limitGoods;
    private Handler mHandler = new Handler();
//    private ShopCartPopupWindow shopCartPopupWindow;
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            //刷新订单状态
            showCountDown(limitGoods,tv_tip,tvTipTimeHour,tvTipTimeMiniute,tvTipTimeSecond);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
//        svProgressHUD = new SVProgressHUD(this);
        presenter = new GoodsSearchPresenter(this);
        checkPresenter = new CheckGoodsMustPresenter(this);
        df = new DecimalFormat("#0.00");
        shopInfo = (ShopInfoBean.ShopInfo) getIntent().getSerializableExtra("shop_info");
        shop_id = shopInfo.id;
        shopname = getIntent().getStringExtra("shopname");
        basicPrice = getIntent().getStringExtra("basicPrice");
        shopCart = (ShopCart2) getIntent().getSerializableExtra("shop_cart");
        shop_status = getIntent().getStringExtra("shop_status");
        is_only_promotion = getIntent().getStringExtra("is_only_promotion");
        open_promotion = getIntent().getStringExtra("open_promotion");
        promotion_rule = (ArrayList<ManJian>) getIntent().getSerializableExtra("promotion");
        packageList = (ArrayList<GoodsListBean.GoodsInfo>) getIntent().getSerializableExtra("packageList");
        isFirstInit = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstInit) {
            isFirstInit = false;
            if (null != shopCart) {
                showCartData();
            }
            BaseApplication.getPreferences().edit().putBoolean("isFreshSearchData", false).commit();
        } else {
            isFreshSearchData = BaseApplication.getPreferences().getBoolean("isFreshSearchData", false);
            if (isFreshSearchData) {
                isFreshSearchData = false;
                BaseApplication.getPreferences().edit().putBoolean("isFreshSearchData", false).commit();
                //请求选购商品
                page = 1;
                String searchGoods = edtSearch.getText().toString();
                if (!TextUtils.isEmpty(searchGoods)) {
                    presenter.loadGoods(SharedPreferencesUtil.getToken(), Const.ADMIN_ID, shop_id, searchGoods, page + "");
                }
            } else {
                //只刷新购物车数据
                shopCart.clear();
                queryCartGoods();
                updateCartData();
            }
        }
    }

    //查询本地数据
    private void queryCartGoods() {
        ArrayList<GoodsListBean.GoodsInfo> cartList = BaseApplication.greenDaoManager.getAllGoods(shop_id);
        if (null != cartList && cartList.size() > 0) {
            shopCart.addGoodsListFromDB(cartList);
        }
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_goods_search);
        ButterKnife.bind(this);
        toolbar.setTitleText("商品搜索");
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
        ivClear.setOnClickListener(this);
        llSearch.setOnClickListener(this);
        //监听键盘搜索按键
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!StringUtils.isEmpty(edtSearch.getText().toString())) {
                        hideKeyBord();
                        searchGoods();
                    } else {
                        ToastUtil.showTosat(GoodsSearchActivity2.this, "请输入商品名称");
                    }
                }
                return false;
            }
        });
//        shopCartPopupWindow = new ShopCartPopupWindow(this,bottom);
        final LinearLayoutManager goodsManager = new LinearLayoutManager(this);
        recyclerviewGoods.setLayoutManager(goodsManager);
        DividerDecoration divider = new DividerDecoration();
        divider.setDividerColor(Color.parseColor("#ebebeb"));
        recyclerviewGoods.addItemDecoration(divider);
        goodsInfos = new ArrayList<>();
        recyclerviewGoods.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case 0:
                    case 1:
                        Glide.with(GoodsSearchActivity2.this).resumeRequests();
                        break;
                    case 2:
                        Glide.with(GoodsSearchActivity2.this).pauseRequests();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = goodsManager.getItemCount();
                int firstVisibleItem = goodsManager.findFirstVisibleItemPosition();
                if (!isLoading && (totalItemCount - visibleItemCount <= firstVisibleItem && dy > 0)) {
                    //加载更多商品
                    showMoreGoods();
                }
            }
        });
        showCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCart != null && shopCart.getShoppingAccount() > 0) {
                    showCartDetail();
                }
            }
        });
    }

    private void searchGoods() {
        //搜索
        showLoadingDialog();
        page = 1;
        isLoadMore = false;
        presenter.loadGoods(SharedPreferencesUtil.getToken(), Const.ADMIN_ID, shop_id, edtSearch.getText().toString().trim(), page + "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LIMIT_LOGIN:
                case VIP_LOGIN:
                    token = SharedPreferencesUtil.getToken();
                    searchGoods();
                    break;
            }
        }
    }

    private void showMoreGoods() {
        isLoading = true;
        isLoadMore = true;
        presenter.loadGoods(SharedPreferencesUtil.getToken(), Const.ADMIN_ID, shop_id, edtSearch.getText().toString(), page + "");
    }

    //弹出购物清单
    private void showCartDetail() {
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            ShopCartDialog2 dialog = new ShopCartDialog2(GoodsSearchActivity2.this, shopCart, shop_id, R.style.cartdialog, isEffectiveVip);
            Window window = dialog.getWindow();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.setIShopCartDialog(this);
            dialog.show();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            params.x = 0;
            params.y = llCart.getHeight();
            params.dimAmount = 0.5f;
            window.setAttributes(params);

        }
    }

    private GoodsSearchBean.GoodsSearchData data;

    @Override
    public void loadData(GoodsSearchBean bean) {
        if ("1".equals(bean.data.IsShopMember)) {
            isShopMember = true;
            //是店铺会员
            if ("0".equals(bean.data.memberFreeze)) {
                //没有被冻结
                isFreezenMember = false;
                isEffectiveVip = true;//有效会员
            } else {
                isFreezenMember = true;
                isEffectiveVip = false;
            }
        } else {
            isShopMember = false;
            isEffectiveVip = false;
        }
        data = bean.data;
        shopCart.setIsEffectiveVip(isEffectiveVip);
        shopCart.setMemberInfo(isShopMember, isFreezenMember);
        SharedPreferencesUtil.saveIsEffectVip(isEffectiveVip);
        SharedPreferencesUtil.saveIsShopMember(isShopMember);
        SharedPreferencesUtil.saveIsFreezenMember(isFreezenMember);
        isLoading = false;
        if (null != bean.data.foodlist) {
            if (bean.data.foodlist.size() > 0) {
                page++;
                if (!isLoadMore) {
                    goodsInfos.clear();
                    for (GoodsListBean.GoodsInfo packageInfo : packageList) {
                        if (-1 != packageInfo.name.indexOf(edtSearch.getText().toString().trim())) {
                            goodsInfos.add(packageInfo);
                        }
                    }
                }
                for (GoodsListBean.GoodsInfo goodsInfo : bean.data.foodlist) {
                    goodsInfo.shop_id = shop_id;
                    if ("0".equals(goodsInfo.is_nature)) {
                        if (null != goodsInfo.nature) {
                            goodsInfo.nature.clear();
                        }
                    }
                    if("1".equals(goodsInfo.switch_discount)){
                        goodsInfo.type_id="discount";
                    }
                    goodsInfos.add(goodsInfo);
                }

                if (isLoadMore) {
                    if (null != goodsAdapter) {
                        if (bean.data.foodlist.size() % 50 == 0) {
                            goodsAdapter.setIsLoadAll(false);
                        } else {
                            goodsAdapter.setIsLoadAll(true);
                        }
                        goodsAdapter.notifyDataSetChanged();
                    }
                } else {
                    goodsAdapter = new ShopGoodsSearchAdapter(this, goodsInfos, shopCart, shopInfo);
                    goodsAdapter.setShopCartListener(this);
                    goodsAdapter.setIToDetailPage(this);
                    goodsAdapter.setIShowLimitTime(this);
                    if (bean.data.foodlist.size() % 50 == 0) {
                        goodsAdapter.setIsLoadAll(false);
                    } else {
                        goodsAdapter.setIsLoadAll(true);
                    }
                    recyclerviewGoods.setVisibility(View.VISIBLE);
                    recyclerviewGoods.setAdapter(goodsAdapter);
                }
            } else {
                if (isLoadMore) {
                    goodsAdapter.setIsLoadAll(true);
                } else {
                    goodsInfos.clear();
                    for (GoodsListBean.GoodsInfo packageInfo : packageList) {
                        if (-1 != packageInfo.name.indexOf(edtSearch.getText().toString().trim())) {
                            goodsInfos.add(packageInfo);
                        }
                    }
                    if (goodsInfos.size() > 0) {
                        goodsAdapter = new ShopGoodsSearchAdapter(this, goodsInfos, shopCart, shopInfo);
                        goodsAdapter.setShopCartListener(this);
                        goodsAdapter.setIToDetailPage(this);
                        goodsAdapter.setIShowLimitTime(this);
                        if (bean.data.foodlist.size() % 50 == 0) {
                            goodsAdapter.setIsLoadAll(false);
                        } else {
                            goodsAdapter.setIsLoadAll(true);
                        }
                        recyclerviewGoods.setVisibility(View.VISIBLE);
                        recyclerviewGoods.setAdapter(goodsAdapter);
                    } else {
                        recyclerviewGoods.setVisibility(View.GONE);
                    }
                }
            }
            String base_price = bean.data.basicprice;

            if (!TextUtils.isEmpty(basicPrice)) {
                if (!basicPrice.equals(base_price)) {
                    basicPrice = base_price;
                    showCartData();
                }
            }
        }
    }

    @Override
    public void loadFail() {

        llLoadFail.setVisibility(View.VISIBLE);
    }

    @Override
    public void dialogDismiss() {
//        svProgressHUD.dismiss();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (null != loginTipDialog) {
            loginTipDialog.dismiss();
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(GoodsSearchActivity2.this, s);
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(this, R.style.transparentDialog);
        }
        loadingDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_again:
                showLoadingDialog();
                llLoadFail.setVisibility(View.GONE);
                presenter.loadGoods(SharedPreferencesUtil.getToken(), Const.ADMIN_ID, shop_id, edtSearch.getText().toString(), page + "");
                break;
            case R.id.iv_clear:
                edtSearch.setText("");
                break;
            case R.id.ll_search:
                if (StringUtils.isEmpty(edtSearch.getText().toString())) {
                    ToastUtil.showTosat(GoodsSearchActivity2.this, "请输入商品名称");
                    return;
                }
                hideKeyBord();
                searchGoods();
                break;
        }
    }

    @Override
    public void add(View view, int postiion, GoodsListBean.GoodsInfo goods) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        recyclerviewGoods.getLocationInWindow(recycleLocation);
        imgCart.getLocationInWindow(cartLocation);

        AnimatorView animatorView = new AnimatorView(this);
        animatorView.setStartPosition(new Point(addLocation[0], addLocation[1] - (imgCart.getHeight()) / 2));
        animatorView.setEndPosition(new Point(cartLocation[0] + (imgCart.getWidth()) / 2, cartLocation[1] - imgCart.getHeight()));
        mainLayout.addView(animatorView);
        animatorView.setAnimatorListener(this);
        animatorView.startBeizerAnimation();

        showCartData();//显示购物车数据



    }


    public void addFromDialog(View view, int position, GoodsListBean.GoodsInfo goods) {

        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] dialogLocation = new int[2];
        view.getLocationInWindow(addLocation);
        imgCart.getLocationInWindow(cartLocation);
        centerPopView.linearLayout.getLocationInWindow(dialogLocation);


        AnimatorView animatorView = new AnimatorView(this);
        animatorView.setStartPosition(new Point(addLocation[0], addLocation[1]));
        animatorView.setEndPosition(new Point(cartLocation[0] + (imgCart.getWidth()) / 2, cartLocation[1] - imgCart.getHeight()));
        centerPopView.rootView.addView(animatorView);
        animatorView.setAnimatorListener(this);
        animatorView.startBeizerAnimation();

        showCartData();//显示购物车数据

    }

    @Override
    public void remove(int position, GoodsListBean.GoodsInfo goods) {
        showCartData();
    }


    private void showCartData() {
        //处理满减的情况
        String limit = "";
        if (!TextUtils.isEmpty(is_only_promotion) && "1".equals(is_only_promotion)) {
            limit = "（在线支付专享）";
        }
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            imgCart.setImageResource(R.mipmap.icon_shopping_cart_red);
            tvCost.setVisibility(View.VISIBLE);
            String totalPrice = df.format(shopCart.getShoppingTotalPrice());
            tvCost.setText(FormatUtil.numFormat(totalPrice));
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText("" + shopCart.getShoppingAccount());
            if (shopCart.getShoppingTotalPrice() < Float.parseFloat(basicPrice)) {
                tvSubmit.setVisibility(View.GONE);
                tvTips.setVisibility(View.VISIBLE);
                String elsePrice = df.format(Float.parseFloat(basicPrice) - shopCart.getShoppingTotalPrice());
                tvTips.setText("差￥" + FormatUtil.numFormat(elsePrice) + "起送");
                tvTips.setBackgroundColor(Color.parseColor("#404040"));
            } else {
                tvTips.setVisibility(View.GONE);
                tvSubmit.setVisibility(View.VISIBLE);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        token = SharedPreferencesUtil.getToken();
                        if (TextUtils.isEmpty(token)) {
                            startActivity(new Intent(GoodsSearchActivity2.this, LoginActivity.class));
                        } else {
                            checkOrder(shop_id);
                        }
                    }
                });
            }

            boolean isContainDiscount = false;//是否含有折扣商品
            //检查普通商品列表
            Iterator<String> iterator = shopCart.getGoodsMap().keySet().iterator();
            while (iterator.hasNext()){
                String id = iterator.next();
                if (shopCart.getGoodsMap().get(id).size() > 0){
                    GoodsListBean.GoodsInfo goodsInfo = shopCart.getGoods(id);
                    if ("discount".equals(goodsInfo.type_id)) {
                        isContainDiscount = true;
                        break;
                    }
                }
            }

            if (!TextUtils.isEmpty(open_promotion) && "1".equals(open_promotion) && !isContainDiscount) {
                //从低到高排序
                List<ManJian> sort = sortManJianList(promotion_rule);
                //大于起送价的直接显示满减
                for(int i=0;i<sort.size();i++){
                    ManJian manJian=sort.get(i);
                    if (shopCart.getShoppingTotalPrice() <= Float.parseFloat(manJian.amount)) {
                        float cha = Float.parseFloat(manJian.amount) - shopCart.getShoppingTotalPrice();
                        String yijian = "";
                        if (cha > 0){
                            String coumoney = FormatUtil.numFormat(df.format(cha));
                            if (i > 0){
                                yijian = "下单减" + sort.get( i - 1 ).discount + "元，";
                            }
                            tvManjian.setText(manJianText(yijian, coumoney, FormatUtil.numFormat(manJian.discount), limit, "再买"));
                        }else {
                            // 10 == 10
                            yijian = "下单减" + manJian.discount + "元，";
                            tvManjian.setText(manJianText(yijian, manJian.amount, FormatUtil.numFormat(manJian.discount), limit, "已买"));
                            if (sort.size() - 1 > i){
                                float cha1 = Float.parseFloat(sort.get( i + 1 ).amount) - shopCart.getShoppingTotalPrice();
                                String coumoney = FormatUtil.numFormat(df.format(cha1));
                                tvManjian.setText( manJianText(yijian, coumoney, FormatUtil.numFormat(sort.get( i + 1 ).discount), limit, "再买"));
                            }
                        }
                        tvManjian.setVisibility(View.VISIBLE);

                        break;
                    }
                }
                //判断是否大于满减的最高基数
                if (shopCart.getShoppingTotalPrice() > Float.parseFloat(sort.get(sort.size() - 1).amount)) {
                    tvManjian.setText(manJianText("",sort.get(sort.size() - 1).amount, FormatUtil.numFormat(sort.get(sort.size() - 1).discount), limit, "已买"));
                    tvManjian.setVisibility(View.VISIBLE);
                }
//                }
            } else {
                tvManjian.setVisibility(View.GONE);
            }
        } else {
            //判断是否开启了满减活动
            if ("1".equals(open_promotion)) {
                if (promotion_rule.size() > 0) {
                    String manjian = "";
                    for (int i = 0; i < sortManJianList(promotion_rule).size(); i++) {
                        ManJian manJian = sortManJianList(promotion_rule).get(i);
                        if (i == promotion_rule.size() - 1) {
                            manjian += "满" + manJian.amount + "减" + manJian.discount;
                        } else {
                            manjian += "满" + manJian.amount + "减" + manJian.discount + ";";
                        }
                    }
                    tvManjian.setText(manjian + limit);
                    tvManjian.setVisibility(View.VISIBLE);
                } else {
                    tvManjian.setVisibility(View.GONE);
                }
            } else {
                tvManjian.setVisibility(View.GONE);
            }
            imgCart.setImageResource(R.mipmap.icon_shopping_cart_grey);
            tvCost.setText("0");
            tvCost.setVisibility(View.VISIBLE);
            tvCount.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.GONE);
            tvTips.setVisibility(View.VISIBLE);
            tvTips.setText(FormatUtil.numFormat(basicPrice) + "元起送");
        }
    }

    //将满减的标准从低往高排序(冒泡排序)
    private List<ManJian> sortManJianList(List<ManJian> list) {
        ManJian[] array = list.toArray(new ManJian[list.size()]);
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (Float.parseFloat(array[i].amount) > Float.parseFloat(array[j].amount)) {
                    //交换位置
                    ManJian temp = null;
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        Log.e("满减活动列表", new Gson().toJson(array));
        return Arrays.asList(array);
    }

    //处理凑单的提示文字变色
    private SpannableString couDanText(String couMoney) {
        SpannableString s = new SpannableString("还差" + couMoney + "元就能起送【去凑单】");
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), 2, 2 + couMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), 7 + couMoney.length(), 12 + couMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    //处理满减的提示文字变色
    private SpannableString manJianText(String yijian,String elseMoney,String discount,String limit,String header) {
        SpannableString s = new SpannableString(yijian+header+elseMoney+"元可减"+discount+"元"+limit);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), yijian.length()+2, yijian.length()+2+elseMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), yijian.length()+5+elseMoney.length(), yijian.length()+5+elseMoney.length()+discount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }


    private ArrayList<GoodsListBean.GoodsInfo> natureGoodsList = new ArrayList<>();
    private TextView tvNatureCount;
    private TextView tvTotalPrice;
    private ShopNatureGoodsAdapter natureGoodsAdapter;
    private RecyclerView rv_nature_container;
    private boolean isFirst;

    @Override
    public void showNatureDialog(final GoodsListBean.GoodsInfo goods, final int position) {
        //创建数据集合
        natureGoodsList.clear();
        //用popwindow来显示
        View natureView = View.inflate(this, R.layout.dialog_edit_goods, null);
        TextView goodsName = (TextView) natureView.findViewById(R.id.goods_name);
        tvTotalPrice = (TextView) natureView.findViewById(R.id.tv_price);
        final LinearLayout ll_add = (LinearLayout) natureView.findViewById(R.id.ll_add);
        final LinearLayout ll_edit_goods = (LinearLayout) natureView.findViewById(R.id.ll_edit_goods);
        tvNatureCount = (TextView) natureView.findViewById(R.id.tvNaturCount);
        final LinearLayout addCart = (LinearLayout) natureView.findViewById(R.id.ll_add_cart);
        LinearLayout ll_minus = (LinearLayout) natureView.findViewById(R.id.ll_minus);
        LinearLayout ll_close = (LinearLayout) natureView.findViewById(R.id.ll_close);
        rv_nature_container = (RecyclerView) natureView.findViewById(R.id.rv_nature_container);
        //创建默认商品
        for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
            for (int i = 0; i < goodsNature.data.size(); i++) {
                if (i == 0) {
                    goodsNature.data.get(i).is_selected = true;
                } else {
                    goodsNature.data.get(i).is_selected = false;
                }
            }

        }
        if ("1".equals(goods.switch_discount)) {
            goods.isUseDiscount = true;
        }
        for (GoodsListBean.GoodsInfo natureGoods : shopCart.getGoodsList(goods.id)) {
            if (natureGoods.id.equals(goods.id)) {
                natureGoodsList.add(natureGoods);
            }
        }

        if (natureGoodsList.size() > 0) {
            isFirst = false;
        } else {
            isFirst = true;
            natureGoodsList.add(goods);
        }
        natureGoodsAdapter = new ShopNatureGoodsAdapter(this, natureGoodsList, shopCart, tvTotalPrice);
        natureGoodsAdapter.setGoodsPosition(position);
        rv_nature_container.setAdapter(natureGoodsAdapter);
        natureGoodsAdapter.setGoodsPosition(position);
        natureGoodsAdapter.setDeletNatureListener(this);
        rv_nature_container.setLayoutManager(new LinearLayoutManager(this));
        goodsName.setText(goods.name);
        //初始化控件
        if (natureGoodsList.size() > 0) {
            if (!TextUtils.isEmpty(natureGoodsList.get(0).index)) {
                addCart.setVisibility(View.INVISIBLE);
                ll_edit_goods.setVisibility(View.VISIBLE);
                tvNatureCount.setText(natureGoodsList.size() + "");
                natureGoodsAdapter.updateTotalPrice();
            } else {
                addCart.setVisibility(View.VISIBLE);
                ll_edit_goods.setVisibility(View.INVISIBLE);
            }
        } else {
            addCart.setVisibility(View.VISIBLE);
            ll_edit_goods.setVisibility(View.INVISIBLE);
        }
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (shopCart.addShoppingNature(goods,isFirst)) {
                    addCart.setVisibility(View.INVISIBLE);
                    ll_edit_goods.setVisibility(View.VISIBLE);
                    if (isFirst) {
                        natureGoodsList.clear();
                        isFirst = false;
                    }
                    natureGoodsList.clear();
                    natureGoodsList.addAll(shopCart.getGoodsList(goods.id));
                    natureGoodsAdapter.notifyDataSetChanged();
                    natureGoodsAdapter.updateTotalPrice();
                    rv_nature_container.smoothScrollToPosition(natureGoodsList.size() - 1);
                    int count = shopCart.getGoodsCount(goods.id);
                    tvNatureCount.setText(String.valueOf(count));
                    addFromDialog(ll_add, position, goods);
                }
                goodsAdapter.notifyItemChanged(position);
            }
        });

        ll_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (natureGoodsList.size() == 0) {
                    return;
                }
                //减去存在购物车最后一个商品
                if (shopCart.subShoppingSingle(natureGoodsList.get(natureGoodsList.size() - 1))) {
                    int count = shopCart.getGoodsCount(goods.id);
                    tvNatureCount.setText(count+"");
                    natureGoodsList.clear();
                    natureGoodsList.addAll(shopCart.getGoodsList(goods.id));
                    natureGoodsAdapter.notifyDataSetChanged();
                    natureGoodsAdapter.updateTotalPrice();
                    goodsAdapter.notifyItemChanged(position);
                    remove(position, goods);
                    if (natureGoodsList.size() == 0) {
                        if (null != centerPopView) {
                            centerPopView.dismissCenterPopView();
                        }
                        return;
                    }
                }
            }
        });


        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != centerPopView) {
                    centerPopView.dismissCenterPopView();
                }
            }
        });

        //处理加入购物车的逻辑
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_add.performClick();
            }
        });
        centerPopView = new CenterPopView(this);
        centerPopView.setContentView(natureView);
        centerPopView.showCenterPopView();
    }

    //删除属性商品
    @Override
    public void deleteNatureGoods(GoodsListBean.GoodsInfo goods, int position, int refreshPos) {
        if (natureGoodsList.size() == 0) {
            return;
        }
        if (shopCart.subShoppingSingle(natureGoodsList.get(position))) {
            int count = shopCart.getGoodsCount(goods.id);
            tvNatureCount.setText(count+"");
            natureGoodsList.clear();
            natureGoodsList.addAll(shopCart.getGoodsList(goods.id));
            natureGoodsAdapter.notifyDataSetChanged();
            natureGoodsAdapter.updateTotalPrice();
            goodsAdapter.notifyItemChanged(refreshPos);
            remove(refreshPos, goods);
            //减去存在购物车最后一个商品
            if (natureGoodsList.size() == 0) {
                if (null != centerPopView) {
                    centerPopView.dismissCenterPopView();
                }
                return;
            }
        }
    }

    @Override
    public void updateTotalPrice() {
        showCartData();
    }


    private Dialog loginTipDialog;

    @Override
    public void showTipDialog() {
        View dialogView = View.inflate(this, R.layout.dialog_login_tip, null);
        loginTipDialog = new Dialog(this, R.style.CenterDialogTheme2);
        loginTipDialog.setContentView(dialogView);
        loginTipDialog.setCanceledOnTouchOutside(false);

        Button btn_quit = (Button) dialogView.findViewById(R.id.btn_quit);
        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != loginTipDialog) {
                    loginTipDialog.cancel();
                }
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转登录界面
                startActivityForResult(new Intent(GoodsSearchActivity2.this, LoginActivity.class),
                        LIMIT_LOGIN);
            }
        });
        loginTipDialog.show();
    }

    @Override
    public void showVipDialog() {
        View dialogView = View.inflate(this, R.layout.dialog_login_tip_vip, null);
        loginTipDialog = new Dialog(this, R.style.CenterDialogTheme2);
        loginTipDialog.setContentView(dialogView);
        loginTipDialog.setCanceledOnTouchOutside(false);

        Button btn_quit = (Button) dialogView.findViewById(R.id.btn_quit);
        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != loginTipDialog) {
                    loginTipDialog.cancel();
                }
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转登录界面
                startActivityForResult(new Intent(GoodsSearchActivity2.this, LoginActivity.class),
                        VIP_LOGIN);
            }
        });
        loginTipDialog.show();

    }


    boolean isShowLimit;
    private TextView tv_tip;
    private TextView tvTipTimeHour;
    private TextView tvTipTimeMiniute;
    private TextView tvTipTimeSecond;
    @Override
    public void toGoodsDetailPage(GoodsListBean.GoodsInfo goods, int position) {
        if("0".equals(shopInfo.food_showtype)){
            //弹框显示
            View detailView = View.inflate(this, R.layout.dialog_goods_detail, null);
            int width = UIUtils.getWindowWidth() * 4 / 5;
            int height = UIUtils.getWindowHeight() * 2 / 3;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
            detailView.setLayoutParams(params);
            final CenterDialogView centerDialogView = new CenterDialogView(this);
            isShowLimit = false;
            final ImageView img = (ImageView) detailView.findViewById(R.id.img);
            img.getLayoutParams().width = width;
            TextView tvGoodsName=(TextView)detailView.findViewById(R.id.tv_goods_name);
            TextView tv_huodong_label=(TextView)detailView.findViewById(R.id.tv_huodong_label);
            TextView tvPrice=(TextView)detailView.findViewById(R.id.tvPrice);
            TextView tvQi=(TextView)detailView.findViewById(R.id.tvQi);
            TextView tvUnit=(TextView)detailView.findViewById(R.id.tvUnit);
            TextView tv_old_price=(TextView)detailView.findViewById(R.id.tv_old_price);
            TextView tvSaleCount=(TextView)detailView.findViewById(R.id.tv_sale_count);
            TextView tvLimitTag=(TextView)detailView.findViewById(R.id.tvLimitTag);
            TextView tv_desc=(TextView)detailView.findViewById(R.id.tv_desc);
            RelativeLayout rl_count_down=(RelativeLayout) detailView.findViewById(R.id.rl_count_down);
            tv_tip=(TextView) detailView.findViewById(R.id.tv_tip);
            tvTipTimeHour=(TextView) detailView.findViewById(R.id.tv_tip_time_hour);
            tvTipTimeMiniute=(TextView) detailView.findViewById(R.id.tv_tip_time_miniute);
            tvTipTimeSecond=(TextView) detailView.findViewById(R.id.tv_tip_time_second);

            final LinearLayout ll_limit_info=(LinearLayout) detailView.findViewById(R.id.ll_limit_info);
            TextView tv_act_limit = (TextView) detailView.findViewById(R.id.tv_act_limit);
            TextView tv_day_limit = (TextView) detailView.findViewById(R.id.tv_day_limit);
            TextView tv_limit_total = (TextView) detailView.findViewById(R.id.tv_limit_total);
            TextView tv_limit_day = (TextView) detailView.findViewById(R.id.tv_limit_day);
            TextView tv_act_fen = (TextView) detailView.findViewById(R.id.tv_act_fen);
            TextView tv_day_fen = (TextView) detailView.findViewById(R.id.tv_day_fen);
            TextView tv_time = (TextView) detailView.findViewById(R.id.tv_time);
            LinearLayout ll_container1 = (LinearLayout) detailView.findViewById(R.id.ll_container1);
            LinearLayout ll_container2 = (LinearLayout) detailView.findViewById(R.id.ll_container2);
            LinearLayout ll_close = (LinearLayout) detailView.findViewById(R.id.ll_close);
            LinearLayout ll_show_vip = (LinearLayout) detailView.findViewById(R.id.ll_show_vip);
            TextView tvVipPrice = (TextView) detailView.findViewById(R.id.tvVipPrice);
            TextView tvVipQi = (TextView) detailView.findViewById(R.id.tvVipQi);
            LinearLayout ll_discount = (LinearLayout) detailView.findViewById(R.id.ll_discount);
            TextView tv_discount = (TextView) detailView.findViewById(R.id.tv_discount);
            TextView tv_discount_num = (TextView) detailView.findViewById(R.id.tv_discount_num);

            TextView tv_order_limit = (TextView) detailView.findViewById(R.id.tv_order_limit);
            TextView tv_order_limit_count = (TextView) detailView.findViewById(R.id.tv_order_limit_count);
            TextView tv_order_fen = (TextView) detailView.findViewById(R.id.tv_order_fen);
            //加载网络图片
            String url="";
            if(!TextUtils.isEmpty(goods.img)){
                if (goods.img.startsWith("http")) {
                    url=goods.img+"!max200";
                }else{
                    url = RetrofitManager.BASE_URL + goods.img+"!max200";
                }
            }
            tvGoodsName.setText(goods.name);
            //商品标签
            if (!TextUtils.isEmpty(goods.label)) {
                tv_huodong_label.setVisibility(View.VISIBLE);
                tv_huodong_label.setText(goods.label);
            } else {
                tv_huodong_label.setVisibility(View.GONE);
            }
            //判断销量
            if("1".equals(shopInfo.showsales)){
                int saleCount = 0;
                if (!TextUtils.isEmpty(goods.ordered_count)) {
                    saleCount = Integer.parseInt(goods.ordered_count);
                }
                if (saleCount != 0) {
                    tvSaleCount.setText("已售" + saleCount);
                    tvSaleCount.setVisibility(View.VISIBLE);
                }else{
                    tvSaleCount.setVisibility(View.GONE);
                }
            }else{
                tvSaleCount.setVisibility(View.GONE);
            }

            //判断限购活动是否开启
            if ("1".equals(goods.is_limitfood)) {
                tvLimitTag.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(goods.limit_tags)) {
                    tvLimitTag.setText("商品限购");
                } else {
                    tvLimitTag.setText(goods.limit_tags);
                }

                //判断限购倒计时是否显示
                if("1".equals(goods.datetage)){
                    //显示倒计时
                    rl_count_down.setVisibility(View.VISIBLE);
                    limitGoods=goods;
                    showCountDown(goods,tv_tip,tvTipTimeHour,tvTipTimeMiniute,tvTipTimeSecond);
                }else{
                    //如果距离限购开启的最早时间段相差12小时以内显示倒计时
                    try{
                        long startTime = timeFormat.parse(goods.start_time+" "+goods.limit_time.get(0).start).getTime();
                        if(startTime-System.currentTimeMillis()<12*3600*1000){
                            limitGoods=goods;
                            rl_count_down.setVisibility(View.VISIBLE);
                            showCountDown(goods,tv_tip,tvTipTimeHour,tvTipTimeMiniute,tvTipTimeSecond);
                        }else{
                            rl_count_down.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                    }
                }
            } else {
                tvLimitTag.setVisibility(View.GONE);
            }

            //初始化限购信息
            if ("1".equals(goods.is_all_limit)) {
                tv_act_limit.setVisibility(View.VISIBLE);
                tv_limit_total.setVisibility(View.VISIBLE);
                tv_act_fen.setVisibility(View.VISIBLE);
                tv_limit_total.setText(goods.is_all_limit_num);
            }

            if ("1".equals(goods.is_customerday_limit)) {
                tv_day_limit.setVisibility(View.VISIBLE);
                tv_limit_day.setVisibility(View.VISIBLE);
                tv_day_fen.setVisibility(View.VISIBLE);
                tv_limit_day.setText(goods.day_foodnum);
            }
            LogUtil.log("toGoodsDetailPage"," name == " +goods.name + " is == " + goods.is_order_limit + "  num = "+goods.order_limit_num);
            //初始化限购信息
            if ("1".equals(goods.is_order_limit)) {
                tv_order_limit.setVisibility(View.VISIBLE);
                tv_order_limit_count.setVisibility(View.VISIBLE);
                tv_order_fen.setVisibility(View.VISIBLE);
                tv_order_limit_count.setText(goods.order_limit_num);
            }
            tv_time.setText(goods.start_time + "至" + goods.stop_time);
            ll_container1.removeAllViews();
            ll_container2.removeAllViews();
            if (null != goods.limit_time && goods.limit_time.size() > 0) {
                for (int i = 0; i < goods.limit_time.size(); i++) {
                    GoodsListBean.LimitTime limitTime = goods.limit_time.get(i);
                    TextView timeView = (TextView) View.inflate(this, R.layout.item_limit_time_small, null);
                    timeView.setText(limitTime.start + "~" + limitTime.stop);
                    if (i % 2 == 0) {
                        ll_container1.addView(timeView);
                    } else {
                        ll_container2.addView(timeView);
                    }
                }
            } else {
                TextView timeView = (TextView) View.inflate(this, R.layout.item_limit_time_small, null);
                timeView.setText("00:00~23:59");
                ll_container1.addView(timeView);
            }

            if (null != goods.packageNature && goods.packageNature.size() > 0) {
                tvPrice.setText(FormatUtil.numFormat(goods.price));
                tvQi.setVisibility(View.GONE);
            }else{
                if (null != goods.nature && goods.nature.size() > 0) {
                    //商品默认属性价格
                    if (!TextUtils.isEmpty(goods.min_price)) {
                        tvPrice.setText(FormatUtil.numFormat(Float.parseFloat(goods.price) + Float.parseFloat(goods.min_price) + ""));
                    } else {
                        tvPrice.setText(FormatUtil.numFormat(goods.price));
                    }
                    tvQi.setVisibility(View.VISIBLE);
                }else{
                    tvPrice.setText(FormatUtil.numFormat(goods.price));
                    tvQi.setVisibility(View.GONE);
                }
            }
            if("1".equals(shopInfo.unitshow)){
                //添加单位
                if (!TextUtils.isEmpty(goods.unit)) {
                    tvUnit.setVisibility(View.VISIBLE);
                    tvUnit.setText("/" + goods.unit);
                } else {
                    tvUnit.setVisibility(View.GONE);
                }
            }else{
                tvUnit.setVisibility(View.GONE);
            }

            //是否显示原价
            if ("1".equals(goods.has_formerprice)) {
                tv_old_price.setText("￥" + FormatUtil.numFormat(goods.formerprice));
                tv_old_price.getPaint().setAntiAlias(true);
                tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tv_old_price.setVisibility(View.GONE);
            }
            //添加描述
            if (!TextUtils.isEmpty(goods.descript)) {
                tv_desc.setVisibility(View.VISIBLE);
                tv_desc.setText(goods.descript);
            } else {
                tv_desc.setVisibility(View.GONE);
            }

            //折扣商品的显示
            if ("1".equals(goods.switch_discount)) {
                if (!TextUtils.isEmpty(goods.rate_discount)) {
                    tv_discount.setText(FormatUtil.numFormat(goods.rate_discount) + "折");
                }

                if (!TextUtils.isEmpty(goods.num_discount) && Float.parseFloat(goods.num_discount) > 0) {
                    tv_discount_num.setText("限" + goods.num_discount + "份优惠");
                    tv_discount_num.setVisibility(View.VISIBLE);
                }else{
                    tv_discount_num.setVisibility(View.GONE);
                }
                ll_discount.setVisibility(View.VISIBLE);
            } else {
                ll_discount.setVisibility(View.GONE);
                //会员价格显示
                if ("1".equals(goods.member_price_used)) {
                    ll_show_vip.setVisibility(View.VISIBLE);
                    String memberPrice = MemberUtil.getMemberPriceString(goods.member_grade_price);
                    if (!StringUtils.isEmpty(memberPrice)){
                        ll_show_vip.setVisibility(View.VISIBLE);
                        if (null != goods.nature && goods.nature.size() > 0){
                            //商品默认属性价格
                            if (!TextUtils.isEmpty(goods.min_price)) {
                                tvVipPrice.setText("￥" +FormatUtil.numFormat(Float.parseFloat(memberPrice) + Float.parseFloat(goods.min_price) + ""));
                            } else {
                                tvVipPrice.setText("￥" +FormatUtil.numFormat(memberPrice));
                            }
                            tvVipQi.setVisibility(View.VISIBLE);
                        }else{
                            tvVipPrice.setText("￥" + FormatUtil.numFormat(memberPrice));
                            tvVipQi.setVisibility(View.GONE);
                        }
                    }else {
                        ll_show_vip.setVisibility(View.INVISIBLE);
                    }
                } else {
                    ll_show_vip.setVisibility(View.INVISIBLE);
                }
            }

            rl_count_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isShowLimit){
                        isShowLimit=false;
                        ll_limit_info.setVisibility(View.GONE);
                    }else{
                        isShowLimit=true;
                        ll_limit_info.setVisibility(View.VISIBLE);
                    }
                }
            });
            //图片加载后显示弹框
            UIUtils.glideAppLoad(this,url,R.mipmap.common_def_food,img);
            ll_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=centerDialogView){
                        centerDialogView.dismissCenterDialogView();
                    }
                }
            });

            centerDialogView.setContentView(detailView);
            centerDialogView.showCenterDialogView();
        }else{
            //默认跳新页面
            Intent intent = new Intent(this, GoodsDetailActivity2.class);
            intent.putExtra("from_page", "search");
            intent.putExtra("shop_id", shop_id);
            intent.putExtra("shop_name", shopname);
            intent.putExtra("goods_info", goods);
            intent.putExtra("basic_price", basicPrice);
            intent.putExtra("shop_cart", shopCart);
            startActivity(intent);
        }

    }

    private LimitCountDownTimer mCountDownTimer;
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    //显示倒计时
    public void showCountDown(GoodsListBean.GoodsInfo goods,TextView tvTip,TextView tvTipTimeHour,TextView tvTipTimeMiniute,TextView tvTipTimeSecond){
        //先判断在不在时间段中
        boolean result = false;
        long endTime=0;//记录距离结束时间
        long beginTime=0;//记录距离开始时间
        try {
            Date today=new Date();
            SimpleDateFormat todayFormat=new SimpleDateFormat("yyyy-MM-dd");
            long currentTime = System.currentTimeMillis();
            for(int i=0;i<goods.limit_time.size();i++){
                GoodsListBean.LimitTime time=goods.limit_time.get(i);
                long startTime = timeFormat.parse(todayFormat.format(today)+" "+time.start).getTime();
                long stopTime = timeFormat.parse(todayFormat.format(today)+" "+time.stop).getTime();
                if(currentTime >= startTime && currentTime <= stopTime){
                    Log.e("cur..star..stop",timeFormat.format(new Date(currentTime))+".."+timeFormat.format(new Date(startTime))+".."+timeFormat.format(new Date(stopTime)));
                    result = true;
                    endTime=stopTime-currentTime;
                    break;
                }else if(currentTime<startTime){
//                    if(i==0){
//                        beginTime=startTime-currentTime;
//                    }else{
//                        beginTime=startTime-currentTime>beginTime?beginTime:startTime-currentTime;
//                    }
                    beginTime=startTime-currentTime;
                    break;
                }
            }
            if(result){
                Log.e("距结束", "很久很久啦啦啦啦 " );
                Log.e("endTime", endTime+"");
                //表示在限购时间段中
                tvTip.setText("距活动结束：");
                //创建倒计时类
                mCountDownTimer = new LimitCountDownTimer(endTime, 1000, tvTipTimeHour, tvTipTimeMiniute, tvTipTimeSecond);
                mCountDownTimer.start();
                mHandler.postDelayed(run, endTime);
            }else{
                Log.e("距开始", endTime+"");
                Log.e("beginTime", beginTime+"");
                tvTip.setText("距活动开始：");
                mCountDownTimer = new LimitCountDownTimer(beginTime, 1000, tvTipTimeHour, tvTipTimeMiniute, tvTipTimeSecond);
                mCountDownTimer.start();
                mHandler.postDelayed(run, beginTime);
            }
        } catch (Exception e) {
            UIUtils.showToast("后台设置时间格式有误");
        }
    }

    @Override
    public void playAnimator() {
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(imgCart, "scaleX", 1.2f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(imgCart, "scaleY", 1.2f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY);
        animatorSet.setDuration(400);
        animatorSet.start();
    }

    @Override
    protected void onDestroy() {
        //在Activity销毁之前要消失掉dialog，否则报Android.view.WindowLeaked异常
        if (null != centerPopView) {
            centerPopView.dismissCenterPopView();
        }

        super.onDestroy();
    }

    @Override
    public void updateCartData() {
        showCartData();
        //刷新商品列表
        if (null != goodsAdapter) {
            goodsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLimitTime(GoodsListBean.GoodsInfo goods) {
        View dialogView = View.inflate(this, R.layout.dialog_limit_time, null);
        final Dialog limitDialog = new Dialog(this, R.style.CenterDialogTheme2);
        //去掉dialog上面的横线
        Context context = limitDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = limitDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }
        limitDialog.setContentView(dialogView);
        limitDialog.setCanceledOnTouchOutside(false);
        TextView tv_time = (TextView) dialogView.findViewById(R.id.tv_time);
        LinearLayout ll_container1 = (LinearLayout) dialogView.findViewById(R.id.ll_container1);
        LinearLayout ll_container2 = (LinearLayout) dialogView.findViewById(R.id.ll_container2);
        LinearLayout ll_close = (LinearLayout) dialogView.findViewById(R.id.ll_close);
        tv_time.setText(goods.start_time + "至" + goods.stop_time);

        ll_container1.removeAllViews();
        ll_container2.removeAllViews();
        if (null != goods.limit_time && goods.limit_time.size() > 0) {
            for (int i = 0; i < goods.limit_time.size(); i++) {
                GoodsListBean.LimitTime limitTime = goods.limit_time.get(i);
                TextView timeView = (TextView) View.inflate(this, R.layout.item_limit_time, null);
                timeView.setText(limitTime.start + "~" + limitTime.stop);
                if (i % 2 == 0) {
                    ll_container1.addView(timeView);
                } else {
                    ll_container2.addView(timeView);
                }
            }
        } else {
            TextView timeView = (TextView) View.inflate(this, R.layout.item_limit_time, null);
            timeView.setText("00:00~23:59");
            ll_container1.addView(timeView);
        }

        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != limitDialog) {
                    limitDialog.dismiss();
                }
            }
        });
        limitDialog.show();
    }
    public boolean isFullMinBuy(){
        Map<String,Integer> map = new HashMap<>();
        ArrayList<GoodsListBean.GoodsInfo> list = new ArrayList<>();
        Iterator<String> iterator = shopCart.getGoodsMap().keySet().iterator();
        while (iterator.hasNext()){
            String id = iterator.next();
            int c = shopCart.getGoodsCount(id);
            map.put(id,c);
            list.addAll(shopCart.getGoodsList(id));
        }
        for (GoodsListBean.GoodsInfo goodsInfo:list){
            int min = 1;
            if (!StringUtils.isEmpty(goodsInfo.min_buy_count) && Integer.parseInt(goodsInfo.min_buy_count) > 1){
                min = Integer.parseInt(goodsInfo.min_buy_count);
            }
            if (min > map.get(goodsInfo.id)){
                UIUtils.showToast("【"+goodsInfo.name+"】商品不足最小起购份数");
                return false;
            }
        }
        return true;
    }

    //必选商品检测
    private void checkOrder(String shop_id) {
        String admin_id = Const.ADMIN_ID;
        String token = SharedPreferencesUtil.getToken();
        ArrayList<String> goodList = new ArrayList<>();
        List<GoodsListBean.GoodsInfo> goodsInfos = BaseApplication.greenDaoManager.getGoodsListByShopId(shop_id);
        for (GoodsListBean.GoodsInfo goodsInfo : goodsInfos) {
            goodList.add(goodsInfo.original_type_id);
        }
        String food_type = new Gson().toJson(goodList);
        HashMap<String, String> params = IsCollectRequest.checkOrder(admin_id, token, shop_id,food_type);
        checkPresenter.check(UrlConst.CHECK_ORDER, params, shop_id);
    }

    //必选分类提示
    private Dialog goodsTypeNeedDialog;
    private TextView tvError;
    private void showGoodsTypeNeedDialog(String msg) {
        if (goodsTypeNeedDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_goods_type_need, null);
            tvError = (TextView) view.findViewById(R.id.tv_error);
            view.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsTypeNeedDialog.dismiss();
                }
            });
            goodsTypeNeedDialog = DialogUtils.centerDialog(this, view);
        }
        tvError.setText(msg);
        goodsTypeNeedDialog.show();
    }

    @Override
    public void showSuccessVIew(String shop_id) {
        //跳转购物车页面
        Intent intent = new Intent(GoodsSearchActivity2.this, ShoppingCartActivity2.class);
        intent.putExtra("shop_id", shop_id);
        startActivity(intent);
    }

    @Override
    public void showCheckOrderView(String msg) {
        showGoodsTypeNeedDialog(msg);
    }
}
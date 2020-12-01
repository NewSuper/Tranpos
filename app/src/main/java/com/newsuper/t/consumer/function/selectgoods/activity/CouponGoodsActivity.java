package com.newsuper.t.consumer.function.selectgoods.activity;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//
import com.google.gson.Gson;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.GoodsCouponBean;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ManJian;
import com.newsuper.t.consumer.bean.ShopCart;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.selectgoods.adapter.CouponGoodsGridAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter.NatureGoodsAdapter;
import com.newsuper.t.consumer.function.selectgoods.inter.AnimationListener;
import com.newsuper.t.consumer.function.selectgoods.inter.ICheckGoodsMustView;
import com.newsuper.t.consumer.function.selectgoods.inter.ICouponGoods;
import com.newsuper.t.consumer.function.selectgoods.inter.IDeleteNatureGoods;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCart;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCartDialog;
import com.newsuper.t.consumer.function.selectgoods.inter.IShowLimitTime;
import com.newsuper.t.consumer.function.selectgoods.presenter.CheckGoodsMustPresenter;
import com.newsuper.t.consumer.function.selectgoods.presenter.CouponGoodsPresenter;
import com.newsuper.t.consumer.function.selectgoods.request.IsCollectRequest;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.AnimatorView;
import com.newsuper.t.consumer.widget.CenterPopView;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;
import com.newsuper.t.consumer.widget.ShopCartDialog;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponGoodsActivity extends BaseActivity implements ICouponGoods, IShopCart, IGoodsToDetailPage, ICheckGoodsMustView
        , AnimationListener, IDeleteNatureGoods, IShopCartDialog, IShowLimitTime ,View.OnClickListener{

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_ad)
    ImageView ivAd;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
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
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.tv_manjian)
    TextView tvManjian;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.loading_view)
    RefreshThirdStepView loadingView;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;

    private CouponGoodsPresenter presenter;
    private CheckGoodsMustPresenter checkPresenter;
    private String shop_id;
    private ShopInfoBean.ShopInfo shopInfo;
    private ShopCart shopCart;
    private DecimalFormat df;
    private String token,admin_id;
    private String basicPrice;
    private CenterPopView centerPopView;
    private CouponGoodsGridAdapter adapter;
    private boolean isEffectiveVip;
    private boolean isShopMember;//是否店铺会员
    private boolean isFreezenMember;//是否冻结会员
    private boolean isFreshCouponData = true;//是否刷新搜索商品数据
    private Dialog loginTipDialog;
    private final int LIMIT_LOGIN = 11;//点击限购跳转登陆的
    private final int VIP_LOGIN = 12;//点击会员跳转登陆的
    private String is_only_promotion;
    private String open_promotion;
    private boolean isInitData;//成功加载到选购数据
    private List<ManJian> promotion_rule = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    closeLoadingView();
                    break;
            }
        }
    };

    //加载动画
    private AnimationDrawable animationDrawable;
    Runnable anim = new Runnable() {
        @Override
        public void run() {
            animationDrawable.start();
        }
    };
    @Override
    public void initData() {
        shop_id = getIntent().getStringExtra("shop_id");
        shopInfo = (ShopInfoBean.ShopInfo) getIntent().getSerializableExtra("shopInfo");
        basicPrice = getIntent().getStringExtra("basicPrice");
        shopCart = (ShopCart) getIntent().getSerializableExtra("shop_cart");
        presenter = new CouponGoodsPresenter(this);
        checkPresenter = new CheckGoodsMustPresenter(this);
        df = new DecimalFormat("#0.00");
    }



    @Override
    public void initView() {
        setContentView(R.layout.activity_coupon_goods);
        ButterKnife.bind(this);
        animationDrawable = (AnimationDrawable) loadingView.getBackground();
        showCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCart.getShoppingAccount() > 0) {
                    showCartDetail();
                }
            }
        });
        llBack.setOnClickListener(this);
        //加载活动商品
        loadGoods();
        //加载购物车商品
        showCartData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInitData) {
            //只刷新购物车数据
            shopCart.clear();
            queryCartGoods();
            updateCartData();
        }
    }

    //查询本地数据
    private void queryCartGoods() {
        ArrayList<GoodsListBean.GoodsInfo> cartList = BaseApplication.greenDaoManager.getAllGoods(shop_id);
        if (null != cartList && cartList.size() > 0) {
            for (GoodsListBean.GoodsInfo goodsInfo : cartList) {
                if (null != goodsInfo.packageNature && goodsInfo.packageNature.size() > 0) {
                    for (GoodsListBean.PackageNature packageNature : goodsInfo.packageNature) {
                        for (GoodsListBean.PackageNatureValue selectValue : packageNature.value) {
                            if (selectValue.is_selected) {
                                if (shopCart.goodsSingle.containsKey(selectValue.id)) {
                                    int count = shopCart.goodsSingle.get(selectValue.id);
                                    count += goodsInfo.count;
                                    shopCart.goodsSingle.put(selectValue.id, count);
                                } else {
                                    shopCart.goodsSingle.put(selectValue.id, goodsInfo.count);
                                }
                            }
                        }
                    }
                    if ("1".equals(goodsInfo.switch_discount)) {
                        if (goodsInfo.isUseDiscount) {
                            shopCart.shoppingTotalPrice += goodsInfo.count * Float.parseFloat(goodsInfo.price);
                        } else {
                            shopCart.shoppingTotalPrice += goodsInfo.count * Float.parseFloat(goodsInfo.formerprice);
                        }
                    } else {
                        shopCart.shoppingTotalPrice += goodsInfo.count * Float.parseFloat(goodsInfo.price);
                    }
                    shopCart.natureGoodsList.add(goodsInfo);
                    shopCart.shoppingAccount += goodsInfo.count;
                } else {
                    float discountPrice = 0;//折扣单价
                    float price = 0;//原单价
                    if ("1".equals(goodsInfo.switch_discount)) {
                        discountPrice = Float.parseFloat(goodsInfo.price);
                        price = Float.parseFloat(goodsInfo.formerprice);
                        if (null != goodsInfo.nature && goodsInfo.nature.size() > 0) {
                            //属性商品
                            for (GoodsListBean.GoodsNature goodsNature : goodsInfo.nature) {
                                for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                                    if (natureData.is_selected) {
                                        discountPrice += Float.parseFloat(natureData.price);
                                        price += Float.parseFloat(natureData.price);
                                    }
                                }
                            }
                        }
                    } else {
                        if ("1".equals(goodsInfo.member_price_used)) {
                            if (isEffectiveVip) {
                                price = Float.parseFloat(goodsInfo.member_price);
                            } else {
                                price = Float.parseFloat(goodsInfo.price);
                            }
                        } else {
                            price = Float.parseFloat(goodsInfo.price);
                        }
                        if (null != goodsInfo.nature && goodsInfo.nature.size() > 0) {
                            //属性商品
                            for (GoodsListBean.GoodsNature goodsNature : goodsInfo.nature) {
                                for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                                    if (natureData.is_selected) {
                                        price += Float.parseFloat(natureData.price);
                                    }
                                }
                            }
                        }
                    }

                    float totalPrice = 0;//单份商品总价
                    if ("1".equals(goodsInfo.switch_discount)) {
                        //限购份数内按折扣价，限购份数外按原价
                        int preCount = 0;//此前购买的数量
                        if (shopCart.goodsSingle.containsKey(goodsInfo.id)) {
                            preCount = shopCart.goodsSingle.get(goodsInfo.id);
                            shopCart.goodsSingle.put(goodsInfo.id, preCount + goodsInfo.count);
                        } else {
                            shopCart.goodsSingle.put(goodsInfo.id, goodsInfo.count);
                        }
                        if (Float.parseFloat(goodsInfo.num_discount) != 0) {
                            if (preCount < Integer.parseInt(goodsInfo.num_discount)) {
                                //当前仍可购买的折扣商品数量
                                int canBuyNum = Integer.parseInt(goodsInfo.num_discount) - preCount;
                                if (goodsInfo.count <= canBuyNum) {
                                    totalPrice = discountPrice * goodsInfo.count;
                                } else {
                                    totalPrice = discountPrice * canBuyNum + price * (goodsInfo.count - canBuyNum);
                                }
                            } else {
                                totalPrice = price * goodsInfo.count;
                            }
                        } else {
                            totalPrice = discountPrice * goodsInfo.count;
                        }
                    } else {
                        totalPrice = price * goodsInfo.count;
                        if (shopCart.goodsSingle.containsKey(goodsInfo.id)) {
                            int preCount = shopCart.goodsSingle.get(goodsInfo.id);
                            shopCart.goodsSingle.put(goodsInfo.id, preCount + goodsInfo.count);
                        } else {
                            shopCart.goodsSingle.put(goodsInfo.id, goodsInfo.count);
                        }
                    }
                    if (goodsInfo.nature.size() > 0) {
                        shopCart.natureGoodsList.add(goodsInfo);
                        if (shopCart.natureTotalPrice.containsKey(goodsInfo.id)) {
                            float pre = shopCart.natureTotalPrice.get(goodsInfo.id);
                            pre += totalPrice;
                            shopCart.natureTotalPrice.put(goodsInfo.id, pre);
                        } else {
                            shopCart.natureTotalPrice.put(goodsInfo.id, totalPrice);
                        }

                    } else {
                        shopCart.getShoppingSingleMap().put(goodsInfo, goodsInfo.count);
                    }

                    shopCart.shoppingTotalPrice += totalPrice;
                    shopCart.shoppingAccount += goodsInfo.count;
                }
            }
        }

    }





    private void loadGoods(){
        showLoadingView();
        presenter.loadGoods(SharedPreferencesUtil.getToken(), Const.ADMIN_ID, shop_id);
    }

    //正在加载
    private void showLoadingView() {
        if (null != llFail) {
            llFail.setVisibility(View.GONE);
        }
        llLoading.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        handler.post(anim);
    }

    //关闭加载动画
    private void closeLoadingView() {
        if (null != animationDrawable) {
            animationDrawable.stop();
        }
        if (null != llLoading) {
            llLoading.setVisibility(View.GONE);
        }
    }


    @Override
    public void updateCartData() {
        showCartData();
        //刷新商品列表
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(GoodsCouponBean bean) {
        isInitData = true;//获取到数据
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
        shopCart.setIsEffectiveVip(isEffectiveVip);
        shopCart.setMemberInfo(isShopMember, isFreezenMember);
        SharedPreferencesUtil.saveIsEffectVip(isEffectiveVip);
        SharedPreferencesUtil.saveIsShopMember(isShopMember);
        SharedPreferencesUtil.saveIsFreezenMember(isFreezenMember);
        GoodsCouponBean.GoodsCouponData data = bean.data;
        is_only_promotion = data.is_only_promotion;
        open_promotion = data.open_promotion;
        promotion_rule = data.promotion_rule;
        UIUtils.glideAppLoad(this,data.image_advertising,R.mipmap.common_def_food,ivAd);
        for(GoodsListBean.GoodsInfo goods:data.list){
            if(goods.switch_discount.equals("1")){
                goods.type_id = "discount";
            }
        }
        adapter = new CouponGoodsGridAdapter(this, data.list, shopCart, shopInfo);
        adapter.setShopCartListener(this);
        adapter.setIToDetailPage(this);
        adapter.setIShowLimitTime(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvGoods.setLayoutManager(gridLayoutManager);
        rvGoods.setAdapter(adapter);

        showCartData();
        //关闭动画
        closeLoadingView();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LIMIT_LOGIN:
                case VIP_LOGIN:
                    token = SharedPreferencesUtil.getToken();
                    //加载活动商品
                    loadGoods();
                    break;
            }
        }
    }

    //弹出购物清单
    private void showCartDetail() {
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            ShopCartDialog dialog = new ShopCartDialog(this, shopCart, shop_id, R.style.cartdialog, isEffectiveVip);
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
            params.y = bottom.getHeight();
            params.dimAmount = 0.5f;
            window.setAttributes(params);
        }
    }


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
                startActivityForResult(new Intent(CouponGoodsActivity.this, LoginActivity.class),
                        LIMIT_LOGIN);
            }
        });
        loginTipDialog.show();
    }


    @Override
    public void toGoodsDetailPage(GoodsListBean.GoodsInfo goods, int position) {
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
                startActivityForResult(new Intent(CouponGoodsActivity.this, LoginActivity.class),
                        VIP_LOGIN);
            }
        });
        loginTipDialog.show();

    }


    @Override
    public void dialogDismiss() {
        if (null != loginTipDialog) {
            loginTipDialog.dismiss();
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this, s);
    }

    @Override
    public void loadFail() {
        isInitData = false;
        if (null != animationDrawable) {
            animationDrawable.stop();
        }
        llFail.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void add(View view, int postiion, GoodsListBean.GoodsInfo goods) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        rvGoods.getLocationInWindow(recycleLocation);
        imgCart.getLocationInWindow(cartLocation);

        AnimatorView animatorView = new AnimatorView(this);
        animatorView.setStartPosition(new Point(addLocation[0], addLocation[1] - (imgCart.getHeight()) / 2));
        animatorView.setEndPosition(new Point(cartLocation[0] + (imgCart.getWidth()) / 2, cartLocation[1] - imgCart.getHeight()));
        mainLayout.addView(animatorView);
        animatorView.setAnimatorListener(this);
        animatorView.startBeizerAnimation();

        showCartData();//显示购物车数据
        if (shopCart.getShoppingSingleMap().containsKey(goods)) {
            goods.count = shopCart.getShoppingSingleMap().get(goods);
        }

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
        if (shopCart.getShoppingSingleMap().containsKey(goods)) {
            goods.count = shopCart.getShoppingSingleMap().get(goods);
        }
    }


    @Override
    public void remove(int position, GoodsListBean.GoodsInfo goods) {
        showCartData();
        if (shopCart.getShoppingSingleMap().containsKey(goods)) {
            goods.count = shopCart.getShoppingSingleMap().get(goods);
        }
    }


    private void showCartData() {
        //处理满减的情况
        String limit = "";
        if (!TextUtils.isEmpty(is_only_promotion) && "1".equals(is_only_promotion)) {
            limit = "（在线支付专享）";
        }
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            tvCost.setVisibility(View.VISIBLE);
            String totalPrice = df.format(shopCart.getShoppingTotalPrice());
            tvCost.setText("￥ " + FormatUtil.numFormat(totalPrice));
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
                            startActivity(new Intent(CouponGoodsActivity.this, LoginActivity.class));
                        } else {
                            checkOrder(shop_id);
                        }
                    }
                });
            }
            boolean isContainDiscount = false;//是否含有折扣商品
            //检查普通商品列表
            if (shopCart.shoppingSingle.size() > 0) {
                for (GoodsListBean.GoodsInfo goodsInfo : shopCart.shoppingSingle.keySet()) {
                    if ("discount".equals(goodsInfo.type_id)) {
                        isContainDiscount = true;
                        break;
                    }
                }
            }
            //如果已经检查到添加了折扣商品，此步操作不用执行
            //检查属性商品列表
            if (!isContainDiscount) {
                if (shopCart.natureGoodsList.size() > 0) {
                    for (GoodsListBean.GoodsInfo natureGoods : shopCart.natureGoodsList) {
                        if ("discount".equals(natureGoods.type_id)) {
                            isContainDiscount = true;
                            break;
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(open_promotion) && "1".equals(open_promotion) && !isContainDiscount) {
                //从低到高排序
                if (promotion_rule.size() > 0) {
                    List<ManJian> sort = sortManJianList(promotion_rule);
//                if (shopCart.getShoppingTotalPrice() < Float.parseFloat(basicPrice)) {
//                    //低于起送价8元以内提示凑单,超出8元显示整个的满减活动
//                    float elsePrice = Float.parseFloat(basicPrice) - shopCart.getShoppingTotalPrice();
//                    if (elsePrice > 8) {
//                        tvManjian.setVisibility(View.GONE);
//                    } else {
//                        tvManjian.setText(couDanText(FormatUtil.numFormat(df.format(elsePrice))));
//                        tvManjian.setVisibility(View.VISIBLE);
//                    }
//                } else {
                    //大于起送价的直接显示满减
                    for (ManJian manJian : sort) {
                        if (shopCart.getShoppingTotalPrice() < Float.parseFloat(manJian.amount)) {
                            String coumoney = FormatUtil.numFormat(df.format(Float.parseFloat(manJian.amount) - shopCart.getShoppingTotalPrice()));
                            tvManjian.setText(manJianText(coumoney, FormatUtil.numFormat(manJian.discount), limit, "再买"));
                            tvManjian.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    if (shopCart.getShoppingTotalPrice() > Float.parseFloat(sort.get(sort.size() - 1).amount)) {
                        tvManjian.setText(manJianText(sort.get(sort.size() - 1).amount, FormatUtil.numFormat(sort.get(sort.size() - 1).discount), limit, "已买"));
                        tvManjian.setVisibility(View.VISIBLE);
                    }
//                }
                }
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
            tvCost.setVisibility(View.INVISIBLE);
            tvCount.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.GONE);
            tvTips.setVisibility(View.VISIBLE);
            tvTips.setText("￥" + FormatUtil.numFormat(basicPrice) + "起送");
        }
    }

    //将满减的标准从低往高排序(冒泡排序)
    private List<ManJian> sortManJianList(List<ManJian> list) {
        ManJian[] array = list.toArray(new ManJian[list.size()]);
        if (array.length > 0) {
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
        }
        return Arrays.asList(array);
    }

    //处理满减的提示文字变色
    private SpannableString manJianText(String elseMoney, String discount, String limit, String header) {
        SpannableString s = new SpannableString(header + elseMoney + "元可减" + discount + "元" + limit);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), 2, 2 + elseMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), 5 + elseMoney.length(), 5 + elseMoney.length() + discount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }


    private ArrayList<GoodsListBean.GoodsInfo> natureGoodsList = new ArrayList<>();
    private TextView tvNatureCount;
    private TextView tvTotalPrice;
    private NatureGoodsAdapter natureGoodsAdapter;
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
        if (null!=goods.packageNature&&goods.packageNature.size()>0) {
            for (GoodsListBean.PackageNature packageNature : goods.packageNature) {
                boolean isHasSelectNature = false;//有选中属性，控制循环
                for (int i = 0; i < packageNature.value.size(); i++) {
                    if (!isHasSelectNature) {
                        if ("1".equals(packageNature.value.get(i).stockvalid)) {
                            if (packageNature.value.get(i).stock > 0) {
                                if ("NORMAL".equals(packageNature.value.get(i).status)) {
                                    packageNature.value.get(i).is_selected = true;
                                    isHasSelectNature = true;
                                }
                            }
                        } else {
                            if ("NORMAL".equals(packageNature.value.get(i).status)) {
                                packageNature.value.get(i).is_selected = true;
                                isHasSelectNature = true;
                            }
                        }
                    } else {
                        packageNature.value.get(i).is_selected = false;
                    }
                }
            }
        } else {
            for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                for (int i = 0; i < goodsNature.data.size(); i++) {
                    if (i == 0) {
                        goodsNature.data.get(i).is_selected = true;
                    } else {
                        goodsNature.data.get(i).is_selected = false;
                    }
                }

            }
        }

        if ("1".equals(goods.switch_discount)) {
            goods.isUseDiscount = true;
        }

        if (shopCart.natureGoodsList.size() > 0) {
            for (GoodsListBean.GoodsInfo natureGoods : shopCart.natureGoodsList) {
                if (natureGoods.id.equals(goods.id)) {
                    natureGoodsList.add(natureGoods);
                }
            }
        }

        if (natureGoodsList.size() > 0) {
            isFirst = false;
        } else {
            isFirst = true;
            natureGoodsList.add(goods);
        }

        natureGoodsAdapter = new NatureGoodsAdapter(this, natureGoodsList, shopCart, tvTotalPrice);
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
                //创建商品
                GoodsListBean.GoodsInfo addGoods = new GoodsListBean().new GoodsInfo();
                addGoods.shop_id = goods.shop_id;
                addGoods.id = goods.id;
                addGoods.name = goods.name;
                addGoods.img=goods.img;
                addGoods.price = goods.price;
                addGoods.formerprice = goods.formerprice;
                addGoods.has_formerprice = goods.has_formerprice;
                addGoods.type_id = goods.type_id;
                addGoods.second_type_id = goods.second_type_id;
                ArrayList<GoodsListBean.GoodsNature> nautreList = new ArrayList<>();
                if (null != goods.nature && goods.nature.size() > 0) {
                    for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                        GoodsListBean.GoodsNature cloneNuture = new GoodsListBean().new GoodsNature();
                        cloneNuture.naturename = goodsNature.naturename;
                        cloneNuture.maxchoose = goodsNature.maxchoose;
                        ArrayList<GoodsListBean.GoodsNatureData> valueList = new ArrayList<>();

                        for (int i = 0; i < goodsNature.data.size(); i++) {
                            GoodsListBean.GoodsNatureData value = goodsNature.data.get(i);
                            GoodsListBean.GoodsNatureData cloneValue = new GoodsListBean().new GoodsNatureData();
                            cloneValue.naturevalue = value.naturevalue;
                            cloneValue.price = value.price;
                            if (isFirst) {
                                if (value.is_selected) {
                                    cloneValue.is_selected = true;
                                } else {
                                    cloneValue.is_selected = false;
                                }
                            } else {
                                if (i == 0) {
                                    cloneValue.is_selected = true;
                                } else {
                                    cloneValue.is_selected = false;
                                }
                            }
                            valueList.add(cloneValue);
                        }
                        cloneNuture.data = valueList;
                        nautreList.add(cloneNuture);
                    }
                }
                addGoods.nature = nautreList;
                ArrayList<GoodsListBean.PackageNature> packageNaturesList = new ArrayList<>();
                if (null != goods.packageNature && goods.packageNature.size() > 0) {
                    for (GoodsListBean.PackageNature packageNature : goods.packageNature) {
                        GoodsListBean.PackageNature clonePackageNuture = new GoodsListBean().new PackageNature();
                        clonePackageNuture.name = packageNature.name;
                        ArrayList<GoodsListBean.PackageNatureValue> valueList = new ArrayList<>();
                        boolean isHasSelectNature = false;//有选中属性，控制循环
                        for (int i = 0; i < packageNature.value.size(); i++) {
                            GoodsListBean.PackageNatureValue value = packageNature.value.get(i);
                            GoodsListBean.PackageNatureValue cloneValue = new GoodsListBean().new PackageNatureValue();
                            cloneValue.id = value.id;
                            cloneValue.name = value.name;
                            cloneValue.stockvalid = value.stockvalid;
                            cloneValue.stock = value.stock;
                            cloneValue.status = value.status;
                            cloneValue.isCanSelect = value.isCanSelect;
                            if (isFirst) {
                                if (value.is_selected) {
                                    cloneValue.is_selected = true;
                                } else {
                                    cloneValue.is_selected = false;
                                }
                            } else {
                                if (!isHasSelectNature) {
                                    if ("1".equals(cloneValue.stockvalid)) {
                                        if (cloneValue.stock > 0) {
                                            if ("NORMAL".equals(cloneValue.status)) {
                                                cloneValue.is_selected = true;
                                                isHasSelectNature = true;
                                            }
                                        }
                                    } else {
                                        if ("NORMAL".equals(cloneValue.status)) {
                                            cloneValue.is_selected = true;
                                            isHasSelectNature = true;
                                        }
                                    }
                                } else {
                                    cloneValue.is_selected = false;
                                }
                            }
                            valueList.add(cloneValue);
                        }
                        clonePackageNuture.value = valueList;
                        packageNaturesList.add(clonePackageNuture);
                    }
                }
                addGoods.packageNature = packageNaturesList;
                addGoods.count = 1;
                addGoods.unit = goods.unit;
                addGoods.status = goods.status;
                addGoods.stock = goods.stock;
                addGoods.stockvalid = goods.stockvalid;
                addGoods.is_nature = goods.is_nature;
                addGoods.is_dabao = goods.is_dabao;
                addGoods.dabao_money = goods.dabao_money;
                addGoods.member_price_used = goods.member_price_used;
                addGoods.member_price = goods.member_price;
                addGoods.memberlimit = goods.memberlimit;
                addGoods.is_limitfood = goods.is_limitfood;
                addGoods.datetage = goods.datetage;
                addGoods.timetage = goods.timetage;
                addGoods.is_all_limit = goods.is_all_limit;
                addGoods.hasBuyNum = goods.hasBuyNum;
                addGoods.is_all_limit_num = goods.is_all_limit_num;
                addGoods.is_customerday_limit = goods.is_customerday_limit;
                addGoods.hasBuyNumByDay = goods.hasBuyNumByDay;
                addGoods.day_foodnum = goods.day_foodnum;
                addGoods.switch_discount = goods.switch_discount;
                addGoods.num_discount = goods.num_discount;
                addGoods.rate_discount = goods.rate_discount;
                addGoods.discount_type = goods.discount_type;
                addGoods.order_limit_num = goods.order_limit_num;
                addGoods.is_order_limit = goods.is_order_limit;
                addGoods.min_buy_count = goods.min_buy_count;
                addGoods.discount_show_type = goods.discount_show_type;
                addGoods.original_type_id = goods.original_type_id;
                int currentNum = Integer.parseInt(tvNatureCount.getText().toString());//当前选购的份数，默认为0
                if ("1".equals(goods.switch_discount)) {
                    if (Float.parseFloat(goods.num_discount) == 0) {
                        addGoods.isUseDiscount = true;
                    } else {
                        if (currentNum < Float.parseFloat(goods.num_discount)) {
                            addGoods.isUseDiscount = true;
                        } else {
                            addGoods.isUseDiscount = false;
                        }
                    }
                } else {
                    addGoods.isUseDiscount = false;
                }
                addGoods.index = System.currentTimeMillis() + "" + (int) ((Math.random() * 9 + 1) * 1000);
                if (shopCart.addShoppingSingle(addGoods)) {
                    addCart.setVisibility(View.INVISIBLE);
                    ll_edit_goods.setVisibility(View.VISIBLE);

                    if (isFirst) {
                        natureGoodsList.clear();
                        isFirst = false;
                    }
                    natureGoodsList.add(addGoods);
                    natureGoodsAdapter.notifyDataSetChanged();
                    natureGoodsAdapter.updateTotalPrice();
                    rv_nature_container.smoothScrollToPosition(natureGoodsList.size() - 1);
                    int count = Integer.parseInt(tvNatureCount.getText().toString());
                    count++;
                    tvNatureCount.setText(String.valueOf(count));
                    addFromDialog(ll_add, position, addGoods);
                }
                adapter.notifyItemChanged(position);
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
                    int selectNum = Integer.parseInt(tvNatureCount.getText().toString());
                    selectNum--;
                    tvNatureCount.setText(String.valueOf(selectNum));
                    natureGoodsList.remove(natureGoodsList.size() - 1);
                    if (natureGoodsList.size() == 0) {
                        adapter.notifyItemChanged(position);
                        remove(position, goods);
                        if (null != centerPopView) {
                            centerPopView.dismissCenterPopView();
                        }
                        return;
                    }
                    natureGoodsAdapter.notifyDataSetChanged();
                    natureGoodsAdapter.updateTotalPrice();
                    adapter.notifyItemChanged(position);
                    remove(position, goods);
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


    @Override
    public void deleteNatureGoods(GoodsListBean.GoodsInfo goods, int position, int refreshPos) {
        int selectNum = Integer.parseInt(tvNatureCount.getText().toString());
        selectNum--;
        tvNatureCount.setText(String.valueOf(selectNum));
        if (shopCart.subShoppingSingle(natureGoodsList.get(position))) {
            natureGoodsList.remove(position);
            //减去存在购物车最后一个商品
            if (natureGoodsList.size() == 0) {
                adapter.notifyItemChanged(position);
                remove(refreshPos, goods);
                if (null != centerPopView) {
                    centerPopView.dismissCenterPopView();
                }
                return;
            }
            natureGoodsAdapter.notifyDataSetChanged();
            natureGoodsAdapter.updateTotalPrice();
            adapter.notifyItemChanged(position);
            remove(refreshPos, goods);
        }
    }

    @Override
    public void updateTotalPrice() {

        showCartData();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                this.finish();
                break;
        }
    }

    //必选商品检测
    private void checkOrder(String shop_id) {
        admin_id = Const.ADMIN_ID;
        token = SharedPreferencesUtil.getToken();
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
        Intent intent = new Intent(CouponGoodsActivity.this, ShoppingCartActivity2.class);
        intent.putExtra("shop_id", shop_id);
        startActivity(intent);
    }

    @Override
    public void showCheckOrderView(String msg) {
        showGoodsTypeNeedDialog(msg);
    }
}

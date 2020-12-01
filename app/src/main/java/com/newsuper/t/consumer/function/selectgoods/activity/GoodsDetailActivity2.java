package com.newsuper.t.consumer.function.selectgoods.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.GoodsDetailBean;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ManJian;
import com.newsuper.t.consumer.bean.PackageDetailBean;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopNatureGoodsAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopCartDialog2;
import com.newsuper.t.consumer.function.selectgoods.inter.AnimationListener;
import com.newsuper.t.consumer.function.selectgoods.inter.ICheckGoodsMustView;
import com.newsuper.t.consumer.function.selectgoods.inter.IDeleteNatureGoods;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsDetailView;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCartDialog;
import com.newsuper.t.consumer.function.selectgoods.presenter.CheckGoodsMustPresenter;
import com.newsuper.t.consumer.function.selectgoods.presenter.GoodsDetailPresenter;
import com.newsuper.t.consumer.function.selectgoods.request.IsCollectRequest;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.ShareUtils;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StatusBarUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.AnimatorView;
import com.newsuper.t.consumer.widget.CenterPopView;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;
import com.newsuper.t.consumer.widget.advertisment.AdPicturePlayView;
import com.newsuper.t.consumer.widget.spinerwidget.LimitCountDownTimer;
import com.newsuper.t.consumer.wxapi.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailActivity2  extends BaseActivity implements IGoodsDetailView, View.OnClickListener, ICheckGoodsMustView
        , AnimationListener, IShopCartDialog, IDeleteNatureGoods {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_huodong_label)
    TextView tvLabel;
    @BindView(R.id.tv_sale_count)
    TextView tvSaleCount;
    @BindView(R.id.tv_now_price)
    TextView tvNowPrice;
    @BindView(R.id.tv_before_price)
    TextView tvBeforePrice;
    @BindView(R.id.tv_min_buy)
    TextView tv_min_buy;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.imgCart)
    ImageView imgCart;
    @BindView(R.id.tvCount)
    TextView tvCartCount;
    @BindView(R.id.tvCost)
    TextView tvCost;
    @BindView(R.id.tvTips)
    TextView tvTips;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.webview_goods)
    WebView webviewGoods;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.tvQi)
    TextView tvQi;
    @BindView(R.id.tvQi2)
    TextView tvQi2;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tvUnit)
    TextView tvUnit;
    @BindView(R.id.tvLimitTag)
    TextView tvLimitTag;
    @BindView(R.id.llMinus)
    LinearLayout llMinus;
    @BindView(R.id.count)
    TextView tvCount;
    @BindView(R.id.llAdd)
    LinearLayout llAdd;
    @BindView(R.id.ll_limit_time)
    LinearLayout ll_limit_time;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.ll_edit_goods)
    LinearLayout ll_edit_goods;
    @BindView(R.id.tvShowCount)
    TextView tvShowCount;
    @BindView(R.id.rl_select_nature)
    RelativeLayout rl_select_nature;
    @BindView(R.id.ll_add_cart)
    LinearLayout ll_add_cart;
    @BindView(R.id.fl_edit)
    FrameLayout fl_edit;
    @BindView(R.id.show_cart)
    RelativeLayout show_cart;
    @BindView(R.id.ll_show_goods)
    RelativeLayout llShowGoods;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_tip_img)
    ImageView tvTipImg;
    @BindView(R.id.rl_count_down)
    RelativeLayout rlCountDown;
    @BindView(R.id.tv_goods_info)
    TextView tvGoodsInfo;
    @BindView(R.id.ll_goods_info)
    LinearLayout llGoodsInfo;
    @BindView(R.id.ll_goods_des)
    LinearLayout llGoodsDes;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.iv_goods_logo)
    ImageView iv_goods_logo;
    @BindView(R.id.ll_go_shop)
    LinearLayout llGoShop;
    @BindView(R.id.loading_view)
    RefreshThirdStepView loadingView;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_tip_time_hour)
    TextView tvTipTimeHour;
    @BindView(R.id.tv_tip_time_miniute)
    TextView tvTipTimeMiniute;
    @BindView(R.id.tv_tip_time_second)
    TextView tvTipTimeSecond;
    @BindView(R.id.tv_shop_tip)
    TextView tvShopTip;
    @BindView(R.id.playView)
    AdPicturePlayView playView;
    @BindView(R.id.tvVipPrice)
    TextView tvVipPrice;
    @BindView(R.id.ll_show_vip)
    LinearLayout llShowVip;
    @BindView(R.id.rl_shop)
    RelativeLayout rlShop;
    @BindView(R.id.tv_manjian)
    TextView tvManjian;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_discount_num)
    TextView tvDiscountNum;
    @BindView(R.id.ll_discount)
    LinearLayout llDiscount;
    private GoodsDetailPresenter presenter;
    private CheckGoodsMustPresenter checkPresenter;
    private DecimalFormat df;
    private CenterPopView centerPopView;
    private String from_page;//页面跳转来源
    private Intent dataIntent;
    private ShopCart2 shopCart;
    private String basicPrice;
    private String goods_id;
    private String shop_id;
    private String shop_name;
    private String food_type;
    private int foodLimitType;
    private String timeRelativeHD;
    private GoodsListBean.GoodsInfo goodsInfo;
    private LimitCountDownTimer mCountDownTimer;
    private String admin_id = Const.ADMIN_ID;
    private final static int VIP_LOGIN = 10;
    private boolean isEffectiveVip;
    private boolean isShopMember;//是否有效会员
    private boolean isFreezenMember;//是否冻结会员
    public static GoodsDetailActivity2 instance;
    private IWXAPI msgApi;
    private Handler mHandler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            //刷新订单状态
            showLimitTime();
        }
    };

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
    @Override
    public void initStatusBar() {
        StatusBarUtil.setStatusBarFullScreen(this);
    }

    //加载动画
    private AnimationDrawable animationDrawable;
    Runnable anim = new Runnable() {
        @Override
        public void run() {
            animationDrawable.start();
        }
    };
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        instance = this;
        df = new DecimalFormat("#0.00");
        presenter = new GoodsDetailPresenter(this);
        checkPresenter = new CheckGoodsMustPresenter(this);
        msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
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
    }

    private String getWebContent(String content) {
        String css = "<style>.box{ font-size:40px ;color:#676767 ;margin:30px} </style>";
        css = css + "<body><div class = 'box'>" + content + "</div></body";

        return css;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        rlShop.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        llBack.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlShare.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        llGoShop.setOnClickListener(this);
        show_cart.setOnClickListener(this);
        rl_select_nature.setOnClickListener(this);

        animationDrawable = (AnimationDrawable) loadingView.getBackground();

        webviewGoods.getSettings().setUseWideViewPort(true);
        webviewGoods.getSettings().setLoadWithOverviewMode(true);
        webviewGoods.getSettings().setDefaultTextEncodingName("UTF-8");
        webviewGoods.getSettings().setSupportZoom(true);
//        shopCartPopupWindow = new ShopCartPopupWindow(this,bottom);
        collapsingToolbarLayout.setTitle("");
        tvTitle.setVisibility(View.INVISIBLE);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int alpha = 255 - Math.abs(verticalOffset / 2);
                if (alpha <= 0) {
                    alpha = 0;
                }
                llBack.getBackground().setAlpha((int) alpha);
                llShare.getBackground().setAlpha((int) alpha);
                if (verticalOffset == 0) {
                    //展开
                    tvTitle.setVisibility(View.INVISIBLE);
                    ivBack.setImageResource(R.mipmap.icon_back);
                    ivShare.setImageResource(R.mipmap.icon_share);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //折叠
                    tvTitle.setVisibility(View.VISIBLE);
                    llBack.getBackground().setAlpha(0);
                    llShare.getBackground().setAlpha(0);
                    ivBack.setImageResource(R.mipmap.icon_back_gray);
                    ivShare.setImageResource(R.mipmap.icon_shop_share_gray);
                }
            }
        });

        switch (from_page) {
            case "xuangou":
                shop_name = dataIntent.getStringExtra("shop_name");
                shop_id = dataIntent.getStringExtra("shop_id");
                goodsInfo = (GoodsListBean.GoodsInfo) dataIntent.getSerializableExtra("goods_info");
                shopCart = (ShopCart2) dataIntent.getSerializableExtra("shop_cart");
                goods_id = goodsInfo.id;
                tvShopName.setText(shop_name);
                if (null != goodsInfo.packageNature && goodsInfo.packageNature.size() > 0) {
                    food_type = "2";
                } else {
                    food_type = "1";
                }
//                initGoodsShow();
                break;
            case "search":
                //从商品搜索跳转过来的
                shop_name = dataIntent.getStringExtra("shop_name");
                shop_id = dataIntent.getStringExtra("shop_id");
                goodsInfo = (GoodsListBean.GoodsInfo) dataIntent.getSerializableExtra("goods_info");
                goods_id = goodsInfo.id;
                shopCart = (ShopCart2) dataIntent.getSerializableExtra("shop_cart");
                tvShopName.setText(shop_name);
                if ("taocan".equals(goodsInfo.type_id)) {
                    food_type = "2";
                } else {
                    food_type = "1";
                }
                if ("0".equals(goodsInfo.is_nature)) {
                    if (null != goodsInfo.nature) {
                        goodsInfo.nature.clear();
                    }
                }
//                initGoodsShow();
                break;
            case "wpage":
                //从微页面跳转
                goods_id = dataIntent.getStringExtra("goods_id");
                food_type = "1";
                shopCart = new ShopCart2();
                break;
            case "WGoodsList":
                goodsInfo = (GoodsListBean.GoodsInfo) dataIntent.getSerializableExtra("goods_info");
                goods_id = goodsInfo.id;
                shop_id = goodsInfo.shop_id;
                food_type = "1";
                shopCart = new ShopCart2();
                //查询本地数据
                queryCartGoods();
                break;
            case "share":
                shop_id = uri.getQueryParameter("shop_id");
                goods_id = uri.getQueryParameter("food_id");
                food_type = uri.getQueryParameter("food_type");
                shopCart = new ShopCart2();
                //查询本地数据
                queryCartGoods();
                break;
            case "searchShop":
                //店铺搜索
                goods_id = dataIntent.getStringExtra("goods_id");
                food_type = "1";
                shopCart = new ShopCart2();
                break;
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        //加载商品详情
        loadGoodsDetail();
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
        if (1 != goodsInfo.worktime && StringUtils.isEmpty(goodsInfo.outtime_info)) {
            if (-1 == goodsInfo.worktime) {
                tvShopTip.setText("本店已打烊啦");
            } else if (-2 == goodsInfo.worktime) {
                tvShopTip.setText("本店暂停营业");
            } else if (0 == goodsInfo.worktime) {
                tvShopTip.setText("本店休息中");
            } else if (2 == goodsInfo.worktime) {
                tvShopTip.setText("本店停止营业");
            }
            tvShopTip.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
            show_cart.setVisibility(View.GONE);
        } else {
            show_cart.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            tvShopTip.setVisibility(View.GONE);
        }
        coordinatorLayout.setVisibility(View.VISIBLE);
    }

    //查询本地数据
    private void queryCartGoods() {
        ArrayList<GoodsListBean.GoodsInfo> cartList = BaseApplication.greenDaoManager.getAllGoods(shop_id);
        if (null != cartList && cartList.size() > 0) {
            shopCart.addGoodsListFromDB(cartList);
        }
    }

    private void initGoodsShow() {
        if (goodsInfo != null) {
            ArrayList<String> picUrl = new ArrayList<>();
            if (null != goodsInfo.image && goodsInfo.image.size() > 0) {
                picUrl.addAll(goodsInfo.image);
                playView.setImageUrlList(picUrl);
            } else {
                picUrl.add("");
                iv_goods_logo.setVisibility(View.VISIBLE);
                iv_goods_logo.setImageResource(R.mipmap.common_def_food);
            }
            //商品标签
            if (!TextUtils.isEmpty(goodsInfo.label)) {
                tvLabel.setVisibility(View.VISIBLE);
                tvLabel.setText(goodsInfo.label);
            } else {
                tvLabel.setVisibility(View.GONE);
            }
            tvGoodsName.setText(goodsInfo.name);

            //最小起购数
            if (!StringUtils.isEmpty(goodsInfo.min_buy_count) && Integer.parseInt(goodsInfo.min_buy_count) > 1){
                int c = Integer.parseInt(goodsInfo.min_buy_count);
                tv_min_buy.setText(c + "份起购");
                tv_min_buy.setVisibility(View.VISIBLE);
            }else {
                tv_min_buy.setVisibility(View.GONE);
            }

            //折扣商品的显示
            if ("1".equals(goodsInfo.switch_discount)) {
                if (!TextUtils.isEmpty(goodsInfo.rate_discount)) {
                    tvDiscount.setText(FormatUtil.numFormat(goodsInfo.rate_discount) + "折");
                }
                if (!TextUtils.isEmpty(goodsInfo.num_discount) && Float.parseFloat(goodsInfo.num_discount) > 0) {
                    tvDiscountNum.setText("限" + goodsInfo.num_discount + "份优惠");
                    tvDiscountNum.setVisibility(View.VISIBLE);
                } else {
                    tvDiscountNum.setVisibility(View.GONE);
                }
                llDiscount.setVisibility(View.VISIBLE);
            } else {
                llDiscount.setVisibility(View.GONE);
                //会员价格显示
                if ("1".equals(goodsInfo.member_price_used)) {
                    llShowVip.setVisibility(View.VISIBLE);
                    String memberPrice = MemberUtil.getMemberPriceString(goodsInfo.member_grade_price);
                    float price = StringUtils.isEmpty(memberPrice) ? 0 : FormatUtil.numFloat(memberPrice);
                    if (goodsInfo.nature != null && goodsInfo.nature.size() > 0){
                        //计算商品默认属性价格
                        if (!TextUtils.isEmpty(goodsInfo.min_price)) {
                            price += Float.parseFloat(goodsInfo.min_price);
                            tvVipPrice.setText(FormatUtil.numFormat(price+""));
                        } else {
                            tvVipPrice.setText("￥" + memberPrice);
                        }
                        tvQi2.setVisibility(View.VISIBLE);
                    }else {
                        tvVipPrice.setText("￥" + memberPrice);
                    }
                } else {
                    llShowVip.setVisibility(View.GONE);
                }
            }

            //显示销量
            if ("1".equals(goodsInfo.showsales)) {
                int salecount = 0;
                if (!TextUtils.isEmpty(goodsInfo.ordered_count)) {
                    if (Integer.parseInt(goodsInfo.ordered_count) > 0) {
                        salecount = Integer.parseInt(goodsInfo.ordered_count);
                    }
                }
                if (salecount != 0) {
                    tvSaleCount.setText("已售" + salecount);
                } else {
                    tvSaleCount.setVisibility(View.GONE);
                }
            } else {
                tvSaleCount.setVisibility(View.GONE);
            }

            int count = shopCart.getGoodsCount(goodsInfo.id);
            if (null != goodsInfo.nature && goodsInfo.nature.size() > 0) {
                //计算商品默认属性价格
                if (!TextUtils.isEmpty(goodsInfo.min_price)) {
                    tvNowPrice.setText(FormatUtil.numFormat(Float.parseFloat(goodsInfo.price) + Float.parseFloat(goodsInfo.min_price) + ""));
                } else {
                    tvNowPrice.setText(FormatUtil.numFormat(goodsInfo.price));
                }
                tvQi.setVisibility(View.VISIBLE);
                if (null != goodsInfo.status) {
                    if (goodsInfo.status.equals("NORMAL")) {
                        if (1 != goodsInfo.worktime && StringUtils.isEmpty(goodsInfo.outtime_info)) {
                            rl_select_nature.setVisibility(View.INVISIBLE);
                            ll_add_cart.setVisibility(View.INVISIBLE);
                            ll_edit_goods.setVisibility(View.INVISIBLE);
                        } else {
                            rl_select_nature.setVisibility(View.VISIBLE);
                            ll_edit_goods.setVisibility(View.INVISIBLE);
                            ll_add_cart.setVisibility(View.INVISIBLE);
                            if (count < 1) {
                                tvShowCount.setVisibility(View.GONE);
                            } else {
                                tvShowCount.setVisibility(View.VISIBLE);
                                tvShowCount.setText(count + "");
                            }
                        }
                    } else {
                        tvStatus.setVisibility(View.VISIBLE);
                        rl_select_nature.setVisibility(View.INVISIBLE);
                        ll_edit_goods.setVisibility(View.INVISIBLE);
                        ll_add_cart.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (1 != goodsInfo.worktime && StringUtils.isEmpty(goodsInfo.outtime_info)) {
                        rl_select_nature.setVisibility(View.INVISIBLE);
                        ll_edit_goods.setVisibility(View.INVISIBLE);
                    } else {
                        rl_select_nature.setVisibility(View.VISIBLE);
                        ll_edit_goods.setVisibility(View.INVISIBLE);
                        ll_add_cart.setVisibility(View.INVISIBLE);
                        if (count < 1) {
                            tvShowCount.setVisibility(View.GONE);
                        } else {
                            tvShowCount.setVisibility(View.VISIBLE);
                            tvShowCount.setText(count + "");
                        }
                    }
                }
            } else {
                tvNowPrice.setText(FormatUtil.numFormat(goodsInfo.price));
                tvQi.setVisibility(View.GONE);
                if (null != goodsInfo.status) {
                    if (goodsInfo.status.equals("NORMAL")) {
                        if (1 != goodsInfo.worktime && StringUtils.isEmpty(goodsInfo.outtime_info)) {
                            rl_select_nature.setVisibility(View.INVISIBLE);
                            ll_edit_goods.setVisibility(View.INVISIBLE);
                            ll_add_cart.setVisibility(View.INVISIBLE);
                        } else {
                            rl_select_nature.setVisibility(View.GONE);
                            if (count < 1) {
                                llMinus.setVisibility(View.GONE);
                                tvCount.setVisibility(View.GONE);
                                ll_add_cart.setVisibility(View.VISIBLE);
                                ll_edit_goods.setVisibility(View.INVISIBLE);
                            } else {
                                llMinus.setVisibility(View.VISIBLE);
                                tvCount.setVisibility(View.VISIBLE);
                                tvCount.setText(count + "");
                                ll_add_cart.setVisibility(View.GONE);
                                ll_edit_goods.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        tvStatus.setVisibility(View.VISIBLE);
                        rl_select_nature.setVisibility(View.INVISIBLE);
                        ll_edit_goods.setVisibility(View.INVISIBLE);
                        ll_add_cart.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (1 != goodsInfo.worktime && StringUtils.isEmpty(goodsInfo.outtime_info)) {
                        rl_select_nature.setVisibility(View.INVISIBLE);
                        ll_edit_goods.setVisibility(View.INVISIBLE);
                        ll_add_cart.setVisibility(View.INVISIBLE);
                    } else {
                        rl_select_nature.setVisibility(View.GONE);
                        if (count < 1) {
                            llMinus.setVisibility(View.GONE);
                            tvCount.setVisibility(View.GONE);
                            ll_add_cart.setVisibility(View.VISIBLE);
                            ll_edit_goods.setVisibility(View.INVISIBLE);
                        } else {
                            llMinus.setVisibility(View.VISIBLE);
                            tvCount.setVisibility(View.VISIBLE);
                            tvCount.setText(count + "");
                            ll_add_cart.setVisibility(View.INVISIBLE);
                            ll_edit_goods.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            if (goodsInfo.has_formerprice.equals("1")) {
                if (!TextUtils.isEmpty(goodsInfo.min_price)) {
                    tvBeforePrice.setText("￥" + FormatUtil.numFormat(Float.parseFloat(goodsInfo.formerprice) + Float.parseFloat(goodsInfo.min_price) + ""));
                } else {
                    tvBeforePrice.setText("￥" + FormatUtil.numFormat(goodsInfo.formerprice));
                }
                tvBeforePrice.getPaint().setAntiAlias(true);//抗锯齿
                tvBeforePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
            }
            if (1 == goodsInfo.worktime || !StringUtils.isEmpty(goodsInfo.outtime_info)) {
                //判断限购活动是否开启
                if ("1".equals(goodsInfo.is_limitfood)) {
                    tvLimitTag.setVisibility(View.VISIBLE);
                    if (TextUtils.isEmpty(goodsInfo.limit_tags)) {
                        tvLimitTag.setText("商品限购");
                    } else {
                        tvLimitTag.setText(goodsInfo.limit_tags);
                    }
                    //展示非可售时间
                    if ("1".equals(goodsInfo.datetage)) {
                        if ("1".equals(goodsInfo.timetage)) {
                            //正常可售时间
                            ll_limit_time.setVisibility(View.GONE);
                            fl_edit.setVisibility(View.VISIBLE);
                        } else {
                            ll_limit_time.setVisibility(View.VISIBLE);
                            ll_limit_time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showLimitTimeDialog();
                                }
                            });
                            fl_edit.setVisibility(View.GONE);
                        }
                    } else {
                        ll_limit_time.setVisibility(View.VISIBLE);
                        ll_limit_time.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showLimitTimeDialog();
                            }
                        });
                        fl_edit.setVisibility(View.GONE);
                    }
                } else {
                    tvLimitTag.setVisibility(View.GONE);
                }
            }
            //添加单位
            if (!TextUtils.isEmpty(goodsInfo.unit)) {
                tvUnit.setVisibility(View.VISIBLE);
                tvUnit.setText("/" + goodsInfo.unit);
            } else {
                tvUnit.setVisibility(View.GONE);
            }
            ll_add_cart.setOnClickListener(this);
            llMinus.setOnClickListener(this);
            llAdd.setOnClickListener(this);
        }
    }

    public void showLimitTimeDialog() {
        View dialogView = View.inflate(this, R.layout.dialog_limit_time, null);
        final Dialog limitDialog = new Dialog(this, R.style.CenterDialogTheme2);
        limitDialog.setContentView(dialogView);
        limitDialog.setCanceledOnTouchOutside(false);
        TextView tv_time = (TextView) dialogView.findViewById(R.id.tv_time);
        LinearLayout ll_container1 = (LinearLayout) dialogView.findViewById(R.id.ll_container1);
        LinearLayout ll_container2 = (LinearLayout) dialogView.findViewById(R.id.ll_container2);
        LinearLayout ll_close = (LinearLayout) dialogView.findViewById(R.id.ll_close);
        tv_time.setText(goodsInfo.start_time + "至" + goodsInfo.stop_time);
        if (null != goodsInfo.limit_time && goodsInfo.limit_time.size() > 0) {
            for (int i = 0; i < goodsInfo.limit_time.size(); i++) {
                GoodsListBean.LimitTime limitTime = goodsInfo.limit_time.get(i);
                TextView timeView = (TextView) View.inflate(this, R.layout.item_limit_time, null);
                timeView.setText(limitTime.start + "~" + limitTime.stop);
                if (i % 2 == 0) {
                    ll_container1.addView(timeView);
                } else {
                    ll_container2.addView(timeView);
                }
            }
        } else {
            TextView timeView = (TextView) View.inflate(GoodsDetailActivity2.this, R.layout.item_limit_time, null);
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
    //显示倒计时
    private void showLimitTime() {
        switch (foodLimitType) {
            case 0:
                //不显示倒计时
                rlCountDown.setVisibility(View.GONE);
                break;
            case 1:
                //距开始
                rlCountDown.setVisibility(View.VISIBLE);
                rlCountDown.setOnClickListener(this);
                tvTip.setText("距活动开始：");
                //创建倒计时类
                try {
                    String[] time = timeRelativeHD.split(":");
                    long startTime = Integer.parseInt(time[0]) * 3600 * 1000 + Integer.parseInt(time[1]) * 60 * 1000 + Integer.parseInt(time[2]) * 1000;
                    mCountDownTimer = new LimitCountDownTimer(startTime, 1000, tvTipTimeHour, tvTipTimeMiniute, tvTipTimeSecond);
                    mCountDownTimer.start();
                    mHandler.postDelayed(run, startTime);
                } catch (Exception e) {
                    UIUtils.showToast("后台设置时间格式有误");
                }
                break;
            case 2:
                //距结束
                rlCountDown.setVisibility(View.VISIBLE);
                rlCountDown.setOnClickListener(this);
                tvTip.setText("距活动结束：");
                //创建倒计时类
                try {
                    String[] time = timeRelativeHD.split(":");
                    long endTime = Integer.parseInt(time[0]) * 3600 * 1000 + Integer.parseInt(time[1]) * 60 * 1000 + Integer.parseInt(time[2]) * 1000;
                    mCountDownTimer = new LimitCountDownTimer(endTime, 1000, tvTipTimeHour, tvTipTimeMiniute, tvTipTimeSecond);
                    mCountDownTimer.start();
                    mHandler.postDelayed(run, endTime);
                } catch (Exception e) {
                    UIUtils.showToast("后台设置时间格式有误");
                }
                break;
        }

    }

    private void loadGoodsDetail() {
        showLoadingView();
        presenter.loadDetail(SharedPreferencesUtil.getToken(), SharedPreferencesUtil.getAdminId(), goods_id, food_type);
    }

    private void showCartData() {
        //处理满减的情况
        String limit = "";
        if ("1".equals(goodsInfo.is_only_promotion)) {
            limit = "（在线支付专享）";
        }
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            imgCart.setImageResource(R.mipmap.icon_shopping_cart_red);
            tvCost.setVisibility(View.VISIBLE);
            String totalPrice = df.format(shopCart.getShoppingTotalPrice());
            tvCost.setText(FormatUtil.numFormat(totalPrice));
            tvCartCount.setVisibility(View.VISIBLE);
            tvCartCount.setText("" + shopCart.getShoppingAccount());
            if (shopCart.getShoppingTotalPrice() < Float.parseFloat(basicPrice)) {
                tvSubmit.setVisibility(View.GONE);
                tvTips.setVisibility(View.VISIBLE);
                String elsePrice = df.format(Float.parseFloat(basicPrice) - shopCart.getShoppingTotalPrice());
                tvTips.setText("差￥" + FormatUtil.numFormat(elsePrice) + "起送");
            } else {
                tvTips.setVisibility(View.GONE);
                tvSubmit.setVisibility(View.VISIBLE);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isFullMinBuy()){
                            return;
                        }
                        String token = SharedPreferencesUtil.getToken();
                        if (TextUtils.isEmpty(token)) {
                            startActivity(new Intent(GoodsDetailActivity2.this, LoginActivity.class));
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

            if (!TextUtils.isEmpty(goodsInfo.open_promotion) && "1".equals(goodsInfo.open_promotion) && !isContainDiscount) {
                //从低到高排序
                List<ManJian> sort = sortManJianList(goodsInfo.promotion_rule);

                for (int i = 0; i < sort.size(); i++) {
                    ManJian manJian = sort.get(i);
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
                if (shopCart.getShoppingTotalPrice() > Float.parseFloat(sort.get(sort.size() - 1).amount)) {
                    tvManjian.setText(manJianText("", sort.get(sort.size() - 1).amount, FormatUtil.numFormat(sort.get(sort.size() - 1).discount), limit, "已买"));
                    tvManjian.setVisibility(View.VISIBLE);
                }

            } else {
                tvManjian.setVisibility(View.GONE);
            }
        } else {
            //判断是否开启了满减活动
            if ("1".equals(goodsInfo.open_promotion)) {
                if (goodsInfo.promotion_rule.size() > 0) {
                    String manjian = "";
                    for (int i = 0; i < sortManJianList(goodsInfo.promotion_rule).size(); i++) {
                        ManJian manJian = sortManJianList(goodsInfo.promotion_rule).get(i);
                        if (i == goodsInfo.promotion_rule.size() - 1) {
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
            tvCartCount.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.GONE);
            tvTips.setText(FormatUtil.numFormat(basicPrice) + "元起送");
            tvTips.setVisibility(View.VISIBLE);
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
    private SpannableString manJianText(String yijian, String elseMoney, String discount, String limit, String header) {
        SpannableString s = new SpannableString(yijian + header + elseMoney + "元可减" + discount + "元" + limit);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), yijian.length() + 2, yijian.length() + 2 + elseMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), yijian.length() + 5 + elseMoney.length(), yijian.length() + 5 + elseMoney.length() + discount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
            case R.id.iv_back:
            case R.id.rl_back:
                if (from_page.equals("share")) {
                    Intent intent = new Intent(this, SelectGoodsActivity3.class);
                    intent.putExtra("from_page", "share");
                    intent.putExtra("shop_id", shop_id);
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.rl_share:
                showShareDialog();
                break;
            case R.id.btn_ok:
                llFail.setVisibility(View.GONE);
                loadGoodsDetail();
                break;
            case R.id.ll_add_cart:
                ll_add_cart.setVisibility(View.GONE);
                //显示加减号
                ll_edit_goods.setVisibility(View.VISIBLE);
                llAdd.performClick();
                break;
            case R.id.llAdd:
                if ("1".equals(goodsInfo.memberlimit) || "1".equals(goodsInfo.member_price_used)) {
                    if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                        showVipDialog();
                        break;
                    }
                }
                if (null != shopCart) {
                    if (!shopCart.addShoppingSingle(goodsInfo)) {
                        break;
                    }
                }
                add();
                break;
            case R.id.llMinus:
                if (null != shopCart) {
                    if (!shopCart.subShoppingSingle(goodsInfo)) {
                        break;
                    }
                }
                remove(false);
                break;
            case R.id.rl_select_nature:
                showNatureDialog();
                break;
            case R.id.show_cart:
                showCartDetail();
                break;
            case R.id.rl_count_down:
                //显示限购信息弹框
                showLimitInfoDialog();
                break;
            case R.id.rl_shop:
            case R.id.ll_go_shop:
                //跳转店铺页面
                if ("xuangou".equals(from_page)) {
                    this.finish();
                } else {
                    Intent intent = new Intent(this, SelectGoodsActivity3.class);
                    intent.putExtra("shop_id", goodsInfo.shop_id);
                    intent.putExtra("from_page", "goodsDetail");
                    startActivity(intent);
                    this.finish();
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case VIP_LOGIN:
                    loadGoodsDetail();
                    break;
            }
        }
    }

    private Dialog loginTipDialog;

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
                startActivityForResult(new Intent(GoodsDetailActivity2.this, LoginActivity.class),
                        VIP_LOGIN);
            }
        });
        loginTipDialog.show();
    }

    private void showLimitInfoDialog() {
        View dialogView = View.inflate(this, R.layout.pop_limit_show, null);
        final Dialog limitDialog = new Dialog(this, R.style.CenterDialogTheme2);
        limitDialog.setContentView(dialogView);
        limitDialog.setCanceledOnTouchOutside(false);
        TextView tv_act_limit = (TextView) dialogView.findViewById(R.id.tv_act_limit);
        TextView tv_day_limit = (TextView) dialogView.findViewById(R.id.tv_day_limit);
        TextView tv_limit_total = (TextView) dialogView.findViewById(R.id.tv_limit_total);
        TextView tv_limit_day = (TextView) dialogView.findViewById(R.id.tv_limit_day);
        TextView tv_act_fen = (TextView) dialogView.findViewById(R.id.tv_act_fen);
        TextView tv_day_fen = (TextView) dialogView.findViewById(R.id.tv_day_fen);
        TextView tv_time = (TextView) dialogView.findViewById(R.id.tv_time);
        LinearLayout ll_container1 = (LinearLayout) dialogView.findViewById(R.id.ll_container1);
        LinearLayout ll_container2 = (LinearLayout) dialogView.findViewById(R.id.ll_container2);
        LinearLayout ll_close = (LinearLayout) dialogView.findViewById(R.id.ll_close);

        TextView tv_order_limit = (TextView) dialogView.findViewById(R.id.tv_order_limit);
        TextView tv_order_limit_count = (TextView) dialogView.findViewById(R.id.tv_order_limit_count);
        TextView tv_order_fen = (TextView) dialogView.findViewById(R.id.tv_order_fen);

        if ("1".equals(goodsInfo.is_all_limit)) {
            tv_act_limit.setVisibility(View.VISIBLE);
            tv_limit_total.setVisibility(View.VISIBLE);
            tv_act_fen.setVisibility(View.VISIBLE);
            tv_limit_total.setText(goodsInfo.is_all_limit_num);
        }

        if ("1".equals(goodsInfo.is_customerday_limit)) {
            tv_day_limit.setVisibility(View.VISIBLE);
            tv_limit_day.setVisibility(View.VISIBLE);
            tv_day_fen.setVisibility(View.VISIBLE);
            tv_limit_day.setText(goodsInfo.day_foodnum);
        }
        //初始化限购信息
        if ("1".equals(goodsInfo.is_order_limit)) {
            tv_order_limit.setVisibility(View.VISIBLE);
            tv_order_limit_count.setVisibility(View.VISIBLE);
            tv_order_fen.setVisibility(View.VISIBLE);
            tv_order_limit_count.setText(goodsInfo.order_limit_num);
        }
        tv_time.setText(goodsInfo.start_time + "至" + goodsInfo.stop_time);
        ll_container1.removeAllViews();
        ll_container2.removeAllViews();
        if (null != goodsInfo.limit_time && goodsInfo.limit_time.size() > 0) {
            for (int i = 0; i < goodsInfo.limit_time.size(); i++) {
                GoodsListBean.LimitTime limitTime = goodsInfo.limit_time.get(i);
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

    private void showButton(int count) {
        if (null != goodsInfo.nature && goodsInfo.nature.size() > 0) {
            if (count < 1) {
                tvShowCount.setVisibility(View.GONE);
            } else {
                tvShowCount.setVisibility(View.VISIBLE);
                tvShowCount.setText(count + "");
            }
        } else {
            if (count < 1) {
                llMinus.setVisibility(View.GONE);
                tvCount.setVisibility(View.GONE);
                ll_add_cart.setVisibility(View.VISIBLE);
                ll_edit_goods.setVisibility(View.GONE);
            } else {
                llMinus.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText(count + "");
                ll_add_cart.setVisibility(View.GONE);
                ll_edit_goods.setVisibility(View.VISIBLE);
            }
        }
    }

    public void add() {
        int count = shopCart.getGoodsCount(goodsInfo.id);
        showButton(count);
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        ivAdd.getLocationInWindow(addLocation);
        imgCart.getLocationInWindow(cartLocation);

        AnimatorView animatorView = new AnimatorView(this);
        animatorView.setStartPosition(new Point(addLocation[0], addLocation[1] - llAdd.getHeight()));
        animatorView.setEndPosition(new Point(cartLocation[0] + imgCart.getWidth() / 2, cartLocation[1] - imgCart.getHeight()));
        mainLayout.addView(animatorView);
        animatorView.setAnimatorListener(this);
        animatorView.startBeizerAnimation();
        showCartData();
    }

    public void addFromDialog(View view) {
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

    public void remove(boolean isNature) {
       /* if (!isNature) {
            //只处理普通商品
            int count = shopCart.getGoodsCount(goodsInfo.id);
            showButton(count);
        }else {

        }*/
        //只处理普通商品
        int count = shopCart.getGoodsCount(goodsInfo.id);
        showButton(count);
        showCartData();
    }

    private ArrayList<GoodsListBean.GoodsInfo> natureGoodsList = new ArrayList<>();
    private TextView tvNatureCount;
    private TextView tvTotalPrice;
    private ShopNatureGoodsAdapter natureGoodsAdapter;
    private RecyclerView rv_nature_container;
    private boolean isFirst;

    public void showNatureDialog() {
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
        for (GoodsListBean.GoodsNature goodsNature : goodsInfo.nature) {
            for (int i = 0; i < goodsNature.data.size(); i++) {
                if (i == 0) {
                    goodsNature.data.get(i).is_selected = true;
                } else {
                    goodsNature.data.get(i).is_selected = false;
                }
            }

        }
        if ("1".equals(goodsInfo.switch_discount)) {
            goodsInfo.isUseDiscount = true;
        }
        for (GoodsListBean.GoodsInfo natureGoods : shopCart.getGoodsList(goodsInfo.id)) {
            if (natureGoods.id.equals(goodsInfo.id)) {
                natureGoodsList.add(natureGoods);
            }
        }
        if (natureGoodsList.size() > 0) {
            isFirst = false;
        } else {
            isFirst = true;
            natureGoodsList.add(goodsInfo);
        }
        natureGoodsAdapter = new ShopNatureGoodsAdapter(this, natureGoodsList, shopCart, tvTotalPrice);
        rv_nature_container.setAdapter(natureGoodsAdapter);
        natureGoodsAdapter.setGoodsPosition(0);
        natureGoodsAdapter.setDeletNatureListener(this);
        rv_nature_container.setLayoutManager(new LinearLayoutManager(this));
        goodsName.setText(goodsInfo.name);
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

                if (shopCart.addShoppingNature(goodsInfo,isFirst)) {
                    addCart.setVisibility(View.INVISIBLE);
                    ll_edit_goods.setVisibility(View.VISIBLE);
                    if (isFirst) {
                        natureGoodsList.clear();
                        isFirst = false;
                    }
                    natureGoodsList.clear();
                    natureGoodsList.addAll(shopCart.getGoodsList(goodsInfo.id));
                    natureGoodsAdapter.notifyDataSetChanged();
                    natureGoodsAdapter.updateTotalPrice();
                    rv_nature_container.smoothScrollToPosition(natureGoodsList.size() - 1);
                    int count = shopCart.getGoodsCount(goodsInfo.id);
                    tvNatureCount.setText(String.valueOf(count));
                    showButton(count);
                    addFromDialog(ll_add);
                }
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
                    int count = shopCart.getGoodsCount(goodsInfo.id);
                    tvNatureCount.setText(count+"");
                    natureGoodsList.clear();
                    natureGoodsList.addAll(shopCart.getGoodsList(goodsInfo.id));
                    natureGoodsAdapter.notifyDataSetChanged();
                    natureGoodsAdapter.updateTotalPrice();
                    if (natureGoodsList.size() == 0) {
                        remove(true);
                        if (null != centerPopView) {
                            centerPopView.dismissCenterPopView();
                        }
                        return;
                    }
                    remove(true);
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

        if (shopCart.subShoppingSingle(natureGoodsList.get(position))) {
            int count = shopCart.getGoodsCount(goods.id);
            tvNatureCount.setText(count + "");
            natureGoodsList.clear();
            natureGoodsList.addAll(shopCart.getGoodsList(goods.id));
            natureGoodsAdapter.notifyDataSetChanged();
            natureGoodsAdapter.updateTotalPrice();
            //减去存在购物车最后一个商品
            if (natureGoodsList.size() == 0) {
                remove(true);
                if (null != centerPopView) {
                    centerPopView.dismissCenterPopView();
                }
                return;
            }
            remove(true);
        }
    }

    @Override
    public void updateTotalPrice() {
        showCartData();
    }

    //弹出购物清单
    private void showCartDetail() {
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            ShopCartDialog2 dialog = new ShopCartDialog2(this, shopCart, shop_id, R.style.cartdialog, isEffectiveVip);
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
           /* if (shopCartPopupWindow != null){
                boolean b = tvManjian.getVisibility() == View.VISIBLE ;
                shopCartPopupWindow.bindView(shopCart, shop_id,isEffectiveVip,b);
                shopCartPopupWindow.setIShopCartDialog(this);
                if (!shopCartPopupWindow.isShowing()){
                    shopCartPopupWindow.showCartView();
                }else {
                    shopCartPopupWindow.dismiss();
                }

            }*/
        }
    }

    @Override
    public void loadDetail(GoodsDetailBean detail) {
        if (null != detail.data && detail.data.size() > 0) {
            goodsInfo = detail.data.get(0);
            shop_id = goodsInfo.shop_id;
            if ("1".equals(goodsInfo.switch_discount)) {
                goodsInfo.type_id = "discount";
            }

            if ("1".equals(goodsInfo.IsShopMember)) {
                isShopMember = true;
                //是店铺会员
                if ("0".equals(goodsInfo.memberFreeze)) {
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

            SharedPreferencesUtil.saveIsEffectVip(isEffectiveVip);
            SharedPreferencesUtil.saveIsShopMember(isShopMember);
            SharedPreferencesUtil.saveIsFreezenMember(isFreezenMember);

            if ("0".equals(goodsInfo.is_nature)) {
                if (null != goodsInfo.nature) {
                    goodsInfo.nature.clear();
                }
            }
            if ("wpage".equals(from_page)) {
                shop_id = goodsInfo.shop_id;
                if ("0".equals(goodsInfo.is_nature)) {
                    if (null != goodsInfo.nature) {
                        goodsInfo.nature.clear();
                    }
                }
                //查询本地数据
                queryCartGoods();
                tvShopName.setText(goodsInfo.shopname);

            } else if ("WGoodsList".equals(from_page)) {
                tvShopName.setText(goodsInfo.shopname);

            }
            initGoodsShow();
            basicPrice = detail.data.get(0).minBasicprice;
            showCartData();
            foodLimitType = detail.data.get(0).foodLimitType;
            timeRelativeHD = detail.data.get(0).timeRelativeHD;
            if (1 == goodsInfo.worktime) {
                showLimitTime();
            }
            if (!TextUtils.isEmpty(detail.data.get(0).descript)) {
                llGoodsInfo.setVisibility(View.VISIBLE);
                tvInfo.setText("商品信息");
                tvGoodsInfo.setText(detail.data.get(0).descript);
            } else {
                llGoodsInfo.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(detail.data.get(0).description)) {
                llGoodsDes.setVisibility(View.VISIBLE);
                tvDesc.setText("商品描述");
                showWebViewData(detail.data.get(0).description);
            } else {
                llGoodsDes.setVisibility(View.GONE);
            }
        }
        //关闭动画
        closeLoadingView();
    }

    @Override
    public void loadDetail(PackageDetailBean detail) {
        if (null != detail.data && detail.data.size() > 0) {
            GoodsListBean.GoodsPackage goodsPackage = detail.data.get(0);
            goodsInfo = new GoodsListBean().new GoodsInfo();
            goodsInfo.shop_id = goodsPackage.shop_id;
            goodsInfo.id = goodsPackage.id;
            goodsInfo.name = goodsPackage.name;
            goodsInfo.img = goodsPackage.img;
            goodsInfo.image = goodsPackage.image;
            goodsInfo.price = goodsPackage.price;
            goodsInfo.is_dabao = goodsPackage.is_dabao;
            goodsInfo.dabao_money = goodsPackage.dabao_money;
            goodsInfo.unit = goodsPackage.unit;
            goodsInfo.packageNature = goodsPackage.nature;
            goodsInfo.descript = goodsPackage.descript;
            goodsInfo.description = goodsPackage.description;
            goodsInfo.showsales = goodsPackage.showsales;
            goodsInfo.worktime = goodsPackage.worktime;
            goodsInfo.outtime_info = goodsPackage.outtime_info;
            goodsInfo.switch_discount = goodsPackage.switch_discount;
            goodsInfo.num_discount = goodsPackage.num_discount;
            goodsInfo.rate_discount = goodsPackage.rate_discount;
            goodsInfo.open_promotion = goodsPackage.open_promotion;
            goodsInfo.promotion_rule = goodsPackage.promotion_rule;
            goodsInfo.is_only_promotion = goodsPackage.is_only_promotion;
            goodsInfo.min_buy_count = goodsPackage.min_buy_count;
            goodsInfo.discount_show_type = goodsPackage.discount_show_type;
            goodsInfo.original_type_id = goodsPackage.original_type_id;
            if ("1".equals(goodsInfo.switch_discount)) {
                goodsInfo.type_id = "discount";
            }
            if ("wpage".equals(from_page)) {
                tvShopName.setText(goodsInfo.shopname);
                //查询本地数据
                queryCartGoods();
            } else if ("WGoodsList".equals(from_page)) {
                tvShopName.setText(goodsInfo.shopname);
            }
            initGoodsShow();
            basicPrice = detail.data.get(0).minBasicprice;
            showCartData();
            foodLimitType = goodsInfo.foodLimitType;
            timeRelativeHD = goodsInfo.timeRelativeHD;
            if (1 == goodsInfo.worktime || !StringUtils.isEmpty(goodsInfo.outtime_info)) {
                showLimitTime();
            }
            if (!TextUtils.isEmpty(detail.data.get(0).descript)) {
                llGoodsInfo.setVisibility(View.VISIBLE);
                tvInfo.setText("套餐信息");
                tvGoodsInfo.setText(detail.data.get(0).descript);
            } else {
                llGoodsInfo.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(detail.data.get(0).description)) {
                llGoodsDes.setVisibility(View.VISIBLE);
                tvDesc.setText("套餐描述");

            } else {
                llGoodsDes.setVisibility(View.GONE);
            }
        }
        //关闭动画
        closeLoadingView();

    }

    private void showWebViewData(String data) {
        String html = "";
        String css = "<style type=\"text/css\"> img {" +
                "width:100%;" +//限定图片宽度填充屏幕
                "height:auto;" +//限定图片高度自动
                "}" +
                "body {" +
                "margin-right:10px;" +//限定网页中的文字右边距为15px(可根据实际需要进行行管屏幕适配操作)
                "margin-left:10px;" +//限定网页中的文字左边距为15px(可根据实际需要进行行管屏幕适配操作)
                "margin-top:10px;" +//限定网页中的文字上边距为15px(可根据实际需要进行行管屏幕适配操作)
                "font-size:40px;" +//限定网页中文字的大小为40px,请务必根据各种屏幕分辨率进行适配更改
                "word-wrap:break-word;" +//允许自动换行(汉字网页应该不需要这一属性,这个用来强制英文单词换行,类似于word/wps中的西文换行)
                "}" +
                "</style>";
        html = "<html><header>" + css + "</header>" + data + "</html>";
        webviewGoods.loadData(getWebContent(html), "text/html; charset=UTF-8", null);
    }

    @Override
    public void loadFail() {
        if (null != animationDrawable) {
            animationDrawable.stop();
        }
        llFail.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
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

    private Dialog shareDialog;
    private String picUrl;

    private void showShareDialog() {
        if (shareDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
            final Tencent tencent = Tencent.createInstance(Const.QQ_APP_ID, GoodsDetailActivity2.this);
            if (null != goodsInfo) {
                picUrl = goodsInfo.img;
                if (!picUrl.startsWith("http")) {
                    picUrl = "https://img.lewaimai.com" + picUrl;
                }
            }
            final String des = "这菜味道真的很棒，点击就能下单，快来尝尝。";
            //微信好友
            ((LinearLayout) view.findViewById(R.id.ll_share_friend)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareUtils().WXShareUrl(msgApi, RetrofitManager.BASE_URL_H5 + "h5/lwm/share/sharegoods?admin_id=" + admin_id + "&shop_id=" + shop_id
                            + "&food_id=" + goodsInfo.id + "&food_type=" + food_type + "&bundleName="+getString(R.string.share_scheme) + "&customerapp_id=" + RetrofitUtil.ADMIN_APP_ID, picUrl, goodsInfo.name, des, ShareUtils.WX_SEESSION);
                    shareDialog.dismiss();
                }
            });
            //朋友圈
            ((LinearLayout) view.findViewById(R.id.ll_share_online)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareUtils().WXShareUrl(msgApi, RetrofitManager.BASE_URL_H5 + "h5/lwm/share/sharegoods?admin_id=" + admin_id + "&shop_id=" + shop_id + "&food_id="
                            + goodsInfo.id + "&food_type=" + food_type + "&bundleName="+getString(R.string.share_scheme) + "&customerapp_id=" + RetrofitUtil.ADMIN_APP_ID, picUrl, goodsInfo.name, des, ShareUtils.WX_TIME_LINE);
                    shareDialog.dismiss();
                }
            });
            //qq
            ((LinearLayout) view.findViewById(R.id.ll_share_qq)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtils.QQshare(tencent, GoodsDetailActivity2.this, goodsInfo.name, RetrofitManager.BASE_URL_H5 + "h5/lwm/share/sharegoods?admin_id=" + admin_id
                            + "&shop_id=" + shop_id + "&food_id=" + goodsInfo.id + "&food_type=" + food_type +"&bundleName="+getString(R.string.share_scheme) + "&customerapp_id=" + RetrofitUtil.ADMIN_APP_ID, des, picUrl);
                    shareDialog.dismiss();
                }
            });
            //取消
            ((TextView) view.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareDialog.dismiss();
                }
            });
            shareDialog = DialogUtils.BottonDialog(this, view);
        }
        shareDialog.show();
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
    public void updateCartData() {
        int count = shopCart.getGoodsCount(goodsInfo.id);
        showButton(count);
        showCartData();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (from_page.equals("share")) {
                Intent intent = new Intent(this, SelectGoodsActivity3.class);
                intent.putExtra("from_page", "share");
                intent.putExtra("shop_id", shop_id);
                startActivity(intent);
            }
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在Activity销毁之前要消失掉dialog，否则报Android.view.WindowLeaked异常
        if (null != centerPopView) {
            centerPopView.dismissCenterPopView();
        }

        //关闭计时器
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {

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
        admin_id = Const.ADMIN_ID;
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
        Intent cartIntent = new Intent(GoodsDetailActivity2.this, ShoppingCartActivity2.class);
        cartIntent.putExtra("shop_id", shop_id);
        startActivity(cartIntent);
    }

    @Override
    public void showCheckOrderView(String msg) {
        showGoodsTypeNeedDialog(msg);
    }
}

package com.newsuper.t.consumer.function.selectgoods.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.CartGoodsModel;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.OrderPayResultBean;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.bean.ShoppingCartBean;
import com.newsuper.t.consumer.function.order.activity.MyOrderActivity;
import com.newsuper.t.consumer.function.order.fragment.OrderFragment;
import com.newsuper.t.consumer.function.pay.alipay.Alipay;
import com.newsuper.t.consumer.function.selectgoods.adapter.AddServiceAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter.CartGoodsAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter.DayAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter.PayTypeAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter.PresetAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter.TimeAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter.YuseAdapter;
import com.newsuper.t.consumer.function.selectgoods.inter.ICheckGoodsMustView;
import com.newsuper.t.consumer.function.selectgoods.inter.IShoppingCartView;
import com.newsuper.t.consumer.function.selectgoods.presenter.CheckGoodsMustPresenter;
import com.newsuper.t.consumer.function.selectgoods.presenter.ShoppingCartPresenter;
import com.newsuper.t.consumer.function.selectgoods.request.IsCollectRequest;
import com.newsuper.t.consumer.function.top.presenter.LocationPresenter;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.utils.WXResult;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.ListViewForScrollView;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.MapContainer;
import com.newsuper.t.consumer.wxapi.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单结算
 */
public class ShoppingCartActivity2 extends BaseActivity implements View.OnClickListener, IShoppingCartView, ICheckGoodsMustView
        , AMapLocationListener, AMap.OnMapLoadedListener, LocationSource {
    private static final int ADDRESS_ADD_CODE = 13;
    private static final int COUPON_CODE = 14;
    private static final int YUSE_CODE = 15;
    private static final int BEIZHU_CODE = 16;
    private static final String TIPS_USE = "货到付款不可用";
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tvb_shop_send)
    TextView tvShopSend;
    @BindView(R.id.tvb_shop_take)
    TextView tvShopTake;
    @BindView(R.id.tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.tv_address_phone)
    TextView tvAddressPhone;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_address_ti)
    TextView tv_address_ti;
    @BindView(R.id.tv_take_phone)
    EditText tv_take_phone;
    @BindView(R.id.mapview)
    MapView mapView;
    @BindView(R.id.map_container)
    MapContainer map_container;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_send_time)
    TextView tvSendTime;
    @BindView(R.id.ll_send_time)
    LinearLayout llSendTime;
    @BindView(R.id.tv_take_time)
    TextView tvTakeTime;
    @BindView(R.id.ll_take_time)
    LinearLayout llTakeTime;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.listview_goods)
    ListViewForScrollView listviewGoods;
    @BindView(R.id.tv_goods_total_price)
    TextView tvGoodsTotalPrice;
    @BindView(R.id.ll_goods_total_price)
    LinearLayout llGoodsTotalPrice;
    @BindView(R.id.tv_box_fee)
    TextView tvBoxFee;
    @BindView(R.id.ll_food_box)
    LinearLayout llFoodBox;
    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;
    @BindView(R.id.ll_delivery)
    LinearLayout llDelivery;
    @BindView(R.id.tv_service_fee)
    TextView tvServiceFee;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.tv_jian)
    TextView tvJian;
    @BindView(R.id.tv_jian_fee)
    TextView tvJianFee;
    @BindView(R.id.ll_jian)
    LinearLayout llJian;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.tv_new_fee)
    TextView tvNewFee;
    @BindView(R.id.ll_new_user)
    LinearLayout llNewUser;
    @BindView(R.id.tv_shop_new_user)
    TextView tvShopNewUser;
    @BindView(R.id.tv_shop_new_fee)
    TextView tvShopNewFee;
    @BindView(R.id.ll_shop_new_user)
    LinearLayout llShopNewUser;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_vip_fee)
    TextView tvdiscountFee;
    @BindView(R.id.ll_vip)
    LinearLayout llVip;
    @BindView(R.id.ll_activity)
    LinearLayout llActivity;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_coupon_price)
    TextView tvCouponPrice;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.vv_bei)
    View vvBei;
    @BindView(R.id.tv_beizhu_value)
    TextView tvBeizhuValue;
    @BindView(R.id.ll_beizhu)
    LinearLayout llBeizhu;
    @BindView(R.id.tv_tip_address)
    TextView tvTipAddress;
    @BindView(R.id.ll_tip_address)
    LinearLayout llTipAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_waimai)
    LinearLayout ll_waimai;
    @BindView(R.id.ll_add_address)
    LinearLayout llAddAddress;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.tv_delivery)
    TextView tvDelivery;
    @BindView(R.id.tv_total_pay)
    TextView tvTotalPay;
    @BindView(R.id.tv_total_coupon)
    TextView tvTotalCoupon;
    @BindView(R.id.tv_delivery_type)
    TextView tv_delivery_type;
    @BindView(R.id.tv_to_pay)
    TextView tvToPay;
    @BindView(R.id.btn_load_again)
    Button btnLoadAgain;
    @BindView(R.id.ll_load_fail)
    LinearLayout llLoadFail;
    @BindView(R.id.rl_data)
    RelativeLayout rlData;
    @BindView(R.id.listview_preset)
    ListViewForScrollView listviewPreset;
    @BindView(R.id.ll_cart_bottom)
    LinearLayout llCartBottom;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.listview_service)
    ListViewForScrollView listviewService;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.listview_yuse)
    ListViewForScrollView listviewYuse;
    @BindView(R.id.vv_line)
    View vLine;
    @BindView(R.id.tv_order_tip)
    TextView tvOrderTip;
    @BindView(R.id.ll_order_tip)
    LinearLayout llOrderTip;
    @BindView(R.id.ll_take_phone)
    LinearLayout ll_take_phone;
    @BindView(R.id.tv_manzeng)
    TextView tvManzeng;
    @BindView(R.id.tv_manzeng_fee)
    TextView tvManzengFee;
    @BindView(R.id.ll_manzeng)
    LinearLayout llManzeng;
    @BindView(R.id.tv_yuding)
    TextView tv_yuding;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_logo)
    LinearLayout ll_logo;
    private ShoppingCartPresenter cartPresenter;
    private CheckGoodsMustPresenter checkPresenter;
    private AddressBean.AddressList mAddressList;
    private PresetAdapter presetAdapter;
    private YuseAdapter yuseAdapter;
    private ArrayList<ShoppingCartBean.DataBean.OrderFieldBean> orderFieldList;
    private Map<Integer, ShoppingCartBean.DataBean.ValueBean> mapYuse;
    private ArrayList<ShoppingCartBean.DataBean.CouponListBean> couponList;
    private ArrayList<GoodsListBean.GoodsInfo> goodsInfos;
    private int presetSelect = -1;
    private double allGoodsPrice = 0;//商品总价(不含会员价)
    private double finalGoodsPrice = 0;//最终商品总价（会员价后）
    private double disGoodsPrice = 0;//计算打折的商品总价
    private double totalPrice;//总价
    private double basePrice;//基本价
    private double totalDiscount;//优惠t
    private double totalPay;//待支付
    private double deliveryFee;//配送费
    private double basicSendPrice;//起送价
    private double foodBoxFee;//餐盒费
    private double presetFee;//总预设费
    private double couponFee;//优惠券
    private double promotionFee;//满减
    private double discountFee;//打折
    private double chaFee;//差价
    private double vipPriceFee;//会员差价
    private boolean isVipDeliveryFree;
    private boolean isHasSpecailGoods;
    private ArrayList<String> days;
    private ArrayList<String> cSendTimes;
    private ArrayList<String> tSendTimes;
    private ArrayList<String> cTakeTimes;
    private ArrayList<String> tTakeTimes;
    private ShoppingCartBean.DataBean cartDataBean;
    private ArrayList<ShoppingCartBean.DataBean.CouponListBean> nCouponLists;
    private ArrayList<ShoppingCartBean.DataBean.CouponListBean> uCouponLists;
    private CartGoodsAdapter goodsAdapter;
    private ArrayList<CartGoodsModel> goodsModels;
    private AddServiceAdapter serviceAdapter;
    private String shop_id;
    private int MODE_SELECT = 0;//是商家配送还是自提，0表示商家配送1表示自提
    private String pay_type = "online";//支付方式
    private String manzeng_name;//满赠
    private String online_pay_type = "weixinzhifu";//在线支付方式
    private String alipy_name = "shanghuzhifubao";
    private String weixinzhifu_type;
    private String zhifubaozhifu_type;
    private ShoppingCartBean.DataBean.CouponListBean couponListBean;
    private String coupon_id = "-1";//优惠券
    private String deliverydaynum;//顾客所选配送日期1:今天,2:明天,3:后天，一次类推，最大值为7
    private String delivertime = "", send_day;//顾客所选配送时间
    private String deliverydaynum_take, take_day;//顾客所选配送日期1:今天,2:明天,3:后天，一次类推，最大值为7
    private String delivertime_take = "";//顾客所选配送时间
    private String note = "";//备注
    private String captcha = "";//首次下单验证码，默认为空
    private IWXAPI msgApi;//微信支付
    private OrderPayResultBean.ZhiFuParameters parameters;
    private int second = 60;
    private boolean isTimeOut = true;
    private boolean isOnWork;
    private boolean isUseVipPrice;
    public static String order_id;
    public String order_no;
    private LoadingDialog2 loadingDialog;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private String couponUsed, beforePayType = "",selectPayType;
    public ArrayList<String> payTypes;
    private ArrayList<String> goodids;
    private static String diver = "｜";
    private ShopCart2 shopCart2;
    private float oldTotalPrice;
    private int oldAccount;
    private boolean isFirst = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (second >= 0) {
                        if (second == 60) {
                            tvCode.setBackgroundResource(R.drawable.shape_cart_send_code_second);
                            tvCode.setOnClickListener(null);
                            tvCodeYuyin.setTextColor(ContextCompat.getColor(ShoppingCartActivity2.this, R.color.text_color_99));
                            tvCodeYuyin.setOnClickListener(null);
                        }
                        tvCode.setText(second + "s");
                        second--;
                        handler.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        isTimeOut = true;
                        second = 60;
                        tvCode.setText("重新获取");
                        tvCode.setBackgroundResource(R.drawable.shape_cart_send_code_normal);
                        tvCode.setOnClickListener(ShoppingCartActivity2.this);
                        tvCodeYuyin.setTextColor(ContextCompat.getColor(ShoppingCartActivity2.this, R.color.theme_red));
                        tvCodeYuyin.setOnClickListener(ShoppingCartActivity2.this);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);

        map_container.setScrollView(scrollView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) map_container.getLayoutParams();
        int w = UIUtils.getWindowWidth() - UIUtils.dip2px(20);
        int h = (w * 130) / 335;
        layoutParams.height = h;
        LogUtil.log("map_container", " w = " + w);
        LogUtil.log("map_container", " h = " + h);
        map_container.setLayoutParams(layoutParams);

        toolbar.setTitleTextColor(R.color.text_color_33);
        toolbar.setBackIcon(R.mipmap.icon_back_2x);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }


    @Override
    public void initData() {
        msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        msgApi.registerApp(Constants.APP_ID);
        loadingDialog = new LoadingDialog2(this);
        uCouponLists = new ArrayList<>();
        nCouponLists = new ArrayList<>();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_shopping_cart_2);
        ButterKnife.bind(this);
        toolbar.setTitleText("订单结算");
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
//        ll_take_phone.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        llSendTime.setOnClickListener(this);
        llTakeTime.setOnClickListener(this);
        llPay.setOnClickListener(this);
        llCoupon.setOnClickListener(this);
        llBeizhu.setOnClickListener(this);
        llAddAddress.setOnClickListener(this);
        btnLoadAgain.setOnClickListener(this);
        tvToPay.setOnClickListener(this);
        tvShopSend.setOnClickListener(this);
        tvShopTake.setOnClickListener(this);
        ll_logo.setOnClickListener(this);

        shop_id = getIntent().getStringExtra("shop_id");
        shopCart2 = new ShopCart2();
        goodsModels = new ArrayList<>();
        goodsAdapter = new CartGoodsAdapter(this, goodsModels);
        listviewGoods.setAdapter(goodsAdapter);
        goodsInfos = BaseApplication.greenDaoManager.getGoodsListByShopId(shop_id);
        shopCart2.addGoodsListFromDB(goodsInfos);
        oldTotalPrice = shopCart2.getShoppingTotalPrice();
        oldAccount = shopCart2.getShoppingAccount();
        goodids = new ArrayList<>();
        for (GoodsListBean.GoodsInfo goodsInfo : goodsInfos)
            goodids.add(goodsInfo.id);
//        goodsPackages = BaseApplication.greenDaoManager.getPackageListByShopId(shop_id);
        cartPresenter = new ShoppingCartPresenter(this);
        checkPresenter = new CheckGoodsMustPresenter(this);
        loadData();
    }

    private void showGoodsModels(ShoppingCartBean.DataBean bean) {
        updateCart(bean);
        goodsModels.clear();
        goodsModels.addAll(CartGoodsModel.getModels(goodsInfos));
        if (goodsModels.size() > 0) {
            isHasSpecailGoods = CartGoodsModel.isHasSpecialGoods(goodsModels);
            for (CartGoodsModel model : goodsModels) {
                goodids.add(model.id);
                //计算商品（商品价格 + 属性价格）* 个数
                //非特价商品
                if ("1".equals(model.switch_discount)) {
                    if (model.isSpecialGoods) {
                        allGoodsPrice += (FormatUtil.numDouble(model.price) + model.natruePrice) * (model.count == 0 ? 1 : model.count);
                    } else {
                        //超过限购数，按原价
                        allGoodsPrice += (FormatUtil.numDouble(model.formerprice) + model.natruePrice) * (model.count == 0 ? 1 : model.count);
                    }
                } else {
                    allGoodsPrice += (FormatUtil.numDouble(model.price) + model.natruePrice) * (model.count == 0 ? 1 : model.count);
                }
                // 餐盒费
                foodBoxFee += model.dabaoFee * (model.count == 0 ? 1 : model.count);
                if (model.type == 0 && "1".equals(model.member_price_used) && "0".equals(model.switch_discount)) {
                    vipPriceFee += (Double.parseDouble(model.price) - Double.parseDouble(model.member_price)) * (model.count == 0 ? 1 : model.count);
                }

            }

            allGoodsPrice = getFormatData(allGoodsPrice);
            goodsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 更新购物车商品
     */
    private void updateCart(ShoppingCartBean.DataBean bean) {
        allGoodsPrice = 0;//商品总价(不含会员价)
        finalGoodsPrice = 0;//最终商品总价（会员
        disGoodsPrice = 0;//计算打折的商品总价
        totalPrice = 0;//总价
        basePrice = 0;//基本价
        totalDiscount = 0;//优惠t
        totalPay = 0;//待支付
        deliveryFee = 0;//配送费
        basicSendPrice = 0;//起送价
        foodBoxFee = 0;//餐盒费
        presetFee = 0;//总预设费
        couponFee = 0;//优惠券
        promotionFee = 0;//满减
        discountFee = 0;//打折
        chaFee = 0;//差价
        vipPriceFee = 0;//会员差价
        boolean hasMemberLimit = false;
        ArrayList<GoodsListBean.GoodsInfo> foodInfo = bean.foodInfo;
        if (null == foodInfo || foodInfo.size() == 0)
            return;
        ArrayList<GoodsListBean.GoodsInfo> tempList = new ArrayList<>();
        for (GoodsListBean.GoodsInfo oldGoodsInfo : goodsInfos) {
            for (GoodsListBean.GoodsInfo newGoodsInfo : foodInfo) {
                if (oldGoodsInfo.id.equals(newGoodsInfo.id)) {
                    BaseApplication.greenDaoManager.deleteGoods(oldGoodsInfo);
                    GoodsListBean.GoodsInfo tempGoods = oldGoodsInfo;

                    oldGoodsInfo.shop_id = newGoodsInfo.shop_id;
                    oldGoodsInfo.id = newGoodsInfo.id;
                    oldGoodsInfo.name = newGoodsInfo.name;
                    oldGoodsInfo.img = newGoodsInfo.img;
                    oldGoodsInfo.price = newGoodsInfo.price;
                    oldGoodsInfo.formerprice = newGoodsInfo.formerprice;
                    oldGoodsInfo.has_formerprice = newGoodsInfo.has_formerprice;
                    oldGoodsInfo.type_id = newGoodsInfo.type_id;
                    oldGoodsInfo.second_type_id = newGoodsInfo.second_type_id;

                    if (isNatureChange(oldGoodsInfo,newGoodsInfo))//判断属性是否改变
                        break;

                    oldGoodsInfo.unit = newGoodsInfo.unit;
                    oldGoodsInfo.status = newGoodsInfo.status;
                    oldGoodsInfo.stock = newGoodsInfo.stock;
                    oldGoodsInfo.stockvalid = newGoodsInfo.stockvalid;
                    oldGoodsInfo.is_nature = newGoodsInfo.is_nature;
                    oldGoodsInfo.is_dabao = newGoodsInfo.is_dabao;
                    oldGoodsInfo.dabao_money = newGoodsInfo.dabao_money;
                    oldGoodsInfo.member_price_used = newGoodsInfo.member_price_used;
                    oldGoodsInfo.member_price = newGoodsInfo.member_price;
                    oldGoodsInfo.member_grade_price = newGoodsInfo.member_grade_price;
                    oldGoodsInfo.memberlimit = newGoodsInfo.memberlimit;
                    oldGoodsInfo.is_limitfood = newGoodsInfo.is_limitfood;
                    oldGoodsInfo.datetage = newGoodsInfo.datetage;
                    oldGoodsInfo.timetage = newGoodsInfo.timetage;
                    oldGoodsInfo.is_all_limit = newGoodsInfo.is_all_limit;
                    oldGoodsInfo.hasBuyNum = newGoodsInfo.hasBuyNum;
                    oldGoodsInfo.is_all_limit_num = newGoodsInfo.is_all_limit_num;
                    oldGoodsInfo.is_customerday_limit = newGoodsInfo.is_customerday_limit;
                    oldGoodsInfo.hasBuyNumByDay = newGoodsInfo.hasBuyNumByDay;
                    oldGoodsInfo.day_foodnum = newGoodsInfo.day_foodnum;
                    oldGoodsInfo.switch_discount = newGoodsInfo.switch_discount;
                    oldGoodsInfo.num_discount = newGoodsInfo.num_discount;
                    oldGoodsInfo.rate_discount = newGoodsInfo.rate_discount;
                    oldGoodsInfo.discount_type = newGoodsInfo.discount_type;
                    oldGoodsInfo.order_limit_num = newGoodsInfo.order_limit_num;
                    oldGoodsInfo.is_order_limit = newGoodsInfo.is_order_limit;
                    oldGoodsInfo.min_buy_count = newGoodsInfo.min_buy_count;
                    oldGoodsInfo.discount_show_type = newGoodsInfo.discount_show_type;
                    oldGoodsInfo.original_type_id = newGoodsInfo.original_type_id;

                    if ("1".equals(newGoodsInfo.memberlimit))
                        hasMemberLimit = true;
                    tempList.add(tempGoods);
                    BaseApplication.greenDaoManager.addGoods(tempGoods);
                    break;
                }
            }
        }

        goodsInfos.clear();
        goodsInfos.addAll(tempList);
        shopCart2.clear();
        shopCart2.addGoodsListFromDB(goodsInfos);
        float newTotalPrice = shopCart2.getShoppingTotalPrice();
        int newAccount = shopCart2.getShoppingAccount();
        if (isFirst) {
            if ("1".equals(bean.memberFreeze) && hasMemberLimit) {
                showActTipDialog("会员已被冻结，请重新选购商品", true);
            } else if (oldAccount != newAccount) {
                showActTipDialog("商品信息已发生变化，请确认后支付", true);
            } else if (oldTotalPrice != newTotalPrice) {
                showActTipDialog("订单金额已发生变化，请确认后支付", true);
            }
        }
        isFirst = false;
    }

    private boolean isNatureChange(GoodsListBean.GoodsInfo oldGoodsInfo,GoodsListBean.GoodsInfo newGoodsInfo) {
        if (!oldGoodsInfo.is_nature.equals(newGoodsInfo.is_nature)) {
            return true;
        }
        if ("1".endsWith(oldGoodsInfo.is_nature) && null != oldGoodsInfo.nature && null != newGoodsInfo.nature) {
            if (oldGoodsInfo.nature.size() != newGoodsInfo.nature.size()) {
                return true;
            }
            if (oldGoodsInfo.nature.size() > 0) {
                boolean hasNature = false;
                for (GoodsListBean.GoodsNature oldNature : oldGoodsInfo.nature) {
                    for (GoodsListBean.GoodsNature newNature : newGoodsInfo.nature) {
                        if (oldNature.naturename.equals(newNature.naturename)) {
                            hasNature = true;
                            if (!oldNature.maxchoose.equals(newNature.maxchoose)) {
                                return true;
                            }
                            if (oldNature.data.size() != newNature.data.size()) {
                                return true;
                            }
                            boolean hasNatureData = false;
                            for (GoodsListBean.GoodsNatureData oldNatureData : oldNature.data) {
                                for (GoodsListBean.GoodsNatureData newNatureData : newNature.data) {
                                    if (oldNatureData.naturevalue.equals(newNatureData.naturevalue)) {
                                        hasNatureData = true;
                                        if (!oldNatureData.price.equals(newNatureData.price)) {
                                            oldNatureData.price = newNatureData.price;
                                        }
                                        break;
                                    }
                                }
                            }
                            if (!hasNatureData)
                                return true;
                            break;
                        }
                    }
                }
                if (!hasNature)
                    return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //配送
            case R.id.tvb_shop_send:
                MODE_SELECT = 0;
                ll_waimai.setVisibility(View.VISIBLE);
                llType.setBackgroundResource(R.mipmap.icon_send_bg_3x);
                if (mAddressList == null || StringUtils.isEmpty(mAddressList.name)) {
                    llAddAddress.setVisibility(View.VISIBLE);
                    llAddress.setVisibility(View.GONE);
                    llTipAddress.setVisibility(View.GONE);
                } else {
                    llAddAddress.setVisibility(View.GONE);
                    llAddress.setVisibility(View.VISIBLE);
                    llTipAddress.setVisibility(View.VISIBLE);
                }
                if (cartDataBean != null) {
                    if ("1".equals(cartDataBean.shopModel.opendelivertime)) {
                        llSendTime.setVisibility(View.VISIBLE);
                    } else {
                        llSendTime.setVisibility(View.GONE);
                    }
                    tv_delivery_type.setVisibility(View.VISIBLE);
                    if ("1".equals(cartDataBean.shopModel.delivery_mode)) {
                        tv_delivery_type.setText("平台专送");
                    } else {
                        tv_delivery_type.setText("商家配送");
                    }
                }
                llInfo.setVisibility(View.GONE);
                changDataToView(cartDataBean, basePrice);
                break;
            //自提
            case R.id.tvb_shop_take:
                MODE_SELECT = 1;
                tv_delivery_type.setVisibility(View.INVISIBLE);
                llType.setBackgroundResource(R.mipmap.icon_take_bg_3x);
                llInfo.setVisibility(View.VISIBLE);
                ll_waimai.setVisibility(View.GONE);
                llTipAddress.setVisibility(View.GONE);
                llDelivery.setVisibility(View.GONE);
                changDataToView(cartDataBean, basePrice);
                break;
            //送达时间
            case R.id.ll_send_time:
                showSendTimeDialog2(send_day, delivertime);
                break;
            //自取时间
            case R.id.ll_take_time:
                showTakeTimeDialog(take_day, delivertime_take);
                break;
            //支付方式
            case R.id.ll_pay:
                showPayDialog(tvPayType);
                break;
            //优惠券
            case R.id.ll_coupon:
                Intent cIntent = new Intent(ShoppingCartActivity2.this, SelectCouponActivity.class);
                cIntent.putExtra("coupon_use", uCouponLists);
                cIntent.putExtra("coupon_no", nCouponLists);
                cIntent.putExtra("coupon_id", coupon_id);
                startActivityForResult(cIntent, COUPON_CODE);
                break;
            //备注
            case R.id.ll_beizhu:
                Intent bIn = new Intent(ShoppingCartActivity2.this, BeizhuActivity.class);
                ArrayList<String> remark_label = new ArrayList<>();
                if (cartDataBean.shopModel.remark_label != null) {
                    remark_label.addAll(cartDataBean.shopModel.remark_label);
                }
                bIn.putStringArrayListExtra("beizhu", remark_label);
                startActivityForResult(bIn, BEIZHU_CODE);
                break;
            //选择地址
            case R.id.ll_take_phone:
            case R.id.ll_address:
            case R.id.ll_add_address:
                Intent intent = new Intent(new Intent(ShoppingCartActivity2.this, SelectAddressActivity.class));
                intent.putExtra("shop_id", cartDataBean.shopModel.id);
                String mId = "";
                if (mAddressList != null) {
                    mId = mAddressList.id;
                }
                intent.putExtra("address_id", mId);
                startActivityForResult(intent, ADDRESS_ADD_CODE);
                break;
            case R.id.btn_load_again:
                loadData();
                llLoadFail.setVisibility(View.GONE);
                break;
            //结算
            case R.id.tv_to_pay:
                if (tvToPay.getText().toString().equals("去结算")) {
                    checkOrder(shop_id);
                }
                break;
            case R.id.btn_commit:
                if (edtCode != null) {
                    if (!StringUtils.isEmpty(edtCode.getText().toString())) {
                        captcha = edtCode.getText().toString();
                        checkOrder(shop_id);
                        sendCodeDialog.dismiss();
                    }
                }
                break;
            case R.id.tv_code_yuyin:
                sendCode("1");
                break;
            case R.id.tv_code:
                sendCode("2");
                break;
            case R.id.iv_back:
                if (sendCodeDialog != null) {
                    second = -5;
                    sendCodeDialog.dismiss();
                }
                break;
        }
    }

    private void sendCode(String type) {
        String phone = "";
        if (MODE_SELECT == 0) {
            if (mAddressList == null || StringUtils.isEmpty(mAddressList.name)) {
                ToastUtil.showTosat(ShoppingCartActivity2.this, "请选择收货地址");
                return;
            }
            phone = mAddressList.phone;
            if (!StringUtils.isMobile(phone)) {
                ToastUtil.showTosat(ShoppingCartActivity2.this, "请输入正确的手机号");
                return;
            }
        } else {
            phone = tv_take_phone.getText().toString();
            if (!StringUtils.isMobile(phone)) {
                ToastUtil.showTosat(ShoppingCartActivity2.this, "请输入正确的手机号");
                return;
            }
        }
        cartPresenter.sendCode2(SharedPreferencesUtil.getAdminId(), type, phone);
    }

    //加载数据
    private void loadData() {
        if (goodids.size() > 0) {
            loadView.showView();
            ArrayList<String> goodList = new ArrayList<>();
            for (GoodsListBean.GoodsInfo goodsInfo : goodsInfos) {
                goodList.add(goodsInfo.id);
            }
            String food_ids = new Gson().toJson(goodList);
            cartPresenter.loadData(SharedPreferencesUtil.getToken(), SharedPreferencesUtil.getAdminId(), shop_id, food_ids);
        }
    }


    @Override
    public void loadShoppingCart(ShoppingCartBean bean) {
        if (bean.data != null) {
            cartDataBean = bean.data;
            String worktime = bean.data.shopModel.worktime;
            String tip = bean.data.outtime_info;
            //是否在营业时间段
            isOnWork = !StringUtils.isEmpty(tip);
            rlData.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(tip)) {
                llOrderTip.setVisibility(View.VISIBLE);
                tvOrderTip.setText(tip);
                tv_yuding.setText(tip);
                tv_yuding.setVisibility(View.VISIBLE);
                moveAnimator();
            }
            showDataToView(bean.data);
        }
    }

    private OnLocationChangedListener mLocationChangedListener;
    private LocationPresenter presenter;
    private AMap aMap;
    private ArrayList<LatLng> latLngs = new ArrayList<>();

    private void initMap() {
        presenter = new LocationPresenter(this, this);
        if (aMap == null) {
            aMap = mapView.getMap();
            if (aMap == null) {
                return;
            }
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
            myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.setLocationSource(this);// 设置定位监听（1）
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                }
            });
            //手动移动地图监听 （2）
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            //设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
            aMap.getUiSettings().setRotateGesturesEnabled(false);//禁止地图旋转手势
            aMap.getUiSettings().setTiltGesturesEnabled(false);//禁止倾斜手势
            aMap.getUiSettings().setZoomControlsEnabled(true);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    private void initMarker(ArrayList<LatLng> latLngs) {
        aMap.clear();
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLngs.get(0), 15, 0, 0)));
        //------------------------------------------添加中心标记
        Log.i("ti_map", "initMarker latLngs. ==  " + latLngs.size());
        for (int i = 0; i < latLngs.size(); i++) {
            LatLng latLng = latLngs.get(i);
            if (i == 0) {
                final MarkerOptions markerOptions = new MarkerOptions();
                double d = (double) AMapUtils.calculateLineDistance(latLngs.get(0), latLngs.get(1));
                String dis = "";
                if (d > 1000) {
                    d = d / 1000;
                    d = getFormatData(d);
                    dis = FormatUtil.numFormat(d + "") + "km";
                } else {
                    d = getFormatData(d);
                    dis = FormatUtil.numFormat(d + "") + "m";
                }
                dis = "距离您" + dis;
                markerOptions.position(latLng).title(dis);
                markerOptions.draggable(false);
                String url = cartDataBean.shopModel.shopimage;
                if (!StringUtils.isEmpty(url)) {
                    url = RetrofitManager.BASE_IMG_URL + url;
                }
                RequestOptions requestOptions = new RequestOptions()
                        .error(R.mipmap.store_logo_default);
                Glide.with(this).load(url).apply(requestOptions).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (resource != null) {
                            View view = LayoutInflater.from(ShoppingCartActivity2.this).inflate(R.layout.layout_shop_marker, null);
                            RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.iv_shop);
                            Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                            imageView.setImageBitmap(bitmap);
                            imageView.getLayoutParams().height = UIUtils.dip2px(23);
                            imageView.getLayoutParams().width = UIUtils.dip2px(23);
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(view)));
                            aMap.addMarker(markerOptions).showInfoWindow();
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        View view = LayoutInflater.from(ShoppingCartActivity2.this).inflate(R.layout.layout_shop_marker, null);
                        RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.iv_shop);
                        imageView.setImageResource(R.mipmap.store_logo_default);
                        imageView.getLayoutParams().height = UIUtils.dip2px(23);
                        imageView.getLayoutParams().width = UIUtils.dip2px(23);
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(view)));
                        aMap.addMarker(markerOptions).showInfoWindow();
                    }
                });
            } else {
                final MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng).title("");
                markerOptions.draggable(false);
              /*  ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.mipmap.icon_marker_user);
                imageView.getLayoutParams().height = UIUtils.dip2px(23);
                imageView.getLayoutParams().width = UIUtils.dip2px(23);*/
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_marker_user));
                aMap.addMarker(markerOptions).setClickable(false);
            }
        }


    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    //显示数据
    private void showDataToView(ShoppingCartBean.DataBean dataBean) {
        showGoodsModels(dataBean);
        if (mAddressList == null && dataBean.address != null) {
            mAddressList = dataBean.address;
        }
        ShoppingCartBean.DataBean.ShopModelBean modelBean = dataBean.shopModel;
        double la = StringUtils.isEmpty(modelBean.coordinate_x) ? 0 : Double.parseDouble(modelBean.coordinate_x);
        double ln = StringUtils.isEmpty(modelBean.coordinate_y) ? 0 : Double.parseDouble(modelBean.coordinate_y);
        latLngs.add(0, new LatLng(la, ln));
        if (mAddressList == null || StringUtils.isEmpty(mAddressList.name)) {
            llAddAddress.setVisibility(View.VISIBLE);
            llAddress.setVisibility(View.GONE);
            llTipAddress.setVisibility(View.GONE);
        } else {
            tvAddressName.setText(mAddressList.name);
            tvAddressPhone.setText(mAddressList.phone);
            tvAddressDetail.setText(mAddressList.address_name + "  " + mAddressList.address);
            tvTipAddress.setText(mAddressList.address_name + "  " + mAddressList.address);
            tvAddressName.setTag(mAddressList.id);
            llAddress.setVisibility(View.VISIBLE);
            llAddAddress.setVisibility(View.GONE);
            llTipAddress.setVisibility(View.VISIBLE);
            tv_take_phone.setText(mAddressList.phone);
        }

        days = dataBean.shopdelivery_day;
        cSendTimes = dataBean.todaytime;
        tSendTimes = dataBean.othertime;
        cTakeTimes = dataBean.selftake_nowtime;
        tTakeTimes = dataBean.selftake_shoptime;
        tvShopName.setText(modelBean.shopname);
        if (!StringUtils.isEmpty(modelBean.shopaddress)) {
            tv_address_ti.setText(modelBean.shopaddress);
        } else {
            tv_address_ti.setText("暂无店铺地址");
        }
        tv_delivery_type.setVisibility(View.VISIBLE);
        if ("1".equals(cartDataBean.shopModel.delivery_mode)) {
            tv_delivery_type.setText("平台专送");
        } else {
            tv_delivery_type.setText("商家配送");
        }

        //开启到店自取功能，1开启，0关闭
        if ("1".equals(dataBean.is_openselftake)) {
            llType.setVisibility(View.VISIBLE);
            if (!dataBean.shangjia) {
                MODE_SELECT = 1;
                llType.setVisibility(View.GONE);
                ll_waimai.setVisibility(View.GONE);
                llTipAddress.setVisibility(View.GONE);
                llInfo.setVisibility(View.VISIBLE);
                llDelivery.setVisibility(View.GONE);
            }
            initMap();
        } else {
            llType.setVisibility(View.GONE);
        }

        basePrice = 0;
        presetFee = 0;


        foodBoxFee = getFormatData(foodBoxFee);
        basePrice += foodBoxFee;
        tvBoxFee.setText("￥" + FormatUtil.numFormat(foodBoxFee + ""));
        if (foodBoxFee > 0) {
            llFoodBox.setVisibility(View.VISIBLE);
        } else {
            llFoodBox.setVisibility(View.GONE);
        }

        //顾客所选配送日期1:今天,2:明天,3:后天，一次类推，最大值为7 ,如果没有传空
        if ("1".equals(dataBean.shopModel.opendelivertime)) {
            //配送时间
            if (dataBean.shopdelivery_day != null && dataBean.shopdelivery_day.size() > 0) {
                String day = dataBean.shopdelivery_day.get(0);
                send_day = day;
                if ("今天".equals(dataBean.shopdelivery_day.get(0))) {
                    if (dataBean.todaytime != null && dataBean.todaytime.size() > 0) {
                        delivertime = dataBean.todaytime.get(0);
                        tvSendTime.setText(dataBean.todaytime.get(0));
                    } else {
                        tvSendTime.setText("今天已停止配送");
                    }
                } else {
                    if (dataBean.othertime != null && dataBean.othertime.size() > 0) {
                        delivertime = dataBean.othertime.get(0);
                        tvSendTime.setText(day + dataBean.othertime.get(0));
                    }
                }
                if (dataBean.shopdelivery_day_num != null && dataBean.shopdelivery_day_num.size() > 0) {
                    int d = dataBean.shopdelivery_day_num.get(0);
                    deliverydaynum = d + "";
                }
                if ("尽快送达".equals(tvSendTime.getText().toString())) {
                    tvTime.setText("送达时间");
                } else {
                    if (isOnWork) {
                        delivertime = "";
                        tvSendTime.setText("未选择（必填）");
                    } else {
                        tvTime.setText("指定时间");
                    }

                }
            }


        } else {
            llSendTime.setVisibility(View.GONE);
            deliverydaynum = "";
            delivertime = "";
        }
        //提取时间
        if (dataBean.shopdelivery_day != null && dataBean.shopdelivery_day.size() > 0) {
            String day = dataBean.shopdelivery_day.get(0);
            take_day = day;
            if ("今天".equals(dataBean.shopdelivery_day.get(0))) {
                if (dataBean.selftake_nowtime != null && dataBean.selftake_nowtime.size() > 0) {
                    delivertime_take = dataBean.selftake_nowtime.get(0);
                    tvTakeTime.setText(day + dataBean.selftake_nowtime.get(0));
                }
            } else {
                if (dataBean.selftake_shoptime != null && dataBean.selftake_shoptime.size() > 0) {
                    delivertime_take = dataBean.selftake_shoptime.get(0);
                    tvTakeTime.setText(day + dataBean.selftake_shoptime.get(0));
                }
            }
            if (dataBean.shopdelivery_day_num != null && dataBean.shopdelivery_day_num.size() > 0) {
                int d = dataBean.shopdelivery_day_num.get(0);
                deliverydaynum_take = d + "";
            }
        }

        //是否开启增值服务费
        llService.setVisibility(View.GONE);
        if (dataBean.isAddService) {
            if (dataBean.addserviceArray != null && dataBean.addserviceArray.size() > 0) {
                serviceAdapter = new AddServiceAdapter(this, dataBean.addserviceArray);
                listviewService.setAdapter(serviceAdapter);
                double count = 0;
                for (ShoppingCartBean.DataBean.AddserviceArrayBean arrayBean : dataBean.addserviceArray) {
                    float c = StringUtils.isEmpty(arrayBean.price) ? 0 : Float.parseFloat(arrayBean.price);
                    count += c;
                }
                count = getFormatData(count);
                if (count > 0) {
                    tvServiceFee.setText("￥" + FormatUtil.numFormat(count + ""));
                }
                basePrice = basePrice + count;
            }
        }
        //预设选项
        if (dataBean.order_field != null && dataBean.order_field.size() > 0) {
            mapYuse = new HashMap<>();
            orderFieldList = dataBean.order_field;
            presetAdapter = new PresetAdapter(this, orderFieldList);
            listviewPreset.setAdapter(presetAdapter);
            listviewPreset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    presetSelect = position;
                    //字段类型，1选项字段，2填写字段
                    if (orderFieldList.get(position).type.equals("1")) {
                        showYuseDialog(presetAdapter.getValue(position), position);
                    } else {
                        startActivityForResult(new Intent(ShoppingCartActivity2.this, YuseActivity.class), YUSE_CODE);
                    }
                }
            });
            Map<Integer, String> names = new HashMap<>();
            for (int i = 0; i < orderFieldList.size(); i++) {
                ShoppingCartBean.DataBean.OrderFieldBean bean = orderFieldList.get(i);
                if (bean.value != null && bean.value.size() > 0) {
                    if (bean.type.equals("1")) {
                        presetFee += bean.value.get(0).price;
                        for (ShoppingCartBean.DataBean.ValueBean valueBean : bean.value) {
                            valueBean.parent_name = bean.name;
                        }
                        mapYuse.put(i, bean.value.get(0));
                    }
                }
            }
            yuseAdapter = new YuseAdapter(this, mapYuse, names);
            listviewYuse.setAdapter(yuseAdapter);
        } else {
            vvBei.setVisibility(View.GONE);
        }
        basePrice += presetFee;
        //支付方式
        payType(dataBean);
        changDataToView(dataBean, basePrice);
    }

    //价格变动，刷新数据
    private void changDataToView(ShoppingCartBean.DataBean dataBean, double basePrice) {
        double manZengPrice = 0;//满赠的条件
        totalPrice = basePrice;
        totalDiscount = 0;//优惠金额
        disGoodsPrice = allGoodsPrice;
        LogUtil.log("changDataToView", "paye == " + pay_type);
        //会员价
        if (dataBean.IsShopMember && "0".equals(dataBean.memberFreeze)) {
            //是否开启只有余额支付会员才能享受商品会员价,1开启，0关闭)
            if ("1".equals(dataBean.shopModel.is_vip_price_for_yue)) {
                if (pay_type.equals("balance")) {
                    isUseVipPrice = true;
                } else {
                    isUseVipPrice = false;
                }
            } else {
                isUseVipPrice = true;
            }
        } else {
            isUseVipPrice = false;
        }
        //减去会员优惠
        if (isUseVipPrice) {
            disGoodsPrice = allGoodsPrice - vipPriceFee;
            isUseVipPrice = true;
            if (goodsAdapter != null) {
                goodsAdapter.showMemberPrice(true);
            }
        } else {
            disGoodsPrice = allGoodsPrice;
            if (goodsAdapter != null) {
                goodsAdapter.showMemberPrice(false);
            }
        }

        disGoodsPrice = getFormatData(disGoodsPrice);
        finalGoodsPrice = disGoodsPrice;
        tvGoodsTotalPrice.setText("￥" + FormatUtil.numFormat(finalGoodsPrice + ""));

        if (finalGoodsPrice <= 0) {
            tvGoodsTotalPrice.setText("￥0");
        }
        //满赠条件
        manZengPrice = finalGoodsPrice + foodBoxFee;

        //所有总价
        totalPrice += finalGoodsPrice;
        LogUtil.log("changDataToView", "totalDiscount = " + totalDiscount + "   totalPrice  == " + totalPrice);

        //获取优惠券张数
        getCouponList(dataBean);
        //开始计算优惠价格

        //配送费（满多少免配送费是按商品总价，不计算店铺折扣）
        getDeliveryFee(dataBean);
        LogUtil.log("changDataToView", " = del =" + totalDiscount + "   totalPrice  == " + totalPrice);


        boolean isFirst = false;

        //订单有折扣商品，不可使用首单立减
        //首单立减和新客立减 同时开启，默认使用首单
        //是否开启首单减免
        if (dataBean.first_order.equals("1") && dataBean.is_first_discount.equals("1") && !isHasSpecailGoods) {
            llNewUser.setVisibility(View.VISIBLE);
            tvNew.setText("新用户下单立减" + FormatUtil.numFormat(dataBean.first_order_discount + "") + "元");
            tvNewFee.setText("-￥" + FormatUtil.numFormat(dataBean.first_order_discount + ""));
            isFirst = true;
            if (FormatUtil.numFloat(dataBean.first_order_discount) == 0) {
                tvNew.setText("新用户下单立减0元");
                tvNewFee.setText("-￥0");
                llNewUser.setVisibility(View.GONE);
            }
            totalDiscount = totalDiscount + FormatUtil.numDouble(dataBean.first_order_discount);
        } else {
            llNewUser.setVisibility(View.GONE);
        }
        //使用了首单立减就不可以使用新客立减
        //新客立减  is_shop_first_discount 是否开启新客立减   shop_first_order 是否店铺新客
        if ("1".equals(dataBean.is_shop_first_discount) && "1".equals(dataBean.shop_first_order) && !isFirst) {
            llShopNewUser.setVisibility(View.VISIBLE);
            String price = FormatUtil.numFormat(dataBean.shop_first_discount);
            tvShopNewUser.setText("新客立减" + price + "元");
            tvShopNewFee.setText("-￥" + price);
            if (FormatUtil.numFloat(dataBean.shop_first_discount) == 0) {
                tvShopNewUser.setText("新客立减0元");
                tvShopNewFee.setText("-￥0");
                llShopNewUser.setVisibility(View.GONE);
            }
            totalDiscount = totalDiscount + FormatUtil.numDouble(dataBean.shop_first_discount);
        } else {
            llShopNewUser.setVisibility(View.GONE);
        }

        LogUtil.log("changDataToView", "totalDiscount = first= " + totalDiscount + "   totalPrice  == " + totalPrice);
        //折扣(先计算折扣，后计算满减)
        getDiscount(dataBean);
        LogUtil.log("changDataToView", "totalDiscount = dis= " + totalDiscount + "   totalPrice  == " + totalPrice);
        //满减
        boolean isPromotion = getPromotion(dataBean, isFirst);
        LogUtil.log("changDataToView", "totalDiscount = pro= " + totalDiscount + "   totalPrice  == " + totalPrice);
        //满赠
        getManZeng(manZengPrice, isPromotion);
        //计算优惠券
        getCoupon(couponListBean);
        LogUtil.log("changDataToView", " = cou  =" + totalDiscount + "   totalPrice  == " + totalPrice);

    }

    private void getCoupon(ShoppingCartBean.DataBean.CouponListBean couponListBean) {
        couponFee = 0;
        coupon_id = "-1";
        if (couponListBean != null) {
            if (cartDataBean.shopModel.is_coupon.equals("1")) {
                if (cartDataBean.is_only_online.equals("1") && pay_type.equals("offline")) {
                    coupon_id = "-1";
                    llCoupon.setOnClickListener(null);
                    couponFee = 0;
                    tvCoupon.setText(TIPS_USE);
                    tvCoupon.setTextColor(ContextCompat.getColor(this, R.color.text_color_82));
                } else {
                    llCoupon.setOnClickListener(this);
                    couponFee = couponListBean.coupon_value;
                    coupon_id = couponListBean.id;
                    couponUsed = "-￥" + FormatUtil.numFormat(couponListBean.coupon_value + "");
                    tvCoupon.setText(couponUsed);
                    tvCoupon.setTextColor(ContextCompat.getColor(this, R.color.cart_red));
                }
            }
        }
        totalDiscount += couponFee;
        showResultPrice();
    }

    //折扣
    private void getDiscount(ShoppingCartBean.DataBean dataBean) {
        llVip.setVisibility(View.GONE);
        discountFee = 0;
        ShoppingCartBean.DataBean.ShopModelBean modelBean = dataBean.shopModel;
        if (modelBean.is_discount.equals("1") && !isHasSpecailGoods) {
            llVip.setVisibility(View.VISIBLE);
            discountFee = (disGoodsPrice * (10 - modelBean.discount_value)) / 10;
            if (discountFee < 0.01) {
                discountFee = 0;
            }
            discountFee = getFormatData(discountFee);
            boolean is_can_dis = false;
            //只有会员才能打折
            if (modelBean.discountlimitmember.equals("1")) {
                if (dataBean.IsShopMember && "0".equals(dataBean.memberFreeze)) {
                    tvVip.setText("会员全店可享受" + FormatUtil.numFormat(modelBean.discount_value + "") + "折");
                    //已开启会员才能参与店铺打折，是否开启只有余额支付才能享受该活动优惠,1是，0不是
                    if ("1".equals(dataBean.shopModel.is_discount_for_yue)) {
                        if (pay_type.equals("balance")) {
                            LogUtil.log("ShoppingDiscount", " 已开启会员才能参与店铺打折，不是余额支付不能享受该活动优惠 ");
                            is_can_dis = true;
                        }
                    } else {
                        is_can_dis = true;
                    }
                }
            } else {
                //是否开启只有在线支付和余额付款才能打折
                if (dataBean.IsOnlyDiscount) {
                    if (!pay_type.equals("offline")) {
                        is_can_dis = true;
                    }
                } else {
                    is_can_dis = true;

                }
            }
            if (is_can_dis) {
                LogUtil.log("ShoppingDiscount", " 打折 ");
                tvdiscountFee.setTextColor(ContextCompat.getColor(this, R.color.cart_red));
                llVip.setVisibility(View.VISIBLE);
                tvVip.setText("全店可享受" + FormatUtil.numFormat(modelBean.discount_value + "") + "折");
                if (modelBean.discount_value == 0) {
                    tvVip.setText("全店可享受0折");
                }
            } else {
                LogUtil.log("ShoppingDiscount", " 不是打折 ");
                discountFee = 0;
                llVip.setVisibility(View.GONE);
            }
        } else {
            llVip.setVisibility(View.GONE);
        }
        tvdiscountFee.setText("-￥" + FormatUtil.numFormat(discountFee + ""));
        LogUtil.log("ShoppingDiscount", " discountFee === " + discountFee);
        if (discountFee == 0) {
            tvdiscountFee.setText("-￥0");
            llVip.setVisibility(View.GONE);
        }
        totalDiscount = totalDiscount + discountFee;
    }

    //满减
    private boolean getPromotion(ShoppingCartBean.DataBean dataBean, boolean isFirst) {
        boolean isPromotion = false;
        llJian.setVisibility(View.GONE);
        promotionFee = 0;
        //是否开启店铺消费满多少减多少的营销活动   （isFirst）平台首单和满减活动不能同时使用
        if (dataBean.shopModel.open_promotion.equals("1") && !isHasSpecailGoods && !isFirst) {
            if (dataBean.promotions != null && dataBean.promotions.size() > 0) {
                for (ShoppingCartBean.DataBean.PromotionsBean promotionsBean : dataBean.promotions) {
                    // (商品总价>= 满减)
                    double price = finalGoodsPrice - discountFee;
                    if (price >= promotionsBean.amount) {
                        llJian.setVisibility(View.VISIBLE);
                        tvJian.setText("满" + FormatUtil.numFormat(promotionsBean.amount + "") + "减" + FormatUtil.numFormat(promotionsBean.discount + "") + "元");
                        tvJianFee.setText("-￥" + FormatUtil.numFormat(promotionsBean.discount + ""));
                        tvJianFee.setTextColor(ContextCompat.getColor(this, R.color.cart_red));
                        promotionFee = promotionsBean.discount;
                        boolean flag = false;

                        //是否开启只有在线支付和余额付款才进行满减优惠
                        if (dataBean.IsOnlyPromotion && pay_type.equals("offline")) {
                            flag = true;
                        }
                        if (!flag) {
                            LogUtil.log("ShoppingDiscount", " 在线支付 =444444==  " + promotionFee);
                            totalDiscount = totalDiscount + promotionFee;
                            llJian.setVisibility(View.VISIBLE);
                            isPromotion = true;
                        } else {
                            llJian.setVisibility(View.GONE);
                            LogUtil.log("ShoppingDiscount", " 货到 =444444==  " + promotionFee);
                        }
                        return isPromotion;
                    }
                }
            }
        }

        return isPromotion;

    }

    private void getManZeng(double manzengPrice, boolean isPromotion) {
        manzeng_name = "";
        //满赠(商品总价 + 打包费)
        llManzeng.setVisibility(View.GONE);
        if ("1".equals(cartDataBean.is_manzeng)) {
            LogUtil.log("ShoppingDiscount", " getManZeng  " + isPromotion + "  条件 == " + manzengPrice);
            //满赠优惠是否跟满减同享 1同享 0不同享
            if ("1".equals(cartDataBean.manzeng_is_communion) || !isPromotion) {
                if (cartDataBean.manzeng_rule != null && cartDataBean.manzeng_rule.size() > 0) {
                    for (int i = cartDataBean.manzeng_rule.size() - 1; i >= 0; i--) {
                        ShoppingCartBean.DataBean.ManzengBean manzengBean = cartDataBean.manzeng_rule.get(i);
                        if (manzengPrice >= manzengBean.amount && manzengBean.stock > 0) {
                            llManzeng.setVisibility(View.VISIBLE);
                            tvManzengFee.setText(manzengBean.name);
                            this.manzeng_name = manzengBean.name;
                            return;
                        }
                    }
                }

            }
        }
    }

    //结算总价
    private void showResultPrice() {
        if (llJian.getVisibility() == View.GONE && llNewUser.getVisibility() == View.GONE && llVip.getVisibility() == View.GONE) {
            vLine.setVisibility(View.GONE);
        } else {
            vLine.setVisibility(View.VISIBLE);
        }
        /*totalPrice = getFormatData(totalPrice);
        tvTotalPrice.setText("总价￥" + FormatUtil.numFormat(totalPrice + ""));
        if (totalPrice == 0) {
            tvTotalPrice.setText("总价￥0");
        }*/
        totalDiscount = getFormatData(totalDiscount);
        String count = FormatUtil.numFormat(totalDiscount + "");

        String totalCoupon = "已优惠￥" + count;
        tvTotalCoupon.setText(totalCoupon);
        tvCouponPrice.setText("￥" + count);
        if (totalDiscount == 0) {
            tvCouponPrice.setText("");
            tvTotalPrice.setVisibility(View.GONE);
            tvTotalCoupon.setVisibility(View.GONE);
        } else {
            tvTotalPrice.setVisibility(View.VISIBLE);
            tvTotalCoupon.setVisibility(View.VISIBLE);
        }
        //待支付 = 总价 - 优惠
        totalPay = totalPrice - totalDiscount;
        if (totalPay <= 0) {
            totalPay = 0.01;
        }
        totalPay = getFormatData(totalPay);

        String pay = FormatUtil.numFormat(totalPay + "");
        tvPayPrice.setText(pay);
        tvTotalPay.setText(pay);

        if (finalGoodsPrice >= basicSendPrice) {
            tvToPay.setText("去结算");
            tvToPay.setTextColor(ContextCompat.getColor(ShoppingCartActivity2.this, R.color.white));
            tvToPay.setBackgroundColor(ContextCompat.getColor(this, R.color.cart_price));
        } else {
            double cha = getFormatData(basicSendPrice - finalGoodsPrice);
            tvToPay.setText("还差" + FormatUtil.numFormat(cha + "") + "送");
            tvToPay.setTextColor(ContextCompat.getColor(ShoppingCartActivity2.this, R.color.text_color_82));
            tvToPay.setBackgroundColor(ContextCompat.getColor(this, R.color.tv_cart_pay));
        }

    }

    //支付方式  ["offline","balancepay","zhifubaopay","weixinpay"]
    private void payType(ShoppingCartBean.DataBean bean) {
        weixinzhifu_type = bean.weixinzhifu_type;
        zhifubaozhifu_type = bean.zhifubaozhifu_type;
        payTypes = new ArrayList<>();
        if (bean.paytype != null && bean.paytype.size() > 0) {
            for (String s : bean.paytype) {
                if (s.equals("weixinpay")) {
                    payTypes.add(s);
                } else if (s.equals("zhifubaopay")) {
                    payTypes.add(s);
                } else if (s.equals("offline")) {
                    payTypes.add(s);
                } else if (s.equals("balancepay")) {
                    payTypes.add(s);
                }
            }
            String s;
            if (StringUtils.isEmpty(selectPayType))
                s = StringUtils.isEmpty(payTypes.get(0)) ? "" : payTypes.get(0);
            else {
                if (payTypes.contains(selectPayType))
                    s = selectPayType;
                else {
                    s = StringUtils.isEmpty(payTypes.get(0)) ? "" : payTypes.get(0);
                    selectPayType = "";
                }
            }
            switch (s) {
                case "weixinpay":
                    tvPayType.setText("微信支付");
                    pay_type = "online";
                    online_pay_type = weixinzhifu_type;
                    selectPayType = beforePayType = s;
                    break;
                case "zhifubaopay":
                    tvPayType.setText("支付宝支付");
                    pay_type = "online";
                    online_pay_type = zhifubaozhifu_type;
                    selectPayType = beforePayType = s;
                    break;
                case "offline":
                    tvPayType.setText("货到付款");
                    pay_type = "offline";
                    online_pay_type = "offline";
                    selectPayType = beforePayType = s;
                    break;
                case "balancepay":
                    tvPayType.setText("余额支付￥" + cartDataBean.memberBalance);
                    pay_type = "balance";
                    online_pay_type = "balance";
                    selectPayType = beforePayType = s;
                    break;
            }

        }
    }

    //获取优惠券
    private void getCouponList(ShoppingCartBean.DataBean dataBean) {
        nCouponLists.clear();
        uCouponLists.clear();
        couponList = dataBean.couponList;
        ShoppingCartBean.DataBean.ShopModelBean modelBean = dataBean.shopModel;
        //是否开启优惠券
        if (modelBean.is_coupon.equals("1")) {
            if (couponList != null && couponList.size() > 0) {
                int sum = 0;
                for (ShoppingCartBean.DataBean.CouponListBean cl : couponList) {
                    boolean flag1 = false;
                    boolean flag2 = false;
                    boolean flag3 = false;
                    LogUtil.log("getCoupon", "name --- " + cl.coupon_name);
                    //是否可以使用
                    if (cl.coupon_status.equals("OPEN")) {
                        LogUtil.log("getCoupon", "can --- 0000");
                        //满减
                        if (finalGoodsPrice >= cl.coupon_basic_price && cl.coupon_value <= modelBean.coupon_max) {
                            flag3 = true;
                            LogUtil.log("getCoupon", "can --- 1111");
                            //是否关联店铺
                            if (cl.shop_ids != null && (cl.shop_ids.contains("0") || cl.shop_ids.contains(modelBean.id))) {
                                LogUtil.log("getCoupon", "can --- shop");
                                flag1 = true;
                                //是否关联商品
                                if (cl.foodids != null && cl.foodids.contains("0")) {
                                    LogUtil.log("getCoupon", "can --- goods all ");
                                    flag2 = true;
                                } else {
                                    for (String id : goodids) {
                                        if (cl.foodids.contains(id)) {
                                            LogUtil.log("getCoupon", "can --- goods id == " + id);
                                            flag2 = true;
                                        }
                                    }
                                }

                            }
                        }
                        if (flag1 && flag2 && flag3) {
                            uCouponLists.add(cl);
                            sum++;
                        } else {
                            nCouponLists.add(cl);
                        }
                    } else {
                        nCouponLists.add(cl);
                    }
                }
                tvCoupon.setText(sum + "张可用");
                tvCoupon.setTextColor(ContextCompat.getColor(this, R.color.cart_red));
                couponUsed = sum + "张可用";
                if (sum == 0) {
                    tvCoupon.setText(getString(R.string.no_coupon_use));
                    couponUsed = getString(R.string.no_coupon_use);
                    tvCoupon.setTextColor(ContextCompat.getColor(this, R.color.text_color_82));
                }
            } else {
                tvCoupon.setText(getString(R.string.no_coupon_use));
                couponUsed = getString(R.string.no_coupon_use);
                tvCoupon.setTextColor(ContextCompat.getColor(this, R.color.text_color_82));
            }
        } else {
            llCoupon.setVisibility(View.GONE);
        }
    }

    //获取配送费
    private void getDeliveryFee(ShoppingCartBean.DataBean dataBean) {
        LogUtil.log("getDeliveryFee", "MODE_SELECT == " + MODE_SELECT);
        if (MODE_SELECT == 0) {
            ShoppingCartBean.DataBean.ShopModelBean modelBean = dataBean.shopModel;
            basicSendPrice = modelBean.basicprice;
            deliveryFee = modelBean.delivery_fee;
            LogUtil.log("getDeliveryFee", "deliveryFee =11111111= " + deliveryFee);
            if (mAddressList != null && !StringUtils.isEmpty(mAddressList.lat)) {
                LogUtil.log("getDeliveryFee", "mAddressList name == " + mAddressList.address_name);
                //固定配送费、起送价
                if (modelBean.delivery_fee_mode.equals("1")) {
                    deliveryFee = modelBean.delivery_fee;
                    if (modelBean.open_full_free_delivery_fee.equals("1")) {
                        if (finalGoodsPrice >= modelBean.no_delivery_fee_value) {
                            tvDeliveryFee.setText("￥0");
                            tvDelivery.setText("配送费（满" + FormatUtil.numFormat(modelBean.no_delivery_fee_value + "") + "免配送费)");
                            deliveryFee = 0;
                            llDelivery.setVisibility(View.GONE);
                            //不计配送费
                            showResultPrice();
                            return;
                        } else {
                            tvDelivery.setText("配送费（满" + FormatUtil.numFormat(modelBean.no_delivery_fee_value + "") + "免配送费)");
                        }
                    } else {
                        tvDelivery.setText("配送费");
                    }
                }
                //按区域
                else if (modelBean.delivery_fee_mode.equals("2")) {
                    ArrayList<ShoppingCartBean.DataBean.AreaJsonBean.AreaDataBean> arrayList = new ArrayList<>();
                    String lat = mAddressList.lat;
                    String lng = mAddressList.lng;

                    float la1 = StringUtils.isEmpty(lat) ? 0 : Float.parseFloat(lat);
                    float ln1 = StringUtils.isEmpty(lng) ? 0 : Float.parseFloat(lng);
                    LatLng ll = new LatLng(la1, ln1);
                    for (ShoppingCartBean.DataBean.AreaJsonBean.AreaDataBean areaDataBean : dataBean.areaJson.data) {
                        //多边形区域
                        if (areaDataBean.edit_type.equals("polygon") && !areaDataBean.area_str.equals("")) {
                            ArrayList<LatLng> latLngs = new ArrayList<>();
                            String str[] = areaDataBean.area_str.split(";");
                            for (int j = 0; j < str.length; j++) {
                                String str1[] = str[j].split(",");
                                float x = StringUtils.isEmpty(str1[1]) ? 0 : Float.parseFloat(str1[1]);
                                float y = StringUtils.isEmpty(str1[0]) ? 0 : Float.parseFloat(str1[0]);
                                LatLng l = new LatLng(x, y);
                                latLngs.add(l);
                            }
                            if (UIUtils.isInPolygon(ll, latLngs)) {
                                arrayList.add(areaDataBean);
                            }
                        }
                    }
                    if (arrayList.size() > 0) {
                        Collections.sort(arrayList, new AreaComparator());
                        deliveryFee = arrayList.get(0).delivery_price;
                        basicSendPrice = arrayList.get(0).basic_price;
                        if (arrayList.get(0).open_full_free_delivery_fee.equals("1")) {
                            if (finalGoodsPrice >= arrayList.get(0).no_delivery_fee_value) {
                                tvDeliveryFee.setText("￥0");
                                tvDelivery.setText("配送费（满" + FormatUtil.numFormat(arrayList.get(0).no_delivery_fee_value + "") + "免配送费)");
                                deliveryFee = 0;
                                llDelivery.setVisibility(View.GONE);
                                //不计配送费
                                showResultPrice();
                                return;
                            } else {
                                tvDelivery.setText("配送费（满" + FormatUtil.numFormat(arrayList.get(0).no_delivery_fee_value + "") + "免配送费)");
                            }
                        } else {
                            tvDelivery.setText("配送费");
                        }
                    }
                }
                //按距离
                else if (modelBean.delivery_fee_mode.equals("3")) {
                    if (modelBean.open_full_free_delivery_fee_byrange.equals("1")) {
                        if (finalGoodsPrice >= Float.parseFloat(modelBean.full_free_delivery_fee_byrange)) {
                            tvDeliveryFee.setText("￥0");
                            tvDelivery.setText("配送费（满" + FormatUtil.numFormat(modelBean.full_free_delivery_fee_byrange + "") + "免配送费)");
                            deliveryFee = 0;
                            llDelivery.setVisibility(View.GONE);
                            //不计配送费
                            showResultPrice();
                            return;
                        } else {
                            tvDelivery.setText("配送费（满" + FormatUtil.numFormat(modelBean.full_free_delivery_fee_byrange + "") + "免配送费)");
                        }
                    } else {
                        tvDelivery.setText("配送费");
                    }
                    String lat = mAddressList.lat;
                    String lng = mAddressList.lng;

                    float la1 = StringUtils.isEmpty(lat) ? 0 : Float.parseFloat(lat);
                    float ln1 = StringUtils.isEmpty(lng) ? 0 : Float.parseFloat(lng);
                    LatLng ll = new LatLng(la1, ln1);

                    String circle_x = modelBean.coordinate_x;
                    String circle_y = modelBean.coordinate_y;
                    float x = StringUtils.isEmpty(circle_x) ? 0 : Float.parseFloat(circle_x);
                    float y = StringUtils.isEmpty(circle_y) ? 0 : Float.parseFloat(circle_y);
                    LatLng ll2 = new LatLng(x, y);
                    //计算距离
                    float dis = AMapUtils.calculateLineDistance(ll2, ll);
                    if (dataBean.range_delivery_fee_json.size() > 0) {
                        for (ShoppingCartBean.DataBean.RangeDeliveryFeeJsonBean range : dataBean.range_delivery_fee_json) {
                            if (dis >= (range.start * 1000) && dis <= (range.stop * 1000)) {
                                deliveryFee = range.value;
                                basicSendPrice = range.minvalue;
                                break;
                            }
                        }
                    }
                }
                //按时间
                else if (modelBean.delivery_fee_mode.equals("4")) {
                    String a = dataBean.todaydeliveryfee.get(0);
                    deliveryFee = StringUtils.isEmpty(a) ? 0 : Float.parseFloat(a);
                }
            }
            LogUtil.log("getDeliveryFee", "deliveryFee =222222= " + deliveryFee);
            LogUtil.log("getDeliveryFee", "IsShopMember " + cartDataBean.IsShopMember);
            LogUtil.log("getDeliveryFee", "is_delivery_free " + modelBean.is_delivery_free);
            //会员是否免配送费，1是，0否   （限特价商品）
            if (cartDataBean.IsShopMember && "0".equals(cartDataBean.memberFreeze) && "1".equals(modelBean.is_delivery_free) && !isHasSpecailGoods) {
                //已开启会员免配送费，是否只有余额支付才能享受该活动优惠,1开启，0关闭)
                if ("0".equals(modelBean.is_free_delivery_fee_for_yue)) {
                    deliveryFee = 0;
                    tvDelivery.setText("配送费");
                } else {
                    isVipDeliveryFree = true;
                    if (pay_type.equals("balance")) {
                        deliveryFee = 0;
                        tvDelivery.setText("配送费");
                    }
                }
            }
            deliveryFee = getFormatData(deliveryFee);
            basicSendPrice = getFormatData(basicSendPrice);
            LogUtil.log("getDeliveryFee", "deliveryFee =222222= " + deliveryFee);
            tvDeliveryFee.setText("￥" + FormatUtil.numFormat(deliveryFee + ""));
        } else {
            basicSendPrice = 0;
            deliveryFee = 0;
        }
        if (deliveryFee == 0) {
            tvDeliveryFee.setText("￥0");
            llDelivery.setVisibility(View.GONE);
        } else {
            llDelivery.setVisibility(View.VISIBLE);
        }
        LogUtil.log("getDeliveryFee", "deliveryFee final = " + deliveryFee);
        totalPrice = totalPrice + deliveryFee;
        showResultPrice();
    }

    @Override
    public void loadFail() {
        llLoadFail.setVisibility(View.VISIBLE);
        rlData.setVisibility(View.GONE);
    }

    @Override
    public void paySuccess(OrderPayResultBean bean) {
        BaseApplication.getPreferences().edit().putBoolean("isFreshData", true).apply();//下一次选购的时候要刷新商品数据
        BaseApplication.getPreferences().edit().putBoolean("isFreshSearchData", true).apply();
        if (bean.data != null) {
            BaseApplication.greenDaoManager.deleteGoodsByShopId(shop_id);
            order_id = bean.data.order_id;
            order_no = bean.data.order_no;
            if (bean.data.status != null && bean.data.status.equals("success")) {
                Intent intent = new Intent(this, MyOrderActivity.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("order_no", order_no);
                intent.putExtra("from", "is_from_cart");
                startActivity(intent);
                //刷新订单列表
                if (null != OrderFragment.instance) {
                    OrderFragment.instance.loadOrder();
                }
                order_id = "";
                finish();
                return;
            }

            if (tvPayType.getText().toString().equals("微信支付")) {
                if (bean.data.zhifuParameters != null) {
                    parameters = bean.data.zhifuParameters;
                    weixinPay(parameters);
                }
            } else if (tvPayType.getText().toString().equals("支付宝支付")) {
                alipay(bean.data.zhifubaoParameters);
            }
        }
    }

    @Override
    public void payFail() {
        if (!StringUtils.isEmpty(order_id)) {
            BaseApplication.greenDaoManager.deleteGoodsByShopId(shop_id);
            Intent intent = new Intent(ShoppingCartActivity2.this, MyOrderActivity.class);
            intent.putExtra("order_id", order_id);
            intent.putExtra("order_no", order_no);
            intent.putExtra("from", "is_from_cart");
            startActivity(intent);
            order_id = "";
            finish();
        }
    }

    @Override
    public void sendCodeSuccess() {
        ToastUtil.showTosat(this, "发送成功");
        isTimeOut = false;
        handler.sendEmptyMessage(1);
    }

    @Override
    public void sendCodeFail() {
//        ToastUtil.showTosat(this, "发送失败");
    }

    @Override
    public void onResultCode(String code, String msg) {
        //首单短信验证
        if (code.equals("-2")) {
            showSendCodeDialog();
        } else if (code.equals("-5") || code.equals("-6")) {
            showActTipDialog(msg,isFirst);
        } else {
            loadData();
        }
    }

    @Override
    public void dialogDismiss() {
        if (loadView != null) {
            loadView.dismissView();
        }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }

    }

    @Override
    public void showToast(String s) {
        if (!StringUtils.isEmpty(s)) {
            ToastUtil.showTosat(this, s);
        } else {
            ToastUtil.showTosat(this, "网络请求失败");
        }
    }

    private Dialog timeDialog, payDialog, takeDialog, yuseDialog, sendCodeDialog, actTipDialog;
    private DayAdapter dayAdapter, tDayAdapter;
    private TimeAdapter timeAdapter, tTimeAdapter;
    private HashMap<String, ArrayList<String>> hashMap, tHashMap;

    //到店自取的时间
    private void showTakeTimeDialog(final String day, final String time) {
        if (takeDialog == null) {
            tHashMap = new HashMap<>();
            for (int i = 0; i < days.size(); i++) {
                ArrayList<String> list = new ArrayList<>();
                if ("今天".equals(days.get(i))) {
                    if (cTakeTimes != null && cTakeTimes.size() > 0) {
                        list.addAll(cTakeTimes);
                    } else {
                        list.add("");
                    }
                    tHashMap.put(days.get(i), list);
                } else {
                    if (tTakeTimes != null && tTakeTimes.size() > 0) {
                        list.addAll(tTakeTimes);
                    } else {
                        list.add("");
                    }
                    tHashMap.put(days.get(i), list);
                }

            }

            View view = LayoutInflater.from(this).inflate(R.layout.dialog_cart_send_time, null);
            final ListView listViewDay = (ListView) view.findViewById(R.id.listview_day);
            final ListView listViewTime = (ListView) view.findViewById(R.id.listview_time);
            tDayAdapter = new DayAdapter(this);
            tTimeAdapter = new TimeAdapter(this);
            tDayAdapter.setSelectValue(day);
            tDayAdapter.setDays(days);
            tTimeAdapter.setSelectValue(time);
            tTimeAdapter.setTimes(tHashMap.get(day));

            listViewDay.setAdapter(tDayAdapter);
            listViewTime.setAdapter(tTimeAdapter);
            listViewDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String s = days.get(position);
                    tDayAdapter.setSelectValue(s);
                    if (cartDataBean.shopdelivery_day_num != null && cartDataBean.shopdelivery_day_num.size() > position) {
                        int d = cartDataBean.shopdelivery_day_num.get(position);
                        deliverydaynum_take = d + "";
                    }
                    tTimeAdapter.setTimes(tHashMap.get(s));
                    tTimeAdapter.setSelectValue(s.equals(day), tTimeAdapter.getSelectValue());
                }
            });
            listViewTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    delivertime_take = tTimeAdapter.getTimes().get(position);
                    if (StringUtils.isEmpty(delivertime_take)) {
                        takeDialog.dismiss();
                        return;
                    }
                    take_day = tDayAdapter.getSelectValue();
                    String value = tDayAdapter.getSelectValue() + delivertime_take;
                    tvTakeTime.setText(value);
                    if (cartDataBean != null) {
                        if (cartDataBean.shopModel.delivery_fee_mode.equals("4")) {
                            if (!StringUtils.isEmpty((String) tvTakeTime.getTag())) {
                                tvDeliveryFee.setText("￥" + (String) tvTakeTime.getTag());
                                deliveryFee = Float.parseFloat((String) tvTakeTime.getTag());
                                totalPrice = totalPrice + deliveryFee;
                                showResultPrice();
                            }
                        }

                    }
                    takeDialog.dismiss();
                }
            });
            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeDialog.dismiss();

                }
            });
            takeDialog = DialogUtils.BottonDialog(this, view);
        } else {
            tDayAdapter.setSelectValue(day);
            tTimeAdapter.setSelectValue(time);
            tTimeAdapter.setTimes(tHashMap.get(day));
        }
        takeDialog.show();
    }

    public void showSendTimeDialog2(final String day, final String time) {
        if (days == null) {
            return;
        }
        if (timeDialog == null) {
            hashMap = new HashMap<>();
            for (int i = 0; i < days.size(); i++) {
                ArrayList<String> list = new ArrayList<>();
                if ("今天".equals(days.get(i))) {
                    if (cSendTimes != null && cSendTimes.size() > 0) {
                        list.addAll(cSendTimes);
                    } else {
                        list.add("");
                    }
                    hashMap.put(days.get(i), list);
                } else {
                    if (tSendTimes != null && tSendTimes.size() > 0) {
                        list.addAll(tSendTimes);
                    } else {
                        list.add("");
                    }
                    hashMap.put(days.get(i), list);
                }

            }

            View view = LayoutInflater.from(this).inflate(R.layout.dialog_cart_send_time, null);
            final ListView listViewDay = (ListView) view.findViewById(R.id.listview_day);
            final ListView listViewTime = (ListView) view.findViewById(R.id.listview_time);
            dayAdapter = new DayAdapter(this);
            timeAdapter = new TimeAdapter(this);
            dayAdapter.setSelectValue(day);
            dayAdapter.setDays(days);
            timeAdapter.setSelectValue(time);
            timeAdapter.setTimes(hashMap.get(day));

            listViewDay.setAdapter(dayAdapter);
            listViewTime.setAdapter(timeAdapter);
            listViewDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String s = days.get(position);
                    dayAdapter.setSelectValue(s);
                    if (cartDataBean.shopdelivery_day_num != null && cartDataBean.shopdelivery_day_num.size() > position) {
                        int d = cartDataBean.shopdelivery_day_num.get(position);
                        deliverydaynum = d + "";
                    }
                    timeAdapter.setTimes(hashMap.get(s));
                    timeAdapter.setSelectValue(s.equals(day), timeAdapter.getSelectValue());
                }
            });
            listViewTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    timeDialog.dismiss();
                    delivertime = timeAdapter.getTimes().get(position);
                    if (StringUtils.isEmpty(delivertime)) {
                        return;
                    }
                    send_day = dayAdapter.getSelectValue();
                    String value = dayAdapter.getSelectValue() + delivertime;
                    if ("今天".equals(dayAdapter.getSelectValue())) {
                        value = delivertime;
                    }
                    tvSendTime.setText(value);
                    if (value.equals("尽快送达")) {
                        tvTime.setText("送达时间");
                        if (isOnWork) {
                            tv_yuding.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvTime.setText("指定时间");
                        if (isOnWork) {
                            tv_yuding.setVisibility(View.GONE);
                        }
                    }
                    if (cartDataBean != null) {
                        if (cartDataBean.shopModel.delivery_fee_mode.equals("4")) {
                            if (!StringUtils.isEmpty((String) tvSendTime.getTag())) {
                                tvDeliveryFee.setText("￥" + (String) tvSendTime.getTag());
                                deliveryFee = Float.parseFloat((String) tvSendTime.getTag());
                                totalPrice = totalPrice + deliveryFee;
                                showResultPrice();
                            }
                        }

                    }
                }
            });
            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeDialog.dismiss();

                }
            });
            timeDialog = DialogUtils.BottonDialog(this, view);
        } else {
            dayAdapter.setSelectValue(day);
            timeAdapter.setSelectValue(time);
            timeAdapter.setTimes(hashMap.get(day));
        }
        timeDialog.show();
    }


    //预设选项
    private void showYuseDialog(final ArrayList<ShoppingCartBean.DataBean.ValueBean> value, final int position) {
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            //是否开启，1：是，0：否
            if ("1".equals(value.get(i).is_open)) {
                String s = "";
                if (value.get(i).price == 0) {
                    s = " (0元)";
                } else {
                    s = " (" + value.get(i).price + "元)";
                }
                list.add(value.get(i).name + s);
            }
        }
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_yuse, null);
        final WheelView wheelDay = (WheelView) view.findViewById(R.id.wheel_yuse);
        wheelDay.setSelection(0);
        wheelDay.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        wheelDay.setSkin(WheelView.Skin.Holo); // common皮肤
        wheelDay.setWheelData(list);  // 数据集合

        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yuseDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yuseDialog.dismiss();
                if (presetAdapter != null && wheelDay.getSelectionItem() != null) {
                    presetAdapter.setDataChange(position, wheelDay.getCurrentPosition());
                    yuseAdapter.setValue(presetSelect, value.get(wheelDay.getCurrentPosition()));
                    LogUtil.log("presetFee", "111 == " + presetFee);
                    //重新计算总价
                    basePrice -= presetFee;
                    presetFee = yuseAdapter.getPresetFee();
                    basePrice += presetFee;
                    LogUtil.log("presetFee", "222 == " + presetFee);
                    changDataToView(cartDataBean, basePrice);

                }
            }
        });
        yuseDialog = DialogUtils.BottonDialog(this, view);
        yuseDialog.show();
    }


    private PayTypeAdapter payTypeAdapter;

    //支付方式
    private void showPayDialog(final TextView textView) {
        if (payDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_select, null);
//            cartDataBean.paytype.remove("balancepay");
            payTypeAdapter = new PayTypeAdapter(this, payTypes);
            payTypeAdapter.setMemberBalance(cartDataBean.memberBalance);
            payTypeAdapter.setSelectValue(selectPayType);
            ListView listView = (ListView) view.findViewById(R.id.listview_pay);
            listView.setAdapter(payTypeAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    payDialog.dismiss();
                    switch (payTypes.get(position)) {
                        case "weixinpay":
                            textView.setText("微信支付");
                            pay_type = "online";
                            online_pay_type = weixinzhifu_type;
                            break;
                        case "zhifubaopay":
                            textView.setText("支付宝支付");
                            pay_type = "online";
                            online_pay_type = zhifubaozhifu_type;
                            break;
                        case "offline":
                            textView.setText("货到付款");
                            pay_type = "offline";
                            online_pay_type = "offline";
                            break;
                        case "balancepay":
                            textView.setText("余额支付￥" + cartDataBean.memberBalance);
                            pay_type = "balance";
                            online_pay_type = "balance";
                            break;
                    }

                    payTypeAdapter.setSelectValue(position);
                    if (!beforePayType.equals(pay_type)) {
                        if (beforePayType.equals("balance")) {
                            couponListBean = null;//还原已选择的优惠券
                        } else {
                            if (pay_type.equals("balance")) {
                                couponListBean = null;//还原已选择的优惠券
                            }
                        }
                        changDataToView(cartDataBean, basePrice);
                    }
                    beforePayType = pay_type;
                    selectPayType =payTypes.get(position);

                }
            });
            payDialog = DialogUtils.BottonDialog(this, view);
        }

        payDialog.show();
    }

    private TextView tvError, tvCode, tvCodeYuyin;
    private EditText edtCode;
    private ImageView ivBack;

    private void showSendCodeDialog() {
        if (sendCodeDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_cart_send_code, null);
            ivBack = (ImageView) view.findViewById(R.id.iv_back);
            tvError = (TextView) view.findViewById(R.id.tv_error);
            tvCode = (TextView) view.findViewById(R.id.tv_code);
            edtCode = (EditText) view.findViewById(R.id.edt_code);
            tvCodeYuyin = (TextView) view.findViewById(R.id.tv_code_yuyin);
            tvError.setText("短信验证码将发送至" + mAddressList.phone);
            final Button button = (Button) view.findViewById(R.id.btn_commit);
            button.setOnClickListener(this);
            tvCode.setOnClickListener(this);
            ivBack.setOnClickListener(this);
            tvCodeYuyin.setOnClickListener(this);
            edtCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (StringUtils.isEmpty(s.toString())) {
                        button.setBackgroundResource(R.drawable.shape_cart_send_button);
                        button.setOnClickListener(null);
                        tvCodeYuyin.setTextColor(ContextCompat.getColor(ShoppingCartActivity2.this, R.color.text_color_99));
                        tvCodeYuyin.setOnClickListener(null);
                    } else {
                        button.setOnClickListener(ShoppingCartActivity2.this);
                        button.setBackgroundResource(R.drawable.selector_btn_login);
                        tvCodeYuyin.setTextColor(ContextCompat.getColor(ShoppingCartActivity2.this, R.color.theme_red));
                        tvCodeYuyin.setOnClickListener(ShoppingCartActivity2.this);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            sendCodeDialog = DialogUtils.centerDialog(this, view);
        }
        sendCodeDialog.show();
    }

    private void showActTipDialog(String msg, final boolean isFirst) {
        if (actTipDialog != null) {
            actTipDialog.show();
        } else {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_cart_act_tip, null);
            TextView tvError = (TextView) view.findViewById(R.id.tv_error);
            tvError.setText(msg);
            view.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actTipDialog.dismiss();
                    if (!isFirst)
                        loadData();
                    payDialog = null;
                    boolean isCommit = BaseApplication.getPreferences().edit().putBoolean("isFreshData", true).commit();
                    LogUtil.log("update","商品刷新commit== "+ isCommit);
                }
            });
//            actTipDialog = DialogUtils.centerDialog(this, view);
            actTipDialog = new AlertDialog.Builder(this).create();
            actTipDialog.setCancelable(false);
            actTipDialog.show();
            Window window = actTipDialog.getWindow();
            if (null != window) {
                window.setContentView(view);
                window.setLayout(UIUtils.dip2px(290), LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }

    }


    //加载动画
    private void showLoadingDialog() {
        loadingDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADDRESS_ADD_CODE) {
                if (StringUtils.isEmpty(SelectAddressActivity.address_id)) {
                    LogUtil.log("SelectAddressCart", "address_id == null");
                    mAddressList = null;
                    llAddress.setVisibility(View.GONE);
                    llAddAddress.setVisibility(View.VISIBLE);
                    llTipAddress.setVisibility(View.GONE);
                    if (cartDataBean != null) {
                        changDataToView(cartDataBean, basePrice);
                    }
                    return;
                }
                AddressBean.AddressList list = (AddressBean.AddressList) data.getSerializableExtra("address");
                if (list != null) {
                    mAddressList = list;
                    LogUtil.log("SelectAddressCart", "name == " + list.name);
                    tvAddressName.setText(list.name);
                    tvAddressPhone.setText(list.phone);
                    tvAddressDetail.setText(list.address_name + "  " + list.address);
                    tvTipAddress.setText(list.address_name + "  " + list.address);
                    tvAddressName.setTag(list.id);
                    llAddress.setVisibility(View.VISIBLE);
                    llAddAddress.setVisibility(View.GONE);
                    llTipAddress.setVisibility(View.VISIBLE);
                    /*edtName.setText(mAddressList.name);
                    edtPhone.setText(mAddressList.phone);*/
                    tv_take_phone.setText(mAddressList.phone);
                } else {
                    mAddressList = null;
                    LogUtil.log("SelectAddressCart", "name == null");
                    llAddress.setVisibility(View.GONE);
                    llAddAddress.setVisibility(View.VISIBLE);
                    llTipAddress.setVisibility(View.GONE);
                }
                if (cartDataBean != null) {
                    changDataToView(cartDataBean, basePrice);
                }
            } else if (requestCode == BEIZHU_CODE) {
                String beizhu = data.getStringExtra("beizhu");
                tvBeizhuValue.setText(beizhu);
                note = beizhu;
            } else if (requestCode == YUSE_CODE) {
                String yu = data.getStringExtra("yuse");
                if (presetAdapter != null) {
                    presetAdapter.setSelectValue(presetSelect, yu);
                }
            } else if (requestCode == COUPON_CODE) {
                boolean is_select = data.getBooleanExtra("is_select", false);
                if (is_select) {
                    couponListBean = (ShoppingCartBean.DataBean.CouponListBean) data.getSerializableExtra("coupon");
                } else {
                    couponListBean = null;
                }
                changDataToView(cartDataBean, basePrice);
            }
        }
    }

    //下单
    private void placeOrder() {
        String token = SharedPreferencesUtil.getToken();
        String admin_id = cartDataBean.shopModel.admin_id;
        String shop_id = cartDataBean.shopModel.id;
        String name = "";
        String phone = "";
        String address = "";
        String lat = "";
        String lng = "";
        String time = delivertime;
        String daynum = deliverydaynum;
        //配送方式
        if (MODE_SELECT == 0) {
            if (mAddressList == null || StringUtils.isEmpty(mAddressList.name)) {
                ToastUtil.showTosat(this, "请添加收货地址");
                return;
            }
            name = mAddressList.name;
            phone = mAddressList.phone;
            address = mAddressList.address_name + mAddressList.address;
            lat = mAddressList.lat;
            lng = mAddressList.lng;
            if ("1".equals(cartDataBean.shopModel.opendelivertime)) {
                if (StringUtils.isEmpty(time) || StringUtils.isEmpty(daynum) || llSendTime.getVisibility() == View.GONE) {
                    ToastUtil.showTosat(this, "请选择配送时间");
                    return;
                }
            } else {
                time = "";
                daynum = "";
            }
        } else {
           /* if (StringUtils.isEmpty(edtName.getText().toString())) {
                ToastUtil.showTosat(this, "请填写取货人姓名");
                return;
            }*/
            if (StringUtils.isEmpty(tv_take_phone.getText().toString())) {
                ToastUtil.showTosat(this, "请填写取货人电话");
                return;
            }
            if (!StringUtils.isMobile(tv_take_phone.getText().toString())) {
                ToastUtil.showTosat(ShoppingCartActivity2.this, "请输入正确的手机号");
                return;
            }
            phone = tv_take_phone.getText().toString();
            time = delivertime_take;
            daynum = deliverydaynum_take;
            if (StringUtils.isEmpty(time) || StringUtils.isEmpty(daynum)) {
                ToastUtil.showTosat(this, "请选择配送时间");
                return;
            }
        }

        if ("1".equals(cartDataBean.shopModel.is_pay_offline_limit) && "offline".equals(pay_type)) {
            if (totalPay > cartDataBean.shopModel.pay_offline_limit) {
                ToastUtil.showTosat(this, "支付金额已超过货到付款的限制额数（" + cartDataBean.shopModel.pay_offline_limit + "）,请您选择其他支付方式");
                return;
            }
        }
        if (pay_type.equals("balance")) {
            if (totalPay > FormatUtil.numFloat(cartDataBean.memberBalance)) {
                ToastUtil.showTosat(this, "余额不足（" + cartDataBean.memberBalance + "）,请您选择其他支付方式");
                return;
            }
        }
        if ("online".equals(pay_type) && weixinzhifu_type.equals(online_pay_type)) {
            msgApi.registerApp(Constants.APP_ID);
            if (!msgApi.isWXAppInstalled()) {
                ToastUtil.showTosat(this, "您未安装微信，无法进行微信支付");
                return;
            }
        }
        //预设字段
        JSONArray jsonArray = new JSONArray();
        if (presetAdapter != null) {
            for (Integer key : presetAdapter.getPresetValue().keySet()) {
                jsonArray.put(presetAdapter.getPresetValue().get(key));
            }
        }

        //是否使用了会员价
        JSONObject cart = cartPresenter.getGoodsJSONObject(goodsInfos);
        showLoadingDialog();
        //提交订单结算
        cartPresenter.placeOrder(token, admin_id, shop_id, pay_type, online_pay_type, coupon_id, daynum, time, MODE_SELECT + "", name, phone,
                address, lat, lng, note, jsonArray, cart, captcha, totalPay + "", manzeng_name);
        captcha = "";
    }


    //微信支付
    private void weixinPay(OrderPayResultBean.ZhiFuParameters parameters) {
        msgApi.registerApp(Constants.APP_ID);
        if (msgApi.isWXAppInstalled()) {
            PayReq req = new PayReq();
            req.appId = parameters.appid;
            req.partnerId = parameters.partnerid;//微信支付分配的商户号
            req.prepayId = parameters.prepayid;//微信返回的支付交易会话ID
            req.nonceStr = parameters.noncestr;//32位随机字符
            req.timeStamp = parameters.timestamp;// 10位时间戳
            req.packageValue = "Sign=WXPay";//暂填写固定值Sign=WXPay
            req.sign = parameters.sign; //签名
            msgApi.registerApp(Constants.APP_ID);
            msgApi.sendReq(req);
        } else {
            UIUtils.showToast("未安装微信！");
        }
    }

    //支付宝支付
    private void alipay(final String payInfo) {
        Alipay alipay = Alipay.getInstance(this);
        alipay.pay(payInfo);
        alipay.setAlipayPayListener(new Alipay.AlipayPayListener() {
            @Override
            public void onPaySuccess(String statusCode, String msg) {
                switch (statusCode) {
                    //订单支付成功
                    case "9000":
                        //正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "8000":
                        //支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "6004":
                        if (!StringUtils.isEmpty(order_id)) {
                            Intent intent = new Intent(ShoppingCartActivity2.this, MyOrderActivity.class);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("order_no", order_no);
                            intent.putExtra("pay_status", "success");
                            intent.putExtra("from", "is_from_cart");
                            startActivity(intent);
                            order_id = "";
                            //刷新订单列表
                            if (null != OrderFragment.instance) {
                                OrderFragment.instance.loadOrder();
                            }
                            finish();
                            return;
                        }
                        break;
                    //订单支付失败
                    case "4000":
                        ToastUtil.showTosat(ShoppingCartActivity2.this, "支付失败");
                        if (!StringUtils.isEmpty(order_id)) {
                            Intent intent = new Intent(ShoppingCartActivity2.this, MyOrderActivity.class);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("order_no", order_no);
                            intent.putExtra("pay_status", "fail");
                            intent.putExtra("from", "is_from_cart");
                            startActivity(intent);
                            order_id = "";
                            //刷新订单列表
                            if (null != OrderFragment.instance) {
                                OrderFragment.instance.loadOrder();
                            }
                            finish();
                            return;
                        }
                        break;
                    //重复请求
                    case "5000":
                        break;
                    //用户中途取消
                    case "6001":
                        ToastUtil.showTosat(ShoppingCartActivity2.this, "支付取消");
                        if (!StringUtils.isEmpty(order_id)) {
                            Intent intent = new Intent(ShoppingCartActivity2.this, MyOrderActivity.class);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("order_no", order_no);
                            intent.putExtra("pay_status", "fail");
                            intent.putExtra("from", "is_from_cart");
                            startActivity(intent);
                            order_id = "";
                            //刷新订单列表
                            if (null != OrderFragment.instance) {
                                OrderFragment.instance.loadOrder();
                            }
                            finish();
                            return;
                        }
                        break;
                    //网络连接出错
                    case "6002":
                        ToastUtil.showTosat(ShoppingCartActivity2.this, "网络错误，支付失败");
                        if (!StringUtils.isEmpty(order_id)) {
                            Intent intent = new Intent(ShoppingCartActivity2.this, MyOrderActivity.class);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("order_no", order_no);
                            intent.putExtra("pay_status", "fail");
                            intent.putExtra("pay_param", payInfo);
                            intent.putExtra("from", "is_from_cart");
                            startActivity(intent);
                            order_id = "";
                            //刷新订单列表
                            if (null != OrderFragment.instance) {
                                OrderFragment.instance.loadOrder();
                            }
                            finish();
                            return;
                        }
                        break;


                }
            }

            @Override
            public void onPayFail(String msg) {
                ToastUtil.showTosat(ShoppingCartActivity2.this, "网络错误，支付失败");
                if (!StringUtils.isEmpty(order_id)) {
                    Intent intent = new Intent(ShoppingCartActivity2.this, MyOrderActivity.class);
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("order_no", order_no);
                    intent.putExtra("pay_status", "fail");
                    intent.putExtra("pay_param", payInfo);
                    intent.putExtra("from", "is_from_cart");
                    startActivity(intent);
                    order_id = "";
                    //刷新订单列表
                    if (null != OrderFragment.instance) {
                        OrderFragment.instance.loadOrder();
                    }
                    finish();
                    return;
                }
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                latLngs.add(1, new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
                mLocationChangedListener.onLocationChanged(amapLocation);
                initMarker(latLngs);
            }
        }
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationChangedListener = onLocationChangedListener;
        presenter.doLocation();
    }

    @Override
    public void deactivate() {
        presenter.doStopLocation();
    }

    // 先按起送价排序，再按配送费，后按满免
    public static class AreaComparator implements Comparator<ShoppingCartBean.DataBean.AreaJsonBean.AreaDataBean> {
        @Override
        public int compare(ShoppingCartBean.DataBean.AreaJsonBean.AreaDataBean o1, ShoppingCartBean.DataBean.AreaJsonBean.AreaDataBean o2) {
            int result = 0;
            float a = o1.basic_price - o2.basic_price;
            if (a != 0) {
                result = a > 0 ? 3 : -1;
            } else {
                a = o1.delivery_price - o2.delivery_price;
                if (a != 0) {
                    result = a > 0 ? 2 : -2;
                } else {
                    a = o1.no_delivery_fee_value - o2.no_delivery_fee_value;
                    if (a != 0) {
                        result = a > 0 ? 1 : -3;
                    }
                }
            }
            return result;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //微信支付成功（0） 或取消（-2）
        if (WXResult.errCode == 0 || WXResult.errCode == -2) {
            if (!StringUtils.isEmpty(order_id)) {
                Intent intent = new Intent(this, MyOrderActivity.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("order_no", order_no);
                intent.putExtra("pay_status", "success");
                intent.putExtra("from", "is_from_cart");
                if (WXResult.errCode == -2) {
                    intent.putExtra("pay_status", "fail");
                    intent.putExtra("pay_param", parameters);
                }
                startActivity(intent);
                //刷新订单列表
                if (null != OrderFragment.instance) {
                    OrderFragment.instance.loadOrder();
                }

                BaseApplication.greenDaoManager.deleteGoodsByShopId(shop_id);
                order_id = "";
                WXResult.errCode = -2222;
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    public double getFormatData(double d) {
        return Double.parseDouble(df.format(d));
    }

    @Override
    protected void onDestroy() {
        handler.removeMessages(1);
        handler.removeCallbacksAndMessages(null);
        mapView.onDestroy();
        if (presenter != null) {
            presenter.doStopLocation();
        }
        super.onDestroy();
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

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                llOrderTip.clearAnimation();
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

    //必选商品检测
    private void checkOrder(String shop_id) {
        String admin_id = Const.ADMIN_ID;
        String token = SharedPreferencesUtil.getToken();
        ArrayList<String> goodList = new ArrayList<>();
        for (GoodsListBean.GoodsInfo goodsInfo : goodsInfos) {
            goodList.add(goodsInfo.original_type_id);
        }
        String food_type = new Gson().toJson(goodList);
        HashMap<String, String> params = IsCollectRequest.checkOrder(admin_id, token, shop_id, food_type);
        checkPresenter.check(UrlConst.CHECK_ORDER, params, shop_id);
    }

    @Override
    public void showSuccessVIew(String shop_id) {
        placeOrder();
    }

    @Override
    public void showCheckOrderView(String msg) {
        showGoodsTypeNeedDialog(msg);
    }

    //必选分类提示
    private Dialog goodsTypeNeedDialog;

    private void showGoodsTypeNeedDialog(String msg) {
        if (goodsTypeNeedDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_goods_type_need, null);
            TextView tvError = (TextView) view.findViewById(R.id.tv_error);
            tvError.setText(msg);
            view.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsTypeNeedDialog.dismiss();
                }
            });
            goodsTypeNeedDialog = DialogUtils.centerDialog(this, view);
        }
        goodsTypeNeedDialog.show();
    }


}

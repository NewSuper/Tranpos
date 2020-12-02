package com.newsuper.t.consumer.function.selectgoods.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.bean.CollectBean;
import com.newsuper.t.consumer.bean.GoodsByType;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.GoodsType;
import com.newsuper.t.consumer.bean.ManJian;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.selectgoods.activity.GoodsDetailActivity2;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.selectgoods.activity.ShoppingCartActivity2;
import com.newsuper.t.consumer.function.selectgoods.adapter.MyItemAnimator;
import com.newsuper.t.consumer.function.selectgoods.adapter.TypeAdapter2;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopBigGoodsAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopBigGoodsGroupAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopNatureGoodsAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter2.ShopCartDialog2;
import com.newsuper.t.consumer.function.selectgoods.inter.AnimationListener;
import com.newsuper.t.consumer.function.selectgoods.inter.IDeleteNatureGoods;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.ISelectGoodsActivityView;
import com.newsuper.t.consumer.function.selectgoods.inter.ISelectGoodsByTypeView;
import com.newsuper.t.consumer.function.selectgoods.inter.ISelectGoodsFragmentView;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCart;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCartDialog;
import com.newsuper.t.consumer.function.selectgoods.inter.IShowLimitTime;
import com.newsuper.t.consumer.function.selectgoods.presenter.SelectGoodsActivityPresenter;
import com.newsuper.t.consumer.function.selectgoods.presenter.SelectGoodsByTypePresenter;
import com.newsuper.t.consumer.function.selectgoods.presenter.SelectGoodsFragmentPresenter;
import com.newsuper.t.consumer.function.selectgoods.request.GetGoodsByTypeRequest;
import com.newsuper.t.consumer.function.selectgoods.request.GetGoodsListRequest;
import com.newsuper.t.consumer.function.selectgoods.request.IsCollectRequest;
import com.newsuper.t.consumer.function.selectgoods.request.QuitCollectRequest;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.CenterLayoutManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.ShareUtils;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.AnimatorView;
import com.newsuper.t.consumer.widget.CenterPopView;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;
import com.newsuper.t.consumer.widget.SlidTabView;
import com.newsuper.t.consumer.widget.TimePicker.CenterDialogView;
import com.newsuper.t.consumer.widget.spinerwidget.LimitCountDownTimer;
import com.newsuper.t.consumer.wxapi.Constants;

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
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class SelectBigGoodsFragment2 extends Fragment implements ISelectGoodsFragmentView, ISelectGoodsByTypeView, TypeAdapter2.OnItemSelectedListener, IShopCart, IGoodsToDetailPage, IShopCartDialog, AnimationListener,
        View.OnClickListener, IShowLimitTime, ISelectGoodsActivityView, IDeleteNatureGoods {

    @BindView(R.id.rv_classify)
    RecyclerView rvClassify;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.headerLayout)
    LinearLayout headerLayout;
    @BindView(R.id.second_type)
    SlidTabView secondTypeLayout;//显示二级分类控件
    @BindView(R.id.loading_view)
    RefreshThirdStepView loadingView;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.ll_select_goods)
    LinearLayout llSelectGoods;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    Unbinder unbinder;
    @BindView(R.id.scl_no_goods)
    NestedScrollView sclNoGoods;
    private View rootView;
    private ArrayList<GoodsType> goodsList;//商品数据（包含一级分类信息）,用来处理分类滑动
    private ArrayList<GoodsListBean.GoodsInfo> goodsItems;//用来处理二级分类下的商品
//    private ArrayList<GoodsListBean.GoodsInfo> selectGoods;//已选购的商品
    private ArrayList<GoodsListBean.GoodsInfo> packageItems;//存放套餐商品
    private ArrayList<GoodsListBean.GoodsInfo> searchGoodsItem;//存放搜索商品
    private ArrayList<GoodsListBean.SecondType> secondTypes;//存放所有二级分类
    private ArrayList<GoodsListBean.SecondType> currentSecondTypes;//当前一级分类下的二级分类列表
    private ArrayList<String> secondTypeNames;//当前二级分类的名称
    private ArrayList<GoodsListBean.GoodsInfo> foodlist;//存放后台一次性返回的商品数据(包含套餐)
    private String selectedTypeId;//选中的一级分类id
    private String selectedSecondId;//选中的二级分类id
    private TypeAdapter2 typeAdapter;
    private ShopBigGoodsGroupAdapter goodsGroupAdapter;
    private ShopBigGoodsAdapter adapter;
    private DecimalFormat df;
    private TextView tvCount, tvCost, tvTips, tvSubmit,tvManjian;
    private LinearLayout bottom,ll_cart;
    private ImageView imgCart;
    private String admin_id = Const.ADMIN_ID;
    private String token;
    private GoodsType headType;
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动
    private ShopCart2 shopCart;
    private HashMap<String, Integer> typeSelect;
    private boolean isGuodu = true;//判断是否过渡滑动
    private SelectGoodsByTypePresenter goodsPresenter;
    private String basicPrice;
    private CenterPopView centerPopView;
    private HashMap<String, HashMap<String, ArrayList<GoodsListBean.GoodsInfo>>> allGoodsList;//缓存选购页所有商品数据
    private boolean isTotal;//判断数据是否一次性返回
    private TabLayout tab_select_goods;
//    private View view_line;
    private Dialog shareDialog;
    private CartReceiver cartReceiver;
    private ShopInfoBean.ShopInfo shopInfo;
    private String shop_id;
    private TextView tvShopTip;
    private FrameLayout flBottom;
    private boolean isFirstRequest = true;//判断是否第一次请求数据
    private ArrayList<String> sale_time = new ArrayList<>();
    private boolean isViewInitiated;//是否初始化视图
    private boolean isVisibleToUser;//是否可见
    private ImageView ivIconCollect;
    private boolean isShopCollected;
    private int page = 1;
    private LinearLayoutManager goodsManager;
    private boolean isLoading;
    private boolean isLoadMore = false;
    private boolean isFreshData;
    private LoadingDialog2 loadingDialog;
    private String picUrl;
    private SelectGoodsActivityPresenter collectPresenter;
    private final int COLLECT_LOGIN = 10;//点击收藏跳转登陆的
    private final int LIMIT_LOGIN = 11;//点击限购跳转登陆的
    private final int VIP_LOGIN = 12;//点击会员跳转登陆的
    private boolean isInitData;//成功加载到选购数据
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
    private int typeSelectPosition = 0;//一级分类选中的位置
    private boolean isEffectiveVip;//是否有效会员
    private boolean isFromCollectToLogin;
    private GoodsListBean.GoodsListData data;
    public String goods_id ,second_type_id,type_id;
    private int worktime;
    private String keyWord;
    private int position = 0;
    //加载动画
    private AnimationDrawable animationDrawable;
    Runnable anim = new Runnable() {
        @Override
        public void run() {
            animationDrawable.start();
        }
    };

    private GoodsListBean.GoodsInfo limitGoods;
    private Handler mHandler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            //刷新订单状态
            showCountDown(limitGoods,tv_tip,tvTipTimeHour,tvTipTimeMiniute,tvTipTimeSecond);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectGoodsActivity3 activity3 = (SelectGoodsActivity3)getActivity();
        if (activity3.from_page.equals("searchShop")){
            goods_id = activity3.goods_id;
            type_id = activity3.type_id;
            second_type_id = activity3.second_type_id;
            searchGoodsItem = activity3.getSearchGoodsItem();
            keyWord = activity3.getKeyWord();
            LogUtil.log("searchGoodsType","4、获取activity搜索商品数据GoodsSearchBean== "+new Gson().toJson(searchGoodsItem));
            if (null!=searchGoodsItem && searchGoodsItem.size() > 0 && !StringUtils.isEmpty(goods_id))
                for (int i = 0; i < searchGoodsItem.size(); i++) {
                    GoodsListBean.GoodsInfo goodsInfo = searchGoodsItem.get(i);
                    if (goodsInfo.id.equals(goods_id)) {
                        position = i;
                        LogUtil.log("searchGoodsType","4.0、选中商品id== " + goods_id + "; position== " + position);
                        break;
                    }
                }
        }
        keyWord = activity3.getKeyWord();
        collectPresenter = new SelectGoodsActivityPresenter(this);
        shopInfo = ((SelectGoodsActivity3) getActivity()).getShopInfo();
        shop_id = ((SelectGoodsActivity3) getActivity()).getShopId();
        worktime = shopInfo.worktime;
        df = new DecimalFormat("#0.00");
        shopCart = new ShopCart2();
        goodsList = new ArrayList<>();
        goodsItems = new ArrayList<>();
        packageItems = new ArrayList<>();
        typeSelect = new HashMap<>();
        secondTypeNames = new ArrayList<>();
        currentSecondTypes = new ArrayList<>();
//        selectGoods = new ArrayList<>();
        foodlist = new ArrayList<>();
        allGoodsList = new HashMap<>();
        goodsPresenter = new SelectGoodsByTypePresenter(this);

        //拼接营业时间段
        sale_time.clear();
        if (null != shopInfo) {
            if (shopInfo.business_hours != null) {
                for (ShopInfoBean.SaleTime time : shopInfo.business_hours) {
                    sale_time.add(time.start.substring(0, time.start.lastIndexOf(":")) + "~" + time.stop.substring(0, time.stop.lastIndexOf(":")));
                }
            }
        }
        //请求选购商品
        isFreshData = true;//第一次进入该页面的时候要请求数据
//        load();

    }

    public boolean getVipInfo() {
        return isEffectiveVip;
    }
    private ArrayList<GoodsListBean.GoodsInfo> cartList;
    private void load() {
        //获取购物车中数据
        cartList = BaseApplication.greenDaoManager.getAllGoods(shop_id);
        ArrayList<String> foodSelectList = new ArrayList<>();
        ArrayList<String> packageSelectList = new ArrayList<>();
        for (GoodsListBean.GoodsInfo cartGoods : cartList) {
            if (null != cartGoods.packageNature && cartGoods.packageNature.size() > 0)  {
                if (!packageSelectList.contains(cartGoods.id)) {
                    packageSelectList.add(cartGoods.id);
                }
            } else {
                if (!foodSelectList.contains(cartGoods.id)) {
                    foodSelectList.add(cartGoods.id);
                }
            }
        }
        Gson gson = new Gson();
        SelectGoodsFragmentPresenter presenter = new SelectGoodsFragmentPresenter(this);
        HashMap<String, String> params = GetGoodsListRequest.getGoodsListRequest(token, admin_id, shop_id, gson.toJson(foodSelectList), gson.toJson(packageSelectList), "3");
        presenter.loadData(UrlConst.GETGOODSLIST, params);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case COLLECT_LOGIN:
                    isFromCollectToLogin = true;
                    token = SharedPreferencesUtil.getToken();
                    load();
                    break;
                case LIMIT_LOGIN:
                case VIP_LOGIN:
                    token = SharedPreferencesUtil.getToken();
                    load();
                    break;
            }
        }
    }

    public void showCollectTipDialog() {
        View dialogView = View.inflate(getContext(), R.layout.dialog_collect_tip, null);
        final Dialog loginTipDialog = new Dialog(getContext(), R.style.CenterDialogTheme2);
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
                if (null != loginTipDialog) {
                    loginTipDialog.cancel();
                }
                startActivityForResult(new Intent(getContext(), LoginActivity.class), COLLECT_LOGIN);

            }
        });
        loginTipDialog.show();
    }

    @Override
    public void showDataToVIew(CollectBean bean, String flag) {
        if ("add".equals(flag)) {
            isShopCollected = true;
            ivIconCollect.setImageResource(R.mipmap.collect_s_2x);
            UIUtils.showToast("收藏成功！可到我的收藏查看");
        }
        if ("cancel".equals(flag)) {
            isShopCollected = false;
            ivIconCollect.setImageResource(R.mipmap.collect_n_2x);
            UIUtils.showToast("取消收藏成功");
        }
        if ("check".equals(flag)) {
            token = SharedPreferencesUtil.getToken();
            if (TextUtils.isEmpty(token)) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else {
                //跳转购物车页面
                Intent intent = new Intent(getContext(), ShoppingCartActivity2.class);
                intent.putExtra("shop_id", shop_id);
                startActivity(intent);
            }
        }
//        //发送广播
//        Intent intent = new Intent();
//        intent.putExtra("shop_id", shopInfo.id);
//        if (isShopCollected) {
//            intent.putExtra("is_collect", "1");
//        } else {
//            intent.putExtra("is_collect", "0");
//        }
//        intent.setAction("update_collect_state");
//        getContext().sendBroadcast(intent);
    }

    public ShopCart2 getShopCart() {
        return shopCart;
    }

    public ArrayList<GoodsListBean.GoodsInfo> getPackageItems() {
        return packageItems;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_select_big_goods, null);
        ButterKnife.bind(this, rootView);
        initView();
        isViewInitiated = true;

        //注册广播接收者
        cartReceiver = new CartReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("update_cart");    //只有持有相同的action的接受者才能接收此广播
        getContext().registerReceiver(cartReceiver, filter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
    }

    @Override
    public void onResume() {
        super.onResume();
        isEffectiveVip = SharedPreferencesUtil.getIsEffectVip();
        shopCart.setIsEffectiveVip(isEffectiveVip);
        if (isViewInitiated && isVisibleToUser) {
            if (!isFreshData) {
                isFreshData = BaseApplication.getPreferences().getBoolean("isFreshData", false);
            }
            if (isFreshData) {
                isFreshData = false;
                BaseApplication.getPreferences().edit().putBoolean("isFreshData", false).commit();
                //请求选购商品
                shopCart.clear();
                token = SharedPreferencesUtil.getToken();
                allGoodsList.clear();//清除商品缓存
                //加载动画
                showLoadingView();
                load();
            } else {
                if (isInitData) {
                    //只刷新购物车数据
                    shopCart.clear();
                    queryCartGoods();
                    updateCartData();
                }
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

    private void initView() {
        btnOk.setOnClickListener(this);
        Activity activity = getActivity();
        imgCart = (ImageView) activity.findViewById(R.id.imgCart);
        tvCount = (TextView) activity.findViewById(R.id.tvCount);
        tvCost = (TextView) activity.findViewById(R.id.tvCost);
        tvTips = (TextView) activity.findViewById(R.id.tvTips);
        tvSubmit = (TextView) activity.findViewById(R.id.tvSubmit);
        tvManjian = (TextView) activity.findViewById(R.id.tv_manjian);
        bottom = (LinearLayout) activity.findViewById(R.id.bottom);
        ll_cart = (LinearLayout) activity.findViewById(R.id.ll_cart);
        tvShopTip = (TextView) activity.findViewById(R.id.tv_shop_tip);
        flBottom = (FrameLayout) activity.findViewById(R.id.fl_bottom);

        ivIconCollect = (ImageView) activity.findViewById(R.id.iv_icon_collect);
        tab_select_goods = (TabLayout) activity.findViewById(R.id.tab_select_goods);
//        view_line = (View) activity.findViewById(R.id.view_line);

        activity.findViewById(R.id.show_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCartDetail();
            }
        });
        activity.findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDilaog();
            }
        });
        animationDrawable = (AnimationDrawable) loadingView.getBackground();
        btnOk.setOnClickListener(this);

        CenterLayoutManager manager = new CenterLayoutManager(getContext());
//        DividerDecoration typeDivider = new DividerDecoration();
//        typeDivider.setDividerColor(Color.parseColor("#ebebeb"));
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvClassify.setLayoutManager(manager);
        goodsManager = new LinearLayoutManager(getContext());
//        goodsManager.setAutoMeasureEnabled(false);
        rvGoods.setLayoutManager(goodsManager);
        rvGoods.setItemAnimator(new MyItemAnimator());
//        rvGoods.addItemDecoration(typeDivider);

        ivIconCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                token = SharedPreferencesUtil.getToken();
                if (TextUtils.isEmpty(token)) {
                    showCollectTipDialog();
                    return;
                }
                if (isShopCollected) {
                    //取消收藏
                    HashMap<String, String> params = QuitCollectRequest.quitCollectRequest(admin_id, token, shopInfo.id);
                    collectPresenter.loadData(UrlConst.QUITCOLLECTSHOP, params, "cancel");
                } else {
                    //收藏
                    HashMap<String, String> params = IsCollectRequest.isCollectRequest(admin_id, token, shopInfo.id);
                    collectPresenter.loadData(UrlConst.COLLECTSHOP, params, "add");
                }
            }
        });
    }

    //正在加载
    private void showLoadingView() {
        tab_select_goods.setVisibility(View.GONE);
//        view_line.setVisibility(View.GONE);
        if (null != llFail) {
            llFail.setVisibility(View.GONE);
        }
        flBottom.setVisibility(View.GONE);
        llLoading.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        handler.post(anim);
    }

    //关闭加载动画
    private void closeLoadingView() {
        animationDrawable.stop();
        if (null != llLoading) {
            llLoading.setVisibility(View.GONE);
        }
        if (null != llFail) {
            llFail.setVisibility(View.GONE);
        }
        tab_select_goods.setVisibility(View.VISIBLE);
//        view_line.setVisibility(View.VISIBLE);
        boolean isLinkComment = BaseApplication.getPreferences().getBoolean("isLinkComment", false);
        //用来处理微页面跳转评论页面时底部状态栏的隐藏
        if (isLinkComment) {
            BaseApplication.getPreferences().edit().putBoolean("isLinkComment", false).commit();
        } else {
            flBottom.setVisibility(View.VISIBLE);
        }

        if (1 != shopInfo.worktime) {
            if (!StringUtils.isEmpty(shopInfo.outtime_info)){
                if (getActivity() != null){
                    ((SelectGoodsActivity3)getActivity()).animationTip(shopInfo.outtime_info);
                }
                bottom.setVisibility(View.VISIBLE);
                tvShopTip.setVisibility(View.GONE);
            }else {
                if (-1 == shopInfo.worktime) {
                    tvShopTip.setText("本店已打烊啦");
                } else if (-2 == shopInfo.worktime) {
                    tvShopTip.setText("本店暂停营业");
                } else if (0 == shopInfo.worktime) {
                    tvShopTip.setText("本店休息中");
                } else if (2 == shopInfo.worktime) {
                    tvShopTip.setText("本店停止营业");
                }
                tvShopTip.setVisibility(View.VISIBLE);
                bottom.setVisibility(View.GONE);
                if (isFirstRequest) {
                    showShopTipDialog();
                }
            }

        } else {
            bottom.setVisibility(View.VISIBLE);
            tvShopTip.setVisibility(View.GONE);
        }
        isFirstRequest = false;
    }
    Dialog shopTipDialog = null;
    private void showGoodsTipDialog(String title){
        if (StringUtils.isEmpty(title))
            return;
        if (shopTipDialog == null){
            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shop_goods_tip, null);
            TextView tv_tip_title = (TextView) dialogView.findViewById(R.id.tv_tip_title);
            tv_tip_title.setText(title);
            Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != shopTipDialog) {
                        shopTipDialog.cancel();
                    }
                    showCartDetail();
                }
            });
            shopTipDialog  =  DialogUtils.centerDialog(getContext(),dialogView);
        }
        shopTipDialog.show();
    }
    //弹出店铺提示框
    private void showShopTipDialog() {
        View dialogView = View.inflate(getActivity(), R.layout.dialog_shop_tip, null);
        final Dialog shopTipDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);
        //去掉dialog上面的横线
        Context context = shopTipDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = shopTipDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

        shopTipDialog.setContentView(dialogView);
        shopTipDialog.setCanceledOnTouchOutside(false);
        String time = "";//营业时间
        if (sale_time.size() == 1) {
            time += "“" + sale_time.get(0) + "”";
        } else {
            for (int i = 0; i < sale_time.size(); i++) {
                if (i == 0) {
                    time += "“" + sale_time.get(i) + "，";
                } else {
                    if (i != sale_time.size() - 1) {
                        time += sale_time.get(i) + "，";
                    } else {
                        time += sale_time.get(i) + "”";
                    }
                }
            }
        }
        TextView tv_tip_title = (TextView) dialogView.findViewById(R.id.tv_tip_title);
        TextView tv_tip = (TextView) dialogView.findViewById(R.id.tv_tip);
        String tip = "";
        switch (shopInfo.worktime) {
            case -2:
                //店铺打烊,不在营业星期内
                tv_tip_title.setText("暂停营业");
                if (!TextUtils.isEmpty(shopInfo.work_week)) {
                    tip += "本店今天暂停营业，营业日期：\n" + shopInfo.work_week;
                } else {
                    tip += "本店今天暂停营业";
                }
                break;
            case -1:
                //店铺打烊,不在营业时间内
                tv_tip_title.setText("店铺打烊");
                tip += "本店已打烊，正常营业时间为：\n" + time;
                break;
            case 0:
                //休息中
                tv_tip_title.setText("休息中");
                if (TextUtils.isEmpty(shopInfo.ordervalid_remind)) {
                    tip += "本店正在休息中，请稍后下单";
                } else {
                    tip += shopInfo.ordervalid_remind;
                }
                break;
            case 2:
                //停止营业
                tv_tip_title.setText("停止营业");
                if (TextUtils.isEmpty(shopInfo.closeinfo)) {
                    tip += "本店铺停止营业";
                } else {
                    tip += shopInfo.closeinfo;
                }
                break;
        }
        tv_tip.setText(tip);
        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != shopTipDialog) {
                    shopTipDialog.cancel();
                }
            }
        });
        shopTipDialog.show();
    }


    private void initHeadView() {
        headType = goodsGroupAdapter.getGoodsTypeByPosition(0);
        headerLayout.setContentDescription("0");
        if (null != headType) {
            if ("search".equals(headType.type_id)) {
                String key = "（#"+headType.name+"#的相关结果）";
                SpannableString spannableString = StringUtils.matcherSearchWord2(Color.parseColor("#828282"), headType.name + key, key);
                tvType.setText(spannableString);
            } else
                tvType.setText(headType.name);
        }
    }


    private void showHeadView() {
        headerLayout.setTranslationY(0);
        View underView = rvGoods.findChildViewUnder(tvType.getX(), 0);
        if (underView != null && underView.getContentDescription() != null) {
            int position = Integer.parseInt(underView.getContentDescription().toString());
            GoodsType goodsType = goodsGroupAdapter.getGoodsTypeByPosition(position + 1);
            headType = goodsType;
            if ("search".equals(headType.type_id)) {
                String key = "（#"+headType.name+"#的相关结果）";
                SpannableString spannableString = StringUtils.matcherSearchWord2(Color.parseColor("#828282"), headType.name + key, key);
                tvType.setText(spannableString);
            } else
                tvType.setText(headType.name);
        }
    }

    @Override
    public void showDataToVIew(GoodsListBean bean) {
        closeLoadingView();//关闭动画
        //获取商品列表数据
        if (null != bean) {
            data=bean.data;
            isInitData = true;//获取到数据
            //如果该店铺没有商品
            if (bean.data.food_package.size() == 0 && bean.data.foodlist.size() == 0 && bean.data.foodDiscountlist.size() == 0 && bean.data.foodPackageDiscountlist.size() == 0 ) {
                sclNoGoods.setVisibility(View.VISIBLE);
                return;
            } else {
                if (null != sclNoGoods) {
                    sclNoGoods.setVisibility(View.GONE);
                }
            }
            if ("1".equals(bean.data.IsShopMember)) {
                //是店铺会员
                if ("0".equals(bean.data.memberFreeze)) {
                    //没有被冻结
                    isEffectiveVip = true;//有效会员
                } else {
                    isEffectiveVip = false;
                }
            } else {
                isEffectiveVip = false;
            }
            shopCart.setIsEffectiveVip(isEffectiveVip);
            SharedPreferencesUtil.saveIsEffectVip(isEffectiveVip);
            if (isFromCollectToLogin) {
                isFromCollectToLogin = false;
                if (isShopCollected) {
                    UIUtils.showToast("已经收藏");
                } else {
                    //收藏
                    HashMap<String, String> params = IsCollectRequest.isCollectRequest(admin_id, token, shopInfo.id);
                    collectPresenter.loadData(UrlConst.COLLECTSHOP, params, "add");
                }
            } else {
                //更新数据库数据
                if (null != bean.data.foodSelectedArr && bean.data.foodSelectedArr.size() > 0) {
                    for (GoodsListBean.GoodsInfo goodsSelect : bean.data.foodSelectedArr) {
                        //查询数据库的该商品
                        ArrayList<GoodsListBean.GoodsInfo> oldGoodsList = BaseApplication.greenDaoManager.getGoodsFromDb(shop_id, goodsSelect.id);
                        //更新选购的商品数据
                        if (null != oldGoodsList && !oldGoodsList.isEmpty()) {
                            shopCart.addGoodsListFromDB(oldGoodsList);

                            /*for (GoodsListBean.GoodsInfo oldGoods : oldGoodsList) {
                                if (changeGoodsInfoValue(goodsSelect, oldGoods)) {
                                    float price = 0;
                                    if ("1".equals(oldGoods.switch_discount)) {
                                        if (null != oldGoods.nature && oldGoods.nature.size() > 0) {
                                            if (oldGoods.isUseDiscount) {
                                                price = Float.parseFloat(oldGoods.price);
                                            } else {
                                                price = Float.parseFloat(oldGoods.formerprice);
                                            }
                                            //属性商品
                                            for (GoodsListBean.GoodsNature goodsNature : oldGoods.nature) {
                                                for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                                                    if (natureData.is_selected) {
                                                        price += Float.parseFloat(natureData.price);
                                                    }
                                                }
                                            }
                                        } else {
                                            float discountPrice = Float.parseFloat(oldGoods.price);
                                            float formerprice = Float.parseFloat(oldGoods.formerprice);
                                                //限购份数内按折扣价，限购份数外按原价
                                                if (Float.parseFloat(oldGoods.num_discount) > 0) {
                                                        //当前仍可购买的折扣商品数量
                                                        int canBuyNum = Integer.parseInt(oldGoods.num_discount);
                                                        if (oldGoods.count <= canBuyNum) {
                                                            price = discountPrice * oldGoods.count;
                                                        } else {
                                                            price = discountPrice * canBuyNum + formerprice * (oldGoods.count - canBuyNum);
                                                        }
                                                } else {
                                                    price = discountPrice * oldGoods.count;
                                                }
                                        }
                                    } else {
                                        if ("1".equals(oldGoods.member_price_used)) {
                                            if (isEffectiveVip) {
                                                price = Float.parseFloat(oldGoods.member_price);
                                            } else {
                                                price = Float.parseFloat(oldGoods.price);
                                            }
                                        } else {
                                            price = Float.parseFloat(oldGoods.price);
                                        }
                                        if (null != oldGoods.nature && oldGoods.nature.size() > 0){
                                            //属性商品 数量均为1
                                            for (GoodsListBean.GoodsNature goodsNature : oldGoods.nature) {
                                                for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                                                    if (natureData.is_selected) {
                                                        price += Float.parseFloat(natureData.price);
                                                    }
                                                }
                                            }
                                        }else{
                                            price=price*oldGoods.count;
                                        }
                                    }
                                    if (oldGoods.nature.size() > 0) {
                                        if (shopCart.natureTotalPrice.keySet().contains(oldGoods.id)) {
                                            float prePrice = shopCart.natureTotalPrice.get(oldGoods.id);
                                            prePrice += price;
                                            shopCart.natureTotalPrice.put(oldGoods.id, prePrice);
                                        } else {
                                            shopCart.natureTotalPrice.put(oldGoods.id, price);
                                        }
                                        BaseApplication.greenDaoManager.updateNatureGoods(oldGoods);
                                    } else {
                                        BaseApplication.greenDaoManager.updateGoods(oldGoods);
                                    }
                                    shopCart.addGoods(oldGoods);
                                } else {
                                    BaseApplication.greenDaoManager.deleteGoodsAll(oldGoods);
                                }
                            }*/
                        }
                    }
                }
                packageItems.clear();
                if ("1".equals(bean.data.is_guodu)) {
                    //分类过渡滑动
                    isGuodu = true;
                    secondTypeLayout.setVisibility(View.GONE);
                    headerLayout.setVisibility(View.VISIBLE);
                    headerLayout.setContentDescription("0");

                    goodsList.clear();
                    //搜索虚拟分类
                    if (null!=searchGoodsItem && searchGoodsItem.size() > 0) {
                        GoodsType goodsType = new GoodsType();
                        goodsType.type_id = "search";
                        goodsType.name = keyWord;
                        goodsType.goodsList = searchGoodsItem;
                        goodsList.add(goodsType);
                        LogUtil.log("searchGoodsType","5、过渡滑动 添加搜索虚拟分类 goodsType== " + new Gson().toJson(goodsType));
                    }
                    //先判断是否有折扣商品
                    if(bean.data.foodDiscountlist.size() > 0){
                        GoodsType goodsType = new GoodsType();
                        goodsType.type_id = "discount";
                        goodsType.name = "折扣";
                        ArrayList<GoodsListBean.GoodsInfo> tempList = new ArrayList<>();
                        for (GoodsListBean.GoodsInfo goods : bean.data.foodDiscountlist){
                            goods.shop_id = shop_id;
                            goods.discount_type="1";//折扣商品
                            goods.type_id = "discount";
                            if ("0".equals(goods.is_nature)) {
                                if (null != goods.nature) {
                                    goods.nature.clear();
                                }
                            }
                            tempList.add(goods);
                        }
                        goodsType.goodsList = tempList;
                        goodsList.add(goodsType);
                    }

                    for (GoodsListBean.Type type : bean.data.foodtype) {
                        GoodsType goodsType = new GoodsType();
                        goodsType.type_id = type.id;
                        goodsType.name = type.name;
                        ArrayList<GoodsListBean.GoodsInfo> tempList = new ArrayList<>();
                        for (GoodsListBean.GoodsInfo goods : bean.data.foodlist) {
                            if (goods.type_id.equals(type.id)) {
                                goods.shop_id = shop_id;
                                if ("0".equals(goods.is_nature)) {
                                    if (null != goods.nature) {
                                        goods.nature.clear();
                                    }
                                }
                                tempList.add(goods);
                            }
                        }
                        goodsType.goodsList = tempList;
                        goodsList.add(goodsType);
                    }
                    //初始化一级分类列表
                    refreshGoodsTypeCount();

                    //关闭动画
//                    closeLoadingView();
                    typeAdapter = new TypeAdapter2(getContext(), goodsList);
                    typeAdapter.addItemSelectedListener(this);
                    rvClassify.setAdapter(typeAdapter);
                    goodsGroupAdapter = new ShopBigGoodsGroupAdapter(getContext(), goodsList, shopCart, shopInfo);
                    rvGoods.setAdapter(goodsGroupAdapter);
                    goodsGroupAdapter.setShopCartListener(this);
                    goodsGroupAdapter.setIToDetailPage(this);
                    goodsGroupAdapter.setIShowLimitTime(this);
                    initHeadView();
                    //设置过渡滑动的监听
                    rvGoods.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            switch (newState) {
                                case RecyclerView.SCROLL_STATE_DRAGGING:
                                    Glide.with(SelectBigGoodsFragment2.this).resumeRequests();
                                    break;
                                case RecyclerView.SCROLL_STATE_SETTLING:
                                    Glide.with(SelectBigGoodsFragment2.this).pauseRequests();
                                    break;
                                case RecyclerView.SCROLL_STATE_IDLE:
                                    Glide.with(SelectBigGoodsFragment2.this).resumeRequests();
                                    break;
                            }

                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (isGuodu) {
                                if (recyclerView.canScrollVertically(1) == false) {//无法下滑
                                    showHeadView();
                                    return;
                                }
                                View underView = null;
                                if (dy > 0) {
                                    underView = rvGoods.findChildViewUnder(headerLayout.getX(), headerLayout.getMeasuredHeight() + 1);
                                } else {
                                    underView = rvGoods.findChildViewUnder(headerLayout.getX(), 0);
                                }
                                if (underView != null && underView.getContentDescription() != null) {
                                    int position = Integer.parseInt(underView.getContentDescription().toString());
                                    GoodsType goodsType = goodsGroupAdapter.getGoodsTypeByPosition(position);
                                    if(null!=goodsType&&null!=headType) {
                                        if (leftClickType || !goodsType.name.equals(headType.name)) {
                                            if (dy > 0 && headerLayout.getTranslationY() <= 1 && headerLayout.getTranslationY() >= -1 * headerLayout.getMeasuredHeight() * 4 / 5 && !leftClickType) {
                                                int dealtY = underView.getTop() - headerLayout.getMeasuredHeight();
                                                headerLayout.setTranslationY(dealtY);
                                            } else if (dy < 0 && headerLayout.getTranslationY() <= 0 && !leftClickType) {
                                                if ("search".equals(goodsType.type_id)) {
                                                    String key = "（#"+goodsType.name+"#的相关结果）";
                                                    SpannableString spannableString = StringUtils.matcherSearchWord2(Color.parseColor("#828282"), goodsType.name + key, key);
                                                    tvType.setText(spannableString);
                                                } else
                                                    tvType.setText(goodsType.name);
                                                int dealtY = underView.getBottom() - headerLayout.getMeasuredHeight();
                                                headerLayout.setTranslationY(dealtY);
                                            } else {
                                                headerLayout.setTranslationY(0);
                                                headType = goodsType;
                                                if ("search".equals(headType.type_id)) {
                                                    String key = "（#"+headType.name+"#的相关结果）";
                                                    SpannableString spannableString = StringUtils.matcherSearchWord2(Color.parseColor("#828282"), headType.name + key, key);
                                                    tvType.setText(spannableString);
                                                } else
                                                    tvType.setText(headType.name);
                                                for (int i = 0; i < goodsList.size(); i++) {
                                                    if (goodsList.get(i).type_id == headType.type_id) {
                                                        rvClassify.smoothScrollToPosition(i);
                                                        typeAdapter.setSelectedPositon(i, false);
                                                        break;
                                                    }
                                                }
                                                if (leftClickType) leftClickType = false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                    typeAdapter.setSelectedPositon(typeSelectPosition,false);
                    if (0 != this.position) {
                        LogUtil.log("searchGoodsType","8、过渡滑动 搜索商品定位 position== " + this.position);
                        ((LinearLayoutManager) rvGoods.getLayoutManager()).scrollToPositionWithOffset(this.position+1,UIUtils.dip2px(30));
                        this.position = 0;
                    }
                } else {
                    isGuodu = false;
                    secondTypes = bean.data.foodsecondtypelist;
                    //初始化数据
                    if ("1".equals(bean.data.is_total)) {
                        //一次性返回
                        isTotal = true;
                        foodlist.clear();//存放所有商品数据信息
                        //搜索虚拟分类
                        if (null!=searchGoodsItem && searchGoodsItem.size() > 0) {
                            for (GoodsListBean.GoodsInfo goods : searchGoodsItem) {
                                goods.shop_id = shop_id;
                                goods.type_id = "search";
                                foodlist.add(goods);
                            }
                            LogUtil.log("searchGoodsType","6、非过渡滑动 is_total 1 添加搜索虚拟分类 foodlist== " + new Gson().toJson(foodlist));
                        }
                        if(bean.data.foodDiscountlist.size() > 0){
                            for (GoodsListBean.GoodsInfo goods : bean.data.foodDiscountlist){
                                goods.shop_id = shop_id;
                                goods.discount_type="1";//折扣商品
                                goods.type_id = "discount";
                                if ("0".equals(goods.is_nature)) {
                                    if (null != goods.nature) {
                                        goods.nature.clear();
                                    }
                                }
                                foodlist.add(goods);
                            }
                        }
                        for (GoodsListBean.GoodsInfo goods : bean.data.foodlist) {
                            goods.shop_id = shop_id;
                            if ("0".equals(goods.is_nature)) {
                                if (null != goods.nature) {
                                    goods.nature.clear();
                                }
                            }
                            foodlist.add(goods);
                        }
                    } else {
                        isTotal = false;
                        //搜索虚拟分类
                        if (null!=searchGoodsItem && searchGoodsItem.size() > 0) {
                            ArrayList<GoodsListBean.GoodsInfo> tempList = new ArrayList<>();
                            for (GoodsListBean.GoodsInfo goods : searchGoodsItem) {
                                goods.shop_id = shop_id;
                                goods.type_id = "search";
                                tempList.add(goods);
                            }
                            HashMap<String, ArrayList<GoodsListBean.GoodsInfo>> goodsMap = new HashMap<>();
                            goodsMap.put("0", tempList);
                            allGoodsList.put("search", goodsMap);
                            LogUtil.log("searchGoodsType","7、非过渡滑动 is_total 0 添加搜索虚拟分类 allGoodsList== " + new Gson().toJson(allGoodsList));
                        }
                        if(bean.data.foodDiscountlist.size() > 0){
                            ArrayList<GoodsListBean.GoodsInfo> tempList = new ArrayList<>();
                            for (GoodsListBean.GoodsInfo goods : bean.data.foodDiscountlist){
                                goods.shop_id = shop_id;
                                goods.discount_type="1";//折扣商品
                                goods.type_id = "discount";
                                if ("0".equals(goods.is_nature)) {
                                    if (null != goods.nature) {
                                        goods.nature.clear();
                                    }
                                }
                                tempList.add(goods);
                            }
                            HashMap<String, ArrayList<GoodsListBean.GoodsInfo>> goodsMap = new HashMap<>();
                            goodsMap.put("0", tempList);
                            allGoodsList.put("discount", goodsMap);
                        }
                        //设置分批加载的滚动监听
                        if (null != rvGoods) {
                            rvGoods.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    if (getActivity() == null){
                                        return;
                                    }
                                }

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    int visibleItemCount = recyclerView.getChildCount();
                                    int totalItemCount = goodsManager.getItemCount();
                                    int firstVisibleItem = goodsManager.findFirstVisibleItemPosition();

                                    if (!isLoading && (totalItemCount - visibleItemCount <= firstVisibleItem && dy > 0)) {
                                        if (!"taocan".equals(selectedTypeId) && !"search".equals(selectedTypeId)) {
                                            showMoreGoods();
                                        }
                                    }
                                }
                            });
                        }
                    }
                    //初始化一级分类
                    goodsList.clear();
                    //搜索虚拟分类
                    if (null!=searchGoodsItem && searchGoodsItem.size() > 0) {
                        GoodsType goodsType = new GoodsType();
                        goodsType.type_id = "search";
                        goodsType.name = keyWord;
                        goodsType.goodsList = searchGoodsItem;
                        goodsList.add(goodsType);
                    }
                    if (bean.data.foodDiscountlist.size() > 0) {
                        GoodsType goodsType = new GoodsType();
                        goodsType.type_id = "discount";
                        goodsType.name = "折扣";
                        goodsList.add(goodsType);
                    }
                    for (GoodsListBean.Type type : bean.data.foodtype) {
                        GoodsType goodsType = new GoodsType();
                        goodsType.type_id = type.id;
                        goodsType.name = type.name;
                        goodsList.add(goodsType);
                    }
                    //初始化一级分类列表
                    refreshGoodsTypeCount();
                    typeAdapter = new TypeAdapter2(getContext(), goodsList);
                    typeAdapter.addItemSelectedListener(this);
                    if (null != rvClassify) {
                        rvClassify.setAdapter(typeAdapter);
                        typeAdapter.setSelectedPositon(typeSelectPosition,true);
                    }
                    handler.sendEmptyMessageDelayed(1, 600);
                }
            }

            //无论从那种页面返回刷新，起送价都得刷新
            basicPrice = bean.data.basicprice;
            tvTips.setText(FormatUtil.numFormat(basicPrice) + "元起送");
            showCartData();
            if ("order_again".equals(((SelectGoodsActivity3)getActivity()).from_page)){
                String tip = ((SelectGoodsActivity3) getActivity()).goods_tip;
                if (1 != shopInfo.worktime) {
                    if (!StringUtils.isEmpty(shopInfo.outtime_info)) {
                        showGoodsTipDialog(tip);
                        if (shopTipDialog == null){
                            showCartDetail();
                        }
                    }
                }else {
                    showGoodsTipDialog(tip);
                    if (shopTipDialog == null){
                        showCartDetail();
                    }
                }

            }
        }
    }

    //显示更多的评论
    private void showMoreGoods() {
        isLoading = true;
        isLoadMore = true;
        String temp = "discount".equals(selectedTypeId) ? "-1" : selectedTypeId;
        //加载更多商品
        HashMap<String, String> params = GetGoodsByTypeRequest.getGoodsByTypeRequest(token, admin_id, shop_id, temp, selectedSecondId
                , "3", ("discount".equals(selectedTypeId) && 1 == page ? 2 : page) + "");
        goodsPresenter.loadData(UrlConst.GETGOODSBYTYPE, params);
    }


    @Override
    public void loadGoodsFail() {
        showLoadingFailView();
        isInitData = false;
    }

    //加载失败
    private void showLoadingFailView() {
        if (null != animationDrawable) {
            animationDrawable.stop();
        }
        llFail.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    //置换商品数据
    public boolean changeGoodsInfoValue(GoodsListBean.GoodsInfo newInfo, GoodsListBean.GoodsInfo oldInfo) {
        //如果商品停售  删除
        if (!newInfo.status.equals("NORMAL")) {
            return false;
        }
        //商品是否开启属性 不同就删除
        if (!newInfo.is_nature.equals(oldInfo.is_nature)) {
            return false;
        }
        //如果用户登录后不是有效会员，商品又开启了会员专享，直接删除
        if (!TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
            //登录后
            if ("1".equals(newInfo.memberlimit)) {
                if (!isEffectiveVip) {
                    return false;
                }
            }
        }
        //如果购买商品不满足新商品的库存就删掉
        if (newInfo.stockvalid.equals("1")) {
            if (oldInfo.count > newInfo.stock) {
                return false;
            }
        }
        //判断限购
        if ("1".equals(newInfo.is_limitfood)) {
            if ("1".equals(newInfo.datetage)) {
                if ("1".equals(newInfo.timetage)) {
                    int selectNum = 0;
                    if (oldInfo.nature.size() > 0) {
                        selectNum = shopCart.getGoodsCount(newInfo.id);
                    } else {
                        selectNum = oldInfo.count;
                    }
                    //判断已获取限购商品数量是否到达活动期间上限
                    if ("1".equals(newInfo.is_all_limit)) {
                        int total = 0;
                        if (!TextUtils.isEmpty(newInfo.hasBuyNum)) {
                            total = selectNum + Integer.parseInt(newInfo.hasBuyNum);
                        } else {
                            total = selectNum;
                        }
                        if (total > Integer.parseInt(newInfo.is_all_limit_num)) {
                            return false;
                        }
                    }

                    //判断当前数量是否达到当天购买上限
                    if ("1".equals(newInfo.is_customerday_limit)) {
                        int count = 0;
                        if (!TextUtils.isEmpty(newInfo.hasBuyNumByDay)) {
                            count = selectNum + Integer.parseInt(newInfo.hasBuyNumByDay);
                        } else {
                            count = selectNum;
                        }
                        //判断已获取限购商品数量是否到达每天上限
                        if (count > Integer.parseInt(newInfo.day_foodnum)) {
                            return false;
                        }
                    }

                    //判断当前数量是否达到每人每单限购数量
                    if ("1".equals(newInfo.is_order_limit)) {
                        //判断已获取限购商品数量是否到达每天上限
                        if (selectNum > Integer.parseInt(newInfo.order_limit_num)) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        //新传商品带属性
        if (null != newInfo.nature && newInfo.nature.size() > 0) {
            if (null != oldInfo.nature && oldInfo.nature.size() > 0) {
                //判断选购商品中选中的属性在新商品中能否找到
                for (GoodsListBean.GoodsNature goodsNature : oldInfo.nature) {
                    if (newInfo.nature.contains(goodsNature)) {
                        for (GoodsListBean.GoodsNatureData selectData : goodsNature.data) {
                            GoodsListBean.GoodsNature newNature = newInfo.nature.get(newInfo.nature.indexOf(goodsNature));
                            //如果新商品属性分类下最多选择的数量和之前不同，直接删掉
                            if (!newNature.maxchoose.equals(goodsNature.maxchoose)) {
                                return false;
                            }
                            //只判断选中的商品属性有没有，如果没有就直接删除该商品
                            if (selectData.is_selected) {
                                if (newNature.data.contains(selectData)) {
                                    GoodsListBean.GoodsNatureData newData = newNature.data.get(newNature.data.indexOf(selectData));
                                    selectData.naturevalue = newData.naturevalue;
                                    selectData.price = newData.price;
                                } else {
                                    return false;
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            if (null != oldInfo.nature && oldInfo.nature.size() > 0) {
                return false;
            }
        }
        oldInfo.price = newInfo.price;
        oldInfo.buying_price = newInfo.buying_price;
        oldInfo.formerprice = newInfo.formerprice;
        oldInfo.has_formerprice = newInfo.has_formerprice;
        oldInfo.is_dabao = newInfo.is_dabao;
        oldInfo.dabao_money = newInfo.dabao_money;
        oldInfo.open_autostock = newInfo.open_autostock;
        oldInfo.autostocknum = newInfo.autostocknum;
        oldInfo.member_price = newInfo.member_price;
        oldInfo.member_price_used = newInfo.member_price_used;
        oldInfo.member_grade_price = newInfo.member_grade_price;
        oldInfo.memberlimit = newInfo.memberlimit;
        oldInfo.unit = newInfo.unit;
        oldInfo.stockvalid = newInfo.stockvalid;
        oldInfo.stock = newInfo.stock;
        oldInfo.is_limitfood = newInfo.is_limitfood;
        oldInfo.datetage = newInfo.datetage;
        oldInfo.timetage = newInfo.timetage;
        oldInfo.is_all_limit = newInfo.is_all_limit;
        oldInfo.hasBuyNum = newInfo.hasBuyNum;
        oldInfo.is_all_limit_num = newInfo.is_all_limit_num;
        oldInfo.is_order_limit = newInfo.is_order_limit;
        oldInfo.order_limit_num = newInfo.order_limit_num;
        oldInfo.is_customerday_limit = newInfo.is_customerday_limit;
        oldInfo.hasBuyNumByDay = newInfo.hasBuyNumByDay;
        oldInfo.day_foodnum = newInfo.day_foodnum;
        oldInfo.switch_discount = newInfo.switch_discount;
        oldInfo.num_discount = newInfo.num_discount;
        oldInfo.rate_discount = newInfo.rate_discount;
        return true;
    }

    //获取分类下的商品
    @Override
    public void showDataToVIew(GoodsByType bean) {
        isLoading = false;
        if (bean != null) {
            if (null != bean.data.foodlist) {
                if (bean.data.foodlist.size() > 0) {
                    page++;
                    if (!isLoadMore) {
                        goodsItems.clear();
                    }
                    for (GoodsListBean.GoodsInfo goods : bean.data.foodlist) {
                        goods.shop_id = shop_id;
                        if ("0".equals(goods.is_nature)) {
                            if (null != goods.nature) {
                                goods.nature.clear();
                            }
                        }
                        goodsItems.add(goods);
                    }
                    if (null == adapter) {
                        adapter = new ShopBigGoodsAdapter(getContext(), goodsItems, shopCart, shopInfo);
                        if (rvGoods != null) {
                            rvGoods.setAdapter(adapter);
                        }
                        adapter.setShopCartListener(this);
                        adapter.setIToDetailPage(this);
                        adapter.setIShowLimitTime(this);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    if (goodsItems.size() % 50 != 0 || "search".equals(selectedTypeId)) {
                        //商品已加载完毕
                        adapter.setIsLoadAll(true);
                    } else {
                        page = (goodsItems.size() / 50) + 1;
                        adapter.setIsLoadAll(false);
                    }

                    //缓存套餐数据
                    //先判断该分类下有没有存过商品
                    HashMap<String, ArrayList<GoodsListBean.GoodsInfo>> goodsInType = allGoodsList.get(selectedTypeId);
                    if (null != goodsInType && goodsInType.size() > 0) {
                        if (goodsInType.keySet().contains(selectedSecondId)) {
                            ArrayList<GoodsListBean.GoodsInfo> goodsTemp = goodsInType.get(selectedSecondId);
                            if (null != goodsTemp && goodsTemp.size() > 0) {
                                goodsTemp.clear();
                                goodsTemp.addAll(goodsItems);
                            }
                        } else {
                            if (goodsItems.size() > 0) {
                                ArrayList<GoodsListBean.GoodsInfo> tempList = new ArrayList<>();
                                for (GoodsListBean.GoodsInfo goodsInfo : goodsItems) {
                                    tempList.add(goodsInfo);
                                }
                                goodsInType.put(selectedSecondId, tempList);
                                allGoodsList.put(selectedTypeId, goodsInType);
                            }
                        }
                    } else {
                        HashMap<String, ArrayList<GoodsListBean.GoodsInfo>> goodsMap = new HashMap<>();
                        ArrayList<GoodsListBean.GoodsInfo> tempList = new ArrayList<>();
                        for (GoodsListBean.GoodsInfo goodsInfo : goodsItems) {
                            tempList.add(goodsInfo);
                        }
                        goodsMap.put(selectedSecondId, tempList);
                        allGoodsList.put(selectedTypeId, goodsMap);
                    }
                } else {
                    if (isLoadMore) {
                        adapter.setIsLoadAll(true);
                    } else {
                        goodsItems.clear();
                        goodsItems.addAll(bean.data.foodlist);
                        if (null == adapter) {
                            adapter = new ShopBigGoodsAdapter(getContext(), goodsItems, shopCart, shopInfo);
                            adapter.setIsLoadAll(true);
                            rvGoods.setAdapter(adapter);
                            adapter.setShopCartListener(this);
                            adapter.setIToDetailPage(this);
                            adapter.setIShowLimitTime(this);
                        } else {
                            adapter.setIsLoadAll(true);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }


    @Override
    public void onTypeItemSelected(int position, GoodsType goodsType) {
        LogUtil.log("searchGoodsType","8.0、触发 onTypeItemSelected ");
        typeSelectPosition = position;
        rvClassify.smoothScrollToPosition(position);
        if (isGuodu) {
            leftClickType = true;
            int movePos = 0;
            for (int i = 0; i < position; i++) {
                movePos += goodsList.get(i).goodsList.size() + 1;
            }
            LinearLayoutManager layoutManager = (LinearLayoutManager) rvGoods.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(movePos, 0);
            if (rvGoods.canScrollVertically(1) == true){
                if ("search".equals(goodsType.type_id)) {
                    String key = "（#"+goodsType.name+"#的相关结果）";
                    SpannableString spannableString = StringUtils.matcherSearchWord2(Color.parseColor("#828282"), goodsType.name + key, key);
                    tvType.setText(spannableString);
                } else
                    tvType.setText(goodsType.name);
            }
        } else {
            //显示二级分类，默认选中第一个
            selectedTypeId = goodsType.type_id;
            if (null != secondTypes && secondTypes.size() > 0) {
                secondTypeNames.clear();
                currentSecondTypes.clear();
                for (GoodsListBean.SecondType type : secondTypes) {
                    if (type.typeid.equals(goodsType.type_id)) {
                        secondTypeNames.add(type.name);
                        currentSecondTypes.add(type);
                    }
                }
                if (currentSecondTypes.size() > 0) {
                    secondTypeLayout.setVisibility(View.VISIBLE);
                    secondTypeLayout.setTypeList(secondTypeNames);
                    secondTypeLayout.setCheckTypeListener(new SlidTabView.OnCheckTypeListener() {
                        @Override
                        public void check(int position) {
                            selectedSecondId = currentSecondTypes.get(position).second_type_id;
                            //请求该分类下的商品
                            getGoodsByType();
                        }
                    });
                    secondTypeLayout.setCheck(0);
                } else {
                    secondTypeLayout.setVisibility(View.GONE);
                    selectedSecondId = "0";
                    getGoodsByType();
                }
            } else {
                selectedSecondId = "0";
                getGoodsByType();
            }
        }
    }

    //获取分类下的商品
    private void getGoodsByType() {
        if (isTotal) {
            goodsItems.clear();
            for (GoodsListBean.GoodsInfo goods : foodlist) {
                if ("0".equals(selectedSecondId)) {
                    //没有二级分类,显示所有一级分类下的商品
                    if (goods.type_id.equals(selectedTypeId)) {
                        goodsItems.add(goods);
                    }
                } else {
                    if (goods.type_id.equals(selectedTypeId) && (goods.second_type_id.equals(selectedSecondId))) {
                        goodsItems.add(goods);
                    }
                }
            }
            adapter = new ShopBigGoodsAdapter(getContext(), goodsItems, shopCart, shopInfo);
            adapter.setIsLoadAll(true);
            rvGoods.setAdapter(adapter);
            adapter.setShopCartListener(this);
            adapter.setIToDetailPage(this);
            adapter.setIShowLimitTime(this);
            if (0 != this.position && "search".equals(selectedTypeId)) {
                LogUtil.log("searchGoodsType","9、非过渡滑动 搜索商品定位 isTotal=="+isTotal+"; position== " + this.position);
                ((LinearLayoutManager) rvGoods.getLayoutManager()).scrollToPositionWithOffset(this.position,0);
                this.position = 0;
            }
        } else {
            HashMap<String, ArrayList<GoodsListBean.GoodsInfo>> list = allGoodsList.get(selectedTypeId);
            if (null != list && list.size() > 0) {
                ArrayList<GoodsListBean.GoodsInfo> cachGoods = list.get(selectedSecondId);
                if (null != cachGoods && cachGoods.size() > 0) {
                    //使用缓存中的数据
                    goodsItems.clear();
                    for (GoodsListBean.GoodsInfo goods : cachGoods) {
                        goodsItems.add(goods);
                    }
                    if (null == adapter) {
                        adapter = new ShopBigGoodsAdapter(getContext(), goodsItems, shopCart, shopInfo);
                        adapter.setShopCartListener(this);
                        adapter.setIToDetailPage(this);
                        adapter.setIShowLimitTime(this);
                        rvGoods.setAdapter(adapter);
                        if (0 != this.position && "search".equals(selectedTypeId)) {
                            LogUtil.log("searchGoodsType","10、非过渡滑动 搜索商品定位 isTotal=="+isTotal+"; position== " + this.position);
                            ((LinearLayoutManager) rvGoods.getLayoutManager()).scrollToPositionWithOffset(this.position, 0);
                            this.position = 0;
                        }
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    if (goodsItems.size() % 50 != 0 || "search".equals(selectedTypeId)) {
                        //商品已加载完毕
                        adapter.setIsLoadAll(true);
                    } else {
                        page = (goodsItems.size() / 50) + 1;
                        adapter.setIsLoadAll(false);
                    }
                } else {
                    requestGoodsByPage();
                }
            } else {
                requestGoodsByPage();
            }
        }
    }

    private void requestGoodsByPage() {
        if ("search".equals(selectedTypeId))
            return;
        page = 1;
        isLoadMore = false;
        showLoadingDialog();
        String temp = "discount".equals(selectedTypeId) ? "-1" : selectedTypeId;
        //加载更多商品
        HashMap<String, String> params = GetGoodsByTypeRequest.getGoodsByTypeRequest(token, admin_id, shop_id, temp, selectedSecondId, "3", page + "");
        goodsPresenter.loadData(UrlConst.GETGOODSBYTYPE, params);
    }

    @Override
    public void add(View view, int position, GoodsListBean.GoodsInfo goods) {

        //更改右侧分类选中数量
        updateGoodsTypeCount();

        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        mainLayout.getLocationInWindow(recycleLocation);
        imgCart.getLocationInWindow(cartLocation);

        AnimatorView animatorView = new AnimatorView(getContext());
        animatorView.setStartPosition(new Point(addLocation[0] + view.getWidth() / 2, addLocation[1] - recycleLocation[1]));
        animatorView.setEndPosition(new Point(cartLocation[0] + (imgCart.getWidth()) / 2, cartLocation[1] - recycleLocation[1]));
        mainLayout.addView(animatorView, new LinearLayout.LayoutParams(50, 50));
        animatorView.setAnimatorListener(this);
        animatorView.startBeizerAnimation();
        showCartData();//显示购物车数据

    }

    public void addFromDialog(View view, int position, GoodsListBean.GoodsInfo goods) {

        //更改右侧分类选中数量
        updateGoodsTypeCount();

        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] dialogLocation = new int[2];
        view.getLocationInWindow(addLocation);
        imgCart.getLocationInWindow(cartLocation);
        centerPopView.linearLayout.getLocationInWindow(dialogLocation);


        AnimatorView animatorView = new AnimatorView(getContext());
        animatorView.setStartPosition(new Point(addLocation[0], addLocation[1]));
        animatorView.setEndPosition(new Point(cartLocation[0] + (imgCart.getWidth()) / 2, cartLocation[1] - imgCart.getHeight()));
        centerPopView.rootView.addView(animatorView, new LinearLayout.LayoutParams(50, 50));
        animatorView.setAnimatorListener(this);
        animatorView.startBeizerAnimation();

        showCartData();//显示购物车数据

    }

    @Override
    public void remove(int position, GoodsListBean.GoodsInfo goods) {
        //更改右侧分类选中数量
        updateGoodsTypeCount();
        showCartData();
    }


    //显示分享弹框
    public void showShareDilaog() {
        if (shareDialog == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_share, null);
            final Tencent tencent = Tencent.createInstance(Const.QQ_APP_ID, getContext());
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(getContext(), Constants.APP_ID);
            picUrl = ((SelectGoodsActivity3) getActivity()).getShopLogo();
            final String des = "这家店我试过，味道很棒哦。点击就能下单，快来尝尝。";
            //微信
            //微信好友
            ((LinearLayout) view.findViewById(R.id.ll_share_friend)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareUtils().WXShareUrl(msgApi, RetrofitManager.BASE_URL_H5 + "h5/lwm/share/shareshop?admin_id=" + admin_id + "&shop_id=" + shop_id+"&bundleName=consumer"+"&customerapp_id=" + RetrofitUtil.ADMIN_APP_ID, picUrl,
                            shopInfo.shopname, des, ShareUtils.WX_SEESSION);
                    shareDialog.dismiss();
                }
            });
            //朋友圈
            ((LinearLayout) view.findViewById(R.id.ll_share_online)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareUtils().WXShareUrl(msgApi, RetrofitManager.BASE_URL_H5 + "h5/lwm/share/shareshop?admin_id=" + admin_id + "&shop_id=" + shop_id+"&bundleName=consumer"+"&customerapp_id=" + RetrofitUtil.ADMIN_APP_ID, picUrl,
                            shopInfo.shopname, des, ShareUtils.WX_TIME_LINE);
                    shareDialog.dismiss();
                }
            });

            //qq
            ((LinearLayout) view.findViewById(R.id.ll_share_qq)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtils.QQshare(tencent, getActivity(), shopInfo.shopname, RetrofitManager.BASE_URL_H5 + "h5/lwm/share/shareshop?admin_id=" + admin_id + "&shop_id=" + shop_id+"&bundleName=consumer"+"&customerapp_id=" + RetrofitUtil.ADMIN_APP_ID,
                            des, picUrl);
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


            shareDialog = DialogUtils.BottonDialog(getContext(), view);
        }
        shareDialog.show();
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
        View natureView = View.inflate(getContext(), R.layout.dialog_edit_goods, null);
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
        if (null != goods.packageNature && goods.packageNature.size() > 0) {
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

        for (GoodsListBean.GoodsInfo natureGoods : shopCart.getGoodsList(goods.id)) {
            if (natureGoods.id.equals(goods.id)) {
                natureGoodsList.add(natureGoods);
            }
        }

        if(natureGoodsList.size()>0){
            isFirst=false;
        }else {
            isFirst=true;
            natureGoodsList.add(goods);
        }

        natureGoodsAdapter = new ShopNatureGoodsAdapter(getContext(), natureGoodsList, shopCart, tvTotalPrice);
        natureGoodsAdapter.setGoodsPosition(position);
        rv_nature_container.setAdapter(natureGoodsAdapter);
        natureGoodsAdapter.setGoodsPosition(position);
        natureGoodsAdapter.setDeletNatureListener(this);
        rv_nature_container.setLayoutManager(new LinearLayoutManager(getContext()));
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
                if (isGuodu) {
                    goodsGroupAdapter.notifyItemChanged(position);
                } else {
                    // TODO: 2017/7/3 0003
                    adapter.notifyItemChanged(position);
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
                    int count = shopCart.getGoodsCount(goods.id);
                    tvNatureCount.setText(count+"");
                    natureGoodsList.clear();
                    natureGoodsList.addAll(shopCart.getGoodsList(goods.id));
                    if (natureGoodsList.size() == 0) {
                        if (isGuodu) {
                            goodsGroupAdapter.notifyItemChanged(position);
                        } else {
                            adapter.notifyItemChanged(position);
                        }
                        remove(position, goods);
                        if (null != centerPopView) {
                            centerPopView.dismissCenterPopView();
                        }
                        return;
                    }
                    natureGoodsAdapter.notifyDataSetChanged();
                    natureGoodsAdapter.updateTotalPrice();
                    if (isGuodu) {
                        goodsGroupAdapter.notifyItemChanged(position);
                    } else {
                        adapter.notifyItemChanged(position);
                    }
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
        centerPopView = new CenterPopView(getActivity());
        centerPopView.setContentView(natureView);
        centerPopView.showCenterPopView();
    }

    @Override
    public void updateTotalPrice() {
        showCartData();
    }

    //删除属性商品
    @Override
    public void deleteNatureGoods(GoodsListBean.GoodsInfo goods, int position, int refreshPos) {
        if (natureGoodsList.size() == 0){
            return;
        }
        if (shopCart.subShoppingSingle(natureGoodsList.get(position))) {
            int count = shopCart.getGoodsCount(goods.id);
            tvNatureCount.setText(count+"");
            natureGoodsList.clear();
            natureGoodsList.addAll(shopCart.getGoodsList(goods.id));
            //减去存在购物车最后一个商品
            if (natureGoodsList.size() == 0) {
                if (isGuodu) {
                    goodsGroupAdapter.notifyItemChanged(refreshPos);
                } else {
                    adapter.notifyItemChanged(refreshPos);
                }
                remove(refreshPos, goods);
                if (null != centerPopView) {
                    centerPopView.dismissCenterPopView();
                }
                return;
            }
            natureGoodsAdapter.notifyDataSetChanged();
            natureGoodsAdapter.updateTotalPrice();
            if (isGuodu) {
                goodsGroupAdapter.notifyItemChanged(refreshPos);
            } else {
                adapter.notifyItemChanged(refreshPos);
            }
            remove(refreshPos, goods);
        }
    }


    private Dialog loginTipDialog;

    //显示限购商品需登录
    @Override
    public void showTipDialog() {
        View dialogView = View.inflate(getActivity(), R.layout.dialog_login_tip, null);
        loginTipDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);
        loginTipDialog.setContentView(dialogView);
        loginTipDialog.setCanceledOnTouchOutside(false);

        //去掉dialog上面的横线
        Context context = loginTipDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = loginTipDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

        Button btn_quit = (Button) dialogView.findViewById(R.id.btn_quit);
        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != loginTipDialog) {
                    loginTipDialog.dismiss();
                }
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转登录界面
                startActivityForResult(new Intent(getActivity(), LoginActivity.class),
                        LIMIT_LOGIN);
            }
        });
        loginTipDialog.show();
    }


    @Override
    public void showVipDialog() {
        View dialogView = View.inflate(getActivity(), R.layout.dialog_login_tip_vip, null);
        loginTipDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);
        loginTipDialog.setContentView(dialogView);
        loginTipDialog.setCanceledOnTouchOutside(false);

        //去掉dialog上面的横线
        Context context = loginTipDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = loginTipDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

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
                startActivityForResult(new Intent(getActivity(), LoginActivity.class),
                        VIP_LOGIN);
            }
        });
        loginTipDialog.show();
    }

    private void showCartData() {
        //处理满减的情况
        String limit = "";
        if (!TextUtils.isEmpty(data.is_only_promotion)&&"1".equals(data.is_only_promotion)) {
            limit = "（在线支付专享）";
        }
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            tvCost.setVisibility(View.VISIBLE);
            imgCart.setImageResource(R.mipmap.icon_shopping_cart_red);
            String totalPrice = df.format(shopCart.getShoppingTotalPrice());
            tvCost.setText(FormatUtil.numFormat(totalPrice));
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText("" + shopCart.getShoppingAccount());
            if (shopCart.getShoppingTotalPrice() < Float.parseFloat(basicPrice)) {
                tvSubmit.setVisibility(View.GONE);
                tvTips.setVisibility(View.VISIBLE);
                String elsePrice = df.format(Float.parseFloat(basicPrice) - shopCart.getShoppingTotalPrice());
                tvTips.setText("差" + FormatUtil.numFormat(elsePrice) + "元起送");
            } else {
                tvTips.setVisibility(View.GONE);
                tvSubmit.setVisibility(View.VISIBLE);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isFullMinBuy()){
                            return;
                        }
                        checkOrder();
                    }
                });
            }
            boolean isContainDiscount=false;//是否含有折扣商品
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
            if (!TextUtils.isEmpty(data.open_promotion) && "1".equals(data.open_promotion)&&!isContainDiscount) {
                //从低到高排序
                List<ManJian> sort = sortManJianList(data.promotion_rule);
                //大于起送价的直接显示满减
                for (int i = 0; i < sort.size(); i++) {
                    ManJian manJian = sort.get(i);
                    //  15
                    //10  20   30
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
            if ("1".equals(data.open_promotion)) {
                if (data.promotion_rule.size() > 0) {
                    String manjian = "";
                    for (int i = 0; i < sortManJianList(data.promotion_rule).size(); i++) {
                        ManJian manJian = sortManJianList(data.promotion_rule).get(i);
                        if (i == data.promotion_rule.size() - 1) {
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
    private List<ManJian> sortManJianList(List<ManJian> list){
        ManJian[] array=list.toArray(new ManJian[list.size()]);
        for(int i=0;i<array.length;i++){
            for(int j=i+1;j<array.length;j++){
                if(Float.parseFloat(array[i].amount)>Float.parseFloat(array[j].amount)){
                    //交换位置
                    ManJian temp=null;
                    temp=array[i];
                    array[i]=array[j];
                    array[j]=temp;
                }
            }
        }
        return Arrays.asList(array);
    }

    //处理凑单的提示文字变色
    private SpannableString couDanText(String couMoney) {
        SpannableString s = new SpannableString("还差" +couMoney+"元就能起送【去凑单】");
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), 2, 2+couMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), 7+couMoney.length(), 12+couMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    //处理满减的提示文字变色
    private SpannableString manJianText(String yijian,String elseMoney,String discount,String limit,String header) {
        SpannableString s = new SpannableString(yijian+header+elseMoney+"元可减"+discount+"元"+limit);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), yijian.length()+2, yijian.length()+2+elseMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#DF5457")), yijian.length()+5+elseMoney.length(), yijian.length()+5+elseMoney.length()+discount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                showLoadingView();
                load();
                break;
        }
    }

    @Override
    public void showLimitTime(GoodsListBean.GoodsInfo goods) {
        View dialogView = View.inflate(getContext(), R.layout.dialog_limit_time, null);
        final Dialog limitDialog = new Dialog(getContext(), R.style.CenterDialogTheme2);
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
        if (null != goods.limit_time && goods.limit_time.size() > 0) {
            for (int i = 0; i < goods.limit_time.size(); i++) {
                GoodsListBean.LimitTime limitTime = goods.limit_time.get(i);
                TextView timeView = (TextView) View.inflate(getContext(), R.layout.item_limit_time, null);
                timeView.setText(limitTime.start + "~" + limitTime.stop);
                if (i % 2 == 0) {
                    ll_container1.addView(timeView);
                } else {
                    ll_container2.addView(timeView);
                }
            }
        } else {
            TextView timeView = (TextView) View.inflate(getContext(), R.layout.item_limit_time, null);
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


    //注册更新购物车信息的广播接受者
    public class CartReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String flag = intent.getStringExtra("flag");
            if ("clear_cart".equals(flag)) {
                shopCart.clear();
            } else {
                shopCart = (ShopCart2) intent.getSerializableExtra("shop_cart");
                if (isGuodu) {
                    goodsGroupAdapter.setShopCart(shopCart);
                } else {
                    adapter.setShopCart(shopCart);
                }
            }
            updateCartData();
        }
    }

    //弹出购物清单
    private void showCartDetail() {
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            ShopCartDialog2 cartDialog = new ShopCartDialog2(getContext(), shopCart, shop_id, R.style.cartdialog, isEffectiveVip);
            Window window = cartDialog.getWindow();
            cartDialog.setCanceledOnTouchOutside(true);
            cartDialog.setCancelable(true);
            cartDialog.setIShopCartDialog(this);
            cartDialog.show();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            params.x = 0;
            params.y = ll_cart.getHeight();
            params.dimAmount = 0.5f;
            window.setAttributes(params);
           /* ShopCartPopupWindow popupWindow = ((SelectGoodsActivity3)getActivity()).shopCartPopupWindow;
            if (popupWindow != null){
                boolean b = tvManjian.getVisibility() == View.VISIBLE ;
                popupWindow.bindView(shopCart, shop_id,isEffectiveVip,b);
                popupWindow.setIShopCartDialog(this);
                if (!popupWindow.isShowing()){
                    popupWindow.showCartView();
                }else {
                    popupWindow.dismiss();
                }

            }*/
        }
    }


    @Override
    public void updateCartData() {
        showCartData();
        //刷新商品列表
        //刷新商品分类列表
        refreshGoodsTypeCount();
        typeAdapter.notifyDataSetChanged();
        if (isGuodu) {
            goodsGroupAdapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(getContext(), R.style.transparentDialog);
        }
        loadingDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != centerPopView) {
            centerPopView.dismissCenterPopView();
        }
        //这个地方易报错，暂时注释掉
        if (null != cartReceiver) {
            getContext().unregisterReceiver(cartReceiver);
        }
    }

    @Override
    public void dialogDismiss() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (null != loginTipDialog) {
            loginTipDialog.dismiss();
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(getContext(),s);
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
            View detailView = View.inflate(getActivity(), R.layout.dialog_goods_detail, null);
            int width = UIUtils.getWindowWidth() * 4 / 5;
            int height = UIUtils.getWindowHeight() * 2 / 3;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
            detailView.setLayoutParams(params);

            final CenterDialogView centerDialogView = new CenterDialogView(getActivity());
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
                ll_limit_info.setVisibility(View.VISIBLE);
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
                        TextView timeView = (TextView) View.inflate(getContext(), R.layout.item_limit_time_small, null);
                        timeView.setText(limitTime.start + "~" + limitTime.stop);
                        if (i % 2 == 0) {
                            ll_container1.addView(timeView);
                        } else {
                            ll_container2.addView(timeView);
                        }
                    }
                } else {
                    TextView timeView = (TextView) View.inflate(getContext(), R.layout.item_limit_time_small, null);
                    timeView.setText("00:00~23:59");
                    ll_container1.addView(timeView);
                }


            } else {
                tvLimitTag.setVisibility(View.GONE);
            }
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
                if (!TextUtils.isEmpty(goods.min_price)) {
                    tv_old_price.setText("￥" + FormatUtil.numFormat(Float.parseFloat(goods.formerprice) + Float.parseFloat(goods.min_price) + ""));
                } else {
                    tv_old_price.setText("￥" + FormatUtil.numFormat(goods.formerprice));
                }
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
            UIUtils.glideAppLoad(getContext(),url,R.mipmap.common_def_food,img);
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
            Intent intent = new Intent(getContext(), GoodsDetailActivity2.class);
            intent.putExtra("from_page", "xuangou");
            intent.putExtra("shop_id", shop_id);
            intent.putExtra("shop_name", shopInfo.shopname);
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
                //表示在限购时间段中
                tvTip.setText("距活动结束：");
                //创建倒计时类
                mCountDownTimer = new LimitCountDownTimer(endTime, 1000, tvTipTimeHour, tvTipTimeMiniute, tvTipTimeSecond);
                mCountDownTimer.start();
                mHandler.postDelayed(run, endTime);
            }else{
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
//        Set<String> sets = null;
        for (GoodsListBean.GoodsInfo goodsInfo:list){
            int min = 1;
            if (!StringUtils.isEmpty(goodsInfo.min_buy_count) && Integer.parseInt(goodsInfo.min_buy_count) > 1){
                min = Integer.parseInt(goodsInfo.min_buy_count);
            }
            if (min > map.get(goodsInfo.id)){
                UIUtils.showToast("【"+goodsInfo.name+"】商品不足最小起购份数");
                return false;
            }

//            //判断是否有必选分类
//            if (typeAdapter.getTypeNeedMap() != null && typeAdapter.getTypeNeedMap().size() > 0){
//                if (sets == null){
//                    sets = new HashSet<>();
//                }
//                if (typeAdapter.getTypeNeedMap().containsKey(goodsInfo.type_id)){
//                    sets.add(goodsInfo.type_id);
//                }
//            }
        }
//        //判断是否有必选分类
//        if (sets != null && sets.size() != typeAdapter.getTypeNeedMap().size()){
//            Set<Map.Entry<String, String>> entrySet = typeAdapter.getTypeNeedMap().entrySet();
//            StringBuffer buffer = new StringBuffer("需要选择");
//            for (Map.Entry<String, String> entry : entrySet) {
//                if (!sets.contains(entry.getKey())){
//                    buffer.append("["+entry.getValue()+"]");
//                }
//            }
//            buffer.append("下的商品才可下单哦");
//            showGoodsTypeNeedDialog(buffer.toString());
//            return false;
//        }
        return true;
    }

    @Override
    public void showCheckOrderView(String msg) {
        showGoodsTypeNeedDialog(msg);
    }

    //必选商品检测
    private void checkOrder() {
        ArrayList<String> goodList = new ArrayList<>();
        Iterator<String> iterator = shopCart.getGoodsMap().keySet().iterator();
        while (iterator.hasNext()) {
            String id = iterator.next();
            GoodsListBean.GoodsInfo goodsInfo = shopCart.getGoods(id);
            goodList.add(goodsInfo.original_type_id);
            LogUtil.log("updateCartData","goodsInfo == "+new Gson().toJson(goodsInfo));
        }
        String food_type = new Gson().toJson(goodList);
        HashMap<String, String> params = IsCollectRequest.checkOrder(admin_id, token, shopInfo.id,food_type);
        collectPresenter.loadData(UrlConst.CHECK_ORDER, params, "check");
    }

    //必选分类提示
    private Dialog goodsTypeNeedDialog;
    private TextView tvError;
    private void showGoodsTypeNeedDialog(String msg) {
        if (goodsTypeNeedDialog == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_goods_type_need, null);
            tvError = (TextView) view.findViewById(R.id.tv_error);
            view.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsTypeNeedDialog.dismiss();
                }
            });
            goodsTypeNeedDialog = DialogUtils.centerDialog(getContext(), view);
        }
        tvError.setText(msg);
        goodsTypeNeedDialog.show();
    }

    //添加商品时更新分类
    //添加商品时更新分类数量
    private void updateGoodsTypeCount(){
        //更改右侧分类选中数量
        refreshGoodsTypeCount();
        typeAdapter.notifyDataSetChanged();
    }
    //计算分类商品数量
    private void refreshGoodsTypeCount(){
        for (GoodsType type : goodsList) {
            if ("search".equals(type.type_id))
                continue;
            int count = 0;
            LogUtil.log("updateCartData","type.name == "+type.name +" start ---------------");
            Iterator<String> iterator = shopCart.getGoodsMap().keySet().iterator();
            while (iterator.hasNext()) {
                String id = iterator.next();
                GoodsListBean.GoodsInfo goodsInfo = shopCart.getGoods(id);
                boolean isDiscountGoods = "1".equals(goodsInfo.discount_show_type) || "2".equals(goodsInfo.discount_show_type);
                LogUtil.log("updateCartData","goods == "+goodsInfo.name + " isDiscountGoods == " + isDiscountGoods);
                if (isDiscountGoods) {
                    if (type.type_id.equals("discount")) {
                        count += shopCart.getGoodsCount(goodsInfo.id);
                        LogUtil.log("updateCartData","goods == "+goodsInfo.name + " type.name == " + type.name);
                    } else
                        LogUtil.log("updateCartData","goods == "+goodsInfo.name + " type.name == " + type.name + " 未找到");
                } else {
                    if (goodsInfo.type_id.equals(type.type_id)) {
                        count += shopCart.getGoodsCount(goodsInfo.id);
                        LogUtil.log("updateCartData","goods == "+goodsInfo.name + " type.name == " + type.name);
                    } else if ("search".equals(goodsInfo.type_id) && goodsInfo.original_type_id.equals(type.type_id)) {
                        count += shopCart.getGoodsCount(goodsInfo.id);
                        LogUtil.log("updateCartData","goods == "+goodsInfo.name + " type.name == " + type.name);
                    } else
                        LogUtil.log("updateCartData","goods == "+goodsInfo.name + " type.name == " + type.name + " 未找到");
                }
            }
            type.count = count;
            LogUtil.log("updateCartData","type.name == " + type.name +" end type.count == " + type.count+" ------------");
        }
    }
}


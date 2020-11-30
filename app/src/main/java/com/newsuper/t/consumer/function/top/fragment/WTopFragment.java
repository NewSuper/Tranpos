package com.newsuper.t.consumer.function.top.fragment;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.AoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.*;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.application.BaseApplication;
import com.xunjoy.lewaimai.consumer.base.BaseFragment;
import com.xunjoy.lewaimai.consumer.bean.GetCouponBean;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.ItemRecod;
import com.xunjoy.lewaimai.consumer.bean.OrderAgainBean;
import com.xunjoy.lewaimai.consumer.bean.TopBean;
import com.xunjoy.lewaimai.consumer.bean.WShopCart2;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.function.TopActivity3;
import com.xunjoy.lewaimai.consumer.function.cityinfo.activity.CityInfoActivity;
import com.xunjoy.lewaimai.consumer.function.distribution.HelpClassifyActivity;
import com.xunjoy.lewaimai.consumer.function.distribution.PaotuiOrderActivity;
import com.xunjoy.lewaimai.consumer.function.distribution.PaotuiTopActivity;
import com.xunjoy.lewaimai.consumer.function.login.LoginActivity;
import com.xunjoy.lewaimai.consumer.function.person.InviteFriendActivity;
import com.xunjoy.lewaimai.consumer.function.person.MyCollectionActivity;
import com.xunjoy.lewaimai.consumer.function.person.activity.MyCouponActivity;
import com.xunjoy.lewaimai.consumer.function.person.activity.SignActivity;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.GoodsDetailActivity2;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.ShoppingCartListActivity;
import com.xunjoy.lewaimai.consumer.function.selectgoods.adapter.WNatureGoodsAdapter;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IWDeleteNatureGoods;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IWShopCart;
import com.xunjoy.lewaimai.consumer.function.top.adapter.WActivityBetterSelectAdapter;
import com.xunjoy.lewaimai.consumer.function.top.adapter.WActivityNaviAdapter;
import com.xunjoy.lewaimai.consumer.function.top.adapter.WGoodsBetterSelectAdapter;
import com.xunjoy.lewaimai.consumer.function.top.adapter.WGoodsGroupScrollAdapter;
import com.xunjoy.lewaimai.consumer.function.top.adapter.WShopAdapter;
import com.xunjoy.lewaimai.consumer.function.top.adapter.WShopListAdapter;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.DredgeAreaActivity;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.FormActivity;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.JoinActivity;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.ShopSearchActivity;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.TopLocationActivity;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.WMapActivity;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.WShopListActivity;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.WeiActivity;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.WeiGroupActivity;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.WeiWebViewActivity;
import com.xunjoy.lewaimai.consumer.function.top.internal.IOrderAgainView;
import com.xunjoy.lewaimai.consumer.function.top.internal.IWTopFragmentView;
import com.xunjoy.lewaimai.consumer.function.top.presenter.LocationPresenter;
import com.xunjoy.lewaimai.consumer.function.top.presenter.OrderAgainPresenter;
import com.xunjoy.lewaimai.consumer.function.top.presenter.WTopFragmentPresenter;
import com.xunjoy.lewaimai.consumer.function.top.request.TopRequest;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.service.LocationService;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.NetWorkUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.CenterPopView;
import com.xunjoy.lewaimai.consumer.widget.CustomNoScrollListView.CustomNoScrollGridView;
import com.xunjoy.lewaimai.consumer.widget.CustomNoScrollListView.CustomNoScrollListView;
import com.xunjoy.lewaimai.consumer.widget.CustomNoScrollListView.CustomThreeColumnsView;
import com.xunjoy.lewaimai.consumer.widget.CustomNoScrollListView.GoodsBigPicAdapter;
import com.xunjoy.lewaimai.consumer.widget.CustomNoScrollListView.GoodsListAdapter;
import com.xunjoy.lewaimai.consumer.widget.CustomNoScrollListView.GoodsSmallPicAdapter;
import com.xunjoy.lewaimai.consumer.widget.CustomNoScrollListView.GoodsThreeColumnsAdapter;
import com.xunjoy.lewaimai.consumer.widget.CustomRelativeLayout;
import com.xunjoy.lewaimai.consumer.widget.DirectionListView;
import com.xunjoy.lewaimai.consumer.widget.HorizontalMarqueeView;
import com.xunjoy.lewaimai.consumer.widget.LoadingAnimatorView;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog2;
import com.xunjoy.lewaimai.consumer.widget.LocationAddressView;
import com.xunjoy.lewaimai.consumer.widget.PictureGuideView;
import com.xunjoy.lewaimai.consumer.widget.ShopRadioView;
import com.xunjoy.lewaimai.consumer.widget.ShopScreenView;
import com.xunjoy.lewaimai.consumer.widget.WFooterView;
import com.xunjoy.lewaimai.consumer.widget.advertisment.AdPicturePlayView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.ActivityBetterSelect;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.ActivityNavigationSelect;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.BuyAgainView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.GoodsBetterSelect;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.GoodsGroupView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.HShopSelectView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.LikeShopView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.PhoneView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.PictureNavigationView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.RecommendShopView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.SearchView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.ShopSelectView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.ShopTitleView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WAdvertismentPorterView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WPictureBigView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WTabGridView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WTextGuideView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WTitleView;
import com.xunjoy.lewaimai.consumer.widget.popupwindow.AdvertisementPopupWindow;
import com.xunjoy.lewaimai.consumer.widget.popupwindow.GetCouponPopupWindow;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static android.Manifest.permission.CALL_PHONE;
import static com.xunjoy.lewaimai.consumer.widget.defineTopView.PictureNavigationView.*;

/**
 * Created by Administrator on 2017/8/31 0031.
 * 微页面
 */

public class WTopFragment extends BaseFragment implements IWTopFragmentView, AMapLocationListener, View.OnClickListener, IWShopCart,
        IGoodsToDetailPage, PoiSearch.OnPoiSearchListener,IWDeleteNatureGoods,GeocodeSearch.OnGeocodeSearchListener,IOrderAgainView {
    public static String TAG = "WRefreshFragment";
    public static final int CART_CODE = 1;
    public static final int WEI_CODE = 3561;
    public static int CODE_LOGIN_GOODS = 11002;
    public static String WET_ID = "";
    Unbinder unbinder;
    LinearLayout llMain;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.btn_load_again)
    Button btnLoadAgain;
    @BindView(R.id.ll_load_fail)
    LinearLayout llLoadFail;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.tv_cart_count)
    TextView tvCartCount;
    @BindView(R.id.rl_cart)
    RelativeLayout rlCart;
    @BindView(R.id.address_view)
    LocationAddressView addressView;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.shop_screen)
    ShopScreenView shopScreen;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.ll_location_fail)
    LinearLayout llLocationFail;
    @BindView(R.id.tv_search_2)
    TextView tvSearch2;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.ll_location_search)
    LinearLayout llLocationSearch;
    @BindView(R.id.fl_location)
    FrameLayout flLocation;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.ll_app_guoqi)
    LinearLayout llAppGuoqi;
    @BindView(R.id.tv_join_application)
    TextView tvJoin;
    @BindView(R.id.rl_goods_group)
    RecyclerView rlGoodsGroup;
    private String token;
    private int page = 1;
    private int cartGoodsNum;//购物车中商品的总数量
    private String lat = "";
    private String lng = "";
    private String pinpai_logo = "";
    private String search_area_id = "";
    private String wId, divPageId;
    private String locationAddress = "定位中...";
    private WTopFragmentPresenter presenter;
    private LocationPresenter locationPresenter;
    private OrderAgainPresenter orderAgainPresenter;
    private WShopCart2 shopCart;
    private DecimalFormat df;
    private CenterPopView centerPopView;
    private WGoodsBetterSelectAdapter goodsBetterAdapter;
    private ArrayList<TopBean.ShopList> shoplist = new ArrayList<>();
    private WShopListAdapter shopAdapter;
    private boolean isLoadingMore, isLoactionFail = true;
    private boolean isBottom,isSelectType;
    private boolean isShowShoplist;
    private String currentCity;
    private View headerView;
    private WFooterView wFooterView;
    private GoodsBigPicAdapter goodsBigPicAdapter;
    private GoodsSmallPicAdapter goodsSmallPicAdapter;
    private GoodsThreeColumnsAdapter goodsThreeColumnsAdapter;
    private GoodsListAdapter goodsListAdapter;
    private LoadingDialog2 loadingDialog;
    private ArrayList<String> listType;//商品展示控件的种类
    private String admin_id = Const.ADMIN_ID;
    private GetCouponPopupWindow getCouponPopupWindow;
    private AdvertisementPopupWindow advertisementPopupWindow;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    llLoadFail.setVisibility(View.VISIBLE);
                    flLocation.setVisibility(View.GONE);
                    loadView.dismissView();
                    tvFail.setText(R.string.not_network);
                    break;
                case 111:
                    loadView.dismissView();
                    if (smartRefreshLayout.getState() == RefreshState.Refreshing){
                        smartRefreshLayout.finishRefresh(500);
                    }
                    break;
                case 4:
                    doLocation();
                    break;
                default:
                    break;
            }
        }
    };
    //商品分组监听结合
    private ArrayList<GoodsGroupView.ScrollListener> listenerArrayList;
    //记录当前第 currentShowPistion 个 商品分组
    private int currentShowPistion = -1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WTopFragmentPresenter(this);
        shopCart = new WShopCart2();
        listType = new ArrayList<>();
        df = new DecimalFormat("#0.00");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wei, null);
        unbinder = ButterKnife.bind(this, view);
        btnLoadAgain.setOnClickListener(this);
        rlCart.setOnClickListener(this);
        llLoadFail.setOnClickListener(this);
        flLocation.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvSearch2.setOnClickListener(this);
        tv_area.setOnClickListener(this);
        tvJoin.setOnClickListener(this);
        tvOpen.setOnClickListener(this);
        llLocationFail.setOnClickListener(this);

        headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_wei_top_header, null);
        llMain = (LinearLayout) headerView.findViewById(R.id.ll_header);
        listview.addHeaderView(headerView);
        shopAdapter = new WShopListAdapter(getContext(), shoplist);
        shopAdapter.setListView(listview);
        listview.setAdapter(shopAdapter);
        shopAdapter.setShopTypeSelectListener(new WShopListAdapter.ShopTypeSelectListener() {
            @Override
            public void onSelected(int i) {
               shopScreen.setVisibility(VISIBLE);
               shopScreen.setSelect(i);
                if (i == 0 || i == 3){
                    listViewScroll();
                }
            }

            @Override
            public void onFastSelected(int i, Set<String> value) {
                shopScreen.setVisibility(VISIBLE);
                LogUtil.log("ShopScreenView","11111 == "+value);
                shopScreen.addValue(value,i);
                shopAdapter.setSelectCount2(shopScreen.getSelectCount());
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                isSelectType = true;
                filterShop();
            }

        });
        shopScreen.setShowSingle(true);
        shopScreen.setShopScreenListener(new ShopScreenView.ShopScreenListener() {
            @Override
            public void onSort(String value,String valueName) {
                shopAdapter.setSortTitle(valueName);
                shopAdapter.setSelect(0);
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                isSelectType = true;
                filterShop();
            }

            @Override
            public void onSale(String value) {
                shopAdapter.setSelect(1);
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                isSelectType = true;
                filterShop();
            }

            @Override
            public void onDistance(String value) {
                shopAdapter.setSelect(2);
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                isSelectType = true;
                filterShop();
            }

            @Override
            public void onScreen(String value1,String value2,String value3) {
                shopAdapter.setSelect(3,shopScreen.getSelectCount());
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                isSelectType = true;
//                loadShop();
                filterShop();
            }

            @Override
            public void onSelect(int i) {

            }

            @Override
            public void onSingleSelect(int i,  Set<String> selectValue) {
                shopAdapter.setSingleSelect(selectValue);
                shopAdapter.setSelectCount(shopScreen.getSelectCount());
            }

            @Override
            public void onClear() {
                shopAdapter.clearValue();
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                isSelectType = true;
                loadShop();
            }
        });
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        if (isBottom && !isLoadingMore && smartRefreshLayout.getState() != RefreshState.Refreshing ) {
                            loadMoreShop();
                        }
                        Glide.with(getActivity()).resumeRequests();
                        break;
                    case SCROLL_STATE_FLING:
                    case SCROLL_STATE_TOUCH_SCROLL:
                        Glide.with(getActivity()).pauseRequests();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCurrentfirstVisibleItem = firstVisibleItem;
                View firstVisibleItemView = listview.getChildAt(0);
                if (firstVisibleItemView != null ) {
                    ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                    if (itemRecord == null) {
                        itemRecord = new ItemRecod();
                    }
                    //关于这个height和top到底是多少,怎么变化,我们可以通过日志来看,一目了然
                    itemRecord.height = firstVisibleItemView.getHeight();//获取当前最顶部Item的高度
                    itemRecord.top = firstVisibleItemView.getTop();//获取对应item距离顶部的距离
                    recordSp.append(firstVisibleItem, itemRecord);//添加键值对设置值
                    int ScrollY = getScrollY();//这就是滑动的距离,我们可以根据这个距离做很多事
                    LogUtil.log("radioViewMain", "getTop === " + ScrollY);
                    addressView.moveView(-ScrollY);
                }
                if (firstVisibleItem == 0) {
                    View sVisibleItemView = listview.getChildAt(1);
                    if (sVisibleItemView != null){
                        LogUtil.log("radioViewMain", "sVisibleItemView getTop === " + sVisibleItemView.getTop());
                         if (sVisibleItemView.getTop() <= addressView.getBottom() && isShowShoplist){
                             shopScreen.setVisibility(VISIBLE);
                             rlGoodsGroup.setVisibility(View.GONE);
                             LogUtil.log("radioViewMain", " top ");
                         }else {
                             shopScreen.setVisibility(GONE);
                             LogUtil.log("radioViewMain", "no top ");
                         }
                    }
                    if (listenerArrayList != null && listenerArrayList.size() > 0 && shopScreen.getVisibility()!= View.VISIBLE){
                        int scroll = headerView.getTop() - addressView.getBottom();
                        LogUtil.log("GoodsGroupView", "  scroll === " + scroll );
                        for (int i = 0;i< listenerArrayList.size();i++){
                            listenerArrayList.get(i).onScrolled(scroll,i);
                        }
                    }
                }
                if (firstVisibleItem >= 1 && shopScreen.getVisibility() != View.VISIBLE  && isShowShoplist){
                    shopScreen.setVisibility(View.VISIBLE);
                    rlGoodsGroup.setVisibility(View.GONE);
                }
                isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
                LogUtil.log("radioViewMain", "firstVisibleItem === "+firstVisibleItem);

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 2 && position < shoplist.size()) {
                    Intent intent = new Intent(getActivity(), SelectGoodsActivity3.class);
                    intent.putExtra("from_page", "Wlink");
                    intent.putExtra("shop_id", shoplist.get(position - 2).id);
                    startActivity(intent);
                }

            }
        });
        initPtrFrame();
        wFooterView = new WFooterView(getContext());
        wFooterView.setListener(this);
        listview.addFooterView(wFooterView);
        locationPresenter = new LocationPresenter(getContext(), this);
        loadView.showView("");
        loadView.setOnClickListener(this);
        wId = WET_ID;
        //开始定位
//        startService();
        doLocation();
        return view;
    }
    private int mCurrentfirstVisibleItem;
    private SparseArray recordSp = new SparseArray(10);//设置容器大小，默认是10
    private int getScrollY() {
        int height = 0;
        for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
            ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
            if (itemRecod != null){
                height += itemRecod.height;
            }
        }
        ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
        if (null == itemRecod) {
            itemRecod = new ItemRecod();
        }
        return height - itemRecod.top;
    }

    private void doLocation() {
        LogUtil.log("loadData", "doLocation ------");
        lat = "";
        lng = "";
        if (NetWorkUtil.isNetworkConnected()) {
            //判断是否为android6.0系统版本，如果是，需要动态添加权限
            if (Build.VERSION.SDK_INT >= 23) {
                LogUtil.log("isGetLocationPermission", "than 23 ");
                LogUtil.log("loadData", "SDK_INT ------ than 23");
                //判断权限
                if (isGetLocationPermission()) {
                    LogUtil.log("loadData", "isGetLocationPermission ------ yes");
                   /* if (lService != null) {
                        lService.doLocation();
                    }*/
                    locationPresenter.doLocation();
                } else {
                    LogUtil.log("loadData", "isGetLocationPermission ------ no");
                }
            } else {
                LogUtil.log("loadData", "SDK_INT ------ low 23");
                /*if (lService != null) {
                    lService.doLocation();
                }*/
                locationPresenter.doLocation();
            }
        } else {
            handler.sendEmptyMessageDelayed(2, 2000);
        }
    }

    //下拉刷新
    private void initPtrFrame() {
        //设置全局的Header构建器

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setDisableContentWhenRefresh(true);
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setTextSizeTitle(14);
                layout.setPrimaryColorsId(R.color.bg_eb, R.color.text_color_66);//全局设置主题颜色
                classicsHeader.setDrawableArrowSize(15);
//                FalsifyHeader falsifyHeader = new FalsifyHeader(context);
                return classicsHeader;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData();
            }
        });
        smartRefreshLayout.setDisableContentWhenRefresh(true);
    }

    @Override
    public void load() {
    }

    public void loadData() {
        llLoadFail.setVisibility(View.GONE);
        flLocation.setVisibility(View.GONE);
        shopScreen.setDefaultValue2();
        shopAdapter.clearValue();
        if (NetWorkUtil.isNetworkConnected()) {
            presenter.loadData(admin_id, WET_ID, lat, lng);
        } else {
            handler.sendEmptyMessageDelayed(2, 3000);
        }
    }

    @Override
    public void onDestroyView() {
        loadView.tryRecycleAnimationDrawable();
        unbinder.unbind();
        if (locationPresenter != null){
            locationPresenter.doStopLocation();
        }
        super.onDestroyView();

    }

    @Override
    public void dialogDismiss() {
        refreshComplete();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(getContext(), s);
    }

    @Override
    public void loadDataToView(final WTopBean bean) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        llLoadFail.setVisibility(View.GONE);
        flLocation.setVisibility(View.GONE);
        refreshComplete(bean);
    }
    @Override
    public void loadFail() {
        llLoadFail.setVisibility(View.VISIBLE);
        flLocation.setVisibility(View.GONE);
        tvFail.setText("加载数据失败");
        loadView.dismissView();
    }


    @Override
    public void loadShop() {
        LogUtil.log("loadData", "shop   load");
        if (isShowShoplist) {
//            wFooterView.setFooterViewStatus(WFooterView.STATUS_LOAD_MORE);
            page = 1;
            token = SharedPreferencesUtil.getToken();
            String type_id = shopScreen.getTypeValue();
            String order_type = shopScreen.getSortValue();
            String condition = shopScreen.getFilterValue();
            String services = shopScreen.getServiceValue();
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.weiShopRequestNoLogin(admin_id, type_id, order_type, condition,services ,lat, lng, page + "", "2", divPageId);
            } else {
                map = TopRequest.weiShopRequestLogin(admin_id, token, type_id, order_type, condition, services ,lat, lng, page + "", "2", divPageId);
            }
            presenter.loadShopData(map);
        }
    }
    private void filterShop(){
        if (isShowShoplist) {
//            wFooterView.setFooterViewStatus(WFooterView.STATUS_LOAD_MORE);
            page = 1;
            token = SharedPreferencesUtil.getToken();
            String type_id = shopScreen.getTypeValue();
            String order_type = shopScreen.getSortValue();
            String condition = shopScreen.getFilterValue();
            String services = shopScreen.getServiceValue();
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.weiShopRequestNoLogin(admin_id, type_id, order_type, condition,services ,lat, lng, page + "", "2", divPageId);
            } else {
                map = TopRequest.weiShopRequestLogin(admin_id, token, type_id, order_type, condition, services ,lat, lng, page + "", "2", divPageId);
            }
            presenter.loadFilterShopData(map);
        }
    }
    @Override
    public void loadMoreShop() {
        if (wFooterView.getStatus() != WFooterView.STATUS_LOAD_MORE){
            return;
        }
        if ( isShowShoplist && shoplist.size() > 5) {
            wFooterView.setFooterViewStatus(WFooterView.STATUS_LOAD_MORE);
            isLoadingMore = true;
            token = SharedPreferencesUtil.getToken();
            String type_id = shopScreen.getTypeValue();
            String order_type = shopScreen.getSortValue();
            String condition = shopScreen.getFilterValue();
            String services = shopScreen.getServiceValue();
            HashMap<String, String> map = null;
            if (StringUtils.isEmpty(token)) {
                map = TopRequest.weiShopRequestNoLogin(admin_id, type_id, order_type, condition, services ,lat, lng, (page + 1) + "", "2", divPageId);
            } else {
                map = TopRequest.weiShopRequestLogin(admin_id, token, type_id, order_type, condition, services ,lat, lng, (page + 1) + "", "2", divPageId);
            }
            presenter.loadMoreShopData(map);
        }else {
            wFooterView.setFooterViewStatus(WFooterView.STATUS_LOGO);
        }
    }

    @Override
    public void showDataToView(TopBean bean) {
        refreshComplete();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadView.dismissView();
        if (bean != null) {
            if (shopScreen != null) {
                shopScreen.setShopClassifyData(bean.data.shoptype);
            }
            LogUtil.log("loadData", "showDataToView shoplist  " + bean.data.shoplist.size());
            shoplist.clear();
            if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
                shopAdapter.setIs_show_expected_delivery(bean.data.is_show_expected_delivery);
                shoplist.addAll(bean.data.shoplist);
            }
            if (shoplist.size() <= 3){
                wFooterView.setFooterViewStatus(WFooterView.STATUS_LOAD_EMPTY);
            }else {
                wFooterView.setFooterViewStatus(WFooterView.STATUS_LOAD_MORE);
            }
            shopAdapter.notifyDataSetChanged();
        }
        if (isSelectType){
            isSelectType  = false;
            listViewScroll();
        }
    }
    @Override
    public void showFilterDataToView(TopBean bean) {
        refreshComplete();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadView.dismissView();
        shoplist.clear();
        if (bean.data.shoplist != null && bean.data.shoplist.size() > 0) {
            shopAdapter.setIs_show_expected_delivery(bean.data.is_show_expected_delivery);
            shoplist.addAll(bean.data.shoplist);
        }
        if (shoplist.size() == 0){
            wFooterView.setFooterViewStatus(WFooterView.STATUS_FILTER_EMPTY);
        }else {
            if (shoplist.size() <= 5){
                wFooterView.setFooterViewStatus(WFooterView.STATUS_LOAD_EMPTY);
            }else {
                wFooterView.setFooterViewStatus(WFooterView.STATUS_LOAD_MORE);
            }
        }
        shopAdapter.notifyDataSetChanged();
        if (isSelectType){
            isSelectType  = false;
            listViewScroll();
        }
    }
    @Override
    public void showMoreShop(TopBean bean) {
        isLoadingMore = false;
        if (bean != null) {
            if (bean.data.shoplist != null && bean.data.shoplist.size() > 0) {
                page++;
                if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
                    shoplist.addAll(bean.data.shoplist);
                }
                shopAdapter.notifyDataSetChanged();
            } else {
                ToastUtil.showTosat(getContext(),"所有店铺都加载完啦，可以点击搜索更多店铺");
                wFooterView.setFooterViewStatus(WFooterView.STATUS_LOGO);
            }
        } else {
            ToastUtil.showTosat(getContext(),"所有店铺都加载完啦，可以点击搜索更多店铺");
            wFooterView.setFooterViewStatus(WFooterView.STATUS_LOGO);
        }
    }

    @Override
    public void loadMoreShopFail() {
        isLoadingMore = false;
    }

    @Override
    public void loadShopFail() {
        refreshComplete();
    }

    @Override
    public void showResponeDialog() {
        loadView.dismissView();
        llLoadFail.setVisibility(View.GONE);
        flLocation.setVisibility(View.VISIBLE);
        llLocationFail.setVisibility(View.GONE);
        llLocationSearch.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reset:
                shopScreen.setDefaultValue2();
                shopAdapter.setSortTitle("综合排序");
                shopAdapter.clearValue();
                listViewScroll();
                shopScreen.setVisibility(VISIBLE);
                loadShop();
                break;
            case R.id.btn_load_again:
                LogUtil.log("loadData", "btn_load_again");
                if (!NetWorkUtil.isNetworkConnected()) {
                    String s = getString(R.string.not_network);
                    ToastUtil.showTosat(getContext(), s);
                    return;
                }
                llLoadFail.setVisibility(View.GONE);
                flLocation.setVisibility(View.GONE);
                loadView.showView();
                if (isLoactionFail){
                    doLocation();
                }else {
                    loadData();
                }
                break;
            case R.id.ll_location_fail:
                addressView.setAddressValue("定位中...");
                loadView.showView("");
                flLocation.setVisibility(View.GONE);
                doLocation();
                break;
            case R.id.tv_open:
                isOpenLocation = true;
                isSearchAddress = false;
                getAppDetailSettingIntent(getActivity());
                break;
                //查看开通区域
            case R.id.tv_area:
                startActivity(new Intent(getContext(), DredgeAreaActivity.class));
                break;
                //加盟
            case R.id.tv_join_application:
                startActivity(new Intent(getContext(), JoinActivity.class));
                break;
            case R.id.tv_search:
            case R.id.tv_search_2:
                if (smartRefreshLayout.getState() == RefreshState.Refreshing){
                    return;
                }
                Intent sintent = new Intent(getActivity(), TopLocationActivity.class);
                sintent.putExtra("currentCity", currentCity);
                sintent.putExtra("address", locationAddress);
                startActivityForResult(sintent, RESULT_LOCATION_SEARCH);
                break;
            case R.id.ll_address:
                if (smartRefreshLayout.getState() == RefreshState.Refreshing){
                    return;
                }
                Intent intent = new Intent(getActivity(), TopLocationActivity.class);
                intent.putExtra("currentCity", currentCity);
                intent.putExtra("address", locationAddress);
                startActivityForResult(intent, RESULT_LOCATION_CODE);
                break;
            case R.id.ll_search_address:
            case R.id.ll_search_shop:
                if (smartRefreshLayout.getState() == RefreshState.Refreshing){
                    return;
                }
                Intent sIntent = new Intent(getActivity(), ShopSearchActivity.class);
                sIntent.putExtra("search_area_id", search_area_id);
                startActivityForResult(sIntent, CART_CODE);
                break;
            case R.id.rl_cart:
                if (smartRefreshLayout.getState() == RefreshState.Refreshing){
                    return;
                }
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                //点击购物车悬浮按钮，跳转到购物车列表
                startActivity(new Intent(getActivity(), ShoppingCartListActivity.class));
                break;
            case R.id.ll_close:
            case R.id.vv_touch:
              /*  llType.setVisibility(View.GONE);
                radioViewMain.closeView();*/
                break;
            //登录领取优惠券
            case R.id.btn_login:
                getCouponPopupWindow.dismiss();
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, WEI_GET_COUPON_CODE);
                }
                break;
            case R.id.iv_dimiss:
                if (getCouponPopupWindow != null && getCouponPopupWindow.isShowing()){
                    getCouponPopupWindow.dismiss();
                    LogUtil.log("tankuang_guanggao","show -- ");
                    if (advertisementPopupWindow != null && !advertisementPopupWindow.isShowing()){
                        advertisementPopupWindow.show();
                    }
                }
                break;
            //再来一单
            case R.id.tv_buy_again:
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                dealOrderAgain();
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        refreshComplete();
        isOpenLocation = false;
        LogUtil.log("loadData", "onLocationChanged ");
        //定位回调
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                LogUtil.log("loadData", "onLocationChanged    success");
                isLoactionFail = false;
                lat = aMapLocation.getLatitude() + "";
                lng = aMapLocation.getLongitude() + "";
                SharedPreferencesUtil.saveLatitude(lat);
                SharedPreferencesUtil.saveLongitude(lng);
                currentCity = aMapLocation.getCity();
                locationAddress = aMapLocation.getAoiName();
                LogUtil.log("loadData", "address  == "+aMapLocation.getAddress() + " lat = "+lat + "  lng = "+lng);
//                locationPresenter.doAccurateSearch(getContext(), aMapLocation.getLatitude(), aMapLocation.getLongitude(), currentCity, WTopFragment.this);
                locationPresenter.getAddressByLatlng(getContext(),aMapLocation.getLatitude(), aMapLocation.getLongitude(),this);

            } else {
                isLoactionFail = true;
            }
        } else {
            isLoactionFail = true;
        }
        if (isLoactionFail) {
            lat = "";
            lng = "";
            LogUtil.log("loadData", "onLocationChanged    fail");
            llLoadFail.setVisibility(View.GONE);
            flLocation.setVisibility(View.VISIBLE);
            llLocationFail.setVisibility(View.VISIBLE);
            llLocationSearch.setVisibility(View.GONE);
            loadView.dismissView();
            locationAddress = "定位失败";
            addressView.setAddressValue(locationAddress);
        }
    }

    public void initData(WTopBean bean) {
        LogUtil.log("loadData", "initData ------");
        if (bean.data != null && bean.data.divpage != null) {
            /*pinpai_logo = bean.data.pinpai_logo;
            SharedPreferencesUtil.saveCustomerLogo(pinpai_logo);
            wFooterView.setLogoUrl(pinpai_logo);*/
            divPageId = bean.data.divpage.id;
            search_area_id = bean.data.divpage.area_id;
            SharedPreferencesUtil.saveWPageid(bean.data.divpage.id);
            SharedPreferencesUtil.saveAreaId(search_area_id);
            //微页面分区数据
            LogUtil.log("loadData", "divpage ------");
            listType.clear();
            if (bean.data.divpage.data != null && bean.data.divpage.data.size() > 0) {
                llMain.removeAllViews();
                if (listenerArrayList != null){
                    listenerArrayList.clear();
                }
                if (buyAgainViews != null){
                    buyAgainViews.clear();
                }
                rlGoodsGroup.setAdapter(null);
                rlGoodsGroup.setVisibility(View.GONE);
                isShowShoplist = false;
                for (WTopBean.PageData data : bean.data.divpage.data) {
                    switch (data.type) {
                        //定位
                        case "address_position":
                            addressView.setAddressValue(locationAddress);
                            addressView.setVisibility(View.VISIBLE);
                            addressView.setOnClickListener(this);
                            View tView = new View(getContext());
                            tView.setLayoutParams(new PtrFrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UIUtils.dip2px(85)));
                            llMain.addView(tView);
                            break;
                        //富文本
                        case "rich_text":
                            final WTopBean.RichTextData richTextData = data.rich_textData;
                            if (richTextData != null) {
                                WebView webView = new WebView(getContext());
                                webView.getSettings().setUseWideViewPort(true);
                                webView.getSettings().setLoadWithOverviewMode(true);
                                webView.getSettings().setDefaultTextEncodingName("UTF-8");
                                webView.getSettings().setSupportZoom(true);
                                webView.getSettings().setDefaultFontSize(100);
                                webView.loadData(getWebContent(richTextData.html), "text/html; charset=UTF-8", null);
                                llMain.addView(webView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }

                            break;
                        //搜索
                        case "food_search":
                            SearchView searchView = new SearchView(getContext());
                            searchView.setListener(this);
                            llMain.addView(searchView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            break;
                        //轮播图
                        case "lunbo_image":
                            if (data.lunbo_imageData != null && data.lunbo_imageData.size() > 0) {
                                final ArrayList<WTopBean.LunboImageData> lunboImageDatas = data.lunbo_imageData;
                                AdPicturePlayView picturePlayView = new AdPicturePlayView(getContext(), 1);
                                boolean radio = "0".equals(data.radioVal);
                                picturePlayView.setRadioVal(radio);
                                if (!StringUtils.isEmpty(data.space) ){
                                    picturePlayView.setSpace(Integer.parseInt(data.space));
                                }
                                if (!StringUtils.isEmpty(data.pageSpace) ){
                                    picturePlayView.setPageSpace(Integer.parseInt(data.pageSpace));
                                }
                                picturePlayView.setmListView(listview);
                                picturePlayView.setmPtrLayout(smartRefreshLayout);
                                picturePlayView.setLunboImageData(lunboImageDatas, new AdPicturePlayView.PlayViewOnClickListener() {
                                    @Override
                                    public void onClick(WTopBean.LunboImageData imageData) {
                                        goToActivity(imageData);
                                    }
                                });
                                llMain.addView(picturePlayView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                            }
                            break;
                        //图标导航
                        case "tubiao_daohang":
                            if (data.tubiao_daohangData != null && data.tubiao_daohangData.size() > 0) {
                                final WTabGridView gridView = new WTabGridView(getContext(), data.tubiao_daohangData.size());

                                gridView.setTabData(data.tubiao_daohangData);
                                gridView.setTabClickListener(new WTabGridView.TabClickListener() {
                                    @Override
                                    public void onTabClick(WTopBean.BaseData baseData) {
                                        //跳转页面
                                        goToActivity(baseData);
                                    }
                                });
                                gridView.setmListView(listview);
                                gridView.setmPtrLayout(smartRefreshLayout);
                                llMain.addView(gridView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //标题
                        case "biaoti":
                            if (data.biaotiData != null) {
                                WTitleView titleView = new WTitleView(getContext());
                                final WTopBean.TitleData titleData = data.biaotiData;
                                titleView.setTitleText(data.biaotiData, new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (titleData.list != null && titleData.list.size() > 0){
                                            //跳转页面
                                            goToActivity(titleData.list.get(0));
                                        }
                                    }
                                });
                                llMain.addView(titleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //拨打电话
                        case "phone":
                            if(null!=data.phoneData){
                                PhoneView phoneView=new PhoneView(getContext());
                                phoneView.setData(data.phoneData);
                                llMain.addView(phoneView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //文本导航
                        case "wenben_daohang":
                            if (data.wenben_daohangData != null) {
                                for (final WTopBean.TextGuideData guideData : data.wenben_daohangData) {
                                    WTextGuideView guideView = new WTextGuideView(getContext());
                                    guideView.setTextValue(guideData.title);
                                    guideView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            goToActivity(guideData);

                                        }
                                    });
                                    llMain.addView(guideView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                }
                            }
                            break;
                        //进入店铺
                        case "jinrudianpu":
                            final WTopBean.ShopComeIn comeIn = data.jinrudianpuData;
                            if (comeIn != null) {
                                ShopTitleView titleView = new ShopTitleView(getContext());
                                titleView.setShopName(comeIn.shopname);
                                titleView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getContext(), SelectGoodsActivity3.class);
                                        intent.putExtra("from_page", "WGoShop");
                                        intent.putExtra("shop_info", comeIn);
                                        getContext().startActivity(intent);
                                    }
                                });
                                llMain.addView(titleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //推荐店铺
                        case "dianpu_liebiao":
                            WTopBean.ShopListData listData = data.dianpu_liebiaoData;
                            if (listData.list != null && listData.list.size() > 0) {
                                RecommendShopView shopView = new RecommendShopView(getContext());
                                shopView.setShopName(listData.title);
                                shopView.setShopDataNoLocation(listData.list, false,bean.data.is_show_expected_delivery,listData.is_show_sale);
                                llMain.addView(shopView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //店铺优选
                        case "dianpu_youxuan":
                            WTopBean.ShopSelectData shopSelect = data.dianpu_youxuanData;
                            if (shopSelect.list != null && shopSelect.list.size() > 0) {
                                ShopSelectView selectView = new ShopSelectView(getContext());
                                selectView.setShopSelectData(shopSelect.list);
                                selectView.setmListView(listview);
                                selectView.setmPtrLayout(smartRefreshLayout);
                                llMain.addView(selectView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //横排店铺
                        case "hengpai_dianpu":
                            WTopBean.HShopList hShopList = data.hengpai_dianpuData;
                            if (hShopList.list != null && hShopList.list.size() > 0) {
                                HShopSelectView hShopSelectView = new HShopSelectView(getContext(), hShopList);
                                llMain.addView(hShopSelectView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //竖排店铺
                        case "push_shop":
                            LogUtil.log("initData", "push_shop ------");
                            isShowShoplist = true;

                            break;
                        //图片导航
                        case "tupian_daohang":
                            final WTopBean.PictureGuideData pictureGuideData = data.tupian_daohangData;
                            if (pictureGuideData != null ) {
                                PictureGuideView guideView = new PictureGuideView(getContext(),pictureGuideData.scrollWay);
                                boolean radio = "0".equals(data.radioVal);
                                guideView.setRadioVal(radio);
                                if (!StringUtils.isEmpty(pictureGuideData.imageSpace)){
                                    guideView.setSpace(Integer.parseInt(pictureGuideData.imageSpace));
                                }
                                if (!StringUtils.isEmpty(pictureGuideData.pageSpace) ){
                                    guideView.setPageSpace(Integer.parseInt(pictureGuideData.pageSpace));
                                }
                                guideView.setData(pictureGuideData);
                                guideView.setGuideListener(new PictureGuideView.PictureGuideListener() {
                                    @Override
                                    public void onPictureClick(int position) {
                                        goToActivity(pictureGuideData.list.get(position));
                                    }
                                });
                                llMain.addView(guideView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //图片广告
                        case "tupian_guanggao":
                            final WTopBean.PictureAdvertismentData advertismentData = data.tupian_guanggaoData;

                            if (advertismentData != null) {
                                LogUtil.log("tupain_show_type","show_type == "+advertismentData.show_type);
                                int style  = 0;
                                if ("0".equals(advertismentData.show_type)){
                                    style = 1;
                                }else if ("1".equals(advertismentData.show_type)){
                                    style = 2;
                                }else if ("2".equals(advertismentData.show_type)){
                                    style = 5;
                                }else if ("3".equals(advertismentData.show_type)){
                                    style = 3;
                                }else if ("4".equals(advertismentData.show_type)){
                                    style = 4;
                                }
                                PictureNavigationView navigationView = new PictureNavigationView(getContext(),style);
                                boolean radio = "0".equals(advertismentData.radioVal);
                                navigationView.setRadioVal(radio);
                                if (!StringUtils.isEmpty(advertismentData.space)){
                                    navigationView.setSpace(Integer.parseInt(advertismentData.space));
                                }
                                if (!StringUtils.isEmpty(advertismentData.pageSpace) ){
                                    navigationView.setPageSpace(Integer.parseInt(advertismentData.pageSpace));
                                }
                                navigationView.setData(advertismentData);
                                navigationView.setViewOnClickListener(new ViewOnClickListener() {
                                    @Override
                                    public void OnClick(WTopBean.PictureAdvertisment advertisment) {
                                        goToActivity(advertisment);
                                    }
                                });
                                llMain.addView(navigationView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //广告海报(自由拼图)
                        case "guanggao_haibao":
                            final WTopBean.AdvertismentPosterData posterData = data.guanggao_haibaoData;
                            if (posterData != null) {
                                LogUtil.log("guanggao_show_type","show_type == "+posterData.show_type);
                                int style = 0;
                                if ("1".equals(posterData.show_type)){
                                    //1左4右
                                    style = WAdvertismentPorterView.STYLE_LINE_ONE_RIGHT_FOUR;
                                }else if ("2".equals(posterData.show_type)){
                                    style = WAdvertismentPorterView.STYLE_TOP_TWO_BOTTOM_FOUR;
                                    //2上4下
                                }else if ("3".equals(posterData.show_type)){
                                    style = WAdvertismentPorterView.STYLE_ROW_ONE_COLUMN_TWO;
                                    //1行2个
                                }else if ("4".equals(posterData.show_type)){
                                    style = WAdvertismentPorterView.STYLE_ROW_ONE_COLUMN_THREE;
                                    //1行3个
                                }else if ("5".equals(posterData.show_type)){
                                    style = WAdvertismentPorterView.STYLE_ROW_ONE_COLUMN_FOUR;
                                    //1行4个
                                }else if ("6".equals(posterData.show_type)){
                                    style = WAdvertismentPorterView.STYLE_LEFT_ONE_RIGHT_TWO;
                                    //2行2个
                                }else if ("7".equals(posterData.show_type)){
                                    style = WAdvertismentPorterView.STYLE_LEFT_TWO_RIGHT_TWO;
                                    ////1左2右
                                }else if ("8".equals(posterData.show_type)){
                                    style = WAdvertismentPorterView.STYLE_LEFT_ONE_RIGHT_THREE;
                                    //1左3右
                                }else if ("9".equals(posterData.show_type)){
                                    style = WAdvertismentPorterView.STYLE_TOP_ONE_BOTTOM_TWO;
                                    //1上2下
                                }
                                int space  = StringUtils.isEmpty(posterData.space) ? 0:Integer.parseInt(posterData.space);
                                int pageSpace  = StringUtils.isEmpty(posterData.pageSpace) ? 0:Integer.parseInt(posterData.pageSpace);
                                final WAdvertismentPorterView porterView = new WAdvertismentPorterView(getContext(),style,space,pageSpace);
                                boolean radio = "0".equals(posterData.radioVal);
                                porterView.setRadioVal(radio);
                                porterView.setAdvertismentData(posterData, new WAdvertismentPorterView.AdvertismentItemClick() {
                                    @Override
                                    public void onItemClick(int position) {
                                        goToActivity(posterData.list.get(position));

                                    }
                                });
                                llMain.addView(porterView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //辅助线
                        case "fuzhuxian":
                            View fuzhuxian = View.inflate(getContext(), R.layout.view_fuzhuxian, null);
                            fuzhuxian.setBackgroundColor(Color.parseColor("#f5f5f5"));
                            llMain.addView(fuzhuxian, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            break;
                        //辅助空白
                        case "fuzhukongbai":
                            WTopBean.FuzhuKongbai dividerData = data.fuzhukongbaiData;
                            if (dividerData != null){
                                int h = FormatUtil.numInteger(dividerData.height);
                                if (h == 0){
                                    h = 20;
                                }
                                View kongbai = new View(getContext());
                                if (StringUtils.isEmpty(dividerData.colors)){
                                    kongbai.setBackgroundColor(Color.parseColor("#f5f5f5"));
                                }else {
                                    kongbai.setBackgroundColor(UIUtils.getColor(dividerData.colors));
                                }
                                kongbai.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UIUtils.dip2px(h)));

                                llMain.addView(kongbai, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UIUtils.dip2px(h)));

                            }
                            break;
                        //公告
                        case "gonggao":
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_notice, null);
                            HorizontalMarqueeView tv_notice = (HorizontalMarqueeView) view.findViewById(R.id.tv_notice);
                            List<String> stringList = new ArrayList<>();
                            stringList.add(data.gonggaoData.content);
                            tv_notice.setData(stringList);
                            tv_notice.startScroll();
                            llMain.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(36)));
                            break;
                        //商品列表
                        case "shangpin_liebiao":
                            if ("1".equals(data.show_type)) {
                                //大图展示
                                if (null != data.shangpin_liebiaoData && data.shangpin_liebiaoData.size() > 0) {
                                    ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
                                    for (GoodsListBean.GoodsInfo goodsInfo : data.shangpin_liebiaoData) {
                                        if (goodsInfo.is_nature.equals("0")) {
                                            goodsInfo.nature.clear();
                                        }
                                        goodsList.add(goodsInfo);
                                    }
                                    goodsBigPicAdapter = new GoodsBigPicAdapter(getContext(), goodsList, shopCart, "shangpin_big");
                                    goodsBigPicAdapter.setWShopCartListener(this);
                                    goodsBigPicAdapter.setIToDetailPage(this);
                                    View goodsBigPicSelect = LayoutInflater.from(getContext()).inflate(R.layout.view_goods_big_pic, null);
                                    CustomNoScrollListView lv_goods = (CustomNoScrollListView) goodsBigPicSelect.findViewById(R.id.lv_goods);
                                    lv_goods.setAdapter(goodsBigPicAdapter);
                                    goodsBigPicAdapter.setParentLayout(lv_goods);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    llMain.addView(goodsBigPicSelect, layoutParams);
                                    listType.add("shangpin_big");
                                }
                            } else if ("2".equals(data.show_type)) {
                                //小图展示
                                if (null != data.shangpin_liebiaoData && data.shangpin_liebiaoData.size() > 0) {
                                    ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
                                    for (GoodsListBean.GoodsInfo goodsInfo : data.shangpin_liebiaoData) {
                                        if (goodsInfo.is_nature.equals("0")) {
                                            goodsInfo.nature.clear();
                                        }
                                        goodsList.add(goodsInfo);
                                    }
                                    goodsSmallPicAdapter = new GoodsSmallPicAdapter(getContext(), goodsList, shopCart, "shangpin_small");
                                    goodsSmallPicAdapter.setWShopCartListener(this);
                                    goodsSmallPicAdapter.setIToDetailPage(this);
                                    View goodsSmallPicSelect = LayoutInflater.from(getContext()).inflate(R.layout.view_goods_small_pic, null);
                                    CustomNoScrollGridView rv_goods = (CustomNoScrollGridView) goodsSmallPicSelect.findViewById(R.id.rv_goods);
                                    rv_goods.setAdapter(goodsSmallPicAdapter);
                                    goodsSmallPicAdapter.setParentLayout(rv_goods);
                                    llMain.addView(goodsSmallPicSelect, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    listType.add("shangpin_small");
                                }
                            } else if ("3".equals(data.show_type)) {
                                //列表展示
                                if (null != data.shangpin_liebiaoData && data.shangpin_liebiaoData.size() > 0) {
                                    ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
                                    for (GoodsListBean.GoodsInfo goodsInfo : data.shangpin_liebiaoData) {
                                        if (goodsInfo.is_nature.equals("0")) {
                                            goodsInfo.nature.clear();
                                        }
                                        goodsList.add(goodsInfo);
                                    }
                                    goodsListAdapter = new GoodsListAdapter(getContext(), goodsList, shopCart, "shangpin_list");
                                    goodsListAdapter.setWShopCartListener(this);
                                    goodsListAdapter.setIToDetailPage(this);
                                    View goodsListSelect = LayoutInflater.from(getContext()).inflate(R.layout.view_goods_list, null);
                                    CustomNoScrollListView rv_goods = (CustomNoScrollListView) goodsListSelect.findViewById(R.id.rv_goods);
                                    rv_goods.setAdapter(goodsListAdapter);
                                    goodsListAdapter.setParentLayout(rv_goods);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    llMain.addView(goodsListSelect, layoutParams);
                                    listType.add("shangpin_list");
                                }
                            }else if ("4".equals(data.show_type)) {
                                //展示一行三个商品
                                if (null != data.shangpin_liebiaoData && data.shangpin_liebiaoData.size() > 0) {
                                    ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
                                    for (GoodsListBean.GoodsInfo goodsInfo : data.shangpin_liebiaoData) {
                                        if (goodsInfo.is_nature.equals("0")) {
                                            goodsInfo.nature.clear();
                                        }
                                        goodsList.add(goodsInfo);
                                    }
                                    goodsThreeColumnsAdapter = new GoodsThreeColumnsAdapter(getContext(), goodsList, shopCart, "shangpin_three_columns");
                                    goodsThreeColumnsAdapter.setWShopCartListener(this);
                                    goodsThreeColumnsAdapter.setIToDetailPage(this);
                                    View goodsThreeColumnsSelect = LayoutInflater.from(getContext()).inflate(R.layout.view_three_goods, null);
                                    CustomThreeColumnsView rv_goods = (CustomThreeColumnsView) goodsThreeColumnsSelect.findViewById(R.id.rv_goods);
                                    rv_goods.setAdapter(goodsThreeColumnsAdapter);
                                    goodsThreeColumnsAdapter.setParentLayout(rv_goods);
                                    llMain.addView(goodsThreeColumnsSelect, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                    listType.add("shangpin_three_columns");
                                }
                            }
                            break;
                        //活动优选
                        case "huodong_youxuan":
                            ArrayList<WTopBean.ActivityData> activityBetterList = data.huodong_youxuanData.list;
                            if (null != activityBetterList && activityBetterList.size() > 0) {
                                ActivityBetterSelect activityBetterSelect = new ActivityBetterSelect(getContext());
                                WActivityBetterSelectAdapter activityBetterAdapter = new WActivityBetterSelectAdapter(getContext(), activityBetterList);
                                activityBetterAdapter.setActivityClickListener(new WActivityBetterSelectAdapter.ActivityClickListener() {
                                    @Override
                                    public void onActivityClick(WTopBean.BaseData baseData) {
                                        goToActivity(baseData);
                                    }
                                });
                                activityBetterSelect.setActivityNaviAdapter(activityBetterAdapter);
                                llMain.addView(activityBetterSelect, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //活动导航
                        case "huodong_daohang":
                            ArrayList<WTopBean.ActivityData> activityList = data.huodong_daohangData.list;
                            if (null != activityList && activityList.size() > 0) {
                                ActivityNavigationSelect activityNavigationSelect = new ActivityNavigationSelect(getContext());
                                WActivityNaviAdapter activityNaviAdapter = new WActivityNaviAdapter(getContext(), activityList);
                                activityNaviAdapter.setActivityClickListener(new WActivityNaviAdapter.ActivityClickListener() {
                                    @Override
                                    public void onActivityClick(WTopBean.BaseData baseData) {
                                        goToActivity(baseData);
                                    }
                                });
                                activityNavigationSelect.setActivityNaviAdapter(activityNaviAdapter);
                                llMain.addView(activityNavigationSelect, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        //商品优选
                        case "shangpin_youxuan":
                            WTopBean.GoodsSelectData goodsSelectData = data.shangpin_youxuanData;
                            if (null != goodsSelectData && goodsSelectData.list.size() > 0) {
                                ArrayList<GoodsListBean.GoodsInfo> youxuanList = new ArrayList<>();
                                for (GoodsListBean.GoodsInfo goodsInfo : goodsSelectData.list) {
                                    if (goodsInfo.is_nature.equals("0")) {
                                        goodsInfo.nature.clear();
                                    }
                                    youxuanList.add(goodsInfo);
                                }
                                GoodsBetterSelect goodsBetterSelect = new GoodsBetterSelect(getContext());
                                goodsBetterAdapter = new WGoodsBetterSelectAdapter(getContext(), youxuanList, shopCart, "shangpin_youxuan");
                                goodsBetterAdapter.setWShopCartListener(this);
                                goodsBetterAdapter.setIToDetailPage(this);
                                goodsBetterSelect.setGoodsAdapter(goodsBetterAdapter);
                                llMain.addView(goodsBetterSelect, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                listType.add("shangpin_youxuan");
                            }
                            break;
                        //菜单导航
                        case "caidan_daohang":
                            break;
                        case "call_phone":
                            View pView = View.inflate(getContext(), R.layout.layout_call_phone, null);
                            ((TextView)pView.findViewById(R.id.tv_phone)).setText("");
                            pView.findViewById(R.id.tv_call).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    callPhone("");
                                }
                            });
                            break;
                        case "guess_like":
                            final ArrayList<WTopBean.GuessLikeData> guess_likeData = data.guess_likeData;
                            if (guess_likeData != null && guess_likeData.size() > 0){
                                LikeShopView shopView = new LikeShopView(getContext());
                                shopView.setData("猜你喜欢",guess_likeData, new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String shop_id = guess_likeData.get(position).shop_id;
                                        String shop_name = guess_likeData.get(position).shopname;
                                        Intent intent = new Intent(getContext(), SelectGoodsActivity3.class);
                                        intent.putExtra("from_page","cartList");
                                        intent.putExtra("shop_id",shop_id);
                                        intent.putExtra("shop_name",shop_name);
                                        startActivity(intent);
                                    }
                                });
                                llMain.addView(shopView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                            }

                            break;
                        case "shangpin_fenzu":
                            WTopBean.GoodsGroupData goodsGroupData = data.shangpin_fenzuData;
                            if (goodsGroupData != null){
                                LogUtil.log("GoodsGroupView","goodsGroupView  init");
                                final GoodsGroupView goodsGroupView = new GoodsGroupView(getContext(),goodsGroupData,shopCart,this,this);
                                goodsGroupView.setGoodsData(goodsGroupData);
                                if (goodsGroupData.group_list != null &&  goodsGroupView.getTypeAdapter().getItemCount() > 0){
                                    if (listenerArrayList == null){
                                        currentShowPistion = -1;
                                        listenerArrayList = new ArrayList<>();
                                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                        rlGoodsGroup.setLayoutManager(manager);
                                    }
                                    GoodsGroupView.ScrollListener scrollListener = new GoodsGroupView.ScrollListener() {
                                        @Override
                                        public void onScrolled(int y,int postion) {
                                            LogUtil.log("GoodsGroupView", postion + "  Top " + (goodsGroupView.getTop() + y) );
                                            LogUtil.log("GoodsGroupView", postion +"   Bottom " + (goodsGroupView.getBottom() + y) );
                                            if ( (goodsGroupView.getTop() + y ) <= 0  && (goodsGroupView.getBottom() + y) >= 0){
                                                rlGoodsGroup.setVisibility(VISIBLE);
                                                if (rlGoodsGroup.getAdapter() == null || !rlGoodsGroup.getAdapter().equals(goodsGroupView.getTypeAdapter())){
                                                    rlGoodsGroup.setAdapter(goodsGroupView.getTypeAdapter());
                                                }
                                                currentShowPistion = postion;
                                            }else {
                                                if (rlGoodsGroup.getAdapter() == null){
                                                    rlGoodsGroup.setVisibility(View.GONE);
                                                    currentShowPistion = -1;
                                                }else {
                                                    if (rlGoodsGroup.getAdapter().equals(goodsGroupView.getTypeAdapter())){
                                                        rlGoodsGroup.setVisibility(View.GONE);
                                                    }
                                                }
                                            }
                                        }
                                    };
                                    listenerArrayList.add(scrollListener);
                                }
                                llMain.addView(goodsGroupView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            break;
                        case "again_order":
                            if (buyAgainViews == null){
                                buyAgainViews = new ArrayList<>();
                            }
                            WTopBean.AgainOrderData againOrderData = data.again_orderData;
                            BuyAgainView  buyAgainView = new BuyAgainView(getContext());
                            buyAgainView.setData(this,againOrderData);
                            buyAgainViews.add(buyAgainView);
                            llMain.addView(buyAgainView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            break;
                    }
                }
               startLoadShop();
                if (buyAgainViews != null){
                    if (orderAgainPresenter == null){
                        orderAgainPresenter = new OrderAgainPresenter(this);
                    }
                    orderAgainPresenter.getOrderGoods("");
                }
            }
        }
    }
    private void callPhone(String number) {
        if (StringUtils.isEmpty(number)){
            return;
        }
        String phoneNumber = number.trim();
        phoneNumber = phoneNumber.replace("-", "");
        callPhone(phoneNumber);
        if ((ContextCompat.checkSelfPermission(getContext(), CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE},
                    4000);
        } else {
            Uri uri = Uri.parse("tel:" + number);
            Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(callIntent);
        }
    }
    private void startLoadShop(){
        LogUtil.log("loadData", "isShowShoplist ------" + isShowShoplist);
        //是否显示店铺列表
        if (isShowShoplist) {
            shopAdapter.setShowShoplist(true);
            loadShop();
        } else {
            shoplist.clear();
            shopAdapter.notifyDataSetChanged();
            shopAdapter.setShowShoplist(false);
            wFooterView.setFooterViewStatus(WFooterView.STATUS_LOAD_EMPTY);
            handler.sendEmptyMessage(111);
        }
        isCanRefresh = false;
       presenter.loadCouponData(wId);

    }



    @Override
    public void add(GoodsListBean.GoodsInfo goods) {
        cartGoodsNum = BaseApplication.greenDaoManager.getAllGoodsNum();
        LogUtil.log("iWShopCart", " add   end === ");
        if (cartGoodsNum > 0) {
            rlCart.setVisibility(View.VISIBLE);
            tvCartCount.setText(cartGoodsNum + "");
            if (cartGoodsNum < 10){
                tvCartCount.setBackgroundResource(R.drawable.shape_top_cart);
            }else {
                tvCartCount.setBackgroundResource(R.drawable.shape_top_cart_1);
            }
        }
    }

    @Override
    public void remove(GoodsListBean.GoodsInfo goods) {
        cartGoodsNum = BaseApplication.greenDaoManager.getAllGoodsNum();
        if (cartGoodsNum < 1) {
            rlCart.setVisibility(View.GONE);
        } else {
            tvCartCount.setText(cartGoodsNum + "");
            if (cartGoodsNum < 10){
                tvCartCount.setBackgroundResource(R.drawable.shape_top_cart);
            }else {
                tvCartCount.setBackgroundResource(R.drawable.shape_top_cart_1);
            }
        }
    }

    private ArrayList<GoodsListBean.GoodsInfo> natureGoodsList = new ArrayList<>();
    private TextView tvNatureCount;
    private TextView tvTotalPrice;
    private WNatureGoodsAdapter natureGoodsAdapter;
    private RecyclerView rv_nature_container;
    private boolean isFirst;

    WGoodsGroupScrollAdapter wGoodsGroupScrollAdapter;
    @Override
    public void notifyItemChanged(WGoodsGroupScrollAdapter scrollAdapter) {
        wGoodsGroupScrollAdapter = scrollAdapter;
    }
    @Override
    public void showNatureDialog(final GoodsListBean.GoodsInfo goods, final int position, final String flag) {
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
        for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
            for(int i=0;i<goodsNature.data.size();i++){
                if(i==0){
                    goodsNature.data.get(i).is_selected = true;
                }else{
                    goodsNature.data.get(i).is_selected = false;
                }
            }

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

        natureGoodsAdapter = new WNatureGoodsAdapter(getContext(), natureGoodsList, shopCart, tvTotalPrice,flag);
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
                    add(goods);
                    switch (flag) {
                        case "shangpin_youxuan":
                            goodsBetterAdapter.notifyItemChanged(position);
                            break;
                        case "shangpin_big":
                            goodsBigPicAdapter.notifyItemView(position);
                            break;
                        case "shangpin_small":
                            goodsSmallPicAdapter.notifyItemView(position);
                            break;
                        case "shangpin_list":
                            goodsListAdapter.notifyItemView(position);
                            break;
                        case "shangpin_three_columns":
                            goodsThreeColumnsAdapter.notifyItemView(position);
                            break;
                        case "goods_group":
                            if (wGoodsGroupScrollAdapter != null){
                                wGoodsGroupScrollAdapter.notifyItemChanged(position,position);
                            }
                            break;
                    }
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
                    natureGoodsList.addAll(shopCart.getGoodsList(goods.id));
                    natureGoodsAdapter.notifyDataSetChanged();
                    natureGoodsAdapter.updateTotalPrice();
                    if (natureGoodsList.size() == 0) {
                        if (null != centerPopView) {
                            centerPopView.dismissCenterPopView();
                        }
                    }
                    switch (flag) {
                        case "shangpin_youxuan":
                            goodsBetterAdapter.notifyItemChanged(position);
                            break;
                        case "shangpin_big":
                            goodsBigPicAdapter.notifyItemView(position);
                            break;
                        case "shangpin_small":
                            goodsSmallPicAdapter.notifyItemView(position);
                            break;
                        case "shangpin_list":
                            goodsListAdapter.notifyItemView(position);
                            break;
                        case "shangpin_three_columns":
                            goodsThreeColumnsAdapter.notifyItemView(position);
                            break;
                        case "goods_group":
                            if (wGoodsGroupScrollAdapter != null){
                                wGoodsGroupScrollAdapter.notifyItemChanged(position,position);
                            }
                            break;
                    }
                    remove(goods);
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
    public void onLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, CODE_LOGIN_GOODS);
    }

    @Override
    public void onGoodsDetailPage(int position, GoodsListBean.GoodsInfo goods ,WGoodsGroupScrollAdapter scrollAdapter) {
        wGoodsGroupScrollAdapter = scrollAdapter;
        Intent intent = new Intent(getContext(), GoodsDetailActivity2.class);
        intent.putExtra("from_page", "WGoodsList");
        intent.putExtra("shop_id", goods.shop_id);
        intent.putExtra("goods_info", goods);
        intent.putExtra("shop_cart", shopCart);
        startActivity(intent);
    }
    //删除属性商品
    @Override
    public void deleteNatureGoods(GoodsListBean.GoodsInfo goods, int position, int refreshPos,String flag) {
        GoodsListBean.GoodsInfo deleteGoods = natureGoodsList.get(position);
        if (shopCart.subShoppingSingle(deleteGoods)){
            int count = shopCart.getGoodsCount(goods.id);
            tvNatureCount.setText(count+"");
            natureGoodsList.clear();
            natureGoodsList.remove(position);
            natureGoodsAdapter.notifyDataSetChanged();
            natureGoodsAdapter.updateTotalPrice();
            if (natureGoodsList.size() == 0) {
                if (null != centerPopView) {
                    centerPopView.dismissCenterPopView();
                }
            }
            switch (flag) {
                case "shangpin_youxuan":
                    goodsBetterAdapter.notifyItemChanged(refreshPos);
                    break;
                case "shangpin_big":
                    goodsBigPicAdapter.notifyItemView(refreshPos);
                    break;
                case "shangpin_small":
                    goodsSmallPicAdapter.notifyItemView(refreshPos);
                    break;
                case "shangpin_list":
                    goodsListAdapter.notifyItemView(refreshPos);
                    break;
                case "shangpin_three_columns":
                    goodsThreeColumnsAdapter.notifyItemView(refreshPos);
                    break;
                case "goods_group":
                    if (wGoodsGroupScrollAdapter != null){
                        wGoodsGroupScrollAdapter.notifyItemChanged(refreshPos,position);
                    }
                    break;
            }
            remove(deleteGoods);
        }
    }

    @Override
    public void showTipInfo(String content) {
        UIUtils.showToast(content);
    }
    public void goToActivity(WTopBean.BaseData baseData) {

        if (smartRefreshLayout.getState() == RefreshState.Refreshing || baseData == null) {
            return;
        }
        String url_from = baseData.url_from;
        String url_from_id = baseData.url_from_id;
        String url = baseData.url;

        String id;
        if ("selfurl".equals(url_from) || "couponpackage".equals(url_from) ){
            id = url;
        }else if( "doform".equals(url_from)){
            id = baseData.url;
        }else {
            id = url_from_id;
        }

        LogUtil.log("goToActivity","select == "+url_from+"  id == "+id +"   url = "+baseData.url);
        if (StringUtils.isEmpty(url_from)){
            return;
        }
        switch (url_from) {
            //店铺列表
            case "shoplist":
                Intent shopIntent1 = new Intent(getActivity(), WShopListActivity.class);
                shopIntent1.putExtra("from_id", "");
                shopIntent1.putExtra("combine_id", "");
                shopIntent1.putExtra("area_id", search_area_id);
                startActivity(shopIntent1);
                break;
            //店铺分类
            case "shopcat":
                Intent shopIntent2 = new Intent(getActivity(), WShopListActivity.class);
                shopIntent2.putExtra("from_id", id);
                shopIntent2.putExtra("combine_id", "0");
                shopIntent2.putExtra("area_id", search_area_id);
                startActivity(shopIntent2);
                break;

            //店铺分类组合
            case "shopcombine":
                Intent shopIntent = new Intent(getActivity(), WShopListActivity.class);
                shopIntent.putExtra("from_id", "");
                shopIntent.putExtra("combine_id", id);
                shopIntent.putExtra("area_id", search_area_id);
                startActivity(shopIntent);
                break;
            //店铺首页
            case "shopindex":
                Intent intent11 = new Intent(getActivity(), SelectGoodsActivity3.class);
                intent11.putExtra("from_page", "Wlink");
                intent11.putExtra("shop_id", id);
                startActivity(intent11);
                break;
            //选购
            case "xuangou":
                Intent intent = new Intent(getActivity(), SelectGoodsActivity3.class);
                intent.putExtra("from_page", "Wlink");
                intent.putExtra("shop_id", id);
                startActivity(intent);
                break;

            //店铺评论
            case "comment":
                BaseApplication.getPreferences().edit().putBoolean("isLinkComment", true).commit();
                Intent intent2 = new Intent(getActivity(), SelectGoodsActivity3.class);
                intent2.putExtra("from_page", "WlinkComment");
                intent2.putExtra("shop_id", id);
                startActivity(intent2);
                break;
            //销量排行
            case "salesort":
                break;
            //商品详情
            case "foodinfo":
                Intent goodsIntent = new Intent(getActivity(), GoodsDetailActivity2.class);
                goodsIntent.putExtra("from_page", "wpage");
                goodsIntent.putExtra("goods_id", id);
                startActivity(goodsIntent);
                break;
            //个人中心
            case "usercenter":
                if (getActivity() instanceof TopActivity3){
                    ((TopActivity3) getActivity()).setCurrentFragment("usercenter");
                }
                break;
            //订单查询
            case "orderhistory":
                if (getActivity() instanceof TopActivity3){
                    ((TopActivity3) getActivity()).setCurrentFragment("orderhistory");
                }
                break;
            //我的优惠券
            case "mycoupon":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(getActivity(), MyCouponActivity.class));
                break;
            //店铺收藏
            case "shopcollect":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                break;
            //优惠券礼包
            case "couponpackage":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                //外链接
            case "selfurl":
                if(!TextUtils.isEmpty(id)){
                    Intent uIntent1 = new Intent(getActivity(), WeiWebViewActivity.class);
                    uIntent1.putExtra("url",id);
                    startActivity(uIntent1);
                }
                break;
            //同城信息
            case "tongcheng":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                Intent tIntent1 = new Intent(getActivity(), CityInfoActivity.class);
                tIntent1.putExtra("type_id",id);
                startActivity(tIntent1);
                break;
            //微页面
            case "divpage":
                Intent dIntent1 = new Intent(getActivity(), WeiActivity.class);
                dIntent1.putExtra("wid",id);
                startActivityForResult(dIntent1,WEI_CODE);
                break;
            //微页面组合
            case "divpagegroup":
                Intent gIntent1 = new Intent(getActivity(), WeiGroupActivity.class);
                gIntent1.putExtra("wid",id);
                startActivityForResult(gIntent1,WEI_CODE);
                break;
            //跑腿代购
            case "errandindex":
                Intent pIntent1 = new Intent(getActivity(), PaotuiTopActivity.class);
                startActivity(pIntent1);
                break;
            //跑腿订单
            case "errandorder":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                Intent ointent = new Intent(getContext(),PaotuiOrderActivity.class);
                ointent.putExtra("where_from","order_list");
                startActivity(ointent);
                break;
            //跑腿分类
            case "errandcat":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                if (!StringUtils.isEmpty(id)){
                    String ids [] = id.split(",");
                    if (ids.length == 2){
                        Intent ppintent = new  Intent(getContext(), HelpClassifyActivity.class);
                        ppintent.putExtra("type",ids[1]);
                        ppintent.putExtra("address", "");
                        ppintent.putExtra("lat", lat);
                        ppintent.putExtra("lng", lng);
                        ppintent.putExtra("type_id",ids[0]);
                        ppintent.putExtra("title", "");
                        startActivity(ppintent);
                    }

                }
                break;
            //地图
            case "mapnav":
                Intent wIntent1 = new Intent(getContext(), WMapActivity.class);
                wIntent1.putExtra("lat",baseData.lat);
                wIntent1.putExtra("lng",baseData.lng);
                String d = StringUtils.isEmpty(baseData.desc) ? "" : baseData.desc;
                wIntent1.putExtra("dec",d);
                startActivity(wIntent1);
                break;
            case "doform":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                if(!TextUtils.isEmpty(id)){
                    Intent form = new Intent(getContext(), FormActivity.class);
                    form.putExtra("url",id);
                    form.putExtra("title","");
                    startActivity(form);
                }
                break;
            //签到
            case "qiandao":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                Intent sign = new Intent(getContext(), SignActivity.class);
                sign.putExtra("type", 1);
                sign.putExtra("url", baseData.url);
                startActivity(sign);
                break;
            //大转盘
            case "dazhuanpan":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                Intent draw = new Intent(getContext(), SignActivity.class);
                draw.putExtra("type", 3);
                draw.putExtra("url", baseData.url);
                startActivity(draw);
                break;
            //邀请有奖
            case "yaoqingyoujiang":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                Intent fi = new Intent(getContext(), InviteFriendActivity.class);
                startActivity(fi);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.log("loadData", "onActivityResult ------");
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == RESULT_LOCATION_CODE) {
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");
                SharedPreferencesUtil.saveLatitude(lat);
                SharedPreferencesUtil.saveLongitude(lng);
                locationAddress = data.getStringExtra("address");
                addressView.setAddressValue(locationAddress);
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
//                loadView.showView();
                isCanRefresh = true;
                loadData();
            }
            if (requestCode == WEI_CODE){
                SharedPreferencesUtil.saveLatitude(lat);
                SharedPreferencesUtil.saveLongitude(lng);
            }
            if (requestCode == RESULT_LOCATION_SEARCH){
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");
                SharedPreferencesUtil.saveLatitude(lat);
                SharedPreferencesUtil.saveLongitude(lng);
                locationAddress = data.getStringExtra("address");
                addressView.setAddressValue(locationAddress);
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                isCanRefresh = false;
                loadData();
            }
            if (requestCode == Const.GO_TO_LOGIN) {
                token = SharedPreferencesUtil.getToken();
                //点击购物车悬浮按钮，跳转到购物车列表
//                startActivity(new Intent(getActivity(), ShoppingCartListActivity.class));
            }
            if (requestCode == WEI_GET_COUPON_CODE){
                token = SharedPreferencesUtil.getToken();
                if (!StringUtils.isEmpty(token)){
                    presenter.loadCouponData(wId);
                }
            }
            if (requestCode == CODE_LOGIN_GOODS){
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog2(getContext());
                }
                loadingDialog.show();
                isCanRefresh = false;
                loadData();
            }
        }
    }

    private void queryCartGoods() {
        ArrayList<GoodsListBean.GoodsInfo> cartList = BaseApplication.greenDaoManager.getAllGoodsInfo();
        if (null != cartList && cartList.size() > 0) {
            shopCart.addGoodsListFromDB(cartList);
        }
    }

    //富文本css
    private String getWebContent(String content) {
        String css = "<style>.box{ font-size:40px ;color:#373737 ;margin:30px}</style>";
        css = css + "<body><div class = 'box'>" + content + "</div></body";
        return css;
    }

    @Override
    public void toGoodsDetailPage(GoodsListBean.GoodsInfo goods, int position) {
        Intent intent = new Intent(getContext(), GoodsDetailActivity2.class);
        intent.putExtra("from_page", "WGoodsList");
        intent.putExtra("goods_info", goods);
        getContext().startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.log("loadData", "onResume == " + getUserVisibleHint());
        cartGoodsNum = BaseApplication.greenDaoManager.getAllGoodsNum();
        if (cartGoodsNum < 1) {
            rlCart.setVisibility(View.INVISIBLE);
        } else {
            tvCartCount.setText(cartGoodsNum + "");
            if (cartGoodsNum < 10){
                tvCartCount.setBackgroundResource(R.drawable.shape_top_cart);
            }else {
                tvCartCount.setBackgroundResource(R.drawable.shape_top_cart_1);
            }
            rlCart.setVisibility(View.VISIBLE);
        }
        shopCart.clear();
        queryCartGoods();//刷新购物车中数据
        if (listType.size() > 0) {
            for (String widgetType : listType) {
                switch (widgetType) {
                    case "shangpin_youxuan":
                        goodsBetterAdapter.notifyDataSetChanged();
                        break;
                    case "shangpin_big":
                        goodsBigPicAdapter.notifyItemCount();
                        break;
                    case "shangpin_small":
                        goodsSmallPicAdapter.notifyItemCount();
                        break;
                    case "shangpin_list":
                        goodsListAdapter.notifyItemCount();
                        break;
                    case "shangpin_three_columns":
                        goodsThreeColumnsAdapter.notifyItemCount();
                        break;
                }
            }
        }
        if (wGoodsGroupScrollAdapter != null){
            wGoodsGroupScrollAdapter.notifyDataSetChanged();
        }
    }

    public void requestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION) {
            if ((grantResults.length == 1 || grantResults.length == 3) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LogUtil.log("loadData", "requestPermissionsResult ------open");
                llLoadFail.setVisibility(View.GONE);
                flLocation.setVisibility(View.GONE);
                loadView.showView("");
                doLocation();
            } else {
                LogUtil.log("loadData", "requestPermissionsResult ------fail");
                llLoadFail.setVisibility(View.GONE);
                flLocation.setVisibility(View.VISIBLE);
                llLocationFail.setVisibility(View.VISIBLE);
                llLocationSearch.setVisibility(View.GONE);
                loadView.dismissView();
                locationAddress = "定位失败";
                addressView.setAddressValue(locationAddress);
            }
        }
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        locationPresenter.upRegisterLocationListener(this);
        super.onDestroy();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        LogUtil.log("loadData", "onPoiSearched ");
        if (poiResult.getPois().size() > 0) {
            PoiItem poiItem = poiResult.getPois().get(0);
            locationAddress = poiItem.getTitle();
            lat = poiItem.getLatLonPoint().getLatitude() + "";
            lng = poiItem.getLatLonPoint().getLongitude() + "";
        }
        if (addressView != null) {
            addressView.setAddressValue(locationAddress);
        }
        loadData();

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public void refreshComplete() {
        if (smartRefreshLayout.getState() == RefreshState.Refreshing) {
            smartRefreshLayout.finishRefresh();
        }
    }
    public void refreshComplete(final WTopBean bean) {
        initData(bean);

    }

    boolean isHasShow = false;
    @Override
    public void loadCouponSuccess(final GetCouponBean.CouponInfo info) {
        if (info == null){
            return;
        }
        if ("1".equals(info.show_type)){
            if (getCouponPopupWindow == null){
                getCouponPopupWindow = new GetCouponPopupWindow(getContext(),this);
            }
            //未登录，弹一次
            if (StringUtils.isEmpty(SharedPreferencesUtil.getToken()) && isHasShow){
                return;
            }
            getCouponPopupWindow.showWithData(info);
            isHasShow = true;

        }else  if ("2".equals(info.show_type)){
            if (advertisementPopupWindow == null){
                advertisementPopupWindow = new AdvertisementPopupWindow(getContext());
                advertisementPopupWindow.setData(info.advertisement.title,info.advertisement.img_url);
                advertisementPopupWindow.setAdvertClickListener(new AdvertisementPopupWindow.AdvertClickListener() {
                    @Override
                    public void onAdvertClicked() {
                        if(!TextUtils.isEmpty(info.advertisement.location_url)){
                            Intent uIntent1 = new Intent(getActivity(), WeiWebViewActivity.class);
                            uIntent1.putExtra("url",info.advertisement.location_url);
                            startActivity(uIntent1);
                        }
                    }
                });
            }
            if (advertisementPopupWindow.isFirstShow()){
                LogUtil.log("guanggaoDialog","show -- ");
                advertisementPopupWindow.show();
            }

        }
    }

    public void getCoupons(){
        if (getCouponPopupWindow != null && StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            return;
        }
        presenter.loadCouponData(wId);
    }
    //滑动吸顶
    public void listViewScroll(){
        if (listview.getChildCount() > 1){
            int top = addressView.finalHeight;
            listview.setSelectionFromTop(1,top);
        }
    }
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        LogUtil.log("LocationChanged", "onRegeocodeSearched ");
        List<AoiItem> list = regeocodeResult.getRegeocodeAddress().getAois();
        if (list != null && list.size() > 0){
            AoiItem item = list.get(0);
            LogUtil.log("LocationChanged", "onRegeocodeSearched == "+ item.getAoiName());
            locationAddress = item.getAoiName();
        }else {
            String building = regeocodeResult.getRegeocodeAddress().getBuilding();
            if (!StringUtils.isEmpty(building)){
                locationAddress = building;
            }else {
                locationAddress = regeocodeResult.getRegeocodeAddress().getStreetNumber().getStreet();
            }
        }
        if (addressView != null) {
            addressView.setAddressValue(locationAddress);
        }
        loadData();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    private ArrayList<BuyAgainView> buyAgainViews;
    private OrderAgainBean orderAgainBean;
    @Override
    public void getOrderGoods(OrderAgainBean bean) {
        orderAgainBean = bean;
        if (bean.data != null ){
            String shop_name = "";
            String url  = "";
            StringBuffer goods = new StringBuffer();
            if (bean.data.cart != null && bean.data.cart.size() > 0){
                shop_name = bean.data.cart.get(0).foodInfo.shopname;
                url = bean.data.cart.get(0).foodInfo.img;
                for (OrderAgainBean.CartGoodsData cartGoodsData : bean.data.cart){
                    goods.append(cartGoodsData.foodInfo.name);
                }

                for (BuyAgainView view : buyAgainViews){
                    view.setGoodsData(url,shop_name,goods.toString());
                }
            }

        }
    }
    private void dealOrderAgain(){
        if (orderAgainBean != null ){
            String shop_id = "";
            if (orderAgainBean.data.cart != null && orderAgainBean.data.cart.size() > 0){
                shop_id = orderAgainBean.data.cart.get(0).foodInfo.shop_id;
                BaseApplication.greenDaoManager.deleteGoodsByShopId(shop_id);
                String tip = orderAgainPresenter.addGoodsToDB(orderAgainBean.data);
                Intent intent = new Intent(getContext(), SelectGoodsActivity3.class);
                intent.putExtra("from_page", "order_again");
                intent.putExtra("shop_id",shop_id);
                intent.putExtra("goods_tip",tip);
                startActivity(intent);
            }

        }
    }

    @Override
    public void getOrderGoodsFail() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(getContext());
        }
        loadingDialog.dismiss();
    }
}
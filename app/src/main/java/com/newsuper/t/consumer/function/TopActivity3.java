package com.newsuper.t.consumer.function;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.base.BaseFragment;
import com.newsuper.t.consumer.bean.APPInfoBean;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.bean.TopTabBean;
import com.newsuper.t.consumer.function.distribution.fragment.DistributionFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpBuyFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpCustomFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpLineUpFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpSendFragment;
import com.newsuper.t.consumer.function.distribution.fragment.HelpTakeFragment;
import com.newsuper.t.consumer.function.distribution.fragment.PaotuiOrderFragment;
import com.newsuper.t.consumer.function.login.internal.IAppInfo;
import com.newsuper.t.consumer.function.login.presenter.AppInfoPresenter;
import com.newsuper.t.consumer.function.order.activity.OrderListActivity;
import com.newsuper.t.consumer.function.order.fragment.OrderFragment;
import com.newsuper.t.consumer.function.person.PersonFragment3;
import com.newsuper.t.consumer.function.top.avtivity.WeiWebViewActivity;
import com.newsuper.t.consumer.function.top.fragment.CouponClassifyFragment;
import com.newsuper.t.consumer.function.top.fragment.FormFragment;
import com.newsuper.t.consumer.function.top.fragment.MapFragment;
import com.newsuper.t.consumer.function.top.fragment.ShopClassifyFragment;
import com.newsuper.t.consumer.function.top.fragment.ShopCollectFragment;
import com.newsuper.t.consumer.function.top.fragment.WAreaTopFragment2;
import com.newsuper.t.consumer.function.top.fragment.WTopFragment;
import com.newsuper.t.consumer.function.top.fragment.WeiWebFragment;
import com.newsuper.t.consumer.function.top.internal.ITopView;
import com.newsuper.t.consumer.function.top.presenter.TopPresenter;
import com.newsuper.t.consumer.service.GeTuiIntentService;
import com.newsuper.t.consumer.service.GeTuiPushService;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StatusBarUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.LimitScrollViewPager;
import com.newsuper.t.consumer.widget.defineTopView.WGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopActivity3 extends BaseActivity implements View.OnClickListener, ITopView, IAppInfo {
    @BindView(R.id.top_viewpager)
    LimitScrollViewPager topViewpager;
    @BindView(R.id.tab_gridview)
    WGridView tabGridview;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.btn_load_again)
    Button btn_load_again;
    @BindView(R.id.tv_fail)
    TextView tv_fail;
    @BindView(R.id.ll_fail)
    LinearLayout ll_fail;
    @BindView(R.id.vv_bar)
    View vv_bar;
    @BindView(R.id.tv_call)
    TextView tv_call;
    @BindView(R.id.ll_app_out_date)
    LinearLayout ll_app_out_date;

    public static int TAB_STYLE = 1;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ViewPagerAdapter pagerAdapter;
    private TopPresenter presenter;
    private AppInfoPresenter appInfoPresenter;
    private ArrayList<TopTabBean.TabData> list = new ArrayList<>();
    private TabGridAdapter tabGridAdapter;
    private String show_type = "1";
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top3);
        ButterKnife.bind(this);
        vv_bar.getLayoutParams().height = StatusBarUtil.getStatusBarHeight(this);
        btn_load_again.setOnClickListener(this);
        tv_call.setOnClickListener(this);
        tv_fail.setText("网络错误，获取数据失败，请稍候重试。");
        isShowOrderDialog = true;
        appInfoPresenter = new AppInfoPresenter(this);
        presenter = new TopPresenter(this);
        appInfoPresenter.loadAPPInfo(Const.ADMIN_ID);
    }


    @Override
    public void initData() {
        initGeTui();
    }

    @Override
    public void initView() {

    }
    private void initFragment(ArrayList<TopTabBean.TabData> l) {
        for (int i = 0; i < l.size(); i++) {
            TopTabBean.TabData tabData = l.get(i);
            switch (l.get(i).url_from) {
                //店铺列表
                case "shoplist":
                    ShopClassifyFragment shopClassifyFragment = new ShopClassifyFragment();
                    Bundle sbundle1 = new Bundle();
                    sbundle1.putString("from_id", "");
                    sbundle1.putString("combine_id", "");
                    shopClassifyFragment.setArguments(sbundle1);
                    fragments.add(shopClassifyFragment);
                    break;
                //店铺分类
                case "shopcat":
                    ShopClassifyFragment shopClassifyFragment2 = new ShopClassifyFragment();
                    Bundle sbundle2 = new Bundle();
                    sbundle2.putString("from_id", tabData.url_from_id);
                    sbundle2.putString("combine_id", "");
                    shopClassifyFragment2.setArguments(sbundle2);
                    fragments.add(shopClassifyFragment2);
                    break;
                //店铺分类组合com.xunjoy.lewaimai.user.consumer.chihuwaimai
                case "shopcombine":
                    ShopClassifyFragment shopClassifyFragment3 = new ShopClassifyFragment();
                    Bundle sbundle3 = new Bundle();
                    sbundle3.putString("from_id", "");
                    sbundle3.putString("combine_id", tabData.url_from_id);
                    shopClassifyFragment3.setArguments(sbundle3);
                    fragments.add(shopClassifyFragment3);
                    break;
                //店铺评论
                case "comment":
                    break;
                //销量排行
                case "salesort":
                    break;
                //商品详情
                case "foodinfo":
                    break;
                //我的优惠券
                case "mycoupon":
                    fragments.add(new CouponClassifyFragment());
                    break;
                //店铺收藏
                case "shopcollect":
                    fragments.add(new ShopCollectFragment());
                    break;
                //店铺留言
                case "message":
                    break;
                //微页面
                case "divpage":
                    WTopFragment wTopFragment = new WTopFragment();
                    WTopFragment.WET_ID = tabData.url_from_id;
                    fragments.add(wTopFragment);
                    break;
                //分区微页面
                case "areaindex":
                    WAreaTopFragment2 wAreaTopFragment2 = new WAreaTopFragment2();
                    fragments.add(wAreaTopFragment2);
                    break;
                //我的
                case "usercenter":
                    fragments.add(new PersonFragment3());
                    break;
                //订单查询
                case "orderhistory":
                    fragments.add(new OrderFragment());
                    break;
                //外链接
                case "selfurl":
                    String url  = StringUtils.isEmpty(tabData.url)? "":tabData.url;
                    LogUtil.log("initFragment","url == " + url);
                    WeiWebFragment weiWebFragment = new WeiWebFragment();
                    Bundle weibundle = new Bundle();
                    weibundle.putString("url",url);
                    weiWebFragment.setArguments(weibundle);
                    fragments.add(weiWebFragment);
                    break;
                //表单
                case "doform":
                    String doform  = StringUtils.isEmpty(tabData.url)? "":tabData.url;
                    LogUtil.log("initFragment","url == " + doform);
                    FormFragment formFragment = new FormFragment();
                    Bundle fbundle = new Bundle();
                    fbundle.putString("url",doform);
                    fbundle.putString("title","");
                    formFragment.setArguments(fbundle);
                    fragments.add(formFragment);
                    break;
                //优惠券礼包
                case "couponpackage":
                    break;
                case "map":
                    MapFragment mapFragment = new MapFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("lat", "23.0584545");
                    bundle.putString("lng", "113.0584545");
                    bundle.putString("des", "电话：0755-45454-9898");
                    mapFragment.setArguments(bundle);
                    fragments.add(mapFragment);
                    break;
                //跑腿代购
                case "errandindex":
                    fragments.add(new DistributionFragment());
                    break;
                //跑腿订单
                case "errandorder":
                    PaotuiOrderFragment paotuiOrderFragment = new PaotuiOrderFragment();
                    Bundle pbundle = new Bundle();
                    pbundle.putString("where_from", "order_list");
                    paotuiOrderFragment.setArguments(pbundle);
                    fragments.add(paotuiOrderFragment);
                    break;
                //跑腿分类
                case "errandcat":
                    if (!StringUtils.isEmpty(tabData.url_from_id)) {
                        String ids[] = tabData.url_from_id.split(",");
                        if (ids.length == 2) {
                            Bundle ebundle = new Bundle();
                            ebundle.putString("type", ids[1]);
                            ebundle.putString("address", "");
                            ebundle.putString("lat", SharedPreferencesUtil.getLatitude());
                            ebundle.putString("lng", SharedPreferencesUtil.getLongitude());
                            ebundle.putString("type_id", ids[0]);
                            ebundle.putString("title", "");
                            switch (ids[1]) {
                                //帮买
                                case "1":
                                    HelpBuyFragment buyFragment = new HelpBuyFragment();
                                    buyFragment.setArguments(ebundle);
                                    fragments.add(buyFragment);
                                    break;
                                //帮送
                                case "2":
                                    HelpSendFragment sendFragment = new HelpSendFragment();
                                    sendFragment.setArguments(ebundle);
                                    fragments.add(sendFragment);
                                    break;
                                //帮取
                                case "3":
                                    HelpTakeFragment takeFragment = new HelpTakeFragment();
                                    takeFragment.setArguments(ebundle);
                                    fragments.add(takeFragment);
                                    break;
                                //帮排队
                                case "4":
                                    HelpLineUpFragment lineUpFragment = new HelpLineUpFragment();
                                    lineUpFragment.setArguments(ebundle);
                                    fragments.add(lineUpFragment);
                                    break;
                                //自定义
                                case "5":
                                    HelpCustomFragment customFragment = new HelpCustomFragment();
                                    customFragment.setArguments(ebundle);
                                    fragments.add(customFragment);
                                    break;
                            }

                        }

                    }
                    break;
                default:
                    fragments.add(new Fragment());
                    break;
            }
        }
        list.addAll(l);
        intViewPager(2);
    }

    //1:默认  2：微页面组合
    private void intViewPager(int style) {
        tabGridAdapter = new TabGridAdapter(this, list,show_type,style);
        tabGridview.setNumColumns(list.size());
        tabGridview.setAdapter(tabGridAdapter);
        tabGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                topViewpager.setCurrentItem(position);
                tabGridAdapter.setCurrentSelect(position);
                TopTabBean.TabData tabData = list.get(position);
                String s = StringUtils.isEmpty(tabData.url_from)? "":tabData.url_from;
                vv_bar.setVisibility(View.VISIBLE);
                switch (s) {
                    //店铺列表
                    case "shoplist":
                    //店铺分类
                    case "shopcat":
                    //店铺分类组合
                    case "shopcombine":
                        if (fragments.get(position) instanceof ShopClassifyFragment) {
                            ((ShopClassifyFragment) fragments.get(position)).refresh();
                        }
                        break;
                    //店铺首页
                    case "shopindex":
                        break;
                    //店铺评论
                    case "comment":
                        break;
                    //销量排行
                    case "salesort":
                        break;
                    //我的优惠券
                    case "mycoupon":

                        break;
                    //店铺收藏
                    case "shopcollect":

                        break;
                    //店铺留言
                    case "message":
                        break;
                    //微页面
                    case "divpage":
                        if (fragments.get(position) instanceof WTopFragment) {
                            ((WTopFragment) fragments.get(position)).getCoupons();
                        }
                        break;
                    //分区微页面
                    case "areaindex":
                        if (fragments.get(position) instanceof WAreaTopFragment2) {
                            ((WAreaTopFragment2) fragments.get(position)).getCoupons();
                        }
                        break;
                    //我的
                    case "usercenter":
                        if (fragments.get(position) instanceof PersonFragment3) {
                            vv_bar.setVisibility(View.GONE);
                            ((PersonFragment3) fragments.get(position)).refresh();
                        }
                        break;
                    //订单查询
                    case "orderhistory":
                        break;
                    //外链接
                    case "selfurl":
                        if(!TextUtils.isEmpty(tabData.url)){
                            Intent uIntent1 = new Intent(TopActivity3.this, WeiWebViewActivity.class);
                            uIntent1.putExtra("url",tabData.url);
                            startActivity(uIntent1);
                            return;
                        }
                        break;
                    //优惠券礼包
                    case "couponpackage":
                        break;
                    case "map":
                        break;
                    //跑腿代购
                    case "errandindex":
                        if (fragments.get(position) instanceof DistributionFragment){
                            ((DistributionFragment) fragments.get(position)).refresh();
                        }
                        break;
                    //跑腿订单
                    case "errandorder":
                        if (fragments.get(position) instanceof PaotuiOrderFragment) {
                            ((PaotuiOrderFragment) fragments.get(position)).refresh();
                        }
                        break;
                    //跑腿分类
                    case "errandcat":
                        break;
                }
            }
        });
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        topViewpager.setAdapter(pagerAdapter);
        topViewpager.setOffscreenPageLimit(fragments.size());
        topViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabGridAdapter.setCurrentSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void setCurrentFragment(String index){
        switch (index){
            //个人中心
            case "usercenter":
                topViewpager.setCurrentItem(list.size() - 1);
                break;
            //订单查询
            case "orderhistory":
                vv_bar.setVisibility(View.VISIBLE);
                if (TAB_STYLE == 1){
                    topViewpager.setCurrentItem(1);
                }else {
                    boolean isHas = false;
                    for (int i = 0;i < list.size();i++){
                        if (list.get(i).url_from.equals("orderhistory")){
                            isHas = true;
                            topViewpager.setCurrentItem(i);
                            break;
                        }
                    }
                    if (!isHas){
                        startActivity(new Intent(this, OrderListActivity.class));
                    }
                }
                break;
        }
    }
    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this, s);
    }

    @Override
    public void loadTabData(TopTabBean bean) {
        llTab.setVisibility(View.VISIBLE);
        if (bean != null && bean.data != null) {
            if (!StringUtils.isEmpty(bean.data.bg_color) && bean.data.bg_color.startsWith("#") && (bean.data.bg_color.length() == 7 || bean.data.bg_color.length() == 9)){
                llTab.setBackgroundColor(Color.parseColor(bean.data.bg_color));
            }
            show_type = bean.data.show_type;
            initFragment(bean.data.list);
        }
    }

    @Override
    public void loadAppInfo(APPInfoBean bean) {
        ll_fail.setVisibility(View.GONE);
        if (null != bean) {
            phone = bean.data.due_date;
            if (!StringUtils.isEmpty(bean.data.due_date)){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date = format.parse(bean.data.due_date);
                    if (date.getTime() < System.currentTimeMillis()){
                        ll_app_out_date.setVisibility(View.VISIBLE);
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            SharedPreferencesUtil.saveCustomerLogo(bean.data.brand_icon);
            SharedPreferencesUtil.saveCustomerAppId(bean.data.customer_app_id);
            SharedPreferencesUtil.saveTechnology(bean.data.is_show_jszc);
            //1:默认  2：微页面组合
            if ("2".equals(bean.data.app_index_style)) {
                TAB_STYLE = 2;
                presenter.loadData(bean.data.divpagegroup_id);
            } else {
                TopTabBean.TabData tabData1 = new TopTabBean.TabData();
                tabData1.name = "首页";
                if ("1".equals(bean.data.app_type)){
                    tabData1.url_from  = "divpage";
                    WTopFragment wTopFragment = new WTopFragment();
                    WTopFragment.WET_ID = bean.data.divpage_id;
                    fragments.add(wTopFragment);
                }else {
                    tabData1.url_from  = "areaindex";
                    WAreaTopFragment2 wAreaTopFragment2 = new WAreaTopFragment2();
                    fragments.add(wAreaTopFragment2);
                }
                list.add(tabData1);

                TopTabBean.TabData tabData2 = new TopTabBean.TabData();
                tabData2.name = "订单";
                tabData2.url_from = "errandorder";
                list.add(tabData2);
                fragments.add(new OrderFragment());


                TopTabBean.TabData tabData3 = new TopTabBean.TabData();
                tabData3.name = "我的";
                tabData3.url_from = "usercenter";
                list.add(tabData3);
                fragments.add(new PersonFragment3());
                intViewPager(1);
            }
        }
    }

    @Override
    public void loadFail() {
        ll_fail.setVisibility(View.VISIBLE);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> list;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;

        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //清除购物车信息
        BaseApplication.greenDaoManager.getSession().getGoodsDbBeanDao().deleteAll();
        super.onDestroy();
    }

    public static final int PERMISSION_CODE = 1112;
    public static final int CALL_PHONE_CODE = 1112;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtil.log("loadData", "requestPermissionsResult ------");
        if (requestCode == PERMISSION_CODE) {
            LogUtil.log("loadData", "requestPermissionsResult ------" + grantResults.length);
            if (grantResults.length > 0) {
                if (grantResults.length == 3) {
                    if ((grantResults[1] == PackageManager.PERMISSION_GRANTED
                            && grantResults[2] == PackageManager.PERMISSION_GRANTED)) {
                        //初始化个推
                        PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiPushService.class);
                    } else {
                        //初始化个推
                        PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiPushService.class);
                    }

                }
                if (fragments.size() > 0 && fragments.get(0) != null) {
                    ((BaseFragment) fragments.get(0)).requestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }else if (requestCode == CALL_PHONE_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }

    }

    // 个推
    private void initGeTui() {
        PackageManager pkgManager = getPackageManager();
        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestPermission();
        } else {
            //初始化个推
            PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiPushService.class);
        }
        PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);
        //开启个推
        PushManager.getInstance().turnOnPush(this);
        //绑定别名
        PushManager.getInstance().bindAlias(this, SharedPreferencesUtil.getUserId());
    }

    private void requestPermission() {
        LogUtil.log("loadData", "requestPermissionsResult ------start");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                PERMISSION_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_again:
                ll_fail.setVisibility(View.GONE);
                appInfoPresenter.loadAPPInfo(Const.ADMIN_ID);
                break;
            case R.id.tv_call:
                callPhone(phone);
                break;
        }
    }

    private ShopInfoBean bean;
    public void setShopInfoBean(ShopInfoBean bean){
        this.bean=bean;
    }
    public ShopInfoBean getShopInfoBean(){
        return bean;
    }


    @Override
    public void initStatusBar() {
        StatusBarUtil.setStatusBarFullScreen(this);
    }


    private void callPhone(String number) {
        if (TextUtils.isEmpty(number)) {
            UIUtils.showToast("当前号码为空");
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE )
                != PackageManager.PERMISSION_GRANTED) {
            //申请ACCESS_FINE_LOCATION权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE },
                    CALL_PHONE_CODE);
            return ;
        }
        Uri uri = Uri.parse("tel:" + number);
        Intent callPnoenIntent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(callPnoenIntent);
    }

}

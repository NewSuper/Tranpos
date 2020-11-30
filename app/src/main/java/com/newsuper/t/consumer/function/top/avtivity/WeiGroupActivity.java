package com.newsuper.t.consumer.function.top.avtivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.igexin.sdk.PushManager;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.application.BaseApplication;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.base.BaseFragment;
import com.xunjoy.lewaimai.consumer.bean.APPInfoBean;
import com.xunjoy.lewaimai.consumer.bean.ShopInfoBean;
import com.xunjoy.lewaimai.consumer.bean.TopTabBean;
import com.xunjoy.lewaimai.consumer.function.TabGridAdapter;
import com.xunjoy.lewaimai.consumer.function.TopActivity3;
import com.xunjoy.lewaimai.consumer.function.distribution.fragment.DistributionFragment;
import com.xunjoy.lewaimai.consumer.function.distribution.fragment.HelpBuyFragment;
import com.xunjoy.lewaimai.consumer.function.distribution.fragment.HelpCustomFragment;
import com.xunjoy.lewaimai.consumer.function.distribution.fragment.HelpLineUpFragment;
import com.xunjoy.lewaimai.consumer.function.distribution.fragment.HelpSendFragment;
import com.xunjoy.lewaimai.consumer.function.distribution.fragment.HelpTakeFragment;
import com.xunjoy.lewaimai.consumer.function.distribution.fragment.PaotuiOrderFragment;
import com.xunjoy.lewaimai.consumer.function.login.internal.IAppInfo;
import com.xunjoy.lewaimai.consumer.function.login.presenter.AppInfoPresenter;
import com.xunjoy.lewaimai.consumer.function.order.fragment.OrderFragment;
import com.xunjoy.lewaimai.consumer.function.person.PersonFragment3;
import com.xunjoy.lewaimai.consumer.function.top.fragment.CouponClassifyFragment;
import com.xunjoy.lewaimai.consumer.function.top.fragment.MapFragment;
import com.xunjoy.lewaimai.consumer.function.top.fragment.ShopClassifyFragment;
import com.xunjoy.lewaimai.consumer.function.top.fragment.ShopCollectFragment;
import com.xunjoy.lewaimai.consumer.function.top.fragment.WAreaTopFragment2;
import com.xunjoy.lewaimai.consumer.function.top.fragment.WTopFragment;
import com.xunjoy.lewaimai.consumer.function.top.internal.ITopView;
import com.xunjoy.lewaimai.consumer.function.top.presenter.TopPresenter;
import com.xunjoy.lewaimai.consumer.service.GeTuiIntentService;
import com.xunjoy.lewaimai.consumer.service.GeTuiPushService;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.widget.LimitScrollViewPager;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeiGroupActivity extends BaseActivity implements ITopView {
    @BindView(R.id.top_viewpager)
    LimitScrollViewPager topViewpager;
    @BindView(R.id.tab_gridview)
    WGridView tabGridview;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;

    ArrayList<Fragment> fragments = new ArrayList<>();
    ViewPagerAdapter pagerAdapter;
    TopPresenter presenter;
    ArrayList<TopTabBean.TabData> list = new ArrayList<>();
    TabGridAdapter tabGridAdapter;
    private String show_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_group);
        ButterKnife.bind(this);
        presenter = new TopPresenter(this);
        String id = getIntent().getStringExtra("wid");
        presenter.loadData(id);
    }

    @Override
    public void initData() {
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
                //店铺分类组合
                case "shopcombine":
                    ShopClassifyFragment shopClassifyFragment3 = new ShopClassifyFragment();
                    Bundle sbundle3 = new Bundle();
                    sbundle3.putString("from_id", "");
                    sbundle3.putString("combine_id", tabData.url_from_id);
                    shopClassifyFragment3.setArguments(sbundle3);
                    fragments.add(shopClassifyFragment3);
                    break;
                //店铺首页
                case "shopindex":
                    break;
                //选购
                case "xuangou":
//                    TabSelectGoodsFragment selectGoodsFragment=new TabSelectGoodsFragment();
//                    Bundle shopBundle = new Bundle();
//                    shopBundle.putString("shop_id",tabData.url_from_id);
//                    selectGoodsFragment.setArguments(shopBundle);
//                    fragments.add(selectGoodsFragment);
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
                    Bundle wbundle3 = new Bundle();
                    wbundle3.putString("id", tabData.url_from_id);
                    wTopFragment.setArguments(wbundle3);
                    fragments.add(wTopFragment);
                    break;
                //分区微页面
                case "areaindex":
                    WAreaTopFragment2 wAreaTopFragment2 = new WAreaTopFragment2();
                    Bundle wabundle3 = new Bundle();
                    wabundle3.putString("id", tabData.url_from_id);
                    wAreaTopFragment2.setArguments(wabundle3);
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
                   fragments.add(new Fragment());
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
                switch (tabData.url_from) {
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
                            ((PersonFragment3) fragments.get(position)).refresh();
                        }
                        break;
                    //订单查询
                    case "orderhistory":
                        break;
                    //外链接
                    case "selfurl":
                        if(!TextUtils.isEmpty(tabData.url)){
                            Intent uIntent1 = new Intent(WeiGroupActivity.this, WeiWebViewActivity.class);
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
                        break;
                    //跑腿订单
                    case "errandorder":
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
            if (!StringUtils.isEmpty(bean.data.bg_color) && bean.data.bg_color.startsWith("#")){
                llTab.setBackgroundColor(Color.parseColor(bean.data.bg_color));
            }
            show_type = bean.data.show_type;
            initFragment(bean.data.list);
        }
    }
    @Override
    public void loadFail() {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

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

    public static final int PERMISSION_CODE = 1112;

}
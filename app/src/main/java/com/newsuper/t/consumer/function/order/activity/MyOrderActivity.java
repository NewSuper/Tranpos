package com.newsuper.t.consumer.function.order.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.OrderInfoBean;
import com.newsuper.t.consumer.function.inter.ILoadData;
import com.newsuper.t.consumer.function.inter.IOrderInfoActivityView;
import com.newsuper.t.consumer.function.order.adapter.OrderFragmentAdapter;
import com.newsuper.t.consumer.function.order.fragment.BaseOrderFragment;
import com.newsuper.t.consumer.function.order.fragment.OrderDetailFragment;
import com.newsuper.t.consumer.function.order.fragment.OrderStatusFragment;
import com.newsuper.t.consumer.function.order.fragment.OrderStatusFragment2;
import com.newsuper.t.consumer.function.order.presenter.OrderInfoPresenter;
import com.newsuper.t.consumer.function.order.request.OrderInfoRequest;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;

import java.lang.reflect.Field;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyOrderActivity extends BaseActivity implements IOrderInfoActivityView, CustomToolbar.CustomToolbarListener, ILoadData {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;
    @BindView(R.id.loading_view)
    RefreshThirdStepView loadingView;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.line_horizontal)
    View lineHorizontal;
    private OrderFragmentAdapter adapter;
    private String admin_id = Const.ADMIN_ID;
    private String token;
    private OrderInfoBean bean;
    private String order_no;
    private String order_id;
    private String shop_id;
    private String from;

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

    public void setOrderNo(String order_no) {
        this.order_no = order_no;
    }

    @Override
    public void initData() {
        order_no = getIntent().getStringExtra("order_no");
        order_id = getIntent().getStringExtra("order_id");
        from = getIntent().getStringExtra("from");
        admin_id = SharedPreferencesUtil.getAdminId();
        token = SharedPreferencesUtil.getToken();

        //获取订单详情
        loadData();
    }

    public String getFrom(){
        return  from;
    }

    public void loadData() {
        //获取默认信息
        String lat = "";
        lat = SharedPreferencesUtil.getLatitude();
        String lnt = "";
        lnt = SharedPreferencesUtil.getLongitude();
        OrderInfoPresenter presenter = new OrderInfoPresenter(this);
        HashMap<String, String> request = OrderInfoRequest.orderInfoRequest(token, admin_id, order_no,lat,lnt);
        presenter.loadData(UrlConst.ORDER_INFO, request);
    }

    //正在加载
    private void showLoadingView() {
        vpOrder.setVisibility(View.INVISIBLE);
        llLoading.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        handler.post(anim);
    }

    //关闭加载动画
    private void closeLoadingView() {
        animationDrawable.stop();
        llLoading.setVisibility(View.GONE);
        vpOrder.setVisibility(View.VISIBLE);
        lineHorizontal.setVisibility(View.VISIBLE);
    }

    public String getOrderId() {
        return order_id;
    }

    public String getOrderNo() {
        return order_no;
    }

    public String getShopId() {
        return shop_id;
    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        tabOrder.setTabIndicatorFullWidth(false);
        toolbar.setTitleText("我的订单");
        toolbar.setCustomToolbarListener(this);
        toolbar.setTvMenuVisibility(View.INVISIBLE);

        animationDrawable = (AnimationDrawable) loadingView.getBackground();
        //加载动画
        showLoadingView();
    }
    //加载成功
    @Override
    public void setActivityData(OrderInfoBean bean) {
        this.bean = bean;
        shop_id = bean.data.shopmodel.id;
        //在获取数据之后才添加fragment
        if (adapter == null) {
            adapter = new OrderFragmentAdapter(getSupportFragmentManager());
            if ("0".equals(bean.data.orderinfo.is_selftake)) {
                adapter.addFragment(new OrderStatusFragment());
            } else if ("1".equals(bean.data.orderinfo.is_selftake)) {
                //到店自取
                adapter.addFragment(new OrderStatusFragment2());
            }
            adapter.addFragment(new OrderDetailFragment());
            vpOrder.setAdapter(adapter);
            tabOrder.setupWithViewPager(vpOrder);
            tabOrder.setTabMode(TabLayout.MODE_FIXED);
            setIndicator(this, tabOrder, 40, 40);
        } else {
            if (adapter.getFramentList().size() > 0) {
                for (BaseOrderFragment fragment : adapter.getFramentList()) {
                    //刷新订单状态和订单详情页面
                    fragment.notifyOrderInfo(bean);
                }
            }
        }

        //关闭动画
        handler.sendEmptyMessageDelayed(1, 600);
    }


    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {

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

            int left = (int) (getDisplayMetrics(context).density * leftDip);
            int right = (int) (getDisplayMetrics(context).density * rightDip);

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


    public OrderInfoBean getActivityData() {
        return bean;
    }

    @Override
    public void onBackClick() {
        this.finish();
    }

    @Override
    public void onMenuClick() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onLoadData() {
        //获取订单详情
        loadData();
    }
}

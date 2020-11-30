package com.newsuper.t.consumer.function.person.activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.function.person.adapter.DepositFragmentAdapter;
import com.xunjoy.lewaimai.consumer.function.person.fragment.DepositHistoryAllFragment;
import com.xunjoy.lewaimai.consumer.function.person.fragment.DepositHistoryPayFragment;
import com.xunjoy.lewaimai.consumer.function.person.fragment.DepositHistoryRefundFragment;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.advertisment.adapter.AdPagerAdapter;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositHistoryActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tab_deposit_history)
    TabLayout tab;
    @BindView(R.id.vp_deposit_history)
    ViewPager vp;
    DepositFragmentAdapter adapter;

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_deposit_history_list);
        ButterKnife.bind(this);
        toolbar.setTitleText("押金明细记录");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        toolbar.setTvMenuVisibility(View.INVISIBLE);
        adapter = new DepositFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new DepositHistoryAllFragment());
        adapter.addFragment(new DepositHistoryPayFragment());
        adapter.addFragment(new DepositHistoryRefundFragment());
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
        tab.setTabMode(TabLayout.MODE_FIXED);
//        setIndicator(this, tab, 20, 20);
        tab.setTabIndicatorFullWidth(false);
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

//            int left = (int) (getDisplayMetrics(context).density * leftDip);
//            int right = (int) (getDisplayMetrics(context).density * rightDip);
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
}

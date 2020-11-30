package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/7/20 0020.
 * 图标导航
 */

public class WTabGridView extends LinearLayout implements ViewPager.OnPageChangeListener{
    private Context mContext;
    private ArrayList<View> views;
    private ArrayList<ImageView> points;
    private View mView ;
    private ViewPager viewPager;
    private LinearLayout llPonit;
    private TabViewPagerAdapter pagerAdapter;
    private int num;
    public WTabGridView(Context context) {
        super(context);
        initView(context);
    }
    public WTabGridView(Context context,int num) {
        super(context);
        this.num = num;
        initView(context);
    }

    public WTabGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public WTabGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context){
        this.mContext = context;
        if (num <= 5){
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_view_2,null);
        }else {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_view,null);
        }

        viewPager = (ViewPager)mView.findViewById(R.id.viewpager_tab);
        llPonit = (LinearLayout) mView.findViewById(R.id.ll_point);
        views = new ArrayList<>();
        points = new ArrayList<>();
        pagerAdapter = new TabViewPagerAdapter(views);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    public void setTabData(ArrayList<WTopBean.IconGuideData> list){

        if (list == null && list.size() == 0){
            return;
        }
        LogUtil.log("WTabGridView","list == "+list.size());
        int sum = list.size() / 10;
        if (list.size() % 10 != 0){
            sum++;
        }
        LogUtil.log("WTabGridView","sum == "+sum);
        for (int i = 0;i < sum ;i++){
            ArrayList<WTopBean.IconGuideData> list1 = new ArrayList<>();
            for (int j = i * 10 ; j < list.size(); j++){
               list1.add(list.get(j));
                if (j == ( (i + 1) * 10 ) - 1){
                    break;
                }
           }
            addItemData(sum ,list1,i);
        }
        pagerAdapter.notifyDataSetChanged();
        addView(mView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void addItemData(int sum, final ArrayList<WTopBean.IconGuideData> list1, int i){
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_viewpager_tab,null);
        WGridView gridView = (WGridView)view.findViewById(R.id.wgrid_view);
        gridView.setAdapter(new WTabGridViewAdapter(mContext,list1));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tabClickListener != null){
                    WTopBean.IconGuideData guideData = list1.get(position);
                    tabClickListener.onTabClick(guideData);

                }
            }
        });
        views.add(view);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.circle_normal);
        LayoutParams params = new LayoutParams(
                UIUtils.dip2px(7), UIUtils.dip2px(7));
        params.rightMargin =  UIUtils.dip2px(6);
        if (sum > 1){
            if (i == 0){
                imageView.setImageResource(R.mipmap.circle_select);
            }
            llPonit.addView(imageView, params);
        }else {
            llPonit.setVisibility(GONE);
        }
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0;i < llPonit.getChildCount();i++){
            if ( i == position){
                ((ImageView)llPonit.getChildAt(i)).setImageResource(R.mipmap.circle_select);
            }else {
                ((ImageView)llPonit.getChildAt(i)).setImageResource(R.mipmap.circle_normal);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class TabViewPagerAdapter extends PagerAdapter {
        private ArrayList<View> list;

        public TabViewPagerAdapter(ArrayList<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = list.get(position);
            container.addView(view,LayoutParams.MATCH_PARENT,view.getHeight());
            return list.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    private TabClickListener tabClickListener;

    public void setTabClickListener(TabClickListener tabClickListener) {
        this.tabClickListener = tabClickListener;
    }

    public interface TabClickListener{
        void onTabClick(WTopBean.BaseData baseData);
    }

    private ViewGroup mPtrLayout;
    private ListView mListView;

    public void setmListView(ListView mListView) {
        this.mListView = mListView;
    }

    public void setmPtrLayout(ViewGroup mPtrLayout) {
        this.mPtrLayout = mPtrLayout;
    }

    int mDownY,mDownX;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mListView != null){
                    mListView.requestDisallowInterceptTouchEvent(true);
                }
                if (mPtrLayout != null){
                    mPtrLayout.setEnabled(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                //ViewPager滑动
                if (Math.abs(moveX-mDownX) > Math.abs(moveY-mDownY)) {
                    if (mListView != null){
                        mListView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (mPtrLayout != null){
                        mPtrLayout.setEnabled(false);
                    }
                    //ListView滑动
                }else {
                    if (mListView != null){
                        mListView.requestDisallowInterceptTouchEvent(false);
                    }
                    if (mPtrLayout != null){
                        mPtrLayout.setEnabled(true);
                    }
                }
                mDownX = moveX;
                mDownY = moveY;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mPtrLayout != null){
                    mPtrLayout.setEnabled(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

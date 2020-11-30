package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.top.adapter.HShopRecyclerAdapter;
import com.newsuper.t.consumer.utils.LogUtil;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/8/2 0002.
 * 店铺优选
 */

public class ShopSelectView extends LinearLayout {
    private HShopRecyclerAdapter hShopRecyclerAdapter;
    private ArrayList<WTopBean.ShopSelect> shopSelects = new ArrayList<>();
    private RecyclerView recyclerView;
    public ShopSelectView(Context context) {
        super(context);
        initView(context);
    }

    public ShopSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ShopSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_wei_sjop_select, null);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        hShopRecyclerAdapter = new HShopRecyclerAdapter(context,shopSelects);
        recyclerView.setAdapter(hShopRecyclerAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        addView(view, new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
    }
    public void setShopSelectData(ArrayList<WTopBean.ShopSelect> list){
        if (list != null && list.size() > 0){
            LogUtil.log("HShopRecyclerAdapter","list == add ");
            shopSelects.clear();
            shopSelects.addAll(list);
            hShopRecyclerAdapter.notifyDataSetChanged();

        }

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

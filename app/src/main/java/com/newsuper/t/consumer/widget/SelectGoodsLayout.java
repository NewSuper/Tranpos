package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.utils.LogUtil;


//店铺首页
public class SelectGoodsLayout extends RelativeLayout {
    private Context context;
    // 头部 View
    private LinearLayout headerView;
    //店铺信息View
    private LinearLayout shopView;
    //商品View
    private LinearLayout goodsView;
    // 底部 View
    private ClickableFrameLayout bottomView;

    private LinearLayout llActInfo;
    private LinearLayout llActTip;


    //toolbarView 高度
    private int headerHeight;

    //shopView 高度
    private int shopHeight;
    /**
     * 滑动最大距离
     */
    private int maxScrollHeight;

    /**
     * 最大拖动比率(最大高度/Header高度)
     */
    private float mHeaderMaxDragRate = 2.5f;

    /**
     * 状态 0 ： 完全打开 1：关闭 2：打开中
     */
    private int shopStatus = 0;

    //当前滑动的高度
    private int currenrScrollHeight;

    //触点坐标
    private float mTouchX;
    private float mTouchY;

    //触点坐标
    private float aTouchX;
    private float aTouchY;

    //触点坐标
    private float mlastX;
    private float mlastY;

    //是否拦截事件
    private boolean isIntercept = false;

    //判断子View是否滚动到顶部
    private boolean isChildViewScrollTop = true;

    public SelectGoodsLayout(Context context) {
        super(context);
        this.context = context;
    }

    public SelectGoodsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SelectGoodsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView(){
        headerView = findViewById(R.id.rl_top);
        shopView = findViewById(R.id.ll_shop);
        goodsView = findViewById(R.id.ll_goods);
        bottomView = findViewById(R.id.fl_bottom);
        llActInfo = findViewById(R.id.ll_act_info);
        llActTip = findViewById(R.id.ll_act_tip);

    }

    View childView;
    public void setChildView(View childView){
        this.childView = childView;
    }
    public boolean getChildViewScrollTop(){

        boolean b = true;
        if (childView != null){
            if (childView instanceof RecyclerView){
                b = childView.canScrollVertically(-1) ? false:true;
                LogUtil.log("getChildViewScrollTop", "RecyclerView   == "+b);
            }else if (childView instanceof NestedScrollView){
                int scrollY = childView.getScrollY();
                b =  scrollY > 0 ? false:true;
                LogUtil.log("getChildViewScrollTop", "NestedScrollView   == "+b);
            }
        }

        return b;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isIntercept){
            return isIntercept;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        isIntercept = false;
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                headerHeight = headerView.getMeasuredHeight();
                shopHeight = shopView.getMeasuredHeight();
                maxScrollHeight = shopHeight - headerHeight;
                LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_DOWN  "+maxScrollHeight);
                mTouchX = ev.getX();
                mTouchY = ev.getY();
                mlastX = mTouchX;
                mlastY = mTouchY;
                currenrScrollHeight = shopView.getTop();

                openHeight = hasScrollHeight;
                if (shopStatus == 0){
                    //记住打开的坐标 用于操作活动view
                    aTouchX = ev.getX();
                    aTouchY = ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_MOVE  shopStatus == "+shopStatus);
                float x = ev.getX();
                float y = ev.getY();
                int scrollX = (int) (x - mTouchX);
                int scrollY = (int) (y - mTouchY);
                LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_MOVE scrollY == "+scrollY);
                if (Math.abs(scrollX) < Math.abs(scrollY)){
                    //如果是打开状态
                    if (shopStatus == 0 ){
//                        LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_MOVE 1");
                        //向上滑动
                        if (mlastY > y){
                            LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_MOVE UP");
                            changeShopViewHeight(scrollY,ev);
                        }else {
                            LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_MOVE down");
                            mTouchX = ev.getX();
                            mTouchY = ev.getY();
                            currenrScrollHeight = 0;
                        }

                        int aScrollY = (int)(y - aTouchY);
                        //shopView全部打开时，继续下拉打开活动信息view
                        openingView(aScrollY);
                        isIntercept = true;
                        return true;
                    }
                    //打开中
                    else if (shopStatus == 2){
//                        LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_MOVE 3");
                        changeShopViewHeight(scrollY,ev);
                        isIntercept = true;
                        return true;
                    }
                    //关闭
                    else if (shopStatus == 1){
//                        LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_MOVE 4");
                        //向下滑动,子View滑动到顶部才能向下拉出shopView
                        if (mlastY < y ){
                            if (getChildViewScrollTop()){
                                LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_MOVE UP");
                                changeShopViewHeight(scrollY,ev);
                                isIntercept = true;
                                return true;
                            }else {
                                mTouchX = ev.getX();
                                mTouchY = ev.getY();
                                currenrScrollHeight = shopView.getTop();
                            }

                        }else {
                            LogUtil.log("TouchEventLog", "dispatchTouchEvent  ACTION_MOVE down");
                            mTouchX = ev.getX();
                            mTouchY = ev.getY();
                            currenrScrollHeight = shopView.getTop();
                        }

                    }
                }
                mlastX = x;
                mlastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                openHeight = 0;
                if (OPEN_STATUS != 1){
                    if (hasScrollHeight > (llActTip.getMeasuredHeight() * 2)) {
                        openActView();
                    } else {
                        closeActView();
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void changeShopViewHeight(int scroll,MotionEvent ev){
        //活动view未关闭，禁止操作shopView
        if (OPEN_STATUS != 1){
            //记录坐标、用于活动view关闭时操作shopView
            mTouchX = ev.getX();
            mTouchY = ev.getY();
            return;
        }
        LogUtil.log("TouchEventLog", "dispatchTouchEvent  shopStatus = "+shopStatus);
        LogUtil.log("TouchEventLog", "dispatchTouchEvent  scroll = "+scroll);
        LayoutParams layoutParams = (LayoutParams) shopView.getLayoutParams();
        int h = currenrScrollHeight + scroll;
        if (h > 0) {
            h = 0;
        }
        if (Math.abs(h) > maxScrollHeight){
            h = - maxScrollHeight;
        }
        LogUtil.log("TouchEventLog", "ShopViewHeight  == "+h);

        layoutParams.topMargin = h;
        shopView.setLayoutParams(layoutParams);
        shopView.invalidate();

        changeTopBgColor(h);

        //获取到layoutParams然后改变属性，在设置回去
        if ((shopView.getBottom() - headerView.getMeasuredHeight()) <= 0) {
            shopStatus = 1;//关闭
        }else if (shopView.getTop() >= 0) {
            //完全打开
            shopStatus = 0;

            //记住打开的坐标 用于操作活动view
            aTouchX = ev.getX();
            aTouchY = ev.getY();
        }else {
            shopStatus = 2;//正在打开
        }
    }

    private void changeTopBgColor(int scroll) {
        int sc = Math.abs(scroll);
        if (sc > 255) {
            sc = 255;
        }
        headerView.setBackgroundColor(Color.argb( sc, 251, 121, 123));
    }


    int openHeight, hasScrollHeight;
    boolean isOpening;
    /**
     * 状态 0 ： 完全打开 1：关闭 2：打开中
     */
    int OPEN_STATUS = 1;

    float speed = 0.75f;

    //正在打开
    private void openingView(int scroll) {
        //完全打开状态禁止操作
        if (OPEN_STATUS == 0) {
            return;
        }
        int height = openHeight + (int) (scroll * speed);
        hasScrollHeight = height;//记录本次滑动的距离
        LogUtil.log("openActView", "openHeight ==   " + openHeight + " scroll =" + scroll + " hasScrollHeight = " + height);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llActInfo.getLayoutParams();
        if (height <= (llActTip.getMeasuredHeight() * 1.5)) {
            isOpening = false;
            llActTip.setVisibility(View.VISIBLE);
            float heightPixels = getResources().getDisplayMetrics().heightPixels;
            float alpha = 1 - height / (heightPixels / 3);
            llActTip.setAlpha(alpha);
            layoutParams.height = 1;
            llActInfo.setLayoutParams(layoutParams);
            llActInfo.invalidate();
            OPEN_STATUS = 1;
        } else {
            llActTip.setVisibility(View.GONE);
            LogUtil.log("openActView", "height ==   " + height);
            layoutParams.height = height;
            llActInfo.setLayoutParams(layoutParams);
            llActInfo.invalidate();
            OPEN_STATUS = 2;
        }
    }

    //close act view
    public void closeActView() {
        OPEN_STATUS = 1;
        isOpening = false;
        hasScrollHeight = 0;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llActInfo.getLayoutParams();
        layoutParams.height = 1;
        llActInfo.setLayoutParams(layoutParams);
        llActInfo.invalidate();
        setBackgroundColor(ContextCompat.getColor(context, R.color.white_f5));
        llActTip.setVisibility(View.VISIBLE);
        llActTip.setAlpha(1);
        bottomView.setVisibility(View.VISIBLE);

    }

    //open act view
    public void openActView() {
        llActTip.setVisibility(View.GONE);
        bottomView.setVisibility(View.GONE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llActInfo.getLayoutParams();
        OPEN_STATUS = 0;
        isOpening = true;
        hasScrollHeight = 0;
        layoutParams.height = 0;
        llActInfo.setLayoutParams(layoutParams);
        llActInfo.invalidate();
        setBackgroundColor(ContextCompat.getColor(context, R.color.text_color_99));
    }

}

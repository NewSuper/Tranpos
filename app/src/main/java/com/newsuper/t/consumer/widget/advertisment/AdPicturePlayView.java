package com.newsuper.t.consumer.widget.advertisment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.advertisment.adapter.AdPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;



/**
 * Created by Administrator on 2017/4/28 0028.
 * 广告轮播
 */

public class AdPicturePlayView extends LinearLayout {
    private Context mContext;
    private AutoScrollViewPager ad_viewpager;
    private LinearLayout linearLayout_circle_img;
    private ArrayList<String> url_list;
    private ArrayList<ImageView> imageViews;
    private ArrayList<View> views;
    private AdPagerAdapter adapter;
    private int mode = 0;
    private int viewHeight,viewWidth;
    private  View view ;
    public AdPicturePlayView(Context context, int mode) {
        super(context);
        this.mContext = context;
        this.mode = mode;
        initView();
    }
    public AdPicturePlayView(Context context, int mode,int viewHeight) {
        super(context);
        this.mContext = context;
        this.mode = mode;
        this.viewHeight = viewHeight;
        initView();
    }
    @SuppressLint("NewApi")
    public AdPicturePlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }
    public AdPicturePlayView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }
    public AdPicturePlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AdPicturePlayView);
        this.mode = ta.getInteger(R.styleable.AdPicturePlayView_mode,0);
        initView();
    }
    public void setViewHeight(int height){
        if (height > 0){
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            addView(view,params);
        }else {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
            addView(view,params);
        }
    }
    private void initView(){
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_ad_play_wei,null);
        ad_viewpager = (AutoScrollViewPager) view.findViewById(R.id.ad_viewpager);
        //自动循环
        ad_viewpager.setCycle(false);
        //间隔时间
        ad_viewpager.setInterval(4000);
        ad_viewpager.setOffscreenPageLimit(1);
        //触摸时不切换
        ad_viewpager.setStopScrollWhenTouch(true);
        linearLayout_circle_img = (LinearLayout)view.findViewById(R.id.linearLayout_circle_img);
        url_list = new ArrayList<String>();
        imageViews = new ArrayList<ImageView>();
        views = new ArrayList<>();
        ad_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (linearLayout_circle_img.getChildCount() == 0){
                    return;
                }
                position = position % linearLayout_circle_img.getChildCount();
                if (position < 0){
                    position = position + linearLayout_circle_img.getChildCount();
                }
                for (int i = 0;i < linearLayout_circle_img.getChildCount();i++){
                    if (i == position){
                        ((ImageView)linearLayout_circle_img.getChildAt(i)).setImageResource(R.mipmap.yuan_dian_select);
                    }else {
                        ((ImageView)linearLayout_circle_img.getChildAt(i)).setImageResource(R.mipmap.yuan_dian_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 添加圆点
     * @param list
     */
    public void setImageUrlList(final ArrayList<String> list){
        if (list.size() > 0){
            String url = list.get(0);
            if (!url.startsWith("http")){
                url =   RetrofitManager.BASE_IMG_URL + url;
            }
            Glide.with(mContext).load(url).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                    LogUtil.log("getViewHeight", "onResourceReady  load");
                    if (bitmap == null){
                        return;
                    }
                    int height = bitmap.getHeight(); //px
                    int width = bitmap.getWidth(); //px
                    final float cbi = ((float)height) / ((float) width);
                    viewWidth = UIUtils.getWindowWidth() - (pageSpace * 2);
                    viewHeight = (int)( viewWidth * cbi);
                    setViewHeight(viewHeight);
                    showImageViewList(list);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                }
            });
        }
    }
    boolean radioVal;
    int space,pageSpace;

    public void setPageSpace(int pageSpace) {
        this.pageSpace = UIUtils.dip2px(pageSpace);
        LogUtil.log("AdPicturePlayView", "setPageSpace  == "+this.pageSpace+"  pageSpace == "+pageSpace);

    }

    public void setRadioVal(boolean radioVal) {
        this.radioVal = !radioVal;
        LogUtil.log("AdPicturePlayView", "setPageSpace  == "+this.radioVal);
    }

    public void setSpace(int space) {
        this.space = UIUtils.dip2px(space);
        LogUtil.log("AdPicturePlayView", "setSpace  == "+this.space+"  space == "+space);
        setPadding(space,0,space,0);
    }

    /**
     * 添加圆点
     * @param datas
     */
    public void setLunboImageData(final ArrayList<WTopBean.LunboImageData> datas, final PlayViewOnClickListener playViewOnClickListener){
        if (datas != null && datas.size() > 0){
            String url = datas.get(0).image;
            countWidthHeight(url,datas,playViewOnClickListener);
        }
    }
    private void showImageViewList(ArrayList<String> list){

        if (list != null && list.size() > 0){
            boolean isDouble = false;
            if (list.size() == 2){
                list.add(list.get(0));
                list.add(list.get(1));
                isDouble = true;
            }
            url_list.clear();
            url_list.addAll(list);
            linearLayout_circle_img.removeAllViews();
            imageViews.clear();
            for (int i = 0;i < list.size();i++){
                if (list.size() > 1){
                    if (isDouble){
                        if (i < 2){
                            ImageView imageView = new ImageView(mContext);
                            imageView.setImageResource(R.mipmap.yuan_dian_normal);
                            if (i == 0){
                                imageView.setImageResource(R.mipmap.yuan_dian_select);
                            }
                            LayoutParams params = new LayoutParams(
                                    UIUtils.dip2px(7),UIUtils.dip2px(7));
                            params.rightMargin =  UIUtils.dip2px(6);
                            linearLayout_circle_img.addView(imageView, params);
                        }
                    }else {
                        ImageView imageView = new ImageView(mContext);
                        imageView.setImageResource(R.mipmap.yuan_dian_normal);
                        if (i == 0){
                            imageView.setImageResource(R.mipmap.yuan_dian_select);
                        }
                        LayoutParams params = new LayoutParams(
                                UIUtils.dip2px(7),UIUtils.dip2px(7));
                        params.rightMargin =  UIUtils.dip2px(6);
                        linearLayout_circle_img.addView(imageView, params);
                    }
                }
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_viewpager_ad,null);
                RoundedImageView ad = (RoundedImageView)view.findViewById(R.id.iv_ad);
                if (radioVal){
                    ad.setCornerRadius(10);
                }
                String url = list.get(i);
                loadImageView3(url,ad);
                imageViews.add(ad);
                views.add(view);
            }
        }
        if (imageViews.size() > 0){
            adapter = new AdPagerAdapter(mContext,views);
            ad_viewpager.setAdapter(adapter);
            if (imageViews.size() > 1){
                ad_viewpager.startAutoScroll();
            }
        }
    }
    private void showImageView(ArrayList<WTopBean.LunboImageData> datas,final PlayViewOnClickListener playViewOnClickListener){
        final ArrayList<String> list = new ArrayList<>();
        for (WTopBean.LunboImageData lunboImageData : datas) {
            list.add(lunboImageData.image);
        }
        if (list != null && list.size() > 0){
            boolean isDouble = false;
            if (list.size() == 2){
                list.add(list.get(0));
                list.add(list.get(1));
                datas.add(datas.get(0));
                datas.add(datas.get(1));
                isDouble = true;
            }
            url_list.clear();
            url_list.addAll(list);
            linearLayout_circle_img.removeAllViews();
            imageViews.clear();
            for (int i = 0;i < list.size();i++){
                if (list.size() > 1){
                    if (isDouble){
                        if (i < 2){
                            ImageView imageView = new ImageView(mContext);
                            imageView.setImageResource(R.mipmap.yuan_dian_normal);
                            if (i == 0){
                                imageView.setImageResource(R.mipmap.yuan_dian_select);
                            }
                            LayoutParams params = new LayoutParams(
                                    UIUtils.dip2px(7),UIUtils.dip2px(7));
                            params.rightMargin =  UIUtils.dip2px(6);
                            linearLayout_circle_img.addView(imageView, params);
                        }
                    }else {
                        ImageView imageView = new ImageView(mContext);
                        imageView.setImageResource(R.mipmap.yuan_dian_normal);
                        if (i == 0){
                            imageView.setImageResource(R.mipmap.yuan_dian_select);
                        }
                        LayoutParams params = new LayoutParams(
                                UIUtils.dip2px(7),UIUtils.dip2px(7));
                        params.rightMargin =  UIUtils.dip2px(6);
                        linearLayout_circle_img.addView(imageView, params);
                    }

                }
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_viewpager_ad,null);
                RoundedImageView ad = (RoundedImageView)view.findViewById(R.id.iv_ad);
                if (radioVal){
                    ad.setCornerRadius(10);
                }
                String url = list.get(i);
                loadImageView3(url,ad);
                final WTopBean.LunboImageData lunboImageData = datas.get(i);
                ad.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (playViewOnClickListener != null){
                            playViewOnClickListener.onClick(lunboImageData);
                        }
                    }
                });
                imageViews.add(ad);
                views.add(view);
            }
        }
        if (imageViews.size() > 0){
            adapter = new AdPagerAdapter(mContext,views);
            ad_viewpager.setAdapter(adapter);
            if (imageViews.size() > 1){
                ad_viewpager.startAutoScroll();
            }
        }
    }

    private void loadImageView3(String url, final ImageView imageView ){
        imageView.getLayoutParams().width = viewWidth;
        imageView.getLayoutParams().height = viewHeight;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (StringUtils.isEmpty(url)){
            imageView.setImageResource(R.mipmap.common_def_food);
        }else {
            if (!url.startsWith("http")){
                url =   RetrofitManager.BASE_IMG_URL + url;
            }
            LogUtil.log("getViewHeight", "loadImageView  == "+ url);
            RequestOptions requestOptions = new RequestOptions()
//                    .placeholder(R.mipmap.common_def_food)
                    .error(R.mipmap.common_def_food);
            Glide.with(mContext).load(url).apply(requestOptions).into(imageView);
        }

    }
    private void countWidthHeight(String url,final ArrayList<WTopBean.LunboImageData> datas,final PlayViewOnClickListener playViewOnClickListener){
        if (!url.startsWith("http")){
            url =   RetrofitManager.BASE_IMG_URL + url;
        }
        Glide.with(mContext).load(url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                LogUtil.log("getViewHeight", "onResourceReady  load");
                if (bitmap == null){
                    return;
                }
                int height = bitmap.getHeight(); //px
                int width = bitmap.getWidth(); //px
                final float cbi = ((float)height) / ((float) width);
                 viewWidth = UIUtils.getWindowWidth() - (pageSpace * 2);
                 viewHeight = (int)( viewWidth * cbi);
                 setViewHeight(viewHeight);
                 showImageView(datas,playViewOnClickListener);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
            }
        });
    }


    public interface PlayViewOnClickListener{
        void onClick(WTopBean.LunboImageData imageData);
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
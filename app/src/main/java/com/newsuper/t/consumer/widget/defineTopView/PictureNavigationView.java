package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.makeramen.roundedimageview.RoundedImageView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.WShopCart2;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IWShopCart;
import com.newsuper.t.consumer.function.top.adapter.PictureRecycleAdapter;
import com.newsuper.t.consumer.function.top.adapter.PictureViewPagerAdapter;
import com.newsuper.t.consumer.function.top.adapter.WPictureBigAdapter;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import com.newsuper.t.consumer.widget.ListViewForScrollView;
import com.newsuper.t.consumer.widget.advertisment.AutoScrollViewPager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2018/8/11 0011.
 */

public class PictureNavigationView extends LinearLayout {
    public static final int STYLE_VIEWPAGER = 1;
    public static final int STYLE_LISTVIEW = 2;
    public static final int STYLE_RECYCERVIEW_SMALL = 3;
    public static final int STYLE_RECYCERVIEW_BIG = 4;
    public static final int STYLE_GRIDVIEW = 5;
    private WGridView gridView;
    private ListViewForScrollView listView;
    private AutoScrollViewPager viewPager;
    private RecyclerView recyclerView;
    private WTopBean.PictureAdvertismentData data;
    private LinearLayout ll_point;
    private Context context;
    private  int showStyle = 1;
    View view = null;
    public PictureNavigationView(Context context) {
        super(context);
        intiView(context);
    }
    public PictureNavigationView(Context context,int showStyle) {
        super(context);
        this.showStyle = showStyle;
        intiView(context);
    }
    public PictureNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intiView(context);
    }

    public PictureNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView(context);
    }
    boolean radioVal;
    int space,pageSpace;

    public void setPageSpace(int pageSpace) {
        this.pageSpace = pageSpace;
        LogUtil.log("PictureNavigationView", showStyle + "setPageSpace  == "+this.pageSpace+"  pageSpace == "+pageSpace);
        view.setPadding(pageSpace,pageSpace,pageSpace,pageSpace);
    }

    public void setRadioVal(boolean radioVal) {
        this.radioVal = !radioVal;
        LogUtil.log("PictureNavigationView", showStyle + "setRadioVal  == "+this.radioVal);
    }

    public void setSpace(int space) {
        this.space = space;
        LogUtil.log("PictureNavigationView", showStyle + "setSpace  == "+this.space+"  space == "+space);
    }

    private void intiView(Context context){
        this.context = context;
        LogUtil.log("PictureNavigationView","showStyle == "+showStyle);
     if (showStyle == STYLE_GRIDVIEW){
         view = LayoutInflater.from(context).inflate(R.layout.layout_picture_navigation_gridview,null);
         gridView = (WGridView)view.findViewById(R.id.wgrid_view);
     } else if (showStyle == STYLE_LISTVIEW){
         view = LayoutInflater.from(context).inflate(R.layout.layout_picture_navigation_listview,null);
         listView = (ListViewForScrollView)view.findViewById(R.id.listview);

     } else if (showStyle == STYLE_RECYCERVIEW_BIG){
         view = LayoutInflater.from(context).inflate(R.layout.layout_picture_navigation_recycleview,null);
         recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
     } else if (showStyle == STYLE_RECYCERVIEW_SMALL){
         view = LayoutInflater.from(context).inflate(R.layout.layout_picture_navigation_recycleview,null);
         recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
     } else if (showStyle == STYLE_VIEWPAGER){
         view = LayoutInflater.from(context).inflate(R.layout.layout_picture_navigation_viewpager,null);
         viewPager = (AutoScrollViewPager)view.findViewById(R.id.viewpager);
         ll_point = (LinearLayout)view.findViewById(R.id.ll_point);
         //自动循环
         viewPager.setCycle(true);
         //间隔时间
         viewPager.setInterval(4000);
         viewPager.setOffscreenPageLimit(1);
         //触摸时不切换
         viewPager.setStopScrollWhenTouch(false);
         viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(int position) {
                 if (ll_point.getChildCount() == 0){
                     return;
                 }
                 position = position % ll_point.getChildCount();
                 if (position < 0){
                     position = position + ll_point.getChildCount();
                 }
                 for (int i = 0;i < ll_point.getChildCount();i++){
                     if (i == position){
                         ((ImageView)ll_point.getChildAt(i)).setImageResource(R.mipmap.yuan_dian_select);
                     }else {
                         ((ImageView)ll_point.getChildAt(i)).setImageResource(R.mipmap.yuan_dian_normal);
                     }
                 }
             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });
     }
    }

    public void setData(final WTopBean.PictureAdvertismentData data){
        if (data != null && data.list != null && data.list.size() > 0){
            int height = FormatUtil.numInteger(data.list.get(0).height); //px
            int width = FormatUtil.numInteger(data.list.get(0).width); //px
            LogUtil.log("PictureNavigationView","setData ==   showStyle  "+showStyle);
            final float cbi = ((float)height) / ((float) width);
            int mWidth = UIUtils.getWindowWidth();
            int mHeight = (int)( mWidth * cbi);
            showData(mWidth,mHeight,width,height,data);
        }

    }
    private void showData(int mWidth,int mHeight,int width,int height,final WTopBean.PictureAdvertismentData data){
        if (showStyle == STYLE_GRIDVIEW){
            final float cbi = ((float)height) / ((float) width);
            int w = mWidth / 2;
            int h = (int)( w * cbi);

            WPictureBigAdapter adapter = new WPictureBigAdapter(context,data.list,w,h,radioVal);
            gridView.setHorizontalSpacing(space);
            gridView.setVerticalSpacing(space);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (viewOnClickListener != null){
                        viewOnClickListener.OnClick(data.list.get(position));
                    }
                }
            });
            addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        } else if (showStyle == STYLE_LISTVIEW){
            WPictureBigAdapter adapter = new WPictureBigAdapter(context,data.list,mWidth,mHeight,radioVal);
            listView.setDividerHeight(space);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (viewOnClickListener != null){
                        viewOnClickListener.OnClick(data.list.get(position));
                    }
                }
            });

            addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        } else if (showStyle == STYLE_RECYCERVIEW_BIG){
            final float cbi = ((float)height) / ((float) width);
//            int w = mWidth * 3 / 4;
            int w = 670;
            int h = (int)( w * cbi);
            PictureRecycleAdapter adapter = new PictureRecycleAdapter(context,data.list,2,w,h,radioVal,space);
            adapter.setItemOnClickListener(new PictureRecycleAdapter.AdapterItemOnClickListener() {
                @Override
                public void OnItemOnCliked(WTopBean.PictureAdvertisment advertisment) {
                    if (viewOnClickListener != null){
                        viewOnClickListener.OnClick(advertisment);
                    }
                }
            });
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        } else if (showStyle == STYLE_RECYCERVIEW_SMALL){
            final float cbi = ((float)height) / ((float) width);
            int w = 305;
            int h = (int)( w * cbi);
            PictureRecycleAdapter adapter = new PictureRecycleAdapter(context,data.list,1,w,h,radioVal,space);
            adapter.setItemOnClickListener(new PictureRecycleAdapter.AdapterItemOnClickListener() {
                @Override
                public void OnItemOnCliked(WTopBean.PictureAdvertisment advertisment) {
                    if (viewOnClickListener != null){
                        viewOnClickListener.OnClick(advertisment);
                    }
                }
            });
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        } else if (showStyle == STYLE_VIEWPAGER){
            ArrayList<View> views = new ArrayList<>();
            for (int i = 0; i < data.list.size();i++){
                final WTopBean.PictureAdvertisment advertisment = data.list.get(i);
                View view = LayoutInflater.from(context).inflate(R.layout.adapter_wei_picture_ad, null);
                RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.iv_ad);
                TextView textView = (TextView)view.findViewById(R.id.tv_title);
                if (StringUtils.isEmpty(advertisment.title)){
                    textView.setVisibility(GONE);
                }else {
                    if (!StringUtils.isEmpty(advertisment.text_color) && advertisment.text_color.startsWith("#")
                            && (advertisment.text_color.length() == 7 || advertisment.text_color.length() == 9 )){
                        textView.setTextColor(Color.parseColor(advertisment.text_color));
                    }else {
                        textView.setTextColor(Color.parseColor("#ffffff"));
                    }
                    textView.setText(advertisment.title);
                    textView.setVisibility(VISIBLE);
                    if (radioVal){
                        imageView.setCornerRadius(10);
                        textView.setBackgroundResource(R.drawable.shape_pic_bottom_corner);
                    }else {
                        textView.setBackgroundColor(Color.parseColor("#99000000"));
                    }
                }
                String url = advertisment.image;
                if (!url.startsWith("http")){
                    url = RetrofitManager.BASE_IMG_URL_BIG + url;
                }
                imageView.getLayoutParams().height = mHeight;
                imageView.getLayoutParams().width = mWidth;

                Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(imageView);
//                UIUtils.glideAppLoadCorner(context,url,R.mipmap.store_logo_default,imageView,radioVal);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewOnClickListener != null){
                            viewOnClickListener.OnClick(advertisment);
                        }
                    }
                });
                views.add(view);

                ImageView ponitView = new ImageView(context);
                ponitView.setImageResource(R.mipmap.yuan_dian_normal);
                if (i == 0){
                    ponitView.setImageResource(R.mipmap.yuan_dian_select);
                }
                LayoutParams params = new LayoutParams(
                        UIUtils.dip2px(7),UIUtils.dip2px(7));
                params.rightMargin =  UIUtils.dip2px(6);
                ll_point.addView(ponitView, params);
            }
            LogUtil.log("PictureNavigationView","views == "+views.size());
            PictureViewPagerAdapter adapter = new PictureViewPagerAdapter(context,views);
            viewPager.setAdapter(adapter);
            viewPager.getLayoutParams().height = mHeight;
            viewPager.startAutoScroll();
            addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }


    ViewOnClickListener viewOnClickListener;

    public void setViewOnClickListener(ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;
    }

    public interface ViewOnClickListener{
        void OnClick(WTopBean.PictureAdvertisment advertisment);
    }

}

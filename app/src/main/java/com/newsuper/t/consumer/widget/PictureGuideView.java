package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.top.adapter.PictureGuideAdapter;
import com.newsuper.t.consumer.function.top.adapter.PictureGuideScrollAdapter;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.defineTopView.WGridView;


public class PictureGuideView extends LinearLayout {
    private Context context;
    private WGridView gridView;
    private RecyclerView recyclerView;
    private View view;
    private String style;
    public PictureGuideView(Context context) {
        super(context);
        intiView(context);
    }
    public PictureGuideView(Context context,String style) {
        super(context);
        this.style = style;
        intiView(context);
    }
    public PictureGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intiView(context);
    }

    public PictureGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView(context);
    }
    private void intiView(Context context) {
        this.context = context;
        LogUtil.log("PictureGuideView", "init == "+style);
        if ("1".equals(style)){
            view = LayoutInflater.from(context).inflate(R.layout.layout_picture_guide, null);
            gridView = (WGridView) view.findViewById(R.id.gridview);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.layout_picture_guide_2, null);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        }
    }
    boolean radioVal;
    int space,pageSpace;

    public void setPageSpace(int pageSpace) {
        this.pageSpace = pageSpace;
        LogUtil.log("PictureGuideView", "setPageSpace  == "+this.pageSpace+"  pageSpace == "+pageSpace);
        view.setPadding(pageSpace,0,pageSpace,0);
    }

    public void setRadioVal(boolean radioVal) {
        this.radioVal = !radioVal;
        LogUtil.log("PictureGuideView", "setPageSpace  == "+this.radioVal);
    }

    public void setSpace(int space) {
        this.space = space;

        LogUtil.log("PictureGuideView", "setSpace  == "+this.space+"  space == "+space);
    }
    public void setData(final WTopBean.PictureGuideData pictureGuideData ){
        LogUtil.log("PictureGuideView", "setData == ");
        if (pictureGuideData == null || pictureGuideData.list == null || pictureGuideData.list.size() == 0){
            return;
        }
        view.setBackgroundColor(UIUtils.getColor(pictureGuideData.bg_color));
        int height = FormatUtil.numInteger(pictureGuideData.list.get(0).height); //px
        int width = FormatUtil.numInteger(pictureGuideData.list.get(0).width); //px
        float cbi = ((float)height) / ((float) width);
        LogUtil.log("PictureGuideView", "bili== " + cbi);
        if ("1".equals(style)){
            int count = pictureGuideData.list.size();
            LogUtil.log("PictureGuideView", "getWindowWidth== " + UIUtils.getWindowWidth());
            int mWidth = (UIUtils.getWindowWidth() / count);
            int mHeight = (int)( mWidth * cbi);
            LogUtil.log("PictureGuideView", "mWidth== " + mWidth);
            LogUtil.log("PictureGuideView", "mHeight== " + mHeight);
            final PictureGuideAdapter guideAdapter = new PictureGuideAdapter(context,pictureGuideData.list,mHeight,radioVal,pictureGuideData.text_color);
            gridView.setNumColumns(pictureGuideData.list.size());
            gridView.setHorizontalSpacing(space);
            gridView.setAdapter(guideAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (guideListener != null){
                        guideListener.onPictureClick(position);
                    }
                }
            });
        }else {
            int mWidth = (int)(UIUtils.getWindowWidth() / 3)  - ( 3 * space);
            int mHeight = (int)( mWidth * cbi);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            PictureGuideScrollAdapter scrollAdapter = new PictureGuideScrollAdapter(context,pictureGuideData.list,mHeight,mWidth,space,radioVal,pictureGuideData.text_color);
            recyclerView.setAdapter(scrollAdapter);
            scrollAdapter.setGuideListener(guideListener);
        }
        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }
    PictureGuideListener guideListener;

    public void setGuideListener(PictureGuideListener guideListener) {
        this.guideListener = guideListener;
    }

    public interface PictureGuideListener{
        void onPictureClick(int position);
    }
}

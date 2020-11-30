package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;

public class WFooterView extends LinearLayout {
    public static int STATUS_LOAD_MORE = 101;
    public static int STATUS_LOAD_EMPTY = 102;
    public static int STATUS_FILTER_EMPTY = 103;
    public static int STATUS_LOGO = 104;
    public static WFooterView instance;
    private int status = 101;
    private Context context;
    private View rootView;
    private FooterViewHolder viewHolder;

    public int getStatus() {
        return status;
    }

    public WFooterView(Context context) {
        super(context);
        initView(context);
    }

    public WFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WFooterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(final Context context) {
        this.context = context;
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_filter_empty, null);
        viewHolder = new FooterViewHolder(rootView);
        addView(rootView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        instance = this;
    }
    public static WFooterView getInstance() {
        return instance;
    }
    public void setFooterViewStatus(int status){
        this.status = status;
        switch (status){
            case 101:
                viewHolder.tvLoadMore.setText("加载中...");
                viewHolder.tvLoadMore.setVisibility(VISIBLE);
                viewHolder.llFilterEmpty.setVisibility(GONE);
                viewHolder.llLogo.setVisibility(GONE);
                break;
            case 104:
                viewHolder.tvLoadMore.setVisibility(GONE);
                viewHolder.llFilterEmpty.setVisibility(GONE);
                viewHolder.llLogo.setVisibility(VISIBLE);
                viewHolder.vw_empty.setVisibility(GONE);
                break;
            case 103:
                viewHolder.tvLoadMore.setVisibility(GONE);
                viewHolder.llFilterEmpty.setVisibility(VISIBLE);
                viewHolder.llLogo.setVisibility(GONE);
                break;
            case 102:
                viewHolder.tvLoadMore.setVisibility(GONE);
                viewHolder.llFilterEmpty.setVisibility(GONE);
                viewHolder.llLogo.setVisibility(VISIBLE);
                viewHolder.vw_empty.setVisibility(VISIBLE);
                break;
        }
    }
    public void setListener(OnClickListener listener){
        viewHolder.tvReset.setOnClickListener(listener);
    }
    public void setFooterViewStatusTextValue(String s){
        viewHolder.tvLoadMore.setText(s);
        viewHolder.tvLoadMore.setVisibility(VISIBLE);
        viewHolder.llFilterEmpty.setVisibility(GONE);
        viewHolder.llLogo.setVisibility(GONE);
    }
    static class FooterViewHolder {
        @BindView(R.id.tv_load_more)
        TextView tvLoadMore;
        @BindView(R.id.ll_logo)
        LinearLayout llLogo;
        @BindView(R.id.tv_reset)
        TextView tvReset;
        @BindView(R.id.ll_filter_empty)
        LinearLayout llFilterEmpty;
        @BindView(R.id.vw_empty)
        View vw_empty;
        FooterViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setVwEmptyHight(int height) {
        if (viewHolder.vw_empty.getVisibility()==VISIBLE) {
            ViewGroup.LayoutParams layoutParams = viewHolder.vw_empty.getLayoutParams();
            layoutParams.height = height;
            viewHolder.vw_empty.setLayoutParams(layoutParams);
        }
    }
    public void setLLFilterEmptyMarginTop(int h) {
        if (viewHolder.llFilterEmpty.getVisibility()==VISIBLE) {
            LayoutParams layoutParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    ,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParam.setMargins(0,h,0,0);
            viewHolder.llFilterEmpty.setLayoutParams(layoutParam);
        }
    }
}

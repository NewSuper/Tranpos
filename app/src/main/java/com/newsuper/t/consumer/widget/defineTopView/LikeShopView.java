package com.newsuper.t.consumer.widget.defineTopView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.top.adapter.LikeShopAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LikeShopView extends LinearLayout {
    private Context context;
    private LikeViewHolder holder;
    public LikeShopView(Context context) {
        super(context);
        initView(context);
    }

    public LikeShopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LikeShopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @SuppressLint("NewApi")
    public LikeShopView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_you_like_shop, null);
        holder = new LikeViewHolder(view);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    }
    public void setData(String title, ArrayList<WTopBean.GuessLikeData> data, AdapterView.OnItemClickListener onItemClickListener){
        holder.tvTitle.setText(title);
        if (data == null && data.size() == 0){
            return;
        }
        LikeShopAdapter shopAdapter = new LikeShopAdapter(context,data);
        holder.gvShop.setAdapter(shopAdapter);
        holder.gvShop.setOnItemClickListener(onItemClickListener);
    }

    static class LikeViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.gv_shop)
        WGridView gvShop;

        LikeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

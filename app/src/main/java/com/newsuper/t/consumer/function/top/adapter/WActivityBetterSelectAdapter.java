package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.util.ArrayList;

public class WActivityBetterSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<WTopBean.ActivityData> data;
    private IGoodsToDetailPage iToGoodsDetailPage;


    public WActivityBetterSelectAdapter(Context mContext, ArrayList<WTopBean.ActivityData> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void setIToDetailPage(IGoodsToDetailPage iToGoodsDetailPage) {
        this.iToGoodsDetailPage = iToGoodsDetailPage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewGoods = LayoutInflater.from(mContext).inflate(R.layout.adapter_activity_select, parent, false);
        ActivityViewHolder goodsHolder = new ActivityViewHolder(viewGoods);
        return goodsHolder;
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_activity;
        public TextView tv_activity_name;
        public TextView tv_activity_des;
        public TextView tv_joy;
        public LinearLayout item_root;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            iv_activity = (ImageView) itemView.findViewById(R.id.iv_activity);
            tv_activity_name = (TextView) itemView.findViewById(R.id.tv_activity_name);
            tv_activity_des = (TextView) itemView.findViewById(R.id.tv_activity_des);
            tv_joy = (TextView) itemView.findViewById(R.id.tv_joy);
            item_root=(LinearLayout) itemView.findViewById(R.id.item_root);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ActivityViewHolder activityHolder = (ActivityViewHolder) holder;
        if (null!=activityHolder) {
        final WTopBean.ActivityData activityData=data.get(position);
        int color=0;
         switch (position%3){
             case 0:
                 color=Color.parseColor("#fecc51");
                 break;
             case 1:
                 color=Color.parseColor("#3EB1F0");
                 break;
             case 2:
                 color=Color.parseColor("#F87A7C");
                 break;
         }
        if(null!=activityData){
            activityHolder.tv_activity_name.setText(activityData.title);
            activityHolder.tv_activity_name.setTextColor(color);
            activityHolder.tv_activity_des.setText(activityData.secondtitle);
            activityHolder.tv_joy.setBackgroundColor(color);
            //加载网络图片
            UIUtils.glideAppLoad(mContext,activityData.image,R.mipmap.common_def_food,activityHolder.iv_activity);
            activityHolder.item_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activityClickListener != null){
                        activityClickListener.onActivityClick(activityData);
                    }
                }
            });

        }
      }
    }

    private ActivityClickListener activityClickListener;
    public void setActivityClickListener(ActivityClickListener activityClickListener) {
        this.activityClickListener = activityClickListener;
    }
    public interface ActivityClickListener{
        void onActivityClick(WTopBean.BaseData baseData);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    
}

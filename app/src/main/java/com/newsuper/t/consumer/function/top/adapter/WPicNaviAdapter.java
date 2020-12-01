package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.ArrayList;

public class WPicNaviAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<WTopBean.PictureGuide> data;
    private IGoodsToDetailPage iToGoodsDetailPage;


    public WPicNaviAdapter(Context mContext, ArrayList<WTopBean.PictureGuide> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void setIToDetailPage(IGoodsToDetailPage iToGoodsDetailPage) {
        this.iToGoodsDetailPage = iToGoodsDetailPage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewGoods = LayoutInflater.from(mContext).inflate(R.layout.adapetr_picture_navi, parent, false);
        ActivityViewHolder goodsHolder = new ActivityViewHolder(viewGoods);
        return goodsHolder;
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_picture;
        public TextView tv_picture_name;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            tv_picture_name = (TextView) itemView.findViewById(R.id.tv_picture_name);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ActivityViewHolder activityHolder = (ActivityViewHolder) holder;
        if (null!=activityHolder) {
        WTopBean.PictureGuide pictureGuide=data.get(position);
        if(null!=pictureGuide){
            activityHolder.tv_picture_name.setText(pictureGuide.title);
            //加载网络图片
            UIUtils.glideAppLoad(mContext,pictureGuide.image,R.mipmap.common_def_food,activityHolder.iv_picture);
        }
      }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    
}

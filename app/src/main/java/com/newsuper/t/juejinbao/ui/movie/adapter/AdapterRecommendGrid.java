package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.entity.MoviePostDataEntity;
import com.newsuper.t.juejinbao.utils.StringUtils;


import java.util.ArrayList;
import java.util.List;


public class AdapterRecommendGrid extends BaseAdapter {
    private Context context;
    private List<MoviePostDataEntity.DataBean.MovieBean> beans = new ArrayList<>();
    private int width;


    public AdapterRecommendGrid(Context context){
        this.context = context;
    }

    public void update(List<MoviePostDataEntity.DataBean.MovieBean> beans , int width){
        this.beans = beans;
        this.width = width;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup){
        ViewHolder mViewHolder;
        if(view == null){
            view = View.inflate(context, R.layout.item_recommend_grid, null);

            mViewHolder = new ViewHolder();
            mViewHolder.iv = view.findViewById(R.id.iv);
            mViewHolder.rl = view.findViewById(R.id.rl);
            view.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder)view.getTag();
        }



        ViewGroup.LayoutParams params = mViewHolder.iv.getLayoutParams();
        params.height = width * 15 / 10;
        params.width = width;
        mViewHolder.rl.setLayoutParams(params);

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.emptystate_pic)
                .error(R.mipmap.emptystate_pic)
                .skipMemoryCache(true)
                .fitCenter().centerCrop()
                .dontAnimate()
                .override(StringUtils.dip2px(context, 200), StringUtils.dip2px(context, 200))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        try {
            if(mViewHolder != null && mViewHolder.iv != null) {
                //带白色边框的圆形图片加载
                Glide.with(context).load(beans.get(position).getCover())
                        .apply(options)
                        .into(mViewHolder.iv);
            }
        }catch (Exception e){
            Log.e("zy" , "glide_Exception");}







        return view;
    }


    public static class ViewHolder {
        public ImageView iv;
        public RelativeLayout rl;
    }



}
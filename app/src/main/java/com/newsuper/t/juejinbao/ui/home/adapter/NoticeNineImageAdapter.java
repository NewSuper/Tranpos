package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;


import java.util.ArrayList;
import java.util.List;

public class NoticeNineImageAdapter extends RecyclerView.Adapter<NoticeNineImageAdapter.MyViewHolder>{
    private Context context;

    private List<NoticeImage> beans = new ArrayList<>();

    public NoticeNineImageAdapter(Context context){
        this.context = context;
    }

    public void update(List<NoticeImage> beans){
        this.beans = beans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemView(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(beans.get(i));
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(ViewGroup parent) {super(parent);}
        public void setData(Object object) {}
    }

    class ItemView extends MyViewHolder{
        private NoticeImage bean;
        private ImageView iv;



        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_notice_image, parent, false));
            iv = itemView.findViewById(R.id.iv);


        }

        public void setData(Object object) {
            bean = (NoticeImage) object;

            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .centerCrop()
                    .dontAnimate()
                    .bitmapTransform(new RoundedCorners(10))
//                    .override(StringUtils.dip2px(context, 200), StringUtils.dip2px(context, 200))
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            try {
                if(iv != null) {
                    Glide.with(context).load(bean.imgUrl)
                            .apply(options)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(iv);
                }
            }catch (Exception e){
                Log.e("zy" , "glide_Exception");}

        }

    }


    //数据
    public static class NoticeImage{
        public String imgUrl;
    }

}

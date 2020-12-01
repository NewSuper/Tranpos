package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

//
import com.squareup.picasso.Picasso;
import com.newsuper.t.R;
import com.newsuper.t.consumer.function.selectgoods.inter.OnPhotoClickListener;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.ArrayList;


public class PhotoGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> photoList;
    private Context mContext;
    private OnPhotoClickListener listener;

    public PhotoGridAdapter(Context mContext, ArrayList<String> photoList) {
        this.mContext = mContext;
        this.photoList = photoList;
    }

    public void setPhotoClickListener(OnPhotoClickListener listener){
        this.listener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewPhoto = LayoutInflater.from(mContext).inflate(R.layout.item_shop_photo, parent, false);
        PhotoViewHolder photoViewHolder = new PhotoViewHolder(viewPhoto);
        return photoViewHolder;

    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_photo;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PhotoViewHolder goodsHolder = (PhotoViewHolder) holder;
        if (goodsHolder != null) {
            String photoUrl=photoList.get(position);
            if (!StringUtils.isEmpty(photoUrl)){
                if (!photoUrl.startsWith("http")){
                    photoUrl = RetrofitManager.BASE_URL + photoUrl;
                }
            }
            Picasso.with(mContext).load(photoUrl).error(R.mipmap.common_def_food).into(goodsHolder.iv_photo);
//            UIUtils.glideAppLoad(mContext,photoUrl,R.mipmap.common_def_food,goodsHolder.iv_photo);
            goodsHolder.iv_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=listener){
                        listener.onClickPhoto(position,photoList);
                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return photoList.size();
    }

}

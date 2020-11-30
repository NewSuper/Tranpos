package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.PictureGuideView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureGuideScrollAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public ArrayList<WTopBean.PictureGuide> list;
    private int height,width ,space;
    private boolean isCorner;
    private String text_color;

    public PictureGuideScrollAdapter(Context context, ArrayList<WTopBean.PictureGuide> list, int height,int width ,int space, boolean isCorner,String text_color) {
        this.context = context;
        this.list = list;
        this.height = height;
        this.isCorner = isCorner;
        this.space = space;
        this.text_color = text_color;
        this.width = width;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.pic_better_scroll, parent,false);
        return new PictureGuideScrollViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        PictureGuideScrollViewHolder holder = (PictureGuideScrollViewHolder)viewHolder;
        WTopBean.PictureGuide pictureGuide = list.get(position);
        holder.tvPictureName.setTextColor(UIUtils.getColor(text_color));
        if (!TextUtils.isEmpty(pictureGuide.title)) {
            holder.tvPictureName.setVisibility(View.VISIBLE);
            holder.tvPictureName.setText(pictureGuide.title);
        } else {
            holder.tvPictureName.setVisibility(View.INVISIBLE);
        }
        if (position != getItemCount() -1){
            holder.vw.setVisibility(View.VISIBLE);
        }else {
            holder.vw.setVisibility(View.GONE);
        }
        //加载网络图片
        String url = pictureGuide.image;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_URL + url;
            }
            Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(holder.ivPicture);
//            UIUtils.glideAppLoadCorner(context,url,R.mipmap.common_def_food,holder.ivPicture,isCorner);

        }else {
            holder.ivPicture.setImageResource(R.mipmap.store_logo_default);
        }
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guideListener != null){
                    guideListener.onPictureClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    PictureGuideView.PictureGuideListener guideListener;

    public void setGuideListener(PictureGuideView.PictureGuideListener guideListener) {
        this.guideListener = guideListener;
    }
    class PictureGuideScrollViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_picture)
        RoundedImageView ivPicture;
        @BindView(R.id.tv_picture_name)
        TextView tvPictureName;
        @BindView(R.id.vw)
        View vw;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;


        PictureGuideScrollViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ll_main.getLayoutParams().width = width;
            ivPicture.getLayoutParams().height = height;
            tvPictureName.getLayoutParams().width = width;
            vw.getLayoutParams().width = space;
            if (isCorner){
                ivPicture.setCornerRadius(10);
            }

        }
    }
}

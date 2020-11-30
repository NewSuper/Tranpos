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

import com.makeramen.roundedimageview.RoundedImageView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/11 0011.
 */

public class PictureRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<WTopBean.PictureAdvertisment> list;
    private int space;
    private int style = 1;//1：小 2：大
    private int height,width;
    private boolean radioVal;
    public PictureRecycleAdapter(Context context, ArrayList<WTopBean.PictureAdvertisment> list,int style,int width,int height,boolean radioVal,int space) {
        this.context = context;
        this.list = list;
        this.style = style;
        this.height = height;
        this.width = width;
        this.radioVal = radioVal;
        this.space = space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PictureViewHolder  viewHolder = null;
        if(style == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_picture_recycle_small,parent, false);
            viewHolder = new PictureViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_picture_recycle_big,parent, false);
            viewHolder = new PictureViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PictureViewHolder  viewHolder = (PictureViewHolder)holder;
        final WTopBean.PictureAdvertisment advertisment = list.get(position);
        if (!StringUtils.isEmpty(advertisment.text_color) && advertisment.text_color.startsWith("#")
                && (advertisment.text_color.length() == 7 || advertisment.text_color.length() == 9 )){
            viewHolder.tvTitle.setTextColor(Color.parseColor(advertisment.text_color));
        }else {
            viewHolder.tvTitle.setTextColor(Color.parseColor("#ffffff"));
        }
        if (StringUtils.isEmpty(advertisment.title)){
            viewHolder.tvTitle.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.tvTitle.setText(advertisment.title);
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
        }
        String url = advertisment.image;
        if (!url.startsWith("http")){
            url = RetrofitManager.BASE_IMG_URL_BIG + url;
        }
        if (position == getItemCount() - 1 ){
            viewHolder.vvDiv.setVisibility(View.GONE);
        }else {
            viewHolder.vvDiv.setVisibility(View.VISIBLE);
        }
//        UIUtils.glideAppLoadCorner(context,url,R.mipmap.store_logo_default,viewHolder.ivPic,radioVal);
//        Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(viewHolder.ivPic);
        UIUtils.glideAppLoadCorner(context,url,R.mipmap.store_logo_default,height,viewHolder.ivPic,((PictureViewHolder) holder).tvTitle,radioVal);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClickListener != null){
                    itemOnClickListener.OnItemOnCliked(advertisment);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_pic)
        RoundedImageView ivPic;
        @BindView(R.id.vv_div)
        View vvDiv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        View mView;
        public PictureViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            if (radioVal){
                ivPic.setCornerRadius(10);
                tvTitle.setBackgroundResource(R.drawable.shape_pic_bottom_corner);
            }else {
              tvTitle.setBackgroundColor(Color.parseColor("#99000000"));
            }
            view.getLayoutParams().height = height;
//            view.getLayoutParams().width = width;
            vvDiv.getLayoutParams().width = space;
            mView = view;
        }
    }
    private AdapterItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(AdapterItemOnClickListener onClickListener) {
        this.itemOnClickListener = onClickListener;
    }

    public interface AdapterItemOnClickListener{
        void OnItemOnCliked(WTopBean.PictureAdvertisment advertisment);
    }
}

package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureGuideAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<WTopBean.PictureGuide> list;
    private int height;
    private boolean isCorner;
    private String text_color;
    public PictureGuideAdapter(Context context,ArrayList<WTopBean.PictureGuide> list,int height,boolean isCorner,String text_color){
        this.context = context;
        this.list = list;
        this.height = height;
        this.isCorner = isCorner;
        this.text_color = text_color;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PictureGuideViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pic_better_select_1, null);
            holder = new PictureGuideViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PictureGuideViewHolder)convertView.getTag();
        }
        WTopBean.PictureGuide pictureGuide = list.get(position);
        holder.tvPictureName.setTextColor(UIUtils.getColor(text_color));
        if (!TextUtils.isEmpty(pictureGuide.title)) {
            holder.tvPictureName.setText(pictureGuide.title);
        } else {
            holder.tvPictureName.setVisibility(View.INVISIBLE);
        }
        //加载网络图片
        String url = pictureGuide.image;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_URL + url;
            }
//            UIUtils.glideAppLoadCorner(context,url,R.mipmap.common_def_food,holder.ivPicture,isCorner);
            Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(holder.ivPicture);
        }else {
            holder.ivPicture.setImageResource(R.mipmap.store_logo_default);
        }
        return convertView;
    }

    class PictureGuideViewHolder {
        @BindView(R.id.iv_picture)
        RoundedImageView ivPicture;
        @BindView(R.id.tv_picture_name)
        TextView tvPictureName;
        PictureGuideViewHolder(View view) {
            ButterKnife.bind(this, view);
            LogUtil.log("PictureGuideView", "mHeight   000 == " + ivPicture.getLayoutParams().height);
            LogUtil.log("PictureGuideView", "mWidth   000 == " + ivPicture.getLayoutParams().width);
            ivPicture.getLayoutParams().height = height;
            LogUtil.log("PictureGuideView", "mHeight   111 == " + ivPicture.getLayoutParams().height);
            if (isCorner){
                ivPicture.setCornerRadius(10);
            }
        }
    }
}

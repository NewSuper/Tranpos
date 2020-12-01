package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GoodsType;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.MyImageSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<GoodsType> mTypeList;
    private int mSelectedPosition=-1;//选中位置
    private OnItemSelectedListener onItemSelectedListener;
//    private Map<String,String> typeNeedMap;
    public TypeAdapter(Context mContext, ArrayList<GoodsType> mTypeList) {
        this.mContext = mContext;
        this.mSelectedPosition = -1;
        this.mTypeList = mTypeList;
        if (mTypeList.size() > 0){
            if ("活动".equals(mTypeList.get(0).name)){
                mTypeList.remove(0);
            }
//            //筛选出必选分类
//            for (GoodsType goodsType : mTypeList){
//              /*  if ("折扣".equals(goodsType.name)){
//                    goodsType.is_need = "1";
//                }
//                if ("水果".equals(goodsType.name)){
//                    goodsType.is_need = "1";
//                }*/
//                if ("1".equals(goodsType.is_need)){
//                    if (typeNeedMap == null){
//                        typeNeedMap = new HashMap<>();
//                    }
//                    LogUtil.log("isFullMinBuy","goodsInfo.type 000==  has");
//                    typeNeedMap.put(goodsType.type_id,goodsType.name);
//                }
//            }
        }
    }

//    public Map<String, String> getTypeNeedMap() {
//        return typeNeedMap;
//    }

    public interface OnItemSelectedListener{
        void onTypeItemSelected(int position, GoodsType goodsType);
    }

    public void addItemSelectedListener(OnItemSelectedListener listener){
        onItemSelectedListener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_type,parent,false);
        TypeHolder holder = new TypeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            TypeHolder viewHolder = (TypeHolder) holder;
            GoodsType goodsType = mTypeList.get(position);
            if(goodsType.count>0){
                viewHolder.tvCount.setVisibility(View.VISIBLE);
                viewHolder.tvCount.setText(goodsType.count+"");
            }else{
                viewHolder.tvCount.setVisibility(View.INVISIBLE);
            }
            if(mSelectedPosition==position){
                viewHolder.rlRoot.setSelected(true);
            }else{
                viewHolder.rlRoot.setSelected(false);
            }
            switch (mTypeList.get(position).type_id) {
                case "discount":
                    Drawable drawable = ContextCompat.getDrawable(mContext,R.mipmap.icon_discount_type);
                    if (null!=drawable) {
                        drawable.setBounds(UIUtils.dip2px(5), 0, UIUtils.dip2px(15), UIUtils.dip2px(15));
                        MyImageSpan span = new MyImageSpan(mContext,UIUtils.drawableToBitmap(drawable));
                        CharSequence text = "iconImage  " + goodsType.name;
                        String rString = "iconImage";
                        SpannableStringBuilder builder = new SpannableStringBuilder(text);
                        builder.setSpan(span,0,rString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        viewHolder.tvType.setText(builder);
                        viewHolder.ivType.setVisibility(View.GONE);
                    } else {
                        viewHolder.ivType.setVisibility(View.VISIBLE);
                        viewHolder.ivType.setImageResource(R.mipmap.icon_discount_type);
                    }
                    viewHolder.tvType.setGravity(Gravity.CENTER_VERTICAL);
                    break;
                case "search":
                    Drawable searchDrawable = ContextCompat.getDrawable(mContext,R.mipmap.icon_search_type);
                    if (null!=searchDrawable) {
                        searchDrawable.setBounds(UIUtils.dip2px(5), 0, UIUtils.dip2px(15), UIUtils.dip2px(15));
                        MyImageSpan span = new MyImageSpan(mContext,UIUtils.drawableToBitmap(searchDrawable));
                        CharSequence text = "iconImage  " + goodsType.name;
                        String rString = "iconImage";
                        SpannableStringBuilder builder = new SpannableStringBuilder(text);
                        builder.setSpan(span,0,rString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        viewHolder.tvType.setText(builder);
                        viewHolder.ivType.setVisibility(View.GONE);
                    } else {
                        viewHolder.ivType.setVisibility(View.VISIBLE);
                        viewHolder.ivType.setImageResource(R.mipmap.icon_search_type);
                    }
                    viewHolder.tvType.setGravity(Gravity.CENTER_VERTICAL);
                    break;
                default:
                    viewHolder.ivType.setVisibility(View.GONE);
                    viewHolder.tvType.setGravity(Gravity.CENTER);
                    break;
            }
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GoodsType goodsType = mTypeList.get(position);
        final TypeHolder viewHolder = (TypeHolder)holder;
        viewHolder.tvType.setText(goodsType.name);
        if(goodsType.count>0){
            viewHolder.tvCount.setVisibility(View.VISIBLE);
            viewHolder.tvCount.setText(goodsType.count+"");
        }else{
            viewHolder.tvCount.setVisibility(View.INVISIBLE);
        }
        if(mSelectedPosition==position){
            viewHolder.rlRoot.setSelected(true);
        }else{
            viewHolder.rlRoot.setSelected(false);
        }
        viewHolder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemSelected(position);
                mSelectedPosition = position;
                Log.e("点击........", position+"....."+mSelectedPosition);
                notifyDataSetChanged();
            }
        });
        switch (mTypeList.get(position).type_id) {
            case "discount":
                Drawable drawable = ContextCompat.getDrawable(mContext,R.mipmap.icon_discount_type);
                if (null!=drawable) {
                    drawable.setBounds(UIUtils.dip2px(5), 0, UIUtils.dip2px(15), UIUtils.dip2px(15));
                    MyImageSpan span = new MyImageSpan(mContext,UIUtils.drawableToBitmap(drawable));
                    CharSequence text = "iconImage  " + goodsType.name;
                    String rString = "iconImage";
                    SpannableStringBuilder builder = new SpannableStringBuilder(text);
                    builder.setSpan(span,0,rString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    viewHolder.tvType.setText(builder);
                    viewHolder.ivType.setVisibility(View.GONE);
                } else {
                    viewHolder.ivType.setVisibility(View.VISIBLE);
                    viewHolder.ivType.setImageResource(R.mipmap.icon_discount_type);
                }
                viewHolder.tvType.setGravity(Gravity.CENTER_VERTICAL);
                break;
            case "search":
                Drawable searchDrawable = ContextCompat.getDrawable(mContext,R.mipmap.icon_search_type);
                if (null!=searchDrawable) {
                    searchDrawable.setBounds(UIUtils.dip2px(5), 0, UIUtils.dip2px(15), UIUtils.dip2px(15));
                    MyImageSpan span = new MyImageSpan(mContext,UIUtils.drawableToBitmap(searchDrawable));
                    CharSequence text = "iconImage  " + goodsType.name;
                    String rString = "iconImage";
                    SpannableStringBuilder builder = new SpannableStringBuilder(text);
                    builder.setSpan(span,0,rString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    viewHolder.tvType.setText(builder);
                    viewHolder.ivType.setVisibility(View.GONE);
                } else {
                    viewHolder.ivType.setVisibility(View.VISIBLE);
                    viewHolder.ivType.setImageResource(R.mipmap.icon_search_type);
                }
                viewHolder.tvType.setGravity(Gravity.CENTER_VERTICAL);
                break;
            default:
                viewHolder.ivType.setVisibility(View.GONE);
                viewHolder.tvType.setGravity(Gravity.CENTER);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    public void setSelectedPositon(int pos,boolean isLeftClick) {
        if(pos<getItemCount() && pos>=0 ) {
            if(pos==this.mSelectedPosition){
                return;
            }else{
                this.mSelectedPosition = pos;
                notifyDataSetChanged();
                if(isLeftClick){
                    notifyItemSelected(pos);
                }
            }
        }
    }

    public class TypeHolder extends RecyclerView.ViewHolder {
        TextView tvCount, tvType;
        RelativeLayout rlRoot;
        ImageView ivType;
        public TypeHolder(View itemView) {
            super(itemView);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            tvType = (TextView) itemView.findViewById(R.id.type);
            rlRoot = (RelativeLayout) itemView.findViewById(R.id.rl_root);
            ivType = itemView.findViewById(R.id.iv_type);
        }
    }

    private void notifyItemSelected(int position) {
        if(null!=onItemSelectedListener){
            onItemSelectedListener.onTypeItemSelected(position,mTypeList.get(position));
        }
    }
}

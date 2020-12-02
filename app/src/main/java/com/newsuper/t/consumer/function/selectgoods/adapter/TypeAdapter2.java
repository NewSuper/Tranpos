package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.bean.GoodsType;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.MyImageSpan;

import java.util.ArrayList;
import java.util.List;


public class TypeAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<GoodsType> mTypeList;
    private int mSelectedPosition=-1;//选中位置
    private OnItemSelectedListener onItemSelectedListener;
    public static final int ITEM_NUM = 4; // 每行拥有的Item数
//    private Map<String,String> typeNeedMap;

    public TypeAdapter2(Context mContext, ArrayList<GoodsType> mTypeList) {
        this.mContext = mContext;
        this.mTypeList = mTypeList;
        this.mSelectedPosition = -1;

//        //筛选出必选分类
//        for (GoodsType goodsType : mTypeList){
//            if ("1".equals(goodsType.is_need)){
//                if (typeNeedMap == null){
//                    typeNeedMap = new HashMap<>();
//                }
//                typeNeedMap.put(goodsType.type_id,goodsType.name);
//            }
//        }
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_big_goods_type,parent,false);
        //设置每个item的宽度
        ViewGroup.LayoutParams lp=view.getLayoutParams();
        lp.width=getItemStdWidth();
        TypeHolder holder = new TypeHolder(view);
        return holder;
    }

    // 获取标准宽度
    public static int getItemStdWidth() {
        DisplayMetrics displayMetrics = BaseApplication.getContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / ITEM_NUM;
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
                viewHolder.tvCount.setVisibility(View.GONE);
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
            viewHolder.tvCount.setVisibility(View.GONE);
        }
        if(mSelectedPosition==position){
            viewHolder.rlRoot.setSelected(true);
        }else{
            viewHolder.rlRoot.setSelected(false);
        }
        viewHolder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedPosition = position;
                Log.e("点击........", position+"....."+mSelectedPosition);
                notifyDataSetChanged();
                notifyItemSelected(position);
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
    public int setSelectTypeId(String typeId,boolean isLeftClick){
        LogUtil.log("SelectSearchGoods","setSelectTypeId   typeId = "+typeId);
        if (!StringUtils.isEmpty(typeId) && mTypeList.size() > 0){
            for (int i = 0;i < mTypeList.size();i++){
                if (mTypeList.get(i).type_id.equals(typeId)){
                    this.mSelectedPosition = i;
                    LogUtil.log("SelectSearchGoods","setSelectTypeId   mSelectedPosition = "+mSelectedPosition);
                    notifyDataSetChanged();
                    if(isLeftClick){
                        notifyItemSelected(i);
                    }
                    return i;
                }
            }
        }
        return 0;
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

package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.HelpBean;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/19 0019.
 */

public class HelpBuyTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String types[];
    private int selectType = 0;
    private String currentSelectType = "";
    public HelpBuyTypeAdapter(Context context,String type [] ) {
        super();
        this.context = context;
        this.types = type;
    }
    public void setSelectType(int selectType){
        this.selectType = selectType;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return types.length;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_bang_type, null);
        return new HelpBuyTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HelpBuyTypeViewHolder viewHolder = (HelpBuyTypeViewHolder)holder;
        final String type = types[position] ;
        viewHolder.tvType.setText(type);
        viewHolder.tvType.setTextColor(Color.parseColor("#666666"));
        switch (type){
            case "随意购":
               if (selectType == position){
                   currentSelectType = "1";
                   viewHolder.ivType.setImageResource(R.mipmap.icon_suiyigou_red_3x);
                   viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
               }else {
                   viewHolder.ivType.setImageResource(R.mipmap.icon_suiyigou_gray_3x);
               }
                break;
            case "咖啡":
                if (selectType == position){
                    currentSelectType = "2";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_cafei_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_cafei_gary_3x);
                }
                break;
            case "香烟":
                if (selectType == position){
                    currentSelectType = "3";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_xiangyan_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_xiangyan_gray_3x);
                }
                break;
            case "药品":
                if (selectType == position){
                    currentSelectType = "7";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_yao_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_yao_gary_3x);
                }
                break;
            case "酒":
                if (selectType == position){
                    currentSelectType = "4";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_jiu_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_jiu_gary_3x);
                }
                break;
            case "早餐":
                if (selectType == position){
                    currentSelectType = "5";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_zaocan_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_zaocan_gray_3x);
                }
                break;
            case "宵夜":
                if (selectType == position){
                    currentSelectType = "6";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_xiaoye_red_2x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_xiaoye_gray_3x);
                }
                break;
            case "生鲜":
                if (selectType == position){
                    currentSelectType = "8";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shengxian_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shengxian_gary_3x);
                }
                break;
            case "水果":
                if (selectType == position){
                    currentSelectType = "9";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shuiguo_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shuiguo_gray_3x);
                }
                break;
            case "生活用品":
                if (selectType == position){
                    currentSelectType = "10";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shenghuo_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shenghuo_gary_3x);
                }
                break;
            case "五金杂货":
                if (selectType == position){
                    currentSelectType = "11";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_wujin_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_wujin_gray_3x);
                }
                break;
            case "数码":
                if (selectType == position){
                    currentSelectType = "12";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shuma_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shuma_gray_3x);
                }
                break;
            case "其他":
                if (selectType == position){
                    currentSelectType = "1";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_qita_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_qita_gary_3x);
                }
                break;
            case "美食":
                if (selectType == position){
                    currentSelectType = "2";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_meishi_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_meishi_gary_3x);
                }
                break;
            case "文件":
                if (selectType == position){
                    currentSelectType = "3";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_wenjian_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_wenjian_gray_3x);
                }
                break;
            case "蛋糕":
                if (selectType == position){
                    currentSelectType = "4";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_dangao_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_dangao_gary_3x);
                }
                break;
            case "鲜花":
                if (selectType == position){
                    currentSelectType = "5";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_xianhua_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_xianhua_gray_3x);
                }
                break;
            case "钥匙":
                if (selectType == position){
                    currentSelectType = "6";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_yaosi_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_yaosi_gray_3x);
                }
                break;
            case "手机":
                if (selectType == position){
                    currentSelectType = "7";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shouji_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_shouji_gray_3x);
                }
                break;
            case "万能排队":
                if (selectType == position){
                    currentSelectType = "1";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_wanneng_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_wanneng_gray_3x);
                }
                break;
            case "医院排队":
                if (selectType == position){
                    currentSelectType = "2";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_yiyuan_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_yiyuan_gray_3x);
                }
                break;
            case "办事排队":
                if (selectType == position){
                    currentSelectType = "3";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_banshi_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_banshi_gray_3x);
                }
                break;
            case "银行排队":
                if (selectType == position){
                    currentSelectType = "4";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_yinhang_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_yinhang_gary_3x);
                }
                break;
            case "餐厅排队":
                if (selectType == position){
                    currentSelectType = "5";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_restaurant_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_restaurant_gary_3x);
                }
                break;
            case "快递":
                if (selectType == position){
                    currentSelectType = "8";
                    viewHolder.ivType.setImageResource(R.mipmap.icon_kuaidi_red_3x);
                    viewHolder.tvType.setTextColor(Color.parseColor("#f87a7c"));
                }else {
                    viewHolder.ivType.setImageResource(R.mipmap.icon_kuaidi_gray_3x);
                }
                break;

        }
        final int index = position;
        viewHolder.llType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectType(index);
                if (itemSelectListener != null){
                    itemSelectListener.onItemSelect(type);
                }
            }
        });
    }
    private ItemSelectListener itemSelectListener;

    public void setItemSelectLisenter(ItemSelectListener itemSelectLisenter) {
        this.itemSelectListener = itemSelectLisenter;
    }

    public interface ItemSelectListener{
        void onItemSelect(String type);
    }

    public String getSelectType() {
        return currentSelectType;
    }

    static class HelpBuyTypeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.ll_type)
        LinearLayout llType;

        HelpBuyTypeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
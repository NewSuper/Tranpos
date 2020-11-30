package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.NoticeTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int currentSelect;
    private int select_bg_type;
    private ArrayList<WTopBean.GoodsGroupListData> groupList;
    public GoodsTypeAdapter(Context context,int select_bg_type) {
        this.context = context;
        this.select_bg_type = select_bg_type;
        this.groupList = new ArrayList<>();
    }
    public void setGroupList(ArrayList<WTopBean.GoodsGroupListData> groupList){
        this.groupList.addAll(groupList);
        notifyDataSetChanged();
    }
    public void setGroupList(WTopBean.GoodsGroupListData data){
        this.groupList.add(data);
    }
    public void setCurrentSelect(int currentSelect) {
        this.currentSelect = currentSelect;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_goods_type_wei, parent, false);
        return new TypeViewHolder(view,getItemCount());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        TypeViewHolder viewHolder = (TypeViewHolder)holder;
        if (viewHolder != null){
            if (currentSelect == position){
                viewHolder.vwLine.setVisibility(View.VISIBLE);
                if (select_bg_type == 0){
                    viewHolder.tvType.setBackgroundResource(R.drawable.shape_goods_group_select_type);
                    viewHolder.vwLine.setVisibility(View.INVISIBLE);
                    viewHolder.tvType.setTextColor(ContextCompat.getColor(context,R.color.white));
                }else if (select_bg_type == 1){
                    viewHolder.tvType.setTextColor(ContextCompat.getColor(context,R.color.white));
                    viewHolder.tvType.setBackgroundColor(ContextCompat.getColor(context,R.color.theme_red));
                    viewHolder.vwLine.setVisibility(View.INVISIBLE);
                }else {
                    viewHolder.tvType.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    viewHolder.tvType.setTextColor(ContextCompat.getColor(context,R.color.theme_red));
                    viewHolder.vwLine.setVisibility(View.VISIBLE);
                }
            }else {
                viewHolder.tvType.setTextColor(ContextCompat.getColor(context,R.color.text_color_99));
                viewHolder.vwLine.setVisibility(View.INVISIBLE);
                viewHolder.tvType.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            }
            String name = groupList.get(position).name;
            if (!StringUtils.isEmpty(name)){
                if (getItemCount() == 2) {
                    if (name.length() > 10){
                        viewHolder.tvType.setText(name.substring(0,9) + "...");
                    }else {
                        viewHolder.tvType.setText(name);
                    }
                }else if (getItemCount() == 3){
                    if (name.length() > 6){
                        viewHolder.tvType.setText(name.substring(0,5) + "...");
                    }else {
                        viewHolder.tvType.setText(name);
                    }
                }else if (getItemCount() == 4){
                    if (name.length() > 5){
                        viewHolder.tvType.setText(name.substring(0,4)+"...");
                    }else {
                        viewHolder.tvType.setText(name);
                    }
                }else if (getItemCount() > 4){
                    if (name.length() > 4){
                        viewHolder.tvType.setText(name.substring(0,3)+"...");
                    }else {
                        viewHolder.tvType.setText(name);
                    }
                }
            }
            viewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentSelect = position;
                    notifyDataSetChanged();
                    if (typeListener != null){
                        typeListener.onSelect(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    private GoodsTypeListener typeListener;

    public void setTypeListener(GoodsTypeListener typeListener) {
        this.typeListener = typeListener;
    }

    public interface GoodsTypeListener{
        void onSelect(int position);
    }
    static class TypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.vw_line)
        View vwLine;
        @BindView(R.id.rl_main)
        RelativeLayout rl_main;
        TypeViewHolder(View view,int count) {
            super(view);
            ButterKnife.bind(this, view);
            int w = 0;
            if (count <= 4 && count > 0){
                 w = UIUtils.getWindowWidth() /count;
            }else {
                 w = (int)(UIUtils.getWindowWidth() / 4.5);
            }
            view.getLayoutParams().width = w;
            if (count == 2){
                vwLine.getLayoutParams().width = UIUtils.dip2px(55);
            } else if (count == 3){
                vwLine.getLayoutParams().width = UIUtils.dip2px(50);
            } else if (count == 4){
                vwLine.getLayoutParams().width = UIUtils.dip2px(45);

            } else{
                vwLine.getLayoutParams().width = UIUtils.dip2px(40);
            }
        }
    }
}

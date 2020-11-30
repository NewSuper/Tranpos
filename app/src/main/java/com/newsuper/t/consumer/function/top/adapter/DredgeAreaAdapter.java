package com.newsuper.t.consumer.function.top.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.DredgeAreaBean;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.DredgeMapActivity;

import java.util.ArrayList;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class DredgeAreaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<DredgeAreaBean.DataBean> mList;
    private final static int TYPE_CONTENT = 1;
    private final static int TYPE_FOOTER = 2;

    public DredgeAreaAdapter(Context context,ArrayList<DredgeAreaBean.DataBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_dredge_area_list, parent, false);
            return new DredgeAreaViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_dredge_area_foot_view, parent,false);
            return new FootHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DredgeAreaViewHolder) {
            ((DredgeAreaViewHolder) holder).tvName.setText(mList.get(position).area_name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DredgeMapActivity.class);
                    intent.putExtra("id",mList.get(position).id);
                    intent.putExtra("title",mList.get(position).area_name);
                    ((Activity)mContext).startActivityForResult(intent,11);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mList || position == mList.size())
            return TYPE_FOOTER;
        else
            return TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return null != mList ? mList.size()+1:1;
    }

    static class DredgeAreaViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public DredgeAreaViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_dredge_area_list_name);
        }
    }

    private class FootHolder extends RecyclerView.ViewHolder{
        public FootHolder(View itemView) {
            super(itemView);
        }
    }
}

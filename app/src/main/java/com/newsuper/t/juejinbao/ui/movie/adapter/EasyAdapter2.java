package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 快速的recyclerview adapter ，无需派生，直接new出来用，其中T必须继承EasyEasyAdapter.TypeBean
 *
 *  CommonAdapterListener中回调返回:
 *   return new EasyAdapter.MyViewHolder<CodeBallsArray>(context, R.layout.item_prize_more_item, viewGroup){
 *
 *
 */
public class EasyAdapter2<T> extends RecyclerView.Adapter<EasyAdapter2.MyViewHolder> {
    protected Context context;
    private CommonAdapterListener commonAdapterListener;
    protected List<T> beans = new ArrayList<>();


    public EasyAdapter2(Context context, CommonAdapterListener commonAdapterListener) {
        this.context = context;
        this.commonAdapterListener = commonAdapterListener;
    }



    public void update(List<T> beans) {
        this.beans = beans;
        notifyDataSetChanged();
    }

    public List<T> getBeans() {
        return beans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return commonAdapterListener.getItemViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(beans.get(position) , position);
    }

    @Override
    public int getItemCount() {
        return beans != null ? beans.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Object object;

        public MyViewHolder(Context context , int layoutId , ViewGroup viewGroup) {
            super( LayoutInflater.from(context).inflate(layoutId, viewGroup, false));
            itemView.setOnClickListener(this);
        }

        public void setData(Object object , int position) {
        }

        @Override
        public void onClick(View view) {

        }
    }


    public interface CommonAdapterListener {
        MyViewHolder getItemViewHolder(ViewGroup viewGroup);

    }



}

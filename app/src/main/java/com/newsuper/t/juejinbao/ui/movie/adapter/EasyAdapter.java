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
public class EasyAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<EasyAdapter.MyViewHolder> {
    protected Context context;
    private CommonAdapterListener commonAdapterListener;
    protected List<T> beans = new ArrayList<>();


    public EasyAdapter(Context context, CommonAdapterListener commonAdapterListener) {
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
        switch (i) {
            case TypeBean.HEADER:
                return commonAdapterListener.getHeaderViewHolder(viewGroup);
            case TypeBean.ITEM:
                return commonAdapterListener.getItemViewHolder(viewGroup);
            case TypeBean.FOOTER:
                return commonAdapterListener.getFooterViewHolder(viewGroup);
            case TypeBean.BLANK:
                return commonAdapterListener.getBlankViewHolder(viewGroup);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(beans.get(position) , position);
    }

    @Override
    public int getItemViewType(int position) {
        return beans.get(position).getUiType();
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

        public void setData(TypeBean typeBean , int position) {
        }

        @Override
        public void onClick(View view) {

        }
    }


    public interface CommonAdapterListener {
        MyViewHolder getHeaderViewHolder(ViewGroup viewGroup);

        MyViewHolder getItemViewHolder(ViewGroup viewGroup);

        MyViewHolder getFooterViewHolder(ViewGroup viewGroup);

        MyViewHolder getBlankViewHolder(ViewGroup viewGroup);

    }

    public static class TypeBean implements Serializable {
        public static final int HEADER = 0;
        public static final int ITEM = 1;
        public static final int FOOTER = 2;
        public static final int BLANK = 3;
        public static final int FOOTER2 = 4;

        private int uiType = ITEM;
        private String exStr;
        private Object object;

        public String getExStr() {
            return exStr;
        }

        public void setExStr(String exStr) {
            this.exStr = exStr;
        }

        public int getUiType() {
            return uiType;
        }

        public void setUiType(int uiType) {
            this.uiType = uiType;

        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }

}

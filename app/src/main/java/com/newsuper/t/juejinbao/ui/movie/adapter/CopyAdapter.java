package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juejinchain.android.R;

import java.util.ArrayList;
import java.util.List;

public class CopyAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<CopyAdapter.MyViewHolder>{
    private Context context;
    private List<T> items = new ArrayList<>();


    public CopyAdapter(Context context){
        this.context = context;
    }


    public void update(List<T> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case EasyAdapter.TypeBean.ITEM:
                return new ItemView(viewGroup);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getUiType();
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(ViewGroup parent) {super(parent);}
        public void setData(Object object) {}
    }

    class ItemView extends MyViewHolder implements View.OnClickListener {

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_radar_list, parent, false));

        }

        @Override
        public void onClick(View v) {

        }
    }

}

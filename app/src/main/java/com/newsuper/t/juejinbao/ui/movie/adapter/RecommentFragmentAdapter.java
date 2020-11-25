package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newsuper.t.juejinbao.ui.movie.bean.AdapterItem;

import java.util.ArrayList;
import java.util.List;

public class RecommentFragmentAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;

    private List<AdapterItem> items = new ArrayList<>();

    private GetViewListener getViewListener;

    public RecommentFragmentAdapter(Context context , GetViewListener getViewListener) {
        this.context = context;
        this.getViewListener = getViewListener;
    }

    public void update(List<AdapterItem> itemList){
        this.items = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case AdapterItem.HEAD:
//                return new HeadViewHolder(viewGroup);
                return getViewListener.getHeadView(viewGroup);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }


    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getUiType();
    }

    public interface GetViewListener{
        MyViewHolder getHeadView(ViewGroup viewGroup);
    }

}

package com.newsuper.t.juejinbao.ui.song.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.adapter.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryAdapter  extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<String> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public SearchHistoryAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<String> itemList){
        this.items = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    public class ItemViewHolder extends MyViewHolder implements View.OnClickListener {

        private String str;

        private TextView tvMusicName;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_music_search_history, parent, false));
            tvMusicName = itemView.findViewById(R.id.tv_music_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            str = (String) object;
            tvMusicName.setText(str);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onSearchHistoryClick(str);
        }
    }

    public interface OnItemClickListener{
        void onSearchHistoryClick(String string);
    }
}

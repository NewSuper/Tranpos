package com.newsuper.t.juejinbao.ui.book.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.book.entity.BookCategoryEntity;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BookCategoryListAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<BookCategoryEntity.Category> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public BookCategoryListAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<BookCategoryEntity.Category> itemList){
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

        private BookCategoryEntity.Category category;

        private TextView tvName;


        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_book_category, parent, false));
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            category = (BookCategoryEntity.Category) object;
            tvName.setText(category.getName());
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.click(category);
        }
    }

    public interface OnItemClickListener{
        void click(BookCategoryEntity.Category book);
    }
}

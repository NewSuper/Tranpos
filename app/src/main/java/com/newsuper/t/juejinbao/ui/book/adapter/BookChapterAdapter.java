package com.newsuper.t.juejinbao.ui.book.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyViewHolder;
import com.newsuper.t.juejinbao.utils.ClickUtil;

import java.util.ArrayList;
import java.util.List;

public class BookChapterAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<BookChapterEntity.Chapter> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public BookChapterAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<BookChapterEntity.Chapter> itemList){
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
        myViewHolder.setData(i);
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    public class ItemViewHolder extends MyViewHolder implements View.OnClickListener {

        private int position;

        private TextView tvChapterName;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_book_chapter, parent, false));
            tvChapterName = itemView.findViewById(R.id.tv_chapter_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            position = (int) object;
            BookChapterEntity.Chapter chapter = items.get(position);
            tvChapterName.setText(chapter.getName());
        }

        @Override
        public void onClick(View view) {
            if(!ClickUtil.isNotFastClick()){
                return;
            }
            onItemClickListener.click(position);
        }
    }

    public interface OnItemClickListener{
        void click(int position);
    }
}

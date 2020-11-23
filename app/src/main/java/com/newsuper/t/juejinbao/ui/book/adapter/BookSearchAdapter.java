package com.newsuper.t.juejinbao.ui.book.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BookSearchAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<Book> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public BookSearchAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<Book> itemList){
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

        private Book book;

        private TextView tvNo;
        private TextView tvBookName;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_book_hot_search, parent, false));
            tvNo = itemView.findViewById(R.id.tv_no);
            tvBookName = itemView.findViewById(R.id.tv_book_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            int position = (int) object;
            book = items.get(position);
            tvBookName.setText(book.getNovel().getName());
            tvNo.setText(String.valueOf(position+1));
            switch (position){
                case 0:
                    tvNo.setTextColor(context.getResources().getColor(R.color.white));
                    tvNo.setBackground(context.getResources().getDrawable(R.drawable.shape_ff0000_round_2));
                    break;
                case 1:
                    tvNo.setTextColor(context.getResources().getColor(R.color.white));
                    tvNo.setBackground(context.getResources().getDrawable(R.drawable.shape_ff6200_round_2));
                    break;
                case 2:
                    tvNo.setTextColor(context.getResources().getColor(R.color.white));
                    tvNo.setBackground(context.getResources().getDrawable(R.drawable.shape_ffb900_round_2));
                    break;
                default:
                    tvNo.setTextColor(context.getResources().getColor(R.color.c_999999));
                    tvNo.setBackground(context.getResources().getDrawable(R.drawable.shape_efefef_round_2));
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.click(book);
        }
    }

    public interface OnItemClickListener{
        void click(Book book);
    }
}

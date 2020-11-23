package com.newsuper.t.juejinbao.ui.book.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BookRankingListAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<Book> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public BookRankingListAdapter(Context context, OnItemClickListener onItemClickListener) {
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
        private ImageView img1;
        private ImageView img2;
        private ImageView img3;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_book_ranking, parent, false));
            tvNo = itemView.findViewById(R.id.tv_No);
            tvBookName = itemView.findViewById(R.id.tv_book_name);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            int position = (int) object;
            book = items.get(position);
            position = position+1;

            tvNo.setText(String.valueOf(position));
            img1.setVisibility(View.GONE);
            img2.setVisibility(View.GONE);
            img3.setVisibility(View.GONE);
            switch (position){
                case 1:
                    tvNo.setTextColor(context.getResources().getColor(R.color.c_FF0000));
                    img1.setVisibility(View.VISIBLE);
                    img2.setVisibility(View.VISIBLE);
                    img3.setVisibility(View.VISIBLE);
                    img1.setImageResource(R.mipmap.ic_no1);
                    img2.setImageResource(R.mipmap.ic_no1);
                    img3.setImageResource(R.mipmap.ic_no1);
                    break;
                case 2:
                    tvNo.setTextColor(context.getResources().getColor(R.color.c_FF6200));
                    img1.setVisibility(View.VISIBLE);
                    img2.setVisibility(View.VISIBLE);
                    img1.setImageResource(R.mipmap.ic_no2);
                    img2.setImageResource(R.mipmap.ic_no2);
                    break;
                case 3:
                    tvNo.setTextColor(context.getResources().getColor(R.color.c_FFB900));
                    img1.setVisibility(View.VISIBLE);
                    img1.setImageResource(R.mipmap.ic_no3);
                    break;
                default:
                    tvNo.setTextColor(context.getResources().getColor(R.color.c_999999));
                    break;
            }
            tvBookName.setText(book.getNovel().getName());
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

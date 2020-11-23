package com.newsuper.t.juejinbao.ui.book.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyViewHolder;


import java.util.ArrayList;
import java.util.List;

public class BooKHotClassifyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<Book> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public BooKHotClassifyAdapter(Context context , OnItemClickListener onItemClickListener) {
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
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    //item
    public class ItemViewHolder extends MyViewHolder implements View.OnClickListener {

        private Book book;

        private LinearLayout llTop;
        private TextView tvName;
        private TextView tvNum;
        private ImageView ivBookBg;
        private TextView tvBookName;
        private TextView tvBookContent;
        private TextView tvBookState;


        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_book_recommend, parent, false));
            llTop = itemView.findViewById(R.id.ll_top);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNum = itemView.findViewById(R.id.tv_num);
            ivBookBg = itemView.findViewById(R.id.iv_book_bg);
            tvBookName = itemView.findViewById(R.id.tv_book_name);
            tvBookContent = itemView.findViewById(R.id.tv_book_content);
            tvBookState = itemView.findViewById(R.id.tv_book_state);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            book = (Book) object;

            if(book.getTop() !=null){
                llTop.setVisibility(View.VISIBLE);
                tvName.setText(book.getTop().getName());
                tvNum.setText(String.format("共%s本", book.getTop().getNum()));
            }else{
                llTop.setVisibility(View.GONE);
            }

            Glide.with(context).load(book.getNovel().getCover()).into(ivBookBg);
            tvBookName.setText(book.getNovel().getName());
            tvBookContent.setText(book.getNovel().getIntro());
            String state = book.getNovel().getIsover()==1?"完结":"连载中";
            tvBookState.setText(String.format("%s·%s·%s", book.getAuthor().getName(), book.getCategory().getName(), state));
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

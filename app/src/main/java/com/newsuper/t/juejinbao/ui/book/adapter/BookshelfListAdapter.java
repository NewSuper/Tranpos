package com.newsuper.t.juejinbao.ui.book.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyViewHolder;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.TimeUtils;


import java.util.ArrayList;
import java.util.List;

public class BookshelfListAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<Book> items = new ArrayList<>();
    private OnItemChildClickListener onItemChildClickListener;

    public BookshelfListAdapter(Context context, OnItemChildClickListener onItemChildClickListener) {
        this.context = context;
        this.onItemChildClickListener = onItemChildClickListener;
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

        private RelativeLayout rlItem;
        private ImageView ivBookBg;
        private TextView tvBookName;
        private TextView tvWriter;
        private TextView tvClassify;
        private TextView tvTime;
        private TextView tvProgress;
        private TextView tvRead;
        private ImageView ivDelete;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_book_list, parent, false));
            rlItem = itemView.findViewById(R.id.rl_item);
            ivBookBg = itemView.findViewById(R.id.iv_book_bg);
            tvBookName = itemView.findViewById(R.id.tv_book_name);
            tvWriter = itemView.findViewById(R.id.tv_writer);
            tvClassify = itemView.findViewById(R.id.tv_classify);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvProgress = itemView.findViewById(R.id.tv_progress);
            tvRead = itemView.findViewById(R.id.tv_read);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            int position = (int) object;
            book = items.get(position);
            Glide.with(context).load(book.getNovel().getCover()).into(ivBookBg);
            tvBookName.setText(book.getNovel().getName());
            tvWriter.setText(String.format("作者：%s", book.getAuthor().getName()));
            tvClassify.setText(String.format("类型：%s", book.getCategory().getName()));
            tvTime.setText(String.format("时间：%s", TimeUtils.getTime(book.getNovel().getPostdate()*1000,TimeUtils.DATE_FORMAT_DATE)));
            tvProgress.setText(String.format("还有%s章节未读", book.getHasnew()));
            if(book.getHasnew() == 0){
                tvRead.setText("全部读完");
            }else{
                tvRead.setText("继续阅读");
                tvRead.setTag(position);
                tvRead.setOnClickListener(this);
            }

            rlItem.setTag(position);
            ivDelete.setTag(position);
            rlItem.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(!ClickUtil.isNotFastClick()){
                return;
            }
            onItemChildClickListener.click(view);
        }
    }

    public interface OnItemChildClickListener{
        void click(View view);
    }
}

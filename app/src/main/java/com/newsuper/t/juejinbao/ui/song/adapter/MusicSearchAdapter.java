package com.newsuper.t.juejinbao.ui.song.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.adapter.MyViewHolder;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.song.entity.MusicSearchEntity;

import java.util.ArrayList;
import java.util.List;

public class MusicSearchAdapter extends RecyclerView.Adapter<MusicSearchAdapter.MyViewHolder>{

    private Context context;
    private List<MusicSearchEntity.DataBean> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    private String key = "";
    public MusicSearchAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<MusicSearchEntity.DataBean> itemList , String key){
        this.items = itemList;
        this.key = key;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i) , i);
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(ViewGroup parent) {
            super(parent);
        }

        public void setData(Object object , int position) {

        }
    }


    public class ItemViewHolder extends MyViewHolder implements View.OnClickListener {

        private MusicSearchEntity.DataBean dataBean;

        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvType;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_music_search_result, parent, false));
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvType = itemView.findViewById(R.id.tv_type);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object , int position) {
            super.setData(object , position);
            dataBean = (MusicSearchEntity.DataBean) object;
            tvTitle.setText(dataBean.getTitle());
            tvAuthor.setText(dataBean.getAuthor());
            //tvType.setText(String.format("(来源%s)", dataBean.getType()));
            tvType.setText("(来源：疯狂音乐网)");

            tvTitle.setText(Utils.getSearchTitle2(context, dataBean.getTitle(),key));

        }

        @Override
        public void onClick(View view) {
            onItemClickListener.click(dataBean);
        }
    }

    public interface OnItemClickListener{
        void click(MusicSearchEntity.DataBean dataBean);
    }
}

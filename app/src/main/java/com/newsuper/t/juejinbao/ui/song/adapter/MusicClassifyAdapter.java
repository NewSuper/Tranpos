package com.newsuper.t.juejinbao.ui.song.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.adapter.MyViewHolder;
import com.juejinchain.android.module.song.entity.CategorysBean;
import com.juejinchain.android.module.song.entity.MusicDataListEntity;

import java.util.ArrayList;
import java.util.List;

public class MusicClassifyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<CategorysBean> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public MusicClassifyAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<CategorysBean> itemList){
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

        private CategorysBean dataBean;

        private ImageView ivBg;
        private TextView tvName;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_music_classify_list, parent, false));
            ivBg = itemView.findViewById(R.id.iv_bg);
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            dataBean = (CategorysBean) object;
            Glide.with(context).load(dataBean.getThumbnail()).apply(new RequestOptions().error(R.mipmap.ic_music_default_bg)).into(ivBg);
            tvName.setText(dataBean.getTitle());
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onMusicClassify(dataBean);
        }
    }

    public interface OnItemClickListener{
        void onMusicClassify(CategorysBean dataBean);
    }
}

package com.newsuper.t.juejinbao.ui.song.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyViewHolder;
import com.newsuper.t.juejinbao.ui.song.entity.MusicHotListEntity;

import java.util.ArrayList;
import java.util.List;

public class MusicHotAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<MusicHotListEntity.DataBean> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public MusicHotAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<MusicHotListEntity.DataBean> itemList){
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

        private MusicHotListEntity.DataBean dataBean;

        private TextView tvTitle;
        private TextView tvType;
        private TextView tvNum;
        private TextView tvNo;
        private ImageView iv_hot;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_music_search_hot, parent, false));
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvNo = itemView.findViewById(R.id.tv_no);
            tvType = itemView.findViewById(R.id.tv_type);
            tvNum = itemView.findViewById(R.id.tv_num);
            iv_hot = itemView.findViewById(R.id.iv_hot);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            int position = (int) object;
            dataBean = items.get(position);

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
            tvTitle.setText(dataBean.getTitle());
            tvType.setText(dataBean.getDesc());
            tvNum.setText(dataBean.getNumber());

            if(position < 5){
                iv_hot.setVisibility(View.VISIBLE);
            }else{
                iv_hot.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onSearchHotItemClick(dataBean);
        }
    }

    public interface OnItemClickListener{
        void onSearchHotItemClick(MusicHotListEntity.DataBean dataBean);
    }
}

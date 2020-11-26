package com.newsuper.t.juejinbao.ui.task.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyViewHolder;
import com.newsuper.t.juejinbao.ui.task.entity.TaskListEntity;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<TaskListEntity.BBean> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public TaskAdapter(Context context,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<TaskListEntity.BBean> itemList){
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

        private TaskListEntity.BBean bean;

        private ImageView ivLogo;
        private TextView tvName;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_task, parent, false));
            ivLogo = itemView.findViewById(R.id.iv_logo);
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            bean = (TaskListEntity.BBean) object;
            Glide.with(context).load(bean.getImg()).into(ivLogo);
            tvName.setText(bean.getTitle());
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.click(bean);
        }
    }

    public interface OnItemClickListener{
        void click(TaskListEntity.BBean bean);
    }
}

package com.newsuper.t.juejinbao.ui.task.adapter;

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
import com.juejinchain.android.module.task.entity.TaskSignBean;

import java.util.ArrayList;
import java.util.List;

public class TaskSignAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<TaskSignBean> items = new ArrayList<>();

    public TaskSignAdapter(Context context) {
        this.context = context;
    }

    public void update(List<TaskSignBean> itemList){
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

    public class ItemViewHolder extends MyViewHolder{

        private ImageView ivSign;
        private ImageView ivSigned;
        private TextView tvCoin;
        private TextView tvDay;
        private View viewLeft;
        private View viewRight;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_task_sign, parent, false));

            viewLeft = itemView.findViewById(R.id.view_left);
            viewRight = itemView.findViewById(R.id.view_right);
            ivSign = itemView.findViewById(R.id.iv_sign);
            ivSigned = itemView.findViewById(R.id.iv_signed);
            tvCoin = itemView.findViewById(R.id.tv_coin);
            tvDay = itemView.findViewById(R.id.tv_day);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            int position = (int) object;
            TaskSignBean signBean = items.get(position);

            viewLeft.setVisibility(View.VISIBLE);
            viewRight.setVisibility(View.VISIBLE);
            if(position==0){
                viewLeft.setVisibility(View.GONE);
            }else if(position == items.size()-1){
                viewRight.setVisibility(View.GONE);
            }
            tvCoin.setText(String.valueOf(signBean.getCoin()));
            tvDay.setText(String.format("%s天", signBean.getDay()));
            if(signBean.isSign()){
                ivSign.setImageResource(R.mipmap.ic_signed);
                ivSigned.setVisibility(View.VISIBLE);
                tvCoin.setTextColor(context.getResources().getColor(R.color.c_9e6316));
                tvDay.setTextColor(context.getResources().getColor(R.color.c_EEAF40));
            }else{
                ivSign.setImageResource(R.mipmap.ic_un_sign);
                ivSigned.setVisibility(View.GONE);
                tvCoin.setTextColor(context.getResources().getColor(R.color.c_D09E5E));
                tvDay.setTextColor(context.getResources().getColor(R.color.c_F7DB92));
            }
        }
    }
}

package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.newsuper.t.juejinbao.ui.movie.entity.DependentResourcesDataEntity;

import java.util.ArrayList;
import java.util.List;

public class DependentResourceConditionAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;

    private List<DependentResourcesDataEntity.DataBeanX.CountBean> items = new ArrayList<>();

    private OnItemClickListener onItemClickListener;




    public DependentResourceConditionAdapter(Context context , OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<DependentResourcesDataEntity.DataBeanX.CountBean> itemList){
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
        private DependentResourcesDataEntity.DataBeanX.CountBean countBean;

        private TextView tv;

        public ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_resourcedetail_condition, parent, false));
            tv = itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            countBean = (DependentResourcesDataEntity.DataBeanX.CountBean) object;

            if(countBean.getName().equals("全部")){
                tv.setText(countBean.getName());
            }else {
                tv.setText(countBean.getName() + " " + countBean.getCount() + "");
            }

            if(countBean.isCheck){
                tv.setTextColor(context.getResources().getColor(R.color.white));
                tv.setBackgroundResource(R.drawable.bg_radio);
            }else{
                tv.setTextColor(context.getResources().getColor(R.color.app_text));
                tv.setBackgroundResource(R.drawable.bg_radio3);
            }


        }

        @Override
        public void onClick(View view) {

            for(DependentResourcesDataEntity.DataBeanX.CountBean condition2 : items){
                condition2.isCheck = condition2.getName().equals(countBean.getName());
            }
            notifyDataSetChanged();

            onItemClickListener.click(countBean.getName());


        }



    }



    public interface OnItemClickListener{
        void click(String tag);
    }


}

package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juejinchain.android.R;

import java.util.ArrayList;
import java.util.List;

public class MovieNewFilterTagAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;

    private List<Condition> items = new ArrayList<>();

    private OnItemClickListener onItemClickListener;



    public MovieNewFilterTagAdapter(Context context , OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<Condition> itemList){
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
        private Condition condition;

        private TextView tv;

        public ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movietab_condition2, parent, false));
            tv = itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            condition = (Condition) object;

            tv.setText(condition.name);

            if(condition.isCheck){
                tv.setTextColor(context.getResources().getColor(R.color.white));
                tv.setBackgroundResource(R.drawable.bg_filterradio);
            }else{
                tv.setTextColor(context.getResources().getColor(R.color.app_text));
                tv.setBackgroundResource(R.drawable.bg_filterradio2);
            }


        }

        @Override
        public void onClick(View view) {

                for (Condition condition2 : items) {
                    condition2.isCheck = condition2.name.equals(condition.name);
                }
                notifyDataSetChanged();

                onItemClickListener.click(condition.name);


        }



    }

    public static class Condition{
        public boolean isCheck = false;
        public String name = "";

        public Condition(){

        }

        public Condition(boolean isCheck , String name){
            this.isCheck = isCheck;
            this.name = name;
        }
    }

    public interface OnItemClickListener{
        void click(String tag);
    }


}

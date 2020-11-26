package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.newsuper.t.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用于平均分的textview的grid
 */
public class TempAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private Context context;
    private List<String> items = new ArrayList<>();

    public TempAdapter(Context context){
        this.context = context;
    }

    public void update(List<String> items){
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemView(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }


    class ItemView extends MyViewHolder{


        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_look, parent, false));
        }

        public void setData(Object object) {


        }
    }

}

package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.newsuper.t.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/31 0031.
 */

public class PictureSelectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private Set<String> set ;
    public PictureSelectAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
        set =  new HashSet<>();
    }
    public void saveSelectPath(String s){
        set.add(s);
        notifyDataSetChanged();
    }
    public void removeSelectPath(String s){
        set.remove(s);
        notifyDataSetChanged();
    }
    public int getSelectCount(){
        return set.size();
    }
    public boolean isSelected(String s){
        return set.contains(s);
    }
    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PictureSelectViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_pic_select, null);
            holder = new PictureSelectViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PictureSelectViewHolder)convertView.getTag();
        }
        if (position == 0){
            holder.ivPic.setImageResource(R.mipmap.camera_icon_camera_3x);
            holder.ivSelect.setVisibility(View.GONE);
        }else {
            Picasso.with(context).load(list.get(position - 1)).into(holder.ivPic);
            if (set.contains(list.get(position - 1))){
                holder.ivPic.setImageResource(R.mipmap.camera_icon_xuan_2x);
                holder.ivSelect.setVisibility(View.VISIBLE);
            }else {
                holder.ivPic.setImageResource(R.mipmap.camera_icon_2x);
                holder.ivSelect.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    static class PictureSelectViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.iv_select)
        ImageView ivSelect;

        PictureSelectViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

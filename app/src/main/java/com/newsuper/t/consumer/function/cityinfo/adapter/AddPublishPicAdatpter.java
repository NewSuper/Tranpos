package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.newsuper.t.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class AddPublishPicAdatpter extends BaseAdapter {
    private Context context;

    public AddPublishPicAdatpter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
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
        AddPublishPicViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_add_pic, null);
            viewHolder = new AddPublishPicViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (AddPublishPicViewHolder)convertView.getTag();
        }
        if (getCount() == 1){
            viewHolder.ivPic.setImageResource(R.mipmap.recruit_add_3x);
        }else {
            if (getCount() - 1 == position){
                viewHolder.ivPic.setImageResource(R.mipmap.recruit_add_3x);
            }else {
                Picasso.with(context).load("").into(viewHolder.ivPic);
            }
        }
        return convertView;
    }

    static class AddPublishPicViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;

        AddPublishPicViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

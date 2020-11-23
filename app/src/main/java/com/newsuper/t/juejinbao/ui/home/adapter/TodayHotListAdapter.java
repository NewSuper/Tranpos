package com.newsuper.t.juejinbao.ui.home.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.BaseAdapter;
import com.newsuper.t.juejinbao.base.BaseHolder;
import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;


import java.util.List;

public class TodayHotListAdapter extends BaseAdapter<TodayHotEntity.DataBean> {

    public TodayHotListAdapter(List<TodayHotEntity.DataBean> list, Activity context) {
        super(list, context);
    }

    @Override
    protected int getContentView(int viewType) {
        return R.layout.item_today_hot_list;
    }

    @Override
    protected void covert(BaseHolder holder, TodayHotEntity.DataBean data, int position) {
        TextView tvNo = holder.getView().findViewById(R.id.tv_No);
        TextView tvName = holder.getView().findViewById(R.id.tv_name);
        TextView tvNum = holder.getView().findViewById(R.id.tv_num);
        ImageView ivTag = holder.getView().findViewById(R.id.iv_tag);

        tvNo.setText(String.format("%s", position+1));
        tvNo.setTextColor(position < 3 ? mContext.getResources().getColor(R.color.c_f85535) : mContext.getResources().getColor(R.color.c_f8A335));
        tvName.setText(data.getHot_word());
        tvNum.setText(data.getHot_value());
        if(TextUtils.isEmpty(data.getLabel_image())){
            ivTag.setVisibility(View.INVISIBLE);
        }else{
            ivTag.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(data.getLabel_image()).into(ivTag);
        }
    }
}

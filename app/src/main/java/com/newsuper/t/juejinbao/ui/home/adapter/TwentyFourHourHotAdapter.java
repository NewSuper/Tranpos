package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.activity.HomeDetailActivity;
import com.newsuper.t.juejinbao.ui.home.entity.HotPointEntity;
import com.newsuper.t.juejinbao.utils.RecyclerViewAdapterHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwentyFourHourHotAdapter extends RecyclerViewAdapterHelper<HotPointEntity.HotPoint> {


    private Context context;
    private ItemClickListener itemClickListener;
    private int type = 0;
    private String textSizeLevel = "middle";
    private String today;

    public TwentyFourHourHotAdapter(Context context, List list) {
        super(context, list);
        this.context = context;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_twenty_four_hour_hot_adapter, parent, false);
        return new MyViewHolder(view);
    }

    public void setTextSizeLevel(String level) {
        textSizeLevel = level;
        notifyDataSetChanged();
    }

    private void setTextSize(TextView content) {
        switch (textSizeLevel) {
            case "small":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }

    private void setTextSizeSmall(TextView content) {
        switch (textSizeLevel) {
            case "small":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.todayhot_small));
                break;
            case "middle":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.todayhot_middle));
                break;
            case "big":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.todayhot_big));
                break;
            case "large":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.todayhot_lager));
                break;
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        HotPointEntity.HotPoint hotPointEntity = mList.get(position);
        //设置字体大小
        setTextSize(mHolder.tv);


        mHolder.tvRank.setText(( position + 1) + "");
        mHolder.tv.setText(mList.get(position).getTitle());

        if(position < 3){
            mHolder.tvRank.setTextColor(Color.parseColor("#F85535"));
        }else{
            mHolder.tvRank.setTextColor(Color.parseColor("#F8A335"));
        }

//                        if(position < 5){
        mHolder.rlHot.setVisibility(View.VISIBLE);
        int random=(int)(Math.random()*3+1);
        switch (random){
            case 1:
                mHolder.tvHot.setText("荐");
                mHolder.tvHot.setBackgroundResource(R.drawable.bg_jian);
                break;
            case 2:
                mHolder.tvHot.setText("热");
                mHolder.tvHot.setBackgroundResource(R.drawable.bg_re);
                break;
            case 3:
                mHolder.tvHot.setText("沸");
                mHolder.tvHot.setBackgroundResource(R.drawable.bg_fei);
                break;
        }
//
//                        }else{
//                            rl_hot.setVisibility(View.GONE);
//                        }

        switch (position){
            case 0:
                mHolder.tv2.setText((int)(Math.random()* (6800000-5800000)) + 5800000 + "");
                break;
            case 1:
                mHolder.tv2.setText((int)(Math.random()* (5800000-5000000)) + 5000000 + "");
                break;
            case 2:
                mHolder.tv2.setText((int)(Math.random()* (4800000-4000000)) + 4000000 + "");
                break;
            case 3:
                mHolder.tv2.setText((int)(Math.random()* (4000000-3000000)) + 3000000 + "");
                break;
            case 4:
                mHolder.tv2.setText((int)(Math.random()* (3000000-2000000)) + 2000000 + "");
                break;
            case 5:
                mHolder.tv2.setText((int)(Math.random()* (2000000-1500000)) + 1500000 + "");
                break;
            case 6:
                mHolder.tv2.setText((int)(Math.random()* (1500000-1000000)) + 1000000 + "");
                break;
            case 7:
                mHolder.tv2.setText((int)(Math.random()* (1000000-900000)) + 900000 + "");
                break;
            case 8:
                mHolder.tv2.setText((int)(Math.random()* (900000-800000)) + 800000 + "");
                break;
            case 9:
                mHolder.tv2.setText((int)(Math.random()* (800000-600000)) + 600000 + "");
                break;
        }

        //点击跳转到详情
        mHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomeDetailActivity.class);
                intent.putExtra("id", String.valueOf(hotPointEntity.getId()));
                mContext.startActivity(intent);
            }
        });
    }

    private String formatDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        return sdf.format(new Date(date));
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setToDay(String today) {
        this.today = today;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rank)
        TextView tvRank;
        @BindView(R.id.tv_hot)
        TextView tvHot;
        @BindView(R.id.rl_hot)
        RelativeLayout rlHot;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tv2)
        TextView tv2;
        @BindView(R.id.img_arrows)
        ImageView imgArrows;
        @BindView(R.id.root)
        LinearLayout root;
        private final SparseArray<View> views;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.views = new SparseArray<>();
        }

        public <T extends View> T getView(@IdRes int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }
    }

    public interface ItemClickListener {
        void onClick(int position, View view);

        void onRemove(int position);
    }


}

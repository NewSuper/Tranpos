package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.activity.HomeDetailActivity;
import com.newsuper.t.juejinbao.ui.home.entity.HotPointEntity;
import com.newsuper.t.juejinbao.utils.RecyclerViewAdapterHelper;


import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TodayHotAdapter extends RecyclerViewAdapterHelper<HotPointEntity.HotPoint> {
    private Context context;
    private ItemClickListener itemClickListener;
    private int type = 0;
    private String textSizeLevel = "middle";
    private String today;

    public TodayHotAdapter(Context context, List list) {
        super(context, list);
        this.context = context;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_today_hot_adapter, parent, false);
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
        setTextSize(mHolder.mTvTitle);
        setTextSizeSmall(mHolder.mTvDesc);

        if (position == 0) {
            //第一个条目要隐藏掉
            mHolder.fistGone.setVisibility(View.GONE);
            //月份日期
            mHolder.mTvMonth.setVisibility(View.VISIBLE);
            mHolder.mTvMonth.setText(today);
            //左边图标
            mHolder.mIvStart.setBackgroundResource(R.mipmap.icon_today_hot_circle);
        } else {
            //把不同日期的条目标注出来
            if (formatDate(hotPointEntity.getPublish_time() * 1000l).equals(formatDate(mList.get(position - 1).getPublish_time() * 1000l))) {
                mHolder.fistGone.setVisibility(View.VISIBLE);
                mHolder.mTvMonth.setVisibility(View.GONE);
                mHolder.mIvStart.setBackgroundResource(R.mipmap.icon_today_hot_oval);
            } else {
                mHolder.fistGone.setVisibility(View.GONE);
                mHolder.mTvMonth.setVisibility(View.VISIBLE);
                mHolder.mTvMonth.setText(formatDate(hotPointEntity.getPublish_time() * 1000l));
                mHolder.mIvStart.setBackgroundResource(R.mipmap.icon_today_hot_circle);
            }
        }

        //时间
        mHolder.mTvTime.setText(hotPointEntity.getShort_publish_time());
        //标题
        mHolder.mTvTitle.setText(hotPointEntity.getTitle());
        //描述
        mHolder.mTvDesc.setText(hotPointEntity.getDescription());
        //图片
        Glide.with(mContext).load(hotPointEntity.getImg_url().get(0)).into(mHolder.mIvPic);
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
        @BindView(R.id.root)
        View root;
        @BindView(R.id.first_gone)
        View fistGone;
        @BindView(R.id.tv_month)
        TextView mTvMonth;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.iv_pic)
        ImageView mIvPic;
        @BindView(R.id.iv_start)
        ImageView mIvStart;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_desc)
        TextView mTvDesc;
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

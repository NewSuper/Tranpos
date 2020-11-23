package com.newsuper.t.juejinbao.ui.home.adapter;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.BaseAdapter;
import com.newsuper.t.juejinbao.base.BaseHolder;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JzvdStd;

public class NewListPagerAdapter extends BaseAdapter<HomeListEntity.DataBean> {


    @BindView(R.id.img_video)
    ImageView imgVideo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_readTimes)
    TextView tvReadTimes;
    @BindView(R.id.tv_videoLength)
    TextView tvVideoLength;

    @BindView(R.id.jzvdPlayer)
    JzvdStd jzvdPlayer;

    @BindView(R.id.tv_from)
    TextView tvFrom;

    @BindView(R.id.tv_comments)
    TextView tvComments;

    public NewListPagerAdapter(List<HomeListEntity.DataBean> list, Activity context) {
        super(list, context);
    }

    @Override
    protected int getContentView(int viewType) {
        return R.layout.item_pager_video;
    }

    @Override
    protected void covert(BaseHolder holder, HomeListEntity.DataBean data, int position) {
        ButterKnife.bind(holder.getView());
        tvTitle.setText(data.getTitle());
    }
}

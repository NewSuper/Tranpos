package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityMovienewtabRankBinding;
import com.juejinchain.android.databinding.ActivityRecommentVideoListBinding;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.movie.adapter.MovieNewTabActivityListAdapter;
import com.juejinchain.android.module.movie.adapter.RecommentVideoListAdapter;
import com.juejinchain.android.module.movie.entity.MovieIndexRecommendEntity;
import com.juejinchain.android.module.movie.entity.MovieInfoEntity;
import com.juejinchain.android.module.movie.entity.MovieNewTabRankEntity;
import com.juejinchain.android.module.movie.entity.MovieRotationListEntity;
import com.juejinchain.android.module.movie.entity.RecommendRankingEntity;
import com.juejinchain.android.module.movie.presenter.impl.MovieNewTabRankImpl;
import com.juejinchain.android.module.movie.presenter.impl.RecommentVideoListImpl;
import com.juejinchain.android.module.share.dialog.ShareDialog;
import com.juejinchain.android.module.share.entity.ShareInfo;
import com.juejinchain.android.utils.GetPicByView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.EventID;
import com.ys.network.base.LoginEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 电影/电视剧 榜单
 */
public class MovieNewTabRankActivity extends BaseActivity<MovieNewTabRankImpl, ActivityMovienewtabRankBinding> implements MovieNewTabRankImpl.MvpView {

    private static final String RECOMMENT_DATA = "";
    private MovieIndexRecommendEntity.DataBeanX.RankBean dataBean;
    int page = 1;
    private MovieNewTabActivityListAdapter mAdapter;
    List<MovieNewTabRankEntity.DataBeanX.DataBean> dataList = new ArrayList<>();


    public static void intentMe(Context context, MovieIndexRecommendEntity.DataBeanX.RankBean bean) {
        Intent intent = new Intent(context, MovieNewTabRankActivity.class);
        intent.putExtra(RECOMMENT_DATA, bean);
        context.startActivity(intent);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movienewtab_rank;
    }

    @Override
    public void initView() {
        dataBean = (MovieIndexRecommendEntity.DataBeanX.RankBean) getIntent().getSerializableExtra(RECOMMENT_DATA);
//        if(dataBean.getLists().size() > 0) {
//            Glide.with(mActivity).load(dataBean.getLists().get(0).getVod_pic()).into(mViewBinding.ivPoster);
//        }

        mViewBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                Map<String, String> map = new HashMap<>();
                map.put("page", page + "");
                map.put("pre_page", 10 + "");
                map.put("type", dataBean.getAlias());
                mPresenter.requestNewTabRankList(mActivity, map, page);
            }
        });

        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                Map<String, String> map = new HashMap<>();
                map.put("page", page + "");
                map.put("pre_page", 10 + "");
                map.put("type", dataBean.getAlias());
                mPresenter.requestNewTabRankList(mActivity, map, page);
            }
        });

//        mViewBinding.srl.setEnableLoadMore(false);


        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.rv.setNestedScrollingEnabled(false);

        mAdapter = new MovieNewTabActivityListAdapter(this);

        mViewBinding.rv.setAdapter(mAdapter);
        mAdapter.update(dataList);
    }

    @Override
    public void initData() {
        mViewBinding.loading.showLoading();
        page = 1;
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("pre_page", 10 + "");
        map.put("type", dataBean.getAlias());
        mPresenter.requestNewTabRankList(mActivity, map, page);
    }


    @Override
    public void requestNewTabRankList(MovieNewTabRankEntity entity, int page) {
        mViewBinding.loading.showContent();
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();


        mViewBinding.srl.setEnableLoadMore(true);
//        mViewBinding.tvDesc.setText(new StringBuilder().append("共").append(entity.getData().getTotal()).append("部"));
//        mViewBinding.tvSubtitle.setText(new StringBuilder().append("片单列表·共").append(entity.getData().getTotal()).append("部"));
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
        if (page == 1) {
            mViewBinding.tvTitle.setText(entity.getData().getSection().getName());
            mViewBinding.tvSmallTitle.setText(entity.getData().getSection().getSub_title());
            Glide.with(mActivity).asBitmap().load(entity.getData().getSection().getHeader_icon()).into(new BitmapImageViewTarget(mViewBinding.iv) {
                @Override
                public void onResourceReady (@NonNull Bitmap resource,
                                             @Nullable Transition<? super Bitmap> transition) {
                    mViewBinding.iv.setImageBitmap(resource);



                    //同步方式
//                        Palette palette = Palette.generate(resource);
                    //异步任务---可能分析的图片会比较大或者颜色分布比较复杂，会耗时比较久，防止卡死主线程。
                    Palette.from(resource).generate(new Palette.PaletteAsyncListener() {

                        @Override
                        public void onGenerated(Palette palette) {
                            //暗、柔和的颜色
                            int darkMutedColor = palette.getDarkMutedColor(Color.WHITE);//如果分析不出来，则返回默认颜色
                            //暗、柔和
                            int lightMutedColor = palette.getLightMutedColor(Color.WHITE);
                            //暗、鲜艳
                            int darkVibrantColor = palette.getDarkVibrantColor(Color.WHITE);
                            //亮、鲜艳
                            int lightVibrantColor = palette.getLightVibrantColor(Color.WHITE);
                            //柔和
                            int mutedColor = palette.getMutedColor(Color.WHITE);
//                                //柔和
                            int vibrantColor = palette.getVibrantColor(Color.WHITE);

                            if(mActivity != null){
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mViewBinding.ivBg.setBackgroundColor(mutedColor);
                                    }
                                });
                            }

                        }
                    });


                }
            });
//            Glide.with(mActivity).load(entity.getData().getSection().getHeader_bg()).into(mViewBinding.ivPoster);
            if (entity.getData().getData().size() > 0) {
                Glide.with(mActivity).load(entity.getData().getData().get(0).getVod_pic()).into(mViewBinding.ivPoster);
            }
            //刷新
            dataList.clear();
            dataList.addAll(entity.getData().getData());

        } else {
            //加载
            dataList.addAll(entity.getData().getData());
        }
        int total = entity.getData().getTotal();
        if (page >= total / 10) {
            mViewBinding.srl.finishLoadMoreWithNoMoreData();
            mViewBinding.srl.setEnableFooterFollowWhenNoMoreData(true);
        }
        mAdapter.update(dataList);
    }


    @Override
    public void error(String string) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
    }
}

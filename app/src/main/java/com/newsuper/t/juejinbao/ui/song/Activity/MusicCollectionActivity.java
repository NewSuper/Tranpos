package com.newsuper.t.juejinbao.ui.song.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityMusiccollectionBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter2;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.song.entity.MusicCollectionEntity;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.ui.song.presenter.impl.MusicCollectionImpl;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;


import java.util.ArrayList;
import java.util.List;

public class MusicCollectionActivity extends BaseActivity<MusicCollectionImpl, ActivityMusiccollectionBinding> implements MusicCollectionImpl.MvpView {

    EasyAdapter2 adapter = null;

    List<MusicCollectionEntity.DataBean> dataBeans = new ArrayList<>();

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_musiccollection;
    }

    @Override
    public void initView() {
        mViewBinding.loading.setEmptyView(R.layout.empty_view_movie_detail);
        mViewBinding.loading.setEmptyText("暂时没有收藏歌单");

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity , 3);
        mViewBinding.rv.setNestedScrollingEnabled(false);
        mViewBinding.rv.setLayoutManager(gridLayoutManager);
        mViewBinding.rv.setAdapter(adapter = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {


            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_musiccollection, viewGroup) {

                    private MusicCollectionEntity.DataBean dataBean;

                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        dataBean = (MusicCollectionEntity.DataBean) object;

                        RequestOptions options = new RequestOptions()
                                .placeholder(R.mipmap.emptystate_pic)

                                .error(R.mipmap.emptystate_pic)
                                .skipMemoryCache(true)
//                                .centerCrop()
                                .dontAnimate()
//                        .override(StringUtils.dip2px(context, 100), StringUtils.dip2px(context, 200))
                                .transform(new MultiTransformation(new CenterCrop(),new RoundedCorners(10)))

                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        try {
                            if (itemView.findViewById(R.id.iv) != null) {
                                //带白色边框的圆形图片加载
                                Glide.with(mActivity).asBitmap().load(dataBean.getThumbnail())
                                        .apply(options)
//                                .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(((ImageView) itemView.findViewById(R.id.iv)));
                            }
                        } catch (Exception e) {
                            Log.e("zy", "glide_Exception");
                        }

                        ((TextView)itemView.findViewById(R.id.tv_count)).setText(Utils.FormatW(dataBean.getPlay_count()) );
                        ((TextView)itemView.findViewById(R.id.tv)).setText(dataBean.getName());
                    }

                    @Override
                    public void onClick(View view) {
                        MusicMenuActivity.intentMe(mActivity , Constant.MUSIC_SONGS , dataBean.getId() + "" , null);
                    }

                };
            }


        }));

        adapter.update(dataBeans);

        mViewBinding.rlLikesong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicMenuActivity.intentMe(mActivity, Constant.MUSIC_COLLECTION , null,null);
            }
        });

    }

    @Override
    public void initData() {

    }




    public static void intentMe(Context context){
        Intent intent = new Intent(context , MusicCollectionActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void gedanCollection(MusicCollectionEntity musicCollectionEntity) {
        dataBeans.clear();
        dataBeans.addAll(musicCollectionEntity.getData());
        adapter.update(dataBeans);

        if(dataBeans.size() == 0){
            mViewBinding.loading.showEmpty();

        }else{
            mViewBinding.loading.showContent();
        }
    }

    @Override
    public void error(String string) {
        MyToast.show(mActivity , string);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SongPlayManager.getInstance().getSongList() != null && SongPlayManager.getInstance().getSongList().size() > 0){
            mViewBinding.bottomController.setVisibility(View.VISIBLE);
            mViewBinding.bottomController.setSongBean(SongPlayManager.getInstance().getCurrentSongInfo());
        }else{
//            mViewBinding.bottomController.setVisibility(View.GONE);
        }

        mPresenter.gedanCollection(mActivity);
    }

}

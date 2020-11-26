package com.newsuper.t.juejinbao.ui.song.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.newsuper.t.databinding.ActivitySongslistBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter2;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.song.entity.ClassAreaDetailEntity;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.ui.song.presenter.impl.SongsListImpl;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;


import java.util.ArrayList;
import java.util.List;

public class SongsListActivity extends BaseActivity<SongsListImpl, ActivitySongslistBinding> implements SongsListImpl.MvpView {

    EasyAdapter2 adapter1 = null;

    private String id;

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
        return R.layout.activity_songslist;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        id = getIntent().getStringExtra("id");

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity , 3);
        mViewBinding.rv.setLayoutManager(gridLayoutManager);
        mViewBinding.rv.setAdapter(adapter1 = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {


            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_songslist, viewGroup) {

                    private ClassAreaDetailEntity.DataBean dataBean;

                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        dataBean = (ClassAreaDetailEntity.DataBean) object;

                        RequestOptions options = new RequestOptions()
                                .placeholder(R.mipmap.gedan_defualt)

                                .error(R.mipmap.gedan_defualt)
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
                        ((TextView)itemView.findViewById(R.id.tv)).setText(dataBean.getTitle());

                    }

                    @Override
                    public void onClick(View view) {
                        MusicMenuActivity.intentMe(mActivity , Constant.MUSIC_SONGS , dataBean.getId() + "" , null);
                    }

                };
            }


        }));



        mPresenter.classAreaDetail(mActivity , id);
    }


    public static void intentMe(Context context , String id){
        Intent intent = new Intent(context , SongsListActivity.class);
        intent.putExtra("id" , id);
        context.startActivity(intent);
    }

    @Override
    public void classAreaDetail(ClassAreaDetailEntity bean) {
        adapter1.update(bean.getData());
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
    }
}

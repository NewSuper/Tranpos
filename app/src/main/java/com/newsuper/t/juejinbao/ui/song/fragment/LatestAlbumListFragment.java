package com.newsuper.t.juejinbao.ui.song.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.newsuper.t.databinding.FragmentLatestalbumlistBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.song.Activity.MusicMenuActivity;
import com.newsuper.t.juejinbao.ui.song.entity.LatestAlbumListEntity;
import com.newsuper.t.juejinbao.ui.song.presenter.impl.LatestAlbumListImpl;


import java.util.ArrayList;
import java.util.List;

public class LatestAlbumListFragment extends BaseFragment<LatestAlbumListImpl , FragmentLatestalbumlistBinding> implements LatestAlbumListImpl.MvpView {

    private String tag;

    EasyAdapter adapter1 = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_latestalbumlist, container, false);
    }

    @Override
    public void initView() {
        tag = getArguments().getString("tag");


        mViewBinding.loading.showLoading();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity , 2);
        mViewBinding.rv.setLayoutManager(gridLayoutManager);
        mViewBinding.rv.setAdapter(adapter1 = new EasyAdapter(mActivity, new EasyAdapter.CommonAdapterListener() {
            @Override
            public EasyAdapter.MyViewHolder getHeaderViewHolder(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public EasyAdapter.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter.MyViewHolder(mActivity, R.layout.item_latestalbum, viewGroup) {

                    private LatestAlbumListEntity.DataBean bean;

                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean, int position) {
                        super.setData(typeBean, position);
                        bean = (LatestAlbumListEntity.DataBean) typeBean;

                        RequestOptions options = new RequestOptions()
                                .placeholder(R.mipmap.emptystate_pic)

                                .error(R.mipmap.emptystate_pic)
                                .skipMemoryCache(true)
                                .transform(new MultiTransformation(new CenterCrop(),new RoundedCorners(10)))
//                                .centerCrop()
                                        .dontAnimate()
//                        .override(StringUtils.dip2px(context, 100), StringUtils.dip2px(context, 200))
//                                .bitmapTransform(new RoundedCorners(10))
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        try {
                            if (itemView.findViewById(R.id.iv) != null) {
                                //带白色边框的圆形图片加载
                                Glide.with(context).asBitmap().load(bean.getThumbnail())
                                        .apply(options)
//                                .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(((ImageView) itemView.findViewById(R.id.iv)));
                            }
                        } catch (Exception e) {
                            Log.e("zy", "glide_Exception");
                        }

                        ((TextView)itemView.findViewById(R.id.tv_title)).setText(bean.getTitle());
                        ((TextView)itemView.findViewById(R.id.tv_content)).setText(bean.getSubtitle());

                    }

                    @Override
                    public void onClick(View view) {
                        MusicMenuActivity.intentMe(mActivity , Constant.MUSIC_ALBUM , bean.getId() , null);
                    }

                };
            }

            @Override
            public EasyAdapter.MyViewHolder getFooterViewHolder(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public EasyAdapter.MyViewHolder getBlankViewHolder(ViewGroup viewGroup) {
                return null;
            }
        }));

        mPresenter.albumList(mActivity , tag);


    }

    @Override
    public void initData() {

    }


    @Override
    public void albumList(LatestAlbumListEntity latestAlbumListEntity) {
        mViewBinding.loading.showContent();

        adapter1.update(latestAlbumListEntity.getData());
    }

    @Override
    public void error(String errResponse) {

    }
}

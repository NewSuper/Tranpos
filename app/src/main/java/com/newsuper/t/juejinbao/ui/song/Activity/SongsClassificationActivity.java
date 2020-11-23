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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivitySongsclassificationBinding;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.EasyAdapter2;
import com.juejinchain.android.module.song.entity.ClassAreaEntity;
import com.juejinchain.android.module.song.presenter.impl.SongsClassificationImpl;
import com.ys.network.base.BaseActivity;
import com.ys.network.utils.androidUtils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class SongsClassificationActivity extends BaseActivity<SongsClassificationImpl, ActivitySongsclassificationBinding> implements  SongsClassificationImpl.MvpView {

    EasyAdapter2 adapter1 = null;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_songsclassification;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @Override
    public void initView() {
        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity , 2);
        mViewBinding.rv.setLayoutManager(gridLayoutManager);
        mViewBinding.rv.setAdapter(adapter1 = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {


            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_gedanfenlei, viewGroup) {

                    private ClassAreaEntity.DataBean dataBean;

                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        dataBean = (ClassAreaEntity.DataBean) object;

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

                    }

                    @Override
                    public void onClick(View view) {
                        SongsListActivity.intentMe(mActivity , dataBean.getId() + "");
                    }

                };
            }


        }));

//        adapter1.update(list1);

        mPresenter.classArea(mActivity);

    }

    public static void intentMe(Context context){
        Intent intent = new Intent(context , SongsClassificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void classArea(ClassAreaEntity bean) {
        adapter1.update(bean.getData());
    }
}

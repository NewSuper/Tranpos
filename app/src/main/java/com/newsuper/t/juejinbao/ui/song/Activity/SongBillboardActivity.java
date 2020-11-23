package com.newsuper.t.juejinbao.ui.song.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.databinding.ActivitySongbillboardBinding;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.EasyAdapter2;
import com.juejinchain.android.module.song.entity.SongBillBoardEntity;
import com.juejinchain.android.module.song.manager.SongPlayManager;
import com.juejinchain.android.module.song.presenter.impl.SongBillboardImpl;
import com.ys.network.base.BaseActivity;
import com.ys.network.utils.androidUtils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌曲榜单
 */
public class SongBillboardActivity extends BaseActivity<SongBillboardImpl , ActivitySongbillboardBinding> implements SongBillboardImpl.MvpView {

    private List<SongBillBoardEntity.DataBean> dataBeans;

    private EasyAdapter2 adapter1;
    private EasyAdapter2 adapter2;


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
        return R.layout.activity_songbillboard;
    }

    @Override
    public void initView() {

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv1.setLayoutManager(linearLayoutManager1);
        mViewBinding.rv1.setAdapter(adapter1 = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {




            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_songbillboard1, viewGroup) {
                    private SongBillBoardEntity.DataBean dataBean;
                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        dataBean = (SongBillBoardEntity.DataBean) object;

                        ((TextView)itemView.findViewById(R.id.tv)).setText(dataBean.getTitle());

                        if(dataBean.isClick){
                            ((TextView)itemView.findViewById(R.id.tv)).setTextColor(getResources().getColor(R.color.app_color));
                            ((TextView)itemView.findViewById(R.id.tv)).setBackgroundColor(Color.parseColor("#ffffff"));
                        }else{
                            ((TextView)itemView.findViewById(R.id.tv)).setTextColor(getResources().getColor(R.color.app_text3));
                            ((TextView)itemView.findViewById(R.id.tv)).setBackgroundColor(Color.parseColor("#f9f9f9"));

                        }

                        itemView.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setRv2(dataBean);
                            }
                        });

                    }


                };
            }


        }));

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mActivity);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv2.setLayoutManager(linearLayoutManager2);
        mViewBinding.rv2.setAdapter(adapter2 = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {

            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_songbillboard2, viewGroup) {

                    private SongBillBoardEntity.DataBean.ListsBean listsBean;

                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        listsBean = (SongBillBoardEntity.DataBean.ListsBean) object;

                        RequestOptions options = new RequestOptions()
                                .placeholder(R.mipmap.emptystate_pic)

                                .error(R.mipmap.emptystate_pic)
                                .skipMemoryCache(true)
                                .centerCrop()
                                .dontAnimate()
//                        .override(StringUtils.dip2px(context, 100), StringUtils.dip2px(context, 200))
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        try {
                            if (itemView.findViewById(R.id.iv) != null) {
                                //带白色边框的圆形图片加载
                                Glide.with(mActivity).asBitmap().load(listsBean.getThumbnail())
                                        .apply(options)
//                                .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(((ImageView) itemView.findViewById(R.id.iv)));
                            }
                        } catch (Exception e) {
                            Log.e("zy", "glide_Exception");
                        }


                        ((TextView)itemView.findViewById(R.id.tv)).setText(listsBean.getTitle());

                    }

                    @Override
                    public void onClick(View view) {
                        MusicMenuActivity.intentMe(mActivity , Constant.MUSIC_RANK , listsBean.getId() + "" , null);
                    }

                };
            }


        }));

    }

    @Override
    public void initData() {

        mPresenter.allRankList(mActivity);

    }

    private void setRv2(SongBillBoardEntity.DataBean dataBean){

        for(SongBillBoardEntity.DataBean dataBean1 : dataBeans){
            dataBean1.isClick = false;
        }

        dataBean.isClick = true;

        adapter1.update(dataBeans);

        adapter2.update(dataBean.getLists());


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

    public static void intentMe(Context context){
        Intent intent = new Intent(context , SongBillboardActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void allRankList(SongBillBoardEntity songBillBoardEntity) {
        dataBeans = songBillBoardEntity.getData();
        if(dataBeans.size() > 0) {
            setRv2(dataBeans.get(0));
        }
    }

    @Override
    public void error(String errResponse) {

    }
}

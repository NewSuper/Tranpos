package com.newsuper.t.juejinbao.ui.song.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.juejinchain.android.R;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.databinding.FragmentMusicBinding;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.EasyAdapter2;
import com.juejinchain.android.module.song.Activity.LatestAlbumListActivity;
import com.juejinchain.android.module.song.Activity.MusicCollectionActivity;
import com.juejinchain.android.module.song.Activity.MusicMenuActivity;
import com.juejinchain.android.module.song.Activity.NewSongLookActivity;
import com.juejinchain.android.module.song.Activity.SongActivity;
import com.juejinchain.android.module.song.Activity.SongBillboardActivity;
import com.juejinchain.android.module.song.Activity.SongsClassificationActivity;
import com.juejinchain.android.module.song.Activity.SongsListActivity;
import com.juejinchain.android.module.song.adapter.MusicClassifyAdapter;
import com.juejinchain.android.module.song.adapter.MusicListAdapter;
import com.juejinchain.android.module.song.adapter.SongListAdapter;
import com.juejinchain.android.module.song.entity.CategorysBean;
import com.juejinchain.android.module.song.entity.MusicDataListEntity;
import com.juejinchain.android.module.song.entity.SongBean;
import com.juejinchain.android.module.song.manager.SongPlayManager;
import com.juejinchain.android.module.song.presenter.impl.MusicPresenterImpl;
import com.juejinchain.android.utils.ClickUtil;
import com.juejinchain.android.utils.NetUtil;
import com.lzx.starrysky.provider.SongInfo;
import com.ys.network.base.BaseFragment;
import com.ys.network.base.LoginEntity;
import com.ys.network.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class MusicFragment extends BaseFragment<MusicPresenterImpl, FragmentMusicBinding> implements MusicPresenterImpl.MvpView, MusicClassifyAdapter.OnItemClickListener{

    private List<CategorysBean> classifyList = new ArrayList<>();
    private List<SongInfo>  songList = new ArrayList<>();
//    private MusicClassifyAdapter classifyAdapter;
    private MusicListAdapter musicAdapter;

    //推荐歌单
    EasyAdapter2 adapter1 = null;
    private List<CategorysBean>  categorysBeanArrayList = new ArrayList<>();

    //分类专区
    EasyAdapter2 adapter2 = null;
    private List<MusicDataListEntity.DataBean.ClassAreaBean> classAreaBeans = new ArrayList<>();

    //最新专辑
    EasyAdapter2 adapter3 = null;
    private List<MusicDataListEntity.DataBean.LatestAlbumBean> latestAlbumBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void initView() {

        // 热门歌单
//        mViewBinding.rvHot.setLayoutManager(new GridLayoutManager(mActivity,3));
//        mViewBinding.rvHot.setNestedScrollingEnabled(false);
//        classifyAdapter = new MusicClassifyAdapter(mActivity,this);
//        mViewBinding.rvHot.setAdapter(classifyAdapter);
        // 大家在听
        mViewBinding.rvMusic.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewBinding.rvMusic.setNestedScrollingEnabled(false);
        musicAdapter = new MusicListAdapter(songList,mActivity);
        mViewBinding.rvMusic.setAdapter(musicAdapter);
        musicAdapter.setListener(new MusicListAdapter.OnSongClickListener() {
            @Override
            public void onMusicClick(int position) {
                toSongActivity(position);
            }

            @Override
            public void onDelClick(int position) {
                if(SongPlayManager.getInstance().isPlaying()){
                    SongPlayManager.getInstance().pauseMusic();
                    SongInfo songInfo = SongPlayManager.getInstance().getCurrentSongInfo();

                    //当前点击的不是播放中的歌曲，直接跳转到详情页
                    if(!songList.get(position).getSongId().equals(songInfo.getSongId())){
                        toSongActivity(position);
                    }
                }else {
                    toSongActivity(position);
                }

//                if(!SongPlayManager.getInstance().isPaused()){
//                    SongPlayManager.getInstance().pauseMusic();
//                }else{
//                    toSongActivity(position);
//                }
            }
        });

        mViewBinding.srl.setEnableLoadMore(false);
        mViewBinding.srl.setOnRefreshListener(refreshLayout -> mPresenter.getMusicDataList(mActivity));

        mViewBinding.loading.setOnErrorClickListener(view -> {
            if(NetUtil.isNetworkAvailable(mActivity)){
                mPresenter.getMusicDataList(mActivity);
//                mViewBinding.loading.showLoading();
            }else{
                ToastUtils.getInstance().show(mActivity,"网络未连接");
            }
        });

        //展开免责声明
        mViewBinding.zk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.mz1.setVisibility(View.GONE);
                mViewBinding.mz2.setVisibility(View.VISIBLE);
            }
        });
    }


    public void getData(){
        if(NetUtil.isNetworkAvailable(mActivity)){
            mViewBinding.loading.showLoading();
        }else{
            mViewBinding.loading.showError();
        }
        mPresenter.gedanCollection(mActivity);
        mPresenter.getMusicDataList(mActivity);
        mPresenter.getMusicCollectList(mActivity);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        getData();
    }

    @Override
    public void initData() {

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvTuijiangedan.setLayoutManager(linearLayoutManager1);
        mViewBinding.rvTuijiangedan.setAdapter(adapter1 = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {

            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_musicfragment_tuijiangedan, viewGroup) {


                    private CategorysBean categorysBean;
                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        categorysBean = (CategorysBean) object;

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
                                Glide.with(context).asBitmap().load(categorysBean.getThumbnail())
                                        .apply(options)
//                                .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(((ImageView) itemView.findViewById(R.id.iv)));
                            }
                        } catch (Exception e) {
                            Log.e("zy", "glide_Exception");
                        }

                        ((TextView)itemView.findViewById(R.id.tv)).setText(categorysBean.getTitle());

                    }

                    @Override
                    public void onClick(View view) {
                        MusicMenuActivity.intentMe(mActivity, Constant.MUSIC_SONGS , categorysBean.getCid(), null);
                    }

                };
            }

        }));
        adapter1.update(categorysBeanArrayList);

        mViewBinding.tvMoreTuijiangedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongsClassificationActivity.intentMe(mActivity);
            }
        });

        //分类专区
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mActivity);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvFenleizhuanqu.setLayoutManager(linearLayoutManager2);
        mViewBinding.rvFenleizhuanqu.setAdapter(adapter2 = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {


            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_musicfragment_fenleizhuanqu, viewGroup) {

                    private MusicDataListEntity.DataBean.ClassAreaBean classAreaBean;
                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        classAreaBean = (MusicDataListEntity.DataBean.ClassAreaBean) object;

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
                                Glide.with(context).asBitmap().load(classAreaBean.getThumbnail())
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
                        SongsListActivity.intentMe(mActivity , classAreaBean.getId() + "");
                    }

                };
            }
        }));
        mViewBinding.tvMoreFenleizhuanqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongsClassificationActivity.intentMe(mActivity);
            }
        });
        adapter2.update(classAreaBeans);


        //最新专辑
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvZuixinzhuanji.setLayoutManager(linearLayoutManager3);
        mViewBinding.rvZuixinzhuanji.setAdapter(adapter3 = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {


            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_musicfragment_zuixinzhuanji, viewGroup) {

                    private MusicDataListEntity.DataBean.LatestAlbumBean albumBean;
                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        albumBean = (MusicDataListEntity.DataBean.LatestAlbumBean) object;
                        RequestOptions options = new RequestOptions()
                                .placeholder(R.mipmap.emptystate_pic)

                                .error(R.mipmap.emptystate_pic)
                                .skipMemoryCache(true)
//                                .centerCrop()
                                .dontAnimate()
//                        .override(StringUtils.dip2px(context, 100), StringUtils.dip2px(context, 200))
//                                .bitmapTransform(new RoundedCorners(10))
                                .transform(new MultiTransformation(new CenterCrop(),new RoundedCorners(10)))

                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        try {
                            if (itemView.findViewById(R.id.iv) != null) {
                                //带白色边框的圆形图片加载
                                Glide.with(context).asBitmap().load(albumBean.getThumbnail())
                                        .apply(options)
//                                .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(((ImageView) itemView.findViewById(R.id.iv)));
                            }
                        } catch (Exception e) {
                            Log.e("zy", "glide_Exception");
                        }

                        ((TextView)itemView.findViewById(R.id.tv)).setText(albumBean.getTitle());
                        ((TextView)itemView.findViewById(R.id.tv2)).setText(albumBean.getDesc());
                    }

                    @Override
                    public void onClick(View view) {
                        MusicMenuActivity.intentMe(mActivity , Constant.MUSIC_ALBUM , albumBean.getId() + "" , null);
                    }

                };
            }

        }));
        adapter3.update(latestAlbumBeans);

        mViewBinding.tvMoreZuixinzhuanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatestAlbumListActivity.intentMe(mActivity);
            }
        });
    }

    @OnClick({R.id.tv_like, R.id.tv_history, R.id.tv_hot ,R.id.tv_gequbangdan})
    public void onClick(View view) {
        if (!LoginEntity.getIsLogin()) {
            startActivity(new Intent(mActivity, GuideLoginActivity.class));
            return;
        }

        switch (view.getId()) {
            case R.id.tv_like:
//                MusicMenuActivity.intentMe(mActivity, Constant.MUSIC_COLLECTION,null);
                MusicCollectionActivity.intentMe(mActivity);
                break;
            case R.id.tv_history:
                MusicMenuActivity.intentMe(mActivity,Constant.MUSIC_HISTORY , null ,null);
                break;
            case R.id.tv_hot:
//                MusicMenuActivity.intentMe(mActivity,Constant.MUSIC_POPULAR,null);
                NewSongLookActivity.intentMe(mActivity);
                break;
            case R.id.tv_gequbangdan:
                SongBillboardActivity.intentMe(mActivity);
                break;
        }
    }

    @Override
    public void onMusicClassify(CategorysBean dataBean) {
        MusicMenuActivity.intentMe(mActivity, dataBean.getCid() , null, dataBean);
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getMusicDataList(MusicDataListEntity data) {
        mViewBinding.loading.showContent();
        mViewBinding.srl.finishRefresh();
        if(data.getCode() == 0){
            if (data.getData() == null) {
                return;
            }
            //热门歌单
//            classifyList.clear();
//            classifyList.addAll(data.getData().getCategorys());
//            classifyAdapter.update(classifyList);
            //大家在听
            songList.clear();
            if(data.getData().getSongs()!=null && data.getData().getSongs().size()!=0){
                for (SongBean song:data.getData().getSongs()) {
                    SongInfo songInfo = new SongInfo();
                    songInfo.setSongId(song.getId());
                    songInfo.setSongName(song.getSong_name());
                    songInfo.setSongUrl(song.getSong_url());
                    songInfo.setArtist(song.getSinger());
                    songInfo.setSongCover(song.getImage());
                    songInfo.setDescription(song.getSpecial_name());
                    songInfo.setLanguage(song.getLyric());
                    songList.add(songInfo);
                }
            }
            musicAdapter.notifyDataSetChanged(songList);

            //刷新推荐歌单
            categorysBeanArrayList.clear();
            categorysBeanArrayList.addAll(data.getData().getCategorys());
            adapter1.update(categorysBeanArrayList);

            //刷新分类专区
            classAreaBeans.clear();
            classAreaBeans.addAll(data.getData().getClass_area());
            adapter2.update(classAreaBeans);

            //最新专辑
            latestAlbumBeans.clear();
            latestAlbumBeans.addAll(data.getData().getLatest_album());
            adapter3.update(latestAlbumBeans);
        }else{
            ToastUtils.getInstance().show(mActivity,data.getMsg());
        }
    }

    private void toSongActivity(int position) {
        if(!ClickUtil.isNotFastClick()){
            return;
        }

        if(!LoginEntity.getIsLogin()){
            GuideLoginActivity.start(mActivity,false,"");
            return;
        }

        SongPlayManager.getInstance().addSongListAndPlay(songList,position);
        Intent intent = new Intent(mActivity, SongActivity.class);
        intent.putExtra(SongActivity.SONG_INFO, songList.get(position));
        startActivity(intent);
    }

}

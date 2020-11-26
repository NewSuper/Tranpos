package com.newsuper.t.juejinbao.ui.song.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import com.lzx.starrysky.provider.SongInfo;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityMusicMenuBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.song.adapter.MusicMenuAdapter;
import com.newsuper.t.juejinbao.ui.song.entity.AlbumDetailEntity;
import com.newsuper.t.juejinbao.ui.song.entity.CategorysBean;
import com.newsuper.t.juejinbao.ui.song.entity.MusicCollectEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicHistoryEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicListEntity;
import com.newsuper.t.juejinbao.ui.song.entity.SongBean;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.ui.song.presenter.impl.MusicMenuPresenterImpl;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.view.BlurTransformation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.OnClick;

/**
 * 音乐收藏、历史、热门 以及类别详情页面
 */
public class MusicMenuActivity extends BaseActivity<MusicMenuPresenterImpl, ActivityMusicMenuBinding> implements MusicMenuPresenterImpl.MvpView {

    private String type;
    private String id;
    private CategorysBean category;
    private List<SongInfo> songList = new ArrayList<>();
    private MusicMenuAdapter adapter;

    public static void intentMe(Context context, String type, String id, CategorysBean dataBean) {
        if (!ClickUtil.isNotFastClick()) {
            return;
        }

        Intent intent = new Intent(context, MusicMenuActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        intent.putExtra("data", dataBean);
        context.startActivity(intent);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_music_menu;
    }

    @Override
    public void initView() {
        if (NetUtil.isNetworkAvailable(mActivity)) {
//            mViewBinding.loading.showLoading();
        } else {
            mViewBinding.loading.showError();
        }

        mViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MusicMenuAdapter(songList, this);
        mViewBinding.recyclerView.setNestedScrollingEnabled(false);
        mViewBinding.recyclerView.setAdapter(adapter);
        adapter.setListener(new MusicMenuAdapter.OnSongClickListener() {
            @Override
            public void onMusicClick(int position) {
                if (!ClickUtil.isNotFastClick()) {
                    return;
                }

                toSongActivity(position);
            }

            @Override
            public void onDelClick(int position) {
                if (!ClickUtil.isNotFastClick()) {
                    return;
                }

                switch (type) {
                    case Constant.MUSIC_COLLECTION:// 收藏歌单
                        mPresenter.delCollect(mActivity, songList.get(position).getSongId());
                        break;
                    case Constant.MUSIC_HISTORY:// 历史歌单
                        mPresenter.delHistory(mActivity, songList.get(position).getSongId());
                        break;
                    default:
//                        if(!SongPlayManager.getInstance().isPaused()){
//                            SongPlayManager.getInstance().pauseMusic();
//                        }else{
//                            toSongActivity(position);
//                        }
                        if (SongPlayManager.getInstance().isPlaying()) {
                            SongPlayManager.getInstance().pauseMusic();
                            SongInfo songInfo = SongPlayManager.getInstance().getCurrentSongInfo();

                            //当前点击的不是播放中的歌曲，直接跳转到详情页
                            if (!songList.get(position).getSongId().equals(songInfo.getSongId())) {
                                toSongActivity(position);
                            }
                        } else {
                            toSongActivity(position);
                        }

                        break;
                }
            }
        });

        mViewBinding.appBar.addOnOffsetChangedListener((appBarLayout, i) -> {
            float percent = (Math.abs(i * 1.0f) * 2 / appBarLayout.getTotalScrollRange());
            mViewBinding.titleBg.setAlpha(percent);
        });

        mViewBinding.loading.setOnErrorClickListener(view -> {
            if (NetUtil.isNetworkAvailable(mActivity)) {
                getData();
                mViewBinding.loading.showLoading();
            } else {
                ToastUtils.getInstance().show(mActivity, "网络未连接");
            }
        });


        mViewBinding.loading.setEmptyView(R.layout.empty_view_movie_detail);
        mViewBinding.loading.setEmptyText("暂无歌曲，快去搜索歌曲吧~");
    }

    @Override
    public void initData() {
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        category = (CategorysBean) getIntent().getSerializableExtra("data");
        if (type == null)
            return;
        getData();


    }

    private void getData() {
        mViewBinding.loading.showLoading();
        switch (type) {
            case Constant.MUSIC_COLLECTION:// 收藏歌单
                mPresenter.getMusicCollectList(mActivity);
                mViewBinding.insideFixedBar.ivDeleteAll.setVisibility(View.VISIBLE);
                mViewBinding.header.ivBg.setImageResource(R.mipmap.ic_music_like_bg);
                mViewBinding.titleBg.setImageResource(R.mipmap.ic_music_like_bg);
                mViewBinding.header.ivClassBg.setImageResource(R.mipmap.icon_music_like);
                mViewBinding.header.tvClassify.setText("收藏歌曲");
                mViewBinding.header.tvDesc.setText("只听你喜欢的音乐～");
                break;
            case Constant.MUSIC_HISTORY:// 历史歌单
                mPresenter.getMusicHistoryList(mActivity);
                mViewBinding.insideFixedBar.ivDeleteAll.setVisibility(View.VISIBLE);
                mViewBinding.header.ivBg.setImageResource(R.mipmap.ic_music_history_bg);
                mViewBinding.titleBg.setImageResource(R.mipmap.ic_music_history_bg);
                mViewBinding.header.ivClassBg.setImageResource(R.mipmap.icon_music_history);
                mViewBinding.header.tvClassify.setText("历史歌单");
                mViewBinding.header.tvDesc.setText("记录您的听歌轨迹");
                break;
            case Constant.MUSIC_POPULAR:// 热门歌单
                mPresenter.getMusicList(mActivity, "8");
                mViewBinding.insideFixedBar.ivDeleteAll.setVisibility(View.GONE);
                mViewBinding.header.ivBg.setImageResource(R.mipmap.ic_music_hot_bg);
                mViewBinding.titleBg.setImageResource(R.mipmap.ic_music_hot_bg);
                mViewBinding.header.ivClassBg.setImageResource(R.mipmap.icon_music_hot);
                mViewBinding.header.tvClassify.setText("热门歌单");
                mViewBinding.header.tvDesc.setText("热门新歌速递");
                break;
            //歌单
            case Constant.MUSIC_SONGS:
                //已收藏
                if(mPresenter.isCollectGedan(id)){
                    mViewBinding.insideFixedBar.ivCollect.setBackgroundResource(R.mipmap.gedan_collected);
                }else{
                    mViewBinding.insideFixedBar.ivCollect.setBackgroundResource(R.mipmap.gedan_collect);

                }

                mViewBinding.insideFixedBar.ivCollect.setVisibility(View.VISIBLE);
                mViewBinding.insideFixedBar.ivDeleteAll.setVisibility(View.GONE);
                mPresenter.getMusicList(mActivity, id);
                break;
            //专辑
            case Constant.MUSIC_ALBUM:
                //已收藏
                if(mPresenter.isCollectGedan(id)){
                    mViewBinding.insideFixedBar.ivCollect.setBackgroundResource(R.mipmap.gedan_collected);
                }else{
                    mViewBinding.insideFixedBar.ivCollect.setBackgroundResource(R.mipmap.gedan_collect);

                }

                mViewBinding.insideFixedBar.ivCollect.setVisibility(View.VISIBLE);
                mViewBinding.insideFixedBar.ivDeleteAll.setVisibility(View.GONE);
                mPresenter.albumDetail(mActivity, id);
                break;
            case Constant.MUSIC_RANK:
                //已收藏
                if(mPresenter.isCollectGedan(id)){
                    mViewBinding.insideFixedBar.ivCollect.setBackgroundResource(R.mipmap.gedan_collected);
                }else{
                    mViewBinding.insideFixedBar.ivCollect.setBackgroundResource(R.mipmap.gedan_collect);

                }

                mViewBinding.insideFixedBar.ivCollect.setVisibility(View.VISIBLE);
                mViewBinding.insideFixedBar.ivDeleteAll.setVisibility(View.GONE);
                mPresenter.rankList(mActivity, id);
                break;
            default://分类歌单
                mPresenter.getMusicList(mActivity, type);
                mViewBinding.insideFixedBar.ivDeleteAll.setVisibility(View.GONE);
                if (category != null) {
                    Glide.with(mActivity)
                            .load(category.getThumbnail())
                            .apply(new RequestOptions().placeholder(R.mipmap.ic_music_like_bg).error(R.mipmap.ic_music_like_bg))
                            .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 10)))
                            .into(mViewBinding.header.ivBg);

                    Glide.with(mActivity)
                            .load(category.getThumbnail())
                            .apply(new RequestOptions().placeholder(R.mipmap.ic_music_like_bg).error(R.mipmap.ic_music_like_bg))
                            .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 10)))
                            .into(mViewBinding.titleBg);

                    Glide.with(mActivity).load(category.getThumbnail()).apply(RequestOptions.bitmapTransform(new RoundedCorners(10))).into(mViewBinding.header.ivClassify);
                    mViewBinding.header.tvClassify.setText(category.getTitle());
                    mViewBinding.header.tvDesc.setText(category.getDesc());
                    mViewBinding.header.tvPlayNum.setText(String.format("%s次播放", category.getPlay_count()));
                }
                break;
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_play_all, R.id.iv_delete_all , R.id.iv_collect})
    public void onClick(View view) {
        if (!LoginEntity.getIsLogin()) {
            startActivity(new Intent(mActivity, GuideLoginActivity.class));
            return;
        }

        if (!ClickUtil.isNotFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_play_all:
                toSongActivity(0);
                break;
            case R.id.iv_delete_all:

                new android.support.v7.app.AlertDialog.Builder(mActivity)
                        .setCancelable(true)
                        .setMessage("确定要清空列表吗？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            dialog.dismiss();

                            if (type.equals(Constant.MUSIC_COLLECTION)) {
                                mPresenter.delCollect(mActivity, "-1");

                                //收藏初始化
                                SongPlayManager.getInstance().setCollectList(new HashSet<String>());

                            } else if (type.equals(Constant.MUSIC_HISTORY)) {
                                mPresenter.delHistory(mActivity, "-1");
                            }

                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();


//                if(type.equals(Constant.MUSIC_COLLECTION)){
//                    mPresenter.delCollect(mActivity,"-1");
//                }else if(type.equals(Constant.MUSIC_HISTORY)){
//                    mPresenter.delHistory(mActivity,"-1");
//                }
                break;
                //点击收藏歌单
            case R.id.iv_collect:
                //已收藏，取消收藏
                if(mPresenter.isCollectGedan(id)){

                    new android.support.v7.app.AlertDialog.Builder(mActivity)
                            .setCancelable(true)
                            .setMessage("确定不再收藏此歌单吗？")
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();

                                mPresenter.delCollectSheet(mActivity , id);

                            })
                            .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                            .create()
                            .show();


                }else{
                    mPresenter.addCollectSheet(mActivity , id);
                }

                break;

        }
    }

    @Override
    public void getMusicCollectList(MusicCollectEntity data) {
        mViewBinding.loading.showContent();
        if (data.getCode() == 0) {
            if (data.getData() == null)
                return;
            setViewData(data.getData().getCategorys(), data.getData().getSongs());


        } else {
            ToastUtils.getInstance().show(mActivity, data.getMsg());
        }
    }

    @Override
    public void getMusicHistoryList(MusicHistoryEntity data) {
        mViewBinding.loading.showContent();
        if (data.getCode() == 0) {
            if (data.getData() == null)
                return;

//            Collections.reverse(data.getData().getSongs());

            setViewData(data.getData().getCategorys(), data.getData().getSongs());


        } else {
            ToastUtils.getInstance().show(mActivity, data.getMsg());
        }
    }

    @Override
    public void getMusicList(MusicListEntity data) {
        mViewBinding.loading.showContent();
        if (data.getCode() == 0) {
            if (data.getData() == null)
                return;
            setViewData(data.getData().getCategorys(), data.getData().getSongs());


        } else {
            ToastUtils.getInstance().show(mActivity, data.getMsg());
        }
    }

    @Override
    public void albumDetail(AlbumDetailEntity data) {
        mViewBinding.loading.showContent();
        if (data.getCode() == 0) {
            if (data.getData() == null)
                return;
            setViewData(data.getData().getCategorys(), data.getData().getSongs());


        } else {
            ToastUtils.getInstance().show(mActivity, data.getMsg());
        }
    }

    @Override
    public void rankList(MusicListEntity data) {
        mViewBinding.loading.showContent();
        if (data.getCode() == 0) {
            if (data.getData() == null)
                return;
            setViewData(data.getData().getCategorys(), data.getData().getSongs());


        } else {
            ToastUtils.getInstance().show(mActivity, data.getMsg());
        }
    }

    @Override
    public void delHistory(BaseEntity data) {
        if (data.getCode() == 0) {
            mPresenter.getMusicHistoryList(mActivity);
        } else {
            ToastUtils.getInstance().show(mActivity, data.getMsg());
        }
    }

    @Override
    public void delCollect(BaseEntity data) {
        if (data.getCode() == 0) {
            mPresenter.getMusicCollectList(mActivity);
        } else {
            ToastUtils.getInstance().show(mActivity, data.getMsg());
        }
    }

    @Override
    public void collectSheetResult() {
        //已收藏
        if(mPresenter.isCollectGedan(id)){
            MyToast.show(mActivity , "收藏成功");
            mViewBinding.insideFixedBar.ivCollect.setBackgroundResource(R.mipmap.gedan_collected);
        }else{
            MyToast.show(mActivity , "取消收藏成功");
            mViewBinding.insideFixedBar.ivCollect.setBackgroundResource(R.mipmap.gedan_collect);

        }
    }

    private void setViewData(CategorysBean category, List<SongBean> songs) {
        if (songs.size() == 0) {
            mViewBinding.loading.showEmpty();
        }

        if (type != null && !type.equals(Constant.MUSIC_COLLECTION) && !type.equals(Constant.MUSIC_HISTORY) && !type.equals(Constant.MUSIC_POPULAR)) {
            Glide.with(mActivity)
                    .load(category.getThumbnail())
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_music_like_bg).error(R.mipmap.ic_music_like_bg))
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 10)))
                    .into(mViewBinding.header.ivBg);

            Glide.with(mActivity)
                    .load(category.getThumbnail())
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_music_like_bg).error(R.mipmap.ic_music_like_bg))
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 10)))
                    .into(mViewBinding.titleBg);

            Glide.with(mActivity).load(category.getThumbnail()).apply(RequestOptions.bitmapTransform(new RoundedCorners(10))).into(mViewBinding.header.ivClassify);
        }
        mViewBinding.header.tvClassify.setText(category.getTitle());
        if (!TextUtils.isEmpty(category.getDesc())) {
            mViewBinding.header.tvDesc.setText(category.getDesc());
        }
        mViewBinding.header.tvPlayNum.setText(String.format("%s次播放", category.getPlay_count()));


//            mViewBinding.header.tvDec2.setVisibility(View.VISIBLE);
//            mViewBinding.header.tvDesc.setVisibility(View.INVISIBLE);
//            mViewBinding.header.tvPlayNum.setVisibility(View.GONE);
//
//            mViewBinding.header.tvDec2.setText(category.getDesc());
//        }else{

            mViewBinding.header.tvDesc.setVisibility(View.VISIBLE);
//            mViewBinding.header.tvDec2.setVisibility(View.GONE);
            mViewBinding.header.tvPlayNum.setVisibility(View.VISIBLE);
//        }

        if (type .equals( Constant.MUSIC_ALBUM)) {
            mViewBinding.header.tvDesc.setVisibility(View.INVISIBLE);
        }

        this.category = category;

        getSongList(songs);


    }

    private void getSongList(List<SongBean> songs) {
        songList.clear();
        if (songs == null){
            return;
        }
        mViewBinding.insideFixedBar.tvTotal.setText(String.format("（共%s首）", songs.size()));
        if (songs.size() != 0)
            for (SongBean song : songs) {
                SongInfo songInfo = new SongInfo();
                songInfo.setSongId(song.getId() == null ? song.getSong_id() : song.getId());
                songInfo.setSongName(song.getSong_name());
                songInfo.setSongUrl(song.getSong_url());
                songInfo.setArtist(song.getSinger());
                songInfo.setSongCover(song.getImage());
                songInfo.setDescription(song.getSpecial_name());
                songInfo.setLanguage(song.getLyric());
                songInfo.setMimeType(type);
                songList.add(songInfo);
            }
        adapter.notifyDataSetChanged(songList);


        if (SongPlayManager.getInstance().getSongList() != null) {
            mViewBinding.bottomController.setSongBean(SongPlayManager.getInstance().getCurrentSongInfo());
        }
    }

    @Override
    public void error(String str) {
    }

    private void toSongActivity(int position) {

        if (!LoginEntity.getIsLogin()) {
            GuideLoginActivity.start(mActivity, false, "");
            return;
        }

        if (position >= songList.size()) {
            adapter.notifyDataSetChanged();
            return;
        }

        SongPlayManager.getInstance().addSongListAndPlay(songList, position);



        SongActivity.intentMe(mActivity, songList.get(position));

        //更新bottom状态（当音乐播放不出时的情况）
        if (SongPlayManager.getInstance().getSongList() != null) {
            mViewBinding.bottomController.setSongBean(SongPlayManager.getInstance().getCurrentSongInfo());
        }

        //播放统计
        mPresenter.songIncr(mActivity , SongPlayManager.getInstance().getCurrentSongInfo().getSongId());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapter != null){
            adapter.destory();
            adapter = null;
        }
    }
}

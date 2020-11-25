package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.module.song.entity.LatestMusicListEntity;
import com.lzx.starrysky.provider.SongInfo;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class NewSongLookListImpl extends BasePresenter<NewSongLookListImpl.MvpView>{

    //新歌速递
    public void latestSongList(Context context , String en) {

        Map<String , String> map = new HashMap<>();
        map.put("tag" , en);
        rx.Observable<LatestMusicListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .latestSongList(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LatestMusicListEntity>() {
            @Override
            public void next(LatestMusicListEntity bean) {
                List<SongInfo> songInfos = new ArrayList<>();
                if (bean.getData() != null && bean.getData().size() != 0)
                    for (LatestMusicListEntity.DataBean dataBean : bean.getData()) {
                        SongInfo songInfo = new SongInfo();
                        songInfo.setSongId(dataBean.getId() + "");
                        songInfo.setSongName(dataBean.getSong_name());
                        songInfo.setSongUrl(dataBean.getSong_url());
                        songInfo.setArtist(dataBean.getSinger());
                        songInfo.setSongCover(dataBean.getImage());
                        songInfo.setDescription(dataBean.getSpecial_name());
                        songInfo.setLanguage(dataBean.getLyric());
                        songInfo.setMimeType(Constant.MUSIC_HISTORY);
                        songInfos.add(songInfo);
                    }

                getView().latestSongList(songInfos);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public interface MvpView{
        public void latestSongList(List<SongInfo> songInfoList);
        public void error(String errResponse);
    }

}
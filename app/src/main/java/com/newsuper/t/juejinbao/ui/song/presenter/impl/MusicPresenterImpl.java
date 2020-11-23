package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.song.entity.MusicCollectEntity;
import com.juejinchain.android.module.song.entity.MusicCollectionEntity;
import com.juejinchain.android.module.song.entity.MusicDataListEntity;
import com.juejinchain.android.module.song.entity.SongBean;
import com.juejinchain.android.module.song.manager.SongPlayManager;
import com.ys.network.base.BasePresenter;
import com.ys.network.base.PagerCons;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class MusicPresenterImpl extends BasePresenter<MusicPresenterImpl.MvpView> {


    //收藏歌单列表
    public void getMusicCollectList(Context context) {
        rx.Observable<MusicCollectEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getCollectList().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicCollectEntity>() {
            @Override
            public void next(MusicCollectEntity bean) {

//                SongPlayManager.getInstance().getCollectList();

                Set<String> collectSongIds = new HashSet<>();
                for(SongBean songBean : bean.getData().getSongs()){
                    collectSongIds.add(songBean.getId());
                }
                SongPlayManager.getInstance().setCollectList(collectSongIds);

            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //音乐首页
    public void getMusicDataList(Context context) {
        rx.Observable<MusicDataListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getMusicData().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicDataListEntity>() {
            @Override
            public void next(MusicDataListEntity bean) {
                getView().getMusicDataList(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //歌单列表
    public void gedanCollection(Context context) {

        Map<String , String> map = new HashMap<>();
        rx.Observable<MusicCollectionEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .gedanCollect(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicCollectionEntity>() {
            @Override
            public void next(MusicCollectionEntity bean) {
                //缓存
                List<String> ids = new ArrayList<>();
                for(MusicCollectionEntity.DataBean dataBean : bean.getData()){
                    ids.add(dataBean.getId() + "");
                }
                book().write(PagerCons.KEY_GEDAN_LIKE_LIST , ids);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
//                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    public interface MvpView {
        void error(String str);

        void getMusicDataList(MusicDataListEntity data);
    }
}

package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.base.BaseEntity;
import com.juejinchain.android.module.song.entity.AlbumDetailEntity;
import com.juejinchain.android.module.song.entity.MusicCollectEntity;
import com.juejinchain.android.module.song.entity.MusicHistoryEntity;
import com.juejinchain.android.module.song.entity.MusicListEntity;
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

public class MusicMenuPresenterImpl extends BasePresenter<MusicMenuPresenterImpl.MvpView> {

    //收藏歌单列表
    public void getMusicCollectList(Context context) {
        rx.Observable<MusicCollectEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getCollectList().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicCollectEntity>() {
            @Override
            public void next(MusicCollectEntity bean) {
                Set<String> collectSongIds = new HashSet<>();
                for(SongBean songBean : bean.getData().getSongs()){
                    collectSongIds.add(songBean.getId());
                }
                SongPlayManager.getInstance().setCollectList(collectSongIds);

                getView().getMusicCollectList(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //历史歌单列表
    public void getMusicHistoryList(Context context) {
        rx.Observable<MusicHistoryEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getHistoryList().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicHistoryEntity>() {
            @Override
            public void next(MusicHistoryEntity bean) {
                getView().getMusicHistoryList(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //分类歌单列表
    public void getMusicList(Context context, String alias) {
        Map<String,String> map = new HashMap<>();
        //分类别名
        map.put("alias",alias);
        rx.Observable<MusicListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getMusicList(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicListEntity>() {
            @Override
            public void next(MusicListEntity bean) {
                getView().getMusicList(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //最新专辑列表
    public void albumDetail(Context context, String album_id) {
        Map<String,String> map = new HashMap<>();
        map.put("album_id",album_id);
        rx.Observable<AlbumDetailEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .albumDetail(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<AlbumDetailEntity>() {
            @Override
            public void next(AlbumDetailEntity bean) {
                getView().albumDetail(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //榜单详情
    public void rankList(Context context, String rid) {
        Map<String,String> map = new HashMap<>();
        map.put("rid",rid);
        rx.Observable<MusicListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .rankList(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicListEntity>() {
            @Override
            public void next(MusicListEntity bean) {
                getView().rankList(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //删除历史记录
    public void delHistory(Context context, String id) {
        Map<String,Object> map = new HashMap<>();
        // 历史的ID， 值为 -1 执行删除全部历史
        map.put("song_id",id);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .delHistory(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity bean) {
                getView().delHistory(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //取消收藏
    public void delCollect(Context context, String id) {
        Map<String,Object> map = new HashMap<>();
        // 收藏ID，值 -1 执行全部删除
        map.put("song_id",id);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .delCollect(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity bean) {
                getView().delCollect(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //收藏歌单
    public void addCollectSheet(Context context,String sheet_id) {
        Map<String, String> map = new HashMap<>();
        map.put("sheet_id",sheet_id);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .addCollectSheet(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity bean) {
                //加入缓存
                List<String> ids = new ArrayList<>();
                Object object = book().read(PagerCons.KEY_GEDAN_LIKE_LIST);
                if(object != null){
                    ids = (List<String>) object;
                }
                ids.add(sheet_id);
                book().write(PagerCons.KEY_GEDAN_LIKE_LIST , ids);

                getView().collectSheetResult();
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //取消收藏歌单
    public void delCollectSheet(Context context,String sheet_id) {
        Map<String, String> map = new HashMap<>();
        map.put("sheet_id",sheet_id);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .delCollectSheet(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity bean) {
                //移除缓存
                List<String> ids = new ArrayList<>();
                Object object = book().read(PagerCons.KEY_GEDAN_LIKE_LIST);
                if(object != null){
                    ids = (List<String>) object;
                }
                ids.remove(sheet_id);
                book().write(PagerCons.KEY_GEDAN_LIKE_LIST , ids);

                getView().collectSheetResult();
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //播放统计
    public void songIncr(Context context, String id) {
        Map<String , String> map = new HashMap<>();
        // 收藏ID，值 -1 执行全部删除
        map.put("song_id" , id);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .songIncr(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity bean) {

            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {

            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //是否收藏歌单
    public boolean isCollectGedan(String id){
        Object object = book().read(PagerCons.KEY_GEDAN_LIKE_LIST);
        if(object != null){
            List<String> ids = (List<String>) object;
            for(String str : ids){
                if(id.equals(str)){
                    return true;
                }
            }
        }
        return false;
    }


    public interface MvpView {

        void getMusicCollectList(MusicCollectEntity data);

        void getMusicHistoryList(MusicHistoryEntity data);

        void getMusicList(MusicListEntity data);

        void albumDetail(AlbumDetailEntity bean);

        void rankList(MusicListEntity data);

        void delHistory(BaseEntity data);

        void delCollect(BaseEntity data);

        void collectSheetResult();

        void error(String str);
    }
}

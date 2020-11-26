package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.movie.craw.ThreadPoolInstance;
import com.newsuper.t.juejinbao.ui.movie.craw.crawmusic.BeanMusic;
import com.newsuper.t.juejinbao.ui.movie.craw.crawmusic.MusicAnalysis;
import com.newsuper.t.juejinbao.ui.song.entity.AddSongEntity;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Subscriber;
import rx.Subscription;

public class SongPresenterImpl extends BasePresenter<SongPresenterImpl.MvpView> {

    String data = "{\n" +
            "\t\"sourceListStartIndex\": 1,\n" +
            "\t\"sourceListEndIndex\": 0,\n" +
            "\t\"name\": \"音乐助手\",\n" +
            "\t\"requestType\": \"get\",\n" +
            "\t\"requestFormData\": [\n" +
            "\t\t[\n" +
            "\t\t\t\"wd\",\n" +
            "\t\t\t\"key\"\n" +
            "\t\t],\n" +
            "\t\t[\n" +
            "\t\t\t\"submit\",\n" +
            "\t\t\t\"search\"\n" +
            "\t\t]\n" +
            "\t],\n" +
            "\t\"url\": \"https://music.hwkxk.cn/?kw={key}&lx=migu\",\n" +
            "\t\"list\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"t\": \"select\",\n" +
            "\t\t\t\"n\": \"body\",\n" +
            "\t\t\t\"i\": -1\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"t\": \"select\",\n" +
            "\t\t\t\"n\": \"td[class=song-bitrate]\",\n" +
            "\t\t\t\"i\": -1\n" +
            "\t\t}\n" +
            "\t],\n" +
            "\t\"ignore\": [\n" +
            "\t\t\".mp3\"\n" +
            "\t],\n" +
            "\t\"item\": {\n" +
            "\t\t\"songName\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"t\": \"select\",\n" +
            "\t\t\t\t\"n\": \"a[class=btn btn-xs btn-info ]\",\n" +
            "\t\t\t\t\"i\": -1\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"t\": \"attr\",\n" +
            "\t\t\t\t\"n\": \"download\",\n" +
            "\t\t\t\t\"i\": -1\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"singer\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"t\": \"select\",\n" +
            "\t\t\t\t\"n\": \"a[class=btn btn-xs btn-info ]\",\n" +
            "\t\t\t\t\"i\": -1\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"t\": \"attr\",\n" +
            "\t\t\t\t\"n\": \"download\",\n" +
            "\t\t\t\t\"i\": -1\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"link\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"t\": \"select\",\n" +
            "\t\t\t\t\"n\": \"a[class=btn btn-xs btn-info ]\",\n" +
            "\t\t\t\t\"i\": -1\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"t\": \"attr\",\n" +
            "\t\t\t\t\"n\": \"href\",\n" +
            "\t\t\t\t\"i\": -1\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";

    //收藏
    public void addCollect(Context context,String songId) {
        Map<String, Object> map = new HashMap<>();
        map.put("song_id",songId);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .addCollect(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity bean) {
                getView().addCollect(bean , songId);
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
                Set<String> stringSet = SongPlayManager.getInstance().getCollectList();
                stringSet.remove(id);
                SongPlayManager.getInstance().setCollectList(stringSet);
                getView().resetCollect();
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    public void crawMusicList(Activity activity , String key){
        ThreadPoolInstance.getInstance().add(new Thread(new Runnable() {
            @Override
            public void run() {
                List<BeanMusic> beanMusicList = new MusicAnalysis().getBeanMusicList(key , data);
                Log.e("zy" , "数据长度:" + beanMusicList.size());

                if(activity != null){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(getView() != null){
                                getView().crawMusicList(beanMusicList);
                            }
                        }
                    });
                }


            }
        }));

    }

    //添加歌曲到服务端，得到歌曲id
    public void addSongs(Context context,Map<String, Object> map) {
        rx.Observable<AddSongEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .addSongs(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<AddSongEntity>() {
            @Override
            public void next(AddSongEntity bean) {
                getView().addSongs(bean , map);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public interface MvpView {
        void error(String str);

        void addCollect(BaseEntity data, String songId);
        void resetCollect();
        void crawMusicList(List<BeanMusic> beanMusicList);
        void addSongs(AddSongEntity data, Map<String, Object> map);
    }
}
package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.movie.bean.UpdateEntity;
import com.newsuper.t.juejinbao.ui.movie.craw.ThreadPoolInstance;
import com.newsuper.t.juejinbao.ui.movie.craw.moviedetail.BeanMovieDetail;
import com.newsuper.t.juejinbao.ui.movie.craw.moviedetail.MovieDetailAnlysis;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieRadarMovieDetailEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.UploadMovieDetailBean;
import com.newsuper.t.juejinbao.ui.share.entity.ShareDetailEntity;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class MovieDetailImpl extends BasePresenter<MovieDetailImpl.MvpView> {
    public MovieRadarMovieDetailEntity movieRadarMovieDetailEntity;
    private ShareDetailEntity shareDetailEntity = null;


    //请求影院详情爬取规则
    public void movieDetailValue(Activity activity, String link) {
        try {
            MvpView mvpView = getView();
            final String key = new URI(link).toURL().getHost();

            if (JunjinBaoMainActivity.movieRadarMovieDetailEntityMap != null && JunjinBaoMainActivity.movieRadarMovieDetailEntityMap.containsKey(key) && JunjinBaoMainActivity.movieRadarMovieDetailEntityMap.get(key) != null) {
                movieRadarMovieDetailEntity = JunjinBaoMainActivity.movieRadarMovieDetailEntityMap.get(key);


//                getMovieDetail(activity , link);
                mvpView.movieDetailValue();
                return;
            }


            Map<String, String> map = new HashMap<>();
            map.put("domain", key);


            rx.Observable<MovieRadarMovieDetailEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).movieRadarCrawDetailValue(map).map((new HttpResultFunc<MovieRadarMovieDetailEntity>()));
            Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieRadarMovieDetailEntity>() {
                @Override
                public void next(MovieRadarMovieDetailEntity movieRadarMovieDetailEntity) {
                    if (movieRadarMovieDetailEntity.getCode() == 0) {
                        MovieDetailImpl.this.movieRadarMovieDetailEntity = movieRadarMovieDetailEntity;
                        if (JunjinBaoMainActivity.movieRadarMovieDetailEntityMap == null) {
                            JunjinBaoMainActivity.movieRadarMovieDetailEntityMap = new HashMap<>();
                        }
                        JunjinBaoMainActivity.movieRadarMovieDetailEntityMap.put(key, movieRadarMovieDetailEntity);
//                        getMovieDetail(activity, link);
                        mvpView.movieDetailValue();
                    }
                }

                @Override
                public void error(String target, Throwable e, String errResponse) {

                }
            }, activity, false);
            RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
            addSubscrebe(rxSubscription);
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        } catch (
                URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //获取详情
    public void getMovieDetail(Activity activity, String link) {
        try {
            ThreadPoolInstance.getInstance().add(new Thread(new Runnable() {
                @Override
                public void run() {
                    BeanMovieDetail beanMovieDetail = new MovieDetailAnlysis().getMovieDetail(link, movieRadarMovieDetailEntity.getData());

                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getView() == null) {
                                    return;
                                }

                                if (beanMovieDetail == null || beanMovieDetail.getPlaybackSources() == null) {
                                    getView().getMovieDetail(null);
                                    return;
                                }
                                //设置播放标识
                                if (beanMovieDetail.getPlaybackSources().size() > 0 && beanMovieDetail.getPlaybackSources().get(0).getDramaSeries().size() > 0) {
                                    beanMovieDetail.getPlaybackSources().get(0).getDramaSeries().get(0).setPlayed(1);
                                }
                                getView().getMovieDetail(beanMovieDetail);
                            }
                        });

                    }
                }
            }));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取播放地址
    public void getPlaySource(Activity activity, final BeanMovieDetail.PlaybackSource.Drama drama, List<BeanMovieDetail.PlaybackSource.Drama> dramaList, boolean isPlay) {
        if (movieRadarMovieDetailEntity != null) {
            try {
                ThreadPoolInstance.getInstance().add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String playUrl = new MovieDetailAnlysis().getPlayPath(drama.getLink(), movieRadarMovieDetailEntity.getData()).replace("\\", "");

//                            if(playUrl.endsWith("m3u8") || playUrl.endsWith("mp4")) {

                            if (activity != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (getView() == null) {
                                            return;
                                        }
                                        getView().getPlaySource(drama, playUrl, dramaList, isPlay);

                                    }
                                });

                            }

//                            }else{
//                                if(!playUrl.contains("://")){
//                                    if(playUrl.contains(":/")){
//                                        playUrl.replace(":/" , "://");
//                                    }
//                                }
//
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                            if (activity != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (getView() == null) {
                                            return;
                                        }
                                        getView().getPlaySourceError(drama);

                                    }
                                });

                            }
                        }
                    }
                }));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //新手奖励
    public void getNewTaskReward(Map<String, String> StringMap, Activity activity) {
        final MvpView baseView = getView();
        if (baseView == null || activity==null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getNewTaskReward(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity entity) {
                baseView.getNewTaskRewardSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //检测APP更新
    public void updateApp(Activity activity, Map<String, String> StringMap) {
        rx.Observable<UpdateEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                updateAPP(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<UpdateEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UpdateEntity>() {
            @Override
            public void next(UpdateEntity updateEntity) {
                getView().updateApp(updateEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                MyToast.show(activity, "下载地址获取失败");
//                getView().onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //保存观影历史
    public void savePlayed(BeanMovieDetail beanMovieDetail) {
        try {
            ArrayList<BeanMovieDetail> saves = book().read(PagerCons.KEY_MOVIEHISTORY_SAVE2);

            if (saves == null) {
                saves = new ArrayList<>();
            }

            //移除之前的
            for (BeanMovieDetail mBeanMovieDetail : saves) {
                if (mBeanMovieDetail.getTitle().equals(beanMovieDetail.getTitle())) {
                    saves.remove(mBeanMovieDetail);
                    break;
                }
            }


            while (saves.size() >= 50) {
                saves.remove(49);
            }


            saves.add(0, beanMovieDetail);

            book().write(PagerCons.KEY_MOVIEHISTORY_SAVE2, saves);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //请求分享信息
    public void uploadMovieDetail(Activity activity, Map<String, String> StringMap) {


        rx.Observable<UploadMovieDetailBean> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getUploadMovieDetail(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<UploadMovieDetailBean>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UploadMovieDetailBean>() {
            @Override
            public void next(UploadMovieDetailBean shareDetailEntity) {
                    getView().uploadMovieDetail(shareDetailEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                getView().onUploadMovieDetailError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public interface MvpView {
        void movieDetailValue();

        void getMovieDetail(BeanMovieDetail beanMovieDetail);

        void updateApp(UpdateEntity updateEntity);

        void getPlaySource(BeanMovieDetail.PlaybackSource.Drama drama, String playUrl, List<BeanMovieDetail.PlaybackSource.Drama> dramaList, boolean isPlay);

        void uploadMovieDetail(UploadMovieDetailBean bean);

        void getPlaySourceError(BeanMovieDetail.PlaybackSource.Drama drama);
//        public void requestShareInfo(ShareDetailEntity shareDetailEntity);

        void onUploadMovieDetailError(String errorString);

        void getNewTaskRewardSuccess(BaseEntity entity);
    }


}

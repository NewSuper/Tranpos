package com.newsuper.t.juejinbao.ui.movie.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Log;


import com.alibaba.fastjson.JSON;
import com.newsuper.t.juejinbao.ui.movie.bean.Sniff;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieDetailEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieThirdIframeEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.PlayerExFuncImpl;
import com.newsuper.t.juejinbao.ui.movie.view.DeviceSelectDialog;
import com.newsuper.t.juejinbao.ui.movie.view.DownloadListPopupWindow;
import com.newsuper.t.juejinbao.ui.movie.view.PlayerPopupWindow;
import com.newsuper.t.juejinbao.ui.movie.view.PlayerSpeedPopupWindow;
import com.newsuper.t.juejinbao.ui.movie.view.SelectTVPlayPopupWindow;
import com.newsuper.t.juejinbao.utils.MyToast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.Jzvd;

public class PlayerExFunc implements PlayerExFuncImpl.MvpView {
    //去闪电APP投屏
    public static final int LIGHT_SCREEN = 1;
    //去闪电APP下载
    public static final int LIGHT_DOWNLOAD = 2;
    //去闪电APP 影视详情页
    public static final int LIGHT_MOVIEDETAIL = 3;

    protected PlayerExFuncImpl mPresenter;

    private Activity activity;
    private String itemVideoName;
    private String url;
    private JzvdStdEx jzvdStd;

    //操作弹窗
    private PlayerPopupWindow playerPopupWindow;

    //投屏设备选择
    private DeviceSelectDialog deviceSelectDialog;

    //下载列表弹窗
    private DownloadListPopupWindow downloadListPopupWindow;
    //下载列表数据
    private List<MovieThirdIframeEntity.DownListBean> downListBeans;

    //选集列表弹窗
    private SelectTVPlayPopupWindow selectTVPlayPopupWindow;

    //下载嗅探监听
    private DownloadListPopupWindow.DownloadSniffingListener downloadSniffingListener;
    //选集嗅探监听
    private SelectTVPlayPopupWindow.SelectSniffingListener selectSniffingListener;

    //倍速弹窗
    private PlayerSpeedPopupWindow playerSpeedPopupWindow;

    //闪电APP下载地址
    private String appDownloadUrl;
    //是否能投屏
    private int is_screen;

    //好看影视id
    private String vod_id = "";


    public PlayerExFunc(Activity activity, String itemVideoName, String vod_id, JzvdStdEx jzvdStd, List<MovieThirdIframeEntity.DownListBean> downListBeans, DownloadListPopupWindow.DownloadSniffingListener downloadSniffingListener, SelectTVPlayPopupWindow.SelectSniffingListener selectSniffingListener) {
        this.activity = activity;
        this.itemVideoName = itemVideoName;
        this.vod_id = vod_id;

        this.jzvdStd = jzvdStd;
        this.downListBeans = downListBeans;
        this.downloadSniffingListener = downloadSniffingListener;
        this.selectSniffingListener = selectSniffingListener;

        mPresenter = new PlayerExFuncImpl();
        mPresenter.attachModelView(this);

        jzvdStd.setDismissListener(new Jzvd.DismissListener() {
            @Override
            public void dismiss() {
                if (playerSpeedPopupWindow != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playerSpeedPopupWindow.hide();
                        }
                    });

                }
            }
        });

        if (!(downListBeans.size() > 1)) {
            jzvdStd.showSelectTV(false);
        }

    }

    public void setVideoUrl(String url) {
        this.url = url;
        if (jzvdStd == null) {
            return;
        }
        jzvdStd.setNetUrl(url,
                new JzvdStdEx.UpnpListener() {
                    @Override
                    public void more() {
                        showPopup();
                    }

                    @Override
                    public void upnp(String netUrl) {
//                        clickTP(true);
                        clickTP(true);
                    }

                    @Override
                    public void download() {
                        clickDownload();
                    }

                    @Override
                    public void cancelupnp() {
//                        clickTP(false);
                        clickTP(false);
                    }

                    @Override
                    public void selectTV() {
                        selectTVPlayPopupWindow.show(jzvdStd);
                    }

                    @Override
                    public void nextTV() {
                        selectTVPlayPopupWindow.next();
                    }

                    @Override
                    public void beisu() {
                        if (playerSpeedPopupWindow == null) {
                            playerSpeedPopupWindow = new PlayerSpeedPopupWindow(activity, jzvdStd);
                        }
//                        if(playerSpeedPopupWindow.popupWindow.isShow){
//                            playerSpeedPopupWindow.hide();
//                        }else {
                        playerSpeedPopupWindow.show(jzvdStd.rl_beisu);
                        jzvdStd.startDismissControlViewTimer();
//                        }
                    }
                });
    }


    //显示功能弹窗
    public void showPopup() {
        if (playerPopupWindow == null) {
            playerPopupWindow = new PlayerPopupWindow(activity, jzvdStd, new PlayerPopupWindow.TPListener() {
                @Override
                public void tp() {
                    clickTP(true);
                }

                @Override
                public void download() {
//                    jzvdStd.clickDownload();
                    clickDownload();
                }
            });
        }
        playerPopupWindow.openDownloadFun();
        playerPopupWindow.show(jzvdStd);
    }

    //音量键改变音量
    public void volumeChange(boolean up) {

        //操作框的UI变化
        if (playerPopupWindow != null && playerPopupWindow.isShow()) {

            AudioManager mAudioManager = (AudioManager) activity.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            int mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            if (up) {
                mGestureDownVolume++;
            } else {
                mGestureDownVolume--;
            }

            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume, 0);


            playerPopupWindow.show(jzvdStd);
        }
        //播放器音量弹框的UI变化
        else {
            AudioManager mAudioManager = (AudioManager) activity.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            int mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            if (up) {
                mGestureDownVolume++;
            } else {
                mGestureDownVolume--;
            }

            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume, 0);

            jzvdStd.showVolumeDialog(0, (int) (100f * mGestureDownVolume / max));
        }
    }

    //设置视频下载地址
//    public void setVideoDownloadUrl(String title, String videoDownloadUrl) {
//        if (playerPopupWindow == null) {
//            playerPopupWindow = new PlayerPopupWindow(activity, jzvdStd, new PlayerPopupWindow.TPListener() {
//                @Override
//                public void tp() {
//                    clickTP(true);
//                }
//
//                @Override
//                public void download() {
////                    jzvdStd.clickDownload();
//                    clickDownload();
//                }
//            });
//        }
//        //显示下载按钮
//        playerPopupWindow.openDownloadFun();
//        jzvdStd.setVideoDownloadUrl(title, videoDownloadUrl);
//    }

    //获得APP下载地址后初始化下载弹窗
    public void setAppDownloadUrl(String appDownloadUrl, int is_screen) {
        this.appDownloadUrl = appDownloadUrl;
        this.is_screen = is_screen;
        if (downloadListPopupWindow == null) {
            downloadListPopupWindow = new DownloadListPopupWindow(activity, downListBeans, downloadSniffingListener);
        }
        downloadListPopupWindow.setAppDownloadUrl(appDownloadUrl);
    }

    //设置选集弹窗
    public void setSelectTVPopupWindow() {
        if (selectTVPlayPopupWindow == null) {
            selectTVPlayPopupWindow = new SelectTVPlayPopupWindow(activity, downListBeans, selectSniffingListener);
        }
    }

    //关闭播放，背景开启
    public void closePlay() {
//        Jzvd.releaseAllVideos();
//        jzvdStd.mediaInterface.pause();
//        jzvdStd.onStatePause();
        jzvdStd.showBlackboard(true);

    }


//    //打开下载功能
//    public void setAppDownloadUrl(String appDownloadUrl, JzvdStdEx.LightDownloadListener lightDownloadListener) {
//        if (playerPopupWindow == null) {
//            playerPopupWindow = new PlayerPopupWindow(activity, jzvdStd, new PlayerPopupWindow.TPListener() {
//                @Override
//                public void tp() {
//                    clickTP(true);
//                }
//
//                @Override
//                public void download() {
////                    jzvdStd.clickDownload();
//                    clickDownload();
//                }
//            });
//        }
//        //显示下载按钮
//        playerPopupWindow.openDownloadFun();
//        jzvdStd.setAppDownloadUrl(appDownloadUrl, lightDownloadListener);
//    }

    //隐藏功能弹窗
    public void hidePopup() {
        if (playerPopupWindow != null) {
            playerPopupWindow.hide();
        }

        if (downloadListPopupWindow != null) {
            downloadListPopupWindow.hide();
        }

        if (jzvdStd != null) {
            jzvdStd.hideFuncButton();
        }
    }

    //点击投屏按钮
    public void clickTP(boolean isStart) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        //好看影视，需要请求接口获取影视详情
        if (!vod_id.equals("0")) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("vod_id", vod_id);
            mPresenter.requestMovieDetail(activity, hashMap);
            return;
        }


        if (is_screen == 1) {
            //掘金宝直接投屏
            if (deviceSelectDialog == null) {
                deviceSelectDialog = new DeviceSelectDialog(activity, url, new DeviceSelectDialog.StartListener() {
                    //启动投屏
                    @Override
                    public void start() {
                        if (Jzvd.CURRENT_JZVD != null) {
                            Jzvd.goOnPlayOnPause();
                        }
                        jzvdStd.upnpCloseShow(true);
                    }

                    @Override
                    public void stop() {
                        jzvdStd.upnpCloseShow(false);
                    }
                });
            }
            deviceSelectDialog.show(isStart);
        } else {
            //掘金宝移除投屏功能，改为下载
            if (!TextUtils.isEmpty(appDownloadUrl)) {
                List<MovieThirdIframeEntity.DownListBean> downListBeans = new ArrayList<>();
                MovieThirdIframeEntity.DownListBean downListBean = new MovieThirdIframeEntity.DownListBean();
                downListBean.setSelected(1);
                downListBean.setName(itemVideoName);
                downListBean.setVideoDownloadUrl(url);
                downListBeans.add(downListBean);
                ToLightApp.download(activity, downListBeans, downloadSniffingListener.getVideoName(), appDownloadUrl, PlayerExFunc.LIGHT_SCREEN);
            } else {
                MyToast.show(activity, "未获取到app下载地址");
            }
        }

    }

    //点击下载按钮
    public void clickDownload() {

        if (downloadListPopupWindow == null) {
//            downloadListPopupWindow = new DownloadListPopupWindow(activity, downListBeans, startSniffingListener);
            MyToast.show(activity, "未获取到app下载地址");
            return;
        }
        downloadListPopupWindow.show(jzvdStd);

    }

    //点击选集按钮
//    public void clickSelectTV(){
//        if(selectTVPlayPopupWindow == null){
//            selectTVPlayPopupWindow = new SelectTVPlayPopupWindow(activity , downListBeans ,)
//        }
//    }

    //下载嗅探结果并刷新数据
    public void DownloadSnffingResult(Sniff sniff, boolean success) {
        if (success) {
            downloadListPopupWindow.SnffingSuccess(sniff);
        } else {
            downloadListPopupWindow.SnffingFail(sniff);
        }
    }

    //选集嗅探结果
    public void SelectSniffingReuslt(Sniff sniff, boolean success) {
        if (success) {
            jzvdStd.showBlackboard(false);
            //设置选集选中状态
            for (MovieThirdIframeEntity.DownListBean downListBean : downListBeans) {
                //需要嗅探的
                if (!TextUtils.isEmpty(sniff.webUrl)) {

                    if (downListBean.getUrl().equals(sniff.webUrl)) {
                        downListBean.setVideoDownloadUrl(sniff.videoDownloadUrl);
                        downListBean.setPlayed(1);
                    } else {
                        downListBean.setPlayed(0);
                    }

                }
                //无需嗅探的
                else {
                    if (downListBean.getVideoDownloadUrl().equals(sniff.videoDownloadUrl)) {
                        downListBean.setPlayed(1);
                    } else {
                        downListBean.setPlayed(0);
                    }
                }

            }
            selectTVPlayPopupWindow.updateData();
        } else {

        }
    }

    //直接刷新下载列表
    public void refreshDownloadList() {
        if (downloadListPopupWindow != null) {
            downloadListPopupWindow.updateData();
        }
    }


    public void destory() {
        Jzvd.VIDEO_IMAGE_DISPLAY_TYPE = Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL;
        jzvdStd = null;

        if (deviceSelectDialog != null) {
            deviceSelectDialog.destory();
            deviceSelectDialog = null;
        }

        if (playerPopupWindow != null) {
            playerPopupWindow.destory();
            playerPopupWindow = null;
        }

        if (downloadListPopupWindow != null) {
            downloadListPopupWindow.destory();
            downloadListPopupWindow = null;
        }

        if (mPresenter != null) {
            mPresenter.unSubscribe();
            mPresenter = null;
        }
    }


    //影视详情返回
    @Override
    public void requestMovieDetail(MovieDetailEntity movieDetailEntity) {
        Log.e("zy", JSON.toJSONString(movieDetailEntity));
        if (Utils.saveTxtFile("movieDetail.txt", JSON.toJSONString(movieDetailEntity))) {
            if (!TextUtils.isEmpty(appDownloadUrl)) {
                List<MovieThirdIframeEntity.DownListBean> downListBeans = new ArrayList<>();
                MovieThirdIframeEntity.DownListBean downListBean = new MovieThirdIframeEntity.DownListBean();
                downListBean.setSelected(1);
                downListBean.setName(itemVideoName);
                downListBean.setVideoDownloadUrl(url);
                downListBeans.add(downListBean);
                ToLightApp.download(activity, downListBeans, downloadSniffingListener.getVideoName(), appDownloadUrl, PlayerExFunc.LIGHT_MOVIEDETAIL);
            } else {
                MyToast.show(activity, "未获取到app下载地址");
            }
        } else {
            MyToast.show(activity, "储存数据失败");
        }
    }


}

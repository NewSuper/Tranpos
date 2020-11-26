package com.newsuper.t.juejinbao.ui.movie.player;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.activity.ScreenTeachActivity;
import com.newsuper.t.juejinbao.ui.movie.craw.moviedetail.BeanMovieDetail;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieRadarMovieDetailEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.PlayerExFunc;
import com.newsuper.t.juejinbao.ui.movie.utils.ToLightApp2;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.movie.view.DownloadListPopupWindow2;
import com.newsuper.t.juejinbao.ui.movie.view.PlayerPopupWindow2;
import com.newsuper.t.juejinbao.ui.movie.view.PlayerSpeedPopupWindow2;
import com.newsuper.t.juejinbao.ui.movie.view.SelectTVPlayPopupWindow2;
import com.newsuper.t.juejinbao.utils.MyToast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 播放器界面-业务层
 */
public class JzvdPlayerBusiness extends JzvdPlayerNet {
    private Activity activity;


    //投屏按钮
    private RelativeLayout ll_upnp;
    //下载按钮
    private RelativeLayout ll_download;
    //设置
    private ImageView iv_set;
    //投屏教程
    private ImageView iv_tpjc;
    //倍速按钮
    protected RelativeLayout rl_speed;
    //选集按钮
    protected RelativeLayout rl_xuanji;
    //中场栏
    protected RelativeLayout rl_halftime;
    //文本
    protected TextView tv_content;
    //下一集
    protected RelativeLayout rl_next;
    //分享弹框
    protected RelativeLayout rl_share;
    private LinearLayout ll_pyq;
    private LinearLayout ll_wx;
    private LinearLayout ll_qq;
    private LinearLayout ll_qqkj;

    //操作弹窗
    private PlayerPopupWindow2 playerPopupWindow;
    //下载列表弹窗
    private DownloadListPopupWindow2 downloadListPopupWindow;
    //数据
    private List<BeanMovieDetail.PlaybackSource.Drama> dramaList;
    //连续剧名
    private String movieName;
    //闪电app下载地址
    private String appDownloadUrl;
    //倍速弹窗
    private PlayerSpeedPopupWindow2 playerSpeedPopupWindow;
    //选集弹窗
    private SelectTVPlayPopupWindow2 selectTVPlayPopupWindow;

    private PlayerStateListener playerStateListener;

    public JzvdPlayerBusiness(Context context) {
        super(context);
    }

    public JzvdPlayerBusiness(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);

        ll_upnp = findViewById(R.id.ll_upnp);
        ll_download = findViewById(R.id.ll_download);

        iv_set = findViewById(R.id.iv_set);
        iv_tpjc = findViewById(R.id.iv_tpjc);
        rl_speed = findViewById(R.id.rl_speed);
        rl_xuanji = findViewById(R.id.rl_xuanji);
        rl_halftime = findViewById(R.id.rl_halftime);
        tv_content = findViewById(R.id.tv_content);
        rl_next = findViewById(R.id.rl_next);
        rl_share = findViewById(R.id.rl_share);
        ll_pyq = findViewById(R.id.ll_pyq);
        ll_wx = findViewById(R.id.ll_wx);
        ll_qq = findViewById(R.id.ll_qq);
        ll_qqkj = findViewById(R.id.ll_qqkj);


    }


    //业务相关
    public void setBusinessInit(Activity activity,
                                String movieName) {
        this.activity = activity;
        this.movieName = movieName;

        //小米异形屏适配
//        if (OSUtils.isMiui() && (!TextUtils.isEmpty(OSUtils.getSystemModel())) ) {
////            if(!TextUtils.isEmpty(OSUtils.getSystemModel())){
////                if(OSUtils.getSystemModel().toUpperCase().contains("K30")){
//            v_xiaomi.setVisibility(View.VISIBLE);
////                }
////            }
//
//
//        } else {
//            v_xiaomi.setVisibility(View.GONE);
//        }


        ll_upnp.setOnClickListener(new

                                           OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   clickTP(true);
                                               }
                                           });

        ll_download.setOnClickListener(new

                                               OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       clickDownload();
                                                   }
                                               });

        iv_set.setOnClickListener(new

                                          OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  if (playerPopupWindow == null) {
                                                      playerPopupWindow = new PlayerPopupWindow2(activity, JzvdPlayerBusiness.this, new PlayerPopupWindow2.TPListener() {
                                                          @Override
                                                          public void tp() {
                                                              clickTP(true);
                                                          }

                                                          @Override
                                                          public void download() {
                                                              clickDownload();
                                                          }
                                                      });
                                                  }
                                                  playerPopupWindow.openDownloadFun();
                                                  playerPopupWindow.show(JzvdPlayerBusiness.this);

                                              }
                                          });

        iv_tpjc.setOnClickListener(new

                                           OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   ScreenTeachActivity.intentMe(activity);
                                               }
                                           });

        rl_speed.setOnClickListener(new

                                            OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (playerSpeedPopupWindow == null) {
                                                        playerSpeedPopupWindow = new PlayerSpeedPopupWindow2(activity, JzvdPlayerBusiness.this);
                                                    }
//                        if(playerSpeedPopupWindow.popupWindow.isShow){
//                            playerSpeedPopupWindow.hide();
//                        }else {
                                                    playerSpeedPopupWindow.show(rl_speed);
                                                    startDismissControlViewTimer();
                                                }
                                            });

        rl_xuanji.setOnClickListener(new

                                             OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     if (selectTVPlayPopupWindow == null) {
                                                         selectTVPlayPopupWindow = new SelectTVPlayPopupWindow2(activity, dramaList);
                                                     }
                                                     selectTVPlayPopupWindow.show(rl_xuanji);
                                                 }
                                             });

        rl_next.setOnClickListener(new

                                           OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   setNextTV();
                                               }
                                           });


        ib_back2.setOnClickListener(new

                                            OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (activity != null) {
                                                        activity.finish();
                                                    }
                                                }
                                            });


        //复制下载链接
        rl_copylink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BeanMovieDetail.PlaybackSource.Drama drama = null;

                if (dramaList == null) {
                    return;
                }

                for (BeanMovieDetail.PlaybackSource.Drama mDrama : dramaList) {
                    if (mDrama.getPlayed() == 1) {
                        drama = mDrama;
                        break;
                    }
                }


                if (drama == null) {
                    return;
                }
                if (TextUtils.isEmpty(drama.getVideoDownloadUrl())) {
                    return;
                }

                ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", drama.getVideoDownloadUrl());
                myClipboard.setPrimaryClip(myClip);

                MyToast.show(activity, "已复制到剪贴板");
            }
        });
    }


    //点击下一集
    private void setNextTV() {
        int playIndex = 0;
        int totalIndex = dramaList.size();
        for (int i = 0; i < dramaList.size(); i++) {
            if (dramaList.get(i).getPlayed() == 1) {
                playIndex = i;
                break;
            }
        }

        if (playIndex >= (totalIndex - 1)) {
            MyToast.show(activity, "已经是最后一集");
            if (isFull) {
                backPress();
            } else {
                EventBus.getDefault().post(new EventControllerPlayUrl(EventControllerPlayUrl.TYPE_PLAY, dramaList.get(0), dramaList));

            }


        } else {
            EventBus.getDefault().post(new EventControllerPlayUrl(EventControllerPlayUrl.TYPE_PLAY, dramaList.get(playIndex + 1), dramaList));

        }
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    //设置播放源数据列表
    public void setDramaList(List<BeanMovieDetail.PlaybackSource.Drama> dramaList) {
        this.dramaList = dramaList;
    }


    //亮度相关
    public int brightnessPercent = 100;

    @Override
    public void showBrightnessDialog(int brightnessPercent) {
        super.showBrightnessDialog(brightnessPercent);
        this.brightnessPercent = brightnessPercent;
    }

    //设置标题
    public void setTitle(String title) {
        tv_title.setText(title);
        tv_title2.setText(title);
    }

    //播放完成
    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        setNextTV();
    }


    //毁灭
    @Override
    public void destory() {
        super.destory();
        try {
            Jzvd.releaseAllVideos();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (playerSpeedPopupWindow != null) {
            playerSpeedPopupWindow.destory();
        }
        activity = null;
    }

    private BeanMovieDetail beanMovieDetail;
    private MovieRadarMovieDetailEntity movieRadarMovieDetailEntity;

    public void setBeanMovieDetail(BeanMovieDetail beanMovieDetail, MovieRadarMovieDetailEntity movieRadarMovieDetailEntity) {
        this.beanMovieDetail = beanMovieDetail;
        this.movieRadarMovieDetailEntity = movieRadarMovieDetailEntity;
    }

    //点击投屏按钮
    public void clickTP(boolean isStart) {

        if (beanMovieDetail == null) {
            return;
        }

        if (!Utils.saveTxtFile("beanMovieDetail.txt", JSON.toJSONString(beanMovieDetail))) {
            MyToast.show(activity, "详情数据存储失败");
            return;
        }
        if (!Utils.saveTxtFile("movieRadarMovieDetailEntity.txt", JSON.toJSONString(movieRadarMovieDetailEntity))) {
            MyToast.show(activity, "详情数据存储失败");
            return;
        }


        BeanMovieDetail.PlaybackSource.Drama drama = null;

        for (BeanMovieDetail.PlaybackSource.Drama mDrama : dramaList) {
            if (mDrama.getPlayed() == 1) {
                drama = mDrama;
                break;
            }
        }


        if (drama == null) {
            return;
        }
        if (TextUtils.isEmpty(drama.getVideoDownloadUrl())) {
            return;
        }


        //好看影视，需要请求接口获取影视详情
//        if (!vod_id.equals("0")) {
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put("vod_id", vod_id);
//            mPresenter.requestMovieDetail(activity, hashMap);
//            return;
//        }


//        if (is_screen == 1) {
//            //掘金宝直接投屏
//            if (deviceSelectDialog == null) {
//                deviceSelectDialog = new DeviceSelectDialog(activity, url, new DeviceSelectDialog.StartListener() {
//                    //启动投屏
//                    @Override
//                    public void start() {
//                        if (Jzvd.CURRENT_JZVD != null) {
//                            Jzvd.goOnPlayOnPause();
//                        }
//                        jzvdStd.upnpCloseShow(true);
//                    }
//
//                    @Override
//                    public void stop() {
//                        jzvdStd.upnpCloseShow(false);
//                    }
//                });
//            }
//            deviceSelectDialog.show(isStart);
//        } else {
        //掘金宝移除投屏功能，改为下载
        if (!TextUtils.isEmpty(appDownloadUrl)) {
            //直接切到竖屏，以免回到掘金宝横竖屏错误
            if (isFull) {
                fullscreenButton.performClick();
            }

            List<BeanMovieDetail.PlaybackSource.Drama> downListBeans = new ArrayList<>();
            BeanMovieDetail.PlaybackSource.Drama downListBean = new BeanMovieDetail.PlaybackSource.Drama();
            downListBean.setSelected(1);
            downListBean.setName(movieName + " " + drama.getName());
            downListBean.setVideoDownloadUrl(drama.getVideoDownloadUrl());
            downListBeans.add(downListBean);
            ToLightApp2.download(activity, downListBeans, movieName, appDownloadUrl, PlayerExFunc.LIGHT_SCREEN);
        } else {
            MyToast.show(activity, "未获取到app下载地址");
        }
//        }

    }

    //点击下载按钮
    public void clickDownload() {

        if (downloadListPopupWindow == null) {
            downloadListPopupWindow = new DownloadListPopupWindow2(activity, dramaList, movieName, appDownloadUrl);
//            MyToast.show(activity, "未获取到app下载地址");
//            return;
        }
        downloadListPopupWindow.show(JzvdPlayerBusiness.this);

    }


    public void updateDownloadPopupWindow() {
        if (downloadListPopupWindow != null) {
            downloadListPopupWindow.updateData();
        }
    }

    public void updateSelectTVPopupWindow() {
        if (selectTVPlayPopupWindow != null) {
            selectTVPlayPopupWindow.updateData();
        }
    }

    @Override
    protected void showUI(boolean show) {
        super.showUI(show);
        if (show) {

        } else {
            if (playerSpeedPopupWindow != null) {
                playerSpeedPopupWindow.hide();
            }
        }
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
        rl_halftime.setVisibility(View.GONE);

        //重置倍速
        if (playerSpeedPopupWindow != null) {
            playerSpeedPopupWindow.destory();
            playerSpeedPopupWindow = null;

        }

        if (playerStateListener != null) {
            playerStateListener.stateNormal();
        }

    }


    //中场休息
    public void setHelfTime(String context) {

        //盖上大遮罩
        rl_halftime.setVisibility(View.VISIBLE);
        tv_content.setText(context);
    }

    //关闭中场休息
    public void closeHelfTime() {
        rl_halftime.setVisibility(View.GONE);
        //关闭loading
        stopLoadMonitor();
        ll_loading.setVisibility(View.GONE);
    }

    //继续播放
    public void playContinue() {
        if (state == Jzvd.STATE_PAUSE) {
            showOperateUI();
            clickPlay();
        }
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        if (playerStateListener != null) {
            playerStateListener.statePlaying();
        }
    }


    @Override
    public void onStateError() {
        super.onStateError();
        if (playerStateListener != null) {
            playerStateListener.stateError();
        }
    }

    public static interface PlayerStateListener {
        public void stateNormal();

        public void statePlaying();

        public void stateError();
    }

    public void setPlayerStateListener(PlayerStateListener playerStateListener) {
        this.playerStateListener = playerStateListener;
    }

    //音量键改变音量
    public void volumeChange(boolean up) {
        if (activity == null) {
            return;
        }

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


            playerPopupWindow.show(rl_speed);
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

            showVolumeDialog(0, (int) (100f * mGestureDownVolume / max));

        }
    }

    //切到竖屏
    public void toVerticalScreen() {
        if (isFull) {
            iv_full.performClick();
        }
    }


    //播放失败
    public void playError(PlayerListener playerListener) {
        if (playerListener != null) {
            rl_playerror.setVisibility(View.VISIBLE);
            rl_playerror.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tv_playerror.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerListener.callback();
                    rl_playerror.setVisibility(View.GONE);
                }
            });
        } else {
            rl_playerror.setVisibility(View.GONE);
        }
    }


    public static interface PlayerListener {
        public void callback();
    }

    //显示分享弹框
    public void showShareView(ShareViewClickListener shareViewClickListener) {
        rl_share.setVisibility(View.VISIBLE);
        rl_share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_pyq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareViewClickListener.pyq();
                rl_share.setVisibility(View.GONE);
            }
        });
        ll_wx.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareViewClickListener.wx();
                rl_share.setVisibility(View.GONE);
            }
        });
        ll_qq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareViewClickListener.qq();
                rl_share.setVisibility(View.GONE);
            }
        });
        ll_qqkj.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareViewClickListener.qqkj();
                rl_share.setVisibility(View.GONE);
            }
        });
    }

    public static interface ShareViewClickListener {
        public void pyq();

        public void wx();

        public void qq();

        public void qqkj();

    }

    //分享按钮点击
    public void setPlayerShareIconClickListener(PlayerListener playerListener) {
        rl_playershare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playerListener.callback();
            }
        });
    }


}

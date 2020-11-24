package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityMoviedetailBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.BaseConfigEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.movie.craw.moviedetail.BeanMovieDetail;
import com.newsuper.t.juejinbao.ui.movie.player.JzvdPlayerBusiness;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieDetailImpl;
import com.newsuper.t.juejinbao.ui.movie.utils.NetWorkStateReceiver;
import com.newsuper.t.juejinbao.ui.movie.view.ForceShareByPlayMovieDialog;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;
import com.newsuper.t.juejinbao.ui.share.constant.ShareType;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareConfigEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.GetPicByView;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.jzvd.JZMediaSystem;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.paperdb.Paper;

import static io.paperdb.Paper.book;

/**
 * 影视详情
 */
public class MovieDetailActivity extends BaseActivity<MovieDetailImpl, ActivityMoviedetailBinding> implements MovieDetailImpl.MvpView {

    //当前网址
    private String link;

    //是否是新手任务跳转
    private String from;

    //网络监听广播
    private NetWorkStateReceiver netWorkStateReceiver;

    //页面数据
    private BeanMovieDetail beanMovieDetail;

    //当前选中的播放源地址
    private BeanMovieDetail.PlaybackSource.Drama playDrama;
    //播放地址
    private String playUrl;
    private String parsedUrl;
    //当前播放源
    private List<BeanMovieDetail.PlaybackSource.Drama> dramaList;

    private ShareDialog shareDialog;

    //强制分享
    private ForceShareByPlayMovieDialog shareByPlayMovieDialog;
    private boolean isClickShare;

    //P2P开关
    private boolean p2p;
    private String platform;
    private int type;

    private UserDataEntity userDataEntity;
    private UserDataEntity.DataBean.Task task;

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //动态注册网络状态监听器
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);


        int state = book().read(ShareDialog.SHARE_NOTIFY, 0);
        if (state == 1) {
            book().write(ShareDialog.SHARE_NOTIFY, 0);
            int times = book().read(PagerCons.PLAY_MOVIE_TIMES, 0);
            book().write(PagerCons.PLAY_MOVIE_TIMES, times + 1);
        }

//        int times = book().read(PagerCons.PLAY_MOVIE_TIMES, 0);
//        if (times == 0) {
//            book().write(PagerCons.PLAY_MOVIE_FIRST_TIME, System.currentTimeMillis());
//        }
//        int needShareCount = book().read(PagerCons.KEY_MOVIE_PLAY_COUNR_NEED_SHARE, 0);

        if (mViewBinding.player != null) {
            mViewBinding.player.playContinue();
        }
    }

    @Override
    protected void onDestroy() {
        mViewBinding.player.destory();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (netWorkStateReceiver != null) {
            unregisterReceiver(netWorkStateReceiver);
        }


    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_moviedetail;
    }

    @Override
    public void initView() {
//        Glide.with(mActivity).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoadingBg);
//        mViewBinding.rlLoading.setVisibility(View.VISIBLE);
        mViewBinding.loadingView.setEmptyView(R.layout.empty_view_movie_detail);
        mViewBinding.loadingView.setEmptyText("    播放地址飞走了~\n请返回选择其他播放源哦~");
        mViewBinding.loadingView.showLoading();


        EventBus.getDefault().register(this);

        try {
            beanMovieDetail = (BeanMovieDetail) getIntent().getSerializableExtra("bean");
            link = getIntent().getStringExtra("link");
            from = getIntent().getStringExtra("from");
            mPresenter.movieDetailValue(mActivity, link);


        } catch (Exception e) {
            e.printStackTrace();
        }

        mViewBinding.ibBback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewBinding.player.setPlayerStateListener(new JzvdPlayerBusiness.PlayerStateListener() {
            @Override
            public void stateNormal() {
                mViewBinding.rlBack.setVisibility(View.VISIBLE);
            }

            @Override
            public void statePlaying() {
                mViewBinding.player.playError(null);
                mViewBinding.rlBack.setVisibility(View.GONE);


                mViewBinding.rlProjectionScreen.setVisibility(View.VISIBLE);

                //TODO 观看分享
                //每次开始播放视频时
                checkShareTimes();

            }

            @Override
            public void stateError() {
                mViewBinding.rlBack.setVisibility(View.GONE);
//                MyToast.show(mActivity , "stateError");
                mViewBinding.player.playError(new JzvdPlayerBusiness.PlayerListener() {
                    @Override
                    public void callback() {
                        try {
                            //重新播放链接
                            mViewBinding.player.playError(null);

                            mViewBinding.player.setUp(p2p ? parsedUrl : playUrl, "", mViewBinding.player.screen);
                            mViewBinding.player.setTitle(beanMovieDetail.getTitle() + " " + playDrama.getName());
                            mViewBinding.player.startVideo();

                            mViewBinding.player.updateDownloadPopupWindow();
                            mViewBinding.player.updateSelectTVPopupWindow();

                            mPresenter.savePlayed(beanMovieDetail);

                            mViewBinding.player.setBeanMovieDetail(beanMovieDetail, mPresenter.movieRadarMovieDetailEntity);

//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        mViewBinding.rlSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (beanMovieDetail != null && beanMovieDetail.getPlaybackSources() != null) {
                    for (BeanMovieDetail.PlaybackSource playbackSource : beanMovieDetail.getPlaybackSources()) {
                        Collections.reverse(playbackSource.getDramaSeries());
                    }
                    selectPlaySource(dramaList);
                }
            }
        });

        //分享微信
        mViewBinding.llShareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(0, ShareType.SHARE_TYPE_WECHAT);
            }
        });

        //分享朋友圈
        mViewBinding.llSharePyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(0, ShareType.SHARE_TYPE_FRIEND_MOMENT);
            }
        });


        //分享
        mViewBinding.player.setPlayerShareIconClickListener(new JzvdPlayerBusiness.PlayerListener() {
            @Override
            public void callback() {
                try {

                    share(0, "");
                } catch (Exception e) {
                    ToastUtils.getInstance().show(mActivity, "该影视源异常，请稍后进行分享");
                }
            }
        });

        //也是分享
        mViewBinding.rlActivityshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (beanMovieDetail == null) {
                    MyToast.show(mActivity, "还未获取到数据");
                    return;
                }
                try {

                    share(0, "");
                } catch (Exception e) {
                    ToastUtils.getInstance().show(mActivity, "该影视源异常，请稍后进行分享");
                }
            }
        });
        //投屏
        mViewBinding.rlProjectionScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBinding.player.clickTP(true);
            }
        });

        mViewBinding.player.screen = Jzvd.SCREEN_NORMAL;


        //P2P切换
        mViewBinding.swc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                p2p = isChecked;


//                if(Jzvd.CURRENT_JZVD != null && (Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PLAYING ||Jzvd.CURRENT_JZVD.state == Jzvd.STATE_ERROR ||Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PREPARING)  ){
                //重新播放链接

                if (playUrl != null && parsedUrl != null && beanMovieDetail != null) {

                    if (mViewBinding.player.state == Jzvd.STATE_PLAYING || mViewBinding.player.state == Jzvd.STATE_PAUSE) {
                        long position = mViewBinding.player.getCurrentPositionWhenPlaying();
                        JZUtils.saveProgress(mActivity, mViewBinding.player.jzDataSource.getCurrentUrl(), position);
                        mViewBinding.player.seekToInAdvance = 0;
                    }

                    mViewBinding.player.playError(null);

                    mViewBinding.player.setUp(p2p ? parsedUrl : playUrl, "", mViewBinding.player.screen);
                    mViewBinding.player.setTitle(beanMovieDetail.getTitle() + " " + playDrama.getName());
                    mViewBinding.player.startVideo();

                    mViewBinding.player.updateDownloadPopupWindow();
                    mViewBinding.player.updateSelectTVPopupWindow();

                    mPresenter.savePlayed(beanMovieDetail);

                    mViewBinding.player.setBeanMovieDetail(beanMovieDetail, mPresenter.movieRadarMovieDetailEntity);
                }
//                }
            }
        });
//        mViewBinding.swc.setChecked(true);


        BaseConfigEntity.DataBean dataBean = Paper.book().read(PagerCons.KEY_JJB_CONFIG);
        if (dataBean != null && dataBean.getIs_open_p2p() == 1) {
            //可以开启P2P
            mViewBinding.llShare.setVisibility(View.VISIBLE);
        } else {
            //不能开启P2P
            mViewBinding.llShare.setVisibility(View.GONE);
        }
    }

    /**
     * @param type 0 普通分享 1 强制分享
     */
    public void share(int type, String platform) {
        uploadMovieDetail(beanMovieDetail);
        this.platform = platform;
        this.type = type;
    }


    public void doShare(String mdid) {
        if (beanMovieDetail != null) {
            isClickShare = true;
            mViewBinding.llProgress.setVisibility(View.VISIBLE);
            View shareView = View.inflate(mActivity, R.layout.share_detail_video_poster, null);
            ImageView iv_avatar = shareView.findViewById(R.id.iv_avatar);
            TextView tv_name = shareView.findViewById(R.id.tv_name);
            TextView tv_title = shareView.findViewById(R.id.tv_title);
            TextView tv_date = shareView.findViewById(R.id.tv_date); //少一个日期
            TextView tv_other = shareView.findViewById(R.id.tv_other);
            ImageView iv_poster = shareView.findViewById(R.id.iv_poster);
            ImageView iv_erweima = shareView.findViewById(R.id.iv_erweima);
            TextView tv_invset = shareView.findViewById(R.id.tv_invset);

            LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
            tv_name.setText(loginEntity.getData().getNickname());
            tv_title.setText("《" + beanMovieDetail.getTitle() + "》");
            tv_other.setText("主演：" + beanMovieDetail.getActor());

            tv_invset.setText("邀请码：" + LoginEntity.getInvitation());
            Glide.with(mActivity).load(loginEntity.getData().getAvatar()).into(iv_avatar);

            Glide.with(mActivity).asBitmap().load(TextUtils.isEmpty(beanMovieDetail.getImg()) ? R.mipmap.bg_movie_share_default : beanMovieDetail.getImg()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    iv_poster.setImageBitmap(resource);

                    GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                        @Override
                        public void bitmapDone(Bitmap bitmap) {
                            mViewBinding.llProgress.setVisibility(View.GONE);
//                            if (shareDialog != null) {
//                                shareDialog.show();
//                                return;
//                            }

                            ShareInfo shareInfo = new ShareInfo();
                            shareInfo.setBitmap(bitmap);
                            String shareContent = "《" + beanMovieDetail.getTitle() + "》" + "\n" + getShareContent() + "，邀请码：" + LoginEntity.getInvitation() + "\n" + ShareConfigEntity.getDownLoadUrl() + "&inviteCode=" + LoginEntity.getInvitation() + "&mdid=" + mdid;
                            shareInfo.setShareContent(shareContent);
                            shareInfo.setIsPictrueBybase64(1);
                            shareInfo.setUploadId(mdid);
                            shareInfo.setTitle("《" + beanMovieDetail.getTitle() + "》");
                            shareInfo.setDescription("邀请码：" + LoginEntity.getInvitation() + "，" + getShareContent());
                            shareInfo.setUrl(ShareConfigEntity.getDownLoadUrl() + "&inviteCode=" + LoginEntity.getInvitation() + "&mdid=" + mdid);
                            shareInfo.setThumb(beanMovieDetail.getImg());

                            shareInfo.setUrl_type(ShareInfo.TYPE_MOVIE);
                            shareInfo.setUrl_path(ShareInfo.PATH_MOVIE);


                            if (!TextUtils.isEmpty(platform)) {
                                shareInfo.setPlatform_type(platform);
                            }

                            shareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                                @Override
                                public void result() {
                                    if (type == 1) {
                                        int times = book().read(PagerCons.PLAY_MOVIE_TIMES, 0);
                                        book().write(PagerCons.PLAY_MOVIE_TIMES, times + 1);
                                    }
                                }
                            });
                            shareDialog.setOnShareBtnClick(new ShareDialog.OnShareBtnClick() {
                                @Override
                                public void onbtnClick(String type) {
                                    mViewBinding.llShare.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            book().write(PagerCons.PLAY_MOVIE_TIMES, book().read(PagerCons.PLAY_MOVIE_TIMES, 0) + 1);
                                        }
                                    }, 3 * 1000);
                                }
                            });

                        }
                    });

                    Bitmap qrbitmap = getPicByView.generateBitmap(ShareConfigEntity.getDownLoadUrl() + "&inviteCode=" + LoginEntity.getInvitation() + "&mdid=" + mdid, (int) getResources().getDimension(R.dimen.ws65dp), (int) getResources().getDimension(R.dimen.ws65dp));
                    iv_erweima.setImageBitmap(qrbitmap);
                    getPicByView.viewToImage(mActivity, shareView);

                    if (TextUtils.isEmpty(platform)) {
                        shareDialog.show();
                    }

                }


                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    if (!isClickShare) {
                        return;
                    }
                    isClickShare = false;

                    mViewBinding.llProgress.setVisibility(View.GONE);

                    iv_poster.setBackgroundResource(R.mipmap.bg_movie_share_default);

                    GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                        @Override
                        public void bitmapDone(Bitmap bitmap) {

//                            if (shareDialog != null) {
//                                shareDialog.show();
//                                return;
//                            }


                            ShareInfo shareInfo = new ShareInfo();
                            shareInfo.setBitmap(bitmap);
                            String shareContent = "《" + beanMovieDetail.getTitle() + "》" + "\n" + getShareContent() + "，邀请码：" + LoginEntity.getInvitation() + "\n" + ShareConfigEntity.getDownLoadUrl() + "&inviteCode=" + LoginEntity.getInvitation() + "&mdid=" + mdid;
                            shareInfo.setShareContent(shareContent);
                            shareInfo.setTitle("《" + beanMovieDetail.getTitle() + "》");
                            shareInfo.setDescription("邀请码：" + LoginEntity.getInvitation() + "，" + getShareContent());
                            shareInfo.setUploadId(mdid);

                            shareInfo.setIsPictrueBybase64(1);
                            shareInfo.setUrl_type(ShareInfo.TYPE_MOVIE);
                            shareInfo.setUrl_path(ShareInfo.PATH_MOVIE);
                            shareInfo.setUploadId(mdid);
                            shareInfo.setUrl(ShareConfigEntity.getDownLoadUrl() + "&inviteCode=" + LoginEntity.getInvitation() + "&mdid=" + mdid);
                            if (!TextUtils.isEmpty(platform)) {
                                shareInfo.setPlatform_type(platform);
                            }
                            shareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                                @Override
                                public void result() {
                                    if (type == 1) {
                                        int times = book().read(PagerCons.PLAY_MOVIE_TIMES, 0);
                                        book().write(PagerCons.PLAY_MOVIE_TIMES, times + 1);
                                    }
                                }
                            });
                            shareDialog.setOnShareBtnClick(new ShareDialog.OnShareBtnClick() {
                                @Override
                                public void onbtnClick(String type) {
                                    mViewBinding.llShare.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            book().write(PagerCons.PLAY_MOVIE_TIMES, book().read(PagerCons.PLAY_MOVIE_TIMES, 0) + 1);
                                        }
                                    }, 3 * 1000);
                                }
                            });


                        }
                    });
                    Bitmap qrbitmap = getPicByView.generateBitmap(ShareConfigEntity.getDownLoadUrl() + "&inviteCode=" + LoginEntity.getInvitation() + "&mdid=" + mdid, (int) getResources().getDimension(R.dimen.ws60dp), (int) getResources().getDimension(R.dimen.ws60dp));
                    iv_erweima.setImageBitmap(qrbitmap);
                    getPicByView.viewToImage(mActivity, shareView);
                    if (TextUtils.isEmpty(platform)) {
                        shareDialog.show();
                    }


                }
            });
        } else {
            ToastUtils.getInstance().show(mActivity, "该影视源异常，请稍后进行分享");
        }
    }

    @Override
    public void initData() {

        //请求闪电app下载地址
        Map params = new HashMap();
        params.put("version_code", Utils.getLocalVersion(mActivity) + "");
        params.put("from", "jjb");
        mPresenter.updateApp(mActivity, params);
    }

    @Override
    protected void onPause() {
        try {
            if (Jzvd.CURRENT_JZVD != null) {
//                if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
//                    Jzvd.goOnPlayOnPause();
//                } else {
                Jzvd.goOnPlayOnPause();
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    public static void intentMe(Context context, String link, BeanMovieDetail beanMovieDetail) {

        if (!ClickUtil.isNotFastClick()) {
            return;
        }

        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("link", link);
        intent.putExtra("bean", beanMovieDetail);
        context.startActivity(intent);
    }

    public static void intentMe(Context context, String link, BeanMovieDetail beanMovieDetail, String from) {

        if (!ClickUtil.isNotFastClick()) {
            return;
        }

        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("link", link);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }


    //获取到影视详情数据
//    @Override
//    public void movieDetailValue(BeanMovieDetail beanMovieDetail) {
//        if(beanMovieDetail != null){
//            movieDetailValue(beanMovieDetail);
//        }else {
//
//        }
//    }

    @Override
    public void movieDetailValue() {
        if (beanMovieDetail != null) {
            getMovieDetail(beanMovieDetail);
        } else {
            mPresenter.getMovieDetail(mActivity, link);
        }
    }

    @Override
    public void getMovieDetail(BeanMovieDetail beanMovieDetail) {
        if (beanMovieDetail == null) {
            mViewBinding.loadingView.showEmpty();
        } else {
            mViewBinding.loadingView.showContent();
//        mViewBinding.rlLoading.setVisibility(View.GONE);
            setUI(beanMovieDetail);

            //新手奖励
            userDataEntity = Paper.book().read(PagerCons.PERSONAL);
            if(userDataEntity!=null && userDataEntity.getData().getNewbie_task()!=null
                    && userDataEntity.getData().getNewbie_task().size()!=0){
                showNewTaskRewordDialog(userDataEntity);
            }
        }
    }

    private void showNewTaskRewordDialog(UserDataEntity userDataEntity) {
        for (UserDataEntity.DataBean.Task bean: userDataEntity.getData().getNewbie_task()) {
            if(bean.getTag().equals("newbie_task_second"))
                task = bean;
        }
        if(task==null)
            return;
        NewTaskRewordDialog dialog = new NewTaskRewordDialog(mActivity, task);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setClickListener((position, view) -> {
            Map<String, String> map = new HashMap<>();
            map.put("tag", task.getTag());
            mPresenter.getNewTaskReward(map, mActivity);
        });
        dialog.show();
    }

    //获取闪电下载地址
    @Override
    public void updateApp(UpdateEntity updateEntity) {
        mViewBinding.player.setAppDownloadUrl(updateEntity.getData().getDownloadUrl());
    }


    private long a;
    private long b;
    private long c;

    @Override
    public void getPlaySource(BeanMovieDetail.PlaybackSource.Drama drama, String playUrl, List<BeanMovieDetail.PlaybackSource.Drama> dramaList, boolean isPlay) {
        if (mViewBinding == null || beanMovieDetail == null || mPresenter == null) {
            return;
        }


        //拿到地址，直接播放
        if (isPlay) {

            if (!TextUtils.isEmpty(playUrl)) {

                if (playUrl.endsWith("m3u8") || playUrl.endsWith("mp4")) {
                    //保存播放地址
                    drama.setVideoDownloadUrl(playUrl);

                    //更新播放状态
                    for (BeanMovieDetail.PlaybackSource playbackSource : beanMovieDetail.getPlaybackSources()) {
                        for (BeanMovieDetail.PlaybackSource.Drama mDrama : playbackSource.getDramaSeries()) {
                            mDrama.setPlayed(0);
                        }
                    }
                    drama.setPlayed(1);
//                    drama.setSelected(1);

                    this.playDrama = drama;
                    this.playUrl = playUrl;
                    Log.e("zy", "play:" + playUrl);

                    //P2P

//                    Log.e("zy", "url:" + playUrl + "，是否连接" + P2pEngine.getInstance().isConnected());
                    BaseConfigEntity.DataBean dataBean = Paper.book().read(PagerCons.KEY_JJB_CONFIG);
                    if (dataBean != null && dataBean.getIs_open_p2p() == 1) {
                        parsedUrl = P2pEngine.getInstance().parseStreamUrl(playUrl);

                        //测试
//                        mViewBinding.test1.setText("onHttpDownloaded:");
//                        mViewBinding.test2.setText("onP2pDownloaded:");
//                        mViewBinding.test3.setText("onP2pUploaded:");
//                        mViewBinding.test4.setText("onPeers:");
//                        mViewBinding.test5.setText("http总下载:");
//                        mViewBinding.test6.setText("p2p总下载:");
//                        mViewBinding.test7.setText("p2p总上传:");
//                        mViewBinding.test8.setText("p2p比http:");
//                        P2pEngine.getInstance().addP2pStatisticsListener(new P2pStatisticsListener() {
//                            @Override
//                            public void onHttpDownloaded(long l) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        a += l;
//                                        mViewBinding.test1.setText("onHttpDownloaded:" + l);
//                                        mViewBinding.test5.setText("http总下载:" + a / 1024 + "mb");
//                                        mViewBinding.test8.setText("p2p比http:" + 1f * ( b /(a + b)));
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onP2pDownloaded(long l) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        b += l;
//                                        mViewBinding.test2.setText("onP2pDownloaded:" + l);
//                                        mViewBinding.test6.setText("p2p总下载:" + b / 1024 + "mb");
//                                        mViewBinding.test8.setText("p2p比http:" + 1f * ( b /(a + b)));
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onP2pUploaded(long l) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        c += l;
//                                        mViewBinding.test3.setText("onP2pUploaded:" + l);
//                                        mViewBinding.test7.setText("p2p总上传:" + c / 1024 + "mb");
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onPeers(List<String> list) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mViewBinding.test4.setText("onPeers: size=" + list.size());
//                                    }
//                                });
//                            }
//                        });

                        Log.e("zy", "p2p:" + parsedUrl);
                    } else {
                        parsedUrl = playUrl;
                    }


                    if (mViewBinding.player.state == Jzvd.STATE_PLAYING || mViewBinding.player.state == Jzvd.STATE_PAUSE) {
                        long position = mViewBinding.player.getCurrentPositionWhenPlaying();
                        JZUtils.saveProgress(mActivity, mViewBinding.player.jzDataSource.getCurrentUrl(), position);
                        mViewBinding.player.seekToInAdvance = 0;
                    }

                    mViewBinding.player.playError(null);

                    mViewBinding.player.setUp(p2p ? parsedUrl : playUrl, "", mViewBinding.player.screen);
                    mViewBinding.player.setTitle(beanMovieDetail.getTitle() + " " + drama.getName());
                    mViewBinding.player.startVideo();

                    mViewBinding.player.updateDownloadPopupWindow();
                    mViewBinding.player.updateSelectTVPopupWindow();

                    mPresenter.savePlayed(beanMovieDetail);

                    mViewBinding.player.setBeanMovieDetail(beanMovieDetail, mPresenter.movieRadarMovieDetailEntity);


                } else {
                    mViewBinding.loadingView.showEmpty();
                }
            } else {
                mViewBinding.player.closeHelfTime();
                MyToast.show(mActivity, "无效的播放地址");
            }
            selectPlaySource(dramaList);
        }
        //拿到地址 不直接播放
        else {
            if (!TextUtils.isEmpty(playUrl)) {
                //保存播放地址
                drama.setVideoDownloadUrl(playUrl);
                mViewBinding.player.updateDownloadPopupWindow();
            } else {
                drama.setSelected(0);
                MyToast.show(mActivity, "无效的播放地址");
                mViewBinding.player.updateDownloadPopupWindow();
            }

        }
    }

    @Override
    public void uploadMovieDetail(UploadMovieDetailBean bean) {
        if (bean.getCode() == 0) {
            doShare(bean.getData().getMdid());
        } else {
            doShare("");
        }
    }

    @Override
    public void getPlaySourceError(BeanMovieDetail.PlaybackSource.Drama drama) {
        drama.setSelected(0);
        mViewBinding.player.updateDownloadPopupWindow();
    }

    @Override
    public void onUploadMovieDetailError(String errorString) {
        doShare("");
    }

    @Override
    public void getNewTaskRewardSuccess(BaseEntity entity) {
        if(entity.getCode()==0){
            if(userDataEntity!=null && task!=null && userDataEntity.getData().getNewbie_task()!=null
                && userDataEntity.getData().getNewbie_task().size()!=0){
                userDataEntity.getData().getNewbie_task().remove(task);
                Paper.book().write(PagerCons.PERSONAL,userDataEntity);
            }
            task = null;
        }
    }


    //填充UI
    private void setUI(BeanMovieDetail beanMovieDetail) {
        this.beanMovieDetail = beanMovieDetail;
        mViewBinding.tvName.setText(beanMovieDetail.getTitle());
        mViewBinding.tvNow.setText("状态：" + beanMovieDetail.getNow());
        mViewBinding.tvActor.setText("主演：" + beanMovieDetail.getActor());
        if(TextUtils.isEmpty(beanMovieDetail.getDetail())){
            mViewBinding.tvDetail.setText(Html.fromHtml("暂无剧情介绍"));

        }else {

            mViewBinding.tvDetail.setText(Html.fromHtml("<font color=\"#000000\">[剧情介绍]</font>" +
                    beanMovieDetail.getDetail()));
            mViewBinding.tvDetail2.setText(Html.fromHtml("<font color=\"#000000\">[剧情介绍]</font>" +
                    beanMovieDetail.getDetail()));
        }


        if (mViewBinding.tvDetail2.getLineCount() <= 2) {
            mViewBinding.tvZk.setVisibility(View.GONE);
        }
        mViewBinding.tvZk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBinding.rlSq.setVisibility(View.GONE);
                mViewBinding.rlZk.setVisibility(View.VISIBLE);
            }
        });
        mViewBinding.tvSq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBinding.rlZk.setVisibility(View.GONE);
                mViewBinding.rlSq.setVisibility(View.VISIBLE);
            }
        });

        if (beanMovieDetail == null || beanMovieDetail.getPlaybackSources() == null || mPresenter == null) {
            return;
        }

        if (beanMovieDetail.getPlaybackSources().size() == 0) {
            mViewBinding.loadingView.showEmpty();
        } else if (beanMovieDetail.getPlaybackSources().size() > 1) {
            mViewBinding.tvBfytext.setVisibility(View.VISIBLE);
        }
//        if(beanMovieDetail.getPlaybackSources().size() > 0 && beanMovieDetail.getPlaybackSources().get(0).getDramaSeries().size() > 0){
//            mViewBinding.player.setHelfTime("即将播放 " + beanMovieDetail.getPlaybackSources().get(0).getDramaSeries().get(0).getName());
//
//            mPresenter.getPlaySource(mActivity , beanMovieDetail.getPlaybackSources().get(0).getDramaSeries().get(0) , beanMovieDetail.getPlaybackSources().get(0).getDramaSeries() , true);
//        }

        //直接播放
        for (BeanMovieDetail.PlaybackSource playbackSource : beanMovieDetail.getPlaybackSources()) {
            for (BeanMovieDetail.PlaybackSource.Drama drama : playbackSource.getDramaSeries()) {
                if (drama.getPlayed() == 1) {
                    drama.setPlayed(0);
                    //选中下载
//                    drama.setSelected(1);
                    mViewBinding.player.setHelfTime("即将跳转 " + drama.getName());
                    mPresenter.getPlaySource(mActivity, drama, playbackSource.getDramaSeries(), true);
                } else {
                    drama.setSelected(0);
                }
            }
        }

        initRadioGroup(beanMovieDetail);

        mViewBinding.player.setBusinessInit(mActivity, beanMovieDetail.getTitle());


        //P2P引导
        BaseConfigEntity.DataBean dataBean = Paper.book().read(PagerCons.KEY_JJB_CONFIG);
        if (dataBean != null && dataBean.getIs_open_p2p() == 1) {
            boolean guidp2p = book().read(PagerCons.KEY_MOVIE_P2P_GUIDE, false);
            if (!guidp2p) {
                new MovieP2PGuideDialog(mActivity).show();
            }
        }
    }


    //选择播放源1
    private void initRadioGroup(BeanMovieDetail beanMovieDetail) {

        for (int i = 0; i < beanMovieDetail.getPlaybackSources().size(); i++) {
            BeanMovieDetail.PlaybackSource playbackSource = beanMovieDetail.getPlaybackSources().get(i);

            RadioButton rb = (RadioButton) LayoutInflater.from(this).inflate(R.layout.movie_rb, null);
            rb.setText(playbackSource.getName());
            rb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            mViewBinding.rgTab.addView(rb);

            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectPlaySource(playbackSource.getDramaSeries());
                    mViewBinding.player.setDramaList(playbackSource.getDramaSeries());
                    MovieDetailActivity.this.dramaList = playbackSource.getDramaSeries();
                }
            });

            if (i == 0) {
                rb.setChecked(true);
                selectPlaySource(playbackSource.getDramaSeries());
                mViewBinding.player.setDramaList(playbackSource.getDramaSeries());
                MovieDetailActivity.this.dramaList = playbackSource.getDramaSeries();
            }
        }


    }

    RadioButton rb;

    //选择播放源2
    private void selectPlaySource(List<BeanMovieDetail.PlaybackSource.Drama> dramaList) {
        mViewBinding.rgTab2.removeAllViews();
        mViewBinding.rgTab2.clearCheck();

        for (int i = 0; i < dramaList.size(); i++) {
            BeanMovieDetail.PlaybackSource.Drama drama = dramaList.get(i);
            RadioButton rb = (RadioButton) LayoutInflater.from(this).inflate(R.layout.movie_rb2, null);
            rb.setText(drama.getName());
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 0, 40, 0);


            mViewBinding.rgTab2.addView(rb, lp);

            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (playDrama == null) {
                        return;
                    }

                    if (playDrama.getLink().equals(drama.getLink())) {
                        MyToast.show(mActivity, "正在播放");
                        return;
                    }

                    mViewBinding.player.setHelfTime("即将跳转 " + drama.getName());
                    mPresenter.getPlaySource(mActivity, drama, dramaList, true);

                }
            });

            //刚刚选中的按钮
            if (playDrama != null && playDrama.getLink().equals(drama.getLink())) {
                rb.setChecked(true);
            }
        }
    }

    //网络状态变化，通知player
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void netChange(EventNetChange eventNetChange) {
        if (mViewBinding != null) {
            mViewBinding.player.changeNetState(eventNetChange.getName());
        }
    }

    //player内通知获取播放地址
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void crawPlayPath(EventControllerPlayUrl eventControllerPlayUrl) {
        //不播放
        if (eventControllerPlayUrl.getTyep() == EventControllerPlayUrl.TYPE_NOPLAY) {
            mPresenter.getPlaySource(mActivity, eventControllerPlayUrl.getDrama(), eventControllerPlayUrl.getDramaList(), false);
        }
        //播放
        else if (eventControllerPlayUrl.getTyep() == EventControllerPlayUrl.TYPE_PLAY) {
            mViewBinding.player.setHelfTime("即将跳转 " + eventControllerPlayUrl.getDrama().getName());
            mPresenter.getPlaySource(mActivity, eventControllerPlayUrl.getDrama(), eventControllerPlayUrl.getDramaList(), true);
        }
    }

    //    private String[] contents = new String[]{
//            "不浪费钱了，在这免费看vip电影",
//            "全网vip影视免费看，还能做股东",
//            "为看不到VIP电影感到苦恼？试试",
//            "资讯、影视、视频免费观看神器",
//            "用免费观影丰富你的知识和钱包",
//            "看看资讯和电影就有收益",
//            "vip电影不花一分看，还赚车赚房",
//            "这里看资讯、电影能分原始股",
//            "用了掘金宝，影视资讯都看好",
//            "想告别vip影视付费？用掘金宝啊",
//            "有一种表白是全网vip免费",
//            "看视频、直播、电影白赚零花钱"
//    };
    private String[] contents = new String[]{
            "王者归来，续战江湖！掘金宝VIP影视大片在线随便看，无广告！看新闻，看电视，分享赚大钱！"
    };

    private String getShareContent() {
        Random random = new Random();
        return contents[random.nextInt(contents.length)];
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (mViewBinding != null) {
                    mViewBinding.player.volumeChange(true);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (mViewBinding != null) {
                    mViewBinding.player.volumeChange(false);
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                if (mViewBinding.player.isFull) {
                    Jzvd.backPress();
                    return true;
                }
        }
        return super.onKeyDown(keyCode, event);
    }


//    @Override
//    public void requestShareInfo(ShareDetailEntity shareDetailEntity) {
//
//    }

    /**
     * 分享判断
     */
    public void checkShareTimes() {

        int times = book().read(PagerCons.PLAY_MOVIE_TIMES, 0);
        if (times == 0) {
            book().write(PagerCons.PLAY_MOVIE_FIRST_TIME, System.currentTimeMillis());
        }
        int needShareCount = book().read(PagerCons.KEY_MOVIE_PLAY_COUNR_NEED_SHARE, 0);
        //分享之后才可以观看
        if (needShareCount != 0 && times == needShareCount) {

            if (LoginEntity.getIsNew()) {
                return;
            }

            mViewBinding.player.toVerticalScreen();
            showForceShareDialog();
        } else {
            //24小时之后 重置分享次数
            if (System.currentTimeMillis() - (long) Paper.book().read(PagerCons.PLAY_MOVIE_FIRST_TIME, 0l) >= 6 * 60 * 60 * 1000) {
                book().write(PagerCons.PLAY_MOVIE_TIMES, 1);
                book().write(PagerCons.PLAY_MOVIE_FIRST_TIME, System.currentTimeMillis());
            } else {
                book().write(PagerCons.PLAY_MOVIE_TIMES, times + 1);
            }
        }

    }

    /**
     * 强制分享蒙层
     */
    public void showForceShareDialog() {


        onPause();


        mViewBinding.rlActivityshare.setVisibility(View.GONE);
        mViewBinding.player.showShareView(new JzvdPlayerBusiness.ShareViewClickListener() {
            @Override
            public void pyq() {
                mViewBinding.rlActivityshare.setVisibility(View.VISIBLE);
                share(1, ShareType.SHARE_TYPE_FRIEND_MOMENT);
            }

            @Override
            public void wx() {
                mViewBinding.rlActivityshare.setVisibility(View.VISIBLE);
                share(1, ShareType.SHARE_TYPE_WECHAT);
            }

            @Override
            public void qq() {
                mViewBinding.rlActivityshare.setVisibility(View.VISIBLE);
                share(1, ShareType.SHARE_TYPE_QQ);
            }

            @Override
            public void qqkj() {
                mViewBinding.rlActivityshare.setVisibility(View.VISIBLE);
                share(1, ShareType.SHARE_TYPE_QQ_ZONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void uploadMovieDetail(BeanMovieDetail detail) {
        String intro = detail.getDetail();
        if (TextUtils.isEmpty(intro) && intro.length() > 20) {
            intro = intro.substring(0, 20);
        }

        HashMap hashMap = new HashMap();
        hashMap.put("title", detail.getTitle());
        hashMap.put("starring", detail.getActor());
        hashMap.put("director", detail.getDirector());
        hashMap.put("movie_type", detail.getForm());
        hashMap.put("introduction", intro);
        hashMap.put("poster_url", detail.getImg());
        mPresenter.uploadMovieDetail(mActivity, hashMap);
    }
}

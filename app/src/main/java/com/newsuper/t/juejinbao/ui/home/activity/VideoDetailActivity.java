package com.newsuper.t.juejinbao.ui.home.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityVideoDetailBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.EventID;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.basepop.util.InputMethodUtils;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.adapter.DetailRecomentAdapter;
import com.newsuper.t.juejinbao.ui.home.adapter.HomePagerAdapter;
import com.newsuper.t.juejinbao.ui.home.dialog.ActicleDetailRewordDialog01;
import com.newsuper.t.juejinbao.ui.home.dialog.GuideActicleRewardDialog;
import com.newsuper.t.juejinbao.ui.home.entity.DetailRecomentEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.VideoDetailEntity;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.ppw.ActicleRewardPop;
import com.newsuper.t.juejinbao.ui.home.presenter.VideoDetailPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.VideoDetailPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.movie.craw.ThreadPoolInstance;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.KeyboardChangeListener;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.newsuper.t.juejinbao.view.rewardAnim.RewardAnimManager;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.Jzvd;
import io.paperdb.Paper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//import razerdp.util.InputMethodUtils;

import static io.paperdb.Paper.book;

public class VideoDetailActivity extends BaseActivity<VideoDetailPresenterImpl, ActivityVideoDetailBinding> implements VideoDetailPresenter.View, View.OnClickListener {

    private static final int REQUEST_CODE_REPLY = 0x01;
    private static final int REQUEST_CODE_LOGIN = 0x02;
    //视频数据
    private HomeListEntity.DataBean detailBean;

    HomePagerAdapter videoPagerAdapter;

    List<Object> mData = new ArrayList<>();
  //  private TTAdNative mTTAdNative;

    private ShareDialog mShareDialog;

    //跳转类型 sigletop
    private int intentType = 0;

    private boolean isClickShare = false;
    private int page = 1;

    //评论列表是否刷新
    private boolean isRefresh = true;

    private DetailRecomentAdapter adapter;

    private List<Object> mList = new ArrayList<>();

    private int INTERVAL = 30 * 1000;
    private int countDownTime = INTERVAL;

    private PollTask pollTask;
    private Timer pollTimer;

    private View managerView;
    private InputMethodManager manager;

    private long startReadTime; //开始阅读时间

    //评论历史 用于处理重复评论刷屏情况
    List<String> commentHistory = new ArrayList<>();

    //评论结束之后
    boolean isCommentTop = false;

    //激励广告弹窗动画效果
    private RewardAnimManager rewardAnimManager;
    //双倍奖励时间戳
    private long doubleRewardTime = 0;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        //step1:初始化sdk
//        TTAdManager ttAdManager = TTAdManagerHolder.get();
//        //step2:创建TTAdNative对象,用于调用广告请求接口
//        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
//        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
//        super.onCreate(savedInstanceState);
//
//        MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_DETAIL_PV);
//        MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_DETAIL_UV);
//    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Paper.book().write(PagerCons.KEY_VIDEO_COUNT_DOWN_TIME, countDownTime);
        detailBean = (HomeListEntity.DataBean) intent.getSerializableExtra("data");
        initView();
        initData();
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void initView() {
        startReadTime = System.currentTimeMillis() / 1000;
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        initTitle();
        initListener();

        textSizeLevel = book().read(PagerCons.KEY_TEXTSET_SIZE, "middle");

        if (intentType == 0) {
            detailBean = (HomeListEntity.DataBean) getIntent().getSerializableExtra("data");
            intentType = 1;
        }
        initRecometListRe();

        if("好看视频".equals(detailBean.getSource())){
            parseHaokanUrl();
        }else {
            parseTouTiao();
        }

        setTextSize(mViewBinding.tvTitle);
        mViewBinding.tvTitle.setText(detailBean.getTitle());
        mViewBinding.tvInfo.setText(Utils.FormatW(detailBean.getRead_count()) + "次播放    " + detailBean.getComment_count() + "次评论    " + detailBean.getDigg_count() + "次点赞");
        mViewBinding.tvDesc.setText(detailBean.getDescription());

        videoPagerAdapter = new HomePagerAdapter(mActivity, mData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mViewBinding.recommendRv.setLayoutManager(linearLayoutManager);
        mViewBinding.recommendRv.setAdapter(videoPagerAdapter);
        videoPagerAdapter.setTextSizeLevel(textSizeLevel);
        videoPagerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                if (mData.get(position) instanceof HomeListEntity.DataBean){
                    HomeListEntity.DataBean bean = (HomeListEntity.DataBean) mData.get(position);
                    intentMe(mActivity, bean);
                }
            }

            @Override
            public void tatistical(int id, int type) {

            }
        });

        manager = ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE));
        managerView = getWindow().peekDecorView();

        mViewBinding.activityHomeDetailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.activityHomeDetailInput.setVisibility(View.VISIBLE);
                if (null != managerView) {
                    manager.showSoftInput(managerView, 0);
                }
                mViewBinding.activityHomeDetailEditInput.setFocusable(true);
                mViewBinding.activityHomeDetailEditInput.setFocusableInTouchMode(true);
                mViewBinding.activityHomeDetailEditInput.requestFocus();
                mViewBinding.activityHomeDetailEditInput.setCursorVisible(true);
                InputMethodUtils.showInputMethod(mViewBinding.activityHomeDetailEditInput);
            }
        });

        mViewBinding.activityHomeDetailEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    mViewBinding.activityHomeDetailInputCommit.setEnabled(false);
                    mViewBinding.activityHomeDetailInputCommit.setTextColor(Color.parseColor("#666666"));
                } else {
                    mViewBinding.activityHomeDetailInputCommit.setEnabled(true);
                    mViewBinding.activityHomeDetailInputCommit.setTextColor(Color.parseColor("#4683FF"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mViewBinding.loadingView.showLoading();
        mViewBinding.rlCircleReward.setVisibility(View.GONE);

        if(rewardAnimManager != null){
            rewardAnimManager.destory();
            rewardAnimManager = null;
        }

        getRecommedVideoList();

        mViewBinding.jzvdStdPlayer.setFullListener(new Jzvd.FullListener() {
            @Override
            public void full() {
                if(rewardAnimManager != null){
                    rewardAnimManager.destory();
                }
            }

            @Override
            public void normal() {
                if(rewardAnimManager != null){
                    rewardAnimManager.destory();
                }
            }
        });

    }

    private void initListener() {
        mViewBinding.imgGiveLike.setOnClickListener(this);
        mViewBinding.modelTitleBar.moduleIncludeTitleBarShare.setOnClickListener(this);
        mViewBinding.imgShare.setOnClickListener(this);
        mViewBinding.activityHomeDetailContentAbout.setOnClickListener(this);
        mViewBinding.circlePercentProgress.setOnClickListener(this);
        mViewBinding.activityHomeDetailInputCommit.setOnClickListener(this);
        mViewBinding.activityHomeDetailFinish.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        try {
            if (Jzvd.CURRENT_JZVD != null) {
                Jzvd.releaseAllVideos();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();

        if(rewardAnimManager != null){
            rewardAnimManager.destory();
            rewardAnimManager = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Paper.book().write(PagerCons.KEY_VIDEO_COUNT_DOWN_TIME, countDownTime);
        try {
            if (Jzvd.CURRENT_JZVD != null) {
                if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
                    Jzvd.goOnPlayOnPause();
                } else {
                    Jzvd.goOnPlayOnPause();
                }
            }

            if (pollTask != null) {
                pollTask.cancel();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();

    }

    public void getRecommedVideoList() {
        mViewBinding.imgGiveLike.setBackgroundResource(R.mipmap.ic_give_like_uncheck);

        Map<String, String> param = new HashMap<>();

        param.put("column_id", 0 + "");
        param.put("type", "video");
        param.put("change_uid", "n");
        param.put("ua", "n");
        String str = System.currentTimeMillis() + "";//待处理字符串 取时间戳后四位传给后台
        if (Paper.book().read(PagerCons.HOME_TIME) != null) {
            param.put("ua", Paper.book().read(PagerCons.HOME_TIME));
        } else {
            param.put("ua", str);
            Paper.book().write(PagerCons.HOME_TIME, str);
        }
        mPresenter.getVideoList(param, mActivity);
    }

    private void initTitle() {
        mViewBinding.modelTitleBar.moduleIncludeTitleBarTitle.setText("视频详情");
        mViewBinding.modelTitleBar.moduleIncludeTitleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.modelTitleBar.moduleIncludeTitleBarShare.setImageResource(R.mipmap.share_article);
    }


    @Override
    public void initData() {
        //软键盘监听
        KeyboardChangeListener softKeyboardStateHelper = new KeyboardChangeListener(this);
        softKeyboardStateHelper.setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (isShow) {
                    //键盘的弹出


                    //     et_seartch.setCursorVisible(true);
                } else {
                    //键盘的收起

                    mViewBinding.activityHomeDetailInput.setVisibility(View.GONE);
                    mViewBinding.activityHomeDetailEditInput.setFocusable(false);
                    mViewBinding.activityHomeDetailEditInput.setFocusableInTouchMode(false);

                    //   et_seartch.setCursorVisible(false);
                }
            }
        });
        getRecommedVideoList();

        if(Paper.book().read(PagerCons.KEY_GIVE_LIKE_LIST)!=null){
            List<Integer> idList = Paper.book().read(PagerCons.KEY_GIVE_LIKE_LIST);
            for (Integer id: idList) {
                if(id == detailBean.getId()){
                    detailBean.setIsGiveLike(1);
                    break;
                }
            }
        }
        mViewBinding.imgGiveLike.setImageResource(detailBean.getIsGiveLike()==0?R.mipmap.ic_give_like_uncheck:R.mipmap.ic_give_like_check);
    }

    private void initRecometListRe() {
        adapter = new DetailRecomentAdapter(mActivity, mList, detailBean.getId() + "");
        adapter.setType(1);
        adapter.setReplyOnItemClick(new DetailRecomentAdapter.ReplyOnItemClick() {
            @Override
            public void onClick(int position) {
                DetailRecomentEntity.DataBeanX.DataBean bean = (DetailRecomentEntity.DataBeanX.DataBean) mList.get(position);
                Intent intent = new Intent(mActivity, ArticleCollectReplyActivity.class);
                intent.putExtra("cid", bean.getCid());
                intent.putExtra("content", bean.getContent());
                intent.putExtra("avatar", bean.getAvatar());
                intent.putExtra("fabulous", bean.getFabulous());
                intent.putExtra("name", bean.getNickname());
                intent.putExtra("is_fabulous", bean.getIs_fabulous());
                intent.putExtra("aid", detailBean.getId() + "");
                intent.putExtra("reply", bean.getReply());
                intent.putExtra("comment_time", bean.getComment_time());
                intent.putExtra("type", 1);
                startActivityForResult(intent, REQUEST_CODE_REPLY);
            }

            //广告点击统计
            @Override
            public void tatistical(int id) {


            }


        });
        LinearLayoutManager manager = new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mViewBinding.commentsRv.setLayoutManager(manager);
        mViewBinding.commentsRv.setAdapter(adapter);
        adapter.setTextSizeLevel(textSizeLevel);
    }

    public static void intentMe(Context context, HomeListEntity.DataBean bean) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("data", bean);
        context.startActivity(intent);
    }

    public static void intentMe(Context context, String id) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public void getVideoDetailSuccess(Serializable serializable) {
        VideoDetailEntity entity = (VideoDetailEntity) serializable;

    }

    @Override
    public void getVideoListSuccess(Serializable serializable) {
        HomeListEntity homeListEntity = (HomeListEntity) serializable;
        mViewBinding.loadingView.showContent();
        if (LoginEntity.getIsLogin()) {
            mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
        }
        boolean isShowGuide = book().read(PagerCons.KEY_ARTICLE_IS_SHOW_GUIDE, true);
        countDownTime = INTERVAL;
        if (Paper.book().read(PagerCons.KEY_VIDEO_COUNT_DOWN_TIME) != null) {
            int time = Paper.book().read(PagerCons.KEY_VIDEO_COUNT_DOWN_TIME);
            if (time > 0) {
                countDownTime = time;
                mViewBinding.circlePercentProgress.setPercentage((INTERVAL - countDownTime) * 1000 / INTERVAL);
            }
        }



        if (LoginEntity.getIsLogin()) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_arcticle_circle_reward);
            drawable.setBounds(0, 0, 70, 80);
            mViewBinding.tvCircleInnerPic.setCompoundDrawables(drawable, null, null, null);
            mViewBinding.tvCircleInnerPic.setText("");

            if (isShowGuide) {
                showGuideDialog();
            } else {
                if (LoginEntity.getIsLogin()) {
                    mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
                    startCountDown();
                } else {
                    mViewBinding.rlCircleReward.setVisibility(View.GONE);
                }
            }
        }


        mViewBinding.activityHomeDetailScroll.scrollTo(0, 0);
        mData.clear();
        List<HomeListEntity.DataBean> data = homeListEntity.getData();
        if (data.size() > 5) {
            data = homeListEntity.getData().subList(0, 5);
        }
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setShowtype(9);
        }
        mData.addAll(data);
        loadRecommendListAd();
        page = 1;
        getCommentList();
        videoPagerAdapter.notifyDataSetChanged();
    }

    /**
     * 转圈奖励蒙层
     */
    public void showGuideDialog() {
        GuideActicleRewardDialog dialog = new GuideActicleRewardDialog(mActivity);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (LoginEntity.getIsLogin()) {
                    mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
                    startCountDown();
                } else {
                    mViewBinding.rlCircleReward.setVisibility(View.GONE);
                }

            }
        });
        dialog.show();
        Paper.book().write(PagerCons.KEY_ARTICLE_IS_SHOW_GUIDE, false);
    }

    @Override
    public void giveLikeSuccess(Serializable serializable) {
        BaseEntity entity = (BaseEntity) serializable;
        if (entity.getCode() == 0) {
            mViewBinding.imgGiveLike.setBackgroundResource(R.mipmap.ic_give_like_check);
            mViewBinding.tvInfo.setText(Utils.FormatW(detailBean.getRead_count()) + "次播放    " + detailBean.getComment_count() + "次评论    " + (detailBean.getDigg_count() + 1) + "次点赞");
        } else {
            ToastUtils.getInstance().show(mActivity, entity.getMsg());
        }
    }

    @Override
    public void getRewardByShareSuccess(Serializable serializable) {
        GetCoinEntity entity = (GetCoinEntity) serializable;
        if (entity.getData().getCoin() != 0) {
            RewardEntity rewardEntity = new RewardEntity(
                    "奖励到账",
                    entity.getData().getCoin(),
                    "分享文章奖励",
                    "",
                    false
            );
            ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
            acticleRewardPop.setView(rewardEntity);
            acticleRewardPop.showPopupWindow();
        }
    }

    @Override
    public void getVideoCommentListSuccess(Serializable serializable) {

        isRefresh = true;
        DetailRecomentEntity detailRecomentEntity = (DetailRecomentEntity) serializable;
        if (detailRecomentEntity.getCode() == 0) {
            if (detailRecomentEntity.getData() != null) {
                if (detailRecomentEntity.getData().getData() != null) {
                    //显示评论多少条
                    if (detailRecomentEntity.getData().getTotal() > 99) {
                        mViewBinding.activityHomeDetailBigContent.setText("99+");
                    } else {
                        mViewBinding.activityHomeDetailBigContent.setText(detailRecomentEntity.getData().getTotal() + "");

                    }
                    if (detailRecomentEntity.getData().getTotal() == 0) {
                        mViewBinding.activityHomeDetailBigContent.setVisibility(View.GONE);
                    } else {
                        mViewBinding.activityHomeDetailBigContent.setVisibility(View.VISIBLE);
                    }
                    //显示没有更多了
                    if (detailRecomentEntity.getData().getData().size() > 0) {
                        isRefresh = true;
//                        mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.INVISIBLE);
                    } else {
                        isRefresh = false;
                        if (detailRecomentEntity.getData().getTotal() == 0) {
                            mViewBinding.smallVideoPopCommentsListMore.setText("暂无相关评论");
                            mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.VISIBLE);
                        } else {
                            mViewBinding.smallVideoPopCommentsListMore.setText("暂无更多评论");
                            mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.VISIBLE);
                        }
                    }
                    //更新列表
                    if (page == 1) {
                        adapter.reloadRecyclerView(detailRecomentEntity.getData().getData(), true);
                    } else {
                        adapter.reloadRecyclerView(detailRecomentEntity.getData().getData(), false);

                    }

                } else {
                    mViewBinding.smallVideoPopCommentsListMore.setText("暂无更多评论");
                    mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.VISIBLE);
                    isRefresh = false;
                }
            } else {
                mViewBinding.smallVideoPopCommentsListMore.setText("暂无更多评论");
                mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.VISIBLE);
                isRefresh = false;
            }
            //评论结束后跳转至最新评论处
            if(isCommentTop){
                mViewBinding.activityHomeDetailScroll.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewBinding.activityHomeDetailScroll.smoothScrollTo(0, mViewBinding.commentsRv.getTop());
                    }
                },500);

            }
        } else {
            ToastUtils.getInstance().show(mActivity, detailRecomentEntity.getMsg());
        }


    }

    @Override
    public void getRewardOf30secondSuccess(Serializable serializable) {
        GetCoinEntity coinEntity = (GetCoinEntity) serializable;
        if (coinEntity.getCode() == 0) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_article_reward_coin);
            drawable.setBounds(0, 0, 32, 32);
            mViewBinding.tvCircleInnerPic.setCompoundDrawables(drawable, null, null, null);
            mViewBinding.tvCircleInnerPic.setText("+" + Utils.FormatGold(coinEntity.getData().getCoin()));

            int acticleShowCount = Paper.book().read(PagerCons.ACTICLE_REWARD_STYLE, 0);

//            showRewardDialog(acticleShowCount, coinEntity.getData().getCoin());

            if(rewardAnimManager != null){
                rewardAnimManager.destory();
                rewardAnimManager = null;
            }

            if(rewardAnimManager == null){
                rewardAnimManager = new RewardAnimManager(this , ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0) , 10 , 130 , coinEntity);
                rewardAnimManager.show();
            }
        } else {
            if (coinEntity.getCode() == -1000) {
                mViewBinding.rlAlreadGetReward.setVisibility(View.VISIBLE);
                //点击后消失
                mViewBinding.rlAlreadGetReward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewBinding.rlAlreadGetReward.setVisibility(View.GONE);
                    }
                });
            } else {
                ToastUtils.getInstance().show(mActivity, coinEntity.getMsg());
            }
//            mViewBinding.tvCircleInnerPic.setCompoundDrawables(null, null, null, null);
//            mViewBinding.tvCircleInnerPic.setText("已领");
        }

    }

    @Override
    public void getRewardDouble(RewardDoubleEntity rewardDoubleEntity) {
        RewardEntity entity = new RewardEntity(
                "奖励到账",
                rewardDoubleEntity.getData().getCoin(),
                "金币奖励",
                "用掘金宝App看视\n频，能多赚更多金币！",
                false
        );
        ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
        acticleRewardPop.setView(entity);
        acticleRewardPop.showPopupWindow();
    }

    @Override
    public void leavePageCommitSuccess(Serializable serializable) {

    }

    @Override
    public void postCommentSuccess(Serializable serializable) {
        BaseEntity giveLikeEnty = (BaseEntity) serializable;
        if (giveLikeEnty.getCode() == 0) {
            commentHistory.add(mViewBinding.activityHomeDetailEditInput.getText().toString().trim());
            mViewBinding.activityHomeDetailEditInput.setText("");
            mViewBinding.activityHomeDetailInput.setVisibility(View.GONE);
            mViewBinding.activityHomeDetailEditInput.setFocusable(false);
            mViewBinding.activityHomeDetailEditInput.setFocusableInTouchMode(false);
            manager = ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE));
            managerView = getWindow().peekDecorView();
            if (null != managerView) {
                manager.hideSoftInputFromWindow(managerView.getWindowToken(), 0);
            }
            page = 1;

            String commentString = mViewBinding.activityHomeDetailEditInput.getText().toString();

            isCommentTop = true;
            getCommentList();

            mViewBinding.activityHomeDetailEditInput.setText("");
        } else {
            ToastUtils.getInstance().show(mActivity, giveLikeEnty.getMsg());
        }
    }

    @Override
    public void onError(String str) {

    }

    /**
     * 加载推荐列表feed广告
     */
    private void loadRecommendListAd() {

//        //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(TTAdManagerHolder.POS_ID)
//                .setSupportDeepLink(true)
//                .setImageAcceptedSize(640, 320)
//                .setAdCount(2) //请求广告数量为1到3条
//                .build();
//        //step5:请求广告，调用feed广告异步请求接口，加载到广告后，拿到广告素材自定义渲染
//        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
//            @Override
//            public void onError(int code, String message) {
//
//            }
//
//            @Override
//            public void onFeedAdLoad(List<TTFeedAd> ads) {
//                if (ads == null || ads.isEmpty()) {
//                    return;
//                }
//
//                Iterator<Object> iterator = mData.iterator();
//                while (iterator.hasNext()) {
//                    Object value = iterator.next();
//                    if (value instanceof TTFeedAd) {
//                        iterator.remove();
//                    }
//                }
//
//
//                if (ads.size() > 0 && mData.size() <= 2 && !mData.contains(ads.get(0))) {
//                    if (!mData.contains(ads.get(0))) {
//                        mData.add(0, ads.get(0));
//                    }
//
//                }
//                if (ads.size() > 0 && mData.size() > 3) { //第二条加入一条
//                    Random r = new Random();
//                    int a = r.nextInt(2);
//                    if (!mData.contains(ads.get(0))) {
//                        mData.add(a, ads.get(0));
//                    }
//                }
//                if (ads.size() >= 2 && mData.size() > 4) { //倒数第二再加入一条
//                    if (!mData.contains(ads.get(1))) {
//                        mData.add(mData.size() - 1, ads.get(1));
//                    }
//
//                }
//                videoPagerAdapter.notifyDataSetChanged();
//                mViewBinding.activityHomeDetailScroll.scrollTo(0, 0);
//            }
//        });
    }

    public void getCommentList() {
        Map<String, String> mapComment = new HashMap<>();
        mapComment.put("vid", detailBean.getId() + "");
        mapComment.put("page", String.valueOf(page));
        mPresenter.getVideoCommentList(mapComment, mActivity);
    }

    /**
     * @param conut 次数
     * @param coin  数量
     */
    public void showRewardDialog(int conut, double coin) {

        if (conut == 0) {
            ActicleDetailRewordDialog01 detailRewordDialog01 = new ActicleDetailRewordDialog01(mActivity, coin, "视频奖励");
            detailRewordDialog01.setTitle("视频奖励");
            detailRewordDialog01.show();

        } else if (conut == 1) {
            RewardEntity entity = new RewardEntity(
                    "奖励到账",
                    coin,
                    "视频奖励",
                    "用掘金宝App读文章看视\n频，能多赚更多金币！",
                    false
            );
            ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
            acticleRewardPop.setView(entity);
            acticleRewardPop.showPopupWindow();
        } else {

            RewardEntity entity = new RewardEntity(
                    "奖励到账",
                    coin,
                    "视频奖励",
                    "用掘金宝App读文章看视\n频，能多赚更多金币！",
                    true
            );
            ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
            acticleRewardPop.setView(entity);
            acticleRewardPop.showPopupWindow();
        }
        Paper.book().write(PagerCons.ACTICLE_REWARD_STYLE, ++conut);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_give_like: //点赞
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(this, GuideLoginActivity.class));
                    return;
                }
                HashMap param = new HashMap();
                param.put("vid", detailBean.getId() + "");
                mPresenter.giveLike(param, mActivity);
                break;
            case R.id.activity_home_detail_content_about:
                mViewBinding.activityHomeDetailScroll.smoothScrollTo(0, mViewBinding.commentsRv.getTop());
                break;

            case R.id.module_include_title_bar_share:
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_DETAIL_SHARE);
            case R.id.img_share: //分享
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(this, GuideLoginActivity.class));
                }

               // MobclickAgent.onEvent(mActivity, EventID.HOMEPAGE_VIDEO_SHARE);   //首页-视频分享-埋点

                ShareInfo shareInfo = new ShareInfo();

                shareInfo.setUrl_type(ShareInfo.TYPE_VIDEO);
                shareInfo.setUrl_path("/VideoDetail/" + detailBean.getId());

                shareInfo.setType("video");
                shareInfo.setId(detailBean.getId() + "");

                shareInfo.setAid(detailBean.getId() + "");


                if (detailBean != null && detailBean.getImg_url().size() != 0) {
                    shareInfo.setSharePicUrl(detailBean.getImg_url().get(0));
                }
                mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("tag", "video");
                        map.put("aid", detailBean.getId() + "");

                        mPresenter.getRewardByShare(map, mActivity);
                    }
                });
                isClickShare = true;
                mShareDialog.show();
                break;
            case R.id.circle_percent_progress:
                if (LoginEntity.getIsLogin()) {
//                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + Constant.ACTICLE_REWARD);
                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TODAT_COIN);
                } else {
                    startActivityForResult(new Intent(mActivity, GuideLoginActivity.class), REQUEST_CODE_LOGIN);
                }
                break;
            case R.id.activity_home_detail_input_commit:
                if (!ClickUtil.isNotFastClick()) {
                    return;
                }
                LoginEntity loginEntity = book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    Intent intent = new Intent(mActivity, GuideLoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_LOGIN);
                    return;
                }
                if (TextUtils.isEmpty(mViewBinding.activityHomeDetailEditInput.getText().toString().trim())) {
                    MyToast.showToast("请输入评论内容");
                    return;
                }
                initCommit();
                break;
            case R.id.activity_home_detail_finish:
                finish();
                break;


        }
    }

    //发布评论
    private void initCommit() {
        if (TextUtils.isEmpty(mViewBinding.activityHomeDetailEditInput.getText().toString().trim())) {
            ToastUtils.getInstance().show(mActivity, "请输入评论");
            return;
        }
        if (commentHistory.contains(mViewBinding.activityHomeDetailEditInput.getText().toString().trim())) {
            ToastUtils.getInstance().show(mActivity, "请勿发布重复评论");
            return;
        }


//        aid: 6074904
//        content: 爱仕达大奥
        Map<String, String> map = new HashMap<>();
        map.put("vid", detailBean.getId() + "");
        map.put("content", mViewBinding.activityHomeDetailEditInput.getText().toString().trim());
        mPresenter.postComment(map, mActivity);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      //  UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_CODE_REPLY) {

        }

        if (LoginEntity.getIsLogin() && mPresenter != null) {


            switch (requestCode) {
                //双倍奖励
                case PlayRewardVideoAdActicity.DOUBLEREWARD:
                    if(resultCode == RESULT_OK) {
                        HashMap hashMap = new HashMap<String, String>();
                        hashMap.put("aid", detailBean.getId() + "");
                        hashMap.put("type", "video");
                        hashMap.put("read_time", doubleRewardTime + "");
                        mPresenter.getRewardDouble(hashMap, mActivity);
                        doubleRewardTime = 0;
                    }
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginEntity.getIsLogin()) {
            startCountDown();
        }
        //分享回调
        int state = book().read(ShareDialog.SHARE_NOTIFY, 0);
        if (state == 1 && isClickShare) {
            book().write(ShareDialog.SHARE_NOTIFY, 0);

            HashMap<String, String> map = new HashMap<>();
            map.put("tag", "video");
            map.put("aid", detailBean.getId() + "");

            mPresenter.getRewardByShare(map, mActivity);
        }
    }

    /**
     * 开启读秒倒计时
     */
    public void startCountDown() {

        if (pollTask != null) {
            pollTask.cancel();
        }
        pollTask = new PollTask();
        //schedule 计划安排，时间表
        if (pollTimer == null) {
            pollTimer = new Timer();
        }
        pollTimer.schedule(pollTask, 0, 1);
    }

    Handler handler = new Handler();

    public class PollTask extends TimerTask {

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    countDownTime--;
                    if (countDownTime == 0) {
                        mViewBinding.circlePercentProgress.setPercentage(1000);
                        if (pollTask != null) {
                            pollTask.cancel();
                        }
                        if (LoginEntity.getIsLogin()) {
                            //上传阅读时间
                            commitReadTime();
                            getRewardOf30S();
                        }
                    } else {
                        mViewBinding.circlePercentProgress.setPercentage((INTERVAL - countDownTime) * 1000 / INTERVAL);
                    }
                }
            });
        }
    }


    /**
     * 阅读30s随机奖励
     */
    public void getRewardOf30S() {
        if (mPresenter == null || this.isDestroyed()) {
            return;
        }
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("aid", detailBean.getId() + "");
        hashMap.put("type", "video");
        hashMap.put("starttime", startReadTime + "");
        hashMap.put("read_time", (doubleRewardTime = System.currentTimeMillis()) + "");

        mPresenter.getRewardOf30second(hashMap, mActivity);
    }

    /**
     * 上传阅读时间
     */
    public void commitReadTime() {
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("aid", detailBean.getId() + "");
        hashMap.put("type", "video");
        hashMap.put("starttime", startReadTime + "");

        if(mPresenter!=null)
            mPresenter.leavePageCommit(hashMap, mActivity);
    }

    private String textSizeLevel = "middle";

    private void setTextSize(TextView content) {
        switch (textSizeLevel) {
            case "small":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }

    public void parseHaokanUrl(){
        ThreadPoolInstance.getInstance().add(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("https://haokan.baidu.com/v?vid=" + detailBean.getOther().getVideo_id() +  "&tab=recommend").get();

                    String attr = document.select("video").attr("src");
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(detailBean.getImg_url().size()>1){
                                mViewBinding.jzvdStdPlayer.setUp(attr, detailBean.getTitle());
                            }
                            mViewBinding.jzvdStdPlayer.setVisibility(View.VISIBLE);
                            mViewBinding.jzvdStdPlayer.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mViewBinding.jzvdStdPlayer.startVideo();
                                }
                            }, 500);

                        }
                    });



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));



    }

    public void parseTouTiao(){
        String url = "http://i.snssdk.com/video/urls/1/toutiao/mp4/" + detailBean.getOther().getVideo_id();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(detailBean.getImg_url().size()>1){
                            mViewBinding.jzvdStdPlayer.setUp(detailBean.getImg_url().get(1), detailBean.getTitle());
                        }
                        mViewBinding.jzvdStdPlayer.setVisibility(View.VISIBLE);
                        mViewBinding.jzvdStdPlayer.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mViewBinding.jzvdStdPlayer.startVideo();
                            }
                        }, 500);

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String dataString = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(dataString);

                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONObject videoListJSONObject = dataObject.getJSONObject("video_list");

                    JSONObject video_3 = videoListJSONObject.getJSONObject("video_3");

                    String mainUrl = video_3.optString("main_url");


                    String decodeWord = new String(Base64.decode(mainUrl, Base64.NO_WRAP), "utf-8");


                    detailBean.getImg_url().set(1, decodeWord);

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(detailBean.getImg_url().size()>1){
                                mViewBinding.jzvdStdPlayer.setUp(detailBean.getImg_url().get(1), detailBean.getTitle());
                            }
                            mViewBinding.jzvdStdPlayer.setVisibility(View.VISIBLE);
                            mViewBinding.jzvdStdPlayer.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mViewBinding.jzvdStdPlayer.startVideo();
                                }
                            }, 500);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(detailBean.getImg_url().size()>1){
                                mViewBinding.jzvdStdPlayer.setUp(detailBean.getImg_url().get(1), detailBean.getTitle());
                            }
                            mViewBinding.jzvdStdPlayer.setVisibility(View.VISIBLE);
                            mViewBinding.jzvdStdPlayer.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mViewBinding.jzvdStdPlayer.startVideo();
                                }
                            }, 500);
                        }
                    });

                }


            }
        });
    }

}

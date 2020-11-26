package com.newsuper.t.juejinbao.ui.task.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentTaskDetailBinding;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.base.BusProvider;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.SwitchTabEvent;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.ad.BaoquGameActivity;
import com.newsuper.t.juejinbao.ui.home.entity.FinishTaskEntity;
import com.newsuper.t.juejinbao.ui.home.entity.NewTaskEvent;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.TabChangeEvent;
import com.newsuper.t.juejinbao.ui.home.ppw.RewardMoreCoinPop_2;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.movie.activity.WebActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.BoxShareEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.movie.view.GoldDialog;
import com.newsuper.t.juejinbao.ui.movie.view.TreasureBoxDialog;
import com.newsuper.t.juejinbao.ui.my.activity.InviteFriendActivity;
import com.newsuper.t.juejinbao.ui.my.activity.UserInfoActivity;
import com.newsuper.t.juejinbao.ui.my.dialog.FragmentMyConfigDialog;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserInfoEntity;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.ui.share.util.LogUtil;
import com.newsuper.t.juejinbao.ui.task.NewTaskDialog;
import com.newsuper.t.juejinbao.ui.task.NewTasksRewordDialog;
import com.newsuper.t.juejinbao.ui.task.adapter.TaskAdapter;
import com.newsuper.t.juejinbao.ui.task.adapter.TaskListAdapter;
import com.newsuper.t.juejinbao.ui.task.adapter.TaskSignAdapter;
import com.newsuper.t.juejinbao.ui.task.entity.BoxInfoEntity;
import com.newsuper.t.juejinbao.ui.task.entity.BoxTimeEntity;
import com.newsuper.t.juejinbao.ui.task.entity.SignEntity;
import com.newsuper.t.juejinbao.ui.task.entity.TaskListEntity;
import com.newsuper.t.juejinbao.ui.task.entity.TaskMsgEntity;
import com.newsuper.t.juejinbao.ui.task.entity.TaskSignBean;
import com.newsuper.t.juejinbao.ui.task.presenter.TaskDetailPresenterImpl;
import com.newsuper.t.juejinbao.ui.task.sleep.SleepMoneyActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.GlideImgsLoader;
import com.newsuper.t.juejinbao.utils.StringUtils;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;
import com.newsuper.t.juejinbao.view.ReceiveTwoEggsDialog;
import com.newsuper.t.juejinbao.view.ReceiveTwoEggsSuccessDialog;
import com.newsuper.t.juejinbao.view.ViewDragMode;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.listener.OnBannerListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.OnClick;
import io.paperdb.Paper;
import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class TaskDetailFragment extends BaseFragment<TaskDetailPresenterImpl, FragmentTaskDetailBinding> implements TaskDetailPresenterImpl.MvpView, TaskAdapter.OnItemClickListener, TaskListAdapter.OnItemClickListener {

    private List<TaskSignBean> signList = new ArrayList<>();
    private List<TaskListEntity.BBean> taskList = new ArrayList<>();
    private List<TaskListEntity.DayListBean> newTaskList = new ArrayList<>();
    private List<TaskListEntity.DayListBean> dayTaskList = new ArrayList<>();
    private TaskSignAdapter signAdapter;
    private TaskAdapter taskAdapter;
    private TaskListAdapter newTaskAdapter;
    private TaskListAdapter dayTaskAdapter;
    //用户信息用于大转盘判断手机号微信是否绑定
    private String mobile;
    private String unionid;
    //宝箱弹框
    protected ShareDialog mShareDialog;
    private TreasureBoxDialog treasureBoxDialog;
    private GoldDialog goldDialog;
    private long time;
    private long taskTime;
    private CountDownTimer openBoxTimer;
    private CountDownTimer newTaskTimer;
    private boolean getTreasureBoxTime = false; // 是否是进入页面获取时间
    private int openBoxIndex = -1; // 记录开启宝箱下标
    private boolean showNewTaskDialog = false;// 展示新手任务引导弹窗
    private UserDataEntity userDataEntity;
    private UserDataEntity.DataBean.Task task;

    private PollTask pollTask;
    private Timer pollTimer;
    private int countDownTime = COUNTDOWNINTERVAL;
    private static final int COUNTDOWNINTERVAL = 8 * 1000; //奖励转圈时间

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    // 每次Tab切换都刷
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabChange(TabChangeEvent tabChangeEvent) {
        if (((JunjinBaoMainActivity)mActivity).Is_Show_Movie && tabChangeEvent.getTabPosition() == 3) {
            // 默认清空标记任务
            Paper.book().write(PagerCons.SELECT_TAG, "");
            getData();
        }else if(!((JunjinBaoMainActivity)mActivity).Is_Show_Movie && tabChangeEvent.getTabPosition() == 2){
            // 默认清空标记任务
            Paper.book().write(PagerCons.SELECT_TAG, "");
            getData();
        }else{
            if (pollTimer != null) {
                pollTimer.cancel();
                pollTimer = null;
            }
            if (pollTask != null) {
                pollTask.cancel();
                pollTask = null;
            }
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onNewTaskEvent(NewTaskEvent event) {
        if(((JunjinBaoMainActivity)mActivity).Is_Show_Movie){
            if(event.getTabPosition() == 3){
                // 新手任务跳转
                showNewTaskDialog = true;
                showNewTaskReadDialog();
            }else if(event.getTabPosition() == 103){
                // 领鸡蛋任务跳转
                showReceiveTwoEggsDialog();
            }
        }else{
            if(event.getTabPosition() == 2){
                showNewTaskDialog = true;
                showNewTaskReadDialog();
            }else if(event.getTabPosition() == 102){
                // 领鸡蛋任务跳转
                showReceiveTwoEggsDialog();
            }
        }
    }

    @Override
    public void initView() {
        // 签到
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvSign.setLayoutManager(linearLayoutManager);
        signAdapter = new TaskSignAdapter(mActivity);
        mViewBinding.rvSign.setAdapter(signAdapter);
        // 活动中心
        taskAdapter = new TaskAdapter(mActivity, this);
        // 新手任务
        mViewBinding.rvNewTask.setLayoutManager(new LinearLayoutManager(mActivity));
        newTaskAdapter = new TaskListAdapter(mActivity, this);
        mViewBinding.rvNewTask.setAdapter(newTaskAdapter);
        // 日常任务
        mViewBinding.rvDayTask.setLayoutManager(new LinearLayoutManager(mActivity));
        dayTaskAdapter = new TaskListAdapter(mActivity, this);
        dayTaskAdapter.setOnLastItemClickListener(new TaskListAdapter.onLastItemClickListener() {
            @Override
            public void onClick() {
                mViewBinding.scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewBinding.scrollView.scrollTo(0, 10000);
                    }
                }, 100);
            }
        });

        mViewBinding.rvDayTask.setAdapter(dayTaskAdapter);

        mViewBinding.srl.setEnableLoadMore(false);
        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //任务左上角滚动轮播图
                mPresenter.getTaskMessage(mActivity);
                //任务签到接口
                mPresenter.signIn(mActivity);
                // 任务列表
                mPresenter.getTaskList(mActivity);
            }
        });

        //领鸡蛋
        mViewBinding.rlCircleReward.setOnClickListener(view -> {
            if(!Paper.book().read(PagerCons.FINISH_TASK2_RECEIVE_EGGS,false)
                && countDownTime == COUNTDOWNINTERVAL){
                showReceiveTwoEggsDialog();
            }
        });

        Glide.with(mActivity).load(R.mipmap.ic_game2).into(mViewBinding.ivGame1);
        Glide.with(mActivity).load(R.mipmap.ic_game1).into(mViewBinding.ivGame2);
        Glide.with(mActivity).load(R.mipmap.ic_game3).into(mViewBinding.ivGame3);


        new ViewDragMode(mViewBinding.rlBox);
    }

    @Override
    public void initData() {
    }

    private void getData() {
        if (LoginEntity.getIsLogin()) {
            mViewBinding.tvLogin.setVisibility(View.GONE);
            mViewBinding.tvSign.setVisibility(View.GONE);
            mViewBinding.tvNextDaySign.setVisibility(View.VISIBLE);
            //领鸡蛋
            if(!Paper.book().read(PagerCons.FINISH_TASK2_RECEIVE_EGGS,false)
                    && Paper.book().read(PagerCons.FINISH_TASK1_RECEIVE_EGGS,false)
                    && Paper.book().read(PagerCons.HAS_TASK_RECEIVE_EGGS,false)){
                mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
                if(countDownTime!=0 && countDownTime!=COUNTDOWNINTERVAL)
                    startCountDown();
            }else{
                mViewBinding.rlCircleReward.setVisibility(View.GONE);
            }
            getTreasureBoxTime = true;
            mPresenter.getTreasureBoxTime(mActivity);
            mPresenter.getUserInfo(mActivity);
        } else {
            mViewBinding.tvLogin.setVisibility(View.VISIBLE);
            mViewBinding.tvSign.setVisibility(View.VISIBLE);
            mViewBinding.tvNextDaySign.setVisibility(View.GONE);
        }

        int state = book().read(ShareDialog.SHARE_NOTIFY, 0);
        if (state == 2) {
            book().write(ShareDialog.SHARE_NOTIFY, 0);
            shareBoxBackInterface();
        }

        //任务左上角滚动轮播图
        mPresenter.getTaskMessage(mActivity);
        //任务签到接口
        if(!showNewTaskDialog)
            mPresenter.signIn(mActivity);
        // 任务列表
        mPresenter.getTaskList(mActivity);
    }

    @Override
    public void error(String str) {

    }

    @Override
    public void signIn(SignEntity data) {

        if (data.getData() != null) {
            if (data.getData().getPrompt_reward() == 1) {
                showRewardPop(Double.valueOf(data.getData().getCoin()));
            }
            //关闭任务小红点
            Message msg = new Message();
            msg.what = BusConstant.TASK_CLOSE_ALERT;
            BusProvider.getInstance().post(msg);
        }

        mViewBinding.tvNextDaySign.setText(String.format("今日已签到,明天可领%s金币", data.getData().getNext_coin()));
        signList.clear();
        if (data.getData().getList() != null && data.getData().getList().size() != 0) {
            for (int i = 0; i < data.getData().getList().size(); i++) {
                TaskSignBean taskSignBean = new TaskSignBean();
                taskSignBean.setCoin(data.getData().getList().get(i));
                taskSignBean.setDay(i + 1);
                signList.add(taskSignBean);
            }
        }

        if (data.getData().getSign_list() != null && data.getData().getSign_list().size() != 0) {
            for (int i = 0; i < data.getData().getSign_list().size(); i++) {
                signList.get(i).setSign(true);
            }
        } else {
            signList.get(0).setSign(LoginEntity.getIsLogin());
        }
        signAdapter.update(signList);
        mViewBinding.srl.finishRefresh();

      //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.MISSION_DAILY_ATTENDANCE);
    }

    @Override
    public void getTaskMessage(TaskMsgEntity data) {
        if (data.getData() == null || data.getData().size() == 0)
            return;
        mViewBinding.viewFlipper.removeAllViews();
        for (TaskMsgEntity.DataBean bean : data.getData()) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.model_task_flipper, null);
            TextView tvContent = view.findViewById(R.id.model_view_filpper_context);
            ImageView ivBg = view.findViewById(R.id.model_view_filpper_bg);
            Glide.with(mActivity).load(bean.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivBg);
            String remark = bean.getRemark();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                remark = String.valueOf(Html.fromHtml(remark, Html.FROM_HTML_MODE_LEGACY));
            } else {
                remark = String.valueOf(Html.fromHtml(remark));
            }
            String title = bean.getNickname() + " " + bean.getMinutes_text() + " " + bean.getTag_text() + " " + remark;
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(title);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDB6F")), title.indexOf(bean.getNickname()), bean.getNickname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF5DF")), title.indexOf(bean.getMinutes_text()), title.indexOf(bean.getMinutes_text()) + bean.getMinutes_text().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDB6F")), title.indexOf(bean.getTag_text()), title.indexOf(bean.getTag_text()) + bean.getTag_text().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (remark.contains("金币")) {
                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDB6F")), title.indexOf("获得"), title.indexOf("获得") + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), title.indexOf("获得") + 2, title.indexOf("金币"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDB6F")), title.indexOf("金币"), title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (remark.contains("掘金宝")) {
                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDB6F")), title.indexOf("获得"), title.indexOf("获得") + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), title.indexOf("获得") + 2, title.indexOf("掘金宝"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDB6F")), title.indexOf("掘金宝"), title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDB6F")), title.indexOf(bean.getRemark()), title.indexOf(bean.getRemark()) + bean.getRemark().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            tvContent.setText(stringBuilder);
            tvContent.setSingleLine();
            tvContent.setEllipsize(TextUtils.TruncateAt.END);
            mViewBinding.viewFlipper.addView(view);
        }
        mViewBinding.srl.finishRefresh();
    }

    @Override
    public void getTaskList(TaskListEntity data) {
        if (data.getData() == null)
            return;
        // 商学院
        if (data.getData().getBanner_lists() != null && data.getData().getBanner_lists().getA() != null &&
                data.getData().getBanner_lists().getA().size() != 0) {
            List<String> imgs = new ArrayList<>();
            for (int i = 0; i < data.getData().getBanner_lists().getA().size(); i++) {
                if (data.getData().getBanner_lists().getA().get(i).getStatus() == 1) {
                    imgs.add(data.getData().getBanner_lists().getA().get(i).getImg());
                }
            }
            mViewBinding.llBusiness.setVisibility(View.VISIBLE);
            mViewBinding.businessBanner.setVisibility(View.VISIBLE);
            mViewBinding.businessBanner.setImageLoader(new GlideImgsLoader());
            mViewBinding.businessBanner.setImages(imgs);
            mViewBinding.businessBanner.start();
            mViewBinding.businessBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (!LoginEntity.getIsLogin()) {
                        Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                    InviteFriendActivity.intentMe(mActivity);

//                    if (!TextUtils.isEmpty(data.getData().getBanner_lists().getA().get(position).getUrl())) {
//                        BridgeWebViewActivity.intentMe(getActivity(), data.getData().getBanner_lists().getA().get(position).getUrl());
//                    } else {
//                        ToastUtils.getInstance().show(mActivity, "入口消失在二次元了...");
//                    }
                }
            });
        } else {
            mViewBinding.llBusiness.setVisibility(View.GONE);
            mViewBinding.businessBanner.setVisibility(View.GONE);
        }

        // 活动中心
        if (data.getData().getBanner_lists() != null && data.getData().getBanner_lists().getB() != null &&
                data.getData().getBanner_lists().getB().size() != 0) {
            mViewBinding.llActivity.setVisibility(View.VISIBLE);
            mViewBinding.rvTask.setVisibility(View.VISIBLE);

            taskList.clear();
            for (TaskListEntity.BBean bean : data.getData().getBanner_lists().getB()) {
                if (bean.getStatus() == 1) {
                    taskList.add(bean);
                }
            }
            GridLayoutManager gridLayoutManager;
            switch (taskList.size()) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 9:
                    gridLayoutManager = new GridLayoutManager(mActivity, 3);
                    break;
                default:
                    gridLayoutManager = new GridLayoutManager(mActivity, 4);
                    break;
            }
            mViewBinding.rvTask.setLayoutManager(gridLayoutManager);
            taskAdapter.update(taskList);
            mViewBinding.rvTask.setAdapter(taskAdapter);
        } else {
            mViewBinding.llActivity.setVisibility(View.GONE);
            mViewBinding.rvTask.setVisibility(View.GONE);
        }
        // 新手任务
        if (data.getData().getFirst() == null || data.getData().getFirst().size() == 0) {
            mViewBinding.llNewTask.setVisibility(View.GONE);
            mViewBinding.rvNewTask.setVisibility(View.GONE);
        } else {
            mViewBinding.llNewTask.setVisibility(View.VISIBLE);
            mViewBinding.rvNewTask.setVisibility(View.VISIBLE);
            taskTime = data.getData().getDatetime_remaining();
            startNewTaskTime();
            newTaskList.clear();
            newTaskList.addAll(data.getData().getFirst());
            newTaskAdapter.update(newTaskList);
        }
        // 日常任务
        if (data.getData().getDay_list() == null || data.getData().getDay_list().size() == 0) {
            mViewBinding.llDayTask.setVisibility(View.GONE);
            mViewBinding.rvDayTask.setVisibility(View.GONE);
        } else {
            mViewBinding.llDayTask.setVisibility(View.VISIBLE);
            mViewBinding.rvDayTask.setVisibility(View.VISIBLE);

            dayTaskList.clear();
            dayTaskList.addAll(data.getData().getDay_list());
            dayTaskAdapter.update(dayTaskList);

            for (int i = 0; i < data.getData().getDay_list().size(); i++) {
                if (!TextUtils.isEmpty(data.getData().getDay_list().get(i).getTag()) &&
                        data.getData().getDay_list().get(i).getTag().equals("treasure_box")) {
                    openBoxIndex = i;
                    break;
                }
            }
        }
        mViewBinding.srl.finishRefresh();
    }

    @Override
    public void getTreasureBoxTime(BoxTimeEntity data) {
        if (data.getData() != 0) {
            time = data.getData();
            startTime();
        } else if (!getTreasureBoxTime) {
            mPresenter.treasureBoxSave(mActivity);
        }
    }

    private void startTime() {
        if (openBoxTimer != null) {
            openBoxTimer.cancel();
            openBoxTimer = null;
        }
        openBoxTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long l) {
                long day = l / (1000 * 60 * 60 * 24);
                long hour = (l - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60);
                long minute = (l - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60) / (1000 * 60);
                long second = (l - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60 - minute * 1000 * 60) / 1000;
                String hourStr = hour < 10 ? "0" + hour : String.valueOf(hour);
                String minuteStr = minute < 10 ? "0" + minute : String.valueOf(minute);
                String secondStr = second < 10 ? "0" + second : String.valueOf(second);
                String time = hourStr + ":" + minuteStr + ":" + secondStr;
                mViewBinding.tvTime.setText(time);

                try {
                    if (openBoxIndex != -1 && mViewBinding.rvDayTask != null && mViewBinding.rvDayTask.getLayoutManager() != null
                            && mViewBinding.rvDayTask.getLayoutManager().getChildAt(openBoxIndex) != null) {
                        TextView textView = Objects.requireNonNull(mViewBinding.rvDayTask.getLayoutManager().getChildAt(openBoxIndex)).findViewById(R.id.tv_tag);
                        textView.setText(time);
                    }
                } catch (Exception e) {
                    LogUtil.e(e.getMessage());
                }

            }

            @Override
            public void onFinish() {
                mViewBinding.tvTime.setText("立即开启");
            }
        };
        openBoxTimer.start();
    }

    private void startNewTaskTime() {
        if (newTaskTimer != null) {
            newTaskTimer.cancel();
            newTaskTimer = null;
        }
        newTaskTimer = new CountDownTimer(taskTime * 1000, 60000) {
            @Override
            public void onTick(long l) {
                long day = l / (1000 * 60 * 60 * 24);
                long hour = (l - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60);
                long minute = (l - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60) / (1000 * 60);
                String time = "还有" + day + "天" + hour + "时" + minute + "分结束";
                if (mViewBinding.tvTaskTime != null)
                    mViewBinding.tvTaskTime.setText(time);
            }

            @Override
            public void onFinish() {
                // 任务列表
                mPresenter.getTaskList(mActivity);
            }
        };
        newTaskTimer.start();
    }

    @Override
    public void treasureBoxSave(BoxInfoEntity data) {
        getTreasureBoxTime = true;
        mPresenter.getTreasureBoxTime(mActivity);
        showInvitationBox("", String.valueOf(data.getData().getCoin()), "", "");
    }

    @Override
    public void getUserInfoSuccess(UserInfoEntity data) {
        if (data.getData() == null)
            return;
        mobile = data.getData().getMobile();
        unionid = data.getData().getUnionid();
    }

    @Override
    public void getNewTaskRewardSuccess(BaseEntity data) {
        getTreasureBoxTime = false;
        mPresenter.getTreasureBoxTime(mActivity);
        if(data.getCode()==0){
            if(userDataEntity!=null && task!=null && userDataEntity.getData().getNewbie_task()!=null
                    && userDataEntity.getData().getNewbie_task().size()!=0){
                userDataEntity.getData().getNewbie_task().remove(task);
                Paper.book().write(PagerCons.PERSONAL,userDataEntity);
            }
            task=null;
        }
    }

    @Override
    public void onEggsWelfareSuccess(FinishTaskEntity data) {
        mViewBinding.rlCircleReward.setVisibility(View.GONE);
        if(data.getCode()==0){
            Paper.book().write(PagerCons.FINISH_TASK2_RECEIVE_EGGS,true);
            Paper.book().write(PagerCons.HAS_TASK_RECEIVE_EGGS,false);
            showReceiveTwoEggsSuccessDialog();
        }else{
            mViewBinding.circleProgress.setPercentage(0);
            countDownTime = COUNTDOWNINTERVAL;
            Paper.book().write(PagerCons.FINISH_TASK2_RECEIVE_EGGS,false);
            ToastUtils.getInstance().show(mActivity,data.getMessage());
        }
    }

    @OnClick({R.id.tv_login,R.id.tv_title, R.id.tv_sign_question, R.id.iv_business, R.id.rl_box, R.id.tv_end,R.id.ll_game, R.id.iv_game1, R.id.iv_game2, R.id.iv_game3})
    public void onClick(View view) {
        if (!ClickUtil.isNotFastClick()) {
            return;
        }

        switch (view.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(mActivity, GuideLoginActivity.class));
                break;
            case R.id.tv_title:
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TECH_EARN_MONEY);

             //   MobclickAgent.onEvent(MyApplication.getContext(), EventID.EARNMONEYPAGE_HOWEARN_CLICK);
                break;
            case R.id.tv_sign_question:
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_USER_FEED_BACK);
                FragmentMyConfigDialog configDialog = new FragmentMyConfigDialog(mActivity, 10);
                configDialog.show();

                break;
            case R.id.iv_business:
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_COLLAGE);
                break;
            case R.id.rl_box:
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                showRewordDialog();

              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.EARNMONEYPAGE_OPENBOX_CLICK);
                break;
            case R.id.tv_end:
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TECH_SECRET_BOOK);
                break;
            case R.id.ll_game:
            case R.id.iv_game1:
            case R.id.iv_game2:
            case R.id.iv_game3:
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                startActivity(new Intent(mActivity, BaoquGameActivity.class));
               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.MISSION_EVERYDAYMISSION_FISHADV);
                break;
        }
    }

    // 活动专区点击事件
    @Override
    public void click(TaskListEntity.BBean bean) {

        if (!ClickUtil.isNotFastClick()) {
            return;
        }

        if (bean.getIs_need_login() == 1 && !LoginEntity.getIsLogin()) {
            startActivity(new Intent(mActivity, GuideLoginActivity.class));
            return;
        }

        if (bean.getTitle().equals("敬请期待")) {
            ToastUtils.getInstance().show(mActivity, "敬请期待");
        } else {
            if (bean.getTitle().equals("邀请好友")) {
                InviteFriendActivity.intentMe(mActivity);
                return;
            } else if (bean.getTitle().equals("走路赚钱")) {
              ///  MobclickAgent.onEvent(MyApplication.getContext(), EventID.SPORT_ENTRANCE_BANNER);
            } else if (bean.getTitle().equals("睡眠赚")) {
                LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
                startActivity(new Intent(mActivity, SleepMoneyActivity.class));
                return;
            }else if(bean.getTitle().equals("商学院")){
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_COLLAGE);
                return;
            }

            if (bean.getTitle().equals("幸运大转盘") || bean.getIs_need_bind_wx() == 1) {
                if (TextUtils.isEmpty(unionid)) {
                    new AlertDialog.Builder(mActivity)
                            .setCancelable(true)
                            .setMessage("请先绑定微信")
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();
                                // 绑定微信
                                if (!StringUtils.isWxInstall(mActivity)) {
                                    ToastUtils.getInstance().show(mActivity, "您未安装微信");
                                    return;
                                }
                              //  authorization(SHARE_MEDIA.WEIXIN);
                            })
                            .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                            .create()
                            .show();
                    return;
                }
                //埋点（点击转盘入口）
               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.TURNTABLE_PUSH_ACTIVITY_BANNER);
            }

            if (bean.getUrl().contains("http")) {
                BridgeWebViewActivity.intentMe(mActivity, bean.getUrl());
            } else {
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINEO + bean.getUrl());
            }
        }
    }

    // 新手任务日常任务点击事件
    @Override
    public void click(TaskListEntity.DayListBean bean) {

        if (!ClickUtil.isNotFastClick()) {
            return;
        }

        if (!LoginEntity.getIsLogin()) {
            startActivity(new Intent(mActivity, GuideLoginActivity.class));
            return;
        }
        if (!TextUtils.isEmpty(bean.getTag())) {
            // 标记任务刷新数据后依然展开显示
            Paper.book().write(PagerCons.SELECT_TAG, bean.getTag());
            switch (bean.getTag()) {
                case "wealth_plan":// 阅读赚车赚房
                    jumpTab(null, 1, 2, "?task=1");
                    ToastUtils.getInstance().show(mActivity, "认真阅读赚车赚房页面内容，即可获得奖励");
                    break;
                case "wechat_certification"://微信认证
                    if (!StringUtils.isWxInstall(mActivity)) {
                        ToastUtils.getInstance().show(mActivity, "您未安装微信");
                        return;
                    }
                   // authorization(SHARE_MEDIA.WEIXIN);
                    break;
                case "qq_certification"://关联QQ
                    if (!Utils.isQQClientAvailable(mActivity)) {
                        Toast.makeText(mActivity, "未安装QQ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                   // authorization(SHARE_MEDIA.QQ);
                    break;
                case "fill_invitation_code"://填邀请码
                    BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_SET_INVATE_CODE);
                    break;
                case "inviting_friend_first"://首次邀请好友
                case "inviting_friend_mobile_first"://首次邀请手机好友
                case "inviting_friend"://邀请好友
                case "inviting_friend_mobile"://邀请手机好友
                    //BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_INVITE_FRIEND);
                    startActivity(new Intent(mActivity, InviteFriendActivity.class));

                  //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.MISSION_INVITEFRIEND);
                    break;
                case "questionnaire_investigation"://问卷调查
                    BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_QUESTIONAIRE);
                    break;
                case "phone_bind"://手机号认证

                    break;
                case "play_games_on_trial"://试玩任务奖励
                    startActivity(new Intent(mActivity, BaoquGameActivity.class));
                    break;
                case "treasure_box"://开宝箱
                    getTreasureBoxTime = false;
                    mPresenter.getTreasureBoxTime(mActivity);
                    break;
                case "reading_article"://浏览奖励
                    jumpTab(null, 1, 0, null);
                    ToastUtils.getInstance().show(mActivity, "选择任意文章进行阅读，即可获得奖励");
                    break;
                case "read_60minute"://每日阅读时长65分钟
                    jumpTab(null, 1, 0, null);
                    ToastUtils.getInstance().show(mActivity, "每日累计阅读，即可获得奖励");
                    break;
                case "sharing_article"://分享文章
                    jumpTab(null, 1, 0, null);
                    ToastUtils.getInstance().show(mActivity, "分享文章给新用户浏览，即可赚取奖励");
                   // MobclickAgent.onEvent(MyApplication.getContext(), EventID.MISSION_EVERYDAYMISSION_SHARE_ARTICLE_CLICK);
                    break;
                case "exposure_to_income"://晒收入
                    BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_WALLET);
                   // MobclickAgent.onEvent(MyApplication.getContext(), EventID.MISSION_EVERYDAYMISSION_SHARE_INCOME_CLICK);
                    break;
                case "walk_make_coin"://走路赚钱
                    BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_WALK_MONEY);

                   // MobclickAgent.onEvent(MyApplication.getContext(), EventID.SPORT_ENTRANCE_BUTTON);
                    break;
                case "view_ad_movie"://完成视频任务赚金币
                    PlayRewardVideoAdActicity.intentMe(mActivity, PlayRewardVideoAdActicity.WATCHAD);
//                    GDTRewardVideoActivityActivity.intentMe(mActivity, PlayRewardVideoAdActicity.WATCHAD);
                    break;
                case "page_view_reward_by_ip"://分享页被浏览
                    jumpTab("", 0, 0, null);
                    ToastUtils.getInstance().show(mActivity, "分享文章给新用户浏览，即可赚取奖励");
                    break;
                case "finish_profile_info":// 完善个人信息
//                    PrivacyActivity.intentMe(mActivity,1,PrivacyActivity.REQUEST_CODE_TASK);
                    UserInfoActivity.intentMe(LoginEntity.getInvitation(), 0, mActivity, 1);
                    break;
                case "download_app_task": //下载试玩领金币
                 //   AdManager.getInstance(mActivity).openCommonTaskList(mActivity);
                    break;
                case "wx_applet_task": //小程序任务
                   // AdManager.getInstance(mActivity).openWeChatTaskSetList(mActivity);
                    break;
            }
        } else {
            if (!TextUtils.isEmpty(bean.getLink())) {
                if (bean.getLink().contains("http")) {
                    WebActivity.intentMe(mActivity, "广告", bean.getLink());
                } else {
                    WebActivity.intentMe(mActivity, "广告", RetrofitManager.WEB_URL_ONLINEO + bean.getLink());
                }
            }
        }
    }

    //宝箱弹框
    public void showInvitationBox(String url, String coin, String share_friend, String inviting_friend) {
        if (LoginEntity.getIsLogin()) {
            if (treasureBoxDialog == null) {
                treasureBoxDialog = new TreasureBoxDialog(this, url, coin, share_friend, inviting_friend);
            }
            treasureBoxDialog.show();
        } else {
            Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
            mActivity.startActivity(intent);
        }
    }

    public void jumpTab(String path, int self, int index, String query) {
        //跳转tab
        if (TextUtils.isEmpty(path)) {
            //通知跳转
            Message msg = new Message();
            msg.what = BusConstant.MAIN_SWITCH;
            msg.arg1 = index;
            msg.obj = query;

            BusProvider.getInstance().post(msg);
            startActivity(new Intent(mActivity, JunjinBaoMainActivity.class));
        } else {
            if (self == 1) {
                //刷新页面
                initData();
            } else {
                //跳转新页面
                if (path.contains("http")) {
                    if (!ClickUtil.isNotFastClick()) {
                        return;
                    }
                    BridgeWebViewActivity.intentMe(mActivity, path);
                } else {
                    if (!ClickUtil.isNotFastClick()) {
                        return;
                    }
                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINEO + path);
                }
            }
        }
    }

//    public void authorization(SHARE_MEDIA share_media) {
//        UMShareAPI.get(mActivity).getPlatformInfo(mActivity, share_media, umAuthListener);
//    }

    /**
     * 授权监听
     */
//    private UMAuthListener umAuthListener = new UMAuthListener() {
//        @Override
//        public void onStart(SHARE_MEDIA platform) {
//            //授权开始的回调
//            Log.e("TAG", "onStart =============》》》》》》》" + "授权开始的回调");
//        }
//
//        @Override
//        public void onComplete(SHARE_MEDIA share_media, int action, Map<String, String> map) {
//
//            Log.e("TAG", "onComplete =============》》》》》》》" + "授权完成");
//            //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
//            String uid = map.get("uid");
//            String openid = map.get("openid");//微博没有
//            String unionid = map.get("unionid");//微博没有
//            String access_token = map.get("access_token");
//            String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
//            String expires_in = map.get("expires_in");
//            String name = map.get("name");
//            String gender = map.get("gender");
//            String iconurl = map.get("iconurl");
//            Log.e("TAG", "onComplete: 打印第三方获取的参数=======>>>>>" + "name=" + name
//                    + "uid=" + uid
//                    + "openid=" + openid
//                    + "unionid =" + unionid
//                    + "access_token =" + access_token
//                    + "refresh_token=" + refresh_token
//                    + "expires_in=" + expires_in
//                    + "gender=" + gender
//                    + "iconurl=" + iconurl);
//
//
//            //绑定第三方接口
//            Map<String, String> paramMap = new HashMap<>();
//            paramMap.put("access_token", access_token);
//            paramMap.put("openid", openid);
//            if (share_media.equals(SHARE_MEDIA.WEIXIN)) {
//                paramMap.put("platform", "wechat");
//
//                rx.Observable<BindThirdEntity> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).bindWX(paramMap).map((new HttpResultFunc<BindThirdEntity>()));
//                Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BindThirdEntity>() {
//                    @Override
//                    public void next(BindThirdEntity testBean) {
//                        //关联成功
//                        if (testBean.getCode() == 0) {
//                            showReward(Utils.FormatGold(testBean.getData().getCoin()), "奖励到账", "认证微信成功");
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//                            builder.setTitle(testBean.getMsg());
//                            //点击对话框以外的区域是否让对话框消失
//                            builder.setCancelable(true);
//                            //设置正面按钮
//                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                            builder.show();
//                        }
//                    }
//
//                    @Override
//                    public void error(String target, Throwable e, String errResponse) {
//                        Log.e("zy", "bindThird error=====++++e=" + e.toString());
//                    }
//                }, mActivity, false);
//                RetrofitManager.getInstance(mActivity).toSubscribe(observable, (Subscriber) rxSubscription);
//
//            } else if (share_media.equals(SHARE_MEDIA.QQ)) {
//                paramMap.put("platform", "qq");
//
//                rx.Observable<BindThirdEntity> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).bindThird(paramMap).map((new HttpResultFunc<BindThirdEntity>()));
//                Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BindThirdEntity>() {
//                    @Override
//                    public void next(BindThirdEntity testBean) {
//                        //关联成功
//                        if (testBean.getCode() == 0) {
//
//                            if (!TextUtils.isEmpty(testBean.getData().getUser_token())) {
//                                LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
//                                loginEntity.getData().setUser_token(testBean.getData().getUser_token());
//                                Paper.book().write(PagerCons.USER_DATA, loginEntity);
//                            }
//                            showReward(Utils.FormatGold(testBean.getData().getCoin()), "奖励到账", "关联QQ成功");
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//                            builder.setTitle(testBean.getMsg());
//                            //点击对话框以外的区域是否让对话框消失
//                            builder.setCancelable(true);
//                            //设置正面按钮
//                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                            builder.show();
//                        }
//                    }
//
//                    @Override
//                    public void error(String target, Throwable e, String errResponse) {
//                        Log.e("zy", "bindThird error=====++++e=" + e.toString());
//                    }
//                }, mActivity, false);
//                RetrofitManager.getInstance(mActivity).toSubscribe(observable, (Subscriber) rxSubscription);
//            } else {
//                return;
//            }
//
//
//            //直接取消授权
//            UMShareAPI.get(mActivity).deleteOauth(mActivity, share_media, new UMAuthListener() {
//                @Override
//                public void onStart(SHARE_MEDIA share_media) {
//
//                }
//
//                @Override
//                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//
//                }
//
//                @Override
//                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//
//                }
//
//                @Override
//                public void onCancel(SHARE_MEDIA share_media, int i) {
//
//                }
//            });
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            Log.e("TAG", "onError =============》》》》》》》" + "onError");
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform, int action) {
//            Log.e("TAG", "onCancel =============》》》》》》》" + "onCancel");
//        }
//    };

    //显示奖励框
    public void showReward(String gold, String title, String detail) {
        if (goldDialog == null) {
            goldDialog = new GoldDialog(mActivity);
        }
        goldDialog.show(gold, title, detail);
    }

    //宝箱分享回调调用接口
    public void shareBoxBackInterface() {
        //宝箱分享成功接口回调
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tag", "treasure_box");
        rx.Observable<BoxShareEntity> observable = RetrofitManager.getInstance(getActivity()).create(ApiService.class).BoxShareSuccess(paramMap).map((new HttpResultFunc<BoxShareEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BoxShareEntity>() {
            @Override
            public void next(BoxShareEntity testBean) {
                if (testBean.getData().getCoin() > 0) {
                    showReward(Utils.FormatGold(testBean.getData().getCoin()), "获得奖励", "分享奖励");
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
            }
        }, getActivity(), false);
        RetrofitManager.getInstance(getActivity()).toSubscribe(observable, (Subscriber) rxSubscription);
    }

    //分享弹框
    public void showShareDialog(ShareInfo shareInfo, boolean isBox) {

        if (LoginEntity.getIsLogin()) {
            mShareDialog = new ShareDialog(getActivity(), shareInfo, new ShareDialog.OnResultListener() {
                @Override
                public void result() {
                    //宝箱分享成功，调用接口
                    if (isBox) {
                        shareBoxBackInterface();
                    }


                }
            });
//          分享方式为空时 显示分享框
            if (TextUtils.isEmpty(shareInfo.getPlatform_type())) {
                mShareDialog.show();
            }
        } else {
            Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
            getActivity().startActivity(intent);
            return;
        }
    }

    // 签到弹窗
    public void showRewardPop(double coin) {
        RewardEntity rewardEntity = new RewardEntity();
        rewardEntity.setCoin(coin);
        rewardEntity.setRewardType("签到");
        rewardEntity.setTitle("签到成功");

        RewardMoreCoinPop_2 acticleRewardPop = new RewardMoreCoinPop_2(mActivity);
        acticleRewardPop.setView(rewardEntity);
        acticleRewardPop.setOnClickListener(new RewardMoreCoinPop_2.OnClickListener() {
            @Override
            public void onclick(View view) {
                PlayRewardVideoAdActicity.intentMe(mActivity, PlayRewardVideoAdActicity.WATCHAD);
            }
        });
        acticleRewardPop.showPopupWindow();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

//    /**
//     * 隐私设置任务奖励
//     *
//     * @param event
//     */
//    @org.greenrobot.eventbus.Subscribe()
//    public void privacySettingReward(PrivacySettingRewardEvent event) {
//        Log.i(PlayRewardVideoAdActicity.TAG, "在首页接口issuccess的值：" + event.isSuccess);
//        if (event.isSuccess) {
//            RewardEntity entity = new RewardEntity(
//                    "奖励到账",
//                    350,
//                    "隐私设置奖励",
//                    "",
//                    true
//            );
//            ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
//            acticleRewardPop.setView(entity);
//            acticleRewardPop.showPopupWindow();
//        }
//    }

    @Override
    public void onStop() {
        super.onStop();
        if (openBoxTimer != null) {
            openBoxTimer.onFinish();
            openBoxTimer.cancel();
            openBoxTimer = null;
        }
        if (newTaskTimer != null) {
            newTaskTimer.onFinish();
            newTaskTimer.cancel();
            newTaskTimer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewBinding.viewFlipper.removeAllViews();
        EventBus.getDefault().unregister(this);
        if (pollTimer != null) {
            pollTimer.cancel();
            pollTimer = null;
        }
        if (pollTask != null) {
            pollTask.cancel();
            pollTask = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 新手任务奖励引导弹窗
     */
    public void showNewTaskReadDialog() {
        NewTaskDialog dialog = new NewTaskDialog(mActivity);
        dialog.setOnDismissListener(dialog1 -> {
            showRewordDialog();
        });
        dialog.show();
    }

    /**
     * 新手任务奖励弹窗
     */
    private void showRewordDialog() {
        userDataEntity = Paper.book().read(PagerCons.PERSONAL);
        if(userDataEntity==null || userDataEntity.getData().getNewbie_task()==null
            || userDataEntity.getData().getNewbie_task().size()==0){
            getTreasureBoxTime = false;
            mPresenter.getTreasureBoxTime(mActivity);
            return;
        }
        for (UserDataEntity.DataBean.Task bean: userDataEntity.getData().getNewbie_task()) {
            if(bean.getTag().equals("newbie_task_third"))
                task = bean;
        }
        if(task==null){
            getTreasureBoxTime = false;
            mPresenter.getTreasureBoxTime(mActivity);
            return;
        }
        NewTasksRewordDialog dialog = new NewTasksRewordDialog(mActivity,task);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setClickListener((position, view) -> {
            Map<String,String> map = new HashMap<>();
            map.put("tag",task.getTag());
            mPresenter.getNewTaskReward(map,mActivity);
        });
        dialog.setOnDismissListener(dialogInterface -> {
            if(showNewTaskDialog)
                mPresenter.signIn(mActivity);
            showNewTaskDialog = false;
        });
        dialog.show();
    }

    /**
     * 领鸡蛋引导弹窗
     */
    private void showReceiveTwoEggsDialog() {
        ReceiveTwoEggsDialog dialog = new ReceiveTwoEggsDialog(mActivity,"浏览时长转满一圈\n再获得2枚鸡蛋奖励！");
        dialog.setOnDismissListener(dialogInterface -> {
            mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
            mViewBinding.ivEggs.setImageResource(R.mipmap.ic_egg);
            startCountDown();
        });
        dialog.show();
    }

    /**
     * 领鸡蛋成功弹窗
     */
    private void showReceiveTwoEggsSuccessDialog() {
        ReceiveTwoEggsSuccessDialog dialog = new ReceiveTwoEggsSuccessDialog(mActivity,2);
        dialog.setClickListener((position, object) -> {
            if(position==1){
                EventBus.getDefault().post(new SwitchTabEvent(SwitchTabEvent.HOME));
            }
        });
        dialog.show();
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
            handler.post(() -> {
                countDownTime--;
                if (countDownTime == 0) {
                    mViewBinding.circleProgress.setPercentage(1000);
                    if (pollTask != null) {
                        pollTask.cancel();
                    }
                    //领取两枚鸡蛋
                    mPresenter.getEggsWelfare(mActivity);
                } else {
                    mViewBinding.circleProgress.setPercentage((COUNTDOWNINTERVAL - countDownTime) * 1000 / COUNTDOWNINTERVAL);
                }
            });
        }
    }
}

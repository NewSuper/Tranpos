package com.newsuper.t.juejinbao.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.juejinchain.android.R;
import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.event.SwitchTabEvent;
import com.juejinchain.android.jsbridge.BridgeHandler;
import com.juejinchain.android.jsbridge.BridgeWebView;
import com.juejinchain.android.jsbridge.CallBackFunction;
import com.juejinchain.android.module.home.entity.FinishTaskEntity;
import com.juejinchain.android.module.home.entity.NewTaskEvent;
import com.juejinchain.android.module.home.entity.TabChangeEvent;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.movie.entity.BoxShareEntity;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.movie.utils.WebViewUtils;
import com.juejinchain.android.module.movie.view.TreasureBoxDialog;
import com.juejinchain.android.module.share.dialog.ShareDialog;
import com.juejinchain.android.module.share.entity.ShareInfo;
import com.juejinchain.android.view.CirclePercentView;
import com.qq.e.comm.util.StringUtil;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;
import com.ys.network.utils.NetworkUtils;
import com.ys.network.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import rx.Subscriber;
import rx.Subscription;

public class WebFragment extends ImmersionFragment {
    View view;

    private RelativeLayout rlStatus;

    protected BridgeWebView webView;

    private String type;

    //加载地址
    protected String url;

    protected WebViewUtils webViewUtils;

    //分享弹框
    protected ShareDialog mShareDialog;

    //宝箱弹框
    protected TreasureBoxDialog treasureBoxDialog;

    protected boolean isLogin = false;

    private LinearLayout llError;
    private TextView tv_error_msg;
    private RelativeLayout rlCircleReward;
    private ImageView ivEggs;
    private CirclePercentView circleProgress;
    private Activity mActivity;

    private PollTask pollTask;
    private Timer pollTimer;
    private int countDownTime = COUNTDOWNINTERVAL;
    private static final int COUNTDOWNINTERVAL = 8 * 1000; //奖励转圈时间

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
        if(url == null) {
            url = getArguments().getString("url");
            type = getArguments().getString("type");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_web, container, false);
        llError = view.findViewById(R.id.ll_error);
        rlStatus = view.findViewById(R.id.rl_status);
        tv_error_msg = view.findViewById(R.id.tv_error_msg);
        rlCircleReward = view.findViewById(R.id.rl_circle_reward);
        ivEggs = view.findViewById(R.id.iv_eggs);
        circleProgress = view.findViewById(R.id.circle_progress);
        webView = view.findViewById(R.id.webView);
        if(!StringUtil.isEmpty(type) && type.equals("赚车赚房")){
            rlStatus.setVisibility(View.VISIBLE);
            ImmersionBar.with(this).titleBar(rlStatus).init();
            //领鸡蛋
            if(!Paper.book().read(PagerCons.FINISH_TASK1_RECEIVE_EGGS,false) &&
                    Paper.book().read(PagerCons.HAS_TASK_RECEIVE_EGGS,false)){
                rlCircleReward.setVisibility(View.VISIBLE);
            }else{
                rlCircleReward.setVisibility(View.GONE);
            }
            //切换Tab
            webView.registerHandler("changeTab", new BridgeHandler() {

                @Override
                public void handler(String datas, CallBackFunction function) {
                    try {
                        JSONObject jsonObject = new JSONObject(datas);
                        String position = jsonObject.optString("position");
                        switch (position){
                            case "0":
                                rlStatus.setBackgroundResource(R.mipmap.bg_status);
                                break;
                            default:
                                rlStatus.setBackgroundResource(R.mipmap.bg_status2);
                                break;
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        webViewUtils = new WebViewUtils(null, this, webView, view.findViewById(R.id.rl_loading), new WebViewUtils.WebViewUtilsListener() {
            @Override
            public void logout() {
                Utils.logout(getActivity());
            }

            @Override
            public void showRightBottomAD(String imgUrl, String link) {

            }

            @Override
            public void getWebMovieInfo(String url) {

            }

            @Override
            public void getWebLiveInfo(String url, String js) {

            }
        });
        webView.setWebChromeClient(new MyWebChromeClient());
        clearCache();

        Log.e("TAG", "initView:注解状态2=======>>>>>>> " + url);


        if(!NetworkUtils.isConnected(getActivity())){
            llError.setVisibility(View.VISIBLE);
            tv_error_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!NetworkUtils.isConnected(getActivity())){
                        ToastUtils.getInstance().show(getActivity() , "网络未链接");
                    }else{
                        llError.setVisibility(View.GONE);
                        webView.loadUrl(url);
                    }
                }
            });
        }else{
            webView.loadUrl(url);
        }

        //领鸡蛋
        rlCircleReward.setOnClickListener(view -> {
            if(!Paper.book().read(PagerCons.FINISH_TASK1_RECEIVE_EGGS,false) &&
                    countDownTime == COUNTDOWNINTERVAL){
                showReceiveTwoEggsDialog();
            }
        });
        return view;
    }

    @Override
    public void initImmersionBar() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabChange(TabChangeEvent event) {
        if (((MainActivity) mActivity).Is_Show_Movie && event.getTabPosition() == 2 || !((MainActivity) mActivity).Is_Show_Movie && event.getTabPosition() == 1) {
            if(!Paper.book().read(PagerCons.FINISH_TASK1_RECEIVE_EGGS,false) &&
                    Paper.book().read(PagerCons.HAS_TASK_RECEIVE_EGGS,false)){
                rlCircleReward.setVisibility(View.VISIBLE);
                if(countDownTime!=0 && countDownTime!=COUNTDOWNINTERVAL)
                    startCountDown();
            }else{
                rlCircleReward.setVisibility(View.GONE);
            }
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
        if(((MainActivity)mActivity).Is_Show_Movie && event.getTabPosition() == 102){
            showReceiveTwoEggsDialog();
        }else if(!((MainActivity)mActivity).Is_Show_Movie && event.getTabPosition() == 101){
            showReceiveTwoEggsDialog();
        }
    }

    private void showReceiveTwoEggsDialog() {
        ReceiveTwoEggsDialog dialog = new ReceiveTwoEggsDialog(mActivity,"转满一圈后就可以\n获得鸡蛋奖励喲！");
        dialog.setOnDismissListener(dialogInterface -> {
            rlCircleReward.setVisibility(View.VISIBLE);
            ivEggs.setImageResource(R.mipmap.ic_egg);
            startCountDown();
        });
        dialog.show();
    }

    private class MyWebChromeClient extends WebChromeClient {




        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(getActivity() , message , Toast.LENGTH_SHORT).show();
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onHideCustomView() {



        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getActivity()).load(R.drawable.ic_top_loading).into((ImageView) view.findViewById(R.id.progress));
        if (LoginEntity.getIsLogin()) {
            isLogin = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        webViewUtils.onActivityResult(requestCode, resultCode, data);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        if (isAdded() && !isHidden()) {
            onShowFragment();
        }
    }

    @Override
    public void onPause() {
        if (isVisible()) {
            onHideFragment();
        }
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onShowFragment();
        } else {
            onHideFragment();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void onShowFragment() {

        if(webView == null){
            return;
        }

        if (LoginEntity.getIsLogin() && !isLogin) {
            webView.clearCache(true);
            webView.reload();
            isLogin = true;
        } else if (!LoginEntity.getIsLogin() && isLogin) {
            clearCache();
            webView.reload();
            isLogin = false;
        } else {
            //刷新前端数据
            webView.loadUrl("javascript:window.refresh()");
        }
    }

    private void onHideFragment() {
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        if (webView != null) {
            webView.destroy();
        }
        if(webViewUtils!=null){
            webViewUtils.onDestory();
        }
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
        super.onDestroyView();
    }

    //加载新的URL
    public void loadUrl(String urlAndQuery) {
//        clearCache();
        if (webView != null) {
            webView.loadUrl(urlAndQuery);
        }else{
            //oncreate时调用
            url = urlAndQuery;
        }
    }

    //分享弹框
    public void showShareDialog(ShareInfo shareInfo , boolean isBox) {

        if (LoginEntity.getIsLogin()) {
            mShareDialog = new ShareDialog(getActivity(), shareInfo, new ShareDialog.OnResultListener() {
                @Override
                public void result() {
                    //宝箱分享成功，调用接口
                    if(isBox) {
                        shareBoxBackInterface();
                    }


                }
            });
//          分享方式为空时 显示分享框
            if(TextUtils.isEmpty(shareInfo.getPlatform_type())){
                mShareDialog.show();
            }
        } else {
            Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
            getActivity().startActivity(intent);
            return;
        }
    }

    //宝箱分享回调调用接口
    public void shareBoxBackInterface(){
        //宝箱分享成功接口回调
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tag", "treasure_box");
        rx.Observable<BoxShareEntity> observable = RetrofitManager.getInstance(getActivity()).create(ApiService.class).BoxShareSuccess(paramMap).map((new HttpResultFunc<BoxShareEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BoxShareEntity>() {
            @Override
            public void next(BoxShareEntity testBean) {
                if(testBean.getData().getCoin() > 0) {
//                                GoldDialog goldDialog = new GoldDialog(getActivity());
//                                goldDialog.show(testBean.getData().getCoin(), "获得奖励", "分享奖励");
                    webViewUtils.showReward(Utils.FormatGold(testBean.getData().getCoin()) , "获得奖励", "分享奖励");
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
//                baseView.error(errResponse);
            }
        }, getActivity(), false);
        RetrofitManager.getInstance(getActivity()).toSubscribe(observable, (Subscriber) rxSubscription);
    }

    //宝箱弹框
    public void showInvitationBox(String url , String coin , String share_friend , String inviting_friend) {
        if (LoginEntity.getIsLogin()) {
            if (treasureBoxDialog == null) {
//                shareInfo.setUrl_type(ShareInfo.TYPE_MOVIE);
//                shareInfo.setUrl_path(ShareInfo.PATH_MOVIE);
                treasureBoxDialog = new TreasureBoxDialog(this, url , coin , share_friend , inviting_friend);
            }
            treasureBoxDialog.show();
        } else {
            Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
            getActivity().startActivity(intent);
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void clearCache() {
        try {
            CookieSyncManager.createInstance(getActivity().getApplicationContext());
            CookieManager cookieManager = CookieManager.getInstance();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.removeSessionCookies(null);
                cookieManager.removeAllCookie();
                cookieManager.flush();
            } else {
//                cookieManager.removeSessionCookies(null);
//                cookieManager.removeAllCookie();
//                CookieSyncManager.getInstance().sync();
            }
            WebStorage.getInstance().deleteAllData();
        }catch (Exception e){}

        webView.clearCache(true);
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
                    circleProgress.setPercentage(1000);
                    if (pollTask != null) {
                        pollTask.cancel();
                    }
                    //领取两枚鸡蛋
                    getEggsWelfare();
                } else {
                    circleProgress.setPercentage((COUNTDOWNINTERVAL - countDownTime) * 1000 / COUNTDOWNINTERVAL);
                }
            });
        }
    }

    public void getEggsWelfare() {
        Map<String, String> map = new HashMap<>();
        rx.Observable<FinishTaskEntity> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).
                finishTask(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<>()));
        RetrofitManager.getInstance(mActivity).toSubscribe(observable, new Subscriber<FinishTaskEntity>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(FinishTaskEntity entity) {
                rlCircleReward.setVisibility(View.GONE);
                if(entity.getCode()==0){
                    showReceiveTwoEggsSuccessDialog();
                    Paper.book().write(PagerCons.FINISH_TASK1_RECEIVE_EGGS,true);
                }else{
                    circleProgress.setPercentage(0);
                    countDownTime = COUNTDOWNINTERVAL;
                    Paper.book().write(PagerCons.FINISH_TASK1_RECEIVE_EGGS,false);
                    ToastUtils.getInstance().show(mActivity,entity.getMessage());
                }
            }
        });
    }

    private void showReceiveTwoEggsSuccessDialog() {
        ReceiveTwoEggsSuccessDialog dialog = new ReceiveTwoEggsSuccessDialog(mActivity,1);
        dialog.setClickListener((position, object) -> {
            EventBus.getDefault().post(new SwitchTabEvent(SwitchTabEvent.TASK,false,true));
        });
        dialog.show();
    }
}

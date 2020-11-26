package com.newsuper.t.juejinbao.ui.my;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentMyNewBinding;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.ad.GDTHolder;
import com.newsuper.t.juejinbao.ui.home.dialog.HomeTipsAlertDialog;
import com.newsuper.t.juejinbao.ui.home.entity.TabChangeEvent;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.DebugActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.WebActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.ResponseEntity;
import com.newsuper.t.juejinbao.ui.my.activity.InviteFriendActivity;
import com.newsuper.t.juejinbao.ui.my.activity.SettingActivity;
import com.newsuper.t.juejinbao.ui.my.activity.UserInfoActivity;
import com.newsuper.t.juejinbao.ui.my.dialog.FragmentMyConfigDialog;
import com.newsuper.t.juejinbao.ui.my.entity.AdFirstEntity;
import com.newsuper.t.juejinbao.ui.my.entity.AdTwoEntity;
import com.newsuper.t.juejinbao.ui.my.entity.MyUnreadMessageEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;
import com.newsuper.t.juejinbao.ui.my.presenter.impl.MyFragmentPresenterImpl;
import com.newsuper.t.juejinbao.ui.share.constant.ShareType;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.GlideImageLoader;
import com.newsuper.t.juejinbao.utils.GlideImgsLoader;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.NoDoubleListener;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.comm.util.AdError;
import com.qq.e.comm.util.StringUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.youth.banner.listener.OnBannerListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.paperdb.Paper;
import rx.Subscriber;
import rx.Subscription;

public class MyFragment extends BaseFragment<MyFragmentPresenterImpl, FragmentMyNewBinding> implements MyFragmentPresenterImpl.SettingView {
    @BindView(R.id.fragment_my_login_on)
    View llLoginOn;
    @BindView(R.id.fragment_my_login_out)
    View llLoginOut;
    Unbinder bind;
    private boolean isOneVisibility = true;
    private boolean isTwoVisibility = true;
    private boolean isThreeVisibility = true;
    private boolean isForeVisibility = true;
    private boolean isFiveVisibility = true;
    private boolean isSexVisibility = true;
    private boolean isOneOnTach = true;
    private boolean isTwoOnTach = true;
    private boolean isThreeOnTach = true;
    private boolean isForeOnTach = true;
    private boolean isFiveOnTach = true;
    private boolean isSexOnTach = true;
    private int oneId = 0;
    private int twoId = 0;
    private int threeId = 0;
    private int foreId = 0;
    private int fiveId = 0;
    private int sexId = 0;
    private FragmentMyConfigDialog configDialog;
    //输入邀请码奖励
    private boolean isShow = false;
    private ShareDialog mShareDialog;
    private UserDataEntity userDataEntity;
    private BigDecimal bigCompare = new BigDecimal(1.00);
    String actvityEnterUrl = "";

    private long newUserStartTime = 0;//统计新用户使用时长
    private long oldUserStartTime = 0;//统计老用户使用时长
    private long touristsStartTime = 0;//统计游客使用时长


    //广点通
    UnifiedBannerView bv;
    String posId;


    //过审控制
    private HashMap<String, Integer> auditMap;

    public static MyFragment newInstance(Bundle data) {
        MyFragment fragment = new MyFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_new, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {
        auditMap = Paper.book().read(PagerCons.KEY_AUDIT_MAP);
        if (auditMap == null) {
            auditMap = new HashMap();
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        //debug入口
//        if (RetrofitManager.RELEASE) {
//            mViewBinding.modelFragmentDebug.setVisibility(View.GONE);
//        } else {
       mViewBinding.modelFragmentDebug.setVisibility(View.VISIBLE);
//        }
        mViewBinding.modelFragmentDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugActivity.intentMe(mActivity);
            }
        });

        mViewBinding.modelFragmentMyRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //请求data数据
                mPresenter.getUserData(new HashMap<String, String>(), getActivity());
                //请求第一个邀请广告位的数据
                mPresenter.getAdFirst(new HashMap<String, String>(), getActivity());
                //请求第二个邀请广告位的数据
                mPresenter.getAdTwo(new HashMap<String, String>(), getActivity());
                //请求未读消息
                mPresenter.getMessge(new HashMap<String, String>(), getActivity());
            }
        });
        mViewBinding.imgActEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BridgeWebViewActivity.intentMe(mActivity, actvityEnterUrl);
            }
        });
        int mineMovieInfoControl = auditMap.get(Constant.KEY_MINE_MOVIE_REVIEW_CONTROL) == null ? 0 : auditMap.get(Constant.KEY_MINE_MOVIE_REVIEW_CONTROL);
        if (mineMovieInfoControl == 1) {
            mViewBinding.groupMovie.setVisibility(View.GONE);
        } else {
            mViewBinding.groupMovie.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        bind.unbind();
//        mViewBinding.fraagmentMyMarqueeView.removeAllViews();
        mViewBinding.fragmetMyFlipper.removeAllViews();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIsLoginEvent(SettingLoginEvent settingLoginEvent) {

        initViewGone();
    }

    private void initViewGone() {
        llLoginOut.setVisibility(View.VISIBLE);
        llLoginOn.setVisibility(View.GONE);
        mViewBinding.gropIdentity.setVisibility(View.GONE);
        mViewBinding.modelFragmentMyInvitationAbout.setVisibility(View.GONE);
        mViewBinding.fragmnetMyAdvOne.setVisibility(View.GONE);
        //金币余额
        mViewBinding.modelFragmentMyHaveMoney.setText("--");
//        mViewBinding.fragmentMyBalanceOfGoldConins.setText("--");
        //掘金宝个数
        mViewBinding.fragmentMyMoneyVcoin.setText("--");
        //       mViewBinding.framentMyBalanceCount.setText("--");
        //昨日收入
        mViewBinding.fragmentMyMoneyVcoinYesterday.setText("--");
//        mViewBinding.fragmentMyTheCurrentValue.setText("--");
//        mViewBinding.fragmentMyYesterdayHave.setText("--");
//        mViewBinding.fragmentMyYesterdayHaveAdd.setText("--");
//        //当前价值
        mViewBinding.fragmentMyThisMoney.setText("--");
//        mViewBinding.fragmentMyThisMoney.setText("¥--");
//        //股价
        mViewBinding.fragmentMyOldStoke.setText("--");

        //+多少个掘金宝
        mViewBinding.fragmentMyAddMoney.setVisibility(View.GONE);
        mViewBinding.fragmentMyInvitationPeople.setText(0 + "人");

    }


    private Boolean isRefresh = true;

    // 每次Tab切换都刷
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabChange(TabChangeEvent tabChangeEvent) {
        if (isRefresh && tabChangeEvent.getTabPosition() == 4 || tabChangeEvent.getTabPosition() == 3) {

            LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
            if (loginEntity == null) {
                return;
            }
            //请求data数据
            mPresenter.getUserData(new HashMap<String, String>(), getActivity());
            //邀请码
            mViewBinding.fragmentMyCode.setText(loginEntity.getData().getInvitation());
            //请求第一个邀请广告位的数据
            mPresenter.getAdFirst(new HashMap<String, String>(), getActivity());
            //请求第二个邀请广告位的数据
            mPresenter.getAdTwo(new HashMap<String, String>(), getActivity());

            isRefresh = false;
        }

        if ((((JunjinBaoMainActivity) mActivity).Is_Show_Movie && tabChangeEvent.getTabPosition() == 4) ||
                (!((JunjinBaoMainActivity) mActivity).Is_Show_Movie && tabChangeEvent.getTabPosition() == 3)) {

            newUserStartTime = System.currentTimeMillis() / 1000;
            oldUserStartTime = System.currentTimeMillis() / 1000;
            touristsStartTime = System.currentTimeMillis() / 1000;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        doRefreshBanner();
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            initViewGone();
//            mPresenter.getUserData(new HashMap<String, String>(), getActivity());
        } else {
            llLoginOut.setVisibility(View.GONE);
            llLoginOn.setVisibility(View.VISIBLE);
            setUserIdentity();
            mViewBinding.modelFragmentMyInvitationAbout.setVisibility(View.VISIBLE);
            mViewBinding.fragmnetMyAdvOne.setVisibility(View.VISIBLE);
//            Glide.with(mActivity).asGif().load(R.drawable.shop_unity_gif).apply(new RequestOptions()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL))
//                    .into(mViewBinding.frgametMyHelpGif);
            //请求data数据
            mPresenter.getUserData(new HashMap<String, String>(), getActivity());
            //邀请码
            mViewBinding.fragmentMyCode.setText(loginEntity.getData().getInvitation());
            //请求第一个邀请广告位的数据
            mPresenter.getAdFirst(new HashMap<String, String>(), getActivity());
            //请求第二个邀请广告位的数据
            mPresenter.getAdTwo(new HashMap<String, String>(), getActivity());

        }

        if (!isQmmSdkInit) {
            boolean isDebug;
            //通过 api地址 判断当前apk环境
            if (RetrofitManager.APP_URL_DOMAIN.equals("api.juejinchain.com")) {
                isDebug = false;
            } else {
                isDebug = true;
            }
            //初始化钱盟盟sdk
          //  JNLoanAgent.getInstance().init(mActivity, Constant.QMM_APPID, Constant.QMM_APPKEY, isDebug, callback);
        }

        newUserStartTime = System.currentTimeMillis() / 1000;
        oldUserStartTime = System.currentTimeMillis() / 1000;
        touristsStartTime = System.currentTimeMillis() / 1000;

      //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.MYCENTERPAGE_PV);
      //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.MYCENTERPAGE_UV);
    }

    @OnClick({R.id.model_fragment_my_to_login, R.id.fragment_my_read_time,
            R.id.model_fragment_my_setting, R.id.model_fragment_my_feedback/*,R.id.model_fragment_my_problem*/ /*,R.id.model_fragment_my_receive_gift*/,
            R.id.model_fragment_my_movie_remind, R.id.model_fragment_my_film_critics, R.id.model_fragment_my_focus,
            R.id.model_fragment_my_make_red, R.id.model_fragment_my_friend, R.id.model_fragment_my_make_picture, R.id.model_fragment_my_make_url,
            R.id.model_fragment_my_input_code, R.id.frgamet_my_help, R.id.frgamet_my_gift, R.id.model_fragment_my_invitation_look, R.id.fragment_my_header, R.id.fragment_my_detail,
            R.id.fragment_my_notify_title, R.id.fragment_my_when_ticket, R.id.fragment_my_msg_about, /*R.id.fragment_my_wallet,*/ R.id.fragment_my_wechat,
            R.id.fragment_my_qq, R.id.fragment_my_friends, R.id.model_fragment_my_ranking, R.id.fragment_my_copy,
            R.id.frgamet_my_help_gif, R.id.model_fragment_my_have_money_title,/*R.id.model_fragment_my_have_money_mark_new, R.id.model_fragment_my_have_money_mark,*/
            R.id.fragment_my_wallet_icon_about, R.id.iv_ad_qmm, R.id.iv_deal_enter, R.id.model_fragment_my_office})
    public void onClick(View view) {

        if (!ClickUtil.isNotFastClick()) {
            return;
        }

        Intent intent;
        LoginEntity loginEntity;
        switch (view.getId()) {
            //有效阅读时间
            case R.id.fragment_my_read_time:
                configDialog = new FragmentMyConfigDialog(mActivity, 9);
                configDialog.show();
                break;
            //商学院
            case R.id.frgamet_my_help_gif:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_COLLAGE);

                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_COLLAGE);
                break;
            //掘金宝官网
            case R.id.model_fragment_my_office:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                openBroser("http://www.juejinchain.com/");
//                WebActivity.intentMe(mActivity,"掘金宝官网" , "http://www.juejinchain.com/");
                ToastUtils.getInstance().show(context, "敬请期待");
                break;
            //复制
            case R.id.fragment_my_copy:
                //埋点（点击复制邀请码）
               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.MINEPAGE_INVITE_CODE_COPY);

                //获取剪贴版
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                //创建ClipData对象
                //第一个参数只是一个标记，随便传入。
                //第二个参数是要复制到剪贴版的内容
                ClipData clip = ClipData.newPlainText("code", mViewBinding.fragmentMyCode.getText().toString().trim());
                //传入clipdata对象.
                clipboard.setPrimaryClip(clip);
                MyToast.showToast("复制成功");

                break;
            //跳登录
            case R.id.model_fragment_my_to_login:
                intent = new Intent(getActivity(), GuideLoginActivity.class);
                getActivity().startActivity(intent);

            //    MobclickAgent.onEvent(MyApplication.getContext(), EventID.MINEPAGE_REGISTER_TOURISTS_CLICK);
                break;
            //设置
            case R.id.model_fragment_my_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent);
                break;
            //用户反馈
            case R.id.model_fragment_my_feedback:
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_FEEDBACK);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_USER_FEED_BACK);
                break;
            //常见问题
//            case R.id.model_fragment_my_problem:
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_PROBLEN);
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TECH_SECRET_BOOK);
//                break;
            //活动奖励领取
//            case R.id.model_fragment_my_receive_gift:
//                loginEntity = Paper.book().read(PagerCons.USER_DATA);
//                if (loginEntity == null) {
//                    intent = new Intent(getActivity(), GuideLoginActivity.class);
//                    getActivity().startActivity(intent);
//                    return;
//                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_GIFT);
//                break;
            //影视提醒
            case R.id.model_fragment_my_movie_remind:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_MOVIE_REMID);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MOVIEALERT);

                break;
            //我的影评
//            case R.id.model_fragment_my_film_critics:
//                loginEntity = Paper.book().read(PagerCons.USER_DATA);
//                if (loginEntity == null) {
//                    intent = new Intent(getActivity(), GuideLoginActivity.class);
//                    getActivity().startActivity(intent);
//                    return;
//                }
////                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_FILM_CRITICS);
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_MOVIECOMMENT);
//                break;
            //收藏
            case R.id.model_fragment_my_film_critics:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_COLLECTION);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_COLLECTION);
                break;
            //关注
            case R.id.model_fragment_my_focus:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                startActivity(new Intent(getContext(), BaoquGameActivity.class));
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_FOCUS);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_CORCER);

                break;
            //历史阅读
            case R.id.model_fragment_my_make_red:
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_HISTORY);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_READ_HISTORY);
                break;
            //富豪排行榜
            case R.id.model_fragment_my_ranking:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_RANKING);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_RICH_LIST);
                break;
            //我的好友
            case R.id.model_fragment_my_friend:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_FRIEND);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_FRIEND);
                break;
            //生成邀请链接
            case R.id.model_fragment_my_make_url:
                //埋点（点击我的-生成邀请链接）
             //   MobclickAgent.onEvent(MyApplication.getContext(), EventID.MINEPAGE_CREATE_INPUT_INNVITE_URL);

                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_MAKE_URL);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_GET_SHORT_URL);
                break;
            //生成邀请海报
            case R.id.model_fragment_my_make_picture:
                //埋点（点击我的-生成邀请海报）
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.MINEPAGE_CREATE_INPUT_INNVITE_POSTER);

                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_CREATE_POSTER);
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_MAKE_PITURE);
                break;
            //邀请码
            case R.id.model_fragment_my_input_code:
                //埋点(点击我的-输入邀请码)
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.MINEPAGE_INPUT_INNVITE_CODE);
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_MAKE_CODE);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_SET_INVATE_CODE);
                break;
            //手把手教你赚取掘金宝
            case R.id.frgamet_my_help:
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_MAKE_HELP);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TECH_EARN_MONEY);

                break;
            //查看奖品
            case R.id.frgamet_my_gift:
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_MAKE_GIFT);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_REWARD_LIST);
                break;
            //立即查看 邀请好友
            case R.id.model_fragment_my_invitation_look:
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_INVITE_FRIEND);
                startActivity(new Intent(mActivity, InviteFriendActivity.class));
                break;
            //进入钱盟盟广告
            case R.id.iv_ad_qmm:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }

              //  JNLoanAgent.getInstance().launchLoan(context, loginEntity.getData().getMobile(), loginEntity.getData().getUser_token());
                break;
            //进入交易入口
            case R.id.iv_deal_enter:
//                loginEntity = Paper.book().read(PagerCons.USER_DATA);
//                if (loginEntity == null) {
//                    intent = new Intent(getActivity(), GuideLoginActivity.class);
//                    getActivity().startActivity(intent);
//                    return;
//                }
//
//                if(!TextUtils.isEmpty(tradeEnterUrl)) {
//                    BridgeWebViewActivity.intentMe(getActivity(), tradeEnterUrl);
//                }else {
//                    ToastUtils.getInstance().show(mActivity,"入口消失在二次元了...");
//                }
                break;
            //我的 用户信息
            case R.id.fragment_my_detail:
            case R.id.fragment_my_header:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_USER_DAATA);
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_USER_INFO);

                UserInfoActivity.intentMe(loginEntity.getData().getInvitation(), 0, mActivity);

                break;
            //title弹窗
            case R.id.fragment_my_notify_title:
                configDialog = new FragmentMyConfigDialog(context, 1);
                configDialog.show();
                break;
            //何时兑换
            case R.id.fragment_my_when_ticket:
                configDialog = new FragmentMyConfigDialog(context, 2);
                configDialog.show();
                break;
            //未读消息
            case R.id.fragment_my_msg_about:
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_MESSGE);
                if (userDataEntity == null) {
                    GuideLoginActivity.start(mActivity, false, "");
                    return;
                }
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_MESSAGE + ("1".equals(userDataEntity.getData().getMsg_type()) ? "?msg_type=1" : ""));
                mViewBinding.fragmentMyMsgRemind.setVisibility(View.GONE);
                break;
            //我的钱包my/profit
//            case R.id.fragment_my_wallet:
            case R.id.fragment_my_wallet_icon_about:

                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_WALLET);
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_WALLET);
                break;

//            case R.id.model_fragment_my_have_money_mark_new:
//            case R.id.model_fragment_my_have_money_mark:
            case R.id.model_fragment_my_have_money_title:
                configDialog = new FragmentMyConfigDialog(mActivity, 8);
                configDialog.show();
                break;
            case R.id.fragment_my_friends:
                //朋友圈分享
                if (LoginEntity.getIsLogin()) {
                    ShareInfo shareInfo = new ShareInfo();
                    shareInfo.setId(LoginEntity.getUserToken());
                    shareInfo.setType("index");
                    shareInfo.setUrl_type(ShareInfo.TYPE_ME);
                    shareInfo.setUrl_path(ShareInfo.PATH_PERSONAL_CENTER);
                    shareInfo.setPlatform_type(ShareType.SHARE_TYPE_FRIEND_MOMENT);
                    mShareDialog = new ShareDialog(getActivity(), shareInfo, new ShareDialog.OnResultListener() {
                        @Override
                        public void result() {

                        }
                    });
                } else {
                    getActivity().startActivity(new Intent(getActivity(), GuideLoginActivity.class));
                    return;
                }
                break;
            case R.id.fragment_my_wechat:
                //微信分享
                if (LoginEntity.getIsLogin()) {
                    ShareInfo shareInfo = new ShareInfo();
                    shareInfo.setId(LoginEntity.getUserToken());
                    shareInfo.setType("index");
                    shareInfo.setUrl_type(ShareInfo.TYPE_ME);
                    shareInfo.setUrl_path(ShareInfo.PATH_PERSONAL_CENTER);
                    shareInfo.setPlatform_type(ShareType.SHARE_TYPE_WECHAT);
                    mShareDialog = new ShareDialog(getActivity(), shareInfo, new ShareDialog.OnResultListener() {
                        @Override
                        public void result() {

                        }
                    });
                } else {
                    getActivity().startActivity(new Intent(getActivity(), GuideLoginActivity.class));
                    return;
                }
                break;
            case R.id.fragment_my_qq:
                //qq分享
                if (LoginEntity.getIsLogin()) {
                    ShareInfo shareInfo = new ShareInfo();
                    shareInfo.setId(LoginEntity.getUserToken());
                    shareInfo.setType("index");
                    shareInfo.setUrl_type(ShareInfo.TYPE_ME);
                    shareInfo.setUrl_path(ShareInfo.PATH_PERSONAL_CENTER);
                    shareInfo.setPlatform_type(ShareType.SHARE_TYPE_QQ);
                    mShareDialog = new ShareDialog(getActivity(), shareInfo, new ShareDialog.OnResultListener() {
                        @Override
                        public void result() {

                        }
                    });
                } else {
                    getActivity().startActivity(new Intent(getActivity(), GuideLoginActivity.class));
                    return;
                }
                break;


        }

    }

    private boolean isQmmSdkInit = false;
//    private JNBaseRequestCallback callback = new JNBaseRequestCallback() {
//        @Override
//        public void process(int id, JNResponse response) {
//            if (id == JNMessageId.JN_INIT.getId()) {   //初始化业务ID
//                if (response.getCode() == 0) {
//                    //设置登录回调
//                    JNLoanAgent.getInstance().setLoginCallback(callback);
//                } else {
//                    Log.e("qmmSdk", "-->code = " + response.getCode() + "，msg = " + response.getMsg());
//                }
//            } else if (id == JNMessageId.JN_LOGIN.getId()) {   //登录回调业务
//                if (response.getCode() == 0) {
//                    ResultItem it = response.getData();
//                    //openId 商戶可以保存到自己服務器，在跟sdk服务器数据回传的时候，需要传openId
//                    String openId = it.getString("openId");
//                    //todo 把openId保存到自己服务器
//                    Log.d("qmmSdk", "-->openId = " + openId);
//                    setQmmOpenId(openId);
//                }
//            }
//        }
//    };

    private void setQmmOpenId(String openId) {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (TextUtils.isEmpty(openId) || loginEntity == null || loginEntity.getData() == null) {
            return;
        }
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("openid", openId);

        rx.Observable<ResponseEntity<Object>> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).
                setQmmOpenId(HttpRequestBody.generateRequestQuery(stringMap)).map((new HttpResultFunc<ResponseEntity<Object>>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ResponseEntity<Object>>() {
            @Override
            public void next(ResponseEntity<Object> entity) {

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("qmmsdk", "error:" + errResponse);
            }
        }, mActivity, false);
        RetrofitManager.getInstance(mActivity).toSubscribe(observable, (Subscriber) rxSubscription);
    }

    @Override
    public void initData() {


//        mViewBinding.modelFragmentMyScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                // 可以先判断ScrollView中的mView是不是在屏幕中可见
//                Rect rect = new Rect();
//                //广告1
//                //先判断View是否是visibility
//                if (mViewBinding.fragmnetMyAdvThree.isShown()) {
//                    //判断当前view是否在屏幕中可见
//                    boolean localVisibleRect = mViewBinding.fragmnetMyAdvThree.getGlobalVisibleRect(rect);
//                    if (isOneVisibility && localVisibleRect) {
//                        if (oneId == 0) {
//                            return;
//                        }
//                        initPostLook(0, oneId);
//                        isOneVisibility = false;
//                    }
//                }
//                //广告2
//                if (mViewBinding.fragmnetMyAdvSix.isShown()) {
//                    //判断当前view是否在屏幕中可见
//                    boolean localVisibleRect = mViewBinding.fragmnetMyAdvSix.getGlobalVisibleRect(rect);
//                    if (isTwoVisibility && localVisibleRect) {
//                        if (twoId == 0) {
//                            return;
//                        }
//                        initPostLook(0, twoId);
//                        isTwoVisibility = false;
//                    }
//                }
//                //广告3
//                if (mViewBinding.modelFragmentMyBanner.isShown()) {
//                    //判断当前view是否在屏幕中可见
//                    boolean localVisibleRect = mViewBinding.modelFragmentMyBanner.getGlobalVisibleRect(rect);
//                    if (isThreeVisibility && localVisibleRect) {
//                        if (threeId == 0) {
//                            return;
//                        }
//                        initPostLook(0, threeId);
//                        isThreeVisibility = false;
//                    }
//                }
//                //广告4
//                if (mViewBinding.fragmnetMyAdvFore.isShown()) {
//                    //判断当前view是否在屏幕中可见
//                    boolean localVisibleRect = mViewBinding.fragmnetMyAdvFore.getGlobalVisibleRect(rect);
//                    if (isForeVisibility && localVisibleRect) {
//                        if (foreId == 0) {
//                            return;
//                        }
//                        initPostLook(0, foreId);
//                        isForeVisibility = false;
//                    }
//                }
//                //广告5
//                if (mViewBinding.fragmnetMyAdvSeven.isShown()) {
//                    //判断当前view是否在屏幕中可见
//                    boolean localVisibleRect = mViewBinding.fragmnetMyAdvSeven.getGlobalVisibleRect(rect);
//                    if (isFiveVisibility && localVisibleRect) {
//                        if (fiveId == 0) {
//                            return;
//                        }
//                        initPostLook(0, fiveId);
//                        isFiveVisibility = false;
//                    }
//                }
//                //广告6
//                if (mViewBinding.fragmnetMyAdvFive.isShown()) {
//                    //判断当前view是否在屏幕中可见
//                    boolean localVisibleRect = mViewBinding.fragmnetMyAdvFive.getGlobalVisibleRect(rect);
//                    if (isSexVisibility && localVisibleRect) {
//                        if (sexId == 0) {
//                            return;
//                        }
//                        initPostLook(0, sexId);
//                        isSexVisibility = false;
//                    }
//                }
//            }
//
//
//        });

        final ShareInfo shareInfo = new ShareInfo();
        mViewBinding.modelFragmentMyTitleShare.setOnClickListener(new NoDoubleListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (LoginEntity.getIsLogin()) {
                   // MobclickAgent.onEvent(context, EventID.MINE_TOPRIGHT_SHARE);    //我的-分享-埋点
                    shareInfo.setUrl_type(ShareInfo.TYPE_ME);
                    shareInfo.setUrl_path(ShareInfo.PATH_PERSONAL_CENTER);
                    mShareDialog = new ShareDialog(getActivity(), shareInfo, null);
                    mShareDialog.show();
                } else {
                    Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
            }
        });
    }

    private void initPostLook(int type, int id) {
        // user/ad_count?ad_id=111&type=0
        //0浏览 1点击
        Map<String, String> map = new HashMap<>();
        map.put("ad_id", String.valueOf(id));
        map.put("type", String.valueOf(type));
        mPresenter.getLookOrOnTach(map, getActivity());
    }

    @Override
    public void showLoginOutSuccess(Serializable serializable) {
        Log.e("TAG", "showLoginOutSuccess: =====>>>>>>>" + "走了多少次");
        mViewBinding.modelFragmentMyRefresh.finishRefresh();
        isRefresh = true;
        userDataEntity = (UserDataEntity) serializable;

        if ("0".equals(userDataEntity.getData().getShow_qmm())) {
            mViewBinding.ivAdQmm.setVisibility(View.GONE);
        } else {
            mViewBinding.ivAdQmm.setVisibility(View.VISIBLE);
        }

        if (userDataEntity.getData().getOpen_trade_pos() == null || userDataEntity.getData().getOpen_trade_pos().size() == 0) {
            mViewBinding.dealEnterBanner.setVisibility(View.GONE);
        } else {
            List<String> imgs = new ArrayList<>();
            for (int i = 0; i < userDataEntity.getData().getOpen_trade_pos().size(); i++) {
                imgs.add(userDataEntity.getData().getOpen_trade_pos().get(i).getImg());
            }
            mViewBinding.dealEnterBanner.setVisibility(View.VISIBLE);
            mViewBinding.dealEnterBanner.setImageLoader(new GlideImgsLoader());
            mViewBinding.dealEnterBanner.setImages(imgs);
            mViewBinding.dealEnterBanner.start();
            mViewBinding.dealEnterBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                    if (loginEntity == null) {
                        Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                        startActivity(intent);
                        return;
                    }

                    if (!TextUtils.isEmpty(userDataEntity.getData().getOpen_trade_pos().get(position).getUrl())) {
                        BridgeWebViewActivity.intentMe(getActivity(), userDataEntity.getData().getOpen_trade_pos().get(position).getUrl());
                    } else {
                        ToastUtils.getInstance().show(mActivity, "入口消失在二次元了...");
                    }
                }
            });
        }
//        tradeEnterUrl = userDataEntity.getData().getIs_open_trade();
//        Glide.with(mActivity).load(userDataEntity.getData().getOpen_trade_pos().get(0).getImg()).into(mViewBinding.ivDealEnter);

        if (userDataEntity.getCode() == 705) {
            Paper.book().delete(PagerCons.USER_DATA);
            EventBus.getDefault().post(new SettingLoginEvent());
            initViewGone();
            MyToast.showToast(userDataEntity.getMsg());
            Constant.IS_SHOW = 1;
            Intent intent = new Intent(mActivity, GuideLoginActivity.class);
            mActivity.startActivity(intent);

        } else if (userDataEntity.getCode() == 0) {
            Glide.with(getActivity()).
                    load(userDataEntity.getData().getAvatar()).
                    apply(RequestOptions.bitmapTransform(new CircleCrop()).
                            placeholder(R.mipmap.default_img)).
                    into(mViewBinding.fragmentMyHeader);
            //头像加J
            if (!TextUtils.isEmpty(userDataEntity.getData().getJ_url())) {
                mViewBinding.ivJ.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(userDataEntity.getData().getJ_url()).into(mViewBinding.ivJ);
            } else {
                mViewBinding.ivJ.setVisibility(View.GONE);
            }

            if (userDataEntity.getData().getHas_inviter() == 1) {
                isShow = true;
            }
            String nickName;
            if (userDataEntity.getData().getNickname().trim().length() > 5) {
                nickName = userDataEntity.getData().getNickname().trim().substring(0, 5) + "...";
            } else {
                nickName = userDataEntity.getData().getNickname().trim();
            }

            mViewBinding.llIdentity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_USER_IDENTITY);
                }
            });
            mViewBinding.imgIdentityQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_USER_IDENTITY);
                }
            });

            setUserIdentity();

            mViewBinding.fragmentMyUserName.setText(nickName);
            mViewBinding.fragmentMyReadTime.setText("有效阅读" + BigDecimal.valueOf((double) userDataEntity.getData().getToday_read_time() / (double) 60).setScale(0, BigDecimal.ROUND_HALF_UP).toString() + "分钟");
            //我的等级
            mViewBinding.fragmentMyUserGrade.setText(userDataEntity.getData().getGrade() + "");
            //金币余额
            mViewBinding.modelFragmentMyHaveMoney.setText(userDataEntity.getData().getCoin() + "");
            //         mViewBinding.fragmentMyBalanceOfGoldConins.setText(userDataEntity.getData().getCoin() + "");
            //掘金宝个数 BigDecimal比大小 -1表示小于,0是等于,1是大于。
            Double vcoin = Double.valueOf(userDataEntity.getData().getVcoin());
            if (vcoin < 10000) {
                mViewBinding.fragmentMyMoneyVcoin.setText(new BigDecimal(vcoin).setScale(2, BigDecimal.ROUND_DOWN).toString());

            } else {
                mViewBinding.fragmentMyMoneyVcoin.setText(new BigDecimal(vcoin / 10000).setScale(2, BigDecimal.ROUND_DOWN) + "w");
//                double v = vcoin / 10000;

//                DecimalFormat df=new DecimalFormat(".##");
//                v=Double.valueOf(df.format(v));
//                mViewBinding.fragmentMyMoneyVcoin.setText(df.format(v) + "w");
            }
//            BigDecimal vcoin = new BigDecimal(userDataEntity.getData().getVcoin())
//            if (vcoin.compareTo(bigCompare) == -1) {
//                mViewBinding.framentMyBalanceCount.setText("0" + formatTosepara(vcoin));
//            } else {
//                mViewBinding.framentMyBalanceCount.setText(formatTosepara(vcoin));
//            }
            //昨日收入 fragment_my_money_vcoin_yesterday
//            BigDecimal yesterdayMoney = new BigDecimal(userDataEntity.getData().getYesterday_vcoin());
//            if (yesterdayMoney.compareTo(bigCompare) == -1) {
//                mViewBinding.fragmentMyYesterdayHave.setText("0" + formatTosepara(yesterdayMoney));
//            } else {
//                mViewBinding.fragmentMyYesterdayHave.setText(formatTosepara(yesterdayMoney));
//            }
            //昨日掘金宝增加个数 fragment_my_money_vcoin_yesterday
            Double yesterdayAdd = Double.valueOf(userDataEntity.getData().getYesterday_wealth_rmb());
            if (yesterdayAdd < 10000) {
                mViewBinding.fragmentMyMoneyVcoinYesterday.setText(new BigDecimal(yesterdayAdd).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
            } else {
                mViewBinding.fragmentMyMoneyVcoinYesterday.setText(new BigDecimal(yesterdayAdd / 10000).setScale(2, BigDecimal.ROUND_HALF_EVEN) + "w");
            }
//            BigDecimal yesterdayAdd = new BigDecimal(userDataEntity.getData().getYesterday_wealth_rmb());
//            if (yesterdayAdd.compareTo(bigCompare) == -1) {
//
//                mViewBinding.fragmentMyYesterdayHaveAdd.setText("+0" + formatTosepara(yesterdayAdd));
//            } else {
//                mViewBinding.fragmentMyYesterdayHaveAdd.setText("+" + formatTosepara(yesterdayAdd));
//            }
            //股价
            BigDecimal stock = new BigDecimal(userDataEntity.getData().getToday_stock());
            BigDecimal stockAdd = new BigDecimal(userDataEntity.getData().getYesterday_stock());
            if (stock.compareTo(bigCompare) == -1) {
                // mViewBinding.fragmentMyStock.setText("0" + formatTosepara(stock));
                mViewBinding.fragmentMyOldStoke.setText("0" + formatTosepara(stock));
            } else {
                //mViewBinding.fragmentMyStock.setText(formatTosepara(stock));
                mViewBinding.fragmentMyOldStoke.setText(formatTosepara(stock));

                //根据涨跌改变颜色
                try {
                    if (Float.parseFloat(userDataEntity.getData().getToday_stock()) -
                            Float.parseFloat(userDataEntity.getData().getYesterday_stock())
                            >= 0) {
                        mViewBinding.fragmentMyOldStoke.setTextColor(Color.parseColor("#f13f2d"));
                    } else {
                        mViewBinding.fragmentMyOldStoke.setTextColor(Color.parseColor("#0e9807"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //股价涨幅
            //先与0判断
            if (new BigDecimal(0).compareTo(stock.subtract(stockAdd)) == -1) {
//                if (stock.subtract(stockAdd).compareTo(bigCompare) == -1) {
//                    mViewBinding.fragmentMyStockAdd.setText("0" + formatTosepara(stock.subtract(stockAdd)));
//                } else {
//
//                    mViewBinding.fragmentMyStockAdd.setText(formatTosepara(stock.subtract(stockAdd)));
//                }
//                mViewBinding.fragmentMyStockAdd.setTextColor(Color.parseColor("#ff3300"));
//                mViewBinding.fragmentMyStock.setTextColor(Color.parseColor("#ff3300"));
//                mViewBinding.fragmentMyStockAddImg.setImageResource(R.mipmap.my_fragment_triangle);
//                mViewBinding.fragmentMyOldStoke.setTextColor(Color.parseColor("#ff3300"));
            } else {
//                if (new BigDecimal(-1.00).compareTo(stock.subtract(stockAdd)) == -1) {
//                    mViewBinding.fragmentMyStockAdd.setText("-0" + formatTosepara(stock.subtract(stockAdd)).replace("-", ""));
//                } else {
//                    mViewBinding.fragmentMyStockAdd.setText(formatTosepara(stock.subtract(stockAdd)));
//                }
//                mViewBinding.fragmentMyStockAdd.setTextColor(Color.parseColor("#0f990f"));
//                mViewBinding.fragmentMyStock.setTextColor(Color.parseColor("#0f990f"));
//                mViewBinding.fragmentMyStockAddImg.setImageResource(R.mipmap.my_fragment_triangle_green);
//                mViewBinding.fragmentMyOldStoke.setTextColor(Color.parseColor("#0f990f"));
            }

            //当前价值 fragment_my_this_money
            Double wealth_rmb = Double.valueOf(userDataEntity.getData().getWealth_rmb());
            if (vcoin < 10000) {
                mViewBinding.fragmentMyThisMoney.setText(new BigDecimal(wealth_rmb).setScale(2, BigDecimal.ROUND_DOWN).toString());

            } else {
                mViewBinding.fragmentMyThisMoney.setText(new BigDecimal(wealth_rmb / 10000).setScale(2, BigDecimal.ROUND_DOWN) + "w");
            }
//            BigDecimal wealth_rmb = new BigDecimal(userDataEntity.getData().getWealth_rmb());
//            if (wealth_rmb.compareTo(bigCompare) == -1) {
//                mViewBinding.fragmentMyTheCurrentValue.setText("0" + formatTosepara(wealth_rmb));
//            } else {
//                mViewBinding.fragmentMyTheCurrentValue.setText(formatTosepara(wealth_rmb));
//            }

            //可兑换
            //当为空时，代表新用户，需要谈大礼包
            if (userDataEntity.getData().getEquivalence_current().getTag() == null) {

                Glide.with(getActivity()).load(R.mipmap.ic_gift_car).into(mViewBinding.modelFragmentMyExchangeImg);
                mViewBinding.modelFragmentMyExchangeName.setText("立即兑换");

            } else {
                Glide.with(getActivity()).load(userDataEntity.getData().getEquivalence_current().getThumb()).into(mViewBinding.modelFragmentMyExchangeImg);

                mViewBinding.modelFragmentMyExchangeName.setText(userDataEntity.getData().getEquivalence_current().getShortname() + " >");
            }
            //即将兑换
            Glide.with(getActivity()).load(userDataEntity.getData().getEquivalence_next().getThumb()).into(mViewBinding.modelFragmentMyImmediatelyExchangeImg);
            mViewBinding.modelFragmentMyImmediatelyExchangeName.setText(userDataEntity.getData().getEquivalence_next().getShortname() + " >");
            //进度条
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mViewBinding.fragmetMyProgressImg.getLayoutParams();
            if (userDataEntity.getData().getProgress() < 0.03 &&
                    userDataEntity.getData().getProgress() >= 0.01) {
                layoutParams.width = 30;
            } else if (userDataEntity.getData().getProgress() < 0.01) {
                layoutParams.width = 0;
            } else {
                layoutParams.width = (int) (mViewBinding.fragmetMyProgressAbout.getWidth() * userDataEntity.getData().getProgress());
            }
            layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            mViewBinding.fragmetMyProgressImg.setLayoutParams(layoutParams);
            mViewBinding.fragmetMyProgressNum.setText(BigDecimal.valueOf(userDataEntity.getData().getProgress() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
            mViewBinding.fragmentMyInvitationPeople.setText("" + userDataEntity.getData().getInvite_num() + "人");


//            //可兑换
//            case R.id.model_fragment_my_exchange_about:
//                if (exchange.length()>0){
//
//                }
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_MESSGE);
//                break;
//            //即将兑换
//            case R.id.model_fragment_my_immediately_exchange_about:
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + Constant.MY_MESSGE);
//                break;
            //可兑换
            mViewBinding.modelFragmentMyExchangeAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //说明新用户直接弹出首页的大礼包
                    if (userDataEntity == null || userDataEntity.getData() == null || userDataEntity.getData().getEquivalence_current() == null || StringUtil.isEmpty(userDataEntity.getData().getEquivalence_current().getTag())) {
                        HomeTipsAlertDialog mHomeGiftDialog = new HomeTipsAlertDialog(mActivity, mViewBinding.modelFragmentMyScroll);
                        mHomeGiftDialog.show();
                    } else {
                        if (userDataEntity.getData().getEquivalence_current().getTag().equals("goods")) {
//                            BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + "/goods_detail/" + userDataEntity.getData().getEquivalence_current().getId());
                            BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_GOODS_DETAIL + userDataEntity.getData().getEquivalence_current().getId());

                        } else if (userDataEntity.getData().getEquivalence_current().getTag().equals("house")) {
//                            BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + "/house_detail/" + userDataEntity.getData().getEquivalence_current().getId());
                            BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_HOUSE_DETAIL + userDataEntity.getData().getEquivalence_current().getId());

                        } else if (userDataEntity.getData().getEquivalence_current().getTag().equals("car")) {
//                            BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + "/car_detail/" + userDataEntity.getData().getEquivalence_current().getId());
                            BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_CAR_DETAIL + userDataEntity.getData().getEquivalence_current().getId());

                        }
                    }
                }
            });

            //即将兑换
            mViewBinding.modelFragmentMyImmediatelyExchangeAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //说明新用户直接弹出首页的大礼包
                    if (userDataEntity == null || userDataEntity.getData() == null || userDataEntity.getData().getEquivalence_current() == null || StringUtil.isEmpty(userDataEntity.getData().getEquivalence_current().getTag())) {
                        HomeTipsAlertDialog mHomeGiftDialog = new HomeTipsAlertDialog(mActivity, mViewBinding.modelFragmentMyScroll);
                        mHomeGiftDialog.show();
                    } else {
                        if (userDataEntity.getData().getEquivalence_next().getTag().equals("goods")) {
                            BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_GOODS_DETAIL + userDataEntity.getData().getEquivalence_next().getId());

                        } else if (userDataEntity.getData().getEquivalence_next().getTag().equals("house")) {
                            BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_HOUSE_DETAIL + userDataEntity.getData().getEquivalence_next().getId());

                        } else if (userDataEntity.getData().getEquivalence_next().getTag().equals("car")) {
                            BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_CAR_DETAIL + userDataEntity.getData().getEquivalence_next().getId());

                        }
                    }
                }
            });
           /*
           广告位判断 ad_position
           11 12 13 14 15 16
           对应
           FI-1 -2 -3 -4 -5 -6
           对应位置
           ViewFlipper广告位2下面
           生成邀请海报下面
           Banner位置
           我的收藏下面
           活动奖励领取
           用户反馈下面
           */
            //每次接口请求都重新把广告影藏后通过数据判断是否显示
            mViewBinding.fragmnetMyAdvThree.setVisibility(View.GONE);
            mViewBinding.fragmnetMyAdvSix.setVisibility(View.GONE);
            mViewBinding.modelFragmentMyBanner.setVisibility(View.GONE);
            mViewBinding.fragmnetMyAdvFore.setVisibility(View.GONE);
            mViewBinding.fragmnetMyAdvSeven.setVisibility(View.GONE);
            mViewBinding.fragmnetMyAdvFive.setVisibility(View.GONE);
            for (int i = 0; i < userDataEntity.getData().getAd_info().size(); i++) {

                initViewPosition(Integer.parseInt(userDataEntity.getData().getAd_info().get(i).getAd_position()), i, userDataEntity);
            }
            if (Constant.FRAGMENT_TAB == 4) {
                //等级提升和商品达到同时达到条件，只弹商品
                if (userDataEntity.getData().getGoods_upgrade() == 1) {
                    if (userDataEntity.getData().getUpgrade() == 1) {
                        configDialog = new FragmentMyConfigDialog(context, 3, userDataEntity.getData().getGrade() + "");
                        configDialog.show();
                        initTlakService("user_upgrade");
                    } else {
                        //  String url, 图片地址
                        //  String title, title
                        //  String money, 钱
                        //  String moneyDiscount,折扣钱
                        //  String peopleHave 有多少人
                        if (userDataEntity.getData().getEquivalence_current().getTag().equals("goods")) {
                            configDialog = new FragmentMyConfigDialog(context, 4, userDataEntity.getData().getEquivalence_current().getThumb(),
                                    userDataEntity.getData().getEquivalence_current().getShortname(), "0", userDataEntity.getData().getEquivalence_current().getPrice(), "0");

                        } else if (userDataEntity.getData().getEquivalence_current().getTag().equals("house")) {
                            configDialog = new FragmentMyConfigDialog(context, 4, userDataEntity.getData().getEquivalence_current().getThumb(),
                                    userDataEntity.getData().getEquivalence_current().getShortname(), "0", userDataEntity.getData().getEquivalence_current().getPrice(), "0");

                        } else if (userDataEntity.getData().getEquivalence_current().getTag().equals("car")) {
                            configDialog = new FragmentMyConfigDialog(context, 4, userDataEntity.getData().getEquivalence_current().getThumb(),
                                    userDataEntity.getData().getEquivalence_current().getShortname(), "0", userDataEntity.getData().getEquivalence_current().getPrice(), "0");
                        }

                        configDialog.show();
                        initTlakService("goods_upgrade");
                    }
                } else {
                    if (userDataEntity.getData().getUpgrade() == 1) {
                        configDialog = new FragmentMyConfigDialog(context, 3, userDataEntity.getData().getGrade() + "");
                        configDialog.show();
                        initTlakService("user_upgrade");
                    }
                }
            }


            // 是否开启活动入口
            if (!TextUtils.isEmpty(userDataEntity.getData().getIs_open_activity())) {
                actvityEnterUrl = userDataEntity.getData().getIs_open_activity();
                Glide.with(mActivity).load(R.drawable.gif_activity).into(mViewBinding.imgActEnter);
                mViewBinding.imgActEnter.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.imgActEnter.setVisibility(View.GONE);
            }


        } else if (userDataEntity.getCode() == 706) {
            // 是否开启活动入口
            if (!TextUtils.isEmpty(userDataEntity.getData().getIs_open_activity())) {
                actvityEnterUrl = userDataEntity.getData().getIs_open_activity();
                Glide.with(mActivity).load(R.drawable.gif_activity).into(mViewBinding.imgActEnter);
                mViewBinding.imgActEnter.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.imgActEnter.setVisibility(View.GONE);
            }
        }
        //请求未读消息
        mPresenter.getMessge(new HashMap<String, String>(), getActivity());
    }

    //告诉后台，弹窗已完成
    private void initTlakService(String type) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        mPresenter.getGiftTalk(map, getActivity());

    }

    private void initViewPosition(int position, final int index, final UserDataEntity userDataEntity) {
        switch (position == 11 ? 1 : position == 12 ? 2 : position == 13 ? 3 : position == 14 ? 4 : position == 15 ? 5 : 6) {
            //ViewFlipper广告位2下面
            case 1:
                mViewBinding.fragmnetMyAdvThree.setVisibility(View.VISIBLE);
                //fragmnet_my_adv_three_name
                Glide.with(getActivity()).load(userDataEntity.getData().getAd_info().get(index).getImages().get(0)).into(mViewBinding.fragmnetMyAdvThreeImg);
                mViewBinding.fragmnetMyAdvThreeName.setText(userDataEntity.getData().getAd_info().get(index).getTitle());
                oneId = userDataEntity.getData().getAd_info().get(index).getId();
                //如果为空，影藏广告标示
                if (TextUtils.isEmpty(userDataEntity.getData().getAd_info().get(index).getAd_sign())) {
                    mViewBinding.fragmnetMyAdvThreeLogo.setVisibility(View.GONE);
                } else {
                    mViewBinding.fragmnetMyAdvThreeLogo.setVisibility(View.VISIBLE);
                    mViewBinding.fragmnetMyAdvThreeLogo.setText(userDataEntity.getData().getAd_info().get(index).getAd_sign());
                }
                mViewBinding.fragmnetMyAdvThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebActivity.intentMe(getActivity(), userDataEntity.getData().getAd_info().get(index).getTitle(),
                                userDataEntity.getData().getAd_info().get(index).getLink());
                        if (isOneOnTach) {
                            initPostLook(1, oneId);
                            isOneOnTach = false;
                        }
                    }
                });

                break;
            //生成邀请海报下面
            case 2:
                mViewBinding.fragmnetMyAdvSix.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(userDataEntity.getData().getAd_info().get(index).getImages().get(0)).into(mViewBinding.fragmnetMyAdvSixImg);
                mViewBinding.fragmnetMyAdvSixName.setText(userDataEntity.getData().getAd_info().get(index).getTitle());
                twoId = userDataEntity.getData().getAd_info().get(index).getId();
                //如果为空，影藏广告标示
                if (TextUtils.isEmpty(userDataEntity.getData().getAd_info().get(index).getAd_sign())) {
                    mViewBinding.fragmnetMyAdvSixNameLogo.setVisibility(View.GONE);
                } else {
                    mViewBinding.fragmnetMyAdvSixNameLogo.setVisibility(View.VISIBLE);
                    mViewBinding.fragmnetMyAdvSixNameLogo.setText(userDataEntity.getData().getAd_info().get(index).getAd_sign());
                }
                mViewBinding.fragmnetMyAdvSixName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebActivity.intentMe(getActivity(), userDataEntity.getData().getAd_info().get(index).getTitle(),
                                userDataEntity.getData().getAd_info().get(index).getLink());

                        if (isTwoOnTach) {
                            initPostLook(1, twoId);
                            isTwoOnTach = false;
                        }

                    }
                });
                break;
            // Banner位置
            case 3:
                mViewBinding.modelFragmentMyBanner.setVisibility(View.VISIBLE);
                mViewBinding.modelFragmentMyBanner.setImageLoader(new GlideImageLoader());
                mViewBinding.modelFragmentMyBanner.setImages(userDataEntity.getData().getAd_info().get(index).getImages());
                mViewBinding.modelFragmentMyBanner.start();
                threeId = userDataEntity.getData().getAd_info().get(index).getId();
                mViewBinding.modelFragmentMyBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        WebActivity.intentMe(getActivity(), "广告", userDataEntity.getData().getAd_info().get(index).getLink());

                        if (isThreeOnTach) {
                            initPostLook(1, threeId);
                            isThreeOnTach = false;
                        }

                    }
                });
                break;
            // 我的收藏下面
            case 4:
                mViewBinding.fragmnetMyAdvFore.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(userDataEntity.getData().getAd_info().get(index).getImages().get(0)).into(mViewBinding.fragmnetMyAdvForeImg);
                mViewBinding.fragmnetMyAdvForeName.setText(userDataEntity.getData().getAd_info().get(index).getTitle());
                foreId = userDataEntity.getData().getAd_info().get(index).getId();
                //如果为空，影藏广告标示
                if (TextUtils.isEmpty(userDataEntity.getData().getAd_info().get(index).getAd_sign())) {
                    mViewBinding.fragmnetMyAdvForeLogo.setVisibility(View.GONE);
                } else {
                    mViewBinding.fragmnetMyAdvForeLogo.setVisibility(View.VISIBLE);
                    mViewBinding.fragmnetMyAdvForeLogo.setText(userDataEntity.getData().getAd_info().get(index).getAd_sign());
                }
                mViewBinding.fragmnetMyAdvFore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebActivity.intentMe(getActivity(), userDataEntity.getData().getAd_info().get(index).getTitle(),
                                userDataEntity.getData().getAd_info().get(index).getLink());

                        if (isForeOnTach) {
                            initPostLook(1, foreId);
                            isForeOnTach = false;
                        }

                    }
                });
                break;
            //活动奖励领取
            case 5:
                mViewBinding.fragmnetMyAdvSeven.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(userDataEntity.getData().getAd_info().get(index).getImages().get(0)).into(mViewBinding.fragmnetMyAdvSevenImg);
                mViewBinding.fragmnetMyAdvSevenName.setText(userDataEntity.getData().getAd_info().get(index).getTitle());
                fiveId = userDataEntity.getData().getAd_info().get(index).getId();
                //如果为空，影藏广告标示
                if (TextUtils.isEmpty(userDataEntity.getData().getAd_info().get(index).getAd_sign())) {
                    mViewBinding.fragmnetMyAdvSevenLogo.setVisibility(View.GONE);
                } else {
                    mViewBinding.fragmnetMyAdvSevenLogo.setVisibility(View.VISIBLE);
                    mViewBinding.fragmnetMyAdvSevenLogo.setText(userDataEntity.getData().getAd_info().get(index).getAd_sign());
                }
                mViewBinding.fragmnetMyAdvSeven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebActivity.intentMe(getActivity(), userDataEntity.getData().getAd_info().get(index).getTitle(),
                                userDataEntity.getData().getAd_info().get(index).getLink());

                        if (isFiveOnTach) {
                            initPostLook(1, fiveId);
                            isFiveOnTach = false;
                        }
                    }
                });
                break;
            //用户反馈下面
            case 6:
                mViewBinding.fragmnetMyAdvFive.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(userDataEntity.getData().getAd_info().get(index).getImages().get(0)).into(mViewBinding.fragmnetMyAdvFiveImg);
                mViewBinding.fragmnetMyAdvFiveName.setText(userDataEntity.getData().getAd_info().get(index).getTitle());
                sexId = userDataEntity.getData().getAd_info().get(index).getId();
                //如果为空，影藏广告标示
                if (TextUtils.isEmpty(userDataEntity.getData().getAd_info().get(index).getAd_sign())) {
                    mViewBinding.fragmnetMyAdvFiveLogo.setVisibility(View.GONE);
                } else {
                    mViewBinding.fragmnetMyAdvFiveLogo.setVisibility(View.VISIBLE);
                    mViewBinding.fragmnetMyAdvFiveLogo.setText(userDataEntity.getData().getAd_info().get(index).getAd_sign());
                }
                mViewBinding.fragmnetMyAdvFive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebActivity.intentMe(getActivity(), userDataEntity.getData().getAd_info().get(index).getTitle(),
                                userDataEntity.getData().getAd_info().get(index).getLink());

                        if (isSexOnTach) {
                            initPostLook(1, sexId);
                            isSexOnTach = false;
                        }
                    }
                });
                break;

        }


    }

    @Override
    public void showAdFirstSuccess(Serializable serializable) {
        mViewBinding.modelFragmentMyRefresh.finishRefresh();
        AdFirstEntity adFirstEntity = (AdFirstEntity) serializable;
        List<SpannableStringBuilder> messages = new ArrayList<>();
        messages.clear();
//        mViewBinding.fraagmentMyMarqueeView.removeAllViews();
        for (int i = 0; i < adFirstEntity.getData().size(); i++) {
            String title = adFirstEntity.getData().get(i).getNickname() + "邀请" + adFirstEntity.getData().get(i).getNum() + "位好友，他已经赚得" + adFirstEntity.getData().get(i).getShortname();
            SpannableStringBuilder style = new SpannableStringBuilder(title);
            style.setSpan(new ForegroundColorSpan(Color.parseColor("#F62121")), title.indexOf(adFirstEntity.getData().get(i).getNickname()), title.indexOf(adFirstEntity.getData().get(i).getNickname()) + adFirstEntity.getData().get(i).getNickname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), title.indexOf("邀请"), title.indexOf("邀请") + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.parseColor("#F0C070")), title.indexOf(String.valueOf(adFirstEntity.getData().get(i).getNum())), title.indexOf(String.valueOf(adFirstEntity.getData().get(i).getNum())) + String.valueOf(adFirstEntity.getData().get(i).getNum()).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), title.indexOf("位好友，他已经赚得"), title.indexOf("位好友，他已经赚得") + 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.parseColor("#F0C070")), title.indexOf(adFirstEntity.getData().get(i).getShortname()), title.indexOf(adFirstEntity.getData().get(i).getShortname()) + adFirstEntity.getData().get(i).getShortname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            messages.add(style);
        }
//        mViewBinding.fraagmentMyMarqueeView.startWithList(messages);
    }

    @Override
    public void showAdTwoSuccess(Serializable serializable) {
        mViewBinding.modelFragmentMyRefresh.finishRefresh();
        final AdTwoEntity adTwoEntity = (AdTwoEntity) serializable;
        String text = "";
        mViewBinding.fragmnetMyAdvTwo.setVisibility(View.VISIBLE);
        mViewBinding.fragmetMyFlipper.removeAllViews();
        for (int i = 0; i < adTwoEntity.getData().size(); i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.model_view_flipper, null);
            TextView tvContent = view.findViewById(R.id.model_view_filpper_context);
            ImageView ivBg = view.findViewById(R.id.model_view_filpper_bg);
            Glide.with(getActivity()).load(adTwoEntity.getData().get(i).getGoods().getThumb()).into(ivBg);
            String title = adTwoEntity.getData().get(i).getNickname() +
                    "达到" +
                    adTwoEntity.getData().get(i).getGoods().getLevel() +
                    "级，赚得" +
                    adTwoEntity.getData().get(i).getGoods().getShortname() +
                    adTwoEntity.getData().get(i).getGoods().getBrand_name() + "...";
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(title);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), title.indexOf(adTwoEntity.getData().get(i).getNickname()), title.indexOf(adTwoEntity.getData().get(i).getNickname()) + adTwoEntity.getData().get(i).getNickname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), title.indexOf("达到"), title.indexOf("达到") + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#F45546")), title.indexOf(String.valueOf(adTwoEntity.getData().get(i).getGoods().getLevel())), title.indexOf(String.valueOf(adTwoEntity.getData().get(i).getGoods().getLevel())) + String.valueOf(adTwoEntity.getData().get(i).getGoods().getLevel() + "级").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), title.indexOf("，赚得"), title.indexOf("，赚得") + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#F45546")), title.indexOf(adTwoEntity.getData().get(i).getGoods().getShortname()), title.indexOf(adTwoEntity.getData().get(i).getGoods().getShortname()) + adTwoEntity.getData().get(i).getGoods().getShortname().length() + adTwoEntity.getData().get(i).getGoods().getBrand_name().length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#F45546")), title.indexOf(adTwoEntity.getData().get(i).getGoods().getBrand_name()), title.indexOf(adTwoEntity.getData().get(i).getGoods().getBrand_name()) + adTwoEntity.getData().get(i).getGoods().getBrand_name().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#4a90e2")), title.indexOf("点击查看>"), title.indexOf("点击查看>") + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvContent.setText(stringBuilder);
            mViewBinding.fragmetMyFlipper.addView(view);
        }
        mViewBinding.fragmetMyFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mViewBinding.fragmetMyFlipper.getDisplayedChild();
//                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_COMMON + adTwoEntity.getData().get(position).getUrl().substring(1));

                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_GOODS_DETAIL + adTwoEntity.getData().get(position).getGoods().getId());
            }
        });
        // 跳转WEB_URL_COMMON
        // BridgeWebViewActivity.intentMe();
    }

    //获取统计
    @Override
    public void showLookOrOnTachSuccess(Serializable serializable) {
        mViewBinding.modelFragmentMyRefresh.finishRefresh();
    }

    //未读消息
    @Override
    public void showMessageSuccess(Serializable serializable) {
        mViewBinding.modelFragmentMyRefresh.finishRefresh();
        MyUnreadMessageEntity myUnreadMessageEntity = (MyUnreadMessageEntity) serializable;
        if (myUnreadMessageEntity.getData() != null) {
            if ((myUnreadMessageEntity.getData().getMsg() + myUnreadMessageEntity.getData().getMsg_notice()) > 0) {
                mViewBinding.fragmentMyMsgRemind.setVisibility(View.VISIBLE);
                mViewBinding.fragmentMyMsgRemind.setText((myUnreadMessageEntity.getData().getMsg() + myUnreadMessageEntity.getData().getMsg_notice()) > 99 ? "99+" : (myUnreadMessageEntity.getData().getMsg() + myUnreadMessageEntity.getData().getMsg_notice()) + "");
            } else {
                mViewBinding.fragmentMyMsgRemind.setVisibility(View.GONE);
            }
            if (isShow) {
                mViewBinding.fragmentMyAddMoney.setVisibility(View.GONE);
            } else {
                mViewBinding.fragmentMyAddMoney.setText("+" + myUnreadMessageEntity.getData().getReward_coefficient() + " 掘金宝");
                mViewBinding.fragmentMyAddMoney.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void getGiftTalkSuccess(Serializable serializable) {
        mViewBinding.modelFragmentMyRefresh.finishRefresh();
    }

    @Override
    public void showError(String s) {
        mViewBinding.modelFragmentMyRefresh.finishRefresh();
        isRefresh = true;
    }

    //保留两位有效数字且每三个加一个逗号
    public static String formatTosepara(BigDecimal data) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        return df.format(data);
    }

    @Override
    public void initImmersionBar() {
//        ImmersionBar.with(this).titleBar(mViewBinding.rlTop).init();
    }

    public void setUserIdentity() {
        if (userDataEntity == null || userDataEntity.getData() == null) {
            mViewBinding.gropIdentity.setVisibility(View.GONE);
            return;
        }
        if (!TextUtils.isEmpty(userDataEntity.getData().getIdentity_type())) {
            mViewBinding.gropIdentity.setVisibility(View.VISIBLE);
            mViewBinding.tvIdentity.setText(userDataEntity.getData().getIdentity_type());
        } else {
            mViewBinding.gropIdentity.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Map<String, Object> map = new HashMap<>();
        if (LoginEntity.getIsLogin()) {
            if (LoginEntity.getIsNew()) {
                map.put("onLine", System.currentTimeMillis() / 1000 - (newUserStartTime == 0 ? System.currentTimeMillis() / 1000 : touristsStartTime));
               // MobclickAgent.onEventObject(MyApplication.getContext(), EventID.MYCENTERPAGE_USETIME, map);
            } else {
                map.put("onLine", System.currentTimeMillis() / 1000 - (oldUserStartTime == 0 ? System.currentTimeMillis() / 1000 : touristsStartTime));
              //  MobclickAgent.onEventObject(MyApplication.getContext(), EventID.MINEPAGE_OLD_USETIME, map);
            }
        } else {
            map.put("onLine", System.currentTimeMillis() / 1000 - (touristsStartTime == 0 ? System.currentTimeMillis() / 1000 : touristsStartTime));
           // MobclickAgent.onEventObject(MyApplication.getContext(), EventID.MINEPAGE_TOURISTS_USETIME, map);
        }
    }

    private UnifiedBannerView getBanner() {
        String posId = getPosID();
        if (this.bv != null && this.posId.equals(posId)) {
            return this.bv;
        }
        if (this.bv != null) {
            mViewBinding.adContent.removeView(bv);
            bv.destroy();
        }
        this.posId = posId;
        this.bv = new UnifiedBannerView(mActivity, GDTHolder.APPID, posId, new UnifiedBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {

            }

            @Override
            public void onADReceive() {

            }

            @Override
            public void onADExposure() {

            }

            @Override
            public void onADClosed() {
                doCloseBanner();
            }

            @Override
            public void onADClicked() {

            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADOpenOverlay() {

            }

            @Override
            public void onADCloseOverlay() {

            }
        });
        mViewBinding.adContent.addView(bv, getUnifiedBannerLayoutParams());
        return this.bv;
    }

    /**
     * banner2.0规定banner宽高比应该为6.4:1 , 开发者可自行设置符合规定宽高比的具体宽度和高度值
     *
     * @return
     */
    private FrameLayout.LayoutParams getUnifiedBannerLayoutParams() {
        Point screenSize = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(screenSize);
        int wid = screenSize.x - 60;
        return new FrameLayout.LayoutParams(wid, Math.round(wid / 6.4F));
    }

    private void doRefreshBanner() {
        getBanner().loadAD();
    }

    private String getPosID() {
        return GDTHolder.POS_ID_BANNER;
    }

    private void doCloseBanner() {
        mViewBinding.adContent.removeAllViews();
        if (bv != null) {
            bv.destroy();
            bv = null;
        }
    }

}

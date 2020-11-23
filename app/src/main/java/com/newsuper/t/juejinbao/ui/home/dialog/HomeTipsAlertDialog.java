package com.newsuper.t.juejinbao.ui.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.HideShowGiftCarButtonEvent;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.LoginEvent;
import com.newsuper.t.juejinbao.ui.home.entity.BigGiftEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GiftCarEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.DensityUtils;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.view.CustomPopWindow;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import rx.Subscriber;

/**
 * 首页大礼包弹窗提示点，击外面不消失
 * 两种情况
 * 1、每次进入APP未登录显示，
 * 1.2、点击领取跳到登录页，不登录回来不消失
 * 2、登录后未领取的
 */
public class HomeTipsAlertDialog extends Dialog {

    private FrameLayout rootView = null;
    public boolean isGoLogin;
    private JSONObject mJo;
    private GiftCarEntity giftCarEntity;
    //    private Homefragment mHomeFragment;
    private CustomPopWindow successPopup;
    private Context context;

    public HomeTipsAlertDialog(Context context, FrameLayout root) {
        this(context, 0);
        this.context = context;
//        mHomeFragment = homeFragment;
        rootView = root;
        setCanceledOnTouchOutside(false);

    }

    public HomeTipsAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_home_alert);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - DensityUtils.dp2px(context, 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;

        getWindow().setAttributes(params);

        initView();
        loadData();
    }

    @Override
    public void onBackPressed() {

    }

    void setTouch(boolean isfirst) {
        if (!isfirst) {
            findViewById(R.id.img_get).setOnTouchListener(null);
            findViewById(R.id.cancel).setOnTouchListener(null);
            return;
        }
//        findViewById(R.id.img_get).setOnTouchListener(new View.OnTouchListener() { //不能用tou事件
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (UserEntity.isLogin()) {
//                    loadGiftApi();
//                } else {
//                    dismiss(); //去登录不消失
//                    isGoLogin = true;
//                    //去登录
//                    EventBus.getDefault().post(new LoginEvent());
//                }
//                return true;
//            }
//        });

        findViewById(R.id.cancel).setOnTouchListener(new View.OnTouchListener() { //不能用tou事件
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dismiss();
                return false;
            }
        });

    }

    @Override
    public void show() {
        super.show();
        setTouch(giftCarEntity == null);

        if (!isShowing() && giftCarEntity != null) {
            loadData();
        }
    }


    void loadData() {

        Map<String, String> map = new HashMap<>();

        rx.Observable<GiftCarEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getCarType(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<GiftCarEntity>()));
        RetrofitManager.getInstance(context).toSubscribe(observable, new Subscriber<GiftCarEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError:======>>>>> " + e.toString());

            }

            @Override
            public void onNext(GiftCarEntity giftCarEntity) {
                fillView(giftCarEntity);
            }
        });

    }

    void fillView(GiftCarEntity giftCarEntity) {
        if (giftCarEntity.getCode() == 0) {
            ((TextView) findViewById(R.id.tv_gftCar_shortName)).setText(giftCarEntity.getData().getShortname());
            ((TextView) findViewById(R.id.tv_gftCar_fullName)).setText(giftCarEntity.getData().getFullname());
            ImageView logo = findViewById(R.id.img_gftLogo);
            ImageView image = findViewById(R.id.img_gftLarge);
            Glide.with(context).load(giftCarEntity.getData().getBrand_logo()).into(logo);
            Glide.with(context).load(giftCarEntity.getData().getImages()).into(image);
        }

    }

    /**
     * 加载领取大礼包接口
     */
    void loadGiftApi() {

        Map<String, String> map = new HashMap<>();
        rx.Observable<BigGiftEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getBigGif(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<BigGiftEntity>()));
        RetrofitManager.getInstance(context).toSubscribe(observable, new Subscriber<BigGiftEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dismiss();
            }

            @Override
            public void onNext(BigGiftEntity giftCarEntity) {

                if (giftCarEntity.getCode() == 0) {
                    LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                    loginEntity.getData().setAchieved_gift_bag(1);
                    Paper.book().write(PagerCons.USER_DATA, loginEntity);
                    EventBus.getDefault().post(new HideShowGiftCarButtonEvent(false));
                    showGiftSuccessDialog(giftCarEntity);
                    Paper.book().write(PagerCons.ISSHOWBIGGIFT, false);
                    dismiss();
                } else {
                    ToastUtils.getInstance().show(getContext(), giftCarEntity.getMsg());
                }

            }
        });

//        NetUtil.getRequest(NetConfig.API_GiftBag, null, new NetUtil.OnResponse() {
//            @Override
//            public void onResponse(JSONObject response) {
//                dismiss();
//                if (NetUtil.isSuccess(response)) {
//                    response = response.getJSONObject("data");
//                    UserModel.setGetGiftBag(true);
//                    showGiftSuccessDialog(response);
//                    EventBus.getDefault().post(new HideShowGiftCarButtonEvent(false));
//                } else {
//                    Toast.makeText(getContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
//                    //已领取
//                    if (response.getInteger("code") == 1) {
//                        UserModel.setGetGiftBag(true);
//                        EventBus.getDefault().post(new TabSelectEvent(MainFragment.MINE));
//                    }
//                }
//
//            }
//        });
    }

    /**
     * 显示领取成功对话框
     *
     * @param
     */
    void showGiftSuccessDialog(BigGiftEntity giftCarEntity) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_get_gift_success, null);
        //处理popWindow 显示内容
        handleView(contentView);
        //创建并显示popWindow

        //显示大小
        successPopup = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(contentView)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAtLocation(rootView, Gravity.CENTER, 0, 0);
        TextView tvNum = contentView.findViewById(R.id.tv_juejinBao);
        tvNum.setText(Utils.FormatGold(giftCarEntity.getData().getVcoin()) + "个掘金宝");
        TextView tvMoneyNum = contentView.findViewById(R.id.tv_money);
        TextView tvPeopleNum = contentView.findViewById(R.id.tv_lingCount);
        tvMoneyNum.setText("（￥" + BigDecimal.valueOf(giftCarEntity.getData().getVcoin_to_rmb()).setScale(2, RoundingMode.HALF_EVEN).toString() + "元现金）");
        tvPeopleNum.setText(giftCarEntity.getData().getUser_count() + "");
        Button button = contentView.findViewById(R.id.btn_seeGift);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到钱包
//                BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_COMMON + Constant.MY_WALLET);
                BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_WALLET);
                successPopup.dissmiss();
                dismiss();
            }
        });
    }

    private void handleView(View contentView) {


    }

    private void initView() {
        ImageView gifImage = findViewById(R.id.img_get);
        Glide.with(getContext()).load(R.drawable.get_button).into(gifImage);

        //点击领取按钮
        gifImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null || !loginEntity.isLogin()) {
                    dismiss();
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    context.startActivity(intent);
                    return;
                }
                if (LoginEntity.getIsLogin()) {
                    loadGiftApi();
                } else {
                    dismiss(); //去登录不消失
                    isGoLogin = true;
                    //去登录
                    EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent , GuideLoginActivity.params));
//                    new GiftSuccessPopup(getContext()).showPopupWindow(); //测试领取成功
                }
            }
        });

//        gifImage.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                return true;
//            }
//        });


        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                EventBus.getDefault().post(new HideShowGiftCarButtonEvent(true));
            }
        });
    }



}

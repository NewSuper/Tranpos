package com.newsuper.t.juejinbao.ui.my.activity;

import android.view.View;
import android.widget.CompoundButton;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityModelSettingBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.LoginEvent;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.ui.home.entity.RaffleEntity;
import com.newsuper.t.juejinbao.ui.home.ppw.RafflePop;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.login.activity.WebSmsActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.my.dialog.ConfigDialog;
import com.newsuper.t.juejinbao.ui.my.presenter.SettingPresenter;
import com.newsuper.t.juejinbao.ui.my.presenter.impl.SettingPresenterImpl;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.utils.DataCleanManager;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;


public class SettingActivity extends BaseActivity<SettingPresenterImpl, ActivityModelSettingBinding> implements
        SettingPresenter.SettingView {

    private ConfigDialog configDialog;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_model_setting;
    }

    @Override
    public void initView() {
        if (Paper.book().read(PagerCons.PUSH_TYPE) == null) {
            Paper.book().write(PagerCons.PUSH_TYPE, "1");
        }
        if (Paper.book().read(PagerCons.PUSH_TYPE).equals("1")) {

            mViewBinding.pushSwitch.setChecked(true);
        } else {
            mViewBinding.pushSwitch.setChecked(false);
        }

        mViewBinding.pushSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //1打开，2关闭
                    Paper.book().write(PagerCons.PUSH_TYPE, "1");
                    EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent , GuideLoginActivity.params));

                } else {
                    Paper.book().write(PagerCons.PUSH_TYPE, "2");
//                    if (OSUtils.isMiuiRom()) {
//                        MiPushClient.unregisterPush(mActivity);
//                    } else if (OSUtils.isHuaweiRom()) {
//                        HMSAgent.destroy();
//                    }
//                        else if (OSUtils.isOPPORom()) {
//
//                        } else if (OSUtils.isVivoRom()) {
//
//                        } else if (OSUtils.isFlyme()) {
//                            com.meizu.cloud.pushsdk.PushManager.unRegister(mActivity, MyApplication.MEIZU_APP_ID, MyApplication.MEIZU_APP_KEY);
//                        }
            //        else {
//                        PushManager.getInstance().stopService(mActivity);
              //      }
                }
            }
        });

        /**
         * 非正式环境将：[版本号]_[环境]_[渠道号]展示出来 方便测试
         */
        if (RetrofitManager.APP_URL_DOMAIN.equals("api.juejinchain.com")) {
            mViewBinding.rlCurrentVersion.setVisibility(View.GONE);
        } else {
            mViewBinding.rlCurrentVersion.setVisibility(View.VISIBLE);
            String domain = "dev.api.juejinchain.cn".equals(RetrofitManager.APP_URL_DOMAIN) ? "测试环境" : "预发布";
            mViewBinding.tvCurrentVersion.setText("版本：" +  Utils.getVersion() + "-" + domain + "-" + "渠道号：" + JJBApplication.getChannel());


        }

//        File file =new File(this.getCacheDir().getPath());
//        try {
//            mViewBinding.tvCacheSize.setText(DataCleanManager.getCacheSize(file));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        mViewBinding.activitySettingYszc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSmsActivity.intentMe(mActivity, RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_SMS_AGREEMENT,"掘金宝隐私条款");

            }
        });

        mViewBinding.activitySettingYhxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSmsActivity.intentMe(mActivity, RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_USE_AGREEMENT,"掘金宝用户协议");

            }
        });

    }

    public void showTurnRewardDialog(RaffleEntity entity) {

        RafflePop acticleRewardPop = new RafflePop(mActivity);
        acticleRewardPop.setView(entity);
        acticleRewardPop.setOnClickListener(new RafflePop.OnClickListener() {
            @Override
            public void onclick(View view, int type) {
                if (type == 0) {
                    //看广告
                    PlayRewardVideoAdActicity.intentMe(mActivity, PlayRewardVideoAdActicity.BIGTURNTAB);

                } else if (type == 1) {
                    //不看广告直接领取
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("coin", 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    //领取成功
                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TURN_RECORD);
                }

                acticleRewardPop.dismiss();
            }
        });
        acticleRewardPop.showPopupWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTextSize();
    }

    private void initTextSize() {
        String textSize = book().read(PagerCons.KEY_TEXTSET_SIZE, "middle");
        switch (textSize) {
            case "small":
                mViewBinding.tvTextSize.setText("小字");
                break;
            case "middle":
                mViewBinding.tvTextSize.setText("标准");
                break;
            case "big":
                mViewBinding.tvTextSize.setText("大字");
                break;
            case "large":
                mViewBinding.tvTextSize.setText("超大");
                break;
        }
    }

    @Override
    public void initData() {
        mViewBinding.activityModelSettingBar.moduleIncludeTitleBarTitle.setText("设置");
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            mViewBinding.activityLoginOut.setVisibility(View.GONE);
        }

    }

    LoginEntity loginEntity;

    @OnClick({R.id.activity_login_out, R.id.activity_setting_clean_cash,
            R.id.activity_setting_about_us,R.id.activity_setting_text_set,R.id.module_include_title_bar_return})
    public void onClick(View view) {
        switch (view.getId()) {
            //退出登录
            case R.id.activity_login_out:

                configDialog = new ConfigDialog(mActivity, 1);
                configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
                    @Override
                    public void onOKClick(String code) {

                        //暂停音乐
                        if (!SongPlayManager.getInstance().isPlaying()) {
                        } else {
                            SongPlayManager.getInstance().pauseMusic();
                        }

                        loginEntity = Paper.book().read(PagerCons.USER_DATA);
                        if (loginEntity == null) {
                            MyToast.showToast("当前未登录");
                            return;
                        }
//                        if (OSUtils.isMiuiRom()) {
//                            MiPushClient.unregisterPush(mActivity);
//                            MiPushClient.unsetAlias(mActivity, loginEntity == null ? "" : loginEntity.getData() == null ? "" : String.valueOf(loginEntity.getData().getUid()), "");
//                        } else if (OSUtils.isHuaweiRom()) {
//                            HMSAgent.destroy();
//                        }
//                        else if (OSUtils.isOPPORom()) {
//
//                        } else if (OSUtils.isVivoRom()) {
//
//                        } else if (OSUtils.isFlyme()) {
//                            com.meizu.cloud.pushsdk.PushManager.unRegister(mActivity, MyApplication.MEIZU_APP_ID, MyApplication.MEIZU_APP_KEY);
//                        }
                        else {
//                            PushManager.getInstance().unBindAlias(mActivity, (loginEntity == null ? "" : loginEntity.getData() == null ? "" :
//                                    String.valueOf(loginEntity.getData().getUid())), true);
//                            PushManager.getInstance().stopService(mActivity);
                          //  JPushInterface.deleteAlias(mActivity , 0);
                        }

//                        UMShareAPI.get(mActivity).deleteOauth(mActivity, SHARE_MEDIA.QQ, new UMAuthListener() {
//                            @Override
//                            public void onStart(SHARE_MEDIA share_media) {
//
//                            }
//
//                            @Override
//                            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//
//                            }
//
//                            @Override
//                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//
//                            }
//
//                            @Override
//                            public void onCancel(SHARE_MEDIA share_media, int i) {
//
//                            }
//                        });
//                        UMShareAPI.get(mActivity).deleteOauth(mActivity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
//                            @Override
//                            public void onStart(SHARE_MEDIA share_media) {
//
//                            }
//
//                            @Override
//                            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//
//                            }
//
//                            @Override
//                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//
//                            }
//
//                            @Override
//                            public void onCancel(SHARE_MEDIA share_media, int i) {
//
//                            }
//                        });

                        Map<String, String> map = new HashMap<>();
                        mPresenter.LoginOut(map, mActivity);
                        MyToast.showToast("退出成功");
                        Paper.book().delete(PagerCons.USER_DATA);
                        Constant.IS_SHOW = 1;
                        EventBus.getDefault().post(new SettingLoginEvent());
                        configDialog.dismiss();

                        //推出钱盟盟sdk 2019/09/03


                        finish();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
                configDialog.show();

                break;
            //清除缓存
            case R.id.activity_setting_clean_cash:
                configDialog = new ConfigDialog(mActivity, 3);
                configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
                    @Override
                    public void onOKClick(String code) {
                        DataCleanManager.clearAllCache(mActivity);

//                        DataCleanManager.cleanInternalCache(getApplicationContext());
                        MyToast.showToast("清除成功");
//                        mViewBinding.tvCacheSize.setText("0.0KB");

                        configDialog.dismiss();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
                configDialog.show();

                break;
            //关于我们
            case R.id.activity_setting_about_us:
//                BridgeWebViewActivity.intentMe(this, RetrofitManager.WEB_URL_COMMON + Constant.ABOUT_US);
                BridgeWebViewActivity.intentMe(this, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_ABOUT_US);
                break;
            case R.id.activity_setting_text_set:
                TextSizeSettingActivity.start(mActivity);
                break;
            case R.id.module_include_title_bar_return:
                finish();
                break;
        }
    }

    @Override
    public void showLoginOutSuccess(Serializable serializable) {
//        LoginEntity loginEntity = (LoginEntity) serializable;
//        if (loginEntity.getCode() == 0) {
//            MyToast.showToast("退出成功");
//            Paper.book().delete(PagerCons.USER_DATA);
//            EventBus.getDefault().post(new SettingLoginEvent());
//            finish();
//        } else if (loginEntity.getCode() == 705) {
//            Paper.book().delete(PagerCons.USER_DATA);
//            EventBus.getDefault().post(new SettingLoginEvent());
//            MyToast.showToast(loginEntity.getMsg());
//            finish();
//        } else {
//            MyToast.showToast(loginEntity.getMsg());
//        }
    }

    @Override
    public void showError(String s) {

    }
}

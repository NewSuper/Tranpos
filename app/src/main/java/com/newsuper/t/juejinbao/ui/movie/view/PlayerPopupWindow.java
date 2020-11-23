package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ViewPlayerpopupBinding;
import com.juejinchain.android.module.movie.utils.JzvdStdEx;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.utils.MyToast;
import com.ys.network.base.PagerCons;

import java.util.concurrent.TimeUnit;

import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static io.paperdb.Paper.book;

/**
 * 播放器弹窗
 */
public class PlayerPopupWindow {
    private Activity activity;
    private PopupWindow popupWindow;
    private JzvdStdEx jzvdStd;

    private ViewPlayerpopupBinding mViewBinding;

    //定时关闭计时
    private Subscription subscription;

    private TPListener tpListener;

    //是否处于显示状态
    private boolean isShow = false;


    public PlayerPopupWindow(Activity activity, JzvdStdEx jzvdStd, TPListener tpListener) {
        this.activity = activity;
        this.jzvdStd = jzvdStd;
        this.tpListener = tpListener;
        init();
    }

    private void init() {
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.view_playerpopup, null, false);
        popupWindow = new PopupWindow(mViewBinding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setClippingEnabled(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isShow = false;
            }
        });

//        mViewBinding.rbBs2.setChecked(true);
        mViewBinding.rbCc2.setChecked(true);
        mViewBinding.rbGb1.setChecked(true);

        //光亮度初始化
        mViewBinding.sb.setMax(100);


        //计时
        subscription = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        long nowTime = System.currentTimeMillis();
                        String str = book().read(PagerCons.KEY_PLAYAPP_DSGB, "");

                        if (!TextUtils.isEmpty(str)) {
                            String[] strs = str.split("_");
                            if (strs.length == 2) {
                                long recordTime = Utils.strToLong(strs[0]);
                                int type = Utils.strToInt(strs[1]);
                                switch (type) {
                                    case 30:
                                        if (nowTime - recordTime > 1000L * 60 * 30) {
                                            MyToast.show(activity, "已到达关闭时间");
                                            if (Jzvd.CURRENT_JZVD != null) {
                                                Jzvd.goOnPlayOnPause();
                                            }
                                            //重置播放器定时关闭
                                            book().write(PagerCons.KEY_PLAYAPP_DSGB, "");
                                        }
                                        break;
                                    case 60:
                                        if (nowTime - recordTime > 1000L * 60 * 60) {
                                            MyToast.show(activity, "已到达关闭时间");
                                            if (Jzvd.CURRENT_JZVD != null) {
                                                Jzvd.goOnPlayOnPause();
                                            }
                                            //重置播放器定时关闭
                                            book().write(PagerCons.KEY_PLAYAPP_DSGB, "");
                                        }
                                        break;
                                    case 90:
                                        if (nowTime - recordTime > 1000L * 60 * 90) {
                                            MyToast.show(activity, "已到达关闭时间");
                                            if (Jzvd.CURRENT_JZVD != null) {
                                                Jzvd.goOnPlayOnPause();
                                            }
                                            //重置播放器定时关闭
                                            book().write(PagerCons.KEY_PLAYAPP_DSGB, "");
                                        }
                                        break;
                                }
                            }
                        }
                    }

                });


//        //倍速选择
//        mViewBinding.rgBs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    //0.75倍
//                    if (checkedId == R.id.rb_bs_1) {
//                        jzvdStd.mediaInterface.setSpeed(0.75f);
//                    }
//                    //正常
//                    if (checkedId == R.id.rb_bs_2) {
//                        jzvdStd.mediaInterface.setSpeed(1f);
//                    }
//                    //1.25倍
//                    if (checkedId == R.id.rb_bs_3) {
//                        jzvdStd.mediaInterface.setSpeed(1.25f);
//                    }
//                    //1.5倍
//                    if (checkedId == R.id.rb_bs_4) {
//                        jzvdStd.mediaInterface.setSpeed(1.5f);
//                    }
//                    //2倍
//                    if (checkedId == R.id.rb_bs_5) {
//                        jzvdStd.mediaInterface.setSpeed(2f);
//                    }
//                    jzvdStd.mediaInterface.start();
//                    jzvdStd.onStatePlaying();
//                }catch (Exception e){}
//            }
//        });

        //尺寸选择
        mViewBinding.rgCc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //满屏
//                if (checkedId == R.id.rb_cc_1) {
//                    jzvdStd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
//                }
                //100%

                try {
                    if (checkedId == R.id.rb_cc_2) {
                        jzvdStd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL);
                    }
                    //75%
                    if (checkedId == R.id.rb_cc_3) {
                        jzvdStd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL_75);
                    }
                    //50%
                    if (checkedId == R.id.rb_cc_4) {
                        jzvdStd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL_50);
                    }
                } catch (Exception e) {

                }

            }
        });

        //定时关闭
        mViewBinding.rgGb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //不开启
                if (checkedId == R.id.rb_gb_1) {
                    book().write(PagerCons.KEY_PLAYAPP_DSGB, "");
                }
                //30分钟
                else if (checkedId == R.id.rb_gb_2) {
                    book().write(PagerCons.KEY_PLAYAPP_DSGB, System.currentTimeMillis() + "_" + 30);
                }
                //60分钟
                else if (checkedId == R.id.rb_gb_3) {
                    book().write(PagerCons.KEY_PLAYAPP_DSGB, System.currentTimeMillis() + "_" + 60);
                }
                //90分钟
                else if (checkedId == R.id.rb_gb_4) {
                    book().write(PagerCons.KEY_PLAYAPP_DSGB, System.currentTimeMillis() + "_" + 90);
                }
            }
        });

        //点击投屏
        mViewBinding.llUpnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpListener.tp();
                hide();
            }
        });

        //点击下载
        mViewBinding.llDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpListener.download();
                hide();
            }
        });

        //音量监听
        mViewBinding.sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    AudioManager mAudioManager = (AudioManager) activity.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                    int now = seekBar.getProgress();
                    int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (1f * max * now / 100f), 0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //亮度监听
        mViewBinding.sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int light = seekBar.getProgress();
                WindowManager.LayoutParams params = JZUtils.getWindow(activity).getAttributes();
                params.screenBrightness = light / 100f;
                JZUtils.getWindow(activity).setAttributes(params);
                jzvdStd.brightnessPercent = light;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void openDownloadFun() {
        mViewBinding.llDownload.setVisibility(View.VISIBLE);
    }

    public void show(View anchor) {
        isShow = true;
        //显示范围
        popupWindow.setWidth(Utils.dip2px(activity, 400));
//        popupWindow.setHeight(Utils.dip2px(activity , 255) + 7);
//        popWindow.showAsDropDown(anchor, 0, 0);
        popupWindow.showAtLocation(anchor, Gravity.RIGHT, 0, 0);


//        int systemBrightness = 0;
//        try {
//            systemBrightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }
        mViewBinding.sb.setProgress(jzvdStd.brightnessPercent);
//            mGestureDownBrightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        //音量百分比初始化
        AudioManager mAudioManager = (AudioManager) activity.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mViewBinding.sb2.setProgress((int) (100.0f * mGestureDownVolume / max));
    }

    public void hide() {
        popupWindow.dismiss();
    }

    public void destory() {
        popupWindow.dismiss();
        Jzvd.VIDEO_IMAGE_DISPLAY_TYPE = Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL;
        jzvdStd = null;

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }


    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public static interface TPListener {
        public void tp();

        public void download();
    }

}

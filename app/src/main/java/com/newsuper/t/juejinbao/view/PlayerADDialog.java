package com.newsuper.t.juejinbao.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.databinding.DialogPlayeradBinding;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.activity.WebActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.PlayerADEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.PlayerADReadEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;


import java.util.HashMap;

import rx.Subscriber;
import rx.Subscription;

public class PlayerADDialog {
    private Activity activity;

    private DialogPlayeradBinding mViewBinding;

    private Dialog mDialog;

    public PlayerADDialog(Activity activity) {
        this.activity = activity;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_playerad, null, false);
        mDialog = new Dialog(activity, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);


        mViewBinding.ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        //请求数据
//        updateUI(playerADEntity);
    }


    public void show() {
        try {
            mDialog.dismiss();
            mDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hide() {
        activity = null;
        mDialog.dismiss();
    }

//    //请求广告数据
//    private void request(){
//        rx.Observable<PlayerADEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).playerAD(new HashMap<>()).map((new HttpResultFunc<PlayerADEntity>()));
//        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PlayerADEntity>() {
//            @Override
//            public void next(PlayerADEntity testBean) {
//                updateUI(testBean);
//            }
//
//            @Override
//            public void error(String target, Throwable e, String errResponse) {
//                Log.e("zy", "bindThird error=====++++e=" + e.toString());
////                baseView.error(errResponse);
//            }
//        }, activity, false);
//        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
//
//    }

    public boolean isShowAD(Activity activity , PlayerADEntity.DataBean dataBean){


        //随机数0-0.99
        double random = (Math.random() * 100) / 100d;

        //显示
        if(dataBean.getAd_probability() > random){
            readAD(activity , dataBean);
            updateUI(activity , dataBean);
        }
        //隐藏
        else{
            return false;
        }
        return true;

    }

    private void updateUI(Activity activity , PlayerADEntity.DataBean dataBean) {


        //按概率显示广告图片
        if(dataBean.getAd_control_type().equals("2")) {

            //随机数0-0.99
            double random = (Math.random() * 100) / 100d;
            Log.e("zy", "random = " + random);

            double temp = 0d;
            for (PlayerADEntity.DataBean.ImgsBean imgBean : dataBean.getImgs()) {
                temp += imgBean.getProbability();
                //选中广告
                if (temp > random) {
                    RequestOptions options = new RequestOptions()
                            .placeholder(R.mipmap.emptystate_pic)
                            .error(R.mipmap.emptystate_pic)
                            .skipMemoryCache(true)
                            .centerCrop()
                            .dontAnimate()
//                        .override(StringUtils.dip2px(activity, 340), StringUtils.dip2px(activity, 221))
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    try {
                        //带白色边框的圆形图片加载
                        Glide.with(activity).asBitmap().load(imgBean.getSrc())
                                .apply(options)
//                            .transition(DrawableTransitionOptions.withCrossFade())
                                .into(mViewBinding.ivAd);

                    } catch (Exception e) {
                        Log.e("zy", "glide_Exception");
                    }

                    mViewBinding.ivAd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickAD(activity , dataBean);
                            toAd(activity ,dataBean , imgBean);

//                            WebActivity.intentMe(activity, dataBean.getTitle(), dataBean.getUrl());
                        }
                    });
                    break;
                }


            }
        }
        //随机显示广告图片
        else{
            int number = dataBean.getImgs().size();
            int random = (int) (Math.random() * number);

            if (random < 0) {
                random = 0;
            }

            if (random >= number) {
                random = number - 1;
            }

            if(number == 0){
                return;
            }

            PlayerADEntity.DataBean.ImgsBean imgsBean = dataBean.getImgs().get(random);

            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .dontAnimate()
//                        .override(StringUtils.dip2px(activity, 340), StringUtils.dip2px(activity, 221))
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            try {
                //带白色边框的圆形图片加载
                Glide.with(activity).asBitmap().load(imgsBean.getSrc())
                        .apply(options)
//                            .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mViewBinding.ivAd);

            } catch (Exception e) {
                Log.e("zy", "glide_Exception");
            }

            mViewBinding.ivAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toAd(activity , dataBean , imgsBean);
//                    WebActivity.intentMe(activity, dataBean.getTitle(), dataBean.getUrl());
                }
            });

        }


    }

    //跳转广告
    private void toAd(Activity activity ,PlayerADEntity.DataBean dataBean , PlayerADEntity.DataBean.ImgsBean imgsBean){
        hide();

        if(imgsBean.getUrl().startsWith("http://") || imgsBean.getUrl().startsWith("https://")) {
            //内部打开
            if (dataBean.getRedirect_type() == 1) {

                //横屏打开
                if (dataBean.getView_type().equals("1")) {
                    WebActivity.intentMe(activity, dataBean.getTitle(), imgsBean.getUrl(), true);
                }
                //竖屏打开
                else {
                    WebActivity.intentMe(activity, dataBean.getTitle(), imgsBean.getUrl());
                }

            }
            //外部浏览器打开
            else {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(imgsBean.getUrl());
                intent.setData(content_url);
                activity.startActivity(intent);
            }
        }
    }

    //广告阅读
    private void readAD(Activity activity , PlayerADEntity.DataBean dataBean){

        HashMap map = new HashMap();
        map.put("ad_id" , dataBean.getId());
        map.put("type" , 0);
        map.put("uuid" , "");

        rx.Observable<PlayerADReadEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                playerADCount(map).map((new HttpResultFunc<PlayerADReadEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PlayerADReadEntity>() {
            @Override
            public void next(PlayerADReadEntity updateEntity) {

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {

            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
    }

    //广告点击
    private void clickAD(Activity activity , PlayerADEntity.DataBean dataBean){
        HashMap map = new HashMap();
        map.put("ad_id" , dataBean.getId());
        map.put("type" , 1);
        map.put("uuid" , "");

        rx.Observable<PlayerADReadEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                playerADCount(map).map((new HttpResultFunc<PlayerADReadEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PlayerADReadEntity>() {
            @Override
            public void next(PlayerADReadEntity updateEntity) {

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);

    }

}

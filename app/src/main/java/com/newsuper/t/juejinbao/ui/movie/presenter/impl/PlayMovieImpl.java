package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.google.gson.JsonObject;
import com.juejinchain.android.R;
import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.config.TTAdManagerHolder;
import com.juejinchain.android.module.MainActivity;
import com.juejinchain.android.module.movie.bean.UpdateEntity;
import com.juejinchain.android.module.movie.bean.YXL;
import com.juejinchain.android.module.movie.entity.CommenEntity;
import com.juejinchain.android.module.movie.entity.MovieReadEntity;
import com.juejinchain.android.module.movie.entity.MovieThirdIframeEntity;
import com.juejinchain.android.module.movie.entity.PlayerADEntity;
import com.juejinchain.android.module.movie.entity.VideoProgressBean;
import com.juejinchain.android.module.movie.utils.GlideRoundTransform;
import com.juejinchain.android.utils.MyToast;
import com.juejinchain.android.view.PlayerADDialog;
import com.ys.network.base.BasePresenter;
import com.ys.network.base.PagerCons;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;
import com.ys.network.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class PlayMovieImpl extends BasePresenter<PlayMovieImpl.MvpView> {


    //检测APP更新
    public void updateApp(Activity activity , Map<String, String> StringMap) {
        rx.Observable<UpdateEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                updateAPP(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<UpdateEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UpdateEntity>() {
            @Override
            public void next(UpdateEntity updateEntity) {
                getView().updateApp(updateEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                MyToast.show(activity , "下载地址获取失败");
//                getView().onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    /**
     * 请求云线路
     * @param context
     */
    public void requestYXL(Context context){
        rx.Observable<JsonObject> observable = RetrofitManager.getInstance(context).create(ApiService.class).vipparse(new HashMap<>()).map((new HttpResultFunc<JsonObject>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<JsonObject>() {
            @Override
            public void next(JsonObject testBean) {

                List<YXL> yxls = new ArrayList<>();

                String str = testBean.toString();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if(jsonObject.optString("code").equals("0")){
                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                        boolean isFirst = true;
                        Iterator iterator = jsonObject1.keys();
                        while(iterator.hasNext()){
                            String key = (String) iterator.next();
                            String value = jsonObject1.optString(key);

                            YXL yxl = new YXL();
                            yxl.name = key;
                            yxl.url = value;
                            if(isFirst){
                                yxl.select = true;
                                isFirst = false;
                            }else{
                                yxl.select = false;
                            }
                            yxls.add(yxl);
                        }

                    }

                }catch (JSONException e){}


                getView().requestYXL(yxls);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error();
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    /**
     * 错误上报
     * @param activity
     */
    public void shieldSource(final Activity activity , Map<String , String> map){
        rx.Observable<JsonObject> observable = RetrofitManager.getInstance(activity).create(ApiService.class).shieldSource(map).map((new HttpResultFunc<JsonObject>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<JsonObject>() {
            @Override
            public void next(JsonObject testBean) {
                ToastUtils.getInstance().show(activity,"无法播放请切换源试试");
//                Toast.makeText(activity , "无法播放" , Toast.LENGTH_SHORT).show();
                activity.finish();
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error();
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //超时上报
    public void movieSearchOutTime(Activity activity , Map<String, String> StringMap) {
        rx.Observable<CommenEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                movieSearchOutTime(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<CommenEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<CommenEntity>() {
            @Override
            public void next(CommenEntity updateEntity) {

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
//                getView().onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //好看影视播放计数上报
    public void movieRead(Activity activity , Map<String, String> StringMap) {
        rx.Observable<MovieReadEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                movieRead(StringMap).map((new HttpResultFunc<MovieReadEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieReadEntity>() {
            @Override
            public void next(MovieReadEntity movieReadEntity) {

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
//                getView().onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //保存视频进度
    public void saveProgress(MovieThirdIframeEntity movieThirdIframeEntity , int num){

        //标题为空不储存
        if(TextUtils.isEmpty(movieThirdIframeEntity.getTitle()) ){
            return;
        }

        try {
            String name = "";
            for(MovieThirdIframeEntity.DownListBean downListBean : movieThirdIframeEntity.getDownlist()){
                if(downListBean.getPlayed() == 1){
                    name = movieThirdIframeEntity.getTitle() + downListBean.getName();
                }
            }
            Log.e("zy" ,  name + " " + num);

            String data = book().read(PagerCons.PLAYMOVIEPROGRES);
            //存在缓存
            if (data != null && JSON.parseObject(data, VideoProgressBean.class) != null) {
                VideoProgressBean videoProgressBean = JSON.parseObject(data, VideoProgressBean.class);

                VideoProgressBean.ProgressBean progressBean = null;
                for (VideoProgressBean.ProgressBean progressBean1 : videoProgressBean.getProgressBeanList()) {
                    if (progressBean1.getTitle().equals(name)) {
                        progressBean = progressBean1;
                    }
                }

                //存在此进度，替换
                if (progressBean != null) {
                    progressBean.setProgress(num);
                }
                //不存在此进度，添加
                else {
                    progressBean = new VideoProgressBean.ProgressBean();
                    progressBean.setTitle(name);
//                    progressBean.setIndex(movieThirdIframeEntity.getCurrentIndex());
                    progressBean.setProgress(num);
                    videoProgressBean.getProgressBeanList().add(progressBean);
                }

                book().write(PagerCons.PLAYMOVIEPROGRES, JSON.toJSONString(videoProgressBean));

            }
            //不存在缓存
            else {
                VideoProgressBean videoProgressBean = new VideoProgressBean();

                List<VideoProgressBean.ProgressBean> progressBeanList = new ArrayList<>();

                VideoProgressBean.ProgressBean progressBean = new VideoProgressBean.ProgressBean();
                progressBean.setTitle(name);
//                progressBean.setIndex(movieThirdIframeEntity.getCurrentIndex());
                progressBean.setProgress(num);
                progressBeanList.add(progressBean);

                videoProgressBean.setProgressBeanList(progressBeanList);

                book().write(PagerCons.PLAYMOVIEPROGRES, JSON.toJSONString(videoProgressBean));

            }
        }catch (Exception e){}
    }

    //读取视频进度
    public int loadProgress(MovieThirdIframeEntity movieThirdIframeEntity){


        try {
            String name = "";
            for(MovieThirdIframeEntity.DownListBean downListBean : movieThirdIframeEntity.getDownlist()){
                if(downListBean.getPlayed() == 1){
                    name = movieThirdIframeEntity.getTitle() + downListBean.getName();
                }
            }

            String data = book().read(PagerCons.PLAYMOVIEPROGRES);
            //存在缓存
            if (data != null && JSON.parseObject(data, VideoProgressBean.class) != null) {
                VideoProgressBean videoProgressBean = JSON.parseObject(data, VideoProgressBean.class);

                for (VideoProgressBean.ProgressBean progressBean1 : videoProgressBean.getProgressBeanList()) {
                    if (progressBean1.getTitle().equals(name)) {
                        Log.e("zy" , "获取进度：" + name + " " + progressBean1.getProgress());
                        return progressBean1.getProgress();
                    }
                }

                return 0;

            } else {
                return 0;
            }


        }catch (Exception e){}

        return 0;
    }

    //请求服务器广告数据
    public void requestADData(Activity mActivity){
        rx.Observable<PlayerADEntity> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).playerAD(new HashMap<>()).map((new HttpResultFunc<PlayerADEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PlayerADEntity>() {
            @Override
            public void next(PlayerADEntity testBean) {
                getView().requestADData(testBean);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("zy", "bindThird error=====++++e=" + e.toString());
//                baseView.error(errResponse);
            }
        }, mActivity, false);
        RetrofitManager.getInstance(mActivity).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //显示广告弹窗
    public void loadADAndShow(Activity mActivity , PlayerADEntity playerADEntity) {


        try {
             if (playerADEntity.getData() != null) {


                if(playerADEntity.getData().size() == 0){
                    return;
                }

                //随机获取位置
                int number = playerADEntity.getData().size();
                int random = (int) (Math.random() * number);

                Log.e("zy" , "广告位置 " + number + " " + random);

                if (random < 0) {
                    random = 0;
                }

                if (random >= number) {
                    random = number - 1;
                }


                PlayerADDialog playerADDialog = new PlayerADDialog(mActivity);
                playerADDialog.show();
                if(!playerADDialog.isShowAD(mActivity , playerADEntity.getData().get(random))){
                    playerADDialog.hide();
                }


            }
        }catch (Exception e){
        }

    }

    /**
     * 加载插屏广告
     */
    @SuppressWarnings({"ALL", "SameParameterValue"})
    public void loadInteractionAd(Activity mActivity) {
        //step4:创建广告请求参数AdSlot,注意其中的setNativeAdtype方法，具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(TTAdManagerHolder.POS_ID_MOVIEPAUSE)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(900, 600)
                .setNativeAdType(AdSlot.TYPE_INTERACTION_AD)//请求原生广告时候，请务必调用该方法，设置参数为TYPE_BANNER或TYPE_INTERACTION_AD
                .build();

        //step5:请求广告，对请求回调的广告作渲染处理
        MainActivity.mTTAdNative.loadNativeAd(adSlot, new TTAdNative.NativeAdListener() {
            @Override
            public void onError(int code, String message) {

            }

            @Override
            public void onNativeAdLoad(List<TTNativeAd> ads) {
                if (ads.get(0) == null) {
                    return;
                }
                showAd(mActivity , ads.get(0));
            }
        });
    }

    @SuppressWarnings("RedundantCast")
    private void showAd(Activity mActivity , TTNativeAd ad) {
        Dialog mAdDialog = new Dialog(mActivity, R.style.native_insert_dialog);
        mAdDialog.setCancelable(false);
        mAdDialog.setContentView(R.layout.native_insert_ad_layout2);
        ViewGroup mRootView = mAdDialog.findViewById(R.id.native_insert_ad_root);
        ImageView mAdImageView = (ImageView) mAdDialog.findViewById(R.id.iv);
        //限制dialog 的最大宽度不能超过屏幕，宽高最小为屏幕宽的 1/3
//        DisplayMetrics dm = mActivity.getResources().getDisplayMetrics();
//        int maxWidth = (dm == null) ? 0 : dm.widthPixels;
//        int minWidth = maxWidth / 3;
//        mAdImageView.setMaxWidth(maxWidth);
//        mAdImageView.setMinimumWidth(minWidth);
//        //noinspection SuspiciousNameCombination
//        mAdImageView.setMinimumHeight(minWidth);
        ImageView mCloseImageView = (ImageView) mAdDialog.findViewById(R.id.iv_close);
        ImageView iv_dislike = (ImageView) mAdDialog.findViewById(R.id.iv_dislike);


        mCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdDialog.dismiss();
            }
        });

        //绑定广告view事件交互
        //可以被点击的view, 比如标题、icon等,点击后尝试打开落地页，也可以把nativeView放进来意味整个广告区域可被点击
        List<View> clickViewList = new ArrayList<>();
        clickViewList.add(mAdImageView);

        //触发创意广告的view（点击下载或拨打电话），比如可以设置为一个按钮，按钮上文案根据广告类型设定提示信息
        List<View> creativeViewList = new ArrayList<>();
        //如果需要点击图文区域也能进行下载或者拨打电话动作，请将图文区域的view传入
        //creativeViewList.add(nativeView);
        creativeViewList.add(mAdImageView);

        //重要! 这个涉及到广告计费，必须正确调用。convertView必须使用ViewGroup。
        ad.registerViewForInteraction(mRootView, clickViewList, creativeViewList, iv_dislike, new TTNativeAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, TTNativeAd ad) {
                if (ad != null) {
//                    ToastUtils.getInstance().show(mActivity, "广告" + ad.getTitle() + "被点击");
                }
                mAdDialog.dismiss();
            }

            @Override
            public void onAdCreativeClick(View view, TTNativeAd ad) {
                if (ad != null) {
//                    ToastUtils.getInstance().show(mActivity, "广告" + ad.getTitle() + "被创意按钮被点击");
                }
                mAdDialog.dismiss();
            }

            @Override
            public void onAdShow(TTNativeAd ad) {
                if (ad != null) {
//                    ToastUtils.getInstance().show(mActivity, "广告" + ad.getTitle() + "展示");
                }
            }
        });
        //加载ad 图片资源
        if (ad.getImageList() != null && !ad.getImageList().isEmpty()) {
            TTImage image = ad.getImageList().get(0);
            if (image != null && image.isValid()) {
//                mRequestManager.load(image.getImageUrl()).into(mAdImageView);

                RequestOptions myOptions = new RequestOptions()
                        .centerCrop()
                        .transform(new GlideRoundTransform(mActivity,10));
                Glide.with(mActivity).load(image.getImageUrl()).apply(myOptions).into(mAdImageView);
            }
        }

        mAdDialog.show();

//        TTImage image = ad.getImageList().get(0);
//        int width = image.getWidth();
//        String url = image.getImageUrl();
//        Glide.with(mActivity).asBitmap().load(url).into(
//                new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        // 保存图片至相册
//                        if (mAdImageView != null) {
//                            mAdImageView.setBackground(bitmapToDrawable(mActivity, resource));
//
//                            if (Looper.getMainLooper() != Looper.myLooper()) {
//                                throw new IllegalStateException("不能在子线程调用 TTInteractionAd.showInteractionAd");
//                            }
//                            mAdDialog.show();
//                        }
//
//                    }
//                }
//        );
    }

    //加载插屏广告
//    public void loadInteractionAd(Activity activity , TTAdNative mTTAdNative){
////step4:创建插屏广告请求参数AdSlot,具体参数含义参考文档
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(TTAdManagerHolder.POS_ID_MOVIEPAUSE)
//                .setSupportDeepLink(true)
//                .setImageAcceptedSize(600, 600) //根据广告平台选择的尺寸，传入同比例尺寸
//                .build();
//        //step5:请求广告，调用插屏广告异步请求接口
//        mTTAdNative.loadInteractionAd(adSlot, new TTAdNative.InteractionAdListener() {
//            @Override
//            public void onError(int code, String message) {
//                Log.e("zy" , message);
////                TToast.show(getApplicationContext(), "code: " + code + "  message: " + message);
//            }
//
//            @Override
//            public void onInteractionAdLoad(TTInteractionAd ttInteractionAd) {
////                TToast.show(getApplicationContext(), "type:  " + ttInteractionAd.getInteractionType());
//                ttInteractionAd.setAdInteractionListener(new TTInteractionAd.AdInteractionListener() {
//                    @Override
//                    public void onAdClicked() {
////                        Log.d(TAG, "被点击");
////                        TToast.show(mContext, "广告被点击");
//                    }
//
//                    @Override
//                    public void onAdShow() {
////                        Log.d(TAG, "被展示");
////                        TToast.show(mContext, "广告被展示");
//                    }
//
//                    @Override
//                    public void onAdDismiss() {
////                        Log.d(TAG, "插屏广告消失");
////                        TToast.show(mContext, "广告消失");
//                    }
//                });
//                //如果是下载类型的广告，可以注册下载状态回调监听
//                if (ttInteractionAd.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
//                    ttInteractionAd.setDownloadListener(new TTAppDownloadListener() {
//                        @Override
//                        public void onIdle() {
////                            Log.d(TAG, "点击开始下载");
////                            TToast.show(mContext, "点击开始下载");
//                        }
//
//                        @Override
//                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
////                            Log.d(TAG, "下载中");
////                            TToast.show(mContext, "下载中");
//                        }
//
//                        @Override
//                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
////                            Log.d(TAG, "下载暂停");
////                            TToast.show(mContext, "下载暂停");
//                        }
//
//                        @Override
//                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
////                            Log.d(TAG, "下载失败");
////                            TToast.show(mContext, "下载失败");
//                        }
//
//                        @Override
//                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {
////                            Log.d(TAG, "下载完成");
////                            TToast.show(mContext, "下载完成");
//                        }
//
//                        @Override
//                        public void onInstalled(String fileName, String appName) {
////                            Log.d(TAG, "安装完成");
////                            TToast.show(mContext, "安装完成");
//                        }
//                    });
//                }
//                //弹出插屏广告
//                ttInteractionAd.showInteractionAd(activity);
//            }
//        });
//    }


    //保存观影历史
    public void savePlayed(MovieThirdIframeEntity movieThirdIframeEntity){
//        try {
//            ArrayList<MovieThirdIframeEntity> saves = book().read(PagerCons.KEY_MOVIEHISTORY_SAVE);
//
//            if(saves == null){
//                saves = new ArrayList<>();
//            }
//
//            //移除之前的
//            for(MovieThirdIframeEntity mMovieThirdIframeEntity : saves){
//                if(mMovieThirdIframeEntity.getTitle().equals(movieThirdIframeEntity.getTitle())){
//                    saves.remove(mMovieThirdIframeEntity);
//                    break;
//                }
//            }
//
//
//            while (saves.size() >= 50){
//                saves.remove(49);
//            }
//
//
//            saves.add(0 , movieThirdIframeEntity);
//
//            book().write(PagerCons.KEY_MOVIEHISTORY_SAVE , saves);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public interface MvpView{
        void requestYXL(List<YXL> yxls);
        void error();
        void updateApp(UpdateEntity updateEntity);
        void requestADData(PlayerADEntity playerADEntity);
    }




}
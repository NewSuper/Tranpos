package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

//import com.bytedance.sdk.openadsdk.AdSlot;
//import com.bytedance.sdk.openadsdk.TTAdNative;
//import com.bytedance.sdk.openadsdk.TTDrawFeedAd;
//import com.bytedance.sdk.openadsdk.TTNativeAd;


import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentAdBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.home.entity.PlaySmallvideoMsg;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
import com.newsuper.t.juejinbao.view.FullScreenVideoView;
import com.newsuper.t.juejinbao.view.photodrag.DragRelativeLayout;
import com.newsuper.t.juejinbao.view.photodrag.OnDragListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


public class AdFragment extends BaseFragment<PublicPresenterImpl, FragmentAdBinding> {
    private int[] loc;

    boolean isAlive = false;

    private DragRelativeLayout mDragLayout;

   // private TTDrawFeedAd mTTDrawFeedAd;

    //fragment所处的位置
    private int position = 0;

    private boolean live = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ad, container, false);
        mDragLayout = view.findViewById(R.id.rl_drag);
        isAlive = true;

        loc = getArguments().getIntArray("region");
        position = getArguments().getInt("position");
        if (loc != null) {
            mDragLayout.setTransitionsRegion(loc[0], loc[1], loc[2], loc[3], loc[4], loc[5]);
        }

        mDragLayout.setOnoDragListener(new OnDragListener() {
            @Override
            public void onStartDrag() {
                super.onStartDrag();
            }

            @Override
            public void onStartEnter(boolean outOfBound) {
                super.onStartEnter(outOfBound);
            }

            @Override
            public void onRelease(boolean isResume) {
                super.onRelease(isResume);
//                if (!isResume) {
//                    mIvBg.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onEndExit() {
                super.onEndExit();
                if(getActivity() != null) {
                    getActivity().finish();
                }
            }

            @Override
            public void onEndEnter() {
                super.onEndEnter();
            }

            @Override
            public void onMoveTop() {
                super.onMoveTop();

            }

            @Override
            public void onStartExit(boolean outOfBound) {
                super.onStartExit(outOfBound);
            }

            @Override
            public void onEndResume() {
                super.onEndResume();

            }
        });

        return view;

    }

    @Override
    public void initView() {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


    }

    private View getView2() {
        FullScreenVideoView videoView = new FullScreenVideoView(mActivity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        videoView.setLayoutParams(layoutParams);
        return videoView;
    }

    @Override
    public void initData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                requestTTDrawFeedAds(mTTAdNative);
//            }
//        }).start();

    }

//    private TTDrawFeedAd ttDrawFeedAd;
//    public void setTTDrawFeedAd(TTDrawFeedAd ttDrawFeedAd){
//        this.ttDrawFeedAd = ttDrawFeedAd;
//    }


  //  private TTAdNative mTTAdNative;
    //请求广告
//    public void requestTTDrawFeedAds(TTAdNative mTTAdNative) {
//        if(mTTAdNative == null){
//            mTTAdNative = TTAdManagerHolder.get().createAdNative(mActivity);
//        }
//
//        if(!isAlive){
//            return;
//        }
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId("920793078")
//                .setSupportDeepLink(true)
//                .setImageAcceptedSize(1080, 1920)
//                .setAdCount(1) //请求广告数量为1到3条
//                .build();
//
//
//        //step4:请求广告,对请求回调的广告作渲染处理
//        mTTAdNative.loadDrawFeedAd(adSlot, new TTAdNative.DrawFeedAdListener() {
//            @Override
//            public void onError(int code, String message) {
//                Log.e("zy", message);
//                //无广告，继续请求
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////
////                        try {
////                            Thread.sleep(3000);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
////
////                        requestTTDrawFeedAds(mTTAdNative);
////                    }
////                }).start();
//
//
//                Log.e("zy" , "请求广告失败 " + message);
//
//                if(mTTDrawFeedAd == null) {
//                    //从预留广告中获取
//                    mTTDrawFeedAd = ((PlaySmallVideoActivity) getActivity()).ttDrawFeedAdList.get((int) (Math.random() * 3));
////                    update(((PlaySmallVideoActivity) getActivity()).ttDrawFeedAdList.get((int) (Math.random() * 3)));
//                }
//
//                //加载缓存广告
////                String adCache = book().read(PagerCons.KEY_SMALLVIDEO_AD_CACHE);
////                if(!TextUtils.isEmpty(adCache)){
////                    Log.e("zy" , "有广告缓存");
////                    TTDrawFeedAd ttDrawFeedAd = JSON.parseObject(adCache, TTDrawFeedAd.class);
////                    update(ttDrawFeedAd);
////                }else{
////                    Log.e("zy" , "无广告缓存");
////                }
//            }
//
//            @Override
//            public void onDrawFeedAdLoad(List<TTDrawFeedAd> ads) {
//                if (ads == null || ads.isEmpty()) {
//                    return;
//                }
//                if(ads != null && ads.size() >= 1) {
//                    update(ads.get(0));
////                    update(((PlaySmallVideoActivity)getActivity()).ttDrawFeedAdList.get((int)(Math.random()*3)));
//                }
//            }
//        });
//    }
//
//    private void update(TTDrawFeedAd ttDrawFeedAd){
//        Log.e("zy" , "获取到广告:" + position);
////        Log.e("zy" , "总广告数：" +  ((PlaySmallVideoActivity)getActivity()).getTtDrawFeedAdList().size());
////        Log.e("zy" , "拉取广告：" + ttDrawFeedAd.getTitle());
//        //重复广告，继续请求
////        if(removeDuplicate(ttDrawFeedAd , ((PlaySmallVideoActivity)getActivity()).getTtDrawFeedAdList())){
////            Log.e("zy" , "重复广告，继续请求");
////            requestTTDrawFeedAds(mTTAdNative);
////            return;
////        }else{
////            Log.e("zy" , "加入广告:" + ttDrawFeedAd.getTitle());
////            ((PlaySmallVideoActivity)getActivity()).addTTDrawFeedAd(ttDrawFeedAd);
////        }
//
//        mTTDrawFeedAd = ttDrawFeedAd;
//
//
//        showAD();
//    }
//
//    private void showAD(){
//            if( mTTDrawFeedAd != null) {
//
//                mTTDrawFeedAd.setActivityForDownloadApp(mActivity);
//                //点击监听器必须在getAdView之前调
//                mTTDrawFeedAd.setCanInterruptVideoPlay(true);
//                mTTDrawFeedAd.setPauseIcon(BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.dislike_icon), 60);
//
//
//                View view = getView2();
//                view = mTTDrawFeedAd.getAdView();
//
//                mViewBinding.flAd.removeAllViews();
//                mViewBinding.flAd.addView(view);
//
//
//
//                //
//                Button action = new Button(mActivity);
//                action.setText(mTTDrawFeedAd.getButtonText());
//                action.setTextColor(getResources().getColor(R.color.app_white));
//                action.setBackgroundResource(R.drawable.bg_videoad2);
//
//
//
//
////                action.setVisibility(View.GONE);
//                Button btTitle = new Button(mActivity);
//                btTitle.setText(mTTDrawFeedAd.getTitle());
//                btTitle.setTextColor(Color.parseColor("#4badf3"));
//                btTitle.setBackgroundResource(R.drawable.bg_videoad1);
////                btTitle.setVisibility(View.GONE);
//
//                int height = (int) dip2px(mActivity, 50);
//                int margin = (int) dip2px(mActivity, 10);
//
//                //noinspection SuspiciousNameCombination
//                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(height * 3, height);
//                lp.gravity = Gravity.END | Gravity.BOTTOM;
//                lp.leftMargin = margin;
//                lp.rightMargin = margin;
//                lp.bottomMargin = margin;
//                mViewBinding.flAd.addView(action, lp);
//
//                FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(height * 3, height);
//                lp1.gravity = Gravity.START | Gravity.BOTTOM;
//                lp1.rightMargin = margin;
//                lp1.leftMargin = margin;
//                lp1.bottomMargin = margin;
//                mViewBinding.flAd.addView(btTitle, lp1);
//
//                //
//
//
//                List<View> clickViews = new ArrayList<>();
//                clickViews.add(btTitle);
//                List<View> creativeViews = new ArrayList<>();
//                creativeViews.add(action);
//
//
//
//                try {
//                    JzvdStd.releaseAllVideos();
//                    Jzvd.releaseAllVideos();
//                }catch (Exception e){}
//
//                mTTDrawFeedAd.registerViewForInteraction(mViewBinding.flAd, clickViews, creativeViews, new TTNativeAd.AdInteractionListener() {
//                    @Override
//                    public void onAdClicked(View view, TTNativeAd ad) {
////                    showToast("onAdClicked");
//                        MobclickAgent.onEvent(MyApplication.getContext(), EventID.LITTLEPLAY_ADS_CILCKTIMES);
//                        MobclickAgent.onEvent(MyApplication.getContext(), EventID.LITTLEPLAY_ADS_CILCKUSERS);
//                    }
//
//                    @Override
//                    public void onAdCreativeClick(View view, TTNativeAd ad) {
////                    showToast("onAdCreativeClick");
//                    }
//
//                    @Override
//                    public void onAdShow(TTNativeAd ad) {
////                    showToast("onAdShow");
//                    }
//                });
//
//
//            }
//
//    }


//    //去重判断
//    private boolean removeDuplicate(TTDrawFeedAd ttDrawFeedAd , List<TTDrawFeedAd> ttDrawFeedAdList){
//
//        boolean isDuplicate = false;
//
//        for(TTDrawFeedAd mTTDrawFeedAd : ttDrawFeedAdList){
//            if (ttDrawFeedAd.getTitle().trim().equals(mTTDrawFeedAd.getTitle().trim())){
//                Log.e("zy" , "重复广告:" + ttDrawFeedAd.getTitle());
//                isDuplicate = true;
//                break;
//            }
//        }
//
////        if(!isDuplicate){
////            get
////            ttDrawFeedAdList.add(ttDrawFeedAd);
////        }
//
//        return isDuplicate;
//
//    }


//    boolean viewVisiable = false;
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(isVisible){
            try {
                    JzvdStd.releaseAllVideos();
                    Jzvd.releaseAllVideos();
                }catch (Exception e){}
//            showAD();
//            if(!live) {
//                Log.e("zy" , "当前页面刷新");
//                live = true;
//
//
////                try {
////                    JzvdStd.releaseAllVideos();
////                    Jzvd.releaseAllVideos();
////                }catch (Exception e){}
//
////                requestTTDrawFeedAds(mTTAdNative);
//            }
        }else{

        }
//        else{
//            mViewBinding.flAd.removeAllViews();
//            if(mTTDrawFeedAd != null){
//                ((PlaySmallVideoActivity)getActivity()).ttDrawFeedAdList.remove(mTTDrawFeedAd);
//            }
//        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(PlaySmallvideoMsg message) {
        if (message.getPagePosition() == position || message.getPagePosition() == position + 1 || message.getPagePosition() == position -1) {
            if(!live) {
                Log.e("zy" , "open小视频广告:" + position);
                live = true;
              //  requestTTDrawFeedAds(mTTAdNative);
            }
        }else{
            live = false;
//            Log.e("zy" , "close小视频广告:" + position);
            mViewBinding.flAd.removeAllViews();
        }
    }
//    @Subscribe()
//    public void onPlay(VideoPlayEvent videoPlayEvent) {
//        if (videoPlayEvent.getType() == 1 && videoPlayEvent.getPosition() == position) {
//            requestTTDrawFeedAds(mTTAdNative);
//        }else{
//            mViewBinding.flAd.removeAllViews();
//            if(mTTDrawFeedAd != null){
//                ((PlaySmallVideoActivity)getActivity()).ttDrawFeedAdList.remove(mTTDrawFeedAd);
//            }
//        }
//    }




    @Override
    public void onDestroyView() {
        //保存广告数据
//        book().write(PagerCons.KEY_SMALLVIDEO_AD_CACHE , JSON.toJSONString(mTTDrawFeedAd));

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        isAlive = false;
        mViewBinding.flAd.removeAllViews();
//        if(mTTDrawFeedAd != null){
//            ((PlaySmallVideoActivity)getActivity()).getTtDrawFeedAdList().remove(mTTDrawFeedAd);
//        }
      //  mTTAdNative = null;

        if(mDragLayout!=null){
            mDragLayout.clearAnimation();
            mDragLayout.removeAllViews();
        }

        super.onDestroyView();
    }


    //dp转像素
    public int dip2px(Context context , float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

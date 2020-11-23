package com.newsuper.t.juejinbao.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.activity.WebActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.movie.utils.WebViewUtils;
import com.newsuper.t.juejinbao.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

import static android.content.Context.TELEPHONY_SERVICE;
import static io.paperdb.Paper.book;

public class TaskWebFragment extends WebFragment{

    private RelativeLayout rlTitle;

    private WebView wb;

//    private List<TaskADEntity.DataBean.AdInfo9Bean> adInfo9BeanList = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_web, container, false);
        rlTitle = view.findViewById(R.id.rl_title);
        webView = view.findViewById(R.id.webView);
        webViewUtils = new WebViewUtils(null, this, webView, view.findViewById(R.id.rl_loading) , new WebViewUtils.WebViewUtilsListener() {
            @Override
            public void logout() {
                Utils.logout(getActivity());
            }

            @Override
            public void showRightBottomAD(String imgUrl, String link) {
                view.findViewById(R.id.iv_ad).setVisibility(View.VISIBLE);


                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.emptystate_pic)
                        .error(R.mipmap.emptystate_pic)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .dontAnimate()
                        .override(StringUtils.dip2px(getActivity(), 100), StringUtils.dip2px(getActivity(), 100))
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                try {
                    if(view.findViewById(R.id.iv_ad) != null) {
                        //带白色边框的圆形图片加载
                        Glide.with(getActivity()).asBitmap().load(imgUrl)
                                .apply(options)
                                .into(((ImageView) view.findViewById(R.id.iv_ad)));
                    }
                }catch (Exception e){
                    Log.e("zy" , "glide_Exception");
                }


                view.findViewById(R.id.iv_ad).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebActivity.intentMe(getActivity() , "广告" , link);
                    }
                });

            }

            @Override
            public void getWebMovieInfo(String url) {

            }

            @Override
            public void getWebLiveInfo(String url, String js) {

            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        clearCache();
        Log.e("TAG", "initView:注解状态1=======>>>>>>> " + url);
        webView.loadUrl(url);

        wb = view.findViewById(R.id.wb);
        view.findViewById(R.id.rl_title).setVisibility(View.VISIBLE);
//        initAdWeb();

        if(NetUtils.isConnected(getActivity())) {
            requestAd();
        }

        view.findViewById(R.id.img_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!LoginEntity.getIsLogin()) {
                    Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
                MobclickAgent.onEvent(getContext(), EventID.TASK_TOPRIGHT_SHARE);   //任务-分享-埋点
                //任务分享
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setUrl_type(ShareInfo.TYPE_TASK);
                shareInfo.setUrl_path(ShareInfo.PATH_TASK);
                ShareDialog mShareDialog = new ShareDialog(getActivity(), shareInfo , null);
                mShareDialog.show();
            }
        });
        return view;
    }

//    private void initAdWeb(){
//        wb = view.findViewById(R.id.wb);
//        view.findViewById(R.id.rl_title).setVisibility(View.VISIBLE);
//
//        WebSettings websettings = wb.getSettings();
//        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        websettings.setLoadsImagesAutomatically(true);
//        websettings.setJavaScriptEnabled(true);
//        websettings.setUseWideViewPort(true);
//        websettings.setSupportMultipleWindows(true);
//        websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        websettings.setAppCacheEnabled(true);
//        websettings.setDomStorageEnabled(true);
//
//        //与js弹框交互
//        websettings.setJavaScriptEnabled(true);
//        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
//
//        wb.setWebViewClient(new WebViewClient());
//        wb.setWebChromeClient(new WebChromeClient());
//
//
//    }

    //请求广告
    private void requestAd(){


        Map<String, String> paramMap = new HashMap<>();

        rx.Observable<TaskADEntity> observable = RetrofitManager.getInstance(getActivity()).create(ApiService.class).getTaskAD(paramMap).map((new HttpResultFunc<TaskADEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<TaskADEntity>() {
            @Override
            public void next(TaskADEntity testBean) {
                TaskADEntity.DataBean.AdInfo9Bean bean = new TaskADEntity.DataBean.AdInfo9Bean();
                bean.setAd_name("我的任务");
                testBean.getData().getAd_info_9().add(0 ,bean);

                for (int i = 0; i < testBean.getData().getAd_info_9().size(); i++) {
                    TaskADEntity.DataBean.AdInfo9Bean adInfo9Bean = testBean.getData().getAd_info_9().get(i);

                    TextView textView = new TextView(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0 , 0 , Utils.dip2px(getActivity() , 15) , 0);
                    textView.setLayoutParams(params);
                    textView.setText(adInfo9Bean.getAd_name());
                    textView.setGravity(Gravity.CENTER);
                    if(i == 0){
                        textView.setTextColor(Color.parseColor("#ffffff"));
                        textView.setTextSize(18);

                        TextPaint tp = textView.getPaint();
                        tp.setFakeBoldText(true);

                    }else {
                        textView.setTextColor(Color.parseColor("#eeeeee"));
                        textView.setTextSize(15);

                        TextPaint tp = textView.getPaint();
                        tp.setFakeBoldText(false);
                    }
                    textView.setTag(adInfo9Bean);

                    ((LinearLayout)view.findViewById(R.id.ll_contain)).addView(textView);
                    textViews.add(textView);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for(TextView textView1 : textViews){
                                textView1.setTextColor(Color.parseColor("#ffffff"));
                                textView1.setTextSize(15);

                                TextPaint tp = textView1.getPaint();
                                tp.setFakeBoldText(false);
                            }
                            textView.setTextColor(Color.parseColor("#ffffff"));
                            textView.setTextSize(18);

                            TextPaint tp = textView.getPaint();
                            tp.setFakeBoldText(true);

                            clickADTab((TaskADEntity.DataBean.AdInfo9Bean) textView.getTag());
                        }
                    });

                }

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
//                Log.e("zy" ,  target);
//                baseView.error(errResponse);
            }
        }, getActivity(), false);
        RetrofitManager.getInstance(getActivity()).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //点击上方tab
    private void clickADTab(TaskADEntity.DataBean.AdInfo9Bean adInfo9Bean){
        if(TextUtils.isEmpty(adInfo9Bean.getLink()) ){
            wb.setVisibility(View.GONE);
        }else{
            wb.setVisibility(View.VISIBLE);
            wb.loadUrl(adInfo9Bean.getLink());


            try {
                TelephonyManager TelephonyMgr = (TelephonyManager) getActivity().getSystemService(TELEPHONY_SERVICE);
                @SuppressLint("MissingPermission") String szImei = TelephonyMgr.getDeviceId();
                //调用广告浏览接口
                Map<String, String> map1 = new HashMap<>();
                map1.put("type", "0");
                map1.put("uuid", szImei);
                map1.put("ad_id", adInfo9Bean.getId() + "");
                Utils.scanOrClickWebAD(getActivity(), map1);

                Map<String, String> map2 = new HashMap<>();
                map2.put("type", "1");
                map2.put("uuid", szImei);
                map2.put("ad_id", adInfo9Bean.getId() + "");
                Utils.scanOrClickWebAD(getActivity(), map2);
            }catch (Exception e){}

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        //宝箱分享通知显示奖励
        int state = book().read(ShareDialog.SHARE_NOTIFY , 0);
        if(state == 2){
            book().write(ShareDialog.SHARE_NOTIFY , 0);
//            webViewUtils.showReward(150 + "" , "奖励到账" , "分享成功");
            shareBoxBackInterface();
        }

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(rlTitle)
                .init();
    }
}

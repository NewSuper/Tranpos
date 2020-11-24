package com.newsuper.t.juejinbao.ui.movie.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.DialogCheckinBinding;
import com.newsuper.t.juejinbao.ui.movie.activity.WebActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.CheckInEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;


import java.util.HashMap;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;

public class CheckInDialog {

    private Context context;
    DialogCheckinBinding mViewBinding;


    private Dialog mDialog;


    public CheckInDialog(Context context , double gold, CheckInEntity.DataBean.AdInfoBean adInfoBean) {
        this.context = context;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_checkin, null, false);
        mDialog = new Dialog(context, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);

        mViewBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

        mViewBinding.tv1.setText(adInfoBean.getTitle());
        mViewBinding.tvGold.setText("+" + Utils.FormatGold(gold) + "金币");


        if(adInfoBean.getImages().size() > 0) {
            //            RequestOptions options = new RequestOptions()
//                    .placeholder(R.mipmap.emptystate_pic)
//                    .error(R.mipmap.emptystate_pic)
//                    .skipMemoryCache(true)
//                    .centerCrop()
//                    .dontAnimate()
//                .override(Utils.dip2px(context, 200), Utils.dip2px(context, 100))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(adInfoBean.getImages().get(0)).into(mViewBinding.ivAd);
//            RequestOptions options = new RequestOptions()
//                    .placeholder(R.mipmap.emptystate_pic)
//                    .error(R.mipmap.emptystate_pic)
//                    .skipMemoryCache(true)
//                    .centerCrop()
//                    .dontAnimate()
//                .override(Utils.dip2px(context, 200), Utils.dip2px(context, 100))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL);
//            //带白色边框的圆形图片加载
//            Glide.with(context).asBitmap().load(adInfoBean.getImages().get(0))
//                    .apply(options)
//                    .into(mViewBinding.ivAd);
            mViewBinding.tvAd.setText(adInfoBean.getAd_name());
            mViewBinding.tvSign.setText(adInfoBean.getAd_sign());
        }



        mViewBinding.rlAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                WebActivity.intentMe(context , adInfoBean.getAd_name() , adInfoBean.getLink());


                try {
                    TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
                    @SuppressLint("MissingPermission") String szImei = TelephonyMgr.getDeviceId();
                    //调用广告浏览接口
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("type", "0");
                    map1.put("uuid", szImei);
                    map1.put("ad_id", adInfoBean.getId() + "");
                    Utils.scanOrClickWebAD(context, map1);

                    Map<String, String> map2 = new HashMap<>();
                    map2.put("type", "1");
                    map2.put("uuid", szImei);
                    map2.put("ad_id", adInfoBean.getId() + "");
                    Utils.scanOrClickWebAD(context, map2);
                }catch (Exception e){}

                hide();
            }
        });

    }

    public void show() {
        mDialog.show();
    }

    public void hide() {
        mDialog.dismiss();
    }



}

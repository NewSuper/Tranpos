package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ViewPlayerspeedBinding;
import com.newsuper.t.juejinbao.ui.movie.player.JzvdPlayerBusiness;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

//倍速弹窗
public class PlayerSpeedPopupWindow2 {
    private Activity activity;
    public MyPopupWindow popupWindow;
    private JzvdPlayerBusiness jzvdStd;

    private ViewPlayerspeedBinding mViewBinding;

    int widthPixels;
    int heightPixels;

    public PlayerSpeedPopupWindow2(Activity activity, JzvdPlayerBusiness jzvdStd) {
        this.activity = activity;
        this.jzvdStd = jzvdStd;
        init();
    }

    private void init(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        widthPixels = outMetrics.widthPixels;
        heightPixels = outMetrics.heightPixels;

        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.view_playerspeed, null, false);
        popupWindow = new MyPopupWindow(mViewBinding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setClippingEnabled(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        mViewBinding.rbBs2.setChecked(true);


        //倍速选择
        mViewBinding.rgBs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    //0.75倍
                    if (checkedId == R.id.rb_bs_1) {
                        jzvdStd.mediaInterface.setSpeed(0.75f);
                    }
                    //正常
                    if (checkedId == R.id.rb_bs_2) {
                        jzvdStd.mediaInterface.setSpeed(1f);
                    }
                    //1.25倍
                    if (checkedId == R.id.rb_bs_3) {
                        jzvdStd.mediaInterface.setSpeed(1.25f);
                    }
                    //1.5倍
                    if (checkedId == R.id.rb_bs_4) {
                        jzvdStd.mediaInterface.setSpeed(1.5f);
                    }
                    //2倍
                    if (checkedId == R.id.rb_bs_5) {
                        jzvdStd.mediaInterface.setSpeed(2f);
                    }
                    jzvdStd.mediaInterface.start();
                    jzvdStd.onStatePlaying();
                    hide();
                }catch (Exception e){}
            }
        });
    }


    public void show(View anchor) {
        popupWindow.isShow = true;
//        if (Build.VERSION.SDK_INT >= 24) {
//            Rect visibleFrame = new Rect();
//            anchor.getGlobalVisibleRect(visibleFrame);
//            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
//            popupWindow.setHeight(height);
//
//        }
//
//        popupWindow.showAsDropDown(anchor , 0 , -200);
        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY , widthPixels - Utils.dip2px(activity , 100 + 30 + 50 ) , heightPixels -Utils.dip2px(activity , 200 + 50 + 20));


    }

    public void hide() {
        popupWindow.isShow = false;
        popupWindow.dismiss();
    }

    public static class MyPopupWindow extends PopupWindow{
        public boolean isShow = false;
        @Override
        public void dismiss() {

            super.dismiss();
        }
        public MyPopupWindow(View contentView, int width, int height) {
            super(contentView, width, height, false);
        }
    }

    public void destory(){
        jzvdStd = null;
    }

}

package com.newsuper.t.consumer.widget.TimePicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.newsuper.t.R;
import com.newsuper.t.consumer.widget.ClickableLinearLayoutLayout;


public class CenterDialogView {

    private Activity activity;
    private WindowManager.LayoutParams params;
    private boolean isShow;
    private WindowManager windowManager;
    public ViewGroup rootView;
    public ClickableLinearLayoutLayout linearLayout;
    private View contentView;

    public CenterDialogView(Activity activity){

        this.activity = activity;
        windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        initLayout();
    }

    private void initLayout(){

        //这是根布局
        rootView = (ViewGroup) View.inflate(activity, R.layout.item_root_dialogwindow, null);
        linearLayout = (ClickableLinearLayoutLayout) rootView.findViewById(R.id.linearLayout);
        View view_out = (View) rootView.findViewById(R.id.view_out);

        //这里给你根布局设置背景透明, 为的是让他看起来和activity的布局一样
        params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.RGBA_8888;//背景透明
        params.gravity = Gravity.LEFT | Gravity.TOP;

        //当点击根布局时, 隐藏
        view_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissCenterDialogView();
            }
        });
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //如果是显示状态那么隐藏视图
                if(keyCode == KeyEvent.KEYCODE_BACK && isShow) dismissCenterDialogView();
                return isShow;
            }
        });
    }

    public void setContentView(View view){
        contentView = view;
        linearLayout.addView(view);
    }

    public void showCenterDialogView(){
            try {
                //这个步骤是得到该view相对于屏幕的坐标, 注意不是相对于父布局哦!
                linearLayout.measure(0, 0);
                linearLayout.setX(0);
                linearLayout.setY(0);

                //这里就是使用WindowManager直接将我们处理好的view添加到屏幕最前端
                windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
                windowManager.addView(rootView, params);

                isShow=true;

                //视图被弹出来时得到焦点, 否则就捕获不到Touch事件
                rootView.setFocusable(true);
                rootView.setFocusableInTouchMode(true);
                rootView.requestFocus();
                rootView.requestFocusFromTouch();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    public void dismissCenterDialogView(){
            isShow = false;
            windowManager.removeViewImmediate(rootView);
    }

    public ViewGroup getLayout(){
        return linearLayout;
    }

    /**
     * popupwindow是否是显示状态
     */
    public boolean isShow(){
        return isShow;
    }

}

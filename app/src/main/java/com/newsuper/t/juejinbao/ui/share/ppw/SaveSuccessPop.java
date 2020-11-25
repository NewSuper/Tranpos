package com.newsuper.t.juejinbao.ui.share.ppw;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import razerdp.basepopup.BasePopupWindow;

public class SaveSuccessPop extends BasePopupWindow {

    private int recLen = 10;
    private Button button;
    OnButtonClickLintener onButtonClickLintener;
//    private ImageView imageView;
    private ImageView imgClose;
    private TextView tvDesc;


    public void setOnButtonClickLintener(OnButtonClickLintener onButtonClickLintener){
        this.onButtonClickLintener = onButtonClickLintener;
    }

    public SaveSuccessPop(Context context, String infoStri) {
        super(context);
        initView(infoStri);
    }

    private void initView(String infoString) {
        button = findViewById(R.id.confirm);
        tvDesc = findViewById(R.id.tv_desc);
//        imageView = findViewById(R.id.img_share);
        imgClose = findViewById(R.id.img_close);

        tvDesc.setText(infoString);
//        Glide.with(getContext()).load(imgUrl).into(imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onButtonClickLintener!=null){
                    onButtonClickLintener.onClick();
                }
                dismiss();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss() {
        super.onDismiss();
    }


    @Override
    public View onCreateContentView() {
        View v = createPopupById(R.layout.save_img_success);
        return v;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("basePopup", "onTouchEvent: ");
        if (event.getAction() == MotionEvent.ACTION_UP){
            dismissWithOutAnimate();
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onOutSideTouch() {
//        return super.onOutSideTouch();
        return false;  //不消失
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onDispatchKeyEvent(KeyEvent event) {
        return super.onDispatchKeyEvent(event);
    }

}


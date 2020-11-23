package com.newsuper.t.juejinbao.ui.ad;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;


public class PushAdDialog extends Dialog {
    private PushAdClickListener pushAdClickListener;
    private int type;
    private TextView tvTitle, tvContent, tvOk;
    private ImageView ivBg, ivClose;
    private String title, content, imgUrl;
    private RelativeLayout relativeLayout;
    private Context context;

    public void setClickListener(PushAdClickListener pushAdClickListener) {
        this.pushAdClickListener = pushAdClickListener;
    }

    public PushAdDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_back_alert);
        this.context = context;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - NotchScreenUtil.dp2px(context, 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;
        getWindow().setAttributes(params);
        initView();
    }

    public PushAdDialog(Context context, int type) {
        super(context);
        setContentView(R.layout.dialog_push_ad);
        this.context = context;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - NotchScreenUtil.dp2px(context, 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;
        getWindow().setAttributes(params);
        this.type = type;
        initView();
    }

    public PushAdDialog(Context context, int type, String title, String content, String imgUrl) {
        super(context);
        setContentView(R.layout.dialog_push_ad);
        this.context = context;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - NotchScreenUtil.dp2px(context, 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;
        getWindow().setAttributes(params);
        this.type = type;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        initView();
    }

    private void initView() {
        switch (type) {
            case 0:
                relativeLayout = findViewById(R.id.dialog_push_ad_one);
                relativeLayout.setVisibility(View.VISIBLE);
                tvTitle = findViewById(R.id.dialog_push_ad_one_title);
                tvContent = findViewById(R.id.dialog_push_ad_one_content);
                tvOk = findViewById(R.id.dialog_push_ad_one_look);
                ivClose = findViewById(R.id.dialog_push_ad_one_close);
                ivBg = findViewById(R.id.dialog_push_ad_one_img);
                tvContent.setText(TextUtils.isEmpty(content) ? "" : content);
                tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
                Glide.with(context).load(imgUrl).into(ivBg);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                tvOk.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(pushAdClickListener!=null){
                                    pushAdClickListener.OkClick();
                                }
                            }
                        }
                );
                break;
            case 1:
                relativeLayout = findViewById(R.id.dialog_push_ad_two);
                relativeLayout.setVisibility(View.VISIBLE);
                ivBg = findViewById(R.id.dialog_push_ad_two_img);
                ivClose = findViewById(R.id.dialog_push_ad_two_close);
                Glide.with(context).load(imgUrl).into(ivBg);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                ivBg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(pushAdClickListener!=null){
                            pushAdClickListener.OkClick();
                        }
                    }
                });

                break;
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface PushAdClickListener {
        void OkClick();

        void CancleClick();
    }

}

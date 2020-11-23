package com.newsuper.t.juejinbao.ui.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;


/**
 * 首页返回退出弹窗提示
 */
public class ActicleDetailRewordDialog01 extends Dialog {

    private CustomItemClickListener clickListener;
    boolean alDismiss;
    private String title = "阅读奖励";

    public void setClickListener(CustomItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ActicleDetailRewordDialog01(Context context,double coin,String title) {
        super(context);
        setContentView(R.layout.dialog_article_reward_01);
        this.title = title;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - NotchScreenUtil.dp2px(context, 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;


        getWindow().setAttributes(params);

        initView(coin);

    }


    //禁止返回键消失
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void show() {
        super.show();
        alDismiss = false;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        alDismiss = true;
    }

    private void initView(double coin) {
        TextView tvConfirm = findViewById(R.id.dialog_config_my_title_know);
        TextView tvCoin = findViewById(R.id.tv_coin);
        TextView tvTitle = findViewById(R.id.textView);
        tvTitle.setText(title);
        String coinString = coin + "";
        if(coinString.length()>2){
            tvCoin.setTextSize(15);
        }else {
            tvCoin.setTextSize(17);
        }
        tvCoin.setText(String.format("%s", Utils.FormatGold(coin)));
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}

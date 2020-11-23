package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.home.interf.CustomItemClickListener;

/**
 * 文章奖励指引
 */
public class ForceShareByPlayMovieDialog extends Dialog {

    private CustomItemClickListener clickListener;
    boolean alDismiss;

    public void setClickListener(CustomItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ForceShareByPlayMovieDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_force_share_by_play_movie);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        getWindow().setAttributes(params);

        initView();

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

    private void initView() {
        TextView shareBtn = findViewById(R.id.share_btn);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null){
                    clickListener.onItemClick(0,v);
                }
            }
        });
    }
}

package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.home.interf.CustomItemClickListener;
import com.juejinchain.android.module.my.entity.UserDataEntity;
import com.juejinchain.android.view.CustomLayout;
import com.ys.network.base.PagerCons;

import io.paperdb.Paper;

/**
 * 视频奖励指引
 */
public class NewTaskMovieDetailDialog extends Dialog {

    boolean alDismiss;

    public void setClickListener(CustomItemClickListener clickListener) {
    }

    public NewTaskMovieDetailDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_new_task_movie_detail);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.dimAmount =0f;
        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        CustomLayout viewBg = findViewById(R.id.view_bg);
        viewBg.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onBackPressed() {
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
}

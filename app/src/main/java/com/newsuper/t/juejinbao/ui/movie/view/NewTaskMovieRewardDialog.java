package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.view.CustomLayout;

/**
 * 视频奖励指引
 */
public class NewTaskMovieRewardDialog extends Dialog {

    boolean alDismiss;

    public void setClickListener(CustomItemClickListener clickListener) {
    }

    public NewTaskMovieRewardDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_new_task_movie_reward);
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

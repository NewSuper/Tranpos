package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;

import io.paperdb.Paper;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * 视频奖励指引
 */
public class NewTaskMovieSearchDialog extends Dialog {

    boolean alDismiss;

    public void setClickListener(CustomItemClickListener clickListener) {
    }

    public NewTaskMovieSearchDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_new_task_movie_search);
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
        FrameLayout viewBg = findViewById(R.id.view_bg);
        TextView tvSearch = findViewById(R.id.tv_search);

        UserDataEntity userDataEntity = Paper.book().read(PagerCons.PERSONAL);
        if(userDataEntity!=null && userDataEntity.getData().getNewbie_task()!=null
                && userDataEntity.getData().getNewbie_task().size()!=0){
            tvSearch.setHint(userDataEntity.getData().getNewbie_task().get(0).getMovie_title());
        }

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

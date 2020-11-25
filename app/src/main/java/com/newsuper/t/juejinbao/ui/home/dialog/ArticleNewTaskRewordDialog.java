package com.newsuper.t.juejinbao.ui.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;


/**
 * 首页返回退出弹窗提示
 */
public class ArticleNewTaskRewordDialog extends Dialog {

    boolean alDismiss;

    private CustomItemClickListener clickListener;

    public void setClickListener(CustomItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ArticleNewTaskRewordDialog(Context context, UserDataEntity.DataBean.Task task) {
        super(context);
        setContentView(R.layout.dialog_article_new_task_reward);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;
        getWindow().setAttributes(params);
        initView(task);
    }

    private void initView(UserDataEntity.DataBean.Task task) {
        TextView tvGold = findViewById(R.id.tv_gold);
        TextView tvRmb = findViewById(R.id.tv_rmb);
        TextView tvClose = findViewById(R.id.tv_close);

        tvGold.setText(String.format("%s金币", task.getValue()));
        tvRmb.setText(String.format("1掘金宝≈%s元", task.getVcoin_to_rmb()));
        tvClose.setOnClickListener(view -> {
            clickListener.onItemClick(0,null);
            dismiss();
        });

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
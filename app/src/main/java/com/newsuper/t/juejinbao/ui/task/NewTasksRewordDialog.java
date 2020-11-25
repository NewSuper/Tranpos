package com.newsuper.t.juejinbao.ui.task;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;

public class NewTasksRewordDialog extends Dialog {

    boolean alDismiss;

    private CustomItemClickListener clickListener;

    public void setClickListener(CustomItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public NewTasksRewordDialog(Context context, UserDataEntity.DataBean.Task task) {
        super(context);
        setContentView(R.layout.dialog_tasks_new_task_reward);
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
        TextView tvClose = findViewById(R.id.tv_close);

        tvGold.setText(String.format("%s金币", task.getValue()));
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
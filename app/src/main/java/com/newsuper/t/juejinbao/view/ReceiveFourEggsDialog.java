package com.newsuper.t.juejinbao.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.interf.CustomClickListener;

/**
 * 领鸡蛋弹窗
 */
public class ReceiveFourEggsDialog extends Dialog {

    private boolean alDismiss;
    private CustomClickListener clickListener;

    public void setClickListener(CustomClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ReceiveFourEggsDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_receive_four_eggs);
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
        findViewById(R.id.btn_start).setOnClickListener(v -> {
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

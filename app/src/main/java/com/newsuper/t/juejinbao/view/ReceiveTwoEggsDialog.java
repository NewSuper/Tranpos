package com.newsuper.t.juejinbao.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.interf.CustomClickListener;


/**
 * 领鸡蛋弹窗
 */
public class ReceiveTwoEggsDialog extends Dialog {

    private boolean alDismiss;
    private String content;
    private CustomClickListener clickListener;

    public void setClickListener(CustomClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ReceiveTwoEggsDialog(Context context,String content) {
        super(context);
        this.content = content;
        setContentView(R.layout.dialog_receive_two_eggs);
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
        CirclePercentView percentView = findViewById(R.id.progress);
        percentView.setPercentage(250f);

        ((TextView)findViewById(R.id.tv_tip)).setText(content);

        findViewById(R.id.view_bg).setOnClickListener(v -> {
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

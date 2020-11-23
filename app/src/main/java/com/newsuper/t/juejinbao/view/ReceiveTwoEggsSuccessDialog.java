package com.newsuper.t.juejinbao.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.interf.CustomClickListener;


/**
 * 领鸡蛋弹窗
 */
public class ReceiveTwoEggsSuccessDialog extends Dialog {

    private boolean alDismiss;
    private Context context;
    private int type;// 1:任务1 2:任务1
    private CustomClickListener clickListener;

    public void setClickListener(CustomClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ReceiveTwoEggsSuccessDialog(Context context, int type) {
        super(context);
        this.context = context;
        this.type = type;
        setContentView(R.layout.dialog_receive_two_eggs_success);
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
        TextView tvTitle = findViewById(R.id.tv_title);
        Button btnReceive = findViewById(R.id.btn_receive);
        ImageView ivClose = findViewById(R.id.iv_close);
        tvTitle.setText(type==1?"恭喜你完成赚鸡蛋任务（1/2）":"恭喜你完成赚鸡蛋任务（2/2）");
        tvTitle.setTextColor(type==1?context.getResources().getColor(R.color.c_333333):context.getResources().getColor(R.color.c_FF621D));
        findViewById(R.id.tv_desc).setVisibility(type==1?View.GONE:View.VISIBLE);
        ivClose.setVisibility(type==1?View.GONE:View.VISIBLE);
        btnReceive.setText(type==1?"再领两枚蛋":"领取新手大礼包");
        btnReceive.setOnClickListener(v -> {
            clickListener.onItemClick(1,null);
            dismiss();
        });
        ivClose.setOnClickListener(view -> {
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

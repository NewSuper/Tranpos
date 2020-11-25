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


/**
 * 字体设置指引
 */
public class GuideTextSettingDialog extends Dialog {

    private CustomItemClickListener clickListener;
    boolean alDismiss;

    public void setClickListener(CustomItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public GuideTextSettingDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_guide_text_setting);

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
//        super.onBackPressed();
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
        findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
package com.newsuper.t.juejinbao.ui.task.sleep;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.ys.network.utils.ScreenUtils;
import com.ys.network.utils.androidUtils.NotchScreenUtil;

public class ActiveRuleDialog extends Dialog implements View.OnClickListener {

    public ActiveRuleDialog(Context context) {
        super(context, 0);
        setContentView(R.layout.dialog_active_rule);

        Window window = getWindow();
        WindowManager.LayoutParams params =  window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - NotchScreenUtil.dp2px(context, 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;

        getWindow().setAttributes(params);

        initView();

    }

    private void initView() {
        TextView closeBtn = findViewById(R.id.tv_sleep_rule);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}

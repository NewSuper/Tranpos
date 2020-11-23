package com.newsuper.t.juejinbao.ui.ad;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;


public class AdTipDialog extends Dialog implements View.OnClickListener {

    public AdTipDialog(Context context) {
        super(context, 0);
        setContentView(R.layout.dialog_ad_tip);

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

        Button closeBtn = findViewById(R.id.btn_back);
        ImageView closeImg = findViewById(R.id.dialog_config_edite_code_close);

        closeBtn.setOnClickListener(this);
        closeImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}

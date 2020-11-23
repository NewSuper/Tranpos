package com.newsuper.t.juejinbao.ui.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.utils.DensityUtils;
import com.newsuper.t.juejinbao.utils.ScreenUtils;

/**
 * 压缩/上传 视频弹窗
 */
public class UploadVedioDialog extends Dialog {

    private Context context;
    private ProgressBar progressbar;
    private TextView tv_desc;
    private String mTitle;

    private Handler updateHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int progress = (int) msg.obj;
            progressbar.setProgress(progress);
            return false;
        }
    });

    public UploadVedioDialog(Context context, String title) {
        this(context, 0);
        this.context = context;
        mTitle = title;
        setCanceledOnTouchOutside(false);
    }

    public UploadVedioDialog(Context context, int themeResId) {
        super(context, themeResId);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - DensityUtils.dp2px(context, 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;
        getWindow().setAttributes(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_upload_vedio);

        initView();
    }

    private void initView() {
        tv_desc = findViewById(R.id.tv_desc);
        progressbar = findViewById(R.id.progressbar);

        tv_desc.setText(mTitle);
    }

    public void setProgressbar(int progress) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            progressbar.setProgress(progress);
        } else {
            Message msg = Message.obtain();
            msg.obj = progress;
            updateHandler.sendMessage(msg);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        updateHandler.removeCallbacksAndMessages(null);
        updateHandler = null;
    }

    @Override
    public void onBackPressed() {

    }

}

package com.newsuper.t.juejinbao.ui.movie.view;

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
import com.newsuper.t.juejinbao.ui.share.interf.OnButtonClickLintener;


public class ShareImgSaveSuccessDialog extends Dialog {

    private Button button;
    OnButtonClickLintener onButtonClickLintener;
    //    private ImageView imageView;
    private ImageView imgClose;
    private TextView tvDesc;

    public void setOnButtonClickLintener(OnButtonClickLintener onButtonClickLintener) {
        this.onButtonClickLintener = onButtonClickLintener;
    }

    public ShareImgSaveSuccessDialog(Context context, String infoStri) {
        super(context);
        setContentView(R.layout.save_img_success);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        getWindow().setAttributes(params);

        initView(infoStri);

    }

    //禁止返回键消失
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void initView(String infoStri) {
        button = findViewById(R.id.confirm);
        tvDesc = findViewById(R.id.tv_desc);
        imgClose = findViewById(R.id.img_close);

        tvDesc.setText(infoStri);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickLintener != null) {
                    onButtonClickLintener.onClick();
                }
                dismiss();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}

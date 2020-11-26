package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.PagerCons;

import static io.paperdb.Paper.book;


public class MovieP2PGuideDialog extends Dialog {

    boolean alDismiss;

    public MovieP2PGuideDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_movie_p2pguide);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        getWindow().setAttributes(params);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        initView();

    }

    //禁止返回键消失
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    @Override
    public void show() {
        try {
            super.show();
            alDismiss = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
            alDismiss = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initView() {
        ImageView confirmBtn = findViewById(R.id.iv4);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book().write(PagerCons.KEY_MOVIE_P2P_GUIDE, true);
                dismiss();
            }
        });
    }
}

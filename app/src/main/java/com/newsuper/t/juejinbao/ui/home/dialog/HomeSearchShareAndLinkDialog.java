package com.newsuper.t.juejinbao.ui.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.newsuper.t.R;

import static io.paperdb.Paper.book;


public class HomeSearchShareAndLinkDialog extends Dialog {

    private Context context;

    boolean alDismiss;

    private ItemClickListener itemClickListener;

    public HomeSearchShareAndLinkDialog(Context context , ItemClickListener itemClickListener) {
        super(context);
        this.context = context;
        this.itemClickListener = itemClickListener;
        setContentView(R.layout.dialog_homesearch_shareandlink);

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
        findViewById(R.id.rl_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                itemClickListener.share();
            }
        });

        findViewById(R.id.rl_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                itemClickListener.link();
            }
        });

        findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static interface ItemClickListener{
        public void share();
        public void link();
    }


}

package com.newsuper.t.juejinbao.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;

public class LoadingDialog {

    private volatile static LoadingDialog diaLog;

    public LoadingDialog() {
    }

    public static LoadingDialog getInstance() {
        if (diaLog == null) {
            synchronized (LoadingDialog.class) {
                if (diaLog == null) {
                    diaLog = new LoadingDialog();

                }
            }
        }
        return diaLog;
    }

//    private AnimationDrawable refreshingAnim;

    public Dialog showDiaLog(final Context context) {
        // 创建对话框构建器
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.mydialog);
//        // 获取布局
//        View view2 = View.inflate(mContext, R.layout.dialog_loading, null);
//        // 获取布局中的控件
////        final ImageView loading_imge = view2.findViewById(R.id.loading_imge);
////        loading_imge.setImageResource(R.drawable.pulltorefresh_anim);
////        refreshingAnim = (AnimationDrawable) loading_imge.getDrawable();
////        refreshingAnim.start();
////        indicator.show();
//        // 设置参数
//        builder.setView(view2);
////        LinearLayout layout_dialog = (LinearLayout) view2.findViewById(R.id.layout_dialog);
//
//        // 创建对话框
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.setCanceledOnTouchOutside(false);
//        alertDialog.setCancelable(true);
//        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
////                refreshingAnim.stop();
//            }
//        });
//
//
//        return alertDialog;

        Dialog mDialog = new Dialog(context, R.style.loadingdialog);
        View view2 = View.inflate(context, R.layout.dialog_loading, null);
        mDialog.setContentView(view2, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                refreshingAnim.stop();
            }
        });


        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wl);


        Glide.with(context).load(R.drawable.ic_top_loading).into((ImageView) view2.findViewById(R.id.iv));
        return mDialog;


    }

    public interface DataCallBack {
        void dataCall(String str);
    }
}

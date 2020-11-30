package com.newsuper.t.consumer.utils;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.newsuper.t.R;


public class DialogUtils {
    /**
     * 从屏幕底部弹出dialog，并且宽度占满整个屏幕！
     * @param context
     * @param view  dialog的布局；
     * @return   dialog；
     */
    public static Dialog BottonDialog(Context context,View view){

        Dialog dialog = new Dialog(context);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);

        dialog.setCanceledOnTouchOutside(true);

        dialog.setCancelable(true);

        Window window = dialog.getWindow();

        WindowManager.LayoutParams windowparams = window.getAttributes();

        window.setGravity(Gravity.BOTTOM);

        Rect rect = new Rect();

        View view1 = window.getDecorView();

        view1.getWindowVisibleDisplayFrame(rect);
        // windowparams.height = UIUtils.dip2px(285);

        windowparams.width =window.getWindowManager().getDefaultDisplay().getWidth();

        window.setWindowAnimations(R.style.DialogAnimation);

        window.setBackgroundDrawableResource(android.R.color.transparent);

        window.setAttributes( windowparams);

        return dialog;

    }
    /**
     * 从屏幕底部弹出dialog，并且宽度占满整个屏幕！
     * @param context
     * @param view  dialog的布局；
     * @return   dialog；
     */
    public static Dialog CenterBottomDialog(Context context,View view, int width, int height){

        Dialog dialog = new Dialog(context, R.style.BottomCenterDialogTheme);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);

        dialog.setCanceledOnTouchOutside(true);

        dialog.setCancelable(true);

        Window window = dialog.getWindow();

        WindowManager.LayoutParams windowparams = window.getAttributes();

        window.setGravity(Gravity.BOTTOM|Gravity.LEFT);

        Rect rect = new Rect();

        View view1 = window.getDecorView();

        view1.getWindowVisibleDisplayFrame(rect);
        // windowparams.height = UIUtils.dip2px(285);
        windowparams.x = UIUtils.dip2px(140);
        windowparams.y = UIUtils.dip2px(20);
        windowparams.width = UIUtils.dip2px(width);
        windowparams.height = UIUtils.dip2px(height);

        window.setWindowAnimations(R.style.DialogAnimation);

        window.setBackgroundDrawableResource(android.R.color.transparent);

        window.setAttributes( windowparams);

        return dialog;

    }


    /**
     * 他创建一个普通的居中的对话框
     * @param context
     * 				上下文
     * @param dialogView
     * 				对话框的布局；
     * @return
     */
    public static Dialog centerDialog(Context context,View dialogView){

        Builder builder = new Builder(context);

        builder.setView(dialogView);

        Dialog dialog = builder.create();

        return dialog;
    }
    /**
     * 他创建一个普通的居中的对话框
     * @param context
     * 				上下文
     * @param dialogView
     * 				对话框的布局；
     * @return
     */
    public static Dialog centerDialog2(Context context,View dialogView){

        Builder builder = new Builder(context, R.style.CenterDialogTheme);

        builder.setView(dialogView);

        Dialog dialog = builder.create();

        return dialog;
    }
    public static ProgressDialog loadDialog(Context context,String titel,String value){
        ProgressDialog dialog =new ProgressDialog(context);
        dialog.setTitle(titel);
        dialog.setMessage(value);
        return dialog;
    }

}


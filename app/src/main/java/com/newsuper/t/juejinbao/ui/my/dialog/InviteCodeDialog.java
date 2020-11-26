package com.newsuper.t.juejinbao.ui.my.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.newsuper.t.R;
import com.newsuper.t.databinding.DialogInvitecodeBinding;


public class InviteCodeDialog extends Dialog {

    private Context context;
    DialogInvitecodeBinding mViewBinding;
    private Dialog mDialog;

    public InviteCodeDialog(Context context) {
        super(context);
        this.context = context;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_invitecode, null, false);
        mDialog = new Dialog(context, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);


        mViewBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

    }

    public void show() {
        mDialog.show();
    }

    public void hide() {
        mDialog.dismiss();
    }

}

package com.newsuper.t.juejinbao.ui.home.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.view.alerter.Alerter;

import io.paperdb.Paper;

@SuppressLint("ValidFragment")
public class CommonDialog extends DialogFragment {

    private int layout = -1;
    private TextView confirmTv, cancelTv, contentTv;
    private String confirm, cancel, content;
    private int clickCount = 0;
    private long clickTime = 0;

    public CommonDialog(int layout) {
        setStyle(STYLE_NO_FRAME, R.style.common_dialog_style);
        this.layout = layout;
    }

    public CommonDialog() {
        setStyle(STYLE_NO_FRAME, R.style.common_dialog_style);
    }
    public CommonDialog(int layout,int style) {
        setStyle(STYLE_NO_FRAME, style);
        this.layout = layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(layout == -1 ? R.layout.dialog_common : layout, container, false);
        confirmTv = mView.findViewById(R.id.confirm);
        cancelTv = mView.findViewById(R.id.cancel);
        contentTv = mView.findViewById(R.id.content);

        if (this.cancel != null){
            cancelTv.setText(cancel);
        }
        if (this.confirm != null){
            confirmTv.setText(confirm);
        }
        if (this.content != null){
            contentTv.setText(content);
        }
        if(confirmTv != null) {
            confirmTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alerter.hide();
                    if(mOnCertainButtonClickListener != null){
                        mOnCertainButtonClickListener.onCertainButtonClick();
                    }
                }
            });
        }
        if(cancelTv != null) {
            cancelTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alerter.hide();
                    if (mOnCancelClickListener != null){
                        mOnCancelClickListener.onCancelClick();
                    }
                }
            });
        }
        if(contentTv!=null){
            contentTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickTime == 0) {
                        clickTime = System.currentTimeMillis();
                    }
                    if (System.currentTimeMillis() - clickTime > 500) {
                        clickCount = 0;
                    } else {
                        clickCount++;
                    }
                    clickTime = System.currentTimeMillis();
                    if (clickCount > 7) {
                        ToastUtils.getInstance().show(getContext(),"app防护已关闭");
                        Paper.book().write(PagerCons.KEY_IS_OPEN_APP_PROTECT,0);

                        int isOpenAppProtect = Paper.book().read(PagerCons.KEY_IS_OPEN_APP_PROTECT, 0);
                        Log.i("zzz", "checkLimit: " + isOpenAppProtect);
                    }

                }
            });
        }
        return mView;
    }

    public interface OnCertainButtonClickListener {
        void onCertainButtonClick();
    }
    public interface onCancelClickListener{
        void onCancelClick();
    }
    private OnCertainButtonClickListener mOnCertainButtonClickListener;
    private onCancelClickListener mOnCancelClickListener;

    public CommonDialog setOnCancelClickListener(onCancelClickListener listener){
        mOnCancelClickListener = listener;
        return this;
    }
    public CommonDialog setOnCertainButtonClickListener(OnCertainButtonClickListener listener) {
        mOnCertainButtonClickListener = listener;
        return  this;
    }

    public CommonDialog setConfirm(String confirm) {
        this.confirm = confirm;
        return this;
    }

    public CommonDialog setCancel(String cancel) {
        this.cancel = cancel;
        return this;
    }

    public CommonDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public CommonDialog setDailogCancelable(boolean isCancel){
        this.setCancelable(isCancel);
        return this;
    }
}

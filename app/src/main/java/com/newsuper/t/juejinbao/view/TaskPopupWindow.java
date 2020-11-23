package com.newsuper.t.juejinbao.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newsuper.t.R;


public class TaskPopupWindow extends PopupWindow {
    private Context mContext;

    private View mContentView;
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public TaskPopupWindow(Context context,String msg) {
        super(context);
        mContext = context;

        initView(msg);
    }

    private void initView(String msg){
//        setFocusable(false);
//        setOutsideTouchable(false);
//        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable());

        mContentView = View.inflate(mContext, R.layout.ling_task, null);
        TextView textView = mContentView.findViewById(R.id.tv);
        textView.setText(msg);
        setContentView(mContentView);

        mContentView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        if(onClickListener != null) onClickListener.onClick(mContentView);
                    }
                }
        );

    }


}

package com.newsuper.t.juejinbao.ui.share.ppw;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.basepop.BasePopupWindow;
import com.newsuper.t.juejinbao.ui.share.interf.OnButtonClickLintener;
import com.newsuper.t.juejinbao.utils.SPUtils;

import java.util.Timer;
import java.util.TimerTask;



public class GuidePop extends BasePopupWindow {

    private int recLen = 10;
    private Button button;
    OnButtonClickLintener onButtonClickLintener;

    Timer timer = new Timer();

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            getContext().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    button.setText("我学会了" + "(" + recLen + "s" + ")");
                    if (recLen < 0) {
                        getContext().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timer.cancel();
                                button.setText("我学会了");
                                button.setEnabled(true);
                            }
                        });

                    }
                }
            });

        }
    };


    public void setOnButtonClickLintener(OnButtonClickLintener onButtonClickLintener) {
        this.onButtonClickLintener = onButtonClickLintener;
    }

    public GuidePop(Context context, boolean isClose, int entryType) {
        super(context);
        initView(isClose, entryType);
    }

    /**
     * @param isFirst   是否首次
     * @param entryType 进入类型 0 查看  1 跳转
     */
    private void initView(boolean isFirst, int entryType) {
        SPUtils.getInstance().put(PagerCons.KEY_IS_FIRST_SHARE, false);
        button = findViewById(R.id.confirm);

        if (entryType == 0) {
            //查看
            button.setEnabled(true);
            button.setText("我学会了");
        } else {
            //跳转
            if (isFirst) {
                //是否首次进入
                button.setEnabled(false);
                timer.schedule(timerTask, 1000, 1000);       // timeTask
            } else {
                button.setEnabled(true);

                button.setText("我学会了");
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onButtonClickLintener != null && entryType == 1) {
                    onButtonClickLintener.onClick();
                }
            }
        });
    }

    @Override
    public void onDismiss() {
        super.onDismiss();
        timer.cancel(); //清空资源 防止内存泄漏
    }


    @Override
    public View onCreateContentView() {
        View v = createPopupById(R.layout.ppw_guide);
        return v;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("basePopup", "onTouchEvent: ");
        if (event.getAction() == MotionEvent.ACTION_UP) {
            dismissWithOutAnimate();
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onOutSideTouch() {
//        return super.onOutSideTouch();
        return true;  //不消失
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onDispatchKeyEvent(KeyEvent event) {
        return super.onDispatchKeyEvent(event);
    }

}


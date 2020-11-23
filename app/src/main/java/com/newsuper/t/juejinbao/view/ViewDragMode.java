package com.newsuper.t.juejinbao.view;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;


public class ViewDragMode implements View.OnTouchListener{
    private View targetView;
    public ViewDragMode(final View targetView) {
        this.targetView=targetView;
        this.targetView.setOnTouchListener(this);
    }

    private int parentHeight;
    private int parentWidth;
    private int lastX;
    private int lastY;
    private boolean isDrag;
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();

        Log.i("zzz", "onTouch: ---" + "x:" +  rawX  + "----"+ "Y：" +rawY );
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                targetView.setPressed(true);
                isDrag = false;
                ViewParent viewParent = targetView.getParent();
                viewParent.requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                ViewGroup parent;
                parent = (ViewGroup) viewParent;
                parentHeight = parent.getHeight();
                parentWidth = parent.getWidth();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("zzz", "parentHeight: ---" + "x:" +  parentHeight  + "----"+ "parentWidth：" +parentWidth );
                if (parentHeight <= 0 || parentWidth == 0) {
                    isDrag = false;
                    break;
                } else {
                    isDrag = true;
                }
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                //这里修复一些华为手机无法触发点击事件
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (distance == 0) {
                    isDrag = false;
                    break;
                }
                float x = targetView.getX() + dx;
                float y = targetView.getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > parentWidth - targetView.getWidth() ? parentWidth - targetView.getWidth() : x;
                y = targetView.getY() < 0 ? 0 : targetView.getY() + targetView.getHeight() > parentHeight ? parentHeight - targetView.getHeight() : y;
                targetView.setX(x);
                targetView.setY(y);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isNotDrag()) {
                    //恢复按压效果
                    targetView.setPressed(false);
                    if (rawX >= parentWidth / 2) {
                        //靠右吸附
                        targetView.animate().setInterpolator(new DecelerateInterpolator())
                                .setDuration(500)
                                .xBy(parentWidth - targetView.getWidth() - targetView.getX())
                                .start();
                    } else {
                        //靠左吸附
                        ObjectAnimator oa = ObjectAnimator.ofFloat(targetView, "x", targetView.getX(), 0);
                        oa.setInterpolator(new DecelerateInterpolator());
                        oa.setDuration(500);
                        oa.start();
                    }
                }
                break;
        }
        //如果是拖拽则消s耗事件，否则正常传递即可。
        return !isNotDrag() || targetView.onTouchEvent(event);
    }

    /**
     * 是否拖拽中
     * @return
     */
    public boolean isDraging(){
        return isDrag;
    }
    private boolean isNotDrag() {
        if(targetView ==null)return true;
        return !isDrag ;
    }
}


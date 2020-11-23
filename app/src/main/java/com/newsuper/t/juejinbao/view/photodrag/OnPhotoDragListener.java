package com.newsuper.t.juejinbao.view.photodrag;

import android.view.View;


public interface OnPhotoDragListener {
    /**
     * @return 动画是否在运行
     */
    boolean isAnimationRunning();

    /**
     * 拖动的Y坐标
     *
     * @param dy
     */
    void onDrag(float dy);

    /**
     * 释放
     */
    void onRelease();

    /**
     * 拽动view
     *
     * @return
     */
    View getDragView();

}

package com.newsuper.t.consumer.function.inter;


import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface ISelectQuitReason extends IBaseView {
    void quitReason(int reasonPos);
}

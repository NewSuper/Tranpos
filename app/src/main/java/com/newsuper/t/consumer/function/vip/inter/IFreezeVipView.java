package com.newsuper.t.consumer.function.vip.inter;


import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IFreezeVipView extends IBaseView {
    void freezeFail();
    void freezeSuccess();
}

package com.newsuper.t.consumer.function.selectgoods.inter;


import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Create by Administrator on 2019/10/29 0029
 */
public interface ICheckGoodsMustView extends IBaseView {
    void showSuccessVIew(String shop_id);
    void showCheckOrderView(String msg);
}

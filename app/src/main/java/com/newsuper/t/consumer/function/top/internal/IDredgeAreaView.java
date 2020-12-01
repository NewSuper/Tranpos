package com.newsuper.t.consumer.function.top.internal;


import com.newsuper.t.consumer.bean.DredgeAreaBean;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public interface IDredgeAreaView extends IBaseView {
    void showLoadData(DredgeAreaBean bean);
    void loadFail();
}

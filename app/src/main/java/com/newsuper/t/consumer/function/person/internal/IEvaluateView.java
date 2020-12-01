package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.EvaluateBean;
import com.newsuper.t.consumer.bean.TraceBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public interface IEvaluateView extends IBaseView {
    void showDataToView(EvaluateBean bean);
    void loadFail();
    void deleteEvalSucc();
}

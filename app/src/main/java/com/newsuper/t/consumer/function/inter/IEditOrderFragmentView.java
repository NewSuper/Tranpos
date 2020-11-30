package com.newsuper.t.consumer.function.inter;


import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IEditOrderFragmentView extends IBaseView {

    void notifyOrderList(String flag, int position);

}

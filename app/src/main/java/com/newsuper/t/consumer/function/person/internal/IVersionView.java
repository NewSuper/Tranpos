package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.VersionBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;


public interface IVersionView extends IBaseView {
    void showVersionView(VersionBean bean);
   // void loadVersion(VersionBean bean);
}

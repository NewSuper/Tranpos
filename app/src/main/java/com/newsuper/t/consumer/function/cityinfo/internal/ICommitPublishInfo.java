package com.newsuper.t.consumer.function.cityinfo.internal;


import com.newsuper.t.consumer.bean.PublishResultBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface ICommitPublishInfo extends IBaseView {
    void publishData(PublishResultBean bean);
}

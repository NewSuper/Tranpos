package com.newsuper.t.consumer.function.cityinfo.internal;


import com.newsuper.t.consumer.bean.PublishCollectBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IPublishCollectView extends IBaseView {
    void getCollectData(PublishCollectBean bean);
    void getMoreCollectData(PublishCollectBean bean);
    void getCollectFail();
}

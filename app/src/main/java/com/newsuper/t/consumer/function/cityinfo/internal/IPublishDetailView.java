package com.newsuper.t.consumer.function.cityinfo.internal;


import com.newsuper.t.consumer.bean.PublishDetailBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IPublishDetailView extends IBaseView {
    void getPublishData(PublishDetailBean bean);
    void getPublishDataFail();
    void onReportSuccess();
    void onReportFail();
    void onCollectSuccess();
    void onCollectFail();
}

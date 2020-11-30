package com.newsuper.t.consumer.function.cityinfo.internal;


import com.newsuper.t.consumer.bean.DefaultPublishInfo;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IGetDefaultInfo extends IBaseView {
    void getPublishData(DefaultPublishInfo bean);
}

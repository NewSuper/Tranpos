package com.newsuper.t.consumer.function.cityinfo.internal;


import com.newsuper.t.consumer.bean.PublishSearchBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IPublishSearchView extends IBaseView {
    void getSearchData(PublishSearchBean bean);
    void getMoreSearchData(PublishSearchBean bean);
    void searchFail();
}

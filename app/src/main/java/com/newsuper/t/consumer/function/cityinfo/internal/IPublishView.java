package com.newsuper.t.consumer.function.cityinfo.internal;

import com.newsuper.t.consumer.bean.CategoryBean;
import com.newsuper.t.consumer.bean.PublishAreabean;
import com.newsuper.t.consumer.bean.PublishListBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2018/6/19 0019.
 */

public interface IPublishView extends IBaseView {
    void getSecondCategory(CategoryBean bean);
    void getSecondCategoryImg(CategoryBean bean);
    void getSecondCategoryFail();
    void getPublishList(PublishListBean bean);
    void getMorePublishList(PublishListBean bean);
    void getPublishfail();
    void getPublishArea(PublishAreabean bean);
    void getPublishAreaFail();
    void onCollectFail();
    void onCollectSuccess();
    void onRepotFail();
    void onRepotSuccess();
    void onDeleteFail();
    void onDeleteSuccess();
}

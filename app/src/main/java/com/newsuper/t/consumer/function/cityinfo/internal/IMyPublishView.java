package com.newsuper.t.consumer.function.cityinfo.internal;

import com.newsuper.t.consumer.bean.MyPublishListBean;
import com.newsuper.t.consumer.bean.SetTopInfoBean;
import com.newsuper.t.consumer.bean.SetTopPayBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IMyPublishView extends IBaseView {
    void getPublishList(MyPublishListBean bean);
    void getMorePublishList(MyPublishListBean bean);
    void getPublishFail();
    void onDeleteSuccess();
    void onDeleteFail();
    void onEditSuccess();
    void onEditFail();
    void onSetTopSuccess(SetTopPayBean payBean);
    void onSetTopFail();
    void getTopInfoSuccess(SetTopInfoBean bean);
    void getTopInfoFail();
}

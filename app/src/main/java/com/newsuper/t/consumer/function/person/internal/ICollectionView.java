package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.CollectionBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

public interface ICollectionView extends IBaseView{
    void showDataToVIew(CollectionBean bean);
    void loadFail();
}

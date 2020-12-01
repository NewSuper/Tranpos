package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.CollectionBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface ICollectionView extends IBaseView{
    void showDataToVIew(CollectionBean bean);
    void loadFail();
}

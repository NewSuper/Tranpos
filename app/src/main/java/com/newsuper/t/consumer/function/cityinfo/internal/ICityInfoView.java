package com.newsuper.t.consumer.function.cityinfo.internal;


import com.newsuper.t.consumer.bean.CategoryBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface ICityInfoView extends IBaseView {
    void getSecondCategoryImg(CategoryBean bean);
    void getSecondCategoryImgFail();
}

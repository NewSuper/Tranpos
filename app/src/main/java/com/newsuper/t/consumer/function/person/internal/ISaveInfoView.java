package com.newsuper.t.consumer.function.person.internal;


import com.xunjoy.lewaimai.consumer.bean.UpdateImgBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

public interface ISaveInfoView extends IBaseView{
    void showSaveInfo(UpdateImgBean bean);
    void updateFail();
}

package com.newsuper.t.consumer.function.person.internal;


import com.newsuper.t.consumer.bean.UpdateImgBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface ISaveInfoView extends IBaseView{
    void showSaveInfo(UpdateImgBean bean);
    void updateFail();
}

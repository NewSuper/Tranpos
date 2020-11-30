package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.PersonCenterBean;

public interface PersonSelectListener {
    void onSelected(int position, String title, PersonCenterBean.PersonCenterCustom centerCustom);
}

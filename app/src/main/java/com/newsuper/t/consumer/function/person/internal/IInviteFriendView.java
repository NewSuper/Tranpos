package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.InviteFriendBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

public interface IInviteFriendView extends IBaseView {

    void loadInviteData(InviteFriendBean bean);
    void loadInviteFail();
}

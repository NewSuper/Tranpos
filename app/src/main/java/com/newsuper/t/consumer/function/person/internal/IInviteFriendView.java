package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.InviteFriendBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IInviteFriendView extends IBaseView {

    void loadInviteData(InviteFriendBean bean);
    void loadInviteFail();
}

package com.newsuper.t.juejinbao.ui.login.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;

public interface DefaultInvitePresenter {
    void getDefaultInviteCode(Map<String, String> StringMap, Activity activity);

    interface View {
        void onDefaultInviteCodeSuccess(Serializable serializable);
        void onError(String s);
    }
}

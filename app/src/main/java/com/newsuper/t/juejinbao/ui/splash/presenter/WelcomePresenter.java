package com.newsuper.t.juejinbao.ui.splash.presenter;

import android.app.Activity;

import java.io.Serializable;

public interface WelcomePresenter {
    void getAuditData(Activity activity);

    interface View {
        void onAuditDataBack(Serializable serializable);

        void onerror(String errResponse);
    }
}

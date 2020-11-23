package com.newsuper.t.juejinbao.ui.my.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface PrivacyPresenter {

    void settingPrivacy(Map<String, String> StringMap, Activity activity);

    void getUserInfo(Activity activity);

    void setAllSwitch(Activity activity);

    interface View{

        void settingPrivacySuccess(Serializable serializable);

        void getUserInfoSuccess(Serializable serializable);

        void setAllSwitchBack(Serializable serializable);

        void showError(String s);
    }
}

package com.newsuper.t.juejinbao.ui.my.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.bean.BaseEntity;

import java.io.Serializable;
import java.util.Map;


public interface UserInfoPresenter {

    void getUserProfile(Map<String, String> StringMap, Activity activity);

    interface View {

        void getUserProfileSuccess(Serializable serializable);
        void uploadImage(BaseEntity baseEntity);
        void showError(String s);
    }
}

package com.transpos.sale.ui.navi.mvp;

import android.content.Context;

import com.trans.network.callback.JsonObjectCallback;
import com.transpos.sale.base.mvp.IBaseModel;
import com.transpos.sale.base.mvp.IBaseView;
import com.transpos.sale.callback.WxFaceCallback;

import java.util.Map;

public interface FaceContract {

    interface Model extends IBaseModel{
       void getAuthInfo(Context context, WxFaceCallback callback);
       void queryMemberInfo(String openid, JsonObjectCallback callback);
    }

    interface View extends IBaseView{
        void onFaceAuthSuccess(Map info);
        void onQueryMemberSuccess();
    }


    interface Presenter{
        void getFaceInfo();
        void queryMemberInfo(Map info);
    }
}

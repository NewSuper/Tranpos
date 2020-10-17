package com.transpos.sale.callback;

import java.util.Map;

public interface WxFaceCallback {
    void onAuthInfoSuccess(Map info);
    void onAuthInfoError();
}

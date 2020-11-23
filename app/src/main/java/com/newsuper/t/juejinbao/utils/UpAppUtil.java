package com.newsuper.t.juejinbao.utils;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.home.dialog.UpAppDialog;
import com.newsuper.t.juejinbao.ui.home.entity.UpAppEntity;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

public class UpAppUtil {
    /**
     * 检查更新
     */
    public static void checkVersion(Activity context, String urlHeader) {
        Map<String, String> map = new HashMap<>();
        map.put("vsn", Utils.getVersion());

        rx.Observable<UpAppEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getUpApp(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<UpAppEntity>()));
        RetrofitManager.getInstance(context).toSubscribe(observable, new Subscriber<UpAppEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UpAppEntity upAppEntity) {
                if (upAppEntity.getCode() == 0) {

                    if (isNewVersion(upAppEntity.getVsn())) {
                        /**
                         * appstore =
                         * 1\省流量下载用浏览器打开 res.data.domain + /mobile/download.php
                         * 0 直接下载 apk包
                         */
                        upAppEntity.setUrlHeader(urlHeader);
                        new UpAppDialog(context, upAppEntity).show();
                        if(context instanceof JunjinBaoMainActivity){
                            ((JunjinBaoMainActivity) context).isCheckUpdate = false;
                        }

                    }

                }
            }
        });


    }

    /**
     * 是否为最新版本
     *
     * @param serviceVsn
     * @return
     */
    public static boolean isNewVersion(String serviceVsn) {

        int service = Integer.valueOf(serviceVsn.replace(".", ""));
        int currVsn = Integer.valueOf(Utils.getVersion().replace(".", ""));
        Log.i("zzz", "isNewVersion: " + "service" + service + ";" + "currVsn" + currVsn);
        return service > currVsn;
    }

}

package com.newsuper.t.consumer.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import net.grandcentrix.tray.TrayPreferences;
import net.grandcentrix.tray.core.TrayStorage;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class MyTraySharePrefrence extends TrayPreferences {
    public MyTraySharePrefrence(Context context){
        super(context, "MyTraySharePrefrence", 1);
    }
    public MyTraySharePrefrence(@NonNull Context context, @NonNull String module, int version, TrayStorage.Type type) {
        super(context, module, version, type);
    }
}

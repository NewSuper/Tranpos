package com.newsuper.t.markert.service;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class ScreenOffAdminReceiver extends DeviceAdminReceiver{
    @Override
    public void onEnabled(Context context, Intent intent) {
       Toast.makeText(context, "设备管理器使用",Toast.LENGTH_SHORT);
    }
    @Override
    public void onDisabled(Context context, Intent intent) {
      //  Logger.d("设备管理器没有使用");
        Toast.makeText(context, "设备管理器没有使用",Toast.LENGTH_SHORT);
    }
}

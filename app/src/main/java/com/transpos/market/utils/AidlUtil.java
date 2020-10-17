package com.transpos.market.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;

//import woyou.aidlservice.jiuiv5.ICallback;
//import woyou.aidlservice.jiuiv5.ILcdCallback;
//import woyou.aidlservice.jiuiv5.IWoyouService;

public class AidlUtil {
//    private static final String SERVICE＿PACKAGE = "woyou.aidlservice.jiuiv5";
//    private static final String SERVICE＿ACTION = "woyou.aidlservice.jiuiv5.IWoyouService";
//
//    private IWoyouService woyouService;
//    private static AidlUtil mAidlUtil = new AidlUtil();
//
//    private Context context;
//
//    private AidlUtil() {
//    }
//
//    public static AidlUtil getInstance() {
//        return mAidlUtil;
//    }
//
//    /**
//     * 连接服务
//     *
//     * @param context context
//     */
//    public void connectPrinterService(Context context) {
//        this.context = context.getApplicationContext();
//        Intent intent = new Intent();
//        intent.setPackage(SERVICE＿PACKAGE);
//        intent.setAction(SERVICE＿ACTION);
//        context.getApplicationContext().startService(intent);
//        context.getApplicationContext().bindService(intent, connService, Context.BIND_AUTO_CREATE);
//    }
//
//    /**
//     * 断开服务
//     *
//     * @param context context
//     */
//    public void disconnectPrinterService(Context context) {
//        if (woyouService != null) {
//            context.getApplicationContext().unbindService(connService);
//            woyouService = null;
//        }
//    }
//
//    public boolean isConnect() {
//        return woyouService != null;
//    }
//
//    private ServiceConnection connService = new ServiceConnection() {
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            woyouService = null;
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            woyouService = IWoyouService.Stub.asInterface(service);
//        }
//    };
//
//    public void OpenCash(){
//        try {
//            woyouService.openDrawer(new ICallback() {
//                @Override
//                public void onRunResult(boolean isSuccess, int code, String msg) throws RemoteException {
//
//                }
//
//                @Override
//                public IBinder asBinder() {
//                    return null;
//                }
//            });
//        }catch (Exception e){
//
//        }
//    }
//    public void ShowLCDString(String str){
//        try {
//            woyouService.sendLCDString(str, new ILcdCallback() {
//                @Override
//                public void onRunResult(boolean show) throws RemoteException {
//
//                }
//
//                @Override
//                public IBinder asBinder() {
//                    return null;
//                }
//            });
//        } catch (RemoteException e) {
//
//        }
//    }
//    public void ShowLCDMAp(Bitmap bitmap){
//        try {
//            woyouService.sendLCDBitmap(bitmap, new ILcdCallback() {
//                @Override
//                public void onRunResult(boolean show) throws RemoteException {
//
//                }
//
//                @Override
//                public IBinder asBinder() {
//                    return null;
//                }
//            });
//        } catch (RemoteException e) {
//
//        }
//    }
//    public void InitLcd(){
//        try {
//            woyouService.sendLCDCommand(1);
//        } catch (RemoteException e) {
//
//        }
//    }
//    public void SleepLcd(){
//        try {
//            woyouService.sendLCDCommand(3);
//        } catch (RemoteException e) {
//
//        }
//    }
//    public void WakeUpLcd(){
//        try {
//            woyouService.sendLCDCommand(2);
//        } catch (RemoteException e) {
//
//        }
//    }
//    public void ClearLcd(){
//        try {
//            woyouService.sendLCDCommand(4);
//        } catch (RemoteException e) {
//
//        }
//    }
}


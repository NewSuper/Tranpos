
package com.newsuper.t.juejinbao.ui.upnp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.fourthline.cling.controlpoint.ControlPoint;

public class SystemService extends Service {
    private static final String TAG = SystemService.class.getSimpleName();

    private Binder binder = new LocalBinder();
    private ClingDevice mSelectedDevice;
    private int mDeviceVolume;
    private AVTransportSubscriptionCallback mAVTransportSubscriptionCallback;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        //End all subscriptions
        if (mAVTransportSubscriptionCallback != null)mAVTransportSubscriptionCallback.run();
            mAVTransportSubscriptionCallback.end();

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        public SystemService getService() {
            return SystemService.this;
        }
    }

    public IDevice getSelectedDevice() {
        return mSelectedDevice;
    }

    public void setSelectedDevice(IDevice selectedDevice, ControlPoint controlPoint) {
        if (selectedDevice == mSelectedDevice) return;

        Log.i(TAG, "Change selected device.");
        mSelectedDevice = (ClingDevice) selectedDevice;
        //End last device's subscriptions
        if (mAVTransportSubscriptionCallback != null) {
            mAVTransportSubscriptionCallback.end();
        }
        //Init Subscriptions
        mAVTransportSubscriptionCallback = new AVTransportSubscriptionCallback(
                mSelectedDevice.getDevice().findService(ClingManager.AV_TRANSPORT_SERVICE), this);
        controlPoint.execute(mAVTransportSubscriptionCallback);

        Intent intent = new Intent(Intents.ACTION_CHANGE_DEVICE);
        sendBroadcast(intent);
    }

    public void subscribeMediaRender(ControlPoint controlPoint){
        //Init Subscriptions
//        mAVTransportSubscriptionCallback = new AVTransportSubscriptionCallback(
//                mSelectedDevice.getDevice().findService(ClingUpnpServiceManager.AV_TRANSPORT_SERVICE), this);
//        controlPoint.execute(mAVTransportSubscriptionCallback);
    }

    public int getDeviceVolume() {
        return mDeviceVolume;
    }

    public void setDeviceVolume(int currentVolume) {
        mDeviceVolume = currentVolume;
    }

}

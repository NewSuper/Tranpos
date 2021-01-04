
package com.newsuper.t.consumer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.newsuper.t.consumer.function.top.presenter.LocationPresenter;
import com.newsuper.t.consumer.utils.LogUtil;

public class LocationService extends Service implements AMapLocationListener{
    private  LocationPresenter locationPresenter;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocationBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        locationPresenter = null;
        LogUtil.log("loadData","Service onDestroy ");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }
    public void doLocation(){
        LogUtil.log("loadData","Service doLocation ");
        if (locationPresenter == null){
            LogUtil.log("loadData","init doLocation ");
            locationPresenter = new LocationPresenter(getApplicationContext(),this);
        }
        locationPresenter.doLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        LogUtil.log("loadData","Service onLocationChanged ");
        if (latLngCallback != null){
            latLngCallback.getAMapLocation(aMapLocation);
        }
    }

    public class LocationBinder extends Binder {
        public LocationService getService(){
            LogUtil.log("loadData", "getService    success");
            return LocationService.this;
        }
    }
    private LatLngCallback latLngCallback;

    public void setLatLngCallback(LatLngCallback latLngCallback) {
        this.latLngCallback = latLngCallback;
    }

    public interface LatLngCallback{
        void getAMapLocation(AMapLocation aMapLocation);
    }
}

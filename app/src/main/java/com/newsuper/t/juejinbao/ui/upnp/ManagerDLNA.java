package com.newsuper.t.juejinbao.ui.upnp;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerDLNA {
    private AndroidUpnpService mUpnpService = null;
    private MediaServer mediaServer = null;
    private Context mContext;
    private List<DeviceInfo> listServiceInfo = null;
    private boolean isServerPrepared = false;
    private List<AudioInfo> listAudios = null;
    private RegistryListener registryListener = null;
    private ServiceConnection mServiceConnection = null;
    private ControlPoint mControlPoint = null;

    public ManagerDLNA(Context context, BrowseRegistryListener listener){
        mContext = context;
        listServiceInfo = new ArrayList();
        listAudios = new ArrayList();
        registryListener = listener;
    }

    public ControlPoint getControlPoint(){
        return mControlPoint;
    }

    public void setUpnpService(AndroidUpnpService upnpService){
        mUpnpService = upnpService;
    }

    public MediaServer getMediaServer() {
        return this.mediaServer;
    }

    public void setMediaServer(MediaServer mediaServer) {
        this.mediaServer = mediaServer;
    }

    public ServiceConnection getServiceConnection(){
        return mServiceConnection;
    }

    public void initConnection(){
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                setUpnpService((AndroidUpnpService) service);
                if(mediaServer ==null){
                    try {
                        mediaServer = new MediaServer(mContext);
                        mUpnpService.getRegistry().addDevice(mediaServer.getDevice());
                        addServiceDevices(mediaServer.getDevice());
                        mUpnpService.getRegistry().addListener(registryListener);
                        mControlPoint = mUpnpService.getControlPoint();
                        mUpnpService.getControlPoint().search();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mUpnpService = null;
                setServerPrepared(false);


            }
        };
    }




    public void addServiceDevices(Device device) {
        DeviceInfo info = new DeviceInfo();
        info.setName(this.getDeviceName(device));
        info.setDevice(device);
        this.listServiceInfo.add(info);
    }

    public String getDeviceName(Device device) {
        String name = "";
        if(device.getDetails() != null && device.getDetails().getFriendlyName() != null) {
            name = device.getDetails().getFriendlyName();
        } else {
            name = device.getDisplayString();
        }

        return name;
    }

    public boolean isServerPrepared() {
        return this.isServerPrepared;
    }

    public void setServerPrepared(boolean isServerPrepared) {
        this.isServerPrepared = isServerPrepared;
    }

    public class BrowseRegistryListener extends DefaultRegistryListener {
        @Override
        public void deviceAdded(Registry registry, final Device device) {
            super.deviceAdded(registry, device);
        }

        @Override
        public void deviceRemoved(Registry registry, final Device device) {
            super.deviceRemoved(registry, device);
        }
    }
}

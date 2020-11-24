package com.newsuper.t.juejinbao.ui.upnp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.newsuper.t.juejinbao.ui.upnp.control.ClingPlayControl;
import com.newsuper.t.juejinbao.ui.upnp.control.callback.ControlCallback;
import com.newsuper.t.juejinbao.ui.upnp.entity.ClingDeviceList;
import com.newsuper.t.juejinbao.ui.upnp.entity.DLANPlayState;
import com.newsuper.t.juejinbao.ui.upnp.entity.IDevice;
import com.newsuper.t.juejinbao.ui.upnp.entity.IResponse;
import com.newsuper.t.juejinbao.ui.upnp.listener.BrowseRegistryListener;
import com.newsuper.t.juejinbao.ui.upnp.listener.DeviceListChangedListener;
import com.newsuper.t.juejinbao.ui.upnp.service.ClingUpnpService;
import com.newsuper.t.juejinbao.ui.upnp.service.manager.ClingManager;
import com.newsuper.t.juejinbao.ui.upnp.service.manager.DeviceManager;

public class UpnpManager {
    private Activity activity;


    private BroadcastReceiver mTransportStateBroadcastReceiver;
    /**
     * 投屏控制器
     */
    private ClingPlayControl mClingPlayControl;

    /**
     * 用于监听发现设备
     */
    private BrowseRegistryListener mBrowseRegistryListener;

    private ServiceConnection mUpnpServiceConnection;

    //设备选择监听
    private AddDeviceListener addDeviceListener;


    public UpnpManager (Activity activity , AddDeviceListener addDeviceListener){
        this.activity = activity;
        this.addDeviceListener = addDeviceListener;
        mClingPlayControl = new ClingPlayControl();

        // 设置发现设备监听
        mBrowseRegistryListener = new BrowseRegistryListener();
//        mBrowseRegistryListener.setOnDeviceListChangedListener(new DeviceListChangedListener() {
//            @Override
//            public void onDeviceAdded(final IDevice device) {
//                activity.runOnUiThread(new Runnable() {
//                    public void run() {
//                        Log.e("zy" , "发现设备" + device.getDevice().toString());
//
//                        addDeviceListener.addDevice(device);
//
////                        play();
//                    }
//                });
//            }
//
//            @Override
//            public void onDeviceRemoved(final IDevice device) {
//                activity.runOnUiThread(new Runnable() {
//                    public void run() {
//
//                    }
//                });
//            }
//        });

        //service连接回调
        mUpnpServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {


                ClingUpnpService.LocalBinder binder = (ClingUpnpService.LocalBinder) service;
                ClingUpnpService beyondUpnpService = binder.getService();

//                ClingManager clingUpnpServiceManager = ClingManager.getInstance();
//                clingUpnpServiceManager.setUpnpService(beyondUpnpService);
//                clingUpnpServiceManager.setDeviceManager(new DeviceManager());


                //张野:添加服务设备
//                MediaServer mediaServer = null;
//                try {
//                    mediaServer = new MediaServer(activity);
//                    clingUpnpServiceManager.getRegistry().addDevice(mediaServer.getDevice());
//                } catch (ValidationException e) {
//                    e.printStackTrace();
//                }

//                clingUpnpServiceManager.getRegistry().addListener(mBrowseRegistryListener);
//                //Search on service created.
//                clingUpnpServiceManager.searchDevices();


            }

            @Override
            public void onServiceDisconnected(ComponentName className) {

                ClingManager.getInstance().setUpnpService(null);
            }
        };

        bindServices();
        registerReceivers();
//        play();
    }

    //选择设备
    public void selectDeviceAndPlay(final IDevice device){
    //    ClingManager.getInstance().setSelectedDevice(device);
//        play(url);
    }


    private void bindServices() {
        // Bind UPnP service
        Intent upnpServiceIntent = new Intent(activity, ClingUpnpService.class);
        activity.bindService(upnpServiceIntent, mUpnpServiceConnection, Context.BIND_AUTO_CREATE);
        // Bind System service
        //        Intent systemServiceIntent = new Intent(MainActivity.this, SystemService.class);
        //        bindService(systemServiceIntent, mSystemServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void registerReceivers() {
        //Register play status broadcast
        mTransportStateBroadcastReceiver = new TransportStateBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intents.ACTION_PLAYING);
        filter.addAction(Intents.ACTION_PAUSED_PLAYBACK);
        filter.addAction(Intents.ACTION_STOPPED);
        filter.addAction(Intents.ACTION_TRANSITIONING);
        activity.registerReceiver(mTransportStateBroadcastReceiver, filter);
    }


    /**
     * 接收状态改变信息
     */
    private class TransportStateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intents.ACTION_PLAYING.equals(action)) {

            } else if (Intents.ACTION_PAUSED_PLAYBACK.equals(action)) {

            } else if (Intents.ACTION_STOPPED.equals(action)) {

            } else if (Intents.ACTION_TRANSITIONING.equals(action)) {
            }
        }
    }


    /**
     * 播放视频
     */
    public void play(String url) {
        @DLANPlayState.DLANPlayStates int currentState = mClingPlayControl.getCurrentState();

        /**
         * 通过判断状态 来决定 是继续播放 还是重新播放
         */

//        if (currentState == DLANPlayState.STOP) {
////            mClingPlayControl.playNew(mIP + Config.LOCAL_URL, new ControlCallback() {
//            mClingPlayControl.playNew(url, new ControlCallback() {
//
//                @Override
//                public void success(IResponse response) {
//                    //                    ClingUpnpServiceManager.getInstance().subscribeMediaRender();
//                    //                    getPositionInfo();
//                    ClingManager.getInstance().registerAVTransport(activity);
//                    ClingManager.getInstance().registerRenderingControl(activity);
//                }
//
//                @Override
//                public void fail(IResponse response) {
//                }
//            });
//        } else {
//            mClingPlayControl.play(new ControlCallback() {
//                @Override
//                public void success(IResponse response) {
//                }
//
//                @Override
//                public void fail(IResponse response) {
//                }
//            });
//        }
    }


    public void stop(){
//        mClingPlayControl.stop(new ControlCallback() {
//            @Override
//            public void success(IResponse response) {
//            }
//
//            @Override
//            public void fail(IResponse response) {
//            }
//        });
    }

        //设备选择监听
    public static interface AddDeviceListener{
        public void addDevice(IDevice device);
    }


    public void destory(){
        // Unbind UPnP service
        activity.unbindService(mUpnpServiceConnection);
        // Unbind System service
        //        unbindService(mSystemServiceConnection);
        // UnRegister Receiver
        activity.unregisterReceiver(mTransportStateBroadcastReceiver);

        ClingManager.getInstance().destroy();
        ClingDeviceList.getInstance().destroy();
    }

}

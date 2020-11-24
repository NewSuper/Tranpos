package com.newsuper.t.juejinbao.ui.upnp.control;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.support.annotation.NonNull;

import com.newsuper.t.juejinbao.ui.upnp.entity.IDevice;
import com.newsuper.t.juejinbao.ui.upnp.service.callback.AVTransportSubscriptionCallback;
import com.newsuper.t.juejinbao.ui.upnp.service.callback.RenderingControlSubscriptionCallback;
import com.newsuper.t.juejinbao.ui.upnp.util.ClingUtils;
import com.newsuper.t.juejinbao.ui.upnp.util.Utils;
//
//public class SubscriptionControl implements ISubscriptionControl<BluetoothClass.Device> {
//
//    private AVTransportSubscriptionCallback mAVTransportSubscriptionCallback;
//    private RenderingControlSubscriptionCallback mRenderingControlSubscriptionCallback;
//
//    public SubscriptionControl() {
//    }
//
//    @Override
//    public void registerAVTransport(@NonNull IDevice<BluetoothClass.Device> device, @NonNull Context context) {
//        if (Utils.isNotNull(mAVTransportSubscriptionCallback)) {
//            mAVTransportSubscriptionCallback.end();
//        }
//        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
//        if (Utils.isNull(controlPointImpl)) {
//            return;
//        }
//
//        mAVTransportSubscriptionCallback = new AVTransportSubscriptionCallback(device.getDevice().findService(ClingManager.AV_TRANSPORT_SERVICE), context);
//        controlPointImpl.execute(mAVTransportSubscriptionCallback);
//    }
//
//    @Override
//    public void registerRenderingControl(@NonNull IDevice<Device> device, @NonNull Context context) {
//        if (Utils.isNotNull(mRenderingControlSubscriptionCallback)) {
//            mRenderingControlSubscriptionCallback.end();
//        }
//        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
//        if (Utils.isNull(controlPointImpl)) {
//            return;
//        }
//        mRenderingControlSubscriptionCallback = new RenderingControlSubscriptionCallback(device.getDevice().findService(ClingManager
//                .RENDERING_CONTROL_SERVICE), context);
//        controlPointImpl.execute(mRenderingControlSubscriptionCallback);
//    }
//
//    @Override
//    public void destroy() {
//        if (Utils.isNotNull(mAVTransportSubscriptionCallback)) {
//            mAVTransportSubscriptionCallback.end();
//        }
//        if (Utils.isNotNull(mRenderingControlSubscriptionCallback)) {
//            mRenderingControlSubscriptionCallback.end();
//        }
//    }
//}

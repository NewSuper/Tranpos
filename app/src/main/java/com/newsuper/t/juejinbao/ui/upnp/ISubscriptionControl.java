package com.newsuper.t.juejinbao.ui.upnp;

import android.content.Context;

/**
 * 说明：tv端回调
 */

public interface ISubscriptionControl<T> {

    /**
     * 监听投屏端 AVTransport 回调
     */
    void registerAVTransport(IDevice<T> device, Context context);

    /**
     * 监听投屏端 RenderingControl 回调
     */
    void registerRenderingControl(IDevice<T> device, Context context);

    /**
     * 销毁: 释放资源
     */
    void destroy();
}

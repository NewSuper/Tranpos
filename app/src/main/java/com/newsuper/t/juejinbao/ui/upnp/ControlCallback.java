package com.newsuper.t.juejinbao.ui.upnp;



/**
 * 说明：设备控制操作 回调
 */

public interface ControlCallback<T> {

    void success(IResponse<T> response);

    void fail(IResponse<T> response);
}

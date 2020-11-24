package com.newsuper.t.juejinbao.ui.upnp.control.callback;

import com.newsuper.t.juejinbao.ui.upnp.entity.IResponse;

public interface ControlCallback<T> {

    void success(IResponse<T> response);

    void fail(IResponse<T> response);
}

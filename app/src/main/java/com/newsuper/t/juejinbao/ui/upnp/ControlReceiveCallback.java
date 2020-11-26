package com.newsuper.t.juejinbao.ui.upnp;



/**
 * 说明：手机端接收投屏端信息回调
 */

public interface ControlReceiveCallback extends ControlCallback{

    void receive(IResponse response);
}

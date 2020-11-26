package com.newsuper.t.juejinbao.ui.upnp;


import org.fourthline.cling.registry.Registry;

public interface IClingManager extends IDLNAManager {

    void setUpnpService(ClingUpnpService upnpService);

    void setDeviceManager(IDeviceManager deviceManager);

    Registry getRegistry();
}

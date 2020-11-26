package com.newsuper.t.juejinbao.ui.upnp;



import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import org.fourthline.cling.model.meta.Device;


public class ClingDevice extends EasyAdapter.TypeBean implements IDevice<Device> {

    private Device mDevice;
    /** 是否已选中 */
    private boolean isSelected;

    public ClingDevice(Device device) {
        this.mDevice = device;
    }

    @Override
    public Device getDevice() {
        return mDevice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
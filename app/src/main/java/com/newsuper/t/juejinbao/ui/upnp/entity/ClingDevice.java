package com.newsuper.t.juejinbao.ui.upnp.entity;


import com.juejinchain.android.module.movie.adapter.EasyAdapter;

import org.fourthline.cling.model.meta.Device;

/**
 * 说明：
 * 作者：zhouzhan
 * 日期：17/6/27 17:47
 */

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
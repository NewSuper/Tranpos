package com.newsuper.t.juejinbao.ui.upnp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.juejinchain.android.R;
import com.juejinchain.android.module.upnp.entity.ClingDevice;

import org.fourthline.cling.model.meta.Device;

/**
 * 说明：
 * 作者：zhouzhan
 * 日期：17/6/28 15:50
 */

public class DevicesAdapter extends ArrayAdapter<ClingDevice> {
    private LayoutInflater mInflater;

    public DevicesAdapter(Context context) {
        super(context, 0);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.devices_items, null);

        ClingDevice item = getItem(position);
        if (item == null || item.getDevice() == null) {
            return convertView;
        }

        Device device = item.getDevice();
        return convertView;
    }
}
package com.transpos.market.ui.register.mvp;

import com.trans.network.HttpManger;
import com.trans.network.callback.StringCallback;
import com.transpos.market.base.HttpUrl;
import com.transpos.market.utils.DeviceUtils;
import com.transpos.market.utils.FoodConstant;
import com.transpos.market.utils.OpenApiUtils;


import java.util.ArrayList;
import java.util.Map;

public class RegisterModel implements RegisterContract.Model {
    @Override
    public void register(String authCode,StringCallback stringCallback) {
        Map<String, Object> parameters = OpenApiUtils.getInstance().newParameters();
        parameters.put("name", "regist");
        parameters.put("appSign", FoodConstant.appSign);
        parameters.put("terminalType", FoodConstant.terminalType);
        DeviceUtils device = DeviceUtils.getInstance();
        parameters.put("computerName", device.getComputerName());
        parameters.put("macAddress", device.getMacAddress());
        parameters.put("serialNumber", device.getMotherboardNumber());
        parameters.put("cpuNumber", device.getCpuID());
        parameters.put("authCode", authCode);
        parameters.put("sign", OpenApiUtils.getInstance().sign(parameters, new ArrayList<String>()));
        HttpManger.getSingleton().postString(HttpUrl.BASE_OPEN_URL, parameters, stringCallback);
    }
}

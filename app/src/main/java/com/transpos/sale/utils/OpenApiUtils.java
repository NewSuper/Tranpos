package com.transpos.sale.utils;

import com.transpos.sale.base.BaseApp;
import com.transpos.sale.entity.RegistrationCode;
import com.transpos.tools.tputils.TPUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenApiUtils {

    private static final String TAG ="OpenApiUtils";

    private static OpenApiUtils _instance = null;

    private OpenApiUtils(){}

    public static OpenApiUtils getInstance(){
        if(_instance == null){
            _instance = new OpenApiUtils();
        }
        return _instance;
    }




    public Map<String, Object> newParameters(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("timestamp", DateUtil.getNowDateStr());
        return parameters;
    }

    public Map<String, Object> newApiParameters(){
        Map<String, Object> parameters = new HashMap<>();
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);
        if(api != null){
            parameters.put("app_key", api.getAppKey());
            parameters.put("format","json");
            parameters.put("timestamp", DateUtil.getNowDateStr());
            parameters.put("version", "1.0");
        }
        return parameters;
    }

    public String sign(RegistrationCode api, Map<String, Object> params){
        return sign(api, params, null);
    }
    /**
     * API签名
     * @param api
     * @param params
     * @param ignores
     * @return
     */
    public String sign(RegistrationCode api, Map<String, Object> params, List<String> ignores) {
        StringBuffer sBuffer = new StringBuffer();
        List<String> paramNames = new ArrayList<String>(params.size());
        paramNames.addAll(params.keySet());
        if (paramNames != null && ignores != null) {
            for (String name : ignores) {
                paramNames.remove(name);
            }
        }
        Collections.sort(paramNames);

        sBuffer.append(api.getAppSecret());
        for (String paramName : paramNames) {
            sBuffer.append(paramName).append(params.get(paramName));
        }

        sBuffer.append(api.getAppSecret());

        return MD5.hex_md5(sBuffer.toString());
    }

    /**
     * extend 加密
     * @param params
     * @param ignores
     * @return
     */
    public String sign(Map<String, Object> params, List<String> ignores) {
        StringBuffer sBuffer = new StringBuffer();
        List<String> paramNames = new ArrayList<String>(params.size());
        paramNames.addAll(params.keySet());
        if (paramNames != null && ignores != null) {
            for (String name : ignores) {
                paramNames.remove(name);
            }
        }
        Collections.sort(paramNames);

        for (String paramName : paramNames) {
            sBuffer.append(paramName).append(params.get(paramName));
        }
        return MD5.hex_md5(sBuffer.toString());
    }


}

package com.newsuper.t.juejinbao.utils.network;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HttpRequestBody {
    public static RequestBody getRequestBody(JSONObject jsonObject) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));
        return body;
    }

    public static Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

    public static Map<String, String> generateRequestQuery(Map<String, String> requestDataMap) {
        Map<String, String> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {

            String value = requestDataMap.get(key) == null ? "" : requestDataMap.get(key);
            requestBodyMap.put(key, value);
        }
        return requestBodyMap;
    }

}

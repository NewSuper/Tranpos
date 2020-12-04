package com.newsuper.t.consumer.utils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class JsonUtils {
    public static <T> T parseObj(String strJson, Class<T> tClass) {
        return new Gson().fromJson(strJson, tClass);
    }

    public static <T> List<T> parseArray(String strJson, Class<T> t) {
        return new Gson().fromJson(strJson, new TypeToken<List<T>>() {}.getType());
    }


    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static String parseElement(String strJson, String type){
        String element = null;
        JsonParser parser = new JsonParser();
        JsonElement jsonEl = parser.parse(strJson);

        JsonObject jsonObj = null;

        if(jsonEl.isJsonObject()) {
            jsonObj = jsonEl.getAsJsonObject();//转换成Json对象
            if (jsonObj.has(type)) {
                element = jsonObj.get(type).getAsString();//status节点
            }
        }
        return element;
    }

//    public static <T> BaseResponse<T> parseObjEntity(String strJson) {//解析错误
////        BaseResponse<UserEntity> dataEntity = JSONObject.parseObject(strJson, new TypeReference<BaseResponse<UserEntity>>(){}.getUserType());
//        return JSONObject.parseObject(strJson, new TypeReference<BaseResponse<T>>() {
//        }.getType());
//    }
//
//    public static String objToJson(Object obj) {
//        return JSONObject.toJSONString(obj);
//    }


//    public static void main(String[] args) {
//        BaseResponse<UserEntity> baseObjListBean = parseObjEntity(TestJson.jsonObjEntity, UserEntity.class);
//        System.out.print("自定义数据类型解析=" + baseObjListBean.getResults());
//        System.out.print("转json字符串objToJson="+objToJson(baseObjListBean));

//        System.out.print("数组解析="+parseArray(TestJson.arrayJson, NoticeBean.class).get(0).getGoodsName());

//        BaseResponse<GoodsEntity>  baseObjListBeans = parseObj(TestJson.jsonObjEntity, BaseResponse.class);
//        GoodsEntity goodsEntity =  baseObjListBeans.getResults();
//        System.out.print("基本数据解析=" + goodsEntity);

//    }
}

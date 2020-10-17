package com.transpos.market.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 
 * 处理json的工具类. <br>
 * 本类为处理json的工具类
 * 
 */
public class JsonTools {

	private static final String TAG = "JsonTools";
	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	public static String serialize(Object object){
		return JSON.toJSONString(object);
//		Gson gson = new Gson();
//		return gson.toJson(object);
	}

	/**
	 * 字符串转对象
	 * @param json
	 * @param <T>
	 * @return
	 */
	public static <T> T deserialize(String json, Class<T> t){
		return JSON.parseObject(json, t);
//		Gson gson = new Gson();
//		return gson.fromJson(json, t);
	}

	/**
	 * 字符串转list对象
	 * @param json
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> deserialize4Array(String json, Class<T> type){
		return JSON.parseArray(json, type);


//		List<T> result = null;
//		if (StringUtils.isNotBlank(json)) {
//			Gson gson = new GsonBuilder().create();
//			try {
//				JsonParser parser = new JsonParser();
//				JsonArray Jarray = parser.parse(json).getAsJsonArray();
//				if (Jarray != null) {
//					result = new ArrayList<>();
//					for (JsonElement obj : Jarray) {
//						try {
//							T cse = gson.fromJson(obj, type);
//							result.add(cse);
//						} catch (Exception e) {
//							LogUtil.e(TAG, "deserialize4Array异常", e);
//						}
//					}
//				}
//			} catch (Exception e) {
//				LogUtil.e(TAG, "deserialize4Array异常", e);
//			} finally {
//				if (gson != null) {
//					gson = null;
//				}
//			}
//		}
//		return result;
	}
}
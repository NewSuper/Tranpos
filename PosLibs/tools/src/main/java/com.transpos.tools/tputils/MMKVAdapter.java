package com.transpos.tools.tputils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KY
 * @date 2018/9/25
 */
public final class MMKVAdapter implements IAdapter {

    private static final Gson GSON = new Gson();
    private MMKV mmkv;
    private static Map<String, MMKVAdapter> cacheAdapter = new HashMap<>();

    private MMKVAdapter(Context context, String name) {
        MMKV.initialize(context);
        this.mmkv = MMKV.mmkvWithID(name, MMKV.MULTI_PROCESS_MODE);
    }

    synchronized static IAdapter getImpl(Context context, String name) {
        MMKVAdapter mmkvAdapter;
        if (!cacheAdapter.keySet().contains(name)) {
            mmkvAdapter = new MMKVAdapter(context, name);
            cacheAdapter.put(name, mmkvAdapter);
        } else {
            mmkvAdapter = cacheAdapter.get(name);
        }
        return mmkvAdapter;
    }

    @Override
    public boolean put(String key, Object value) {
        boolean result;
        if (value instanceof String) {
            result = mmkv.encode(key, (String) value);
        } else if (value instanceof Integer) {
            result = mmkv.encode(key, (Integer) value);
        } else if (value instanceof Boolean) {
            result = mmkv.encode(key, (Boolean) value);
        } else if (value instanceof Float) {
            result = mmkv.encode(key, (Float) value);
        } else if (value instanceof Long) {
            result = mmkv.encode(key, (Long) value);
        } else {
            throw new RuntimeException("该方法只支持基础数据类型");
        }
        return result;
    }

    @Override
    public <T> T get(String key, T defaultValue) {
        T t;
        if (defaultValue instanceof String) {
            t = (T) mmkv.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            t = (T) (Integer) mmkv.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            t = (T) (Boolean) mmkv.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            t = (T) (Float) mmkv.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            t = (T) (Long) mmkv.getLong(key, (Long) defaultValue);
        } else {
            throw new RuntimeException("该方法只支持基础数据类型");
        }
        return t;
    }

    @Override
    public <T> boolean putObject(String key, T object) {
        return mmkv.encode(key, GSON.toJson(object));
    }

    @Override
    public <T> T getObject(String key, Class<T> tClass) {
        String json = mmkv.getString(key, "");
        return GSON.fromJson(json, tClass);
    }

    @Override
    public <T> boolean putList(String key, List<T> list) {
        return mmkv.encode(key, GSON.toJson(list));
    }

    @Override
    public <T> List<T> getList(String key, Class<T[]> tClass) {
        String json = mmkv.getString(key, "");
        return fromJsonToList(json, tClass);
    }

    @Override
    public boolean contains(String key) {
        return mmkv.contains(key);
    }

    @Override
    public boolean remove(String key) {
        return mmkv.remove(key).commit();
    }

    @Override
    public boolean clear() {
        return mmkv.clear().commit();
    }

    private static <T> List<T> fromJsonToList(String json, Class<T[]> clazz) {
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        try {
            T[] arr = GSON.fromJson(json, clazz);
            if (arr == null || arr.length <= 0) {
                return new ArrayList<>();
            }
            List<T> ts = Arrays.asList(arr);
            return new ArrayList<>(ts);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

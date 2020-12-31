package com.transpos.tools.tputils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import net.grandcentrix.tray.TrayPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KY
 * @date 2018/9/25
 */
public final class TrayAdapter implements IAdapter {

    private static final Gson GSON = new Gson();
    private TrayPreferences trayPreferences;
    private static Map<String, TrayAdapter> cacheAdapter = new HashMap<>();

    private TrayAdapter(Context context, String name) {
        this.trayPreferences = new TrayPreferences(context, name, 1);
    }

    synchronized static IAdapter getImpl(Context context, String name) {
        TrayAdapter trayAdapter;
        if (!cacheAdapter.keySet().contains(name)) {
            trayAdapter = new TrayAdapter(context, name);
            cacheAdapter.put(name, trayAdapter);
        } else {
            trayAdapter = cacheAdapter.get(name);
        }
        return trayAdapter;
    }

    @Override
    public boolean put(String key, Object value) {
        boolean result;
        if (value instanceof String) {
            result = trayPreferences.put(key, (String) value);
        } else if (value instanceof Integer) {
            result = trayPreferences.put(key, (Integer) value);
        } else if (value instanceof Boolean) {
            result = trayPreferences.put(key, (Boolean) value);
        } else if (value instanceof Float) {
            result = trayPreferences.put(key, (Float) value);
        } else if (value instanceof Long) {
            result = trayPreferences.put(key, (Long) value);
        } else {
            throw new RuntimeException("该方法只支持基础数据类型");
        }
        return result;
    }

    @Override
    public <T> T get(String key, T defaultValue) {
        T t;
        if (defaultValue instanceof String) {
            t = (T) trayPreferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            t = (T) (Integer) trayPreferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            t = (T) (Boolean) trayPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            t = (T) (Float) trayPreferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            t = (T) (Long) trayPreferences.getLong(key, (Long) defaultValue);
        } else {
            throw new RuntimeException("该方法只支持基础数据类型");
        }
        return t;
    }

    @Override
    public <T> boolean putObject(String key, T object) {
        return trayPreferences.put(key, GSON.toJson(object));
    }

    @Override
    public <T> T getObject(String key, Class<T> tClass) {
        String json = trayPreferences.getString(key, "");
        return GSON.fromJson(json, tClass);
    }

    @Override
    public <T> boolean putList(String key, List<T> list) {
        return trayPreferences.put(key, GSON.toJson(list));
    }

    @Override
    public <T> List<T> getList(String key, Class<T[]> tClass) {
        String json = trayPreferences.getString(key, "");
        return fromJsonToList(json, tClass);
    }

    @Override
    public boolean contains(String key) {
        return trayPreferences.contains(key);
    }

    @Override
    public boolean remove(String key) {
        return trayPreferences.remove(key);
    }

    @Override
    public boolean clear() {
        return trayPreferences.clear();
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

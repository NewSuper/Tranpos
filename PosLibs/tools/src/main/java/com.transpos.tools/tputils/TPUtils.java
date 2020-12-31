package com.transpos.tools.tputils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.tencent.mmkv.MMKV;

import java.util.List;


/**
 * 替代 Sharedpreference 的跨进程数据存储封装
 * 为了防止在运行时切换底层实现导致出现问题，具体实现的配置放在了manifest的meta-data中，
 * key:tp_impl;value(int):0/1(mmkv/tray)
 *
 * @author KY
 * @date 2018/9/12
 */
@SuppressWarnings("unused")
public class TPUtils {

    private static final String DEFAULT_GROUP_NAME = "xiaoma";
    /**
     * 静态方法使用的单例adapter
     */
    private static IAdapter adapter;

    // 静态方法,使用默认分组名

    /**
     * 基础类型的保存
     *
     * @param context context
     * @param key     key
     * @param value   value
     * @return is operation success
     */
    public static boolean put(Context context, String key, Object value) {
        return put(getInstance(context), key, value);
    }

    /**
     * 基础类型的获取
     *
     * @param context      context
     * @param key          key
     * @param defaultValue defaultValue
     * @return the save value or defaultValue for none
     */
    public static <T> T get(Context context, String key, @NonNull T defaultValue) {
        return get(getInstance(context), key, defaultValue);
    }

    /**
     * 非泛型对象的保存
     *
     * @param context context
     * @param key     key
     * @param object  Non-generic object, object's fields also couldn't be generic
     * @return is operation success
     */
    public static boolean putObject(Context context, String key, Object object) {
        return putObject(getInstance(context), key, object);
    }

    /**
     * 非泛型对象的获取
     *
     * @param context context
     * @param key     key
     * @param tClass  Non-generic object class
     * @param <T>     object type
     * @return the save object or null for none
     */
    public static <T> T getObject(Context context, String key, Class<T> tClass) {
        return getObject(getInstance(context), key, tClass);
    }

    /**
     * 集合的保存
     *
     * @param context context
     * @param key     key
     * @param list    list object
     * @return is operation success
     */
    public static boolean putList(Context context, String key, List list) {
        return putList(getInstance(context), key, list);
    }

    /**
     * 集合的获取
     *
     * @param context context
     * @param key     key
     * @param tClass  list object class
     * @param <T>     object type
     * @return the save list or null for none
     */
    public static <T> List<T> getList(Context context, String key, Class<T[]> tClass) {
        return getList(getInstance(context), key, tClass);
    }

    /**
     * 判断是否包含某个key
     *
     * @param context context
     * @param key     key
     * @return is contains the key
     */
    public static boolean contains(Context context, String key) {
        return contains(getInstance(context), key);
    }

    /**
     * 移除key对应的value
     *
     * @param context context
     * @param key     key
     * @return is operation success
     */
    public static boolean remove(Context context, String key) {
        return remove(getInstance(context), key);
    }

    /**
     * 清空所有数据
     *
     * @param context context
     * @return is operation success
     */
    public static boolean clear(Context context) {
        return clear(getInstance(context));
    }

    /**
     * 基础类型的保存
     * 用户相关的key-value存储，封装自{@link TPUtils.NamedTP}
     *
     * @param context context
     * @param key     key
     * @param value   value
     * @return is operation success
     */
    public static boolean put(Context context, String id, String key, Object value) {
        return put(getConfigAdapter(context, id), key, value);
    }

    /**
     * 基础类型的获取
     * 用户相关的key-value存储，封装自{@link TPUtils.NamedTP}
     *
     * @param context      context
     * @param key          key
     * @param defaultValue defaultValue
     * @return the save value or defaultValue for none
     */
    public static <T> T get(Context context, String id, String key, @NonNull T defaultValue) {
        return get(getConfigAdapter(context, id), key, defaultValue);
    }

    /**
     * 非泛型对象的保存
     * 用户相关的key-value存储，封装自{@link TPUtils.NamedTP}
     *
     * @param context context
     * @param key     key
     * @param object  Non-generic object, object's fields also couldn't be generic
     * @return is operation success
     */
    public static boolean putObject(Context context, String id, String key, Object object) {
        return putObject(getConfigAdapter(context, id), key, object);
    }

    /**
     * 非泛型对象的获取
     * 用户相关的key-value存储，封装自{@link TPUtils.NamedTP}
     *
     * @param context context
     * @param key     key
     * @param tClass  Non-generic object class
     * @param <T>     object type
     * @return the save object or null for none
     */
    public static <T> T getObject(Context context, String id, String key, Class<T> tClass) {
        return getObject(getConfigAdapter(context, id), key, tClass);
    }

    /**
     * 集合的保存
     * 用户相关的key-value存储，封装自{@link TPUtils.NamedTP}
     *
     * @param context context
     * @param key     key
     * @param list    list object
     * @return is operation success
     */
    public static boolean putList(Context context, String id, String key, List list) {
        return putList(getConfigAdapter(context, id), key, list);
    }

    /**
     * 集合的获取
     * 用户相关的key-value存储，封装自{@link TPUtils.NamedTP}
     *
     * @param context context
     * @param key     key
     * @param tClass  list object class
     * @param <T>     object type
     * @return the save list or null for none
     */
    public static <T> List<T> getList(Context context, String id, String key, Class<T[]> tClass) {
        return getList(getConfigAdapter(context, id), key, tClass);
    }

    /**
     * 判断是否包含某个key
     * 用户相关的key-value存储，封装自{@link TPUtils.NamedTP}
     *
     * @param context context
     * @param key     key
     * @return is contains the key
     */
    public static boolean contains(Context context, String id, String key) {
        return contains(getConfigAdapter(context, id), key);
    }

    /**
     * 移除key对应的value
     * 用户相关的key-value存储，封装自{@link TPUtils.NamedTP}
     *
     * @param context context
     * @param key     key
     * @return is operation success
     */
    public static boolean remove(Context context, String id, String key) {
        return remove(getConfigAdapter(context, id), key);
    }

    /**
     * 清空所有数据
     * 用户相关的key-value存储，封装自{@link TPUtils.NamedTP}
     *
     * @param context context
     * @return is operation success
     */
    public static boolean clear(Context context, String id) {
        return clear(getConfigAdapter(context, id));
    }

    /**
     * 使用特定分组名的MMKV封装
     */
    public static class NamedTP {
        private IAdapter adapter;

        /**
         * 使用特定分组名构造方法
         *
         * @param context contex
         * @param name    分组名
         */
        public NamedTP(Context context, String name) {
            this.adapter = getConfigAdapter(context, name);
        }

        /**
         * 使用特定分组名，基础类型的保存
         *
         * @param key   key
         * @param value value
         * @return is operation success
         */
        public boolean put(String key, Object value) {
            return TPUtils.put(adapter, key, value);
        }

        /**
         * 使用特定分组名，基础类型的获取
         *
         * @param key          key
         * @param defaultValue defaultValue
         * @return the save value or defaultValue for none
         */
        public <T> T get(String key, T defaultValue) {
            return TPUtils.get(adapter, key, defaultValue);
        }

        /**
         * 使用特定分组名，非泛型对象的保存
         *
         * @param key    key
         * @param object Non-generic object, object's fields also couldn't be generic
         * @return is operation success
         */
        public boolean putObject(String key, Object object) {
            return TPUtils.putObject(adapter, key, object);
        }

        /**
         * 使用特定分组名，非泛型对象的获取
         *
         * @param key    key
         * @param tClass Non-generic object class
         * @param <T>    object type
         * @return the save object or null for none
         */
        public <T> T getObject(String key, Class<T> tClass) {
            return TPUtils.getObject(adapter, key, tClass);
        }

        /**
         * 使用特定分组名，集合的保存
         *
         * @param key  key
         * @param list list object
         * @return is operation success
         */
        public boolean putList(String key, List list) {
            return TPUtils.putList(adapter, key, list);
        }

        /**
         * 使用特定分组名，集合的获取
         *
         * @param key    key
         * @param tClass list object class
         * @param <T>    object type
         * @return the save list or null for none
         */
        public <T> List<T> getList(String key, Class<T[]> tClass) {
            return TPUtils.getList(adapter, key, tClass);
        }

        /**
         * 使用特定分组名，判断是否包含某个key
         *
         * @param key key
         * @return is contains the key
         */
        public boolean contains(String key) {
            return TPUtils.contains(adapter, key);
        }

        /**
         * 使用特定分组名，移除key对应的value
         *
         * @param key key
         * @return is operation success
         */
        public boolean remove(String key) {
            return TPUtils.remove(adapter, key);
        }

        /**
         * 使用特定分组名，清空所有数据
         *
         * @return is operation success
         */
        public boolean clear() {
            return TPUtils.clear(adapter);
        }
    }

    // 私有方法

    private static Boolean put(IAdapter adapter, String key, Object value) {
        return adapter.put(key, value);
    }

    private static <T> T get(IAdapter adapter, String key, @NonNull T defaultValue) {
        return adapter.get(key, defaultValue);
    }

    private static boolean putObject(IAdapter adapter, String key, Object object) {
        return adapter.putObject(key, object);
    }

    private static <T> T getObject(IAdapter adapter, String key, Class<T> tClass) {
        return adapter.getObject(key, tClass);
    }

    private static boolean putList(IAdapter adapter, String key, List list) {
        return adapter.putList(key, list);
    }

    private static <T> List<T> getList(IAdapter adapter, String key, Class<T[]> tClass) {
        return adapter.getList(key, tClass);
    }

    private static boolean contains(IAdapter adapter, String key) {
        return adapter.contains(key);
    }

    private static boolean remove(IAdapter adapter, String key) {
        return adapter.remove(key);
    }

    private static boolean clear(IAdapter adapter) {
        return adapter.clear();
    }

    /**
     * 根据manifest中配置的meta-data获取默认的Adapter
     *
     * @param context context
     * @return IAdapter impl
     */
    private static IAdapter getInstance(Context context) {
        if (adapter == null) {
            synchronized (TPUtils.class) {
                if (adapter == null) {
                    adapter = getConfigAdapter(context);
                }
            }
        }
        return adapter;
    }

    private static IAdapter getConfigAdapter(Context context) {
        return getConfigAdapter(context, null);
    }

    private static IAdapter getConfigAdapter(Context context, String name) {
        ImplType implType;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            int typeValue = appInfo.metaData.getInt("tp_impl");
            implType = ImplType.get(typeValue);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            implType = ImplType.MMKV;
        }

        switch (implType) {
            case TRAY:
                return TrayAdapter.getImpl(context, name == null ? DEFAULT_GROUP_NAME : name);
            case MMKV:
            default:
                MMKV.initialize(context);
                return MMKVAdapter.getImpl(context, name == null ? DEFAULT_GROUP_NAME : name);
        }
    }

    /**
     * TPUtils的底层实现类型枚举
     */
    public enum ImplType {
        /**
         * MMKV实现
         */
        MMKV(0),
        /**
         * tray实现
         */
        TRAY(1);

        private int value;

        ImplType(int value) {
            this.value = value;
        }

        public static ImplType get(int value) {
            switch (value) {
                case 0:
                    return ImplType.MMKV;
                case 1:
                    return ImplType.TRAY;
                default:
                    return ImplType.MMKV;
            }
        }
    }
}

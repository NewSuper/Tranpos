package com.transpos.tools.tputils;

import java.util.List;

/**
 * @author KY
 * @date 2018/9/25
 */
public interface IAdapter {
    /**
     * 基础类型的保存
     *
     * @param key   key
     * @param value value
     * @return is operation success
     */
    boolean put(String key, Object value);

    /**
     * 基础类型的获取
     *
     * @param key          key
     * @param defaultValue defaultValue
     * @return the save value or defaultValue for none
     */
    <T> T get(String key, T defaultValue);

    /**
     * 非泛型对象的保存
     *
     * @param key    key
     * @param object Non-generic object, object's fields also couldn't be generic
     * @return is operation success
     */
    <T> boolean putObject(String key, T object);

    /**
     * 非泛型对象的获取
     *
     * @param key    key
     * @param tClass Non-generic object class
     * @param <T>    object type
     * @return the save object or null for none
     */
    <T> T getObject(String key, Class<T> tClass);

    /**
     * 集合的保存
     *
     * @param key  key
     * @param list list object
     * @return is operation success
     */
    <T> boolean putList(String key, List<T> list);

    /**
     * 集合的获取
     *
     * @param key    key
     * @param tClass list object class
     * @param <T>    object type
     * @return the save list or null for none
     */
    <T> List<T> getList(String key, Class<T[]> tClass);

    /**
     * 判断是否包含某个key
     *
     * @param key key
     * @return is contains the key
     */
    boolean contains(String key);

    /**
     * 移除key对应的value
     *
     * @param key key
     * @return is operation success
     */
    boolean remove(String key);

    /**
     * 清空所有数据
     *
     * @return is operation success
     */
    boolean clear();
}

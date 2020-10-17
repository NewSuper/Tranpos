package com.transpos.market.utils;

import java.lang.reflect.ParameterizedType;

public class CreateUtil {
    public static <T> T getT(Object obj, int i){
        try {
            return ((Class<T>)((ParameterizedType)(obj.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}

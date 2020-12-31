package com.trans.network.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vincenthu
 * Date: 2016/11/22
 * Time: 11:04
 */
public class GsonHelper {

    public static String toJson(Object src) {
        if (src == null) {
            return "";
        }
        Gson gson = new Gson();
        try {
            return gson.toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String toJsonPlus(Object src) {
        if (src == null) {
            return "";
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        try {
            return gson.toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String toJson(Object src, Type typeOfSrc) {
        if (src == null) {
            return "";
        }
        Gson gson = new Gson();
        try {
            return gson.toJson(src, typeOfSrc);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String json, Type type) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        try {
            T result = gson.fromJson(json, type);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> fromJsonToList(String json, Class<T[]> clazz) {
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        try {
            T[] arr = new Gson().fromJson(json, clazz);
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

    public static String jsonFormat(String json) {
        if (json == null) {
            return "  null";
        }
        json = json.trim();
        if (TextUtils.isEmpty(json)) {
            return "  { }";
        }
        final String lineSeparator = System.getProperty("line.separator");
        final int indentSpaces = 2;
        String message;
        json = json.trim();

        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                message = jsonObject.toString(indentSpaces);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                message = jsonArray.toString(indentSpaces);
            } else {
                message = json;
            }
        } catch (JSONException e) {
            message = json;
        }
        String[] lines = message.split(lineSeparator);
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append(lineSeparator);
            builder.append("  ");
            builder.append(line);
        }
        return builder.toString();
    }
}

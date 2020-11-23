package com.newsuper.t.juejinbao.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    static final Charset UTF_8 = Charset.forName("UTF-8");
    final Gson gson;
    final TypeAdapter<T> adapter;
    JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
        Log.e("RetrofitLog", "#IRequestBodyConverter初始化#");
    }
    @Override
    public RequestBody convert(T value) throws IOException {
        String json = value.toString();
//        Log.e("RetrofitLog", "#加密前#" + json);
//        try {
//            json = Des3.encode(json.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.e("RetrofitLog", "#加密后#" + json);

        return RequestBody.create(MEDIA_TYPE, json);	}


}

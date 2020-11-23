package com.newsuper.t.juejinbao.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class JsonConverterFactory extends Converter.Factory {
    private Context activity;
    private final Gson gson;

    public static JsonConverterFactory create(Context mContext) {
        return create(new GsonBuilder().setLenient().create()).setActivity(mContext);
    }

    public Context getActivity() {
        return activity;
    }

    public JsonConverterFactory setActivity(Context activity) {
        this.activity = activity;
        return this;
    }

    public static JsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new JsonConverterFactory(gson);

    }

    private JsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = null;
        try {
            adapter = gson.getAdapter(TypeToken.get(type));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResponseBodyConverter<>(gson, adapter, activity); // 响应
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        Log.e("RetrofitLog", "requestBodyConverter:#发起请求#");
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new JsonRequestBodyConverter<>(gson, adapter); // 请求	}

    }
}

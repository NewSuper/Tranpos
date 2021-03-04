package com.newsuper.t.consumer.manager.gsonfactory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.newsuper.t.consumer.bean.BaseBean;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class CustomGsonResponseBodyConverter  <T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        BaseBean baseBean = gson.fromJson(value.string(),BaseBean.class);
        switch (baseBean.error_code){
            case "0":
                break;
            case "-1":
                break;
            case "-100":
                break;
            case "-200":
                break;
            case "-300":
                break;
            case "-301":
                break;
        }
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}

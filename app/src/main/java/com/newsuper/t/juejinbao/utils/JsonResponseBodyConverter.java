package com.newsuper.t.juejinbao.utils;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {


    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private Context activity;

    JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, Context activity) {
        this.gson = gson;
        this.adapter = adapter;
        this.activity = activity;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String string = value.string();
//        Log.e("RetrofitLog", "#解密前#" + string);
        try {
            /**
             * Event bus [Bus "default"] accessed from non-main thread null
             */
            JSONObject jsonObject = new JSONObject(string);
//            JSONObject meta = jsonObject.getJSONObject("meta");
            int code = jsonObject.getInt("code");
            if (code == 1008) {
//                BusProvider.getInstance().post("main");
                Intent intent = new Intent("action");
                intent.putExtra("code", 1008);
                activity.sendBroadcast(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            string = Des3.decode(string);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.e("RetrofitLog", "#解密后#" + string);
        return adapter.fromJson(string);
    }
}

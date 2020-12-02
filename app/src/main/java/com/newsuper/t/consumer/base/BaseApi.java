package com.newsuper.t.consumer.base;

import com.newsuper.t.consumer.bean.TestModle;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface BaseApi {
    @FormUrlEncoded
    @POST
    Call<String> getStringResponse(@Url String url, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST()
    Observable<String> getStringData(@Url String url, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST()
    Observable<TestModle> getData(@Url String url, @FieldMap Map<String, String> map);

}

package com.basic.retrofit.using.java.data.remote.api.common;

import java.util.Map;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Add basic API to reduce Api redundancy.
 */
public interface Service /* Retrofit turns your HTTP API into a Java interface. */ {

    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> parameters);

    @POST()
    Observable<ResponseBody> postBody(@Url String url, @Body Object object);

    @POST()
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> postJson(@Url String url, @Body RequestBody jsonRequestBody);

    @POST()
    Observable<ResponseBody> postBody(@Url String url, @Body RequestBody requestBody);

    @GET()
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> parameters);

    @DELETE()
    Observable<ResponseBody> delete(@Url String url, @QueryMap Map<String, String> parameters);

    //@DELETE() //The delete body request is special and needs to be customized
    @HTTP(method = "DELETE",/*path = "",*/hasBody = true)
    Observable<ResponseBody> deleteBody(@Url String url, @Body Object object);

    //@DELETE() //The delete body request is special and needs to be customized
    @HTTP(method = "DELETE",/*path = "",*/hasBody = true)
    Observable<ResponseBody> deleteBody(@Url String url, @Body RequestBody requestBody);

    //@DELETE() //The delete body request is special and needs to be customized
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @HTTP(method = "DELETE",/*path = "",*/hasBody = true)
    Observable<ResponseBody> deleteJson(@Url String url, @Body RequestBody jsonRequestBody);

    @PUT()
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> maps);

    @PUT()
    Observable<ResponseBody> putBody(@Url String url, @Body Object object);

    @PUT()
    Observable<ResponseBody> putBody(@Url String url, @Body RequestBody requestBody);

    @PUT()
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> putJson(@Url String url, @Body RequestBody jsonRequestBody);
}

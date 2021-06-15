package com.badasoftware.library.network.api.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadService {

    /*
     * If the file is very large, you must use the Streaming annotation.
     * Otherwise retrofit will read the entire file into memory by default.
     */

    /*
     * option 1: a resource relative to your base URL
     * No use any Interceptor in okHttpClientBuilder
     * If you use @Streaming annotation
     */
    @Streaming
    @GET("zip_9MB.zip")
    Observable<Response<ResponseBody>> downloadFile();

    /*
     * option 2: using a dynamic URL
     * No use any Interceptor in okHttpClientBuilder
     * If you use @Streaming annotation
     */
    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFile(@Url String downloadFileFromThisUrl);
}

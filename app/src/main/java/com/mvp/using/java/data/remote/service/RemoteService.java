package com.mvp.using.java.data.remote.service;

import com.badasoftware.library.network.ApiResponse;
import com.mvp.using.java.data.remote.RemoteConfiguration;
import com.mvp.using.java.model.direct.Base;
import com.mvp.using.java.model.withbase.EmployeeList;
import com.mvp.using.java.model.withbase.Page;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RemoteService {

    /* Here not define ApiResponse at first we don't want already define base response*/
    @GET(RemoteConfiguration.EMPLOYEE)
    Observable<Response<Base>> getEmployee();

    /* Here we define ApiResponse at first we want already define base response*/
    @GET(RemoteConfiguration.EMPLOYEE_LIST)
    Observable<Response<ApiResponse<EmployeeList>>> getEmployeeList();

    @FormUrlEncoded
    @POST(RemoteConfiguration.GET_USERS)
    Observable<Response<ApiResponse<Page>>> getPage(
            @Field("apiKey") String key,
            @Field("pageNumber") String pageNumber);

    /**
     * download example
     * @param start
     * @param url
     * @return
     */
    /*Large files need to add this judgment to prevent writing to the memory during downloading*/
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);
}

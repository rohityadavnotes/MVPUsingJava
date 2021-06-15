package com.mvp.using.java.di.module;

import android.content.Context;

import com.badasoftware.library.network.di.qualifier.ApiBaseUrl;
import com.badasoftware.library.network.di.qualifier.ApplicationContext;
import com.badasoftware.library.network.helper.OkHttpClientHelper;
import com.badasoftware.library.network.helper.RetrofitHelper;
import com.badasoftware.library.network.interceptor.HeaderInterceptor;
import com.badasoftware.library.network.utils.GsonUtils;
import com.mvp.using.java.BuildConfig;
import com.mvp.using.java.data.remote.RemoteConfiguration;
import com.mvp.using.java.di.qualifier.remote.CustomOkHttpClient;
import com.mvp.using.java.di.qualifier.remote.CustomRetrofit;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RemoteModule {

    @Provides
    @CustomOkHttpClient
    @Singleton
    OkHttpClient provideCustomOkHttpClient(@ApplicationContext Context context) {
        OkHttpClientHelper.Builder builder = OkHttpClientHelper.getInstance(context).new Builder(context);

        builder.setConnectTimeout(RemoteConfiguration.CUSTOM_HTTP_CONNECT_TIMEOUT_IN_SECONDS);
        builder.setWriteTimeout(RemoteConfiguration.CUSTOM_HTTP_WRITE_TIMEOUT_IN_SECONDS);
        builder.setReadTimeout(RemoteConfiguration.CUSTOM_HTTP_READ_TIMEOUT_IN_SECONDS);

        Map<String, String> headers = new HashMap<>();
        headers.put(HeaderInterceptor.HEADER_KEY_CONTENT_TYPE, "application/json; charset=utf-8");
        headers.put(HeaderInterceptor.HEADER_KEY_ACCEPT, "application/json");
        headers.put(HeaderInterceptor.HEADER_KEY_ACCEPT_CHARSET, "utf-8");
        headers.put(HeaderInterceptor.HEADER_KEY_USER_AGENT, "Retrofit Kit");
        headers.put("app_version", "android_" + 1.0);

        builder.addHeader(headers);

        builder.setApiKey(RemoteConfiguration.API_KEY);

        builder.setBearerAuthenticationToken(RemoteConfiguration.BEARER_AUTHENTICATION_TOKEN);

        builder.setCustomCache(String.valueOf(
                context.getCacheDir()),
                RemoteConfiguration.CUSTOM_OK_HTTP_CACHE_FILE_NAME,
                RemoteConfiguration.CUSTOM_OK_HTTP_CACHE_SIZE,
                RemoteConfiguration.CUSTOM_CACHE_DURATION_WITH_NETWORK_IN_SECONDS,
                RemoteConfiguration.CUSTOM_CACHE_DURATION_WITHOUT_NETWORK_IN_SECONDS
        );

        /*
         * Add more custom interceptor, if you want
         *
         * If you add network only interceptor like this
         * builder.addInterceptor(new CustomInterceptor(context));
         *
         * If you add network network interceptor like this
         * builder.addNetworkInterceptor(new CustomInterceptor(context));
         */

        boolean LOG_ENABLE = BuildConfig.DEBUG;
        builder.setShowLog(LOG_ENABLE);

        return builder.build();
    }

    @Provides
    @CustomRetrofit
    @Singleton
    Retrofit provideCustomRetrofit(@ApiBaseUrl String apiBaseUrl, @CustomOkHttpClient OkHttpClient okHttpClient) {
        RetrofitHelper.Builder builder = RetrofitHelper.getInstance().new Builder();
        builder.setBaseUrl(apiBaseUrl);
        builder.setOkHttpClient(okHttpClient);

        builder.addConverterFactory(GsonConverterFactory.create(GsonUtils.getGsonBuilder().create()));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.build();
    }
}

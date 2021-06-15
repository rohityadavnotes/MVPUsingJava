package com.badasoftware.library.network.di.module;

import android.content.Context;
import com.badasoftware.library.network.Network;
import com.badasoftware.library.network.di.qualifier.ApiBaseUrl;
import com.badasoftware.library.network.di.qualifier.ApiKeyOkHttpClient;
import com.badasoftware.library.network.di.qualifier.ApiKeyRetrofit;
import com.badasoftware.library.network.di.qualifier.ApplicationContext;
import com.badasoftware.library.network.di.qualifier.BearerAuthenticationTokenOkHttpClient;
import com.badasoftware.library.network.di.qualifier.BearerAuthenticationTokenRetrofit;
import com.badasoftware.library.network.di.qualifier.CustomCachedOkHttpClient;
import com.badasoftware.library.network.di.qualifier.CustomCachedRetrofit;
import com.badasoftware.library.network.di.qualifier.DefaultCachedOkHttpClient;
import com.badasoftware.library.network.di.qualifier.DefaultCachedRetrofit;
import com.badasoftware.library.network.di.qualifier.DefaultOkHttpClient;
import com.badasoftware.library.network.di.qualifier.DefaultRetrofit;
import com.badasoftware.library.network.di.qualifier.DownloadOkHttpClient;
import com.badasoftware.library.network.di.qualifier.DownloadRetrofit;
import com.badasoftware.library.network.di.qualifier.UploadOkHttpClient;
import com.badasoftware.library.network.di.qualifier.UploadRetrofit;
import com.badasoftware.library.network.di.qualifier.isDebug;
import com.badasoftware.library.network.helper.OkHttpClientHelper;
import com.badasoftware.library.network.helper.RetrofitHelper;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    @isDebug
    boolean provideIsDebug() {
        return Network.isDebug();
    }

    @Provides
    @Singleton
    @ApiBaseUrl
    String provideApiBaseUrl() {
        return Network.getBaseUrl();
    }

    @Provides
    @DefaultOkHttpClient
    @Singleton
    OkHttpClient provideDefaultOkHttpClient(@isDebug boolean showLog, @ApplicationContext Context context) {
        OkHttpClientHelper.Builder builder = OkHttpClientHelper.getInstance(context).new Builder(context);
        builder.setShowLog(showLog);
        return builder.build();
    }

    @Provides
    @DefaultRetrofit
    @Singleton
    Retrofit provideDefaultRetrofit(@ApiBaseUrl String apiBaseUrl, @DefaultOkHttpClient OkHttpClient okHttpClient) {
        RetrofitHelper.Builder builder = RetrofitHelper.getInstance().new Builder();
        builder.setBaseUrl(apiBaseUrl);
        builder.setOkHttpClient(okHttpClient);
        return builder.build();
    }

    @Provides
    @DefaultCachedOkHttpClient
    @Singleton
    OkHttpClient provideDefaultCachedOkHttpClient(@isDebug boolean showLog, @ApplicationContext Context context) {
        OkHttpClientHelper.Builder builder = OkHttpClientHelper.getInstance(context).new Builder(context);
        builder.enableCache(true);
        builder.setShowLog(showLog);
        return builder.build();
    }

    @Provides
    @DefaultCachedRetrofit
    @Singleton
    Retrofit provideDefaultCachedRetrofit(@ApiBaseUrl String apiBaseUrl, @DefaultCachedOkHttpClient OkHttpClient okHttpClient) {
        RetrofitHelper.Builder builder = RetrofitHelper.getInstance().new Builder();
        builder.setBaseUrl(apiBaseUrl);
        builder.setOkHttpClient(okHttpClient);
        return builder.build();
    }

    @Provides
    @CustomCachedOkHttpClient
    @Singleton
    OkHttpClient provideCustomCachedOkHttpClient(@isDebug boolean showLog, @ApplicationContext Context context) {
        OkHttpClientHelper.Builder builder = OkHttpClientHelper.getInstance(context).new Builder(context);
        builder.setCustomCache(String.valueOf(
                Network.getContext().getCacheDir()),
                Network.getCustomOkHttpCacheFileName(),
                Network.getCustomOkHttpCacheSize(),
                Network.getCustomCacheDurationWithNetworkInSeconds(),
                Network.getCustomCacheDurationWithoutNetworkInSeconds()
        );
        builder.setShowLog(showLog);
        return builder.build();
    }

    @Provides
    @CustomCachedRetrofit
    @Singleton
    Retrofit provideCustomCachedRetrofit(@ApiBaseUrl String apiBaseUrl, @CustomCachedOkHttpClient OkHttpClient okHttpClient) {
        RetrofitHelper.Builder builder = RetrofitHelper.getInstance().new Builder();
        builder.setBaseUrl(apiBaseUrl);
        builder.setOkHttpClient(okHttpClient);
        return builder.build();
    }

    @Provides
    @ApiKeyOkHttpClient
    @Singleton
    OkHttpClient provideApiKeyOkHttpClient(@isDebug boolean showLog, @ApplicationContext Context context) {
        OkHttpClientHelper.Builder builder = OkHttpClientHelper.getInstance(context).new Builder(context);
        builder.setBearerAuthenticationToken(Network.getApiKey());
        builder.setShowLog(showLog);
        return builder.build();
    }

    @Provides
    @ApiKeyRetrofit
    @Singleton
    Retrofit provideApiKeyRetrofit(@ApiBaseUrl String apiBaseUrl, @ApiKeyOkHttpClient OkHttpClient okHttpClient) {
        RetrofitHelper.Builder builder = RetrofitHelper.getInstance().new Builder();
        builder.setBaseUrl(apiBaseUrl);
        builder.setOkHttpClient(okHttpClient);
        return builder.build();
    }

    @Provides
    @BearerAuthenticationTokenOkHttpClient
    @Singleton
    OkHttpClient provideBearerAuthenticationTokenOkHttpClient(@isDebug boolean showLog, @ApplicationContext Context context) {
        OkHttpClientHelper.Builder builder = OkHttpClientHelper.getInstance(context).new Builder(context);
        builder.setBearerAuthenticationToken(Network.getBearerAuthenticationToken());
        builder.setShowLog(showLog);
        return builder.build();
    }

    @Provides
    @BearerAuthenticationTokenRetrofit
    @Singleton
    Retrofit provideBearerAuthenticationTokenRetrofit(@ApiBaseUrl String apiBaseUrl, @BearerAuthenticationTokenOkHttpClient OkHttpClient okHttpClient) {
        RetrofitHelper.Builder builder = RetrofitHelper.getInstance().new Builder();
        builder.setBaseUrl(apiBaseUrl);
        builder.setOkHttpClient(okHttpClient);
        return builder.build();
    }

    @Provides
    @DownloadOkHttpClient
    @Singleton
    OkHttpClient provideDownloadOkHttpClient(@ApplicationContext Context context) {
        OkHttpClientHelper.Builder builder = OkHttpClientHelper.getInstance(context).new Builder(context);
        return builder.build();
    }

    @Provides
    @DownloadRetrofit
    @Singleton
    Retrofit provideDownloadRetrofit(@ApiBaseUrl String apiBaseUrl, @DownloadOkHttpClient OkHttpClient okHttpClient) {
        RetrofitHelper.Builder builder = RetrofitHelper.getInstance().new Builder();
        builder.setBaseUrl(apiBaseUrl);
        builder.setOkHttpClient(okHttpClient);
        return builder.build();
    }

    @Provides
    @UploadOkHttpClient
    @Singleton
    OkHttpClient provideUploadOkHttpClient(@ApplicationContext Context context) {
        OkHttpClientHelper.Builder builder = OkHttpClientHelper.getInstance(context).new Builder(context);
        return builder.build();
    }

    @Provides
    @UploadRetrofit
    @Singleton
    Retrofit provideUploadRetrofit(@ApiBaseUrl String apiBaseUrl, @UploadOkHttpClient OkHttpClient okHttpClient) {
        RetrofitHelper.Builder builder = RetrofitHelper.getInstance().new Builder();
        builder.setBaseUrl(apiBaseUrl);
        builder.setOkHttpClient(okHttpClient);
        return builder.build();
    }
}

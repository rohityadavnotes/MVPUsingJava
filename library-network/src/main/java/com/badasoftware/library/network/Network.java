package com.badasoftware.library.network;

import android.app.Application;

public class Network {

    private static Application context                              = null;
    private static boolean debug                                    = false;
    private static String baseUrl                                   = null;
    private static String apiKey                                    = null;
    private static String bearerAuthenticationToken                 = null;
    private static String customOkHttpCacheFileName                 = null;
    private static long customOkHttpCacheSize                       = 0;
    private static int customCacheDurationWithNetworkInSeconds      = 0;
    private static int customCacheDurationWithoutNetworkInSeconds   = 0;

    public static void init(Application application, boolean debug, String baseUrl) {
        setContext(application);
        setDebug(debug);
        setBaseUrl(baseUrl);
    }

    public static Application getContext() {
        return context;
    }

    public static void setContext(Application context) {
        Network.context = context;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Network.debug = debug;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        Network.baseUrl = baseUrl;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String apiKey) {
        Network.apiKey = apiKey;
    }

    public static String getBearerAuthenticationToken() {
        return bearerAuthenticationToken;
    }

    public static void setBearerAuthenticationToken(String bearerAuthenticationToken) {
        Network.bearerAuthenticationToken = bearerAuthenticationToken;
    }

    public static String getCustomOkHttpCacheFileName() {
        return customOkHttpCacheFileName;
    }

    public static void setCustomOkHttpCacheFileName(String customOkHttpCacheFileName) {
        Network.customOkHttpCacheFileName = customOkHttpCacheFileName;
    }

    public static long getCustomOkHttpCacheSize() {
        return customOkHttpCacheSize;
    }

    public static void setCustomOkHttpCacheSize(long customOkHttpCacheSize) {
        Network.customOkHttpCacheSize = customOkHttpCacheSize;
    }

    public static int getCustomCacheDurationWithNetworkInSeconds() {
        return customCacheDurationWithNetworkInSeconds;
    }

    public static void setCustomCacheDurationWithNetworkInSeconds(int customCacheDurationWithNetworkInSeconds) {
        Network.customCacheDurationWithNetworkInSeconds = customCacheDurationWithNetworkInSeconds;
    }

    public static int getCustomCacheDurationWithoutNetworkInSeconds() {
        return customCacheDurationWithoutNetworkInSeconds;
    }

    public static void setCustomCacheDurationWithoutNetworkInSeconds(int customCacheDurationWithoutNetworkInSeconds) {
        Network.customCacheDurationWithoutNetworkInSeconds = customCacheDurationWithoutNetworkInSeconds;
    }
}
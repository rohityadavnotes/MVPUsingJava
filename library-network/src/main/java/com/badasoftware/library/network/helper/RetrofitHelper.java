package com.badasoftware.library.network.helper;

import android.annotation.SuppressLint;
import com.badasoftware.library.network.utils.CommonUtils;
import com.badasoftware.library.network.utils.GsonUtils;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    @SuppressLint("StaticFieldLeak")
    private static RetrofitHelper instance;

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    public class Builder {

        private String baseUrl                                      = null;
        private OkHttpClient okHttpClient                           = null;

        private List<Converter.Factory> converterFactoryList        = new ArrayList<>();
        private List<CallAdapter.Factory> callAdapterFactoryList    = new ArrayList<>();

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public String checkBaseUrl(String baseUrl) {
            if (baseUrl.endsWith("/")) {
                return baseUrl;
            } else {
                return baseUrl + "/";
            }
        }

        public Builder setOkHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        /**
         * Partially set Converter.Factory, default GsonConverterFactory.create()
         */
        public Builder addConverterFactory(Converter.Factory factory) {
            if (factory != null) {
                converterFactoryList.add(factory);
            }
            return this;
        }

        /**
         * Set CallAdapter.Factory locally, default RxJavaCallAdapterFactory.create()
         */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            if (factory != null) {
                callAdapterFactoryList.add(factory);
            }
            return this;
        }

        public Retrofit build() {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

            if(CommonUtils.isEmpty(baseUrl))
            {
                throw new BaseUrlException(101, "provide baseUrl using setBaseUrl(baseUrl)");
            }
            else if (okHttpClient == null)
            {
                throw new OkHttpClientException(102, "provide okHttpClient using setOkHttpClient(okHttpClient)");
            }
            else
            {
                retrofitBuilder.baseUrl(checkBaseUrl(baseUrl));
                retrofitBuilder.client(okHttpClient);

                if (converterFactoryList != null && converterFactoryList.size() != 0)
                {
                    for (Converter.Factory converterFactory : converterFactoryList) {
                        retrofitBuilder.addConverterFactory(converterFactory);
                    }
                }
                else
                {
                    /*
                     * Default, GSON converter factory
                     */
                    retrofitBuilder.addConverterFactory(GsonConverterFactory.create(GsonUtils.getGsonBuilder().create()));
                }

                if (callAdapterFactoryList != null && callAdapterFactoryList.size() != 0)
                {
                    for (CallAdapter.Factory adapterFactory : callAdapterFactoryList)
                    {
                        retrofitBuilder.addCallAdapterFactory(adapterFactory);
                    }
                }
                else
                {
                    /*
                     * Default, Rx call adapter factory
                     */
                    retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                }

                return retrofitBuilder.build();
            }
        }
    }

    public static class BaseUrlException extends RuntimeException {
        private int code;
        private String displayMessage;

        public BaseUrlException(Throwable throwable) {
            super(throwable);
        }

        public BaseUrlException(int code, String displayMessage) {
            this.code = code;
            this.displayMessage = displayMessage;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDisplayMessage() {
            return displayMessage;
        }

        public void setDisplayMessage(String displayMessage) {
            this.displayMessage = displayMessage;
        }
    }

    public static class OkHttpClientException extends RuntimeException {
        private int code;
        private String displayMessage;

        public OkHttpClientException(Throwable throwable) {
            super(throwable);
        }

        public OkHttpClientException(int code, String displayMessage) {
            this.code = code;
            this.displayMessage = displayMessage;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDisplayMessage() {
            return displayMessage;
        }

        public void setDisplayMessage(String displayMessage) {
            this.displayMessage = displayMessage;
        }
    }
}

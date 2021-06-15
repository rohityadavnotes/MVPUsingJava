package com.badasoftware.library.network.api;

import retrofit2.Retrofit;

public class ServiceGenerator {

    public static final String TAG = ServiceGenerator.class.getSimpleName();

    private static ServiceGenerator instance;

    public static ServiceGenerator getInstance() {
        if (instance == null) {
            synchronized (ServiceGenerator.class) {
                if (instance == null) {
                    instance = new ServiceGenerator();
                }
            }
        }
        return instance;
    }

    public <S> S createService(Class<S> serviceClass, Retrofit retrofit) {
        return retrofit.create(serviceClass);
    }
}

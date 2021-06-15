package com.badasoftware.library.network.interceptor;

import androidx.annotation.NonNull;
import com.badasoftware.library.network.body.ProgressRequestBody;
import com.badasoftware.library.network.body.ProgressRequestBodyListener;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressRequestBodyInterceptor implements Interceptor {

    private ProgressRequestBodyListener progressRequestBodyListener;

    public ProgressRequestBodyInterceptor(ProgressRequestBodyListener progressRequestBodyListener) {
        this.progressRequestBodyListener = progressRequestBodyListener;
        if (progressRequestBodyListener == null) {
            throw new NullPointerException("This progressRequestListener must not null");
        }
    }

    @NotNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (originalRequest.body() == null) {
            return chain.proceed(originalRequest);
        }

        Request.Builder builder = originalRequest.newBuilder();
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(originalRequest.body(), progressRequestBodyListener);
        builder.method(originalRequest.method(), progressRequestBody);
        Request progressRequest = builder.build();

        return chain.proceed(progressRequest);
    }
}

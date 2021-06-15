package com.badasoftware.library.network.interceptor;

import com.badasoftware.library.network.body.ProgressResponseBody;
import com.badasoftware.library.network.body.ProgressResponseBodyListener;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ProgressResponseBodyInterceptor implements Interceptor {

    private ProgressResponseBodyListener progressResponseBodyListener;

    public ProgressResponseBodyInterceptor(ProgressResponseBodyListener progressResponseBodyListener) {
        this.progressResponseBodyListener = progressResponseBodyListener;
        if (progressResponseBodyListener == null) {
            throw new NullPointerException("This progressRequestListener must not null");
        }
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), progressResponseBodyListener))
                .build();
    }
}

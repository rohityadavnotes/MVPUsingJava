package com.badasoftware.library.network.body;

import androidx.annotation.NonNull;
import java.io.IOException;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestBody extends RequestBody {

    private final RequestBody requestBody;
    private final ProgressRequestBodyListener progressRequestBodyListener;
    private long lastTime;

    /**
     * constructor, assignment
     *
     * @param requestBody
     * @param progressRequestBodyListener
     */
    public ProgressRequestBody(RequestBody requestBody, ProgressRequestBodyListener progressRequestBodyListener) {
        this.requestBody                    = requestBody;
        this.progressRequestBodyListener    = progressRequestBodyListener;
        if(requestBody == null)
        {
            throw new NullPointerException("This requestBody must not null");
        }
        else if (progressRequestBodyListener == null)
        {
            throw new NullPointerException("This progressRequestListener must not null");
        }
    }

    /**
     * Rewrite the contentType of the actual response body
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /**
     * Rewrite the contentLength that calls the actual response body
     *
     * @return contentLength
     */
    @Override
    public long contentLength() {
        try
        {
            return requestBody.contentLength();
        }
        catch (IOException iOException)
        {
            iOException.printStackTrace();
        }
        return -1;
    }

    /**
     * Rewrite to write
     *
     * @param sink
     * @throws IOException exception
     */
    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        requestBody.writeTo(bufferedSink);  /* recursive call */
        bufferedSink.flush();
    }

    private final class CountingSink extends ForwardingSink {

        /* The current number of bytes written */
        private long currentLength = 0L;

        /* total byte length, avoid calling the contentLength () method multiple times */
        private long totalLength = 0L;

        public CountingSink(Sink sink) {
            super(sink);
        }

        @Override
        public void write(@NonNull Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            /* Increase the number of bytes currently written */
            currentLength += byteCount;

            if (totalLength == 0) {
                /* Get the value of contentLength, no longer call later */
                totalLength = contentLength();
            }

            long currentTime = System.currentTimeMillis();

            if (currentTime - lastTime >= 100 || lastTime == 0 || currentLength == totalLength)
            {
                lastTime = currentTime;

                Observable.just(currentLength).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        progressRequestBodyListener.onRequestProgress(currentLength, totalLength, currentLength == totalLength);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        progressRequestBodyListener.onRequestFail(throwable);
                    }
                });
            }
        }
    }
}

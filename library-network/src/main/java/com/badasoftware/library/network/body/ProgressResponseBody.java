package com.badasoftware.library.network.body;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private ProgressResponseBodyListener progressResponseBodyListener;
    private BufferedSource bufferedSource;

    /**
     * constructor, assignment
     *
     * @param responseBody     The response body to be wrapped
     * @param progressResponseBodyListener callback interface
     */
    public ProgressResponseBody(ResponseBody responseBody, ProgressResponseBodyListener progressResponseBodyListener) {
        this.responseBody             = responseBody;
        this.progressResponseBodyListener = progressResponseBodyListener;
    }

    /**
     * Rewrite the contentType of the actual response body
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * Rewrite the contentLength that calls the actual response body
     *
     * @return contentLength
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    /**
     * Rewrite to wrap the source
     *
     * @return BufferedSource
     */
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            Source source = source(responseBody.source());
            bufferedSource = Okio.buffer(source);
        }
        return bufferedSource;
    }

    /**
     * Read, callback progress interface
     *
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {
        return new ForwardingSource(source) {

            /* The current number of bytes read */
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                /* Currently read the number of bytes */
                long bytesRead = super.read(sink, byteCount);

                /*
                 * read() returns the number of bytes read, or -1 if this source is exhausted.
                 * Increase the number of bytes currently read, if the reading is completed, bytesRead will return -1.
                 */
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;

                if (null != progressResponseBodyListener) {
                    /* callback, if contentLength() doesn't know the length, it will return -1 */
                    progressResponseBodyListener.onResponseProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                }

                return bytesRead;
            }
        };
    }
}

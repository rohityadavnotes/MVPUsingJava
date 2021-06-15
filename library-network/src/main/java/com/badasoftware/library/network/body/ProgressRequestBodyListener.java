package com.badasoftware.library.network.body;

/**
 * Request body progress callback interface, such as for file upload
 */
public interface ProgressRequestBodyListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean isDone);
    void onRequestFail(Throwable throwable);
}
package com.badasoftware.library.network.body;

/**
 * Response body progress callback interface, such as for file download
 */
public interface ProgressResponseBodyListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean isDone);
}
package com.badasoftware.library.network.picasso;

public interface PicassoImageLoadingListener {
    void imageLoadSuccess();
    void imageLoadError(Exception exception);
}
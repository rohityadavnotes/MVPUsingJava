package com.badasoftware.library.network;

import io.reactivex.disposables.Disposable;

public interface ApiListener<BaseResponse> {
        /**
         * Network request accept
         * Subscribe success
         *
         * @return string
         */
        void requestAccept(Disposable disposable);

        /**
         * Network request cancel
         * Unsubscribe success
         *
         * @return string
         */
        void requestCancel();

        /**
         * onNext callback
         * @param baseResponse
         */
        void onSuccess(BaseResponse baseResponse);

        /**
         * onError callback
         *
         * @param errorMessage
         */
        void onError(String errorMessage);
}
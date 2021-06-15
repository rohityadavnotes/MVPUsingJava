package com.badasoftware.library.network.helper;

import android.util.Log;
import com.badasoftware.library.network.func.RetryFunction;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxHelper {

    public static final String TAG = RxHelper.class.getSimpleName();

    private static RxHelper instance;

    public static RxHelper getInstance() {
        if (instance == null) {
            synchronized (RxHelper.class) {
                if (instance == null) {
                    instance = new RxHelper();
                }
            }
        }
        return instance;
    }

    public <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())

                        /* Update the UI on the main thread after the request is completed */
                        .observeOn(AndroidSchedulers.mainThread())

                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                /* Here you can do some operations yourself, such as judging the network connection status, etc. */
                                Log.e(TAG, "doOnSubscribe : "+disposable.isDisposed());
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.e(TAG, "doFinally");
                            }
                        });
            }
        };
    }

    public <T> ObservableTransformer<T, T> applySchedulers(int retryDelaySeconds, int maxRetries) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())

                        /* Update the UI on the main thread after the request is completed */
                        .observeOn(AndroidSchedulers.mainThread())

                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                /* Here you can do some operations yourself, such as judging the network connection status, etc. */
                                Log.e(TAG, "doOnSubscribe : "+disposable.isDisposed());
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.e(TAG, "doFinally");
                            }
                        })

                        .retryWhen(new RetryFunction(retryDelaySeconds, maxRetries));
            }
        };
    }

    /**
     * unsubscribe
     *
     * @param disposable subscription information
     */
    public void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            Log.e(TAG,"Call dispose(Disposable disposable)");
        }
    }
}

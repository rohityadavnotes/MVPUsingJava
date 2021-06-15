package com.badasoftware.library.network;

import android.app.Activity;
import com.badasoftware.library.network.exception.ExceptionHelper;
import com.badasoftware.library.network.exception.RemoteException;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public abstract class ApiObserver<T> implements io.reactivex.Observer<Response<T>> {

    private Activity activity;

    public ApiObserver(Activity activity, Boolean showDialog) {
        this.activity       = activity;
    }

    public ApiObserver(Activity activity) {
        this(activity,true);
    }

    public ApiObserver() {
    }

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        onBegin(disposable);
    }

    @Override
    public void onNext(@NonNull Response<T> response) {
        System.out.println("code : "+response.code());
        System.out.println("errorBody : "+response.errorBody());
        System.out.println("body : "+response.body());
        onSuccess(response);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        onFailure(ExceptionHelper.getInstance().handleException(throwable));
    }

    @Override
    public void onComplete() {
    }

    /**
     * OnSubscribe callback
     *
     * @param disposable
     */
    public abstract void onBegin(Disposable disposable);

    /**
     * onNext callback
     *
     * @param response
     */
    public abstract void onSuccess(Response<T> response);

    /**
     * onError callback
     *
     * @param remoteException
     */
    public abstract void onFailure(RemoteException remoteException);
}
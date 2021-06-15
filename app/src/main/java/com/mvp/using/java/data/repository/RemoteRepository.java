package com.mvp.using.java.data.repository;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import com.badasoftware.library.network.ApiListener;
import com.badasoftware.library.network.ApiObserver;
import com.badasoftware.library.network.ApiResponse;
import com.badasoftware.library.network.api.ServiceGenerator;
import com.badasoftware.library.network.exception.HttpStatusCode;
import com.badasoftware.library.network.exception.RemoteException;
import com.badasoftware.library.network.helper.RxHelper;
import com.mvp.using.java.data.remote.RemoteConfiguration;
import com.mvp.using.java.data.remote.service.RemoteService;
import com.mvp.using.java.model.direct.Base;
import com.mvp.using.java.model.withbase.Page;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RemoteRepository  {

    public static final String TAG = RemoteRepository.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static RemoteRepository instance;

    public static RemoteRepository getInstance() {
        if (instance == null) {
            synchronized (RemoteRepository.class) {
                if (instance == null) {
                    instance = new RemoteRepository();
                }
            }
        }
        return instance;
    }

    public void getEmployee(Activity activity, Retrofit retrofit, ApiListener<Base> networkListener) {
        RemoteService service = ServiceGenerator.getInstance().createService(RemoteService.class, retrofit);

        Observable<Response<Base>> observable = service.getEmployee();
        ApiObserver<Base> observer = new ApiObserver<Base>() {
            @Override
            public void onBegin(Disposable disposable) {
                networkListener.requestAccept(disposable);
            }

            @Override
            public void onSuccess(Response<Base> response) {
                if (response.code() == 200)
                {
                    Base baseResponse = response.body();
                    if (networkListener != null) {
                        networkListener.onSuccess(baseResponse);
                    }
                }
                else
                {
                    HttpStatusCode httpStatusCode   = HttpStatusCode.valueOf(response.code());
                    RemoteException remoteException = new RemoteException(httpStatusCode.value(), httpStatusCode.getReasonPhrase());

                    if (networkListener != null) {
                        networkListener.onError(remoteException.getDisplayMessage());
                    }
                }
            }

            @Override
            public void onFailure(RemoteException remoteException) {
                if (networkListener != null) {
                    networkListener.onError(remoteException.getDisplayMessage());
                }
            }
        };

        observable
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        activity.runOnUiThread(new Runnable() {
                            public void run()
                            {
                                if (networkListener != null){
                                    networkListener.requestCancel();
                                }
                            }
                        });
                    }
                })
                .compose(RxHelper.getInstance().<Response<Base>>applySchedulers(3, 3))
                /* Subscribe */
                .subscribe(observer);
    }

    public void getPage(Activity activity, Retrofit retrofit, ApiListener<ApiResponse<Page>> networkListener) {
        RemoteService service = ServiceGenerator.getInstance().createService(RemoteService.class, retrofit);

        Observable<Response<ApiResponse<Page>>> observable = service.getPage(RemoteConfiguration.API_KEY,"1");
        ApiObserver<ApiResponse<Page>> observer = new ApiObserver<ApiResponse<Page>>(activity) {
            @Override
            public void onBegin(Disposable disposable) {
                networkListener.requestAccept(disposable);
            }

            @Override
            public void onSuccess(Response<ApiResponse<Page>> response) {
                if (response.code() == 200)
                {
                    ApiResponse<Page> baseResponse = response.body();
                    if (networkListener != null) {
                        networkListener.onSuccess(baseResponse);
                    }
                }
                else
                {
                    HttpStatusCode httpStatusCode   = HttpStatusCode.valueOf(response.code());
                    RemoteException remoteException = new RemoteException(httpStatusCode.value(), httpStatusCode.getReasonPhrase());

                    if (networkListener != null) {
                        networkListener.onError(remoteException.getDisplayMessage());
                    }
                }
            }

            @Override
            public void onFailure(RemoteException remoteException) {
                if (networkListener != null) {
                    networkListener.onError(remoteException.getDisplayMessage());
                }
                Log.e(TAG, "onFailure(String errorMessage) : "+remoteException.getDisplayMessage());
            }
        };

        observable
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        activity.runOnUiThread(new Runnable() {
                            public void run()
                            {
                                if (networkListener != null) {
                                    networkListener.requestCancel();
                                }
                            }
                        });
                    }
                })
                .compose(RxHelper.getInstance().<Response<ApiResponse<Page>>>applySchedulers())
                /* Subscribe */
                .subscribe(observer);
    }
}
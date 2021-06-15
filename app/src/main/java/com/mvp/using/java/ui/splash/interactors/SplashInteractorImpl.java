package com.mvp.using.java.ui.splash.interactors;

import android.widget.Toast;

import com.badasoftware.library.utilities.ToastUtils;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SplashInteractorImpl implements DelayInteractor {

    public static final String TAG = SplashInteractorImpl.class.getSimpleName();

    @Override
    public void delay(long delay, DelayListener delayListener) {
        Observable.just(true)
                .delay(delay, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        delayListener.delayFinish();
                    }
                });
    }
}

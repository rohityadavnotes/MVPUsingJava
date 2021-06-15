package com.mvp.using.java.ui.splash.presenters;

import com.mvp.using.java.ui.base.presenters.BasePresenter;
import com.mvp.using.java.ui.splash.interactors.DelayInteractor;
import com.mvp.using.java.ui.splash.interactors.SplashInteractorImpl;
import com.mvp.using.java.ui.splash.views.SplashView;

public class SplashPresenterImpl<V extends SplashView> extends BasePresenter<V> implements SplashPresenter<V>,
        DelayInteractor.DelayListener {

    private DelayInteractor delayInteractor;

    public SplashPresenterImpl() {
        this.delayInteractor = new SplashInteractorImpl();
    }

    @Override
    public void onDelay(long delay) {
        if (getMvpView() != null) {
            delayInteractor.delay(delay, this);
        }
    }

    @Override
    public void delayFinish() {
        if (getMvpView() != null) {
            getMvpView().navigateToNextActivity();
        }
    }
}

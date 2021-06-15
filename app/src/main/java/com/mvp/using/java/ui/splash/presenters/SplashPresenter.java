package com.mvp.using.java.ui.splash.presenters;

import com.mvp.using.java.ui.androidmvp.MvpPresenter;
import com.mvp.using.java.ui.base.views.BaseActivityView;

public interface SplashPresenter<V extends BaseActivityView> extends MvpPresenter<V> {
    void onDelay(long delay);
}
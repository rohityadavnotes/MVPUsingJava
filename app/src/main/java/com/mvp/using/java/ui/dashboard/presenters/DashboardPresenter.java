package com.mvp.using.java.ui.dashboard.presenters;

import android.app.Activity;

import com.mvp.using.java.ui.androidmvp.MvpPresenter;
import com.mvp.using.java.ui.base.views.BaseActivityView;

public interface DashboardPresenter<V extends BaseActivityView> extends MvpPresenter<V> {
    void changeLanguage(String language, Activity activity);
    void exitAlert(Activity activity);
}
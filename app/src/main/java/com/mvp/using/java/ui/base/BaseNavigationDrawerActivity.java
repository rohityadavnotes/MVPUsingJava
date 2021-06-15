package com.mvp.using.java.ui.base;

import android.os.Bundle;
import android.util.Log;
import com.mvp.using.java.ui.androidmvp.MvpView;
import com.mvp.using.java.ui.base.presenters.BasePresenter;

public abstract class BaseNavigationDrawerActivity<V extends MvpView, P extends BasePresenter<V>> extends BaseMVPActivity<V, P> {

    public static final String TAG = BaseNavigationDrawerActivity.class.getSimpleName();

    /*
     ***********************************************************************************************
     ********************************* BaseMVPActivity abstract methods ****************************
     ***********************************************************************************************
     */
    public abstract void configureActionBarDrawerToggle();
    public abstract void configureNavigationView();
    public abstract void configureHeader();

    /*
     ***********************************************************************************************
     ********************************* Activity lifecycle methods **********************************
     ***********************************************************************************************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");

        if (presenter == null) {
            presenter = getPresenter();
        }
        presenter.attachView((V)this);

        super.onCreate(savedInstanceState);

        configureActionBarDrawerToggle();
        configureNavigationView();
        configureHeader();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy()");
        if (null != presenter)
            presenter.detachView();
        super.onDestroy();
    }
}

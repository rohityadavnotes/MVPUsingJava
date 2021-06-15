package com.mvp.using.java.ui.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.CallSuper;
import com.mvp.using.java.ui.androidmvp.MvpView;
import com.mvp.using.java.ui.base.presenters.BasePresenter;

public abstract class BaseMVPActivity<V extends MvpView, P extends BasePresenter<V>> extends BaseActivity {

    public static final String TAG = BaseMVPActivity.class.getSimpleName();

    /*
     ***********************************************************************************************
     ******************************************* Properties ****************************************
     ***********************************************************************************************
     */
    protected P presenter;

    /*
     ***********************************************************************************************
     ********************************* BaseMVPActivity abstract methods ****************************
     ***********************************************************************************************
     */
    public abstract P getPresenter();

    /*
     ***********************************************************************************************
     ********************************* Activity lifecycle methods **********************************
     ***********************************************************************************************
     */
    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");

        presenter = getPresenter();
        if (presenter != null) {
            presenter.attachView((V)this);
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        Log.i(TAG, "onDestroy()");
        if (null != presenter)
            presenter.detachView();
        super.onDestroy();
    }
}

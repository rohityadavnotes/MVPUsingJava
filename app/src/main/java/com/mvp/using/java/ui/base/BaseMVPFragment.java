package com.mvp.using.java.ui.base;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.mvp.using.java.ui.androidmvp.MvpView;
import com.mvp.using.java.ui.base.presenters.BasePresenter;

public abstract class BaseMVPFragment<V extends MvpView, P extends BasePresenter<V>> extends BaseFragment {

    public static final String TAG = BaseMVPFragment.class.getSimpleName();

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
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");

        presenter = getPresenter();
        if (presenter != null) {
            presenter.attachView((V)this);
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDestroy()");
        if (null != presenter)
            presenter.detachView();
        super.onDetach();
    }
}

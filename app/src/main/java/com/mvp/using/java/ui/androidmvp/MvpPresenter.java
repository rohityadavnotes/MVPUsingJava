package com.mvp.using.java.ui.androidmvp;

import androidx.annotation.NonNull;
import com.mvp.using.java.ui.base.presenters.BasePresenter;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface MvpPresenter<V extends MvpView> {

    void attachView(@NonNull V mvpView);

    void detachView();

    V getMvpView();

    boolean isViewAttached();

    void checkViewAttached() throws BasePresenter.MvpViewNotAttachedException;
}

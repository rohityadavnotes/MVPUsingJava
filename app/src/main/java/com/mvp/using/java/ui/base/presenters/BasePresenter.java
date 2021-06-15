package com.mvp.using.java.ui.base.presenters;

import androidx.annotation.NonNull;
import com.mvp.using.java.ui.androidmvp.MvpPresenter;
import com.mvp.using.java.ui.androidmvp.MvpView;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;

    @Override
    public V getMvpView() {
        return mMvpView;
    }

    @Override
    public void attachView(@NonNull V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mMvpView != null;
    }

    @Override
    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}

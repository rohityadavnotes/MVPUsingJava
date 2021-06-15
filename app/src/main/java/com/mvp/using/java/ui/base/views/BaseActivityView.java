package com.mvp.using.java.ui.base.views;

import androidx.annotation.StringRes;
import com.mvp.using.java.ui.androidmvp.MvpView;

public interface BaseActivityView extends MvpView {
    void showNoInternet();

    void hideNoInternet();

    void showProgressDialog();

    void hideProgressDialog();

    void showDialogMessage(String dialogTitle, String dialogMessage);

    void showDialogMessage(@StringRes int dialogTitleResId, @StringRes int dialogMessageResId);

    void showToast(String message);

    void showToast(@StringRes int messageResId);
}

package com.mvp.using.java.ui.dashboard.presenters;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;

import com.mvp.using.java.ui.base.presenters.BasePresenter;
import com.mvp.using.java.ui.dashboard.interactors.DashboardInteractorImpl;
import com.mvp.using.java.ui.dashboard.interactors.ExitAlertInteractor;
import com.mvp.using.java.ui.dashboard.interactors.SelectLanguageInteractor;
import com.mvp.using.java.ui.dashboard.views.DashboardView;

public class DashboardPresenterImpl<V extends DashboardView> extends BasePresenter<V> implements DashboardPresenter<V>,
        SelectLanguageInteractor.OnSelectLanguageListener,
        ExitAlertInteractor.OnExitAlertListener {

    private SelectLanguageInteractor selectLanguageInteractor;
    private ExitAlertInteractor exitAlertInteractor;

    public DashboardPresenterImpl() {
        this.selectLanguageInteractor = new DashboardInteractorImpl();
        this.exitAlertInteractor = new DashboardInteractorImpl();
    }

    @Override
    public void changeLanguage(String language, Activity activity) {
        if (getMvpView() != null) {
            selectLanguageInteractor.performLanguageSelect(language, activity, this);
        }
    }

    @Override
    public void onSelectedLanguage(String currentLanguage) {
        if (getMvpView() != null) {
            getMvpView().onCurrentLanguageSet(currentLanguage);
        }
    }

    @Override
    public void exitAlert(Activity activity) {
        if (getMvpView() != null) {
            exitAlertInteractor.performExitAlert(activity, this);
        }
    }

    @Override
    public void onYesExit(AlertDialog alertDialog) {
        if (getMvpView() != null) {
            getMvpView().onYesExit(alertDialog);
        }
    }

    @Override
    public void onCancel(AlertDialog alertDialog) {
        if (getMvpView() != null) {
            getMvpView().onCancel(alertDialog);
        }
    }
}

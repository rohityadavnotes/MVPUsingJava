package com.mvp.using.java.ui.dashboard.views;

import androidx.appcompat.app.AlertDialog;
import com.mvp.using.java.ui.base.views.BaseActivityView;

public interface DashboardView extends BaseActivityView {
    void onCurrentLanguageSet(String currentLanguage);
    void onYesExit(AlertDialog alertDialog);
    void onCancel(AlertDialog alertDialog);
}

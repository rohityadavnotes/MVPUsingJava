package com.mvp.using.java.ui.dashboard.interactors;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;

public interface ExitAlertInteractor {

    void performExitAlert(Activity activity, OnExitAlertListener exitAlertListener);

    interface OnExitAlertListener {
        void onYesExit(AlertDialog alertDialog);

        void onCancel(AlertDialog alertDialog);
    }
}

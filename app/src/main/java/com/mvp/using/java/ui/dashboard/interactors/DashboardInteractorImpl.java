package com.mvp.using.java.ui.dashboard.interactors;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.mvp.using.java.R;
import com.mvp.using.java.langauge.LocaleManager;

public class DashboardInteractorImpl implements SelectLanguageInteractor, ExitAlertInteractor {

    private static final String TAG = DashboardInteractorImpl.class.getSimpleName();

    @Override
    public void performLanguageSelect(String language, Activity activity, OnSelectLanguageListener selectLanguageListener) {
        LocaleManager.setNewLocale(activity, language);
        LocaleManager.recreate(activity, true);
        selectLanguageListener.onSelectedLanguage(LocaleManager.getLanguagePref(activity));
    }

    @Override
    public void performExitAlert(Activity activity, OnExitAlertListener exitAlertListener) {
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.exit_alert_dialog, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.EnterLeftExitRightAnimation;
        alertDialog.show();

        TextView no = dialogView.findViewById(R.id.noButtonTextView);
        TextView yes = dialogView.findViewById(R.id.yesButtonTextView);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitAlertListener.onCancel(alertDialog);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitAlertListener.onYesExit(alertDialog);
            }
        });
    }
}
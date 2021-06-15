package com.mvp.using.java.ui.dashboard.interactors;

import android.app.Activity;
import com.mvp.using.java.langauge.languagesupport.LanguagesSupport;

public interface SelectLanguageInteractor {

    void performLanguageSelect(@LanguagesSupport.Language String language, Activity activity, OnSelectLanguageListener selectLanguageListener);

    interface OnSelectLanguageListener {
        void onSelectedLanguage(String currentLanguage);
    }
}

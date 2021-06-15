package com.mvp.using.java.langauge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.mvp.using.java.langauge.languagesupport.LanguagesSupport;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import static android.os.Build.VERSION_CODES.N;

public class LocaleManager {

    private static final String LANGUAGE_KEY                            = "language_key";

    /**
     * Set Current Locale
     * @param context
     */
    public static Context setLocale(Context context) {
        return updateResources(context, getLanguagePref(context));
    }

    /**
     * Set new Locale
     * @param context
     */
    public static Context setNewLocale(Context context, @LanguagesSupport.Language String language) {
        setLanguagePref(context, language);
        return updateResources(context, language);
    }

    /**
     * Get saved Locale from SharedPreferences
     *
     * @param context current context
     * @return current locale key by (((((((((((((((((((( default return HINDI Locale ))))))))))))))
     */
    public static String getLanguagePref(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(LANGUAGE_KEY, LanguagesSupport.Language.HINDI);
    }

    /**
     * Saved Locale in SharedPreferences
     *
     * @param context current context
     */
    private static void setLanguagePref(Context context, String localeKey) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(LANGUAGE_KEY, localeKey).apply();
    }

    public static Context updateResources(Context context, String language) {

        /* Check if the new language is the system one */
        Configuration systemConfiguration = Resources.getSystem().getConfiguration();
        Locale locale = language.equals(LanguagesSupport.Language.SYSTEM) ? getSystemLocale(systemConfiguration) : new Locale(language);

        /* Sets the new default locale */
        Locale.setDefault(locale);

        /* Obtains resources and configuration from provided context */
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        /* Updates configuration depending on Android Version */
        if (atLeastApiLevel(24))
        {
            /*
             * Android JELLY_BEAN_MR1 :
             * public void setLocale (Locale loc) : Added in API level 17
             *
             * link : https://developer.android.com/reference/android/content/res/Configuration#setLocale(java.util.Locale)
             *
             * Android N
             * public void setLocales (LocaleList locales) : Added in API level 24
             *
             * link : https://developer.android.com/reference/android/content/res/Configuration#setLocales(android.os.LocaleList)
             *
             * Starting in Android 7.0 (API level 24), Android provides enhanced support for multilingual users, allowing them to select multiple locales in settings.
             * 1..... LocaleList API is introduced along with setLocales/getLocales in Configuration.
             * 2..... accessing locale variable gets deprecated in favor of getLocales().get(0).
             *
             * Note that setLocale starts invoking setLocales with a list of just one locale under the hood since API 24.
             */
            setLocaleForApi24(configuration, locale);
        }
        else if (atLeastApiLevel(17))
        {
            System.out.println("Locale preferences updated : " + locale.toString());
            configuration.setLocale(locale);
        }
        else
        {
            /*
             * Android Alpha to N
             * public Locale locale :
             *                        Added in API level 1
             *                        Deprecated in API level 24
             *
             * link : https://developer.android.com/reference/android/content/res/Configuration#locale
             *
             * This field was deprecated in API level 24. Do not set or read this directly.
             * Use getLocales() and setLocales(android.os.LocaleList).
             * If only the primary locale is needed, getLocales().get(0) is now the preferred accessor.
             */
            configuration.locale = locale;
        }

        if (atLeastApiLevel(17)) {
            System.out.println("Setting the layout direction");
            /*
             * Android JELLY_BEAN_MR1
             * public abstract Context createConfigurationContext (Configuration overrideConfiguration) : Added in API level 17
             *
             * https://developer.android.com/reference/android/content/res/Configuration#getLayoutDirection()
             */
            configuration.setLayoutDirection(locale);
        }

        if (atLeastApiLevel(17))
        {
            /*
             * Android JELLY_BEAN_MR1
             * public abstract Context createConfigurationContext (Configuration overrideConfiguration) : Added in API level 17
             *
             * https://developer.android.com/reference/android/content/Context#createConfigurationContext(android.content.res.Configuration)
             */
            context = context.createConfigurationContext(configuration);
        }
        else
        {
            /*
             * Android Alpha to N_MR1
             * public void updateConfiguration (Configuration config, DisplayMetrics metrics) :
             *                              Added in API level 1
             *                              Deprecated in API level 25.
             *
             * This method was deprecated in API level 25. Use Context.createConfigurationContext(Configuration).
             *
             * link : https://developer.android.com/reference/android/content/res/Resources#updateConfiguration(android.content.res.Configuration,%20android.util.DisplayMetrics)
             */
            resources.updateConfiguration(configuration, displayMetrics);
        }
        return context;
    }

    @RequiresApi(api = N)
    public static void setLocaleForApi24(Configuration configuration, Locale target) {
        Set<Locale> set = new LinkedHashSet<>();
        /* bring the target locale to the front of the list */
        set.add(target);

        LocaleList all = LocaleList.getDefault();
        for (int i = 0; i < all.size(); i++) {
            /* append other locales supported by the user */
            set.add(all.get(i));
        }
        Locale[] locales = set.toArray(new Locale[0]);

        LocaleList localeList = new LocaleList(locales);
        System.out.println("Locale preferences updated from N or N above : " + localeList.toString());
        configuration.setLocales(localeList);
    }

    /**
     * Simply compares the given version with the Build.VERSION.SDK_INT of the Android System.
     *
     * @param apiLevel
     *
     * @return 'True' if the current version is the
     * same or higher than the given one. Else, 'false' is returned.
     */
    public static boolean atLeastApiLevel(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    /**
     * Obtains the current locale being used by the system.
     *
     * @param resources the system resources (obtained from a {@link Context},
     *                  for example) from which the locale is obtained.
     *
     * @return {@code Locale} with the user configuration.
     */
    public static Locale getLocale(@NonNull Resources resources) {
        Configuration configuration = resources.getConfiguration();
        return atLeastApiLevel(24) ?
                configuration.getLocales().get(0) :
                configuration.locale;
    }

    /**
     * Obtains the Android system locale, even if the user has changed the
     * application one.
     *
     * @param configuration configuration from which the system locale will be
     *               obtained.
     *
     * @return {@code Locale} with the system language.
     */
    public static Locale getSystemLocale(@NonNull Configuration configuration) {
        Locale locale;
        if (atLeastApiLevel(24)) {
            locale = configuration.getLocales().get(0);
        } else {
            locale = configuration.locale;
        }
        return locale;
    }

    /**
     * Get system language
     * @return {@code Locale} with the default system language.
     */
    public static Locale getSystemLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public static void recreate(Activity activity, boolean animate) {
        Intent restartIntent = new Intent(activity, activity.getClass());
        restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle extras = activity.getIntent().getExtras();
        if (extras != null) {
            restartIntent.putExtras(extras);
        }

        if (animate) {
            ActivityCompat.startActivity(
                    activity,
                    restartIntent,
                    ActivityOptionsCompat
                            .makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out)
                            .toBundle()
            );
        } else {
            activity.startActivity(restartIntent);
            activity.overridePendingTransition(0, 0);
        }
        activity.finish();
    }
}

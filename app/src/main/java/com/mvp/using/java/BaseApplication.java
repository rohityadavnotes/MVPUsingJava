package com.mvp.using.java;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;;
import com.badasoftware.library.crashreporter.CrashHandler;
import com.badasoftware.library.network.Network;
import com.mvp.using.java.data.remote.RemoteConfiguration;
import com.mvp.using.java.di.component.DaggerApplicationComponent;
import com.mvp.using.java.langauge.LocaleManager;
import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class BaseApplication extends Application implements HasAndroidInjector {

    public static final String TAG = BaseApplication.class.getSimpleName();

    /***********************************************************************************************
     ************************ Method to get the [BaseApplication] instance. ************************
     **********************************************************************************************/
    private static BaseApplication INSTANCE;

    public static BaseApplication getInstance() {
        return INSTANCE;
    }

    /***********************************************************************************************
     ************************************* Implement Dagger 2 **************************************
     **********************************************************************************************/
    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
    /***********************************************************************************************
     ***************************** Application Lifecycle Methods ***********************************
     **********************************************************************************************/

    /* Called by the system when the device configuration changes */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LocaleManager.setLocale(this);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "PORTRAIT");
        }
    }

    /*
     * Called when the application is starting, before any other application objects have been
     * created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        Network.init(this, BuildConfig.DEBUG, RemoteConfiguration.BASE_URL);
        CrashHandler.getInstance().init(this);

        DaggerApplicationComponent.builder().application(this).build().inject(this);

        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
    }

    private static final class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
        public void onActivityCreated(Activity activity, Bundle bundle) {
            Log.i(TAG, "onActivityCreated:" + activity.getLocalClassName());
        }

        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, "onActivityDestroyed:" + activity.getLocalClassName());
        }

        public void onActivityPaused(Activity activity) {
            Log.i(TAG, "onActivityPaused:" + activity.getLocalClassName());
        }

        public void onActivityResumed(Activity activity) {
            Log.i(TAG, "onActivityResumed:" + activity.getLocalClassName());
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.i(TAG, "onActivitySaveInstanceState:" + activity.getLocalClassName());
        }

        public void onActivityStarted(Activity activity) {
            Log.i(TAG, "onActivityStarted:" + activity.getLocalClassName());
        }

        public void onActivityStopped(Activity activity) {
            Log.i(TAG, "onActivityStopped:" + activity.getLocalClassName());
        }
    }

    /*
     * This is called when the overall system is running low on memory, and would like actively
     * running processes to tighten their belts.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory()");
    }

    /*
     * Only for testing, not called in production. This method is for use in emulated process
     * environments. It will never be called on a production Android device.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "onTerminate()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(TAG, "onTrimMemory(int level)");
    }

    @Override
    protected void attachBaseContext(Context base) {
        try {
            super.attachBaseContext(LocaleManager.setLocale(base));
            MultiDex.install(base);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "attachBaseContext(Context base) From BaseApplication");
    }
}

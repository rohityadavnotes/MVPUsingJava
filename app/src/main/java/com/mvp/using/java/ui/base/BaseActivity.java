package com.mvp.using.java.ui.base;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.badasoftware.library.checknetworkconnectivity.NetworkStateChangeListener;
import com.badasoftware.library.checknetworkconnectivity.NetworkStateChangeReceiver;
import com.badasoftware.library.checknetworkconnectivity.RegisterAndUnregisterNetworkReceiver;
import com.badasoftware.library.utilities.StatusBarUtils;
import com.badasoftware.library.utilities.ToastUtils;
import com.badasoftware.library.utilities.activity.ActivityStackManager;
import com.mvp.using.java.langauge.LocaleManager;
import com.mvp.using.java.langauge.languagesupport.LanguagesSupport;
import com.mvp.using.java.ui.base.views.BaseActivityView;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseActivityView, BaseFragment.Callback {

    private static final String TAG = BaseActivity.class.getSimpleName();

    /*
     ***********************************************************************************************
     ******************************************* Properties ****************************************
     ***********************************************************************************************
     */
    private ActivityStackManager activityStackManager;
    private SparseArray<View> viewSparseArray;
    private RegisterAndUnregisterNetworkReceiver registerAndUnregisterNetworkReceiver;

    /*
     ***********************************************************************************************
     ********************************* BaseActivity abstract methods *******************************
     ***********************************************************************************************
     */
    @LayoutRes
    protected abstract int getLayoutID();

    protected abstract void initializeView();

    protected abstract void initializeObject();

    protected abstract void initializeToolBar();

    protected abstract void initializeCallbackListener();

    protected abstract void addTextChangedListener();

    protected abstract void setOnClickListener();

    protected abstract void handleClickEvent(View view);
    /*
     ***********************************************************************************************
     ********************************* Activity lifecycle methods **********************************
     ***********************************************************************************************
     */
    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");

        viewSparseArray = new SparseArray<>();

        setContentView(getLayoutID());

        registerAndUnregisterNetworkReceiver = new RegisterAndUnregisterNetworkReceiver(BaseActivity.this);
        checkInternetConnection();

        activityStackManager = ActivityStackManager.getActivityStackManager();
        activityStackManager.pushActivity(this);

        initializeView();
        initializeObject();
        initializeToolBar();
        initializeCallbackListener();
        addTextChangedListener();
        setOnClickListener();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    @CallSuper
    protected void onRestart() { /* Only called after onStop() */
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        registerAndUnregisterNetworkReceiver.registerNetworkReceiver(BaseActivity.this);
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        registerAndUnregisterNetworkReceiver.unregisterNetworkReceiver(BaseActivity.this);
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");

        activityStackManager.popActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "onBackPressed()");
    }

    /**
     * Find the view control and put it in the collection
     *
     * @param viewID the id of the view to be found
     * @param <E>    the returned view
     * @return
     */
    public <E extends View> E findView(int viewID) {
        E view = (E) viewSparseArray.get(viewID);
        if (view == null) {
            /* If the searched view is not in the collection, add it to the collection */
            view = (E) findViewById(viewID);
            if (view != null) {
                viewSparseArray.append(viewID, view);
            }
        }
        return view;
    }

    /**
     * View's click event
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        handleClickEvent(v);
    }

    /**
     * Set the click event to the view
     *
     * @param view The view bound to the event
     * @param <E>
     */
    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }
    /*
     ***********************************************************************************************
     ********************************************* Language ****************************************
     ***********************************************************************************************
     */
    /*
     * Android JELLY_BEAN_MR1 :
     * public void applyOverrideConfiguration (Configuration overrideConfiguration) : Added in API level 17
     *
     * link : https://developer.android.com/reference/android/view/ContextThemeWrapper.html#applyOverrideConfiguration(android.content.res.Configuration)
     */
    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 25) {
            Locale locale = LocaleManager.getSystemLocale(overrideConfiguration);
            overrideConfiguration.setLocale(locale);
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
        Log.i(TAG, "attachBaseContext(Context base) From BaseActivity");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(LanguagesSupport.Language.ARABIC.equals(LocaleManager.getLanguagePref(this)) ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        }
    }
    /*
     ***********************************************************************************************
     ************************************** Accessors methods **************************************
     ***********************************************************************************************
     */
    private void checkInternetConnection() {
        NetworkStateChangeReceiver.setNetworkStateChangeListener(new NetworkStateChangeListener() {
            @Override
            public void networkAvailable() {
                showNoInternet();
            }

            @Override
            public void networkUnavailable() {
                hideNoInternet();
            }
        });
    }

    @Override
    public void showNoInternet() {
       /* ToastUtils.show(
                getApplicationContext(),
                "Connected to Internet",
                Toast.LENGTH_SHORT);*/
    }

    @Override
    public void hideNoInternet() {
        ToastUtils.show(
                getApplicationContext(),
                "No Internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again",
                Toast.LENGTH_LONG);
    }

    @Override
    public void showProgressDialog() {
    }

    @Override
    public void hideProgressDialog() {
    }

    @Override
    public void showDialogMessage(String dialogTitle, String dialogMessage) {
    }

    @Override
    public void showDialogMessage(int dialogTitleResId, int dialogMessageResId) {
    }

    @Override
    public void showToast(String message) {
    }

    @Override
    public void showToast(int messageResId) {
    }
    /*
     ***********************************************************************************************
     **************************** Fragment attach detach callBack methods **************************
     ***********************************************************************************************
     */
    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
    }
    /*
     ***********************************************************************************************
     **************************** Fragment attach detach callBack methods **************************
     ***********************************************************************************************
     */
    public void exitApp(boolean alsoBackgroundProcess) {
        activityStackManager.AppExit(this, alsoBackgroundProcess);
    }

    public void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void setTranslucent(Activity activity, int statusBarAlpha) {
        StatusBarUtils.setTranslucent(activity, statusBarAlpha);
    }
}

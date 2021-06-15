package com.mvp.using.java.ui.dashboard;

import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import com.mvp.using.java.R;
import com.mvp.using.java.ui.base.BaseNavigationDrawerActivity;
import com.mvp.using.java.ui.dashboard.presenters.DashboardPresenterImpl;
import com.mvp.using.java.ui.dashboard.views.DashboardView;

public class DashboardActivity extends BaseNavigationDrawerActivity<DashboardView, DashboardPresenterImpl<DashboardView>> implements DashboardView {

    public static final String TAG = DashboardActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public DashboardPresenterImpl<DashboardView> getPresenter() {
        presenter = new DashboardPresenterImpl<>();
        return presenter;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void initializeView() {
    }

    @Override
    protected void initializeObject() {
       // presenter.exitAlert(ActivityManager.getInstance().getCurrentActivity());
        //presenter.exitAlert(ActivityStackManager.getActivityStackManager().getCurrentActivity());
    }

    @Override
    protected void initializeToolBar() {
    }

    @Override
    protected void initializeCallbackListener() {
    }

    @Override
    protected void addTextChangedListener() {
    }

    @Override
    protected void setOnClickListener() {
    }

    @Override
    protected void handleClickEvent(View view) {
    }

    @Override
    public void onCurrentLanguageSet(String currentLanguage) {
    }

    @Override
    public void onYesExit(AlertDialog alertDialog) {
        alertDialog.dismiss();
        exitApp(true);
    }

    @Override
    public void onCancel(AlertDialog alertDialog) {
        alertDialog.dismiss();
    }

    @Override
    public void configureActionBarDrawerToggle() {
    }

    @Override
    public void configureNavigationView() {
    }

    @Override
    public void configureHeader() {
    }
}
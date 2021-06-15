package com.mvp.using.java.ui.splash;

import android.os.Bundle;
import android.view.View;
import com.badasoftware.library.utilities.activity.ActivityUtils;
import com.mvp.using.java.R;
import com.mvp.using.java.ui.base.BaseMVPActivity;
import com.mvp.using.java.ui.dashboard.DashboardActivity;
import com.mvp.using.java.ui.splash.presenters.SplashPresenterImpl;
import com.mvp.using.java.ui.splash.views.SplashView;

public class SplashActivity extends BaseMVPActivity<SplashView, SplashPresenterImpl<SplashView>> implements SplashView {

    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public SplashPresenterImpl<SplashView> getPresenter() {
        presenter = new SplashPresenterImpl<>();
        return presenter;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initializeView() {
    }

    @Override
    protected void initializeObject() {
    }

    @Override
    protected void initializeToolBar() {
    }

    @Override
    protected void initializeCallbackListener() {
        presenter.onDelay(2);
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
    public void navigateToNextActivity() {
        ActivityUtils.launchActivity(SplashActivity.this, DashboardActivity.class);
    }
}
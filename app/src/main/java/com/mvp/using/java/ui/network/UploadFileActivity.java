package com.mvp.using.java.ui.network;

import android.os.Bundle;
import android.view.View;
import com.mvp.using.java.R;
import com.mvp.using.java.ui.base.BaseActivity;
import javax.inject.Inject;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class UploadFileActivity extends BaseActivity implements HasAndroidInjector {

    public static final String TAG = UploadFileActivity.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_upload_file;
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
}
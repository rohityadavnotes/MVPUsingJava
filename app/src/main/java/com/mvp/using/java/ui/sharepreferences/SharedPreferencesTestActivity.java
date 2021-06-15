package com.mvp.using.java.ui.sharepreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mvp.using.java.R;
import com.mvp.using.java.data.local.sharedpreferences.SharedPreferencesHelper;
import com.mvp.using.java.ui.base.BaseActivity;
import javax.inject.Inject;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class SharedPreferencesTestActivity extends BaseActivity implements HasAndroidInjector {

    public static final String TAG = SharedPreferencesTestActivity.class.getSimpleName();

    private TextView getMaterialButton;
    private TextView textView;

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_shared_preferences_test;
    }

    @Override
    protected void initializeView() {
        getMaterialButton = findViewById(R.id.getMaterialButton);
        textView = findViewById(R.id.textView);
    }

    @Override
    protected void initializeObject() {
        sharedPreferencesHelper.setEmail("email");
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
        getMaterialButton.setOnClickListener(this);
    }

    @Override
    protected void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.getMaterialButton:
                textView.setText(sharedPreferencesHelper.getEmail());
                break;
            default:
                break;
        }
    }
}
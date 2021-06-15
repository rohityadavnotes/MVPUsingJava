package com.mvp.using.java.di.builder;

import com.mvp.using.java.ui.network.DownloadFileActivity;
import com.mvp.using.java.ui.network.RequestTestActivity;
import com.mvp.using.java.ui.network.UploadFileActivity;
import com.mvp.using.java.ui.sharepreferences.SharedPreferencesTestActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract RequestTestActivity bindRequestTestActivity();

    @ContributesAndroidInjector
    abstract DownloadFileActivity bindDownloadFileActivity();

    @ContributesAndroidInjector
    abstract UploadFileActivity bindUploadFileActivity();

    @ContributesAndroidInjector
    abstract SharedPreferencesTestActivity bindSharedPreferencesTestActivity();
}

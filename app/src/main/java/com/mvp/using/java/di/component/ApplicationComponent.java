package com.mvp.using.java.di.component;

import android.app.Application;

import com.badasoftware.library.network.di.module.ContextModule;
import com.badasoftware.library.network.di.module.NetworkModule;
import com.mvp.using.java.BaseApplication;
import com.mvp.using.java.di.builder.ActivityBuilderModule;
import com.mvp.using.java.di.module.ApplicationModule;
import com.mvp.using.java.di.module.RemoteModule;

import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(
        modules = {
                /* Use AndroidInjectionModule.class if you're not using support library */
                AndroidInjectionModule.class,

                ContextModule.class,
                ApplicationModule.class,
                NetworkModule.class,
                RemoteModule.class,
                ActivityBuilderModule.class
        }
)
public interface ApplicationComponent extends AndroidInjector<BaseApplication> {

    /**
     * This Builder will be constructed by Dagger
     * @BindsInstance binds the current instance into the DI graph,
     * It means that we can use this instance anywhere in our DI graph
     */
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    @Override
    void inject(BaseApplication baseApplication);
}
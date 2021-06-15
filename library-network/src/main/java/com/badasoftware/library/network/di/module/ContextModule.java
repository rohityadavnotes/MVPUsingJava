package com.badasoftware.library.network.di.module;

import android.content.Context;
import com.badasoftware.library.network.Network;
import com.badasoftware.library.network.di.qualifier.ApplicationContext;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    @Provides
    @Singleton
    @ApplicationContext
    Context provideContext() {
        return Network.getContext();
    }
}
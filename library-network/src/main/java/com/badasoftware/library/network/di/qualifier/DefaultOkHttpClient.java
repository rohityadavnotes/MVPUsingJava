package com.badasoftware.library.network.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

@Documented
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultOkHttpClient {
}

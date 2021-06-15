package com.mvp.using.java.di.qualifier.local;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Documented
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLiteDatabaseFileName {
}
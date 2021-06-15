package com.mvp.using.java.di.module;

import com.mvp.using.java.data.local.realm.RealmDatabaseConstants;
import com.mvp.using.java.data.local.room.RoomDatabaseConstants;
import com.mvp.using.java.data.local.sharedpreferences.SharedPreferencesConstants;
import com.mvp.using.java.data.local.sqlite.SQLiteDatabaseConstants;
import com.mvp.using.java.di.qualifier.local.RealmDatabaseFileName;
import com.mvp.using.java.di.qualifier.local.RoomDatabaseFileName;
import com.mvp.using.java.di.qualifier.local.SQLiteDatabaseFileName;
import com.mvp.using.java.di.qualifier.local.SharedPreferencesFileName;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @SharedPreferencesFileName
    @Singleton
    String provideSharedPreferencesFileName() {
        return SharedPreferencesConstants.SHARED_PREFERENCES_FILE_NAME;
    }

    @Provides
    @SQLiteDatabaseFileName
    @Singleton
    String provideSQLiteDatabaseFileName() {
        return SQLiteDatabaseConstants.SQLite_DATABASE_FILE_NAME;
    }

    @Provides
    @RoomDatabaseFileName
    @Singleton
    String provideRoomDatabaseFileName() {
        return RoomDatabaseConstants.ROOM_DATABASE_FILE_NAME;
    }

    @Provides
    @RealmDatabaseFileName
    @Singleton
    String provideRealmDatabaseFileName() {
        return RealmDatabaseConstants.REALM_DATABASE_FILE_NAME;
    }
}

package com.krishigadmin.android.di.module;

import com.krishigadmin.android.di.qualifier.local.RoomDatabaseName;
import com.krishigadmin.android.di.qualifier.local.SharedPreferencesName;
import com.krishigadmin.android.ui.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LocalModule {

    @Provides
    @SharedPreferencesName
    @Singleton
    String provideSharedPreferencesFileName() {
        return AppConstants.SharedPreferences.SHARED_PREFERENCES_FILE_NAME;
    }

    @Provides
    @RoomDatabaseName
    @Singleton
    String provideRoomDatabaseFileName() {
        return  AppConstants.Database.ROOM_DATABASE_FILE_NAME;
    }
}

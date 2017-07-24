package com.danielkashin.yandexweatherapp.di.module;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.danielkashin.yandexweatherapp.data.database.SQLiteFactory;
import com.danielkashin.yandexweatherapp.data.database.WeatherDatabaseOpenHelper;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DatabaseModule {

  @Provides
  @Singleton
  public SQLiteOpenHelper provideSqLiteOpenHelper(Context context) {
    return new WeatherDatabaseOpenHelper(context);
  }

  @Provides
  @Singleton
  public StorIOSQLite provideStorIOSQLite(SQLiteOpenHelper sqLiteOpenHelper) {
    return SQLiteFactory.create(sqLiteOpenHelper);
  }

}

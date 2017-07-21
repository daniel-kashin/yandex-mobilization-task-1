package com.danielkashin.yandexweatherapp.di.module;

import com.danielkashin.yandexweatherapp.BuildConfig;
import com.danielkashin.yandexweatherapp.di.qualifiers.ApiKey;
import com.danielkashin.yandexweatherapp.di.qualifiers.DatabaseName;
import com.danielkashin.yandexweatherapp.di.qualifiers.DatabaseBuildNumber;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class KeystoreModule {

  @Provides
  @Singleton
  @ApiKey
  public String provideApiKey() {
    return BuildConfig.WEATHER_API_KEY;
  }

  @Provides
  @Singleton
  @DatabaseName
  public String provideDatabaseName() {
    return BuildConfig.WEATHER_DATABASE_NAME;
  }

  @Provides
  @Singleton
  @DatabaseBuildNumber
  public int provideDatabaseBuildNumber() {
    return BuildConfig.WEATHER_DATABASE_BUILD_NUMBER;
  }



}

package com.danielkashin.yandexweatherapp.di.module;

import android.content.Context;
import android.content.res.Resources;

import com.danielkashin.yandexweatherapp.data.resources.ResourceWeatherConverter;
import com.danielkashin.yandexweatherapp.data.resources.WeatherConverter;
import com.danielkashin.yandexweatherapp.data.settings.PreferencesSettingsService;
import com.danielkashin.yandexweatherapp.data.settings.SettingsService;
import com.danielkashin.yandexweatherapp.di.qualifiers.ApplicationPackageName;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ResourceModule {

  @Provides
  @Singleton
  @ApplicationPackageName
  public String provideApplicationPackageName(Context context) {
    return context.getPackageName();
  }

  @Provides
  @Singleton
  public Resources provideResources(Context context) {
    return context.getResources();
  }

  @Provides
  @Singleton
  public SettingsService provideSettingsService(Context context) {
    return new PreferencesSettingsService(context);
  }

  @Provides
  @Singleton
  public WeatherConverter provideWeatherConverter(Resources resources,
                                                  @ApplicationPackageName String packageName,
                                                  SettingsService settingsService) {
    return new ResourceWeatherConverter(resources, packageName, settingsService);
  }
}


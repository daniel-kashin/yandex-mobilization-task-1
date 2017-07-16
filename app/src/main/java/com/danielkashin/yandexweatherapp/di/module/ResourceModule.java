package com.danielkashin.yandexweatherapp.di.module;

import android.content.Context;

import com.danielkashin.yandexweatherapp.data.resources.ResourceWeatherConverter;
import com.danielkashin.yandexweatherapp.data.resources.WeatherConverter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ResourceModule {

  @Provides
  @Singleton
  public WeatherConverter provideResourceWeatherConverter(Context context) {
    return new ResourceWeatherConverter(context);
  }

}


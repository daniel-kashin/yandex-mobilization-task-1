package com.danielkashin.yandexweatherapp.di.module;

import com.danielkashin.yandexweatherapp.data.data_service.weather.OpenWeatherMapService;
import com.danielkashin.yandexweatherapp.data.data_service.weather.WeatherNetworkService;
import com.danielkashin.yandexweatherapp.di.tags.ApiKey;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;


@Module
public class NetworkModule {

  @Provides
  @Singleton
  public OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build();
  }

  @Provides
  @Singleton
  public WeatherNetworkService provideWeatherNetworkService(@ApiKey String apiKey,
                                                            OkHttpClient okHttpClient) {
    return OpenWeatherMapService.Factory.create(apiKey, okHttpClient);
  }

}

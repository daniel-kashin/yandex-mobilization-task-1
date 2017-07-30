package com.danielkashin.yandexweatherapp.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.danielkashin.yandexweatherapp.data.data_services.weather.remote.OpenWeatherMapService;
import com.danielkashin.yandexweatherapp.data.data_services.weather.remote.RemoteWeatherService;
import com.danielkashin.yandexweatherapp.data.managers.NetworkManager;
import com.danielkashin.yandexweatherapp.di.qualifiers.ApiKey;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;


@Module
public class NetworkModule {

  private final ChangeableBaseUrl baseUrl;

  public NetworkModule(@NonNull String baseUrl) {
    this.baseUrl= new ChangeableBaseUrl(baseUrl);
  }

  @Provides
  @Singleton
  @NonNull
  ChangeableBaseUrl provideChangeableBaseUrl(){
    return baseUrl;
  }

  @Provides
  @Singleton
  public RemoteWeatherService provideRemoteWeatherService(@ApiKey String apiKey, OkHttpClient okHttpClient) {
    return OpenWeatherMapService.Factory.create(baseUrl, apiKey, okHttpClient);
  }

  @Provides
  @Singleton
  public OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build();
  }

  @Provides
  @Singleton
  public NetworkManager provideNetworkManager(Context context) {
    return new NetworkManager(context);
  }


}

package com.danielkashin.yandexweatherapp.di.module;

import android.content.Context;

import com.danielkashin.yandexweatherapp.data.managers.NetworkManager;

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

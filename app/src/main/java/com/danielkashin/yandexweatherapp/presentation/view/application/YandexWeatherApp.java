package com.danielkashin.yandexweatherapp.presentation.view.application;

import android.app.Application;
import com.danielkashin.yandexweatherapp.di.component.ApplicationComponent;
import com.danielkashin.yandexweatherapp.di.component.DaggerApplicationComponent;
import com.danielkashin.yandexweatherapp.di.module.ApplicationModule;
import com.danielkashin.yandexweatherapp.di.module.KeystoreModule;
import com.danielkashin.yandexweatherapp.di.module.NetworkModule;


public class YandexWeatherApp extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();
  }

  public ApplicationComponent getApplicationComponent() {
    if (applicationComponent == null) {
      applicationComponent = buildApplicationComponent();
    }
    return applicationComponent;
  }

  // modules are deprecated because they are not used yet,
  // but we`ll definitely want to use those later
  @SuppressWarnings("deprecation")
  private ApplicationComponent buildApplicationComponent() {
    return DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(getApplicationContext()))
        .keystoreModule(new KeystoreModule())
        .networkModule(new NetworkModule())
        .build();
  }
}

package com.danielkashin.yandexweatherapp.presentation.view.application;

import android.app.Application;

import com.danielkashin.yandexweatherapp.data.constants.Endpoints;
import com.danielkashin.yandexweatherapp.data.services.refresh.CustomJobCreator;
import com.danielkashin.yandexweatherapp.di.component.ApplicationComponent;
import com.danielkashin.yandexweatherapp.di.component.DaggerApplicationComponent;
import com.danielkashin.yandexweatherapp.di.component.WeatherComponent;
import com.danielkashin.yandexweatherapp.di.module.ApplicationModule;
import com.danielkashin.yandexweatherapp.di.module.NetworkModule;
import com.danielkashin.yandexweatherapp.di.module.WeatherModule;
import com.evernote.android.job.JobManager;


public class YandexWeatherApp extends Application {

  private ApplicationComponent applicationComponent;
  private WeatherComponent weatherComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    // can be refactored later
    JobManager.create(this).addJobCreator(new CustomJobCreator());
  }


  public ApplicationComponent getApplicationComponent() {
    if (applicationComponent == null) {
      applicationComponent = buildApplicationComponent().build();
    }
    return applicationComponent;
  }

  public WeatherComponent getWeatherComponent() {
    if (weatherComponent == null) {
        weatherComponent = getApplicationComponent().plusWeatherComponent(new WeatherModule());
    }
    return weatherComponent;
  }

  protected DaggerApplicationComponent.Builder buildApplicationComponent() {
    return DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(getApplicationContext()))
    // This url may be changed dynamically for tests! See ChangeableBaseUrl.
        .networkModule(new NetworkModule(Endpoints.OPEN_WEATHER_MAP_BASE_URL));

  }
}

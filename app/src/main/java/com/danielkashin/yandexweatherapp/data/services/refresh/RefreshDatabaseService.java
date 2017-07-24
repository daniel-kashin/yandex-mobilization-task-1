package com.danielkashin.yandexweatherapp.data.services.refresh;

import android.app.IntentService;
import android.content.Intent;

import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.presentation.view.application.YandexWeatherApp;

import javax.inject.Inject;


public class RefreshDatabaseService extends IntentService {

  public static final String KEY_CLASS_NAME = RefreshDatabaseService.class.getSimpleName();

  @Inject
  WeatherRepository weatherRepository;

  public RefreshDatabaseService() {
    super(KEY_CLASS_NAME);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    ((YandexWeatherApp)getApplication()).getWeatherComponent()
        .inject(this);
  }

  @Override
  public void onDestroy() {
    weatherRepository = null;
    super.onDestroy();
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    try {
      weatherRepository.getWeather(true);
    } catch (ExceptionBundle e) {
      // do nothing
    }
  }
}

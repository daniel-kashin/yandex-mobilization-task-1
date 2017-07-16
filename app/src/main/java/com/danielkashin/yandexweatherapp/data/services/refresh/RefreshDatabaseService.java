package com.danielkashin.yandexweatherapp.data.services.refresh;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.presentation.view.application.YandexWeatherApp;
import java.util.Random;
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
  protected void onHandleIntent(@Nullable Intent intent) {
    try {
      Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(VIBRATOR_SERVICE);
      vibrator.vibrate(2000);

      Notification notification = new NotificationCompat.Builder(getApplicationContext())
          .setContentTitle("Service is now running")
          .setContentText("Service is now running")
          .setAutoCancel(true)
          .setSmallIcon(R.mipmap.ic_launcher)
          .setShowWhen(true)
          .setColor(Color.RED)
          .setLocalOnly(true)
          .build();
      NotificationManagerCompat.from(getApplicationContext())
          .notify(new Random().nextInt(), notification);

      weatherRepository.getWeather("Moscow", true);
    } catch (ExceptionBundle e) {
      e.printStackTrace();
      // do nothing
    }
  }
}

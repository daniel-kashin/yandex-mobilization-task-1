package com.danielkashin.yandexweatherapp.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;


public class PreferencesSettingsService implements SettingsService {

  private static final String KEY_TEMPERATURE_TYPE = "fahrenheit_degree";

  private final SharedPreferences sharedPreferences;


  public PreferencesSettingsService(Context context) {
    this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
  }


  @Override
  public Weather.TemperatureType getTemperatureType() {
    Weather.TemperatureType currentTemperatureType = Weather.TemperatureType.Celsius;
    if (sharedPreferences.contains(KEY_TEMPERATURE_TYPE)) {
      boolean isFahrenheit = sharedPreferences.getBoolean(KEY_TEMPERATURE_TYPE, false);
      if (isFahrenheit) {
        currentTemperatureType = Weather.TemperatureType.Fahrenheit;
      }
    }

    return currentTemperatureType;
  }

}

package com.danielkashin.yandexweatherapp.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.google.android.gms.location.places.Place;

import io.reactivex.Completable;
import io.reactivex.Single;


public class PreferencesSettingsService implements SettingsService {

  private static final String KEY_TEMPERATURE_TYPE = "fahrenheit_degree";
  public static final String KEY_CURRENT_CITY = "current_city";
  public static final String KEY_CURRENT_LATITUDE = "current_latitude";
  public static final String KEY_CURRENT_LONGITUDE = "current_longitude";

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

  @Override
  public String getCurrentCity() {
    return sharedPreferences.getString(KEY_CURRENT_CITY, "");
  }

  @Override
  public void saveCurrentCityName(String city) {
    sharedPreferences
            .edit()
            .putString(KEY_CURRENT_CITY, city)
            .apply();
  }

  @Override
  public double getCurrentCityLatitude() {
    return Double.valueOf(sharedPreferences.getString(KEY_CURRENT_LATITUDE, "0.0"));
  }
  @Override
  public double getCurrentCityLongitude() {
    return Double.valueOf(sharedPreferences.getString(KEY_CURRENT_LONGITUDE, "0.0"));
  }

  @Override
  public void saveCurrentCityLatitude(double value) {
     sharedPreferences
            .edit()
            .putString(KEY_CURRENT_LATITUDE, String.valueOf(value))
            .apply();
  }
  @Override
  public void saveCurrentCityLongitude(double value) {
     sharedPreferences
            .edit()
            .putString(KEY_CURRENT_LONGITUDE, String.valueOf(value))
            .apply();
  }

  @Override
  public Completable saveLocation(Place place) {
      Single<Place> placeSingle = Single.just(place);

          placeSingle.map(Place::getName)
                  .map(CharSequence::toString)
                  .subscribe(this::saveCurrentCityName);

          placeSingle
                  .map(Place::getLatLng)
                  .map(latLng -> latLng.latitude)
                  .subscribe(this::saveCurrentCityLatitude);

          placeSingle
                  .map(Place::getLatLng)
                  .map(latLng -> latLng.longitude)
                  .subscribe(this::saveCurrentCityLongitude);
    return Completable.complete();
  }

  @Override
  public boolean isFirstSetup() {
    return this.getCurrentCityLatitude() == 0.0 && this.getCurrentCityLongitude() ==0;
  }
}

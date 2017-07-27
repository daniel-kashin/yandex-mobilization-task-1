package com.danielkashin.yandexweatherapp.data.settings;


import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.google.android.gms.location.places.Place;

import io.reactivex.Completable;

public interface SettingsService {

  Weather.TemperatureType getTemperatureType();

  String getCurrentCity();
  void saveCurrentCityName(String city);

  double getCurrentCityLatitude();
  double getCurrentCityLongitude();

  void saveCurrentCityLatitude(double value);
  void saveCurrentCityLongitude(double value);

  Completable saveLocation(Place place);

  boolean isFirstSetup();
}

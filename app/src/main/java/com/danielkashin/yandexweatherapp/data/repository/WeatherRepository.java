package com.danielkashin.yandexweatherapp.data.repository;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;


public interface WeatherRepository {

  Weather getWeather(boolean forceRefresh) throws ExceptionBundle;

  Weather refreshWeather(Weather weather);

  Weather getCachedWeather();

  boolean checkCacheRelevance();

  void saveWeatherInCache(Weather weather);
}

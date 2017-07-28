package com.danielkashin.yandexweatherapp.data.data_services.weather.local;

import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;


public interface LocalWeatherService {

  DatabaseWeather getWeather(String city);

  void saveWeather(DatabaseWeather databaseWeather);

}

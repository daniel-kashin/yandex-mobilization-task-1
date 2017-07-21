package com.danielkashin.yandexweatherapp.data.data_services.weather.local;

import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGetObject;
import com.pushtorefresh.storio.sqlite.operations.put.PreparedPutObject;


public interface LocalWeatherService {

  PreparedGetObject<DatabaseWeather> getWeather(String city);

  PreparedPutObject<DatabaseWeather> saveWeather(DatabaseWeather databaseWeather);

}

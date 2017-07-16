package com.danielkashin.yandexweatherapp.data.resources;

import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;


public interface WeatherConverter {

  Weather getWeather(NetworkWeather networkWeather);

  Weather refreshWeather(Weather weather);

}

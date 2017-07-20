package com.danielkashin.yandexweatherapp.data.settings;


import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;

public interface SettingsService {

  Weather.TemperatureType getTemperatureType();

}

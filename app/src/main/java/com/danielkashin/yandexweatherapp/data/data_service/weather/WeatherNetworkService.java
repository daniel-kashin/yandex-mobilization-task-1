package com.danielkashin.yandexweatherapp.data.data_service.weather;

import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import retrofit2.Call;


public interface WeatherNetworkService {

  Call<NetworkWeather> getWeather(String city);

  void parseException(Exception exception) throws ExceptionBundle;

  void checkNetworkCodesForExceptions(int networkResponseCode) throws ExceptionBundle;

}

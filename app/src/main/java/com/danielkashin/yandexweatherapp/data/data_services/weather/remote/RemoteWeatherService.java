package com.danielkashin.yandexweatherapp.data.data_services.weather.remote;

import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import retrofit2.Call;


public interface RemoteWeatherService {

  Call<NetworkWeather> getWeather(String city);

  void parseException(Exception exception) throws ExceptionBundle;

  void checkNetworkCodesForExceptions(int networkResponseCode) throws ExceptionBundle;

}

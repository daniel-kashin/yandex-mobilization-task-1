package com.danielkashin.yandexweatherapp.data.data_services.weather.remote;

import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;

import java.io.IOException;

import retrofit2.Response;


public interface RemoteWeatherService {

  Response<NetworkWeather> getWeather(double latitude, double longitude) throws IOException;

  void parseException(Exception exception) throws ExceptionBundle;

  void checkNetworkCodesForExceptions(int networkResponseCode) throws ExceptionBundle;

}

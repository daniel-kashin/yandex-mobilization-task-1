package com.danielkashin.yandexweatherapp.data.contracts.remote;

import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.danielkashin.yandexweatherapp.data.constants.Endpoints.KEY_DATA;
import static com.danielkashin.yandexweatherapp.data.constants.Endpoints.KEY_V2_5;
import static com.danielkashin.yandexweatherapp.data.constants.Endpoints.KEY_WEATHER;


public interface OpenWeatherMapContract {

  @GET(KEY_DATA + KEY_V2_5 + KEY_WEATHER)
  Call<NetworkWeather> getWeather(
      @Query("lat") double latitude,
      @Query("lon") double longitude,
      @Query("APPID") String apiId
  );

}

package com.danielkashin.yandexweatherapp.data.contracts.remote;

import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import static com.danielkashin.yandexweatherapp.data.constants.Endpoints.OPEN_WEATHER_MAP_BASE_URL;
import static com.danielkashin.yandexweatherapp.data.constants.Endpoints.KEY_WEATHER;
import static com.danielkashin.yandexweatherapp.data.constants.Endpoints.KEY_DATA;
import static com.danielkashin.yandexweatherapp.data.constants.Endpoints.KEY_V2_5;


public interface OpenWeatherMapContract {

  @GET(KEY_DATA + KEY_V2_5 + KEY_WEATHER)
  Call<NetworkWeather> getWeather(
      @Query("q") String city,
      @Query("APPID") String apiId
  );

}

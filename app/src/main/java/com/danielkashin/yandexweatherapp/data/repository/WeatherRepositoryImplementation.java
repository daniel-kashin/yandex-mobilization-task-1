package com.danielkashin.yandexweatherapp.data.repository;

import com.danielkashin.yandexweatherapp.data.data_services.weather.local.LocalWeatherService;
import com.danielkashin.yandexweatherapp.data.data_services.weather.remote.RemoteWeatherService;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.data.managers.NetworkManager;
import com.danielkashin.yandexweatherapp.data.resources.WeatherConverter;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;

import java.util.concurrent.TimeUnit;

import retrofit2.Response;


public class WeatherRepositoryImplementation implements WeatherRepository {

  private final RemoteWeatherService remoteWeatherService;
  private final LocalWeatherService localWeatherService;
  private final WeatherConverter weatherConverter;
  private final NetworkManager networkManager;


  private WeatherRepositoryImplementation(RemoteWeatherService remoteWeatherService,
                                          LocalWeatherService localWeatherService,
                                          WeatherConverter weatherConverter,
                                          NetworkManager networkManager) {
    ExceptionHelper.checkAllObjectsNonNull(weatherConverter, remoteWeatherService,
        localWeatherService, networkManager);
    this.remoteWeatherService = remoteWeatherService;
    this.localWeatherService = localWeatherService;
    this.weatherConverter = weatherConverter;
    this.networkManager = networkManager;
  }


  @Override
  public Weather getWeather(String city, boolean forceRefresh) throws ExceptionBundle {
    try {
      if (networkManager.getStatus() == NetworkManager.NetworkStatus.DISCONNECTED) {
        throw new ExceptionBundle(ExceptionBundle.Reason.NETWORK_UNAVAILABLE);
      }

      Response<NetworkWeather> request = remoteWeatherService.getWeather(city).execute();
      remoteWeatherService.checkNetworkCodesForExceptions(request.code());
      NetworkWeather networkWeather = request.body();
      networkWeather.setTimestamp(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

      localWeatherService.saveWeather(weatherConverter.getDatabaseWeather(networkWeather))
          .executeAsBlocking();

      return weatherConverter.getWeather(networkWeather);
    } catch (Exception e) {
      if (forceRefresh) {
        remoteWeatherService.parseException(e);
        return null;
      } else {
        DatabaseWeather databaseWeather = localWeatherService.getWeather(city).executeAsBlocking();
        if (databaseWeather == null) {
          throw new ExceptionBundle(ExceptionBundle.Reason.EMPTY_DATA);
        } else {
          return weatherConverter.getWeather(databaseWeather);
        }
      }
    }
  }

  @Override
  public Weather refreshWeather(Weather weather) {
    return weatherConverter.refreshWeather(weather);
  }

  public static class Factory {

    private Factory() {
    }

    public static WeatherRepository create(RemoteWeatherService remoteWeatherService,
                                           LocalWeatherService localWeatherService,
                                           WeatherConverter weatherConverter,
                                           NetworkManager networkManager) {
      return new WeatherRepositoryImplementation(remoteWeatherService, localWeatherService,
          weatherConverter, networkManager);
    }
  }
}

package com.danielkashin.yandexweatherapp.data.repository;

import com.danielkashin.yandexweatherapp.data.data_services.weather.remote.WeatherRemoteService;
import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.data.managers.NetworkManager;
import com.danielkashin.yandexweatherapp.data.resources.WeatherConverter;
import com.danielkashin.yandexweatherapp.presentation.presenter.weather.WeatherPresenter;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;
import retrofit2.Response;


public class WeatherRepositoryImplementation implements WeatherRepository {

  private final WeatherRemoteService weatherRemoteService;
  private final WeatherConverter weatherConverter;
  private final NetworkManager networkManager;

  private WeatherRepositoryImplementation(WeatherRemoteService weatherRemoteService,
                                          WeatherConverter weatherConverter,
                                          NetworkManager networkManager) {
    ExceptionHelper.checkAllObjectsNonNull(weatherConverter, weatherRemoteService, networkManager);
    this.weatherRemoteService = weatherRemoteService;
    this.weatherConverter = weatherConverter;
    this.networkManager = networkManager;
  }

  @Override
  public Weather getWeather(String city, boolean forceRefresh) throws ExceptionBundle {
    try {
      if (networkManager.getStatus() == NetworkManager.NetworkStatus.DISCONNECTED) {
        throw new ExceptionBundle(ExceptionBundle.Reason.NETWORK_UNAVAILABLE);
      }

      Response<NetworkWeather> request = weatherRemoteService.getWeather(city).execute();
      weatherRemoteService.checkNetworkCodesForExceptions(request.code());
      NetworkWeather networkWeather = request.body();
      return weatherConverter.getWeather(networkWeather);
    } catch (Exception e) {
      weatherRemoteService.parseException(e);
      return null;
    }
  }

  @Override
  public Weather refreshWeather(Weather weather) {
    return weatherConverter.refreshWeather(weather);
  }

  public static class Factory {

    private Factory() {
    }

    public static WeatherRepository create(WeatherRemoteService weatherRemoteService,
                                           WeatherConverter weatherConverter,
                                           NetworkManager networkManager) {
      return new WeatherRepositoryImplementation(weatherRemoteService, weatherConverter, networkManager);
    }
  }
}

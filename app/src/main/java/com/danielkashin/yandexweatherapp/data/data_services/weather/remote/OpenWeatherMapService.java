package com.danielkashin.yandexweatherapp.data.data_services.weather.remote;


import android.support.annotation.NonNull;

import com.danielkashin.yandexweatherapp.data.constants.Endpoints;
import com.danielkashin.yandexweatherapp.data.contracts.remote.OpenWeatherMapContract;
import com.danielkashin.yandexweatherapp.data.data_services.base.BaseRemoteService;
import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;


public class OpenWeatherMapService extends BaseRemoteService<OpenWeatherMapContract>
    implements  RemoteWeatherService {

  private OpenWeatherMapService(String apiKey, OkHttpClient okHttpClient) {
    super(Endpoints.OPEN_WEATHER_MAP_BASE_URL, apiKey, okHttpClient);
  }

  // --------------------------------- RemoteWeatherService --------------------------------------

  @Override
  public Response<NetworkWeather> getWeather(double latitude, double longitude) throws IOException {
    return getService().getWeather(latitude, longitude, getApiKey()).execute();
  }

  @Override
  public void parseException(Exception exception) throws ExceptionBundle {
    if (exception instanceof ExceptionBundle) {
      throw (ExceptionBundle) exception;
    } else if (exception instanceof ConnectException || exception instanceof SocketTimeoutException
        || exception instanceof UnknownHostException || exception instanceof SSLException) {
      throw new ExceptionBundle(ExceptionBundle.Reason.NETWORK_UNAVAILABLE);
    } else {
      throw new ExceptionBundle(ExceptionBundle.Reason.UNKNOWN);
    }
  }

  @Override
  public void checkNetworkCodesForExceptions(int networkResponseCode) throws ExceptionBundle {
    switch (networkResponseCode) {
      case 200:
        // do nothing
        break;
      default:
        throw new ExceptionBundle(ExceptionBundle.Reason.API_ERROR);
    }
  }

  // ------------------------------------ BaseRemoteService --------------------------------------

  @Override
  @NonNull
  protected OpenWeatherMapContract createService(Retrofit retrofit) {
    return retrofit.create(OpenWeatherMapContract.class);
  }

  // --------------------------------------- factory ----------------------------------------------

  public static final class Factory {

    private Factory() {
    }

    public static RemoteWeatherService create(String apiKey, OkHttpClient okHttpClient) {
      return new OpenWeatherMapService(apiKey, okHttpClient);
    }
  }
}

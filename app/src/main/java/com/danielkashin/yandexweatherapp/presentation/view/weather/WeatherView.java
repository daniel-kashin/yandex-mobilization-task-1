package com.danielkashin.yandexweatherapp.presentation.view.weather;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.presentation.view.base.PresenterView;


public interface WeatherView extends PresenterView {

  void showWeather(Weather weather);

  void showNoInternet();

  void showNoData();

  void showLoading();

  void showRefreshButton();

  void onRefreshButtonClick();

}

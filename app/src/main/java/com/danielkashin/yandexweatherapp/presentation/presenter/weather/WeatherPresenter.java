package com.danielkashin.yandexweatherapp.presentation.presenter.weather;

import com.danielkashin.yandexweatherapp.presentation.presenter.base.BasicPresenter;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterFactory;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherView;


public class WeatherPresenter extends BasicPresenter<WeatherView> {


  public WeatherPresenter() {

  }

  // ------------------------------------- lifecycle ----------------------------------------------

  @Override
  protected void onViewDetached() {

  }

  @Override
  protected void onViewAttached() {

  }

  @Override
  protected void onDestroyed() {

  }

  // --------------------------------------- factory ----------------------------------------------

  public static class Factory implements PresenterFactory<WeatherPresenter, WeatherView> {

    @Override
    public WeatherPresenter create() {
      return new WeatherPresenter();
    }
  }
}

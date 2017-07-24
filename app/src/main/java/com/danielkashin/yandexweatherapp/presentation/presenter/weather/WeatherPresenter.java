package com.danielkashin.yandexweatherapp.presentation.presenter.weather;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.domain.use_cases.GetWeatherUseCase;
import com.danielkashin.yandexweatherapp.domain.use_cases.RefreshWeatherUseCase;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.BasePresenter;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterFactory;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherView;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;


public class WeatherPresenter extends BasePresenter<WeatherView>
    implements GetWeatherUseCase.Callbacks {

  private Weather cachedWeather;
  private boolean firstStart = true;

  private GetWeatherUseCase getWeatherUseCase;
  private RefreshWeatherUseCase refreshWeatherUseCase;


  public WeatherPresenter(GetWeatherUseCase getWeatherUseCase,
                          RefreshWeatherUseCase refreshWeatherUseCase) {
    ExceptionHelper.checkAllObjectsNonNull(getWeatherUseCase, refreshWeatherUseCase);
    this.getWeatherUseCase = getWeatherUseCase;
    this.refreshWeatherUseCase = refreshWeatherUseCase;
  }

  // -------------------------------------- public ------------------------------------------------

  public void onRefresh() {
    if (getView() != null && !getWeatherUseCase.isRunning()) {
      getView().showLoading();
    }
    getWeatherUseCase.run(this, "Moscow", false);
  }

  // ------------------------------------- lifecycle ----------------------------------------------

  @Override
  protected void onViewDetached() {
  }

  @Override
  protected void onViewAttached() {
    if (firstStart) {
      getWeatherUseCase.run(this, "Moscow", true);
      firstStart = false;
      getView().showLoading();
    } else if (cachedWeather != null) {
      getView().showWeather(refreshWeatherUseCase.run(cachedWeather));
    }

    if (getWeatherUseCase.isRunning()) {
      getView().showLoading();
    } else {
      getView().showRefreshButton();
    }
  }

  @Override
  protected void onDestroyed() {
    getWeatherUseCase.dismiss();
    getWeatherUseCase = null;
    cachedWeather = null;
  }

  // ---------------------------------- GetWeatherUseCase -----------------------------------------

  @Override
  public void onGetWeatherSuccess(Weather weather) {
    if (getView() != null) {
      getView().showRefreshButton();
    }

    cachedWeather = weather;
    if (getView() != null) {
      getView().showWeather(weather);
    }
  }

  @Override
  public void onGetWeatherException(ExceptionBundle exceptionBundle) {
    if (getView() != null) {
      getView().showRefreshButton();
    }

    if (exceptionBundle.getReason() == ExceptionBundle.Reason.NETWORK_UNAVAILABLE) {
      if (getView() != null) {
        getView().showNoInternet();
      }
    } else if (exceptionBundle.getReason() == ExceptionBundle.Reason.EMPTY_DATA) {
      if (getView() != null) {
        getView().showNoData();
      }
    }
  }

  // --------------------------------------- factory ----------------------------------------------

  public static class Factory implements PresenterFactory<WeatherPresenter, WeatherView> {

    private final GetWeatherUseCase getWeatherUseCase;
    private final RefreshWeatherUseCase refreshWeatherUseCase;

    public Factory(GetWeatherUseCase getWeatherUseCase, RefreshWeatherUseCase refreshWeatherUseCase) {
      this.getWeatherUseCase = getWeatherUseCase;
      this.refreshWeatherUseCase = refreshWeatherUseCase;
    }

    @Override
    public WeatherPresenter create() {
      return new WeatherPresenter(getWeatherUseCase, refreshWeatherUseCase);
    }
  }
}

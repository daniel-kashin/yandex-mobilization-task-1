package com.danielkashin.yandexweatherapp.presenter;

import com.danielkashin.yandexweatherapp.data.TestContract;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.domain.use_cases.CachedWeatherUseCases;
import com.danielkashin.yandexweatherapp.domain.use_cases.GetWeatherUseCase;
import com.danielkashin.yandexweatherapp.domain.use_cases.RefreshWeatherUseCase;
import com.danielkashin.yandexweatherapp.presentation.presenter.weather.WeatherPresenter;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
public class WeatherPresenterTest {

    @Mock private GetWeatherUseCase getWeatherUseCase;
    @Mock private RefreshWeatherUseCase refreshWeatherUseCase;
    @Mock private CachedWeatherUseCases cachedWeatherUseCase;
    @Mock private WeatherView weatherView;

    private WeatherPresenter weatherPresenter;
    private Weather expectedWeatherCel;
    private Weather expectedWeatherFar;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        expectedWeatherCel = TestContract.createWeatherCel();
        expectedWeatherFar = TestContract.createWeatherFar();
        weatherPresenter = new WeatherPresenter.Factory(getWeatherUseCase, refreshWeatherUseCase, cachedWeatherUseCase).create();
    }

    @Test
    public void viewAttachedWithCache(){
        when(cachedWeatherUseCase.cacheExists()).thenReturn(true);
        when(cachedWeatherUseCase.get()).thenReturn(expectedWeatherCel);
        when(refreshWeatherUseCase.run(expectedWeatherCel)).thenReturn(expectedWeatherFar);

        weatherPresenter.attachView(weatherView);

        verify(cachedWeatherUseCase).cacheExists();
        verify(refreshWeatherUseCase).run(expectedWeatherCel);
        verify(weatherView).showWeather(expectedWeatherFar);
    }

    @Test
    public void viewAttachedWithoutCache(){
        when(cachedWeatherUseCase.cacheExists()).thenReturn(false);

        weatherPresenter.attachView(weatherView);

        verify(cachedWeatherUseCase).cacheExists();
        verify(getWeatherUseCase).run(any(), anyBoolean());
        verify(weatherView).showLoading();
    }

    @Test
    public void viewAttachedIsRunning(){
        when(getWeatherUseCase.isRunning()).thenReturn(true);
        when(cachedWeatherUseCase.cacheExists()).thenReturn(true);

        weatherPresenter.attachView(weatherView);

        verify(weatherView).showLoading();
    }

    @Test
    public void viewAttachedNotRunning(){
        when(getWeatherUseCase.isRunning()).thenReturn(false);

        weatherPresenter.attachView(weatherView);

        verify(weatherView).showRefreshButton();
    }

    @Test
    public void viewDetached(){
        weatherPresenter.destroy();

        verify(getWeatherUseCase).dismiss();
    }

    @Test
    public void callbackSuccess(){
        weatherPresenter.attachView(weatherView);

        weatherPresenter.onGetWeatherSuccess(expectedWeatherCel);

        verify(cachedWeatherUseCase).save(expectedWeatherCel);
        verify(weatherView, atLeastOnce()).showRefreshButton();
        verify(weatherView).showWeather(expectedWeatherCel);
    }

    @Test
    public void callbackErrorNetwork(){
        weatherPresenter.attachView(weatherView);
        ExceptionBundle exceptionBundle = new ExceptionBundle(ExceptionBundle.Reason.NETWORK_UNAVAILABLE);

        weatherPresenter.onGetWeatherException(exceptionBundle);

        verify(weatherView, atLeastOnce()).showRefreshButton();
        verify(weatherView).showNoInternet();
    }

    @Test
    public void callbackErrorEmpty(){
        weatherPresenter.attachView(weatherView);
        ExceptionBundle exceptionBundle = new ExceptionBundle(ExceptionBundle.Reason.EMPTY_DATA);

        weatherPresenter.onGetWeatherException(exceptionBundle);

        verify(weatherView, atLeastOnce()).showRefreshButton();
        verify(weatherView).showNoData();
    }
}

package com.danielkashin.yandexweatherapp.data.repository;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.data.managers.NetworkManager;

import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
public class RepositoryTestErrors extends RepositoryTestCommon {
    @Test
    public void getWeatherForceError() throws ExceptionBundle, IOException {
        doThrow(new ConnectException()).when(remoteWeatherService).getWeather(anyDouble(), anyDouble());
        doThrow(new ExceptionBundle(ExceptionBundle.Reason.NETWORK_UNAVAILABLE)).when(remoteWeatherService).parseException(any());
        thrown.expect(ExceptionBundle.class);

        weatherRepository.getWeather(true);

        verify(remoteWeatherService).parseException(any());
        verify(localWeatherService, never()).getWeather(any());
    }

    @Test
    public void getWeatherNetworkError() throws ExceptionBundle, IOException {
        when(networkManager.getStatus()).thenReturn(NetworkManager.NetworkStatus.DISCONNECTED);
        thrown.expect(ExceptionBundle.class);

        weatherRepository.getWeather(false);

        verify(remoteWeatherService, never()).parseException(any());
        verify(localWeatherService).getWeather(any());
    }


    @Test
    public void getWeatherNotForceError() throws ExceptionBundle, IOException {
        doThrow(new ConnectException()).when(remoteWeatherService).getWeather(anyDouble(), anyDouble());
        thrown.expect(ExceptionBundle.class);

        weatherRepository.getWeather(false);

        verify(remoteWeatherService, never()).parseException(any());
        verify(localWeatherService).getWeather(any());
    }

    @Test
    public void getWeatherNotForceDbExists() throws ExceptionBundle, IOException {
        doThrow(new ConnectException()).when(remoteWeatherService).getWeather(anyDouble(), anyDouble());
        when(localWeatherService.getWeather(any())).thenReturn(weatherFromDB);
        when(weatherConverter.getWeather(weatherFromDB)).thenReturn(expectedWeather);

        Weather actualWeather = weatherRepository.getWeather(false);

        verify(remoteWeatherService, never()).parseException(any());
        verify(localWeatherService).getWeather(any());
        assertThat(actualWeather).isEqualTo(expectedWeather);
    }
}

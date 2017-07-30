package com.danielkashin.yandexweatherapp.data.repository;

import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
public class RepositoryTestHappyPath extends RepositoryTestCommon {
    @Test
    public void refreshDelegated() {
        weatherRepository.refreshWeather(expectedWeather);

        verify(weatherConverter).refreshWeather(expectedWeather);
    }

    @Test
    public void getWeatherSuccess() throws IOException, ExceptionBundle {
        when(remoteWeatherService.getWeather(anyDouble(), anyDouble())).thenReturn(Response.success(weatherNWResponse));

        weatherRepository.getWeather(true);

        verify(weatherConverter).getDatabaseWeather(weatherNWResponse);
        verify(localWeatherService).saveWeather(any());
        verify(weatherConverter).getWeather(weatherNWResponse);
    }
}

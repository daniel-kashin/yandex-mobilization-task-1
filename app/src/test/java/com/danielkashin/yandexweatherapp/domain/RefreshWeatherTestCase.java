package com.danielkashin.yandexweatherapp.domain;

import com.danielkashin.yandexweatherapp.data.TestContract;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.domain.use_cases.RefreshWeatherUseCase;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 *
 */
public class RefreshWeatherTestCase {

    @Mock
    private WeatherRepository weatherRepository;

    private RefreshWeatherUseCase useCase;
    private Weather expectedWeather;

    public Weather run(final Weather weather) {
        ExceptionHelper.checkAllObjectsNonNull(weather);
        return weatherRepository.refreshWeather(weather);
    }
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        expectedWeather = TestContract.createWeatherCel();
        useCase = new RefreshWeatherUseCase(weatherRepository);
    }

    @Test
    public void refreshDelegated(){
        useCase.run(expectedWeather);

        verify(weatherRepository).refreshWeather(expectedWeather);
    }
}

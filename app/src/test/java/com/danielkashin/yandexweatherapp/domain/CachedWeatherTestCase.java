package com.danielkashin.yandexweatherapp.domain;

import com.danielkashin.yandexweatherapp.data.TestContract;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.domain.use_cases.CachedWeatherUseCases;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 *
 */
public class CachedWeatherTestCase {

    @Mock
    private WeatherRepository weatherRepository;

    private CachedWeatherUseCases useCases;
    private Weather expectedWeather;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        useCases = new CachedWeatherUseCases(weatherRepository);
        expectedWeather = TestContract.createWeatherCel();
    }

    @Test
    public void allMethodsAreDelegated(){
        useCases.cacheExists();
        useCases.save(expectedWeather);
        useCases.get();

        verify(weatherRepository).saveWeatherInCache(expectedWeather);
        verify(weatherRepository).getCachedWeather();
        verify(weatherRepository).checkCacheRelevance();
    }

}

package com.danielkashin.yandexweatherapp.data.repository;

import org.junit.Test;

import static com.danielkashin.yandexweatherapp.data.TestContract.ANOTHER_TEST_CITY;
import static com.danielkashin.yandexweatherapp.data.TestContract.EXPECTED_TEST_CITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 *
 */
public class RepositoryTestCache extends RepositoryTestCommon {

    @Test
    public void cacheTest() {
        weatherRepository.saveWeatherInCache(expectedWeather);

        assertThat(weatherRepository.getCachedWeather()).isEqualTo(expectedWeather);
    }

    @Test
    public void cacheRelevanceFalseNull() {

        assertThat(weatherRepository.checkCacheRelevance()).isEqualTo(false);
    }

    @Test
    public void cacheRelevanceFalseDifferent() {
        when(settingsService.getCurrentCity()).thenReturn(ANOTHER_TEST_CITY);

        weatherRepository.saveWeatherInCache(expectedWeather);

        assertThat(weatherRepository.checkCacheRelevance()).isEqualTo(false);
    }

    @Test
    public void cacheRelevanceTrue() {
        when(settingsService.getCurrentCity()).thenReturn(EXPECTED_TEST_CITY);

        weatherRepository.saveWeatherInCache(expectedWeather);

        assertThat(weatherRepository.checkCacheRelevance()).isEqualTo(true);
    }
}

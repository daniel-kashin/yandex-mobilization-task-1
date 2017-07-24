package com.danielkashin.yandexweatherapp.domain.use_cases;

import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;

/**
 * 
 */
public class SelectCityUseCase   {
    private final WeatherRepository weatherRepository;

    public SelectCityUseCase(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }
}

package com.danielkashin.yandexweatherapp.domain.use_cases;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;

/**
 * 
 */
public class CachedWeatherUseCases {
    private final WeatherRepository weatherRepository;

    public CachedWeatherUseCases(WeatherRepository weatherRepository) {
        ExceptionHelper.checkAllObjectsNonNull(weatherRepository);
        this.weatherRepository = weatherRepository;
    }

    public Weather get() {
        return weatherRepository.getCachedWeather();
    }

    public void save(Weather weather) {
        weatherRepository.saveWeatherInCache(weather);
    }

    public boolean status(){
        return weatherRepository.checkCacheRelevance();
    }
}

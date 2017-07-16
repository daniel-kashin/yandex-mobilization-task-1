package com.danielkashin.yandexweatherapp.domain.use_cases;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;


public class RefreshWeatherUseCase {

  private final WeatherRepository weatherRepository;

  public RefreshWeatherUseCase(WeatherRepository weatherRepository) {
    ExceptionHelper.checkAllObjectsNonNull(weatherRepository);
    this.weatherRepository = weatherRepository;
  }

  public Weather run(final Weather weather) {
    ExceptionHelper.checkAllObjectsNonNull(weather);
    return weatherRepository.refreshWeather(weather);
  }
}

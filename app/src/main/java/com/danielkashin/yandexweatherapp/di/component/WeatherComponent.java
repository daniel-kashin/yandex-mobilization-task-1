package com.danielkashin.yandexweatherapp.di.component;

import com.danielkashin.yandexweatherapp.di.module.WeatherModule;
import com.danielkashin.yandexweatherapp.di.scopes.WeatherScope;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherFragment;

import dagger.Subcomponent;


@Subcomponent(modules = {WeatherModule.class})
@WeatherScope
public interface WeatherComponent {

  void inject(WeatherFragment weatherFragment);

}

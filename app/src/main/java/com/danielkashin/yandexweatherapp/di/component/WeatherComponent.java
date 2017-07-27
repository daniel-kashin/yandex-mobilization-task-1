package com.danielkashin.yandexweatherapp.di.component;

import com.danielkashin.yandexweatherapp.data.services.refresh.RefreshDatabaseService;
import com.danielkashin.yandexweatherapp.di.module.WeatherModule;
import com.danielkashin.yandexweatherapp.di.scopes.WeatherScope;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.MainDrawerActivity;
import com.danielkashin.yandexweatherapp.presentation.view.settings.SettingsFragment;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherFragment;

import dagger.Subcomponent;


@Subcomponent(modules = {WeatherModule.class})
@WeatherScope
public interface WeatherComponent {

  void inject(WeatherFragment weatherFragment);
  void inject(SettingsFragment settingsFragment);
  void inject(MainDrawerActivity mainDrawerActivity);

  void inject(RefreshDatabaseService refreshDatabaseService);

}

package com.danielkashin.yandexweatherapp.di.component;

import com.danielkashin.yandexweatherapp.di.module.ApplicationModule;
import com.danielkashin.yandexweatherapp.di.module.KeystoreModule;
import com.danielkashin.yandexweatherapp.di.module.NetworkModule;
import com.danielkashin.yandexweatherapp.di.module.ResourceModule;
import com.danielkashin.yandexweatherapp.di.module.WeatherModule;

import javax.inject.Singleton;
import dagger.Component;


@Component(modules = {ApplicationModule.class, KeystoreModule.class, NetworkModule.class, ResourceModule.class})
@Singleton
public interface ApplicationComponent {

  WeatherComponent plusWeatherComponent(WeatherModule presenterModule);

}

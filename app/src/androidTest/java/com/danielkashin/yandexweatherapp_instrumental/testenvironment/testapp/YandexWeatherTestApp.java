package com.danielkashin.yandexweatherapp_instrumental.testenvironment.testapp;

import com.danielkashin.yandexweatherapp.di.component.DaggerApplicationComponent;
import com.danielkashin.yandexweatherapp.presentation.view.application.YandexWeatherApp;

/**
 *
 */
public class YandexWeatherTestApp extends YandexWeatherApp {

    @Override
    protected DaggerApplicationComponent.Builder buildApplicationComponent() {
        return super.buildApplicationComponent();
    }
}

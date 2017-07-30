package com.danielkashin.yandexweatherapp_instrumental.testenvironment;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import com.danielkashin.yandexweatherapp.presentation.view.application.YandexWeatherApp;

public class TestUtils {

    private TestUtils() {
        throw new IllegalStateException("No instances, please");
    }

    @NonNull
    public static YandexWeatherApp app() {
        return (YandexWeatherApp) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }
}

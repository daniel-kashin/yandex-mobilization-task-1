package com.danielkashin.yandexweatherapp_instrumental.testenvironment.testapp;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 *
 */
public class CustomAndroidTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return Instrumentation.newApplication(YandexWeatherTestApp.class, context);
    }
}

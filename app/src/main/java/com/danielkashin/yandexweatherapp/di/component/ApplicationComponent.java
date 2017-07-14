package com.danielkashin.yandexweatherapp.di.component;


import com.danielkashin.yandexweatherapp.di.module.ApplicationModule;
import com.danielkashin.yandexweatherapp.di.module.KeystoreModule;
import com.danielkashin.yandexweatherapp.di.module.NetworkModule;

import dagger.Component;

@Component(modules = {ApplicationModule.class, KeystoreModule.class, NetworkModule.class})
public interface ApplicationComponent {



}

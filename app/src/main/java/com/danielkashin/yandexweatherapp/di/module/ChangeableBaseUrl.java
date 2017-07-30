package com.danielkashin.yandexweatherapp.di.module;

import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicReference;

import okhttp3.HttpUrl;

/**
 *
 */
public class ChangeableBaseUrl {
    @NonNull
    private final AtomicReference<HttpUrl> baseUrl;

    public ChangeableBaseUrl(@NonNull String baseUrl) {
        this.baseUrl = new AtomicReference<>(HttpUrl.parse(baseUrl));
    }

    public void setBaseUrl(@NonNull String baseUrl) {
        this.baseUrl.set(HttpUrl.parse(baseUrl));
    }

     @NonNull
    public HttpUrl url() {
        return baseUrl.get();
    }
}

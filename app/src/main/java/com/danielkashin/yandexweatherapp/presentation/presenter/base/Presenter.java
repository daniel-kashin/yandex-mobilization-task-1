package com.danielkashin.yandexweatherapp.presentation.presenter.base;


interface Presenter<V> {

  void attachView(V view);

  void detachView();

  void destroy();

}

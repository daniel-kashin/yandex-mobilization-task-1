package com.danielkashin.yandexweatherapp.presentation.presenter.base;


interface IPresenter<V> {

  void attachView(V view);

  void detachView();

  void destroy();

}

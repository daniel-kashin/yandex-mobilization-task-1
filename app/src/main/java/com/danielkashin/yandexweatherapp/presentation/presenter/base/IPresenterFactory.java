package com.danielkashin.yandexweatherapp.presentation.presenter.base;

import com.danielkashin.yandexweatherapp.presentation.view.base.IView;


public interface IPresenterFactory<P extends Presenter<V>, V extends IView> {

  P create();

}
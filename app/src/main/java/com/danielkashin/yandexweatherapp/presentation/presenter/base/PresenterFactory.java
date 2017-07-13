package com.danielkashin.yandexweatherapp.presentation.presenter.base;

import com.danielkashin.yandexweatherapp.presentation.view.base.PresenterView;


public interface PresenterFactory<P extends BasicPresenter<V>, V extends PresenterView> {

  P create();

}
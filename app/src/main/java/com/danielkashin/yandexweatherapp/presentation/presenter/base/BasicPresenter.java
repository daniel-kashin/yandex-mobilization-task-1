package com.danielkashin.yandexweatherapp.presentation.presenter.base;

import com.danielkashin.yandexweatherapp.presentation.view.base.PresenterView;


public abstract class BasicPresenter<V extends PresenterView> implements Presenter<V> {

  private V view;

  // ----------------------------------------------------------------------------------------------

  protected final V getView() {
    return this.view;
  }

  // --------------------------------- Presenter methods -----------------------------------------

  @Override
  public void attachView(V view) {
    this.view = view;
    this.onViewAttached();
  }

  @Override
  public void detachView() {
    this.onViewDetached();
    this.view = null;
  }

  @Override
  public void destroy() {
    this.onDestroyed();
  }

  // ----------------------------------- abstract methods -----------------------------------------

  protected abstract void onViewDetached();

  protected abstract void onViewAttached();

  protected abstract void onDestroyed();
}
package com.danielkashin.yandexweatherapp.presentation.presenter.base;

import android.content.Context;
import android.support.v4.content.Loader;
import com.danielkashin.yandexweatherapp.presentation.view.base.PresenterView;


public class PresenterLoader<P extends BasePresenter<V>, V extends PresenterView> extends Loader<P> {

  private final PresenterFactory<P, V> factory;
  private P presenter;


  public PresenterLoader(Context context, PresenterFactory<P, V> factory) {
    super(context);

    if (factory == null) {
      throw new IllegalStateException("BasePresenter factory must be non null");
    }

    this.factory = factory;
  }

  // ---------------------------------- public methods --------------------------------------------

  // get presenter that was already uploaded
  public final P getPresenter() {
    return presenter;
  }

  // ---------------------------------- Loader methods --------------------------------------------

  @Override
  protected void onStartLoading() {
    if (presenter != null) {
      // return created presenter
      deliverResult(presenter);
    } else {
      // create presenter using factory and return it
      forceLoad();
    }
  }

  @Override
  protected void onForceLoad() {
    presenter = factory.create();
    deliverResult(presenter);
  }

  @Override
  protected void onReset() {
    // called when activity/fragment is destroyed forever
    if (presenter != null) {
      presenter.destroy();
    }

    presenter = null;
  }
}
package com.danielkashin.yandexweatherapp.presentation.presenter.base;

import android.content.Context;
import android.support.v4.content.Loader;
import com.danielkashin.yandexweatherapp.presentation.view.base.IView;


public class PresenterLoader<P extends Presenter<V>, V extends IView> extends Loader<P> {

  private final IPresenterFactory<P, V> factory;
  private P presenter;


  public PresenterLoader(Context context, IPresenterFactory<P, V> factory) {
    super(context);

    if (factory == null) {
      throw new IllegalStateException("Presenter factory must be non null");
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
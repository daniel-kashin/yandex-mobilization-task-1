package com.danielkashin.yandexweatherapp.presentation.view.base;


import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.danielkashin.yandexweatherapp.presentation.presenter.base.BasePresenter;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterFactory;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterLoader;

public abstract class PresenterActivity<P extends BasePresenter<V>, V extends PresenterView>
    extends AppCompatActivity implements PresenterView, LoaderManager.LoaderCallbacks<P> {

  private P presenter;

  protected final BasePresenter<V> getPresenter() {
    return presenter;
  }

  // -------------------------------------- lifecycle ---------------------------------------------

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutRes());

    // loader is created -> get presenter
    Loader<P> loader = getSupportLoaderManager().getLoader(getActivityId());
    if (loader != null) {
      presenter = ((PresenterLoader<P, V>) loader).getPresenter();
    }

    // init loader
    if (presenter == null) {
      getSupportLoaderManager().initLoader(getActivityId(), null, this);
    }

    initializeView();
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.attachView(getViewInterface());
  }

  @Override
  protected void onStop() {
    presenter.detachView();
    super.onStop();
  }

  // ------------------------------ LoaderManager.LoaderCallbacks ---------------------------------

  @Override
  public Loader<P> onCreateLoader(int i, Bundle bundle) {
    return new PresenterLoader<>(this, getPresenterFactory());
  }

  @Override
  public void onLoadFinished(Loader<P> loader, P presenter) {
    this.presenter = presenter;
  }

  @Override
  public void onLoaderReset(Loader<P> loader) {
    this.presenter = null;
  }

  // --------------------------------------- abstract ---------------------------------------------

  protected abstract V getViewInterface();

  protected abstract PresenterFactory<P, V> getPresenterFactory();

  protected abstract int getActivityId();

  protected abstract int getLayoutRes();

  protected abstract void initializeView();

}
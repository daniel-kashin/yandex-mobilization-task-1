package com.danielkashin.yandexweatherapp.presentation.view.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danielkashin.yandexweatherapp.presentation.presenter.base.BasePresenter;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterFactory;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterLoader;


public abstract class PresenterFragment<P extends BasePresenter<V>, V extends PresenterView>
    extends Fragment implements PresenterView, LoaderManager.LoaderCallbacks<P> {

  private P presenter;

  protected final BasePresenter<V> getPresenter() {
    return presenter;
  }

  // -------------------------------------- lifecycle ---------------------------------------------

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // loader is created -> get presenter
    Loader<P> loader = getLoaderManager().getLoader(getFragmentId());
    if (loader != null) {
      presenter = ((PresenterLoader<P, V>) loader).getPresenter();
    }

    // init loader
    if (presenter == null) {
      getLoaderManager().initLoader(getFragmentId(), null, this);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    super.onCreateView(inflater, parent, savedInstanceState);
    return inflater.inflate(getLayoutRes(), parent, false);
  }

  @Override
  public final void onViewCreated(android.view.View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initializeView(view);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    destroyView();
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.attachView(getViewInterface());
  }

  @Override
  public void onStop() {
    presenter.detachView();
    super.onStop();
  }

  // ------------------------------ LoaderManager.LoaderCallbacks ---------------------------------

  @Override
  public Loader<P> onCreateLoader(int i, Bundle bundle) {
    return new PresenterLoader<>(getContext(), getPresenterFactory());
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

  protected abstract int getFragmentId();

  protected abstract int getLayoutRes();

  protected abstract void initializeView(android.view.View view);

  protected abstract void destroyView();

}

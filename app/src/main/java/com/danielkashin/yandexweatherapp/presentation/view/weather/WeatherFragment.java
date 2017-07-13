package com.danielkashin.yandexweatherapp.presentation.view.weather;

import android.content.Context;
import android.view.View;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterFactory;
import com.danielkashin.yandexweatherapp.presentation.presenter.weather.WeatherBasicPresenter;
import com.danielkashin.yandexweatherapp.presentation.view.base.PresenterFragment;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.TitleContainer;


public class WeatherFragment extends PresenterFragment<WeatherBasicPresenter, IWeatherPresenterView>
    implements IWeatherPresenterView {


  // ------------------------------------- newInstance --------------------------------------------

  public static WeatherFragment newInstance() {
    return new WeatherFragment();
  }

  // -------------------------------------- lifecycle ---------------------------------------------

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if (!(context instanceof TitleContainer)) {
      throw new IllegalStateException("Activity must be an instance of TitleContainer");
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    ((TitleContainer) getActivity()).setTitle(getString(R.string.main_drawer_weather));
  }

  // --------------------------------------- BaseFragment -----------------------------------------

  @Override
  protected IWeatherPresenterView getViewInterface() {
    return this;
  }

  @Override
  protected PresenterFactory<WeatherBasicPresenter, IWeatherPresenterView> getPresenterFactory() {
    return null;
  }

  @Override
  protected int getFragmentId() {
    return this.getClass().getSimpleName().hashCode();
  }

  @Override
  protected int getLayoutRes() {
    return R.layout.fragment_weather;
  }

  @Override
  protected void initializeView(View view) {

  }

}
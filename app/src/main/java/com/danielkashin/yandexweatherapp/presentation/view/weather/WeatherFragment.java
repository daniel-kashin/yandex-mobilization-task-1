package com.danielkashin.yandexweatherapp.presentation.view.weather;

import android.os.Bundle;
import android.view.View;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.IPresenterFactory;
import com.danielkashin.yandexweatherapp.presentation.presenter.weather.WeatherPresenter;
import com.danielkashin.yandexweatherapp.presentation.view.base.PresenterFragment;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.ITitleContainer;


public class WeatherFragment extends PresenterFragment<WeatherPresenter, IWeatherView>
    implements IWeatherView {


  // ------------------------------------- newInstance --------------------------------------------

  public static WeatherFragment newInstance() {
    return new WeatherFragment();
  }

  // -------------------------------------- lifecycle ---------------------------------------------

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!(getActivity() instanceof ITitleContainer)) {
      throw new IllegalStateException("Activity must be an instance of ITitleContainer");
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    ((ITitleContainer) getActivity()).setTitle(getString(R.string.main_drawer_weather));
  }

  // --------------------------------------- BaseFragment -----------------------------------------

  @Override
  protected IWeatherView getViewInterface() {
    return this;
  }

  @Override
  protected IPresenterFactory<WeatherPresenter, IWeatherView> getPresenterFactory() {
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
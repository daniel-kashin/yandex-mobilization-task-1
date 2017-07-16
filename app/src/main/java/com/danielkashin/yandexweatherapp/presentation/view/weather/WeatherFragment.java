package com.danielkashin.yandexweatherapp.presentation.view.weather;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterFactory;
import com.danielkashin.yandexweatherapp.presentation.presenter.weather.WeatherPresenter;
import com.danielkashin.yandexweatherapp.presentation.view.application.YandexWeatherApp;
import com.danielkashin.yandexweatherapp.presentation.view.base.PresenterFragment;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.ToolbarContainer;

import javax.inject.Inject;


public class WeatherFragment extends PresenterFragment<WeatherPresenter, WeatherView>
    implements WeatherView {

  @Inject
  PresenterFactory<WeatherPresenter, WeatherView> presenterFactory;

  private TextView textDescription;
  private TextView textMainTemperature;
  private TextView textAdditionalTemperature;
  private ImageView imageCondition;
  private TextView textWind;
  private TextView textHumidity;
  private TextView textPressure;
  private TextView textCloudiness;
  private RelativeLayout layoutOverlay;
  private ScrollView scrollWeather;

  // ------------------------------------- newInstance --------------------------------------------

  public static WeatherFragment newInstance() {
    return new WeatherFragment();
  }

  // -------------------------------------- lifecycle ---------------------------------------------

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if (!(context instanceof ToolbarContainer)) {
      throw new IllegalStateException("Activity must be an instance of ToolbarContainer");
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((YandexWeatherApp) getActivity().getApplication())
        .getWeatherComponent()
        .inject(this);
  }

  @Override
  public void onStart() {
    ((ToolbarContainer) getActivity()).setTitle(getString(R.string.main_drawer_weather));
    super.onStart();
  }

  // --------------------------------------- BaseFragment -----------------------------------------

  @Override
  protected WeatherView getViewInterface() {
    return this;
  }

  @Override
  protected PresenterFactory<WeatherPresenter, WeatherView> getPresenterFactory() {
    return presenterFactory;
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
    textDescription = (TextView) view.findViewById(R.id.text_description);
    textMainTemperature = (TextView) view.findViewById(R.id.text_main_temperature);
    textAdditionalTemperature = (TextView) view.findViewById(R.id.text_additional_temperature);
    imageCondition = (ImageView) view.findViewById(R.id.image_condition);
    textWind = (TextView) view.findViewById(R.id.text_wind);
    textHumidity = (TextView) view.findViewById(R.id.text_humidity);
    textPressure = (TextView) view.findViewById(R.id.text_pressure);
    textCloudiness = (TextView) view.findViewById(R.id.text_cloudiness);
    layoutOverlay = (RelativeLayout) view.findViewById(R.id.layout_overlay);
    scrollWeather = (ScrollView) view.findViewById(R.id.scroll_weather);
  }

  @Override
  protected void destroyView() {
    textDescription = null;
    textMainTemperature = null;
    textAdditionalTemperature = null;
    imageCondition = null;
    textWind = null;
    textHumidity = null;
    textPressure = null;
    textCloudiness = null;
    layoutOverlay = null;
    scrollWeather = null;
  }

  // ---------------------------------------- WeatherView -----------------------------------------

  @Override
  public void showWeather(Weather weather) {
    String degree = getString(R.string.degree);
    String arrowDown = getString(R.string.arrow_down);
    String arrowUp = getString(R.string.arrow_up);
    String wind = getString(R.string.wind);
    String humidity = getString(R.string.humidity);
    String pressure = getString(R.string.pressure);
    String cloudiness = getString(R.string.cloudiness);

    textDescription.setText(weather.conditionDescription);
    textMainTemperature.setText("" + weather.mainTemperature + degree);
    textAdditionalTemperature.setText(arrowDown + weather.minTemperature + degree
        + "  " + arrowUp + weather.maxTemperature + degree);
    imageCondition.setBackgroundResource(weather.conditionIconId);
    textWind.setText(wind + ": " + weather.windSummary);
    textHumidity.setText(humidity + ": " + weather.humidity + "%");
    textPressure.setText(pressure + ": " + weather.pressure);
    textCloudiness.setText(cloudiness + ": " + weather.cloudiness + "%");

    layoutOverlay.setVisibility(View.GONE);
    scrollWeather.setVisibility(View.VISIBLE);
  }

  @Override
  public void showNoInternet() {
    Toast.makeText(getContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showNoData() {
    scrollWeather.setVisibility(View.GONE);
    layoutOverlay.setVisibility(View.VISIBLE);
  }

  @Override
  public void showLoading() {
    ((ToolbarContainer) getActivity()).toggleIcon(true, false);
  }

  @Override
  public void showRefreshButton() {
    ((ToolbarContainer) getActivity()).toggleIcon(false, true);
  }

  @Override
  public void onRefreshButtonClick() {
    if (getPresenter() != null) {
      ((WeatherPresenter)getPresenter()).onRefresh();
    }
  }
}
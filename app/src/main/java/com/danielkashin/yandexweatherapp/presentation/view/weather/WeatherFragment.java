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
import javax.inject.Provider;


public class WeatherFragment extends PresenterFragment<WeatherPresenter, WeatherView>
    implements WeatherView {

  private static String KEY_OVERLAY_LAYOUT_VISIBLE = "KEY_OVERLAY_LAYOUT_VISIBLE";
  private static String KEY_WEATHER_LAYOUT_VISIBLE = "KEY_WEATHER_LAYOUT_VISIBLE";

  private TextView textCityName;
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

  @Inject
  Provider<PresenterFactory<WeatherPresenter, WeatherView>> presenterFactoryProvider;

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

  @Override
  public void onSaveInstanceState(Bundle outState) {
    if (layoutOverlay != null && scrollWeather != null) {
      outState.putBoolean(KEY_OVERLAY_LAYOUT_VISIBLE, layoutOverlay.getVisibility() == View.VISIBLE);
      outState.putBoolean(KEY_WEATHER_LAYOUT_VISIBLE, scrollWeather.getVisibility() == View.VISIBLE);
    }
    super.onSaveInstanceState(outState);
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
    textCityName.setText(weather.cityName + " (" + getString(R.string.updated) +" "
        + weather.date + ")");
    textDescription.setText(weather.conditionDescription);
    textMainTemperature.setText("" + weather.mainTemperature + degree);
    textAdditionalTemperature.setText(arrowDown + weather.minTemperature + degree
        + "  " + arrowUp + weather.maxTemperature + degree);
    imageCondition.setBackgroundResource(weather.conditionIconId);
    textWind.setText(wind + ": " + weather.windSummary);
    textHumidity.setText(humidity + ": " + weather.humidity + "%");
    textPressure.setText(pressure + ": " + weather.pressure + " " + getString(R.string.mmHg));
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

  // --------------------------------------- BaseFragment -----------------------------------------

  @Override
  protected WeatherView getViewInterface() {
    return this;
  }

  @Override
  protected PresenterFactory<WeatherPresenter, WeatherView> getPresenterFactory() {
    return presenterFactoryProvider.get();
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
  protected void initializeView(View view, Bundle savedInstanceState) {
    textCityName = (TextView) view.findViewById(R.id.text_city_name);
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

    if (savedInstanceState != null && savedInstanceState.containsKey(KEY_OVERLAY_LAYOUT_VISIBLE)
        && savedInstanceState.containsKey(KEY_WEATHER_LAYOUT_VISIBLE)){
      layoutOverlay.setVisibility(savedInstanceState.getBoolean(KEY_OVERLAY_LAYOUT_VISIBLE)
          ? View.VISIBLE : View.GONE);
      scrollWeather.setVisibility(savedInstanceState.getBoolean(KEY_WEATHER_LAYOUT_VISIBLE)
          ? View.VISIBLE : View.GONE);
    }
  }

  @Override
  protected void destroyView() {
    textCityName = null;
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
}
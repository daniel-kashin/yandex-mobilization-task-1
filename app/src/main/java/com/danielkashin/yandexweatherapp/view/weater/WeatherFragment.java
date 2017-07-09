package com.danielkashin.yandexweatherapp.view.weater;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.view.main_drawer.ITitleContainer;


public class WeatherFragment extends Fragment {

  public static WeatherFragment getInstance() {
    return new WeatherFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!(getActivity() instanceof ITitleContainer)) {
      throw new IllegalStateException("Activity must be an instance of ITitleContainer");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_weather, container, false);
  }

  @Override
  public void onStart() {
    super.onStart();
    ((ITitleContainer) getActivity()).setTitle(getString(R.string.main_drawer_weather));
  }
}
package com.danielkashin.yandexweatherapp.view.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.view.main_drawer.ITitleContainer;


public class SettingsFragment extends PreferenceFragmentCompat {

  public static SettingsFragment getInstance() {
    return new SettingsFragment();
  }

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
    ((ITitleContainer) getActivity()).setTitle(getString(R.string.main_drawer_settings));
  }

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    addPreferencesFromResource(R.xml.settings);
  }

}
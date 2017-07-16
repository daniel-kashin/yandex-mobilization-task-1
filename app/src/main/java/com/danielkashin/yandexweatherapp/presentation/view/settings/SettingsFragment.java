package com.danielkashin.yandexweatherapp.presentation.view.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.ToolbarContainer;


public class SettingsFragment extends PreferenceFragmentCompat {

  // ------------------------------------- newInstance --------------------------------------------

  public static SettingsFragment newInstance() {
    return new SettingsFragment();
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
  public void onStart() {
    ((ToolbarContainer) getActivity()).setTitle(getString(R.string.main_drawer_settings));
    super.onStart();
  }

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.settings, rootKey);
  }

}

package com.danielkashin.yandexweatherapp.presentation.view.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.ITitleContainer;


public class SettingsFragment extends PreferenceFragmentCompat {


  // ------------------------------------- newInstance --------------------------------------------

  public static SettingsFragment newInstance() {
    return new SettingsFragment();
  }

  // -------------------------------------- lifecycle ---------------------------------------------

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if (!(context instanceof ITitleContainer)) {
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

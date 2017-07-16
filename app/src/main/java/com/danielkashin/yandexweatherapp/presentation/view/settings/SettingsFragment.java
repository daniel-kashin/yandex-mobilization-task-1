package com.danielkashin.yandexweatherapp.presentation.view.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.data.services.refresh.RefreshDatabaseJob;
import com.danielkashin.yandexweatherapp.data.services.refresh.RefreshDatabaseManager;
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
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initializeView(view);
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


  private void initializeView(View view) {
    findPreference("refresh_period").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override
      public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (newValue instanceof String) {
          RefreshDatabaseManager.Period period = RefreshDatabaseManager.getPeriod((String)newValue);
          RefreshDatabaseManager.setCurrentRefreshPolicy(period);
          return true;
        } else {
          return false;
        }
      }
    });
  }
}

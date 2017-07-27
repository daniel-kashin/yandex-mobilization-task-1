package com.danielkashin.yandexweatherapp.presentation.view.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;
import android.widget.Toast;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.data.services.refresh.RefreshDatabaseManager;
import com.danielkashin.yandexweatherapp.data.settings.SettingsService;
import com.danielkashin.yandexweatherapp.presentation.view.application.YandexWeatherApp;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.ToolbarContainer;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static com.danielkashin.yandexweatherapp.data.settings.PreferencesSettingsService.KEY_CURRENT_CITY;


public class SettingsFragment extends PreferenceFragmentCompat {

  Preference selectCityButton;

  private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE_FRAGMENT = 1312;
  @Inject SettingsService settingsService;
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
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((YandexWeatherApp) getActivity().getApplication())
            .getWeatherComponent()
            .inject(this);
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
    findPreference("refresh_period").setOnPreferenceChangeListener(
        new Preference.OnPreferenceChangeListener() {
      @Override
      public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (newValue instanceof String) {
          String[] periods = getResources().getStringArray(R.array.refresh_intervals_identifiers);
          int periodIndex = -1;
          for (int i = 0; i < periods.length; ++i) {
            if (periods[i].equals(newValue)) {
              periodIndex = i;
              break;
            }
          }
          if (periodIndex == -1) throw new IllegalStateException("New period value must be defined");

          RefreshDatabaseManager.Period period = RefreshDatabaseManager.getPeriod(periodIndex);
          RefreshDatabaseManager.setCurrentRefreshPolicy(period);
          return true;
        } else {
          throw new IllegalStateException("New value can not be non String");
        }
      }
    });
    selectCityButton = findPreference("current_city");
    initializeSummary(selectCityButton, R.string.current_city, KEY_CURRENT_CITY);

    selectCityButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override
      public boolean onPreferenceClick(Preference preference) {
        selectCity();
        return true;
      }
    });
  }

  //-------------------------------------Find City-----------------------------------------------
  public void selectCity() {
    try {
      AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
              .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
              .build();
      Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
              .setFilter(typeFilter)
              .build(getActivity());
      startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_FRAGMENT);

    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
      Toast.makeText(getActivity(), getString(R.string.no_gsm), Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_FRAGMENT && resultCode == RESULT_OK) {
      settingsService.saveLocation(PlaceAutocomplete.getPlace(getActivity(), data));
      initializeSummary(selectCityButton, R.string.current_city, KEY_CURRENT_CITY);
    }
  }

  private void initializeSummary(Preference view, int resId, String key){
      view.setSummary(getString(resId) + " " + view.getSharedPreferences().getString(key, " "));
  }
}

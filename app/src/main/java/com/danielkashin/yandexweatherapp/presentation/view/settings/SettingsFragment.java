package com.danielkashin.yandexweatherapp.presentation.view.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.data.services.refresh.RefreshDatabaseManager;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.ToolbarContainer;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import static android.app.Activity.RESULT_OK;


public class SettingsFragment extends PreferenceFragmentCompat {

  Preference selectCityButton;

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
    initializeSummary(selectCityButton, R.string.current_city, CURRENT_CITY);

    selectCityButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override
      public boolean onPreferenceClick(Preference preference) {
        selectCity();
        return true;
      }
    });
  }

  //-------------------------------------Find City-----------------------------------------------
  private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1312;
  private static final String CURRENT_CITY = "curr_city";

  public void selectCity() {
    try {
      AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
              .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
              .build();
      Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
              .setFilter(typeFilter)
              .build(getActivity());
      startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
      Toast.makeText(getActivity(), getString(R.string.no_gsm), Toast.LENGTH_SHORT).show();
    }
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
        Place place = PlaceAutocomplete.getPlace(getActivity(), data);
        saveCity(place.getName().toString());
        Log.d("myLogs", "onActivityResult: " + place.getLatLng());
        initializeSummary(selectCityButton, R.string.current_city, CURRENT_CITY);
    }
  }

  private void saveCity(String newCity) {
    selectCityButton
            .getPreferenceManager()
            .getSharedPreferences()
            .edit()
            .putString(CURRENT_CITY, newCity)
            .apply();
  }

  private void initializeSummary(Preference view, int resId, String key){
      view.setSummary(getString(resId) + " " + view.getSharedPreferences().getString(key, " "));
  }
}

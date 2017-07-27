package com.danielkashin.yandexweatherapp.presentation.view.main_drawer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.data.settings.SettingsService;
import com.danielkashin.yandexweatherapp.presentation.view.about.AboutFragment;
import com.danielkashin.yandexweatherapp.presentation.view.application.YandexWeatherApp;
import com.danielkashin.yandexweatherapp.presentation.view.settings.SettingsFragment;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherFragment;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


@SuppressWarnings("FieldCanBeLocal")
// view fields are quite often useful in the whole activity scope
public class MainDrawerActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, ToolbarContainer {

  private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1312;

  private ImageView imageRefresh;
  private ProgressBar progressBar;
  private TextView textToolbar;
  private Toolbar toolbar;
  private DrawerLayout drawerLayout;
  private NavigationView navigationView;
  private ActionBarDrawerToggle drawerToggle;

  private boolean toolbarNavigationListenerIsRegistered;
  CompositeDisposable compositeDisposable = new CompositeDisposable();

  @Inject SettingsService settingsService;
  // ---------------------------------------- lifecycle -------------------------------------------

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ((YandexWeatherApp) getApplication())
            .getWeatherComponent()
            .inject(this);

    initializeView();
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (settingsService.isFirstSetup()) {
      firstCitySetup();
    } else {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            openDefaultFragment();
        }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    compositeDisposable.add(subscribeOnRefresh());
  }

  private Disposable subscribeOnRefresh() {
    return RxView.clicks(imageRefresh)
            .debounce(300, TimeUnit.MILLISECONDS)
            .map(o -> getSupportFragmentManager().findFragmentById(R.id.fragment_container))
            .filter(this::isWeatherFragment)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(o -> ((WeatherView)o).onRefreshButtonClick());
  }

  private boolean isWeatherFragment(Fragment fragment) {
    if (fragment instanceof WeatherView) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    compositeDisposable.clear();
  }

  private void firstCitySetup() {

    try {
      AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
              .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
              .build();
      Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
              .setFilter(typeFilter)
              .build(this);
      startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
      Toast.makeText(this, getString(R.string.no_gsm), Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
      settingsService.saveLocation(PlaceAutocomplete.getPlace(this, data))
              .subscribe(this::openDefaultFragment);
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }

  // ---------------------------------------- listeners -------------------------------------------

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.navigation_weather:
        addFragment(WeatherFragment.newInstance(), false);
        break;
      case R.id.navigation_settings:
        addFragment(SettingsFragment.newInstance(), true);
        break;
      case R.id.navigation_about:
        addFragment(AboutFragment.newInstance(), true);
        break;
      default:
        throw new IllegalStateException("Unknown navigation item");
    }
    drawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }

  // ------------------------------------- ToolbarContainer ----------------------------------------

  @Override
  public void setTitle(String titleText) {
    imageRefresh.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);
    textToolbar.setText(titleText);
  }

  @Override
  public void toggleIcon(boolean showProgressBar, boolean showRefreshIcon) {
    if (showProgressBar && showRefreshIcon || !showProgressBar && !showRefreshIcon) {
      progressBar.setVisibility(View.GONE);
      imageRefresh.setVisibility(View.GONE);
    } else if (showProgressBar) {
      imageRefresh.setVisibility(View.GONE);
      progressBar.setVisibility(View.VISIBLE);
    } else {
      progressBar.setVisibility(View.GONE);
      imageRefresh.setVisibility(View.VISIBLE);
    }
  }

  // ----------------------------------------- private --------------------------------------------

  private void openDefaultFragment() {
    addFragment(WeatherFragment.newInstance(), false);
    navigationView.setCheckedItem(R.id.navigation_weather);
  }

  private void addFragment(Fragment fragment, boolean addToBackStack) {
    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    if (currentFragment == null || currentFragment.getClass() != fragment.getClass()) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());

      if (addToBackStack) {
        transaction.addToBackStack(null);
      } else if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
      }

      transaction.commitAllowingStateLoss();
    }
  }

  private void initializeView() {
    imageRefresh = (ImageView) findViewById(R.id.image_refresh);
    progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    imageRefresh.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle("");
    textToolbar = (TextView) findViewById(R.id.text_toolbar);
    setSupportActionBar(toolbar);

    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerToggle = new ActionBarDrawerToggle(
        this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(drawerToggle);
    drawerToggle.syncState();

    navigationView = (NavigationView) findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(this);

    getSupportFragmentManager().addOnBackStackChangedListener(() -> {
      setCurrentNavigationIcon();
      setCurrentSelectedDrawer();
    });
    setCurrentNavigationIcon();
  }

  private void setCurrentSelectedDrawer() {
    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    if (currentFragment != null) {
      if (currentFragment instanceof WeatherFragment) {
        navigationView.setCheckedItem(R.id.navigation_weather);
      } else if (currentFragment instanceof SettingsFragment) {
        navigationView.setCheckedItem(R.id.navigation_settings);
      } else if (currentFragment instanceof AboutFragment) {
        navigationView.setCheckedItem(R.id.navigation_about);
      } else {
        throw new IllegalStateException("Unknown fragment in navigation view");}
      }
  }

  private void setCurrentNavigationIcon() {
    if (getSupportActionBar() != null) {
      int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
      if (backStackEntryCount == 0) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawerToggle.setDrawerIndicatorEnabled(true);

        toolbarNavigationListenerIsRegistered = false;
      } else {
        drawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!toolbarNavigationListenerIsRegistered) {
          drawerToggle.setToolbarNavigationClickListener(v -> onBackPressed());

          toolbarNavigationListenerIsRegistered = true;
        }
      }
    }
  }
}

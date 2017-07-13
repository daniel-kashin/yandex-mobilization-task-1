package com.danielkashin.yandexweatherapp.presentation.view.main_drawer;

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
import android.widget.TextView;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.presentation.view.about.AboutFragment;
import com.danielkashin.yandexweatherapp.presentation.view.settings.SettingsFragment;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherFragment;


@SuppressWarnings("FieldCanBeLocal") // view fields are quite often useful in the whole activity scope
public class MainDrawerActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, ITitleContainer {

  private TextView mTextToolbar;
  private Toolbar mToolbar;
  private DrawerLayout mDrawerLayout;
  private NavigationView mNavigationView;
  private ActionBarDrawerToggle mDrawerToggle;

  // ---------------------------------------- lifecycle -------------------------------------------

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initializeView();
  }

  @Override
  protected void onStart() {
    super.onStart();

    if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
      openDefaultFragment();
    }
  }

  // ---------------------------------------- listeners -------------------------------------------

  @Override
  public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.navigation_weather) {
      addFragment(WeatherFragment.newInstance());
    } else if (id == R.id.navigation_settings) {
      addFragment(SettingsFragment.newInstance());
    } else if (id == R.id.navigation_about) {
      addFragment(AboutFragment.newInstance());
    } else {
      throw new IllegalStateException("Unknown navigation item");
    }
    mDrawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }

  // ------------------------------------- ITitleContainer ----------------------------------------

  @Override
  public void setTitle(String titleText) {
    mTextToolbar.setText(titleText);
  }

  // ----------------------------------------- private --------------------------------------------

  private void openDefaultFragment() {
    addFragment(WeatherFragment.newInstance());
    mNavigationView.setCheckedItem(R.id.navigation_weather);
  }

  private void addFragment(Fragment fragment) {
    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    if (currentFragment == null || currentFragment.getClass() != fragment.getClass()) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.fragment_container, fragment);
      transaction.commit();
    }
  }

  private void initializeView() {
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setTitle("");
    mTextToolbar = (TextView) findViewById(R.id.text_toolbar);
    setSupportActionBar(mToolbar);

    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerToggle = new ActionBarDrawerToggle(
        this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    mDrawerLayout.addDrawerListener(mDrawerToggle);
    mDrawerToggle.syncState();

    mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
    mNavigationView.setNavigationItemSelectedListener(this);
  }
}

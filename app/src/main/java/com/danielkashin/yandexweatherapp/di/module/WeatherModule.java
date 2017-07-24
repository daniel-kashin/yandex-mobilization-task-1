package com.danielkashin.yandexweatherapp.di.module;

import com.danielkashin.yandexweatherapp.data.data_services.weather.local.LocalWeatherService;
import com.danielkashin.yandexweatherapp.data.data_services.weather.local.SQLiteWeatherService;
import com.danielkashin.yandexweatherapp.data.data_services.weather.remote.OpenWeatherMapService;
import com.danielkashin.yandexweatherapp.data.data_services.weather.remote.RemoteWeatherService;
import com.danielkashin.yandexweatherapp.data.managers.NetworkManager;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepositoryImplementation;
import com.danielkashin.yandexweatherapp.data.resources.WeatherConverter;
import com.danielkashin.yandexweatherapp.data.settings.SettingsService;
import com.danielkashin.yandexweatherapp.di.qualifiers.ApiKey;
import com.danielkashin.yandexweatherapp.di.scopes.WeatherScope;
import com.danielkashin.yandexweatherapp.domain.use_cases.CachedWeatherUseCases;
import com.danielkashin.yandexweatherapp.domain.use_cases.GetWeatherUseCase;
import com.danielkashin.yandexweatherapp.domain.use_cases.RefreshWeatherUseCase;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterFactory;
import com.danielkashin.yandexweatherapp.presentation.presenter.weather.WeatherPresenter;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherView;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;


@Module
public class WeatherModule {

  @Provides
  @WeatherScope
  public RemoteWeatherService provideRemoteWeatherService(@ApiKey String apiKey, OkHttpClient okHttpClient) {
    return OpenWeatherMapService.Factory.create(apiKey, okHttpClient);
  }

  @Provides
  @WeatherScope
  public LocalWeatherService provideLocalWeatherService(StorIOSQLite storIOSQLite) {
    return SQLiteWeatherService.Factory.create(storIOSQLite);
  }

  @Provides
  @WeatherScope
  public WeatherRepository provideWeatherRepository(RemoteWeatherService remoteWeatherService,
                                                    LocalWeatherService localWeatherService,
                                                    WeatherConverter weatherConverter,
                                                    NetworkManager networkManager,
                                                    SettingsService settingsService) {
    return WeatherRepositoryImplementation.Factory.create(remoteWeatherService, localWeatherService,
        weatherConverter, networkManager, settingsService);
  }

  @Provides
  @WeatherScope
  public GetWeatherUseCase provideGetWeatherUseCase(WeatherRepository weatherRepository) {
    return new GetWeatherUseCase(weatherRepository);
  }

  @Provides
  @WeatherScope
  public RefreshWeatherUseCase provideRefreshWeatherUseCase(WeatherRepository weatherRepository) {
    return new RefreshWeatherUseCase(weatherRepository);
  }

  @Provides
  @WeatherScope
  public CachedWeatherUseCases provideCachedWeatherUseCases(WeatherRepository weatherRepository) {
    return new CachedWeatherUseCases(weatherRepository);
  }

  @Provides
  @WeatherScope
  public PresenterFactory<WeatherPresenter, WeatherView> providePresenterFactory(
          GetWeatherUseCase getWeatherUseCase, RefreshWeatherUseCase refreshWeatherUseCase,
          CachedWeatherUseCases cachedWeatherUseCases) {
    return new WeatherPresenter.Factory(getWeatherUseCase, refreshWeatherUseCase, cachedWeatherUseCases);
  }
}

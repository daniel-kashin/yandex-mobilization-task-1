package com.danielkashin.yandexweatherapp.di.module;

import com.danielkashin.yandexweatherapp.data.data_services.weather.remote.OpenWeatherMapService;
import com.danielkashin.yandexweatherapp.data.data_services.weather.remote.WeatherRemoteService;
import com.danielkashin.yandexweatherapp.data.managers.NetworkManager;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepositoryImplementation;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.data.resources.ResourceWeatherConverter;
import com.danielkashin.yandexweatherapp.data.resources.WeatherConverter;
import com.danielkashin.yandexweatherapp.di.qualifiers.ApiKey;
import com.danielkashin.yandexweatherapp.di.scopes.WeatherScope;
import com.danielkashin.yandexweatherapp.domain.use_cases.GetWeatherUseCase;
import com.danielkashin.yandexweatherapp.domain.use_cases.RefreshWeatherUseCase;
import com.danielkashin.yandexweatherapp.presentation.presenter.base.PresenterFactory;
import com.danielkashin.yandexweatherapp.presentation.presenter.weather.WeatherPresenter;
import com.danielkashin.yandexweatherapp.presentation.view.weather.WeatherView;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;


@Module
public class WeatherModule {

  @Provides
  @WeatherScope
  public WeatherRemoteService provideWeatherRemoteService(@ApiKey String apiKey, OkHttpClient okHttpClient) {
    return OpenWeatherMapService.Factory.create(apiKey, okHttpClient);
  }

  @Provides
  @WeatherScope
  public WeatherRepository provideWeatherRepository(WeatherRemoteService weatherRemoteService,
                                                    WeatherConverter weatherConverter,
                                                    NetworkManager networkManager) {
    return WeatherRepositoryImplementation.Factory.create(weatherRemoteService, weatherConverter, networkManager);
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
  public PresenterFactory<WeatherPresenter, WeatherView> providePresenterFactory(
      GetWeatherUseCase getWeatherUseCase, RefreshWeatherUseCase refreshWeatherUseCase) {
    return new WeatherPresenter.Factory(getWeatherUseCase, refreshWeatherUseCase);
  }
}

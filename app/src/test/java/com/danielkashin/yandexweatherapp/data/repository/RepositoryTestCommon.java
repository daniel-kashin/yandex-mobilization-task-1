package com.danielkashin.yandexweatherapp.data.repository;

import com.danielkashin.yandexweatherapp.data.TestContract;
import com.danielkashin.yandexweatherapp.data.data_services.weather.local.LocalWeatherService;
import com.danielkashin.yandexweatherapp.data.data_services.weather.remote.RemoteWeatherService;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.managers.NetworkManager;
import com.danielkashin.yandexweatherapp.data.resources.WeatherConverter;
import com.danielkashin.yandexweatherapp.data.settings.SettingsService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import retrofit2.Call;


/**
 * 
 */

public class RepositoryTestCommon {
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Mock protected RemoteWeatherService remoteWeatherService;
    @Mock protected LocalWeatherService localWeatherService;
    @Mock protected WeatherConverter weatherConverter;
    @Mock protected NetworkManager networkManager;
    @Mock protected SettingsService settingsService;
    @Mock protected Call<NetworkWeather> weatherCall;

    protected Weather expectedWeather;

    protected WeatherRepository weatherRepository;
    protected NetworkWeather weatherNWResponse;
    protected DatabaseWeather weatherFromDB;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        initTestValues();

        weatherRepository = WeatherRepositoryImplementation.Factory.create(remoteWeatherService,
                localWeatherService, weatherConverter, networkManager, settingsService);
    }

    private void initTestValues() {
        weatherNWResponse = TestContract.createNetworkWeather();
        weatherFromDB = TestContract.createDatabsaseWeather();
        expectedWeather = TestContract.createWeatherFar();
    }

    /*private Weather createExpectedWeather() {
        return new Weather(EXPECTED_TEST_CITY, 0,"0",0,"0",0,null,0,0, 0,"0",0,0,0);
    }

    private DatabaseWeather createTestDatabaseObject() {
        return new DatabaseWeather(123L, 1501205285, "Moskovskaya Oblast", 520, "Rain",296.66,295.15,297.15,1,10,78,1011,40);
    }


*/
}

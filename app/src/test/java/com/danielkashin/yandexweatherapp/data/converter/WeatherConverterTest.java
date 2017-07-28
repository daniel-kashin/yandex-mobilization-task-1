package com.danielkashin.yandexweatherapp.data.converter;

import android.content.res.Resources;

import com.danielkashin.yandexweatherapp.data.TestContract;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.resources.ResourceWeatherConverter;
import com.danielkashin.yandexweatherapp.data.resources.WeatherConverter;
import com.danielkashin.yandexweatherapp.data.settings.SettingsService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.danielkashin.yandexweatherapp.data.TestContract.EXPECTED_RES_ID;
import static com.danielkashin.yandexweatherapp.data.TestContract.EXPECTED_RES_STR;
import static com.danielkashin.yandexweatherapp.data.TestContract.EXPECTED_TEMPERATURE_TYPE_CEL;
import static com.danielkashin.yandexweatherapp.data.TestContract.EXPECTED_TEMPERATURE_TYPE_FAR;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 *
 */
public class WeatherConverterTest {

    @Mock
    private Resources resources;

    private WeatherConverter weatherConverter;
    private String applicationPackageName = "package";
    @Mock
    private SettingsService settingsService;

    private Weather expectedWeatherApp;
    private DatabaseWeather expectedWeatherDB;
    private NetworkWeather expectedWeatherNW;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        expectedWeatherDB =  TestContract.createDatabsaseWeather();
        expectedWeatherNW =  TestContract.createNetworkWeather();

        when(resources.getString(anyInt())).thenReturn(EXPECTED_RES_STR);
        when(resources.getIdentifier(anyString(), anyString(), anyString())).thenReturn(EXPECTED_RES_ID);

        weatherConverter = new ResourceWeatherConverter(resources, applicationPackageName,
                settingsService);
    }

    @Test
    public void convertWeatherFromNW(){
        expectedWeatherApp = TestContract.createWeatherFar();
        when(settingsService.getTemperatureType()).thenReturn(EXPECTED_TEMPERATURE_TYPE_FAR);

        Weather actual = weatherConverter.getWeather(expectedWeatherNW);

        assertTrue(actual.equals(expectedWeatherApp));
    }

    @Test
    public void convertWeatherFromDB(){
        expectedWeatherApp = TestContract.createWeatherFar();
        when(settingsService.getTemperatureType()).thenReturn(EXPECTED_TEMPERATURE_TYPE_FAR);

        Weather actual = weatherConverter.getWeather(expectedWeatherDB);

        assertTrue(actual.equals(expectedWeatherApp));
    }
    @Test
    public void  convertWeatherFromNwToDB(){
        DatabaseWeather actual = weatherConverter.getDatabaseWeather(expectedWeatherNW);

        assertTrue(actual.equals(expectedWeatherDB));
    }
    @Test
    public void refreshWeather(){
        expectedWeatherApp = TestContract.createWeatherFar();
        Weather expectedWeatherAppCEL = TestContract.createWeatherCel();
        when(settingsService.getTemperatureType()).thenReturn(EXPECTED_TEMPERATURE_TYPE_CEL);

        Weather actual = weatherConverter.refreshWeather(expectedWeatherApp);


        assertTrue(actual.equals(expectedWeatherAppCEL));
    }
}

package com.danielkashin.yandexweatherapp.data.resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ResourceWeatherConverter implements WeatherConverter {

  private static final String CONDITION_DESCRIPTION_NAME_PREFIX = "id_";
  private static final String CONDITION_ICON_NAME_PREFIX = "w";
  private static final String KEY_TEMPERATURE_TYPE = "fahrenheit_degree";
  private static final String DATE_TIME_PATTERN = "MM/dd hh:mm";

  private final Context applicationContext;


  public ResourceWeatherConverter(Context context) {
    this.applicationContext = context.getApplicationContext();
  }

  @Override
  public Weather getWeather(NetworkWeather networkWeather) {
    int weatherConditionId = networkWeather.getWeatherSummary().getId();
    Weather.TemperatureType currentTemperatureType = getCurrentTemperatureType();

    Weather weather = new Weather(
        networkWeather.getName(),
        networkWeather.getDt(),
        getDatetimeFromTimestamp(networkWeather.getDt()),
        weatherConditionId,
        getWeatherConditionDescription(weatherConditionId),
        getWeatherConditionIconId(networkWeather.getWeatherSummary().getIcon()),
        currentTemperatureType,
        convertKalvinToWeatherType(currentTemperatureType, networkWeather.getMain().getTemp()),
        convertKalvinToWeatherType(currentTemperatureType, networkWeather.getMain().getTempMin()),
        convertKalvinToWeatherType(currentTemperatureType, networkWeather.getMain().getTempMax()),
        getWindSummary(networkWeather.getWind().getDeg(), networkWeather.getWind().getSpeed()),
        networkWeather.getMain().getHumidity(),
        networkWeather.getMain().getPressure(),
        networkWeather.getClouds().getAll()
    );

    return weather;
  }

  @Override
  public Weather refreshWeather(Weather weather) {
    Weather.TemperatureType currentTemperatureType = getCurrentTemperatureType();
    Weather.TemperatureType oldTemperatureType = weather.temperatureType;
    int mainT = weather.mainTemperature;
    int minT = weather.minTemperature;
    int maxT = weather.maxTemperature;
    if (currentTemperatureType != oldTemperatureType) {
      mainT = switchWeatherType(oldTemperatureType, mainT);
      minT = switchWeatherType(oldTemperatureType, minT);
      maxT = switchWeatherType(oldTemperatureType, maxT);
    }

    Weather refreshedWeather = new Weather(weather.cityName, weather.timestamp,
        getDatetimeFromTimestamp(weather.timestamp), weather.conditionId,
        getWeatherConditionDescription(weather.conditionId), weather.conditionIconId,
        currentTemperatureType, mainT, minT, maxT, weather.windSummary, weather.humidity,
        weather.pressure, weather.cloudiness);

    return refreshedWeather;
  }

  @Override
  public Weather getWeather(DatabaseWeather databaseWeather) {
    Weather.TemperatureType currentType = getCurrentTemperatureType();

    Weather weather = new Weather(
        databaseWeather.getCityName(),
        databaseWeather.getTimestamp(),
        getDatetimeFromTimestamp(databaseWeather.getTimestamp()),
        databaseWeather.getConditionId(),
        getWeatherConditionDescription(databaseWeather.getConditionId()),
        getWeatherConditionIconId(databaseWeather.getConditionIconName()),
        currentType,
        convertKalvinToWeatherType(currentType, databaseWeather.getMainTemperatureInKelvin()),
        convertKalvinToWeatherType(currentType, databaseWeather.getMinTemperatureInKelvin()),
        convertKalvinToWeatherType(currentType, databaseWeather.getMaxTemperatureInKelvin()),
        getWindSummary(databaseWeather.getWindAngle(), databaseWeather.getWindSpeed()),
        databaseWeather.getHumidity(),
        databaseWeather.getPressure(),
        databaseWeather.getCloudiness()
    );

    return weather;
  }

  @Override
  public DatabaseWeather getDatabaseWeather(NetworkWeather networkWeather) {
    int weatherConditionId = networkWeather.getWeatherSummary().getId();
    Weather.TemperatureType currentTemperatureType = getCurrentTemperatureType();

    DatabaseWeather databaseWeather = new DatabaseWeather(
        null,
        networkWeather.getDt(),
        networkWeather.getName(),
        weatherConditionId,
        networkWeather.getWeatherSummary().getIcon(),
        networkWeather.getMain().getTemp(),
        networkWeather.getMain().getTempMin(),
        networkWeather.getMain().getTempMax(),
        networkWeather.getWind().getSpeed(),
        networkWeather.getWind().getDeg(),
        networkWeather.getMain().getHumidity(),
        networkWeather.getMain().getPressure(),
        networkWeather.getClouds().getAll()
    );

    return databaseWeather;
  }

  private String getWeatherConditionDescription(int weatherConditionId) {
    String descriptionName = CONDITION_DESCRIPTION_NAME_PREFIX + weatherConditionId;
    int resId = applicationContext.getResources()
        .getIdentifier(descriptionName, "string", applicationContext.getPackageName());
    return applicationContext.getString(resId);
  }

  private int getWeatherConditionIconId(String iconName) {
    String fullIconName = CONDITION_ICON_NAME_PREFIX + iconName;
    return applicationContext.getResources()
        .getIdentifier(fullIconName, "drawable", applicationContext.getPackageName());
  }

  // we can add functionality later
  private Weather.TemperatureType getCurrentTemperatureType() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    Weather.TemperatureType currentTemperatureType = Weather.TemperatureType.Celsius;

    if (sharedPreferences.contains(KEY_TEMPERATURE_TYPE)) {
      boolean isFahrenheit = sharedPreferences.getBoolean(KEY_TEMPERATURE_TYPE, false);
      if (isFahrenheit) {
        currentTemperatureType = Weather.TemperatureType.Fahrenheit;
      }
    }

    return currentTemperatureType;
  }

  // we can add functionality later
  private int convertKalvinToWeatherType(Weather.TemperatureType temperatureType, double temperature) {
    double celsius = temperature - 273.5;
    if (temperatureType == Weather.TemperatureType.Celsius) {
      return (int) Math.round(celsius);
    } else {
      return (int) Math.round(celsius * 9 / 5 + 32);
    }
  }

  private int switchWeatherType(Weather.TemperatureType currentTemperatureType, int currentValue) {
    if (currentTemperatureType == Weather.TemperatureType.Celsius) {
      return (int) Math.round(currentValue * 9.0 / 5 + 32);
    } else {
      return (int) Math.round((currentValue - 32) / 9.0 * 5);
    }
  }

  // we can add functionality later
  private String getWindSummary(double angle, double speed) {
    return speed + " " + applicationContext.getString(R.string.mps);
  }

  public static String getDatetimeFromTimestamp(Long timestamp) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault());
    return simpleDateFormat.format(new Date(timestamp * 1000));
  }
}

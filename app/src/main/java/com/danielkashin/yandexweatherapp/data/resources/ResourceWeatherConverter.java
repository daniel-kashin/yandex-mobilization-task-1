package com.danielkashin.yandexweatherapp.data.resources;

import android.content.res.Resources;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.settings.SettingsService;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("UnnecessaryLocalVariable") // local variables helps to debug
public class ResourceWeatherConverter implements WeatherConverter {

  private static final String CONDITION_DESCRIPTION_NAME_PREFIX = "id_";
  private static final String CONDITION_ICON_NAME_PREFIX = "w";
  private static final String DATE_TIME_PATTERN = "dd/MM HH:mm";
  private static final double DIFFERENCE_KELVIN_CELSIUS = 273.5;
  private static final double DIFFERENCE_FAHRENHEIT_CELSIUS = 32.0;
  private static final double COEFFICIENT_FAHRENHEIT_CELSIUS = 9.0 / 5.0;
  private static final double COEFFICIENT_PRESSURE = 0.75;

  private final Resources resources;
  private final String applicationPackageName;
  private final SettingsService settingsService;


  public ResourceWeatherConverter(Resources resources, String applicationPackageName,
                                  SettingsService settingsService) {
    ExceptionHelper.checkAllObjectsNonNull(resources, applicationPackageName, settingsService);
    this.resources = resources;
    this.applicationPackageName = applicationPackageName;
    this.settingsService = settingsService;
  }

  // ----------------------------------- WeatherConverter -----------------------------------------

  @Override
  public Weather getWeather(NetworkWeather networkWeather) {
    Weather.TemperatureType currentTemperatureType = settingsService.getTemperatureType();

    int conditionId = networkWeather.getConditionId();
    String dateTime = getDatetimeFromTimestamp(networkWeather.getTimestamp());
    String description = getWeatherConditionDescription(conditionId);
    int iconId = getWeatherConditionIconId(networkWeather.getIcon());
    int mainTemperature = convertKalvin(currentTemperatureType, networkWeather.getMainTemperatureInKelvin());
    int minTemperature = convertKalvin(currentTemperatureType, networkWeather.getMinTemperatureInKelvin());
    int maxTemperature = convertKalvin(currentTemperatureType, networkWeather.getMaxTemperatureInKelvin());
    String windSummary = getWindSummary(networkWeather.getWindAngle(), networkWeather.getWindSpeed());
    int pressure = convertPressureToMMHG(networkWeather.getPressureInHpa());

    Weather weather = new Weather(networkWeather.getName(), networkWeather.getTimestamp(),
        dateTime, conditionId, description, iconId, currentTemperatureType, mainTemperature,
        minTemperature, maxTemperature, windSummary, networkWeather.getHumidity(),
        pressure, networkWeather.getCloudiness());
    return weather;
  }

  @Override
  public Weather getWeather(DatabaseWeather databaseWeather) {
    Weather.TemperatureType currentType = settingsService.getTemperatureType();

    String dateTime = getDatetimeFromTimestamp(databaseWeather.getTimestamp());
    String description = getWeatherConditionDescription(databaseWeather.getConditionId());
    int iconId = getWeatherConditionIconId(databaseWeather.getConditionIconName());
    int mainTemperature = convertKalvin(currentType, databaseWeather.getMainTemperatureInKelvin());
    int minTemperature = convertKalvin(currentType, databaseWeather.getMinTemperatureInKelvin());
    int maxTemperature = convertKalvin(currentType, databaseWeather.getMaxTemperatureInKelvin());
    String windSummary = getWindSummary(databaseWeather.getWindAngle(), databaseWeather.getWindSpeed());
    int pressure = convertPressureToMMHG(databaseWeather.getPressure());

    Weather weather = new Weather(databaseWeather.getCityName(), databaseWeather.getTimestamp(),
        dateTime, databaseWeather.getConditionId(), description, iconId, currentType,
        mainTemperature, minTemperature, maxTemperature, windSummary, databaseWeather.getHumidity(),
        pressure, databaseWeather.getCloudiness());
    return weather;
  }

  @Override
  public DatabaseWeather getDatabaseWeather(NetworkWeather networkWeather) {
    DatabaseWeather databaseWeather = new DatabaseWeather(
        null,
        networkWeather.getTimestamp(),
        networkWeather.getName(),
        networkWeather.getConditionId(),
        networkWeather.getIcon(),
        networkWeather.getMainTemperatureInKelvin(),
        networkWeather.getMinTemperatureInKelvin(),
        networkWeather.getMaxTemperatureInKelvin(),
        networkWeather.getWindSpeed(),
        networkWeather.getWindAngle(),
        networkWeather.getHumidity(),
        networkWeather.getPressureInHpa(),
        networkWeather.getCloudiness()
    );
    return databaseWeather;
  }

  @Override
  public Weather refreshWeather(Weather weather) {
    Weather.TemperatureType currentTemperatureType = settingsService.getTemperatureType();
    Weather.TemperatureType oldTemperatureType = weather.temperatureType;
    String datetime = getDatetimeFromTimestamp(weather.timestamp);
    String description = getWeatherConditionDescription(weather.conditionId);
    int mainTemperature = weather.mainTemperature;
    int minTemperature = weather.minTemperature;
    int maxTemeperature = weather.maxTemperature;
    if (currentTemperatureType != oldTemperatureType) {
      mainTemperature = switchWeatherType(oldTemperatureType, mainTemperature);
      minTemperature = switchWeatherType(oldTemperatureType, minTemperature);
      maxTemeperature = switchWeatherType(oldTemperatureType, maxTemeperature);
    }

    Weather refreshedWeather = new Weather(weather.cityName, weather.timestamp, datetime,
        weather.conditionId, description, weather.conditionIconId, currentTemperatureType,
        mainTemperature, minTemperature, maxTemeperature, weather.windSummary, weather.humidity,
        weather.pressure, weather.cloudiness);
    return refreshedWeather;
  }

  // ---------------------------------------- private ---------------------------------------------

  private String getWeatherConditionDescription(int weatherConditionId) {
    String descriptionName = CONDITION_DESCRIPTION_NAME_PREFIX + weatherConditionId;
    int resId = resources.getIdentifier(descriptionName, "string", applicationPackageName);
    return resources.getString(resId);
  }

  private int getWeatherConditionIconId(String iconName) {
    String fullIconName = CONDITION_ICON_NAME_PREFIX + iconName;
    return resources.getIdentifier(fullIconName, "drawable", applicationPackageName);
  }

  // we can add functionality later
  private int convertKalvin(Weather.TemperatureType temperatureType, double temperature) {
    double celsius = temperature - DIFFERENCE_KELVIN_CELSIUS;
    if (temperatureType == Weather.TemperatureType.Celsius) {
      return (int) Math.round(celsius);
    } else {
      return switchWeatherType(Weather.TemperatureType.Celsius, celsius);
    }
  }

  private int switchWeatherType(Weather.TemperatureType currentTemperatureType, double currentValue) {
    if (currentTemperatureType == Weather.TemperatureType.Celsius) {
      return (int) Math.round(currentValue * COEFFICIENT_FAHRENHEIT_CELSIUS + DIFFERENCE_FAHRENHEIT_CELSIUS);
    } else {
      return (int) Math.round((currentValue - DIFFERENCE_FAHRENHEIT_CELSIUS) / COEFFICIENT_FAHRENHEIT_CELSIUS);
    }
  }

  // we can add functionality later
  private String getWindSummary(double angle, double speed) {
    return speed + " " + resources.getString(R.string.mps);
  }

  private int convertPressureToMMHG(double pressure) {
    return (int)(pressure * COEFFICIENT_PRESSURE);
  }

  private static String getDatetimeFromTimestamp(Long timestamp) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault());
    return simpleDateFormat.format(new Date(TimeUnit.SECONDS.toMillis(timestamp)));
  }
}

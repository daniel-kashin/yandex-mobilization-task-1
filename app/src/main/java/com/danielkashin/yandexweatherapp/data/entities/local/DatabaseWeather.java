package com.danielkashin.yandexweatherapp.data.entities.local;

import android.support.annotation.Nullable;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_CITY_NAME;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_CLOUDINESS;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_CONDITION_ICON_NAME;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_CONDITION_ID;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_HUMIDITY;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_ID;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_MAIN_TEMPERATURE;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_MAX_TEMPERATURE;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_MIN_TEMPERATURE;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_PRESSURE;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_TIMESTAMP;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_WIND_ANDLE;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_WIND_SPEED;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.TABLE_NAME;


@StorIOSQLiteType(table = TABLE_NAME)
public class DatabaseWeather {

  // id
  @StorIOSQLiteColumn(name = COLUMN_NAME_ID, key = true)
  Long id;

  // meta
  @StorIOSQLiteColumn(name = COLUMN_NAME_TIMESTAMP)
  Long timestamp;

  @StorIOSQLiteColumn(name = COLUMN_NAME_CITY_NAME)
  String cityName;

  // condition
  @StorIOSQLiteColumn(name = COLUMN_NAME_CONDITION_ID)
  Integer conditionId;

  @StorIOSQLiteColumn(name = COLUMN_NAME_CONDITION_ICON_NAME)
  String conditionIconName;

  // temperature
  @StorIOSQLiteColumn(name = COLUMN_NAME_MAIN_TEMPERATURE)
  Double mainTemperatureInKelvin;

  @StorIOSQLiteColumn(name = COLUMN_NAME_MIN_TEMPERATURE)
  Double minTemperatureInKelvin;

  @StorIOSQLiteColumn(name = COLUMN_NAME_MAX_TEMPERATURE)
  Double maxTemperatureInKelvin;

  // wind
  @StorIOSQLiteColumn(name = COLUMN_NAME_WIND_SPEED)
  Double windSpeed;

  @StorIOSQLiteColumn(name = COLUMN_NAME_WIND_ANDLE)
  Double windAngle;

  // other
  @StorIOSQLiteColumn(name = COLUMN_NAME_HUMIDITY)
  Integer humidity;

  @StorIOSQLiteColumn(name = COLUMN_NAME_PRESSURE)
  Integer pressure;

  @StorIOSQLiteColumn(name = COLUMN_NAME_CLOUDINESS)
  Integer cloudiness;


  DatabaseWeather() {
  }

  public DatabaseWeather(@Nullable Long id, long timestamp, String cityName, int conditionId,
                         String conditionIconName, double mainTemperatureInKelvin,
                         double minTemperatureInKelvin, double maxTemperatureInKelvin,
                         double windSpeed, double windAngle, int humidity,
                         int pressure, int cloudiness) {
    this.id = id;
    this.timestamp = timestamp;
    this.cityName = cityName;
    this.conditionId = conditionId;
    this.conditionIconName = conditionIconName;
    this.mainTemperatureInKelvin = mainTemperatureInKelvin;
    this.minTemperatureInKelvin = minTemperatureInKelvin;
    this.maxTemperatureInKelvin = maxTemperatureInKelvin;
    this.windSpeed = windSpeed;
    this.windAngle = windAngle;
    this.humidity = humidity;
    this.pressure = pressure;
    this.cloudiness = cloudiness;
  }

  public Long getId() {
    return id;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getCityName() {
    return cityName;
  }

  public int getConditionId() {
    return conditionId;
  }

  public String getConditionIconName() {
    return conditionIconName;
  }

  public double getMainTemperatureInKelvin() {
    return mainTemperatureInKelvin;
  }

  public double getMinTemperatureInKelvin() {
    return minTemperatureInKelvin;
  }

  public double getMaxTemperatureInKelvin() {
    return maxTemperatureInKelvin;
  }

  public double getWindSpeed() {
    return windSpeed;
  }

  public double getWindAngle() {
    return windAngle;
  }

  public int getHumidity() {
    return humidity;
  }

  public int getPressure() {
    return pressure;
  }

  public int getCloudiness() {
    return cloudiness;
  }
}
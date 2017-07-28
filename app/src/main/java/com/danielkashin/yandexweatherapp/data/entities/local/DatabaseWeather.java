package com.danielkashin.yandexweatherapp.data.entities.local;

import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Objects;

import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_CITY_LATITUDE;
import static com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract.COLUMN_NAME_CITY_LONGITUDE;
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
  long timestamp;

  @StorIOSQLiteColumn(name = COLUMN_NAME_CITY_NAME)
  String cityName;

  @StorIOSQLiteColumn(name = COLUMN_NAME_CITY_LATITUDE)
  double latitude;

  @StorIOSQLiteColumn(name = COLUMN_NAME_CITY_LONGITUDE)
  double longitude;

  // condition
  @StorIOSQLiteColumn(name = COLUMN_NAME_CONDITION_ID)
  int conditionId;

  @StorIOSQLiteColumn(name = COLUMN_NAME_CONDITION_ICON_NAME)
  String conditionIconName;

  // temperature
  @StorIOSQLiteColumn(name = COLUMN_NAME_MAIN_TEMPERATURE)
  double mainTemperatureInKelvin;

  @StorIOSQLiteColumn(name = COLUMN_NAME_MIN_TEMPERATURE)
  double minTemperatureInKelvin;

  @StorIOSQLiteColumn(name = COLUMN_NAME_MAX_TEMPERATURE)
  double maxTemperatureInKelvin;

  // wind
  @StorIOSQLiteColumn(name = COLUMN_NAME_WIND_SPEED)
  double windSpeed;

  @StorIOSQLiteColumn(name = COLUMN_NAME_WIND_ANDLE)
  double windAngle;

  // other
  @StorIOSQLiteColumn(name = COLUMN_NAME_HUMIDITY)
  int humidity;

  @StorIOSQLiteColumn(name = COLUMN_NAME_PRESSURE)
  double pressure;

  @StorIOSQLiteColumn(name = COLUMN_NAME_CLOUDINESS)
  int cloudiness;


  DatabaseWeather() {
  }

  public DatabaseWeather(@Nullable Long id, long timestamp, String cityName, int conditionId,
                         String conditionIconName, double mainTemperatureInKelvin,
                         double minTemperatureInKelvin, double maxTemperatureInKelvin,
                         double windSpeed, double windAngle, int humidity,
                         double pressure, int cloudiness) {
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

  public double getPressure() {
    return pressure;
  }

  public int getCloudiness() {
    return cloudiness;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DatabaseWeather that = (DatabaseWeather) o;
    return timestamp == that.timestamp &&
            Double.compare(that.latitude, latitude) == 0 &&
            Double.compare(that.longitude, longitude) == 0 &&
            conditionId == that.conditionId &&
            Double.compare(that.mainTemperatureInKelvin, mainTemperatureInKelvin) == 0 &&
            Double.compare(that.minTemperatureInKelvin, minTemperatureInKelvin) == 0 &&
            Double.compare(that.maxTemperatureInKelvin, maxTemperatureInKelvin) == 0 &&
            Double.compare(that.windSpeed, windSpeed) == 0 &&
            Double.compare(that.windAngle, windAngle) == 0 &&
            humidity == that.humidity &&
            Double.compare(that.pressure, pressure) == 0 &&
            cloudiness == that.cloudiness &&
            Objects.equals(id, that.id) &&
            Objects.equals(cityName, that.cityName) &&
            Objects.equals(conditionIconName, that.conditionIconName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timestamp, cityName, latitude, longitude, conditionId, conditionIconName, mainTemperatureInKelvin, minTemperatureInKelvin, maxTemperatureInKelvin, windSpeed, windAngle, humidity, pressure, cloudiness);
  }

  @Override
  public String toString() {
    return "DatabaseWeather{" +
            "id=" + id +
            ", timestamp=" + timestamp +
            ", cityName='" + cityName + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", conditionId=" + conditionId +
            ", conditionIconName='" + conditionIconName + '\'' +
            ", mainTemperatureInKelvin=" + mainTemperatureInKelvin +
            ", minTemperatureInKelvin=" + minTemperatureInKelvin +
            ", maxTemperatureInKelvin=" + maxTemperatureInKelvin +
            ", windSpeed=" + windSpeed +
            ", windAngle=" + windAngle +
            ", humidity=" + humidity +
            ", pressure=" + pressure +
            ", cloudiness=" + cloudiness +
            '}';
  }
}

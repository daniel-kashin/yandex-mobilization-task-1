package com.danielkashin.yandexweatherapp.data.entities.repository;


import java.util.Objects;

public class Weather {

  public final String cityName;

  public final String date;

  public final long timestamp;

  // condition
  public final int conditionId;

  public final String conditionDescription;

  public final int conditionIconId;

  // temperature
  public final TemperatureType temperatureType;

  public final int mainTemperature;

  public final int minTemperature;

  public final int maxTemperature;

  // wind
  public final String windSummary;

  // other
  public final int humidity;

  public final int pressure;

  public final int cloudiness;


  public Weather(String cityName, long timestamp, String date, int conditionId,
                 String conditionDescription, int conditionIconId, TemperatureType temperatureType,
                 int mainTemperature, int minTemperature, int maxTemperature, String windSummary,
                 int humidity, int pressure, int cloudiness) {
    this.cityName = cityName;
    this.timestamp = timestamp;
    this.date = date;
    this.conditionId = conditionId;
    this.conditionDescription = conditionDescription;
    this.conditionIconId = conditionIconId;
    this.temperatureType = temperatureType;
    this.mainTemperature = mainTemperature;
    this.minTemperature = minTemperature;
    this.maxTemperature = maxTemperature;
    this.windSummary = windSummary;
    this.humidity = humidity;
    this.pressure = pressure;
    this.cloudiness = cloudiness;
  }

  public enum TemperatureType {
    Celsius,
    Fahrenheit
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Weather weather = (Weather) o;
    return timestamp == weather.timestamp &&
            conditionId == weather.conditionId &&
            conditionIconId == weather.conditionIconId &&
            mainTemperature == weather.mainTemperature &&
            minTemperature == weather.minTemperature &&
            maxTemperature == weather.maxTemperature &&
            humidity == weather.humidity &&
            pressure == weather.pressure &&
            cloudiness == weather.cloudiness &&
            Objects.equals(cityName, weather.cityName) &&
            Objects.equals(date, weather.date) &&
            Objects.equals(conditionDescription, weather.conditionDescription) &&
            temperatureType == weather.temperatureType &&
            Objects.equals(windSummary, weather.windSummary);
  }

  @Override
  public String toString() {
    return "Weather{" +
            "cityName='" + cityName + '\'' +
            ", date='" + date + '\'' +
            ", timestamp=" + timestamp +
            ", conditionId=" + conditionId +
            ", conditionDescription='" + conditionDescription + '\'' +
            ", conditionIconId=" + conditionIconId +
            ", temperatureType=" + temperatureType +
            ", mainTemperature=" + mainTemperature +
            ", minTemperature=" + minTemperature +
            ", maxTemperature=" + maxTemperature +
            ", windSummary='" + windSummary + '\'' +
            ", humidity=" + humidity +
            ", pressure=" + pressure +
            ", cloudiness=" + cloudiness +
            '}';
  }
}

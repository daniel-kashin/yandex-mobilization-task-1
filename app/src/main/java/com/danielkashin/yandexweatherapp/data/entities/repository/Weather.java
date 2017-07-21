package com.danielkashin.yandexweatherapp.data.entities.repository;


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
}

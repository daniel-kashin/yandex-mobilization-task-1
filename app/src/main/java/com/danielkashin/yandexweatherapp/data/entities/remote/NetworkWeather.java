package com.danielkashin.yandexweatherapp.data.entities.remote;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class NetworkWeather {

  @SerializedName("coord")
  private NetworkCoordinates coordinates;

  @SerializedName("weather")
  private List<NetworkWeatherSummary> weatherSummaries = null;

  @SerializedName("base")
  private String base;

  @SerializedName("main")
  private NetworkMainWeatherInformation main;

  @SerializedName("wind")
  private NetworkWind wind;

  @SerializedName("clouds")
  private NetworkClouds clouds;

  @SerializedName("dt")
  private long timestamp;

  @SerializedName("sys")
  private NetworkSys sys;

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("cod")
  private int code;


  public NetworkWeather() {
  }


  public double getMainTemperatureInKelvin() {
    return main.getTemp();
  }

  public double getMinTemperatureInKelvin() {
    return main.getTempMin();
  }

  public double getMaxTemperatureInKelvin() {
    return main.getTempMax();
  }

  public int getPressureInHpa() {
    return main.getPressure();
  }

  public int getHumidity() {
    return main.getHumidity();
  }

  public int getCloudiness() {
    return clouds.getAll();
  }

  public double getWindAngle() {
    return wind.getDeg();
  }

  public double getWindSpeed() {
    return wind.getSpeed();
  }

  public int getConditionId() {
    return weatherSummaries.get(0).getId();
  }

  public String getIcon() {
    return weatherSummaries.get(0).getIcon();
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getName() {
    return name;
  }

}
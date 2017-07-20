package com.danielkashin.yandexweatherapp.data.entities.remote;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NetworkWeather {

  @SerializedName("coord")
  private NetworkCoord coord;

  @SerializedName("weather")
  private List<NetworkWeatherSummary> weather = null;

  @SerializedName("base")
  private String base;

  @SerializedName("main")
  private NetworkMain main;

  @SerializedName("wind")
  private NetworkWind wind;

  @SerializedName("clouds")
  private NetworkClouds clouds;

  @SerializedName("rain")
  private NetworkRain rain;

  @SerializedName("dt")
  private long dt;

  @SerializedName("sys")
  private NetworkSys sys;

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("cod")
  private int cod;


  public NetworkWeather() {
  }


  public NetworkCoord getCoord() {
    return new NetworkCoord(coord);
  }

  public NetworkWeatherSummary getWeatherSummary() {
    return new NetworkWeatherSummary(weather.get(0));
  }

  public String getBase() {
    return base;
  }

  public NetworkMain getMain() {
    return new NetworkMain(main);
  }

  public NetworkWind getWind() {
    return new NetworkWind(wind);
  }

  public NetworkClouds getClouds() {
    return new NetworkClouds(clouds);
  }

  public NetworkRain getRain() {
    return new NetworkRain(rain);
  }

  public long getDt() {
    return dt;
  }

  public void setDt(long dt) {
    this.dt = dt;
  }

  public NetworkSys getSys() {
    return new NetworkSys(sys);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCod() {
    return cod;
  }
}
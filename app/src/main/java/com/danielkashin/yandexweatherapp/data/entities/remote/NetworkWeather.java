package com.danielkashin.yandexweatherapp.data.entities.remote;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NetworkWeather {

  @SerializedName("coord")
  @Expose
  private NetworkCoord coord;
  @SerializedName("weather")
  @Expose
  private List<NetworkWeatherSummary> weather = null;
  @SerializedName("base")
  @Expose
  private String base;
  @SerializedName("main")
  @Expose
  private NetworkMain main;
  @SerializedName("wind")
  @Expose
  private NetworkWind wind;
  @SerializedName("clouds")
  @Expose
  private NetworkClouds clouds;
  @SerializedName("rain")
  @Expose
  private NetworkRain rain;
  @SerializedName("dt")
  @Expose
  private long dt;
  @SerializedName("sys")
  @Expose
  private NetworkSys sys;
  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("cod")
  @Expose
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
package com.danielkashin.yandexweatherapp.data.entities.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NetworkWeatherSummary {

  @SerializedName("id")
  private int id;

  @SerializedName("main")
  private String main;

  @SerializedName("description")
  private String description;

  @SerializedName("icon")
  private String icon;

  public NetworkWeatherSummary() {
  }

  public NetworkWeatherSummary(int id, String main, String description, String icon) {
    this.id = id;
    this.main = main;
    this.description = description;
    this.icon = icon;
  }

  public NetworkWeatherSummary(NetworkWeatherSummary other) {
    this(other.id, other.main, other.description, other.icon);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMain() {
    return main;
  }

  public void setMain(String main) {
    this.main = main;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
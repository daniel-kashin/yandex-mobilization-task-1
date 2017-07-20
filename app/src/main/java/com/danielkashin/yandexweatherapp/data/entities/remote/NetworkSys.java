package com.danielkashin.yandexweatherapp.data.entities.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NetworkSys {

  @SerializedName("type")
  private int type;

  @SerializedName("id")
  private int id;

  @SerializedName("message")
  private double message;

  @SerializedName("country")
  private String country;

  @SerializedName("sunrise")
  private int sunrise;

  @SerializedName("sunset")
  private int sunset;

  public NetworkSys() {
  }

  public NetworkSys(int type, int id, double message, String country, int sunrise, int sunset) {
    this.type = type;
    this.id = id;
    this.message = message;
    this.country = country;
    this.sunrise = sunrise;
    this.sunset = sunset;
  }

  public NetworkSys(NetworkSys other) {
    this(other.type, other.id, other.message, other.country, other.sunrise, other.sunset);
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public int getSunrise() {
    return sunrise;
  }

  public void setSunrise(int sunrise) {
    this.sunrise = sunrise;
  }

  public double getMessage() {
    return message;
  }

  public void setMessage(double message) {
    this.message = message;
  }

  public int getSunset() {
    return sunset;
  }

  public void setSunset(int sunset) {
    this.sunset = sunset;
  }
}
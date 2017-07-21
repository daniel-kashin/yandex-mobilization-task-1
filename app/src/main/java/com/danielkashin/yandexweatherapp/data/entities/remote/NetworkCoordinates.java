package com.danielkashin.yandexweatherapp.data.entities.remote;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetworkCoordinates {

  @SerializedName("lon")
  @Expose
  private double lon;
  @SerializedName("lat")
  @Expose
  private double lat;

  public NetworkCoordinates() {
  }

  public NetworkCoordinates(double lon, double lat) {
    this.lon = lon;
    this.lat = lat;
  }

  public NetworkCoordinates(NetworkCoordinates other) {
    this(other.lon, other.lat);
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }
}
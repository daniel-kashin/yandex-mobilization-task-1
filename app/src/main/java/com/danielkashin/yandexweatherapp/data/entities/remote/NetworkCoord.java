package com.danielkashin.yandexweatherapp.data.entities.remote;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetworkCoord {

  @SerializedName("lon")
  @Expose
  private double lon;
  @SerializedName("lat")
  @Expose
  private double lat;

  public NetworkCoord() {
  }

  public NetworkCoord(double lon, double lat) {
    this.lon = lon;
    this.lat = lat;
  }

  public NetworkCoord(NetworkCoord other) {
    this(other.lon, other.lat);
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public static NetworkCoord copyOf(NetworkCoord networkCoord) {
    return new NetworkCoord(networkCoord.lon, networkCoord.lat);
  }
}
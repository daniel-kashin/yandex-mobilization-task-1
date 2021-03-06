package com.danielkashin.yandexweatherapp.data.entities.remote;

import com.google.gson.annotations.SerializedName;


public class NetworkWind {

  @SerializedName("speed")
  private double speed;

  @SerializedName("deg")
  private double deg;

  public NetworkWind() {
  }

  public NetworkWind(double speed, double deg) {
    this.speed = speed;
    this.deg = deg;
  }

  public NetworkWind(NetworkWind other) {
    this(other.speed, other.deg);
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public double getDeg() {
    return deg;
  }

  public void setDeg(int deg) {
    this.deg = deg;
  }
}
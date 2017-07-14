package com.danielkashin.yandexweatherapp.data.entities.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NetworkWind {

  @SerializedName("speed")
  @Expose
  private double speed;
  @SerializedName("deg")
  @Expose
  private int deg;

  public NetworkWind() {
  }

  public NetworkWind(double speed, int deg) {
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

  public int getDeg() {
    return deg;
  }

  public void setDeg(int deg) {
    this.deg = deg;
  }
}
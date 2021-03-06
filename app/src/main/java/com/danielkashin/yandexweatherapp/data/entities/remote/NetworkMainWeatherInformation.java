package com.danielkashin.yandexweatherapp.data.entities.remote;

import com.google.gson.annotations.SerializedName;


public class NetworkMainWeatherInformation {

  @SerializedName("temp")
  private double temp;

  @SerializedName("pressure")
  private double pressure;

  @SerializedName("humidity")
  private int humidity;

  @SerializedName("temp_min")
  private double tempMin;

  @SerializedName("temp_max")
  private double tempMax;

  public NetworkMainWeatherInformation() {
  }

  public NetworkMainWeatherInformation(double temp, double pressure, int humidity, double tempMin, double tempMax) {
    this.temp = temp;
    this.pressure = pressure;
    this.humidity = humidity;
    this.tempMin = tempMin;
    this.tempMax = tempMax;
  }

  public NetworkMainWeatherInformation(NetworkMainWeatherInformation other) {
    this(other.temp, other.pressure, other.humidity, other.tempMin, other.tempMax);
  }

  public double getTemp() {
    return temp;
  }

  public void setTemp(double temp) {
    this.temp = temp;
  }

  public double getPressure() {
    return pressure;
  }

  public void setPressure(int pressure) {
    this.pressure = pressure;
  }

  public int getHumidity() {
    return humidity;
  }

  public void setHumidity(int humidity) {
    this.humidity = humidity;
  }

  public double getTempMin() {
    return tempMin;
  }

  public void setTempMin(double tempMin) {
    this.tempMin = tempMin;
  }

  public double getTempMax() {
    return tempMax;
  }

  public void setTempMax(double tempMax) {
    this.tempMax = tempMax;
  }
}
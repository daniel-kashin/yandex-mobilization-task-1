package com.danielkashin.yandexweatherapp.data.entities.remote;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NetworkRain {

  @SerializedName("3h")
  private int _3h;

  public NetworkRain() {
  }

  public NetworkRain(int _3h) {
    this._3h = _3h;
  }

  public NetworkRain(NetworkRain other) {
    this(other._3h);
  }

  public int get3h() {
    return _3h;
  }

  public void set3h(int _3h) {
    this._3h = _3h;
  }
}

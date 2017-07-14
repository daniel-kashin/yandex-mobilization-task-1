package com.danielkashin.yandexweatherapp.data.entities.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NetworkClouds {

  @SerializedName("all")
  @Expose
  private int all;

  public NetworkClouds() {
  }

  public NetworkClouds(int all) {
    this.all = all;
  }

  public NetworkClouds(NetworkClouds other) {
    this(other.all);
  }

  public int getAll() {
    return all;
  }

  public void setAll(int all) {
    this.all = all;
  }
}

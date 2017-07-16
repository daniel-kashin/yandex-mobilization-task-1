package com.danielkashin.yandexweatherapp.data.data_services.weather.local;

import com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract;
import com.danielkashin.yandexweatherapp.data.data_services.base.BaseDatabaseService;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.danielkashin.yandexweatherapp.presentation.presenter.weather.WeatherPresenter;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGetObject;
import com.pushtorefresh.storio.sqlite.operations.put.PreparedPutObject;
import com.pushtorefresh.storio.sqlite.queries.Query;


public class SQLiteWeatherService extends BaseDatabaseService implements LocalWeatherService {

  private SQLiteWeatherService(StorIOSQLite storIOSQLite) {
    super(storIOSQLite);
  }

  @Override
  public PreparedGetObject<DatabaseWeather> getWeather(String city) {
    return getSQLite().get()
        .object(DatabaseWeather.class)
        .withQuery(Query.builder()
            .table(WeatherContract.TABLE_NAME)
            .where(WeatherContract.COLUMN_NAME_CITY_NAME + " = ?")
            .whereArgs(city)
            .build())
        .prepare();
  }

  @Override
  public PreparedPutObject<DatabaseWeather> saveWeather(DatabaseWeather databaseWeather) {
    return getSQLite().put()
        .object(databaseWeather)
        .prepare();
  }


  public static class Factory {

    private Factory(){
    }

    public static LocalWeatherService create(StorIOSQLite storIOSQLite) {
      return new SQLiteWeatherService(storIOSQLite);
    }

  }

}

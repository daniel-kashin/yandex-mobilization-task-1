package com.danielkashin.yandexweatherapp.data.data_services.weather.local;

import com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract;
import com.danielkashin.yandexweatherapp.data.data_services.base.BaseDatabaseService;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;


public class SQLiteWeatherService extends BaseDatabaseService implements LocalWeatherService {

  private SQLiteWeatherService(StorIOSQLite storIOSQLite) {
    super(storIOSQLite);
  }

  @Override
  public DatabaseWeather getWeather(String city) {
    return getSQLite().get()
        .object(DatabaseWeather.class)
        .withQuery(Query.builder()
            .table(WeatherContract.TABLE_NAME)
            .where(WeatherContract.COLUMN_NAME_CITY_NAME + " = ?")
            .whereArgs(city)
            .build())
        .prepare()
            .executeAsBlocking();
  }

  @Override
  public void saveWeather(DatabaseWeather databaseWeather) {
     getSQLite().put()
        .object(databaseWeather)
        .prepare().executeAsBlocking();
  }


  public static class Factory {

    private Factory(){
    }

    public static LocalWeatherService create(StorIOSQLite storIOSQLite) {
      return new SQLiteWeatherService(storIOSQLite);
    }

  }

}

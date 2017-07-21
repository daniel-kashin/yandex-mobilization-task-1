package com.danielkashin.yandexweatherapp.data.database;


import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeatherStorIOSQLiteDeleteResolver;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeatherStorIOSQLiteGetResolver;
import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeatherStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

public class SQLiteFactory {

  private SQLiteFactory() {
  }

  public static StorIOSQLite create(SQLiteOpenHelper sqLiteOpenHelper) {
    return DefaultStorIOSQLite.builder()
        .sqliteOpenHelper(sqLiteOpenHelper)
        .addTypeMapping(DatabaseWeather.class, SQLiteTypeMapping.<DatabaseWeather>builder()
            .putResolver(new DatabaseWeatherStorIOSQLitePutResolver())
            .getResolver(new DatabaseWeatherStorIOSQLiteGetResolver())
            .deleteResolver(new DatabaseWeatherStorIOSQLiteDeleteResolver())
            .build())
        .build();
  }
}

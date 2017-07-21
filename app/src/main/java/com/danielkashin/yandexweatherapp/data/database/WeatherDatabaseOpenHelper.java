package com.danielkashin.yandexweatherapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.danielkashin.yandexweatherapp.BuildConfig;
import com.danielkashin.yandexweatherapp.data.contracts.local.WeatherContract;


public class WeatherDatabaseOpenHelper extends SQLiteOpenHelper {

  public WeatherDatabaseOpenHelper(Context context) {
    super(context, BuildConfig.WEATHER_DATABASE_NAME, null, BuildConfig.WEATHER_DATABASE_BUILD_NUMBER);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(WeatherContract.SQL_CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(WeatherContract.SQL_DELETE_TABLE);
    onCreate(db);
  }
}

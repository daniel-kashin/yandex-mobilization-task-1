package com.danielkashin.yandexweatherapp.data.contracts.local;


public class WeatherContract {

  private WeatherContract() {
  }

  // -------------------------------------- constants ---------------------------------------------

  public static final String TABLE_NAME = "weather_predictions";

  // id
  public static final String COLUMN_NAME_ID = "_id";

  // meta
  public static final String COLUMN_NAME_TIMESTAMP = "timestamp";

  public static final String COLUMN_NAME_CITY_NAME = "city_name";

  // condition
  public static final String COLUMN_NAME_CONDITION_ID = "condition_id";

  public static final String COLUMN_NAME_CONDITION_ICON_NAME = "condition_icon_name";

  // temperature (in kelvin)
  public static final String COLUMN_NAME_MAIN_TEMPERATURE = "main_temperature";

  public static final String COLUMN_NAME_MIN_TEMPERATURE = "min_temperature";

  public static final String COLUMN_NAME_MAX_TEMPERATURE = "max_temperature";

  // wind
  public static final String COLUMN_NAME_WIND_SPEED = "wind_speed";

  public static final String COLUMN_NAME_WIND_ANDLE = "wind_angle";

  // other
  public static final String COLUMN_NAME_HUMIDITY = "humidity";

  public static final String COLUMN_NAME_PRESSURE = "pressure";

  public static final String COLUMN_NAME_CLOUDINESS = "cloudiness";



  public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
      + COLUMN_NAME_ID + " INTEGER NOT NULL PRIMARY KEY, "
      + COLUMN_NAME_TIMESTAMP + " TIMESTAMP NON NULL, "
      + COLUMN_NAME_CITY_NAME + " TEXT NON NULL UNIQUE ON CONFLICT REPLACE, "

      + COLUMN_NAME_CONDITION_ID + " INTEGER NON NULL, "
      + COLUMN_NAME_CONDITION_ICON_NAME + " TEXT NON NULL, "

      + COLUMN_NAME_MAIN_TEMPERATURE + " REAL NON NULL, "
      + COLUMN_NAME_MIN_TEMPERATURE + " REAL NON NULL, "
      + COLUMN_NAME_MAX_TEMPERATURE + " REAL NON NULL, "

      + COLUMN_NAME_WIND_SPEED + " REAL NON NULL, "
      + COLUMN_NAME_WIND_ANDLE + " REAL NON NULL, "

      + COLUMN_NAME_HUMIDITY + " INTEGER NON NULL, "
      + COLUMN_NAME_PRESSURE + " INTEGER NON NULL, "
      + COLUMN_NAME_CLOUDINESS + " INTEGER NON NULL "

      + ");";

  public static String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}

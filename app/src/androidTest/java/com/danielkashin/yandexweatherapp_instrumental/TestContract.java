package com.danielkashin.yandexweatherapp_instrumental;

import com.danielkashin.yandexweatherapp.data.entities.local.DatabaseWeather;
import com.danielkashin.yandexweatherapp.data.entities.remote.NetworkWeather;
import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.google.gson.Gson;

/**
 *
 */
public class TestContract {
    public  static final String EXPECTED_RES_STR = "resStr";
    public  static final int EXPECTED_RES_ID = 5;
    public static final Weather.TemperatureType EXPECTED_TEMPERATURE_TYPE_CEL = Weather.TemperatureType.Celsius;
    public static final Weather.TemperatureType EXPECTED_TEMPERATURE_TYPE_FAR = Weather.TemperatureType.Fahrenheit;

    public static final String EXPECTED_TEST_CITY = "Moskovskaya Oblast’";
    public  static final String ANOTHER_TEST_CITY = "Saint-Peterburg";
    private static final long EXPECTED_TIMESTAMP = 1501230600;
    private static final String EXPECTED_DATE = "28/07 11:30";
    private static final int EXPECTED_CONDITION_ID = 800;
    private static final String EXPECTED_DESCRIPTION = EXPECTED_RES_STR;
    private static final int EXPECTED_CONDTIDION_ICON_ID = EXPECTED_RES_ID;
    private static final int EXPECTED_MAIN_TEMPERATURE_FAR = 70;
    private static final int EXPECTED_MIN_TEMPERATURE_FAR = 67;
    private static final int EXPECTED_MAX_TEMPERATURE_FAR = 75;
    private static final int EXPECTED_MAIN_TEMPERATURE_CEL = 21;
    private static final int EXPECTED_MIN_TEMPERATURE_CEL = 19;
    private static final int EXPECTED_MAX_TEMPERATURE_CEL = 24;
    private static final String EXPECTED_WIND_SUMMARY = "4.0 "+ EXPECTED_RES_STR;
    private static final int EXPECTED_HUMIDITY = 57;
    private static final int EXPECTED_PRESSURE = 757;
    private static final int EXPECTED_CLOUDINESS = 0;

    private static final double DB_TEMP_MAIN = 294.64;
    private static final double DB_TEMP_MAX = 297.15;
    private static final double DB_TEMP_MIN = 293.15;
    private static final int DB_PRESSURE = 1010;
    private static final Long EXPECTED_ID = null;
    private static final String EXPECTED_CONDITION_ICON_NAME = "01d";
    private static final long EXPECTED_WIND_SPEED = 4;
    private static final long EXPECTED_WIND_ANGLE = 90;

    public static Weather createWeatherFar(){
        return new Weather(EXPECTED_TEST_CITY,EXPECTED_TIMESTAMP,EXPECTED_DATE, EXPECTED_CONDITION_ID,
                EXPECTED_DESCRIPTION,EXPECTED_CONDTIDION_ICON_ID, EXPECTED_TEMPERATURE_TYPE_FAR, EXPECTED_MAIN_TEMPERATURE_FAR,
                EXPECTED_MIN_TEMPERATURE_FAR, EXPECTED_MAX_TEMPERATURE_FAR, EXPECTED_WIND_SUMMARY,EXPECTED_HUMIDITY,
                EXPECTED_PRESSURE,EXPECTED_CLOUDINESS );
    }

    public static Weather createWeatherCel(){
        return new Weather(EXPECTED_TEST_CITY,EXPECTED_TIMESTAMP,EXPECTED_DATE, EXPECTED_CONDITION_ID,
                EXPECTED_DESCRIPTION,EXPECTED_CONDTIDION_ICON_ID, EXPECTED_TEMPERATURE_TYPE_CEL, EXPECTED_MAIN_TEMPERATURE_CEL,
                EXPECTED_MIN_TEMPERATURE_CEL, EXPECTED_MAX_TEMPERATURE_CEL, EXPECTED_WIND_SUMMARY,EXPECTED_HUMIDITY,
                EXPECTED_PRESSURE,EXPECTED_CLOUDINESS );
    }
    public static DatabaseWeather createDatabsaseWeather(){
        return new DatabaseWeather(EXPECTED_ID, EXPECTED_TIMESTAMP, EXPECTED_TEST_CITY, EXPECTED_CONDITION_ID,
                EXPECTED_CONDITION_ICON_NAME,DB_TEMP_MAIN, DB_TEMP_MIN,
                DB_TEMP_MAX, EXPECTED_WIND_SPEED, EXPECTED_WIND_ANGLE,EXPECTED_HUMIDITY,
                DB_PRESSURE,EXPECTED_CLOUDINESS);
    }

    public static NetworkWeather createNetworkWeather(){
        return  new Gson().fromJson(createResponse(), NetworkWeather.class);
    }

    public static String createResponse() {
        return "{\"coord\":{\"lon\":37.62,\"lat\":55.76},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":294.64,\"pressure\":1010,\"humidity\":57,\"temp_min\":293.15,\"temp_max\":297.15},\"visibility\":10000,\"wind\":{\"speed\":4,\"deg\":90},\"clouds\":{\"all\":0},\"dt\":1501230600,\"sys\":{\"type\":1,\"id\":7323,\"message\":0.0065,\"country\":\"RU\",\"sunrise\":1501205285,\"sunset\":1501263763},\"id\":524925,\"name\":\"Moskovskaya Oblast’\",\"cod\":200}";
    }
}

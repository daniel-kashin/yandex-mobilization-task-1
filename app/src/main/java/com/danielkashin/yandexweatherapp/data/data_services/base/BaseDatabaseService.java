package com.danielkashin.yandexweatherapp.data.data_services.base;

import com.danielkashin.yandexweatherapp.util.ExceptionHelper;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;


public class BaseDatabaseService {

  private final StorIOSQLite sqLite;

  protected BaseDatabaseService(StorIOSQLite sqLite){
    ExceptionHelper.checkAllObjectsNonNull(sqLite);
    this.sqLite = sqLite;
  }

  protected StorIOSQLite getSQLite(){
    return sqLite;
  }

}

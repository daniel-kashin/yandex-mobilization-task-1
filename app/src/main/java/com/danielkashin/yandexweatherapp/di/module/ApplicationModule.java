package com.danielkashin.yandexweatherapp.di.module;

import android.content.Context;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

  private Context applicationContext;

  public ApplicationModule(Context context) {
    ExceptionHelper.checkAllObjectsNonNull(context);
    this.applicationContext = context.getApplicationContext();
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return applicationContext;
  }
}

package com.danielkashin.yandexweatherapp.data.data_services.base;

import com.danielkashin.yandexweatherapp.di.module.ChangeableBaseUrl;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class BaseRemoteService<S> {

  private final String apiKey;

  private S dataService;


  public BaseRemoteService(ChangeableBaseUrl baseUrl, String apiKey, OkHttpClient okHttpClient){
    ExceptionHelper.checkAllObjectsNonNull(baseUrl, apiKey, okHttpClient);

    this.apiKey = apiKey;

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl.url())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build();
    this.dataService = createService(retrofit);
  }


  protected S getService() {
    return this.dataService;
  }

  protected String getApiKey() {
    return apiKey;
  }

  protected abstract S createService(Retrofit retrofit);

}

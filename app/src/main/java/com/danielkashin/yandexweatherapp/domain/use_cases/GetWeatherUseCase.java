package com.danielkashin.yandexweatherapp.domain.use_cases;
import android.os.AsyncTask;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.domain.async_task.AsyncTaskResponse;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;
import static com.danielkashin.yandexweatherapp.domain.async_task.AsyncTaskResponse.RunnableResponse;
import static com.danielkashin.yandexweatherapp.domain.async_task.AsyncTaskResponse.PostExecuteListenerResponse;

public class GetWeatherUseCase {

  private final WeatherRepository weatherRepository;

  private AsyncTaskResponse<Weather> asyncTaskResponse;

  public GetWeatherUseCase(WeatherRepository weatherRepository) {
    ExceptionHelper.checkAllObjectsNonNull(weatherRepository);
    this.weatherRepository = weatherRepository;
  }

  public boolean isRunning() {
    return asyncTaskResponse != null
        && asyncTaskResponse.getStatus() == AsyncTask.Status.RUNNING
        && !asyncTaskResponse.isCancelled();
  }

  public void dismiss() {
    asyncTaskResponse.cancel(false);
    asyncTaskResponse = null;
  }

  public void run(final Callbacks callbacks, final boolean firstStart) {
    ExceptionHelper.checkAllObjectsNonNull(callbacks);

    RunnableResponse<Weather> runnable = new RunnableResponse<Weather>() {
      @Override
      public Weather run() throws ExceptionBundle {
        return weatherRepository.getWeather(!firstStart);
      }
    };

    PostExecuteListenerResponse<Weather> postExecuteListener =
        new PostExecuteListenerResponse<Weather>() {
      @Override
      public void onResult(Weather result) {
        callbacks.onGetWeatherSuccess(result);
      }

      @Override
      public void onException(ExceptionBundle exception) {
        callbacks.onGetWeatherException(exception);
      }
    };

    asyncTaskResponse = new AsyncTaskResponse<>(runnable, postExecuteListener);
    asyncTaskResponse.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }


  public interface Callbacks {

    void onGetWeatherSuccess(Weather weather);

    void onGetWeatherException(ExceptionBundle exceptionBundle);

  }
}

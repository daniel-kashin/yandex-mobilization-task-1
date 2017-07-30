package com.danielkashin.yandexweatherapp.domain.use_cases;

import android.os.AsyncTask;
import android.util.Log;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.data.repository.WeatherRepository;
import com.danielkashin.yandexweatherapp.domain.async_task.AsyncTaskResponse;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.danielkashin.yandexweatherapp.domain.async_task.AsyncTaskResponse.PostExecuteListenerResponse;
import static com.danielkashin.yandexweatherapp.domain.async_task.AsyncTaskResponse.RunnableResponse;

public class GetWeatherUseCase {

  private final WeatherRepository weatherRepository;

  private AsyncTaskResponse<Weather> asyncTaskResponse;
  private final Queue<Callbacks> otherListeners = new ConcurrentLinkedQueue<>();

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
    if (asyncTaskResponse != null) { //app crashes in onDestroy with NPE
      asyncTaskResponse.cancel(false);
      asyncTaskResponse = null;
    }
  }

  public void run(final Callbacks callbacks, final boolean firstStart) {
    ExceptionHelper.checkAllObjectsNonNull(callbacks);

    RunnableResponse<Weather> runnable = () -> weatherRepository.getWeather(!firstStart);
    PostExecuteListenerResponse<Weather> postExecuteListener =
        new PostExecuteListenerResponse<Weather>() {
      @Override
      public void onResult(Weather result) {
        callbacks.onGetWeatherSuccess(result);
        notifyListenerAboutSuccess(result);

      }

      @Override
      public void onException(ExceptionBundle exception) {
        callbacks.onGetWeatherException(exception);
        notifyOtherListenerAboutException(exception);
      }
    };

    asyncTaskResponse = new AsyncTaskResponse<>(runnable, postExecuteListener);
    asyncTaskResponse.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  public void addAdditionalListener(Callbacks callbacks){
    otherListeners.add(callbacks);
  }

  public void removeListener(Callbacks callbacks){
    final boolean removed = otherListeners.remove(callbacks);
    if (!removed) {
      throw new IllegalArgumentException("Listener was not registered!");
    }
  }

  public int size(){
    return otherListeners.size();
  }

  private void notifyListenerAboutSuccess(Weather result) {
    for (Callbacks listener : otherListeners) {
      listener.onGetWeatherSuccess(result);
    }
  }

  private void notifyOtherListenerAboutException(ExceptionBundle exception) {
    for (Callbacks listener : otherListeners) {
      listener.onGetWeatherException(exception);
    }
  }

  public interface Callbacks {

    void onGetWeatherSuccess(Weather weather);

    void onGetWeatherException(ExceptionBundle exceptionBundle);

  }
}

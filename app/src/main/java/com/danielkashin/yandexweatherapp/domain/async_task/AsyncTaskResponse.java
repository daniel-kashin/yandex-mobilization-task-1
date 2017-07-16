package com.danielkashin.yandexweatherapp.domain.async_task;

import android.support.v4.util.Pair;

import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;


public class AsyncTaskResponse<T> extends VoidAsyncTask<Pair<T, ExceptionBundle>> {

  private RunnableResponse<T> repositoryRunnable;
  private PostExecuteListenerResponse<T> postExecuteListener;


  public AsyncTaskResponse(RunnableResponse<T> repositoryRunnable,
                           PostExecuteListenerResponse<T> postExecuteListener) {
    ExceptionHelper.checkAllObjectsNonNull(repositoryRunnable);
    this.repositoryRunnable = repositoryRunnable;
    this.postExecuteListener = postExecuteListener;
  }

  // --------------------------------------- AsyncTask --------------------------------------------

  @Override
  protected void onCancelled() {
    repositoryRunnable = null;
    postExecuteListener = null;
  }

  @Override
  protected Pair<T, ExceptionBundle> doInBackground(Void... params) {
    try {
      if (repositoryRunnable != null) {
        T result = repositoryRunnable.run();
        return new Pair<>(result, null);
      } else {
        return new Pair<>(null, null);
      }
    } catch (ExceptionBundle exception) {
      return new Pair<>(null, exception);
    }
  }

  @Override
  protected void onPostExecute(Pair<T, ExceptionBundle> result) {
    super.onPostExecute(result);
    if (!isCancelled() && postExecuteListener != null && result.second != null) {
      postExecuteListener.onException(result.second);
    } else if (!isCancelled() && postExecuteListener != null) {
      postExecuteListener.onResult(result.first);
    }
  }

  // ------------------------------------ inner types ---------------------------------------------

  public interface PostExecuteListenerResponse<T> {

    void onResult(T result);

    void onException(ExceptionBundle exception);

  }

  public interface RunnableResponse<T> {

    T run() throws ExceptionBundle;

  }
}
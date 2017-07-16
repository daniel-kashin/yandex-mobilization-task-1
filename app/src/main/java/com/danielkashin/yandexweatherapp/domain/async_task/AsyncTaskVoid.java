package com.danielkashin.yandexweatherapp.domain.async_task;

import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.util.ExceptionHelper;


public class AsyncTaskVoid extends VoidAsyncTask<ExceptionBundle> {

  private RunnableVoid repositoryRunnable;
  private PostExecuteListenerVoid postExecuteListener;


  public AsyncTaskVoid(RunnableVoid repositoryRunnable,
                       PostExecuteListenerVoid postExecuteListener) {
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
  protected ExceptionBundle doInBackground(Void... params) {
    try {
      if (repositoryRunnable != null) {
        repositoryRunnable.run();
      }
      return null;
    } catch (ExceptionBundle exception) {
      return exception;
    }
  }

  @Override
  protected void onPostExecute(ExceptionBundle exception) {
    super.onPostExecute(exception);
    if (postExecuteListener != null && !isCancelled()) {
      if (exception != null && !isCancelled()) {
        postExecuteListener.onException(exception);
      } else if (!isCancelled()) {
        postExecuteListener.onResult();
      }
    }
  }

  // ------------------------------------ inner types ---------------------------------------------

  public interface PostExecuteListenerVoid {

    void onResult();

    void onException(ExceptionBundle exception);

  }

  public interface RunnableVoid {

    void run() throws ExceptionBundle;

  }
}
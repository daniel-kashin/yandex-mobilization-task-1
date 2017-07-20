package com.danielkashin.yandexweatherapp.data.services.refresh;

import com.danielkashin.yandexweatherapp.util.ExceptionHelper;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;


public class RefreshDatabaseManager {

  private static final int MIN_PERIOD_IN_MILLIS = 300001;

  public static void setCurrentRefreshPolicy(Period period) {
    if (period != null) {
      JobManager.instance().cancelAllForTag(RefreshDatabaseJob.TAG);

      if (period != Period.DISABLED) {
        new JobRequest.Builder(RefreshDatabaseJob.TAG)
            .setPeriodic(getMillisecondPeriod(period), MIN_PERIOD_IN_MILLIS)
            .setPersisted(true)
            .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
            .build()
            .schedule();
      }
    }
  }

  public static Period getPeriod(String identifier) {
    ExceptionHelper.checkAllObjectsNonNull(identifier);
    switch (identifier) {
      case "Never": return Period.DISABLED;
      case "Half an hour": return Period.HALF_AN_HOUR;
      case "One hour": return Period.ONE_HOUR;
      case "Three hours": return Period.THREE_HOURS;
      case "Six hours": return Period.SIX_HOURS;
      case "Twelve hours": return Period.TWELVE_HOURS;
      case "Twenty four hours": return Period.TWENTY_FOUR_HOURS;
      default: throw new IllegalStateException("Unknown period");
    }
  }

  private static long getMillisecondPeriod(Period period) {
    if (period == Period.DISABLED) throw new IllegalStateException("Cannot convert DISABLED period into duration");
    return TimeUnit.MINUTES.toMillis(period.getMinutes());
  }

  // --------------------------------------- inner classes ----------------------------------------

  public enum Period {
    DISABLED(-1),
    HALF_AN_HOUR(30),
    ONE_HOUR(60),
    THREE_HOURS(180),
    SIX_HOURS(360),
    TWELVE_HOURS(720),
    TWENTY_FOUR_HOURS(1440);

    private final long minutes;

    Period(long minutes) {
      this.minutes = minutes;
    }

    public long getMinutes() {
      return minutes;
    }
  }
}
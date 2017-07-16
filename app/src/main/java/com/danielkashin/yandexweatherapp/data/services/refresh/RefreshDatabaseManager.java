package com.danielkashin.yandexweatherapp.data.services.refresh;

import com.danielkashin.yandexweatherapp.util.ExceptionHelper;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;


public class RefreshDatabaseManager {

  public static void setCurrentRefreshPolicy(Period period) {
    if (period != null) {
      JobManager.instance().cancelAllForTag(RefreshDatabaseJob.TAG);

      if (period != Period.DISABLED) {
        new JobRequest.Builder(RefreshDatabaseJob.TAG)
            .setPeriodic(getMillisecondPeriod(period))
            .setPersisted(true)
            .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
            .setRequirementsEnforced(true)
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
    int halfHours;
    switch (period) {
      case HALF_AN_HOUR: halfHours = 1; break;
      case ONE_HOUR: halfHours = 2; break;
      case THREE_HOURS: halfHours = 6; break;
      case SIX_HOURS: halfHours = 12; break;
      case TWELVE_HOURS: halfHours = 24; break;
      case TWENTY_FOUR_HOURS: halfHours = 48; break;
      default: throw new IllegalStateException("Unknown period");
    }

    return halfHours * 30 * 60 * 1000;
  }

  public enum Period {
    DISABLED,
    HALF_AN_HOUR,
    ONE_HOUR,
    THREE_HOURS,
    SIX_HOURS,
    TWELVE_HOURS,
    TWENTY_FOUR_HOURS
  }
}
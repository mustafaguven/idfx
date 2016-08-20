package mu.comon.utils;

import android.content.Context;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import mu.comon.R;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by mustafaguven on 14.10.2015.
 */
public class DateTimeUtils {

  public static Calendar getDefaultCalendar() {
    Calendar c = Calendar.getInstance();
    c.set(2000, 1, 1);
    return c;
  }

  public static String getCurrentTime() {

    return getCurrentTime(new DateTime());
  }

  public static String getCurrentTime(DateTime dt) {

    int hour = dt.getHourOfDay();
    int min = dt.getMinuteOfHour();

    return String.format("%s:%s", hour < 10 ? "0" + hour : hour, min < 10 ? "0" + min : min);
  }

  public static String getTime(String date) {
    return getCurrentTime(new DateTime(convertToDate(date)));
  }

  public static String getTime(Date date) {
    return getCurrentTime(new DateTime(date));
  }

  public static int getMinuteDiffWithCurrentTime(String date) {

    DateTime currentDate = new DateTime();

    DateTime compDate = new DateTime(convertToDate(date));

    Minutes diff = Minutes.minutesBetween(currentDate, compDate);

    return diff.getMinutes();
  }

  public static int getMinuteDiffWithCurrentTime(Date date) {

    DateTime currentDate = new DateTime();

    DateTime compDate = new DateTime(date);

    Minutes diff = Minutes.minutesBetween(currentDate, compDate);

    return diff.getMinutes();
  }

  public static String getMinuteDiffWithCurrentTimeForTrip(String date) {

    int minutes = getMinuteDiffWithCurrentTime(date);

    if (minutes > 0 && minutes < 60) {
      return minutes + " dk kaldı";
    } else {
      return "";
    }
  }

  public static String getMinuteDiffWithCurrentTimeForTrip(Date date) {

    int minutes = getMinuteDiffWithCurrentTime(date);

    if (minutes > 0 && minutes < 60) {
      return minutes + " dk kaldı";
    } else {
      return "";
    }
  }

  public static boolean isFirstDateBiggerThanSecondDate(Date firstDate, Date secondDate) {
    return firstDate.getTime() > secondDate.getTime();
  }

  public static String getFormattedDateForService(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ssZ", Locale.getDefault());
    return dateFormat.format(date);
  }

  public static String getFormattedDate(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    return dateFormat.format(date);
  }

  public static String getFormattedDate(Calendar date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    return dateFormat.format(date.getTime());
  }

  public static String getFormattedDateAsFullMonthName(Date date) {
    return DateTimeFormat.forPattern("dd MMMM yyyy")
        .withLocale(new Locale("tr", "TR"))
        .print(new DateTime(date));
  }

  public static String getFormattedHour(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    return dateFormat.format(date);
  }

  public static int getTimeSpan(
      long firstDateInMilliSeconds, long secondDateInMilliSeconds, TimeUnit timeUnit
  ) {
    int val = -1;
    if (timeUnit == TimeUnit.YEAR) {
      val = (int) ((firstDateInMilliSeconds - secondDateInMilliSeconds)
          / 1000
          / 60
          / 60
          / 24
          / 12
          / 365);
    } else if (timeUnit == TimeUnit.MONTH) {
      val = (int) ((firstDateInMilliSeconds - secondDateInMilliSeconds) / 1000 / 60 / 60 / 24 / 12);
    } else if (timeUnit == TimeUnit.DAY) {
      val = (int) ((firstDateInMilliSeconds - secondDateInMilliSeconds) / 1000 / 60 / 60 / 24);
    } else if (timeUnit == TimeUnit.HOUR) {
      val = (int) ((firstDateInMilliSeconds - secondDateInMilliSeconds) / 1000 / 60 / 60);
    } else if (timeUnit == TimeUnit.MINUTE) {
      val = (int) ((firstDateInMilliSeconds - secondDateInMilliSeconds) / 1000 / 60);
    } else if (timeUnit == TimeUnit.SECOND) {
      val = (int) ((firstDateInMilliSeconds - secondDateInMilliSeconds) / 1000);
    }
    return val;
  }

  public static String getSummarizedDateName(Context context, Calendar selectedDate) {
    String formattedDate = getFormattedDate(new Date(selectedDate.getTimeInMillis()));
    Calendar today = new GregorianCalendar(Locale.getDefault());
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.HOUR, 0);
    today.set(Calendar.SECOND, 0);
    String todayFormatted = getFormattedDate(today.getTime());

    Calendar tomorrow = new GregorianCalendar(Locale.getDefault());
    tomorrow.setTimeInMillis(today.getTimeInMillis());
    tomorrow.add(Calendar.DATE, 1);
    String tomorrowFormatted = getFormattedDate(tomorrow.getTime());

    int timeSpanAsHour =
        getTimeSpan(selectedDate.getTimeInMillis(), today.getTimeInMillis(), TimeUnit.HOUR);
    String nameDay = String.format("%s", formattedDate);

    if (todayFormatted.contentEquals(nameDay)) {
      nameDay = context.getResources().getString(R.string.today);
    } else if (tomorrowFormatted.contentEquals(nameDay)) {
      nameDay = context.getResources().getString(R.string.tomorrow);
    } else if (timeSpanAsHour > 48 && timeSpanAsHour < (24 * 7)) {
      nameDay = new SimpleDateFormat("EEEE", new Locale("tr", "TR")).format(
          new Date(selectedDate.getTimeInMillis()));
    }
    return nameDay;
  }

  public static String getSummarizedDateName(Context context, Date date) {
    Calendar calendar = new GregorianCalendar(Locale.getDefault());
    calendar.setTimeInMillis(date.getTime());
    return getSummarizedDateName(context, calendar);
  }

  public static String getSummarizedDateName(Context context, String date) {
    Date d = convertToDate(date);
    return getSummarizedDateName(context, d);
  }


/*    public static Date convertToDate(String dateString, String format) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
        }
        return date;
    }*/

  public static Date convertToDate(String dateString) {
    return convertToDate(dateString);
    //return convertToDate(dateString, "yyyy-MM-ddHH:mm:ssZ");
  }

  public static Calendar convertToCalendar(String dateString) {
    Date d = convertToDate(dateString);
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(d.getTime());
    return c;
  }

  public static String convertToDateString(Calendar calendar) {
    String date = new SimpleDateFormat("yyyy-MM-ddHH:mm:ssZ", Locale.getDefault()).format(
        new Date(calendar.getTimeInMillis()));
    return date;
  }

  public static String parseFacebookBirthdateToServerDate(String facebookDate) {
    if (facebookDate != null) {
      SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
      SimpleDateFormat dateFormat2 =
          new SimpleDateFormat("yyyy-MM-ddHH:mm:ssZ", Locale.getDefault());
      try {
        return dateFormat2.format(dateFormat1.parse(facebookDate));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return "";
  }

  public static String parseFacebookWorkStartDateToServerDate(String facebookDate) {
    if (facebookDate != null) {
      SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
      SimpleDateFormat dateFormat2 =
          new SimpleDateFormat("yyyy-MM-ddHH:mm:ssZ", Locale.getDefault());
      try {
        return dateFormat2.format(dateFormat1.parse(facebookDate));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return "";
  }

  public static String getDateAsSearchResultType(Context context, String date) {
    return String.format(
        "%s, %s", getSummarizedDateName(context, date),
        getFormattedDateAsFullMonthName(convertToDate(date))).toUpperCase();
  }

  public static String parseDateStringToYear(String dateString) {
    String[] dateString1 = dateString.split("-");
    if (dateString1[0] != null && dateString1[0].length() == 4) {
      return dateString1[0];
    }

    if (dateString1[1] != null) {
      String[] dateString2 = dateString1[1].split("-");
      if (dateString2[0] != null && dateString2[0].length() == 4) {
        return dateString2[0];
      }

      if (dateString2[1] != null) {
        String[] dateString3 = dateString2[1].split("-");
        if (dateString3[0] != null && dateString3[0].length() == 4) {
          return dateString3[0];
        }
      }
    }
    return null;
  }

  public static String parseDateFromDNRDate(String date) {
    String s = date.replace("/Date(", "").replace(")/", "");
    Date d = new Date(Long.parseLong(s));
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    return dateFormat.format(d);
  }

  public enum TimeUnit {
    YEAR, MONTH, DAY, HOUR, MINUTE, SECOND
  }
}

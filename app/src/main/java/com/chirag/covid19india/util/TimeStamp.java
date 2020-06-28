package com.chirag.covid19india.util;

import android.text.format.DateFormat;
import android.util.Log;

import com.chirag.covid19india.ApplicationClass;
import com.chirag.covid19india.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimeStamp {
    private static TimeZone tz = TimeZone.getDefault();
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static final String DateFormate = "dd-MM-yyyy";
    public static final String DateFormateHistory = "dd MMM yyyy @ hh:mm aa";
    public static final String TimeFormateHistory = "hh:mm aa";
    public static final String DateFormateMonth = "dd MMM yyyy";
    public static final String MyPostDateFormate = "EEE. dd.MM.yyyy";
    public static final String MonthYear = "MMM yyyy";
    public static final String DAY = "dd";
    public static final String MONTH = "MM";
    public static final String YEAR = "yyyy";
    public static final String TimeFormate = "hh:mm aa";
    public static final String TimeFormateWithoutampm = "HH:mm";
    public static final String RideDetailDateFormat = "dd MMM yyyy, HH:mm";
    public static final String FullDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.Z";
    public static final String OnkoutDayFormat = "EEEE";
    public static final String OnkoutDateFormat = "dd | MMMM | yyyy";

    public static long formatToSeconds(String value, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date mDate = sdf.parse(value);
            return TimeUnit.MILLISECONDS.toSeconds(mDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long formatToSecondsLocal(String value, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getDefault());
            Date mDate = sdf.parse(value);
            return TimeUnit.MILLISECONDS.toSeconds(mDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long dateToSeconds(String givenDateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
            Date mDate = sdf.parse(givenDateString);
            return TimeUnit.MILLISECONDS.toSeconds(mDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long dateToSecondsInUTC(String givenDateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date mDate = sdf.parse(givenDateString);
            return TimeUnit.MILLISECONDS.toSeconds(mDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long ddMMMyyyyHHmmssToSecondsInUTC(String givenDateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date mDate = sdf.parse(givenDateString);
            return TimeUnit.MILLISECONDS.toSeconds(mDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long ISOtoUTC(String givenDateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date mDate = sdf.parse(givenDateString);
            return TimeUnit.MILLISECONDS.toSeconds(mDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String ddMMMyyyyhhmma(String secondsTime) {
        return ddMMMyyyyhhmma(Long.parseLong(secondsTime));
    }

    public static String ddMMMyyyy(String secondsTime) {
        return ddMMMyyyy(Long.parseLong(secondsTime));
    }

    public static String ddMMMyyyyhhmma(long secondsTime) {
        TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeZone(tz);
        cal.setTimeInMillis(secondsTime * 1000L);
        return new SimpleDateFormat("dd MMM yyyy, hh:mm a ", Locale.ENGLISH).format(cal.getTime());
    }

    public static String dMMMhhmma(long secondsTime) {
        TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeZone(tz);
        cal.setTimeInMillis(secondsTime * 1000L);
        return new SimpleDateFormat("d MMM, HH:mm", Locale.ENGLISH).format(cal.getTime());
    }

    public static String ddMMMMyyyyhhmma(long secondsTime) {
        TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeZone(tz);
        cal.setTimeInMillis(secondsTime * 1000L);
        return new SimpleDateFormat("dd | MMMM | yyyy - hh:mm a ", Locale.ENGLISH).format(cal.getTime());
    }

    public static String ddMMMyyyy(long secondsTime) {
        TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeZone(tz);
        cal.setTimeInMillis(secondsTime * 1000L);
        return new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH).format(cal.getTime());
    }

    public static String hhmma(long secondsTime) {
        TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeZone(tz);
        cal.setTimeInMillis(secondsTime * 1000L);
        return new SimpleDateFormat("hh:mm a ", Locale.ENGLISH).format(cal.getTime());
    }

    public static String secondsToFormat(String secondsTime, String format) {
        return secondsToFormat(Long.parseLong(secondsTime), format);
    }

    public static String secondsToFormat(long secondsTime, String format) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeZone(tz);
        cal.setTimeInMillis(secondsTime * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(cal.getTime());

    }

    public static String[] getCurrentDateElements() {
        /*Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        String[] strDate = new String[4];
        strDate[0] = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
        strDate[1] = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        strDate[2] = String.valueOf(cal.get(Calendar.MONTH));
        strDate[3] = String.valueOf(cal.get(Calendar.YEAR));*/

        String[] strDate = new String[2];
        strDate[0] = String.valueOf(getDateFromTimestamp(System.currentTimeMillis(), "EEEE"));
        strDate[1] = String.valueOf(getDateFromTimestamp(System.currentTimeMillis(), "dd | MMMM | yyyy"));
        return strDate;
    }

    public static boolean canReturn(long date, int duration) {
        TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
        Calendar after48hrs = Calendar.getInstance(Locale.ENGLISH);
        after48hrs.setTimeInMillis(date * 1000L);
        if (duration > 0)
            after48hrs.add(Calendar.MILLISECOND, (int) TimeUnit.HOURS.toMillis(duration));
        else after48hrs.add(Calendar.DATE, +2);
        after48hrs.setTimeZone(tz);
        return System.currentTimeMillis() < after48hrs.getTimeInMillis();
    }

    public static long timeToSeconds(String givenDateString) {
        try {
            String[] getTime = givenDateString.split(" ");
            long hours;
            long mins;
            String[] newTime = getTime[0].split(":");
            if (getTime[1].equalsIgnoreCase("am")) {
                hours = Long.parseLong(newTime[0]) * 60 * 60;
                mins = Long.parseLong(newTime[1]) * 60;
            } else {
                hours = (Long.parseLong(newTime[0]) + 12) * 60 * 60;
                mins = Long.parseLong(newTime[1]) * 60;
            }
            return (hours + mins);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getYear(long timestamp) {
        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat(YEAR, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        date = formatter.format(new Date(timestamp * 1000));
        return date;
    }

    public static String MonthYear(long timestamp) {
        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat(MonthYear, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        date = formatter.format(new Date(timestamp * 1000));
        return date;
    }

    public static String getFullDateAndTime(long timestamp) {
        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat(FullDateTimeFormat, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        date = formatter.format(new Date(timestamp * 1000));
        return date;
    }

    public static int getDifferencesYear(long timestampstart, long timestampend) {

        long starttime = timestampstart / 60;
        long endtime = timestampend / 60;
        long difference = endtime - starttime;
        if (difference > 0) {
            if (difference > 525600) {
                return (int) difference / 525600;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static long getCurrentUTC() {
        Calendar cal = Calendar.getInstance();
        long currenttime = System.currentTimeMillis();
        Log.e("TimestampRaw", "==> " + currenttime);
        long offset = cal.getTimeZone().getOffset(currenttime);
        Log.e("Current offset", "==> " + offset);
        return currenttime - offset;
    }

    public static String getUTCDate(long timestamp, String DateFormat) {
        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormat, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        date = formatter.format(new Date(timestamp));
        return date;
    }

    public static int getRemainingMonth(long timestampstart, long timestampend) {

        long starttime = timestampstart / 60;
        long endtime = timestampend / 60;
        long difference = endtime - starttime;
        if (difference > 0) {
            difference = difference % 525600;

            int months = (int) difference / 43800;

            return months;
        } else {
            return 0;
        }
    }


    public static String getDateFromTimestamp(long timestamp, String DateFormate) {

        if (timestamp < 1000000000000L) {
            timestamp *= 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormate, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getDefault());
        if (timestamp > 0) {
            Date milidate = new Date(timestamp);
            return sdf.format(milidate);
        } else {
            return "";
        }
    }

    public static String getUTCDateFromTimestamp(long timestamp, String DateFormat) {
        if (timestamp < 1000000000000L) {
            timestamp *= 1000;
        }

        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormat, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        date = formatter.format(new Date(timestamp));
        return date;
    }

    public static int findAge(long secondsTime) {
        TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(secondsTime * 1000L);
        cal.setTimeZone(tz);
        String date = DateFormat.format("dd MM yyyy", cal).toString();
        String[] age = date.split(" ");


        return calculateMyAge(Integer.parseInt(age[2]), Integer.parseInt(age[1]), Integer.parseInt(age[0]));
    }

    private static int calculateMyAge(int year, int month, int day) {
        Calendar birthCal = new GregorianCalendar(year, month, day);

        Calendar nowCal = new GregorianCalendar();

        int age = nowCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);

        boolean isMonthGreater = birthCal.get(Calendar.MONTH) >= nowCal
                .get(Calendar.MONTH);

        boolean isMonthSameButDayGreater = birthCal.get(Calendar.MONTH) == nowCal
                .get(Calendar.MONTH)
                && birthCal.get(Calendar.DAY_OF_MONTH) > nowCal
                .get(Calendar.DAY_OF_MONTH);

        if (isMonthGreater || isMonthSameButDayGreater) {
            age = age - 1;
        }
        return age;
    }

    public static String getTimeFromHours(long time) {
        int min = (int) (time / 60);
        int hours = min / 60;
        int remaining_min = min % 60;

        if (hours > 12) {
            return String.format(Locale.ENGLISH, "%02d:%02d %s", hours - 12, remaining_min, "PM");
        } else if (hours < 12) {
            return String.format(Locale.ENGLISH, "%02d:%02d %s", hours, remaining_min, "AM");
        } else {
            return String.format(Locale.ENGLISH, "%02d:%02d %s", hours, remaining_min, "PM");
        }
    }

    public static String dd_MM_yyyy(long secondsTime) {
        TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeZone(tz);
        cal.setTimeInMillis(secondsTime * 1000L);
        return new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(cal.getTime());
    }

    public static String getPrettyTime(long seconds) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeZone(tz);
        calendar.setTimeInMillis(seconds * 1000L);
        Calendar now = Calendar.getInstance(Locale.ENGLISH);
        now.setTimeZone(tz);
        if (now.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
            return ApplicationClass.getAppContext().getString(R.string.today);
        } else if (now.get(Calendar.DATE) - calendar.get(Calendar.DATE) == 1) {
            return ApplicationClass.getAppContext().getString(R.string.yesterday);
        } else {
            return new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH).format(calendar.getTime());
        }
    }

    public static String ddMMyyyy(long secondsTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTimeInMillis(secondsTime * 1000L);
        return new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(cal.getTime());
    }

    public static String customDateFormat(long secondsTime, String dateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTimeInMillis(secondsTime * 1000L);
        return new SimpleDateFormat(dateFormat, Locale.ENGLISH).format(cal.getTime());
    }

    public static String getHours(String string) {
        String[] time = string.split(" ");
        return time[0].split(":")[0];
    }

    public static String getMins(String string) {
        String[] time = string.split(" ");
        return time[0].split(":")[1];
    }

    public static int getDay(long timestamp) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTimeInMillis(timestamp * 1000L);
        String date = new SimpleDateFormat("dd", Locale.ENGLISH).format(cal.getTime());
        if (date != null && date.length() > 0) {
            return Integer.parseInt(date);
        } else {
            return -1;
        }
    }
}

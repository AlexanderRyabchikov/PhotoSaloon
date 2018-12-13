package com.photosaloon.utils;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateUtils {

    public static String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
    public static String DEFAULT_TIME_FORMAT = "HH:mm";
    public static String DEFAULT_DATE_TIME_FORMAT = "dd MMMM yyyy HH:mm";

    private static String TIME_REANGE_TEMPLATE = "%s - %s";

    private static String DATE_FORMAT_DAY = "dd";
    private static String DATE_FORMAT_DAY_MONTH = "dd MMMM";
    private static String DATE_FORMAT_FULL_DATE = "dd MMMM yyyy";
    private static String EVENT_DATE_TEMPLATE_RANGE = "%s - %s";

    public static Date resetTimeOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static String formatDate(@NonNull Date date) {
        Locale locale = new Locale("ru");
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT, locale);
        return format.format(date);
    }

    public static String formatDateTime(@NonNull Date date) {
        Locale locale =  new Locale("ru");
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT, locale);
        return format.format(date);
    }

    public static String formatTime(Date date) {
        return formatTime(date, TimeZone.getDefault());
    }

    public static String formatTime(Date date, TimeZone timeZone) {
        Locale locale =  new Locale("ru");
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_TIME_FORMAT, locale);
        format.setTimeZone(timeZone);
        return format.format(date);
    }

    public static Date utcToLocal(Date date) {
        return new Date(date.getTime() + TimeZone.getDefault().getRawOffset());
    }

    public static Date localToUtc(Date date) {
        return new Date(date.getTime() - TimeZone.getDefault().getRawOffset());
    }

    public static String formatDefaultDateTime(Date date) {
        Locale locale =  new Locale("ru");

        Calendar now = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.setTime(utcToLocal(date));

        int year = c.get(Calendar.YEAR);
        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);

        int nowYear = now.get(Calendar.YEAR);
        int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);

        String formatString;
        if (year == nowYear) {
            if (dayOfYear == nowDayOfYear) {
                return "Сегодня";
            } else if (dayOfYear == nowDayOfYear - 1) {
                return "Вчера";
            } else if (dayOfYear == nowDayOfYear - 2) {
                return "Позавчера";
            } else {
                formatString = DATE_FORMAT_DAY_MONTH;
            }
        } else {
            formatString = DEFAULT_DATE_FORMAT;
        }

        return new SimpleDateFormat(formatString, locale).format(date) + " в " + new SimpleDateFormat(DEFAULT_TIME_FORMAT, locale).format(date);
    }

    public static String formatDateRange(Date startDate, Date endDate) {
        Locale locale = new Locale("ru");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(startDate);
        c2.setTime(endDate);

        int d1 = c1.get(Calendar.DAY_OF_MONTH);
        int d2 = c2.get(Calendar.DAY_OF_MONTH);
        int m1 = c1.get(Calendar.MONTH);
        int m2 = c2.get(Calendar.MONTH);
        int y1 = c1.get(Calendar.YEAR);
        int y2 = c2.get(Calendar.YEAR);

        SimpleDateFormat dayFormat = new SimpleDateFormat(DATE_FORMAT_DAY, locale);
        SimpleDateFormat dayMonthFormat = new SimpleDateFormat(DATE_FORMAT_DAY_MONTH, locale);
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(DATE_FORMAT_FULL_DATE, locale);

        if (y1 == y2 && m1 == m2 && d1 == d2) {
            return fullDateFormat.format(startDate);
        } else {
            String firstDate;
            String secondDate;

            if (y1 == y2) {
                if (m1 == m2) {
                    firstDate = dayFormat.format(startDate);
                } else {
                    firstDate = dayMonthFormat.format(startDate);
                }
            } else {
                firstDate = fullDateFormat.format(startDate);
            }

            secondDate = fullDateFormat.format(endDate);

            return String.format(EVENT_DATE_TEMPLATE_RANGE, firstDate, secondDate);
        }
    }

    public static Date datePlusDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);

        return c.getTime();
    }

}

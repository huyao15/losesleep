package com.xianglanqi.losesleep.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String[] MONTH_STRINGS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec" };

    public static final String getMonthString(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return MONTH_STRINGS[calendar.get(Calendar.MONTH)];
    }

    public static final String getDayString(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            return "0" + day;
        }
        return "" + day;
    }
}

package com.xianglanqi.losesleep.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String[] MONTH_STRINGS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec" };

    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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

    public static final String getDayShow(final Date date) {
        return format.format(date);
    }

    public static final String getTimeShow(final Date date) {
        long now = System.currentTimeMillis();
        long diff = now - date.getTime();
        if (diff / 1000 < 5) {
            return "刚刚";
        }
        if (diff / 1000 < 60) {
            return diff / 1000 + "秒前";
        }
        if (diff / 60000 < 60) {
            return diff / 60000 + "分钟前";
        }
        if (diff / 3600000 < 24) {
            return diff / 3600000 + "小时前";
        }
        return diff / 86400000 + "天前";
    }
}

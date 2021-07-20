package com.ustudy.requestservice.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {
    public static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");


    public static Date stringToDate(String date) throws ParseException {
        return formatter.parse(date);
    }

    public static Date stringToDateTime(String date) throws ParseException {
        return timeFormatter.parse(date);
    }

    public static String dateToString(Date date) {
        return fmt.format(date);
    }

    public static String removeT(String time) {
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) == 'T') {
                time = time.replace(time.charAt(i), ' ');
            }
        }
        return time;
    }
}

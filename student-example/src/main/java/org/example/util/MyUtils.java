package org.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MyUtils {

    public static char getLastNameCharacter(String fullName) {
        String[] nameValues = fullName.split(" ");
        return nameValues[nameValues.length - 1].charAt(0);
    }

    public static float parseFloat(String input) {
        return Float.parseFloat(String.format("%.2f", Float.parseFloat(input)));
    }

    public static Date parseDateFromString(String input, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(input);
    }

    public static String parseDateToString(Date input, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.format(input);
    }

}

package vn.edu.vnua.qlsvfita.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {
    public static Date convertDateFromString(String dateStr, String dateTimeFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(dateStr);
    }

    public static Timestamp convertTimestampFromString(String inputDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setLenient(false);
        return new Timestamp(simpleDateFormat.parse(inputDate).getTime());
    }

    public static String convertStringFromTimestamp(Timestamp inputDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(inputDate);
    }
}

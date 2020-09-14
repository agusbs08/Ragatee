package com.hackaton.bri.ragatee.helper;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public static boolean compareDate(String date1String, String date2String, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        try{
            Date date1 = sdf.parse(date1String);
            Date date2 = sdf.parse(date2String);
            return date1.equals(date2);
        } catch (Exception e){
            Log.d("listpercomp", e.getMessage());
            return false;
        }
    }

    public static String changeDateFormat(String oldDateString, String oldFormat, String newFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
        try{
            Date date = sdf.parse(oldDateString);
            sdf.applyPattern(newFormat);
            return sdf.format(date);
        }
        catch (Exception e){
            return null;
        }
    }

    public static Date convertStringToDate(String dateString, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        try{
            return sdf.parse(dateString);
        } catch (Exception e){
            return null;
        }
    }

    public static String convertDateToString(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        return sdf.format(date);
    }

    public static Date getCurrentDate(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        Date date = Calendar.getInstance().getTime();
        String str = sdf.format(date);
        try {
            return sdf.parse(str);
        } catch (Exception e){
            return null;
        }
    }

    public static Integer getDaysFromMilis(Long milis) {
        milis = milis /1000/60/60/24 ;
        return milis.intValue();
    }
}

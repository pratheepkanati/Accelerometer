package com.lirctek.ics;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class Util {
    public static final String DATE_FORMAT_6 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public  static float  getFloat(Context context,String key){
        SharedPreferences prefs =context. getSharedPreferences("DATA", MODE_PRIVATE);
        return prefs.getFloat(key,0);
    }
    public static  void  setFloat(Context context,String key ,float value){

        SharedPreferences.Editor editor = context.getSharedPreferences("DATA", MODE_PRIVATE).edit();
        editor.putFloat(key,value);
        editor.commit();
    }

    public static long currentTimeUnixTimeStamp(TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdfAmerica = new SimpleDateFormat(DATE_FORMAT_6, Locale.US);
        sdfAmerica.setTimeZone(timeZone);
        return (calendar.getTime()).getTime() / 1000;

    }
    public static long getTimestmap() {
        long DateAndTime = currentTimeUnixTimeStamp(TimeZone.getDefault());
        return DateAndTime;
    }
    public static String currentTime(TimeZone timeZone) {
        SimpleDateFormat sdfAmerica = new SimpleDateFormat(DATE_FORMAT_6, Locale.US);
        sdfAmerica.setTimeZone(timeZone);
        return sdfAmerica.format(new Date());

    }
    public String getIntString(float speed)
    {
        Float aFloat=speed;
        return  Integer.toString(aFloat.intValue());
    }
}

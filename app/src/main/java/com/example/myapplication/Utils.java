package com.example.myapplication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utils {

    public static ColorDrawable[] vibrantDrawableColorList = {
            new ColorDrawable(Color.parseColor("#ffeead")),
            new ColorDrawable(Color.parseColor("#93cfb3")),
            new ColorDrawable(Color.parseColor("#fd7a7a")),
            new ColorDrawable(Color.parseColor("#faca5f")),
            new ColorDrawable(Color.parseColor("#1ba798")),
            new ColorDrawable(Color.parseColor("#6aa9ae")),
            new ColorDrawable(Color.parseColor("#ffbf27")),
            new ColorDrawable(Color.parseColor("#d93947"))    };

    public static ColorDrawable getRandomDrawableColor() {
        int index = new Random().nextInt(vibrantDrawableColorList.length);
        return vibrantDrawableColorList[index];
    }

    public static String DateToTimeFormat(String oldString) {
        PrettyTime p = new PrettyTime();
        String isTime = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd'T'HH:mm:ss''Z");
            Date date = sdf.parse(oldString);
            isTime = p.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isTime;
    }


    public static String DateToTimeFormater(String oldString) {
        PrettyTime p = new PrettyTime(new Locale(getCountry()));
        String isTime = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss 'Z'", Locale.ENGLISH);
            Date date = sdf.parse(oldString);
            isTime = p.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isTime;

    }

    public static String DateFormat(String oldDate) {

        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, yyyy MM dd", new Locale(getCountry()));

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldDate);
            newDate = dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldDate;
        }
        return newDate;
    }

    public static String DateFormater(String oldString) {
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldString);
            newDate = dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldString;
        }
        return newDate;
    }


    private static String getCountries() {
        Locale locale = Locale.getDefault();
        String county = String.valueOf(locale.getCountry());
        return county.toLowerCase();
    }

    public static String getLanguages (){
        Locale locale = Locale.getDefault();
        String language = String.valueOf(locale.getLanguage());
        return language.toLowerCase();
    }

    public static String getCountry() {
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        String language = String.valueOf(locale.getLanguage());
        return language.toLowerCase();

    }


}

package com.example.prasanna.trainshadule.Utilities;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by prasanna on 4/22/17.
 */

public abstract class NumberToWord {

    private static final String[] tensNames = {
            "twenty",
            "thirty",
            "forty",
            "fifty",
            "sixty",
            "seventy",
            "eighty",
            "ninety"
    };

    private static final String[] numNames = {
            "",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen"
    };

    public static String getTimeDuration(String depatureTime, String arrivalTime){
        String[] depature = depatureTime.split(":");
        String[] arrival = arrivalTime.split(":");

        int sec_difference, min_difference, hour_difference;

        if(Integer.parseInt(arrival[2]) > Integer.parseInt(depature[2])) {
            sec_difference = Integer.parseInt(arrival[2]) - Integer.parseInt(depature[2]);
        }else{
            sec_difference = (Integer.parseInt(arrival[2])+60) - Integer.parseInt(depature[2]);
            arrival[1] = String.valueOf(Integer.parseInt(arrival[1])-1);
        }

        if(Integer.parseInt(arrival[1]) > Integer.parseInt(depature[1])) {
            min_difference = Integer.parseInt(arrival[1]) - Integer.parseInt(depature[1]);
        }else{
            min_difference = (Integer.parseInt(arrival[1])+60) - Integer.parseInt(depature[1]);
            arrival[0] = String.valueOf(Integer.parseInt(arrival[0])-1);
        }

        if(Integer.parseInt(arrival[0]) >= Integer.parseInt(depature[0])) {
            hour_difference = Integer.parseInt(arrival[0]) - Integer.parseInt(depature[0]);
        }else{
            return arrivalTime;
        }

        if(sec_difference >= 60){
            min_difference++;
            sec_difference = sec_difference - 60;
        }

        if(min_difference >= 60){
            hour_difference++;
            min_difference = min_difference - 60;
        }

        String ret = "";

        String hour, min, sec;

        if (hour_difference < 20){
            hour = numNames[hour_difference];
        }else{
            int prefix = hour_difference / 10;
            int suffix = hour_difference % 10;
            hour = tensNames[prefix-2];
            hour = hour + " " +  numNames[suffix];
        }

        if (!hour.replace(" ","").equals("")) {
            ret = ret + hour + " hour ";
        }

        if (min_difference < 20){
            min = numNames[min_difference];
        }else{
            int prefix = min_difference / 10;
            int suffix = min_difference % 10;
            min = tensNames[prefix-2];
            min = min + " " +  numNames[suffix];
        }

        if (!min.replace(" ","").equals("")) {
            if(hour.replace(" ","").equals("")){
                ret = ret + min + " minutes ";
            }else {
                ret = ret + ", " + min + " minutes ";
            }
        }

        if (sec_difference < 20){
            sec = numNames[sec_difference];
        }else{
            int prefix = sec_difference / 10;
            int suffix = sec_difference % 10;
            sec = tensNames[prefix-2];
            sec = sec  + " " +   numNames[suffix];
        }

        if (!sec.replace(" ","").equals("")) {
            if(min.replace(" ","").equals("")){
                ret = ret + sec + " seconds";
            }else {
                ret = ret + " and " + sec + " seconds";
            }

        }


        String firstLetter = ret.substring(0,1);
        return firstLetter.toUpperCase() + ret.substring(1);
    }
}

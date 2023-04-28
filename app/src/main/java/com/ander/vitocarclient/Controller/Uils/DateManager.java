package com.ander.vitocarclient.Controller.Uils;

import java.time.LocalDateTime;

public class DateManager {
   public static LocalDateTime parseDate(String date, String time) {
       return LocalDateTime.parse(date + "T" + time);
    }
    public static Boolean passedDate(String date){
       // Get the current time
        LocalDateTime now = LocalDateTime.now();
        // Cast the fecha to a date type
        LocalDateTime fecha = parseDate(date.replace("/","-"), getMinutes());
        //Return true if the fecha has passed
        if(fecha.equals(now)){
            return true;
        }else{
            return fecha.isAfter(now);
        }
    }
    public static String getMinutes(){
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() +":"+ (now.getMinute()+1) +":"+ now.getSecond();
    }
}

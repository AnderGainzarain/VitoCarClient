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
        LocalDateTime fecha = parseDate(date.replace("/","-"), "00:00:00");
        System.out.println(fecha + "\n" + now);
        //Check if the fecha has passed
        return !fecha.isBefore(now);
    }

}

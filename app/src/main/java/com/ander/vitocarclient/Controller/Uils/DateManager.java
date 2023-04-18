package com.ander.vitocarclient.Controller.Uils;

import java.time.LocalDateTime;

public class DateManager {
   public static LocalDateTime parseDate(String date, String time) {
       return LocalDateTime.parse(date + "T" + time);
    }
    public Boolean passedDate(String date){
       // Get the current time
        LocalDateTime now = LocalDateTime.now();
        // Cast the fecha to a date type
        LocalDateTime Fecha = LocalDateTime.parse(date);
        //Check if the fecha has passed
        if (Fecha.isBefore(now)) {
            return false;
        }else
            return true;
    }

}

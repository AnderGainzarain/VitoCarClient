package com.ander.vitocarclient.Controller.Uils;

import java.time.LocalDateTime;

public class DateManager {
    public static Boolean passedDate(String date, String hora){
       // Get the current time
        LocalDateTime now = LocalDateTime.now();
        // Cast the fecha to a date type
        LocalDateTime fecha = LocalDateTime.parse(date.replace("/","-") + "T" + hora);
        //Return true if the fecha has passed
        if(fecha.equals(now)){
            return true;
        }else{
            return fecha.isAfter(now);
        }
    }
}

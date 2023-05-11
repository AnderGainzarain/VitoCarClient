package com.ander.vitocarclient.Controller.Uils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.time.LocalDateTime;

public class DateAndTimePickers{
        public static void mostrarFecha(Context context, EditText fecha){
            // Get the curretn time
            LocalDateTime now = LocalDateTime.now();
            // Create a date picker dialog to set the date in the fecha field
            DatePickerDialog date = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                String f = year+"-"+ dateFormat(month+1) +"-"+dateFormat(dayOfMonth);
                fecha.setText(f);
            // set the default selected date to today
            }, now.getYear(), now.getMonthValue()-1,now.getDayOfMonth());
            // show the date picker
            date.show();
        }
        static String dateFormat(int n){
            // set a hh:mm:ss format to the date
            return (n<=9) ? ("0"+n) : String.valueOf(n);
        }

        public static void mostrarHora(Context context, EditText hora){
            // Get the currrent time
            LocalDateTime now = LocalDateTime.now();
            // Create a time picker to set the selected time to the hora field
            TimePickerDialog time = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                String h = dateFormat(hourOfDay)+":"+dateFormat(minute) + ":00";
                hora.setText(h);
                // set the current time as the default selection
            }, now.getHour(), now.getMinute(), true);
            // show the time picker
            time.show();
        }
}

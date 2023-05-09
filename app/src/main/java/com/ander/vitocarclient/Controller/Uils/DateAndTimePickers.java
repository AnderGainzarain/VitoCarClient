package com.ander.vitocarclient.Controller.Uils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.time.LocalDateTime;

public class DateAndTimePickers{
        public static void mostrarFecha(Context context, EditText fecha){
            LocalDateTime now = LocalDateTime.now();
            DatePickerDialog date = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                String f = year+"-"+ dateFormat(month+1) +"-"+dateFormat(dayOfMonth);
                fecha.setText(f);

            }, now.getYear(), now.getMonthValue()-1,now.getDayOfMonth());
            date.show();
        }
        static String dateFormat(int n){
            return (n<=9) ? ("0"+n) : String.valueOf(n);
        }

        public static void mostrarHora(Context context, EditText hora){
            LocalDateTime now = LocalDateTime.now();
            TimePickerDialog time = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                String h = dateFormat(hourOfDay)+":"+dateFormat(minute) + ":00";
                hora.setText(h);
            }, now.getHour(), now.getMinute(), true);
            time.show();
        }
}

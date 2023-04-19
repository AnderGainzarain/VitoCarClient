package com.ander.vitocarclient.Controller.Uils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class DateAndTimePickers{
        public static void mostrarFecha(View v,Context context, EditText fecha){

            DatePickerDialog date = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    fecha.setText(year+"-"+ dateFormat(month+1) +"-"+dateFormat(dayOfMonth));

                }
            }, 2023, 01,28 );
            date.show();
        }
        private static String dateFormat(int n){
            return (n<=9) ? ("0"+n) : String.valueOf(n);
        }

        public static void mostrarHora(View v, Context context, EditText hora){
            TimePickerDialog time = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hora.setText(dateFormat(hourOfDay)+":"+dateFormat(minute) + ":00");
                }
            }, 10, 15, true);
            time.show();
        }{
    }
}

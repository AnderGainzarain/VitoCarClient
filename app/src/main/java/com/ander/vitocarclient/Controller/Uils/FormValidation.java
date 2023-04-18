package com.ander.vitocarclient.Controller.Uils;

import android.widget.Toast;

import Vista.ToastControll;

public class FormValidation {
    public static String validate(String origen, String destino, String fechaSalida){
        // Fecha is emptu
        if (fechaSalida.isEmpty()){
            return "FechaVacia";
        }
        // same origen and destino
        if(origen.equals(destino)){
            return "Origen y destino iguales";
        }
        // send an error if the date is a date earlier than today
        // return nothing if data is ok
        return "";
    }
    public static String validate(String origen, String destino, String fechaSalida, int precio){
        // Fecha is empty
        if (fechaSalida.isEmpty()){
            return "FechaVacia";
        }
        // same origen and destino
        if(origen.equals(destino)){
            return "Origen y destino iguales";
        }
        // invalid precio
        if(precio <1){
            return "Precio < 1";
        }
        // send an error if the date is a date earlier than today
        // return nothing if data is ok
        return "";
    }

}

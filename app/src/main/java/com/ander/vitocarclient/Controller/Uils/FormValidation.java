package com.ander.vitocarclient.Controller.Uils;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.widget.Toast;

import Vista.ToastControll;

public class FormValidation {
    public static Boolean validate(Context context,String origen, String destino, String fechaSalida){
        // Check if there is fecha salida
        if (fechaSalida.isEmpty()){
            Toast.makeText(context, ToastControll.fechaVacia(), Toast.LENGTH_LONG).show();
            return false;
        }
        // check if there is the same origen and destino
        if(origen.equals(destino)){
            Toast.makeText(context,ToastControll.origenDestinoIguales(), Toast.LENGTH_LONG).show();
            return false;
        }
        // Check if the date has already passed
        // todo: check if the date has passed
        return true;
    }
    public static Boolean validate(Context context, String origen, String destino, String fechaSalida, int precio){
        if (validate(context,origen,destino,fechaSalida).equals(false))
            return false;
        else{
            if(precio < 1){
                Toast.makeText(context,ToastControll.precioMenorUno(), Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
    }

}

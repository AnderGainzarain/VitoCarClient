package com.ander.vitocarclient.Controller.Uils;

import android.content.Context;
import android.widget.Toast;

import com.ander.vitocarclient.Vista.TextControll;

public class FormValidation {
    public static Boolean validate(Context context,String origen, String destino, String fechaSalida){
        // Check if there is fecha salida
        if (fechaSalida.isEmpty()){
            Toast.makeText(context, TextControll.fechaVacia(), Toast.LENGTH_SHORT).show();
            return false;
        }
        // check if there is the same origen and destino
        if(origen.equals(destino)){
            Toast.makeText(context, TextControll.origenDestinoIguales(), Toast.LENGTH_SHORT).show();
            return false;
        }
        // Check if the date has already passed
        if(!DateManager.passedDate(fechaSalida)){
            Toast.makeText(context, TextControll.fechaPasada(), Toast.LENGTH_SHORT).show();

            return false;
        };
        return true;
    }
    public static Boolean validate(Context context, String origen, String destino, String fechaSalida, String precio){
        if (validate(context,origen,destino,fechaSalida).equals(false))
            return false;
        else{
            // check if the precio is valid
            if(Integer.parseInt(precio) < 1){
                Toast.makeText(context, TextControll.precioMenorUno(), Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }

}

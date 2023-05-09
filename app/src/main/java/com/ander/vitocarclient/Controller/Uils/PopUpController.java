package com.ander.vitocarclient.Controller.Uils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.User;
import com.ander.vitocarclient.Model.Viaje;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.Network.ApiViaje;
import com.ander.vitocarclient.R;
import com.ander.vitocarclient.Vista.TextControll;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpController {
    private static ActiveUser au = ActiveUser.getActiveUser();
    private static Button closeButton;
    private static Button submit;
    private static TextView origen;
    private static TextView destino;
    private static TextView fecha;
    private static TextView precio;
    private static TextView pasajero1;
    private static TextView pasajero2;
    private static TextView pasajero3;
    private static PopupWindow popupWindow;
    private static TextView conductor;
    private static TextView contacto;
    public static void show(Context context, int resource, View view){
        popupWindow = new PopupWindow(context);
        View popupView = LayoutInflater.from(context).inflate(resource, null);
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(androidx.appcompat.R.style.Animation_AppCompat_Dialog);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.rgb(225,225,225)));
        closeButton = popupView.findViewById(R.id.close_button);
        submit = popupView.findViewById(R.id.btnSubmitM);
        origen = popupView.findViewById(R.id.origenM);
        destino = popupView.findViewById(R.id.destinoM);
        fecha = popupView.findViewById(R.id.fechaSalidaM);
        precio = popupView.findViewById(R.id.precioM);
        if(resource==R.layout.mas_info_p){
            pasajero1 = popupView.findViewById(R.id.pasajero1);
            pasajero2 = popupView.findViewById(R.id.pasajero2);
            pasajero3 = popupView.findViewById(R.id.pasajero3);
        }
        if(resource==R.layout.mas_info){
            conductor = popupView.findViewById(R.id.conductorM);
            contacto = popupView.findViewById(R.id.contactoM);
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        closeButton.setOnClickListener(v -> popupWindow.dismiss());
    }
    public static void showDataViaje(String pOrigen, String pDestino, String pFecha, String pPrecio){
        origen.setText(pOrigen);
        destino.setText(pDestino);
        fecha.setText(pFecha);
        precio.setText(pPrecio);
    }
    public static void showPasajeros(int idViaje, Context context){
        getPasajeros(idViaje,context);
    }
    public static void hideSubmit(){
        submit.setAlpha(0);
    }
    public static void submitAnular(int idViaje, Context context){
        submit.setOnClickListener(view -> anularViaje(idViaje,context));
    }

    private static void anularViaje(int idViaje,Context context){
        Call<Void> call = ApiClient.getClient().create(ApiViaje.class).anularViaje(idViaje);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, TextControll.viajeAnulado(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showConductor(String a, String s) {
        conductor.setText("a");
        contacto.setText("a@a");
    }

    public static void submitReservar(int idViaje,Context context) {
        submit.setOnClickListener(view -> reservar(idViaje, context));

    }
    private static void reservar(int idViaje, Context context){
        Call<Viaje> call = ApiClient.getClient().create(ApiViaje.class).reservar(au.getDNI(),idViaje);
        call.enqueue(new Callback<Viaje>() {
            @Override
            public void onResponse(Call<Viaje> call, Response<Viaje> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, TextControll.reservaRealizada(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Viaje> call, Throwable t) {
                Toast.makeText(context, TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void submitTextAnular() {
        submit.setText(TextControll.btnAnular());
    }

    public static void submitAnularreserva(int idViaje, Context context) {
        submit.setOnClickListener(view -> anularReserva(idViaje,context));
    }

    private static void anularReserva(int idViaje, Context context){
        Call<Void> call = ApiClient.getClient().create(ApiViaje.class).anularReserva(au.getDNI(),idViaje);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, TextControll.reservaAnulada(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void submitTextReservar() {
        submit.setText(TextControll.btnReservar());
    }

    private static void getPasajeros(int idViaje,Context context) {
        Call<List<User>> call = ApiClient.getClient().create(ApiUser.class).getPasajeros(idViaje);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                if(users ==null || users.isEmpty()){
                    pasajero1.setText(TextControll.noHayPasajeros());
                    pasajero2.setText(TextControll.noHayPasajeros());
                    pasajero3.setText(TextControll.noHayPasajeros());
                }else{
                    System.out.println(users.size());
                    switch (users.size()){
                        case 1:{
                            pasajero1.setText(users.get(0).getNombre());
                            pasajero2.setText(TextControll.noHayPasajeros());
                            pasajero3.setText(TextControll.noHayPasajeros());
                            break;
                        }
                        case 2:{
                            pasajero1.setText(users.get(0).getNombre());
                            pasajero2.setText(users.get(1).getNombre());
                            pasajero3.setText(TextControll.noHayPasajeros());
                            break;
                        }
                        case 3: {
                            pasajero1.setText(users.get(0).getNombre());
                            pasajero2.setText(users.get(1).getNombre());
                            pasajero3.setText(users.get(2).getNombre());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static void showDriverData(int idViaje, Context context){
        Call<User> call = ApiClient.getClient().create(ApiUser.class).getConductorData(idViaje);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User driver = response.body();
                if(driver!=null){
                    conductor.setText(String.valueOf(driver.getDni()));
                    contacto.setText(driver.getMail());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}

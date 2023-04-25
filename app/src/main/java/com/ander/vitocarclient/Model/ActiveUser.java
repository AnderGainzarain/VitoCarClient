package com.ander.vitocarclient.Model;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.Vista.ToastControll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveUser {
    // Atributes
    private static Integer dni;
    private static int telefono;
    private static String mail;
    private static String nombre;
    private static String apellido;
    private static String foto;
    private static String coche;
    private static ActiveUser activeUser;

    //Constructor
    private ActiveUser(Integer DNI, int telefono, String mail, String nombre, String apellido, String foto, String coche){
        dni = DNI;
        ActiveUser.telefono = telefono;
        ActiveUser.mail = mail;
        ActiveUser.nombre = nombre;
        ActiveUser.apellido = apellido;
        ActiveUser.foto = foto;
        ActiveUser.coche = coche;
    }

    public Integer getDNI() {
        return dni;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getFoto() {
        return foto;
    }

    public String getCoche() {
        return coche;
    }

    public static ActiveUser getActiveUser() {
        if(activeUser==null){
            startUser();
        }

        return activeUser;
    }

    public static void startUser(){
        Call<User> call = ApiClient.getClient().create(ApiUser.class).getUser(1111);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User user = response.body();
                String c;
                if(user.getCoche().equals("")){
                    c = "No hay coche registrado";
                }else{
                    c = user.getCoche();
                }
                activeUser = new ActiveUser(user.getDni(),user.getTelefono(),user.getMail(),user.getNombre(),user.getApellido(),user.getFoto(),c);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
}

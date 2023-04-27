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
    private Integer dni;
    private int telefono;
    private String mail;
    private String nombre;
    private String apellido;
    private String foto;
    private String coche;
    private static ActiveUser activeUser;

    //Constructor
    private ActiveUser(Integer dni, int telefono, String mail, String nombre, String apellido, String foto, String coche){
        this.dni = dni;
        this.telefono = telefono;
        this.mail = mail;
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.coche = coche;
    }

    public static void initialize(User user) {
        activeUser = new ActiveUser(user.getDni(),user.getTelefono(),user.getMail(),user.getNombre(),user.getApellido(),user.getFoto(),user.getCoche());
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
        return activeUser;
    }
}

package com.ander.vitocarclient.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ander.vitocarclient.R;

import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.User;
import Network.ApiClient;
import Network.ApiUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Perfil extends Fragment {

    private User user;
    private ActiveUser au = ActiveUser.getActiveUser();
    private TextView nombre;
    private TextView apellido;
    private TextView mail;
    private TextView telefono;
    private TextView coche;
    public Perfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        nombre = view.findViewById(R.id.nombre);
        apellido = view.findViewById(R.id.apellido);
        mail = view.findViewById(R.id.mail);
        telefono = view.findViewById(R.id.telefono);
        coche = view.findViewById(R.id.coche);
        showUser(2222);
    }
    public void showUser(int dni){
        Call<User> call = ApiClient.getClient().create(ApiUser.class).getUser(dni);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                user = response.body();
                // Show the viajes in the text views
                String Coche;
                if(user == null){
                    Toast.makeText(getContext(),"No hay sesion iniciada", Toast.LENGTH_SHORT).show();
                } else {
                    String Nombre = user.getNombre();
                    String Apellido = user.getApellido();
                    String Mail = user.getMail();
                    String Telefono = String.valueOf(user.getTelefono());
                    nombre.setText(Nombre);
                    apellido.setText(Apellido);
                    mail.setText(Mail);
                    telefono.setText(Telefono);
                    if(user.getCoche()==null){
                        Coche = "No hay coche registrado";
                    } else{
                        Coche = user.getCoche();
                    }
                    coche.setText(Coche);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // return an error message if there is an error
                Toast.makeText(getContext(), Vista.ToastControll.getConectionErrorMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
package com.ander.vitocarclient.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ander.vitocarclient.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

import Model.ActiveUser;
import Model.User;
import Network.ApiClient;
import Network.ApiUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity<Busacar> extends AppCompatActivity {
    private Toolbar tb;
    private BottomNavigationView bnv;

    private ActiveUser au = ActiveUser.getActiveUser();
    private int dni = 1111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize the active user for testing
        startUser();
        // Create the fragment so it can listen to the result of the search query
        Buscar buscar = new Buscar();
        setContentView(R.layout.activity_main);
        // Get the bnv and toolbar
        tb = findViewById(R.id.toolbar1);
        bnv = findViewById(R.id.bnv);
        // set the toolbar tittle
        tb.setTitle("Buscar Viaje");
        // change the action bar with the toolbar
        setSupportActionBar(tb);
        // Set buscar viaje as the default option
        getSupportFragmentManager().beginTransaction().add(R.id.flMain, buscar).commit();
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){
                     case R.id.bnvBuscarViaje:
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new Buscar()).commit();
                         tb.setTitle("Buscar Viaje");
                         return true;
                     case R.id.bnvPerfil:
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new Perfil()).commit();
                         tb.setTitle("Perfil");
                         return true;
                     case R.id.bnvPublicarViaje:
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new PublicarViaje()).commit();
                         tb.setTitle("Publicar Viaje");
                         return true;
                     case R.id.bnvReservas:
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new VerReservas()).commit();
                         tb.setTitle("Viajes Reservados");
                         return true;
                     case R.id.bnvViajesPublicados:
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new MisViajes()).commit();
                         tb.setTitle("Viajes Publicados");
                         return true;
                 }
                return false;
            }
        });
    }
    public void startUser(){
        Call<User> call = ApiClient.getClient().create(ApiUser.class).getUser(dni);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User user = response.body();
                assert user != null;
                ActiveUser.initialize(user.getDNI(),user.getTelefono(),user.getMail(),user.getNombre(), user.getApellido(),user.getFoto(),user.getCoche());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // return an error message if there is an error
                Toast.makeText(getApplicationContext(), Vista.ToastControll.getConectionErrorMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
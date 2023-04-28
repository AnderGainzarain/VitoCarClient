package com.ander.vitocarclient.Controller;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import com.ander.vitocarclient.R;
import com.ander.vitocarclient.Vista.ToastControll;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ander.vitocarclient.Model.ActiveUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar tb;
    private Boolean logedIn;
    private final ActiveUser au = ActiveUser.getActiveUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if the user is loged in
        logedIn= au != null;
        // Create the fragment so it can listen to the result of the search query
        Buscar buscar = new Buscar();
        setContentView(R.layout.activity_main);
        // Get the bnv and toolbar
        tb = findViewById(R.id.toolbar1);
        BottomNavigationView bnv = findViewById(R.id.bnv);
        // set the toolbar tittle
        tb.setTitle("Buscar Viaje");
        // change the action bar with the toolbar
        setSupportActionBar(tb);
        // Set buscar viaje as the default option
        getSupportFragmentManager().beginTransaction().add(R.id.flMain, buscar).commit();
        bnv.setOnNavigationItemSelectedListener(item -> {
             switch (item.getItemId()){
                 case R.id.bnvBuscarViaje:
                     getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new Buscar()).commit();
                     tb.setTitle("Buscar Viaje");
                     return true;
                 case R.id.bnvPerfil:
                     if(logedIn){
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new Perfil()).commit();
                         tb.setTitle("Perfil");
                     }else{
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new LogIn()).commit();
                         tb.setTitle("Log In");
                     }
                     return true;
                 case R.id.bnvPublicarViaje:
                     if (logedIn) {
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new PublicarViaje()).commit();
                         tb.setTitle("Publicar Viaje");
                     }else{
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new LogIn()).commit();
                         tb.setTitle("Log In");
                         showPublicarNoLogueado();
                     }
                     return true;
                 case R.id.bnvReservas:
                     if(logedIn){
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new VerReservas()).commit();
                         tb.setTitle("Viajes Reservados");
                     }else{
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new LogIn()).commit();
                         tb.setTitle("Log In");
                         showReservasNoLogueado();
                     }

                     return true;
                 case R.id.bnvViajesPublicados:
                     if(logedIn){
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new MisViajes()).commit();
                         tb.setTitle("Viajes Publicados");
                     }else{
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new LogIn()).commit();
                         tb.setTitle("Log In");
                         showViajesNoLogueado();
                     }

                     return true;
             }
            return false;
        });
    }
    private void showPublicarNoLogueado(){
        Toast.makeText(this, ToastControll.publicarNoLogueado(), Toast.LENGTH_LONG).show();
    }
    private void showReservasNoLogueado(){
        Toast.makeText(this, ToastControll.reservasNoLogueado(), Toast.LENGTH_LONG).show();
    }
    private void showViajesNoLogueado(){
        Toast.makeText(this, ToastControll.reservasNoLogueado(), Toast.LENGTH_LONG).show();
    }
}
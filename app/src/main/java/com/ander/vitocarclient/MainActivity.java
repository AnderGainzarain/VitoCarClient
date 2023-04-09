package com.ander.vitocarclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Toolbar tb;
    private BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the bnv and toolbar
        tb = findViewById(R.id.toolbar1);
        bnv = findViewById(R.id.bnv);
        // set the toolbar tittle
        tb.setTitle("Buscar Viaje");
        // change the action bar with the toolbar
        setSupportActionBar(tb);
        // Set te fragment layout to show the buscar viaje fragment to show by deffault
        getSupportFragmentManager().beginTransaction().add(R.id.flMain, new BuscarViaje()).commit();

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){
                     case R.id.bnvBuscarViaje:
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new BuscarViaje()).commit();
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
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new VerViajes()).commit();
                         tb.setTitle("Viajes Publicados");
                         return true;

                 }
                return false;
            }
        });
    }
}
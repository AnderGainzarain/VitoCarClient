package com.ander.vitocarclient.Controller;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ander.vitocarclient.R;
import com.ander.vitocarclient.Vista.TextControll;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ander.vitocarclient.Model.ActiveUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Toolbar tb;
    private static Boolean logedIn = false;
    private final ActiveUser au = ActiveUser.getActiveUser();
    private static BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the fragment so it can listen to the result of the search query
        Buscar buscar = new Buscar();
        setContentView(R.layout.activity_main);
        // Get the bnv and toolbar
        tb = findViewById(R.id.toolbar1);
         bnv = findViewById(R.id.bnv);
            MenuItem menuItemReservas = bnv.getMenu().findItem(R.id.bnvReservas);
            MenuItem menuItemViajes = bnv.getMenu().findItem(R.id.bnvViajesPublicados);
            MenuItem menuItemPublicar = bnv.getMenu().findItem(R.id.bnvPublicarViaje);

            menuItemReservas.setEnabled(false);
            menuItemViajes.setEnabled(false);
            menuItemPublicar.setEnabled(false);
            menuItemReservas.setVisible(false);
            menuItemViajes.setVisible(false);
            menuItemPublicar.setVisible(false);



        // set the toolbar tittle
        tb.setTitle(TextControll.tBuscarViaje());
        // change the action bar with the toolbar
        setSupportActionBar(tb);
        // Set buscar viaje as the default option
        getSupportFragmentManager().beginTransaction().add(R.id.flMain, buscar).commit();
        bnv.setOnNavigationItemSelectedListener(item -> {
             switch (item.getItemId()){
                 case R.id.bnvBuscarViaje:
                     getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new Buscar()).commit();
                     tb.setTitle(TextControll.tBuscarViaje());
                     return true;
                 case R.id.bnvPerfil:
                     if(logedIn){
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new Perfil()).commit();
                         tb.setTitle(TextControll.tPerfil());
                     }else{
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new LogIn()).commit();
                         tb.setTitle(TextControll.tLogIn());
                     }
                     return true;
                 case R.id.bnvPublicarViaje:
                     if (logedIn) {
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new PublicarViaje()).commit();
                         tb.setTitle(TextControll.tPublicarViaje());
                     }else{
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new LogIn()).commit();
                         tb.setTitle(TextControll.tLogIn());
                         showPublicarNoLogueado();
                     }
                     return true;
                 case R.id.bnvReservas:
                     if(logedIn){
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new VerReservas()).commit();
                         tb.setTitle(TextControll.tReservas());
                     }else{
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new LogIn()).commit();
                         tb.setTitle(TextControll.tLogIn());
                         showReservasNoLogueado();
                     }

                     return true;
                 case R.id.bnvViajesPublicados:
                     if(logedIn){
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new MisViajes()).commit();
                         tb.setTitle(TextControll.tViajesPublicados());
                     }else{
                         getSupportFragmentManager().beginTransaction().replace(R.id.flMain,new LogIn()).commit();
                         tb.setTitle(TextControll.tLogIn());
                         showViajesNoLogueado();
                     }

                     return true;
             }
            return false;
        });
    }
    private void showPublicarNoLogueado(){
        Toast.makeText(this, TextControll.publicarNoLogueado(), Toast.LENGTH_SHORT).show();
    }
    private void showReservasNoLogueado(){
        Toast.makeText(this, TextControll.reservasNoLogueado(), Toast.LENGTH_SHORT).show();
    }
    private void showViajesNoLogueado(){
        Toast.makeText(this, TextControll.viajesNoLogueado(), Toast.LENGTH_SHORT).show();
    }
    public static void setLogedIn(Boolean estado){
        logedIn = estado;
        ActiveUser au = ActiveUser.getActiveUser();
        MenuItem menuItemReservas = bnv.getMenu().findItem(R.id.bnvReservas);
        MenuItem menuItemViajes = bnv.getMenu().findItem(R.id.bnvViajesPublicados);
        MenuItem menuItemPublicar = bnv.getMenu().findItem(R.id.bnvPublicarViaje);
        menuItemReservas.setEnabled(logedIn);
        menuItemViajes.setEnabled(logedIn);
        menuItemPublicar.setEnabled(logedIn);
        menuItemReservas.setVisible(logedIn);
        menuItemViajes.setVisible(logedIn);
        menuItemPublicar.setVisible(logedIn);
        if(au!=null){
            if(au.getCoche().equals(TextControll.cocheVacio())){
                menuItemReservas.setEnabled(false);
                menuItemPublicar.setEnabled(false);
                menuItemReservas.setVisible(false);
                menuItemPublicar.setVisible(false);
            }
        }

    }
}
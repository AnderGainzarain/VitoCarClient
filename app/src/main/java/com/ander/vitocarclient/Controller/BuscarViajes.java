package com.ander.vitocarclient.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ander.vitocarclient.R;

public class BuscarViajes extends AppCompatActivity {
    private Spinner sOrigen;
    private Spinner sDestino;
    private String[] ciudades = {"Vitoria", "Donostia", "Bilbo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_viajes);
        // Get the spinners from the xml
        sOrigen = findViewById(R.id.sOrigen);
        sDestino = findViewById(R.id.sDestino);
        // Create the contents of the spinners
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ciudades);
        // Load the contents into the spinners
        sOrigen.setAdapter(adaptador);
        sDestino.setAdapter(adaptador);
    }
    public void Buscar(){
        // Get the selected contents from the spinner
        String origen = sOrigen.getSelectedItem().toString();
        String destino = sDestino.getSelectedItem().toString();
    }
}
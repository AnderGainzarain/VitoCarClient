package com.ander.vitocarclient.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.ander.vitocarclient.R;

import java.util.List;

import Adapter.ViajeAdapter;
import Model.Viaje;
import Network.ApiClient;
import Network.ApiViaje;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerViajes extends AppCompatActivity {
    private List<Viaje> viajes;
    private RecyclerView rv;
    private ViajeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_viajes);
        // Bind the recycler view
        rv = findViewById(R.id.rvVerViajes);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        showViajes();
    }
    public void showViajes(){
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getViajes();
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                // Show the viajes in the recycler view
                if(response.isSuccessful()){
                    viajes=response.body();
                    adapter = new ViajeAdapter(viajes,getApplicationContext());
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                // return an error message if there is an error
                Toast.makeText(VerViajes.this,"Ha ocurrido un error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
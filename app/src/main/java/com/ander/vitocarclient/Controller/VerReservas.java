package com.ander.vitocarclient.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ander.vitocarclient.R;

import java.util.List;

import Adapter.ViajeAdapter;
import Model.Viaje;
import Network.ApiClient;
import Network.ApiUser;
import Network.ApiViaje;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerReservas extends Fragment {

    private List<Viaje> viajes;
    private RecyclerView rv;
    private ViajeAdapter adapter;
    private int dni = 3333;

    public VerReservas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_reservas, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rvVerReservas);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        showReservas();
    }
    public void showReservas(){
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getMisReservas(dni);
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                // Show the viajes in the recycler view
                if(response.isSuccessful()){
                        viajes=response.body();
                        if(viajes.isEmpty()){
                            Toast.makeText(getContext(),"No hay viajes reservados", Toast.LENGTH_LONG).show();
                        }else{
                            adapter = new ViajeAdapter(viajes,getContext());
                            rv.setAdapter(adapter);
                        }
                }
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                // return an error message if there is an error
                Toast.makeText(getContext(),"Ha ocurrido un error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
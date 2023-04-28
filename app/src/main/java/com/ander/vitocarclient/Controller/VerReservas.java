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
import android.widget.Toast;

import com.ander.vitocarclient.Controller.Uils.DateManager;
import com.ander.vitocarclient.R;

import java.util.List;
import java.util.stream.Collectors;

import com.ander.vitocarclient.Controller.Adapter.ViajeAdapter;
import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.Viaje;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiViaje;
import com.ander.vitocarclient.Vista.ToastControll;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerReservas extends Fragment {

    private List<Viaje> viajes;
    private RecyclerView rv;
    private ViajeAdapter adapter;
    private final ActiveUser au = ActiveUser.getActiveUser();
    private TabLayout tabLayout;

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
        tabLayout=view.findViewById(R.id.tabReservas);
        tabLayout.selectTab(tabLayout.getTabAt(1));
        showReservas(true);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showReservas(tab != tabLayout.getTabAt(0));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                showReservas(tab != tabLayout.getTabAt(0));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                showReservas(tab != tabLayout.getTabAt(0));
            }
        });
    }
    private void showReservas(boolean pasado){
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getMisReservas(au.getDNI());
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                // Show the viajes in the recycler view
                if(response.isSuccessful()){
                        viajes=response.body();
                        if(viajes==null||viajes.isEmpty()){
                            Toast.makeText(getContext(), ToastControll.noViajesReservados(), Toast.LENGTH_LONG).show();
                        }else{
                            List<Viaje> mostrar;
                            if (pasado){
                                mostrar = viajes.stream().filter(v -> !DateManager.passedDate(v.getFechaSalida().substring(0, 10))).collect(Collectors.toList());
                            }else{
                                mostrar = viajes.stream().filter(v -> DateManager.passedDate(v.getFechaSalida().substring(0, 10))).collect(Collectors.toList());
                            }
                            adapter = new ViajeAdapter(mostrar,getContext());
                            rv.setAdapter(adapter);
                        }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                // return an error message if there is an error
                Toast.makeText(getContext(), ToastControll.getConectionErrorMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
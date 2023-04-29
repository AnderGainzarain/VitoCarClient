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
import com.ander.vitocarclient.Vista.ToastControll;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ander.vitocarclient.Controller.Adapter.ViajeAdapter;
import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.Viaje;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MisViajes extends Fragment implements RvInterface {

    private List<Viaje> viajes;
    private RecyclerView rv;
    private ViajeAdapter adapter;
    private final ActiveUser au = ActiveUser.getActiveUser();
    private TabLayout tabLayout;

    public MisViajes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mis_viajes, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        // Bind the recycler view
        rv = view.findViewById(R.id.rvMisViajes);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // Initialize the rv adapter with an empty list
        adapter = new ViajeAdapter(new ArrayList<>(),getContext(),this);
        rv.setAdapter(adapter);
        // Bind the tab layout
        tabLayout = view.findViewById(R.id.tabMisviajes);
        // Set the default tab
        tabLayout.selectTab(tabLayout.getTabAt(1));
        getMisViajes(true);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getMisViajes(tab != tabLayout.getTabAt(0));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                getMisViajes(tab != tabLayout.getTabAt(0));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                getMisViajes(tab != tabLayout.getTabAt(0));
            }
        });
    }


    private void getMisViajes(Boolean pasado){
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiUser.class).getMisViajes(au.getDNI());
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                // Show the viajes in the recycler view
                List<Viaje> show;
                if(response.isSuccessful()){
                    viajes=response.body();
                    if (viajes ==null || viajes.isEmpty()){
                        Toast.makeText(getContext(), ToastControll.noViajesReservados(), Toast.LENGTH_LONG).show();
                    }else{
                        if(pasado){
                            show = viajes.stream()
                                    .filter(v -> !DateManager.passedDate(v.getFechaSalida().substring(0,10)))
                                    .collect(Collectors.toList());
                        }else{
                            show = viajes.stream()
                                    .filter(v -> DateManager.passedDate(v.getFechaSalida().substring(0,10)))
                                    .collect(Collectors.toList());
                        }
                        adapter = new ViajeAdapter(show, getContext(),MisViajes.this);
                        rv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                // return an error message if there is an error
                Toast.makeText(getContext(), ToastControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        // TODO: implement the popup

    }
}
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
import com.google.android.material.tabs.TabLayout;

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


public class MisViajes extends Fragment {

    private List<Viaje> viajes;
    private RecyclerView rv;
    private ViajeAdapter adapter;
    private ActiveUser au = ActiveUser.getActiveUser();
    private TabLayout tabLayout;
    private List<Viaje> pasados;
    private List<Viaje> pendeintes;

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
        // Get the viajes
        getMisViajes(1111);
        // Bind the recycler view
        rv = view.findViewById(R.id.rvMisViajes);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // Bind the tab layout
        tabLayout = view.findViewById(R.id.tabMisviajes);
        // Set the default tab
        tabLayout.selectTab(tabLayout.getTabAt(0));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showViajes(pendeintes);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                showViajes(pasados);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                showViajes(pendeintes);
            }
        });
    }

    public void showViajes(List<Viaje> p){
        adapter = new ViajeAdapter(p,getContext());
        rv.setAdapter(adapter);
    }

    public void getMisViajes(int dni){
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiUser.class).getMisViajes(dni);
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                // Show the viajes in the recycler view

                if(response.isSuccessful()){
                    viajes=response.body();
                    pendeintes = viajes.stream()
                            .filter(v -> DateManager.passedDate(v.getFechaSalida().substring(0,10)))
                            .collect(Collectors.toList());

                    pasados = viajes.stream()
                            .filter(v -> DateManager.passedDate(v.getFechaSalida().substring(0,10)))
                            .collect(Collectors.toList());

                }
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                // return an error message if there is an error
                Toast.makeText(getContext(), Vista.ToastControll.getConectionErrorMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
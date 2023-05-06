package com.ander.vitocarclient.Controller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ander.vitocarclient.Controller.Uils.DateManager;
import com.ander.vitocarclient.Controller.Uils.PopUpController;
import com.ander.vitocarclient.Model.User;
import com.ander.vitocarclient.Network.ApiViaje;
import com.ander.vitocarclient.R;
import com.ander.vitocarclient.Vista.TextControll;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private List<String> pasajeros = new ArrayList<>(Arrays.asList("Libre","Libre","Libre"));
    private RecyclerView rv;
    private ViajeAdapter adapter;
    private final ActiveUser au = ActiveUser.getActiveUser();
    private TabLayout tabLayout;
    private boolean inPasados;

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
        getMisViajes(false);
        inPasados = false;
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getMisViajes(tab == tabLayout.getTabAt(0));
                inPasados = tab == tabLayout.getTabAt(0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                getMisViajes(tab == tabLayout.getTabAt(0));
                inPasados = tab == tabLayout.getTabAt(0);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                getMisViajes(tab == tabLayout.getTabAt(0));
                inPasados = tab == tabLayout.getTabAt(0);
            }
        });
    }


    private void getMisViajes(Boolean pasado){
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiUser.class).getMisViajes(au.getDNI());
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                // Show the viajes in the recycler view
                if(response.isSuccessful()){
                    viajes=response.body();
                    if (viajes ==null || viajes.isEmpty()){
                        Toast.makeText(getContext(), TextControll.noViajesPublicados(), Toast.LENGTH_SHORT).show();
                    }else{
                        if(pasado){
                            viajes = viajes.stream()
                                    .filter(v -> !DateManager.passedDate(v.getFechaSalida().substring(0,10),v.getFechaSalida().substring(11,19)))
                                    .collect(Collectors.toList());
                        }else{
                            viajes = viajes.stream()
                                    .filter(v -> DateManager.passedDate(v.getFechaSalida().substring(0,10),v.getFechaSalida().substring(11,19)))
                                    .collect(Collectors.toList());
                        }
                        adapter = new ViajeAdapter(viajes, getContext(),MisViajes.this);
                        rv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                // return an error message if there is an error
                Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPasajeros(int idViaje) {
        System.out.println("Pide Pasajeros");
        Call<List<User>> call = ApiClient.getClient().create(ApiUser.class).getPasajeros(idViaje);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                System.out.println("Recive pasajeros");
                if(response.code()!=404){
                    System.out.println("inicializa pasajeros");
                    pasajeros.clear();
                    for(User u:users){
                        pasajeros.add(u.getNombre());
                        System.out.println(u.getNombre());
                    }
                }else{
                    pasajeros.clear();
                    pasajeros.add("Libre");
                    pasajeros.add("Libre");
                    pasajeros.add("Libre");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Viaje viaje = viajes.get(position);
        getPasajeros(viaje.getIdViaje());
        System.out.println("Pide pasajeros");
        PopUpController.show(getContext(),R.layout.mas_info_p, getView());
        PopUpController.showDataViaje(viaje.getOrigen(),viaje.getDestino(),viaje.getFechaSalida(),String.valueOf(viaje.getPrecio()));
        PopUpController.submitTextAnular();
        System.out.println("muestra pasajeros");
        PopUpController.showPasajeros(pasajeros.get(0),pasajeros.get(1),pasajeros.get(2));

        if(inPasados){
            PopUpController.hideSubmit();
        }else{
            PopUpController.submitAnular(viaje.getIdViaje(), getContext());
        }
    }
}
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
import com.ander.vitocarclient.Controller.Uils.PopUpController;
import com.ander.vitocarclient.R;
import com.ander.vitocarclient.Vista.TextControll;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Comparator;
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
        adapter = new ViajeAdapter(new ArrayList<>(),this);
        rv.setAdapter(adapter);
        // Bind the tab layout
        tabLayout = view.findViewById(R.id.tabMisviajes);
        // Set the default tab
        tabLayout.selectTab(tabLayout.getTabAt(1));
        // show the proper viajes for that tab
        inPasados = false;
        getMisViajes(false);
        // change the shown viajes if the tab is changed
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
                    // notify the user if there are not published viajes
                    if (viajes ==null || viajes.isEmpty()){
                        Toast.makeText(getContext(), TextControll.noViajesPublicados(), Toast.LENGTH_SHORT).show();
                    }else{
                        // delete the viajes that are not necesary for the tab
                        if(pasado){
                            viajes = viajes.stream()
                                    .filter(v -> !DateManager.passedDate(v.getFechaSalida().substring(0,10),v.getFechaSalida().substring(11,19)))
                                    .collect(Collectors.toList());
                        }else{
                            viajes = viajes.stream()
                                    .filter(v -> DateManager.passedDate(v.getFechaSalida().substring(0,10),v.getFechaSalida().substring(11,19)))
                                    .collect(Collectors.toList());
                        }
                        // Sort the viajes
                        viajes =viajes.stream().sorted(Comparator.comparing(Viaje::getFechaSalida).thenComparing(Viaje::getOrigen).thenComparing(Viaje::getDestino)).collect(Collectors.toList());
                        //show the viajes
                        adapter = new ViajeAdapter(viajes, MisViajes.this);
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



    @Override
    public void onItemClick(int position) {
        // get the selected viaje
        Viaje viaje = viajes.get(position);
        // Show the popup
        PopUpController.show(getContext(),R.layout.mas_info_p, getView());
        // Show the viaje data for the selected viaje
        PopUpController.showDataViaje(viaje.getOrigen(),viaje.getDestino(),viaje.getFechaSalida(),String.valueOf(viaje.getPrecio()));
        // Set the text for the submit button
        PopUpController.submitTextAnular();
        // Show the names of the pasajeros
        PopUpController.showPasajeros(viaje.getIdViaje(),getContext());
        // If the viaje is pasado hide the anular button, if not set it to anular a viaje
        if(inPasados){
            PopUpController.hideSubmit();
        }else{
            PopUpController.submitAnular(viaje.getIdViaje(), getContext());
        }
    }
}
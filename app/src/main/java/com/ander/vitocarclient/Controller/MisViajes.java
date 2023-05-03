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
import com.ander.vitocarclient.Network.ApiViaje;
import com.ander.vitocarclient.R;
import com.ander.vitocarclient.Vista.TextControll;
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
    public void anularViaje(int idViaje){
        Call<Void> call = ApiClient.getClient().create(ApiViaje.class).anularViaje(idViaje);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), TextControll.viajeAnulado(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Viaje viaje = viajes.get(position);
        PopupWindow popupWindow = new PopupWindow(getContext());
        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.mas_info, null);
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(androidx.appcompat.R.style.Animation_AppCompat_Dialog);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.rgb(225,225,225)));
        Button closeButton = popupView.findViewById(R.id.close_button);
        Button submit = popupView.findViewById(R.id.btnreservarM);
        TextView origen = popupView.findViewById(R.id.origenM);
        TextView destino = popupView.findViewById(R.id.destinoM);
        TextView fecha = popupView.findViewById(R.id.fechaSalidaM);
        TextView precio = popupView.findViewById(R.id.precioM);

        origen.setText(viaje.getOrigen());
        destino.setText(viaje.getDestino());
        fecha.setText(viaje.getFechaSalida());
        precio.setText(String.valueOf(viaje.getPrecio()));

        submit.setText(TextControll.btnAnular());
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
        closeButton.setOnClickListener(v -> popupWindow.dismiss());
        if(inPasados){
            submit.setAlpha(0);
        }else{
            submit.setOnClickListener(view -> anularViaje(viajes.get(position).getIdViaje()));
        }
    }
}
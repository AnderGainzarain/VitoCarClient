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
import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.User;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.R;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ander.vitocarclient.Controller.Adapter.ViajeAdapter;
import com.ander.vitocarclient.Model.Viaje;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiViaje;
import com.ander.vitocarclient.Vista.TextControll;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultadosBusqueda extends Fragment implements RvInterface {

    private List<Viaje> viajes;
    private List<Viaje> misViajes;
    private ViajeAdapter adapter;
    private RecyclerView rv;
    private final ActiveUser au = ActiveUser.getActiveUser();
    private final Map<String,String> queryData = new HashMap<>();
    public ResultadosBusqueda() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("query", this, (requestKey, result) -> {
            queryData.put("origen",result.getString("origen"));
            queryData.put("destino", result.getString("destino"));
            queryData.put("fechaSalida",result.getString("fechaSalida"));
            queryData.put("horaSalida",result.getString("horaSalida"));
            misViajes();
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resultados_busqueda, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rvResultadosBusqueda);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
    private void misViajes(){
        if(au!=null){
            Call<List<Viaje>> call = ApiClient.getClient().create(ApiUser.class).getMisViajes(au.getDNI());
            call.enqueue(new Callback<List<Viaje>>() {
                @Override
                public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                    if(response.isSuccessful()){
                        List<Viaje>aux=response.body();
                        if(aux!=null){
                            misViajes = aux;
                        }
                        busqueda();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else busqueda();

    }
    private void busqueda(){
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getViajeConcreto(queryData.get("origen"),queryData.get("destino"), DateManager.parseDate(queryData.get("fechaSalida"),queryData.get("horaSalida")));
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                // Show the viajes in the recycler view
               if(response.isSuccessful()){
                    viajes=response.body();
                    if(viajes == null || viajes.isEmpty()){
                        Toast.makeText(getContext(), TextControll.noHayBusqueda(), Toast.LENGTH_SHORT).show();
                    }else{
                        if(au!=null){
                            for(int i = 0; i <viajes.size();i++){
                                for(int j = 0; j<misViajes.size();j++){
                                    if(viajes.get(i).getIdViaje()==misViajes.get(j).getIdViaje()){
                                        viajes.remove(i);
                                    }
                                }
                            }
                        }
                        viajes =viajes.stream().sorted(Comparator.comparing(Viaje::getFechaSalida)).collect(Collectors.toList());
                        adapter = new ViajeAdapter(viajes,getContext(),ResultadosBusqueda.this);
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
        if(au!=null){
            Viaje viaje = viajes.get(position);
            PopUpController.show(getContext(),R.layout.mas_info,getView());
            PopUpController.showDataViaje(viaje.getOrigen(),viaje.getDestino(),viaje.getFechaSalida(),String.valueOf(viaje.getPrecio()));
            PopUpController.showDriverData(viaje.getIdViaje(), getContext());
            PopUpController.submitTextReservar();
            PopUpController.submitReservar(viaje.getIdViaje(), getContext());
        }
    }
    private void getNumReservas(int idViaje, int index){
        if(idViaje==6) System.out.println("Entra");
        Call<List<User>> call = ApiClient.getClient().create(ApiUser.class).getPasajeros(idViaje);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User>pasajeros = response.body();
                if(pasajeros == null || pasajeros.isEmpty()){
                    return;
                }else{
                    if(idViaje==6) System.out.println("nPasajeros:" + pasajeros.size() + " index: " + index);
                    if(pasajeros.size()==3){
                        viajes.remove(index);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
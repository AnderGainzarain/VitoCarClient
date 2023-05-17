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

import com.ander.vitocarclient.Controller.Uils.PopUpController;
import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.User;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.R;

import java.time.LocalDateTime;
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
    private List<Viaje> misReservas;
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
        // retrieve the query data from the buscar fragment
        getParentFragmentManager().setFragmentResultListener("query", this, (requestKey, result) -> {
            // store the query data in a class atribute
            queryData.put("origen",result.getString("origen"));
            queryData.put("destino", result.getString("destino"));
            queryData.put("fechaSalida",result.getString("fechaSalida"));
            queryData.put("horaSalida",result.getString("horaSalida"));
            // start the process to show the found viajes
            misReservas();
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
        // bind the recycler view and create an adapter
        rv = view.findViewById(R.id.rvResultadosBusqueda);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
    private void misReservas(){
        // if the user is logged in get their reservas
        if(au!=null){
            Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getMisReservas(au.getDNI());
            call.enqueue(new Callback<List<Viaje>>() {
                @Override
                public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                    if(response.isSuccessful()){
                        List<Viaje>aux=response.body();
                        if(aux!=null){
                            misReservas = aux;
                        }
                        // continue the process to show the viajes
                        misViajes();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            // if the user is not logged in continue with the process to show the viajes
        }else misViajes();

    }
    private void misViajes(){
        // if the user is logged in get the viajes they are offering
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
                        // continue with the process to show the viajes
                        busqueda();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            // continue with the process to show the viajes
        }else busqueda();

    }
    private void busqueda(){
        // get all the viajes that match the query data
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getViajeConcreto(queryData.get("origen"),queryData.get("destino"), LocalDateTime.parse(queryData.get("fechaSalida") + "T" + queryData.get("horaSalida")));
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
               if(response.isSuccessful()){
                    viajes=response.body();
                    // notify the user if there are not viajes that match the query data
                    if(viajes == null || viajes.isEmpty()){
                        Toast.makeText(getContext(), TextControll.noHayBusqueda(), Toast.LENGTH_SHORT).show();
                    }else{
                        if(au!=null){
                            // if the user is logged in delete the viajes they have published
                            for(int i = 0; i <viajes.size();i++){
                                for(int j = 0; j<misViajes.size();j++){
                                    if(viajes.get(i).getIdViaje()==misViajes.get(j).getIdViaje()){
                                        viajes.remove(i);
                                    }
                                }
                            }
                            // if the user is logged in delete the viajes they have already booked
                            for(int i = 0; i <viajes.size();i++){
                                for(int j = 0; j<misReservas.size();j++){
                                    if(viajes.get(i).getIdViaje()==misReservas.get(j).getIdViaje()){
                                        viajes.remove(i);
                                    }
                                }
                            }
                        }
                        // don't show the viajes with 3 reservas
                        for(int k = 0; k < viajes.size(); k++){
                            getNumReservas(viajes.get(k).getIdViaje(),k);
                        }
                        // Short the viajes and show them
                        viajes =viajes.stream().sorted(Comparator.comparing(Viaje::getFechaSalida)).collect(Collectors.toList());
                        adapter = new ViajeAdapter(viajes,ResultadosBusqueda.this);
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
    private void getNumReservas(int idViaje, int index){
        Call<List<User>> call = ApiClient.getClient().create(ApiUser.class).getPasajeros(idViaje);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User>pasajeros = response.body();
                if(pasajeros == null || pasajeros.isEmpty()){
                    return;
                }else{
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

    @Override
    public void onItemClick(int position) {
        // if the user is logged in allow a popup with more data to appear
        if(au!=null){
            // Get the data of the selected viaje
            Viaje viaje = viajes.get(position);
            // Show the pop up
            PopUpController.show(getContext(),R.layout.mas_info,getView());
            // Show the data of the selected viaje
            PopUpController.showDataViaje(viaje.getOrigen(),viaje.getDestino(),viaje.getFechaSalida(),String.valueOf(viaje.getPrecio()));
            // show the data of the driver of the viaje
            PopUpController.showDriverData(viaje.getIdViaje(), getContext());
            // set the submit button text to reservar
            PopUpController.submitTextReservar();
            // add an on click listener to the submit button to execute a reserva
            PopUpController.submitReservar(viaje.getIdViaje(), getContext());
        }
    }
}
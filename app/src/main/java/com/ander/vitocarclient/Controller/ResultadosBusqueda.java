package com.ander.vitocarclient.Controller;

import android.content.Context;
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
import com.ander.vitocarclient.Controller.Uils.ShowViajes;
import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.User;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
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

    private static List<Viaje> viajes = new ArrayList<>();
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
            // show viajes
            //viajes = ShowViajes.showSearchResults(getContext(),queryData,rv,ResultadosBusqueda.this);
            showResults();
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
    private void showResults(){
        Integer dni;
        if(au!=null) {
            dni = au.getDNI();
        }else{
            dni = 0;
        }
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getViajeConcreto(queryData.get("origen"),queryData.get("destino"), LocalDateTime.parse(queryData.get("fechaSalida") + "T" + queryData.get("horaSalida")), dni);
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                List<Viaje> results = response.body();
                if(results != null && !results.isEmpty()){
                    viajes = results.stream().sorted(Comparator.comparing(Viaje::getFechaSalida)
                            .thenComparing(Viaje::getOrigen)
                            .thenComparing(Viaje::getDestino)
                            .thenComparing(Viaje::getPrecio)).collect(Collectors.toList());
                    ViajeAdapter adapter = new ViajeAdapter(viajes, ResultadosBusqueda.this);
                    rv.setAdapter(adapter);
                }else{
                    Toast.makeText(getContext(), TextControll.noHayBusqueda(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
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
        }else{
            Toast.makeText(getContext(), TextControll.needLogIn(),Toast.LENGTH_SHORT).show();

        }
    }
}




